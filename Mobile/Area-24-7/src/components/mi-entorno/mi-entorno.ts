import { Component, Input, ViewChild } from '@angular/core';
import { NavController, Modal } from "ionic-angular";

import { AppLayer } from '../../entities/app-layer';
import { DetalleEstacionComponent } from "../detalle-estacion/detalle-estacion";
import { ClimaDetalleComponent } from "../clima-detalle/clima-detalle";
import { MyLocationComponent } from '../my-location/my-location';
import { LayerManagerComponent } from '../layer-manager/layer-manager';
import { TerritorioProvider } from '../../providers/territorio/territorio';
import { Common } from '../../app/shared/utilidades/common';
import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { SideMenu } from '../../app/menu/page/side-menu';
import { FusionLayerComponent } from '../fusion-layer/fusion-layer';
import { GeoLayerStaticComponent } from '../geo-layer-static/geo-layer-static';
import { Geoposition } from '@ionic-native/geolocation';
import { TerritorioComponent } from '../territorio/territorio';
import { MapaComponent } from '../../app/core/mapa/mapa';
import { FichaCaracterizacionComponent } from '../ficha-caracterizacion/ficha-caracterizacion';
import { LayerProvider } from '../../providers/layer/layer';
import { Subscription } from 'rxjs/Subscription';
import { LocationUpdateProvider } from '../../providers/location-update/location-update';

@Component({
    selector: 'mi-entorno',
    templateUrl: 'mi-entorno.html'
})
export class MiEntornoComponent {

    static readonly LOCATION_UPDATES_INTERVAL: number = 5000;
    static readonly DISTANCE_TOLERANCE: number = 0;
    
    @Input() 
    private app: AppLayer;

    @ViewChild(MyLocationComponent) 
    private myLocationComponent: MyLocationComponent;

    @ViewChild(LayerManagerComponent)
    private layerManagerComponent: LayerManagerComponent;

    private locationUpdateSubscription: Subscription;
    private firstLocationCenterMap: boolean = false;

    private timerId: any;
    private actionRadius: number;
    
    constructor(private common: Common
              , private locationChange: LocationChangeProvider
              , private navCtrl: NavController
              , private layerProvider: LayerProvider
              , private locationUpdate: LocationUpdateProvider) {}
                
