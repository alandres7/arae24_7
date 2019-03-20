import { Subscription } from 'rxjs/Subscription';
import { Component, EventEmitter, Output } from "@angular/core";
import { NavController, NavParams, ViewController } from "ionic-angular";
import { GmapsMovilidad } from "../../providers/gmapsMovilidad";
import { Common } from "../../../shared/utilidades/common";
import { DatePipe } from "@angular/common";
import { WsMovilidad } from "../../providers/wsMovilidad";
import { HuellaEmisionRequest } from "../../models/HuellaEmisionRequest.model";
import { UtilsMovilidad } from "../../providers/utilsMovilidad";
import { Geolocation, Geoposition } from '@ionic-native/geolocation';
import { LocationProvider } from '../../providers/location';

@Component({
  selector: "detalle-viaje",
  templateUrl: "detalle-viaje.page.html"
})
export class DetalleViajePage {
  @Output() clickVerEnMapa?= new EventEmitter();
  showDetalle: boolean;
  gmapsMovildiad = GmapsMovilidad;
  myMarker;

  emisionCO2: number;
  emisionPM2_5: number;
  emisionCO2Autos: number;
  emisionCO2Evitada: number;
  kilometrajeAuto: number;
  tarifaTotalViaje: number;
  public app:any = this;
  titulo: string = "Detalle Viaje";
  private DISTANCE_TOLERANCE = 500;
  private LOCATION_UPDATES_INTERVAL = 10;
  private locationUpdateSubscription:Subscription;


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

  pronostico = {
    codigo: 0,
    descTiempoOrigen: "",
    idProbabilidadOrigen: 0,
    descProbabilidadOrigen: "",
    descTiempoDestino: "",
    idProbabilidadDestino: 0,
    descProbabilidadDestino: "",
    descripcion: "",
    urlIconoTiempoOrigen: "",
    urlIconoTiempoDestino: "",
    urlIconoProbabilidadOrigen: "",
    urlIconoProbabilidadDestino: ""
  };

  currentDate = new Date();

  viaje = {
    duracion: "",
    distancia: 0,
    pasos: [],
    flightPlans: []
  };

  adressorigin1 = "";
  adressorigin2 = "";
  adressdestino1 = "";
  adressdestino2 = "";

  pasoAnterior: any;

