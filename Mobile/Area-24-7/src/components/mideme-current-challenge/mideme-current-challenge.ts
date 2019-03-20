import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular';
import { NavController } from 'ionic-angular/navigation/nav-controller';
import { MidemeModalChangeChallengeComponent } from '../mideme-modal-change-challenge/mideme-modal-change-challenge';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeCurrentChallengeComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-current-challenge',
  templateUrl: 'mideme-current-challenge.html'
})
export class MidemeCurrentChallengeComponent {

  color: string;
  dias = 17;
  progressdias = 'progress-' + this.dias;
  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeCurrentChallengeComponent Component');
    this.color = navParams.get('color');
  }

  cancelChallenge(): void {
    this.common.createModal(MidemeModalChangeChallengeComponent).present();
  }

}
