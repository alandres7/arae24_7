import { DetalleRutasCercanasComponent } from './../../components/detalle-rutas-cercanas/detalle-rutas-cercanas.component';
import { Geoposition } from '@ionic-native/geolocation';
import { MenuFavoritoPopoverComponent } from './../../components/menu-favorito-popover/menu-favorito-popover.component';
import { LocationProvider } from './../../providers/location';
import { UbicacionFavoritaPage } from './../ubicacion-favorita/ubicacion-favorita.page';
import { UbicacionFavorita } from './../../models/UbicacionFavorita.model';
import { MapaComponent } from './../../../core/mapa/mapa';
import { RutasCercanasRequest } from './../../models/rutasCercanasRequest.model';
import { Component, Output, EventEmitter } from "@angular/core";
import { NavController, NavParams, ViewController, ToastController,PopoverController } from "ionic-angular";
import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";

declare var google;

import { GmapsMovilidad } from "../../providers/gmapsMovilidad";
import { RutasCercanasReponse } from "../../models/rutasCercanasReponse.model";
import { WsMovilidad } from "../../providers/wsMovilidad";
import { Common } from "../../../shared/utilidades/common";
import { DisponibilidadEnciclaRequest } from "../../models/disponibilidadEnciclaRequest.model";
import { DisponibilidadEnciclaResponse } from "../../models/disponibilidadEnciclaResponse.model";
import { ModoEstacion } from "../../providers/constantesMovilidad";
import { LayerProvider } from '../../../../providers/layer/layer';
import { PreferencesProvider } from '../../../../providers/preferences/preferences';
import { AppLayer } from '../../../../entities/app-layer';
import { TransportPreferences } from '../../../../entities/preferences/transport-preferences';
import { delay } from 'rxjs/operator/delay';

@Component({
    selector: "menu-rutas",
    templateUrl: "menu-rutas.page.html"
})

export class MenuRutasPage {
    @Output()
    outClickRutas? = new EventEmitter();
    @Output()
    outClickLineas? = new EventEmitter();
    @Output()
    outClickCiclovias? = new EventEmitter();

    rutas: Array<{}>;
    ubicacion: Ubicacion = new Ubicacion();
    ubicacion_actual: Ubicacion = new Ubicacion();
    
    showDetalle: boolean;
    showPuntos: boolean;
    origen?: any;
    modalOrigen: any;
    items: any;
    imgDetalle: any;
    txtDetalle: any;
    preferenciasTransportes: any;
    checked: boolean = false;
    showMenu = false;
    rutasCercanas = false;
    activeCard:boolean = false;
    ubicacionesFavoritas: UbicacionFavorita[];
    ubicacionFavoritaPage:any;
    sessionStart:boolean = true;

    markers = [];
    markersPuntoRecarga = [];
    puntosRutas: RutasCercanasReponse;
    rutasCercanasRequest: RutasCercanasRequest;
    cardActiveValue:number= 0;
    public actionRadius:number;
    public app:any = this;
    titulo: string = "Rutas Cercanas";

    public rutasActive:boolean = false;
    public lineasActive:boolean = false;
    public cicloActive:boolean = false;
    public puntosActive:boolean = false;

    public sidebarEvent:any;
    public markerEvent: any;

    autocompleteItems:any[];
    acService: any;

    public imgRutas: string = "assets/movilidad/iconos/bus.png";
    public imgLineas: string = "assets/movilidad/iconos/tranvia.png";
    public imgCiclovias: string = "assets/movilidad/iconos/encicla.png";
    public imgpuntos: string =
        "assets/movilidad/markers/civicarecargaMarker.png";
    public txtRutas: string = "Rutas";
    public txtLineas: string = "Lineas";
    public txtCiclovias: string = "EnCicla";
    public txtpuntos: string = "Recarga";

