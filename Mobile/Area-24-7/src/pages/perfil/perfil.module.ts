import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PerfilPage } from './perfil';

@NgModule({
    declarations: [PerfilPage],
    imports: [IonicPageModule.forChild(PerfilPage)],
    entryComponents: [PerfilPage],
})
export class PerfilPageModule {}
