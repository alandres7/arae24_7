import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { NavController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeModalCheckChallengeComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-modal-check-challenge',
  templateUrl: 'mideme-modal-check-challenge.html'
})
export class MidemeModalCheckChallengeComponent {

  color: string;
  dias = 17;
  ancho = this.dias * 4.761905 + '%';

  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeModalCheckChallengeComponent Component');
    this.color = navParams.get('color');
  }

  closeModal(): void {
    this.common.dismissModal();
  }

}
