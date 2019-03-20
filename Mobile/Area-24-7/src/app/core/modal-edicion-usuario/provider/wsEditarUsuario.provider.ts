import { Injectable, ViewChild } from '@angular/core';
import { UsuarioModel } from './../../../Models/usuario.model';
import { WebService } from './../../../provider/webService/ws';
import { AlertController, LoadingController, App } from 'ionic-angular';
import { LoginPage } from './../../../login/pages/login-page/login-page';



@Injectable()
export class WebServiceEditarUsuario {
    nav: any;
    //view: any;

    constructor(private ws: WebService,
        private alertCtrl: AlertController,
        private readonly loadingCtrl: LoadingController,
        private app: App

    ) {
        this.nav = this.app.getRootNav();
        
    }

    
    public actualizar(url: string, usuario: UsuarioModel) {
        let header = {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem("bearer")
        };
        
        let loading = this.loadingCtrl.create({
            content: 'Un momento por favor...'
        });

        loading.present()
        this.ws.url = url;
        this.ws.wsPut(header, usuario)

            .subscribe((response) => {
                loading.dismiss();

                let alert = this.alertCtrl.create({
                    title: 'Registro',
                    subTitle: response._body,
                    buttons: [

                        {
                            text: 'Aceptar',
                            handler: () => {
                                
                                //this.view.dismiss();
                                this.nav.push(LoginPage);
                            }
                        }
                    ]
                });
                alert.present();

            }, error => {
                loading.dismiss();
                let alertError = this.alertCtrl.create({
                    title: 'Info',
                    subTitle: 'Se ha perdido conexión con servidor, intentelo más tarde.',
                    buttons: [

                        {
                            text: 'Aceptar',
                            handler: () => {
                                //this.nav.setRoot(LoginPage);
                            }
                        }
                    ]
                });
                alertError.present();

            })

    }

}
