import { Common } from './shared/utilidades/common';
import { menuDinamico } from './menu/provider/menu';
import { Component, ViewChild, ViewChildren, ContentChild, ContentChildren, OnInit } from '@angular/core';
import { Response } from '@angular/http';

import { Nav, Platform, MenuController, App, ToastController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { LoginPage } from './login/pages/login-page/login-page';

import { SideMenu } from './menu/page/side-menu';
import { MenuOptionModel } from './menu/model/menu-model';
import { OneSignal, OSNotificationOpenedResult } from '@ionic-native/onesignal';

import { CONFIG_ENV } from './shared/config-env-service/const-env';
import { GoogleMaps } from './shared/utilidades/googleMaps';

import { LayerProvider } from '../providers/layer/layer';
import { AppLayer } from '../entities/app-layer';
import { LocationUpdateProvider } from '../providers/location-update/location-update';
import { InicioPage } from './inicio/pages/inicio-page/inicio-page';
import { MessageProvider } from '../providers/message/message';

@Component({
    templateUrl: 'app.html',
    styles: ['app.scss']
})
export class Amva_247_App {
    @ViewChild(Nav) nav: Nav;

    @ViewChild(SideMenu) sideMenu: SideMenu;

    rootPage: any = LoginPage;
    public options: Array<any>;
    pages: Array<{ title: string, component: any }>;
    switcheCambiosMenu: Boolean = false;

    //public options:Array<any>;

    constructor(
          public platform: Platform
        , public statusBar: StatusBar
        , public splashScreen: SplashScreen
        , private menuCtrl: MenuController
        , private pmenuService: menuDinamico
        , private app: App
        , private oneSignal: OneSignal
        , private common: Common
        , private googleMaps: GoogleMaps
        , private layerProvider: LayerProvider
        , private toastCtrl: ToastController
        , private locationUpdate: LocationUpdateProvider
        , private messageProvider: MessageProvider
        ) 
    {
        this.common.submenu.sAvistamientos = false;
        this.common.submenu.sEntorno = false;
        this.common.submenu.sHuellas = false;
        this.common.submenu.sMovilidad = false;
        this.common.submenu.sOrdenamiento = false;
        this.common.submenu.sVigias = false;     
        this.common.submenu.sConcursoFotografia = false;  

        platform.ready().then(
            () => { 
                if (platform.is('cordova')) this.cordovaSetUps(); 

                this.appLifeCycleEvents();
                this.locationUpdate.init();
            }
        );
    }

    ngOnInit(): void {
        console.log('navId from app.component ' + this.nav.id);

        let n0 = this.app.getRootNavs();
        //console.log(' getting n0 ' + (typeof(n0)));
        // debugger;
    }

    cordovaSetUps(): void {
        this.registerBackButton();
        this.statusBar.styleDefault();
        this.statusBar.overlaysWebView(false);
        this.splashScreen.hide();
        this.oneSignalSetup();
    }

    oneSignalSetup(): void {
        //this.oneSignal.setLogLevel({ logLevel: 6, visualLevel: 6 });        
        this.oneSignal.startInit(CONFIG_ENV.ONESIGNAL_APPLICATION_ID, CONFIG_ENV.ONESIGNAL_SENDER_ID);
        this.oneSignal.inFocusDisplaying(this.oneSignal.OSInFocusDisplayOption.Notification);
        this.oneSignal.setSubscription(true);
       /* this.oneSignal.getIds().then((ids: any) => {
            let objUsuarioW = JSON.parse(localStorage.getItem('usuarioWeb'));
            let objUsuario = JSON.parse(localStorage.getItem('usuario'));
            objUsuario.tokenDispositivo = JSON.stringify(ids);
            objUsuarioW.tokenDispositivo = JSON.stringify(ids);
            //this.nativeStorage.setItem('usuario', JSON.stringify(objUsuario));
            localStorage.setItem('usuario', JSON.stringify(objUsuario));
            localStorage.setItem('usuarioWeb', JSON.stringify(objUsuarioW));
        });*/
        this.oneSignal.handleNotificationReceived().subscribe(
            (item: any) => {});

        this.oneSignal.handleNotificationOpened().subscribe(
            (result: OSNotificationOpenedResult) => {
                this.common.presentAcceptAlert('Mensaje Área 24/7', result.notification.payload.body);
        });
    
        this.oneSignal.endInit();
    }

    registerBackButton(): void {
        this.platform.registerBackButtonAction(() => {
            if (this.common.canDismissModal()) {
                console.log('canDismissModal');
                this.common.dismissModal();
            }
            else if (this.menuCtrl.isOpen()) {

                console.log('menu close');
                this.menuCtrl.close();
            } 
            else if (this.nav.canGoBack()) {
                console.log('nav pop');
                this.nav.pop();
            } 
            else if (this.common.submenu.sAvistamientos
                  || this.common.submenu.sConcursoFotografia
                  || this.common.submenu.sEntorno
                  || this.common.submenu.sHuellas
                  || this.common.submenu.sMovilidad
                  || this.common.submenu.sOrdenamiento
                  || this.common.submenu.sVigias) 
            { 
                //alert('onClickHome ');
                InicioPage.emitGoToHome();
            }
            else {
                //alert('exit app');
                this.messageProvider.getByNombreIdentificador('salir app').subscribe(
                    (response: Response) => {
                        console.log('registerBackButton getByNombreIdentificador ' + JSON.stringify(response));
                        let msg = response.json();
                        this.common.presentAcceptCancelAlert(msg.titulo, msg.descripcion, () => {
                            this.platform.exitApp();
                        });
                    },
                    error => console.log('registerBackButton getByNombreIdentificador error ' + JSON.stringify(error))
                );
            }
        });
    }

    appLifeCycleEvents(): void {
        document.addEventListener(
              'resume'
            , () => { 
                LocationUpdateProvider.switchedToLocationSettings = false;
            }
            , false
        );
    }

    initializeApp() {
        this.platform.ready()
            .then(() => {
                this.statusBar.styleDefault();
                this.statusBar.overlaysWebView(false);
                this.splashScreen.hide();
                this.notification({});

                /*
            setTimeout(() => {
                this.splashScreen.hide();
            }, 100);
*/
            //let options = new Array<any>();

         //   this.cagarMenu();
            //this.options = options;
        });

        /*this.platform.registerBackButtonAction(() => {
        let nav = this.app.getActiveNav();
        let activeView: ViewController = nav.getActive();

        if (activeView != null) {
            if (nav.canGoBack()) {
            nav.pop();
            } else if (typeof activeView.instance.backButtonAction === 'function')
            activeView.instance.backButtonAction();
            else nav.parent.select(0); // goes to the first tab
        }
        });*/
    }

    // Redirect the user to the selected page
    public selectOption(option: MenuOptionModel): void {
        /*this.menuCtrl.close().then(() => {

        // Collapse all the options
        this.sideMenu.collapseAllOptions();

        // Redirect to the selected page
        // this.nav.push(option.component || DetailsPage, { 'title': option.displayName });
        });*/
    }

    public onCloseMenu(): void {
        this.sideMenu.onCloseMenu();
    }

    openPage(page) {
        // Reset the content nav to have just this page
        // we wouldn't want the back button to show in this scenario
        this.nav.setRoot(page.component);
    }
/*
    private cagarMenu() {

        this.pmenuService.cargarMenuWeb('/api/aplicacion/menu')
        .then((res) => {
            console.log('/api/aplicacion/menu/ ' + JSON.stringify(res._body));
            this.options = JSON.parse(res._body);
            this.pmenuService.modelDinamico(this.options);
            this.pmenuService.listaMenu = this.options;
            
            let pusuario: any = JSON.parse(localStorage.getItem("usuario"));

            if (pusuario !== null && pusuario !== undefined) {
                if (!pusuario.preferencias) {
                    pusuario.preferencias = "{\"preferencias\":[{\"radioAccion\":150,\"ultimaActualizacion\":\"2017-10-12 10:26:53.495\",\"capas\":[{\"ultimaActualizacion\":\"2017-10-03 15:43:36.0\",\"favorito\":true,\"id\":17,\"nombre\":\"Viajes\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:37.0\",\"favorito\":true,\"id\":18,\"nombre\":\"Rutas cercanas\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:38.0\",\"favorito\":true,\"id\":19,\"nombre\":\"Lineas y rutas\",\"activo\":true}],\"id\":1,\"nombre\":\"Movilidad\",\"activo\":false},{\"radioAccion\":150,\"ultimaActualizacion\":\"2017-10-12 10:27:11.275\",\"capas\":[{\"ultimaActualizacion\":\"2017-10-03 15:36:44.0\",\"favorito\":true,\"id\":1,\"nombre\":\"Reportar\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:27.0\",\"favorito\":true,\"id\":2,\"nombre\":\"Mis Reportes\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:28.0\",\"favorito\":true,\"id\":3,\"nombre\":\"Todos los reportes\",\"activo\":true}],\"id\":2,\"nombre\":\"Vigias\",\"activo\":true},{\"radioAccion\":150,\"ultimaActualizacion\":\"2017-10-12 10:27:29.515\",\"capas\":[{\"ultimaActualizacion\":\"2017-10-03 15:43:28.0\",\"favorito\":true,\"id\":4,\"nombre\":\"Reportar avistamiento\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:29.0\",\"favorito\":true,\"id\":5,\"nombre\":\"Mis avistamientos\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:30.0\",\"favorito\":true,\"id\":6,\"nombre\":\"Todos los avistamientos reportados\",\"activo\":true}],\"id\":3,\"nombre\":\"Avistamientos\",\"activo\":true},{\"radioAccion\":150,\"ultimaActualizacion\":\"2017-10-12 10:27:44.452\",\"capas\":[{\"ultimaActualizacion\":\"2017-10-03 15:43:30.0\",\"favorito\":true,\"id\":7,\"nombre\":\"Equipamientos\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:30.0\",\"favorito\":true,\"id\":8,\"nombre\":\"Zonas Restringidas\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:31.0\",\"favorito\":true,\"id\":9,\"nombre\":\"Aprovechamiento\",\"activo\":true}],\"id\":4,\"nombre\":\"Ordenamiento\",\"activo\":true},{\"radioAccion\":150,\"ultimaActualizacion\":\"2017-10-12 10:28:04.178\",\"capas\":[{\"ultimaActualizacion\":\"2017-10-11 13:19:04.856\",\"favorito\":true,\"id\":10,\"nombre\":\"Calidad del aire\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-11 13:54:33.169\",\"favorito\":true,\"id\":11,\"nombre\":\"Monitoreo hídrico\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:32.0\",\"favorito\":true,\"id\":12,\"nombre\":\"Clima\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:34.0\",\"favorito\":true,\"id\":13,\"nombre\":\"Suelos\"}],\"id\":5,\"nombre\":\"Entorno\",\"activo\":true},{\"radioAccion\":150,\"ultimaActualizacion\":\"2017-10-12 10:28:19.419\",\"capas\":[{\"ultimaActualizacion\":\"2017-10-03 15:43:34.0\",\"favorito\":true,\"id\":14,\"nombre\":\"Cálculo huella hídrica\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:35.0\",\"favorito\":true,\"id\":15,\"nombre\":\"Cálculo huella de carbono\",\"activo\":true},{\"ultimaActualizacion\":\"2017-10-03 15:43:36.0\",\"favorito\":true,\"id\":16,\"nombre\":\"Gestión de Residuos post consumo\",\"activo\":true}],\"id\":6,\"nombre\":\"Huellas\",\"activo\":true},{\"radioAccion\":1,\"ultimaActualizacion\":\"2017-10-03 15:31:52.0\",\"capas\":[],\"id\":7,\"nombre\":\"Contenedora\",\"activo\":true}]}";                                
                    localStorage.setItem('usuario', pusuario);
                }
                let objPreferencias: any = JSON.parse(pusuario.preferencias);
                let pArreglo: Array<any> = <Array<any>>objPreferencias.preferencias;
                this.pmenuService.compararOpcionesMenu(this.options, pArreglo);  
            }

        },
        (error) => {
            if (error.status == 400) {

            }
        })

    }
*/
    notification(data) {
        if (this.platform.is('ios') || this.platform.is('android')) {
            this.oneSignal.startInit(CONFIG_ENV.ONESIGNAL_APPLICATION_ID, CONFIG_ENV.ONESIGNAL_SENDER_ID);
            this.oneSignal.inFocusDisplaying(this.oneSignal.OSInFocusDisplayOption.InAppAlert);
            this.oneSignal.setSubscription(true);
            this.oneSignal.getIds().then((ids: any) => {
                let objUsuarioW = JSON.parse(localStorage.getItem('usuarioWeb'));
                let objUsuario = JSON.parse(localStorage.getItem('usuario'));
                objUsuario.tokenDispositivo = JSON.stringify(ids);
                objUsuarioW.tokenDispositivo = JSON.stringify(ids);
                //this.nativeStorage.setItem('usuario', JSON.stringify(objUsuario));
                localStorage.setItem('usuario', JSON.stringify(objUsuario));
                localStorage.setItem('usuarioWeb', JSON.stringify(objUsuarioW));
            });
        
            try {
                this.oneSignal.handleNotificationReceived().subscribe((item: any) => {
                alert('recibi mensaje!!');
                console.log('llego mensaje', item.data.prueba1);
                });
        
                this.oneSignal.handleNotificationOpened().subscribe(() => {
                // do something when a notification is opened
                });
            } catch (error) {
        
            }
            this.oneSignal.endInit();
        }
    }
}
