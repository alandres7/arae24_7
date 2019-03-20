import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

@Injectable()
export class DecisionTreeProvider {

    public replayArbol: ReplaySubject<any>;

    constructor(public http: Http) { }

    /*
    getChildren(parentId: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/arbol/' + parentId;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }*/

    getArbol(idApp: number, nombreArbol: string) {
        this.replayArbol = new ReplaySubject(1);

        this.getArbolDeAplicacion(idApp).subscribe((response: Response) => {
            let body = response.json();

            this.getRoot(body[0].id).subscribe((response: Response) => {
                let body = response.json();

                let i = 0;
                while (nombreArbol !== body[i].nombre) {
                    i = i + 1;
                }
                this.replayArbol.next(body[i]);
            });
        });

        return this.replayArbol;
    }

    getArbolDeAplicacion(idApp): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + `/api/objeto/aplicacion/${idApp}`;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getTree(idCapa): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + `/api/arbol/capa/${idCapa}`;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getRoot(idArbol: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + `/api/nodo-arbol/raiz/arbol/${idArbol}`;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getNodo(idNodo: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + `/api/nodo-arbol/${idNodo}`;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response) => {
                return(response.json());
            })
    }

    getChildren(parentId: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + `/api/nodo-arbol/padre/${parentId}`;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getDecisionTreeID(categoryID: number): Observable<number> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/arbol/categoria/' + categoryID;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response) => {
                return response.json()[0].id;
            });
    }
}
