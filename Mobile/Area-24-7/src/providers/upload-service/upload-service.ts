import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { File } from "@ionic-native/file";
import { AlertController } from 'ionic-angular';
import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';
import { FileTransfer, FileUploadOptions, FileTransferObject, FileUploadResult } from '@ionic-native/file-transfer';

export class Upload {
  $key: string;
  base64: string;
  name: string;
  url: string;
  progress: number;
  createdAt: Date = new Date();
  constructor(base64: string) {
    this.base64 = base64;
  }
}

export class UploadVideo {
  $key: string;
  blob: Blob;
  type: string;
  name: string;
  url: string;
  progress: number;
  createdAt: Date = new Date();
  constructor(blob: Blob,
    
    ) {
    this.blob = blob;
  }
}

@Injectable()
export class UploadServiceProvider {
  private fileTransferObject: FileTransferObject;
  private basePath: string = "/uploads";
  uploadPercent: Observable<number>;
  downloadURL: Observable<string>;
  public uploads: Observable<Upload[]>;

  constructor(
    private file: File, private alertCtrl: AlertController,
    private fileTransfer: FileTransfer
  ) {
    this.fileTransferObject = fileTransfer.create();
  }


  uploadMedia(urlMedia: string): Observable<FileUploadResult>{
    let url = CONFIG_ENV.REST_BASE_URL + '/api/multimedia'

    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', localStorage.getItem('bearer'));

    let options: FileUploadOptions = {
        fileKey: 'multimedia',
        mimeType: 'multipart/form-data',
        headers: { 'Authorization': localStorage.getItem('bearer') }
    };
    return Observable.fromPromise(this.fileTransferObject.upload(urlMedia, encodeURI(url), options));
}

  uploadVideo(upload: UploadVideo) {
    let url = CONFIG_ENV.REST_BASE_URL + '/api/multimedia'

    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', localStorage.getItem('bearer'));

    let options: FileUploadOptions = {
        fileKey: 'multimedia',
        mimeType: 'multipart/form-data',
        headers: { 'Authorization': localStorage.getItem('bearer') }
    };
    return Observable.fromPromise(this.fileTransferObject.upload(upload.url, encodeURI(url), options));
  }

  makeFileIntoBlob(_imagePath, name, type) {
    return new Promise((resolve, reject) => {

      this.file
        .resolveLocalFilesystemUrl(_imagePath)
        .then((entry: any) => {
          entry.file((resFile)=>{

            var reader = new FileReader();

            reader.onloadend = (evt: any) => {
              var videoBlob: any = new Blob([evt.target.result], { type: type });
              videoBlob.name = name;
              resolve(videoBlob);
            };

            reader.onerror = (e) => {
              alert('Error cargando video: ' + JSON.stringify(e));
              reject(e);
            };

            reader.readAsArrayBuffer(resFile);
          })

        })
        .catch(error => {
          console.log(error);
        });

    });
  }
}
