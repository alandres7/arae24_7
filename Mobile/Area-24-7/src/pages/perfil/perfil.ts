import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, App } from 'ionic-angular';
import { TerritorioProvider } from '../../providers/territorio/territorio';
import { Country } from '../../entities/country';
import { Department } from '../../entities/department';
import { Municipality } from '../../entities/municipality';
import { AuthenticationProvider } from '../../providers/authentication/authentication';
import { Response } from '@angular/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../../entities/user';
import { CapitalizePipe } from '../../pipes/capitalize/capitalize';
import { Common } from '../../app/shared/utilidades/common';

@IonicPage()
@Component({
    selector: 'page-perfil',
    templateUrl: 'perfil.html'
})
export class PerfilPage {
    static readonly AMVA_TERRITORIES = [
        'MEDELLIN',
        'BARBOSA',
        'BELLO',
        'CALDAS',
        'COPACABANA',
        'ENVIGADO',
        'GIRARDOTA',
        'ITAGUI',
        'LA ESTRELLA',
        'SABANETA'
    ];

    formGroup: FormGroup;
    countries: Country[];
    departments: Department[];
    showDepartments: boolean;
    municipalities: Municipality[];
    showMunicipalities: boolean;
    birthdateValue: string;
    loading: boolean;
    currentDate: string;

    constructor(
        public navCtrl: NavController,
        public navParams: NavParams,
        public territorioProvider: TerritorioProvider,
        public authProvider: AuthenticationProvider,
        public formBuilder: FormBuilder,
        public common: Common,
        private app: App
    ) {
        this.formGroup = this.createFormGroup();
        this.loading = true;
    }

    ionViewDidLoad(): void {

        console.log('navId from perfil ' + this.navCtrl.id);
        let n0 = this.app.getRootNavs();
        //console.log(' getting n0 ' + (typeof(n0)));
        // debugger;

        this.currentDate = new Date(Date.now()).toISOString();

        this.territorioProvider.getCountries().subscribe(
            (countries: Country[]) => {
                this.countries = this.moveUpAmvaTerritories(countries, [
                    'COLOMBIA'
                ]);
                this.countries[0].departments = this.moveUpAmvaTerritories(
                    this.countries[0].departments,
                    ['ANTIOQUIA']
                );

                this.countries[0].departments[0].municipalities = this.moveUpAmvaTerritories(
                    this.countries[0].departments[0].municipalities,
                    PerfilPage.AMVA_TERRITORIES
                );

                this.authProvider.get().subscribe(
                    (response: Response) => {
                        this.loadDataToForm(User.parseFromApi(response.json()));
                        this.loading = false;
                    },
                    error => {
                        console.log(
                            'authProvider get error' + JSON.stringify(error)
                        );
                    }
                );
            },
            error => {
                console.log(PerfilPage.name + ' ionViewDidLoad territorioProvider getCountries error ' + JSON.stringify(error));
            }
        );
    }

    minPossibleDate(): string {
        let date = new Date(Date.now());
        //console.log('date ' + JSON.stringify(date));
        return String(date.getFullYear() - 5);
    }

    goBack() {
        this.common.dismissModal();
    }

    update(): void {
        this.common.presentLoading();
        let user: User = this.getDataFromForm();
        this.authProvider.update(user).subscribe(
            (response: Response) => {
                console.log(PerfilPage.name + ' update ' + JSON.stringify(response));
                this.common.dismissLoading();
                let msg = response.json();
                this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
                this.common.dismissModal();
            },
            error => {
                console.log(PerfilPage.name + ' update error ' + JSON.stringify(error));
            }
        );
    }

    getDataFromForm(): User {
        let user: User = new User();
        user.username = this.formGroup.value.username;
        user.email = this.formGroup.value.email;
        user.phone = this.formGroup.value.phone;
        user.name = this.formGroup.value.name;
        user.surname = this.formGroup.value.surname;
        user.birthdate = new Date(this.formGroup.value.birthdate);
        user.educationLevel = this.formGroup.value.educationLevel;
        user.gender = this.formGroup.value.gender;
        user.password = JSON.parse(localStorage.getItem('usuario')).contrasena;
        user.registrySource = JSON.parse(localStorage.getItem('usuario')).nombreFuenteRegistro;
        let territory = this.formGroup.value.country;
        user.country =
            'string' == typeof territory ? territory : territory.name;
        territory = this.formGroup.value.department;
        user.department =
            'string' == typeof territory ? territory : territory.name;
        territory = this.formGroup.value.municipality;
        user.municipality =
            'string' == typeof territory ? territory : territory.name;
        return user;
    }

    moveUpAmvaTerritories(
        territories: any[],
        amvaTerritories: string[]
    ): any[] {
        let amvaTerritories_ = amvaTerritories.map((amvaTerritory: any) => {
            let index: number = territories.findIndex(
                (territory: any) => territory.name == amvaTerritory
            );
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
        } else {
            this.formGroup.controls.department.reset();
            this.formGroup.controls.municipality.reset();
            this.municipalities = [];
            this.departments = [];
            this.showDepartments = false;
            this.showMunicipalities = false;
        }
    }

    onDepartmentChanged(): void {
        let department: Department = this.formGroup.value.department;
        if (department.municipalities.length > 0) {
            this.municipalities = department.municipalities;
        } else {
            this.municipalities = [];
        }
    }

    createFormGroup(): FormGroup {
        return this.formBuilder.group({
            username: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(3),
                    Validators.maxLength(255)
                ])
            ],
            phone: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(10),
                    Validators.maxLength(255)
                ])
            ],
            name: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(3),
                    Validators.maxLength(255)
                ])
            ],
            surname: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(3),
                    Validators.maxLength(255)
                ])
            ],
            educationLevel: ['', Validators.required],
            country: ['', Validators.compose([Validators.required])],
            department: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(2),
                    Validators.maxLength(255)
                ])
            ],
            municipality: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(2),
                    Validators.maxLength(255)
                ])
            ],
            email: [
                '',
                Validators.compose([
                    Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(255)
                ])
            ],
            birthdate: ['', Validators.required],
            gender: ['', Validators.compose([Validators.required])]
        });
    }

    loadDataToForm(user: User): void {
        this.formGroup.get('username').setValue(user.username);
        this.formGroup.get('name').setValue(user.name);
        this.formGroup.get('surname').setValue(user.surname);
        this.formGroup.get('educationLevel').setValue(user.educationLevel);
        this.formGroup.get('email').setValue(user.email);
        this.formGroup.get('gender').setValue(user.gender);
        this.formGroup.get('phone').setValue(user.phone);

        if (user.birthdate != null) {
            this.birthdateValue = new Date(user.birthdate).toISOString();
        }

        if (user.country) {
            let country: Country = this.countries.find(
                country_ =>
                    country_.name.toUpperCase() == user.country.toUpperCase()
            );
            this.formGroup.get('country').setValue(country);
            this.onCountryChanged();
        }

        if (user.department) {
            let department: Department = this.departments.find(
                department_ =>
                    department_.name.toUpperCase() ==
                    user.department.toUpperCase()
            );
            if (department != undefined) {
                this.formGroup.get('department').setValue(department);
                this.onDepartmentChanged();

                let municipality: Municipality = this.municipalities.find(
                    municipality_ =>
                        municipality_.name.toUpperCase() ==
                        user.municipality.toUpperCase()
                );
                this.formGroup.get('municipality').setValue(municipality);
            } else {
                this.formGroup.get('department').setValue(user.department);
                this.formGroup.get('municipality').setValue(user.municipality);
            }
        }
    }
}
