import { GoogleMaps } from './../../shared/utilidades/googleMaps';
import { ItemComponent } from './../components/item/item';
import { WsOrdenamiento } from './../provider/wsOrdenamiento';
import { Subject } from 'rxjs/Subject';
import { menuDinamico } from './../../menu/provider/menu';
import { Common } from './../../shared/utilidades/common';
import { OnInit, EventEmitter, Component, ViewChild } from '@angular/core';
import { BuscadorComponent } from '../components/buscador/buscador';



@Component({
    selector: 'ordenamiento-page',
    templateUrl: 'ordenamiento-page.html',
    styles: ['ordenamiento-page.scss']
})


export class Ordenamiento implements OnInit {


    listaEquipamentos: any;
    opcionSubmenu: any;
    vigilarSujeto = new Subject();
    token: String;

    listaObjeto = new EventEmitter<any>();
    elemSeleccionado;

    @ViewChild(ItemComponent) itemComponent;
    @ViewChild(BuscadorComponent) buscadorComponent;

    ngOnInit(): void {
        this.listaObjeto.emit(this.listaEquipamentos);
    }
    objetoList: any = [];
    constructor(public utilidad: Common,
        public pmenuService: menuDinamico,
        public wsOrdenamiento: WsOrdenamiento,
        public googleMaps: GoogleMaps
    ) {

        this.listaEquipamentos = <Array<any>>this.objetoList;
        this.token = localStorage.getItem('bearer');
        this.listaObjeto.emit(this.listaEquipamentos);
        this.vigilarSujeto.next(data => {
            this.listaObjeto.emit(data);
            this.listaEquipamentos = data;
        });

    }

    busquedaElemento(dato) {
        (!!~dato)
        {

        }
    }

    objetoSeleccionado(dato) {
        console.log('objeto devuelto ' + JSON.stringify(dato));

    }


    menuAnterior() {
        this.utilidad.submenu.sOrdenamiento = false;
    }

    opcionNo() {
        let pObjeto = { mensaje: 'Opcion no disponible...!', duration: 4000, posicion: 'middle' };
        this.utilidad.appToast(pObjeto);
    }

    itemSeleccionado(objeto) {
        /*Observable.Array(this.pmenuService.listaMenu)
        .map()
        objeto.color = codigoToggle*/
        console.log("objeto enviado desde item, ", objeto);
        this.googleMaps.pintarMarkers(objeto);
    }

    ListaFiltrada(objeto) {
        this.itemComponent.listaFiltrados = objeto;
    }

    /*modeloOpcion(opcion){
        this.opcionSubmenu[opcion] = 
    }

    crearOpcionMenu(valor) {
        if (this.opcionSubmenu[valor] == undefined || this.opcionSubmenu[valor] == null) {
            this.opcionSubmenu[valor] = true;
        }
        else {
            this.opcionSubmenu[valor] = false;
        }
    }*/

    obtenerCapas(id) {
        /*this.wsOrdenamiento.obtenerCapas(this.token, id).then((response: any) => {
            var respuesta = JSON.parse(response._body);
            this.vigilarSujeto.next(respuesta.categorias);
        },
        (error) => {

        });*/
        /*
        var data = id + "/" + this.googleMaps.posicionLat + "/" + this.googleMaps.posicionLon + "/" + this.googleMaps.pRadioAccion;

        this.wsOrdenamiento.obtenerCapasRadioAccion(this.token, data).then((response: any) => {
            var respuesta = JSON.parse(response._body);
            console.log("jonathan",respuesta)
            this.vigilarSujeto.next(respuesta.categorias);
        },
            (error) => {

            });*/
    }

    swtAplicacion(key) {
        
        this.itemComponent.obtenerCapas(key.id);
    }

    ngAfterViewInit() {
        this.itemComponent.buscadorComponent = this.buscadorComponent;
    }

}