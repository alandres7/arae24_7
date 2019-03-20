import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { ModoTransportePage } from '../modo-transporte/modo-transporte.page';
import { RadioAccionPage } from '../radio-accion/radio-accion.page';

@Component({
  selector: 'preferencias-movilidad',
  templateUrl: 'preferencias-movilidad.page.html',
})
export class PreferenciasMovilidadPage {

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  goModoTransportePage() {
    this.navCtrl.push(ModoTransportePage);
  }

  goRadioAccionPage() {
    this.navCtrl.push(RadioAccionPage);
  }
}
