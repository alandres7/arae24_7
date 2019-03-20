import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NavController, NavParams, ViewController, PopoverController, ModalController } from 'ionic-angular';

import { FavoritosProvider } from "../../providers/favoritos/favoritos"
import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";
import { UbicacionFavoritaPage } from "../ubicacion-favorita/ubicacion-favorita.page";
import { BuscarUbicacionComponent } from "../../components/buscar-ubicacion/buscar-ubicacion.component";
import { MenuFavoritoPopoverComponent } from "../../components/menu-favorito-popover/menu-favorito-popover.component";
import { UbicacionFavorita } from '../../models/UbicacionFavorita.model';
import { Common } from '../../../shared/utilidades/common';
import { WsMovilidad } from '../../providers/wsMovilidad';
import { Geoposition } from '@ionic-native/geolocation';
import { LocationProvider } from '../../providers/location';
import { GmapsMovilidad } from '../../providers/gmapsMovilidad';

@Component({
  selector: 'establecer-ubicacion',
  templateUrl: 'establecer-ubicacion.page.html',
})
export class EstablecerUbicacionPage {
  @ViewChild(BuscarUbicacionComponent) buscarUbicacionComponent: BuscarUbicacionComponent;
  @Input() ubicacion?: Ubicacion;
  animate: any;
  root: any;
  clickUbicacionSeleccionada?: any;
  tipo 

  @Output() clickNuevaUbicacionFavorita?= new EventEmitter();
  @Output() clickSeleccionarEnMapa?= new EventEmitter();
  @Output() clickUbicacionFavoritaSeleccionada?= new EventEmitter();

  map: any;
  listenerMarker: any;
  ubicacionesFavoritas: UbicacionFavorita[];
  ubicacionFavoritaPage: any;
  titulo: string = "";
  rootName:number;
  app:any;

  Ctrl:NavController;


  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public popoverCtrl: PopoverController,
    public favoritosProvider: FavoritosProvider,
    private utilidades: Common,
    public wsMovilidad: WsMovilidad,
    public locationProvider: LocationProvider
  ) {
    this.ubicacion = navParams.get('ubicacion');
    this.root = navParams.get('root');
    this.app = navParams.get('app');
    this.animate = false;
    this.ubicacionesFavoritas=[];
    this.rootName = navParams.get('rootName');

    this.titulo = "Establecer Ubicación";
  }

  ionViewDidEnter() {
    let idUsuario=this.utilidades.obtenerUsuarioActivo().id;
    this.onObtenerUbicacionesFavoritas(idUsuario);
  }

  ionViewDidLeave(){
   this.utilidades.dismissLoading();
  }
  
  clickSeleccionarPrediccion(event) {
    this.navCtrl.pop({ animate: false });
  }

  establecerUbicacion(itemUbicacionFavorita:UbicacionFavorita, event) {
    console.log("BuscarUbicacionModalComponent:establecerUbicacion", itemUbicacionFavorita)
    this.ubicacion.descripcion = itemUbicacionFavorita.nombre;
    this.ubicacion.latitud = itemUbicacionFavorita.coordenada[0];
    this.ubicacion.longitud = itemUbicacionFavorita.coordenada[1];

    console.log({"msg":'Ubicacion event', data: event})
    this.utilidades.dismissModal({data: itemUbicacionFavorita})
  }

  clickSeleccionarUbicacionMapa(event) {
    this.clickSeleccionarEnMapa.emit();
  }


  nuevaUbicacionFavorita() {
    let ubicacion = new Ubicacion();
    ubicacion.descripcion = "";
    ubicacion.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
    ubicacion.txtPlaceholder = "Nueva ubicación favorita";
    this.ubicacionFavoritaPage = this.navCtrl.push(UbicacionFavoritaPage, {
      //ubicacion: ubicacion
    });
  }

  mostrarOpcionesUbicacion(event, ubicacion) {
    let popCotrl = this.popoverCtrl.create(MenuFavoritoPopoverComponent, {
      ubicacion: ubicacion,
      rootNavCtrl: this.navCtrl
    });
    popCotrl.present();
  }

  onObtenerUbicacionesFavoritas(idUsuario:number) {
    this.wsMovilidad.obtenerUbicacionesFavoritas(idUsuario)
      .subscribe(
      succces => {
        this.wsMovilidad.ubicacionesFavoritas=succces;
        this.ubicacionesFavoritas=this.wsMovilidad.ubicacionesFavoritas;
      },
      error => {
        this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
      }
      );
  }

  public obtenerUbicacionActual() {
    let myPosition:Geoposition = this.locationProvider.getMyLocation();
    this.ubicacion.latitud = myPosition.coords.latitude;
    this.ubicacion.longitud = myPosition.coords.longitude;
    this.ubicacion.descripcion = 'Mi Ubicación'
    this.utilidades.dismissModal();
 }
 
}
