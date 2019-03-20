import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Response } from '@angular/http';

import { GeoLayer } from '../../entities/geo-layer';

import { LayerProvider } from '../../providers/layer/layer';

import { Common } from '../../app/shared/utilidades/common';
import { AppLayer } from '../../entities/app-layer';
import { Layer } from '../../entities/layer';
import { OtherLayer } from '../../entities/other-layer';

@Component({
    selector: 'layer-manager',
    templateUrl: 'layer-manager.html'
})

export class LayerManagerComponent implements OnInit, OnDestroy {
    
    @Input() 
    private app: AppLayer;

    private currentLayerId: number;

    constructor(private layerProvider: LayerProvider
              , private common: Common) {}

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
        this.cleanMap();
    }

    loadCategories(id: number): void {
        let layer: Layer = this.app.children.find((layer: Layer): boolean => { return layer.id == id; });
        if (layer.children == undefined) {
            this.layerProvider.getNLayer('CATEGORIA', id).subscribe(
                (response: Response) => {
                    console.log(LayerManagerComponent.name + ' loadCategories getNLayer ' + JSON.stringify(response));
                    layer.children = AppLayer.parseStrategy(response.json());
                },
                (error) => console.log(LayerManagerComponent.name + ' loadCategories getNLayer error' + JSON.stringify(error))
            );
        }
    }
    
    onTapLayer(layer: Layer): void {
        //this part of the conditional is never used again
        if (layer instanceof OtherLayer && layer.layerType == 'BACK') {
            this.cleanMap();
            this.common.submenu.sOrdenamiento = false;
            this.common.submenu.sAvistamientos = false;
            this.common.submenu.sEntorno = false;
            this.common.submenu.sHuellas = false;
            this.common.submenu.sVigias = false;
            this.common.submenu.sConcursoFotografia = false;
            this.currentLayerId = undefined;
        }
        else if (layer.active && layer.children && layer.children.length > 0) {
            this.currentLayerId = layer.id;
            
            if (layer.active) {
                this.cleanMap();
                this.app.children.forEach(child => {
                    if(child.id === this.currentLayerId) child.active = false;
                });
                layer.active = false;
                //this.currentLayerId = undefined;
            }
        }
        else if (layer.id == 5) { //Mis avistamientos
            this.cleanMap();
            this.currentLayerId = undefined;
        }
        else {
            this.loadCategories(layer.id);
            this.currentLayerId = layer.id;
            this.app.children.forEach(child => {
                if(child.id === this.currentLayerId) child.active = true;
            });
        }

        if (!layer.active) this.currentLayerId = undefined;
    }
    
    cleanMap(): void {
        this.app.children.forEach((layer: Layer) => {
            if (layer instanceof GeoLayer) {
                layer.visible = false;
                if (layer.markers) {
                    layer.markers.forEach((marker: google.maps.Marker) => {
                        marker.setMap(null);
                    });
                }
                if (layer.polygons) {
                    layer.polygons.forEach((polygon: google.maps.Polygon) => {
                        polygon.setMap(null);
                    });
                }
            }
            else if (layer.children) {
                layer.children.forEach((layer_: GeoLayer) => {
                    layer_.visible = false;
                    if (layer_.markers) {
                        layer_.markers.forEach((marker_: google.maps.Marker) => {
                            marker_.setMap(null);
                        });
                    }
                    if (layer_.polygons) {
                        layer_.polygons.forEach((polygon_: google.maps.Polygon) => {
                            polygon_.setMap(null);
                        });
                    }
                }); 
            }
        });
    }
        
    getBackLayer(): OtherLayer {
        let otherLayer: OtherLayer = new OtherLayer(null);
        otherLayer.id = this.app.id;
        otherLayer.name = 'Atrás';
        otherLayer.layerType = 'BACK';
        otherLayer.active = true;
        return otherLayer;
    }

    getCurrentLayer(): Layer {
        let layer: Layer = this.app.children.find((layer: Layer): boolean => {  
            return layer.active && layer.id == this.currentLayerId;
        });
        if (!layer) this.currentLayerId = undefined;
        if(layer) return layer;
    }

    isInstanceOf(layer: Layer): string {
        if (layer instanceof GeoLayer) return 'GeoLayer';
        else if (layer instanceof OtherLayer) return 'OtherLayer';
        else return 'Layer';
    }

    isLayerTypeOf(layer: Layer, layerType: string): boolean {
        return layer instanceof GeoLayer 
            && layer.layerType == layerType;
    }

    getFixedActionRadius(appId: number, layerId: number): number {
        switch(appId) {
            case 2: //Cuidame
            if (layerId == 211) return Number.MAX_VALUE; //Mis reportes
            else if (layerId == 185) return Number.MAX_VALUE; //Buscador
            else return 500;
            
            case 3: //Asómbrate
                if (layerId == 211) return Number.MAX_VALUE; //Mis avistamientos
                else return 500;
            
           // case 5: //Mi Entorno
             //   return Number.MAX_VALUE;
            default: 
                return undefined;
        }
    }
}