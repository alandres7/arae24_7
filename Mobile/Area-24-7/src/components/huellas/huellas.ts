import { Component, Input, ViewChild } from '@angular/core';

import { AppLayer } from '../../entities/app-layer';
import { MyLocationComponent } from '../my-location/my-location';
import { LayerManagerComponent } from '../layer-manager/layer-manager';
import { TerritorioProvider } from '../../providers/territorio/territorio';
import { Common } from '../../app/shared/utilidades/common';
import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { SideMenu } from '../../app/menu/page/side-menu';
import { FusionLayerComponent } from '../fusion-layer/fusion-layer';
import { Geoposition } from '@ionic-native/geolocation';
import { MapaComponent } from '../../app/core/mapa/mapa';
import { LayerProvider } from '../../providers/layer/layer';

@Component({
    selector: 'huellas',
    templateUrl: 'huellas.html'
})
export class HuellasComponent {

    static readonly LOCATION_UPDATES_INTERVAL: number = 5000;

    @Input() 
    public app: AppLayer;

    @ViewChild(MyLocationComponent) 
    private myLocationComponent: MyLocationComponent;

    @ViewChild(LayerManagerComponent)
    private layerManagerComponent: LayerManagerComponent;

    private timerId: any;
    private actionRadius: number;
    
    constructor(private territorioProvider: TerritorioProvider
                , private common: Common
                , private locationChange: LocationChangeProvider
                , private layerProvider: LayerProvider) 
    {}
                
    ngOnInit(): void {
        console.log('HuellasComponent ngOnInit');
        this.turnOnLocationUpdates();

        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
                this.actionRadius = app.radius;
                this.myLocationComponent.onActionRadiusChange(app.radius);
                FusionLayerComponent.emitActionRadiusChange(app.radius);
            }
        )
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
        if (!this.timerId) {
            this.timerId = setInterval(
                () => {
                    let geoposition: Geoposition = this.locationChange.getGeoposition();
                    console.log('setInterval tick ' + JSON.stringify(geoposition.coords));
                    this.myLocationComponent.createUpdatePositionMarker(geoposition.coords.latitude, geoposition.coords.longitude);
                    FusionLayerComponent.emitLocationChange({
                          lat: geoposition.coords.latitude
                        , lng: geoposition.coords.longitude
                    });
                  
                },
                HuellasComponent.LOCATION_UPDATES_INTERVAL
            );
        }
    }

    turnOffLocationUpdates(): void {
        if (this.timerId) {
            clearInterval(this.timerId);
            this.timerId = undefined;
        } 
    }

    onClickMyLocationButton(): void {
        let geoposition: Geoposition = this.locationChange.getGeoposition();
        this.myLocationComponent.createUpdatePositionMarker(geoposition.coords.latitude, geoposition.coords.longitude);
        FusionLayerComponent.emitLocationChange({
                lat: geoposition.coords.latitude
            , lng: geoposition.coords.longitude
        });
        MapaComponent.mapa.panTo(new google.maps.LatLng(geoposition.coords.latitude, geoposition.coords.longitude));
        this.turnOnLocationUpdates();
    }

    onClickPedestrian(latLng: { lat: number, lng: number }): void {
        /*this.common.presentLoading();
        
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
    }
}
