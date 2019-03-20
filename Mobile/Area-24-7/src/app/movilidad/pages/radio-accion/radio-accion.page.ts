import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'radio-accion',
  templateUrl: 'radio-accion.page.html',
})
export class RadioAccionPage {
  KEYSATURATION: string = 'usuario.saturation';
  saturation: number = 500;

  constructor(public navCtrl: NavController, public navParams: NavParams, public storage: Storage) {
    var self = this;
    this.storage.get(this.KEYSATURATION).then((value) => {
      if (value) {
        self.saturation = value;
      } else {
        self.saturation = 500;
      }
    });
  }

  cambiarSaturacion() {
    this.storage.set(this.KEYSATURATION, this.saturation);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RadioAccionpagePage');
  }

}
