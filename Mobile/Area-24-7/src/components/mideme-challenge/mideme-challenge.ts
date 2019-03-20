import { Component } from '@angular/core';
import { NavParams } from 'ionic-angular';
import { NavController } from 'ionic-angular/navigation/nav-controller';
import { MidemeCurrentChallengeComponent } from '../mideme-current-challenge/mideme-current-challenge';

/**
 * Generated class for the MidemeChallengeComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'mideme-challenge',
  templateUrl: 'mideme-challenge.html'
})
export class MidemeChallengeComponent {

  color: string;

  constructor(private navParams: NavParams
            , private navCtrl: NavController) {
    console.log('Hello MidemeChallengeComponent Component');
    this.color = navParams.get('color');
  }

  goToCurrentChallenge(): void {
    this.navCtrl.push(MidemeCurrentChallengeComponent, {
      color: this.color,
    });
  }

}
