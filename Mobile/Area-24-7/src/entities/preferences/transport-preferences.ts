export class TransportPreferences 
{
    private _id: number;
    private _name: string;
    private _active: boolean;
    private _icon: string;
    private _iconOn: string;
    private _iconOff: string;

    constructor(json?: any) {
        if (!json) return;

        this._id = json._id;
        this._name = json._name;
        this._active = json._active;
        this._icon = json._icon;
        this._iconOn = json._iconOn;
        this._iconOff = json._iconOff;
    }

    get id(): number { return this._id; }

    set id(id: number) { this._id = id; }

    get name(): string { return this._name; }

    set name(name: string) { this._name = name; }

    get active(): boolean { return this._active; }

    set active(active: boolean) { this._active = active; }

    get icon(): string { return this._icon; }

    set icon(icon: string) { this._icon = icon; }

    get iconOn(): string { return this._iconOn; }

    set iconOn(iconOn: string) { this._iconOn = iconOn; }

    get iconOff(): string { return this._iconOff; }

    set iconOff(iconOff: string) { this._iconOff = iconOff; }

    stringifyToDefault(): string {
        let object = {
              id: this.id
            , nombre: this.name
            , activo: this.active
            , icono: this.icon
            , iconOn: this.iconOn
            , iconOff: this.iconOff
        };
        return JSON.stringify(object);
    }

    static parse(json: any[]): TransportPreferences[] {
        return json.map((item: any): TransportPreferences => new TransportPreferences(item));
    }

    static parseFromDefault(json: any[]): TransportPreferences[] {
        return json.map((item: any): TransportPreferences => {
            let transportPreferences: TransportPreferences = new TransportPreferences();
            transportPreferences.id = item.id;
            transportPreferences.name = item.nombre;
            transportPreferences.active = item.activo;
            transportPreferences.icon = item.icono;
            transportPreferences.iconOn = item.iconOn;
            transportPreferences.iconOff = item.iconOff;
            return transportPreferences;
        });
    }

    static parseToDefault(transportsPreferences: TransportPreferences[]): any[] {
        return transportsPreferences.map((transportPreferences: TransportPreferences): any => {
            let object = {
                  id: transportPreferences.id
                , nombre: transportPreferences.name
                , activo: transportPreferences.active
                , icono: transportPreferences.icon
                , iconOn: transportPreferences.iconOn
                , iconOff: transportPreferences.iconOff
            };
            return object;
        });
    }
}