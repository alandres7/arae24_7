import {
  Component,
  Input,
  DoCheck,
  OnInit,
  OnChanges,
  SimpleChanges,
  Output,
  EventEmitter
} from "@angular/core";
import {
  NavController,
  NavParams,
  ViewController,
  PopoverController,
  ModalController,
  App
} from "ionic-angular";
import { EstablecerUbicacionPage } from "../../pages/establecer-ubicacion/establecer-ubicacion.page";
import { VistaViajesPage } from "../../pages/vista-viajes/vista-viajes.page";
import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { WsMovilidad } from "../../providers/wsMovilidad";
import { Common } from "../../../shared/utilidades/common";
import { PosiblesViajesProvider } from "../../../shared/posibles-viajes.service";
import { Events } from "ionic-angular";
import { Geolocation } from '@ionic-native/geolocation';

declare var google;

@Component({
  selector: "viajes-sugeridos",
  templateUrl: "viajes-sugeridos.component.html"
})
export class ViajesSugeridosComponent implements OnInit {
  ngOnInit(): void {}

  @Input() modo: string;

  cardFullViajesSugeridos: boolean;
  cardMinViajesSugeridos: boolean;
  componentFavorito: boolean;
  detalleViaje: boolean;
  destino?: Ubicacion;
  modalOrigen: any;
  modalDestino: any;
  origen?: Ubicacion;
  seleccionarMapaModal: boolean;
  ubicacion: Ubicacion;
  ubicacionModal: boolean;
  ubicacionFavoritaModal: boolean;
  origenDestinoFlag: boolean;
  origenDestinoIcono: string;

  constructor(
    public events: Events,
    public common: Common,
    public wsMovilidad: WsMovilidad,
    public navCtrl: NavController,
    public googleMaps: GoogleMaps,
    public posiblesViajesP: PosiblesViajesProvider,
    public navParams: NavParams,
    private geolocation: Geolocation,
    protected app: App
  ) {
    let self = this;
    this.origenDestinoFlag = false;
    this.origenDestinoIcono = "assets/movilidad/iconos/cambiarIcon.png";
    this.componentFavorito = false;
    this.ubicacionFavoritaModal = false;
    this.seleccionarMapaModal = false;
    this.detalleViaje = false;
    this.origen = new Ubicacion();
    this.origen.txtPlaceholder = "Origen";
    this.origen.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
    this.destino = new Ubicacion();
    this.destino.txtPlaceholder = "Destino";
    this.destino.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
    this.origen.cbDescripcionChange = this.cbFinalizarUbicacion();
    this.destino.cbDescripcionChange = this.cbFinalizarUbicacion();
    this.modalOrigen = this.common.createModal(EstablecerUbicacionPage, {
      ubicacion: self.origen
    });

    this.modalDestino = this.common.createModal(EstablecerUbicacionPage, {
      ubicacion: self.destino
    });

    this.cardFullViajesSugeridos = false;
    this.cardMinViajesSugeridos = false;

    this.obtenerUbicacionActual();
    this.obtenerModosTransporte();
  }

  ionViewDidEnter() {
    document.getElementById("over_map_center").style.top = "57px";
  }

  obtenerModosTransporte(){
    let modos = this.common.obtenerModosTransportesActivos([]);
    if(modos.length > 0){
      return
    }else{
      this.common.basicAlert("Movilidad","No se tienen preferencias de transporte seleccionadas")
    }
  }

  mostrarModal(ubicacion) {
    let navParams = {
        ubicacion: ubicacion,
        root: this,
        rootName:1,
      },
      navOptions = {
        animate: false
      };

    this.navCtrl.push(EstablecerUbicacionPage, navParams, navOptions);
  }

  actualizarVista() {
    let origenValido = this.validarUbicacion(this.origen),
      destinoValido = this.validarUbicacion(this.destino);
    this.cardFullViajesSugeridos = origenValido && destinoValido;
    if (this.cardFullViajesSugeridos) {
      this.posiblesViajesP.cambiarPosiciones(this.origen, this.destino);
      this.posiblesViajesP
        .obtenerviajesSugeridos()
        .then(data => {
         this.app.getRootNav().push(VistaViajesPage, data);
        })
        .catch(error => {
            this.common
            .basicAlert(
              "Movilidad",
              error
            );

        });
    }
    if (!this.cardFullViajesSugeridos) {
      this.cardMinViajesSugeridos = false;
    }
  }

  clickUbicacionSeleccionada() {
    this.actualizarVista();
  }

  clickUbicacionSeleccionadaDismiss() {
    this.navCtrl.pop();
    this.actualizarVista();
  }

  cbFinalizarUbicacion() {
    return () => {
      if (this.validarUbicacion(this.origen)) {
        let comp = <string>this.modo + "";
      }
    };
  }

  validarUbicacion(ubicacion) {
    if (ubicacion.descripcion) {
      return ubicacion.descripcion.length != 0;
    }
    return false;
  }

  toogleViajesSugeridos() {
    // console.log("BusquedaViajesComponent:toogleViajesSugeridos", this);
    //this.cardMinViajesSugeridos = !this.cardMinViajesSugeridos;
  }

  // respuestaModalDetalleViaje(data: any) {
  //   this.toogleViajesSugeridos();
  // }

  crearMarkerFlotante() {
    console.log("BuscarUbicacionComponent:crearMarkerFlotante", this);
    //this.googleMaps.crearMarkercenter();
    var DomElementMarker = document.createElement("div");
    DomElementMarker.id = "domElementMarker";
    DomElementMarker.className = "Centrarpin";
    // this.googleMaps.mapa.DOMElement.appendChild(DomElementMarker);
    // this.googleMaps.mapa.DomElementMarker = DomElementMarker;
  }


  clickNuevaUbicacionFavorita(event) {
    console.log(event);
    this.ubicacionFavoritaModal = true;
    this.componentFavorito = false;
  }

  clickSeleccionarEnMapa(event) {
    console.log("seleccionar en mapa");
    this.crearMarkerFlotante();
    this.seleccionarMapaModal = true;
    this.componentFavorito = false;
  }

  clickAceptarUbicacion(event) {
    this.componentFavorito = false;
    this.seleccionarMapaModal = false;
    this.actualizarVista();
  }

  clickGuardarUbicacionFavorita(event) {
    console.log("guardar Ubicacion Favorita");
    this.ubicacionFavoritaModal = false;
    this.componentFavorito = true;
  }

  clickUbicacionFavoritaSeleccionada(event) {
    this.componentFavorito = false;
    this.actualizarVista();
  }
  clickSeleccionarUbicacionMapaFavorita(event) {
    this.crearMarkerFlotante();
    this.ubicacionFavoritaModal = false;
  }

  clickVerEnMapa(event) {
    this.detalleViaje = false;
  }

  cambiarOrigenDestino() {
    if (this.origenDestinoFlag) {
      this.origenDestinoIcono = "assets/movilidad/iconos/cambiarIcon.png";
      this.origenDestinoFlag = false;
    } else {
      this.origenDestinoIcono = "assets/movilidad/iconos/cambiarIcon2.png";
      this.origenDestinoFlag = true;
    }
    let tmp = this.origen;
    this.origen = this.destino;
    this.destino = tmp;

    this.actualizarVista();
  }

  obtenerUbicacionActual() {
    // this.geolocation.getCurrentPosition().then((resp) => {
    //   this.origen.latitud = resp.coords.latitude;
    //   this.origen.longitud = resp.coords.longitude;
      
    // }).catch((error) => {
    //   console.log('Error getting location', error);
    // });
    // this.origen.descripcion = "Mi ubicación";
  }



}
