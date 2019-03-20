export class Suggestion {

    private _id: string;
    private _description: string;
    private _urlIcon: string;

    constructor(id: string, description: string, urlIcon?: string) {
        this._id = id;
        this._description = description;
        if (urlIcon) this._urlIcon = urlIcon;
    }

    get id(): string { return this._id; }
    
    set id(id: string) { this._id = id; }

    get description(): string { return this._description; }

    set description(description: string) { this._description = description; }

    get urlIcon(): string { return this._urlIcon; }

    set urlIcon(urlIcon: string) { this._urlIcon = urlIcon; }

    static parseFromGoogleAutocomplete(json: any): Suggestion[] {
        let suggestions = new Array<Suggestion>();
        if (json) {
            json.forEach((prediction: any) => {
                suggestions.push(new Suggestion(prediction.place_id, prediction.structured_formatting.main_text));
            });
        }
        return suggestions;
    }

    static parse(json: any[]): Suggestion[] {
        return json.map((item: any) => new Suggestion(item.id, item.nombre, item.rutaWebIcono));
    }   
}