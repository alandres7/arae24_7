import { LineasYRutasMapaPage } from './../lineas-y-rutas-mapa/lineas-y-rutas-mapa.page';
import { ConsultaViaje } from './../consulta-viajes/consulta-viajes.page';
import { MenuRutasPage } from './../menu-rutas/menu-rutas.page';
import { Component, OnInit, Input } from '@angular/core';
import { NavController, Nav, NavParams, MenuController, AlertController, NavOptions } from 'ionic-angular';

import { PreferenciasMovilidadPage } from '../../pages/preferencias-movilidad/preferencias-movilidad.page';
import { Common } from "../../../shared/utilidades/common";
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { GmapsMovilidad } from '../../providers/gmapsMovilidad';
import { AppLayer } from '../../../../entities/app-layer';

import { SideMenu } from "../../../menu/page/side-menu";
import { LayerProvider } from '../../../../providers/layer/layer';
import { PreferencesProvider } from '../../../../providers/preferences/preferences';
import { TransportPreferences } from '../../../../entities/preferences/transport-preferences';

declare var google;

@Component({
  selector: 'movilidad-page',
  templateUrl: 'movilidad-page.html',
})
export class MovilidadPage implements OnInit {

  @Input() app: AppLayer

  map: any;
  busquedaViajes = true;
  modoBusquedaViaje = 1;

  private preferenciasTransportes: any[];
  private layerActive: number;
  public config : NavOptions = {animate:false}

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public menu: MenuController,
    public utilidad: Common,
    public googleMaps: GoogleMaps,
    public alertCtrl: AlertController,
    private common: Common,
    private layerProvider: LayerProvider,
    private preferencesProvider: PreferencesProvider
  ) {
    this.menu.enable(true);
    this.app = navParams.get('app');

    this.preferencesProvider.transportsPreferences$.subscribe(
      (transportsPreferences: TransportPreferences[]) => {
        // console.log('transportsPreferencesChanged$ ' + JSON.stringify(transportsPreferences));
        this.preferenciasTransportes = transportsPreferences;
      }
    );
    this.layerProvider.currentApp.subscribe(
      (app: AppLayer) => {
          GmapsMovilidad.setRadio(app.radius)
      }
    );

  }

  ngOnInit(): void {
    this.obtenerModosTransporte();
  }

  ionViewDidLeave(){
   this.common.dismissLoading();
  }

  mostraBusquedaViajes(modo: number, fabMovilidad: any) {
    this.busquedaViajes = false;
    GmapsMovilidad.eliminarMarkersPolylines(GmapsMovilidad.markersPolylines);
    this.modoBusquedaViaje = modo;

    if (modo != 3) {
      this.obtenerModosTransporte();
    }
    this.layerActive = modo;
  }


  obtenerModosTransporte() {
    let modos = this.common.obtenerModosTransportesActivos(this.preferenciasTransportes);
    let modos_array: any[] = modos.split(',');
    console.log("Modos", modos_array);

    if (modos_array.length == 1) {
      if (modos_array[0] == "9") {
        this.common.presentAcceptAlert("Movilidad", "Debes seleccionar al menos un modo de transporte en tus preferencias.");
      }
    }
  }

  getStyleClass(layer: number): string {
    return (layer == this.layerActive) ? 'layer-active' : 'layer-unactive';
  }

  isLayerActive(layer: number): boolean {
    return layer == this.layerActive;
  }

  showRutasCercanas(){
    let params = {
      'Ctrl':this.navCtrl
    }
    this.navCtrl.push(MenuRutasPage, params, this.config).then(res=>{
      
    })
  }


  showViajes(){
    let params = {
      'Ctrl':this.navCtrl
    }
    this.navCtrl.push(ConsultaViaje, params,this.config)
  }

  showLineasYRutas(){
    let params = {
      'Ctrl':this.navCtrl
    }
    this.navCtrl.push(LineasYRutasMapaPage, params, this.config)
  }

}
