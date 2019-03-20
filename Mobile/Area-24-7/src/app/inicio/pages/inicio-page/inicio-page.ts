import {
    Component,
    OnInit,
    OnDestroy,
    ElementRef,
    ViewChild
} from '@angular/core';
import {
    NavController,
    NavParams,
    MenuController,
    Events,
    Platform,
    FabContainer
} from 'ionic-angular';
import { ModalPage } from './../../../core/modal-page/modal-page';
import { menuDinamico } from './../../../menu/provider/menu';
import { GoogleMaps } from '../../../shared/utilidades/googleMaps';
import { Common } from '../../../shared/utilidades/common';
import {
    Geolocation,
    GeolocationOptions,
    Geoposition
} from '@ionic-native/geolocation';
import { AppDiagnostic } from '../../../shared/utilidades/diagnostic';
import { Response, Jsonp } from '@angular/http';

import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';

import { safeGet } from '@firebase/util/dist/esm/src/obj';

import { LocationChangeProvider } from '../../../../providers/location-change/location-change';

import {
    NativeGeocoder,
    NativeGeocoderReverseResult,
    NativeGeocoderForwardResult
} from '@ionic-native/native-geocoder';

import { LayerProvider } from '../../../../providers/layer/layer';
import { AppLayer } from '../../../../entities/app-layer';
import { CharacterizationCard } from '../../../../entities/characterization-card';
import { Recommendation } from '../../../../entities/recommendation';
import { MapaComponent } from '../../../core/mapa/mapa';
import { SideMenu } from '../../../menu/page/side-menu';

import { OneSignal, OSNotification } from '@ionic-native/onesignal';
import { CONFIG_ENV } from '../../../shared/config-env-service/const-env';
import { TerritorioProvider } from '../../../../providers/territorio/territorio';
import { Municipality } from '../../../../entities/municipality';
import { MapStyle } from '../../../shared/utilidades/mapStyle';
import { DecisionTreeComponent } from '../../../../components/decision-tree/decision-tree';
import { Preferences } from '../../../../entities/preferences/preferences';
import { AppPreferences } from '../../../../entities/preferences/app-preferences';
import { Layer } from '../../../../entities/layer';
import { LayerPreferences } from '../../../../entities/preferences/layer-preferences';

import { PreferencesProvider } from "../../../../providers/preferences/preferences";
import { FusionLayerComponent } from '../../../../components/fusion-layer/fusion-layer';

import { LayerManagerComponent } from '../../../../components/layer-manager/layer-manager';
import { AngularFireList, AngularFireDatabase } from 'angularfire2/database';
import { LocationUpdateProvider } from '../../../../providers/location-update/location-update';
import { Observable } from 'rxjs';
import { App } from 'ionic-angular/components/app/app';
import { MessageProvider } from '../../../../providers/message/message';

//declare var google;
@Component({
    selector: 'inicio-page',
    templateUrl: 'inicio-page.html'
})
export class InicioPage implements OnInit, OnDestroy {
    static municipalities: Municipality[];

    private positionSubscription: Subscription;
    private flagMapInitiated: boolean = false;
    private static goToHome: Subject<void> = new Subject<void>();

    static goToHome$: Observable<void> = InicioPage.goToHome.asObservable();

    static emitGoToHome(): void { InicioPage.goToHome.next(); }

    static readonly MIN_ZOOM = 9;
    static readonly MAX_ZOOM = 21;
    static readonly LOCATION_UPDATES_INTERVAL = 60000;
    static readonly DISTANCE_TOLERANCE = 5;


    private firebaseItemsChange: AngularFireList<any[]>;

    private timerId: any;
    private apps: AppLayer[];
    private insideAmva: boolean = true;
    private currentApp: any;
    private defaultApp = {
        color: '#96c93d',
        name: 'Área 24/7'
    };

    constructor(
        private layerProvider: LayerProvider,
        private territorioProvider: TerritorioProvider,
        private locationUpdate: LocationUpdateProvider,
        private navCtrl: NavController,
        private navParams: NavParams,
        private menu: MenuController,
        private geolocation: Geolocation,
        private platform: Platform,
        private googleMaps: GoogleMaps,
        private prueba: AppDiagnostic,
        private pmenuService: menuDinamico,
        private events: Events,
        private common: Common,
        private geocoder: NativeGeocoder,
        private locationChange: LocationChangeProvider,
        private oneSignal: OneSignal,
        private preferencesProvider: PreferencesProvider,
        private app: App,
        private angularFireDatabase: AngularFireDatabase,
        private messageProvider: MessageProvider) 
    {
        this.firebaseSetup();

        this.menu.enable(true);

        this.drawerOptions = {
            handleHeight: 20,
            thresholdFromBottom: 200,
            thresholdFromTop: 200,
            bounceBack: true
        };
    }

