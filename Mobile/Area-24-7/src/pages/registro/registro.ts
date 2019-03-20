import { Component, OnInit } from '@angular/core';
import {
    IonicPage,
    NavController,
    NavParams,
    AlertController,
    Modal
} from 'ionic-angular';
import {
    FormGroup,
    FormBuilder,
    Validators,
    AbstractControl
} from '@angular/forms';
import { Common } from '../../app/shared/utilidades/common';
import { User } from '../../entities/user';
import { AuthenticationProvider } from '../../providers/authentication/authentication';
import { Response } from '@angular/http';
import { FUENTES_REGISTRO } from '../../app/shared/fuentes-registro';
import { LoginPage } from '../../app/login/pages/login-page/login-page';
import { TerminosCondicionesPage } from '../terminos-condiciones/terminos-condiciones';
import { App } from 'ionic-angular/components/app/app';

@IonicPage()
@Component({
    selector: 'page-registro',
    templateUrl: 'registro.html'
})
export class RegistroPage implements OnInit {
    formGroup: FormGroup;

    constructor(
        public navCtrl: NavController,
        public navParams: NavParams,
        public formBuilder: FormBuilder,
        public common: Common,
        public authProvider: AuthenticationProvider,
        private app: App,
        public alertCtrl: AlertController) 
    {
        this.formGroup = this.createFormGroup();
    }

    ngOnInit(): void {}

    enrollWithForm(): void {
        this.common.presentLoading();
        let user: User = new User();
        user.username = this.formGroup.value.username.toLowerCase();
        user.email = this.formGroup.value.email;
        user.phone = this.formGroup.value.phone;
        user.password = this.formGroup.value.matchingPasswords.password;
        user.termsConditions = this.formGroup.value.termsConditions;
        user.registrySource = FUENTES_REGISTRO.MOBILE_APP;
        this.enrollWithApi(user);
    }

    enrollWithApi(user: User): void {
        this.authProvider.enroll(user).subscribe(
            (response: Response) => {
                console.log(RegistroPage.name + ' enrollWithApi ' + JSON.stringify(response));
                this.common.dismissLoading();
                let msg = response.json();
                this.common.presentAcceptAlert(msg.titulo, msg.descripcion, () => this.common.dismissModal());
            },
            (error: any): void => console.log(RegistroPage.name + ' enrollWithApi error' + JSON.stringify(error))
        );
    }

    goBack() {
        this.common.dismissModal();
    }

    terminosCondiciones() {
        let modal: Modal = this.common.createModal('TerminosCondicionesPage');
        modal.present();
    }

    createFormGroup(): FormGroup {
        return this.formBuilder.group({
            username: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(255),
                    Validators.pattern(/^\w*$/)
                ])
            ],
            email: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.pattern(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/),
                    Validators.minLength(5),
                    Validators.maxLength(255)
                ])
            ],
            phone: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(3),
                    Validators.maxLength(255)
                ])
            ],
            matchingPasswords: this.formBuilder.group({
                password: [
                    '',
                    Validators.compose([
                        Validators.required,
                        Validators.minLength(8),
                        Validators.maxLength(255)
                    ])
                ],
                passwordConfirmation: [
                    '',
                    Validators.compose([
                        Validators.required,
                        Validators.minLength(8),
                        Validators.maxLength(255)
                    ])
                ]
            },
            { validator: this.matchValidator }
            ),
            termsConditions: [
                null, 
                Validators.requiredTrue
            ]
        });
    }

    matchValidator(ac: AbstractControl) {
        let password = ac.get('password').value;
        let passwordConfirmation = ac.get('passwordConfirmation').value;
        if (password === passwordConfirmation) return null;

        if (passwordConfirmation.length == 0) ac.get('passwordConfirmation').setErrors({ matchValidator: true, required: true });
        else ac.get('passwordConfirmation').setErrors({ matchValidator: true });
    }
}