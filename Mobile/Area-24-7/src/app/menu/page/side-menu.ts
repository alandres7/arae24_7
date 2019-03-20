import { EditarUsuario } from './../../core/modal-edicion-usuario/pages/editar-usuario';
import { Observable } from 'rxjs/Observable';
import { LoginPage } from './../../login/pages/login-page/login-page';
import { Response } from '@angular/http';

import { Facebook } from '@ionic-native/facebook';
import { GooglePlus } from '@ionic-native/google-plus';
import { OneSignal } from '@ionic-native/onesignal';

import { AppLayer } from '../../../entities/app-layer';

import {
    Component,
    ElementRef,
    OnInit,
    OnChanges,
    Input,
    ViewChild,
    Output,
    EventEmitter,
    ChangeDetectionStrategy,
    Renderer,
    ViewChildren,
    Renderer2,
    QueryList,
    NgZone
} from '@angular/core';
import {
    Platform,
    DomController,
    App,
    Events,
    NavController
} from 'ionic-angular';
import { menuDinamico } from './../provider/menu';
import { Common } from '../../shared/utilidades/common';
import { GoogleMaps } from '../../shared/utilidades/googleMaps';
import { MenuOptionModel } from '.././model/menu-model';
import { PREFERENCIAS_TRANSPORTE } from '../../movilidad/providers/store';
import { GmapsMovilidad } from '../../movilidad/providers/gmapsMovilidad';

import { LayerProvider } from '../../../providers/layer/layer';
import { Layer } from '../../../entities/layer';
import { Preferences } from '../../../entities/preferences/preferences';
import { AppPreferences } from '../../../entities/preferences/app-preferences';
import { LayerPreferences } from '../../../entities/preferences/layer-preferences';
import { TransportPreferences } from '../../../entities/preferences/transport-preferences';
import { Subject } from 'rxjs/Subject';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { GeoLayer } from '../../../entities/geo-layer';
import { PerfilPage } from '../../../pages/perfil/perfil';

import { PreferencesProvider } from "../../../providers/preferences/preferences";
import { UpdatePasswordPage } from '../../../pages/update-password/update-password';

@Component({
    selector: 'side-menu',
    templateUrl: 'side-menu.html'
    //styles: ['side-menu.scss']
})
export class SideMenu implements OnInit {

    private transportsPreferences: TransportPreferences[];
    private currentApp: AppLayer;

    constructor(
        private preferencesProvider: PreferencesProvider,
        private layerProvider: LayerProvider,
        private appCtrl: App
    ) {}

    ngOnInit () {
        console.log("side-menu");
        
        this.preferencesProvider.transportsPreferences$.subscribe(
            (transportPreferences: TransportPreferences[]) => {
                this.transportsPreferences = transportPreferences;
        });
        
        this.layerProvider.currentApp.subscribe(
            (appLayer: AppLayer) => {
                this.currentApp = appLayer;
            }
        );
    }

    emitActionRadiusChange(): void {
        // console.log("side-menu", "cambió el radio", this.currentApp.radius);
        this.layerProvider.updateAppInTree(this.currentApp);
    }

    actionRadiusStepDown(): void {
        if (this.currentApp.radius > 100) {
            this.currentApp.radius = this.currentApp.radius - 100;
            this.emitActionRadiusChange();
        }
    }

    actionRadiusStepUp() {
        if (this.currentApp.radius < 1500) {
            this.currentApp.radius = this.currentApp.radius + 100;
            this.emitActionRadiusChange();
        }
    }

    onCloseMenu() {
        if (this.currentApp && this.currentApp.id !== -1) {
            console.log("side-menu", "onCloseMenu");
            
            this.preferencesProvider.changeTransportPreferencesAndActionRadious(
                this.transportsPreferences,
                this.currentApp.id,
                this.currentApp.radius
            );
        }
    }
}

// export class SideMenu implements OnInit, OnChanges {
//     private currentApp;
//     private defaultApp = {
//         color: '#96c93d',
//         name: 'Área 24/7'
//     };

//     static readonly MIN_ACTION_RADIUS = 100;
//     static readonly MAX_ACTION_RADIUS = 1500;