    constructor(
        public navCtrl: NavController,
        public navParams: NavParams,
        public viewCtrl: ViewController,
        public googleMaps: GoogleMaps,
        public wsMovilidad: WsMovilidad,
        private utilidades: Common,
        private layerProvider: LayerProvider,
        private preferencesProvider: PreferencesProvider,
        private locationProvider: LocationProvider,
        private toastCtrl: ToastController,
        private popoverCtrl:PopoverController  ) {
            this.ubicacion.txtPlaceholder = "Ubicación"
            this.ubicacion.longitud = 0;
            this.ubicacion.latitud = 0;
        this.acService = new google.maps.places.AutocompleteService();
    }

    ngOnInit() {
        this.actionRadius = GmapsMovilidad.obtenerRadio();
        this.subscribeServices();
    }

    ionViewDidEnter(){
        let idUsuario=this.utilidades.obtenerUsuarioActivo().id;
        this.onObtenerUbicacionesFavoritas(idUsuario);
        this.markers = []
    }


    seleccionarPrediccion(item:any){
        this.utilidades.presentLoading();
        GmapsMovilidad.codificarDireccion(item.description, "address")
          .then(data => {
            this.utilidades.dismissLoading();

            this.ubicacion.latitud = data.latitud;
            this.ubicacion.longitud = data.longitud;
            this.ubicacion.descripcion = item.description;

            this.autocompleteItems = [];
            this.updateCreatePositionMarker(this.ubicacion);
          })
          .catch(error => {
            this.utilidades.dismissLoading();
            this.utilidades.basicAlert(
              "Movilidad",
              "Ocurrió un inconveniente inténtelo nuevamente"
            );
          });
    }