  constructor(
    public navCtrl: NavController,
    public viewCtrl: ViewController,
    public navParams: NavParams,
    public gMaps: GmapsMovilidad,
    private common: Common,
    public wsMovilidad: WsMovilidad,
    private datePipe: DatePipe,
    private geolocation: Geolocation,
    private locationProvider : LocationProvider
  ) {
    this.showDetalle = false;

    let data = this.navParams.get("viaje");
    let origen1 = this.navParams.get("origen");
    let destino1 = this.navParams.get("destino");
    this.kilometrajeAuto = this.navParams.get("kilometrajeAuto");
    this.tarifaTotalViaje = this.navParams.get("tarifaTotalViaje");

    this.origen.latitud = origen1.latitud;
    this.origen.longitud = origen1.longitud;
    this.origen.descripcion = origen1.descripcion;

    this.destino.latitud = destino1.latitud;
    this.destino.longitud = destino1.longitud;
    this.destino.descripcion = destino1.descripcion;

    this.viaje.distancia = data.distancia;
    this.viaje.duracion = data.duracion;
    this.viaje.pasos = data.pasos;

    this.ajustarDireccion();
    GmapsMovilidad.markersPolylinesViajes = [];

    for (let paso of this.viaje.pasos) {
      let modo = "";

      switch (paso.mode) {
        case "TRAM":
          modo = "tranvia";
          break;

        case "RAIL":
          modo = "metro";
          break;

        case "WALK":
          modo = "caminar";
          break;

        case "BICYCLE":
          modo = "bici-particular";
          break;

        case "CABLE_CAR":
          modo = "cicloruta";
          break;

        case "SUBWAY":
          modo = "bus";
          break;

        case "GONDOLA":
          modo = "metrocable";
          break;

        case "FUNICULAR":
          modo = "bus-integrado";
          break;

        case "FERRY":
          modo = "bus-alimentador";
          break;

        case "BUS":
          modo = "metroplus";
          break;
      }

      let flightPlanCoordinates = [];

      if (paso.mode == "WALK") {

        if (this.pasoAnterior) {
          flightPlanCoordinates.push({ lat: this.pasoAnterior.to.lat, lng: this.pasoAnterior.to.lon });
        } else {
          flightPlanCoordinates.push({ lat: paso.from.lat, lng: paso.from.lon });
        }

        for (let step of paso.steps) {
          let lat = step.lat;
          let lon = step.lon;
          flightPlanCoordinates.push({ lat: lat, lng: lon });
        }

        flightPlanCoordinates.push({ lat: paso.to.lat, lng: paso.to.lon });

      } else {

        if (paso.coordenadas != null) {
          console.log("Paso: " + paso.mode, paso.coordenadas);

          if (this.pasoAnterior) {
            flightPlanCoordinates.push({ lat: this.pasoAnterior.to.lat, lng: this.pasoAnterior.to.lon });
          } else {
            flightPlanCoordinates.push({ lat: paso.from.lat, lng: paso.from.lon });
          }

          let contador = 0;

          for (let coord of paso.coordenadas) {
            let lat = coord.x;
            let lon = coord.y;

            if ((contador == 0) || (contador == (paso.coordenadas.length - 1))) {
              console.log("DetalleViaje", "Eliminando coordenadas erroneas " + contador);
            } else {
              flightPlanCoordinates.push({ lat: lat, lng: lon });
            }

            contador++;
          }

          flightPlanCoordinates.push({ lat: paso.to.lat, lng: paso.to.lon });
        }
      }

      let retorno = GmapsMovilidad.renderPoint(flightPlanCoordinates, modo);
      GmapsMovilidad.markersPolylinesViajes.push(retorno);

      this.viaje.flightPlans.push(retorno);
      this.pasoAnterior = paso;
    }
    this.pintarOrigenDestino();
  }


  private pintarOrigenDestino() {
    let dataMarkerOrigen = {
      icono: "assets/movilidad/markers/markerInicio.svg",
      mLat: this.origen.latitud,
      mLng: this.origen.longitud
    };
    let dataMarkerDestino = {
      icono: "assets/movilidad/markers/markerLlegada.svg",
      mLat: this.destino.latitud,
      mLng: this.destino.longitud
    };
    GmapsMovilidad.markersPolylinesViajes.push(
      GmapsMovilidad.pintarMarker(dataMarkerOrigen)
    );
    GmapsMovilidad.markersPolylinesViajes.push(
      GmapsMovilidad.pintarMarker(dataMarkerDestino)
    );

    let bounds = new google.maps.LatLngBounds();
    let map = GmapsMovilidad.getMapa();
    bounds.extend(new google.maps.LatLng(this.origen.latitud ,this.origen.longitud));
    bounds.extend(new google.maps.LatLng(this.destino.latitud ,this.destino.longitud));
    map.fitBounds(bounds);
  }

  ngOnInit() {
    console.log('Detalle Viaje')
    let position: Geoposition = this.locationProvider.getMyLocation();
    if(this.myMarker == null){
      let dataMarker = {
        icono: 'assets/mapa/ubicacion.png',
        mLat: position.coords.latitude,
        mLng: position.coords.longitude
      };
        this.myMarker = GmapsMovilidad.pintarMarker(dataMarker);
      }else{
        let latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        this.myMarker.setPosition(latlng);
      }
      
  }


  //limpiar mapa
  ionViewDidLeave() {
    GmapsMovilidad.eliminarMarkersPolylines(
      GmapsMovilidad.markersPolylinesViajes
    );
    let map = GmapsMovilidad.getMapa();
    map.setZoom(11);
    if(this.myMarker){
      this.myMarker.setMap(null);
    }

    this.common.dismissLoading();
  }

