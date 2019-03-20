import { Injectable, Injector } from '@angular/core';
import { Network } from '@ionic-native/network';
import { AlertController, Alert, AlertOptions, Loading, LoadingController, ToastController, Platform, Events, ModalController, ModalOptions, Modal, NavOptions } from 'ionic-angular';
import { NativeGeocoder, NativeGeocoderReverseResult, NativeGeocoderForwardResult } from "@ionic-native/native-geocoder";
import { Geolocation } from '@ionic-native/geolocation';

@Injectable()
export class Common {

  private static injector: Injector;

  static setInjector(injector: Injector) { Common.injector = injector; }

  static getInjector(): Injector { return Common.injector; }

  private loading: Loading;
  private alert: Alert;
  private modalStack: Modal[] = new Array<Modal>();

  loader: any;
  static alert: any;
  //urlServidor:string = "http://201.184.243.195:8095"; // publica
  //urlServidor: string = "http://172.16.0.20:8095";
  urlServidor: string = "http://172.16.0.20:9095";

  submenu = {
    sHuellas: false,
    sAvistamientos: false,
    sEntorno: false,
    sOrdenamiento: false,
    sVigias: false,
    sMovilidad: false,
    sConcursoFotografia: false
  };

  /*
    menuPreferencias = {
      Huellas: false,
      Avistamientos: false,
      Entorno: false,
      Ordenamiento: false,
      Vigias: false,
      Movilidad: false
    };
  */
 capas = []
  activeLayers = {
    ids: [],
    level: ''
  }

  constructor(
    private modalCtrl: ModalController
    , private loadingCtrl: LoadingController,
    private geolocation: Geolocation
    , private toastCtrl: ToastController
    , public network: Network
    , public alertCtrl: AlertController
    , public platform: Platform
    , private events: Events
    , private nativeGeocoder: NativeGeocoder) {
    this.alertCtrl.create();
    Common.alert = this.alertCtrl;
  }

  createModal(component: any, data?: any, opts?: ModalOptions): Modal {
    let modal: Modal = this.modalCtrl.create(component, data, opts);
    this.modalStack.push(modal);
    return modal;
  }

  dismissModal(data?: any, role?: string, navOptions?: NavOptions): Promise<any> {
    if (this.canDismissModal()) {
      let modal: Modal = this.modalStack.pop();
      return modal.dismiss(data, role, navOptions);
    }
    return null;
  }

  canDismissModal(): boolean {
    console.log('modalStack ' + this.modalStack.length);
    return this.modalStack.length > 0;
  }

  presentAcceptAlert(title: string, message: string, onAccept?: () => void): void {
    if (this.alert) return;

    let options: AlertOptions = {
      title: title,
      message: message,
      buttons: [
        {
          text: 'Aceptar',
          role: 'cancel',
          handler: onAccept
        }
      ]
    };
    this.alert = this.alertCtrl.create(options);
    this.alert.onDidDismiss((data: any, role: string) => {
      this.alert = null;
    });
    this.alert.present();
  }

  dissmisAlert() {
    if (this.alert) this.alert.dismiss();
  }

  presentAcceptCancelAlert(title: string, message: string, onAccept: () => void) {
    if (this.alert) return;
    let options: AlertOptions = {
      title: title,
      message: message,
      buttons: [
        {
          text: 'Aceptar',
          handler: onAccept
        },
        {
          text: 'Regresar'
        }
      ]
    };
    this.alert = this.alertCtrl.create(options);
    this.alert.onDidDismiss((data: any, role: string) => {
      this.alert = null;
    });
    this.alert.present();
  }

  presentAlert(alertOptions: AlertOptions): void {
    if (this.alert) return;
    this.alert = this.alertCtrl.create(alertOptions);
    this.alert.onDidDismiss((data: any, role: string) => {
      this.alert = null;
    });
    this.alert.present();
  }

  presentLoading(): void {
    if (this.loading) return;

    this.loading = this.loadingCtrl.create({
      content: "Un momento por favor..."
    });
    this.loading.present();
  }

  presentFullLoading(): void {
    if (this.loading) return;

    this.loading = this.loadingCtrl.create({
      spinner: 'hide',
      content: '<img src="assets/logo3.png"/>',
      cssClass: 'custom-loading',
    });
    this.loading.present();
  }

  dismissLoading(): void {
    if (this.loading) {
      this.loading.dismissAll();
      this.loading = null;
    }
  }

  appLoading(mensajeLoading, duration: any) {
    let loading = this.loadingCtrl.create({
      spinner: 'hide',
      content: `
      <div class="custom-spinner-container">
      <ion-spinner name="bubbles"></ion-spinner>
        <!-- <div class="custom-spinner-box"></div>-->
        <h5>`+ mensajeLoading + `</h5>
      </div>`,
      duration: duration || 5000
    });

    loading.onDidDismiss(() => {
      console.log('Dismissed loading');
    });

    loading.present();
  }

