import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { Geoposition } from '@ionic-native/geolocation';
import { MapStyle } from '../../app/shared/utilidades/mapStyle';
import { Common } from '../../app/shared/utilidades/common';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { FusionLayerComponent } from '../fusion-layer/fusion-layer';
import { MyLocationComponent } from '../my-location/my-location';
import { AppLayer } from '../../entities/app-layer';
import { LayerProvider } from '../../providers/layer/layer';
import { GeoLayerComponent } from '../geo-layer/geo-layer';
import { GeoLayerDynamicComponent } from '../geo-layer-dynamic/geo-layer-dynamic';
import { MapaComponent } from '../../app/core/mapa/mapa';

declare var google: any;
@Component({
    selector: 'map-select-location',
    templateUrl: 'map-select-location.html'
})
export class MapSelectLocationComponent implements OnInit {
    @ViewChild(MyLocationComponent) 
    private myLocationComponent: MyLocationComponent;
  
    @ViewChild(FusionLayerComponent)
    private fusionLayerSearchComponent: FusionLayerComponent;
    private actionRadius: number;
    private color: any
    private geoposition: Geoposition;
    private amvaRadius: number = 40000;
    private fromOriginPage: string;
    private autocompleteItemsUbicacion: any[] = [];
    private GoogleAutocomplete: any;
    private ubication: string;
    private geocoder: any;
    private static map: google.maps.Map;
    private centerOfMap = new google.maps.LatLng();
    private mapOptions: any = {}
    private centerAMVA: any = { lat: 6.273357, lng: -75.46679 };

    private limitAMVA: any = new google.maps.LatLngBounds(
        new google.maps.LatLng(6.273357, -75.46679),
        new google.maps.LatLng(5, 986228, -75, 619267));

    constructor(private common: Common, private params: NavParams,
        private zone: NgZone,
        private layerProvider: LayerProvider,
        private locationChange: LocationChangeProvider,
        ) {
        this.fromOriginPage = this.params.data.desde;
        this.geocoder = new google.maps.Geocoder;
        this.GoogleAutocomplete = new google.maps.places.AutocompleteService();
        this.geoposition = this.locationChange.getGeoposition();
        this.centerOfMap = new google.maps.LatLng(this.geoposition.coords.latitude, this.geoposition.coords.longitude);
    }

    ngOnInit(): void {
        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
                console.log('la app', app)
                this.actionRadius = app.radius;
                this.color = app.color;
                this.myLocationComponent.onActionRadiusChange(app.radius);
                FusionLayerComponent.emitActionRadiusChange(app.radius);
                GeoLayerComponent.emitActionRadiusChange(app.radius);
                GeoLayerDynamicComponent.emitActionRadiusChange(app.radius);
            }
          )
        this.geoposition = this.locationChange.getGeoposition();
        this.centerOfMap = new google.maps.LatLng(this.geoposition.coords.latitude, this.geoposition.coords.longitude);
        this.mapOptions = {
            zoom: 14,
            center: this.centerOfMap,
            disableDefaultUI: true,
            styles: MapStyle.estiloMapa,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        this.setupMap();
        this.setupMarker();
    }

    setupMap(): void {
        MapSelectLocationComponent.map = new google.maps.Map(
            document.getElementById('map-selection')
            , this.mapOptions);
        google.maps.event.addListener(MapSelectLocationComponent.map, 'bounds_changed', function () {
        });
    }

    setupMarker(): void {
        let marker = new google.maps.Marker({
            position: this.centerOfMap,
            map: MapSelectLocationComponent.map,
            icon: {
                scaledSize: new google.maps.Size(50, 50),
                url: 'assets/mapa/ubicacion.svg'
            },
            animation: google.maps.Animation.DROP,
        });
        marker.bindTo('position', MapSelectLocationComponent.map, 'center');
    }

    selectLocation(): void {
        let location = MapSelectLocationComponent.map.getCenter();
        let geoposition: Geoposition = {
            coords: {
                accuracy: -1,
                altitude: -1,
                altitudeAccuracy: -1,
                heading: -1,
                latitude: location.lat(),
                longitude: location.lng(),
                speed: -1
            },
            timestamp: -1
        };
        this.common.dismissModal(geoposition, 'OK');
    }

    emptyDirection(){
        this.ubication = ''
    }


    searchDirection(address: any): void {
        //Buscamos la direccion destino ingresada por el usuario
        this.resultadosDireccion();
    }

    //Lista de resultados de direcciones
    resultadosDireccion() {
        if (this.ubication == "" || this.ubication.length < 2) {
            this.autocompleteItemsUbicacion = [];
            return;
        }
        var circle = new google.maps.Circle({
            center: this.centerAMVA,
            radius: this.amvaRadius
        });
        this.GoogleAutocomplete.getPlacePredictions(
            {
                input: this.ubication,
                componentRestrictions: { country: "co" },
                bounds: this.limitAMVA,
                strictBounds: true,
            }
            ,
            (predictions: any) => {
                this.autocompleteItemsUbicacion = [];
                this.zone.run(() => {
                    if (predictions != null) {
                        predictions.forEach(prediction => {
                            this.autocompleteItemsUbicacion.push(prediction);

                        });
                    }
                });
            }
        );
    }

    selectDirection(item: any) {
        //se ubica el marker el la direccion seleccionada por el usuario
        this.ubication = item.description;
        this.autocompleteItemsUbicacion = [];

        this.geocoder.geocode({ placeId: item.place_id }, (results, status) => {
            if (status === "OK" && results[0]) {
                let position = {
                    lat: results[0].geometry.location.lat(),
                    lng: results[0].geometry.location.lng(),
                };
                this.centerOfMap = new google.maps.LatLng(position.lat, position.lng);
                this.mapOptions.center = this.centerOfMap;
                this.mapOptions.zoom = 17;
                this.setupMap();
                this.setupMarker();
            }
        });
    }

    closeModal(){
        this.common.dismissModal();
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
        MapSelectLocationComponent.map.panTo(new google.maps.LatLng(geoposition.coords.latitude, geoposition.coords.longitude));
      }
}
