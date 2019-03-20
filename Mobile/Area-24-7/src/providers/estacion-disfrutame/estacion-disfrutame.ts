import { Http, Headers, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

/*
  Generated class for the EstacionDisfrutameProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class EstacionDisfrutameProvider {

    constructor(public http: Http) {}

    getDetail(marcadorId: number): Observable<any> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/entorno/estacion/detalle/find-By-Marker?idMarcador=' + marcadorId;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        console.log(localStorage.getItem('bearer'));
        
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): any => {
                console.log(response.json());
                return response.json();
            });
    }

    getClima(marcadorId: number) {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/entorno/clima/detalle/find-By-Marker?idMarcador=' + marcadorId;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        console.log(localStorage.getItem('bearer'));
        
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): any => {
                console.log(response.json());
                return response.json();
            });
    }
    

    
}
