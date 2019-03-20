import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';

import { Layer } from '../../entities/layer';
import { Common } from '../../app/shared/utilidades/common';

@Component({
    selector: 'layer',
    templateUrl: 'layer.html'
})
export class LayerComponent implements OnInit {
    @Input()
    protected layer: Layer;

    @Input()
    protected layerLevel: string;

    @Input()
    protected color: string;

    @Output()
    protected tapLayer = new EventEmitter<{}>();

    protected active: boolean;
    protected common: Common;

    constructor() {
        //debugger;
        const injector = Common.getInjector();
        this.common = injector.get(Common);
    }

    ngOnInit(): void {
        //debugger;
        //this code won't be called unless we call it from the inherited class
    }

    onTapLayer(): void {
        //debugger;
        if (this.layer.children) {
            this.layer.children.forEach((childLayer: Layer) => {
                this.common.activeLayers.ids = this.common.activeLayers.ids.filter(
                    id => id != childLayer.id
                );   
            });
        }
    
        this.tapLayer.emit(this.layer);
        this.active = !this.active;
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
