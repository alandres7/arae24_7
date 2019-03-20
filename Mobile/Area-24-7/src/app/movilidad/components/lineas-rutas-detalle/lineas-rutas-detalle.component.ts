import { Component, ViewChild } from '@angular/core';
import { NavController, Navbar } from 'ionic-angular';


import { WsMovilidad } from '../../providers/wsMovilidad';
import { Common } from '../../../shared/utilidades/common';
import { RutasLineasResponse } from '../../models/rutasLineasResponse.model';
import { GmapsMovilidad } from '../../providers/gmapsMovilidad';
import { UtilsMovilidad } from '../../providers/utilsMovilidad';
import { ViewController } from 'ionic-angular/navigation/view-controller';
import { NavParams } from 'ionic-angular/navigation/nav-params';



@Component({
  selector: 'lineas-y-rutas-detalle',
  templateUrl: 'lineas-rutas-detalle.component.html',
})
export class LineasYRutasDetalleComponent {

  criterioBusqueda: any;
  mostrarRutasLineas: boolean = false;
  autocompleteItems: any[];

  titulo: string = "Rutas y Líneas";

  showDetalle: boolean;
  rutasLineas: any = [];
  imgDetalle: any;
  txtDetalle: any;
  checked: boolean = false;
  rutasLineasResponse: RutasLineasResponse;
  convertTime24to12 = UtilsMovilidad.convertTime24to12;

  rutas: any = [];
  lineas: any = [];
  markers: any = [];


  public rutasActive:boolean = false;
  public lineasActive:boolean = false;

  @ViewChild(Navbar) navBar: Navbar;

  constructor(
    public navCtrl: NavController,
    public viewCtrl: ViewController,
    public wsMovilidad: WsMovilidad,
    private utilidades: Common,
    private navPrms: NavParams

  ) {

    this.mostrarRutasLineas = true;
    this.showDetalle = false;
    this.autocompleteItems = [];
    
    let data = navPrms.get("data");
    this.criterioBusqueda = navPrms.get("criterio");
    this.rutasLineasResponse = data as RutasLineasResponse;
  }

  ionViewDidLoad() {
  }

  ngOnInit() {
   this.showDetalleAuto();
  }


  private showDetalleAuto(){
    // debugger;
    if(this.rutasLineasResponse.lineas != undefined && this.rutasLineasResponse.lineas.length > 0){
      this.onClickRutasLineas(1)
    }

    if(this.rutasLineasResponse.rutas != undefined && this.rutasLineasResponse.rutas.length > 0){
      this.onClickRutasLineas(0)
    }
  }

  ionViewWillLeave() {
    this.limpiarMarkers();
    GmapsMovilidad.ocultarRutas(this.rutasLineasResponse.lineas);
    GmapsMovilidad.ocultarRutas(this.rutasLineasResponse.rutas);
  }


  limpiarMarkers(){
    if(this.markers.length > 0){
      for (let marker of this.markers) {
        marker.setMap(null);
      }
    }

  }

  onObtenerRutasLineas(data: any) {
    this.wsMovilidad.obtenerRutasyLineas(data)
      .subscribe(
        succces => {
          this.autocompleteItems = [];
          this.limpiarMarkers();

          if (succces.codigo == 1) {
            if (this.rutasLineasResponse) {
              GmapsMovilidad.ocultarRutas(this.rutasLineasResponse.lineas);
              GmapsMovilidad.ocultarRutas(this.rutasLineasResponse.rutas);
            }
            this.rutasLineasResponse = succces as RutasLineasResponse;
            this.mostrarRutasLineas = true;
            this.showDetalle = false;
            this.rutasActive = false;
            this.lineasActive = false;
            this.showDetalleAuto();
            console.log(this.rutasLineasResponse)

          } else {
            if (succces.codigo == 2) {
              this.showDetalle = false;
              this.rutasActive = false;
              this.lineasActive =false;
              this.utilidades.basicAlert('Movilidad', 'No se encontraron resultados');
            }

          }
          this.showDetalle = true;
          this.utilidades.dismissLoading();

        },
        error => {
          console.error('LineasYRutasComponent:onObtenerRutasLineas', error);
          this.utilidades.dismissLoading();
          this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
        }
      );
  }

