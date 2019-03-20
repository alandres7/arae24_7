import { Http, Response, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { AlertController, AlertOptions } from 'ionic-angular';

import { FileTransfer, FileUploadOptions, FileTransferObject, FileUploadResult } from '@ionic-native/file-transfer';
import { SocialSharing } from '@ionic-native/social-sharing';

import { Observable } from 'rxjs/Observable';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

import { LayerProvider } from '../layer/layer';
import { AppLayer } from '../../entities/app-layer';
import { Layer } from '../../entities/layer';
import { Common } from '../../app/shared/utilidades/common';

@Injectable()
export class MidemeProvider {

    private fileTransferObject: FileTransferObject;

    constructor(private http: Http
        , private fileTransfer: FileTransfer
        , private layerProvider: LayerProvider
        , private socialSharing: SocialSharing
        , private alertCtrl: AlertController
        , private common: Common) {
        this.fileTransferObject = fileTransfer.create();
    }
    getForms() {
        let url: string;
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', localStorage.getItem('bearer'));
        url = CONFIG_ENV.REST_BASE_URL + '/api/formulario';
        return this.http
        .get(url, { headers: headers })
        .map((response: Response): void => {
            return (response.json());
        });
    }
}

