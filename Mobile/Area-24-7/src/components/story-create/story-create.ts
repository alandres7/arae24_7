import { Component, ViewChild } from '@angular/core';
import { Response } from '@angular/http';
import { Navbar, NavController, NavParams, LoadingController } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Story } from '../../entities/story';
import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';
import { Common } from '../../app/shared/utilidades/common';

@Component({
  selector: 'story-create',
  templateUrl: 'story-create.html'
})
export class StoryCreateComponent {

    private formGroup: FormGroup;
    private color: string;   

    constructor(private formBuilder: FormBuilder
              , private navCtrl: NavController
              , private navParams: NavParams
              , private avistamientoProvider: AvistamientoProvider
              , private common: Common) 
    {
        this.formGroup = formBuilder.group({
            titleFormCtrl: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(100) ])],
            contentFormCtrl: ['', Validators.compose([ Validators.required, Validators.minLength(3), Validators.maxLength(4000) ])]
        });
        this.color = navParams.get('color');
    }

    saveStory(): void {
        if (!this.formGroup.valid) return;

        this.common.presentLoading();
        let avistamientoId: number = this.navParams.get('avistamientoId');
        let story: Story = new Story(null);
        story.title = this.formGroup.controls.titleFormCtrl.value;
        story.content = this.formGroup.controls.contentFormCtrl.value;
        this.avistamientoProvider.postStory(story, avistamientoId).subscribe(
            (response: Response): void => {
                console.log(StoryCreateComponent.name + ' postStory ' + JSON.stringify(response));
                this.common.dismissLoading();
                let msg = response.json();
                this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
                this.navCtrl.pop();
            },
            (error: any): void => {
                console.log(StoryCreateComponent.name + ' postStory error ' + JSON.stringify(error));
            }
        );
    }
}
