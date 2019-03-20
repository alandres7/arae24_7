import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IonicModule, IonicPageModule } from 'ionic-angular';
import { ModalAvistamientosPage } from './components/modal/modal-avistamientos-page';
import {AvistamientosPage} from './pages/avistamientos-page/avistamientos-page';
import { MediaCapture} from '@ionic-native/media-capture';
import { WsAvistamiento } from './provider/wsAvistamiento';
import { FileTransfer, FileTransferObject } from "@ionic-native/file-transfer";
import { DomSanitizer } from "@angular/platform-browser/platform-browser";
// import { ViewAvistamientoPage } from '../../pages/view-avistamiento/view-avistamiento';
// import { StoriesPage } from '../../pages/stories/stories';
// import { DetailStoryPage } from '../../pages/detail-story/detail-story';
// import { CreateStoryPage } from '../../pages/create-story/create-story';
// import { CommentsPage } from '../../pages/comments/comments';
// import { CreateCommentPage } from '../../pages/create-comment/create-comment';



@NgModule({
    declarations: [
        ModalAvistamientosPage,
        AvistamientosPage,
        // ViewAvistamientoPage,
     
        // StoriesPage,
        // DetailStoryPage,
        // CreateStoryPage,
        // CommentsPage,
        // CreateCommentPage
    ],
    imports: [IonicPageModule.forChild(ModalAvistamientosPage),],
    entryComponents: [
        AvistamientosPage,
        // ViewAvistamientoPage,
    
        // StoriesPage,
        // DetailStoryPage,
        // CreateStoryPage,
        // CommentsPage,
        // CreateCommentPage,
        ],
    providers: [MediaCapture,WsAvistamiento, FileTransfer, FileTransferObject ],
    exports: [IonicModule,AvistamientosPage,],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA, ]
})
export class AvistamientosModule {
  
}