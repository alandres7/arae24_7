import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { WebServiceRegistro } from './../../../registro/provider/wsRegistro.provider';
import { UsuarioModel } from './../../../Models/usuario.model';
import { CONFIG_ENV } from './../../../shared/config-env-service/const-env';
import { Component, Inject, OnInit } from '@angular/core';
import {
    NavController,
    NavParams,
    Alert,
    AlertOptions,
    AlertController,
    Modal,
    MenuController,
    ToastController,
    LoadingController,
    Platform,
    Loading
} from 'ionic-angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WebServiceLogin } from './../../provider/wsLogin';
import { FormRegister } from '../../../registro/pages/form-register';
import { Common } from '../../../shared/utilidades/common';
import { WebService } from '../../../provider/webService/ws';
import { Facebook, FacebookLoginResponse } from '@ionic-native/facebook';
import { GooglePlus } from '@ionic-native/google-plus';
import { Device } from '@ionic-native/device';
import { AppDiagnostic } from '../../../shared/utilidades/diagnostic';
import { GoogleMaps } from '../../../shared/utilidades/googleMaps';

import { AuthenticationProvider } from '../../../../providers/authentication/authentication';

import { User, PASSWORD_WILDCARD } from '../../../../entities/user';

import { FUENTES_REGISTRO } from '../../../shared/fuentes-registro';
import { RegistroPage } from '../../../../pages/registro/registro';
import { MenuMainPage } from '../../../../pages/menu-main/menu-main';
import { InicioPage } from '../../../inicio/pages/inicio-page/inicio-page';
import { TerminosCondicionesPage } from '../../../../pages/terminos-condiciones/terminos-condiciones';
import { MessageProvider } from '../../../../providers/message/message';

// import { TermsConditionsPage } from '../../../../pages/terms-conditions/terms-conditions';

@Component({
    selector: 'login-page',
    templateUrl: 'login-page.html',
    styles: ['login-page.scss']
})
export class LoginPage {
    static readonly MSG_FACEBOOK_NOT_AVAILABLE: string =
        'El Servicio de Facebook no se encuentra disponible.';
    static readonly MSG_GOOGLE_NOT_AVAILABLE: string =
        'El servicio de Google no se encuentra disponible';

    private FACEBOOK_PERMISSIONS: string[] = ['public_profile', 'email'];
    private formGroup: FormGroup;

    ionViewDidLoad(): void {
        //this.gps.checkLocation();
        //this.gps.displayLocationDialog();

        this.tryAutoLogin();
    }

