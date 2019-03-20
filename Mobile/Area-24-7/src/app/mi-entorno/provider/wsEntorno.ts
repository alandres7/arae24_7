import { CONFIG_ENV } from './../../shared/config-env-service/const-env';
import { Common } from './../../shared/utilidades/common';
import { Injectable } from '@angular/core';
import { WebService } from './../../provider/webService/ws';

@Injectable()
export class WsEntorno {
header: any;

    constructor(private ws: WebService, private utilidad: Common) {

    }

    public obtenerCapas(token, data){
        let loading = this.utilidad.httpLoading('Obteniendo información');
        loading.present();
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': token
        };
        return new Promise((resolve, reject) => {
         this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/capa/'+data)
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    this.utilidad.generalAlert('Info', 'Se ha perdido conexión con servidor, intentelo más tarde!');
                    reject(err);
                }, ()=>{
                    loading.dismiss();
                }
            )
        });
    }

    public obtenerPuntos(token, item){
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': token
        };
        return new Promise((resolve, reject) => {
         this.ws.url = CONFIG_ENV.REST_BASE_URL+'';
            this.ws.wsGet(this.header, '')
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    this.utilidad.generalAlert('Info', 'Se ha perdido conexión con servidor, intentelo más tarde!');
                    reject(err);
                })
        });
    }

    public obtenerCapasRadioAccion(token, data){
        let loading = this.utilidad.httpLoading('Obteniendo información');
        loading.present();
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': token
        };
        return new Promise((resolve, reject) => {
         this.ws.url = CONFIG_ENV.REST_BASE_URL;
         var objeto = this.ws.wsGet(this.header, '/api/capa/radioAccion/'+data)
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    this.utilidad.generalAlert('Info', 'Se ha perdido conexión con servidor, intentelo más tarde!' );
                    reject(err);
                },  () => {objeto.unsubscribe(); loading.dismiss();})
        });
    }
}