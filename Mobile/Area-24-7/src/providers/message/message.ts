import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

@Injectable()
export class MessageProvider {

    constructor(public http: Http) {}

    get(id: string): Observable<Response> {
        console.log(MessageProvider.name + ' get ');
        let url = CONFIG_ENV.REST_BASE_URL + '/api/mensaje/' + id;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.get(url, { headers: headers });
    }

    getByNombreIdentificador(nombreIdentificador: string): Observable<Response> {
        console.log(MessageProvider.name + ' get ');
        let url = CONFIG_ENV.REST_BASE_URL + '/api/mensaje/nombreIdentificador/' + nombreIdentificador;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.get(url, { headers: headers });
    }
}
