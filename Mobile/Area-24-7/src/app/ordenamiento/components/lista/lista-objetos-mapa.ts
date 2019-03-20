import { Component, Input } from '@angular/core';
import { GoogleMaps } from '../../../shared/utilidades/googleMaps';
declare var google;

declare var google;

@Component({
  selector: 'lista-objetos-mapa',
  templateUrl: 'lista-objetos-mapa.html'
})
export class ListaObjetosMapaComponent {

  constructor(public map: GoogleMaps) {

  }

  @Input() listaObjetosMapa: Array<any>;

  seleccionObjeto(item) {
    // console.log('item seleccionado ---> ', item);
    try {
      if (item['objetoGoogle']) {
        console.log('item del mapa seleccionado ---> ', item['objetoGoogle']['position']);
        let posicion = { 'lat': 0, 'long': 0 };
        posicion.lat = item['objetoGoogle']['position'].lat();
        posicion.long = item['objetoGoogle']['position'].lng();
        this.map.volverAPunto(posicion);
      }
    } catch (e) {
      try {
        if (item['objetoGoogle']) {
          let bounds = new google.maps.LatLngBounds();
          let posicion = { 'lat': 0, 'long': 0 };
          item['objetoGoogle'].getPath().forEach(function (element, index) { bounds.extend(element) });
          posicion.lat = bounds.getCenter().lat();
          posicion.long = bounds.getCenter().lng();
          this.map.volverAPunto(posicion);
        }
      } catch (error) {

      }

    }

  }
}
