import { Component, Input } from "@angular/core";
import {
  NavController,
  NavParams,
  ViewController,
  PopoverController,
  ModalController
} from "ionic-angular";

import { EstablecerUbicacionPage } from "../../pages/establecer-ubicacion/establecer-ubicacion.page";
import { MenuRutasPage } from "../../pages/menu-rutas/menu-rutas.page";
import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { RutasCercanasRequest } from "../../models/rutasCercanasRequest.model";
import { Common } from "../../../shared/utilidades/common";
import { WsMovilidad } from "../../providers/wsMovilidad";
import { RutasCercanasReponse } from "../../models/rutasCercanasReponse.model";
import { GmapsMovilidad } from "../../providers/gmapsMovilidad";
import { SideMenu } from "../../../menu/page/side-menu";
import { LayerProvider } from "../../../../providers/layer/layer";
import { PreferencesProvider } from "../../../../providers/preferences/preferences";
import { AppLayer } from "../../../../entities/app-layer";
import { TransportPreferences } from "../../../../entities/preferences/transport-preferences";


@Component({
  selector: "rutas-cercanas",
  templateUrl: "rutas-cercanas.component.html"
})
export class RutasCercanasComponent {
  @Input() modo: string;

  componenteFavorito: boolean;
  mostrarRutas: boolean;
  seleccionarMapaModal: boolean;
  ubicacion?: Ubicacion;
  ubicacionModal: boolean;
  ubicacionFavoritaModal: boolean;
  items: any;
  private radio: number;
  private preferenciasTransportes: any[];
  private actionRadiusCircle: google.maps.Circle;

  rutasCercanasRequest: RutasCercanasRequest;

  imgDetalle: any;
  txtDetalle: any;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public wsMovilidad: WsMovilidad,
    public googleMaps: GoogleMaps,
    public popoverCtrl: PopoverController,
    private utilidades: Common,
    private layerProvider: LayerProvider,
    private preferencesProvider: PreferencesProvider
  ) {
    this.componenteFavorito = false;
    this.mostrarRutas = false;
    this.ubicacionFavoritaModal = false;
    this.seleccionarMapaModal = false;
    this.ubicacion = new Ubicacion();
    this.ubicacion.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
    this.ubicacion.txtPlaceholder = "Origen";
    this.ubicacion.cbDescripcionChange = this.cbFinalizarUbicacion();

    this.subscribeServices();
  }

  clickUbicacionSeleccionadaDismiss() {
    console.log(
      "ViajesSugeridosComponent:clickUbicacionSeleccionadaDismiss",
      this
    );
    this.navCtrl.pop();

    console.log(
      "RutasCercanasComponent:clickUbicacionSeleccionada",
      this.ubicacion
    );
  
    let res = this.radio / 1000;
    let modosTransportes = this.utilidades.obtenerModosTransportesActivos(this.preferenciasTransportes);
    this.utilidades.presentLoading();

    this.rutasCercanasRequest = new RutasCercanasRequest(
      new Date().toISOString().substring(0, 10),
      this.ubicacion.latitud,
      this.ubicacion.longitud,
      modosTransportes,
      res
    );

    this.onObtenerRutasCercanas();
  }

  subscribeServices():void{
    this.layerProvider.currentApp.subscribe(
      (app: AppLayer) => {
          this.radio = app.radius;
          this.googleMaps.cambiarRadioAccion(this.radio)
      }
    );
    this.preferencesProvider.transportsPreferences$.subscribe(
      (transportsPreferences: TransportPreferences[]) => {
        console.log('transportsPreferencesChanged$ ' + JSON.stringify(transportsPreferences));
        this.preferenciasTransportes = transportsPreferences;
      }
    );
}

  mostrarModal(ubicacion) {
    let navParams = {
        ubicacion: ubicacion,
        root: this,
        rootName: 2
      },
      navOptions = {
        animate: false,
        color:'#0060B6'
      };

    this.navCtrl.push(EstablecerUbicacionPage, navParams, navOptions);
  }

  cbFinalizarUbicacion() {
    return () => {};
  }

  validarUbicacion(ubicacion) {
    if (ubicacion.descripcion) {
      return ubicacion.descripcion.length != 0;
    }
    return false;
  }

  clickNuevaUbicacionFavorita(event) {
    console.log(event);
    this.ubicacionFavoritaModal = true;
    this.componenteFavorito = false;
    this.mostrarRutas = false;
  }

  clickUbicacionSeleccionada(event) {
    this.utilidades.presentLoading();
    let res = this.radio / 1000;
    let modosTransportes = this.utilidades.obtenerModosTransportesActivos(this.preferenciasTransportes);

    this.rutasCercanasRequest = new RutasCercanasRequest(
      new Date().toISOString().substring(0, 10),
      this.ubicacion.latitud,
      this.ubicacion.longitud,
      modosTransportes,
      res
    );

    this.onObtenerRutasCercanas();
  }

  clickSeleccionarEnMapa(event) {
    this.seleccionarMapaModal = true;
    this.componenteFavorito = false;
    this.mostrarRutas = false;
    this.crearMarkerFlotante();
  }

  crearMarkerFlotante() {
    var DomElementMarker = document.createElement("div");
    DomElementMarker.id = "domElementMarker";
    DomElementMarker.className = "Centrarpin";

  }
  clickAceptarUbicacion(event) {
    this.mostrarRutas = true;
    this.componenteFavorito = false;
    this.seleccionarMapaModal = false;
  }

  clickCancelarUbicacion(event) {
    this.mostrarRutas = true;
    this.componenteFavorito = false;
    this.seleccionarMapaModal = false;
    this.mostrarRutas = false;
  }

  clickGuardarUbicacionFavorita(event) {
    this.componenteFavorito = true;
    this.ubicacionFavoritaModal = false;
    this.mostrarRutas = false;
  }

  onObtenerRutasCercanas() {
    this.wsMovilidad.obtenerRutasCercanas(this.rutasCercanasRequest).subscribe(
      succces => {
        let data = succces as RutasCercanasReponse;
        this.wsMovilidad.responseRutasCercanas = data;
        this.extraeRespuestaRutasCercanas(data);
      },
      error => {
        this.utilidades.dismissLoading();
        if (error.status == 404) {
          this.utilidades
            .basicAlert(
              "Movilidad",
              "No existen rutas cercanas en la ubicacion y preferencias de transportes especificada"
              //error.message
            )
            .then(data => {});
        } else {
          this.utilidades
            .confirmAlert(
              "Movilidad",
              "Ocurrió un inconveniente inténtelo nuevamente"
            )
            .then(data => {
              this.utilidades.presentLoading();
              this.onObtenerRutasCercanas();
            })
            .catch(data => {});
        }
      }
    );
  }

  private extraeRespuestaRutasCercanas(data: RutasCercanasReponse) {
    if (
      data.estaciones.length > 0 ||
      data.paraderos.length > 0 ||
      data.ciclovias.length > 0
    ) {
      let navParams = {
          ubicacion: this.ubicacion,
          rutasCercanasReponse: data,
          root: this
        },
        navOptions = {
          animate: false
        };
      console.log("MenuRutasGo", data);
      this.navCtrl.push(MenuRutasPage, navParams, navOptions);
    } else {
      this.utilidades
        .basicAlert(
          "Movilidad",
          "No existen rutas cercanas en la ubicacion y preferencias de transportes especificada"
        )
        .then(data => {});
    }
  }
}
