import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { AlertController, AlertOptions } from 'ionic-angular';

import { FileTransfer, FileUploadOptions, FileTransferObject, FileUploadResult } from '@ionic-native/file-transfer';
import { SocialSharing } from '@ionic-native/social-sharing';

import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

import { Story } from '../../entities/story';
import { Comment } from '../../entities/comment';
import { Avistamiento } from '../../entities/avistamiento';
import { LayerProvider } from '../layer/layer';
import { AppLayer } from '../../entities/app-layer';
import { Layer } from '../../entities/layer';
import { GeoLayer } from '../../entities/geo-layer';
import { OtherLayer } from '../../entities/other-layer';
import { Common } from '../../app/shared/utilidades/common';
import { DecisionTree } from '../../entities/decision-tree';
import { MessageProvider } from '../message/message';

@Injectable()
export class AvistamientoProvider {

    private fileTransferObject: FileTransferObject;

    constructor(private http: Http
              , private fileTransfer: FileTransfer
              , private layerProvider: LayerProvider
              , private socialSharing: SocialSharing
              , private alertCtrl: AlertController
              , private common: Common
              , private messageProvider: MessageProvider) 
    {
        this.fileTransferObject = fileTransfer.create();
    }

    getAvistamientos(name: string, lat: number, lon: number) {

    }
    

