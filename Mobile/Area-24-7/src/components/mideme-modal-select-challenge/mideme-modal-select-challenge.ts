import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { NavController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeModalSelectChallengeComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-modal-select-challenge',
  templateUrl: 'mideme-modal-select-challenge.html'
})
export class MidemeModalSelectChallengeComponent {

  //listOfChallenges: Array<string>;
  items = ['Reto 1', 'Reto 2', 'Reto 3', 'Reto 4'];
  challengeCategory: string;

  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeModalSelectChallengeComponent Component');
    this.challengeCategory = navParams.get('challengeCategory');
  }

  closeModal(): void {
    this.common.dismissModal();
  }

}
