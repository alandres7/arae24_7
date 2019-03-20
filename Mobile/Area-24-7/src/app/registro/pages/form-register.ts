import { Response } from '@angular/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NavController, NavParams } from 'ionic-angular';

import { Common } from './../../shared/utilidades/common';

import { Country } from '../../../entities/country';
import { Department } from '../../../entities/department';
import { User } from '../../../entities/user';

import { TerritorioProvider } from '../../../providers/territorio/territorio';
import { AuthenticationProvider } from '../../../providers/authentication/authentication';

import { FUENTES_REGISTRO } from '../../shared/fuentes-registro';

import { AbstractControl } from '@angular/forms/src/model';

@Component({
    selector: 'page-form-register',
    templateUrl: 'form-register.html',
    styles: ['form-register.scss']
})
export class FormRegister {

    static readonly AMVA_TERRITORIES = [
          'MEDELLIN'
        , 'BARBOSA'
        , 'BELLO'
        , 'CALDAS'
        , 'COPACABANA'
        , 'ENVIGADO'
        , 'GIRARDOTA'
        , 'ITAGUI'
        , 'LA ESTRELLA'
        , 'SABANETA'
    ];

    private countries: Country[];
    private formGroup: FormGroup;

    constructor(
          private territorioProvider: TerritorioProvider
        , private authProvider: AuthenticationProvider
        , public navCtrl: NavController
        , public navParams: NavParams
        , public formBuilder: FormBuilder
        , private common: Common)
    {
        this.formGroup = this.createFormGroup();
    }

    enrollWithForm(): void {
        this.common.presentLoading();
        let user: User = new User();
        user.email = this.formGroup.value.email;
        user.password = this.formGroup.value.matchingPasswords.password;
        user.name = this.formGroup.value.name;
        user.surname = this.formGroup.value.surname;
        user.birthdate = new Date(this.formGroup.value.birthdate);
        user.educationLevel = this.formGroup.value.educationLevel;
        user.gender = this.formGroup.value.gender;
        user.registrySource = FUENTES_REGISTRO.MOBILE_APP;
        let territory = this.formGroup.value.country;
        user.country = ('string' == typeof territory) ? territory : territory.name;
        territory = this.formGroup.value.department;
        user.department = ('string' == typeof territory) ? territory : territory.name;
        territory = this.formGroup.value.municipality;
        user.municipality  = ('string' == typeof territory) ? territory: territory.name;
        this.enrollWithApi(user);
    }

    enrollWithApi(user: User): void {
        this.authProvider.enroll(user).subscribe(
            (response: Response) => {
                console.log('enrollWithApi' + JSON.stringify(response));
                this.common.dismissLoading();
				this.common.presentAcceptAlert('Información', 'Su registro fue exitoso.');
                this.common.dismissModal();
            },
            (error) => {
                console.log('enrollWithApi error' + JSON.stringify(error));
                if (error.status == 409) {
                    let msg = 'Ya existe un usuario con este correo electrónico.';
                    this.common.presentAcceptAlert('Ups!', msg);
                }
                this.common.dismissLoading();
            });
    }

    goBack(): void {
        this.common.dismissModal();
    }

    createFormGroup(): FormGroup {
        return this.formBuilder.group({
            username: [
                '',
                Validators.compose([ Validators.required ])
            ],
            email: [ 
                '', 
                Validators.compose([ Validators.required, Validators.pattern(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/), Validators.minLength(5), Validators.maxLength(255)])
            ],
            cellPhone: [
                '',
                Validators.compose([ Validators.required, Validators.minLength(10) ])
            ],
            matchingPasswords: this.formBuilder.group({
                password: ['', Validators.compose( [Validators.required, Validators.minLength(8), Validators.maxLength(255)])],
                passwordConfirmation: ['', Validators.compose([ Validators.required, Validators.minLength(8), Validators.maxLength(255)])]
            }, { validator: this.matchValidator })
        });
    }

    matchValidator(ac: AbstractControl) {
        let password = ac.get('password').value;
        let passwordConfirmation = ac.get('passwordConfirmation').value;
        if (password == passwordConfirmation) return null;

        ac.get('passwordConfirmation').setErrors({ matchValidator: true });
    }
}