  clickMostrarDetalle() {
    if (this.pronostico.codigo == 0) {
      let dataPronositco = this.obtenerDataPronostico();
      let dataHuella = this.obtenerDataHuellaCarbono();
      this.common.presentLoading();
      this.getPronosticos(dataHuella, dataPronositco);
      this.showDetalle = !this.showDetalle;
    } else {
      this.showDetalle = !this.showDetalle;
    }
  }

  obtenerDataHuellaCarbono() {
    let data: HuellaEmisionRequest[] = [];

    let item: HuellaEmisionRequest;

    for (let paso of this.viaje.pasos) {
      item = new HuellaEmisionRequest();
      let modoTrasnporte = UtilsMovilidad.obtenerModoTransporteHuella(
        paso.mode
      );

      item.tipoTransporte = modoTrasnporte;
      item.kilometraje = parseFloat(paso.distance);

      data = this.cargarHuellaEmisionRequest(item, data);
    }

    item = new HuellaEmisionRequest();

    item.tipoTransporte = 'AUTO';
    item.kilometraje = this.kilometrajeAuto / 1000;
    data.push(item);
    return data;
  }

  cargarHuellaEmisionRequest(
    item: HuellaEmisionRequest,
    array: HuellaEmisionRequest[]
  ): HuellaEmisionRequest[] {
    if (array.length == 0) {
      array.push(item);
    } else {
      for (let index = 0; index < array.length; index++) {
        if (array[index].tipoTransporte === item.tipoTransporte) {
          array[index].kilometraje += item.kilometraje;
          break;
        } else {
          array.push(item);
          break;
        }
      }
    }

    return array;
  }

  //hacer wrap a las direcciones
  ajustarDireccion() {
    let inicio = this.origen.descripcion.split(", ");
    this.adressorigin1 = inicio[0];

    for (let i = 0; i < inicio.length; i++) {
      if (i != 0) {
        this.adressorigin2 += inicio[i];
        if (i != inicio.length - 1) {
          this.adressorigin2 += ", ";
        }
      }
    }

    let fin = this.destino.descripcion.split(", ");
    this.adressdestino1 = fin[0];

    for (let i = 0; i < fin.length; i++) {
      if (i != 0) {
        this.adressdestino2 += fin[i];
        if (i != fin.length - 1) {
          this.adressdestino2 += ", ";
        }
      }
    }
  }

  obtenerDataPronostico() {
    let day: any = this.currentDate.getDate();
    let month: any = this.currentDate.getMonth() + 1;
    let year: any = this.currentDate.getFullYear();
    let hours: any = this.currentDate.getHours();
    let minutes: any = this.currentDate.getMinutes();
    let seconds: any = this.currentDate.getSeconds();

    let dateInApiFormat =
      year +
      "-" +
      month +
      "-" +
      day +
      " " +
      hours +
      ":" +
      minutes +
      ":" +
      seconds;

    let fechaActual = this.datePipe.transform(
      dateInApiFormat,
      "yyyy-MM-dd HH:mm:ss"
    );

    let duracion = this.viaje.duracion.split(":");
    let h = duracion[0];
    let m = duracion[1];
    let s = duracion[2];

    let sh: number = Number.parseInt(hours) + Number.parseInt(h);
    let sm: number = Number.parseInt(minutes) + Number.parseInt(m);
    let ss: number = Number.parseInt(seconds) + Number.parseInt(s);

    if (ss > 59) {
      sm = sm + 1;
      ss = ss - 60;
    }

    if (sm > 59) {
      sh = sh + 1;
      sm = sm - 60;
    }

    let dateInApiFormat2 =
      year + "-" + month + "-" + day + " " + sh + ":" + sm + ":" + ss;
    let fechaDestino = this.datePipe.transform(
      dateInApiFormat2,
      "yyyy-MM-dd HH:mm:ss"
    );

    let data =
      fechaActual +
      "/" +
      fechaActual +
      "/" +
      this.origen.longitud +
      "/" +
      this.origen.latitud +
      "/" +
      fechaDestino +
      "/" +
      this.destino.longitud +
      "/" +
      this.destino.latitud +
      "/";

    return data;
  }

