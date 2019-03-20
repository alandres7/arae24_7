import { Component, Input } from '@angular/core';
import { NavController, NavParams, ViewController, App, AlertController } from 'ionic-angular';

import { UbicacionFavoritaPage } from '../../pages/ubicacion-favorita/ubicacion-favorita.page';
import { FavoritosProvider } from "../../providers/favoritos/favoritos"
import { Common } from '../../../shared/utilidades/common';
import { WsMovilidad } from '../../providers/wsMovilidad';

@Component({
  selector: 'menu-favorito-popover',
  templateUrl: 'menu-favorito-popover.component.html',
})
export class MenuFavoritoPopoverComponent {
  ubicacion: any;
  rootNavCtrl: any;

  constructor(public viewCtrl: ViewController,
    public navParams: NavParams,
    private alertCtrl: AlertController,
    public favoritosProvider: FavoritosProvider,
    private utilidades: Common,
    public wsMovilidad: WsMovilidad
  ) {
    console.log("FavoritoPopoverPage:construct", this);
    this.ubicacion = this.navParams.get("ubicacion");
    this.rootNavCtrl = this.navParams.get("rootNavCtrl");
  }

  editarUbicacionFavorita() {
    console.log("FavoritoPopoverPage:editarUbicacionFavorita");
    let self = this;
    this.rootNavCtrl.push(UbicacionFavoritaPage, {
      ubicacion: this.ubicacion,
      //root: self.rootNavCtrl
    });
    this.viewCtrl.dismiss();
  }

  eliminarUbicacionFavorita() {
    console.log("FavoritoPopoverPage:eliminarUbicacionFavorita")
    let self = this;

    this.utilidades.confirmAlert('Movilidad', '¿Está seguro que desea eliminar esta ubicacion favorita?')
      .then((data) => {
        this.utilidades.presentLoading();
        this.onEliminarUbicacionFavorita(this.ubicacion.id);
      })
      .catch((data) => {
        self.viewCtrl.dismiss();
      });

    self.viewCtrl.dismiss();
  }

  onEliminarUbicacionFavorita(idUbicacionFavorita:number) {
    this.wsMovilidad.eliminarUbicacionFavorita(idUbicacionFavorita)
      .subscribe(
      succces => {
        console.log('Succces eliminarUbicacionFavorita', succces);
        this.utilidades.dismissLoading();
        this.viewCtrl.dismiss();
        this.wsMovilidad.removerUbicacionFavorita(idUbicacionFavorita);
      },
      error => {
        console.log('Error eliminarUbicacionFavorita', error);
        this.utilidades.dismissLoading();
        this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
      }
      );
  }

  close() {
    this.viewCtrl.dismiss();
  }

}
