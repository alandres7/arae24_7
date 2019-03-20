import { Response } from '@angular/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NavController, NavParams, App, Alert, AlertController, AlertOptions, Loading, LoadingController } from 'ionic-angular';

import { CONFIG_ENV } from '../../../shared/config-env-service/const-env';
import { Common } from '../../../shared/utilidades/common';

import { UsuarioModel } from '../../../Models/usuario.model';
import { LoginPage } from "../../../login/pages/login-page/login-page";

import { Country } from '../../../../entities/country';
import { Department } from '../../../../entities/department';
import { Municipality } from '../../../../entities/municipality';
import { User } from '../../../../entities/user';

import { TerritorioProvider } from '../../../../providers/territorio/territorio';
import { AuthenticationProvider } from '../../../../providers/authentication/authentication';

import { FUENTES_REGISTRO } from '../../../shared/fuentes-registro';

import { count } from 'rxjs/operator/count';
import { last } from 'rxjs/operators/last';
import { AbstractControl } from '@angular/forms/src/model';
import { query } from '@angular/core/src/render3/instructions';
import { CapitalizePipe } from '../../../../pipes/capitalize/capitalize';

@Component({
    selector: 'editar-usuario',
    templateUrl: 'editar-usuario.html',
    styles: ['editar-usuario.scss']
})
export class EditarUsuario {

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

    private birthdateValue: string;
    private genderValue: string;
    private educationLevel: string;

    private countries: Country[];
    private departments: Department[];
    private municipalities: Municipality[];
    private formGroup: FormGroup;
    private showDepartments: boolean = false;
    private showMunicipalities: boolean = false;

    constructor(
          private territorioProvider: TerritorioProvider
        , private authProvider: AuthenticationProvider
        , private alertCtrl: AlertController
        , private loadingCtrl: LoadingController
        , public navCtrl: NavController
        , public navParams: NavParams
        , public formBuilder: FormBuilder
        , private common: Common
        , private app: App) 
    {
        this.formGroup = this.createFormGroup();
    }

    ionViewDidLoad(): void {
        this.territorioProvider.getCountries().subscribe(
            (countries: Country[]) => {
                this.countries = this.moveUpAmvaRerritories(countries, ['COLOMBIA']);
                this.countries[0].departments = this.moveUpAmvaRerritories(this.countries[0].departments, ['ANTIOQUIA']);
                
                this.countries[0].departments[0].municipalities = this.moveUpAmvaRerritories(
                      this.countries[0].departments[0].municipalities
                    , EditarUsuario.AMVA_TERRITORIES
                );

                this.authProvider.get().subscribe(
                    (response: Response) => this.loadDataToForm(User.parseFromApi(response.json())),
                    (error) => {
                        console.log('authProvider get error' + JSON.stringify(error));
                    }
                )      
            },
            (error) => console.log('territorioProvider getCountries error ' + JSON.stringify(error))
        );
    }

    loadDataToForm(user: User): void {  
        this.formGroup.get('name').setValue(CapitalizePipe.prototype.transform(user.name));
        this.formGroup.get('surname').setValue(CapitalizePipe.prototype.transform(user.surname));
        this.formGroup.get('educationLevel').setValue(user.educationLevel);
        this.formGroup.get('email').setValue(user.email);
        this.formGroup.get('gender').setValue(user.gender);
        if (user.birthdate != null) this.birthdateValue = new Date(user.birthdate).toISOString();
        
        let country: Country = this.countries.find(country_ => country_.name.toUpperCase() == user.country.toUpperCase());
        this.formGroup.get('country').setValue(country);
        this.onCountryChanged();
        let department: Department = this.departments.find(department_ => department_.name.toUpperCase() == user.department.toUpperCase());
        if (department != undefined) {
            this.formGroup.get('department').setValue(department);
            this.onDepartamentChanged();
            let municipality: Municipality = this.municipalities.find(municipality_ => municipality_.name.toUpperCase() == user.municipality.toUpperCase());
            this.formGroup.get('municipality').setValue(municipality);
        } else {
            this.formGroup.get('department').setValue(user.department);
            this.formGroup.get('municipality').setValue(user.municipality);                    
        }
    }

    moveUpAmvaRerritories(territories: any[], amvaTerritories: string[]): any[] {
        let amvaTerritories_ = amvaTerritories.map((amvaTerritory: any) => {
            let index: number = territories.findIndex((territory: any) => territory.name == amvaTerritory);
            return territories.splice(index, 1)[0];
        });
        return amvaTerritories_.concat(territories);
    }

    onCountryChanged(): void {
        let country: Country = this.formGroup.value.country;
        if (country && country.departments.length > 0) {
            this.municipalities = [];
            this.departments = country.departments;
            this.showDepartments = true;
            this.showMunicipalities = true;
        }
        else {
            this.formGroup.controls.department.reset();
            this.formGroup.controls.municipality.reset();
            this.municipalities = [];
            this.departments = [];
            this.showMunicipalities = false;
            this.showDepartments = false;
        }
    }

    onDepartamentChanged(): void {
        let department: Department = this.formGroup.value.department;
        if (department.municipalities.length > 0) this.municipalities = department.municipalities;
        else this.municipalities = [];
    }

    getDataFromFrom(): User {
        let user: User = new User();
        user.email = this.formGroup.value.email;
        user.name = this.formGroup.value.name;
        user.surname = this.formGroup.value.surname;
        user.birthdate = new Date(this.formGroup.value.birthdate);
        user.educationLevel = this.formGroup.value.educationLevel;
        user.gender = this.formGroup.value.gender;
        user.registrySource = JSON.parse(localStorage.getItem('usuario')).nombreFuenteRegistro;
        let territory = this.formGroup.value.country;
        user.country = ('string' == typeof territory) ? territory : territory.name;
        territory = this.formGroup.value.department;
        user.department = ('string' == typeof territory) ? territory : territory.name;
        territory = this.formGroup.value.municipality;
        user.municipality = ('string' == typeof territory) ? territory : territory.name;
        return user;
    }

    update(): void {
        this.common.presentLoading();
        let user: User = this.getDataFromFrom();
        this.authProvider.update(user).subscribe(
            (response: Response) => {
                console.log('update' + JSON.stringify(response));
                this.common.dismissLoading();
				this.common.presentAcceptAlert('Información', 'Su actualización fue exitosa.');							
                this.common.dismissModal();
            },
            (error) => {
                console.log('update error' + JSON.stringify(error));
                this.common.dismissLoading();                                                
            });
    }

    goBack(): void {
        this.common.dismissModal();
    }

    createFormGroup(): FormGroup {
        return this.formBuilder.group({
            name: [ '', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(255)])],
            surname: [ '', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(255)])],
            educationLevel: [ '', Validators.required ],
            country: [ '', Validators.compose([ Validators.required])],
            department: [ '', Validators.compose([ Validators.required, Validators.minLength(2), Validators.maxLength(255)])],
            municipality: [ '', Validators.compose([ Validators.required, Validators.minLength(2), Validators.maxLength(255)])],
            email: [ '', Validators.compose([ Validators.required, Validators.minLength(5), Validators.maxLength(255)])],
            birthdate: [ '', Validators.required ],
            gender: ['', Validators.compose([ Validators.required])],
        });
    }
}