    getAvistamiento(markerId: number): Observable<Avistamiento> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/find'
                + '?idMarcador=' + markerId;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): any => {
                console.log(AvistamientoProvider.name + ' getAvistamiento ' + JSON.stringify(response));
                return new Avistamiento(response.json());
            });
    }
       
    /*
    getAvistamientoLayers(): Observable<{ id: number, name: string, layerLevel: string }[]> {
        let observable = new Observable<{ id: number, name: string, layerLevel: string }[]>(obs => {
            let avistamientoApp: AppLayer = this.layerProvider
                .getTree()
                .find((app: AppLayer): boolean => { return app.id == 3; }); //Asómbrate
            let avistamientoLayersResult: { id: number, name: string, layerLevel: string }[] = (avistamientoApp.children) 
                ? avistamientoApp
                    .children
                    .filter((layer: Layer): boolean => { return layer instanceof GeoLayer && layer.layerType == 'AVISTAMIENTO'; })
                    .map((layer: GeoLayer) => { return { id: layer.id, name: layer.name, layerLevel: 'CAPA' }; })
                : [];
            let avistamientoLayers: Layer[] = avistamientoApp.children
                .filter((layer: Layer): boolean => { return !(layer instanceof GeoLayer || layer instanceof OtherLayer); });
            avistamientoLayers.forEach((layer: Layer): void => {
                if (layer.children) {
                    avistamientoLayersResult.concat(layer.children
                        .filter((layer: Layer): boolean => { return layer instanceof GeoLayer && layer.layerType == 'AVISTAMIENTO'; })
                        .map((layer: GeoLayer) => { return { id: layer.id, name: layer.name, layerLevel: 'CATEGORIA' }; }));      
                }
                else {
                    this.layerProvider.getNLayer('CATEGORIA', layer.id).subscribe(
                        (response: Response): void => {
                            debugger;
                            console.log(AvistamientoProvider.name + ' getAvistamientoLayers ' + JSON.stringify(response));
                            layer.children = AppLayer.parseStrategy(response.json());
                            avistamientoLayersResult.concat(layer.children
                                .filter((layer: Layer): boolean => { return layer instanceof GeoLayer && layer.layerType == 'AVISTAMIENTO'; })
                                .map((layer: GeoLayer) => { return { id: layer.id, layerLevel: 'CATEGORIA', name: layer.name }; }));      
                        },
                        (error: any): void => console.log(AvistamientoProvider.name + ' getAvistamientoLayers error ' + JSON.stringify(error))
                    );
                }
            });
        });
        return observable;
    }
*/

    // getAvistamientoLayers(): Observable<{ id: number, name: string, decisionTree: DecisionTree }[]> {
    //     let url = CONFIG_ENV.REST_BASE_URL + '/api/capa/find'
    //             + '?idAplicacion=3';
    //     let headers = new Headers();
    //     headers.append('Content-Type', 'application/json');
    //     headers.append('Authorization', localStorage.getItem('bearer'));
    //     return this.http
    //         .get(url, { headers: headers })
    //         .map((response: Response): any => {
    //             console.log(AvistamientoProvider.name + ' getAvistamientoLayers ' + JSON.stringify(response));
    //             let responseArray = response.json();
    //             let avistamientoLayers: { id: number, name: string, decisionTree: DecisionTree }[] 
    //                 = responseArray.map((item: any) => { return { id: item.id, name: item.nombre, decisionTree: new DecisionTree(item.nodoRaiz) }; }); 
    //             return avistamientoLayers;
    //         });        
    // }

    getAvistamientoLayers(): Observable<{ id: number, name: string }[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/core/category/223/8';
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): any => {
                console.log(AvistamientoProvider.name + ' getAvistamientoLayers ' + JSON.stringify(response), response.json());
                let responseArray = response.json();
                let avistamientoLayers: { id: number, name: string }[] 
                    = responseArray.map((item: any) => { return { id: item.idCategoria, name: item.nombre}; }); 
                return avistamientoLayers;
            });        
    }

    postAvistamiento(avistamiento: Avistamiento): Observable<FileUploadResult> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento'
            + '?username=' + localStorage.getItem('username')
            + '&idCapaCategoria='+ avistamiento.layerId
            + '&latitud=' + avistamiento.lat
            + '&longitud=' + avistamiento.lng
            + '&nombreComun=' + avistamiento.commonName
            + '&descripcion=' + avistamiento.description
            + '&nombreCientifico=' + avistamiento.scientificName
            + '&nivelCapa=CATEGORIA';
        let options: FileUploadOptions = {
            fileKey: 'multimedia',
            mimeType: 'multipart/form-data',
            headers: { 'Authorization': localStorage.getItem('bearer') }
        };
        return Observable.fromPromise(this.fileTransferObject.upload(avistamiento.urlMedia, encodeURI(url), options));
    }

    /*
    postAvistamiento(avistamiento: Avistamiento): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento'
                + '?idUsuario=' + JSON.parse(localStorage.getItem('usuario')).id;
                + '&idCapa=' + avistamiento.layerId
                + '&latitud=' + avistamiento.lat
                + '&longitud=' + avistamiento.lng
                + '&nombreComun=' + avistamiento.commonName
                + '&descripcion=' + avistamiento.description
                + '&nombreCientifico=' + avistamiento.scientificName;
        let headers = new Headers();
        headers.append('Content-Type', 'multipart/form-data');
        headers.append('Accept', 'text/plain');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.post(url, { }, { headers: headers });
    }*/


    getStories(avistamientoId: number): Observable<Story[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/historia/avistamiento/' 
            + avistamientoId + '?nickname=' + localStorage.getItem('username');
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): Story[] => {
                console.log(AvistamientoProvider.name + ' getStories ' + JSON.stringify(response));
                return Story.parse(response.json());
            });
    }

    postStory(story: Story, avistamientoId: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/historia/'
                + '?idAvistamiento=' + avistamientoId
                + '&titulo=' + story.title
                + '&texto=' + story.content
                + '&username=' + localStorage.getItem('username');
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.post(url, null, { headers: headers });
    }

    getAvistamientoComments(avistamientoId: number): Observable<Comment[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/comentario/avistamiento/' 
            + avistamientoId + '?nickname=' + localStorage.getItem('username');
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): Comment[] => {
                console.log(AvistamientoProvider.name + ' getComments ' + JSON.stringify(response));
                return Comment.parse(response.json());
            });
    }

    getStoryComments(storyId: number): Observable<Comment[]> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/historia/comentario/historia/' 
            + storyId  + '?nickname=' + localStorage.getItem('username');
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http
            .get(url, { headers: headers })
            .map((response: Response): Comment[] => {
                console.log(AvistamientoProvider.name + ' getComments ' + JSON.stringify(response));
                return Comment.parse(response.json());
            });
    }

    postAvistamientoComment(comment: Comment, avistamientoId: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/comentario/'
                + '?idAvistamiento=' + avistamientoId
                + '&descripcion=' + comment.content
                + '&username=' + localStorage.getItem('username');                
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        return this.http.post(url, null, { headers: headers });
    }

    postStoryComment(comment: Comment, storyId: number): Observable<Response> {
        let url = CONFIG_ENV.REST_BASE_URL + '/api/avistamiento/historia/comentario/'
                + '?idHistoria=' + storyId
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
        this.messageProvider.getByNombreIdentificador('avistamiento copiado').subscribe(
            response => {
                console.log(AvistamientoProvider.name + ' shareViaFacebook getByNombreIdentificador ' + JSON.stringify(response));
                let msg = response.json();     
                let options: AlertOptions = {
                    title: msg.titulo
                    , message: msg.descripcion
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
            },
            error => console.log(AvistamientoProvider.name + ' shareViaFacebook getByNombreIdentificador error ' + JSON.stringify(error)));
    }

    shareViaInstagram(message: string, urlMedia: string): void {
        this.common.presentLoading();
        this.messageProvider.getByNombreIdentificador('avistamiento copiado').subscribe(
            response => {
                console.log(AvistamientoProvider.name + ' shareViaInstagram getByNombreIdentificador ' + JSON.stringify(response));
                let msg = response.json();
                let options: AlertOptions = {
                    title: msg.titulo
                    , message: msg.descripcion
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
            },
            error => console.log(AvistamientoProvider.name + ' shareViaInstagram getByNombreIdentificador error ' + JSON.stringify(error))
        );
    }
}
