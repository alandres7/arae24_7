import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { MenuMainPage } from './menu-main';
import { MiEntornoModule } from '../../app/mi-entorno/mi-entorno.module';
import { HuellasAmbientalesModule } from '../../app/huellas-ambientales/huellas-ambientales.module';
import { VigiasMedioAmbienteModule } from '../../app/vigias-medio-ambiente/vigias-medio-ambiente.module';
import { MovilidadModule } from '../../app/movilidad/movilidad.module';
import { OrdenamientoModule } from '../../app/ordenamiento/ordenamiento.module';
import { ComponentsModule } from '../../components/components.module';
@NgModule({
  declarations: [
    MenuMainPage,
  ],
  imports: [
    IonicPageModule.forChild(MenuMainPage),
    MiEntornoModule,
    HuellasAmbientalesModule,
    VigiasMedioAmbienteModule,
    MovilidadModule,
    OrdenamientoModule,
    ComponentsModule
  ]
})
export class MenuMainPageModule {}