    absolute(): string {
        if (
            this.common.submenu.sEntorno ||
            this.common.submenu.sHuellas ||
            this.common.submenu.sVigias ||
            this.common.submenu.sAvistamientos ||
            this.common.submenu.sMovilidad ||
            this.common.submenu.sOrdenamiento
        ) {
            return 'initial anim';
        } else {
            return 'absolute';
        }
    }

    ngOnInit(): void {

        let n0 = this.app.getRootNavs();
        //console.log(' getting n0 ' + (typeof(n0)));
        // debugger;
        //this.turnOnLocationUpdates();

        this.apps = this.layerProvider.getTree();

        this.layerProvider.treeChanged$.subscribe((tree: AppLayer[]) => {
            console.log('treeChanged');
            // debugger;
            this.apps = tree;
            console.log('aplicaciones',this.apps)

            this.common.dismissLoading();
            // this.getAndMergePreferences();

            // this.preferencesProvider.appsPreference$.subscribe( appPreferences => {
            //     if (appPreferences.length > 0) {
            //         this.loadPreferences(appPreferences);
            //     } else {
            //         this.preferencesProvider.loadDefaulAppsPreferences(this.apps);
            //     }
            // });
        });
        this.currentApp = this.defaultApp;

        this.locationUpdate.getObservable(InicioPage.DISTANCE_TOLERANCE, InicioPage.LOCATION_UPDATES_INTERVAL).subscribe(
            (latLng: { lat: number, lng: number }): void => {
                //alert('inicio page location update ' + JSON.stringify(latLng));
                if (this.apps) this.setGeoreferencedMessages(latLng);
            }
        );

        InicioPage.goToHome$.subscribe((): void => {
            this.onClickHome();
        });
    }

    ngOnDestroy(): void {
      //  this.turnOffLocationUpdates();

        this.watch = undefined;
        this.googleMaps.removerMapa();
    }

    /*
    decisionTree(): void {
        this.navCtrl.push(DecisionTreeComponent, {
            decisionTreeRoot: {
                _id: 221,
                _question: 'Fauna',
                _urlMedia: 'http://201.184.243.195:9095/api/multimedia/68',
                _description: 'Fauna',
                _hasChildren: true
            },
            color: '#009a37'
        });
    }*/

    /*
    zoomIn(): void {
        if (this.zoom < InicioPage.MAX_ZOOM) {
            ++this.zoom;
            MapaComponent.mapa.setZoom(this.zoom);

            //this.googleMaps.cambiarZoom({ 'lat': this.lat, 'long': this.lng, 'zoom': this.zoom });
        }
        console.log('zoom in ' + this.zoom);
    }

    zoomOut(): void {
        if (this.zoom > InicioPage.MIN_ZOOM) {
            --this.zoom;
            MapaComponent.mapa.setZoom(this.zoom);
            //this.googleMaps.cambiarZoom({ 'lat': this.lat, 'long': this.lng, 'zoom': this.zoom });
        }
        console.log('zoom out ' + this.zoom);
    }

    onRangeZoomChange(zoom: number): void {
        this.zoom = zoom;
        MapaComponent.mapa.setZoom(this.zoom);
    }
*/

    setGeoreferencedMessages(latLng: { lat: number, lng: number }): void {
        this.validateLocationInsideAmva(latLng.lat, latLng.lng);

        if (this.insideAmva) {
            let conocemeApp: AppLayer = this.apps.find(
                (app: AppLayer): boolean => {
                    return app.id === 4;
                }
            );
            if (conocemeApp && conocemeApp.notification)
                this.messageGeoreferenced(
                    latLng.lat, 
                    latLng.lng,
                    conocemeApp.id,
                    -1,
                    '4_messageGeoreferenced'
                );
/*
            let disfrutameApp: AppLayer = this.apps.find(
                (app: AppLayer): boolean => {
                    return app.id === 5;
                }
            );
            if (disfrutameApp && disfrutameApp.notification) {
                this.messageGeoreferenced(
                    latLng.lat, 
                    latLng.lng,
                    disfrutameApp.id,
                    10,
                    '5_10_messageGeoreferenced'
                ); //Aire
                this.messageGeoreferenced(
                    latLng.lat,
                    latLng.lng,
                    disfrutameApp.id,
                    11,
                    '5_11_messageGeoreferenced'
                ); //Agua
            }*/
        }
    }

