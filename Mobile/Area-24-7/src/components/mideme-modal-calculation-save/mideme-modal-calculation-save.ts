import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { NavController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeModalCalculationSaveComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-modal-calculation-save',
  templateUrl: 'mideme-modal-calculation-save.html'
})
export class MidemeModalCalculationSaveComponent {

  color: string;

  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeModalCalculationSaveComponent Component');
    this.color = navParams.get('color');
  }

  closeModal(): void {
    this.common.dismissModal();
  }

}
