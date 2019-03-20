import { IonicPage, NavParams, Slides } from 'ionic-angular';
import { Component, ViewChild } from '@angular/core';
import { Common } from '../../../shared/utilidades/common';

//@IonicPage()

@Component({
    selector: 'modal-huellas-page',
    templateUrl: 'modal-huellas-page.html',
    styles: ['modal-huellas-page.scss'],
})
export class ModalHuellasPage {

    public data: any = {
        'texto': ' ',
        'imagen': 'assets/menu/menu.svg',
        'color': ' '
    };
    optionSelect = {};
    @ViewChild(Slides) slides: Slides;
    ultimoSlide = false;
    mostrarAnterior = false;
    resultado = false;
    encuesta = false;
    constructor(private navParams: NavParams
              , private common: Common) {

    }

    ionViewWillEnter() {
        this.data = this.navParams.data;
        //if(this.data.length > 0){

        document.getElementById('bar-modal').style.backgroundColor = this.data.color;
        document.getElementById('img-modal').style.borderColor = this.data.color;
        document.getElementById('img-modal').style.backgroundColor = this.data.color;
        document.getElementById('texto-modal').style.color = this.data.color; //.style.color = this.data.color;
        //}


    }


    // Metodo para hacer validacion en cambio de slide
    slideChanged() {
        let currentIndex = this.slides.getActiveIndex();
        this.ultimoSlide = this.slides.isEnd();
        if (currentIndex == 0) {
            this.mostrarAnterior = false;
        }
        else {
            this.mostrarAnterior = true;
        }
    }

    closeModal() {
        this.common.dismissModal();
    }

    enviar() {
        this.resultado = true;

    }

    // charts 

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true
    };

    //Chart Labels
    public barChartLabels: string[] = ['Promedio'];
    public barChartType: string = 'bar';
    public barChartLegend: boolean = true;

    //Chart data
    public barChartData: any[] = [
        { data: [6.3+2.7], label: 'Tu Huella' },
        { data: [6.5], label: 'Huella Regional' },
        { data: [6.8], label: 'Huella Departamental' }
    ];

    // Chart events
    public chartClicked(e: any): void {
        console.log(e);
    }

    // Chart events
    public chartHovered(e: any): void {
        console.log(e);
    }

    public lineChartColors: Array<any> = [
        { // rojo
            backgroundColor: 'rgba(215, 40, 40,0.8)',
            borderColor: 'rgba(215, 40, 40,1)',
            pointBackgroundColor: 'rgba(215, 40, 40,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(215, 40, 40,0.8)'
        },
        { // amarillo
            backgroundColor: 'rgba(215, 255, 44,0.8)',
            borderColor: 'rgba(215, 255, 44,1)',
            pointBackgroundColor: 'rgba(215, 255, 44,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(215, 255, 44,1)'
        },
        { // verde fofo
            backgroundColor: 'rgba(19, 255, 154,0.8)',
            borderColor: 'rgba(19, 255, 154,1)',
            pointBackgroundColor: 'rgba(19, 255, 154,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(19, 255, 154,0.8)'
        },
        { // verde
            backgroundColor: 'rgba(5, 85, 90,0.8)',
            borderColor: 'rgba(5, 85, 90,1)',
            pointBackgroundColor: 'rgba(5, 85, 90,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(5, 85, 90,0.8)'
        },
        { // azul
            backgroundColor: 'rgba(5, 133, 130,0.8)',
            borderColor: 'rgba(5, 133, 130,1)',
            pointBackgroundColor: 'rgba(5, 133, 130,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(5, 133, 130,0.8)'
        }
    ];


}