//     private static actionRadius: number = 1500;
//     private static transportsPreferences: TransportPreferences[] = [];

//     private static reloadMenu = new Subject<any>();
//     private static actionRadiusChange = new BehaviorSubject<any>(
//         SideMenu.actionRadius
//     );
//     private static transportsPreferencesChange = new BehaviorSubject<any>(
//         TransportPreferences.parseToDefault(SideMenu.transportsPreferences)
//     );

//     static reloadMenu$ = SideMenu.reloadMenu.asObservable();
//     static actionRadiusChanged$ = SideMenu.actionRadiusChange.asObservable();
//     static transportsPreferencesChanged$ = SideMenu.transportsPreferencesChange.asObservable();

//     static emitReloadMenu(): void {
//         SideMenu.reloadMenu.next();
//     }

//     private static emitActionRadiusChange(actionRadius?: number): void {
//         if (actionRadius) SideMenu.actionRadius = actionRadius;
//         console.log('emitActionRadiusChange ' + SideMenu.actionRadius);
//         SideMenu.actionRadiusChange.next(SideMenu.actionRadius);
//     }

//     private static emitTransportsPreferencesChange(
//         transportsPreferences?: TransportPreferences[]
//     ): void {
//         if (transportsPreferences)
//             SideMenu.transportsPreferences = transportsPreferences;
//         console.log(
//             'emitTransportsPreferencesChange ' +
//                 JSON.stringify(SideMenu.transportsPreferences)
//         );
//         SideMenu.transportsPreferencesChange.next(
//             TransportPreferences.parseToDefault(SideMenu.transportsPreferences)
//         );
//     }

//     get emitActionRadiusChange() {
//         return SideMenu.emitActionRadiusChange;
//     }

//     get actionRadius(): number {
//         return SideMenu.actionRadius;
//     }

//     set actionRadius(actionRadius: number) {
//         SideMenu.actionRadius = actionRadius;
//     }

//     get emitTransportsPreferencesChange() {
//         return SideMenu.emitTransportsPreferencesChange;
//     }

//     get transportsPreferences(): TransportPreferences[] {
//         return SideMenu.transportsPreferences;
//     }

//     set transportsPreferences(transportsPreferences: TransportPreferences[]) {
//         SideMenu.transportsPreferences = transportsPreferences;
//     }

//     private logoutState = false;
//     private apps: AppLayer[] = [];
//     private accordionActivated: boolean[];
//     private disabledApps: { disabled: boolean; id: number }[];
//     private accordionTransportPreferencesActivated: boolean;

//     actionRadiusStepUp(): void {
//         let jump: number =
//             SideMenu.actionRadius + 100 < SideMenu.MAX_ACTION_RADIUS
//                 ? SideMenu.actionRadius + 100
//                 : SideMenu.MAX_ACTION_RADIUS;
//         SideMenu.emitActionRadiusChange(jump);

//         console.log('actionRadiusStepUp ' + SideMenu.actionRadius);
//     }

//     actionRadiusStepDown(): void {
//         let jump: number =
//             SideMenu.actionRadius - 100 > SideMenu.MIN_ACTION_RADIUS
//                 ? SideMenu.actionRadius - 100
//                 : SideMenu.MIN_ACTION_RADIUS;
//         SideMenu.emitActionRadiusChange(jump);

//         console.log('actionRadiusStepDown ' + SideMenu.actionRadius);
//     }

//     @ViewChildren('zone')
//     zone: NgZone;
//     @ViewChildren('options')
//     optionDivs: QueryList<any>;
//     @ViewChildren('headerIcon')
//     headerIcons;
//     @ViewChildren('km')
//     km: Number = 200;

//     @ViewChild('toggleRef')
//     toggleRef: ElementRef;

//     // Main inputs
//     @Input()
//     options: Array<MenuOptionModel>;
//     @Input()
//     accordionMode: boolean = false;

//     // Inputs for custom item sizes
//     @Input()
//     iosItemHeight: number = 50;
//     @Input()
//     mdItemHeight: number = 50;
//     @Input()
//     wpItemHeight: number = 50;

//     // Outputs
//     @Output()
//     selectOption = new EventEmitter<any>();

