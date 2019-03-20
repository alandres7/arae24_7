import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Avistamiento } from '../../entities/avistamiento';
import { Vigia } from '../../entities/vigia';
import { Media, MediaObject } from '@ionic-native/media';
import { File } from '@ionic-native/file';
import { Slides } from 'ionic-angular';
import { MediaCapture, MediaFile, CaptureError, CaptureVideoOptions, CaptureAudioOptions } from '@ionic-native/media-capture';
import { ImageResizer, ImageResizerOptions } from '@ionic-native/image-resizer';
import { VideoEditor, GetVideoInfoOptions } from '@ionic-native/video-editor';
import {
    NavController,
    NavParams,
    ActionSheetController,
    normalizeURL,
    Platform
} from 'ionic-angular';

import { Camera, CameraOptions, DestinationType, PictureSourceType, Direction } from '@ionic-native/camera';
import { FileUploadResult } from '@ionic-native/file-transfer';

import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';
import { Geoposition } from '@ionic-native/geolocation';
import { Common } from '../../app/shared/utilidades/common';
import { DecisionTreeComponent } from '../decision-tree/decision-tree';
import { DecisionTree } from '../../entities/decision-tree';
import { DecisionTreeProvider } from "../../providers/decision-tree/decision-tree";
import { MapSelectLocationComponent } from '../map-select-location/map-select-location';
import { VigiaProvider } from '../../providers/vigia/vigia';
import { DecisionTreeVigiaComponent } from '../decision-tree-vigia/decision-tree-vigia';
import { GoogleGeocoderProvider } from '../../providers/google-geocoder/google-geocoder';
import { LayerProvider } from '../../providers/layer/layer';
import { AppLayer } from '../../entities/app-layer';
import { App } from 'ionic-angular/components/app/app';
import { CuidameSelectUbicationComponent } from '../cuidame-select-ubication/cuidame-select-ubication';
import { UploadServiceProvider, UploadVideo } from '../../providers/upload-service/upload-service';
import { Subscription } from 'rxjs';
import { MessageProvider } from '../../providers/message/message';
declare var google: any;

@Component({
    selector: 'cuidame-create',
    templateUrl: 'cuidame-create.html'
})
export class CuidameCreateComponent implements OnInit, OnDestroy {
    @ViewChild(Slides) slides: Slides;
    private decisionTreeSubscribe: Subscription
    private formGroup: FormGroup;
    private color: string;
    private coordenadas: any;
    private municipio: string;
    private avistamiento: Avistamiento;
    private vigia: Vigia;
    private pictureUri: any[] = [];
    private geoposition: Geoposition;
    private pictureSource: PictureSourceType;
    private address: any = {};
    private directions: any;
    private actionSheet: any;

    private vigiaLayers: {
        id: number;
        name: string;
    }[];

    // variables para captura de audio
    private recording: boolean = false;
    private filePath: string;
    private fileName: string;
    private audio: MediaObject;
    private audioList: any[] = [];

    private usoArbol: boolean = false;
    private otraEspecie: boolean = false;
    private layerId: number;

    constructor(
        private navCtrl: NavController,
        private navParams: NavParams,
        private formBuilder: FormBuilder,
        private camera: Camera,
        private vigiaProvider: VigiaProvider,
        private locationChange: LocationChangeProvider,
        private common: Common,
        private actionSheetCtrl: ActionSheetController,
        private decisionTreeProvider: DecisionTreeProvider,
        private geocoder: GoogleGeocoderProvider,
        private layerProvider: LayerProvider,
        private media: Media,
        private file: File,
        private mediaCapture: MediaCapture,
        public platform: Platform,
        private _upload: UploadServiceProvider,
        private imageResizer: ImageResizer,
        private videoEditor: VideoEditor,
        private messageProvider: MessageProvider
    ) {
        this.directions = new google.maps.Geocoder;
        this.color = navParams.get('color');
        this.layerId = navParams.get('layerId');

        //Formulario de la informacion del reporte
        this.formGroup = formBuilder.group({
            locationFormCtrl: [
                '',
                Validators.compose([Validators.required])
            ],
            descriptionFormCtrl: [
                '',
                Validators.compose([Validators.minLength(3), Validators.maxLength(255), Validators.required])
            ],
            vigiaTypeFormCtrl: [
                '',
            ]
        });
    }

