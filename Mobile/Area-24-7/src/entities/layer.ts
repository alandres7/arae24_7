export class Layer
{
    protected _id: number;
    protected _name: string;
    protected _active: boolean;
    protected _children: Layer[];
    protected _urlIcon: string;
    protected _favorite: boolean;

    constructor(json: any) {
        if (!json) return; 
        
        this._id = json.id;
        this._name = json.nombre;
        this._active = json.activo;
        this._favorite = json.favorito;
        this._urlIcon = json.rutaIconoCapa;
    }

    get id(): number { return this._id; }

    set id(id: number) { this._id = id; }

    get name(): string { return this._name; }

    set name(name: string) { this._name = name; }

    get active(): boolean { return this._active; }

    set active(active: boolean) { this._active = active; }

    get children(): Layer[] { return this._children; }

    set children(children: Layer[]) { this._children = children; }
    
    get urlIcon(): string { return this._urlIcon; }

    set urlIcon(urlIcon: string) { this._urlIcon = urlIcon; }

    get favorite(): boolean { return this._favorite; }

    set favorite(favorite: boolean) { this._favorite = favorite; }

    static parse(json: any[]): Layer[] {
        return json.map((item: any) => new Layer(item));
    }

   
}
