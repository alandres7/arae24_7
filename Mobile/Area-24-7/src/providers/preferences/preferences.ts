import { Http, Response, Headers, ResponseOptions } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

import { Preferences } from '../../entities/preferences/preferences';
import { AppLayer } from '../../entities/app-layer';
import { AppPreferences } from '../../entities/preferences/app-preferences';
import { TransportPreferences } from '../../entities/preferences/transport-preferences';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { LayerProvider } from '../layer/layer';

import { PREFERENCIAS_TRANSPORTE } from '../../app/movilidad/providers/store';


@Injectable()
export class PreferencesProvider {

    private appsPreference = new BehaviorSubject<AppPreferences[]>(new Array<AppPreferences>());
    private transportsPreferences = new BehaviorSubject<TransportPreferences[]>(new Array<TransportPreferences>());
    
    appsPreference$ = this.appsPreference.asObservable();
    transportsPreferences$ = this.transportsPreferences.asObservable();

    constructor(public http: Http/*, private layerProvider: LayerProvider*/) {
        let username = localStorage.getItem(('username'));
        if(username){
            this.getPreferences().subscribe( (response: Response) => {
            
                let body: string = response.text();
                if (body == '') {
    
                    // Verificar si necesita inicializacion por default
                    let appsPreference = new Array<AppPreferences>();
                    let transportsPreferences = new Array<TransportPreferences>();
    
                    this.appsPreference = new BehaviorSubject<AppPreferences[]>(appsPreference);
                    this.transportsPreferences = new BehaviorSubject<TransportPreferences[]>(transportsPreferences);

                    // this.loadDefaulAppsPreferences(this.layerProvider.getTree());
                    
                } else {
                    let preferences: Preferences = new Preferences(response.json());
    
                    this.emitAppsPreference(preferences.appsPreferences, false);
                    this.emitTransportsPreferences(preferences.transportsPreferences, false);
                }
            }, (error: any) =>
                console.log(PreferencesProvider.name + ' getPreferences error ' + JSON.stringify(error))
            );
        }  
    }

    public startAllPreferences() {
        this.getPreferences().subscribe( (response: Response) => {
            let body: string = response.text();
                if (body == '') {
                    // this.loadDefaulAppsPreferences(this.layerProvider.getTree());
                    this.loadDefaultTransportPreferences();
                } else {
                    let preferences: Preferences = new Preferences(response.json());
                    // debugger;
                    this.emitAppsPreference(preferences.appsPreferences, false);            
                    this.emitTransportsPreferences(preferences.transportsPreferences, false);
                }
        }, (error: any) =>
            console.log(PreferencesProvider.name + ' getPreferences error ' + JSON.stringify(error))
        );
    }


    private emitAppsPreference(appsPreference: AppPreferences[], updatePreferences: boolean): void {
            this.appsPreference.next(appsPreference);

            if (updatePreferences) {
                this.putPreferences().subscribe( 
                    (response: Response) => {
                        console.log(PreferencesProvider.name + ' putPreferences ' + JSON.stringify(response));
                },
                error => console.log(PreferencesProvider.name + ' putPreferences ' + JSON.stringify(error)));
            }
    }

    private emitTransportsPreferences(transportsPreferences:TransportPreferences[], updatePreferences: boolean): void {
        if (transportsPreferences.length > 0) {
            this.transportsPreferences.next(transportsPreferences);

            if (updatePreferences) {
                this.putPreferences().subscribe( 
                    (response: Response) => {
                        console.log(PreferencesProvider.name + ' putPreferences ' + JSON.stringify(response));
                },
                error => console.log(PreferencesProvider.name + ' putPreferences error ' + JSON.stringify(error)));
            }
        } else {
            this.startAllPreferences();
            console.log(PreferencesProvider.name + " changeTransportPreferencesAndActionRadious: El arreglo de preferencias de transporte está vacio.")
        }
    }

    getPreferences(): Observable<Response> {
        let username = localStorage.getItem('username');
        username = encodeURIComponent(username);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario/' + username + '/preferencias';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));        
        return this.http.get(url, { headers: headers });        
        
    }

    private putPreferences(): Observable<Response> {        
        // debugger;
        let preferences: Preferences = new Preferences();
        preferences.appsPreferences = this.appsPreference.getValue();
        preferences.transportsPreferences = this.transportsPreferences.getValue();
        
        if (preferences.transportsPreferences.length > 0) {
            let username = localStorage.getItem('username');
            let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario/' + username + '/preferencias';
            let headers = new Headers();
            headers.append('Content-Type', 'application/json');
            headers.append('Authorization', localStorage.getItem('bearer'));        
            return this.http.put(url, JSON.stringify(preferences), { headers: headers });
        } else {
            this.startAllPreferences();
            alert(PreferencesProvider.name + " changeTransportPreferencesAndActionRadious: El arreglo de preferencias de transporte está vacio.")
        }
    }

    changeTransportsPreferences (transportsPreferences: TransportPreferences[]) {
        this.emitTransportsPreferences(transportsPreferences, true);
    }

    reorderApps (orderedApps: AppLayer[]) {
        let currentApps: AppPreferences[] = this.appsPreference.getValue();
        let newApps: AppPreferences[] = new Array<AppPreferences>();

        orderedApps.forEach(orderedApp => {
            let app: AppPreferences = currentApps.find( app => orderedApp.id === app.id );
            if (app) newApps.push(app);
        });

        this.emitAppsPreference(newApps, true);
    }

    updateActionRadius (appId: number, actionRadius: number) {
        let apps: AppPreferences[] = this.appsPreference.getValue();
        let indexApp = apps.findIndex( app => app.id === appId);

        if(indexApp >= 0) {
            apps[indexApp].actionRadius = actionRadius;

            this.emitAppsPreference(apps, true);
        }
    }

    updateAppNotification (appId: number, notification: boolean) {
        let apps: AppPreferences[] = this.appsPreference.getValue();
        let indexApp = apps.findIndex( app => app.id === appId);
        apps[indexApp].notification = notification;
        this.emitAppsPreference(apps, true);
    }

    loadDefaulAppsPreferences (apps: AppLayer[]) {
        let appPreferences = AppPreferences.parseFromAppLayers(apps);

        appPreferences.forEach(app => {
            app.notification = true;
        });

        this.emitAppsPreference(appPreferences, true);
        
    }

    loadDefaultTransportPreferences() {
        this.emitTransportsPreferences(
            TransportPreferences.parseFromDefault(PREFERENCIAS_TRANSPORTE), true
        )
    }

    changeTransportPreferencesAndActionRadious(transportsPreferences: TransportPreferences[], appId: number, actionRadius: number) {
        if (transportsPreferences.length > 0) {
            this.transportsPreferences.next(transportsPreferences);
            this.updateActionRadius(appId, actionRadius);
        } else {
            this.startAllPreferences();
            console.log(PreferencesProvider.name + " changeTransportPreferencesAndActionRadious: El arreglo de preferencias de transporte está vacio.")
        }
    }
}
