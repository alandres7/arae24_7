import { Diagnostic } from '@ionic-native/diagnostic';
import { Injectable } from '@angular/core';
import { Platform } from 'ionic-angular';
import { AlertController } from 'ionic-angular';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import { HttpErrorResponse } from '@angular/common/http';
import { LocationAccuracy } from '@ionic-native/location-accuracy';
@Injectable()
export class AppDiagnostic {
    observableGPS: Observable<any>;
    alertaGps: Boolean = false;
    alertaPauta: Boolean = false;

    constructor(private diagnostic: Diagnostic
              , public alertCtrl: AlertController
              , private platform: Platform
            //  , private locationAccuracy: LocationAccuracy
              ) 
    {
        this.observableGPS = Observable.interval(6000);
        this.platform.pause.subscribe(()=>this.alertaPauta = true);
        this.platform.resume.subscribe(() => this.alertaPauta=false);
    }

    /*
    displayLocationDialog(): void {
        this.locationAccuracy.canRequest()
            .then((value: boolean) => {
                alert(Diagnostic.name + ' displayLocationDialog ' + JSON.stringify(value));

                if (value) {
                    this.locationAccuracy.request(this.locationAccuracy.REQUEST_PRIORITY_LOW_POWER)
                        .then((value: any) => {
                            alert(Diagnostic.name + ' displayLocationDialog request' + JSON.stringify(value));
                        })
                        .catch((reason: any) => {
                            alert(Diagnostic.name + ' displayLocationDialog request error ' + JSON.stringify(reason));
                        });
                }
            })
            .catch((reason: any) => {
                alert(Diagnostic.name + ' displayLocationDialog error ' + JSON.stringify(reason));
            });
    }


    checkLocation() {
        alert(Diagnostic.name + ' checkLocation ' + JSON.stringify(this.platform.platforms()));
        try{
          if (this.platform.is('ios') || this.platform.is('android')) {

            return this.observableGPS
            .subscribe(() => {
                this.diagnostic.isGpsLocationEnabled().then(
                    (isAvailable) => {
                        alert(Diagnostic.name + ' checkLocation ' + JSON.stringify(isAvailable));
                        if (!isAvailable && !this.alertaGps && !this.alertaPauta) {
                            this.alertaGps = true;
                            let alert = this.alertCtrl.create({
                                title: 'Activar Ubicación',
                                message: 'Debes activar la ubicación para el funcionamiento de Área 24/7',
                                buttons: [
                                    {
                                        text: 'Cancelar',
                                        role: 'cancel',
                                        handler: () => {
                                            this.alertaGps = false;
                                            console.log('Cancel clicked');
                                        }
                                    },
                                    {
                                        text: 'Habilitar',
                                        handler: () => {
                                            this.diagnostic.switchToLocationSettings();
                                            this.alertaGps = false;
                                        }
                                    }
                                ]
                            });
                            alert.present();
                        }

                    })
                    .catch((e) => {
                        alert(Diagnostic.name + ' checkLocation error ' + JSON.stringify(e));
                        // console.log(e);
                        // alert(JSON.stringify(e));
                    });
            });
        }
        }
        catch(ex){
            alert(Diagnostic.name + ' checkLocation error ' + JSON.stringify(ex));
        }

    }

    requestLocationAuthorization(): void {
        if (!this.platform.is('android') || !this.platform.is('ios')) return;

        this.diagnostic.requestLocationAuthorization()
            .then(status => {
                alert('requestLocationAuthorization ' + JSON.stringify(status))
            })
            .catch(error => alert('requestLocationAuthorization error' + JSON.stringify(error)));
    }
*/

    /*checkLocation2() {
       return IntervalObservable
           .create(2000)
           .map(()=>{
               this.diagnostic.isGpsLocationEnabled().then(
               (isAvailable) => {
                   if (!isAvailable) {
                       let alert = this.alertCtrl.create({
                           title: 'Activar GPS',
                           message: 'Deseas activar el GPS para el funcionamiento de 24/7?',
                           buttons: [
                               {
                                   text: 'Cancelar',W
                                   role: 'cancel',
                                   handler: () => {
                                       console.log('Cancel clicked');
                                   }
                               },
                               {
                                   text: 'Aceptar',
                                   handler: () => {
                                       console.log('Buy clicked');
                                       return this.diagnostic.switchToLocationSettings();
                                   }
                               }
                           ]
                       });
                       alert.present();
                   }

               }).catch((e) => {
                   console.log(e);
                   alert(JSON.stringify(e));
               });
           })


   }*/
    /*

    console.log('Is available? ' + isAvailable);

                        if (!isAvailable) {
                            let alert = this.alertCtrl.create({
                                title: 'Activar GPS',
                                message: 'Deseas activar el GPS para el funcionamiento de 24/7?',
                                buttons: [
                                    {
                                        text: 'Cancelar',
                                        role: 'cancel',
                                        handler: () => {
                                            console.log('Cancel clicked');
                                        }
                                    },
                                    {
                                        text: 'Aceptar',
                                        handler: () => {
                                            console.log('Buy clicked');
                                            return this.diagnostic.switchToLocationSettings();
                                        }
                                    }
                                ]
                            });
                            alert.present();

    checKUbicacion() {
        if (this.diagnostic.isLocationAuthorized) {
            if (!this.diagnostic.isLocationEnabled || !this.diagnostic.isGpsLocationEnabled) {
                this.diagnostic.switchToLocationSettings()
            }
        }
        else{
            this.diagnostic.permission.ACCESS_COARSE_LOCATION;
        }

    }*/

    checkInternet() {
        if (this.diagnostic.isCameraPresent) {
            this.diagnostic.switchToLocationSettings()
        }
    }



}
