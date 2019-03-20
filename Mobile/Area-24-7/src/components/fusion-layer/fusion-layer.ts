import {
    Component,
    EventEmitter,
    OnInit,
    OnDestroy,
    Input,
    Output,
    ViewChild
} from '@angular/core';
import { Response } from '@angular/http';

import { App, NavController, Modal } from 'ionic-angular';

import { Layer } from '../../entities/layer';
import { GeoLayer } from '../../entities/geo-layer';

import { LayerProvider } from '../../providers/layer/layer';
import { MapaComponent } from '../../app/core/mapa/mapa';
import { GoogleMaps } from '../../app/shared/utilidades/googleMaps';
import { Common } from '../../app/shared/utilidades/common';
import { PosiblesViajesProvider } from '../../app/shared/posibles-viajes.service';
import { VistaViajesPage } from '../../app/movilidad/pages/vista-viajes/vista-viajes.page';
import { SideMenu } from '../../app/menu/page/side-menu';
import { Subject } from 'rxjs/Subject';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { LayerComponent } from '../layer/layer';
import { AvistamientoDetailComponent } from '../avistamiento-detail/avistamiento-detail';
import { DetalleEstacionComponent } from "../detalle-estacion/detalle-estacion";
import { ClimaDetalleComponent } from "../clima-detalle/clima-detalle";
import { BusquedaAvistamientosComponent } from '../busqueda-avistamientos/busqueda-avistamientos';
import { AppLayer } from '../../entities/app-layer';
import { TerritorioComponent } from '../territorio/territorio';
import { TerritorioDetailComponent } from '../territorio-detail/territorio-detail';
import { EstacionDisfrutameProvider } from '../../providers/estacion-disfrutame/estacion-disfrutame';
import { PostconsumoMidemeProvider } from '../../providers/postconsumo-mideme/postconsumo-mideme';
import { CuidameDetailComponent } from '../cuidame-detail/cuidame-detail';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'fusion-layer',
    templateUrl: 'fusion-layer.html'
})
export class FusionLayerComponent extends LayerComponent
    implements OnInit, OnDestroy {
    @Input()
    protected layer: GeoLayer;

    @Input()
    private fixedActionRadius?: number;

    //TODO: generalize to allow multiple additional params

    private static actionRadiusChange = new BehaviorSubject<number>(0);
    private static locationChange = new BehaviorSubject<{
        lat: number;
        lng: number;
    }>({ lat: 0, lng: 0 });
    private static focusOnGeometry = new Subject<number>();
    private static additionalParamsChange = new Subject<string>();
    private static deleteAllMarkers = new Subject<any>();
    private static deleteMarkersByLayerType = new Subject<any>();

    protected static actionRadiusChanged$ = FusionLayerComponent.actionRadiusChange.asObservable();
    protected static locationChanged$ = FusionLayerComponent.locationChange.asObservable();
    private static focusOnGeometry$ = FusionLayerComponent.focusOnGeometry.asObservable();
    private static additionalParamsChange$ = FusionLayerComponent.additionalParamsChange.asObservable();
    private static deleteAllMarkers$ = FusionLayerComponent.deleteAllMarkers.asObservable();
    private static deleteMarkersByLayerType$ = FusionLayerComponent.deleteMarkersByLayerType.asObservable();
    private static focusedGeometry: google.maps.Marker;

    static emitActionRadiusChange(actionRadius: number): void {
        FusionLayerComponent.actionRadiusChange.next(actionRadius);
    }

    static emitLocationChange(latLng: { lat: number; lng: number }): void {
        FusionLayerComponent.locationChange.next(latLng);
    }

    static emitFocusOnGeometry(id: number): void {
        FusionLayerComponent.focusOnGeometry.next(id);
    }

    static emitAdditionalParamsChange(params: string): void {
        FusionLayerComponent.additionalParamsChange.next(params);
    }

    static emitDeleteAllMarkers(): void {
        FusionLayerComponent.deleteAllMarkers.next(''); //Probar vacio
    }

    static emitDeleteMarkersByLayerType(layerType: string): void {
        FusionLayerComponent.deleteMarkersByLayerType.next(layerType);
    }

    protected static currentLat: number;
    protected static currentLng: number;
    @Input() 
    private app: AppLayer;

    protected actionRadius: number;
    protected additionalParams: string;
    protected isLoading: boolean = false;

    private static currentInfoWindow: google.maps.InfoWindow;

    private currentAppSubscription: Subscription;
    private locationChangedSubscription: Subscription;
    private focusOnGeometrySubscription: Subscription;
    private aditionalParamsChangeSubscription: Subscription;
    private deleteAllMarkersSubscription: Subscription;
    private deleteMarkersByLayerTypeSubscription: Subscription;

    constructor(
        private layerProvider: LayerProvider,
        private navCtrl: NavController,
        private googleMaps: GoogleMaps,
        private posiblesViajesProvider: PosiblesViajesProvider,
        private estacionDisfrutameProvider: EstacionDisfrutameProvider,
        private postconsumoMidemeProvider: PostconsumoMidemeProvider,
        private appCtrl: App
    ) {
        super();
    }

    ngOnInit(): void {

        if (this.fixedActionRadius) this.actionRadius = this.fixedActionRadius;
        
        this.currentAppSubscription = this.layerProvider.currentApp.subscribe(
            (app: AppLayer) => {

                /*
                this.common.capas = app.children;
                if(this.common.capas != undefined){
                    this.common.capas.forEach(element => {
                        if(element.layerType == 'MAPA' || element.layerType == 'AVISTAMIENTO' || element.layerType == 'MIS PUBLICACIONES' || element.layerType == 'BUSQUEDA'){
                            if(this.layer.id != element.id){
                                if(element.markers != undefined){
                                    element.markers.forEach((marker: google.maps.Marker) => {
                                        marker.setMap(null);
                                    });
                                }
                                    if(element.polygons != undefined){
                                        element.polygons.forEach(
                                            (polygon: google.maps.Polygon) => {
                                                polygon.setMap(null);
                                            });
                                    }
                            }
                        }
                    });
                }
*/
                if (this.layer.layerType === "BUSQUEDA") {
                    this.actionRadius = Number.MAX_VALUE;
                }else {
                    this.actionRadius = app.radius;
                }
                if (this.layer.layerType == 'AVISTAMIENTO') {
                    if (this.layer.visible) this.loadIntoMap();
                }
                else this.setMarkersVisibleByRadius();
            }
        )
        // SideMenu.actionRadiusChanged$.subscribe(
        //     (actionRadius: number): void => {
                // if (this.layer.layerType === "BUSQUEDA") {
                //     this.actionRadius = Number.MAX_VALUE;
                // }else {
                //     this.actionRadius = actionRadius;
                // }
                // if (this.layer.layerType == 'AVISTAMIENTO') {
                //     if (this.layer.visible) this.loadIntoMap();
                // }
                // else this.setMarkersVisibleByRadius();
        //     }
        // );
        this.locationChangedSubscription = FusionLayerComponent.locationChanged$.subscribe(
            (latLng: { lat: number; lng: number }) => {
                FusionLayerComponent.currentLat = latLng.lat;
                FusionLayerComponent.currentLng = latLng.lng;
                if (this.layer.layerType == 'AVISTAMIENTO'
                    || this.layer.layerType == 'MIS PUBLICACIONES')
                {
                    if (this.layer.visible) this.loadIntoMap();
                }
                else this.setMarkersVisibleByRadius();
            }
        );
        this.focusOnGeometrySubscription = FusionLayerComponent.focusOnGeometry$.subscribe((id: number) => {
            let markerFound: boolean = false;
            if (this.layer.markers) {
                console.log('layerMarkers', this.layer.markers)
                let marker: google.maps.Marker = this.layer.markers.find(
                    (marker_: google.maps.Marker) => marker_['id'] == id
                );
                //todo: make this marker part of the search layer?

                if (marker) {
                    //debugger;
                    markerFound = true;
                    marker.setVisible(true);
                    if (FusionLayerComponent.focusedGeometry) FusionLayerComponent.focusedGeometry.setVisible(false);
                    FusionLayerComponent.focusedGeometry = marker;
                    //marker['alwaysVisible'] = true;
                    MapaComponent.mapa.panTo(marker.getPosition());
                    this.getGeometryInfoAndShowInfoWindow(id, marker.getPosition());
                }
            }
            if (!markerFound && this.layer.polygons) {
                let polygon: google.maps.Polygon = this.layer.polygons.find(
                    (polygon_: google.maps.Polygon) => polygon_['id'] == id
                );
                if (polygon) {
                    let latLng = polygon.getPath().getArray()[0];
                    MapaComponent.mapa.panTo(latLng);
                    this.getGeometryInfoAndShowInfoWindow(id, latLng);
                }
            }
        });
        this.aditionalParamsChangeSubscription = FusionLayerComponent.additionalParamsChange$.subscribe(
            (name: string): void => {
                this.additionalParams = name;
            }
        );

        this.deleteAllMarkersSubscription = FusionLayerComponent.deleteAllMarkers$.subscribe( foo => {
            if (this.layer.layerType === 'BUSQUEDA' && this.layer.markers) {
                this.layer.markers.forEach((marker: google.maps.Marker) => {
                    marker.setMap(null);
                });
            }
        });

        this.deleteMarkersByLayerTypeSubscription = FusionLayerComponent.deleteMarkersByLayerType$.subscribe( layerType => {
            if (this.layer.layerType === layerType && this.layer.markers) {
                this.layer.markers.forEach((marker: google.maps.Marker) => {
                    marker.setMap(null);
                });
            }
        });
    }

    ngOnDestroy(): void {
        this.currentAppSubscription.unsubscribe();
        this.locationChangedSubscription.unsubscribe();
        this.focusOnGeometrySubscription.unsubscribe();
        this.aditionalParamsChangeSubscription.unsubscribe();
        this.deleteAllMarkersSubscription.unsubscribe();
        this.deleteMarkersByLayerTypeSubscription.unsubscribe();

        if (FusionLayerComponent.currentInfoWindow) FusionLayerComponent.currentInfoWindow.close();
   
        /*
        this.layer.visible = false;
        if (this.layer.markers) {
            this.layer.markers.forEach((marker: google.maps.Marker) => {
                marker.setMap(null);
            });
        }
        if (this.layer.polygons) {
            this.layer.polygons.forEach((polygon: google.maps.Polygon) => {
                polygon.setMap(null);
            });
        }*/
    }

    getLegendColor(): string {
        switch (this.layer.id) {
            case 21: //Áreas protegidas
                return '#F9A52B';

            case 66: //Retiros de quebradas
                return '#1469AA';

            case 13: //Tratamiento de suelos Urbanos
                return '#5D3B89';

            case 64: //Uso de suelos Urbanos
                return '#3B294C';

            case 122: //Uso de sulos Rurales
                return '#85B727';

            case 124: //Tratamiento de suelos Rurales
                return '#5D8407';

            default:
                return '#000000';
        }
    }

    getSquarePolygonCenter(polygon: google.maps.Polygon): google.maps.LatLng {
        let path = polygon.getPath().getArray();
        let minLat: number = path[0].lat();
        let maxLat: number = path[0].lat();
        let minLng: number = path[0].lng();
        let maxLng: number = path[0].lng();
        for (let i = 1; i < path.length; i++) {
            if (path[i].lat() > maxLat) maxLat = path[i].lat();
            else if (path[i].lat() < minLat) minLat = path[i].lat();

            if (path[i].lng() > maxLng) maxLng = path[i].lng();
            else if (path[i].lng() < minLng) minLng = path[i].lng();
        }

        return new google.maps.LatLng(
            minLat + (maxLat - minLat) / 2,
            minLng + (maxLng - minLng) / 2
        );
    }

    //TODO: move all these layer dependent logic out from here
    onTapLayer(): void {
        //debugger;
        this.cleanOtherLayers();
        this.active = !this.active;

        if (this.layer.visible) {
            this.layer.visible = false;
            this.setVisible(false);
            console.log('activeLayers ' + JSON.stringify(this.common.activeLayers));
            //debugger;
            this.common.activeLayers.ids = this.common.activeLayers.ids.filter(
                id => id != this.layer.id
            );
            console.log('activeLayers after filter ' + JSON.stringify(this.common.activeLayers));

        }
        else if (this.layer.markers == undefined
            && this.layer.polylines == undefined
            && this.layer.polygons == undefined)
        {
            this.layer.visible = true;
            this.loadIntoMap();
            //debugger;
            this.common.activeLayers.level = this.layerLevel;
            this.common.activeLayers.ids.push(this.layer.id);
        }
        else {
            //debugger;
            this.setGeometriesMap();
            this.layer.visible = true;
            this.setVisible(true);
            this.common.activeLayers.ids.push(this.layer.id);
        }
    }

    cleanOtherLayers(): void {
        let idsThatCleanOtherLayers: number[] = [
            185, //Avistamientos - Buscador
            5, //Avistamientos - Mis avistamientos
            10, //Mi Entorno - Calidad del aire
            11, //Mi Entorno - Monitoreo hídrico
            12, //Mi Entorno - Clima
            13 //Mi Entorno - Suelos
        ];
        let layerToClearId: number = idsThatCleanOtherLayers.find(
            (element: number): boolean => {
                return element === this.layer.id;
            }
        );
        if (this.layerLevel == 'CAPA' && layerToClearId && !this.layer.visible) {
            // emit to clean all other layers
            this.tapLayer.emit(this.layer);
        }
    }

    setGeometriesMap(): void {
        if (this.layer.markers)
            this.layer.markers.forEach(
                (marker: google.maps.Marker): void => marker.setMap(MapaComponent.mapa)
            );
        if (this.layer.polygons)
            this.layer.polygons.forEach(
                (polygon: google.maps.Polygon): void =>
                    polygon.setMap(MapaComponent.mapa)
            );
    }

    loadIntoMap(): void {
        this.isLoading = true;
        this.layerProvider.getGeometries(this.layer.layerType, {
                layerLevel: this.layerLevel,
                layerId: this.layer.id,
                lat: FusionLayerComponent.currentLat,
                lng: FusionLayerComponent.currentLng,
                actionRadius: this.actionRadius,
                name: this.additionalParams
            })
            .subscribe(
            (response: Response) => {
                console.log(FusionLayerComponent.name + ' loadIntoMap getGeometries ' + JSON.stringify(response));
                // debugger;
                if (this.layer.layerType == 'AVISTAMIENTO' || this.layer.layerType == 'MIS PUBLICACIONES')
                    this.multipleTimesLoad(response);
                else this.oneTimeLoad(response);

                this.setVisible(true);
                this.isLoading = false;
            },
            (error: any) => {
                console.log(FusionLayerComponent.name + ' loadIntoMap getGeometries error ' + JSON.stringify(error));
                this.isLoading = false;
            }
        );
    }

    oneTimeLoad(response: Response): void {
        let jsonResponse = response.json(); 
        let color: string = this.getLegendColor();
        if (jsonResponse.markersPoint) {
            this.layer.markers = jsonResponse.markersPoint.map((item: any) => {
                let marker: google.maps.Marker = this.apiMarkerToGoogleMarker(item);
                marker.setMap(MapaComponent.mapa);
                return marker;
            });
        }
        if (jsonResponse.markersPolygon) {
            this.layer.polygons = jsonResponse.markersPolygon.map((item: any) =>
                this.apiPolygonToGooglePolygon(item, color)
            );
        }
    }

    multipleTimesLoad(response: Response): void {
        let x = response.json();
        let newMarkers: google.maps.Marker[] = response.json()
            .markersPoint.map((item: any) => this.apiMarkerToGoogleMarker(item));
        let oldMarkers: google.maps.Marker[] = this.layer.markers || [];
        this.layer.markers = new Array<google.maps.Marker>();
        newMarkers.forEach(
            (newMarker: google.maps.Marker): void => {
                let intersectionMarkerIndex: number = oldMarkers.findIndex(
                    (oldMarker: google.maps.Marker): boolean => {
                        return (newMarker['id'] == oldMarker['id']
                            && newMarker.getIcon().valueOf()['url'] == oldMarker.getIcon().valueOf()['url']); //ensure that markers with icon change are reloaded
                    }
                );
                if (intersectionMarkerIndex != -1) {
                    this.layer.markers.push(oldMarkers[intersectionMarkerIndex]);
                    oldMarkers.splice(intersectionMarkerIndex, 1);
                } else {
                    newMarker.setMap(MapaComponent.mapa);
                    this.layer.markers.push(newMarker);
                }
            }
        );
        oldMarkers.forEach(
            (marker: google.maps.Marker): void => marker.setMap(null)
        );
    }

    setVisible(visible: boolean): void {
        if (this.layer.markers != undefined) {
            if (visible) this.setMarkersVisibleByRadius();
            else {
                this.layer.markers.forEach((marker: google.maps.Marker) =>
                    marker.setVisible(false)
                );
                if (FusionLayerComponent.focusedGeometry) FusionLayerComponent.focusedGeometry.setVisible(true);
            }
        }
        if (this.layer.polylines != undefined)
            this.layer.polylines.forEach((polyline: google.maps.Polyline) =>
                polyline.setVisible(visible)
            );
        if (this.layer.polygons != undefined)
            this.layer.polygons.forEach((polygon: google.maps.Polygon) =>
                polygon.setVisible(visible)
            );
        //if (FusionLayerComponent.currentInfoWindow) FusionLayerComponent.currentInfoWindow.close();
    }

    setMarkersVisibleByRadius(): void {
        if (this.layer.markers != undefined && this.layer.visible) {
            let originLatLng: google.maps.LatLng = new google.maps.LatLng(
                FusionLayerComponent.currentLat,
                FusionLayerComponent.currentLng
            );
            this.layer.markers.forEach((marker: google.maps.Marker) => {
                //if (marker['alwaysVisible']) marker.setVisible(true);
                //else {
                    let distance = google.maps.geometry.spherical.computeDistanceBetween(
                        originLatLng,
                        marker.getPosition()
                    );
                    //TODO: verify if we have a fixed action radius 
                    marker.setVisible(distance <= this.actionRadius);
                //}
            });
            if (FusionLayerComponent.focusedGeometry) FusionLayerComponent.focusedGeometry.setVisible(true);
        }
    }

    getGeometryInfoAndShowInfoWindow(id: number, latLng: any): void {
        this.layerProvider.getMarkerInfo(id).subscribe(
            (response: Response) => {
                console.log(FusionLayerComponent.name + ' getGeometryInfoAndShowInfoWindow getMarkerInfo ' + JSON.stringify(response));
                this.showInfoWindow(response.json(), latLng);
            },
            (error: any) =>
            console.log(FusionLayerComponent.name + ' getGeometryInfoAndShowInfoWindow getMarkerInfo error ' + JSON.stringify(error))
        );
    }

    getPlaceInfoAndShowInfoWindow(id: number, latLng: any, tipoCentro: string): void {

        let tipo = tipoCentro;
        let data;
        let icono, nombre, direccion, horario, telefono, email, descripcion;
        let formatHorario, formatDireccion;

        this.postconsumoMidemeProvider.getDetail(id).subscribe(
            (response: Response) => {
                //debugger;
                console.log('getMarkerInfo ' + JSON.stringify(response));

                data = response;
                data.forEach((element) => {
                    let key = Object.keys(element);

                    if (key[0] === 'icono') icono = element[key[0]]
                    else if (key[0] === 'Nombre') nombre = element[key[0]]
                    else if (key[0] === 'Dirección') direccion = element[key[0]]
                    else if (key[0] === 'Horario') horario = element[key[0]]
                    else if (key[0] === 'Teléfono') telefono = element[key[0]]
                    else if (key[0] === 'Email') email = element[key[0]]
                    else if (key[0] === 'Descripción') descripcion = element[key[0]]
                });

                // debugger;
                // formatDireccion = direccion.split(', ');
                // direccion = formatDireccion[0];
                direccion = direccion.split(', ', 1);

                horario = horario.replace('LUN,MAR,MIE,JUE,VIE 08:00:00 a 17:00:00', 'Lun-Vie 8:00 a.m - 5:00 p.m ');

                let info = { tipo, icono, nombre, direccion, horario, telefono, email, descripcion }

                this.showInfoWindowByPlace(info, latLng);
            },
            (error: any) => 
                console.log('getMarkerInfo error ' + JSON.stringify(error))
        );
    }

    getStationInfoAndShowInfoWindow(id: number, latLng: any): void {

        let data;
        let icono, estado, nombreEstacion, subcuencaEstacion, descripcion;
        let nombreStr, nombre, subcuenca;

        this.estacionDisfrutameProvider.getDetail(id).subscribe(
            (response: Response) => {
                console.log('getMarkerInfo ' + JSON.stringify(response));

                data = response;
                data.forEach((element) => {
                    let key = Object.keys(element);

                    if (key[0] === 'icono') icono = element[key[0]]
                    else if (key[0] === 'estado') estado = element[key[0]]
                    else if (key[0] === 'nombre') nombreEstacion = element[key[0]]
                    else if (key[0] === 'subcuenca') subcuencaEstacion = element[key[0]]
                    else if (key[0] === 'descripcionAgua') descripcion = element[key[0]]
                });

                // Parseo nombre de la Estación
                nombreStr = nombreEstacion.split('. ');
                nombre = nombreStr[nombreStr.length - 1];
                nombre = nombre.replace(' - Nivel', '');
                nombre = nombre.replace(' - ', ', ');

                subcuenca = subcuencaEstacion.replace('Q. ', 'Quebrada ');
                subcuenca = subcuenca.replace('Q ', 'Quebrada ');

                let info = { icono, estado, nombre, subcuenca, descripcion }

                this.showInfoWindowByStation(info, latLng);
            },
            (error: any) => 
                console.log('getMarkerInfo error ' + JSON.stringify(error))
        );
    }

    apiMarkerToGoogleMarker(json: any): google.maps.Marker {
        let marker = new google.maps.Marker({
            animation: google.maps.Animation.DROP,
            position: new google.maps.LatLng(json.point.lat, json.point.lng),
            map: null,
            icon: {
                url: json.rutaWebIcono,
                scaledSize: new google.maps.Size(40, 40)
            },
            visible: false
        });
        console.log('Level ' + this.layerLevel + ' Id ' + this.layer.id + ' Name ' + this.layer.name);
        marker['id'] = json.idMarker;
        marker.addListener('click', (args: any = {}) => {
            if ((this.layerLevel == 'CAPA' && this.layer.id == 5) || (this.layerLevel == 'CATEGORIA' && this.layer.id == 262) || 
            (this.layerLevel == 'CATEGORIA' && this.layer.id == 263) || (this.layerLevel == 'CATEGORIA' && this.layer.id == 264)) { //Mis avistamientos
                this.navCtrl.push(AvistamientoDetailComponent, {
                    markerId: marker['id'],
                    color: this.color
                });
            } else if (this.layerLevel === 'CAPA' && (this.layer.id === 10)) { //Aire
                this.common.createModal(DetalleEstacionComponent, 
                    { markerId: marker['id'], nombreCapa: this.layer.name, idCapa: this.layer.id }).present();
            } else if (this.layerLevel === 'CAPA' && (this.layer.id === 11)) { //Agua
                this.getStationInfoAndShowInfoWindow(marker['id'], args.latLng);
            } else if (this.layerLevel === 'CAPA' && this.layer.id === 12) { // Clima
                this.navCtrl.push(ClimaDetalleComponent, {
                    polygonId: marker['id'],
                    color: this.color
                });
            } else if ((this.layerLevel === 'CATEGORIA' && this.layer.id === 252) 
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 253)
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 254)
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 255)
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 256)
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 257)
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 258)
                        || (this.layerLevel === 'CATEGORIA' && this.layer.id === 259)) { //Posconsumo
                this.getPlaceInfoAndShowInfoWindow(marker['id'], args.latLng, this.layer.name);
            }
            else if (this.layerLevel === 'CAPA' && (this.layer.id === 2 || this.layer.id === 3)) { //Vigia
                let detailsModal = this.common.createModal(CuidameDetailComponent, {
                    markerId: marker['id'],
                    color: this.color,
                    from: 'marker'
                });
                detailsModal.present();
            }
            else if (this.layer instanceof GeoLayer
                && (this.layer.layerType == 'AVISTAMIENTO' || this.layer.layerType == 'BUSQUEDA')) {
                this.navCtrl.push(AvistamientoDetailComponent, {
                    markerId: marker['id'],
                    color: this.color
                });
            }
            else this.getGeometryInfoAndShowInfoWindow(marker['id'], args.latLng);
        });
        return marker;
    }

    apiPolygonToGooglePolygon(json: any, color: string): google.maps.Polygon {

        let decoded = google.maps.geometry.encoding.decodePath(json.encodedPolygon);
        let paths = decoded.map(item => {
            return { lat: item.lat(), lng: item.lng() };
        });
        let polygon = new google.maps.Polygon({
            paths: paths,
            map: MapaComponent.mapa,
            strokeColor: color,
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: color,
            fillOpacity: 0.35
        });
        polygon['id'] = json.id;
        polygon.addListener('click', (args: any = {}) => {
            if (this.layerLevel === 'CAPA' && this.layer.id === 12) { // Clima
                this.navCtrl.push(ClimaDetalleComponent, {
                    polygonId: polygon['id'],
                    color: this.color
                });
            } else {
                this.getGeometryInfoAndShowInfoWindow(polygon['id'], args.latLng);
            }
        });
        return polygon;
    }


    showInfoWindowByPlace(info: any, latLng: any): void {
        let contentString = `
                <style>   
                    .gm-style-iw {
                        height: 170px !important;
                        width:100%!important;
                        max-width:100%!important;
                        max-height:170px!important;
                        min-height: 86px !important;
                    }
                    .gm-style .gm-style-iw {
                        display: contents!important;
                    }
                    .gm-style .gm-style-iw-c {
                        background-color:transparent!important;
                        box-shadow:none!important;
                        overflow: visible!important;
                    }
                    .gm-style .buttonsi {
                        display: inline-block;
                        position: absolute;
                        top: 8px;
                        right: 0;
                        color:#fff;   
                    }
                    .gm-style .buttonsi .buttoni {
                        background: transparent;
                        color:#fff;
                    }
                    .gm-style .buttonsi .buttoni .button-inner{
                        margin-right:15px;
                    }
                    .gm-ui-hover-effect {
                        opacity: 0!important;
                        display:none!important;
                    }
                    .custom-iw, .custom-iw>div:first-child>div:last-child {
                        width: calc(100vw - 40px) !important;
                    }
                    .gm-style div div div div {
                        max-width: calc(100vw - 40px) !important;
                    }
                    .gm-style .gm-style-iw-d {
                        height:0;
                    }
                    .gm-style .gm-style-iw-t::after, .gm-style-iw-t::before {
                        border:none;
                    }
                    #div-main-infoWindow {
                        overflow: visible !important;
                        width: calc(100vw - 40px)!important;
                    }
                    .boton_personalizado {
                        text-decoration: none!important;
                        max-height: 4rem!important;
                        padding: 0.8rem!important;
                        background-color: transparent!important;
                        border-radius: 6px!important;
                        width: 20%!important;
                        margin: 0 auto!important;
                        display:none!important;
                    }
                    .gm-style .box-card {
                        border-radius:6px!important;
                        margin: 0px!important;
                        box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)!important;
                        background: #ffffff!important;
                        max-width: 100%!important;
                        width:100%!important;
                        min-height: 170px!important;
                        position: absolute!important;
                        bottom: 30px!important;
                        left: -50%!important;
                    }
                    .content-ios .gm-style .box-card {
                        left:0!important;
                    }
                    .gm-style .box-card .header {
                        width: 100%!important;
                        height:48px!important;
                        border-radius:6px 6px 0 0!important;
                        color:#ffffff;
                        padding:10px 5px;
                        text-align:center;
                    } 
                    .gm-style .box-card .header img{
                        width: 30px!important;
                        height: 30px!important;
                        filter: hue-rotate(0deg) saturate(0%) brightness(100);
                        position: absolute;
                        left: 10px;
                    }
                    .gm-style .box-card .header span{
                        font-size: 2rem;
                        position: relative;
                        top: 3px;
                        margin-right: 10px;
                        font-weight: 500;
                    }                
                    .gm-style .box-card .body {
                        display: flex!important;
                        justify-content: center!important;
                        flex-direction: column!important;  
                        flex-basis: 0!important;
                        padding: 8px!important;
                        width:100%!important;
                    }                   
                    .gm-style .box-card .body .name {
                        font-size: 1.5rem!important;
                        overflow: hidden!important;
                        max-width: 100%!important;
                        text-align:center;
                    }   
                    .gm-style .box-card .body .infoGeneral {
                        font-size: 1.3rem!important;
                        color: #333333!important;
                        overflow: hidden!important;
                        margin: 0!important;
                        max-width: 100%!important;
                        font-weight:normal;
                    }
                    .gm-style .box-card .body .infoTitle{
                        font-size: 1.3rem!important;
                        overflow: hidden!important;
                        margin: 0!important;
                        max-width: 100%!important;
                        font-weight:bold;
                        text-align:left;
                    }
                    .punta {
                        width: 0!important;
                        height: 0!important;
                        margin-left:-15px!important;
                        border-style: solid!important;
                        border-width: 15px 15px 0 15px!important;
                        border-color: #ffffff transparent transparent transparent!important;
                        position: inherit!important;
                        bottom: -14px!important;
                        left: 50%;
                    }
                    .bold {
                        font-weight:600!important;
                    }
                    .gm-style .box-card .body .infoGeneral.line {
                        border-bottom:1px solid grey;
                        padding-bottom:10px;
                        margin-bottom:10px!important;
                    }
                    #goToMovilidad {
                        background:#0060b6;
                    }
                </style> 
                <div class="box-card">
                    <div class="header"  style="background-color: ${this.color}">
                        <img class="icon-header" src="${info.icono}" alt="">
                        <span>${info.tipo}</span>
                        <div class="buttonsi">
                            <div id="closeInfoWindow" icon-only=""  class="buttoni disable-hover bar-button bar-button-ios bar-button-default bar-button-default-ios">
                                <span class="button-inner">
                                    <ion-icon name="close" role="img" class="icon icon-ios ion-ios-close" aria-label="close"></ion-icon>
                                </span>
                                <div class="button-effect"></div>
                            </div>
                        </div>
                    </div>
                    <div class="body">
                        <span class="name bold">${info.nombre}</span>
                        <span class="infoGeneral line">${info.direccion}</span>  
                        <p class="infoTitle"><span style="color: ${this.color}">Horario:</span> <span class="infoGeneral">${info.horario}</span></p>
                        <p class="infoTitle"><span style="color: ${this.color}">Teléfono:</span> <span class="infoGeneral">${info.telefono}</span></p>
                        <p class="infoTitle"><span style="color: ${this.color}">E-mail:</span> <span class="infoGeneral">${info.email}</span></p>
                        <p class="infoTitle"><span style="color: ${this.color}">Descripción:</span> <span class="infoGeneral">${info.descripcion}</span></p>  
                    </div>
                    <ion-row center="" class="row">  
                        <ion-col class="col" text-center=""> 
                            <button ion-button  id="goToMovilidad" class="button button-md button-default button-default-md"><span class="button-inner">Ir</span><div class="button-effect"></div></button>
                        </ion-col>
                    </ion-row>
                    <div class="punta"></div>
                </div>` ;

        if (FusionLayerComponent.currentInfoWindow)
            FusionLayerComponent.currentInfoWindow.close();

        FusionLayerComponent.currentInfoWindow = new google.maps.InfoWindow({
            content: contentString
        });
        FusionLayerComponent.currentInfoWindow.setPosition(latLng);
        FusionLayerComponent.currentInfoWindow.open(MapaComponent.mapa);
        FusionLayerComponent.currentInfoWindow.addListener('domready', (args: any[]): void => {

            document.getElementById('closeInfoWindow')
                .addEventListener('click', () => {
                    FusionLayerComponent.currentInfoWindow.close();
                });

            document.getElementById('goToMovilidad')
                .addEventListener('click', () => {

                    FusionLayerComponent.currentInfoWindow.close();
                    this.posiblesViajesProvider.origen.lat = FusionLayerComponent.currentLat;
                    this.posiblesViajesProvider.origen.lon = FusionLayerComponent.currentLng;
                    this.posiblesViajesProvider.origen.descripcion = 'Mi ubicación';
                    this.posiblesViajesProvider.destino.lat = latLng.lat();
                    this.posiblesViajesProvider.destino.lon = latLng.lng();
                    this.posiblesViajesProvider.destino.descripcion = info.descripcion;

                    this.posiblesViajesProvider
                        .obtenerviajesSugeridos()
                        .then(data => {
                            this.navCtrl.push(VistaViajesPage, data);
                        })
                        .catch(error => {
                            this.common.basicAlert('Movilidad', error);
                        });
                });
        });
    }

    showInfoWindowByStation(info: any, latLng: any): void {
        let contentString = `
                <style>
                    .gm-style-iw {
                        height: 170px !important;
                        width:100%!important;
                        max-width:100%!important;
                        max-height:170px!important;
                        min-height: 86px !important;
                    }
                    .gm-style .gm-style-iw-c {
                        background-color:transparent!important;
                        box-shadow:none!important;
                        overflow: visible!important;
                    }
                    .gm-style .gm-style-iw {
                        display: contents!important;
                    }
                    .gm-ui-hover-effect {
                        opacity: 0!important;
                        display:none!important;
                    }
                    .custom-iw, .custom-iw>div:first-child>div:last-child {
                        width: calc(100vw - 40px) !important;
                    }   
                    .gm-style div div div div {
                        max-width: calc(100vw - 40px) !important;
                    }
                    .gm-style .gm-style-iw-d {
                        height:0;
                    }
                    .gm-style .gm-style-iw-t::after, .gm-style-iw-t::before {
                        border:none;
                    }
                    #div-main-infoWindow {
                        overflow: visible !important;
                        width: calc(100vw - 40px)!important;
                    }
                    .boton_personalizado {
                        text-decoration: none!important;
                        max-height: 4rem!important;
                        padding: 0.8rem!important;
                        background-color: transparent!important;
                        border-radius: 6px!important;
                        width: 20%!important;
                        margin: 0 auto!important;
                        display:none!important;
                    }
                    .gm-style .box-card {
                        border-radius:6px!important;
                        margin: 0px!important;
                        box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)!important;
                        background: #ffffff!important;
                        max-width: 100%!important;
                        width: 100%!important;
                        min-height: 190px!important;
                        position: absolute!important;
                        bottom: 25px!important;
                        left: -50%!important;
                    }
                    .content-ios .gm-style .box-card {
                        left:0!important;
                    }
                    .gm-style .box-card .header {
                        width: 100%!important;
                        height:auto!important;
                        border-radius:6px 6px 0 0!important;
                        color:#ffffff;
                        padding:2px;
                        padding-right: 20px;
                        display:flex;
                        word-break:break-all;
                    } 
                    .gm-style .box-card .header img{
                        width: 45px!important;
                        height:45px!important;
                        margin:auto 0;
                        -webkit-clip-path: circle(17px at center);
                        clip-path: circle(14px at center);
                        vertical-align: middle;
                        margin-right:-5px;
                    }
                    .gm-style .box-card .header span{
                        font-size: 1.7rem;
                        vertical-align: middle;
                        font-weight:500;
                        margin: auto;
                    } 
                    .gm-style .box-card .body {
                        display: flex!important;
                        justify-content: center!important;                    
                        flex-direction: column!important;    
                        flex-basis: 0!important;
                        padding: 8px!important;
                        width:100%!important;
                    } 
                    .gm-style .box-card .body .name {
                        font-size: 1.5rem!important;
                        overflow: hidden!important;
                        max-width: 100%!important;
                        text-overflow: ellipsis!important;
                        white-space: nowrap!important;
                        text-align:center;
                    } 
                    .gm-style .box-card .body .infoGeneral {
                        font-size: 1.3rem!important;
                        color: #333333!important;
                        overflow: hidden!important;
                        text-overflow: ellipsis!important;
                        white-space: nowrap!important;
                        margin: 0!important;
                        max-width: 100%!important;
                    }
                    .punta {
                        width: 0!important;
                        height: 0!important;
                        margin-left:-15px!important;
                        border-style: solid!important;
                        border-width: 15px 15px 0 15px!important;
                        border-color: #ffffff transparent transparent transparent!important;
                        position: inherit!important;
                        bottom: -14px!important;
                        left: 50%;
                    }
                    .bold {
                        font-weight:600!important;
                    }
                    .gm-style .buttonsi {
                        display: inline-block;
                        position: absolute;
                        top: 8px;
                        right: 0;
                        color:#fff; 
                    }
                    .gm-style .buttonsi .buttoni {
                        background: transparent;
                        color:#fff;
                    }
                    .gm-style .buttonsi .buttoni .button-inner{
                        margin-right:15px;
                    }

                </style>
                <div class="box-card">
                    <div class="header"  style="background-color: ${info.estado}">
                        <img class="icon-header" src="${info.icono}" alt="">
                        <span>${info.descripcion}</span>
                        <div class="buttonsi">
                            <div id="closeInfoWindow" icon-only="" class="buttoni disable-hover bar-button bar-button-ios bar-button-default bar-button-default-ios">
                                <span class="button-inner">
                                    <ion-icon name="close" role="img" class="icon icon-ios ion-ios-close" aria-label="close"></ion-icon>
                                </span>
                                <div class="button-effect"></div>
                            </div>
                        </div>
                    </div>
                    <div class="body">
                        <span class="name bold">Estación:</span>
                        <span class="name">${info.nombre}</span>
                        </br>
                        <span class="infoGeneral bold">Cuenca:</span>
                        <span class="infoGeneral">${info.subcuenca}</span>                   
                    </div>
                    <div class="punta"></div>
                </div>` ;

        if (FusionLayerComponent.currentInfoWindow)
            FusionLayerComponent.currentInfoWindow.close();

        FusionLayerComponent.currentInfoWindow = new google.maps.InfoWindow({
            content: contentString
        });
        FusionLayerComponent.currentInfoWindow.setPosition(latLng);
        FusionLayerComponent.currentInfoWindow.open(MapaComponent.mapa);
        FusionLayerComponent.currentInfoWindow.addListener('domready', (args: any[]): void => {           
            document.getElementById('closeInfoWindow')
                .addEventListener('click', () => {
                    FusionLayerComponent.currentInfoWindow.close();
                });
        });
    }

    showInfoWindow(info: any, latLng: any): void {
        let imagen: string;
        if(info.rutaImagen != null || info.rutaImagen){
            imagen = info.rutaImagen;
        }
        else{
            imagen = this.layer.urlIcon
        }
        let contentString = `
                <style>
                    .gm-style-iw {
                        height: 70px !important;
                        width:100%!important;
                        max-width:100%!important;
                        max-height:70px!important;
                        min-height: 86px !important;
                    }
                    .gm-style .gm-style-iw-c {
                        background-color:transparent!important;
                        box-shadow:none!important;
                        overflow: visible!important;
                    }
                    .gm-style .gm-style-iw {
                        display: contents!important;
                    }
                    .gm-ui-hover-effect {
                        opacity: 0!important;
                        display:none!important;
                    }
                    .custom-iw, .custom-iw>div:first-child>div:last-child {
                        width: calc(100vw - 40px) !important;
                    }
                    .gm-style div div div div {
                        max-width: calc(100vw - 40px) !important;
                    }
                    .gm-style .gm-style-iw-d {
                        height:0;
                    }
                    .gm-style .gm-style-iw-t::after, .gm-style-iw-t::before {
                        border:none;
                    }
                    #div-main-infoWindow {
                        overflow: visible !important;
                        width: calc(100vw - 40px)!important;
                    }
                    .boton_personalizado {
                        text-decoration: none!important;
                        max-height: 4rem!important;
                        padding: 0.8rem!important;
                        background-color: transparent!important;
                        border-radius: 6px!important;
                        width: 20%!important;
                        margin: 0 auto!important;
                        display:none!important;
                    }
                    .gm-style .box-card {
                        border-radius:6px!important;
                        margin: 0px!important;
                        box-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)!important;
                        max-width: 100%!important;
                        width: 100%!important;
                        min-height: 70px!important;
                        display: flex!important;
                        position: absolute!important;
                        bottom: 30px!important;
                        left: -50%!important;
                    }
                    .content-ios .gm-style .box-card {
                        left:0!important;
                    }
                    .gm-style .box-card .icon-container img{
                        width: 70px!important;
                        height:70px!important;
                        border-radius:6px!important;
                        display: flex!important;
                        justify-content: space-around!important;
                        align-items: center!important;
                    }                   
                    .gm-style .box-card .description {
                        display: flex!important;
                        justify-content: center!important;
                        align-items: flex-start!important;
                        flex-direction: column!important;
                        flex-grow: 1!important;
                        flex-basis: 0!important;
                        padding: 8px!important;
                        color: #ffffff!important;
                        width:calc(100% - 70px)!important;
                    }                   
                    .gm-style .box-card .description .name {
                        font-weight: bold!important;
                        font-size: 2rem!important;
                        overflow: hidden!important;
                        max-width: 100%!important;
                        text-overflow: ellipsis!important;
                        white-space: nowrap!important;
                    }   
                    .gm-style .box-card .description .infoGeneral {
                        font-size: 1.3rem!important;
                        font-weight: normal!important;
                        overflow: hidden!important;
                        text-overflow: ellipsis!important;
                        white-space: nowrap!important;
                        margin: 0!important;
                        max-width: 100%!important;
                    }
                    .punta {
                        width: 0!important;
                        height: 0!important;
                        margin-left:-15px!important;
                        border-style: solid!important;
                        border-width: 15px 15px 0 15px!important;                      
                        position: inherit!important;
                        bottom: -14px!important;
                        left: 50%;
                    }
                </style>
                <div class="box-card" id="infoWindowButtonRoutes"  style="background-color: ${this.color}">
                    <div class="icon-container">
                        <img src="${imagen}" style="background: white;">
                    </div>
                    <div class="description">
                        <div class="name">
                        ${info.nombre}
                        </div>
                        <span class="infoGeneral">${info.direccion}</span>
                    </div>
                    <div class="punta" style="border-color: ${this.color} transparent transparent transparent!important;"></div>
                </div>`
                ;
        if (FusionLayerComponent.currentInfoWindow)
            FusionLayerComponent.currentInfoWindow.close();

        FusionLayerComponent.currentInfoWindow = new google.maps.InfoWindow({
            content: contentString
        });
        FusionLayerComponent.currentInfoWindow.setPosition(latLng);
        FusionLayerComponent.currentInfoWindow.open(MapaComponent.mapa);
        FusionLayerComponent.currentInfoWindow.addListener('domready', (args: any[]): void => {           
            document.getElementById('infoWindowButtonRoutes')
                .addEventListener('click', () => {
                    FusionLayerComponent.currentInfoWindow.close();

                    this.posiblesViajesProvider.origen.lat = FusionLayerComponent.currentLat;
                    this.posiblesViajesProvider.origen.lon = FusionLayerComponent.currentLng;
                    this.posiblesViajesProvider.origen.descripcion = 'Mi ubicación';
                    this.posiblesViajesProvider.destino.lat = latLng.lat();
                    this.posiblesViajesProvider.destino.lon = latLng.lng();
                    this.posiblesViajesProvider.destino.descripcion = info.descripcion;
                    this.common.createModal(TerritorioDetailComponent, { info: info, nombreCapa: this.layer.name, iconoCapa: this.layer.urlIcon }).present();
                });
        });

        MapaComponent.mapa.panTo(latLng);
    }

    getColorSelectedLayer(): string {
        return this.layer.visible ? this.color : null;
    }

    public layerActive(): string {
        if (this.active) {
            return this.color;
        } else {
            return 'transparent';
        }
    }

    public isLayerActive(): string {
        return this.active ? 'layer-active' : 'layer-unactive';
    }
}
