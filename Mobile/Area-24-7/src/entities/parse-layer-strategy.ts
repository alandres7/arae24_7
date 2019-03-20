import { Layer } from './layer';
import { GeoLayer } from "./geo-layer";
import { OtherLayer } from "./other-layer";

export class ParseLayerStrategy {

    static parseStrategy(json: any[]): Layer[] {
        let array = new Array<Layer>();

        for (let i = 0; i < json.length; i++) {
            switch (json[i].nombreTipoCapa) {
                case 'Subcapas':
                    let layer: Layer = new Layer(json);
                    array.push(layer);
                    break;

                case 'Mapa':
                    let geoLayer: GeoLayer = new GeoLayer(json);
                    array.push(geoLayer);
                    break;
            }
        }
        return array;

        //return json.map((item: any) => {
          //  switch (item.nombreTipoCapa) {
            /*    case 'Mapa':
                    let geoLayer: GeoLayer = new GeoLayer(json);
                    return geoLayer;
              */  
            //    case 'Subcapas':
              //      let layer: Layer = new Layer(json);
                //    return layer;
/*
                default: 
                    let otherLayer: OtherLayer = new OtherLayer(json);
                    return otherLayer;*/
            //}
        //});
    }
}