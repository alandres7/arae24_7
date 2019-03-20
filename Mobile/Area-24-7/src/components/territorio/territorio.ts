import { Component, OnInit, Input, ViewChild, ViewChildren, ContentChild } from '@angular/core';
import { Response } from '@angular/http';

import { Modal, NavParams } from 'ionic-angular';

import { LayerProvider } from '../../providers/layer/layer';

import { AppLayer } from '../../entities/app-layer';
import { GeoLayer } from '../../entities/geo-layer';
import { Common } from '../../app/shared/utilidades/common';
import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { Geoposition } from '@ionic-native/geolocation';
import { MyLocationComponent } from '../my-location/my-location';
import { SideMenu } from '../../app/menu/page/side-menu';
import { FichaCaracterizacionComponent } from '../../components/ficha-caracterizacion/ficha-caracterizacion';
import { LayerManagerComponent } from '../layer-manager/layer-manager';
import { LayerComponent } from '../layer/layer';
import { MapaComponent } from '../../app/core/mapa/mapa';
import { GeoLayerStaticComponent } from '../geo-layer-static/geo-layer-static';
import { TerritorioProvider } from '../../providers/territorio/territorio';
import { FusionLayerComponent } from '../fusion-layer/fusion-layer';
import { LocationUpdateProvider } from '../../providers/location-update/location-update';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'territorio',
    templateUrl: 'territorio.html'
})
export class TerritorioComponent implements OnInit {

    static readonly LOCATION_UPDATES_INTERVAL: number = 5000;
    static readonly DISTANCE_TOLERANCE: number = 0;

    @Input()
    private app: AppLayer;

    @ViewChild(MyLocationComponent) 
    private myLocationComponent: MyLocationComponent;

    @ViewChild(LayerManagerComponent)
    private layerManagerComponent: LayerManagerComponent;

    private locationUpdateSubscription: Subscription
    private firstLocationCenterMap: boolean = false;

    private layerSearch: GeoLayer = new GeoLayer({
          id: -1
        , nombre: ''
        , activo: true
        , favorito: true
        , nombreTipoCapa: 'BUSQUEDA'
    });
    
    constructor(private territorioProvider: TerritorioProvider
              , private common: Common
              , private locationChange: LocationChangeProvider
              , private locationUpdate: LocationUpdateProvider
              , private navParams: NavParams
              , private layerProvider: LayerProvider) 
    {
        this.app = navParams.get('app');
    }
              
    ngOnInit(): void {
        console.log('TerritorioComponent ngOnInit');
        this.turnOnLocationUpdates();

        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
                this.myLocationComponent.onActionRadiusChange(app.radius);
                FusionLayerComponent.emitActionRadiusChange(app.radius);
                GeoLayerStaticComponent.emitActionRadiusChange(app.radius);
            }
        )
    }

    ngOnDestroy(): void {
        this.turnOffLocationUpdates();
    }

    turnOnLocationUpdates(): void {
        if (this.locationUpdateSubscription) return;

        this.locationUpdateSubscription = this.locationUpdate
            .getObservable(TerritorioComponent.DISTANCE_TOLERANCE, TerritorioComponent.LOCATION_UPDATES_INTERVAL)
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
        console.log(TerritorioComponent.name + ' turnOnLocationUpdates ' + this.locationUpdateSubscription.closed);
    }

    turnOffLocationUpdates(): void {
        if (this.locationUpdateSubscription) this.locationUpdateSubscription.unsubscribe();
        console.log(TerritorioComponent.name + ' turnOffLocationUpdates ' + this.locationUpdateSubscription.closed);
    }


    /*
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
                    GeoLayerStaticComponent.emitLocationChange({
                          lat: geoposition.coords.latitude
                        , lng: geoposition.coords.longitude
                    });
                },
                TerritorioComponent.LOCATION_UPDATES_INTERVAL
            );
        }
    }

    turnOffLocationUpdates(): void {
        if (this.timerId) {
            clearInterval(this.timerId);
            this.timerId = undefined;
        } 
    }*/

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

    onClickPedestrian(latLng: { lat: number, lng: number }): void {
        this.common.presentLoading();
        this.territorioProvider.getFullCharacterizationCard(latLng.lat, latLng.lng).subscribe(
            (response: Response): void => {
                console.log(TerritorioComponent.name + ' onClickPedestrian getFullCharacterizationInfo ' + JSON.stringify(response));
                this.common.dismissLoading();
                let modal: Modal = this.common.createModal(FichaCaracterizacionComponent, { characterizationCard: response.json() });
                modal.present();
            },
            (error: any) => {
                console.log(TerritorioComponent.name + ' onClickPedestrian getFullCharacterizationInfo error ' + JSON.stringify(error));
            }
        );
    }

    onDragendPedestrian(latLng: { lat: number, lng: number }): void {
        this.turnOffLocationUpdates();
        FusionLayerComponent.emitLocationChange(latLng);
        //GeoLayerStaticComponent.emitLocationChange(latLng);
    }

    onClickGoogleSuggestion(latLng: { lat: number, lng: number }): void {
        this.turnOffLocationUpdates();        
        this.myLocationComponent.createUpdatePositionMarker(latLng.lat, latLng.lng);
        MapaComponent.mapa.panTo(new google.maps.LatLng(latLng.lat, latLng.lng));   
        FusionLayerComponent.emitLocationChange(latLng);     
        //GeoLayerStaticComponent.emitLocationChange(latLng);
    }

    onClickApiSuggestion(id: number): void {
        this.turnOffLocationUpdates();        
        FusionLayerComponent.emitFocusOnGeometry(id);
        //GeoLayerStaticComponent.emitFocusOnGeometry(id);
    }
}
