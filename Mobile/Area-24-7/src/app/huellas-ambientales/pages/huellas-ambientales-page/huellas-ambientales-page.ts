import { ModalHuellasPage } from './../../components/modal-huellas-page/modal-huellas-page';
import { Component, Input } from '@angular/core';
import { IonicPage, NavController, NavParams, MenuController } from 'ionic-angular';
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { Common } from "../../../shared/utilidades/common";
import { menuDinamico } from './../../../menu/provider/menu';
import { AppLayer } from '../../../../entities/app-layer';

/**
 * Generated class for the InicioPage page.
 *
 * See http://ionicframework.com/docs/components/#navigation for more info
 * on Ionic pages and navigation.
 */
export class itemPregunta {
    id: Number;
    imagen: String;
    preguntas: Array<any>;
}

export class modelPreguntas {
    id: Number;
    encabezado: String;
    respuestas: Array<any>;
}

export class modelRespuestas {
    id: Number;
    respuesta: String;
}

@Component({
    selector: 'huellas-ambientales-page',
    templateUrl: 'huellas-ambientales-page.html',
})
export class HuellasAmbientalesPage {

    @Input() app: AppLayer;

    constructor(
        public navCtrl: NavController,
        public navParams: NavParams,
        public menu: MenuController,
        private utilidad: Common,
        private googleMaps: GoogleMaps,
        private pmenuService: menuDinamico,
  //      private common: Common
    ) {

    }

    ionViewDidLoad() {

    }

    menuAnterior() {
        this.utilidad.submenu.sHuellas = false;
    }

    /*
    opcionEntorno() {
        this.googleMaps.quitarKmlEntorno();
    }
*/
    opcionNo() {
        let pObjeto = { mensaje: 'Opcion no disponible...!', duration: 4000, posicion: 'middle' };
        this.utilidad.appToast(pObjeto);

    }

