import { Injectable } from '@angular/core';
import { Geoposition, Geolocation } from '@ionic-native/geolocation';
import { LocationAccuracy,  } from '@ionic-native/location-accuracy';
import { Diagnostic } from '@ionic-native/diagnostic';

import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Common } from '../../app/shared/utilidades/common';
import { Alert, AlertController, Platform, AlertOptions } from 'ionic-angular';

interface SubjectSettings {
    subject: Subject<any>
  , changeDistance: number
  , changePeriod: number
};

@Injectable()
export class LocationUpdateProvider {

    //Only for iOS
    static switchedToLocationSettings: boolean = false;

    //Only for Android
    static resultLocationDialogCanceled: boolean = false;

    //bloquea multiples requests para evitar que el plugin se bloquee
    static isRequestInProgress: boolean = false;

    private static currentGeoposition: Geoposition = {
        coords: {
            accuracy: -1,
            altitude: -1,
            altitudeAccuracy: -1,
            heading: -1,
            latitude: 6.255389,
            longitude: -75.5855671,
            speed: -1
        },
        timestamp: -1
    };
    private static INTERVAL_CHECK_LOCATION_ENABLED = 5000;
    private static watchingLocation: boolean = false;

    private subjectsToEmit: SubjectSettings[];

    constructor(private geolocation: Geolocation
              , private locationAccuracy: LocationAccuracy
              , private diagnostic: Diagnostic
              , private common: Common
              , private platform: Platform) 
    {}

    getObservable(changeDistance: number, changePeriod: number): Observable<{ lat: number, lng: number }> {
        if (this.subjectsToEmit) {
            let subjectSettings: SubjectSettings = this.subjectsToEmit.find(
                (subjectSettings_: SubjectSettings): boolean => {
                    return subjectSettings_.changeDistance == changeDistance
                        && subjectSettings_.changePeriod == changePeriod;
            });

            if (subjectSettings) return subjectSettings.subject.asObservable();
            else {
                let newSubjectSettings: SubjectSettings = {
                    subject: new Subject<any>(),
                    changeDistance: changeDistance,
                    changePeriod: changePeriod
                };
                this.subjectsToEmit.push(newSubjectSettings);
                return newSubjectSettings.subject.asObservable();
            }
        }
        else {
            this.subjectsToEmit = new Array();
            let subjectSettings: SubjectSettings = {
                subject: new Subject<any>(),
                changeDistance: changeDistance,
                changePeriod: changePeriod
            };
            this.subjectsToEmit.push(subjectSettings);
            return subjectSettings.subject.asObservable();
        }
    }

    init(): void { this.checkLocationEnabled(); }

    getCurrentGeoposition(): Geoposition { return LocationUpdateProvider.currentGeoposition; }
    
    private getCurrentPosition(): void {
        this.geolocation
            .getCurrentPosition()
            .then((geoposition: Geoposition): any => {
                console.log(LocationUpdateProvider.name + ' getCurrentPosition ' + JSON.stringify(geoposition));
            })
            .catch((reason: any): any => {
                console.log(LocationUpdateProvider.name + ' getCurrentPosition error ' + JSON.stringify(reason));
            });
    }

    private watchLocation() {
        this.geolocation
            .watchPosition()
            .filter((geoposition) => geoposition.coords != undefined)
            .subscribe(
                (geoposition: Geoposition) => {
                    console.log(LocationUpdateProvider.name + ' watchPosition ' + JSON.stringify(geoposition));

                    this.emitLocationUpdate(LocationUpdateProvider.currentGeoposition, geoposition);
                    LocationUpdateProvider.currentGeoposition = geoposition;
            },
            (error) => console.log(LocationUpdateProvider.name + ' watchPosition error ' + JSON.stringify(error)) 
        );
    }

    private emitLocationUpdate(currGeoposition: Geoposition, newGeoposition: Geoposition): void {
        if (!this.subjectsToEmit) return; 
        
        this.subjectsToEmit.forEach((subjectToEmit: SubjectSettings): void => {
            let currLatLng: google.maps.LatLng = new google.maps.LatLng(
                  currGeoposition.coords.latitude
                , currGeoposition.coords.longitude);
            let newLatLng: google.maps.LatLng = new google.maps.LatLng(
                  newGeoposition.coords.latitude
                , newGeoposition.coords.longitude);
            let distance: number = google.maps.geometry.spherical.computeDistanceBetween(currLatLng, newLatLng);
            let time: number = newGeoposition.timestamp - currGeoposition.timestamp;
            if (distance > subjectToEmit.changeDistance && time > subjectToEmit.changePeriod) {
                console.log(LocationUpdateProvider.name + ' emitLocationUpdate distance: ' + distance + ', time: ' + time);
                subjectToEmit.subject.next({ 
                    lat: newGeoposition.coords.latitude,
                    lng: newGeoposition.coords.longitude
                });
            }
        });
    }

    private checkLocationEnabled(): void {
       // this.getCurrentPosition();
        //this.watchLocation();
        setInterval(
            () => {
          //      this.displayLocationDialog_();
                this.displayLocationDialog();
            },
            LocationUpdateProvider.INTERVAL_CHECK_LOCATION_ENABLED
        );
    }

