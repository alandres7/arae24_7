import { Component } from '@angular/core';

/**
 * Generated class for the CuidameComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'cuidame',
  templateUrl: 'cuidame.html'
})
export class CuidameComponent {

  text: string;

  constructor() {
    console.log('Hello CuidameComponent Component');
    this.text = 'Hello World';
  }

}
