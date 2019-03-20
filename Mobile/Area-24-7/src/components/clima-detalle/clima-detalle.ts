import { Component, OnInit } from '@angular/core';
import { NavParams } from 'ionic-angular';

import { EstacionDisfrutameProvider } from '../../providers/estacion-disfrutame/estacion-disfrutame';

@Component({
    selector: 'clima-detalle',
    templateUrl: 'clima-detalle.html',
})
export class ClimaDetalleComponent implements OnInit {
    ventana;
    data;
    derp = ' Medellin ';
    private color: string;
    private currentMonth: string;
    private currentDay: number;
    private monthAndDay: string;
    private afterDay: string;

    private nombreEstacion: string = '';

    private months = ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'];

    constructor(
        private navParams: NavParams,
        private estacionDisfrutameProvider: EstacionDisfrutameProvider,
    ) {
        this.ventana = 'actual';
        this.color = navParams.get('color');

        let markerId: number = this.navParams.get('polygonId');
        this.estacionDisfrutameProvider.getClima(markerId).subscribe(data => {
            this.data = data;

            if (data.municipio == "MEDELLIN") {
                data.municipio = "Medellín";
                this.nombreEstacion = this.parseStationName(data.nombreEstacion);
            }
        
        });

        this.getMonthAndDay();
    }

    ngOnInit() {
        function Ctrl($scope) 
        { 
            $scope.date = new Date();
        }
    }

    parseStationName(estacion: string): string {

        let nombre = estacion;
        nombre = nombre.replace('Medellin ', ', ');

        if (estacion === 'Santa Elena') {
            nombre = nombre.replace('Santa Elena', ', Santa Elena');   
        }

        return nombre
    }

    // if day == false is currentDay, else is the day after currentDay
    getMonthAndDay(): void {
        let currentDate = new Date();
        let month = currentDate.getMonth();
        this.currentMonth = this.months[month];
        this.currentDay = currentDate.getDate();

        this.monthAndDay = this.currentMonth + ' ' + this.currentDay;
        this.afterDay =  this.currentMonth + ' ' + this.getDay();

    }

    getDay(): number {
        let afterDate = new Date();
        afterDate.setDate(afterDate.getDate() + 1);
        this.currentDay = afterDate.getDate();
        
        return this.currentDay
    }
}
