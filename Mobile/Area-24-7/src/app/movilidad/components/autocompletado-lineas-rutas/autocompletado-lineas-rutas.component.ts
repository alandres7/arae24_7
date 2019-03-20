import { Component, Input, Output, EventEmitter } from '@angular/core';
import { WsMovilidad } from '../../providers/wsMovilidad';
import { Common } from '../../../shared/utilidades/common';
import { RutasLineasResponse } from '../../models/rutasLineasResponse.model';

/**
 * Generated class for the AutocompletadoLineasRutasComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'autocompletado-lineas-rutas',
  templateUrl: 'autocompletado-lineas-rutas.component.html'
})
export class AutocompletadoLineasRutasComponent {

  autocompleteItems: any[];
  @Input() criterioBusqueda: String;
  @Output() onResponseAutocompletado = new EventEmitter();


  constructor(public wsMovilidad: WsMovilidad, private utilidades: Common) {

  }

  ionViewWillLeave(){
   this.criterioBusqueda = ""
  }

  actualizarListado() {
    if (this.criterioBusqueda == "") {
      this.autocompleteItems = [];

      return;
    }
    this.onObtenerRutasyLineasAutocompletado(this.criterioBusqueda);
  }


  onKeyEnterRutaLinea(event: any) {
    if (event.keyCode == 13) {
      this.seleccionarItem(this.criterioBusqueda, 2);
    }
  }

  seleccionarItem(item: any, tipo: number) {
    this.utilidades.presentLoading();
    console.log("Click Item seleccionado", item);
    if (tipo == 1) {
      this.criterioBusqueda = item.descripcion;
      this.onObtenerRutaLineaDetalle(item.tipo, item.id);
    } else {
      this.onObtenerRutasLineas(this.criterioBusqueda);
    }
  }


  clickObtenerRutasLineas() {
    if (this.criterioBusqueda.trim().length > 0) {
      this.utilidades.presentLoading();
      this.onObtenerRutasLineas(this.criterioBusqueda);
    } else {
      this.utilidades.basicAlert(
        "Movilidad",
        "Por favor diligencie el campo de busqueda!"
      );
    }

  }

  onObtenerRutasyLineasAutocompletado(criterio: any) {
    this.wsMovilidad
      .obtenerRutasyLineasAutocompletado(criterio)
      .subscribe(
        succces => {
          this.autocompleteItems = [];
          this.autocompleteItems = succces;
        },
        error => {
          this.autocompleteItems = [];
        }
      );
  }

  onObtenerRutaLineaDetalle(tipo: any, id: any) {
    this.wsMovilidad
      .obtenerRutaLineaDetalle(tipo, id)
      .subscribe(
        succces => {

          console.log("Seleccionar item", succces);

          this.autocompleteItems = [];
          this.utilidades.dismissLoading();

          let data = new RutasLineasResponse();

          if (succces.linea != null) {
            let lineas = [];
            lineas.push(succces.linea);
            data.lineas = lineas;
            data.rutas = [];
          } else {
            let rutas = [];
            rutas.push(succces.ruta);
            data.rutas = rutas;
            data.lineas = [];
          }

          let response = {
            data: data,
            tipoResponse: 1
          }
          this.onResponseAutocompletado.emit({ response: response, criterioBusqueda: this.criterioBusqueda });

        },
        error => {
          this.utilidades.dismissLoading();
          this.utilidades.basicAlert(
            "Movilidad",
            "Ocurrió un inconveniente inténtelo nuevamente"
          );

        }
      );
  }


  onObtenerRutasLineas(data: any) {
    this.wsMovilidad.obtenerRutasyLineas(data).subscribe(
      succces => {
        this.autocompleteItems = [];

        console.log("Buscar", succces);
        if (succces.codigo == 1) {
          let response = {
            data: succces,
            tipoResponse: 2
          }
          this.onResponseAutocompletado.emit({ response: response, criterioBusqueda: this.criterioBusqueda });

        } else {
          if (succces.codigo == 2) {
            this.utilidades.basicAlert(
              "Movilidad",
              "No se encontraron resultados"
            );
          }
        }
        this.utilidades.dismissLoading();
      },
      error => {
        this.utilidades.dismissLoading();
        this.utilidades.basicAlert(
          "Movilidad",
          "Ocurrió un inconveniente inténtelo nuevamente"
        );
      }
    );
  }

  onFocusSearchBar() {
    //document.getElementById('gridLineasRutas').classList.add("no-scroll");
    document.body.classList.add('no-scroll');
  }

  onBlurSearchBar() {
    //document.getElementById('gridLineasRutas').classList.remove("no-scroll");
    document.body.classList.remove('no-scroll');
  }


  clearSearch(event:any){
    this.criterioBusqueda = '';
  }

}
