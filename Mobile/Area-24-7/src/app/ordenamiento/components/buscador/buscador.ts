import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy, trigger, state, style, transition, animate, keyframes } from '@angular/core';
import { toArray } from 'rxjs/operators/toArray';
import { GoogleMaps } from '../../../shared/utilidades/googleMaps';
import { map } from 'rxjs/operators/map';

@Component({
  selector: 'buscador',
  templateUrl: 'buscador.html' //,
  // changeDetection: ChangeDetectionStrategy.OnPush,
  // animations: [trigger('flyInOut', [
  //   state('in', style({
  //     transform: 'translate3d(0, 0, 0)'
  //   })),
  //   state('out', style({
  //     transform: 'translate3d(150%, 0, 0)'
  //   })),
  //   transition('in => out', animate('200ms ease-in')),
  //   transition('out => in', animate('200ms ease-out'))
  // ])]
  // animations: [ trigger('onOffTrigger', [
  // 	state('off', style({
  // 	  backgroundColor: '#E5E7E9',
  // 	  transform: 'scale(1)'
  // 	})),
  // 	state('on',   style({
  // 	  backgroundColor: '#17202A',
  // 	  transform: 'scale(1.1)'
  // 	})),
  // 	transition('off => on', animate('.6s 100ms ease-in')),
  // 	transition('on => off', animate('.7s 100ms ease-out'))
  // ])]

  // animations: [

  //   trigger('flip', [
  //     state('flipped', style({
  //       transform: 'rotate(180deg)',
  //       backgroundColor: '#f50e80'
  //     })),
  //     transition('* => flipped', animate('400ms ease'))
  //   ]),

  //   trigger('flyInOut', [
  //     state('in', style({
  //       transform: 'translate3d(0, 0, 0)'
  //     })),
  //     state('out', style({
  //       transform: 'translate3d(150%, 0, 0)'
  //     })),
  //     transition('in => out', animate('200ms ease-in')),
  //     transition('out => in', animate('200ms ease-out'))
  //   ]),

  //   trigger('fade', [
  //     state('visible', style({
  //       opacity: 1
  //     })),
  //     state('invisible', style({
  //       opacity: 0.1
  //     })),
  //     transition('visible <=> invisible', animate('200ms linear'))
  //   ]) //,

    // trigger('bounce', [
    //   state('bouncing', style({
    //     transform: 'translate3d(0,0,0)'
    //   })),
    //   transition('* => bouncing', [
    //     animate('300ms ease-in', keyframes([
    //       style({ transform: 'translate3d(0,0,0)', offset: 0 }),
    //       style({ transform: 'translate3d(0,-10px,0)', offset: 0.5 }),
    //       style({ transform: 'translate3d(0,0,0)', offset: 1 })
    //     ]))
      // ])
    // ])

  //]

  // animations: [
 
  //   trigger('flip', [
  //     state('flipped', style({
  //       transform: 'rotate(180deg)',
  //       backgroundColor: '#f50e80'
  //     })),
  //     transition('* => flipped', animate('400ms ease'))
  //   ]),
 
  //   trigger('flyInOut', [
  //     state('in', style({
  //       transform: 'translate3d(0, 0, 0)'
  //     })),
  //     state('out', style({
  //       transform: 'translate3d(150%, 0, 0)'
  //     })),
  //     transition('in => out', animate('12000ms ease-in')),
  //     transition('out => in', animate('200ms ease-out'))
  //   ]),
 
  //   trigger('fade', [
  //     state('visible', style({
  //       opacity: 1
  //     })),
  //     state('invisible', style({
  //       opacity: 0.1
  //     })),
  //     transition('visible <=> invisible', animate('200ms linear'))
  //   ]),
 
  //   trigger('bounce', [
  //     state('bouncing', style({
  //       transform: 'translate3d(0,0,0)'
  //     })),
  //     transition('* => bouncing', [
  //       animate('300ms ease-in', keyframes([
  //         style({transform: 'translate3d(0,0,0)', offset: 0}),
  //         style({transform: 'translate3d(0,-10px,0)', offset: 0.5}),
  //         style({transform: 'translate3d(0,0,0)', offset: 1})
  //       ]))
  //     ])
  //   ])
 
  // ]
})
export class BuscadorComponent {

  //@Input()
  listaEquipamentos: any;
  nuevaLista: any;
  nuevaListaMapa: any;

  @Output()
  listaFiltrados = new EventEmitter<any>();


  getItems(ev: any) {

    let val = ev.target.value;

    this.nuevaLista = JSON.parse(JSON.stringify(this.listaEquipamentos));
    this.nuevaListaMapa = this.map.ListaMarcadores;
    let objetos: any[] = [];
    let objetosMarcadores: any[] = [];

    console.log("objeto", Object);
    console.log("busqueda ||" + ev.target.value);

    if (val && val.trim() != '') {
      this.nuevaLista['categorias'].map((item) => {
        if (item.nombre.toString().toLowerCase().indexOf(val.toLowerCase()) > -1) {
          objetos.push(item)
        }
      });

      if (this.map.ListaMarcadores) {

        Object.keys(this.nuevaListaMapa).forEach((item, index) => {
          Object.keys(this.nuevaListaMapa[item]).forEach(i => {
            console.log('COLECCION ', this.nuevaListaMapa[item][i]);
            this.nuevaListaMapa[item][i].forEach(element => {
              if (element.nombre.toString().toLowerCase().indexOf(val.toLowerCase()) > -1) {
                objetosMarcadores.push(element)
              }
            });


          })

        });
      }
      console.log("lista encontrada ", this.nuevaLista);
      if (objetos) {
        this.nuevaLista['categorias'] = objetos;
      }
      if (objetosMarcadores) {
        this.nuevaListaMapa = objetosMarcadores;
        console.log("lista encontrada mapa", this.nuevaListaMapa);
      }

      this.listaFiltrados.emit(this.nuevaLista);
    }
    else {
      this.listaFiltrados.emit(undefined);
      this.nuevaListaMapa = [];
    }

  }

  constructor(public map: GoogleMaps) {
  }

}
