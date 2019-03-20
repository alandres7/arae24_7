import { WebServiceLogin } from './provider/wsLogin';
import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { LoginPage } from './pages/login-page/login-page';
import { Device } from '@ionic-native/device';

import { AuthenticationProvider } from '../../providers/authentication/authentication';

@NgModule({
    declarations: [LoginPage],
    imports: [IonicModule],
    entryComponents: [LoginPage],
    providers: [WebServiceLogin, Device, AuthenticationProvider],
})
export class LoginModule {}
