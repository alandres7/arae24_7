
import {NgModule} from '@angular/core';
import { IonicModule } from "ionic-angular";
import { Common } from "./utilidades/common";
import { AppDiagnostic } from "./utilidades/diagnostic";
import { PosiblesViajesProvider } from './posibles-viajes.service';
import { NativeGeocoder } from "@ionic-native/native-geocoder";

@NgModule({
  imports: [],
  declarations: [],
  exports: [IonicModule],
  providers: [
    Common, 
    AppDiagnostic,
    PosiblesViajesProvider,
    
  ]
})
export class SharedModule {

}