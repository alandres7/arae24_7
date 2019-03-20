import { GoogleMaps } from './../../../shared/utilidades/googleMaps';
import { WsOrdenamiento } from './../../provider/wsOrdenamiento';
import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy, ViewChildren, QueryList, ViewChild } from '@angular/core';
import { _finally } from 'rxjs/operator/finally';
import { BuscadorComponent } from '../buscador/buscador';

export interface clase {
  nombre: string;
  rutaLogo: string;
  dato: string;
  activo: boolean;
}

@Component({
  selector: 'item',
  templateUrl: 'item.html'
})

export class ItemComponent {
  lista: any = {};

  listaFiltrados: any[];

  @Input()
  listaEquipamientos: Array<any>;

  getEquipamentos() {
    return this.listaEquipamientos;
  }

  @ViewChild(BuscadorComponent) buscadorComponent;

  @Output()
  select = new EventEmitter<any>();

  seleccionado(objeto: any) {
    objeto.capaPadre = this.lista.id;
    this.select.emit(objeto);
  }

  buscarObjeto(pObjeto: any) {
    if (this.mapsComp.ListaMarcadores[this.lista.id]) {
      if (this.mapsComp.ListaMarcadores[this.lista.id][pObjeto.id]) {

        return true;
      }
      else {
        return false;
      }

    }
    else {
      return false;
    }

  }

  esActivo(pObjeto: any) {
    return this.buscarObjeto(pObjeto);
  }

  mostrarListaPintar() {
    console.log('Lista para pintar --->' + JSON.stringify(this.lista));
  }
  constructor(private wsOrdenamiento: WsOrdenamiento, private mapsComp: GoogleMaps) {
    //this.lista = this.listaEquipamientos || [];
    //this.obtenerCapas(9);
  }

  obtenerCapas(id) {

   /* var token = localStorage.getItem('bearer');
    var data = id + "/" + this.mapsComp.posicionLat + "/" + this.mapsComp.posicionLon + "/" + this.mapsComp.pRadioAccion;

    this.wsOrdenamiento.obtenerCapasRadioAccion(token, data).then((response: any) => {
      // var respuesta = JSON.parse(response._body);

      this.lista = JSON.parse(response._body);
      this.buscadorComponent.listaEquipamentos = JSON.parse(response._body);
    },
      (error) => {

      });
    // this.wsOrdenamiento.obtenerCapas(token, id).then((response: any) => {
    //   var respuesta = JSON.parse(response._body);
    //   this.lista = respuesta;
    // },
    //   (error) => {

    //   });
*/
  }

}
