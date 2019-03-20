import { Injectable, ViewChild } from '@angular/core';
import { UsuarioModel } from './../../Models/usuario.model';
import { WebService } from './../../provider/webService/ws';
import { AlertController, LoadingController, App } from 'ionic-angular';
import { LoginPage } from './../../login/pages/login-page/login-page';
import { FormRegister } from "../pages/form-register";
import { Amva_247_App } from "../../app.component";
import { CONFIG_ENV } from './../../shared/config-env-service/const-env';



@Injectable()
export class WebServiceRegistro {
    nav: any;
    header: any;

    constructor(private ws: WebService,
        private alertCtrl: AlertController,
        private readonly loadingCtrl: LoadingController,
        private app: App,

    ) {
        this.nav = this.app.getRootNav();

    }

    public usuarioRegistrar(url: string, usuario: UsuarioModel, ) {
        this.ws.url = url;
        this.ws.wsPost(null, usuario).subscribe((response) => {

        })
    }
    presentAlert() {
        let alert = this.alertCtrl.create({
            title: 'Low battery',
            subTitle: '10% of battery remaining',
            buttons: ['Dismiss']
        });
        alert.present();
    }
    public registrar(url: string, usuario: UsuarioModel) {
        let respuesa: any;
        let loading = this.loadingCtrl.create({
            content: 'Un momento por favor...'
        });

        loading.present()
        return new Promise((resolve, reject) => {
            this.ws.url = url;
            this.ws.wsPost(null, usuario)

                .subscribe((response) => {
                    loading.dismiss();

                    let alert = this.alertCtrl.create({
                        title: 'Registro',
                        subTitle: response._body,
                        buttons: [

                            {
                                text: 'Aceptar',
                                handler: () => {
                                    response = 'exitoso';
                                    resolve(response);
                                    //this.nav.setRoot(LoginPage);
                                }
                            }
                        ]
                    });
                    alert.present();

                }, error => {
                    console.log("error", error._body);
                    let erroSubTitle = error._body;
                    let subTitle = 'Se ha perdido conexión con servidor, intentelo más tarde.';
                    if (erroSubTitle == 'Conflicto. El usuario ya existe') {
                        subTitle = 'El usuario ya existe';
                    }
                    loading.dismiss();
                    let alertError = this.alertCtrl.create({
                        title: 'Info',
                        subTitle: subTitle,
                        buttons: [

                            {
                                text: 'Aceptar',
                                handler: () => {
                                    error = 'error';
                                    resolve(error);
                                    //this.nav.setRoot(LoginPage);
                                }
                            }
                        ]
                    });
                    alertError.present();

                })
        })

    }

    public registrarRedes(url: string, usuario: UsuarioModel) {
        let loading = this.loadingCtrl.create({
            content: 'Un momento por favor...'
        });
        loading.present()
        return new Promise((resolve, reject) => {
            this.ws.url = url;
            this.ws.wsPost(null, usuario)

                .subscribe((response) => {
                    loading.dismiss();
                    resolve(response);

                }, error => {
                    resolve(error);
                    loading.dismiss();
                    let alertError = this.alertCtrl.create({
                        title: 'Info',
                        subTitle: 'Se ha perdido conexión con servidor, intentelo más tarde.',
                        buttons: [

                            {
                                text: 'Aceptar',
                                handler: () => {

                                }
                            }
                        ]
                    });
                    alertError.present();

                });

        })





    }

    public probarLogin2(url: string) {
        this.ws.url = url;
        var usuario = {
            "contrasena": "string",
            "fuenteRegistro": "AP",
            "username": "string@ada.co"
        };
        this.ws.solicitar('post', usuario);
    }




    public obtenerTodoslosPaises() {
        this.header = {
            'Content-Type': 'application/json',
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/paises')
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                    (err) => {
                        console.log('error al cargar la aplicacion', err);
                        reject(err);
                    });
        });
    }
}