//     constructor(
//         private renderer2: Renderer2,
//         private layerProvider: LayerProvider,
//         private oneSignal: OneSignal,
//         private facebook: Facebook,
//         private googlePlus: GooglePlus,
//         private platform: Platform,
//         private renderer: Renderer,
//         private domCtrl: DomController,
//         public common: Common,
//         public menuService: menuDinamico,
//         protected app: App,
//         public googleMaps: GoogleMaps,
//         private events: Events,
//         private elementRef: ElementRef,
//         private preferencesProvider: PreferencesProvider
//     ) {
//         // this.selectRadio();
//         //this.cargarListaPreferencias();
//         this.currentApp = this.defaultApp;
//     }

//     showUpdatePasswordOption(): boolean {
//         let user = JSON.parse(localStorage.getItem('usuario'));
//         if (user) return user.nombreFuenteRegistro == 'AP';
//         else return false;
//     }

    // ngOnInit(): void {
    //     console.log('ngOnInit side-menu');
    //     //  SideMenu.emitActionRadiusChange();

    //     /* this.layerProvider.reloadMenu$.subscribe(
    //             () => this.loadMenu()
    //         );
    //         */
    //     SideMenu.reloadMenu$.subscribe(() => {
    //         console.log('-------DEBUG-------', 'username', localStorage.getItem('username'));
    //         // if (localStorage.getItem('username') !== null) {
    //         //     this.preferencesProvider.startAllPreferences();
    //         // } else {
    //             this.loadMenu();
    //         // }
            
    //     });
    //     //this.setFirstOpenPreferences();
    //     // this.loadMenu();

    //     this.layerProvider.currentApp.subscribe(app => {
    //         this.currentApp = app;
    //         if (app.radius) {
    //             this.actionRadius = app.radius;
    //             this.emitActionRadiusChange(app.radius);
    //         }

    //         console.log('side-menu ngOnInit app cambiada', app);
    //     });        
    // }

    // ngOnChanges(): void {
    //     /*  this.apps.forEach((app: AppLayer): void => {
    //          debugger;
    //             let htmlElement = document.getElementById(app.id.toString());
    //             if (htmlElement) {
    //                 debugger;
    //                // htmlElement.children[0].style.backgroundColor = "red !important";
    //                // htmlElement.children[0].classList.add("toggle-color");
    //               this.renderer2.addClass(this.elementRef.nativeElement.children[0], 'toggle-color');
    //             }
    //         });*/
    // }

    // loadMenu(): void {
    //     this.layerProvider.getMenu().subscribe(
    //         (response: Response) => {
    //             console.log('LayerProvider getMenu ');
    //             let newApps: AppLayer[] = AppLayer.parseForMenu(
    //                 response.json()
    //             );
    //             //deactivate possible active apps before pruning them from the tree update.
    //             for (let i = 0; i < newApps.length; i++)
    //                 this.onAppActiveChange(newApps, i);
    //             this.cleanMap();

    //             this.layerProvider.setTree(newApps);
    //             this.apps = this.layerProvider.getTree();
    //             // this.getAndMergePreferences();
    //             if (localStorage.getItem('username') !== null) {
    //                 this.preferencesProvider.startAllPreferences();
    //                 this.preferencesProvider.transportsPreferences$.subscribe( transportPreferences => {
    //                     this.emitTransportsPreferencesChange(transportPreferences);
    //                 });
    //             }
    //             this.accordionActivated = new Array(this.apps.length);
    //             this.accordionActivated.fill(false, 0, this.apps.length);
    //             this.accordionTransportPreferencesActivated = false;
    //         },
    //         (error: any) =>
    //             console.log(
    //                 'LayerProvider getMenu error ' + JSON.stringify(error)
    //             )
    //     );
    // }

