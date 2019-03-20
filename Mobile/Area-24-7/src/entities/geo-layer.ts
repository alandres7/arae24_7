import { Layer } from './layer';

export class GeoLayer extends Layer {

    private _urlIconMarker: string;
    private _markers: google.maps.Marker[];
    private _polylines: google.maps.Polyline[];
    private _polygons: google.maps.Polygon[];   
    private _visible: boolean; 
    private _layerType: string;
    
    constructor(json: any) {
        super(json);

        if (json.rutaIconoMarker != null) this._urlIconMarker = json.rutaIconoMarker;  
        this._visible = false;              
        this._layerType = json.nombreTipoCapa.toUpperCase();
    }

    get markers(): google.maps.Marker[] { return this._markers; }

    set markers(markers: google.maps.Marker[]) { this._markers = markers; }

    get polylines(): google.maps.Polyline[] { return this._polylines; }

    set polylines(polylines: google.maps.Polyline[]) { this._polylines = polylines; }

    get polygons(): google.maps.Polygon[] { return this._polygons; }

    set polygons(polygons: google.maps.Polygon[]) { this._polygons = polygons; }

    get urlIconMarker(): string { return this._urlIconMarker; }

    set urlIconMarker(urlIconMarker: string) { this._urlIconMarker = urlIconMarker; }

    get visible(): boolean { return this._visible; }

    set visible(visible: boolean) { this._visible = visible; }
    
    get layerType(): string { return this._layerType; }

    set layerType(layerType: string) { this._layerType = layerType; }

    static parse(json: any[]): GeoLayer[] {
        return json.map((item: any) => new GeoLayer(item));
    }

    static parseForMenu(json: any[]): GeoLayer[] {
        return json.map((item: any): GeoLayer => {
            let geoLayer: GeoLayer = new GeoLayer(null);
            geoLayer.id = item.id;
            geoLayer.name = item.nombre;
            geoLayer.active = item.activo;
            geoLayer.favorite = item.favorito;
            geoLayer.urlIcon = item.icono;
            geoLayer.layerType = item.nombreTipoCapa.toUpperCase();
            return geoLayer;
        });
    }
}