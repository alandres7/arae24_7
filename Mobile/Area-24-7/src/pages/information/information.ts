import { Component } from '@angular/core';
import { IonicPage,  NavParams } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

@IonicPage()
@Component({
  selector: 'page-information',
  templateUrl: 'information.html',
})
export class InformationPage {

    constructor(private common: Common) {}

    goBack(): void {
        this.common.dismissModal();
    }
}