    subscribeServices():void{
        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
                this.actionRadius = app.radius;
                GmapsMovilidad.setRadio(this.actionRadius);
            }
        );
        
     this.sidebarEvent =  this.preferencesProvider.transportsPreferences$.subscribe(
            (transportsPreferences: TransportPreferences[]) => {
                this.preferenciasTransportes = transportsPreferences;
                if(this.ubicacion.longitud != 0 && this.ubicacion.latitud != 0 ){
                    this.updateCreatePositionMarker(this.ubicacion);
                }
            }
        );
        
        MapaComponent.dragedPedestrian$.subscribe((dragedComponent:any)=>{
            this.ubicacion.latitud = dragedComponent.lat;
            this.ubicacion.longitud = dragedComponent.lng;
            if(this.sessionStart){
                this.sessionStart = false;
            }else{
                this.updateCreatePositionMarker(this.ubicacion)
            }
        })

       this.markerEvent =  GmapsMovilidad.markerClick$.subscribe((data:any)=>{
            this.showDetailModal(data)
        })

    }

    updateSearch() {
        try {
            if (this.ubicacion.descripcion == "") {
              this.autocompleteItems = [];
              this.showMenu = false;
              this.showDetalle = false;
              return;
            }

            let self = this;
            let defaultBounds = new google.maps.LatLngBounds(
              new google.maps.LatLng(6.075967, -75.633433),
              new google.maps.LatLng(6.450092, -75.323971)
            );
              
            let config = {
              input: self.ubicacion.descripcion,
              bounds: defaultBounds,
              componentRestrictions: { country: "CO" }
            };
              
            this.acService.getPlacePredictions(config, function(predictions, status) {
              self.autocompleteItems = [];
              if (predictions != null) {
                  predictions.forEach(function(prediction) {
                  self.autocompleteItems.push(prediction);
                });
              }
          });
        } catch (error) {
          console.error(error);
        }
      }

    showDetailModal(data:any){
        let modalDetail = this.utilidades.createModal(DetalleRutasCercanasComponent, {'data': data})
        modalDetail.onDidDismiss(()=>{
            console.log('Close modal')
        })
        modalDetail.present();

    }

    buscarDireccion(event: any) {
        if (event.keyCode == 13) {
            let item = { description: this.ubicacion.descripcion };
            this.seleccionarPrediccion(item);
        }
    }

    ionViewWillLeave() {
            this.sidebarEvent.unsubscribe();
            this.markerEvent.unsubscribe();
            GmapsMovilidad.deleteLocationMarker();
            this.utilidades.presentLoading()
            setTimeout(()=>{
                this.utilidades.dismissLoading();
                this.deleteAllMapMarkers();
            },1000);
    }

    centrarMapa(data: any, zoom: number) {
        let map = GmapsMovilidad.getMapa();
        let latlng = new google.maps.LatLng(data.latitud, data.longitud);
        map.setCenter(latlng);
        map.setZoom(zoom);
    }

    onObtenerDisponibilidadEncicla( data: DisponibilidadEnciclaRequest,marker: any) {
        this.wsMovilidad.obtenerDisponibilidadEncicla(data).subscribe(
            succces => {
                this.utilidades.dismissLoading();
                let data = succces as DisponibilidadEnciclaResponse;

                marker.dataRutaCercana.cantidadPuestosDisponibles = data.cantidadPuestosDisponibles;
                marker.dataRutaCercana.cantidadBicicletasDisponibles = data.cantidadBicicletasDisponibles;
                let infoWindow = GmapsMovilidad.crearInfoWindow(marker.dataRutaCercana, marker);
            },
            error => {
                this.utilidades.dismissLoading();
                this.utilidades
                    .basicAlert(
                        "Movilidad",
                        "Ocurrió un inconveniente inténtelo nuevamente"
                    )
                    .then(data => {});
            }
        );
    }

    onClickRutas() {
        if (this.puntosRutas.rutas.length > 0) {
            this.rutasActive = !this.rutasActive;
            this.pintarMarkersByFiltro();
        } else {
            this.utilidades
                .basicAlert(
                    "Movilidad",
                    "No existen rutas cercanas en la ubicacion ingresada"
                )
                .then(data => {});
        }
    }

    onClickLineas() {
        if (this.puntosRutas.lineas.length > 0) {
            this.lineasActive = !this.lineasActive;
            this.pintarMarkersByFiltro();
        } else {
            this.utilidades
                .basicAlert(
                    "Movilidad",
                    "No existen lineas cercanas en la ubicacion ingresada"
                )
                .then(data => {});
        }
    }

    onClickCiclovias() {
        if (this.puntosRutas.ciclovias.length > 0) {
            this.cicloActive = !this.cicloActive;
            this.pintarMarkersByFiltro();
        } else {
            this.utilidades
                .basicAlert(
                    "Movilidad",
                    "No existen estaciones EnCicla cercanas en la ubicacion ingresada"
                )
                .then(data => {});
        }
    }

    onClickPuntos() {
        if (this.puntosRutas.puntosTarjetaCivica.length > 0) {
            this.puntosActive = !this.puntosActive;
            this.pintarMarkersByFiltro();
        } else {
            this.utilidades
                .basicAlert(
                    "Movilidad",
                    "No existen puntos de recarga cercanos en la ubicacion ingresada"
                )
                .then(data => {});
        }
    }

    onClickOpcionMenu(event) {
        this.showDetalle = !this.showDetalle;
        this.showPuntos = false;
        this.setDetalles(event);
    }


    private pintarMarkersByFiltro(){
        GmapsMovilidad.eliminarMarkersPolylines(this.markers)
        this.markers = []
        if(this.rutasActive){
            this.pintarMarkerRutasCercanas(this.puntosRutas.paraderos)
        }

        if(this.lineasActive){
            let estacionesMetro = this.puntosRutas.estaciones.filter(estacion => estacion.nombreModoEstacion == 'METRO' || estacion.nombreModoEstacion == 'TRANVIA' || estacion.nombreModoEstacion == 'METRO_PLUS' || estacion.nombreModoEstacion == 'METRO_CABLE')
            this.pintarMarkerRutasCercanas(estacionesMetro)
        }

        if(this.cicloActive){
            let estacionesEncicla = this.puntosRutas.estaciones.filter(estacion => estacion.nombreModoEstacion == 'ENCICLA')
            this.pintarMarkerRutasCercanas(estacionesEncicla)
        }

        if(this.puntosActive){
            this.pintarMarkerRutasCercanas(this.puntosRutas.puntosTarjetaCivica)
        }
    }

    private setDetalles(d) {
        this.imgDetalle = d.imgDetalle;
        this.txtDetalle = d.txtDetalle;
        this.items = d.items;
    }

    checkMostrarRuta(event, ruta, index) {
        if (event.checked) {
            if (ruta.idPunto) {
                this.pintarMarkers(ruta);
                this.centrarMapa(ruta, 16);
            }
            ruta.checked = true;
            this.showPuntos = false;
        } else {
            ruta.checked = false;
        }
    }

    onPintarMarkerRutas(rutasCercanasReponse: any) {
        this.showMenu = true;
        GmapsMovilidad.eliminarMarkersPolylines(this.markers)
        this.markers = [];

        this.pintarMarkerRutasCercanas(rutasCercanasReponse.estaciones);
        this.pintarMarkerRutasCercanas(rutasCercanasReponse.paraderos);
    }

    private deleteAllMapMarkers(){
        this.markers.map((elm)=>{
            elm.setMap(null);
        })
    }

    mostrarRuta(event) {
        if (event.visible) {
            this.showDetalle = false;
        }
    }

    private pintarMarkerRutasCercanas(listaRutaCercanas: any) {
        if (listaRutaCercanas.idPunto) {
            this.pintarMarkers(listaRutaCercanas);
            return;
        }

        for (var index = 0; index < listaRutaCercanas.length; index++) {
            let dataMarker = {
                icono: GmapsMovilidad.obtenerIconoMarker(
                    listaRutaCercanas[index]
                ),
                mLat: listaRutaCercanas[index].latitud,
                mLng: listaRutaCercanas[index].longitud
            };

            let marker = GmapsMovilidad.pintarMarker(dataMarker);
            marker.dataRutaCercana = listaRutaCercanas[index];
            if (
                marker.dataRutaCercana.nombreModoEstacion ==
                ModoEstacion.ENCICLA
            ) {
                this.agregarDisponibildadEncicla(marker);
            } else {
                GmapsMovilidad.agregarInfoParadero(marker);
            }
            if (
                marker.dataRutaCercana.codigoParadero ||
                marker.dataRutaCercana.codigoParadero
            ) {
                GmapsMovilidad.agregarInfoParadero(marker);
            }
            this.markers.push(marker);
        }
    }

    pintarMarkers(data: any) {
        if (data.checked) {
            let dataMarker = {
                icono: GmapsMovilidad.obtenerIconoMarker(data),
                mLat: data.latitud,
                mLng: data.longitud
            };
            let marker = GmapsMovilidad.pintarMarker(dataMarker);
            marker.dataRutaCercana = data;
            if (
                marker.dataRutaCercana.nombreModoEstacion ==
                ModoEstacion.ENCICLA
            ) {
                this.agregarDisponibildadEncicla(marker);
            } else {
                GmapsMovilidad.agregarInfoParadero(marker);
            }
            if (
                marker.dataRutaCercana.codigoParadero ||
                marker.dataRutaCercana.codigoParadero
            ) {
                GmapsMovilidad.agregarInfoParadero(marker);
            }
            this.markersPuntoRecarga.push(marker);
        }
    }



    ocultarMarkers():void {
        GmapsMovilidad.eliminarMarkersPolylines(this.markersPuntoRecarga);
    }

    private agregarDisponibildadEncicla(marker: any) {
        google.maps.event.addListener(
            marker,
            "click",
            ((marker, me) => {
                return () => {
                    let data = new DisponibilidadEnciclaRequest(
                        "token",
                        marker.dataRutaCercana.idEstacion,
                        "fecha"
                    );
                    this.utilidades.presentLoading();
                    this.onObtenerDisponibilidadEncicla(data, marker);
                };
            })(marker, this)
        );
    }
    
    obtenerUbicacionActual() {
            let data:Geoposition = this.locationProvider.getMyLocation();
            this.ubicacion.latitud = data.coords.latitude;
            this.ubicacion.longitud = data.coords.longitude
            this.ubicacion.descripcion =  'Mi ubicación'
            this.updateCreatePositionMarker(this.ubicacion)
    }

    clickUbicacionSeleccionada(lat, lng) {
        this.markers = []
        this.lineasActive = false;
        this.cicloActive = false;
        this.rutasActive = false;
        this.puntosActive = false;

        let res = this.actionRadius / 1000;
        let modosTransportes = this.utilidades.obtenerModosTransportesActivos(this.preferenciasTransportes);

        this.rutasCercanasRequest = new RutasCercanasRequest(
            new Date().toISOString().substring(0, 10),
            lat,
            lng,
            modosTransportes,
            res
        );
            this.onObtenerRutasCercanas();
    }


    onObtenerRutasCercanas() {
        this.utilidades.presentLoading()
        this.wsMovilidad.obtenerRutasCercanas(this.rutasCercanasRequest).subscribe(
            succces => {
              let data = succces as RutasCercanasReponse;
              this.wsMovilidad.responseRutasCercanas = data;
              this.extraeRespuestaRutasCercanas(data);
              this.utilidades.dismissLoading()
            },
            error => {
              this.utilidades.dismissLoading();
              console.log("Error", error);
              if (error.status == 404) {
                this.utilidades
                  .basicAlert(
                    "Movilidad",
                    "No existen rutas cercanas en la ubicacion y preferencias de transportes especificada"
                  )
                  .then(data => {});
              } else {
                this.utilidades
                  .confirmAlert(
                    "Movilidad",
                    "Ocurrió un inconveniente inténtelo nuevamente"
                  )
                  .then(data => {})
                  .catch(data => {});
              }
            }
          );
    }


    private extraeRespuestaRutasCercanas(data: RutasCercanasReponse) {
        if (data.estaciones.length > 0 || data.paraderos.length > 0 || data.ciclovias.length > 0) {
            this.puntosRutas = data;
            this.onPintarMarkerRutas(data)
        } else {
            this.utilidades.basicAlert("Movilidad", "No existen rutas cercanas en la ubicacion y preferencias de transportes especificada")
            .then(data => {
                this.utilidades.dismissLoading()
            });
        }
    }

    onObtenerUbicacionesFavoritas(idUsuario:number) {
        this.wsMovilidad.obtenerUbicacionesFavoritas(idUsuario)
          .subscribe(
          succces => {
            this.wsMovilidad.ubicacionesFavoritas=succces;
            this.ubicacionesFavoritas=this.wsMovilidad.ubicacionesFavoritas;
          },
          error => {
            this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
          }
          );
    }

    establecerUbicacion(itemUbicacionFavorita:UbicacionFavorita, event) {
        this.ubicacion.descripcion = itemUbicacionFavorita.nombre;
        this.ubicacion.latitud = itemUbicacionFavorita.coordenada[0];
        this.ubicacion.longitud = itemUbicacionFavorita.coordenada[1];  
        this.updateCreatePositionMarker(this.ubicacion)
    }

    private updateCreatePositionMarker(ubicacion: Ubicacion){
        GmapsMovilidad.eliminarMarkersPolylines(this.markers)
        this.markers = [];
        if(ubicacion != undefined){
            GmapsMovilidad.createUpdatePositionMarker(ubicacion.latitud, ubicacion.longitud)
            GmapsMovilidad.centrarMapa(ubicacion.latitud, ubicacion.longitud);
            this.clickUbicacionSeleccionada(ubicacion.latitud, ubicacion.longitud)
        }
    }

    nuevaUbicacionFavorita() {
        let ubicacion = new Ubicacion();
        ubicacion.descripcion = "";
        ubicacion.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
        ubicacion.txtPlaceholder = "Nueva ubicación favorita";
        this.ubicacionFavoritaPage = this.navCtrl.push(UbicacionFavoritaPage, {});
    }

    mostrarOpcionesUbicacion(event, ubicacion) {
        let popCotrl = this.popoverCtrl.create(MenuFavoritoPopoverComponent, {
          ubicacion: ubicacion,
          rootNavCtrl: this.navCtrl
        });
        popCotrl.present();
      }


      public clearValues(){
          if(this.showMenu){
              this.ubicacion.descripcion = "";
          }
      }
}
