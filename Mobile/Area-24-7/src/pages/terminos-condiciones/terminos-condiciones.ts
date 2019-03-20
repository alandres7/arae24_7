import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

@IonicPage()
@Component({
    selector: 'page-terminos-condiciones',
    templateUrl: 'terminos-condiciones.html',
})
export class TerminosCondicionesPage {

    private acceptButtons = false;

    constructor(public navCtrl: NavController, 
                public navParams: NavParams, 
                private common: Common
    ) {
        this.acceptButtons = this.navParams.get('acceptButtons');

    }

    ionViewDidLoad() {
        console.log('ionViewDidLoad TerminosCondicionesPage');
    }

    closeModal(acceptTerms: boolean) {
        this.common.dismissModal(acceptTerms);
    }

    goBack() {
        this.common.dismissModal();
    }
}