    /**
     * 
    turnOnLocationUpdates(): void {
        if (!this.timerId) {

            this.timerId = setInterval(() => {
           //     console.log('SetInterval ');
                //Valida si cambió la posición del dispositivo
                let geoposition: Geoposition = this.locationChange.getGeoposition();

                //    let previousPosition = JSON.parse(localStorage.getItem('currentPosition'));
      //              let cambioPosicion = false;

    //                if (previousPosition) {
  //                      if (geoposition.coords.latitude !== previousPosition.lat || geoposition.coords.longitude !== previousPosition.lng) {
//                            console.log("Cambió la posición");

//                            cambioPosicion = true;


                this.validateLocationInsideAmva(geoposition.coords.latitude, geoposition.coords.longitude);

                // localStorage.setItem('insideAmva', this.insideAmva.toString());
                // localStorage.setItem('currentPosition', '{"lat":' + geoposition.coords.latitude + ',"lng":' + geoposition.coords.longitude + '}');
                //   } else {
                //  this.insideAmva = (localStorage.getItem('insideAmva') === 'true');
                // }

                if (this.insideAmva) {
                    let conocemeApp: AppLayer = this.apps.find(
                        (app: AppLayer): boolean => {
                            return app.id === 4;
                        }
                    );
                    if (conocemeApp && conocemeApp.notification)
                        this.messageGeoreferenced(
                            geoposition.coords.latitude,
                            geoposition.coords.longitude,
                            conocemeApp.id,
                            -1,
                            '4_messageGeoreferenced'
                        );

                    let disfrutameApp: AppLayer = this.apps.find(
                        (app: AppLayer): boolean => {
                            return app.id === 5;
                        }
                    );
                    if (disfrutameApp && disfrutameApp.notification) {
                        this.messageGeoreferenced(
                            geoposition.coords.latitude,
                            geoposition.coords.longitude,
                            disfrutameApp.id,
                            10,
                            '5_10_messageGeoreferenced'
                        ); //Aire
                        this.messageGeoreferenced(
                            geoposition.coords.latitude,
                            geoposition.coords.longitude,
                            disfrutameApp.id,
                            11,
                            '5_11_messageGeoreferenced'
                        ); //Agua
                    }
                }
                // } else {
                  //      localStorage.setItem('currentPosition', '{"lat":' + geoposition.coords.latitude + ',"lng":' + geoposition.coords.longitude + '}');
                    //}
            }, InicioPage.LOCATION_UPDATES_INTERVAL);
        }
    }
     */

     /*
    turnOffLocationUpdates(): void {
        if (this.timerId) {
            clearInterval(this.timerId);
            this.timerId = undefined;
        }
    }*/

    messageGeoreferenced(
        lat: number,
        lng: number,
        idApp: number,
        idCapa: number,
        currentNotificationLocalStorageKey: string
    ): void 
    {
        let currentNotificationInfo = localStorage.getItem(
            currentNotificationLocalStorageKey
        );

        this.territorioProvider
            .getNotificationInfo(lat, lng, idApp, idCapa)
            .subscribe(
                newNotificationInfo => {
                    if (newNotificationInfo[0] !== currentNotificationInfo) {
                        console.log(
                            currentNotificationLocalStorageKey,
                            'newNotificacion differ from currentNotification'
                        );
                        localStorage.setItem(
                            currentNotificationLocalStorageKey,
                            newNotificationInfo[0]
                        );

                        let notificationMessage = newNotificationInfo[0] + newNotificationInfo[1];
                        this.oneSignal
                            .getIds()
                            .then(
                                (result: {
                                    userId: string;
                                    pushToken: string;
                                }): any => {
                                    console.log(
                                        'getIds ' + JSON.stringify(result)
                                    );

                                    let notification: any = {
                                        include_player_ids: [result.userId],
                                        contents: {
                                            en: notificationMessage,
                                            es: notificationMessage
                                        },
                                        app_id:
                                            CONFIG_ENV.ONESIGNAL_APPLICATION_ID
                                    };
                                    this.oneSignal
                                        .postNotification(notification)
                                        .then(
                                            (value: any): any => {
                                                console.log(
                                                    'postNotification ' +
                                                        JSON.stringify(value)
                                                );
                                            }
                                        )
                                        .catch(
                                            (reason: any): any => {
                                                console.log(
                                                    'postNotification error ' +
                                                        JSON.stringify(reason)
                                                );
                                            }
                                        );
                                }
                            )
                            .catch(
                                (reason: any): any => {
                                    console.log(
                                        'getIds error ' + JSON.stringify(reason)
                                    );
                                }
                            );
                    }
                },
                (error: any): void => console.log(InicioPage.name + ' messageGeorreferenced getNotificationInfo error ' + JSON.stringify(error))
            );
    }

