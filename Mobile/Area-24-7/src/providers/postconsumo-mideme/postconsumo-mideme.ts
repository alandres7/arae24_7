import { Http, Headers, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

@Injectable()
export class PostconsumoMidemeProvider {

    constructor(public http: Http) {}

    getDetail(marcadorId: number): Observable<any> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/huellas/posconsumo/detail?idMarcador=' + marcadorId;
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