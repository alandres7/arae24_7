import { Component } from '@angular/core';
import { Response } from '@angular/http';

import { IonicPage, NavController, NavParams } from 'ionic-angular';

import { LayerProvider } from '../../providers/layer/layer';
import { AppLayer } from '../../entities/app-layer';
import { GeoLayer } from '../../entities/geo-layer';
import { Layer } from '../../entities/layer';
import { OneSignal } from '@ionic-native/onesignal';
import { Common } from '../../app/shared/utilidades/common';
import { AppPreferences } from '../../entities/preferences/app-preferences';
import { LayerPreferences } from '../../entities/preferences/layer-preferences';
import { Preferences } from '../../entities/preferences/preferences';
import { MovilidadPage } from '../../app/movilidad/pages/movilidad-page/movilidad-page';
import { VigiasMedioAmbientePage } from '../../app/vigias-medio-ambiente/pages/vigias-medio-ambiente-page/vigias-medio-ambiente-page';
import { AvistamientoComponent } from '../../components/avistamiento/avistamiento';
import { TerritorioComponent } from '../../components/territorio/territorio';
import { MiEntornoComponent } from '../../components/mi-entorno/mi-entorno';
import { HuellasComponent } from '../../components/huellas/huellas';

@IonicPage()
@Component({
    selector: 'page-menu-main',
    templateUrl: 'menu-main.html',
})
export class MenuMainPage {

    private apps: AppLayer[] = [];

    constructor(private navCtrl: NavController
              , private layerProvider: LayerProvider
              , private oneSignal: OneSignal
              , private common: Common
              , private navParams: NavParams) 
    {}

    ionViewDidLoad() {
        this.loadMenu();
       // SideMenu.reloadMenu$.subscribe(() => this.loadMenu());
    }

    loadMenu(): void {
        this.layerProvider.getMenu().subscribe(
        (response: Response) => {
            console.log(MenuMainPage.name + ' loadMenu getMenu ' + JSON.stringify(response));
            let newApps: AppLayer[] = AppLayer.parseForMenu(response.json());
            //deactivate possible active apps before pruning them from the tree update.
            for (let i = 0; i < newApps.length; i++) this.onAppActiveChange(newApps, i);
            this.cleanMap();

            this.layerProvider.setTree(newApps);
            this.apps = this.layerProvider.getTree();
            this.getAndMergePreferences();
         /*   this.accordionActivated = new Array(this.apps.length);
            this.accordionActivated.fill(false, 0, this.apps.length);
            this.accordionTransportPreferencesActivated = false;*/
        },
        (error: any) =>
            console.log(MenuMainPage.name + ' loadMenu getMenu error ' + JSON.stringify(error))
        );
    }

    cleanMap(): void {
        let appTree: AppLayer[] = this.layerProvider.getTree();
        if (appTree) {
            appTree.forEach((app: AppLayer): void => {
                app.children.forEach((layer: Layer) => {
                    if (layer instanceof GeoLayer) {
                        layer.visible = false;
                        if (layer.markers) {
                            layer.markers.forEach((marker: google.maps.Marker) => {
                                marker.setMap(null);
                            });
                        }
                        if (layer.polygons) {
                            layer.polygons.forEach((polygon: google.maps.Polygon) => {
                                polygon.setMap(null);
                            });
                        }
                    }
                    else if (layer.children) {
                        layer.children.forEach((layer_: GeoLayer) => {
                            layer_.visible = false;
                            if (layer_.markers) {
                                layer_.markers.forEach((marker_: google.maps.Marker) => {
                                    marker_.setMap(null);
                                });
                            }
                            if (layer_.polygons) {
                                layer_.polygons.forEach((polygon_: google.maps.Polygon) => {
                                    polygon_.setMap(null);
                                });
                            }
                        }); 
                    }
                });
            });
        }
    }