  appToast(objetoToast: any) {
    let toast = this.toastCtrl.create({
      message: objetoToast.mensaje,
      duration: objetoToast.duration,
      position: objetoToast.posicion
    });

    toast.onDidDismiss(() => {
      console.log('Dismissed toast');
    });

    toast.present();
  }

  menuApp(opocion, valor) {
    this.submenu[opocion] = valor;
  }

  /*
  checKNetwork() {
    let disconnectSubscription = this.network.onDisconnect().subscribe(() => {
      let alert = this.alertCtrl.create({
        title: 'Ups!',
        subTitle: 'El dispositivo no tiene conexión a internet!',
        buttons: [
          {
            text: 'Salir',
            handler: () => {
              this.platform.exitApp();

            }
          }
        ]
      });
      alert.present();
      console.log('Sin conexion a internet');
    });



    // stop disconnect watch
    //disconnectSubscription.unsubscribe();


    // watch network for a connection
    let connectSubscription = this.network.onConnect().subscribe(() => {
      console.log('network connected!');
      // We just got a connection but we need to wait briefly
      // before we determine the connection type. Might need to wait.
      // prior to doing any api requests as well.
      setTimeout(() => {
        if (this.network.type === 'wifi') {
          console.log('we got a wifi connection, woohoo!');
        }
      }, 3000);
    });

    // stop connect watch
    //connectSubscription.unsubscribe();
  }
*/
  generalAlert(pTitle, pSubtitle) {
    let alert = this.alertCtrl.create({
      title: '<ion-toolbar color="primary">' + pTitle + '</ion-toolbar>',
      subTitle: pSubtitle,
      buttons: [
        {
          text: 'Aceptar',
          handler: () => {
            //this.platform.exitApp();

          }
        }
      ],
      cssClass: '{background-color: white; color: red; button{ color: red;}}'
    });

    alert.present();
  }

  /*cerrarSesion() {
  localStorage.removeItem('usuario');
  localStorage.removeItem('usuarioWeb');
  this.navCtrl.popTo(LoginPage);
}*/

