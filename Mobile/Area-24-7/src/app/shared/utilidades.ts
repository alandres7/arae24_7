import { Injectable } from '@angular/core';
import { LoadingController, ToastController } from 'ionic-angular';

declare var google;

@Injectable()
export class UtilidadesApp {

    constructor(private loadingCtrl: LoadingController, private toastCtrl: ToastController) {

    }


    appLoading(mensajeLoading, duration: any) {
        let loading = this.loadingCtrl.create({
            spinner: 'hide',
            content: `
      <div class="custom-spinner-container">
      <ion-spinner name="bubbles"></ion-spinner>
        <!-- <div class="custom-spinner-box"></div>-->
        <h5>`+ mensajeLoading + `</h5>
      </div>`,
            duration: duration || 5000
        });

        loading.onDidDismiss(() => {
            console.log('Dismissed loading');
        });

        loading.present();
    }

    appToast(objetoToast: any) {
        let toast = this.toastCtrl.create({
            message: objetoToast.mensaje,
            duration: objetoToast.duration,
            position: objetoToast.posicion
        });

        toast.onDidDismiss(() => {
            console.log('Dismissed toast');
        });

        toast.present();
    }

    cargarMapa(lat, lng, elemento:any) {
      let mapa: any;
      let latLng;
      let config;
      latLng = new google.maps.LatLng(lat, lng);
      try {
        mapa = localStorage.getItem('mapa');
      } catch (e) {
        
      }
      if (mapa) {
        mapa.setPosition(latLng);
      } else {
        config = {
          center: latLng,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          zoom: 17
        };
        mapa = new google.maps.Map(elemento, config);
        localStorage.setItem("mapa", mapa);
      }
      //document.getElementById('map') 

      return mapa;
    }

  pintarMarkers(listaMarkers: any, icono: string, mapa: any, contenidoInfo: string) {
    var mLng: Number;
    var mLat: Number;
    var marker: any;
    var listaProcesados = [];

    for (var i = 0; i < listaMarkers.length; i++) {
      mLat = listaMarkers[i].lat;
      mLng = listaMarkers[i].lng;

      marker = new google.maps.Marker({
        position: new google.maps.LatLng(mLat, mLng),
        map: mapa,
        icon: icono
        //title: 'Click to zoom'
      });

      listaProcesados.push(marker);
      marker.addListener('click', function () {
        mapa.setZoom(8);
        mapa.setCenter(marker.getPosition());
        if (contenidoInfo.length > 0) {
          this.crearInfoWindow(marker, contenidoInfo);
        }

      });


    };

    return listaProcesados;
  }

  crearInfoWindow(marker: any, contenido: string) {
    var infowindow = new google.maps.InfoWindow({
      content: contenido,
      position: marker.getPosition()
    });

    if (infowindow.isOpen()) {
      infowindow.close();
    }
    else {
      infowindow.open();
    }

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


  miPunto(marker, mapa, lat, lng, icono) {
    if (marker) {
      marker.setPosition(new google.maps.LatLng(lat, lng));
    }
    else {
      marker = new google.maps.Marker({
        position: new google.maps.LatLng(lat, lng),
        map: mapa //,
        //icon: icono
        //title: 'Click to zoom'
      });
      return marker; 
    }
  }



}