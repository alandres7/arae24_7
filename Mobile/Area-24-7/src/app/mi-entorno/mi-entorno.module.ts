import { WsEntorno } from './provider/wsEntorno';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import {MiEntornoPage} from './pages/mi-entorno-page/mi-entorno-page';
import { CommonModule } from '@angular/common';
import { LayerManagerComponent } from "../../components/layer-manager/layer-manager";

@NgModule({
  declarations: [MiEntornoPage],
  imports: [IonicModule],
  entryComponents: [MiEntornoPage],
  providers: [WsEntorno],
  exports: [MiEntornoPage],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA, ]
})
export class MiEntornoModule {}