    constructor(
        private navCtrl: NavController,
        private loadingCtrl: LoadingController,
        private menu: MenuController,
        private googleMaps: GoogleMaps,
        private formBuilder: FormBuilder,
        private utilidades: Common,
        private facebook: Facebook,
        private googlePlus: GooglePlus,
        private common: Common,
        private gps: AppDiagnostic,
        private alertCtrl: AlertController,
        private authProvider: AuthenticationProvider,
        private messageProvider: MessageProvider
    ) {
        this.menu.enable(false);

        this.formGroup = formBuilder.group({
            username: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(255)
                ])
            ],
            contrasena: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(8),
                    Validators.maxLength(255)
                ])
            ]
        });
    }

    loginWithFacebook(): void {
        this.common.presentFullLoading();
        this.facebook
            .getLoginStatus()
            .then(response => {
                console.log(LoginPage.name + ' loginWithFacebook getLoginStatus ' + JSON.stringify(response));
                switch (response.status) {
                    case 'connected':
                        this.registerWithFacebook();
                        break;
                    // case 'unknown':
                    //     this.presentAcceptAlert('facebook status unknown');
                    //     this.showDialogFacebook();
                    //     this.loading.dismiss();
                    //     break;
                    default:
                        this.facebook
                            .login(this.FACEBOOK_PERMISSIONS)
                            .then((res: FacebookLoginResponse) => {
                                console.log(LoginPage.name + ' loginWithFacebook login ' + JSON.stringify(res));
                                this.registerWithFacebook();
                            })
                            .catch(error => {
                                console.log(LoginPage.name + 'loginWithFacebook login error ' + JSON.stringify(error));
                                this.common.presentLoading();
                                this.messageProvider.getByNombreIdentificador('Login->facebook').subscribe(
                                    (response: Response): void => {
                                        console.log(LoginPage.name + 'loginWithFacebook login error getByNombreIdentificador ' + JSON.stringify(response));
                                        this.common.dismissLoading();
                                    },
                                    (error: any): any => {
                                        console.log(LoginPage.name + 'loginWithFacebook login error getByNombreIdentificador error' + JSON.stringify(error));
                                    }
                                );
                            });
                        break;
                }
            })
            .catch(error => {
                console.log(LoginPage.name + 'loginWithFacebook getLoginStatus error ' + JSON.stringify(error));
                this.common.presentLoading();
                this.messageProvider.getByNombreIdentificador('Login->facebook').subscribe(
                    (response: Response): void => {
                        console.log(LoginPage.name + 'loginWithFacebook login error getByNombreIdentificador ' + JSON.stringify(response));
                        this.common.dismissLoading();
                    },
                    (error: any): any => {
                        console.log(LoginPage.name + 'loginWithFacebook login error getByNombreIdentificador error' + JSON.stringify(error));
                    }
                );
            });
    }

    showDialogFacebook(): void {
        this.facebook.showDialog({
            method: 'login',
            href: '',
            caption: ''
        });
    }

    loginWithForm(): void {
        this.common.presentFullLoading();
        let user: User = new User();
        user.username = this.formGroup.value.username.toLowerCase();
        user.password = this.formGroup.value.contrasena;
        user.registrySource = FUENTES_REGISTRO.MOBILE_APP;
        this.formGroup.reset();
        this.loginWithApi(user);
    }

    registerWithFacebook(): void {
        let url: string = '/me?fields=email,first_name,last_name,gender,locale,age_range';
        this.facebook
            .api(url, this.FACEBOOK_PERMISSIONS)
            .then(response => {
                console.log(LoginPage.name + ' registerWithFacebook ' + JSON.stringify(response));
                let user: User = User.parseFromFacebook(response);
                user.password = PASSWORD_WILDCARD;
                user.registrySource = FUENTES_REGISTRO.FACEBOOK;

                //Busca si ya esta registrado
                this.authProvider.existUser(user.email).subscribe( (response: Response) => {
                    if (JSON.parse(response.text())) {
                        this.loginWithApi(user);
                    } else {
                        this.enrollWithApiCheckTermsConditions(user);
                    }
                });
            })
            .catch(e => {
                console.log(LoginPage.name + ' registerWithFacebook error ' + JSON.stringify(e));
                this.common.presentLoading();
                this.messageProvider.getByNombreIdentificador('Login->facebook').subscribe(
                    (response: Response): void => {
                        console.log(LoginPage.name + 'loginWithFacebook login error getByNombreIdentificador ' + JSON.stringify(response));
                        this.common.dismissLoading();
                    },
                    (error: any): any => {
                        console.log(LoginPage.name + 'loginWithFacebook login error getByNombreIdentificador error' + JSON.stringify(error));
                    }
                );
            });
    }

    logoutFromFacebook(): void {
        this.facebook
            .logout()
            .then(res =>
                console.log('logoutFromFacebook ' + JSON.stringify(res))
            )
            .catch(e =>
                console.log('logoutFromFacebook error' + JSON.stringify(e))
            );
    }

    loginWithGoogle(): void {
        this.common.presentFullLoading();
        this.googlePlus
            .login({
                //  webClientId: '159728882731-c0sil7mael77eetgvnojsjfgmehbhmd4.apps.googleusercontent.com'
            })
            .then(response => {
                let user: User = User.parseFromGoogle(response);
                user.password = PASSWORD_WILDCARD;
                user.registrySource = FUENTES_REGISTRO.GOOGLE_PLUS;
                user.termsConditions = false;
                console.log('user ' + JSON.stringify(user));

                //Busca si ya esta registrado
                this.authProvider.existUser(user.email).subscribe( (response: Response) => {
                    console.debug('-----DEBUG-----', 'existe Usuario', response.text());

                    if (JSON.parse(response.text())) {
                        this.loginWithApi(user);
                    } else {
                        this.enrollWithApiCheckTermsConditions(user);
                    }
                    
                });
            })
            .catch(error => {
                console.log(LoginPage.name + ' loginWithGoogle error ' + JSON.stringify(error));
                this.common.presentLoading();
                this.messageProvider.getByNombreIdentificador('Login->google').subscribe(
                    (response: Response): void => {
                        console.log(LoginPage.name + 'loginWithGoogle login error getByNombreIdentificador ' + JSON.stringify(response));
                        this.common.dismissLoading();
                    },
                    (error: any): any => {
                        console.log(LoginPage.name + 'loginWithGoogle login error getByNombreIdentificador error' + JSON.stringify(error));
                    }
                );
            });
    }

    logoutFromGoogle(): void {
        this.googlePlus
            .logout()
            .then(response =>
                console.log('logoutFromgoogle' + JSON.stringify(response))
            )
            .catch(error =>
                console.log('logoutFromgoogle error' + JSON.stringify(error))
            );
    }

    enrollWithApiCheckTermsConditions(user: User): void {
            this.common.dismissLoading();
            let modal: Modal = this.common.createModal('TerminosCondicionesPage', {'acceptButtons': true});
            modal.onDidDismiss((aceptaTerminos) => {
                if (aceptaTerminos) {
                    user.termsConditions = aceptaTerminos;
                    this.enrollWithApi(user);
                }
             });
             modal.present();
    }

    enrollWithApi(user: User): void {
        this.authProvider.enroll(user).subscribe(
            (response: Response) => {
                console.log(LoginPage.name + ' enrollWithApi ' + JSON.stringify(response));
                let msg = response.json();
                this.common.presentAcceptAlert(msg.titulo, msg.descripcion, () => this.common.dismissModal());
                this.loginWithApi(user);
            },
            error => {
                console.log(LoginPage.name + ' enrollWithApi error ' + JSON.stringify(error));
            }
        );
    }

    loginWithApi(user: User): void {
        this.authProvider.login(user).subscribe(
            (response: Response) => {
                console.log(LoginPage.name + ' loginWithApi ' + JSON.stringify(response));
                this.persistLogin(JSON.stringify(response.json()), user.password);
                this.navCtrl.setRoot(InicioPage);
                this.common.dismissLoading();
            },
            error => {
                console.log(LoginPage.name + ' loginWithApi error ' + JSON.stringify(error));
            }
        );
    }

    persistLogin(json: string, password: string): void {
        let data = JSON.parse(json);
        localStorage.setItem('bearer', data.token);
        localStorage.setItem('username', data.usuario.username);

        console.log('response persist login' + JSON.stringify(json));

        if (json.indexOf('Bearer') > -1 && json.indexOf('usuario') > -1) {
            data.usuario.contrasena = password;
            localStorage.setItem('usuario', JSON.stringify(data.usuario));
        }
    }

    tryAutoLogin(): void {
        let pusuario: any = JSON.parse(localStorage.getItem('usuario'));
        if (pusuario !== null && pusuario !== undefined) {
            this.common.presentFullLoading();
            console.log('usuario: ', pusuario);

            let user: User = new User();
            user.username = pusuario.username;
            user.password = pusuario.contrasena;
            user.registrySource = pusuario.nombreFuenteRegistro;
            this.loginWithApi(user);
        }
    }

    enrollWithForm(): void {
        this.common.createModal(RegistroPage).present();
    }

    restorePasswordDialog(): void {
        let options: AlertOptions = {
            title: 'Recuperación de contraseña',
            message:
                'Ingrese su correo para recibir una nueva contraseña',
            inputs: [
                {
                    name: 'email',
                    placeholder: 'Correo',
                    type: 'email'
                }
            ],
            buttons: [
                {
                    text: 'Cancelar',
                    role: 'cancel'
                },
                {
                    text: 'Enviar',
                    handler: emailObject =>
                        this.restorePassword(emailObject.email)
                }
            ]
        };
        this.alertCtrl.create(options).present();
    }

    restorePassword(email: string): void {
        this.common.presentLoading();
        this.authProvider.restore(email).subscribe(
            (response: Response) => {
                console.log(LoginPage.name + ' restorePassword ' + JSON.stringify(response));
                this.common.dismissLoading();
                let msg = response.json();
                this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
            },
            (error: any) => {
                console.log(LoginPage.name + ' restorePassword error ' + JSON.stringify(error));
            }
        );
    }

    terminosCondiciones() {
        let modal: Modal = this.common.createModal('TerminosCondicionesPage');
        modal.present();
    }
}