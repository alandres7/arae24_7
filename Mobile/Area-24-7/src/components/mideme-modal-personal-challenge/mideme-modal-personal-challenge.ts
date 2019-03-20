import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { NavController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeModalPersonalChallengeComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-modal-personal-challenge',
  templateUrl: 'mideme-modal-personal-challenge.html'
})
export class MidemeModalPersonalChallengeComponent {

  color: string;

  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeModalPersonalChallengeComponent Component');
    this.color = navParams.get('color');
  }

  closeModal(): void {
    this.common.dismissModal();
  }

}
