import { Component, Input } from '@angular/core';
import { Common } from '../../app/shared/utilidades/common';
@Component({
  selector: 'menu-modal',
  templateUrl: 'menu-modal.html'
})
export class MenuModalComponent {
  @Input() titulo: string;

  constructor(private common: Common) {
    console.log('Hello MenuModalComponent Component');
  }

  closeModal(event:any){
    this.common.dismissModal({})
  }

}