    ngOnInit(): void {
        console.log('MiEntornoComponent ngOnInit');
        this.turnOnLocationUpdates();
        
        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
                this.myLocationComponent.onActionRadiusChange(app.radius);
                FusionLayerComponent.emitActionRadiusChange(app.radius);
                GeoLayerStaticComponent.emitActionRadiusChange(app.radius);
            }
            /* (app: AppLayer) => {
                this.actionRadius = app.radius;
                this.myLocationComponent.onActionRadiusChange(app.radius);
                FusionLayerComponent.emitActionRadiusChange(app.radius);
            } */
        );
        // SideMenu.actionRadiusChanged$.subscribe(
        //     (actionRadius: number): void => {
                // this.actionRadius = actionRadius;
                // this.myLocationComponent.onActionRadiusChange(actionRadius);
                // FusionLayerComponent.emitActionRadiusChange(actionRadius);
        //     }
        // );
    }

    ngOnDestroy(): void {
        this.turnOffLocationUpdates();
    }

    turnOnLocationUpdates(): void {
        if (this.locationUpdateSubscription) return;

        this.locationUpdateSubscription = this.locationUpdate
            .getObservable(MiEntornoComponent.DISTANCE_TOLERANCE, MiEntornoComponent.LOCATION_UPDATES_INTERVAL)
            .subscribe(
                (latLng: { lat: number, lng: number }): void => {
                    console.log('new location update ' + JSON.stringify(latLng));
                    if (!this.firstLocationCenterMap) {
                        this.firstLocationCenterMap = true;
                        let latLng_: google.maps.LatLng = new google.maps.LatLng(latLng.lat, latLng.lng);
                        MapaComponent.mapa.setCenter(latLng_);
                    }
                    this.myLocationComponent.createUpdatePositionMarker(latLng.lat, latLng.lng);
                    FusionLayerComponent.emitLocationChange({
                          lat: latLng.lat
                        , lng: latLng.lng
                    });
                }
        );
        console.log(MiEntornoComponent.name + ' turnOnLocationUpdates ' + this.locationUpdateSubscription.closed);


        // if (!this.timerId) {
        //     this.timerId = setInterval(
        //         () => {
        //             let geoposition: Geoposition = this.locationChange.getGeoposition();
        //             console.log('setInterval tick ' + JSON.stringify(geoposition.coords));
        //             this.myLocationComponent.createUpdatePositionMarker(geoposition.coords.latitude, geoposition.coords.longitude);
        //         /*    FusionLayerComponent.emitLocationChange({
        //                     lat: geoposition.coords.latitude
        //                 , lng: geoposition.coords.longitude
        //             });*/
        //             /*GeoLayerStaticComponent.emitLocationChange({
        //                     lat: geoposition.coords.latitude
        //                 , lng: geoposition.coords.longitude
        //             });*/
        //         },
        //         TerritorioComponent.LOCATION_UPDATES_INTERVAL
        //     );
        // }
    }

    turnOffLocationUpdates(): void {
        if (this.locationUpdateSubscription) this.locationUpdateSubscription.unsubscribe();
        console.log(MiEntornoComponent.name + ' turnOffLocationUpdates ' + this.locationUpdateSubscription.closed);
    }

    /* turnOffLocationUpdates(): void {
        if (this.timerId) {
            clearInterval(this.timerId);
            this.timerId = undefined;
        } 
    } */

    onClickMyLocationButton(): void {
        let geoposition: Geoposition = this.locationUpdate.getCurrentGeoposition();

/*        let geoposition: Geoposition = this.locationChange.getGeoposition();*/
        this.myLocationComponent.createUpdatePositionMarker(geoposition.coords.latitude, geoposition.coords.longitude);
        FusionLayerComponent.emitLocationChange({
              lat: geoposition.coords.latitude
            , lng: geoposition.coords.longitude
        });
      /*  GeoLayerStaticComponent.emitLocationChange({
              lat: geoposition.coords.latitude
            , lng: geoposition.coords.longitude
        });*/
        MapaComponent.mapa.panTo(
            new google.maps.LatLng(
                  geoposition.coords.latitude
                , geoposition.coords.longitude));
        this.turnOnLocationUpdates();
    }

    /* onClickMyLocationButton(): void {
        let geoposition: Geoposition = this.locationChange.getGeoposition();

        this.myLocationComponent.createUpdatePositionMarker(geoposition.coords.latitude, geoposition.coords.longitude);
        FusionLayerComponent.emitLocationChange({
                lat: geoposition.coords.latitude
            , lng: geoposition.coords.longitude
        });

        GeoLayerStaticComponent.emitLocationChange({
                lat: geoposition.coords.latitude
            , lng: geoposition.coords.longitude
        });
        MapaComponent.mapa.panTo(new google.maps.LatLng(geoposition.coords.latitude, geoposition.coords.longitude));
        this.turnOnLocationUpdates();
    } */

    onClickPedestrian(latLng: { lat: number, lng: number }): void {
        /*
        this.common.presentLoading();
        this.territorioProvider.getFullCharacterizationCard(latLng.lat, latLng.lng).subscribe(
            (response: Response): void => {
                console.log('LayerProvider getFullCharacterizationInfo ');
                this.common.dismissLoading();
                let modal: Modal = this.modalCtrl.create(FichaCaracterizacionComponent, { characterizationCard: response.json() });
                modal.present();
            },
            (error: any) => {
                console.log('LayerProvider getFullCharacterizationInfo error ' + JSON.stringify(error));
                this.common.dismissLoading();
            }
        );*/
    }

    onDragendPedestrian(latLng: { lat: number, lng: number }): void {
        this.turnOffLocationUpdates();
        FusionLayerComponent.emitLocationChange(latLng);
        //GeoLayerStaticComponent.emitLocationChange(latLng);
    }

    // mostrarClima() {
    //     // let modal = this.modalCtrl.create(ClimaDetalleComponent, { });
    //     // modal.present();
    //     this.navCtrl.push(ClimaDetalleComponent);
    // }
}