//     cleanMap(): void {
//         let appTree: AppLayer[] = this.layerProvider.getTree();
//         if (appTree) {
//             appTree.forEach(
//                 (app: AppLayer): void => {
//                     app.children.forEach((layer: Layer) => {
//                         if (layer instanceof GeoLayer) {
//                             layer.visible = false;
//                             if (layer.markers) {
//                                 layer.markers.forEach(
//                                     (marker: google.maps.Marker) => {
//                                         marker.setMap(null);
//                                     }
//                                 );
//                             }
//                             if (layer.polygons) {
//                                 layer.polygons.forEach(
//                                     (polygon: google.maps.Polygon) => {
//                                         polygon.setMap(null);
//                                     }
//                                 );
//                             }
//                         } else if (layer.children) {
//                             layer.children.forEach((layer_: GeoLayer) => {
//                                 layer_.visible = false;
//                                 if (layer_.markers) {
//                                     layer_.markers.forEach(
//                                         (marker_: google.maps.Marker) => {
//                                             marker_.setMap(null);
//                                         }
//                                     );
//                                 }
//                                 if (layer_.polygons) {
//                                     layer_.polygons.forEach(
//                                         (polygon_: google.maps.Polygon) => {
//                                             polygon_.setMap(null);
//                                         }
//                                     );
//                                 }
//                             });
//                         }
//                     });
//                 }
//             );
//         }
//     }

//     getAndMergePreferences(): void {
//         let username = localStorage.getItem('username');
//         if (username) {
//             this.layerProvider.getPreferences(username).subscribe(
//                 (response: Response) => {
//                     console.log('LayerProvider getPreferences ');
//                     let body: string = response.text();
//                     if (body == '') this.loadPreferences(null);
//                     else {
//                         let preferences: Preferences = new Preferences(
//                             response.json()
//                         );
//                         if (preferences.actionRadius)
//                             this.loadPreferences(preferences);
//                         else this.loadPreferences(null);
//                     }
//                 },
//                 (error: any) =>
//                     console.log(
//                         'LayerProvider getPreferences error ' +
//                             JSON.stringify(error)
//                     )
//             );
//         } else {
//             this.loadPreferences(null);
//         }
//     }

//     loadPreferences(preferences: Preferences): void {
//         if (preferences) {
//             this.apps.forEach(
//                 (app: AppLayer, appIndex: number): void => {
//                     let appPreferences: AppPreferences = preferences.appsPreferences.find(
//                         (appPreferences: AppPreferences): boolean =>
//                             appPreferences.id == app.id
//                     );
//                     if (appPreferences) {
//                         app.active = app.active && appPreferences.active;

//                         this.onAppActiveChange(this.apps, appIndex);

//                         if (appPreferences.layersPreferences) {
//                             app.children.forEach((layer: Layer) => {
//                                 let layerPreferences = appPreferences.layersPreferences.find(
//                                     (
//                                         layerPreferences: LayerPreferences
//                                     ): boolean =>
//                                         layerPreferences.id == layer.id
//                                 );
//                                 if (layerPreferences) {
//                                     layer.active = layerPreferences.active;
//                                     layer.favorite = layerPreferences.favorite;
//                                 }
//                             });
//                         }
//                     }
//                 }
//             );

//             if (preferences.transportsPreferences.length > 0) {
//                 SideMenu.emitActionRadiusChange(preferences.actionRadius);

//                 SideMenu.emitTransportsPreferencesChange(
//                     preferences.transportsPreferences
//                 );
//             } else {
//                 SideMenu.emitActionRadiusChange(1500);

//                 SideMenu.emitTransportsPreferencesChange(
//                     TransportPreferences.parseFromDefault(
//                         PREFERENCIAS_TRANSPORTE
//                     )
//                 );
//             }
//         } else {
//             this.oneSignal.sendTag('Recórreme', '1');
//             this.oneSignal.sendTag('Cuídame', '1');
//             this.oneSignal.sendTag('Asómbrate', '1');
//             this.oneSignal.sendTag('Conóceme', '1');
//             this.oneSignal.sendTag('Disfrútame', '1');
//             this.oneSignal.sendTag('Mídeme', '1');

//             SideMenu.emitActionRadiusChange(1500);

//             SideMenu.emitTransportsPreferencesChange(
//                 TransportPreferences.parseFromDefault(PREFERENCIAS_TRANSPORTE)
//             );
//         }
//     }

//     switchAccordion(appIndex: number, arrowIcon: any): void {
//         let currentValue: boolean = this.accordionActivated[appIndex];

