import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular';
import { NavController } from 'ionic-angular/navigation/nav-controller';

/**
 * Generated class for the MidemeHistoryComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-history',
  templateUrl: 'mideme-history.html'
})
export class MidemeHistoryComponent {

  color: string;
  clase = 'meh';
  nivel = 'Medio';
  valor = '150';
  constructor(private navParams: NavParams
            , private navCtrl: NavController) {
    console.log('Hello MidemeHistoryComponent Component');
    this.color = navParams.get('color');
  }

}
