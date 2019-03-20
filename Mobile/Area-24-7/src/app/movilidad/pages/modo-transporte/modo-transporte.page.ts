import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'modo-transporte',
  templateUrl: 'modo-transporte.page.html',
})
export class ModoTransportePage {

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ModoTransportePage');
  }

}
