import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { InAppBrowser, InAppBrowserOptions } from '@ionic-native/in-app-browser';
import { Clipboard } from '@ionic-native/clipboard';
import { NavController, NavParams, AlertController, Alert, AlertOptions } from 'ionic-angular';
import { StoryListComponent } from '../story-list/story-list';
import { VigiaProvider } from '../../providers/vigia/vigia';
import { Vigia } from '../../entities/vigia';
import { CommentComponent } from '../comment/comment';
import { Common } from '../../app/shared/utilidades/common';
import { DecisionTreeProvider } from '../../providers/decision-tree/decision-tree';
import { Subscription } from 'rxjs';

@Component({
    selector: 'cuidame-detail',
    templateUrl: 'cuidame-detail.html'
})
export class CuidameDetailComponent implements OnInit, OnDestroy {
    private commentsSubscribe: Subscription;
    private nodoSubscribe: Subscription;
    options: InAppBrowserOptions = {
        location: 'yes',//Or 'no' 
        hidden: 'no', //Or  'yes'
        clearcache: 'yes',
        clearsessioncache: 'yes',
        zoom: 'yes',//Android only ,shows browser zoom controls 
        hardwareback: 'yes',
        mediaPlaybackRequiresUserAction: 'no',
        shouldPauseOnSuspend: 'no', //Android only 
        closebuttoncaption: 'Close', //iOS only
        disallowoverscroll: 'no', //iOS only 
        toolbar: 'no', //iOS only 
        enableViewportScale: 'no', //iOS only 
        allowInlineMediaPlayback: 'no',//iOS only 
        presentationstyle: 'pagesheet',//iOS only 
        fullscreen: 'yes',//Windows only    
    };

    private vigia: any;
    private color: string;
    private loading: boolean;
    private comment: Comment;
    private closeView: boolean = true;
    private numberRadicado: string;
    private autoridad: any;

    constructor(private navCtrl: NavController
        , private navParams: NavParams
        , private alertCtrl: AlertController
        , private common: Common
        , private vigiaProvider: VigiaProvider
        , private decisionTreeProvider: DecisionTreeProvider
        , private theInAppBrowser: InAppBrowser
        , private clipboard: Clipboard) {
        this.color = navParams.get('color');

    }

    ngOnInit(): void {
        this.loading = true;
        let markerId: number = this.navParams.get('markerId');
        let fromComponent: string = this.navParams.get('from');
        //Obtenemos la informacion del reporte por medio del id del marcador
        this.vigiaProvider.getVigia(markerId, fromComponent).subscribe((vigia: Vigia) => {
            this.loading = false;
            this.vigia = vigia;
            if (this.vigia.estado == 'PENDIENTE') {
                this.color = '#808080';
            }
            // this.numberRadicado = this.vigia.radicado;
            this.commentsSubscribe = this.vigiaProvider.getVigiaComments(this.vigia.id).subscribe((comments: any) => {
                this.loading = false;
                let comentarios: any[] = [];
                comentarios = comments
                comments.sort((a, b) => a.id > b.id ? -1 : 1);
                this.comment = comments[0];
            })
            //Consultamos la informacion del nodo final que fue seleccionado a la hora de crear el reporte
            this.nodoSubscribe = this.decisionTreeProvider.getNodo(this.vigia.idNodoArbol).subscribe((nodo: any) => {
                this.vigia.rutaIcono = nodo.urIconoVigiaWin
                if(nodo.idAutoridadCompetente != null){
                    this.vigiaProvider.getAuthority(nodo.idAutoridadCompetente).subscribe((autoridad: any) => {
                        if (autoridad != null) {
                            this.autoridad = { nombre: autoridad.nombre, municipio: autoridad.municipio }
                        }
                    })
                }
                this.decisionTreeProvider.getNodo(nodo.idPadre).subscribe((padre: any) => {
                    this.vigia.titulo = padre.descripcion;
                    this.vigia.subtitulo = padre.nombre;
                })
            })
        })


    }

    ngOnDestroy() {
        this.commentsSubscribe.unsubscribe();
        this.nodoSubscribe.unsubscribe();
    }

    viewReport() {
        //Funcion que abre el naveador predeterminado en el movil para poder visualizar la web donde se consultara el estado del reporte
        let target = "_self";
        this.theInAppBrowser.create('http://webservices.metropol.gov.co/pqrsd/WebForms/VerPQRD.aspx', target, this.options);
    }

    //Se abre una alerta con las isntrucciones para consultar estado del reporte
    showState() {
        let radicado: number = 123456;
        let alertOptions: AlertOptions = {
            message:
                `Tu número de radicado es #${radicado} y se ha copiado en el portapapeles, selecciona IR para consultar el estado de tu reporte.`,
            cssClass: 'success sGenRep',
            buttons: [
                {
                    text: 'Cancelar',
                    handler: (value: any): void => {
                    }
                },
                {
                    text: 'Ir',
                    handler: (value: any): void => {
                        this.common.appToast({ mensaje: 'EL número de radicado se ha copiado en el portapapeles', duration: 2000, posicion: 'bottom' })
                        //Se copia en portapapeles el numero de radicado del reporte
                        this.clipboard.copy(radicado.toString());
                        setTimeout(() => {
                            this.viewReport();
                        }, 1500);
                    }
                }
            ]
        };
        let alert = this.alertCtrl.create(alertOptions);
        alert.present();
    }

    viewStoriesOrComments(): void {
        if (this.vigia.hasStories) {
            let params = {
                vigia: this.vigia,
                color: this.color
            };
            this.navCtrl.push(StoryListComponent, params);
        }
        else {
            let params = {
                vigiaId: this.vigia.id,
                color: this.color
            };
            this.navCtrl.push(CommentComponent, params);
        }
    }

    getMessageForSharing(): string {
        let message: string = '';
        if (this.vigia.description) message += 'Descripción: ' + this.vigia.description + '. ';
        message += 'Descripción: ' + this.vigia.description + '.';
        return message;
    }



    parseJPGvigiaUrlMedia(url: string): string {
        if (url.substring(url.length - 4, url.length).toLocaleLowerCase() === '.jpg') {
            url = url.substring(0, url.length - 4);
        }
        return url;
    }

    closeModal() {
        this.common.dismissModal();
    }
    newComment() {
        let params = {
            id: this.vigia.id,
            color: this.color,
            desde: 'Vigia'
        };
        this.navCtrl.push(CommentComponent, params);
    }


    ionViewDidEnter() {
        if (this.navParams.get('comment') == 'comment') {
            this.closeView = true;
        }
    }
}


