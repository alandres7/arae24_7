import { DetalleRutasCercanasComponent } from './../components/detalle-rutas-cercanas/detalle-rutas-cercanas.component';
import { Common } from './../../shared/utilidades/common';
import { MapaComponent } from "../../core/mapa/mapa";
import { UtilsMovilidad } from "./utilsMovilidad";
import { ModoEstacion } from "./constantesMovilidad";
import { Ubicacion } from "../models/ubicacion.model";
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { InicioPage } from '../../inicio/pages/inicio-page/inicio-page';
import { trigger } from '@angular/core';

declare var google;

export class GmapsMovilidad {
  static infoWindow: any;
  static contentInfoWindow: string;
  static rutasLineas: any = [];
  static markersPolylines: any = [];
  static markersPolylinesViajes: any = [];
  static polylines: any = [];

  static locationMarker: google.maps.Marker;
  static actionRadiusCircle: google.maps.Circle;
  static radio:number;
  static common:Common;

  private static markerClick=  new Subject<any>();
  static markerClick$ :Observable<any> = GmapsMovilidad.markerClick.asObservable();



  static emitMarkerClick(data:any){
    this.markerClick.next(data);
  }

  public static getMapa() {
    return MapaComponent.mapa;
  }

  public static centrarMapa(latitud: number, longitud: number) {
    MapaComponent.mapa.setCenter(new google.maps.LatLng(latitud, longitud));  
    console.log(this.radio)

    if(this.radio <= 500){
      MapaComponent.mapa.setZoom(16)
    }else if(this.radio > 500 && this.radio < 1300){
      MapaComponent.mapa.setZoom(15)
    }else{
      MapaComponent.mapa.setZoom(14)
    }

  }

  public static eliminarMarkersPolylines(markersPolylines: any[]) {
    debugger;
    for (var i = 0; i < markersPolylines.length; i++) {
      markersPolylines[i].setMap(null);
    }
  }


  public static obtenerRadio(): number {
    return this.radio;
  }

  public static setRadio(actionRadius:number):void{
    this.radio = actionRadius;
    if(this.actionRadiusCircle != undefined || this.actionRadiusCircle != null ){
      this.updateGraphicRadius(actionRadius);
    }
  }

  public static guardarRadio(actionRadius: any) {
    var pusuario: any = JSON.parse(localStorage.getItem("usuario"));
    let objPreferencias = JSON.parse(pusuario.preferencias);
    objPreferencias.preferencias.forEach(element => {
      if (element.id === 7) {
        element.radioAccion = actionRadius;

      }
    });
    pusuario.preferencias = JSON.stringify(objPreferencias);
    localStorage.setItem("usuario", JSON.stringify(pusuario));
  }

  public static pintarMarker(dataMarker) {

    var icon;

    if(dataMarker.icono === 'assets/mapa/ubicacion_movi.png'){
      icon = {
        url: dataMarker.icono, // url
        scaledSize: new google.maps.Size(40, 46) // size
      };
    } else {
      icon = {
        url: dataMarker.icono, // url
        scaledSize: new google.maps.Size(32, 32) // size
      };
    }

    let marker = new google.maps.Marker({
      position: new google.maps.LatLng(dataMarker.mLat, dataMarker.mLng),
      map: MapaComponent.mapa,
      icon: icon
    });

    return marker;
  }

  public static crearInfoWindow(contentData: any, marker: any):any {
    
    if (this.infoWindow) {
      this.infoWindow.close();
    }

    this.infoWindow = new google.maps.InfoWindow({
      content: "",
      maxWidth: 350,
      minWidth: 350,
      maxHeigth: 70
    });
    
    this.setContenInfoWindows(contentData);
    this.infoWindow.setContent(this.contentInfoWindow);
    this.infoWindow.open(MapaComponent.mapa, marker);
    this.centrarMapa(marker.getPosition().lat(), marker.getPosition().lng())
    this.infoWindow.addListener('domready', (args: any[]): void => {  
        document.getElementById('infoContainer').addEventListener('click', () => 
          {
            this.infoWindow.close();
            this.emitMarkerClick(contentData)
          });
          
        //   document.getElementById('closeInfoWindow').addEventListener('click', ()=>{
        //     this.infoWindow.close()
        //   })
    })

    return this.infoWindow;
  }

  public static closestByClass(el, clazz) {
    while (el.className != clazz) {
      el = el.parentNode;
      if (!el) {
        return null;
      }
    }

    return el;
  }

  public static centrarInfoWindow(latLng: any) {
    if (this.infoWindow) {
      this.infoWindow.setPosition(latLng);
    }
  }

