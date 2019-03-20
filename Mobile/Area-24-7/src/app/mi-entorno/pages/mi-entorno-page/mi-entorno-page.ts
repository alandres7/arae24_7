import { WsEntorno } from './../../provider/wsEntorno';
import { menuDinamico } from './../../../menu/provider/menu';
import { Component, Input } from '@angular/core';
import { IonicPage, NavController, NavParams, MenuController } from 'ionic-angular';
import { Common } from "../../../shared/utilidades/common";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { AppLayer } from '../../../../entities/app-layer';

/**
 * Generated class for the InicioPage page.
 *
 * See http://ionicframework.com/docs/components/#navigation for more info
 * on Ionic pages and navigation.
 */

@Component({
  selector: 'mi-entorno-page',
  templateUrl: 'mi-entorno-page.html'
})
export class MiEntornoPage {

  @Input() app: AppLayer;
  bearer: String;
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public menu: MenuController,
    private utilidad: Common,
    private googleMaps: GoogleMaps,
    private pmenuService: menuDinamico,
    private wsEntorno: WsEntorno
  ) {

  }

  ngOnInit(): void {
    console.log('mi entorno ')
  }

  menuAnterior() {
    this.utilidad.submenu.sEntorno = false;
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
    console.log('opcion pulsada', key);
    this.bearer = localStorage.getItem("bearer");
    if (key.id == 12) {
      this.wsEntorno.obtenerCapas(this.bearer, key.id).then((response: any) => {
        let puntos = JSON.parse(response._body);
        puntos.capaPadre = key.id;
        this.googleMaps.pintarMarkers(puntos);

        if (puntos.marcadores.length <= 0 && puntos.categorias.length <= 0) {
          this.opcionNo();
        }

      });

    }
    else {
/*
      
      var data = key.id + "/" + this.googleMaps.posicionLat + "/" + this.googleMaps.posicionLon + "/" + this.googleMaps.pRadioAccion;
      this.wsEntorno.obtenerCapasRadioAccion(this.bearer, data).then((response: any) => {
        let puntos = JSON.parse(response._body);
        puntos.capaPadre = key.id;
        this.googleMaps.pintarMarkers(puntos);

        if (puntos.marcadores.length <= 0 && puntos.categorias.length <= 0) {
          this.opcionNo();
        }

      });*/
    }




  }

  isActiveLayer(layerId: number): boolean {
    let layer = this.app.children.find((layer_) => { return layer_.id == layerId });
    return layer.active;
}
}
