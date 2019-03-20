import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnChanges,
  SimpleChanges
} from "@angular/core";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { Common } from "../../../shared/utilidades/common";
import { GmapsMovilidad } from "../../providers/gmapsMovilidad";
import { WsMovilidad } from "../../providers/wsMovilidad";
import { Estacion } from "../../models/estacion.model";

declare var google;
@Component({
  selector: "detalle-ruta",
  templateUrl: "detalle-ruta.component.html"
})
export class DetalleRutaComponent implements OnChanges {
  @Input() ruta: any;
  @Output() mostrarRuta = new EventEmitter();
  mostrarDetalle: boolean;
  iconClass: string;
  estacionesMetro: any[] = []; 
  markers = [];

  constructor(
    private googleMaps: GoogleMaps,
    private utilidades: Common,
    public wsMovilidad: WsMovilidad
  ) {
    this.mostrarDetalle = false;
    this.iconClass = "arrow-dropdown-circle";
  }

  ngOnChanges(changes: SimpleChanges) {
    let data = changes.ruta.currentValue;
    if (data.idLinea && data.estaciones) {
      this.obtenerEstaciones(data.estaciones);
    }
  }
  toggleDetalle() {    if (this.mostrarDetalle) {
      this.mostrarDetalle = false;
      this.iconClass = "arrow-dropdown-circle";
    } else {
      this.mostrarDetalle = true;
      this.iconClass = "arrow-dropup-circle";
    }
  }

  checkMostrarRuta(event, ruta) {
    debugger;
    if (event.checked) {
      let modoTransporte = GmapsMovilidad.obtenerModoTransporte(ruta);

      this.pintarExtremos(ruta, 'assets/movilidad/markers/markerInicio.svg', 'start')
      this.pintarExtremos(ruta, 'assets/movilidad/markers/markerLlegada.svg', 'end')
      
      if (ruta.idLinea) {
        this.pintarMarkers(ruta.estaciones);
      } else {
        this.pintarMarkers(ruta.paraderos);
      }

      GmapsMovilidad.mostrarRuta(ruta, modoTransporte);
      ruta.checked = true;
      this.mostrarRuta.emit({ visible: true, markers: this.markers });
    } else {
      GmapsMovilidad.ocultarRuta(ruta);
      GmapsMovilidad.ocultarMarkersRuta(ruta);
      ruta.checked = false;
    }
  }

  public pintarExtremos(ruta:any, icono:String, tipo:String){
    let startMarker;    
    if(tipo == 'start'){
      startMarker = {
        icono : icono,
        mLat : ruta.primerPunto[0],
        mLng: ruta.primerPunto[1]
      }   
    }else if (tipo == 'end'){
      startMarker = {
        icono : icono,
        mLat : ruta.ultimoPunto[0],
        mLng: ruta.ultimoPunto[1]
      }
    }
    
    let marker = GmapsMovilidad.pintarMarker(startMarker)
    if (ruta.idLinea) {
      marker.idRuta = ruta.idLinea;
    } else if (ruta.idRuta) {
      marker.idRuta = ruta.idRuta;
    }
    
    GmapsMovilidad.markersPolylines.push(marker);
    this.markers.push(marker)
  }

  obtenerEstaciones(estaciones: any): any {
    this.estacionesMetro = [];
    estaciones.forEach(element => {
      this.estacionesMetro.push(element.descripcion);
    });
    return this.estacionesMetro;
  }

  private pintarMarkers(listaRutaCercanas: any) {
    console.log("PinarMarkersDetalleRuta", listaRutaCercanas);
    if (listaRutaCercanas) {
      for (var index = 0; index < listaRutaCercanas.length; index++) {
        let dataMarker = {
          icono: GmapsMovilidad.obtenerIconoMarker(listaRutaCercanas[index]),
          mLat: listaRutaCercanas[index].latitud,
          mLng: listaRutaCercanas[index].longitud
        };
        let marker = GmapsMovilidad.pintarMarker(dataMarker);

        if (listaRutaCercanas[index].idLinea) {
          marker.idRuta = listaRutaCercanas[index].idLinea;
        } else if (listaRutaCercanas[index].idRuta){
          marker.idRuta = listaRutaCercanas[index].idRuta;
        }
        
        GmapsMovilidad.markersPolylines.push(marker);
        marker.dataRutaCercana = listaRutaCercanas[index];
        GmapsMovilidad.agregarInfoParadero(marker);
        this.markers.push(marker);
      }
    }
  }
}