    private displayLocationDialog() {
        console.log(LocationUpdateProvider.name + ' displayLocationDialog ');
        this.diagnostic.isLocationEnabled()
            .then((locationEnabled: boolean): any => {
                console.log(LocationUpdateProvider.name + ' displayLocationDialog isLocationEnabled ' + JSON.stringify(locationEnabled));

                if (!locationEnabled && !LocationUpdateProvider.resultLocationDialogCanceled) {
                    if (this.platform.is('ios')) this.locationDialogIos();
                    else if (this.platform.is('android')) this.locationDialogAndroid();
                }

                if (locationEnabled && !LocationUpdateProvider.watchingLocation) { 
                    LocationUpdateProvider.watchingLocation = true;
                    this.watchLocation();
                }
            })
            .catch((reason: any): any => {
                console.log(LocationUpdateProvider.name + ' displayLocationDialog isLocationEnabled error' + JSON.stringify(reason));
            });
    }

    private locationDialogAndroid(): void {
        if (!LocationUpdateProvider.isRequestInProgress) {
            LocationUpdateProvider.isRequestInProgress = true;

            this.locationAccuracy.canRequest()
                .then((value: boolean) => {
                    console.log(LocationUpdateProvider.name + ' displayLocationDialog canRequest ' + JSON.stringify(value));

                    if (value) {
                        this.locationAccuracy.request(this.locationAccuracy.REQUEST_PRIORITY_LOW_POWER)
                            .then((value: any) => {
                                console.log(LocationUpdateProvider.name + ' displayLocationDialog request ' + JSON.stringify(value));
 
                                LocationUpdateProvider.isRequestInProgress = false;
                            })
                            .catch((reason: any) => {
                                console.log(LocationUpdateProvider.name + ' displayLocationDialog request error ' + JSON.stringify(reason));

                                LocationUpdateProvider.isRequestInProgress = false;

                                //User chose not to make required location settings changes: "No, Gracias"
                                if (reason.code == 4) LocationUpdateProvider.resultLocationDialogCanceled = true;
                            });
                    }
                })
                .catch((reason: any) => {
                    console.log(LocationUpdateProvider.name + ' displayLocationDialog canRequest error ' + JSON.stringify(reason));
                });
        }
    }

    private locationDialogIos(): void {
        if (!LocationUpdateProvider.switchedToLocationSettings) {
            this.common.presentAlert(this.alertOptions()); 
        }
    }

    private alertOptions(): AlertOptions {
        return {
              title: ''
            , message: 'Para continuar, activa la ubicación del dispositivo, que usa el servicio de ubicación de Google'
            , buttons: [
                {
                      text: 'NO, GRACIAS'
                    , handler: () => {
                        LocationUpdateProvider.resultLocationDialogCanceled = true;
                    }
                },
                {
                      text: 'ACEPTAR'
                    , handler: () => {
                        LocationUpdateProvider.switchedToLocationSettings = true;
                        this.diagnostic.switchToSettings();
                    }
                }
            ] 
        };
    }
    
    private displayLocationDialog_(): void {
        try {
            this.diagnostic.isLocationEnabled()
            .then((value: boolean): any => {
                console.log(LocationUpdateProvider.name + ' displayLocationDialog_ isLocationEnabled ' + JSON.stringify(value));

                if (!value) {
                    this.common.presentAcceptCancelAlert('Información', 'Debes activar la ubicación para el funcionamiento de Área 24/7', () => {
                        //console.log('will switch');
                        //this.diagnostic.switchToLocationSettings();
                        //console.log('did switch');
                     this.diagnostic.switchToSettings();                 
                    }); 
                }
            })
            .catch((reason: any): any => {
                console.log(LocationUpdateProvider.name + ' displayLocationDialog_ isLocationEnabled error' + JSON.stringify(reason));
            });

            console.log('finish try');
        }
        catch (e) {
            console.log('EXception ' + JSON.stringify(e));
        }
       
    }
/*
    private displayLocationDialog_(): void {
        this.diagnostic.isLocationAuthorized()
            .then((value: any): any => {
                console.log(LocationUpdateProvider.name + ' displayLocationDialog_ isLocationAuthorized ' + JSON.stringify(value));

                if (value) {

                } 
                else {
                    this.diagnostic.requestLocationAuthorization()
                        .then((value: any): any => {
                            console.log(LocationUpdateProvider.name + ' displayLocationDialog_ requestLocationAuthorization ' + JSON.stringify(value));

                            if (value == 'GRANTED') {
                                this.diagnostic.isLocationEnabled()
                                    .then((value: boolean): any => {
                                        console.log(LocationUpdateProvider.name + ' displayLocationDialog_ isLocationEnabled ' + JSON.stringify(value));
                                    })
                                    .catch((reason: any): any => {
                                        console.log(LocationUpdateProvider.name + ' displayLocationDialog_ isLocationEnabled error' + JSON.stringify(reason));
                                    });
                            }
                        })
                        .catch((reason: any): any => {
                            console.log(LocationUpdateProvider.name + ' displayLocationDialog_ requestLocationAuthorization error' + JSON.stringify(reason));
                        });
                }
            })
            .catch((reason: any): any => {
                console.log(LocationUpdateProvider.name + ' displayLocationDialog_ isLocationAuthorized error ' + JSON.stringify(reason));
            });
    }
*/

}