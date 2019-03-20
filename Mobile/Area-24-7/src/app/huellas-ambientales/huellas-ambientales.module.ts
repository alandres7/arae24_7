import { ModalHuellasPage } from './components/modal-huellas-page/modal-huellas-page';
import { NgModule } from '@angular/core';
import { IonicModule, IonicPageModule } from 'ionic-angular';
import {HuellasAmbientalesPage} from './pages/huellas-ambientales-page/huellas-ambientales-page';
import { ChartsModule } from 'ng2-charts';

@NgModule({
  declarations: [HuellasAmbientalesPage, ModalHuellasPage],
  imports: [IonicModule, IonicPageModule.forChild(ModalHuellasPage), ChartsModule],
  entryComponents: [HuellasAmbientalesPage],
  providers: [],
  exports:[HuellasAmbientalesPage]
})
export class HuellasAmbientalesModule {}
