import { Component, EventEmitter, OnInit, OnDestroy, Input, Output } from '@angular/core';
import { Response } from '@angular/http';

import { App } from 'ionic-angular';

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

@Component({
    selector: 'geo-layer',
    templateUrl: 'geo-layer.html'
})
export class GeoLayerComponent extends LayerComponent implements OnInit, OnDestroy {

    @Input()
    protected layer: GeoLayer;

    @Input()
    protected fixedActionRadius?: number;

    private static actionRadiusChange = new BehaviorSubject<number>(0);
    private static locationChange = new BehaviorSubject<{ lat: number, lng: number }>({ lat: 0, lng: 0 });

    protected static actionRadiusChanged$ = GeoLayerComponent.actionRadiusChange.asObservable();
    protected static locationChanged$ = GeoLayerComponent.locationChange.asObservable();

    static emitActionRadiusChange(actionRadius: number): void {
        GeoLayerComponent.actionRadiusChange.next(actionRadius);
    }

    static emitLocationChange(latLng: { lat: number, lng: number }): void {
        GeoLayerComponent.locationChange.next(latLng);
    }

    protected static currentLat: number;
    protected static currentLng: number;

    protected actionRadius: number;
    protected isLoading: boolean = false;

    constructor() { 
        super();

        if (this.fixedActionRadius) this.actionRadius = this.fixedActionRadius;
     }

    ngOnInit(): void {
        console.log('entro en geolayer')

    }

    ngOnDestroy(): void {}
}