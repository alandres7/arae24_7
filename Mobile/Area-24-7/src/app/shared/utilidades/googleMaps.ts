import { CONFIG_ENV } from './../config-env-service/const-env';
import { WebService } from './../../provider/webService/ws';
import { Common } from './common';
import { Events } from 'ionic-angular';
import { Subject } from 'rxjs/Subject';
import { Injectable } from '@angular/core';
import { MapaComponent } from '../../core/mapa/mapa';

declare var google;

@Injectable()
export class GoogleMaps 
{
    private markersToAdd = new Subject<google.maps.Marker[]>();
    private markersToRemove = new Subject<google.maps.Marker[]>();
    private polylinesToAdd = new Subject<google.maps.Polyline[]>();
    private polylinesToRemove = new Subject<google.maps.Polyline[]>();
    private polygonsToAdd = new Subject<google.maps.Polygon[]>();
    private polygonsToRemove = new Subject<google.maps.Polygon[]>();

    markersToAdd$ = this.markersToAdd.asObservable();
    markersToRemove$ = this.markersToRemove.asObservable();
    polylinesToAdd$ = this.polylinesToAdd.asObservable();
    polylinesToRemove$ = this.polygonsToRemove.asObservable();
    polygonsToAdd$ = this.polygonsToAdd.asObservable();
    polygonsToRemove$ = this.polygonsToRemove.asObservable();

  private adjustMap = new Subject<any>();
  private focusOnPosition = new Subject<any>();

  private kmlsToAdd = new Subject<any>();
  private kmlsToRemove = new Subject<any>();

  private pintarMarkersSub = new Subject<any>();
  private quitarMarkersSub = new Subject<any>();
  private miPuntoSub = new Subject<any>();
  private volverAPuntoSub = new Subject<any>();
  private cambiarZoomSub = new Subject<any>();
  private pintarMarkerSub = new Subject<any>();
  private renderPointSub = new Subject<any>();
  private agregarKmlSub = new Subject<any>();
  private quitarKmlSub = new Subject<any>();
  private crearInfoWindowSub = new Subject<any>();
  private agregarListaPoligonoSub = new Subject<any>();
  private quitarListaPoligonoSub = new Subject<any>();
  private initMapSub = new Subject<any>();
  private rmMapSub = new Subject<any>();
  private cambiarRadioAccionSub = new Subject<any>();
  public ListaMarcadores = {};
  public puntosPoligonos = [];
  public posicionLat:any;
  public posicionLon:any;

    private zoomChange = new Subject<any>();
    private locationChange = new Subject<any>();
    private actionRadiusChange = new Subject<any>();
    private geometrySelectedChange = new Subject<any>();
    private typeaheadLocationChange = new Subject<any>();
    private unsubscribeFromLocationChanges = new Subject<any>();
    private preferencesChange = new Subject<any>();
    
    zoomChanged$ = this.zoomChange.asObservable();
    locationChanged$ = this.locationChange.asObservable();
    actionRadiusChanged$ = this.actionRadiusChange.asObservable();
    geometrySelectionChanged$ = this.geometrySelectedChange.asObservable();
    typeaheadLocationChanged$ = this.typeaheadLocationChange.asObservable();
    unsubscribeFromLocationChanges$ = this.unsubscribeFromLocationChanges.asObservable();
    preferencesChanged$ = this.preferencesChange.asObservable();

    emitZoomChange(zoom: number) { this.zoomChange.next(zoom); }
    
    emitLocationChange(latLng: { lat: number, lng: number }) { this.locationChange.next(latLng); }

    emitActionRadiusChange(actionRadius: number) { this.actionRadiusChange.next(actionRadius); }
    
    emitGeometrySelectionChange(id: number) { this.geometrySelectedChange.next(id); }
    
    emitUnsubscribeFromLocationChanges() { this.unsubscribeFromLocationChanges.next(); }
    
    emitTypeaheadLocationChange(latLng: { lat: number, lng: number }) { this.typeaheadLocationChange.next(latLng); }

    emitPreferencesChange() { this.preferencesChange.next(); }
    
  constructor(private events: Events,
    private utilidad: Common,
    private ws: WebService) {
    //this.infowindow = new google.maps.InfoWindow();
    this.events.subscribe('changeGeolocation', (latitud: Number, longitud: Number) => {
      if (this.puntosPoligonos.length > 0) {
        this.puntosPoligonos.forEach(item => {
          //item.lat
          var resultado = google.maps.geometry.poly.containsLocation({ lat: latitud, lng: longitud }, item.poligono) ? true : false;
          if (resultado) {
            if (item.desplegado) {
              item.desplegado = false;
            }
          }
          else if (!resultado && !item.desplegado) {
            if (!item.desplegado) {
              item.desplegado = true;
              utilidad.generalAlert(item.titulo, 'Haz salido de ' + item.nombre);
            }
          }


        });
      }
    });
  }

  pintarMarkers$ = this.pintarMarkersSub.asObservable();
  quitarMarkers$ = this.quitarMarkersSub.asObservable();
  miPuntoSub$ = this.miPuntoSub.asObservable();
  volverAPunto$ = this.volverAPuntoSub.asObservable();
  cambiarZoom$ = this.cambiarZoomSub.asObservable();
  pintarMarker$ = this.pintarMarkerSub.asObservable();
  renderPoint$ = this.renderPointSub.asObservable();

  
  adjustMap$ = this.adjustMap.asObservable();
  focusOnPosition$ = this.focusOnPosition.asObservable();

