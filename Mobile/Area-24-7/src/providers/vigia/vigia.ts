import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { AlertController, AlertOptions } from 'ionic-angular';

import { FileTransfer, FileUploadOptions, FileTransferObject, FileUploadResult } from '@ionic-native/file-transfer';
import { SocialSharing } from '@ionic-native/social-sharing';

import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

import { Story } from '../../entities/story';
import { Comment } from '../../entities/comment';
import { Vigia } from '../../entities/vigia';
import { LayerProvider } from '../layer/layer';
import { AppLayer } from '../../entities/app-layer';
import { Layer } from '../../entities/layer';
import { GeoLayer } from '../../entities/geo-layer';
import { OtherLayer } from '../../entities/other-layer';
import { Common } from '../../app/shared/utilidades/common';
import { DecisionTree } from '../../entities/decision-tree';

@Injectable()
export class VigiaProvider {

    private fileTransferObject: FileTransferObject;

    constructor(private http: Http
        , private fileTransfer: FileTransfer
        , private layerProvider: LayerProvider
        , private socialSharing: SocialSharing
        , private alertCtrl: AlertController
        , private common: Common) {
        this.fileTransferObject = fileTransfer.create();
    }

    getVigia(markerId: number, tipo: string): Observable<any> {
        let url: string;
        if (tipo == 'marker') {
            url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/obtenerPorIdVigiaOIdMarcador'
                + '?idMarcador=' + markerId;
        }
        else {
            if (tipo == 'select') {
                url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/obtenerPorIdVigiaOIdMarcador'
                    + '?idVigia=' + markerId;
            }
        }

        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): Vigia => {
                return (response.json());
            });
    }


    getVigiaLayers(): Observable<{ id: number, name: string }[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/core/category/2/1';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): any => {
                console.log(VigiaProvider.name + ' getVigiaLayers ' + JSON.stringify(response), response.json());
                let responseArray = response.json(); 8
                let vigiaLayers: { id: number, name: string }[]
                    = responseArray.map((item: any) => { return { id: item.idCategoria, name: item.nombre }; });
                return vigiaLayers;
            });
    }

    postVigia(vigia: Vigia, direccion: string, arbol: DecisionTree): Observable<FileUploadResult> {
        let usuario: any = JSON.parse(localStorage.getItem('usuario'));
        let url = CONFIG_ENV.REST_BASE_URL + '/api/vigia'
            + '?nivelCapa=CAPA'
            + '&idNodoArbol=' + arbol.id
            + '&activo=1'
            + '&idUsuario=' + usuario.id
            + '&idCapaCategoria=3'
            + '&multimedia=' + vigia.urlMedia[0].url
            + '&latitud=' + vigia.lat
            + '&longitud=' + vigia.lng
            + '&descripcion=' + vigia.description
            + '&direccion=' + direccion;

        let options: FileUploadOptions = {
            fileKey: 'multimedia',
            mimeType: 'multipart/form-data',
            headers: { 'Authorization': localStorage.getItem('bearer') }
        };
        return Observable.fromPromise(this.fileTransferObject.upload(vigia.urlMedia[0].url, encodeURI(url), options));
    }

    getReports(coordenadas: any): Observable<any> {
        let url: string;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/findByRadioYCapa/CAPA/3'
            + '?latitud=' + coordenadas.lat
            + '&longitud=' + coordenadas.lng
            + '&radioAccion=500';

        return this.http
            .get(url, { headers: headers })
            .map((response: Response): void => {
                // console.log(VigiaProvider.name + ' getReports ' + JSON.stringify(response));
                return (response.json());
            });
    }

    getAuthority(id: number): Observable<void> {
        let url: string;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        url = CONFIG_ENV.REST_BASE_URL + `/api/autoridad/${id}`
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): void => {
                return (response.json());
            });
    }



    getVigiaComments(vigiaId: number): Observable<Comment[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/comentarioVigia/'
            + vigiaId;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): Comment[] => {
                return Comment.parse(response.json());
            });
    }

    postVigiaComment(comment: Comment, vigiaId: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/vigia/comentarioVigia/'
            + '?idVigia=' + vigiaId
            + '&descripcion=' + comment.content
            + '&username=' + localStorage.getItem('username');
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.post(url, null, { headers: headers });
    }


    shareViaTwitter(message: string, urlMedia: string): void {
        this.common.presentLoading();
        if (message.length > 280) message = message.substr(0, 277) + '...';
        this.socialSharing.shareViaTwitter(message, urlMedia + '.jpg')
            .then((value: any): any => {
                console.log('socialSharing shareViaTwitter ' + JSON.stringify(value));
                this.common.dismissLoading();
            })
            .catch((reason: any): any => {
                console.log('socialSharing shareViaTwitter error ' + JSON.stringify(reason));
                this.common.dismissLoading();
            });
    }

    shareViaFacebook(message: string, urlMedia: string): void {
        console.log(' shareViaFacebook message: ' + message + ' urlMedia: ' + urlMedia);
        this.common.presentLoading();
        let options: AlertOptions = {
            title: 'Información'
            , message: 'Reporte copiado al portapaleles.'
            , buttons: [
                {
                    text: 'Aceptar'
                    , handler: () => {
                        this.socialSharing.shareViaFacebookWithPasteMessageHint(message, urlMedia + '.jpg', null, 'Avistamiento copiado al portapaleles.')
                            .then((value: any): any => {
                                console.log('socialSharing shareViaFacebook ' + JSON.stringify(value));
                                this.common.dismissLoading();
                            })
                            .catch((reason: any): any => {
                                console.log('socialSharing shareViaFacebook error ' + JSON.stringify(reason));
                                this.common.dismissLoading();
                            });
                    }
                }
            ]
        };
        this.alertCtrl.create(options).present();
    }

    shareViaInstagram(message: string, urlMedia: string): void {
        this.common.presentLoading();
        let options: AlertOptions = {
            title: 'Información'
            , message: 'Reporte copiado al portapaleles.'
            , buttons: [
                {
                    text: 'Aceptar'
                    , handler: () => {
                        this.socialSharing.shareViaInstagram(message, urlMedia)
                            .then((value: any): any => {
                                console.log('socialSharing shareViaInstagram ' + JSON.stringify(value));
                                this.common.dismissLoading();
                            })
                            .catch((reason: any): any => {
                                console.log('socialSharing shareViaInstagram error ' + JSON.stringify(reason));
                                this.common.dismissLoading();
                            });
                    }
                }
            ]
        };
        this.alertCtrl.create(options).present();
    }

}

