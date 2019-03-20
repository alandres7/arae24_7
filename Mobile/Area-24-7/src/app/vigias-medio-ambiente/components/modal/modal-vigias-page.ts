import { IonicPage, NavParams, Slides, NavController, AlertController } from 'ionic-angular';
import { Component, ViewChild } from '@angular/core';
import { MediaCapture, MediaFile, CaptureError, CaptureImageOptions, CaptureVideoOptions, CaptureAudioOptions } from '@ionic-native/media-capture';
import { Media, MediaObject } from '@ionic-native/media';
import { WsVigias } from "../../provider/wsVigias";
import { CONFIG_ENV } from "../../../shared/config-env-service/const-env";
import { Common } from '../../../shared/utilidades/common';


//@IonicPage()

@Component({
    selector: 'modal-vigias-page',
    templateUrl: 'modal-vigias-page.html',
    styles: ['modal-vigias-page.scss'],

})
export class ModalVigiasPage {

    constructor(private alertCtrl: AlertController
              , private common: Common
              , private navParams: NavParams, private mediaCapture: MediaCapture, public navCtrl: NavController, private media: Media, private WsVigias: WsVigias) {
        this.slide = true;
        this.grabando = 0;
        this.obtenerArbolDesicion();
        this.menuDinamico = false;
        this.multimedia = false;
        this.mostrarFormulario = true;


    }

    @ViewChild(Slides) slides: Slides;
    @ViewChild('myvideo') myVideo: any;
    public data: any = {
        'id': ' ',
        'icono': '',
        'crear': '',
    };
    slide: boolean;
    imagen: any;
    height: any;
    recurso: any;
    imagenRecurso: any;
    datafile: any;
    tipoReporte: any;
    file: MediaObject = this.media.create('myrecording.mp3');
    grabando: any;
    listaArbolDesicion: any;
    listaArbolDesicionFiltrada: any;
    listaArbolDesicionRegreso: any;
    listaArbolDesicionSeleccionada: any = [];
    navegacion: boolean;
    menuDinamico: boolean;
    multimedia: boolean;
    Imagen: boolean
    Audio: boolean
    Video: boolean
    listaElementosHtml: any = [];
    preguntasDinamicas: any = [];
    respuestaDinamicas: any = [];
    mostrarFormulario: boolean;

    ionViewWillEnter() {
        this.recurso = [{ id: 1, src: "assets/vigias/fauna.svg" }, { id: 2, src: "assets/vigias/flora.svg" }, { id: 3, src: "assets/vigias/aire.svg" }, { id: 4, src: "assets/vigias/agua.svg" }, { id: 5, src: "assets/vigias/suelo.svg" }, { id: 6, src: "assets/vigias/paisaje.svg" },];
        this.imagen = "assets/avistamiento/camara.png";
        this.data = this.navParams.data;
        this.imagenRecurso = "assets/avistamiento/pajaro.png";
        this.tipoReporte = 0;
        this.navegacion = false;
        this.Imagen = false;
        this.Audio = false;
        this.Video = false;
        


    }


    closeModal() {
        this.common.dismissModal();
    }

    // Metodo para hacer validacion en cambio de slide
    slideChanged() {
        let currentIndex = this.slides.getActiveIndex()
        if (currentIndex == 0) {
            this.slide = true;
        } else if (currentIndex == 1) {
            this.slide = true;
        } else if (currentIndex == 2) {
            this.slide = true;
        } else if (currentIndex == 3) {
            this.slide = true;
        } else if (currentIndex == 5) {
            this.slide = true;
        }

    }

    // Metodo para tomar foto
    captureFoto() {
        this.tipoReporte = 2;
        let options: CaptureImageOptions = { limit: 1 };
        this.mediaCapture.captureImage(options).then((imagedata: any) => {
            imagedata.forEach(element => {
                this.datafile = element;

            });


        })
    }

    captureVideo() {
        this.tipoReporte = 1;
        let options: CaptureVideoOptions = { limit: 1, duration: 300 };
        this.mediaCapture.captureVideo(options).then((videodata: any) => {
            videodata.forEach(element => {
                this.datafile = element.fullPath;
                let video = this.myVideo.nativeElement;
                video.src = this.datafile;

            });

        })
    }

    habilitarPanelAudio() {
        this.tipoReporte = 3;

    }

    captureAudio() {
        console.log
        this.file.release();
        this.grabando = 1;
        this.file.startRecord()
    }

