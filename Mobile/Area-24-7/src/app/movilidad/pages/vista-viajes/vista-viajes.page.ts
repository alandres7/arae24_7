import { MenuRutasPage } from './../menu-rutas/menu-rutas.page';
import { ConsultaViaje } from './../consulta-viajes/consulta-viajes.page';
import { Component, EventEmitter, Input, Output } from "@angular/core";
import {
  NavController,
  NavParams,
  ViewController,
  AlertController,
  LoadingController
} from "ionic-angular";

import { Geolocation } from '@ionic-native/geolocation';

import { Ubicacion } from "../../models/ubicacion.model";
import { FavoritosProvider } from "../../providers/favoritos/favoritos";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { DetalleViajePage } from "../detalle-viaje/detalle-viaje.page";
import { WsMovilidad } from "../../providers/wsMovilidad";
import { Common } from "../../../shared/utilidades/common";
import { DatePipe, DecimalPipe } from "@angular/common";
import { retry } from "rxjs/operator/retry";
import { GmapsMovilidad } from "../../providers/gmapsMovilidad";

enum Modos {
  NUEVO,
  GUARDAR
}

//declare var google;

@Component({
  selector: "vista-viajes",
  templateUrl: "vista-viajes.page.html"
})
export class VistaViajesPage {
  @Input() ubicacion: Ubicacion;

  ubicacionOrigen: Ubicacion;
  ubicacionDestino: Ubicacion;

  kilometrajeAuto: number;
  tarifaTotalViaje: number;

  gmapsMovildiad = GmapsMovilidad;

  layerActive:number = 1;

  item: any = {
    nombre: "",
    ubicacion: {}
  };

  appSettings:any = {}

  modo: any = Modos.NUEVO;
  alternativaViaje = false;
  root: any;
  response: any;
  itineraries: any[];
  flagShowBestDistance = false;
  flagShowBestTime = false;
  seleccionarMapaModalFavorito: boolean = false;
  modalFavorito: boolean = true;
  sumaDistancia1 = 0;
  sumaDistancia2 = 0;
  titulo: string = "Viajes Sugeridos";

  origen = {
    latitud: 0,
    longitud: 0,
    descripcion: ""
  };

  destino = {
    latitud: 0,
    longitud: 0,
    descripcion: ""
  };

  loader: any;

  mejordistancia = {
    duracion: "",
    distancia: "",
    pasos: []
  };

  mejorTiempo = {
    duracion: "",
    distancia: "",
    pasos: []
  };

  contador1: number = 0;
  contador2: number = 0;

