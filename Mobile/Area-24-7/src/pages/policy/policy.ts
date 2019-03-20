import { Component } from '@angular/core';
import { IonicPage } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';

@IonicPage()
@Component({
  selector: 'page-policy',
  templateUrl: 'policy.html',
})
export class PolicyPage {

    constructor(private common: Common) {
    }
    
    goBack(): void {
        this.common.dismissModal();
    }
}
