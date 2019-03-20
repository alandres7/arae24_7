import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Response } from '@angular/http';

import { NavController, NavParams, Navbar } from 'ionic-angular';

import { Comment } from '../../entities/comment';

import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';
import { Common } from '../../app/shared/utilidades/common';
import { PublicationState } from '../../enums/publication-state';
import { VigiaProvider } from '../../providers/vigia/vigia';
import { Vigia } from '../../entities/vigia';

@Component({
    selector: 'comment',
    templateUrl: 'comment.html'
})
export class CommentComponent implements OnInit {
    @ViewChild('navbar') navBar: Navbar;
    private PublicationState = PublicationState;
    private state: string;
    private comments: Comment[];
    private color: string;
    private formGroup: FormGroup;
    private isAvistamientoComment: boolean;
    private loading: boolean;
    private fromComponent: string

    constructor(private avistamientoProvider: AvistamientoProvider
        , private navCtrl: NavController
        , private navParams: NavParams
        , private formBuilder: FormBuilder
        , private common: Common
        , private vigiaProvider: VigiaProvider) {
        this.formGroup = formBuilder.group({
            contentFormCtrl: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(255)])]
        });
    }

    ngOnInit(): void {
        this.loading = true;
        this.color = this.navParams.get('color');
        this.fromComponent = this.navParams.get('desde');
        let id: number = this.navParams.get('id');
        if (id) {
            if (this.fromComponent == 'Avistamiento') {
                this.avistamientoProvider.getAvistamientoComments(id).subscribe(
                    (comments: Comment[]): void => {
                        this.comments = comments;
                        this.loading = false;
                    }
                );
            }
            else {
                if (this.fromComponent == 'Vigia') {
                    this.vigiaProvider.getVigiaComments(id).subscribe(
                        (comments: Comment[]): void => {
                            this.comments = comments;
                            this.comments.sort((a, b) => b.creationDate > a.creationDate ? -1 : 1);
                            this.loading = false;
                            this.vigiaProvider.getVigia(id, 'select').subscribe((response: any) => {
                                this.state = response.publicationState;
                                this.comments.forEach(comment => {
                                    if (response.estado == 'PENDIENTE') {
                                        comment.publicationState = PublicationState.Pending
                                    }
                                    else {
                                        comment.publicationState = PublicationState.Published;
                                    }
                                });
                            })
                        }
                    );
                }
            }
        }
        else {
            id = this.navParams.get('storyId');
            this.avistamientoProvider.getStoryComments(id).subscribe(
                (comments: Comment[]): void => {
                    this.comments = comments;
                    this.loading = false;

                }
            );
        }
    }

    ionViewDidEnter() {
        this.navBar.backButtonClick = () => {
            this.navCtrl.getPrevious().data.comment = 'comment';
            this.navCtrl.pop();
        };
    }


    saveComment(): void {
        // debugger;
        if (!this.formGroup.valid) return;
        this.common.presentLoading();
        let comment: Comment = new Comment(null);
        comment.content = this.formGroup.controls.contentFormCtrl.value;
        let id: number = this.navParams.get('id');
        if (id) {
            if (this.fromComponent == 'Avistamiento') {
                this.avistamientoProvider.postAvistamientoComment(comment, id).subscribe(
                    (response: Response): void => {
                        console.log(CommentComponent.name + ' postAvistamientoComment ' + JSON.stringify(response));
                        this.ngOnInit();
                        this.formGroup.reset();
                        this.common.dismissLoading();
                    },
                    (error: any): void => {
                        console.log(CommentComponent.name + ' postAvistamientoComment error ' + JSON.stringify(error));
                        this.common.dismissLoading();
                    }
                );
            }
            else {
                if (this.fromComponent == 'Vigia') {
                    this.vigiaProvider.postVigiaComment(comment, id).subscribe(
                        (response: Response): void => {
                            console.log(CommentComponent.name + ' postVigiaComment ' + JSON.stringify(response));
                            this.ngOnInit();
                            this.formGroup.reset();
                            this.common.dismissLoading();
                        },
                        (error: any): void => {
                            console.log(CommentComponent.name + ' postVigiaComment error ' + JSON.stringify(error));
                            this.common.dismissLoading();
                        }
                    );
                }
            }
        }
        else {
            id = this.navParams.get('storyId');
            if (this.fromComponent == 'Avistamiento') {
                this.avistamientoProvider.postStoryComment(comment, id).subscribe(
                    (response: Response): void => {
                        console.log(CommentComponent.name + ' postStoryComment ' + JSON.stringify(response));
                        this.ngOnInit();
                        this.formGroup.reset();
                        this.common.dismissLoading();
                    },
                    (error: any): any => {
                        console.log(CommentComponent.name + ' postStoryComment error ' + JSON.stringify(error));
                        this.common.dismissLoading();
                    }
                );
            }
            else {
                if (this.fromComponent == 'Vigia') {


                }
            }
        }
    }
}
