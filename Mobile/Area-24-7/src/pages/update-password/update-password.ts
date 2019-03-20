import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Common } from '../../app/shared/utilidades/common';
import { AuthenticationProvider } from '../../providers/authentication/authentication';

import { Response } from '@angular/http';

@IonicPage()
@Component({
  selector: 'page-update-password',
  templateUrl: 'update-password.html',
})
export class UpdatePasswordPage {

    private formGroup: FormGroup;

    constructor(private navCtrl: NavController
              , private navParams: NavParams
              , private common: Common
              , private formBuilder: FormBuilder
              , private authenticationProvider: AuthenticationProvider) 
    {
        this.formGroup = this.createFormGroup();
    }

    ionViewDidLoad() {}

    createFormGroup(): FormGroup {
        return this.formBuilder.group({
            currentPassword: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(8),
                    Validators.maxLength(255)
                ])
            ],
            matchingPasswords: this.formBuilder.group(
                {
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
            )
        });
    }

    matchValidator(ac: AbstractControl) {
        let password = ac.get('password').value;
        let passwordConfirmation = ac.get('passwordConfirmation').value;
        if (password === passwordConfirmation) return null;

        ac.get('passwordConfirmation').setErrors({ matchValidator: true });
    }

    updatePassword(): void {
        this.common.presentLoading();

        let currentPassword: string = this.formGroup.value.currentPassword;
        let newPassword: string = this.formGroup.value.matchingPasswords.password;
        let username: string = localStorage.getItem('username');
        this.authenticationProvider.updatePassword(username, currentPassword, newPassword).subscribe(
            (response: Response): void => {
                console.log(UpdatePasswordPage.name + ' updatePassword response ' + JSON.stringify(response));
                this.common.dismissLoading();
                let msg = response.json();
                this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
                this.formGroup.reset();
                this.navCtrl.pop();
            },
            (error: any): void => {
                console.log(UpdatePasswordPage.name + ' updatePassword error ' + JSON.stringify(error));
                this.formGroup.reset();
            }
        );
    }

    goBack(): void {
        this.common.dismissModal();
    }
}
