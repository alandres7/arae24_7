import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';
import { Country } from '../../entities/country';
import { Subscriber } from 'rxjs/Subscriber';
import { Municipality } from '../../entities/municipality';

@Injectable()
export class TerritorioProvider {

    constructor(private http: Http) {}

    getCountries(): Observable<Country[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/paises';
        return this.http
            .get(url)
            .map((response: Response): Country[] => {
                console.log(TerritorioProvider.name + ' getCountries ' + JSON.stringify(response));
                return Country.parse(response.json());
            });
    }

    getFullCharacterizationCard(lat: number, lng: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/markers/character-tab'
                + '?lat=' + lat
                + '&lng=' + lng;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getCharacterizationCard(lat: number, lng: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/marker/land-type'
                + '?lat=' + lat
                + '&lng=' + lng;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getInsideAmva(lat: number, lng: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/marker/inside'
        + '?lat=' + lat
        + '&lng=' + lng;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getAmvaMunicipalities(): Observable<Municipality[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/seguridad/municipios-AMVA';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): Municipality[] => {
                console.log(TerritorioProvider.name + ' getAmvaMunicipalities ' + JSON.stringify(response.json()));

                let municipalities: Municipality[] = Municipality.parse(response.json());
                let medellinIndex: number = municipalities.findIndex((municipality: Municipality): boolean => { return municipality.name == 'MEDELLIN'; });
                let medellin: Municipality = municipalities.splice(medellinIndex, 1)[0];
                municipalities.sort((m1: Municipality, m2: Municipality): number => { return m1.name.localeCompare(m2.name) ; })
                              .unshift(medellin);
                return municipalities;
            });
    }

    getAlert(lat: number, lng: number, idCapa: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/entorno/estacion/detalle/find-by-location'
                + '?idCapa=' + idCapa
                + '&lat=' + lat
                + '&lng=' + lng;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.get(url, { headers: headers });
    }

    getNotificationInfo(lat: number, lng: number, idApp: number, idCapa: number):Observable<string[]> {
        let url: string;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));

        if (idApp == 4) { // Conoceme cambio de territorio
            url = CONFIG_ENV.REST_BASE_URL + '/api/contenedora/marker/land-type'
                + '?lat=' + lat
                + '&lng=' + lng;

            return this.http
                .get(url, { headers: headers })
                .map((response: Response) => this.parserCharacterizationCard(response));
        } 
        else if (idApp == 5) { // Mideme Aire-Agua
            url = CONFIG_ENV.REST_BASE_URL + '/api/entorno/estacion/detalle/find-by-location'
                + '?idCapa=' + idCapa
                + '&lat=' + lat
                + '&lng=' + lng;

            return this.http
                .get(url, { headers: headers })
                .map((response: Response) => this.parserAguaAire(response));
        }
    }

    parserCharacterizationCard(response: Response): string[] {
        let msg: string = 'Estás en: '
            + this.toCapitalCaseLetter(response.json().nombreMunicipio) + '. \n'
            + this.toCapitalCaseLetter(response.json().tipoSuelo);
        console.log('getNotificationInfo', msg);

        let array = new Array<string>();
        array.push(msg);
        return array;
    }

    toCapitalCaseLetter(str: string): string {
        if (!str || str.length == 0) return str;
        else return str[0].toUpperCase() + str.substring(1).toLowerCase();
    }

    parserAguaAire(response: Response) {
        let array = new Array<string>();
        let data = response.json();
        let msg1 = '';
        let msg2 = '';

        data.forEach(element => {
            let key = Object.keys(element);

            if (key[0] === 'fecha' || key[0] === 'hora' ) {
                msg2 = msg2 +
                    this.toCapitalCaseLetter(key[0]) + 
                    ': ' +
                    element[key[0]] +
                    ' \n'; 
            } else {
                if(
                    key[0] !== 'icono' &&
                    key[0] !== 'estado' &&
                    key[0] !== 'recomendaciones' &&
                    element[key[0]].length !== 0
                ) {
                    msg1 = msg1 + 
                        this.toCapitalCaseLetter(key[0]) + 
                        ': ' +
                        element[key[0]] +
                        ' \n'; 
                } else if (
                    key[0] === 'recomendaciones' && 
                    element[key[0]].length !== 0
                ) {
                    msg1 = msg1 + 
                    this.toCapitalCaseLetter(key[0]) + 
                    ': \n' +
                    this.parseRecomendaciones(element[key[0]]);
                }
            }
            
        });

        array.push(msg1);
        array.push(msg2);

        return array;
    }

    parseRecomendaciones(recomendaciones: string): string {
        let parsedRecomendaciones = '';
        let arrayRecomendaciones = recomendaciones.split(', ');

        arrayRecomendaciones.forEach(recomendacion => {
            parsedRecomendaciones = parsedRecomendaciones + recomendacion + ' \n';
        });

        return parsedRecomendaciones
    }
}