    parserAguaAire(data) {
        let salida = '';
        data.forEach(element => {
            let key = Object.keys(element);
            if (
                key[0] !== 'icono' &&
                key[0] !== 'estado' &&
                key[0] !== 'recomendaciones'
            ) {
                let parsedElement = JSON.stringify(element);

                parsedElement = parsedElement.replace('{', '');
                parsedElement = parsedElement.replace('}', '');
                parsedElement = parsedElement.replace(':', ': ');
                while (parsedElement.indexOf('"') > -1) {
                    parsedElement = parsedElement.replace('"', '');
                }

                salida = '\n' + salida + parsedElement;
            } else if (key[0] === 'recomendaciones') {
                let recomendaciones = this.parseRecomendaciones(
                    element[key[0]]
                );
                let parsedRecomendaciones = 'recomendaciones:\n';
                recomendaciones.forEach(element => {
                    parsedRecomendaciones =
                        parsedRecomendaciones + '-' + element + '\n';
                });
                salida = '\n' + salida + parsedRecomendaciones;
            }
        });

        return salida;
    }

    parseRecomendaciones(recomendaciones: string) {
        return recomendaciones.split(', ');
    }

    toCapitalCaseLetter(str: string): string {
        if (!str || str.length == 0) return str;
        else return str[0].toUpperCase() + str.substring(1).toLowerCase();
    }

    public getApp(appId: number): AppLayer {
        return this.apps.find((app: AppLayer): boolean => appId == app.id);
    }

    lat: any;
    lng: any;
    latInicial: any;
    lngInicial: any;
    miMarker: any;
    ctaLayer: any;
    ctaLayer2: any;
    iconoMiPunto: string;
    range: any;
    private zoom: number = 9;
    watch: any;
    drawerOptions: any;

    ionViewDidLoad() {
        console.log('ionViewDidLoad');

        this.initMap();

        this.subscribeToZoomChanges();

        this.territorioProvider.getAmvaMunicipalities().subscribe(
            (municipalities: Municipality[]): void => {
                InicioPage.municipalities = municipalities;
                this.loadMunicipalitiesToMap();
            },
            (error: any): void =>
                console.log( InicioPage.name + ' ngOnInit getAmvaMunicipalities error ' + JSON.stringify(error))
        );

        this.setOneSignalUserTag();

        //this.googleMaps.kmlPrincipal();
        //this.googleMaps.kmlAvistamientos();
        //this.googleMaps.kmlOrdenamiento();
        //this.googleMaps.kmlVigias();
        //this.googleMaps.kmlEntorno();
        //this.subscribeToLocationChanges();
        //this.googleMaps.geoJsonAmva();

        //this.getPosition();

        /*   this.geolocation.getCurrentPosition().then((resp) => {
               this.lat = resp.coords.latitude;
               this.lng = resp.coords.longitude;
               this.common.validarMunicipioUbicacion(this.lat, this.lng).then(respuesta =>{
                   console.log("posicionamiento usuario didLoad" + JSON.stringify(respuesta));
               });

           }).catch((error) => {
               //this.common.generalAlert('Alerta 2', JSON.stringify(error));
               console.log('posicionamiento usuario didLoad error' + JSON.stringify(error));
           });*/
    }

    initMap(): void {
        MapaComponent.mapa = new google.maps.Map(
            document.getElementById('map'),
            {
                zoom: 14,
                center: { lat: 6.263527, lng: -75.5559108 },
                disableDefaultUI: true,
                styles: MapStyle.estiloMapa,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            }
        );
        this.setZoomListener();
    }

    setOneSignalUserTag(): void {
        this.oneSignal.setSubscription(true);
        this.oneSignal.sendTag('Personal', JSON.parse(localStorage.getItem('usuario')).username);
    }

    setZoomListener(): void {
        MapaComponent.mapa.addListener('zoom_changed', () => {
            console.log('zoom_changed fired');
            this.googleMaps.emitZoomChange(MapaComponent.mapa.getZoom());
        });
    }

    loadMunicipalitiesToMap(): void {
        InicioPage.municipalities.forEach(
            (municipality: Municipality): void => {
                let decoded = google.maps.geometry.encoding.decodePath(
                    municipality.polygonLineStr
                );
                let paths = decoded.map(item => {
                    return { lat: item.lat(), lng: item.lng() };
                });
                let polygon = new google.maps.Polygon({
                    paths: paths,
                    map: MapaComponent.mapa,
                    fillColor: '#D9EBB8',
                    strokeColor: '#96C93D'
                });
            }
        );
    }