  basicAlert(titulo: string, mensaje: string, ) {
    return new Promise((resolve, reject) => {
      let alert = Common.alert.create({
        title: titulo,
        enableBackdropDismiss: false,
        subTitle: mensaje,
        buttons: [
          {
            text: 'Aceptar',
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      alert.present();
    });
  }

  confirmAlert(titulo: string, mensaje: string, ) {
    return new Promise((resolve, reject) => {
      let alert = Common.alert.create({
        title: titulo,
        enableBackdropDismiss: false,
        subTitle: mensaje,
        buttons: [
          {
            text: 'Cancelar',
            handler: () => {
              reject(false);
            }
          },
          {
            text: 'Aceptar',
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      alert.present();
    });
  }

  confirmAlertCss(titulo: string, mensaje: string, cssClass: string) {
    return new Promise((resolve, reject) => {
      let alert = Common.alert.create({
        title: titulo,
        enableBackdropDismiss: false,
        subTitle: mensaje,
        cssClass: cssClass,
        buttons: [
          {
            text: 'Regresar',
            handler: () => {
              reject(false);
            }
          },
          {
            text: 'Aceptar',
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      alert.present();
    });
  }

  basicAlePrtCss(titulo: string, mensaje: string, css: string, boton: string) {
    return new Promise((resolve, reject) => {
      let alert = Common.alert.create({
        title: titulo,
        enableBackdropDismiss: false,
        cssClass: css,
        subTitle: mensaje,
        buttons: [
          {
            text: boton,
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      alert.present();
    });
  }



  convertTime24to12(time24: string): string {
    let tmpArr = time24.split(':'), time12;
    if (+tmpArr[0] == 12) {
      time12 = tmpArr[0] + ':' + tmpArr[1] + ' pm';
    } else {
      if (tmpArr[0] == '00') {
        time12 = '12:' + tmpArr[1] + ' am';
      } else {
        if (+tmpArr[0] > 12) {
          time12 = (+tmpArr[0] - 12) + ':' + tmpArr[1] + ' pm';
        } else {
          time12 = (+tmpArr[0]) + ':' + tmpArr[1] + ' am';
        }
      }
    }
    return time12;
  }

  obtenerUsuarioActivo(): any {
    return JSON.parse(localStorage.getItem('usuario'));
  }

  convertColorArrayToRgb(arrayColor: any) {

    let element = { 'r': Number, 'g': Number, 'b': Number };
    let rgbArray: Array<any> = JSON.parse(arrayColor); //arrayColor.replace('"', ' '); //arrayColor[0].split(",");
    element.r = rgbArray[0];
    element.g = rgbArray[2];
    element.b = rgbArray[1];
    //rgbArray[3];
    return this.convertColorRgbToHex(element);
  }

  convertColorRgbToHex(objRGB: any) {
    return "#" + this.componentToHex(objRGB.r) + this.componentToHex(objRGB.g) + this.componentToHex(objRGB.b);
  }

  componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
  }

  getOpacity(arrayColor: Array<any>): Number {
    let rgbArray: Array<any> = arrayColor[0].split(",");
    return (rgbArray[3] / 255);
  }


  httpLoading(mensajeLoading) {
    return this.loadingCtrl.create({
      //spinner: 'bubbles',
      content: `<span>` + mensajeLoading + `</span>`
    });
  }

  crearCoordendas(cooredenadasResponse: any[]): any {
    let coordenadasRuta = [];
    for (var index = 0; index < cooredenadasResponse.length; index++ , index++) {
      let lat = +cooredenadasResponse[index];
      let lng = +cooredenadasResponse[index + 1];
      coordenadasRuta.push({ latitud: lat, longitud: lng })
    }
    return coordenadasRuta;
  }

  obtenerModosTransportesActivos(preferenciasTransporte: any[]) {
    let modosTransportes = [];

    for (let index = 0; index < preferenciasTransporte.length; index++) {
      if (preferenciasTransporte[index].active) {
        let res = this.obtenerModoTransporteIdService(preferenciasTransporte[index]);
        if (res != null) {
          modosTransportes.push(res);
        }
      }

    }
    if (modosTransportes.length > 0) {
      return "9," + modosTransportes.toString();
    } else {
      return "9";
    }

  }

  /*
    obtenerModosTransportes() {
        let preferenciasTransporte = JSON.parse(localStorage.getItem("preferenciasTransporte"));
        return preferenciasTransporte;
    }
  */
  obtenerModoTransporteIdService(modoTransporteIn: any) {
    let modoTransporte = null;
    if (modoTransporteIn.id == 1) {
      modoTransporte = '2';
    }

    if (modoTransporteIn.id == 2) {
      modoTransporte = '0';
    }

    if (modoTransporteIn.id == 3) {
      modoTransporte = '6';
    }

    if (modoTransporteIn.id == 4) {
      modoTransporte = '3';
    }

    if (modoTransporteIn.id == 5) {
      modoTransporte = '4,7';
    }

    if (modoTransporteIn.id == 6) {
      modoTransporte = '1';
    }

    if (modoTransporteIn.id == 7) {
      modoTransporte = '5';
    }

    if (modoTransporteIn.id == 8) {
      modoTransporte = '8';
    }


    if (modoTransporteIn.id == 9) {
      modoTransporte = '7';
    }

    return modoTransporte;
  }

  /*
    watchLocationInsideAmva(): void {
        Scheduler.
    }
*/

  // @ts-ignore
  validarMunicipioUbicacion(lat, lon) {
    let municipio;
    //let posicion: any = this.events.publish('obtenerPosicion');
    if (lat != null) {
      return new Promise((resolve, reject) => {
        this.nativeGeocoder.reverseGeocode(lat, lon)
          // @ts-ignore
          .then((result: NativeGeocoderReverseResult) => {
            console.log(municipio = JSON.stringify(result));
            municipio = result.subAdministrativeArea;
            let respuesta;
            if (municipio == 'Medellín') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Bello') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Itagüí') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Envigado') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Caldas') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Copacabana') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'La Estrella') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Girardota') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Sabaneta') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else if (municipio == 'Barbosa') {
              respuesta = "usuario esta dentro del Área Metropolitana";
              resolve(respuesta);
            } else {
              respuesta = "usuario esta fuera del Área Metropolitana";
              resolve(respuesta);
            }
          })
      });
    }

  }

  insideAMVA(municipio: string) {
    return new Promise((resolve, reject) => {
      if (municipio == 'Medellin') {
        resolve('AMVA');
      } else if (municipio == 'Bello') {
        resolve('AMVA');
      } else if (municipio == 'Itagui') {
        resolve('AMVA');
      } else if (municipio == 'Envigado') {
        resolve('AMVA');
      } else if (municipio == 'Caldas') {
        resolve('AMVA');
      } else if (municipio == 'Copacabana') {
        resolve('AMVA');
      } else if (municipio == 'La Estrella') {
        resolve('AMVA');
      } else if (municipio == 'Girardota') {
        resolve('AMVA');
      } else if (municipio == 'Sabaneta') {
        resolve('AMVA');
      } else if (municipio == 'Barbosa') {
        resolve('AMVA');
      } else {
        resolve('Fuera');
      }
    })
  }

  obtenerUbicacionActual(): Promise<any> {

    return new Promise((resolve, reject) => {
      if (this.platform.is('core') || this.platform.is('mobileweb')) {

        this.getPositionBrowser()
          .then(value => {
            let position = { latitud: value.coords.latitude, longitud: value.coords.longitude };
            resolve(position);

          })
          .catch(error => {
            reject(error);
          });
      } else {

        this.geolocation
          .getCurrentPosition()
          .then(res => {
            let position = { latitud: res.coords.latitude, longitud: res.coords.longitude };
            resolve(position);
          })
          .catch(error => {
            reject(error);
          });


      }


    });


  }

  getPositionBrowser(): Promise<any> {
    return new Promise((resolve, reject) => {
      navigator.geolocation.getCurrentPosition((position) => {
        resolve(position);
      },
        ((error) => {
          reject('No se logro obtener la posicion');
        })
      );

    })
  }

}