//         if (currentValue) this.accordionActivated[appIndex] = false;
//         else {
//             this.accordionActivated = this.accordionActivated.map(
//                 (value: boolean): boolean => false
//             );
//             this.accordionActivated[appIndex] = true;
//             this.toggleOptionIcon(arrowIcon);
//         }
//     }

//     switchFavorite(appIndex: number, layerIndex: number): void {
//         this.apps[appIndex].children[layerIndex].favorite = !this.apps[appIndex]
//             .children[layerIndex].favorite;

//         console.log(
//             'switch favorite apps ' +
//                 this.apps[appIndex].children[layerIndex].favorite
//         );
//         console.log(
//             'switch favorite tree ' +
//                 this.layerProvider.getTree()[appIndex].children[layerIndex]
//                     .favorite
//         );
//     }

//     onAppActiveChange(apps: AppLayer[], appIndex: number): void {
//         if (!apps[appIndex]) return;

//         let appActive = apps[appIndex].active;
//         let appId: number = apps[appIndex].id;
//         switch (appId) {
//             case 1:
//                 if (appActive) this.oneSignal.sendTag('Recórreme', '1');
//                 else {
//                     this.oneSignal.deleteTag('Recórreme');
//                     this.common.submenu.sMovilidad = false;
//                 }
//                 break;
//             case 2:
//                 if (appActive) this.oneSignal.sendTag('Cuídame', '1');
//                 else {
//                     this.oneSignal.deleteTag('Cuídame');
//                     this.common.submenu.sVigias = false;
//                 }
//                 break;
//             case 3:
//                 if (appActive) this.oneSignal.sendTag('Asómbrate', '1');
//                 else {
//                     this.oneSignal.deleteTag('Asómbrate');
//                     this.common.submenu.sAvistamientos = false;
//                 }
//                 break;
//             case 4:
//                 if (appActive) this.oneSignal.sendTag('Conóceme', '1');
//                 else {
//                     this.oneSignal.deleteTag('Conóceme');
//                     this.common.submenu.sOrdenamiento = false;
//                 }
//                 break;
//             case 5:
//                 if (appActive) this.oneSignal.sendTag('Disfrútame', '1');
//                 else {
//                     this.oneSignal.deleteTag('Disfrútame');
//                     this.common.submenu.sEntorno = false;
//                 }
//                 break;
//             case 6:
//                 if (appActive) this.oneSignal.sendTag('Mídeme', '1');
//                 else {
//                     this.oneSignal.deleteTag('Mídeme');
//                     this.common.submenu.sHuellas = false;
//                 }
//                 break;
//         }

//         console.log('toggle state apps ' + apps[appIndex].active);
//     }

//     onLayerActiveChange(layer: Layer, active: boolean): void {
//         if (!active && layer.children) {
//             if (layer.children.length !== 0) {
//                 layer.children.forEach(
//                     (layer_: Layer): void => {
//                         if (layer_ instanceof GeoLayer) {
//                             layer_.visible = false;
//                             if (layer_.markers != undefined)
//                                 layer_.markers.forEach(
//                                     (marker: google.maps.Marker) =>
//                                         marker.setVisible(false)
//                                 );
//                             if (layer_.polylines != undefined)
//                                 layer_.polylines.forEach(
//                                     (polyline: google.maps.Polyline) =>
//                                         polyline.setVisible(false)
//                                 );
//                             if (layer_.polygons != undefined)
//                                 layer_.polygons.forEach(
//                                     (polygon: google.maps.Polygon) =>
//                                         polygon.setVisible(false)
//                                 );
//                         }
//                     }
//                 );
//             } else {
//                 if (layer instanceof GeoLayer) {
//                     layer.visible = false;
//                     if (layer.markers != undefined)
//                         layer.markers.forEach((marker: google.maps.Marker) =>
//                             marker.setVisible(false)
//                         );
//                     if (layer.polylines != undefined)
//                         layer.polylines.forEach(
//                             (polyline: google.maps.Polyline) =>
//                                 polyline.setVisible(false)
//                         );
//                     if (layer.polygons != undefined)
//                         layer.polygons.forEach((polygon: google.maps.Polygon) =>
//                             polygon.setVisible(false)
//                         );
//                 }
//             }
//         }
//     }