    onAppActiveChange(apps: AppLayer[], appIndex: number): void {
        if (!apps[appIndex]) return;

        let appActive = apps[appIndex].active;
        let appId: number = apps[appIndex].id;
        switch (appId) {
            case 1:
                if (appActive) this.oneSignal.sendTag('Recórreme', '1');
                else {
                    this.oneSignal.deleteTag('Recórreme');
                    this.common.submenu.sMovilidad = false;
                }
                break;
            case 2:
                if (appActive) this.oneSignal.sendTag('Cuídame', '1');
                else {
                    this.oneSignal.deleteTag('Cuídame');
                    this.common.submenu.sVigias = false;
                }
                break;
            case 3:
                if (appActive) this.oneSignal.sendTag('Asómbrate', '1');
                else {
                    this.oneSignal.deleteTag('Asómbrate');
                    this.common.submenu.sAvistamientos = false;
                }
                break;
            case 4:
                if (appActive) this.oneSignal.sendTag('Conóceme', '1');
                else {
                    this.oneSignal.deleteTag('Conóceme');
                    this.common.submenu.sOrdenamiento = false;
                }
                break;
            case 5:
                if (appActive) this.oneSignal.sendTag('Disfrútame', '1');
                else {
                    this.oneSignal.deleteTag('Disfrútame');
                    this.common.submenu.sEntorno = false;
                }
                break;
            case 6:
                if (appActive) this.oneSignal.sendTag('Mídeme', '1');
                else {
                    this.oneSignal.deleteTag('Mídeme');
                    this.common.submenu.sHuellas = false;
                }
                break;
            case 6:
                if (appActive) this.oneSignal.sendTag('Concurso', '1');
                else {
                    this.oneSignal.deleteTag('Concurso');
                    this.common.submenu.sConcursoFotografia = false;
                }
                break;
        }
        console.log('toggle state apps ' + apps[appIndex].active);
    }

    getAndMergePreferences(): void {
        let username = localStorage.getItem('username');
        if (username) {
          this.layerProvider.getPreferences(username).subscribe(
            (response: Response) => {
                console.log(MenuMainPage.name + ' getAndMergePreferences getPreferences ' + JSON.stringify(response));
                let body: string = response.text();
                if (body == '') this.loadPreferences(null);
                else {
                let preferences: Preferences = new Preferences(response.json());
                // debugger;
                if (preferences.actionRadius) this.loadPreferences(preferences);
                else this.loadPreferences(null);
                }
            },
            (error: any) =>
                console.log(MenuMainPage.name + ' getAndMergePreferences getPreferences error ' + JSON.stringify(error))
            );
        } 
        else {
            this.loadPreferences(null);
        }
    }

    loadPreferences(preferences: Preferences): void {
        if (preferences) {
            this.apps.forEach(
                (app: AppLayer, appIndex: number): void => {
                    let appPreferences: AppPreferences = preferences.appsPreferences.find(
                        (appPreferences: AppPreferences): boolean =>
                        appPreferences.id == app.id
                    );
                    if (appPreferences) {
                        app.active = app.active && appPreferences.active;
            
                        this.onAppActiveChange(this.apps, appIndex);
            
                        if (appPreferences.layersPreferences) {
                            app.children.forEach((layer: Layer) => {
                                let layerPreferences = appPreferences.layersPreferences.find(
                                (layerPreferences: LayerPreferences): boolean =>
                                    layerPreferences.id == layer.id
                                );
                                if (layerPreferences) {
                                    layer.active = layerPreferences.active;
                                    layer.favorite = layerPreferences.favorite;
                                }
                            });
                        }
                    }
                }
            );
        
        /*
        SideMenu.emitActionRadiusChange(preferences.actionRadius);
    
        SideMenu.emitTransportsPreferencesChange(
            preferences.transportsPreferences
        );*/
        } else {
        this.oneSignal.sendTag('Recórreme', '1');
        this.oneSignal.sendTag('Cuídame', '1');
        this.oneSignal.sendTag('Asómbrate', '1');
        this.oneSignal.sendTag('Conóceme', '1');
        this.oneSignal.sendTag('Disfrútame', '1');
        this.oneSignal.sendTag('Mídeme', '1');
    
        /*
        SideMenu.emitActionRadiusChange(1500);
    
        SideMenu.emitTransportsPreferencesChange(
            TransportPreferences.parseFromDefault(PREFERENCIAS_TRANSPORTE)
        );*/
        }
    }

    onClickApp(app: AppLayer): void {
        switch(app.id) {
          /*  case 1: //Movilidad
                this.navCtrl.setRoot(MovilidadPage, {
                    app: app
                });
                break;*/

            case 2: //Vigias 
                this.navCtrl.setRoot(VigiasMedioAmbientePage, {
                    app: app
                });
                break;

            case 3: //Avistamientos
                this.navCtrl.setRoot(AvistamientoComponent, {
                    app: app
                });
                break;

            case 4: //Territorio
                this.navCtrl.setRoot(TerritorioComponent, {
                    app: app
                });
                break;

            case 5: //Mi Entorno
                this.navCtrl.setRoot(MiEntornoComponent, {
                    app: app
                });
                break;

            case 6: //Huellas
                this.navCtrl.setRoot(HuellasComponent, {
                    app: app
                });
                break;
        }
    }

}
