import { WebServiceRegistro } from './provider/wsRegistro.provider';
import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { FormRegister } from './pages/form-register';


@NgModule({
    declarations: [FormRegister],
    imports: [IonicModule],
    entryComponents: [FormRegister],
    providers: [WebServiceRegistro]
})

export class FormRegisterModule {}