//     onCloseMenu(): void {
        
//         if (!this.logoutState) {
//             if (this.currentApp && this.currentApp.name !== 'Área 24/7') {
//                 this.currentApp.radius = this.actionRadius;
//                 this.preferencesProvider.updateActionRadius(this.currentApp.id, this.actionRadius);
//                 this.preferencesProvider.changeTransportsPreferences(this.transportsPreferences)
//                 let index = this.apps.findIndex(myApp => myApp.id === this.currentApp.id)
//                 this.apps[index] = this.currentApp;
    
//                 this.layerProvider.emitCurrentApp(this.currentApp);
    
//             }
            
//         }
//         /*
//             this.layerProvider.putPreferences(JSON.stringify(this.apps)).subscribe(
//                 (response: Response) => {
//                     console.log('LayerProvider putPreferences ');
//                 },
//                 (error: any) => console.log('LayerProvider putPreferences error ' + JSON.stringify(error))
//             );*/
//         //  localStorage.setItem('localPreferences', JSON.stringify(this.apps));
//         //localStorage.setItem('actionRadius', '' + SideMenu.actionRadius);
//     }

//     updatePreferences(): void {
//         let username: string = localStorage.getItem('username');
//         let preferences: Preferences = Preferences.parseFromEntities(
//             // SideMenu.actionRadius,
//             this.apps,
//             SideMenu.transportsPreferences
//         );
//         this.layerProvider
//             .putPreferences(username, preferences)
//             .subscribe(
//                 (response: Response) =>
//                     console.log('LayerProvider putPreferences '),
//                 (error: any) =>
//                     console.log(
//                         'LayerProvider putPreferences error ' +
//                             JSON.stringify(error)
//                     )
//             );
//     }

//     ngAfterViewInit(): void {
//         /*
//         console.log('ionViewDidEnter');
//         this.elementRef.nativeElement.querySelector('.range-pin').textContent = ``;
//         this.elementRef.nativeElement
//           .querySelector('.range-knob-handle')
//           .addEventListener('pointermove', function() {
//             const value = this.getAttribute('aria-valuenow');
//             this.querySelector('.range-pin').textContent = `${value}m`;
//           });
//     */
//         console.log('listaMenu ' + JSON.stringify(this.menuService.listaMenu));
//     }

//     // ---------------------------------------------------
//     // PUBLIC methods
//     // ---------------------------------------------------
//     listaPreferenciaTransporte: Array<{
//         id: number;
//         nombre: string;
//         activo: boolean;
//         icono: string;
//     }>;

//     /*
//     radioAccionPreferencia(actionRadius: number) {
//       try {
//         if (
//           this.menuService.listaMenu != null &&
//           this.menuService.listaMenu != undefined &&
//           this.menuService.listaMenu.length > 0
//         ) {
//           var pusuario: any = JSON.parse(localStorage.getItem("usuario"));
//           let objPreferencias = JSON.parse(pusuario.preferencias);
//           objPreferencias.preferencias.forEach(element => {
//             if (element.id === 7) {
//               element.radioAccion = actionRadius;
//               console.log("si envio cambio");
//             }
//           });
//           pusuario.preferencias = JSON.stringify(objPreferencias);
//           localStorage.setItem("usuario", JSON.stringify(pusuario));
//         }
//         this.guargarPreferencias();
//       } catch (error) {
//         console.log("error radio preferencia", error);
//       }
//     }
//   */
//     // Send the selected option to the caller component
//     public select(option: MenuOptionModel): void {
//         this.selectOption.emit(option);
//     }

//     // Toggle the sub options of the selected item
//     public toggleItemOptions(
//         optionsDivElement: any,
//         arrowIcon: any,
//         itemsCount: number
//     ): void {
//         let itemHeight;

//         if (this.accordionMode) {
//             this.collapseAllOptionsExceptSelected(optionsDivElement);
//             this.resetAllIconsExceptSelected(arrowIcon);
//         }

//         if (this.platform.is('ios')) {
//             itemHeight = this.iosItemHeight;
//         } else if (this.platform.is('windows')) {
//             itemHeight = this.wpItemHeight;
//         } else {
//             itemHeight = this.mdItemHeight;
//         }

