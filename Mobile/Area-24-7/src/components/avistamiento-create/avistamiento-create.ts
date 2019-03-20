import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Avistamiento } from '../../entities/avistamiento';
import {
    NavController,
    NavParams,
    Alert,
    AlertController,
    AlertOptions,
    ActionSheetController,
    normalizeURL
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
import { MessageProvider } from '../../providers/message/message';

@Component({
    selector: 'avistamiento-create',
    templateUrl: 'avistamiento-create.html'
})
export class AvistamientoCreateComponent implements OnInit {
    private formGroup: FormGroup;
    private color: string;
    private avistamiento: Avistamiento;
    private pictureUri: string;
    private geoposition: Geoposition;
    private pictureSource: PictureSourceType;
    private avistamientoLayers: {
        id: number;
        name: string;
    }[];

    private usoArbol: boolean = false;
    private otraEspecie: boolean = false;

    constructor(
        private navCtrl: NavController,
        private navParams: NavParams,
        private formBuilder: FormBuilder,
        private camera: Camera,
        private avistamientoProvider: AvistamientoProvider,
        private locationChange: LocationChangeProvider,
        private common: Common,
        private alertCtrl: AlertController,
        private actionSheetCtrl: ActionSheetController,
        private decisionTreeProvider: DecisionTreeProvider
        , private messageProvider: MessageProvider
    ) {

        this.color = navParams.get('color');

        this.formGroup = formBuilder.group({
            locationFormCtrl: [
                '',
                Validators.compose([Validators.required])
            ],
            commonNameFormCtrl: [
                '',
                Validators.compose([Validators.maxLength(100)])
            ],
            scientificNameFormCtrl: [
                '',
                Validators.compose([Validators.maxLength(100)])
            ],
            descriptionFormCtrl: [
                '',
                Validators.compose([Validators.maxLength(400)])
            ],
            avistamientoTypeFormCtrl: [
                '',
                Validators.compose([Validators.required])
            ]
        });
    }

    ngOnInit(): void {
        this.geoposition;
        this.presentActionSheet();

        this.avistamientoProvider.getAvistamientoLayers().subscribe(
            (avistamientoLayers: { id: number; name: string }[]): void => {
                console.log(AvistamientoCreateComponent.name + ' getAvistamientoLayers ' + JSON.stringify(avistamientoLayers));

                this.avistamientoLayers = avistamientoLayers;
                if (this.avistamientoLayers.length === 1) {
                    this.formGroup.controls.avistamientoTypeFormCtrl.setValue(this.avistamientoLayers[0]);
                }
            },
            (error: any): void => console.log(AvistamientoCreateComponent.name + ' getAvistamientoLayers error ' + JSON.stringify(error))
        );
    }

    presentActionSheet(): void {
        let actionSheet = this.actionSheetCtrl.create({
            title: 'Elija el origen de la imagen',
            buttons: [
                {
                    text: 'Seleccionar de la galería',
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
                }
                // {
                //     text: 'Cancelar',
                //     role: 'cancel'
                // }
            ]
        });
        actionSheet.present();
    }

    takePictureAndLocation(): void {
        const options: CameraOptions = {
            sourceType: this.pictureSource,
            cameraDirection: this.camera.Direction.BACK,
            correctOrientation: true,
            quality: 50,
            destinationType: this.camera.DestinationType.FILE_URI,
            encodingType: this.camera.EncodingType.JPEG,
            mediaType: this.camera.MediaType.PICTURE
        };
        this.camera
            .getPicture(options)
            .then(
                (pictureUri: string): void => {
                    //alert('raw url ' + JSON.stringify(pictureUri));

                    this.pictureUri = normalizeURL(pictureUri);

                    //alert('normalized url ' + JSON.stringify(this.pictureUri));
              
                    if (this.pictureSource == PictureSourceType.CAMERA) {
                        this.geoposition = this.locationChange.getGeoposition();
                        let locationStr: string = this.geoposition.coords.latitude + ', ' + this.geoposition.coords.longitude;
                        this.formGroup.controls.locationFormCtrl.setValue(locationStr);
                    }
                }
            )
            .catch(
                (reason: any): any =>
                    console.log(
                        AvistamientoCreateComponent.name +
                        ' takePicture error ' +
                        JSON.stringify(reason)
                    )
            );
    }

    selectLocationFromMap(): void {
        if (this.pictureSource == PictureSourceType.PHOTOLIBRARY) {
            let mapSelectLocationModal = this.common.createModal(MapSelectLocationComponent, {desde:'Avistamiento'}, { cssClass: 'mapaUbicacion' });
            mapSelectLocationModal.onDidDismiss((geoposition: Geoposition, role: string): void => {
                if (role == 'OK') {
                    this.geoposition = geoposition;
                    let locationStr: string = this.geoposition.coords.latitude + ', ' + this.geoposition.coords.longitude;
                    this.formGroup.controls.locationFormCtrl.setValue(locationStr);
                }
            });
            mapSelectLocationModal.present();
        }
    }

    saveAvistamiento(): void {
        if (!this.formGroup.valid || !this.pictureUri || !this.geoposition) return;

        this.common.presentLoading();
        this.avistamiento = new Avistamiento(null);
        this.avistamiento.commonName = this.formGroup.controls.commonNameFormCtrl.value;
        this.avistamiento.scientificName = this.formGroup.controls.scientificNameFormCtrl.value;
        this.avistamiento.description = this.formGroup.controls.descriptionFormCtrl.value;
        this.avistamiento.urlMedia = this.pictureUri;
        this.avistamiento.lat = this.geoposition.coords.latitude;
        this.avistamiento.lng = this.geoposition.coords.longitude;
        this.avistamiento.layerId = this.formGroup.controls.avistamientoTypeFormCtrl.value.id;

        this.avistamientoProvider.postAvistamiento(this.avistamiento).subscribe(
            (result: FileUploadResult): void => {
                console.log(AvistamientoCreateComponent.name + ' saveAvistamiento ' + JSON.stringify(result));
                this.common.dismissLoading();
                if(this.usoArbol) {
                    if (this.otraEspecie) {
                        this.messageProvider.getByNombreIdentificador('creacion avistamiento uso arbol otra especie').subscribe(
                            (response) => {
                                console.log(AvistamientoCreateComponent.name + ' saveAvistamiento getByNombreIdentificador ' + JSON.stringify(response));
                                let msg = response.json();
                                let alert = this.alertCtrl.create({
                                    title: msg.titulo,
                                    cssClass: 'warning',
                                    message: msg.descripcion,
                                    buttons: [
                                        {
                                            text: 'Aceptar',
                                            handler: () => {
                                                this.navCtrl.pop();
                                            }
                                        }
                                    ]
                                });
                                alert.present();
                            },
                            error => console.log(AvistamientoCreateComponent.name + ' saveAvistamiento getByNombreIdentificador error ' + JSON.stringify(error))
                        );
                    } 
                    else {
                        this.messageProvider.getByNombreIdentificador('creacion avistamiento uso arbol').subscribe(
                            (response) => {
                                console.log(AvistamientoCreateComponent.name + ' saveAvistamiento getByNombreIdentificador ' + JSON.stringify(response));
                                let msg = response.json();
                                let alert = this.alertCtrl.create({
                                    title: msg.titulo,
                                    cssClass: 'success',
                                    message: msg.descripcion,
                                    buttons: [
                                        {
                                            text: 'Regresar a la app',
                                            handler: () => {
                                                this.navCtrl.pop();
                                            }
                                        }
                                    ]
                                });
                                alert.present();
                            }, error => console.log(AvistamientoCreateComponent.name + ' saveAvistamiento getByNombreIdentificador error ' + JSON.stringify(error)));
                        }
                    if (this.navCtrl.canGoBack()) this.navCtrl.pop();
                }
                else {
                    this.messageProvider.getByNombreIdentificador('creacion avistamiento no uso arbol').subscribe(
                        (response) => {
                            console.log(AvistamientoCreateComponent.name + ' saveAvistamiento getByNombreIdentificador ' + JSON.stringify(response));
                            let msg = response.json();
                            let alertOptions: AlertOptions = {
                                title: msg.titulo,
                                message: msg.descripcion,
                                cssClass: 'success',
                                buttons: [
                                    {
                                        text: 'Finalizar',
                                        handler: (value: any): void => {
                                            if (this.navCtrl.canGoBack())
                                                this.navCtrl.pop();
                                        }
                                    }
                                ]
                            };
                            let alert = this.alertCtrl.create(alertOptions);
                            alert.present();
                        }, error => console.log(AvistamientoCreateComponent.name + ' saveAvistamiento getByNombreIdentificador error ' + JSON.stringify(error)));
                }
            },
            (error: any): void => {
                console.log(AvistamientoCreateComponent.name + ' saveAvistamiento error ' + JSON.stringify(error));
            }
        );
    }

    identificarAvistamiento(): void {
        this.decisionTreeProvider.getDecisionTreeID(this.formGroup.controls.avistamientoTypeFormCtrl.value.id)
            .subscribe(treeID => {
                this.navCtrl.push(DecisionTreeComponent, {
                    fromAvistamientoCreate: true,
                    treeID: treeID,
                    color: this.color
                });
            });
    }

    ionViewDidEnter() {        
        this.formGroup.controls.commonNameFormCtrl.setValue(this.navParams.get('name') || "");
        this.formGroup.controls.scientificNameFormCtrl.setValue(this.navParams.get('binomialNomenclature') || "");
        this.formGroup.controls.descriptionFormCtrl.setValue(this.navParams.get('description') || "");

        this.usoArbol = this.navParams.get('usoArbol') || false;
        this.otraEspecie = this.navParams.get('otraEspecie') || false;
        

        if (this.usoArbol) {
            this.saveAvistamiento();
        }

    }
}
