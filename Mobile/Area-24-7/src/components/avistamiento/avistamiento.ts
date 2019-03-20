import { Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import {
    NavController,
    NavParams,
    MenuController,
    ModalController,
    App,
    Modal,
    AlertController
} from 'ionic-angular';
import {
    MediaCapture,
    MediaFile,
    CaptureError,
    CaptureImageOptions
} from '@ionic-native/media-capture';
import { AppLayer } from '../../entities/app-layer';
import { MyLocationComponent } from '../my-location/my-location';
import { Common } from '../../app/shared/utilidades/common';
import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { SideMenu } from '../../app/menu/page/side-menu';
import { Geoposition } from '@ionic-native/geolocation';
import { MapaComponent } from '../../app/core/mapa/mapa';

import { AvistamientoDetailComponent } from '../avistamiento-detail/avistamiento-detail';
import { LayerProvider } from '../../providers/layer/layer';
import { OtherLayer } from '../../entities/other-layer';
import { Layer } from '../../entities/layer';
import { GeoLayerComponent } from '../geo-layer/geo-layer';
import { GeoLayerDynamicComponent } from '../geo-layer-dynamic/geo-layer-dynamic';
import { BusquedaAvistamientosComponent } from '../busqueda-avistamientos/busqueda-avistamientos';
import { FusionLayerComponent } from '../fusion-layer/fusion-layer';
import { GeoLayer } from '../../entities/geo-layer';
import { InicioPage } from '../../app/inicio/pages/inicio-page/inicio-page';
import { Subscription } from 'rxjs/Subscription';
import { LocationUpdateProvider } from '../../providers/location-update/location-update';

@Component({
    selector: 'avistamiento',
    templateUrl: 'avistamiento.html'
})
export class AvistamientoComponent implements OnInit, OnDestroy {

    static readonly LOCATION_UPDATES_INTERVAL: number = 5000;
    static readonly DISTANCE_TOLERANCE: number = 0;

    @Input()
    private app: AppLayer;

    @ViewChild(MyLocationComponent)
    private myLocationComponent: MyLocationComponent;

    @ViewChild(FusionLayerComponent)
    private fusionLayerSearchComponent: FusionLayerComponent;

    private locationUpdateSubscription: Subscription;
    private firstLocationCenterMap: boolean = false;

    private layerSearch: GeoLayer = new GeoLayer({
        id: -1,
        nombre: '',
        activo: true,
        favorito: true,
        nombreTipoCapa: 'BUSQUEDA'
    });

    constructor(
        private common: Common,
        private locationChange: LocationChangeProvider,
        public navCtrl: NavController,
        private modalCtrl: ModalController,
        private alertCtrl: AlertController,
        private layerProvider: LayerProvider
        , private locationUpdate: LocationUpdateProvider
    ) {}

    ngOnInit(): void {
        console.log('AvistamientoComponent ngOnInit');
        this.turnOnLocationUpdates();

        this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {
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
        /*
        BusquedaAvistamientosComponent.municipalityCentroidChange$.subscribe(
            (latLng: { lat: number, lng: number }): void => {
                this.turnOffLocationUpdates();                
                this.myLocationComponent.createUpdatePositionMarker(latLng.lat, latLng.lng);
                MapaComponent.mapa.panTo(new google.maps.LatLng(latLng.lat, latLng.lng));  
                FusionLayerComponent.emitLocationChange(latLng);      
                GeoLayerDynamicComponent.emitLocationChange(latLng);
            }
        );
        */
        /*
        //---Marker----
        let myLatLng = {lat:6.233065, lng:-75.592692};
        let marker = new google.maps.Marker({
        position: myLatLng,
        map: MapaComponent.mapa,
        });

        marker.addListener('click', (args: any[])=>{
            this.navCtrl.push(AvistamientoDetailComponent, { 
                avistamientoId: this.idAvistamiento,
                color: this.app.color
            });
        });
        //---Marker----*/
    }

    ngOnDestroy(): void {
        this.turnOffLocationUpdates();
    }

    turnOnLocationUpdates(): void {
        //alert('turnOnLocationUpdates ');
        if (this.locationUpdateSubscription) return;

        this.locationUpdateSubscription = this.locationUpdate
            .getObservable(AvistamientoComponent.DISTANCE_TOLERANCE, AvistamientoComponent.LOCATION_UPDATES_INTERVAL)
            .subscribe(
                (latLng: { lat: number, lng: number }): void => {
                    //alert('new Location update ' + JSON.stringify(latLng));
                   // alert('firstLocationCenterMap ' + this.firstLocationCenterMap);
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
      /*  if (!this.timerId) {
            this.timerId = setInterval(
                () => {
                   this.updateLocation();
                },
                AvistamientoComponent.LOCATION_UPDATES_INTERVAL
            );
        }*/
    }

    /*
    updateLocation (): void {
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
    }*/

    turnOffLocationUpdates(): void {
        if (this.locationUpdateSubscription) this.locationUpdateSubscription.unsubscribe();
        console.log(AvistamientoComponent.name + ' turnOffLocationUpdates ' + this.locationUpdateSubscription.closed);
        /*if (this.timerId) {
            clearInterval(this.timerId);
            this.timerId = undefined;
        }*/
    }

    onClickMyLocationButton(): void {
        let geoposition: Geoposition = this.locationUpdate.getCurrentGeoposition();
       /* let geoposition: Geoposition = this.locationChange.getGeoposition();*/
        this.myLocationComponent.createUpdatePositionMarker(
            geoposition.coords.latitude,
            geoposition.coords.longitude
        );
        FusionLayerComponent.emitLocationChange({
            lat: geoposition.coords.latitude,
            lng: geoposition.coords.longitude
        });
      /*  GeoLayerComponent.emitLocationChange({
            lat: geoposition.coords.latitude,
            lng: geoposition.coords.longitude
        });
        GeoLayerDynamicComponent.emitLocationChange({
            lat: geoposition.coords.latitude,
            lng: geoposition.coords.longitude
        });*/
        MapaComponent.mapa.panTo(
            new google.maps.LatLng(
                geoposition.coords.latitude,
                geoposition.coords.longitude
            )
        );
        this.turnOnLocationUpdates();
    }

    onClickPedestrian(latLng: { lat: number; lng: number }): void {
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

    onDragendPedestrian(latLng: { lat: number; lng: number }): void {
        this.turnOffLocationUpdates();
        FusionLayerComponent.emitLocationChange(latLng);
        GeoLayerComponent.emitLocationChange(latLng);
        GeoLayerDynamicComponent.emitLocationChange(latLng);
    }

    /*
    addFilterLayers(): void {
        let filterLayer: Layer = this.app.children.find((layer: Layer): boolean => { return layer instanceof OtherLayer && layer.layerType == 'FILTER'; });
        if (!filterLayer) { 
            this.app.children.push(this.getFilterLayer('Todos', 'FILTER', 'people'));
            this.app.children.push(this.getFilterLayer('Mis', 'FILTER', 'person'));
        }
    }*

    /*
    getFilterLayer(name: string, layerType: string, iconName: string): OtherLayer {
        let otherLayer: OtherLayer = new OtherLayer(null);
        otherLayer.id = this.app.id;
        otherLayer.name = name;
        otherLayer.layerType = layerType;
        otherLayer.active = true;
        otherLayer.urlIcon = iconName;
        return otherLayer;
    }*/

    //TODO: try to bind layer.visible to markers visible property
    onShowSearchView(): void {
        this.turnOffLocationUpdates();
        let shearchView = this.alertCtrl.create({
            title: 'Consultar avistamientos',
            message: 'Seleccione el método para realizar la búsqueda',
            buttons: [
                {
                    text: 'Por nombre',
                    handler: () => {
                        this.searchByName();
                    }
                },
                {
                    text: 'Por municipio',
                    handler: () => {
                        this.searchByMunicipality();
                    }
                }
            ]
        });
        shearchView.present();

    }

    searchByName(){
        console.log('Busqueda por nombre');
        let byName = this.alertCtrl.create({
            title: 'Búsqueda por nombre',
            inputs: [
                {
                    name: 'name',
                    placeholder: 'Nombre'
                }
            ],
            buttons: [
                {
                    text: 'Cancelar',
                    role: 'cancel'
                },
                {
                    text: 'Buscar',
                    handler: (data) => {
                        console.log('Buscar por nombre: ', data.name);
                        FusionLayerComponent.emitDeleteMarkersByLayerType('BUSQUEDA');
                        FusionLayerComponent.emitAdditionalParamsChange(data.name);
                        this.layerSearch.visible = true;
                        this.fusionLayerSearchComponent.loadIntoMap();

                    }
                }
            ]
        });
        byName.present();
    }

    searchByMunicipality(){
        console.log('Busqueda por municipio');
        let municipalities = InicioPage.municipalities;
        let byMunicipality = this.alertCtrl.create({
            title: 'Búsqueda por municipio',
            buttons: [
                {
                    text: 'Cancelar',
                    role: 'cancel'
                },
                {
                    text: 'Buscar',
                    handler: (data) => {
                        console.log('Buscar por municipio: ', data, JSON.parse(data));
                        this.onSearchByMunicipalityCentroid({ lat: JSON.parse(data).lat, lng: JSON.parse(data).lng });

                    }
                }
            ],
        });

        municipalities.forEach(municipality => {
            byMunicipality.addInput({
                type: 'radio',
                label: municipality.name,
                value: `{"lat": ${municipality.centroidLat}, "lng": ${municipality.centroidLng}}`
            });
        });

        byMunicipality.present();
    }

    /*
    cleanSearchLayer(): void {
        if (!active && layer.children) {
            layer.children.forEach((layer_: Layer): void => {
                    if (layer_ instanceof GeoLayer) {
                        layer_.visible = false;
                        if (layer_.markers != undefined) layer_.markers.forEach((marker: google.maps.Marker) => marker.setVisible(false));
                        if (layer_.polylines != undefined) layer_.polylines.forEach((polyline: google.maps.Polyline) => polyline.setVisible(false));
                        if (layer_.polygons != undefined) layer_.polygons.forEach((polygon: google.maps.Polygon) => polygon.setVisible(false));
                    } 
            });
        }
    }
*/
    onHideSearchView(turnOnLocationUpdates: boolean): void {}

    //TODO: verify if needed emitLocationChange to static and dynamic layers
    onSearchByMunicipalityCentroid(latLng: { lat: number; lng: number }): void {
        this.myLocationComponent.createUpdatePositionMarker(
            latLng.lat,
            latLng.lng
        );
        MapaComponent.mapa.panTo(
            new google.maps.LatLng(latLng.lat, latLng.lng)
        );
        FusionLayerComponent.emitLocationChange(latLng);
        GeoLayerDynamicComponent.emitLocationChange(latLng);
    }

    getNumberMaxValue(): number {
        return Number.MAX_VALUE;
    }
}