    swtAplicacion(key) {
        console.log('objeto, ', key);
        switch (key.id) {
            case 14:
                let datos = { 'texto': '', 'color': '', 'imagen': '', 'nombre': '', 'email': '', 'mensajeTitulo': '', 'mensajeTexto': '', 'listaPreguntas': [] };

                datos.color = "#EA7926";
                datos.imagen = 'assets/huellas-temp/pies.svg';
                datos.texto = 'Calcula la cantidad de agua que consumes';
                datos.nombre = 'prueba';
                datos.email = 'prueba@ada.com';
                datos.mensajeTitulo = 'titulo mensaje';
                datos.mensajeTexto = 'esto es una prueba de cualquier texto enviado';

                // listado de preguntas 

                datos.listaPreguntas = [];
                let objetoPregunta: itemPregunta;
                let preguntas: modelPreguntas;
                let respuestas: modelRespuestas;

                objetoPregunta = new itemPregunta();
                objetoPregunta.id = 1;
                objetoPregunta.imagen = 'assets/huellas-temp/Lavado de dientes.svg';
                objetoPregunta.preguntas = [];

                preguntas = new modelPreguntas();
                preguntas.id = 1;
                preguntas.encabezado = 'Cuantas veces te lavas los dientes al día';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 1;
                respuestas.respuesta = '1 a 3 al día';
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 2;
                respuestas.respuesta = '4 a 6 al día';
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 3;
                respuestas.respuesta = 'más de 7 al día';

                preguntas.respuestas.push(respuestas);
                objetoPregunta.preguntas.push(preguntas);

                preguntas = new modelPreguntas();
                preguntas.id = 2;
                preguntas.encabezado = '¿Cuanto tardas?';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 4;
                respuestas.respuesta = '1 min';
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 5;
                respuestas.respuesta = '3 min';
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 6;
                respuestas.respuesta = 'más de 5 min ';

                preguntas.respuestas.push(respuestas);
                objetoPregunta.preguntas.push(preguntas);
                datos.listaPreguntas.push(objetoPregunta);

                objetoPregunta = new itemPregunta();
                objetoPregunta.id = 2;
                objetoPregunta.imagen = 'assets/huellas-temp/ducha.svg';
                objetoPregunta.preguntas = [];

                preguntas = new modelPreguntas();
                preguntas.id = 1;
                preguntas.encabezado = 'Cuantas veces se baña al día';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 1;
                respuestas.respuesta = '1 al día'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 2;
                respuestas.respuesta = '2 al día'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 3;
                respuestas.respuesta = 'más 3 al día'
                preguntas.respuestas.push(respuestas);

                objetoPregunta.preguntas.push(preguntas);
                datos.listaPreguntas.push(objetoPregunta);

                objetoPregunta = new itemPregunta();
                objetoPregunta.id = 3;
                objetoPregunta.imagen = 'assets/huellas-temp/Lavado de platos.svg';
                objetoPregunta.preguntas = [];

                preguntas = new modelPreguntas();
                preguntas.id = 1;
                preguntas.encabezado = 'Cuantas veces lavas los platos al día';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 1;
                respuestas.respuesta = '1 a 3 al día';
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 2;
                respuestas.respuesta = '4 a 6 al día';
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 3;
                respuestas.respuesta = 'más de 7 al día';

                preguntas.respuestas.push(respuestas);
                objetoPregunta.preguntas.push(preguntas);

                preguntas = new modelPreguntas();
                preguntas.id = 2;
                preguntas.encabezado = '¿Cuanto tardas?';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 1;
                respuestas.respuesta = '1 min'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 2;
                respuestas.respuesta = '3 min'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 3;
                respuestas.respuesta = 'más de 5 min '
                preguntas.respuestas.push(respuestas);

                objetoPregunta.preguntas.push(preguntas);

                datos.listaPreguntas.push(objetoPregunta);

                objetoPregunta = new itemPregunta();
                objetoPregunta.id = 4;
                objetoPregunta.imagen = 'assets/huellas-temp/Lavadora.svg';
                objetoPregunta.preguntas = [];

                preguntas = new modelPreguntas();
                preguntas.id = 1;
                preguntas.encabezado = '¿Cuantas veces usas la lavadora a la semana?';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 1;
                respuestas.respuesta = '1 vez a la semana'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 2;
                respuestas.respuesta = '2 veces a la semana'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 3;
                respuestas.respuesta = 'más 3 veces a la semana'
                preguntas.respuestas.push(respuestas);

                objetoPregunta.preguntas.push(preguntas);

                datos.listaPreguntas.push(objetoPregunta);

                objetoPregunta = new itemPregunta();
                objetoPregunta.id = 5;
                objetoPregunta.imagen = 'assets/huellas-temp/baño.svg';
                objetoPregunta.preguntas = [];

                preguntas = new modelPreguntas();
                preguntas.id = 1;
                preguntas.encabezado = '¿Cuantas veces vas al baño al día?';
                preguntas.respuestas = [];

                respuestas = new modelRespuestas();
                respuestas.id = 1;
                respuestas.respuesta = '1 al día'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 2;
                respuestas.respuesta = '2 al día'
                preguntas.respuestas.push(respuestas);

                respuestas = new modelRespuestas();
                respuestas.id = 3;
                respuestas.respuesta = 'más 3 al día'
                preguntas.respuestas.push(respuestas);

                objetoPregunta.preguntas.push(preguntas);

                datos.listaPreguntas.push(objetoPregunta);

                console.log('impresion lista preguntas ', datos.listaPreguntas);
              //  let modal = this.common.create(ModalHuellasPage, datos);
                //modal.present();

                break;

            /*case 4:
                this.utilidad.submenu.sOrdenamiento = true;
                break;

            case 6:
                this.utilidad.submenu.sHuellas = true;
                break;

            case 2:
                this.utilidad.submenu.sVigias = true;
                break;

            case 1:
                this.utilidad.submenu.sMovilidad = true;
                break;

            case 5:
                this.utilidad.submenu.sEntorno = true;
                break;*/
            default:
                this.opcionNo();
                break;
        }
    }

    isActiveLayer(layerId: number): boolean {
        let layer = this.app.children.find((layer_) => { return layer_.id == layerId });
        return layer.active;
    }
}
