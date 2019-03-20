import { menuDinamico } from './../../../menu/provider/menu';
import { Component, Input } from '@angular/core';
import { IonicPage, NavController, NavParams, MenuController } from 'ionic-angular';
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { Common } from "../../../shared/utilidades/common";
import { ModalVigiasPage } from "../../components/modal/modal-vigias-page";
import { WsVigias } from "../../provider/wsVigias";
import { AppLayer } from '../../../../entities/app-layer';

/**
 * Generated class for the InicioPage page.
 *
 * See http://ionicframework.com/docs/components/#navigation for more info
 * on Ionic pages and navigation.
 */

@Component({
  selector: 'vigias-medio-ambiente-page',
  templateUrl: 'vigias-medio-ambiente-page.html',
})
export class VigiasMedioAmbientePage {

    @Input() app: AppLayer;
    listaVigias: any = {};

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public menu: MenuController,
    private utilidad: Common,
    private googleMaps: GoogleMaps,
    private pmenuService: menuDinamico,
    private wsVigias: WsVigias,
    private common: Common
  ) {

  }

  ionViewDidLoad() {

  }


  menuAnterior() {
    this.utilidad.submenu.sVigias = false;
  }

  /*
  opcionVigias() {
    this.googleMaps.quitarKmlVigias();
  }
*/
  opcionNo() {
    let pObjeto = { mensaje: 'Opcion no disponible...!', duration: 4000, posicion: 'middle' };
    this.utilidad.appToast(pObjeto);
  }

  swtAplicacion(item) {
    console.log('jonathan capasVigias', item);

    switch (item.id) {
      case 1:
        this.abrirModalRegistrar(item);


        break;

      case 2:
        
        this.obtenerVigias(item);

        break;

      case 3:

        //this.todosLosAvistamientos();
        this.todosLasVigias(item);

        break;

    }

  }



  abrirModalRegistrar(item) {
    var datos = { icono: String, id: String};
    datos.id = item.id;
    datos.icono = item.icono;
    const modal = this.common.createModal(ModalVigiasPage, datos);
    modal.present();


  }

  /* Metodo para realizar carga de los avistamientos de cada persona */
  obtenerVigias(id) {
    this.wsVigias.obtenerVigias().then((response: any) => {
      var respuesta: Array<any>;
      respuesta = JSON.parse(response._body);
      //respuesta = <Array<any>>response._body;
      this.listaVigias = respuesta;
      console.log("prueba vigias", this.listaVigias);

      if (this.listaVigias) {
        let cont = 0;
        this.listaVigias.forEach((element, index) => {
          this.listaVigias[cont].marcadores = [];
          //console.log("prueba vigias 2", this.listaVigias[cont]);
          if (element.marcador) { this.listaVigias[cont].marcadores.push(element.marcador); }
          this.listaVigias[cont].capaPadre = id.id;
          this.googleMaps.pintarMarkers(this.listaVigias[cont]);
          cont = cont + 1;
        });
      }


    },
      (error) => {

      });
  }


  todosLasVigias(id) {
    this.wsVigias.obtenerTodosLasVigias().then((response: any) => {
      var respuesta = JSON.parse(response._body);
      this.listaVigias = respuesta;
      console.log("VIGIAS APROBADOS", this.listaVigias);



      if (this.listaVigias) {
        let cont = 0;
        this.listaVigias.forEach((element, index) => {
          this.listaVigias[cont].marcadores = [];
          console.log("prueba vigias 2", this.listaVigias);
          this.listaVigias[cont].marcadores.push(element.marcador);
          this.listaVigias[cont].capaPadre = id.id;
          this.googleMaps.pintarMarkers(this.listaVigias[cont]);
          cont = cont + 1;
        });
      }



    },
      (error) => {

      });

  }

  isActiveLayer(layerId: number): boolean {
    let layer = this.app.children.find((layer_) => { return layer_.id == layerId });
    return layer.active;
}

}
