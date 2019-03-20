import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';

import { NavController, NavParams } from 'ionic-angular';

import { BusquedaAvistamientosComponent } from "../busqueda-avistamientos/busqueda-avistamientos";
import { StoryListComponent } from '../story-list/story-list';

import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';

import { Avistamiento } from '../../entities/avistamiento';
import { CommentComponent } from '../comment/comment';
import { PublicationState } from '../../enums/publication-state';
import { Common } from '../../app/shared/utilidades/common';

@Component({
    selector: 'avistamiento-detail',
    templateUrl: 'avistamiento-detail.html',
})
export class AvistamientoDetailComponent implements OnInit {
   
    private PublicationState = PublicationState;
    
    private avistamiento: Avistamiento = new Avistamiento(null);
    private color: string;
    private loading: boolean;
    
    constructor(private navCtrl: NavController
              , private navParams: NavParams
              , private avistamientoProvider: AvistamientoProvider
              , private common: Common)
    {
        this.color = navParams.get('color');
    }

    ngOnInit(): void {
        this.loading = true;
        let markerId: number = this.navParams.get('markerId');
        this.avistamientoProvider.getAvistamiento(markerId).subscribe(
            (avistamiento: Avistamiento): void => {
                this.avistamiento = avistamiento;
                this.loading = false;
            });
    }

    viewStoriesOrComments(): void {
        if (this.avistamiento.hasStories) {
            let params = {
                avistamiento: this.avistamiento,
                color: this.color
            };
            this.navCtrl.push(StoryListComponent, params);
        } 
        else {
            let params = {
                id: this.avistamiento.id,
                color: this.color,
                desde: 'Avistamiento'
            };
            this.navCtrl.push(CommentComponent, params);
        }
    }

    getMessageForSharing(): string {
        let message: string = '';
        if (this.avistamiento.commonName) message += 'Nombre común: ' + this.avistamiento.commonName + '. ';
        if (this.avistamiento.scientificName) message += 'Nombre científico: ' + this.avistamiento.scientificName + '. ';
        message += 'Descripción: ' + this.avistamiento.description + '.';
        return message;
    }

    shareViaTwitter(): void {
        this.avistamientoProvider.shareViaTwitter(this.getMessageForSharing(), this.parseJPGAvistamientoUrlMedia(this.avistamiento.urlMedia));
    }

    shareViaFacebook(): void {
        // debugger;
        this.avistamientoProvider.shareViaFacebook(this.getMessageForSharing(), this.parseJPGAvistamientoUrlMedia(this.avistamiento.urlMedia));
    }

    shareViaInstagram(): void {
        this.avistamientoProvider.shareViaInstagram(this.getMessageForSharing(), this.avistamiento.urlMedia);
    }

    parseJPGAvistamientoUrlMedia (url: string): string {
        if (url.substring(url.length-4, url.length).toLocaleLowerCase() === '.jpg') {
            url = url.substring(0, url.length-4);
        }
        return url;
    }

    //TODO acomodar al crear historia
  /*  viewArbol(){
        const modal = this.modalCtrl.create(DecisionTreeComponent);
        modal.present();
    } */
    viewBusqueda(){
        const modal = this.common.createModal(BusquedaAvistamientosComponent);
        modal.present();
    }
}