    stopAudio() {
        this.grabando = 0;
        this.file.stopRecord()
    }

    playAudio() { this.file.play() }

    pauseAudio() { this.file.pause() }

    obtenerArbolDesicion() {
        this.WsVigias.obtenerArbolDesicion().then((response: any) => {
            var respuesta: Array<any>;
            respuesta = JSON.parse(response._body);
            this.listaArbolDesicion = respuesta;
            console.log("respuesta arbol desicions", this.listaArbolDesicion);
            this.listaArbolDesicionFiltrada = [];

            for (var i = 0; i < this.listaArbolDesicion.length; i++) {
                if (this.listaArbolDesicion[i].idPadre == null) {
                    this.listaArbolDesicionFiltrada.push(this.listaArbolDesicion[i]);
                }
            }

        },
            (error) => {

            });


    }

    seleccionOpcionArbol(eventObject) {
        this.navegacion = true;
        this.listaArbolDesicionFiltrada = [];
        let borrar = false;
        let listaLength: any = this.listaArbolDesicionSeleccionada.length;
        let listaLength1: any = 0
        let objetoArbol: any;
        for (var i = 0; i < this.listaArbolDesicion.length; i++) {
            if (this.listaArbolDesicion[i].idPadre == eventObject.id) {
                this.listaArbolDesicionFiltrada.push(this.listaArbolDesicion[i]);
            }
        }
        if (this.listaArbolDesicionFiltrada.length == 0) {
            this.menuDinamico = true;
        }
        if (this.listaArbolDesicionSeleccionada.length > 0) {

            for (var j = 0; j < listaLength; j++) {
                if (borrar) {
                    this.listaArbolDesicionSeleccionada.splice(j - listaLength1, 1);
                    listaLength1 = listaLength1 + 1;
                }

                if (!borrar) {
                    if (this.listaArbolDesicionSeleccionada[j].idPadre == eventObject.idPadre) {

                        objetoArbol = eventObject;
                        borrar = true;
                    }
                }

            }
            if (borrar) {
                this.listaArbolDesicionSeleccionada.splice(objetoArbol.idPadre, 1);
                this.listaArbolDesicionSeleccionada.push(objetoArbol);
                borrar = true;
            }
            if (!borrar) {
                this.listaArbolDesicionSeleccionada.push(eventObject);
                borrar = true;
            }
        }
        if (!borrar) {
            this.listaArbolDesicionSeleccionada.push(eventObject);
        }
        console.log("arbol seleccionado:", this.listaArbolDesicionSeleccionada);

        // aca validamos si se debe crear el menu dinámico
        if (this.menuDinamico == true) {
            this.consultaFormularioDinamico();
            //this.construirMenuDinamico();

        }

    }

    regresarArbolSeleccion() {
        this.listaArbolDesicionRegreso = this.listaArbolDesicion;
        let padre = this.listaArbolDesicionFiltrada[0].idPadre;
        for (var i = 0; i < this.listaArbolDesicion.length; i++) {
            if (this.listaArbolDesicion[i].id == padre) {
                let idPadre = this.listaArbolDesicion[i].idPadre;
                if (idPadre == null) {
                    this.navegacion = false;
                }
                this.listaArbolDesicionFiltrada = [];
                for (var i = 0; i < this.listaArbolDesicion.length; i++) {
                    if (this.listaArbolDesicionRegreso[i].idPadre == idPadre) {
                        this.listaArbolDesicionFiltrada.push(this.listaArbolDesicionRegreso[i]);
                    }
                }
            }
        }

    }

