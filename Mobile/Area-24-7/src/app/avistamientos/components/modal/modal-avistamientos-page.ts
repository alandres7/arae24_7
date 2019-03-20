import { IonicPage, NavParams, Slides, NavController, Events } from 'ionic-angular';
import { Component, ViewChild, } from '@angular/core';
import { WsAvistamiento } from "../../provider/wsAvistamiento";
import { CONFIG_ENV } from "../../../shared/config-env-service/const-env";
import { Common } from '../../../shared/utilidades/common';

//@IonicPage()

@Component({
    selector: 'modal-avistamiento-page',
    templateUrl: 'modal-avistamientos-page.html',
    styles: ['modal-avistamientos-page.scss'],

})
export class ModalAvistamientosPage {
    @ViewChild(Slides) slides: Slides;

    public data: any = {
        'texto': ' ',
        'imagen': 'assets/menu/menu.svg',
        'color': ' ',
        'contenido': ' ',
        'id': ' ',
        'imagenPrincipal': ' ',
        'nombreComun': ' ',
        'idCategoria': ' ',
        'idAplicacion': ' ',
        'idSubcategoria': ' ',
        'descripcion': ' ',
        'name': ' ',
    };

    imagen: any;
    slide: boolean;
    navegacion: boolean;
    menuDinamico: boolean;
    height: any;
    comentarios: any;
    subcategorias: any;
    modelSubcategoria: any;
    descripcion: any;
    listaArbolDesicion: any;
    listaArbolDesicionFiltrada: any;
    listaArbolDesicionRegreso: any;
    listaArbolDesicionSeleccionada: any = [];
    html: any;
    datosFormularioDinamico: any = {};

    constructor(private navParams: NavParams
              , public navCtrl: NavController
              , private wsAvistamiento: WsAvistamiento
              , private events: Events
              , private common: Common) {
        this.imagen = 'http://www.estudiantes.info/ciencias_naturales/images/ardilla-marron.png';
        this.slide = true;
        this.navegacion = false;
        this.menuDinamico = true;
        this.height = "290px;";
        this.events.subscribe('cerrarModalAvistamientos', () => {
            this.closeModal();
        });
        this.obtenerArbolDesicion();
    }




    ionViewWillEnter() {

        this.data = this.navParams.data;
        this.data.imagenPrincipal = this.imagen;

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
            this.height = "200px";
        }

    }

    cambiarColorIcono(eventObject) {


        var srcObj = eventObject.srcElement.children["0"];
        if (srcObj.tagName == "IMG") {
            srcObj.setAttribute("src", srcObj.getAttribute("src").replace("/assets/avistamiento/pajaro.png"));

        }

    }

    seleccionEspecie(subcategoria: any) {
        if (subcategoria === 27) {
            this.subcategorias = [{ id: 1, nombreComun: "pajaro 1" }, { id: 2, nombreComun: "pajaro 2" }, { id: 3, nombreComun: "pajaro 3" }];


        } else if (subcategoria === 28) {
            this.subcategorias = [{ id: 1, nombreComun: "mamifero 1" }, { id: 2, nombreComun: "mamiferos 2" }, { id: 3, nombreComun: "pajaro 3" }];

        } else if (subcategoria === 29) {
            this.subcategorias = [{ id: 1, nombreComun: "reptiles  1" }, { id: 2, nombreComun: "reptiles  2" }, { id: 3, nombreComun: "reptiles  3" }];

        }


    }


    registrar() {
        this.data.nombreComun = this.modelSubcategoria;
        this.data.idIcono = 1;
        this.data.descripcion = this.descripcion;
        this.data.name = 1234567;
        let last: any = this.listaArbolDesicionSeleccionada[this.listaArbolDesicionSeleccionada.length - 1];
        let respuesta = this.wsAvistamiento.registrar(CONFIG_ENV.REST_BASE_URL + "/api/avistamiento", this.data)

    }

    obtenerArbolDesicion() {
        this.wsAvistamiento.obtenerArbolDesicion().then((response: any) => {
            var respuesta: Array<any>;
            respuesta = JSON.parse(response._body);
            this.listaArbolDesicion = respuesta;
            console.log("respuesta arbol desicions", this.listaArbolDesicion);
            this.listaArbolDesicionFiltrada = [];

            for (var i = 0; i < this.listaArbolDesicion.length; i++) {
                if (this.listaArbolDesicion[i].idPadre == 23) {
                    this.listaArbolDesicionFiltrada.push(this.listaArbolDesicion[i]);
                }
            }

        },
            (error) => {

            });

        console.log("respuesta arbol desicions", this.listaArbolDesicionFiltrada);

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
        if (this.menuDinamico = true) {
            this.construirMenuDinamico();
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

    construirMenuDinamico() {
        this.menuDinamico = true;
        this.html = "";
        //let objeto = { categoria: "label", input: "1", descripcion: "label", input1: "2" };
        let objeto = [{ label: "descripcion" }, { input: "" }, { label: " titulo" }, { input: "" }, { image: "" }];
        objeto.forEach(element => {
            console.log("objeto", element);
            if (element.hasOwnProperty('label')) {
                let parent = document.getElementById('dinamicoFormulario');
                let div1 = document.createElement("div");
                let h4 = document.createElement("h4");
                h4.setAttribute('class', 'titulo');
                let text = document.createTextNode(element[0].label);
                h4.appendChild(text);
                div1.appendChild(h4);
                parent.appendChild(h4);

            } else if (element.hasOwnProperty('input')) {
                let parent = document.getElementById('dinamicoFormulario');
                let textarea = document.createElement("textarea");
                textarea.setAttribute('name', 'prueba');
                parent.appendChild(textarea);
                
                // this.html = this.html + `<div><ion-textarea  [(ngModel)]="` + element + `" name="` + element + `"></ion-textarea></div>`;
            }
        });
        console.log("objeto", this.html);
    }

}