    private locationSubscription: Subscription;

    subscribeToLocationChanges(): void {
        let changeDistance = 1;
        let changePeriod = 10000;
        this.locationSubscription = this.locationChange
            .wathLocation(changeDistance, changePeriod)
            .subscribe(
                (latLng: { lat: number; lng: number }) => {
                    console.log(
                        'locationChangeProvider watchLocation ' +
                            JSON.stringify(latLng)
                    );

                    this.googleMaps.posicionLat = latLng.lat;
                    this.googleMaps.posicionLon = latLng.lng;
                    this.validateLocationInsideAmva(latLng.lat, latLng.lng);
                    // alert('new location update');
                },
                (error: any) =>
                    console.log(
                        'locationChangeProvider watchLocation error ' +
                            JSON.stringify(error)
                    )
            );
    }

    ionViewDidEnter(): void {
        console.log('ionViewDidEnter');

        //  this.subscribeToPositionChanges();
    }

    subscribeToZoomChanges(): void {
        this.googleMaps.zoomChanged$.subscribe((zoom: number) => {
            this.zoom = zoom;
            console.log('zoomChanged$: ' + zoom);
        });
    } /*
                            this.subscribeToPositionChanges();
                        })
                        .catch(error => {
                            console.log('getCurrentPosition error' + JSON.stringify(error));

                            //this.getPosition();
                        });
                })
                .catch(error => console.log('ready error' + JSON.stringify(error)));
        }
    */ /*
                     this.common.validarMunicipioUbicacion(this.lat, this.lng).then(respuesta =>{
                         console.log("posicionamiento usuario didLoad" + JSON.stringify(respuesta));
                     });
                     this.googleMaps.miPunto({ 'lat': this.lat, 'lng': this.lng, 'icono': 'assets/mapa/ubicacion.png' });

*/

    /*
    getPosition(): void {
        this.platform.ready()
            .then(() => {
                this.geolocation.getCurrentPosition()
                    .then((value) => {
                        console.log('getCurrentPosition ' + JSON.stringify(value.coords));

                        this.lat = value.coords.latitude;
                        this.lng = value.coords.longitude;
                        this.latInicial = value.coords.latitude;
                        this.lngInicial = value.coords.longitude;
                        this.googleMaps.posicionLat = this.lat;
                        this.googleMaps.posicionLon = this.lng;

                        this.validateLocationInsideAmva(value.coords.latitude, value.coords.longitude);
                        //this.validateLocationInsideAmva(6.255389,-75.5855671); //Medellín
                        //this.validateLocationInsideAmva(6.3370129,-75.5572993); //Bello
                        //this.validateLocationInsideAmva(6.1798047,-75.6124132); //Itagui
                        //this.validateLocationInsideAmva(6.1644186,-75.5772535); //Envigado
                        //this.validateLocationInsideAmva(6.4381196,-75.3323771); //Barbosa
                        //this.validateLocationInsideAmva(6.0905064,-75.6362947); //Caldas
                        //this.validateLocationInsideAmva(6.3547312,-75.5067545); //Copacabana
                        //this.validateLocationInsideAmva(6.3789145,-75.4501655); //Girardota
                        //this.validateLocationInsideAmva(6.1569404,-75.6411061); //La Estrella
                        //this.validateLocationInsideAmva(6.1515668,-75.6185199); //Sabaneta
                        //this.validateLocationInsideAmva(6.1518262,-75.3768397); //outside Amva (Rionegro)
       */
    subscribeToPositionChanges(): void {
        this.positionSubscription = this.geolocation
            .watchPosition()
            .filter(p => p.coords != undefined)
            .subscribe(
                position => {
                    console.log(
                        'subscribeToPositionChanges ' +
                            JSON.stringify(position.coords)
                    );

                    this.lat = position.coords.latitude;
                    this.lng = position.coords.longitude;
                    this.googleMaps.posicionLat = this.lat;
                    this.googleMaps.posicionLon = this.lng;
                    /*  this.googleMaps.miPunto({
                            'lat': this.lat
                        , 'lng': this.lng
                        , 'icono': 'assets/mapa/ubicacion.png'
                        , 'animation': google.maps.Animation.DROP });

                    let distancia: Number = this.obtenerDistancia(this.latInicial, this.lngInicial, this.lat, this.lng);
                    if (distancia >= ((this.googleMaps.pRadioAccion - (this.googleMaps.pRadioAccion / 5)))) {
                        this.latInicial = this.lat;
                        this.lngInicial = this.lng;
                        console.log("supero radio de accion || distancia " + distancia);
                        console.log("radio de accion || " + this.googleMaps.pRadioAccion);
                        this.events.publish('busquedAutomatica', this.lat, this.lng);
                    } else {
                        console.log("No supero radio de accion || distancia --> " + distancia);
                    }
                    //console.log("location error || " + JSON.stringify(this.miMarker));
                    //this.googleMaps.crearInfoWindow({ 'marker': this.miMarker, 'contenido': '<h3> Mi posicion </h3>' });
*/
                },
                error => {
                    console.log(
                        'subscribeToPositionChanges error' +
                            JSON.stringify(error)
                    );
                }
            );
    }

