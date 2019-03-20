import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { GoogleChartComponent } from './google-chart';

@NgModule({
  declarations: [
    GoogleChartComponent,
  ],
  imports: [
    IonicPageModule.forChild(GoogleChartComponent),
  ],
  exports: [
    GoogleChartComponent
  ]
})
export class GoogleChartComponentModule {}
