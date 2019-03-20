import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { NavController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeModalChangeChallengeComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-modal-change-challenge',
  templateUrl: 'mideme-modal-change-challenge.html'
})
export class MidemeModalChangeChallengeComponent {

  text: string;

  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeModalChangeChallengeComponent Component');
    this.text = 'Hello World';
  }

  closeModal(): void {
    this.common.dismissModal();
  } 
}
