import { menuDinamico } from './../../../menu/provider/menu';
import { Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import { NavController, NavParams, MenuController, App } from 'ionic-angular';
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { Common } from "../../../shared/utilidades/common";
import { ModalAvistamientosPage } from './../../../avistamientos/components/modal/modal-avistamientos-page';
import { MediaCapture, MediaFile, CaptureError, CaptureImageOptions } from '@ionic-native/media-capture';
import { WsAvistamiento } from "../../provider/wsAvistamiento";
import { PosiblesViajesProvider } from '../../../shared/posibles-viajes.service';
import { VistaViajesPage } from '../../../movilidad/pages/vista-viajes/vista-viajes.page';
import { AppLayer } from '../../../../entities/app-layer';
import { MapaComponent } from '../../../core/mapa/mapa';
// import { ViewAvistamientoPage } from '../../../../pages/view-avistamiento/view-avistamiento';
import { MyLocationComponent } from '../../../../components/my-location/my-location';
import { Geoposition } from '@ionic-native/geolocation';
import { LocationChangeProvider } from '../../../../providers/location-change/location-change';
import { SideMenu } from '../../../menu/page/side-menu';
import { LayerProvider } from '../../../../providers/layer/layer';

@Component({
  selector: 'avistamientos-page',
  templateUrl: 'avistamientos-page.html',
  styles: ['modal-avistamientos-page.scss']
})

export class AvistamientosPage implements OnInit, OnDestroy {

    static readonly LOCATION_UPDATES_INTERVAL: number = 5000;
    
    @Input() 
    private app: AppLayer;

    @ViewChild(MyLocationComponent) 
    private myLocationComponent: MyLocationComponent;

    private timerId: any;
    private actionRadius: number;

    constructor(
         private common: Common,
         private layerProvider: LayerProvider
        , private locationChange: LocationChangeProvider) {
            console.log("Avistamientos load");
        }

    ngOnInit(): void {
        console.log('AvistamientosComponent ngOnInit');
        this.turnOnLocationUpdates();

        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
                this.actionRadius = app.radius;
                this.myLocationComponent.onActionRadiusChange(app.radius);
            }
        )
        // SideMenu.actionRadiusChanged$.subscribe(
        //     (actionRadius: number): void => {
        //         this.actionRadius = actionRadius;
        //         this.myLocationComponent.onActionRadiusChange(actionRadius);
        //       //  GeoLayerComponent.emitActionRadiusChange(actionRadius);
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
                    
                    /*this.myLocationComponent.createUpdatePositionMarker(geoposition.coords.latitude, geoposition.coords.longitude);
                    GeoLayerComponent.emitLocationChange({
                          lat: geoposition.coords.latitude
                        , lng: geoposition.coords.longitude
                    });*/
                },
                AvistamientosPage.LOCATION_UPDATES_INTERVAL
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
       /* GeoLayerComponent.emitLocationChange({
              lat: geoposition.coords.latitude
            , lng: geoposition.coords.longitude
        });*/
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
        //GeoLayerComponent.emitLocationChange(latLng);
    }
}