  private static setContenInfoWindows(contentData: any) {
      let icono_estacion = this.obtenerIconoInfoWindow(contentData);
      let titulo = this.obtenerTituloInfoWindows(contentData);
      let content = this.parseContentInfoWindow(contentData); 

      this.contentInfoWindow = `
      <style>
        .infoContainer{

          display:flex;
          flex-direction:row;
          align-items: center;
          justify-content: center;
          width:100%
          height:50px;
          margin-top: -5px;
        }

        .infoContainer img{
            width:3.5rem;
            height: 3.5rem;
            margin: 0px 5px;
        }

        .infoContainer #content-Container h1{
          font-size:16px;
          margin-top:10px;
        }

        .gm-style div div div div {
          max-width: 80vw !important;
          max-height:80px !important;
          align-items: center;
          justify-content: center;
        }

        #content-Container{
          margin-top: -10px;
          margin-left: 10px;
          padding: 10px 0px;
          overflow:hidden;
        }

        #detail{
          font-size:10px; 
          color: #006fac;
        }

      </style>

        <div class="infoContainer" id="infoContainer">
          <img src="${icono_estacion}">
          <div id="content-Container">
            <h1>${titulo}</h1>
            <p style="margin-top:-8px;">${content} <br> <span  id="detail" >Detalle...</span></p>
            
          </div>
        </div>
      `;
}