    construirMenuDinamico(formulario) {
        var datos = formulario.preguntas;
        console.log("preguntas", datos);
        this.preguntasDinamicas = datos;
        var tipoMultimedia = formulario.tipoMultimedia;
        datos.forEach(element => {
            console.log("objeto", element);
            if (element.tipoPregunta.nombre == "Texto") {
                let parent = document.getElementById('dinamicoFormulario');
                let div1 = document.createElement("div");
                let h4 = document.createElement("h4");
                h4.setAttribute('class', 'titulo');
                let text = document.createTextNode(element.descripcion);
                h4.appendChild(text);
                div1.appendChild(h4);
                parent.appendChild(h4);

                let textarea = document.createElement("textarea");
                textarea.setAttribute('class', 'textarea');
                textarea.setAttribute('name', element.Pregunta);
                textarea.setAttribute('id', element.Pregunta);
                parent.appendChild(textarea);
                this.listaElementosHtml.push(element.Pregunta);
                

            } else if (element.tipoPregunta.nombre == "Lista") {
                let parent = document.getElementById('dinamicoFormulario');
                let div1 = document.createElement("div");
                let h4 = document.createElement("h4");
                h4.setAttribute('class', 'titulo');
                let text = document.createTextNode(element.descripcion);
                h4.appendChild(text);
                div1.appendChild(h4);
                parent.appendChild(h4);
                this.listaElementosHtml.push(element.Pregunta);
                //Create and append select list
                var selectList = document.createElement("select");
                selectList.setAttribute('class', 'titulo');
                selectList.setAttribute('id', element.Pregunta);
                parent.appendChild(selectList);

                //Create and append the options
                for (var i = 0; i < element.opcPreguntas.length; i++) {
                    var option = document.createElement("option");
                    option.value = element.opcPreguntas[i].valor;
                    option.text = element.opcPreguntas[i].valor;
                    selectList.appendChild(option);
                }


            }

        });
        this.Imagen = true;
        this.multimedia = true;

        if (tipoMultimedia == 'Imagen') {
            this.Imagen = true;
            this.multimedia = true;
        } else if (tipoMultimedia == 'Audio') {
            this.Audio = true;
            this.multimedia = true;
        } else if (tipoMultimedia == 'Video') {
            this.Video = true;
            this.multimedia = true;
        }
    }

    consultaFormularioDinamico() {
        var datos: any = "";
        this.listaArbolDesicionSeleccionada.forEach(element => {
            if (element.aliasReporte) {
                datos = datos + ', ' + element.aliasReporte;
            } else if (element.reporte) {
                datos = datos + ', ' + element.nombre;
            }
        });

        var nodo = this.listaArbolDesicionSeleccionada[this.listaArbolDesicionSeleccionada.length - 1];
        this.WsVigias.obtenerFomrularioDinamico(nodo.objeto.id).then((response: any) => {
            var respuesta: Array<any>;
            respuesta = JSON.parse(response._body);
            if (respuesta != null) {
                this.construirMenuDinamico(respuesta);
                console.log("respuesta arbol desicions", respuesta);
            }

        },
            (error) => {
            });

    }

    validarExistencia() {
        var datos: any = "";
        this.listaArbolDesicionSeleccionada.forEach(element => {
            if (element.aliasReporte) {
                datos = datos + ', ' + element.aliasReporte;
            } else if (element.reporte) {
                datos = datos + ', ' + element.nombre;
            }
        });

        var nodo = this.listaArbolDesicionSeleccionada[this.listaArbolDesicionSeleccionada.length - 2];
        this.WsVigias.validarExistenciaReporteSimilar(nodo, datos).then((response: any) => {
            var respuesta: Array<any>;
            respuesta = response._body;
            console.log("respuesta numero vigias", respuesta);
            let alert = this.alertCtrl.create({
                title: 'Nodos seleccionados',
                buttons: [
                    {
                        text: 'Comentar',
                        handler: () => {
                            this.registrarVigia("comentar");
                        }
                    },
                    {
                        text: 'Crear nuevo',
                        handler: () => {
                            this.registrarVigia("crear");
                        }
                    }
                ],
            });
            alert.present();

        },
            (error) => {
            });
    }

    aceptarDatos(){
        this.mostrarFormulario = false;
        this.listaElementosHtml.forEach((element, index) => {
            let respuesta: any = {};
            let tsnString = (document.getElementById(element) as HTMLTextAreaElement).value;
            let pregunta = this.preguntasDinamicas[index].descripcion;
            respuesta.pregunta = pregunta;
            respuesta.respuesa = tsnString;
            this.respuestaDinamicas.push(respuesta);
            console.log("respuesta", this.respuestaDinamicas);
        });

    }

    registrarVigia(tipo) {
       

        if (tipo == 'comentar') {
            this.data.crear = false;
        } else {
            this.data.crear = true;
        }
        var nodo = this.listaArbolDesicionSeleccionada[this.listaArbolDesicionSeleccionada.length - 2];
        let respuesta = this.WsVigias.registrar(CONFIG_ENV.REST_BASE_URL + "/api/vigia", this.data, nodo.id, this.listaArbolDesicionSeleccionada, this.datafile,this.respuestaDinamicas);

    }


}