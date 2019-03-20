import { UsuarioModel } from './../../Models/usuario.model';
import { AlertController, LoadingController } from 'ionic-angular';
import { Common } from './../../shared/utilidades/common';
import { Injectable } from '@angular/core';
import { WebService } from './../../provider/webService/ws';
import { CONFIG_ENV } from './../../shared/config-env-service/const-env';

@Injectable()
export class WebServiceLogin {
    respuesta: any;
     header: any;
    constructor(
        private ws: WebService,
        private readonly loadingCtrl: LoadingController,
        private alertCtrl: AlertController,
        public utilidad: Common,
    ) {

    }

    public wsLogin(url: string, data: any): any {
        let loading = this.loadingCtrl.create({
            spinner: 'crescent',
            content: 'Un momento por favor...',
            dismissOnPageChange: true
        });
        loading.present()
        try {
            return new Promise((resolve, reject) => {
                this.ws.url = url;
                this.ws.wsPost(url, data)
                    .subscribe(response => {
                        if (response.status === 200 && response._body.length > 0) {
                            loading.dismiss();
                            this.respuesta = response._body;
                            resolve(this.respuesta);
                        }
                        else {
                            loading.dismiss();
                        }
                    }, error => {
                        loading.dismiss();
                        this.utilidad.generalAlert("Ups!", error._body);
                        this.respuesta = JSON.parse(error);
                        reject(this.respuesta);
                    })
            })
        } catch (error) {
            loading.dismiss();
            this.utilidad.generalAlert("Alert", JSON.stringify(error._body));

        }

    }

    public probarLogin(url: string, usuario: UsuarioModel) {

        this.ws.url = url;
        this.ws.wsPost(null, usuario)

            .subscribe((response) => {

                console.log("RESPONSE  " + response._body);

                let alert = this.alertCtrl.create({
                    title: 'Registro',
                    subTitle: response._body,
                    buttons: [

                        {
                            text: 'Aceptar',
                            handler: () => {
                                console.log('Aceptar REGISTRO');

                            }
                        }
                    ]
                });
                alert.present();

            }, error => {
                console.log("ERROR " + error);
                console.log("ERROR ._Body " + error._body);

            })

    }

    public wsActualizarUsuario(url: string, data: any) {
        this.ws.url = url;
        this.ws.wsPut(null, data)
            .map(res => res.json())
            .subscribe(response => {
                if (response.status.equals("OK") && response._body.length > 0) {
                    this.utilidad.generalAlert("Ups!", JSON.stringify(response._body));
                    return response._body;

                }
            }, error => {
                console.log('Error ' + error);
            })
    }

    public wsRecuperarContrasena(url: string, data: any) {
        this.header = {
            'Content-Type': 'application/json',
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL + url;
            this.ws.wsPut(this.header, data)
                .map(res => res.json())
                .subscribe(response => {
                    if (response.status.equals("OK") && response._body.length > 0) {
                        this.utilidad.generalAlert("Ups!", JSON.stringify(response._body));
                        resolve(response._body);
                        //return response._body;

                    }
                }, error => {
                    resolve(error);
                    console.log('Error ' + error);
                })
        })
    }


}