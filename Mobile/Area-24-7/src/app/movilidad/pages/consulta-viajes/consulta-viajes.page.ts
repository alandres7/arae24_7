import { Subscription } from 'rxjs/Subscription';
import { Geoposition } from '@ionic-native/geolocation';
import { GmapsMovilidad } from './../../providers/gmapsMovilidad';
import { LocationProvider } from './../../providers/location';
import { VistaViajesPage } from './../vista-viajes/vista-viajes.page';
import { TransportPreferences } from './../../../../entities/preferences/transport-preferences';
import { PreferencesProvider } from './../../../../providers/preferences/preferences';
import { EstablecerUbicacionPage } from './../establecer-ubicacion/establecer-ubicacion.page';
import { App } from 'ionic-angular';
import { PosiblesViajesProvider } from './../../../shared/posibles-viajes.service';
import { WsMovilidad } from './../../providers/wsMovilidad';
import { Ubicacion, MODOS_BUSQUEDA } from './../../models/ubicacion.model';
import { Common } from './../../../shared/utilidades/common';
import { Component, Input, Injectable } from "@angular/core";
import { NavController, NavParams, ViewController, Events, ModalController } from 'ionic-angular';

@Component({
    selector:"consulta-viaje",
    templateUrl:"consulta-viajes.page.html"
})

export class ConsultaViaje {

    cardFullViajesSugeridos: boolean;
    cardMinViajesSugeridos: boolean;
    componentFavorito: boolean;
    detalleViaje: boolean;
    destino: Ubicacion;
    modalOrigen: any;
    modalDestino: any;
    origen: Ubicacion;
    seleccionarMapaModal: boolean;
    ubicacion: Ubicacion;
    ubicacionModal: boolean;
    ubicacionFavoritaModal: boolean;
    origenDestinoFlag: boolean;
    origenDestinoIcono: string;
    preferenciasTransportes:any[];
    titulo = 'Viajes'
    
    locationUpdateSubscription: Subscription
    private firstLocationCenterMap: boolean = false;
    private LOCATION_UPDATES_INTERVAL: number = 5000;
    private DISTANCE_TOLERANCE: number = 5;

    constructor(
        public events: Events,
        public common: Common,
        public wsMovilidad: WsMovilidad,
        public navCtrl: NavController,
        public posiblesViajesP: PosiblesViajesProvider,
        public navParams: NavParams,
        private preferencesProvider : PreferencesProvider, 
        protected app: App,
        private locationProvider:LocationProvider
      ) {
        this.origen = new Ubicacion();
        this.origen.txtPlaceholder = "Origen";
        this.origen.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
        this.destino = new Ubicacion();
        this.destino.txtPlaceholder = "Destino";
        this.destino.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
        this.subscribePreferences();
      }

    subscribePreferences(){
        this.preferencesProvider.transportsPreferences$.subscribe(
        (transportsPreferences: TransportPreferences[]) => {
            this.preferenciasTransportes = transportsPreferences;
        }
    );
    }

    ionViewWillLeave(){
        GmapsMovilidad.deletePositionMarker();
        this.common.dismissLoading();
    }

    mostrarModal(ubicacion) {
        let navParams = {
            ubicacion: ubicacion,
            root: this,
            rootName:1,
    }

        let navOptions = {
            showBackdrop: false,
            enableBackdropDismiss: false
        };
        
        let modal = this.common.createModal(EstablecerUbicacionPage, navParams, navOptions)
        modal.onDidDismiss(data =>{
            this.actualizarVista();
            if(this.origen.descripcion == 'Mi Ubicación'){
                this.obtenerUbicacionActual();
            }else if(this.origen.descripcion != ''){
                GmapsMovilidad.createPositionMarker(this.origen)
                GmapsMovilidad.centrarMapa(this.origen.latitud,this.origen.longitud)
            }
        })
        GmapsMovilidad.deletePositionMarker()
        modal.present()
    }

    actualizarVista() {
        let origenValido = this.validarUbicacion(this.origen);
        let destinoValido = this.validarUbicacion(this.destino);
        this.cardFullViajesSugeridos = origenValido && destinoValido;
        if (this.cardFullViajesSugeridos) {
            this.posiblesViajesP.cambiarPosiciones(this.origen, this.destino);
            this.posiblesViajesP.obtenerviajesSugeridos()
            .then(data => {
                GmapsMovilidad.deletePositionMarker();
                this.app.getRootNav().push(VistaViajesPage, data);
            })
            .catch(error => {
                this.common.basicAlert("Movilidad",error);
            });
        }
        if (!this.cardFullViajesSugeridos) {
          this.cardMinViajesSugeridos = false;
        }
    }

    searchPositionBtn(){
        this.actualizarVista()
    }

    validarUbicacion(ubicacion) {
        if (ubicacion.descripcion) {
            return ubicacion.descripcion.length != 0;
        }
        return false;
    }

    toogleViajesSugeridos() {
    //     this.cardMinViajesSugeridos = !this.cardMinViajesSugeridos;
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
       let myPosition:Geoposition = this.locationProvider.getMyLocation();
       this.origen.latitud = myPosition.coords.latitude;
       this.origen.longitud = myPosition.coords.longitude;
       this.origen.descripcion = 'Mi Ubicación'
       
       if(this.origen != undefined){
           GmapsMovilidad.createPositionMarker(this.origen)
           GmapsMovilidad.centrarMapa(this.origen.latitud,this.origen.longitud)
       }
    }
}