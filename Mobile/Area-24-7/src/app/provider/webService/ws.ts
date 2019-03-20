import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
//Incluyo las dependencias para trabajar con Observables
import { Observable } from 'rxjs/Observable';
//import { BehaviorSubject } from 'rxjs/BehaviorSubject';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


@Injectable()
export class WebService {
    url: string;
    paquete: string;
    respuesta: any;
    //respuesta: Observable<any>;
    //private _todas: BehaviorSubject<any[]>;//Es necesario crear un Sujeto que sera el encargado de inicializar el observable

    constructor(public http: Http) {
        //this._todas = <BehaviorSubject<any[]>>new BehaviorSubject([]); //Inicializo el sujeto
        //this.personas = this._todas.asObservable();//Le digo a mi objeto de personas que el sujeto sera un observable     
    }

    solicitar(tipoServicio, datos) {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ "headers": headers });
        this.http[tipoServicio](this.url, datos, options)
            .map((res: Response) => {
                //mapear la respuesta a clase especifica
                res.json();
            })
            .subscribe(data => {
                if (data) {
                    this.respuesta = data;
                    console.log(this.respuesta);
                }
            },
            error => console.log(error));

        return this.respuesta;
    };

    // convertir Observable a Promise
    /*delete(id: number): Promise<void> {
        return this.http.delete(this.url + this.paquete, { headers: this.headers })
            .toPromise()
            .then(() => null)
            .catch(this.handleError);
    }*/

    wsPost(header, datos) {
        //let bodyString = JSON.stringify(body); // Stringify payload
        let headers = new Headers(header || { 'Content-Type': 'application/json' });
        let options = new RequestOptions({ 'headers': headers });
        return this.http.post(this.url, datos, options)
            .map(this.extract)
            .catch(this.handleError);
    };

    wsGet(header, datos) {
        let headers = new Headers(header || { 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.get(this.url + datos || '', options)
            .map(this.extract)
            .catch(this.handleError);
    };

    wsPut(header, datos) {
        console.log('datos ' + JSON.stringify(datos));
        let headers = new Headers(header || { 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.put(this.url, datos, options)
            .map(this.extract)
            .catch(this.handleError);
    };

    wsDelete(header, datos) {
        let headers = new Headers(header || { 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.put(this.url, datos, options)
            .map(this.extract)
            .catch(this.handleError);
    };

    private extract(res: Response) {
        if (res.status >= 400 && res.status <= 500) {
            return res.json();
        }
        else return res;
    }

    private handleError(error: Response | any) {
        if (error instanceof Response) {
            console.log(error);
            return Observable.throw(error);
        }
    }


}

