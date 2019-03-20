import { RutasLineasResponse } from './../../models/rutasLineasResponse.model';
import { Common } from './../../../shared/utilidades/common';
import { WsMovilidad } from './../../providers/wsMovilidad';
import { LineasYRutasDetalleComponent } from './../../components/lineas-rutas-detalle/lineas-rutas-detalle.component';
import { Component, Input, ViewChild } from '@angular/core';
import { NavController, NavParams, ViewController, PopoverController, ModalController } from 'ionic-angular';
import { Ubicacion } from "../../models/ubicacion.model";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";

@Component({
  selector: 'lineas-y-rutas-mapa',
  templateUrl: 'lineas-y-rutas-mapa.page.html',
})
export class LineasYRutasMapaPage {
  ubicacion?: Ubicacion;
  titulo: string = "Rutas y Líneas";
  public criterioBusqueda:any;
  public autocompleteItems:any[];

  public app:any;
  public rutasLineasResponse: RutasLineasResponse;
  public showDetalle: boolean;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public googleMaps: GoogleMaps,
    public utilidades:Common,
    public wsMovilidad: WsMovilidad
  ) {
    this.app= this.navParams.get('app')

    this.autocompleteItems = [];
    this.criterioBusqueda = "";
  }

  onResponseAutocompletado(data:any){
    let navOptions = {
      animate: false
    };

    this.navCtrl.push(LineasYRutasDetalleComponent,{ data: data.response.data, criterio: data.criterioBusqueda }, navOptions)
  }

  clickObtenerRutasLineas() {
    this.utilidades.presentLoading();
    this.onObtenerRutasLineas(this.criterioBusqueda.descripcion);
  }

  ionViewDidLeave(){
    this.utilidades.dismissLoading();
  }
  
  
  onObtenerRutasLineas(data: any) {
    this.wsMovilidad.obtenerRutasyLineas(data)
      .subscribe(
      succces => {
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
