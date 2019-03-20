import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

import { BuscarUbicacionComponent } from '../buscar-ubicacion/buscar-ubicacion.component';
import { MenuFavoritoPopoverComponent } from '../menu-favorito-popover/menu-favorito-popover.component'
import { UbicacionFavoritaPage } from '../../pages/ubicacion-favorita/ubicacion-favorita.page';
import { FavoritosProvider } from "../../providers/favoritos/favoritos"
import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";

import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { PUNTOS_DETALLES_RUTAS, PUNTOS_RUTAS_LINEAS, PUNTOS_RUTAS } from "../../mocks";
import { LineasYRutasMapaPage } from "../../pages/lineas-y-rutas-mapa/lineas-y-rutas-mapa.page";
import { WsMovilidad } from '../../providers/wsMovilidad';
import { Common } from '../../../shared/utilidades/common';
import { RutasLineasResponse } from '../../models/rutasLineasResponse.model';
import { GmapsMovilidad } from '../../providers/gmapsMovilidad';
import { UtilsMovilidad } from '../../providers/utilsMovilidad';
import { Ruta } from '../../models/ruta.model';
import { LineasYRutasDetalleComponent } from '../lineas-rutas-detalle/lineas-rutas-detalle.component';


@Component({
  selector: 'lineas-y-rutas',
  templateUrl: 'lineas-y-rutas.component.html',
})
export class LineasYRutasComponent {

  criterioBusqueda: any;
  mostrarRutasLineas: boolean = false;
  autocompleteItems: any[];

  showDetalle: boolean;
  rutasLineas: any;
  imgDetalle: any;
  txtDetalle: any;
  checked: boolean = false;
  rutasLineasResponse: RutasLineasResponse;
  convertTime24to12=UtilsMovilidad.convertTime24to12;


  constructor(
    public navCtrl: NavController,
    public wsMovilidad: WsMovilidad,
    private utilidades: Common

  ) {
    console.log("lineas y rutas");
    this.autocompleteItems = [];
    this.criterioBusqueda = "";
  }

  clickObtenerRutasLineas() {
    this.utilidades.presentLoading();
    this.onObtenerRutasLineas(this.criterioBusqueda.descripcion);
  }

  onResponseAutocompletado(data:any){
    let navOptions = {
      animate: false
    };

    this.navCtrl.push(LineasYRutasDetalleComponent,{ data: data.response.data, criterio: data.criterioBusqueda }, navOptions);
  }

  onObtenerRutasLineas(data: any) {
    this.wsMovilidad.obtenerRutasyLineas(data)
      .subscribe(
      succces => {
        console.log('LineasYRutasComponent:onObtenerRutasLineas', succces);
        if (succces.codigo == 1) {

          this.rutasLineasResponse = succces as RutasLineasResponse;
          console.log('rutas', this.rutasLineasResponse);

          let navOptions = {
            animate: false
          };
          this.navCtrl.push(LineasYRutasDetalleComponent, { data: succces, criterio: this.criterioBusqueda }, navOptions);

        } else {
          if (succces.codigo == 2) {
            this.showDetalle = false;
            this.utilidades.basicAlert('Movilidad', 'No se encontraron resultados');
          }

        }
        this.utilidades.dismissLoading();

      },
      error => {
        console.error('LineasYRutasComponent:onObtenerRutasLineas', error);
        this.utilidades.dismissLoading();
        this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
      }
      );
  }

}
