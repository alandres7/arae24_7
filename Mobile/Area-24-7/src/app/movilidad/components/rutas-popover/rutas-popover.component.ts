import { Component } from '@angular/core';
import { NavController, NavParams, ViewController } from 'ionic-angular';
import { Common } from '../../../shared/utilidades/common'
import { DetalleRutaComponent } from '../detalle-ruta/detalle-ruta.component';

@Component({
  selector: 'rutas-popover',
  templateUrl: 'rutas-popover.component.html',
})
export class RutasPopoverComponent {

  mostrarDetalle: boolean;
  iconClass: string;

  //array de tipos de rutas
  rutas: any;
  //index del tipo de array de rutas
  tipo: any;

  constructor(public viewCtrl: ViewController, public navParams: NavParams, public common : Common) {
    this.mostrarDetalle = false;
    this.iconClass = "arrow-dropdown-circle";
    this.rutas = this.navParams.get('rutas');
    this.tipo = this.navParams.get('tipo');
  }


  toggleDetalle() {
    if (this.mostrarDetalle) {
      this.mostrarDetalle = false;
      this.iconClass = "arrow-dropdown-circle";
    } else {
      this.mostrarDetalle = true;
      this.iconClass = "arrow-dropup-circle";
    }
  }
  close() {
    this.common.dismissModal();
  }

}
