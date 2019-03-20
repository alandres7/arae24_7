import { Component, Input, OnChanges, OnInit, DoCheck, ViewChild, AfterViewInit } from '@angular/core';
import { NavController, NavParams, ModalController, PopoverController, LoadingController, Nav } from 'ionic-angular';

import { EstablecerUbicacionPage } from '../../pages/establecer-ubicacion/establecer-ubicacion.page';
import { RutasPopoverComponent } from '../rutas-popover/rutas-popover.component';
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { PUNTOS_DETALLES_RUTAS, PUNTOS_RUTAS_LINEAS, PUNTOS_RUTAS } from "../../mocks"
import { PuntosProvider } from '../../providers/PuntosProvider/puntos-provider';
import { Ubicacion } from '../../models/ubicacion.model';

import { LineasYRutasComponent } from '../lineas-y-rutas/lineas-y-rutas.component';
import { ViajesSugeridosComponent } from "../viajes-sugeridos/viajes-sugeridos.component";
import { RutasCercanasComponent } from "../rutas-cercanas/rutas-cercanas.component";

declare var google;

@Component({
  selector: 'busqueda-viajes',
  templateUrl: 'busqueda-viajes.component.html',
})
export class BusquedaViajesComponent{
  @Input() modo: string;
  origen?: any;
  destino?: any;

  rutasCercanas: boolean;
  rutasLineas: boolean; 
  cardFullViajesSugeridos: boolean;
  cardMinViajesSugeridos: boolean;

  infowindow: any;
  modoPlaceho: any;
  rutas: Array<{}>;
  cbClickAceptarSeleccionPosicion?: any;
  cbClickCancelarSeleccionPosicion?: any; 
  pageUbicacion: any;

  root: any;

  constructor(
    public navCtrl: NavController,
    public popoverCtrl: PopoverController,
    public navParams: NavParams,
    public loadingCtrl: LoadingController,
    public puntosProvider: PuntosProvider,
    public googleMaps: GoogleMaps
  ) {
    let self = this;
    this.rutas = PUNTOS_DETALLES_RUTAS;

    /*this.cargando = this.loadingCtrl.create({
      content: 'Cargando Rutas desde sistemas legados...'
    });*/

    this.origen = new Ubicacion();
    this.destino = new Ubicacion();
    this.origen.cbDescripcionChange = this.cbFinalizarUbicacion();
    this.destino.cbDescripcionChange = this.cbFinalizarUbicacion();

    this.rutasCercanas = false;
    this.rutasLineas = false;
    this.cardFullViajesSugeridos = false;
    this.cardMinViajesSugeridos = false;
  }

  ngDoCheck() {
    this.actualizarVista();
  }

  cbFinalizarUbicacion() {
    return () => {
      console.log("BusquedaViajesComponent:cbFinalizarUbicacion", this);

      if (this.validarUbicacion(this.origen)) {
        let comp = (<string>this.modo) + "";
        switch (comp) {
          case "2": {
            console.log("caso 2")
            this.pintarMarkerRutas(PUNTOS_RUTAS);
            this.rutasCercanas = true;
            break;
          }
          case "3": {
            console.log("caso 3")
            this.pintarMarkerRutas(PUNTOS_RUTAS_LINEAS);
            break;
          }
          default: {
            break;
          }
        }
      }
    };
  };

  actualizarVista() {
    let origenValido = this.validarUbicacion(this.origen),
      destinoValido = this.validarUbicacion(this.destino);
    this.cardFullViajesSugeridos = origenValido && destinoValido;
    if (!this.cardFullViajesSugeridos) {
      this.cardMinViajesSugeridos = false;
    }

    if (this.modo == '2' && !origenValido) {
      this.rutasCercanas = false;
      this.borrarMarkerRutas(PUNTOS_RUTAS);
    }

    if (this.modo == '3' && !origenValido) {

      // this.rutasLineas = false;
      // this.borrarMarkerRutas(PUNTOS_RUTAS_LINEAS);
    }
  }

  mostrarRutasPopoverPage(event: Event, tipo: number) {
    let self = this;
    this.popoverCtrl
      .create(RutasPopoverComponent, {
        rutas: self.rutas,
        tipo: tipo
      })
      .present({
        ev: event
      });
  }

  limpiarInputs() {
    this.origen.descripcion = null;
    this.destino.descripcion = null;
  }

  ngOnChanges(changes: any): void {
    let self = this;
    this.rutasLineas = false;
    this.rutasCercanas = false;
    this.limpiarInputs();
    this.limpiarMapa();

    if (changes.modo.currentValue == '1') {
    }

    if (changes.modo.currentValue == '2') {
      this.origen.txtPlaceholder = 'Rutas cercanas';
      this.root = RutasCercanasComponent;

    }

    if (changes.modo.currentValue == '3') {
    }
  }

  toogleViajesSugeridos() {
    console.log("BusquedaViajesComponent:toogleViajesSugeridos", this);
    this.cardMinViajesSugeridos = !this.cardMinViajesSugeridos;
  }

  respuestaModalDetalleViaje(data: any) {
    this.toogleViajesSugeridos();
    this.pintarRuta();
    //  this.googleMaps.mapa.setZoom(12);
  }

  pintarRuta() {
    var flightPlanCoordinatesCaminando = [
      { lat: 6.271901, lng: -75.558352 },
      { lat: 6.273005, lng: -75.564693 }
    ];
    var flightPlanCoordinatesEncicla = [
      { lat: 6.273005, lng: -75.564693 },
      { lat: 6.276146, lng: -75.570149 }
    ];
    var flightPlanCoordinatesMetro = [
      { lat: 6.276146, lng: -75.570149 },
      { lat: 6.284709, lng: -75.568722 },
      { lat: 6.292217, lng: -75.564087 }
    ];
    var flightPlanCoordinatesTranvia = [
      { lat: 6.292217, lng: -75.564087 },
      { lat: 6.299904, lng: -75.559161 },
      { lat: 6.306689, lng: -75.558172 }
    ];
    var flightPlanCoordinatesCarro = [
      { lat: 6.306689, lng: -75.558172 },
      { lat: 6.311064, lng: -75.566084 }
    ];
  }

  validarUbicacion(ubicacion) {
    if (ubicacion.descripcion) {
      return ubicacion.descripcion.length != 0;
    }
    return false;
  }

  pintarMarkerRutas(listaPuntos: any[]) {
    console.log("BusquedaViajesComponent:pintarMarkerRutas")
    for (var i = 0; i < listaPuntos.length; i++) {
      if (!listaPuntos[i].marker) {
        google.maps.event.addListener(listaPuntos[i].marker, 'click', ((marker, me) => {
          return () => {
          }
        })(listaPuntos[i].marker, this));
      } else {
      }
    }
  }

  borrarMarkerRutas(listaPuntos: any) {
    for (var i = 0; i < listaPuntos.length; i++) {
      if (listaPuntos[i].marker) {
        listaPuntos[i].marker.setMap(null);
      }
    }
  }

  limpiarMapa() {
    console.log("BusquedaViajesComponent:limpiarMapa");
    this.borrarMarkerRutas(PUNTOS_RUTAS);
    this.borrarMarkerRutas(PUNTOS_RUTAS_LINEAS);
  }

  /*
    mostrarCargando() {
      let self = this;
      this.cargando.present();
      setTimeout(() => {
        self.ocultarCargando();
      }, 5000);
    }

    ocultarCargando() {
      this.cargando.dismiss();
    }*/

    /*getApp(appId: number): AppLayer {
      return this.apps.find((app: AppLayer): boolean => appId == app.id);
    }*/
}
