import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { PosiblesViajesProvider } from '../../app/shared/posibles-viajes.service';
import { Common } from '../../app/shared/utilidades/common';
import { VistaViajesPage } from '../../app/movilidad/pages/vista-viajes/vista-viajes.page';
import { LayerProvider } from "../../providers/layer/layer";

@Component({
  selector: 'territorio-detail',
  templateUrl: 'territorio-detail.html'
})
export class TerritorioDetailComponent {

    private info: any;
    private nombreCapa: string;
    private iconoCapa: string;
    private currentApp;

    constructor(private navCtrl: NavController
              , private navParams: NavParams
              , private posiblesViajesProvider: PosiblesViajesProvider
              , private common: Common
              , private layerProvider: LayerProvider) 
        
    {
        this.info = navParams.get('info');
        this.nombreCapa = navParams.get('nombreCapa');
        this.iconoCapa = navParams.get('iconoCapa');
    }

    ngOnInit(): void {
        this.layerProvider.currentApp.subscribe(app => {
          this.currentApp = app;
        });

        console.log('navId from territorio-detail ' + this.navCtrl.id);
    }

    goToMovilidad(): void {
        this.posiblesViajesProvider
            .obtenerviajesSugeridos()
            .then(data => {
                this.navCtrl.push(VistaViajesPage, data);
            })
            .catch(error => {
                this.common.basicAlert('Movilidad', error);
            });
    }

    closeModal(): void {
        this.common.dismissModal();
    }
}
