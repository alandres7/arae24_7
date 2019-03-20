import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular';
import { NavController } from 'ionic-angular/navigation/nav-controller';
import { MidemeCalculatorComponent } from '../mideme-calculator/mideme-calculator';
import { MidemeHistoryComponent } from '../mideme-history/mideme-history';
import { MidemeChallengeComponent } from '../mideme-challenge/mideme-challenge';
import { MidemeModalCheckChallengeComponent } from '../mideme-modal-check-challenge/mideme-modal-check-challenge';
import { Common } from '../../app/shared/utilidades/common';

/**
 * Generated class for the MidemeOptionsComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-options',
  templateUrl: 'mideme-options.html'
})
export class MidemeOptionsComponent {

  color: string;

  constructor(private navParams: NavParams
            , private navCtrl: NavController
            , private common: Common) {
    console.log('Hello MidemeOptionsComponent Component');

    this.color = navParams.get('color');
    console.log(this.color);
  }

  goToCalculator(): void {
    this.navCtrl.push(MidemeCalculatorComponent, {
      color: this.color,
    });
  }

  goToHistory(): void {
    this.navCtrl.push(MidemeHistoryComponent, {
      color: this.color,
    });
  }

  goToChallenge(): void {
    this.navCtrl.push(MidemeChallengeComponent, {
      color: this.color,
    });
  }

  goToCheckChallenge(): void {
    this.common.createModal(MidemeModalCheckChallengeComponent, {
      color: this.color
    }).present();
  }

}