    ngOnInit(): void {
        this.geoposition;
        this.presentActionSheet();
    }

    ngOnDestroy() {
        this.actionSheet.dismiss();
        // this.decisionTreeSubscribe.unsubscribe();
    }

    //Opciones para seleccionar multimedia
    presentActionSheet(): void {
        //Validamos que aun no se hayan cargado 5 archivos
        if (this.pictureUri.length >= 5) {
            this.common.appToast({ mensaje: 'Ya alcanzo el límite de archivos', duration: 2000, posicion: 'bottom' })
        }
        else {
            this.actionSheet = this.actionSheetCtrl.create(
                {
                    title: 'Elija el origen del multimedia',
                    buttons: [
                        {
                            text: 'Seleccionar desde galería',
                            handler: () => {
                                this.pictureSource = PictureSourceType.PHOTOLIBRARY;
                                this.takePictureAndLocation();
                            }
                        },
                        {
                            text: 'Tomar una fotografía',
                            handler: () => {
                                this.pictureSource = PictureSourceType.CAMERA
                                this.takePictureAndLocation();
                            }
                        },
                        {


                            text: 'Grabar un video',
                            handler: () => {
                                this.takeVideo();
                            }
                        },
                        {
                            text: 'Grabar un audio',
                            handler: () => {
                                this.takeAudio();
                            }
                        },
                    ]
                });
            this.actionSheet.present();
        }
    }
    //Tomar una fotografia o seleccionar una imagen desde la galeria
    takePictureAndLocation(): void {
        // this.common.presentLoading();
        const options: CameraOptions = {
            sourceType: this.pictureSource,
            cameraDirection: this.camera.Direction.BACK,
            correctOrientation: true,
            quality: 100,
            destinationType: this.camera.DestinationType.FILE_URI,
            encodingType: this.camera.EncodingType.JPEG,
            mediaType: this.camera.MediaType.PICTURE,
            saveToPhotoAlbum: false,
        };
        this.camera
            .getPicture(options)
            .then(
                (pictureUri: string): void => {
                    this.common.presentLoading();
                    let options = {
                        uri: normalizeURL(pictureUri),
                        quality: 100,
                        width: 480,
                        height: 800,
                        base64: false,
                    } as ImageResizerOptions;

                    this.imageResizer
                        .resize(options)
                        .then((filePath: string) => {
                            this.common.dismissLoading();
                            this.pictureUri.push({
                                url: normalizeURL(filePath),
                                tipo: 'image'
                            });
                            this.slides.slideNext();
                        })
                        .catch(e => console.log(e));
                }
            )
            .catch(
                (reason: any): any =>
                    console.log(
                        CuidameCreateComponent.name +
                        ' takePicture error ' +
                        JSON.stringify(reason)
                    )
            );
    }

    selectLocationFromMap(): void {
        // Se abre modal de mapa para que el usuario seleccione ubicacion
        // this.pictureUri.push({
        //     tipo: 'video',
        //     url: 'http://area247-api.adacsc.co/api/multimedia/96054'
        // },
        //     {
        //         tipo: 'image',
        //         url: 'http://area247-api.adacsc.co/api/multimedia/96055'
        //     },
        //     {
        //         tipo: 'audio',
        //         url: 'http://area247-api.adacsc.co/api/multimedia/96142'
        //     },
        //     {
        //         tipo: 'audio',
        //         url: 'http://area247-api.adacsc.co/api/multimedia/96142'
        //     },
        //     )
        let SelectLocationModal = this.common.createModal(CuidameSelectUbicationComponent, { color: this.color, layerId: this.layerId });
        SelectLocationModal.onDidDismiss((address): void => {
            this.formGroup.controls.locationFormCtrl.setValue(address.direction);
            this.coordenadas = address.coordenadas;
        });
        SelectLocationModal.present();
    }