//         this.toggleOptionSubItems(
//             optionsDivElement,
//             itemHeight + 1,
//             itemsCount
//         );
//         // this.toggleOptionIcon(arrowIcon);
//     }

//     // Reset the entire menu
//     public collapseAllOptions(): void {
//         this.optionDivs.forEach(optionDiv => {
//             this.hideSubItems(optionDiv.nativeElement);
//         });
//         this.headerIcons.forEach(headerIcon => {
//             this.resetIcon(headerIcon.nativeElement);
//         });
//     }

//     // ---------------------------------------------------
//     // PRIVATE methods
//     // ---------------------------------------------------

//     // Toggle the sub items of the selected option
//     private toggleOptionSubItems(
//         optionsContainer: any,
//         elementHeight: number,
//         itemsCount: number
//     ): void {
//         this.domCtrl.write(() => {
//             this.subItemsAreExpanded(optionsContainer)
//                 ? this.renderer.setElementStyle(
//                       optionsContainer,
//                       'height',
//                       '0px'
//                   )
//                 : this.renderer.setElementStyle(
//                       optionsContainer,
//                       'height',
//                       `${elementHeight * itemsCount}px`
//                   );
//         });
//     }

//     // Toggle the arrow icon of the selected option
//     private toggleOptionIcon(arrowIcon: any): void {
//         this.domCtrl.write(() => {
//             this.iconIsRotated(arrowIcon)
//                 ? this.renderer.setElementClass(arrowIcon, 'rotate', false)
//                 : this.renderer.setElementClass(arrowIcon, 'rotate', true);
//         });
//     }

//     // Reset the arrow icon of all the options except the selected option
//     private resetAllIconsExceptSelected(selectedArrowIcon: any): void {
//         this.headerIcons.forEach(headerIcon => {
//             let iconElement = headerIcon.nativeElement;
//             if (
//                 iconElement.id !== selectedArrowIcon.id &&
//                 this.iconIsRotated(iconElement)
//             ) {
//                 this.resetIcon(iconElement);
//             }
//         });
//     }

//     // Collapse the sub items of all the options except the selected option
//     private collapseAllOptionsExceptSelected(
//         selectedOptionsContainer: any
//     ): void {
//         this.optionDivs.forEach(optionDiv => {
//             let optionElement = optionDiv.nativeElement;
//             if (
//                 optionElement.id !== selectedOptionsContainer.id &&
//                 this.subItemsAreExpanded(optionElement)
//             ) {
//                 this.hideSubItems(optionElement);
//             }
//         });
//     }

//     // Return true if sub items are expanded
//     private subItemsAreExpanded(element: any): boolean {
//         return element.style.height !== '' && element.style.height !== '0px';
//     }

//     // Return true if the icon is rotated
//     private iconIsRotated(element: any): boolean {
//         return element.classList.contains('rotate');
//     }

//     // Collapse the sub items of the selected option
//     private hideSubItems(optionsContainer: any): void {
//         this.domCtrl.write(() => {
//             this.renderer.setElementStyle(optionsContainer, 'height', '0px');
//         });
//     }

//     // Reset the arrow icon of the selected option
//     private resetIcon(arrowIcon: any): void {
//         this.domCtrl.write(() => {
//             this.renderer.setElementClass(arrowIcon, 'rotate', false);
//         });
//     }

//     /*
//     guargarPreferencias() {
//       this.menuService.guardarPreferenciasUsuario(
//         localStorage.getItem("bearer"),
//         "/api/usuario"
//       );
//     }
//   */
//     logout(): void {
//         //this.updatePreferences();
//         this.logoutState = true;
//         this.oneSignal.deleteTag(
//             JSON.parse(localStorage.getItem('usuario')).username
//         );
//         localStorage.removeItem('usuario');
//         localStorage.removeItem('username');
//         localStorage.removeItem('bearer');

//         this.oneSignal.setSubscription(false);
//         this.googleMaps.removerMapa();
//         this.app.getRootNav().setRoot(LoginPage);
//     }

