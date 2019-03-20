import { Http, Request, RequestOptionsArgs, Response, ResponseOptions, RequestOptions, ConnectionBackend } from '@angular/http';
import { App } from 'ionic-angular';
import { Observable } from 'rxjs/Observable';

import { Common } from '../utilidades/common';

export class InterceptorHttp extends Http {

    constructor(
        protected app: App,
        public backend: ConnectionBackend,
        public defaultOptions: RequestOptions,
        public common: Common)
    {
        super(backend, defaultOptions);
    }

    request(url: string | Request, options?: RequestOptionsArgs): Observable<Response> {
        return super.request(url, options);
    }

    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.get(url, options));

        /*return this.intercept(super.get(url, this.getRequestOptionArgs(options)).timeout(
        CONSTANTS.TIMEOUT_REQUEST
        ).catch((err: Response) => {
        return Observable.throw(new Response(new ResponseOptions({ status: 999 })));
        }));*/
    }

    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        // return this.intercept(super.post(url, body, this.getRequestOptionArgs(options)).timeout(
        //   CONSTANTS.TIMEOUT_REQUEST
        // ).catch((err: Response) => {
        //   return Observable.throw(new Response(new ResponseOptions({ status: 999 })));
        // }));
        return this.intercept(super.post(url, body, this.getRequestOptionArgs(options)));
    }

    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.put(url, body, this.getRequestOptionArgs(options)));
    }

    delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.delete(url, options));
    }

    getRequestOptionArgs(options?: RequestOptionsArgs): RequestOptionsArgs {
        // if (options == null) {
        //     options = new RequestOptions();
        // }
        // if (options.headers == null) {
        //     options.headers = new Headers();
        // }
        // options.headers.append('Content-Type', 'application/json');
        return options;
    }

    intercept(observable: Observable<Response>): Observable<Response> {
        let manejador = new Observable<Response>(obs => {
            observable.subscribe((data: Response) => {
                console.log('intercept response ok');
                
                if (data.status == 205) {
                    let message = data.json();
                    this.common.presentAcceptAlert(message.titulo, message.descripcion);
                }
                obs.next(data);
                obs.complete();
            },
            (err) => {
                console.log('intercept response error ' + JSON.stringify(err));

                this.common.dismissLoading();
                this.common.dissmisAlert();
                
                if (err.status == 0) {
                    if (this.common.network.type == 'none') this.common.presentAcceptAlert('Ups!', 'No tienes conexión a internet. Activa el Wi-Fi o los datos móviles y vuelve a intentarlo.');
                    else this.common.presentAcceptAlert('Ups!', 'Servicio no disponible, inténtalo más tarde.');
                    obs.complete();
                } 
                else if (err.status == 417 || err.status == 418) {
                    let message = err.json();
                    this.common.presentAcceptAlert(message.titulo, message.descripcion);
                    obs.complete();
                }
                else if (err.status >= 500 && err.status < 600) {
                    this.common.presentAcceptAlert('Ups!', 'Ocurrió un inconveniente, inténtalo nuevamente.');
                    obs.complete();
                }
                else obs.error(err);
            });
        });
        return manejador;
    }
}