    takeVideo() {
        if (!this.platform.is("cordova")) {
            this.common.appToast({ mensaje: 'Dispositivo no puede grabar video', posicion: 'bottom', duration: 2000 });
        } else {
            this.pictureSource = PictureSourceType.CAMERA;
            let options: CaptureVideoOptions = { limit: 1, quality: 1, duration: 60 };

            this.mediaCapture.captureVideo(options).then(
                (data: MediaFile[]) => {
                    this.common.presentLoading();
                    let video = data[0];
                    this.videoEditor.transcodeVideo({
                        fileUri: normalizeURL(video.fullPath),
                        outputFileName: 'output-name',
                        outputFileType: this.videoEditor.OutputFileType.MPEG4,
                        saveToLibrary: false,
                        maintainAspectRatio: true,
                        width: 240,
                        height: 320,
                        videoBitrate: 250000,
                    })
                        .then((fileUri: string) => {
                            this.common.dismissLoading();
                            this.pictureUri.push({
                                tipo: 'video',
                                url: normalizeURL(fileUri),
                            })
                            this.slides.slideNext();
                        })

                        .catch((error: any) => console.log('error', error.json()));
                },
                (err: CaptureError) => {
                    this.common.dismissLoading();
                    console.log(JSON.stringify(err));
                }
            );
        }
    }

    //Grabar un audio
    takeAudio() {
        if (!this.platform.is("cordova")) {
            this.common.appToast({ mensaje: 'Dispositivo no puede grabar video', posicion: 'bottom', duration: 2000 });
        } else {
            this.common.presentLoading();
            this.pictureSource = PictureSourceType.CAMERA;
            let options: CaptureAudioOptions = { limit: 1, duration: 60 };

            this.mediaCapture.captureAudio(options).then(
                (data: MediaFile[]) => {
                    let audio = data[0];
                    this.common.dismissLoading();
                    this.pictureUri.push({
                        tipo: 'audio',
                        url: normalizeURL(audio.fullPath),
                    })
                },
                (err: CaptureError) => {
                    this.common.dismissLoading();
                    console.log(JSON.stringify(err));
                }
            );
        }
    }


    identificarReporte(): void {
        //Se abre pagina que contiene el arbol de decision
        this.decisionTreeSubscribe = this.decisionTreeProvider.getTree(this.layerId).subscribe((response) => {
            if (this.pictureUri) {
                this.pictureUri.forEach(element => {
                    // console.log(element.JSON.stringify())
                });
                this.navCtrl.push(DecisionTreeVigiaComponent, {
                    info: this.formGroup.controls,
                    fromVigiaCreate: true,
                    treeID: response.json()[0].id,
                    color: this.color,
                    municipio: this.municipio,
                    pictures: this.pictureUri,
                    coordenadas: this.coordenadas,
                });
            }
            else {
                this.navCtrl.push(DecisionTreeVigiaComponent, {
                    info: this.formGroup.controls,
                    fromVigiaCreate: true,
                    treeID: response.json()[0].id,
                    color: this.color,
                    municipio: this.municipio,
                    picture: '',
                    coordenadas: this.coordenadas,
                });
            }
        })
    }

    ionViewDidEnter() {
        //Si la pagina regresa con el usoArbol activo es que se debe cerrar la pagina porque ya se creo un reporte
        if (this.navParams.get('usoArbol')) {
            this.navCtrl.pop();
        }
        this.usoArbol = this.navParams.get('usoArbol') || false;
        this.otraEspecie = this.navParams.get('otraEspecie') || false;
        // this.formGroup.controls.descriptionFormCtrl.setValue(this.navParams.get('description') || "");
    }

    //Eliminar un multimedia 
    deleteMedia(item: number) {
        this.pictureUri.splice(item, 1);
        this.slides.slidePrev();
        // this.common.confirmAlert('Eliminar archivo', '¿Esta seguro de eliminar este archivo?').then((response) => {
        // })
        //     .catch((cancel) => {
        //         this.common.dissmisAlert();
        //     })
    }
}

