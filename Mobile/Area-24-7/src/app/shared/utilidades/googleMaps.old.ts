import { MapStyle } from './mapStyle';
import { Injectable } from '@angular/core';


declare var google;

@Injectable()
export class GoogleMaps {

  mapa: any;
  miLocacion: any;
  infowindow: any;
  latLng: Number;
  config: any;
  lat;
  lng;
  zoom: Number = 17;


  constructor() {
    this.infowindow = new google.maps.InfoWindow();
  }

  initMap(lat, lng, element) {
    try {
      this.latLng = new google.maps.LatLng(lat, lng);
      if (this.mapa) {
        this.mapa.setPosition(this.latLng);
        /*let mapNode = this.mapa.getDiv();
         elemento.append(mapNode);
         this.mapa.setOptions(this.config);
         google.maps.event.trigger(this.mapa, "resize");*/
      } else {
        this.config = {
          center: this.latLng,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          zoom: this.zoom,
          disableDefaultUI: true,
          styles: MapStyle.estiloMapa
        };
        this.mapa = new google.maps.Map(element, this.config);
        this.miPunto(null, lat, lng, null);

      }
    } catch (error) {

    }

  }

  pintarMarkers(listaMarkers: any, icono: string, contenidoInfo: string) {
    var mLng: Number;
    var mLat: Number;
    var marker: any;
    var listaProcesados = [];

    for (var i = 0; i < listaMarkers.length; i++) {
      mLat = listaMarkers[i].lat;
      mLng = listaMarkers[i].lng;

      marker = new google.maps.Marker({
        position: new google.maps.LatLng(mLat, mLng),
        map: this.mapa,
        icon: icono
        //title: 'Click to zoom'
      });

      listaProcesados.push(marker);
      marker.addListener('click', function () {
        this.mapa.setZoom(8);
        this.mapa.setCenter(marker.getPosition());
        if (contenidoInfo.length > 0) {
          this.crearInfoWindow(marker, contenidoInfo);
        }

      });


    }
    ;

    return listaProcesados;
  }

  crearInfoWindow(marker: any, contenido: string) {


    this.infowindow.setContent(contenido);
    //this.infowindow.open(this.mapa, this.miLocacion);


  }

  quitarMarker(listaMarkers: any) {
    for (var i = 0; i < listaMarkers.length; i++) {
      listaMarkers[i].setMap = null;
    }
  }

  /*limpiarMapa(mapa: any) {
   //asignar nuevo mapa global de la aplicación
   mapa = getMapa;
   }*/


  miPunto(marker, lat, lng, icono) {
    if (this.miLocacion) {
      this.miLocacion.setPosition(new google.maps.LatLng(lat, lng));
    }
    else {
      this.miLocacion = new google.maps.Marker({
        position: new google.maps.LatLng(lat, lng),
        map: this.mapa //,
        //icon: icono
        //title: 'Click to zoom'
      });
      return this.miLocacion;
    }
  }

  volverAPunto(lat, lng) {
    this.mapa.setCenter(new google.maps.LatLng(lat, lng));
    this.mapa.setZoom(this.zoom);
  }

  cambiarZoom(lat, lng) {
    this.mapa.setCenter(new google.maps.LatLng(lat, lng));
    this.mapa.setZoom(this.zoom);
  }



  pintarMarker(icono: string, contenidoInfo: string, mLat: number, mLng: number) {

    var marker: any;
    marker = new google.maps.Marker({
      position: new google.maps.LatLng(mLat, mLng),
      map: this.mapa,
      icon: icono
    });


    return marker;
  }

  // prueba kmls muestra aplicaciones
  ctaLayerDivision;
  ctaLayerEntorno;
  ctaLayerVigias;
  ctaLayerAvistamientos;
  ctaLayerOrdenamiento;

  kmlPrincipal() {
    this.ctaLayerDivision = new google.maps.KmlLayer({
      url: 'https://bitbucket.org/luisyepes/kmls/raw/5563efa9d9dc5b8a642848f203817b18f9b881b5/Meteorologico2.kml',
      map: this.mapa
    });
  }

  kmlEntorno() {
    this.ctaLayerEntorno = new google.maps.KmlLayer({
      url: 'https://bitbucket.org/luisyepes/kmls/raw/7561beb2ffddbdd73a24c587507cbc2120f22927/puntosAire2.kml',
      map: this.mapa
    });
  }

  kmlAvistamientos() {
    this.ctaLayerAvistamientos = new google.maps.KmlLayer({
      url: 'https://bitbucket.org/luisyepes/kmls/raw/049ed60ca554ccf04dec494191526cccd4b72a5e/avistamiento.kml',
      map: this.mapa
    });
  }

  kmlOrdenamiento() {
    this.ctaLayerOrdenamiento = new google.maps.KmlLayer({
      url: 'https://bitbucket.org/luisyepes/kmls/raw/8638a125a992e9925cb5ad455963ef94e7482885/odenamiento.kml',
      map: this.mapa
    });
  }

  kmlVigias() {
    this.ctaLayerVigias = new google.maps.KmlLayer({
      url: 'https://bitbucket.org/luisyepes/kmls/raw/9ebb6a17e627414a2d0023f7ff73111f388e5909/vigias.kml',
      map: this.mapa
    });
  }

  quitarPrincipal() {
    if (this.ctaLayerDivision !== null) {
      this.ctaLayerDivision.setMap(null);
      this.ctaLayerDivision = null;
    }
    else {
      this.kmlPrincipal();
    }

    //this.ctaLayer.removeLayerFromMap();
  }

  quitarKmlEntorno() {
    if (this.ctaLayerEntorno) {
      this.ctaLayerEntorno.setMap(null);
      this.ctaLayerEntorno = null;
    }
    else {
      this.kmlEntorno();
    }

  }

  quitarKmlAvistamientos() {
    if (this.ctaLayerAvistamientos) {
      this.ctaLayerAvistamientos.setMap(null);
      this.ctaLayerAvistamientos = null;
    }
    else {
      this.kmlAvistamientos();
    }

  }

  quitarKmlOrdenamiento() {
    if (this.ctaLayerOrdenamiento) {
      this.ctaLayerOrdenamiento.setMap(null);
      this.ctaLayerOrdenamiento = null;
    }
    else {
      this.kmlOrdenamiento();
    }

  }

  quitarKmlVigias() {
    if (this.ctaLayerVigias) {
      this.ctaLayerVigias.setMap(null);
      this.ctaLayerVigias = null;
    }
    else {
      this.kmlVigias();
    }

  }

  renderPoint(result, polyline) {
    let flightPath;
    var Caminando = {
      path: 'M 0,-1 0,1',
      strokeOpacity: 1,
      scale: 4
    };
    var Carro = {
      path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
    };

    switch (polyline) {
      case "Caminando": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          icons: [{
            icon: Caminando,
            offset: '0',
            repeat: '20px'
          }],
          strokeOpacity: 0,
          strokeColor: '#1636ff',
          strokeWeight: 2
        });
        break;
      }
      case "Carro": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          icons: [{
            icon: Carro,
            offset: '0',
            repeat: '20px'
          }]
        });
        break;
      }
      case "Metro": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeOpacity: 1,
          strokeColor: '#ff5e00',
          strokeWeight: 2
        });
        break;
      }
      case "Tranvia": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeOpacity: 1,
          strokeColor: '#00ff2a',
          strokeWeight: 2
        });
        break;
      }
      case "Encicla": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeOpacity: 1,
          strokeColor: '#ff00ee',
          strokeWeight: 2
        });
        break;
      }
    }
    flightPath.setMap(this.mapa);
  }


}
