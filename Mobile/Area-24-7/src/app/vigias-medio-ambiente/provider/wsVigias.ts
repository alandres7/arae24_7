import { CONFIG_ENV } from './../../shared/config-env-service/const-env';
import { Common } from './../../shared/utilidades/common';
import { Injectable } from '@angular/core';
import { WebService } from './../../provider/webService/ws';
import { Events } from "ionic-angular";
import { FileUploadOptions, FileTransfer, FileTransferObject } from "@ionic-native/file-transfer";
import { Observable } from "rxjs/Observable";
import { NativeGeocoder, NativeGeocoderReverseResult, NativeGeocoderForwardResult } from '@ionic-native/native-geocoder';

import { File } from '@ionic-native/file';

declare var google;
@Injectable()
export class WsVigias {
    header: any;
    token: String;
    usuario: any;
    fileTransfer: FileTransferObject = this.transfer.create();
    _zone: any;
    marker: any;

    constructor(
        private ws: WebService,
        private utilidad: Common,
        private transfer: FileTransfer,
        private events: Events,
        private nativeGeocoder: NativeGeocoder,
        private file: File,

    ) {
        this.token = localStorage.getItem('bearer');
        this.usuario = JSON.parse(localStorage.getItem('usuario'));


    }




    public obtenerVigias() {
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/vigia/usuario/' + this.usuario.id)
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                });
        });
    }


    public obtenerTodosLasVigias() {
        this.header = {

            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/vigia/estado/' + "APROBADO")
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                });
        });

    }


    public registrar(url: string, datos: any, datos2: any, datos3: any, multidemia: any, respuestaDinamicas:any) {
        var vigia: any = [];
        let Municipio: any;
        let Direccion: any;
        let posicion: any = this.events.publish('obtenerPosicion');

        this.nativeGeocoder.reverseGeocode(posicion[0].lat, posicion[0].lng)
        // @ts-ignore
            .then((result: NativeGeocoderReverseResult) => {
                console.log(Municipio = JSON.stringify(result));
                Municipio = result.subAdministrativeArea;
            }).catch((error: any) => console.log(error));
        let latlng = { lat: posicion[0].lat, lng: posicion[0].lng };
        this.codificarDireccion(latlng, 'location')
            .then((data) => {
                Direccion = data.descripcion;
                let newName = this.createFileName();
                let options: FileUploadOptions = {
                    fileKey: 'multimedia',
                    fileName: newName,
                    mimeType: "multipart/form-data",
                    params: {
                        'municipio': Municipio,
                        'idAplicacion': datos.id,
                        'idUsuario': this.usuario.id,
                        'idIcono': 2,
                        'latitud': posicion[0].lat,
                        'longitud': posicion[0].lng,
                        'descripcion': "prueba grabacion vigia",
                        'direccion': Direccion,
                        'idNodoArbol': datos2,
                        'crear': datos.crear,
                        'recorridoArbol': datos3,
                        'respuestaFormulario': JSON.stringify(respuestaDinamicas),
                        'textoComentario': "prueba grabacion vigia",
                        'idVigia': 123456,
                        'multimedia': newName
                    },
                    headers: { 'Authorization': this.token }
                }

                let correctPath = multidemia.fullPath.substr(0, multidemia.fullPath.lastIndexOf('/') + 1);
                console.log('correct path, ', correctPath);
                this.file.copyFile(correctPath, multidemia.name, this.file.dataDirectory, newName).then(success => {
                    //this.lastImage = newFileName;
                    console.log('copio el archivo, resultado ', success);
                    let ruta = this.pathForImage(newName);
                    console.info("ruta local ---> ", ruta);

                    this.fileTransfer.upload(ruta, url, options)
                        .then((data) => {
                            console.log("respuesta registrar vigias", data);
                            this.events.publish('cerrarModalAvistamientos');

                        }, (err) => {
                            console.log("error respuesta registrar vigias", err);
                        });

                }, error => {
                    console.log('Error while storing file. ', error);
                });
            })

    }


    public codificarDireccion(data: any, modo: string): Promise<any> {
        let geocoder = new google.maps.Geocoder();
        let config: any;
        if (modo == 'location') {
            config = { 'location': data }
        } else {
            config = { 'address': data }
        }
        return new Promise((resolve, reject) => {
            geocoder.geocode(config, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    let data = {
                        latitud: results[0].geometry.location.lat(),
                        longitud: results[0].geometry.location.lng(),
                        descripcion: results[0].formatted_address
                    };

                    resolve(data);
                } else {
                    console.error('GmapsMovilidad:codificarDireccion-error', status);
                    reject(status);
                }
            });
        });


    }



    public registraComentario(url: string, comentarios: any) {



    }

    private createFileName() {
        var d = new Date(),
            n = d.getTime(),
            newFileName = n + ".jpg";
        return newFileName;
    }

    // copiar la imagen al directiorio local
    private copyFileToLocalDir(namePath, currentName, newFileName) {
        this.file.copyFile(namePath, currentName, this.file.dataDirectory, newFileName).then(success => {
            //this.lastImage = newFileName;
            console.log('copio el archivo, resultado ', success);
        }, error => {
            console.log('Error cuando copio el archivo. Error: ', error);
        });
    }


    // obtener la ruta de la imagen copiada
    public pathForImage(img) {
        if (img === null) {
            return '';
        } else {
            return this.file.dataDirectory + img;
        }
    }

    public obtenerArbolDesicion() {
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/nodo-arbol/arbol/' + "23")
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                });
        });
    }

    // codigo para obtener el formulario dinamico
    public obtenerFomrularioDinamico(nodo: string) {
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/formulario/formularioPorIdObjeto/' + nodo)
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                });
        });
    }

    // codigo para validar si existe reporte similar en la ubicacion
    public validarExistenciaReporteSimilar(informacion: any, informacion2: any) {
        var vigia: any = [];
        let posicion: any = this.events.publish('obtenerPosicion');
        vigia.idCapa = 1;
        vigia.latitud = posicion[0].lat;
        vigia.longitud = posicion[0].lng;
        vigia.aliasReporte = informacion.nombre;
        vigia.nombre = informacion.nombre;
        this.header = {
            'Content-Type': 'application/json',
            'Authorization': this.token
        };
        return new Promise((resolve, reject) => {
            this.ws.url = CONFIG_ENV.REST_BASE_URL;
            this.ws.wsGet(this.header, '/api/vigia/validarReporte/' + vigia.idCapa + '/' + vigia.latitud + '/' + vigia.longitud + '/' + vigia.aliasReporte + '/' + vigia.nombre)
                .subscribe((pArray) => {
                    resolve(pArray);
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                });
        });
    }




    // IMPORTANTE: CODIGO TEMPORAL
    public registrarRadicado(url: string, vigia: any) {
        let posicion: any = this.events.publish('obtenerPosicion');
        //vigia.latitud = posicion[0].lat;
        //vigia.longitud = posicion[0].lng;
        //vigia.idUsuario = this.usuario.id;
        let resultado1;
        this.nativeGeocoder.reverseGeocode(posicion[0].lat, posicion[0].lng)
        // @ts-ignore
            .then((result: NativeGeocoderReverseResult) => console.log(resultado1 = JSON.stringify(result)))
            .catch((error: any) => console.log(error));
        let promise = new Promise((resolve, reject) => {
            let ClienteREST = {
                usuario: "APP_24_7",
                password: "User24"
            }

            let Tramite = {
                codigoTramite: 0,
                idProceso: 1,
                comentarios: "Avistamiento de Fauna prueba",
                mensaje: "Se observa un animal parecido a un leopardo pasando la carretera",
                evidencia: "QEA=",
                tipoMime: "JPG",
                indices: [
                    [
                        "Direccion",
                        "Cra 45 nro 23a 67"
                    ],
                    [
                        "Latitud",
                        "79,343434435454"
                    ],
                    [
                        "Longitud",
                        "-5,87845885"
                    ],
                    [
                        "Municipio",
                        "La Estrella"
                    ]
                ],
                radicado: "",
                fechaRadicacion: ""
            }
            var myData: any = {};
            myData.ClienteRestData = ClienteREST;
            myData.TramiteData = Tramite;
            this.ws.url = "http://webservices.metropol.gov.co/WsAMVA/api/Tramites/IniciarTramite";
            this.ws.wsPost("", myData)
                .toPromise()
                .then(
                res => { // Success
                    console.log(" 2", JSON.stringify(res));
                    resolve();
                });

        });
        return promise;



    }




}





