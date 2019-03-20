import { Component } from '@angular/core';

/**
 * Generated class for the GoogleChartComponent component.
 *
 * See https://angular.io/docs/ts/latest/api/core/index/ComponentMetadata-class.html
 * for more info on Angular Components.
 */
@Component({
  selector: 'google-chart',
  templateUrl: 'google-chart.html'
})
export class GoogleChartComponent {

  text: string;

  constructor() {
    console.log('Hello GoogleChartComponent Component');
    this.text = 'Hello World';
  }

}
