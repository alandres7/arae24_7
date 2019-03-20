import { Component, OnInit } from '@angular/core';
import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';
import { NavController, NavParams } from 'ionic-angular';
import { Story } from '../../entities/story';
import { StoryDetailComponent } from '../story-detail/story-detail';
import { StoryCreateComponent } from '../story-create/story-create';
import { Avistamiento } from '../../entities/avistamiento';

import { PublicationState } from '../../enums/publication-state';

@Component({
  selector: 'story-list',
  templateUrl: 'story-list.html'
})
export class StoryListComponent implements OnInit {

    private PublicationState = PublicationState;

    private stories: Story[];
    private avistamiento: Avistamiento;
    private color: string;
    private loading: boolean;

    constructor(private avistamientoProvider: AvistamientoProvider
              , private navCtrl: NavController
              , private navParams: NavParams) 
    {
        this.avistamiento = navParams.get('avistamiento');
        this.color = navParams.get('color');
    }

    ngOnInit(): void {
        this.loadStories();
    }

    ionViewWillEnter(): void {
        this.loadStories();
    }

    loadStories(): void {
        this.loading = true;
        this.avistamientoProvider.getStories(this.avistamiento.id).subscribe(
            (stories: Story[]): void => {
                this.stories = stories;
                this.loading = false;
            }
        );
    }

    viewStory(story: Story): void {
        this.navCtrl.push(StoryDetailComponent, {
            avistamiento: this.avistamiento,
            story: story,
            color: this.color
        });
    }

    createStory(): void {
        this.navCtrl.push(StoryCreateComponent, {
            avistamientoId: this.avistamiento.id,
            color: this.color,
        });
    }
}
