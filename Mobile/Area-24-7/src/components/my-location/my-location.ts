import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Response } from "@angular/http";

import { Platform } from 'ionic-angular';
import { Loading, LoadingController } from 'ionic-angular';

import { Subscription } from 'rxjs/Subscription';

import { GoogleMaps } from '../../app/shared/utilidades/googleMaps';
import { MapaComponent } from '../../app/core/mapa/mapa';
//import { ModalCaracterizacionPage } from "../../pages/modal-caracterizacion/modal-caracterizacion";

import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { LayerProvider } from "../../providers/layer/layer";
import { SideMenu } from '../../app/menu/page/side-menu';

@Component({
    selector: 'my-location',
    templateUrl: 'my-location.html'
})
export class MyLocationComponent implements OnInit, OnDestroy {
    
    @Input() 
    private color: string;

    @Input()
    private actionRadius: number;

    @Output()
    private clickPedestrian = new EventEmitter<{ lat: number, lng: number }>();

    @Output()
    private dragendPedestrian = new EventEmitter<{ lat: number, lng: number }>();

    @Output()
    private clickMyLocationButton = new EventEmitter<void>();
    
    private locationMarker: google.maps.Marker;
    private actionRadiusCircle: google.maps.Circle;

    constructor(private platform: Platform) {}

    ngOnInit(): void {

        /*
        this.googleMaps.typeaheadLocationChanged$.subscribe(
            (latLng: { lat: number, lng: number }) => {
                console.log('locationChanged msg ' + JSON.stringify(latLng));
                this.unsubscribeToPositionChanges();       
                this.googleMaps.emitLocationChange(latLng);                
                this.createUpdatePositionMarker(latLng.lat, latLng.lng);
            }
        );
        this.googleMaps.unsubscribeFromLocationChanges$.subscribe(
            () => {
                console.log('unsubscribeFromLocationChanges ');
                this.unsubscribeToPositionChanges();       
            }
        );*/
    }

    ngOnDestroy(): void {
        if (this.locationMarker) this.locationMarker.setMap(null);
        if (this.actionRadiusCircle) this.actionRadiusCircle.setMap(null);
    }

    onActionRadiusChange(actionRadius: number): void {
        if (this.actionRadiusCircle) this.actionRadiusCircle.setRadius(actionRadius);        
    }

    onMyLocationClick(): void {
        this.clickMyLocationButton.emit();
    }

    createUpdatePositionMarker(lat: number, lng: number): void {
        let position: google.maps.LatLng = new google.maps.LatLng(lat, lng);
        if (this.locationMarker) {
            this.locationMarker.setPosition(position);
            this.actionRadiusCircle.setCenter(position);
        }
        else {
            this.locationMarker = new google.maps.Marker({
                map: MapaComponent.mapa,
                position: position,
                icon: {
                    scaledSize: new google.maps.Size(50, 50),
                    url: 'assets/mapa/ubicacion.svg'
                },
                animation: google.maps.Animation.DROP,
                draggable: true,
                zIndex: google.maps.Marker.MAX_ZINDEX + 1
            });
            this.locationMarker.addListener('dragend', (args: any[]) => {
                let position: google.maps.LatLng = this.locationMarker.getPosition();
                this.dragendPedestrian.emit({ lat: position.lat(), lng: position.lng() });                
            });
            this.locationMarker.addListener('click', (args: any[]): void => {
                let position: google.maps.LatLng = this.locationMarker.getPosition();
                this.clickPedestrian.emit({ lat: position.lat(), lng: position.lng() });
            });

            this.actionRadiusCircle = new google.maps.Circle({
                map: MapaComponent.mapa,
                radius: this.actionRadius,
                fillColor: '#f9bbbb',
                strokeColor: '#f9bbbb',
                strokeOpacity: 0.3,
                strokeWeight: 0.8,
                fillOpacity: 0.1,
            });
            this.actionRadiusCircle.bindTo('center', this.locationMarker, 'position');
        }
    }

    getPedestrianLocation(): { lat: number, lng: number } {
        return { lat: this.locationMarker.getPosition().lat(), lng: this.locationMarker.getPosition().lng() };
    }
}