    private validateLocationInsideAmvaNative(lat: number, lng: number): void {
        this.geocoder
            .reverseGeocode(lat, lng)
            // @ts-ignore
            .then((result: NativeGeocoderReverseResult) => {
                let municipality: Municipality = InicioPage.municipalities.find(
                    (municipality: Municipality) => {
                        return (
                            municipality.name ==
                                result[0].subAdministrativeArea ||
                            municipality.name == result[0].locality
                        );
                    }
                );
                if (municipality) {
                    console.log('inside Amva');
                    this.insideAmva = true;
                } 
                else {
                    console.log('outside Amva');
                    this.insideAmva = false;
                    this.messageProvider.getByNombreIdentificador('fuera del amva').subscribe(
                        (response: Response): void => {
                            console.log(InicioPage.name + 'validateLocationInsideAmvaNative getByNombreIdentificador ' + JSON.stringify(response))
                            let msg = response.json();
                            this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
                        },
                        (error: any): any =>
                            console.log(InicioPage.name + 'validateLocationInsideAmvaNative getByNombreIdentificador error ' + JSON.stringify(error))
                    );
                }
            })
            .catch(error =>
                console.log(
                    'validateLocationInsideAmva error ' + JSON.stringify(error)
                )
            );
    }

    private validateLocationInsideAmva(lat: number, lng: number): void {
        this.territorioProvider.getInsideAmva(lat, lng).subscribe(
            (response: Response): void => {
                console.log(InicioPage.name + ' validaLocationInsideAmva getInsideAmva ' + JSON.stringify(response));
                if (response.text() == 'true') {
                    console.log('inside Amva');
                    this.insideAmva = true;
                } 
                else {
                    console.log('outside Amva');
                    this.insideAmva = false;
                    this.messageProvider.getByNombreIdentificador('fuera del amva').subscribe(
                        (response: Response): void => {
                            console.log(InicioPage.name + 'validateLocationInsideAmva getByNombreIdentificador ' + JSON.stringify(response))
                            let msg = response.json();
                            this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
                        },
                        (error: any): any =>
                            console.log(InicioPage.name + 'validateLocationInsideAmva getByNombreIdentificador error ' + JSON.stringify(error))
                    );
                }
            },
            (error: any): any =>
                console.log(InicioPage.name + 'validateLocationInsideAmva getInsideAmva error ' + JSON.stringify(error))
        );
    }

    swtAplicacion(key) {
        switch (key) {
            case 1:
                this.common.submenu.sMovilidad = true;
                break;
            case 2:
                this.common.submenu.sVigias = true;
                break;
            case 3:
                this.common.submenu.sAvistamientos = true;
                break;
            case 4:
                this.common.submenu.sOrdenamiento = true;
                break;
            case 5:
                this.common.submenu.sEntorno = true;
                this.menu.swipeEnable(false);
                //TODO: disable menu gesture
                break;
            case 6:
                this.common.submenu.sHuellas = true;
                break;
            /* default:
                 break; */
        }
        let currentApp: AppLayer = this.apps.find(app => app.id === key);
        this.layerProvider.emitCurrentApp(currentApp);
        this.currentApp = currentApp;
    }

    /*
    mensajeAplicacion(dato) {
        if (dato.recomendaciones && dato.recomendaciones.length > 0) {
            //codigoColor
            var datos = {
                texto: String,
                color: String,
                imagen: String,
                nombre: String,
                tipo: String
            };
            datos.color = dato.codigoColor;
            datos.imagen = dato.rutaIcono;
            datos.texto = dato.recomendaciones[0].descripcion;
            datos.nombre = dato.recomendaciones[0].nombre;
            datos.tipo = null;
            const modal = this.modalCtrl.create(ModalPage, datos);
            modal.present();
        }
    }*/

