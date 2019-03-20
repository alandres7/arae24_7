import { CoreModule } from './../core/core.module';

import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { InicioPage } from './pages/inicio-page/inicio-page';
import { Geolocation } from '@ionic-native/geolocation';
import { MiEntornoModule } from '../mi-entorno/mi-entorno.module';
import { HuellasAmbientalesModule } from '../huellas-ambientales/huellas-ambientales.module';
import { VigiasMedioAmbienteModule } from '../vigias-medio-ambiente/vigias-medio-ambiente.module';
import { MovilidadModule } from '../movilidad/movilidad.module';
import { OrdenamientoModule } from './../ordenamiento/ordenamiento.module';
import { ComponentsModule } from '../../components/components.module';
//import { ModalCambioTerritorioPage } from "../../pages/modal-cambio-territorio/modal-cambio-territorio";


@NgModule({
  declarations: [InicioPage, ],
  imports: [IonicModule, 
  MiEntornoModule,
  HuellasAmbientalesModule,
  VigiasMedioAmbienteModule,
  MovilidadModule,
  OrdenamientoModule,
  CoreModule,
  ComponentsModule
  ],
  entryComponents: [InicioPage, ],
  providers: [
      Geolocation
  ]
})
export class InicioModule {}
