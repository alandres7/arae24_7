import { Component, Input } from '@angular/core';

@Component({
  selector: 'barra-navegabilidad',
  templateUrl: 'barra-navegabilidad.component.html',
})
export class BarraNavegabilidad {

  @Input() titulo: string;

  constructor(){}

}