import { Component, OnInit, Input, ViewChild, OnDestroy } from '@angular/core';

import { NavController, Modal } from 'ionic-angular';

import { Geoposition } from '@ionic-native/geolocation';

import { SideMenu } from '../../app/menu/page/side-menu';

import { FusionLayerComponent } from '../fusion-layer/fusion-layer';
import { MyLocationComponent } from '../my-location/my-location';
import { GeoLayerComponent } from '../geo-layer/geo-layer';
import { GeoLayerDynamicComponent } from '../geo-layer-dynamic/geo-layer-dynamic';
import { MapaComponent } from '../../app/core/mapa/mapa';
import { BusquedaAvistamientosComponent } from '../busqueda-avistamientos/busqueda-avistamientos';

import { LocationChangeProvider } from '../../providers/location-change/location-change';

import { AppLayer } from '../../entities/app-layer';
import { GeoLayer } from '../../entities/geo-layer';

import { Common } from '../../app/shared/utilidades/common';
import { LayerProvider } from '../../providers/layer/layer';

/**
 * Generated class for the VigiaComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'vigia',
  templateUrl: 'vigia.html'
})
export class VigiaComponent implements OnInit, OnDestroy{

  static readonly LOCATION_UPDATES_INTERVAL: number = 60000;

  @Input() 
  private app: AppLayer;

  @ViewChild(MyLocationComponent) 
  private myLocationComponent: MyLocationComponent;

  @ViewChild(FusionLayerComponent)
  private fusionLayerSearchComponent: FusionLayerComponent;

  private timerId: any;
  private actionRadius: number;
  private showingSearchView: boolean = false;
  private pedestrianLocation: { lat: number, lng: number };
  private searchResult: string;
  private layerSearch: GeoLayer = new GeoLayer({
      id: -1,
      nombre: '',
      activo: true,
      favorito: true,
      nombreTipoCapa: 'BUSQUEDA'
    });

  constructor(private common: Common
    , private locationChange: LocationChangeProvider
    , public navCtrl: NavController
    , private layerProvider: LayerProvider) {}

  ngOnInit() : void {
        this.turnOnLocationUpdates();

        this.layerProvider.currentApp.subscribe(
          (app: AppLayer) => {
              this.actionRadius = app.radius;
              this.myLocationComponent.onActionRadiusChange(app.radius);
              FusionLayerComponent.emitActionRadiusChange(app.radius);
              GeoLayerComponent.emitActionRadiusChange(app.radius);
              GeoLayerDynamicComponent.emitActionRadiusChange(app.radius);
          }
        )
        // SideMenu.actionRadiusChanged$.subscribe(
        //     (actionRadius: number): void => {
                // this.actionRadius = actionRadius;
                // this.myLocationComponent.onActionRadiusChange(actionRadius);
                // FusionLayerComponent.emitActionRadiusChange(actionRadius);
                // GeoLayerComponent.emitActionRadiusChange(actionRadius);
                // GeoLayerDynamicComponent.emitActionRadiusChange(actionRadius);
        //     }
        // );

  }

  ngOnDestroy() : void {
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
                GeoLayerComponent.emitLocationChange({
                      lat: geoposition.coords.latitude
                    , lng: geoposition.coords.longitude
                });
                GeoLayerDynamicComponent.emitLocationChange({
                      lat: geoposition.coords.latitude
                    , lng: geoposition.coords.longitude 
                });
            },
            VigiaComponent.LOCATION_UPDATES_INTERVAL
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
  GeoLayerComponent.emitLocationChange({
        lat: geoposition.coords.latitude
      , lng: geoposition.coords.longitude
  });
  GeoLayerDynamicComponent.emitLocationChange({
        lat: geoposition.coords.latitude
      , lng: geoposition.coords.longitude
  });
  MapaComponent.mapa.panTo(new google.maps.LatLng(geoposition.coords.latitude, geoposition.coords.longitude));
  this.turnOnLocationUpdates();
}

onDragendPedestrian(latLng: { lat: number, lng: number }): void {
  this.turnOffLocationUpdates();
  FusionLayerComponent.emitLocationChange(latLng);
  GeoLayerComponent.emitLocationChange(latLng);
  GeoLayerDynamicComponent.emitLocationChange(latLng);
}

onShowSearchView(): void {
  this.turnOffLocationUpdates();
  let modal: Modal = this.common.createModal(BusquedaAvistamientosComponent);
  modal.onDidDismiss((data: any, role: string): void => {
      if (data) {
          if (data.name) {
              FusionLayerComponent.emitAdditionalParamsChange(data.name);
              this.layerSearch.visible = true;
              this.fusionLayerSearchComponent.loadIntoMap();
          }
          else this.onSearchByMunicipalityCentroid({ lat: data.lat, lng: data.lng });
      }
  });
  modal.present();
}

onSearchByMunicipalityCentroid(latLng: { lat: number, lng: number }): void {
  this.myLocationComponent.createUpdatePositionMarker(latLng.lat, latLng.lng);
  MapaComponent.mapa.panTo(new google.maps.LatLng(latLng.lat, latLng.lng));    
  FusionLayerComponent.emitLocationChange(latLng);    
  GeoLayerDynamicComponent.emitLocationChange(latLng);
}

getNumberMaxValue(): number {
  return Number.MAX_VALUE;
}

}
