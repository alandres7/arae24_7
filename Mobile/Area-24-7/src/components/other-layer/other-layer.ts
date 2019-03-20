import { Component, Input, OnInit, style } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { LayerComponent } from '../layer/layer';
import { OtherLayer } from '../../entities/other-layer';
import { AvistamientoCreateComponent } from '../avistamiento-create/avistamiento-create';
import { DecisionTreeComponent } from "../decision-tree/decision-tree";
import { CuidameCreateComponent } from '../cuidame-create/cuidame-create';
import { MidemeOptionsComponent } from '../mideme-options/mideme-options';

@Component({
  selector: 'other-layer',
  templateUrl: 'other-layer.html'
})
export class OtherLayerComponent extends LayerComponent {
  @Input()
  layer: OtherLayer;

  constructor(public navCtrl: NavController) {
    super();
  }

  onTapLayer(): void {
    switch (this.layer.layerType) {
      case 'BACK':
        this.tapLayer.emit(this.layer);
        break;

      case 'REPORTE':
      if(this.layer.id == 4){
        this.navCtrl.push(AvistamientoCreateComponent, {
          color: this.color,
          layerId: this.layer.id
        });
      }
      else{
        this.navCtrl.push(CuidameCreateComponent, {
          color: this.color,
          layerId: this.layer.id
        });
      }
        break;

        case 'ARBOL':
            this.navCtrl.push(DecisionTreeComponent, {
                treeID: 4,
                color:this.color,
                layerId: this.layer.id
            });
        break;

        case 'ENCUESTA':
            this.navCtrl.push(MidemeOptionsComponent, {
              color: this.color,
              layerId: this.layer.id
            });
        break;

      case 'FILTRO':
        break;
    }
  }
}
