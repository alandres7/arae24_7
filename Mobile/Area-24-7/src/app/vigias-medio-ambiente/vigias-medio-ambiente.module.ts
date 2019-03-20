import { NgModule } from '@angular/core';
import { IonicModule, IonicPageModule  } from 'ionic-angular';
import { MediaCapture} from '@ionic-native/media-capture';
import {VigiasMedioAmbientePage} from './pages/vigias-medio-ambiente-page/vigias-medio-ambiente-page';
import { ModalVigiasPage } from "./components/modal/modal-vigias-page";
import { WsVigias } from "./provider/wsVigias";
import { NativeGeocoder } from "@ionic-native/native-geocoder";


@NgModule({
  declarations: [ModalVigiasPage,VigiasMedioAmbientePage],
  imports: [IonicPageModule.forChild(ModalVigiasPage)],
  entryComponents: [VigiasMedioAmbientePage],
  providers: [MediaCapture,WsVigias],
  exports: [IonicModule,VigiasMedioAmbientePage]
})
export class VigiasMedioAmbienteModule {}
