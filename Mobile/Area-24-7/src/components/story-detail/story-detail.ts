import { Component } from '@angular/core';

import { NavController, NavParams } from 'ionic-angular';

import { Story } from '../../entities/story';

import { CommentComponent } from '../comment/comment';
import { Avistamiento } from '../../entities/avistamiento';
import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';
import { PublicationState } from '../../enums/publication-state';

@Component({
    selector: 'story-detail',
    templateUrl: 'story-detail.html'
})
export class StoryDetailComponent {

    private PublicationState = PublicationState; 
    
    private avistamiento: Avistamiento;
    private story: Story;
    private color: string;

    constructor(
        private navCtrl: NavController,
        private navParams: NavParams,
        private avistamientoProvider: AvistamientoProvider
    ) {
        this.color = navParams.get('color');
        this.avistamiento = navParams.get('avistamiento');
        this.story = navParams.get('story');
    }

    viewComments(): void {
        this.navCtrl.push(CommentComponent, {
            storyId: this.story.id,
            color: this.color,
            desde: 'Avistamiento'
        });
    }

    getMessageForSharing(): string {
        let message: string = '';
        if (this.avistamiento.commonName) message += 'Nombre común: ' + this.avistamiento.commonName + '. ';
        if (this.avistamiento.scientificName) message += 'Nombre científico: ' + this.avistamiento.scientificName + '. ';
        message += 'Descripción: ' + this.avistamiento.description + '.';
        message += 'HISTORIA: Título: ' + this.story.title + '. Contenido: ' + this.story.content;
        return message;
    }

    shareViaTwitter(): void {
        this.avistamientoProvider.shareViaTwitter(
        this.getMessageForSharing(),
        this.parseJPGAvistamientoUrlMedia(this.avistamiento.urlMedia)
        );
    }

    shareViaFacebook(): void {
        this.avistamientoProvider.shareViaFacebook(
        this.getMessageForSharing(),
        this.parseJPGAvistamientoUrlMedia(this.avistamiento.urlMedia)
        );
    }

    shareViaInstagram(): void {
        this.avistamientoProvider.shareViaInstagram(
        this.getMessageForSharing(),
        this.avistamiento.urlMedia
        );
    }

    parseJPGAvistamientoUrlMedia (url: string): string {
        if (url.substring(url.length-4, url.length).toLocaleLowerCase() === '.jpg') {
            url = url.substring(0, url.length-4);
        }
        return url;
    }
}
