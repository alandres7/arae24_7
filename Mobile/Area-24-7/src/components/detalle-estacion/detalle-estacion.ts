import { Component, OnInit } from '@angular/core';
import { NavParams, ModalController } from 'ionic-angular';

import { EstacionDisfrutameProvider } from '../../providers/estacion-disfrutame/estacion-disfrutame';
import { Common } from '../../app/shared/utilidades/common';
import { LayerProvider } from "../../providers/layer/layer";

@Component({
    selector: 'detalle-estacion',
    templateUrl: 'detalle-estacion.html',
})
export class DetalleEstacionComponent implements OnInit {

    icono = '';
    color: string;
    toolbar: string;
    nombre: string;
    data;

    colorClass: string;

    recomendaciones: Array<any>;
    interpretacion: string;
    aireDescripcion: Array<any>;

    detalleItems;

    constructor(
        private estacionDisfrutameProvider: EstacionDisfrutameProvider,
        private navParams: NavParams,
        private common: Common
    ) { }

    ngOnInit() {

        let markerId: number = this.navParams.get('markerId');
        console.log('MarkerID ' + markerId);

        this.estacionDisfrutameProvider.getDetail(markerId).subscribe(data => {
            this.data = data;

            this.detalleItems = [];
            this.recomendaciones = [];

            this.data.forEach((element, index) => {
                let key = Object.keys(element);
                console.log(key, element[key[0]]);

                if (key[0] === 'icono') {
                    this.icono = element[key[0]];
                } else if (key[0] === 'estado') {
                    this.color = element[key[0]];
                } else if (key[0] === 'ListaRecomendaciones') {
                    element[key[0]].forEach((childElement) => {
                        this.recomendaciones.push(childElement);
                    });

                } else if (element[key[0]] !== '') {
                    this.detalleItems.push(this.parser(JSON.stringify(element)));

                    if(key[0] === 'nombre') {
                        this.nombre = this.parseName(element[key[0]])
                    } else if(key[0] === 'descripcionAire') {
                        this.parseAireDescripcion(element[key[0]])
                        
                    } else if(key[0] === 'interpretacion') {
                        this.interpretacion = element[key[0]]
                    }              
                }
            });

        });
    }
 
    parseAireDescripcion(descripcion: string): void {

        this.colorClass = (descripcion == 'No aplica normatividad') ? 'Black' : 'White';
        console.log('The color to render in DOM is -- ' + this.colorClass);

        this.aireDescripcion = [];

        let nameStr = descripcion.split(' | ');

        nameStr.forEach((element) => {
            this.aireDescripcion.push(element);
        });
    }

    parseName(estacion: string): string {

        let nameStr;
        let nameToShow: string;

        nameStr = estacion.split(' - ');
        nameToShow = nameStr[nameStr.length - 1];

        return nameToShow

    }

    parser(element: string) {
        let salida = element;

        salida = salida.replace('{', '');
        salida = salida.replace('}', '');
        salida = salida.replace(':', ': ');
        while (salida.indexOf('"') > -1) {
            salida = salida.replace('"', '');
        }
        return salida;
    }

    closeModal(): void {
        this.common.dismissModal();
    }
}
