import { Component, ViewChild, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { NavController, NavParams, ViewController } from 'ionic-angular';

import { FavoritosProvider } from "../../providers/favoritos/favoritos";
import { GoogleMaps } from '../../../shared/utilidades/googleMaps';
import { GmapsMovilidad } from '../../providers/gmapsMovilidad';
import { Common } from '../../../shared/utilidades/common';
import { Events } from 'ionic-angular';

declare var google: any;

@Component({
  selector: 'seleccionar-mapa',
  templateUrl: 'seleccionar-mapa.page.html',
})
export class SeleccionarMapaPage {
  animate: any;

  @ViewChild('contenedor') contenedor: ElementRef;
  @Input() ubicacion?: any;
  @Output() clickAceptarUbicacion?= new EventEmitter();
  @Output() clickCancelarUbicacion?= new EventEmitter();
  location: any;
  radius: any;
  marker: any;
  geocoder: any;
  mapaDOM: any;
  padreDOMMapa: any;
  domElementMarker: any;
  root: any;

  titulo: string = "Seleccionar En El Mapa";

  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public googleMaps: GoogleMaps,
    private utilidades: Common,
    public favoritos: FavoritosProvider,
    public events: Events
  ) {
    this.animate = this.navParams.data.animate;
    this.root = this.navParams.data.root;
    this.ubicacion = this.navParams.data.ubicacion;
    this.geocoder = new google.maps.Geocoder;
    this.mapaDOM = GmapsMovilidad.getMapa().DOMElement;
  }

  ionViewDidLoad() {
    this.crearMarkerFlotante();
  }


  crearMarkerFlotante() {
    this.domElementMarker = document.createElement('div');
    this.domElementMarker.id = 'domElementMarker';
    this.domElementMarker.className = 'centrarpin';
    document.getElementById("map").appendChild(this.domElementMarker);

  }

  quitarMarkerFlotante() {
    // document.getElementById("map").parentNode.removeChild(document.getElementById("domElementMarker"));
    // document.getElementById("domElementMarker").remove();
  }

  geolocate() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        this.radius = 5000;
      },
        (error) => {
          console.log('error');
        }
      );
    } else {
      console.log('unable');
    }
  }

  cancelar() {
    this.quitarMarkerFlotante();
  }

  clickAceptar() {
    this.quitarMarkerFlotante();
    let latlng = { lat: parseFloat(GmapsMovilidad.getMapa().getCenter().lat()), lng: parseFloat(GmapsMovilidad.getMapa().getCenter().lng()) };
    GmapsMovilidad.codificarDireccion(latlng, 'location')
      .then((data) => {
        // debugger;
        if(this.ubicacion){
          this.ubicacion.descripcion = data.descripcion;
          this.ubicacion.latitud = data.latitud;
          this.ubicacion.longitud = data.longitud;
        }
        this.utilidades.dismissModal(data)
      })
      .catch((error) => {
        this.utilidades.dismissLoading();
        this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
      });

  }

  ionViewWillLeave() {
    this.quitarMarkerFlotante();
    this.utilidades.dismissLoading();
  }

}
