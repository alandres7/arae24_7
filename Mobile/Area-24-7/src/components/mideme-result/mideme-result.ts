import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { NavController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';
import { MidemeModalSelectChallengeComponent } from '../mideme-modal-select-challenge/mideme-modal-select-challenge';
import { MidemeModalPersonalChallengeComponent } from '../mideme-modal-personal-challenge/mideme-modal-personal-challenge';
import { MidemeModalCalculationSaveComponent } from '../mideme-modal-calculation-save/mideme-modal-calculation-save';

/**
 * Generated class for the MidemeResultComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-result',
  templateUrl: 'mideme-result.html'
})
export class MidemeResultComponent {

  color: string;
  clase = 'meh';
  nivel = 'Medio';
  valor = '150';
  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeResultComponent Component');
    this.color = navParams.get('color');
  }

  goToAcceptChallenge(): void {
    this.common.createModal(MidemeModalPersonalChallengeComponent, {
      color: this.color
    }).present();
  }

  goToDeclineChallenge(): void {
    this.common.createModal(MidemeModalCalculationSaveComponent, {
      color: this.color
    }).present();
  }

  openChallenges(): void {
    this.common.createModal(MidemeModalSelectChallengeComponent,
      { challengeCategory: 'Baño' }).present();
  }
}
