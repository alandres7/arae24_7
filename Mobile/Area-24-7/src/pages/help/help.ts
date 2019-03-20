import { Component } from '@angular/core';
import { IonicPage, NavParams } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

@IonicPage()
@Component({
  selector: 'page-help',
  templateUrl: 'help.html',
})
export class HelpPage {

    constructor(private common: Common)
    {}

    goBack(): void {
        this.common.dismissModal();
    }
}