    private rad(x) {
        return (x * Math.PI) / 180;
    }

    private obtenerDistancia(lat1, lng1, lat2, lng2) {
        var R = 6378137; // Earth’s mean radius in meter
        var dLat = this.rad(lat2 - lat1);
        var dLong = this.rad(lng2 - lng1);
        var a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(this.rad(lat1)) *
                Math.cos(this.rad(lat2)) *
                Math.sin(dLong / 2) *
                Math.sin(dLong / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        var d = R * c;
        return d; // returns the distance in meter
    }

    onClickHome(): void {
        if (this.currentApp.children) {
            this.currentApp.children.forEach(child => {
                if (child.active) child.active = false;
            });
    
            this.common.submenu.sMovilidad = false;
            this.common.submenu.sVigias = false;
            this.common.submenu.sAvistamientos = false;
            this.common.submenu.sOrdenamiento = false;
            this.common.submenu.sEntorno = false;
            this.common.submenu.sHuellas = false;
            this.currentApp = this.defaultApp;
    
            FusionLayerComponent.emitDeleteAllMarkers();
        }
        let defaultAppLayer = new AppLayer({
            codigoColor: '#96c93d',
            nombre: 'Área 24/7',
            recomendaciones: "",
            id: -1
        });
        this.layerProvider.emitCurrentApp(defaultAppLayer)
        this.menu.swipeEnable(true);
    }

    getAndMergePreferences(): void {
        let username = localStorage.getItem('username');
        if (username) {
            this.layerProvider.getPreferences(username).subscribe(
                (response: Response) => {
                    console.log(InicioPage.name + ' getAndMergePreferences getPreferences ' + JSON.stringify(response));
                    let body: string = response.text();
                    
                    if (body == '') this.loadPreferences(null);
                    else {
                        let preferences: Preferences = new Preferences(
                            response.json()
                        );
                        if (preferences.actionRadius){}
                            // this.loadPreferences(preferences);
                        else this.loadPreferences(null);
                    }
                },
                (error: any) =>
                    console.log(InicioPage.name + ' getAndMergePreferences getPreferences error ' + JSON.stringify(error))
            );
        } else {
            this.loadPreferences(null);
        }
    }



    loadPreferences(appsPreferences: AppPreferences[]): void {
        
        if(appsPreferences) {
            let orderedApps = new Array<AppLayer>();
            appsPreferences.forEach( (app: AppPreferences, appIndex: number): void => {
                //Busca la app que corresponde a la posicion
                let appToOrder: AppLayer = this.apps.find( (myApp: AppLayer): boolean => 
                    myApp.id === app.id
                );
                if (appToOrder && appToOrder.children.length > 0) {
                    appToOrder.radius = app.actionRadius;
                    orderedApps.push(appToOrder);
                }
            });

            this.apps = orderedApps;

            //Aplica la notificación de las preferencias
            this.apps.forEach( (app: AppLayer, appIndex: number): void => {
                let appPreferences: AppPreferences = appsPreferences.find(
                    (appPreferences: AppPreferences): boolean => 
                        appPreferences.id === app.id
                );
                if (appPreferences) {
                    if(appPreferences.notification === undefined) {
                        this.setNotificationStatusToApp(appPreferences.id, appIndex, true);
                    } else {
                        this.setNotificationStatusToApp(appPreferences.id, appIndex, appPreferences.notification);
                    }
                    
                }
            });
        } else {
            // this.preferencesProvider.loadDefaulAppsPreferences(this.apps);
        }
    }

    // loadPreferences(preferences: Preferences): void {
    //     if (preferences) {

    //         this.apps.forEach(
    //             (app: AppLayer, appIndex: number): void => {

    //                 let appPreferences: AppPreferences = preferences.appsPreferences.find(
    //                     (appPreferences: AppPreferences): boolean =>
    //                         appPreferences.id == app.id
    //                 );
    //                 if (appPreferences) {
    //                     app.active = app.active && appPreferences.active;

    //                     this.onAppActiveChange(this.apps, appIndex);

    //                     if (appPreferences.layersPreferences) {
    //                         app.children.forEach((layer: Layer) => {
    //                             let layerPreferences = appPreferences.layersPreferences.find(
    //                                 (layerPreferences: LayerPreferences): boolean =>
    //                                     layerPreferences.id == layer.id
    //                             );
    //                             if (layerPreferences) {
    //                                 layer.active = layerPreferences.active;
    //                                 layer.favorite = layerPreferences.favorite;
    //                             }
    //                         });
    //                     }
    //                 }
    //             }
    //         );
    //     } else {

    //         this.oneSignal.sendTag('Recórreme', '1');
    //         this.oneSignal.sendTag('Cuídame', '1');
    //         this.oneSignal.sendTag('Asómbrate', '1');
    //         this.oneSignal.sendTag('Conóceme', '1');
    //         this.oneSignal.sendTag('Disfrúta<me', '1');
    //         this.oneSignal.sendTag('Mídeme', '1');

    //     }
    // }
    notificationButton(app: AppLayer) {
        this.layerProvider.changeAppNotification(app);
        // if (!apps[appIndex]) return;

        
        // this.preferencesProvider.updateAppNotification(apps[appIndex].id, !apps[appIndex].notification)
    }

    setNotificationStatusToApp(
        appId: number,
        appIndex: number,
        notificationStatus: boolean
    ): void {
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

        this.apps[appIndex].notification = notificationStatus;
        
        console.log('toggle state apps ' + notificationStatus);
    }

    // onAppActiveChange(apps: AppLayer[], appIndex: number): void {
    //     if (!apps[appIndex]) return;

    //     let appActive = !apps[appIndex].notification;
    //     let appId: number = apps[appIndex].id;
    //     console.log(appId);

    //     console.log("Valor de appActive", appActive);

    //     switch (appId) {
    //         case 1:
    //             if (appActive) this.oneSignal.sendTag('Recórreme', '1');
    //             else {
    //                 this.oneSignal.deleteTag('Recórreme');
    //                 this.common.submenu.sMovilidad = false;
    //             }
    //             break;
    //         case 2:
    //             if (appActive) this.oneSignal.sendTag('Cuídame', '1');
    //             else {
    //                 this.oneSignal.deleteTag('Cuídame');
    //                 this.common.submenu.sVigias = false;
    //             }
    //             break;
    //         case 3:
    //             if (appActive) this.oneSignal.sendTag('Asómbrate', '1');
    //             else {
    //                 this.oneSignal.deleteTag('Asómbrate');
    //                 this.common.submenu.sAvistamientos = false;
    //             }
    //             break;
    //         case 4:
    //             if (appActive) this.oneSignal.sendTag('Conóceme', '1');
    //             else {
    //                 this.oneSignal.deleteTag('Conóceme');
    //                 this.common.submenu.sOrdenamiento = false;
    //             }
    //             break;
    //         case 5:
    //             if (appActive) this.oneSignal.sendTag('Disfrútame', '1');
    //             else {
    //                 this.oneSignal.deleteTag('Disfrútame');
    //                 this.common.submenu.sEntorno = false;
    //             }
    //             break;
    //         case 6:
    //             if (appActive) this.oneSignal.sendTag('Mídeme', '1');
    //             else {
    //                 this.oneSignal.deleteTag('Mídeme');
    //                 console.log("desactiva mideme");

    //                 this.common.submenu.sHuellas = false;
    //             }
    //             break;
    //     }
    //     this.apps[appIndex].notification = !this.apps[appIndex].notification;
    //     this.updatePreferences();
    //     console.log('toggle state apps ' + apps[appIndex].notification);
    // }

    // updatePreferences(): void {
    //     let username: string = localStorage.getItem('username');
    //     let preferences: Preferences = Preferences.parseFromEntities(
    //         // SideMenu.getActionRadius(),
    //         this.apps,
    //         SideMenu.getTransportsPreferences()
    //     );
    //     this.layerProvider
    //         .putPreferences(username, preferences)
    //         .subscribe(
    //             (response: Response) =>
    //                 console.log(
    //                     'LayerProvider putPreferences ',
    //                     response.text()
    //                 ),
    //             (error: any) =>
    //                 console.log(
    //                     'LayerProvider putPreferences error ' +
    //                         JSON.stringify(error)
    //                 )
    //         );
    // }

    reorderApps(indexes) {
        let element = this.apps[indexes.from];
        this.apps.splice(indexes.from, 1);
        this.apps.splice(indexes.to, 0, element);
        this.preferencesProvider.reorderApps(this.apps);
    }

    firebaseSetup(): void {
        this.firebaseItemsChange = this.angularFireDatabase.list('/');
        this.firebaseItemsChange.valueChanges().subscribe(
            (change) => {
                console.log('FirebaseSetup valueChanges ' + JSON.stringify(change));
                this.layerProvider.loadMenu();           
            },
            (error: any) => console.log('FirebaseSetup valueChanges error ' + JSON.stringify(error))
        );
    }
}
