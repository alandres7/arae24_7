import { WebServiceEditarUsuario } from './modal-edicion-usuario/provider/wsEditarUsuario.provider';
import { EditarUsuario } from './modal-edicion-usuario/pages/editar-usuario';
import { ModalPage } from './modal-page/modal-page';
import { MapaComponent } from './mapa/mapa';
import {NgModule} from '@angular/core';
import { IonicModule, IonicPageModule } from 'ionic-angular';
import { BarraNavegabilidad } from './barra-navegabilidad/barra-navegabilidad.component';
import { SideMenuContentComponent } from './side-menu-content/side-menu-content.component';
import { ContentDrawerComponent } from "../../components/content-drawer/content-drawer";
//import { ContentDrawerComponent } from './content-drawer/content-drawer';


@NgModule({
  imports: [IonicPageModule.forChild(ModalPage), IonicPageModule.forChild(EditarUsuario)],
  declarations: [ModalPage,MapaComponent, BarraNavegabilidad, EditarUsuario, SideMenuContentComponent, ContentDrawerComponent],
  providers: [WebServiceEditarUsuario],
  exports: [IonicModule,MapaComponent,SideMenuContentComponent, BarraNavegabilidad, EditarUsuario, ContentDrawerComponent]
})
export class CoreModule {
}