  kmlsToAdd$ = this.kmlsToAdd.asObservable();
  kmlsToRemove$ = this.kmlsToRemove.asObservable();
  agregarKml$ = this.agregarKmlSub.asObservable();
  quitarKml$ = this.quitarKmlSub.asObservable();
  crearInfoWindow$ = this.crearInfoWindowSub.asObservable();
  agregarListaPoligono$ = this.agregarListaPoligonoSub.asObservable();
  quitarListaPoligono$ = this.quitarListaPoligonoSub.asObservable();
  initMap$ = this.initMapSub.asObservable();
  cambiarRadioAccion$ = this.cambiarRadioAccionSub.asObservable();
  removerMapa$ = this.rmMapSub.asObservable();

  updateMarkersToAdd(markersToAdd: any): void {
    this.markersToAdd.next(markersToAdd);
  }

  updateMarkersToRemove(markersToRemove: any): void {
    this.markersToRemove.next(markersToRemove);
  }

  updateKmlsToAdd(kmlsToAdd: any): void {
    this.kmlsToAdd.next(kmlsToAdd);
  }

  updateKmlsToRemove(kmlsToRemove: any): void {
    this.kmlsToRemove.next(kmlsToRemove);
  }

  updateAdjustMap(): void {
    this.adjustMap.next({});
  }


  public pintarMarkers(objeto: any): any {
    this.pintarMarkersSub.next(objeto);
  }

  public quitarMarkers(markers: any) {
    this.quitarMarkersSub.next(markers);
  }

  public miPunto(objeto: { lat: any, lng: any, icono: string, animation: google.maps.Animation }): any {
    return this.miPuntoSub.next(objeto);
  }

  public volverAPunto(objeto: { lat: Number, long: Number }) {
    this.volverAPuntoSub.next(objeto);
  }

  public pintarMarker(objetoMarker: any) {
    this.pintarMarkerSub.next(objetoMarker);
  }

  public cambiarZoom(objLatLong: { lat: Number, long: Number, zoom: Number }) {
    this.cambiarZoomSub.next(objLatLong);
  }

  public renderPoint(objeto: { result: any, polyline: any }) {
    this.renderPointSub.next(objeto);
  }

  public agregarListaPoligono(objeto: any) {
    this.agregarListaPoligonoSub.next(objeto);
  }

  public quitarListaPoligono(objeto: any) {
    this.quitarListaPoligonoSub.next(objeto);
  }

  public initMap(objeto: any) {
    this.initMapSub.next(objeto);
  }

  public cambiarRadioAccion(km: Number) {
    this.cambiarRadioAccionSub.next(km);
  }

  public removerMapa() {
    this.rmMapSub.next();
  }
  // prueba kmls muestra aplicaciones
  ctaLayerDivision;
  ctaLayerEntorno;
  ctaLayerVigias;
  ctaLayerAvistamientos;
  ctaLayerOrdenamiento;

  /*
  kmlPrincipal() {
    this.ctaLayerDivision = this.agregarKmlSub.next('https://bitbucket.org/luisyepes/kmls/raw/5563efa9d9dc5b8a642848f203817b18f9b881b5/Meteorologico2.kml');
  }

  kmlEntorno() {
    this.ctaLayerEntorno = this.agregarKmlSub.next('https://bitbucket.org/luisyepes/kmls/raw/7561beb2ffddbdd73a24c587507cbc2120f22927/puntosAire2.kml');
  }

  kmlAvistamientos() {
    this.ctaLayerAvistamientos = this.agregarKmlSub.next('https://bitbucket.org/luisyepes/kmls/raw/049ed60ca554ccf04dec494191526cccd4b72a5e/avistamiento.kml');
  }

  kmlOrdenamiento() {
    this.ctaLayerOrdenamiento = this.agregarKmlSub.next('https://bitbucket.org/luisyepes/kmls/raw/8638a125a992e9925cb5ad455963ef94e7482885/odenamiento.kml');
  }

  kmlVigias() {
    this.ctaLayerVigias = this.agregarKmlSub.next('https://bitbucket.org/luisyepes/kmls/raw/9ebb6a17e627414a2d0023f7ff73111f388e5909/vigias.kml');
  }
*/
/*
  quitarPrincipal() {
    if (this.ctaLayerDivision !== null) {
      this.quitarKmlSub.next(this.ctaLayerDivision);
      this.ctaLayerDivision = null;
    }
    else {
      this.kmlPrincipal();
    }

  }

  quitarKmlEntorno() {
    if (this.ctaLayerEntorno) {
      this.quitarKmlSub.next(this.ctaLayerEntorno);
      this.ctaLayerEntorno = null;
    }
    else {
      this.kmlEntorno();
    }

  }

  quitarKmlAvistamientos() {
    if (this.ctaLayerAvistamientos) {
      this.quitarKmlSub.next(this.ctaLayerAvistamientos);
      this.ctaLayerAvistamientos = null;
    }
    else {
      this.kmlAvistamientos();
    }

  }

  quitarKmlOrdenamiento() {
    if (this.ctaLayerOrdenamiento) {
      this.quitarKmlSub.next(this.ctaLayerOrdenamiento);
      this.ctaLayerOrdenamiento = null;
    }
    else {
      this.kmlOrdenamiento();
    }

  }

  quitarKmlVigias() {
    if (this.ctaLayerVigias) {
      this.quitarKmlSub.next(this.ctaLayerVigias);
      this.ctaLayerVigias = null;
    }
    else {
      this.kmlVigias();
    }

  }
*/
  crearInfoWindow(objeto: { marker: any, contenido: string }) {
    this.crearInfoWindowSub.next(objeto);
  }

}