//     logoutFromFacebook(): void {
//         try {
//             this.facebook
//                 .logout()
//                 .then(res => alert('logoutFromFacebook ' + JSON.stringify(res)))
//                 .catch(e =>
//                     alert('logoutFromFacebook error' + JSON.stringify(e))
//                 );
//         } catch (e) {
//             alert('facebook caught exception: ' + JSON.stringify(e));
//         }
//     }

//     logoutFromGoogle(): void {
//         this.googlePlus
//             .logout()
//             .then(response =>
//                 console.log('logoutFromgoogle' + JSON.stringify(response))
//             )
//             .catch(error =>
//                 console.log('logoutFromgoogle error' + JSON.stringify(error))
//             );
//     }

//     cerrarSesion() {
//         localStorage.removeItem('usuario');
//         localStorage.removeItem('usuarioWeb');
//         localStorage.removeItem('bearer');
//         this.googleMaps.removerMapa();
//         console.log('listado views', this.app.getRootNav().getViews());
//         this.app
//             .getRootNav()
//             .getViews()
//             .forEach((element, index) => {
//                 this.app.getRootNav().remove(0, index);
//             });
//         this.app.getRootNav().setRoot(LoginPage);
//         console.log('listado views2', this.app.getRootNav().getViews());
//         //window.cookies.clear(function() {});
//         window.location.reload();

//         /*this.app.getRootNav().popAll();
//             this.app.getRootNav().setRoot(LoginPage);*/
//         /*this.app.getRootNav().setRoot(LoginPage).then(() => {
//                 const index = this.app.getRootNav().getActive().index;
//                 this.app.getRootNav().remove(0, index);
//               });*/

//         /*let nav = this.app.getActiveNav();
//             nav.setRoot(LoginPage).then(()=>{
//               nav.popAll()
//             });*/

//         //this.app.getActiveNav().parent.setRoot(LoginPage);
//         //this.app.getRootNav().setRoot(LoginPage);
//     }

//     doRefresh(refresher) {
//         console.log('Begin async operation', refresher);

//         setTimeout(() => {
//             console.log('Async operation has ended');
//             refresher.complete();
//         }, 2000);
//     }

//     cambiarFavorito(item): Boolean {
//         let res = false;
//         if (!item) {
//             res = true;
//         }
//         return res;
//         //this.menuService.cambiarFavorito(idItem, IdApp);
//     }

//     perfilUsuario() {
//         let datos = JSON.parse(localStorage.getItem('usuario'));
//         let modal = this.modalCtrl.create(PerfilPage, datos);
//         modal.present();
//     }

//     updatePassword(): void {
//         this.modalCtrl.create(UpdatePasswordPage).present();
//     }

//     /*
//     cargarListaPreferencias() {
//       var pusuario: any = JSON.parse(localStorage.getItem("usuario"));
//       var preferenciasTransporte: any = JSON.parse(
//         localStorage.getItem("preferenciasTransporte")
//       );
  
//       if (preferenciasTransporte == null) {
//         this.listaPreferenciaTransporte = PREFERENCIAS_TRANSPORTE;
  
//         localStorage.setItem(
//           "preferenciasTransporte",
//           JSON.stringify(this.listaPreferenciaTransporte)
//         );
//       } else {
//         this.listaPreferenciaTransporte = JSON.parse(
//           localStorage.getItem("preferenciasTransporte")
//         );
//         console.log("Preferencias", this.listaPreferenciaTransporte);
//       }
//     }*/

//     guargarPreferenciasTransporte(str: string) {
//         localStorage.setItem('preferenciasTransporte', str);
//         console.log('preferencias transporte', str);
//     }

//     openModal() {
//         const infoPage = this.modalCtrl.create('InformationPage');
//         infoPage.present();
//     }
//     helpModal() {
//         const helpPage = this.modalCtrl.create('HelpPage');
//         helpPage.present();
//     }
//     policyModal() {
//         const policyPage = this.modalCtrl.create('PolicyPage');
//         policyPage.present();
//     }

//     setNotification(key: string, value: boolean): void {
//         if (value) this.oneSignal.sendTag(key, '1');
//         else this.oneSignal.deleteTag(key);
//     }

//     public static getActionRadius() {
//         return this.actionRadius;
//     }

//     public static getTransportsPreferences() {
//         return this.transportsPreferences;
//     }
// }