  @Output() clickGuardarUbicacionFavorita?= new EventEmitter();
  @Output() clickSeleccionarUbicacionMapaFavorita?= new EventEmitter();

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public favoritos: FavoritosProvider,
    public googleMaps: GoogleMaps,
    public wsMovilidad: WsMovilidad,
    private common: Common,
    private pipe: DecimalPipe,
    private geolocation: Geolocation
  ) {

    this.ubicacionDestino = new Ubicacion();
    this.ubicacionOrigen = new Ubicacion();

    let lat_origen = this.navParams.get("lat_origen");
    let lon_origen = this.navParams.get("lon_origen");
    let lat_destino = this.navParams.get("lat_destino");
    let lon_destino = this.navParams.get("lon_destino");
    let ori_desc = this.navParams.get("ori_desc");
    let des_desc = this.navParams.get("des_desc");

    this.cargarUbicacionInicioDestino(
      lat_origen,
      lon_origen,
      ori_desc,
      lat_destino,
      lon_destino,
      des_desc
    );

    this.gmapsMovildiad.centrarMapa(this.ubicacionOrigen.latitud, this.ubicacionOrigen.longitud);
    this.cargarDatos(this.navParams.get("data"), this.navParams.get("mensaje"));
    this.appSettings.color = 'blue'
  }

  ionViewDidLeave(){
    this.common.dismissLoading();
  }


  private cargarUbicacionInicioDestino(
    lat_origen: any,
    lon_origen: any,
    ori_desc: any,
    lat_destino: any,
    lon_destino: any,
    des_desc: any
  ) {
    this.origen.latitud = lat_origen;
    this.origen.longitud = lon_origen;
    this.origen.descripcion = ori_desc;
    this.ubicacionOrigen.latitud = lat_origen;
    this.ubicacionOrigen.longitud = lon_origen;
    this.ubicacionOrigen.descripcion = ori_desc;
    this.destino.latitud = lat_destino;
    this.destino.longitud = lon_destino;
    this.destino.descripcion = des_desc;
    this.ubicacionDestino.latitud = lat_destino;
    this.ubicacionDestino.longitud = lon_destino;
    this.ubicacionDestino.descripcion = des_desc;
  }

  cargarDatos(response, mensaje) {
    this.response = response;

    if (mensaje != null) {
      this.alternativaViaje = true;
    }
    this.itineraries = response.plan.itineraries;
    //this.tarifaTotalViaje = response.plan.itineraries[0].rate;
    this.kilometrajeAuto = response.plan.distanceByCar;
    this.llenarPasos();
    this.common.presentLoading();
    setTimeout(() => {
      this.convertirtiempo();
      this.convertirDistancia();
      this.common.dismissLoading();
    }, 1500);
  }



  llenarPasos() {
    this.sumaDistancia1 = 0;
    this.sumaDistancia2 = 0;
    this.contador1 = 0;
    this.contador2 = 0;

    this.mejorTiempo.pasos = [];
    this.mejordistancia.pasos = [];
    if (this.itineraries.length == 2) {
      this.flagShowBestDistance = true;
      this.flagShowBestTime = true;
      for (let paso of this.itineraries[1].legs) {

        let mode = paso.mode;
        let tipoTransporte = paso.tipoTransporte;
        let routeId = paso.routeId;
        let agencyName = paso.agencyName;
        let distance = paso.distance;

        let coordenadas = [];
        let steps = [];

        if (mode != "WALK") {
          coordenadas = paso.coordenadas;
        } else {
          steps = paso.steps;
        }

        let from = {
          name: paso.from.name,
          lat: paso.from.lat,
          lon: paso.from.lon,
          cantidadBicicletasDisponibles: paso.from.cantidadBicicletasDisponibles,
          cantidadPuestosDisponibles: paso.from.cantidadPuestosDisponibles
        };

        let to = {
          name: paso.to.name,
          lat: paso.to.lat,
          lon: paso.to.lon,
          cantidadBicicletasDisponibles: paso.to.cantidadBicicletasDisponibles,
          cantidadPuestosDisponibles: paso.to.cantidadPuestosDisponibles
        };

        if(mode == "BUS" && paso.routeId != "L1" && paso.routeId != "L2"){
          mode = "FUNICULAR"
        }

        let add_paso = {
          mode: mode,
          distance: this.ajustarDistancia(distance),
          tipoTransporte: tipoTransporte,
          routeId: routeId,
          agencyName: agencyName,
          from: from,
          to: to,
          coordenadas: coordenadas,
          steps: steps,
          mensaje: this.ajustarDetallePaso(paso, this.itineraries[1].legs, this.contador1)
        };

        this.contador1++;
        this.mejorTiempo.pasos.push(add_paso);
        this.sumaDistancia2 += paso.distance;
      }
      for (let paso of this.itineraries[0].legs) {

        let mode = paso.mode;
        let tipoTransporte = paso.tipoTransporte;
        let routeId = paso.routeId;
        let agencyName = paso.agencyName;
        let distance = paso.distance;

        let coordenadas = [];
        let steps = [];

        if (mode != "WALK") {
          coordenadas = paso.coordenadas;
        } else {
          steps = paso.steps;
        }

        let from = {
          name: paso.from.name,
          lat: paso.from.lat,
          lon: paso.from.lon,
          cantidadBicicletasDisponibles: paso.from.cantidadBicicletasDisponibles,
          cantidadPuestosDisponibles: paso.from.cantidadPuestosDisponibles
        };

        let to = {
          name: paso.to.name,
          lat: paso.to.lat,
          lon: paso.to.lon,
          cantidadBicicletasDisponibles: paso.to.cantidadBicicletasDisponibles,
          cantidadPuestosDisponibles: paso.to.cantidadPuestosDisponibles
        };

        let add_paso = {
          mode: mode,
          distance: this.ajustarDistancia(distance),
          tipoTransporte: tipoTransporte,
          routeId: routeId,
          agencyName: agencyName,
          from: from,
          to: to,
          coordenadas: coordenadas,
          steps: steps,
          mensaje: this.ajustarDetallePaso(paso, this.itineraries[0].legs, this.contador2)
        };

        this.contador2++;
        this.mejordistancia.pasos.push(add_paso);
        this.sumaDistancia1 += paso.distance;
      }
    } else {
      this.flagShowBestDistance = true;
      this.flagShowBestTime = false;

      for (let paso of this.itineraries[0].legs) {

        let mode = paso.mode;
        let tipoTransporte = paso.tipoTransporte;
        let routeId = paso.routeId;
        let agencyName = paso.agencyName;
        let distance = paso.distance;

        let coordenadas = [];
        let steps = [];

        if (mode != "WALK") {
          coordenadas = paso.coordenadas;
        }else {
          steps = paso.steps;
        }

        let from = {
          name: paso.from.name,
          lat: paso.from.lat,
          lon: paso.from.lon,
          cantidadBicicletasDisponibles: paso.from.cantidadBicicletasDisponibles,
          cantidadPuestosDisponibles: paso.from.cantidadPuestosDisponibles
        };

        let to = {
          name: paso.to.name,
          lat: paso.to.lat,
          lon: paso.to.lon,
          cantidadBicicletasDisponibles: paso.to.cantidadBicicletasDisponibles,
          cantidadPuestosDisponibles: paso.to.cantidadPuestosDisponibles
        };

        // if(mode == "BUS" && paso.routeId != "L1" && paso.routeId != "L2"){
        //     mode = "FUNICULAR"
        // }

        let add_paso = {
          mode: mode,
          distance: this.ajustarDistancia(distance),
          tipoTransporte: tipoTransporte,
          routeId: routeId,
          agencyName: agencyName,
          from: from,
          to: to,
          coordenadas: coordenadas,
          steps: steps,
          mensaje: this.ajustarDetallePaso(paso, this.itineraries[0].legs, this.contador2)
        };

        this.contador2++;
        this.mejordistancia.pasos.push(add_paso);
        this.sumaDistancia1 += paso.distance;
      }
    }

  }

  ajustarDistancia(distance): string {
    let distancia = this.pipe.transform(distance / 1000, "1.2-2");
    return distancia;
  }

  //agregar descripcion del detalle de cada paso
  ajustarDetallePaso(paso, data, index): string {
    let message = "";

    switch (paso.mode) {
      case "TRAM":
        message =
          "Tomar el " +
          paso.routeLongName +
          " del " +
          paso.agencyName +
          " desde " +
          paso.from.name +
          " hasta " +
          paso.to.name;
        break;

      case "RAIL":
        message =
          "Tomar la " +
          paso.routeLongName +
          " del " +
          paso.agencyName +
          " en la estación " +
          paso.from.name +
          " " +
          "hasta la estación " +
          paso.to.name;
        break;

      case "WALK":
        if (paso.to.name == "Destination") {
          message =
            "Caminar " +
            this.ajustarDistancia(paso.distance) +
            " km hasta " +
            this.destino.descripcion;
        } else {
          if(index < data.length){
            index++;
            console.log("DetalleViaje", "Entro ", data);
            if(data[index].mode == "BICYCLE"){
              message =
              "Caminar " +
              this.ajustarDistancia(paso.distance) +
              " km hasta estación encicla " +
              //" km hasta " +
              //this.origen.descripcion
              paso.to.name;
            } else {
              message =
                "Caminar " +
                this.ajustarDistancia(paso.distance) +
                " km hasta " +
                paso.to.name;
            }
          } else {
            message =
              "Caminar " +
              this.ajustarDistancia(paso.distance) +
              " km hasta " +
              paso.to.name;
          }
        }

        break;

      case "BICYCLE":
        message =
          "Andar " +
          this.ajustarDistancia(paso.distance) +
          " km hasta " +
          paso.to.name;
        break;

      case "CABLE_CAR":
        message =
          "Andar " +
          this.ajustarDistancia(paso.distance) +
          " km hasta " +
          paso.to.name;
        break;

      case "SUBWAY":
        message =
          "Tomar la ruta de bus " +
          paso.routeLongName +
          "-" +
          paso.routeShortName +
          " hasta el paradero " +
          paso.to.name;
        break;

      case "GONDOLA":
        message =
          "Tomar el " +
          paso.routeLongName +
          " del " +
          paso.agencyName +
          " desde " +
          paso.from.name +
          " hasta " +
          paso.to.name;
        break;

      case "FUNICULAR":
        message =
          "Tomar la ruta de integrado " +
          paso.routeLongName +
          "-" +
          paso.routeLongName +
          " del " +
          paso.agencyName +
          " desde " +
          paso.from.name +
          " hasta " +
          paso.to.name;
        break;

      case "FERRY":
        message =
          "Tomar la ruta  de alimentador " +
          paso.routeShortName +
          "-" +
          paso.routeLongName +
          " del " +
          paso.agencyName +
          " desde " +
          paso.from.name +
          " hasta " +
          paso.to.name;
        break;

      case "BUS":
        message =
          "Esperar la ruta de bus " +
          paso.routeLongName +
          " hasta la estación " +
          paso.to.name;
        break;
    }

    return message;
  }

  convertirtiempo() {
    if (this.itineraries.length == 2) {
      let time = this.itineraries[1].duration;

      let hours = Math.floor(time / 3600);
      let minutes = Math.floor((time % 3600) / 60);
      let seconds = time % 60;

      this.mejorTiempo.duracion = hours + ":" + minutes + ":" + seconds; // 0:00:00

      let time2 = this.itineraries[0].duration;

      let hours2 = Math.floor(time2 / 3600);
      let minutes2 = Math.floor((time2 % 3600) / 60);
      let seconds2 = time2 % 60;

      this.mejordistancia.duracion =
        ("0" + hours2).slice(-2) +
        ":" +
        ("0" + minutes2).slice(-2) +
        ":" +
        ("0" + seconds2).slice(-2);
    } else {
      let time2 = this.itineraries[0].duration;

      let hours2 = Math.floor(time2 / 3600);
      let minutes2 = Math.floor((time2 % 3600) / 60);
      let seconds2 = time2 % 60;

      this.mejordistancia.duracion =
        ("0" + hours2).slice(-2) +
        ":" +
        ("0" + minutes2).slice(-2) +
        ":" +
        ("0" + seconds2).slice(-2);
    }
  }

  convertirDistancia() {
    this.mejordistancia.distancia = this.pipe.transform(
      this.sumaDistancia1 / 1000,
      "1.2-2"
    );
    this.mejorTiempo.distancia = this.pipe.transform(
      this.sumaDistancia2 / 1000,
      "1.2-2"
    );
  }

  mostrarModalDetalleViaje(viaje, position) {

    if(position == 1){
      this.tarifaTotalViaje = this.response.plan.itineraries[0].rate;
      console.log("Tarifa", position , this.response.plan.itineraries[0].rate);
    }else{
      if(this.response.plan.itineraries.length == 0){
        this.tarifaTotalViaje = this.response.plan.itineraries[0].rate;
        console.log("Tarifa", position , this.response.plan.itineraries[0].rate);
        return;
      }
      this.tarifaTotalViaje = this.response.plan.itineraries[1].rate;
      console.log("Tarifa--", position , this.response.plan.itineraries[1].rate);
    }

    console.log("DetalleViaje", viaje);
    this.navCtrl.push(DetalleViajePage, {
      viaje: viaje,
      origen: this.origen,
      destino: this.destino,
      kilometrajeAuto: this.kilometrajeAuto,
      tarifaTotalViaje: this.tarifaTotalViaje
    });
  }

  onResponseViajesSugeridos(response: any) {
    console.log("onResponseViajesSugeridos", response);
    this.cargarUbicacionInicioDestino(
      response.lat_origen,
      response.lon_origen,
      response.ori_desc,
      response.lat_destino,
      response.lon_destino,
      response.des_desc
    );
    this.response = response.data;
    this.cargarDatos(response.data, response.mensaje);
  }


isLayerActive(layer: number): boolean {
    return layer == this.layerActive;
  }

getStyleClass(layer: number): string {
return (layer == this.layerActive) ? 'layer-active' : 'layer-unactive';
}

goTo(page:number):void{
        let navParams:any;
        switch(page){
            case 1:
                navParams = {'page-name': page, 'app':this,'color':'rgb(0, 96, 182)'}
                    this.navCtrl.push(ConsultaViaje, navParams)
                break;
            case 2:
                navParams = {'page-name': page, 'app':this,'color':'rgb(0, 96, 182)'}
                    this.navCtrl.push(MenuRutasPage, navParams)
                break;
            case 3:
                navParams = {'page-name': page, 'app':this,'color':'rgb(0, 96, 182)'}
                this.navCtrl.popToRoot({animate:false})
                break;
            default:
                break;
        }
}
}
