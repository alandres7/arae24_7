import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';

import { User } from '../../entities/user';
import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

@Injectable()
export class AuthenticationProvider {

    constructor(private http: Http) {}

    enroll(user: User): Observable<Response> {
        console.log("AuthenticationProvider enroll " + JSON.stringify(user.toJsonObject()));
        let url = CONFIG_ENV.REST_BASE_URL + '/api/registro';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post(url, JSON.stringify(user.toJsonObject()), { headers: headers });
    }

    login(user: User): Observable<Response> {
        console.log("AuthenticationProvider login " + JSON.stringify(user.toJsonObject()));
        let url = CONFIG_ENV.REST_BASE_URL + '/api/login';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post(url, JSON.stringify(user.toJsonObject()), { headers: headers });
    }

    restore(email: string): Observable<Response> {
        console.log("AuthenticationProvider restore " + email);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario-restablecer-pass'
                + '?username=' + email;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.put(url, {}, { headers: headers });
    }

    update(user: User): Observable<Response> {
        console.log("AuthenticationProvider update " + JSON.stringify(user.updateJsonObject()));
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.put(url, JSON.stringify(user.updateJsonObject()), { headers: headers });
    }

    get(): Observable<Response> {
        let username: string = localStorage.getItem('username');
        username = encodeURIComponent(username);

        console.log("AuthenticationProvider post " + username);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario-por-username?username=' + username;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.post(url, {}, { headers: headers });
    }

    existUser(username: string): Observable<Response> {
        username = encodeURIComponent(username);
        let url = CONFIG_ENV.REST_BASE_URL + '/api/existeUsuario?nickname=' + username;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post(url, {}, { headers: headers });
    }

    updatePassword(username: string, currentPassword: string, newPassword: string): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/usuario/actualizar-password';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        let params = { 
              username: username
            , currentPassword: currentPassword
            , newPassword: newPassword 
        };
        return this.http.put(url, JSON.stringify(params), { headers: headers });
    }
}