  onClickRutasLineas(tipo: number) {
    this.autocompleteItems = [];
    this.rutasLineas = [];
    this.txtDetalle = 'Detalle'
    if(tipo == 0){
      this.rutasActive = !this.rutasActive;
    }else if(tipo == 1){
      this.lineasActive = !this.lineasActive;
    }

    if(this.lineasActive){
      if(this.rutasLineasResponse.lineas.length == 0){
        this.utilidades.basicAlert('Movilidad', 'No hay registros para mostrar');
        this.lineasActive = false;
      }else{
        this.rutasLineasResponse.lineas.map((element)=>{
          this.rutasLineas.push(element)
        })
      }
    }


    if(this.rutasActive){
      if(this.rutasLineasResponse.rutas.length == 0){
        this.utilidades.basicAlert('Movilidad', 'No hay registros para mostrar');
        this.rutasActive = false;
      }else{
        this.rutasLineasResponse.rutas.map((element)=>{
              this.rutasLineas.push(element)
        })
      }
    }
    this.showDetalle = true;

    if(!this.rutasActive && !this.lineasActive){
      this.showDetalle = false;
    }
  }

  checkMostrarRuta(event, ruta) {
    if (event.checked) {
      let modoTransporte = GmapsMovilidad.obtenerModoTransporte(ruta);
      GmapsMovilidad.mostrarRuta(ruta, modoTransporte);
      ruta.checked = true;
    } else {
      GmapsMovilidad.ocultarRuta(ruta);
      ruta.checked = false;
    }
  }

  mostrarRuta(event) {
    console.log("Se ejecutó el emitter Detalle Ruta");
    if (event.visible) {
      this.showDetalle = false;
      this.rutasActive = false;
      this.lineasActive =false;
    }

    for (let i = 0; i < event.markers.length; i++) {
      this.markers.push(event.markers[i]);
    }
  }


  actualizarListado() {
    if (this.criterioBusqueda == "") {
      this.autocompleteItems = [];

      return;
    }
    this.onObtenerRutasyLineasAutocompletado(this.criterioBusqueda);
  }


  onKeyEnterRutaLinea(event: any) {
    if (event.keyCode == 13) {
      this.seleccionarItem(this.criterioBusqueda, 2);
    }
  }

  seleccionarItem(item: any, tipo: number) {
    this.utilidades.presentLoading();
    // debugger;
    if (tipo == 1) {
      this.criterioBusqueda = item.descripcion;
      this.onObtenerRutaLineaDetalle(item.tipo, item.id);
    } else {
      this.onObtenerRutasLineas(this.criterioBusqueda);
    }
  }

   clickObtenerRutasLineas() {
    if (this.criterioBusqueda.trim().length > 0) {
      this.utilidades.presentLoading();
      this.onObtenerRutasLineas(this.criterioBusqueda);
    } else {
      this.utilidades.basicAlert(
        "Movilidad",
        "Por favor diligencie el campo de busqueda!"
      );
    }

  }

  onObtenerRutasyLineasAutocompletado(criterio: any) {
    this.wsMovilidad
      .obtenerRutasyLineasAutocompletado(criterio)
      .subscribe(
        succces => {
          this.autocompleteItems = [];
          this.autocompleteItems = succces;
        },
        error => {
          this.autocompleteItems = [];
        }
      );
  }

  onObtenerRutaLineaDetalle(tipo: any, id: any) {
    this.wsMovilidad
      .obtenerRutaLineaDetalle(tipo, id)
      .subscribe(
        succces => {

          console.log("Seleccionar item", succces);

          this.autocompleteItems = [];
          this.limpiarMarkers();
          this.utilidades.dismissLoading();

          if (this.rutasLineasResponse) {
            GmapsMovilidad.ocultarRutas(this.rutasLineasResponse.lineas);
            GmapsMovilidad.ocultarRutas(this.rutasLineasResponse.rutas);
          }

          let data = new RutasLineasResponse();

          if (succces.linea != null) {
            let lineas = [];
            lineas.push(succces.linea);
            data.lineas = lineas;
            data.rutas = [];
          } else {
            let rutas = [];
            rutas.push(succces.ruta);
            data.rutas = rutas;
            data.lineas = [];
          }

          this.rutasLineasResponse = data;
          console.log(this.rutasLineasResponse);
          this.mostrarRutasLineas = true;
          this.showDetalle = false;
          this.rutasActive = false;
          this.lineasActive =false;
          this.showDetalleAuto();
        },
        error => {
          this.utilidades.dismissLoading();
          this.utilidades.basicAlert(
            "Movilidad",
            "Ocurrió un inconveniente inténtelo nuevamente"
          );

        }
      );
  }

  onFocusSearchBar() {
    //document.getElementById('gridLineasRutas').classList.add("no-scroll");
    document.body.classList.add('no-scroll');
  }

  onBlurSearchBar() {
    //document.getElementById('gridLineasRutas').classList.remove("no-scroll");
    document.body.classList.remove('no-scroll');
  }

  clearSearch(event:any){
    this.showDetalleAuto();
    this.criterioBusqueda = '';
  }
}