  obtenerPronostico(data) {
    this.wsMovilidad.obtenerPronosticoSiata(data).subscribe(
      succes => {
        this.common.dismissLoading();
        console.log("ObtenerPronosticoSucces", succes);
        this.mapearPronostico(succes);
      },
      error => {
        this.common.dismissLoading();
        console.log("ObtenerPronosticoError", error);
        this.common
          .basicAlert(
            "Movilidad",
            "Ocurrió un inconveniente inténtelo nuevamente"
          )
          .then(() => { });
      }
    );
  }

  mapearPronostico(responsePronostico) {
    this.pronostico.codigo = responsePronostico.codigo;
    this.pronostico.descTiempoOrigen = responsePronostico.descTiempoOrigen;
    this.pronostico.idProbabilidadOrigen =
      responsePronostico.idProbabilidadOrigen;
    this.pronostico.descProbabilidadOrigen =
      responsePronostico.descProbabilidadOrigen;
    this.pronostico.descTiempoDestino = responsePronostico.descTiempoDestino;
    this.pronostico.idProbabilidadDestino =
      responsePronostico.idProbabilidadDestino;
    this.pronostico.descProbabilidadDestino =
      responsePronostico.descProbabilidadDestino;
    this.pronostico.descripcion = responsePronostico.descripcion;
    this.pronostico.urlIconoTiempoOrigen =
      responsePronostico.urlIconoTiempoOrigen;
    this.pronostico.urlIconoTiempoDestino =
      responsePronostico.urlIconoTiempoDestino;
    this.pronostico.urlIconoProbabilidadOrigen =
      responsePronostico.urlIconoProbabilidadOrigen;
    this.pronostico.urlIconoProbabilidadDestino =
      responsePronostico.urlIconoProbabilidadDestino;
  }

  mapearHuellaCarbono(responseHuellaCarbono) {
    this.emisionCO2 = responseHuellaCarbono.responses[0].emisionCO2;
    this.emisionPM2_5 = responseHuellaCarbono.responses[0].emisionPM2_5;
    this.emisionCO2Autos = responseHuellaCarbono.responses[0].emisionCO2Autos;
    this.emisionCO2Evitada = Number((this.emisionCO2Autos - this.emisionCO2).toFixed(2));
  }


  public getPronosticos(dataHuella, dataPronositco) {
    debugger;
    var pronostico  = this.wsMovilidad.obtenerHuellaCarbonoPronostico(dataHuella,dataPronositco)
    pronostico.subscribe(data =>{
          console.log(data)
          this.mapearHuellaCarbono(data[0]);
          this.mapearPronostico(data[1]);
          this.common.dismissLoading();
    }, err =>{
      this.common.dismissLoading();
      this.common.basicAlert(
        "Movilidad",
        "Ocurrió un inconveniente inténtelo nuevamente"
      );
    })
  }
  
  onObtenerHuellaCarbonoPronostico(dataHuella, dataPronositco) {
    console.log("dataHuella", dataHuella);
    console.log("dataPronositco", dataPronositco),

    this.wsMovilidad
      .obtenerHuellaCarbonoPronostico(dataHuella, dataPronositco)
      .subscribe(
        data => {
          console.log("onObtenerHuellaCarbonoPronostico", data);
          this.mapearHuellaCarbono(data[0]);
          this.mapearPronostico(data[1]);
          this.common.dismissLoading();
        },
        err => {
          this.common.dismissLoading();
          this.common.basicAlert(
            "Movilidad",
            "Ocurrió un inconveniente inténtelo nuevamente"
          );
        }
      );
  }
}
