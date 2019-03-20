import { CONFIG_ENV } from './../../shared/config-env-service/const-env';
import { Common } from './../../shared/utilidades/common';
import { Injectable } from '@angular/core';
import { WebService } from './../../provider/webService/ws';
import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer';
import { Events } from "ionic-angular";
import { AngularFireList } from "angularfire2/database";


@Injectable()
export class WsAvistamiento {
    header: any;
    token: String;
    usuario: any;
    nav: any;
    fileTransfer: FileTransferObject = this.transfer.create();
    respuesta1: any;
    comentarios: AngularFireList<any[]>;



    constructor(
        private ws: WebService,
        private utilidad: Common,
        private transfer: FileTransfer,
        private events: Events
    ) {

        this.token = localStorage.getItem('bearer');
        this.usuario = JSON.parse(localStorage.getItem('usuario'));


    }




    public obtenerAvistamientos() {
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/avistamiento/usuario/' + this.usuario.id)
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                })
        });
    }


    public obtenerTodosLosAvistamientos() {
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/avistamiento/estado/' + "1")
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                })
        });

    }


    public registrar(url: string, avistamiento: any) {
        let posicion: any = this.events.publish('obtenerPosicion');
        avistamiento.latitud = posicion[0].lat;
        avistamiento.longitud = posicion[0].lng;
        avistamiento.idUsuario = this.usuario.id;  // se envia la variable 1 para tipo imagenes
        avistamiento.activo = true;
        let options: FileUploadOptions = {
            fileKey: 'multimedia',
            fileName: this.usuario.id + avistamiento.name,
            mimeType: 'multipart/form-data',
            params: avistamiento,

            headers: {
                'Authorization': this.token,
                'Content-Type': 'multipart/form-data',
                'Accept': 'text/plain'
            }

        }


        this.fileTransfer.upload(avistamiento.imagenPrincipal, url, options)
            .then((data) => {
                this.events.publish('cerrarModalAvistamientos');

            }, (err) => {

            })
    }


    public registraComentario(url: string, comentarios: any) {
        let promise = new Promise((resolve, reject) => {
            let header = { 'Authorization': this.token };
            let respuesta = {};
            this.ws.url = url;
            this.ws.wsPost(header, comentarios)
                .toPromise()
                .then(
                res => { // Success
                    console.log(" 2", JSON.stringify(res));
                    this.respuesta1 = JSON.parse(res);
                    if (this.respuesta1._body === "comentario adicionado correctamente") {
                   //     this.comentarios.push({ descripcion: comentarios.descripcion, usuario: this.usuario.username, fechaPublicacion: comentarios.fechaPublicacion, id: comentarios.id });
                    }
                    resolve();
                }
                );

        });
        return promise;

    }

    public obtenerArbolDesicion() {
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL; // https://api.myjson.com/bins/1alin3 
            this.ws.wsGet(this.header, '/api/nodo-arbol/arbol/' + "23")
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                })
        });
    }

}