  public static codificarDireccion(data: any, modo: string): Promise<any> {
    let geocoder = new google.maps.Geocoder();
    let config: any;

    let defaultBounds = new google.maps.LatLngBounds(
      new google.maps.LatLng(6.075967, -75.633433),
      new google.maps.LatLng(6.450092, -75.323971)
    );

    if (modo == "location") {
      config = { location: data, bounds: defaultBounds, region: "CO" };
    } else {
      config = { address: data, bounds: defaultBounds, region: "CO" };
    }
    return new Promise((resolve, reject) => {
      geocoder.geocode(config, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          let data = {
            latitud: results[0].geometry.location.lat(),
            longitud: results[0].geometry.location.lng(),
            descripcion: results[0].formatted_address
          };

          resolve(data);
        } else {
          console.error("GmapsMovilidad:codificarDireccion-error", status);
          reject(status);
        }
      });
    });
  }

  public static crearCoordendasRuta(cooredenadasResponse: any[]): any {
    let coordenadasRuta = [];
    for (var index = 0; index < cooredenadasResponse.length; index++ , index++) {
      let lat = +cooredenadasResponse[index];
      let lng = +cooredenadasResponse[index + 1];
      coordenadasRuta.push({ lat: lat, lng: lng });
    }
    return coordenadasRuta;
  }

  public static renderPoint(result: any, polyline: any): any {
    let flightPath;
    let caminando = {
      path: "M 0,-1 0,1",
      strokeOpacity: 1,
      scale: 4
    };

    switch (polyline) {
      case "caminar": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          icons: [
            {
              icon: caminando,
              offset: "0",
              repeat: "20px"
            }
          ],
          strokeOpacity: 0,
          strokeColor: "#0060B6",
          strokeWeight: 2
        });
        break;
      }
      case "cicloruta": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#FF9C04",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }

      case "bici-particular": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#FF000F",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }
      case "bus": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#1717D3",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }
      case "bus-integrado": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#3bbe98",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }

      case "bus-alimentador": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#FFFB00",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }
      case "metrocable": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#5d6a9c",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }
      case "tranvia": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#5db8ff",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }
      case "metroplus": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#3b897f",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }

      case "metro": {
        flightPath = new google.maps.Polyline({
          path: result,
          geodesic: true,
          strokeColor: "#00D20D",
          strokeOpacity: 0.5,
          strokeWeight: 10
        });
        break;
      }
    }
    flightPath.setMap(MapaComponent.mapa);
    return flightPath;
  }


  public static obtenerTransporte(polyline: any): any {
    let modoTransporte = {
      color: "",
      imagen: ""
    };
    switch (polyline) {
      case "WALK": {
        modoTransporte.imagen = "assets/movilidad/iconos/caminar.svg";
        modoTransporte.color = "#0060B6";
        break;
      }
      case "CABLE_CAR": {
        //Ciclo ruta
        modoTransporte.imagen = "assets/movilidad/iconos/enCiclaIcon.svg";
        modoTransporte.color = "#FF9C04";
        break;
      }

      case "BICYCLE": {
        modoTransporte.imagen = "assets/movilidad/iconos/enCiclaIcon.svg";
        modoTransporte.color = "#FF000F";
        break;
      }
      case "SUBWAY": {
        //BUSES GTPC
        modoTransporte.imagen = "assets/movilidad/iconos/bus.svg";
        modoTransporte.color = "#1717D3";
        break;
      }
      case "FUNICULAR": {
        //integrado
        modoTransporte.imagen = "assets/movilidad/iconos/integrado.svg";
        modoTransporte.color = "#3bbe98";
        break;
      }

      case "FERRY": {
        //alimentador
        modoTransporte.imagen = "assets/movilidad/iconos/integrado.svg";
        modoTransporte.color = "#FFFB00";
        break;
      }
      case "GONDOLA": {
        modoTransporte.imagen = "assets/movilidad/iconos/metrocable.svg";
        modoTransporte.color = "#5d6a9c";
        break;
      }
      case "TRAM": {
        modoTransporte.imagen = "assets/movilidad/iconos/tranvia.svg";
        modoTransporte.color = "#5db8ff";
        break;
      }
      case "BUS": {
        //Metro PLUS

        modoTransporte.imagen = "assets/movilidad/iconos/metroplus.svg";
        modoTransporte.color = "#3b897f";
        break;
      }

      case "RAIL": {
        modoTransporte.imagen = "assets/movilidad/iconos/metro.svg";
        modoTransporte.color = "#00D20D";
        break;
      }
    }
    return modoTransporte;
  }

  public static obtenerIconoMarker(data: any): string {
    let icono = "";

    if (data.idEstacion || data.idEstacion == null) {
        if(data.idModoEstacion == 2){
          icono = "assets/movilidad/markers/metroMarker.svg";
        }
  
        if(data.idModoEstacion == 6){
          icono = "assets/movilidad/markers/metrocableMarker.svg";
        }
  
        if(data.idModoEstacion == 3){
          icono = "assets/movilidad/markers/mertroplusMarker.svg";
        }

        if(data.idModoEstacion == 0){
          icono = "assets/movilidad/markers/tranviaMarker.svg";
        }

        if(data.idModoEstacion == 5){
          icono = "assets/movilidad/markers/enciclaMarker.svg";
        }
    }

    if (data.codigoParadero) {
      icono = "assets/movilidad/markers/busMarker.svg";
    }

    if (data.idPunto) {
      if (data.tipoPunto == 'R') {
        icono = "assets/movilidad/markers/civicarecargaMarker.svg";
      } else {
        icono = "assets/movilidad/markers/ExpCivicaMarker.svg";
      }
    }

    if(data.nombreModoEstacion == ModoEstacion.ENCICLA){
      icono = "assets/movilidad/markers/enciclaMarker.svg";
    }

    return icono;
  }

  public static obtenerIconoInfoWindow(data:any): string{
      let icono = "";
  
      if (data.idEstacion || data.idEstacion == null) {
          if(data.idModoEstacion == 2){
            icono = "assets/movilidad/iconos/infoWindow/metro.svg";
          }
    
          if(data.idModoEstacion == 6){
            icono = "assets/movilidad/iconos/infoWindow/metrocable.svg";
          }
    
          if(data.idModoEstacion == 3){
            icono = "assets/movilidad/iconos/infoWindow/metroplus.svg";
          }
  
          if(data.idModoEstacion == 0){
            icono = "assets/movilidad/iconos/infoWindow/tranvia.svg";
          }
  
          if(data.idModoEstacion == 5){
            icono = "assets/movilidad/iconos/infoWindow/enCiclaIcon.svg";
          }
      }
  
      if (data.codigoParadero) {
        icono = "assets/movilidad/iconos/infoWindow/bus.svg";
      }
  
      if (data.idPunto) {
        if (data.tipoPunto == 'R') {
          icono = "assets/movilidad/iconos/infoWindow/ExpCivica.svg";
        } else {
          icono = "assets/movilidad/iconos/infoWindow/ExpCivica.svg";
        }
      }
  
      if(data.nombreModoEstacion == ModoEstacion.ENCICLA){
        icono = "assets/movilidad/iconos/infoWindow/enCiclaIcon.svg";
      }
  
      return icono;
  }

  public static obtenerTituloInfoWindows(data: any): string {
    let titulo = "";

    if (data.idEstacion || data.idEstacion == null) {
      if (data.idModoEstacion == 2 && data.nombreModoEstacion == "ENCICLA") {
        titulo = "Estación EnCicla";
      }else{
        titulo = "Estación Metro";
      }

      if (data.idModoEstacion == 1) {
        titulo = "Estación Autobus";
      }

      if (data.idModoEstacion == 0) {
        titulo = "Estación Tranvia";
      }

      if (data.idModoEstacion == 6) {
        titulo = "Estación Metrocable";
      }

      if (data.idModoEstacion == 3) {
        titulo = "Estación Metroplús";
      }
    }

    if (data.codigoParadero) {
      titulo = "Paradero Bus";
    }

    if (data.codigoRuta) {
      titulo = "Ruta Bus";
    }

    if (data.idModoLinea) {
      titulo = "Linea Metro";
    }

    if (data.idCiclovia) {
      titulo = "Cicloruta";
    }

    if (data.idPunto) {

      if (data.tipoPunto == 'R') {
        titulo = "Punto Recarga Cívica";
      } else {
        titulo = "Punto Expedición Cívica";
      }

    }
    return titulo;
  }


  private static parseContentInfoWindow(data){
    let descripcion = ''
    if (data.idEstacion || data.idEstacion == null) {
      if (data.idModoEstacion == 6 ) {
        descripcion = `${data.descripcion} - ${data.descripcion}`
      }

      if(data.idModoEstacion == 2 && data.nombreModoEstacion == 'ENCICLA'){
        descripcion = `${data.nombre}`
      }else if (data.idModoEstacion == 2){
        let desc = data.direccion || data.descripcionLinea;
        descripcion = `${data.descripcion} - ${desc}`
      }


      if(data.idModoEstacion == 3){
        descripcion = `${data.descripcion} - ${data.descripcionLinea}`
      }

      if (data.idModoEstacion == 1) {
        descripcion = "data.descripcion";
      }

      if (data.idModoEstacion == 0) {
        descripcion = `${data.descripcion} - ${data.descripcionLinea} `
      }
    }

    if (data.codigoParadero) {
       descripcion = `${data.descripcion}`
    }

    if (data.codigoRuta) {
      descripcion = `Ruta Bus`
    }

    if (data.idPunto) {
      if (data.tipoPunto == 'R') {
        descripcion = `${data.descripcion}`
      } else {
        descripcion = "Punto Expedición Cívica";
      }

    }
    return descripcion;
  }

  public static mostrarRuta(ruta: any, modoTransporte: string) {
    let coordenadasRuta = GmapsMovilidad.crearCoordendasRuta(ruta.coordenadas);
    ruta.flightPath = this.renderPoint(coordenadasRuta, modoTransporte);

    GmapsMovilidad.markersPolylines.push(ruta.flightPath);
    let bounds = new google.maps.LatLngBounds();

    ruta.flightPath.getPath().forEach(function (latLng) {
      bounds.extend(latLng);
    });

    MapaComponent.mapa.fitBounds(bounds);
    this.agregarInfoRuta(ruta.flightPath, ruta);
  }

  public static ocultarRuta(ruta) {
    if (ruta.flightPath) {
      ruta.flightPath.setMap(null);
    } else {
      if (ruta.setMap) {
        ruta.setMap(null);
      }
    }
  }

  public static ocultarMarkersRuta(ruta) {
    for (let index = 0; index < this.markersPolylines.length; index++) {
      const element = this.markersPolylines[index];
      debugger;
      if (element.idRuta) {
        if (element.idRuta == ruta.idRuta || element.idRuta == ruta.idLinea) {
          element.setMap(null);
        }
      }
    }
  }

  public static ocultarRutas(rutas) {
    for (var index = 0; index < rutas.length; index++) {
      if (rutas[index].flightPath) {
        rutas[index].flightPath.setMap(null);
      }
    }
  }



  public static agregarInfoParadero(marker: any) {
    google.maps.event.addListener(marker, 'click', ((marker, me) => {
      return () => {
        GmapsMovilidad.crearInfoWindow(marker.dataRutaCercana, marker);
      };
    })(marker, this));
  }


  public static agregarInfoRuta(ruta: any, data: any) {
    let latlng;

    ruta.addListener("click", function (polyMouseEvent) {
      latlng = {
        lat: polyMouseEvent.latLng.lat(),
        lng: polyMouseEvent.latLng.lng()
      };

      GmapsMovilidad.crearInfoWindow(data, ruta);

      GmapsMovilidad.centrarInfoWindow(latlng);
      ruta.setOptions({ strokeOpacity: 1.0 });
    });

    ruta.addListener("mouseout", function (polyMouseEvent) {
      ruta.setOptions({ strokeOpacity: 0.5 });
    });
  }

  public static obtenerModoTransporte(objeto: any): string {
    let modoTransporte = "";
    if (objeto.codigoRuta) {
      modoTransporte = "bus";
    }

    if (objeto.idLinea) {
      modoTransporte = "metro";
    }

    if (objeto.idCiclovia) {
      modoTransporte = "cicloruta";
    }

    return modoTransporte;
  }

  public static createUpdatePositionMarker(lat: number, lng: number): void {
    let position: google.maps.LatLng = new google.maps.LatLng(lat, lng);
    let actionRadius = GmapsMovilidad.obtenerRadio();
    if (this.locationMarker) {
        this.locationMarker.setPosition(position);
        this.locationMarker.setMap(MapaComponent.mapa)
        MapaComponent.mapa.setZoom(15)
        if(this.actionRadiusCircle != undefined){
          this.actionRadiusCircle.setMap(MapaComponent.mapa)
          this.actionRadiusCircle.setCenter(position);
        }else{
          this.locationMarker.setMap(null)
          this.locationMarker = null;
          this.locationMarker = new google.maps.Marker({
            map: MapaComponent.mapa,
            position: position,
            icon: {
                scaledSize: new google.maps.Size(50, 50),
                url: 'assets/mapa/ubicacion.svg'
            },
            animation: google.maps.Animation.DROP,
            draggable: true,
            zIndex: google.maps.Marker.MAX_ZINDEX + 1
        });
        MapaComponent.mapa.setZoom(15)

        this.actionRadiusCircle = new google.maps.Circle({
          map: MapaComponent.mapa,
          radius: actionRadius,
          fillColor: '#f9bbbb',
          strokeColor: '#f9bbbb',
          strokeOpacity: 0.3,
          strokeWeight: 0.8,
          fillOpacity: 0.1,
      });

      google.maps.event.addListener(this.locationMarker, 'dragend', function (event:any) {
        MapaComponent.emitDragedPedestrian({'lat':event.latLng.lat(), 'lng':event.latLng.lng()})
      });

        this.actionRadiusCircle.bindTo('center', this.locationMarker, 'position');
        }
    }
    else {
          this.locationMarker = new google.maps.Marker({
              map: MapaComponent.mapa,
              position: position,
              icon: {
                  scaledSize: new google.maps.Size(50, 50),
                  url: 'assets/mapa/ubicacion.svg'
              },
              animation: google.maps.Animation.DROP,
              draggable: true,
              zIndex: google.maps.Marker.MAX_ZINDEX + 1
          });

          this.actionRadiusCircle = new google.maps.Circle({
            map: MapaComponent.mapa,
            radius: actionRadius,
            fillColor: '#f9bbbb',
            strokeColor: '#f9bbbb',
            strokeOpacity: 0.3,
            strokeWeight: 0.8,
            fillOpacity: 0.1,
        });

        google.maps.event.addListener(this.locationMarker, 'dragend', function (event:any) {
          MapaComponent.emitDragedPedestrian({'lat':event.latLng.lat(), 'lng':event.latLng.lng()})
        });

          this.actionRadiusCircle.bindTo('center', this.locationMarker, 'position');
      }
    }

    createDraggedPositionIcon(){
      
    }


  public static createPositionMarker(ubicacion:Ubicacion){
      let position: google.maps.LatLng = new google.maps.LatLng(ubicacion.latitud, ubicacion.longitud);
      if (this.locationMarker) {
          this.locationMarker.setPosition(position);
          this.locationMarker.setMap(MapaComponent.mapa)
          MapaComponent.mapa.setZoom(15)
      }
      else {
            this.locationMarker = new google.maps.Marker({
                map: MapaComponent.mapa,
                position: position,
                icon: {
                    scaledSize: new google.maps.Size(50, 50),
                    url: 'assets/mapa/ubicacion.svg'
                },
                animation: google.maps.Animation.DROP,
                draggable: false,
                zIndex: google.maps.Marker.MAX_ZINDEX + 1
            });

            MapaComponent.mapa.setZoom(15)
        }
  }

  public static deletePositionMarker(){
    if(this.locationMarker){
      this.locationMarker.setMap(null)
      this.locationMarker = null;
    }
  }

  public static updateGraphicRadius(radio:number){
      this.actionRadiusCircle.setRadius(radio)
  }

  public static deleteLocationMarker(){
    if(this.locationMarker){
      this.locationMarker.setMap(null);
      this.actionRadiusCircle.setMap(null);
      this.locationMarker = null;
      this.actionRadiusCircle = null;
    }
  }
}


