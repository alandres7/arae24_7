import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

import { AppLayer } from '../../entities/app-layer';
import { Layer } from '../../entities/layer';
import { Preferences } from '../../entities/preferences/preferences';
import { SideMenu } from '../../app/menu/page/side-menu';
import { PreferencesProvider } from '../preferences/preferences';
import { AppPreferences } from '../../entities/preferences/app-preferences';

import { OneSignal } from '@ionic-native/onesignal';
import { Common } from '../../app/shared/utilidades/common';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class LayerProvider {

    public currentApp = new BehaviorSubject<AppLayer>(new AppLayer({
        codigoColor: '#96c93d',
        nombre: 'Área 24/7',
        recomendaciones: "",
        id: -1
    }));

    public emitCurrentApp(app: AppLayer): void {
        this.currentApp.next(app);
        console.log("LayerProvider", "emitCurrentApp", app);

    }

    private reloadMenu = new Subject<any>();

    reloadMenu$ = this.reloadMenu.asObservable();

    emitReloadMenu(): void { this.reloadMenu.next(); }

    private tree: AppLayer[];
    private treeChange = new Subject<AppLayer[]>();

    constructor(
        private http: Http,
        private preferencesProvider: PreferencesProvider,
        private oneSignal: OneSignal,
        private common: Common
    ) { }

    treeChanged$ = this.treeChange.asObservable();

    private emitTreeChange(): void { this.treeChange.next(this.tree); }

    getTree(): AppLayer[] { return this.tree; }

    setTree(tree: AppLayer[]): void {
        this.tree = this.pruneDeactivatedLayers(tree);
        this.emitTreeChange();
    }

    pruneDeactivatedLayers(tree: AppLayer[]): AppLayer[] {
        return tree.filter((app: AppLayer): boolean => app.active)
            .map((app: AppLayer) => {
                app.children = app.children.filter((layer: Layer): boolean => layer.active);
                return app;
            });
    }

    getApps(): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/apps';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getNLayer(level: string, idLayer: number): Observable<Response> {
        console.log('LayerProvider getNLayer level: ' + level + ', idLayer: ' + idLayer);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/capa-n'
            + '/' + level
            + '/' + idLayer;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    //TODO: se podría editar el modelo para que no se haga un request por Layer sino que se haga un único request 
    //a nivel de layerManager y se actualice la información en cada layer.
    getGeometriesByRadius(layerLevel: string, layerId: number, lat: number, lng: number, actionRadius: number): Observable<Response> {
        console.log(LayerProvider.name + ' getGeometriesByRadius');
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/find/' + layerLevel + '/' + layerId
            + '?latitud=' + lat
            + '&longitud=' + lng
            + '&radioAccion=' + actionRadius;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getGeometries(layerType: string, args: any): Observable<Response> {
        console.log('argumentos para crear marker', args)
        console.log('layerType', layerType)

        let url: string;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        switch (layerType) {
            case 'MAPA':
                if (args.layerId == 3) {
                    url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/marcadoresPorIdUsuarioOEstado?estado=APROBADO';
                }
                else {
                    url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/markers/' + args.layerLevel + '/' + args.layerId;
                }
                break;

            case 'AVISTAMIENTO':
                url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/find/' + args.layerLevel + '/' + args.layerId
                    + '?latitud=' + args.lat
                    + '&longitud=' + args.lng
                    + '&radioAccion=' + args.actionRadius;
                break;

            case 'MIS PUBLICACIONES':
                if (args.layerId == 263 || args.layerLevel == 'CATEGORIA') {
                    url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/usuario/' + JSON.parse(localStorage.getItem('usuario')).id;
                }
                else {
                    if (args.layerId == 2) {
                        url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/marcadoresPorIdUsuarioOEstado/'
                            + '?idUsuario=' + JSON.parse(localStorage.getItem('usuario')).id;
                    }
                }
                break;

            case 'BUSQUEDA':
                url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/findbyname'
                    + '?nombre=' + args.name
                    + '&latitud=' + args.lat
                    + '&longitud=' + args.lng;
                break;
        }
        return this.http.get(url, { headers: headers });
    }

    getAllGeometries(layerLevel: string, layerId: number): Observable<Response> {
        let url: string;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));

        switch (layerId) {
            case 5: //Avistamientos - Mis avistamientos
                url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/usuario/' + JSON.parse(localStorage.getItem('usuario')).id;
                break;

            case 185: //Avistamientos - Buscador
                url = CONFIG_ENV.REST_BASE_URL + 'api/avistamiento/'

            default:
                url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/markers/' + layerLevel + '/' + layerId;
                break;
        }
        return this.http.get(url, { headers: headers });
    }

    getGeometriesByUser(): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/usuario/' + JSON.parse(localStorage.getItem('usuario')).id;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getSuggestions(level: string, idsLayers: string, query: string): Observable<Response> {
        console.log('LayerProvider getSuggestions level ' + level + ' idsLayers ' + idsLayers + ' query ' + query);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/markers/' + level + '/' + idsLayers + '/' + query;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getGeometry(id: string): Observable<Response> {
        console.log('LayerProvider getGeometry id ' + id);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/marker/' + id;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getMarkerInfo(id: number): Observable<Response> {
        console.log('LayerProvider getMarkerInfo id ' + id);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/marker-info/' + id;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getUrlIcon(id: number): string {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/icono/' + id;
        return url;
    }

    getMenu(): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/aplicacion/menu';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.get(url, { headers: headers });
    }

    getPreferences(username: string): Observable<Response> {
        username = encodeURIComponent(username);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario/' + username + '/preferencias';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    putPreferences(username: string, preferences: Preferences): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario/' + username + '/preferencias';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.put(url, JSON.stringify(preferences), { headers: headers });
    }

    search(latLng: { lat: number, lng: number }, name: string): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/findbyname'
            + '?nombre=' + name
            + '&latitud=' + latLng.lat
            + '&longitud=' + latLng.lng;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    loadMenu(): void {
        this.getMenu().subscribe(
            (response: Response) => {
                console.log('LayerProvider loadMenu');
                let newApps: AppLayer[] = AppLayer.parseForMenu(response.json());

                let apps = this.pruneDeactivatedLayers(newApps);

                //Remueve contenedora
                apps = apps.filter(app => app.id !== 7)

                //por què se pregunta por username en localstorage?

                //se pregunta por username ya que el servicio de preferencias depende de este (username)
                if (localStorage.getItem('username') !== null) {
                    //se asegura de que el arreglo de preferencias este lleno y se parseen bien las preferencias a las entidades
                    let continueLoadingPreferences = true;
                    this.preferencesProvider.appsPreference$
                        .takeWhile(() => continueLoadingPreferences) //que hace el takeWhile aquì?
                        .subscribe(
                            (appsPreferences: AppPreferences[]) => {
                                //caso positivo: trae preferencias y hace merge con el àrbol
                                if (appsPreferences.length > 0) {
                                    this.loadAppsPreferences(apps, appsPreferences);
                                    continueLoadingPreferences = false;
                                } else { //case negativo: no trae preferencias entonces vuelve a intentar traerlas...
                                    this.preferencesProvider.startAllPreferences();
                                }
                            }
                        );
                }
            }
        );
    }

    //se agregan las apps que no estàn en preferencias pero llegan desde el servicio (menu), se ordenan las apps
    loadAppsPreferences(apps: AppLayer[], appsPreference: AppPreferences[]) {
        // debugger;
        let appsWithPreferences: AppLayer[] = new Array<AppLayer>();

        //Guardará las apps que estén en la variable apps pero no en preferencias para insertarlas al final.
        let missingApps: AppLayer[] = apps;

        //Ordena las apps según las preferencias
        appsPreference.forEach(appPreferences => {
            // debugger;
            let app: AppLayer = apps.find(app => appPreferences.id === app.id);
            if (app) {

                app.radius = appPreferences.actionRadius;
                app.notification = appPreferences.notification;

                appsWithPreferences.push(app);

                missingApps = missingApps.filter(missingApp => missingApp.id !== app.id)
            }
        });

        missingApps.forEach(missingApp => {
            missingApp.notification = true;
            missingApp.radius = 1500;
        });

        //Agregar apps faltantes
        appsWithPreferences = appsWithPreferences.concat(missingApps);


        this.initializeNotificationsOneSignal(appsWithPreferences);

        //Si habían apps faltantes las agrega a las preferencias
        if (missingApps.length > 0) {
            //TODO: Agregar appsWithPreferences a preferencias 
        }




        this.tree = appsWithPreferences;
        this.emitTreeChange();
    }

    initializeNotificationsOneSignal(apps: AppLayer[]) {
        apps.forEach(app => {
            this.setAppNotificationOneSignal(app.id, app.notification);
        });
    }

    private setAppNotificationOneSignal(appId: number, notificationStatus: boolean) {
        switch (appId) {
            case 1:
                if (notificationStatus) {
                    this.oneSignal.sendTag('Recórreme', '1');
                } else {
                    this.oneSignal.deleteTag('Recórreme');
                    this.common.submenu.sMovilidad = false;
                }
                break;
            case 2:
                if (notificationStatus) {
                    this.oneSignal.sendTag('Cuídame', '1');
                } else {
                    this.oneSignal.deleteTag('Cuídame');
                    this.common.submenu.sMovilidad = false;
                }
                break;
            case 3:
                if (notificationStatus) {
                    this.oneSignal.sendTag('Asómbrate', '1');
                } else {
                    this.oneSignal.deleteTag('Asómbrate');
                    this.common.submenu.sMovilidad = false;
                }
                break;
            case 4:
                if (notificationStatus) {
                    this.oneSignal.sendTag('Conóceme', '1');
                } else {
                    this.oneSignal.deleteTag('Conóceme');
                    this.common.submenu.sMovilidad = false;
                }
                break;
            case 5:
                if (notificationStatus) {
                    this.oneSignal.sendTag('Disfrútame', '1');
                } else {
                    this.oneSignal.deleteTag('Disfrútame');
                    this.common.submenu.sMovilidad = false;
                }
                break;
            case 6:
                if (notificationStatus) {
                    this.oneSignal.sendTag('Mídeme', '1');
                } else {
                    this.oneSignal.deleteTag('Mídeme');
                    this.common.submenu.sMovilidad = false;
                }
                break;
        }
        console.log('LayerProvider', 'setAppNotificationOneSignal', 'toggle state app ' + notificationStatus);
    }

    changeAppNotification(app: AppLayer) {
        app.notification = !app.notification;
        this.preferencesProvider.updateAppNotification(app.id, app.notification);
        this.setAppNotificationOneSignal(app.id, app.notification);
    }

    updateAppInTree(app: AppLayer) {
        let newTree: AppLayer[] = this.tree.map(appFromTree => {
            if (appFromTree.id === app.id) {
                appFromTree = app;
            }
            return appFromTree;
        });

        this.tree = newTree;
        this.emitTreeChange();
        this.emitCurrentApp(app);
    }
}
