import { Component, OnInit } from '@angular/core';
import { PerfilPage } from '../../pages/perfil/perfil';
import { UpdatePasswordPage } from '../../pages/update-password/update-password';
import { OneSignal } from '@ionic-native/onesignal';
import { GoogleMaps } from '../../app/shared/utilidades/googleMaps';
import { LoginPage } from '../../app/login/pages/login-page/login-page';
import { NavController } from 'ionic-angular/navigation/nav-controller';
import { Common } from '../../app/shared/utilidades/common';
import { App, Platform } from 'ionic-angular';
import { Facebook } from '@ionic-native/facebook';

@Component({
    selector: 'general-configuration',
    templateUrl: 'general-configuration.html'
})
export class GeneralConfigurationComponent {

    private logoutState = false;

    constructor(
        private oneSignal: OneSignal,
        public googleMaps: GoogleMaps,
        protected app: App,
        private common: Common,
        private platform: Platform,
        private facebook: Facebook
    ) { }

    helpModal() {
        this.common.createModal('HelpPage');
    }

    perfilUsuario() {
        let datos = JSON.parse(localStorage.getItem('usuario'));
        this.common.createModal(PerfilPage, datos).present();
    }

    showUpdatePasswordOption(): boolean {
        let user = JSON.parse(localStorage.getItem('usuario'));
        if (user) return user.nombreFuenteRegistro == 'AP';
        else return false;
    }

    updatePassword(): void {
        this.common.createModal(UpdatePasswordPage).present();
    }

    policyModal() {
        this.common.createModal('PolicyPage').present();
    }

    openModal() {
        this.common.createModal('InformationPage').present();
    }

    logout(): void {
        this.logoutState = true;
        this.oneSignal.deleteTag(
            JSON.parse(localStorage.getItem('usuario')).username
        );
        localStorage.removeItem('usuario');
        localStorage.removeItem('username');
        localStorage.removeItem('bearer');

        this.oneSignal.setSubscription(false);
        this.googleMaps.removerMapa();
        this.app.getRootNav().setRoot(LoginPage);
    }
}
