import { ModalController } from 'ionic-angular';
import { SeleccionarMapaPage } from './../seleccionar-mapa/seleccionar-mapa.page';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NavController, NavParams, ViewController, AlertController } from 'ionic-angular';

import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";
import { FavoritosProvider } from "../../providers/favoritos/favoritos"
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { WsMovilidad } from '../../providers/wsMovilidad';
import { UbicacionFavorita } from '../../models/UbicacionFavorita.model';
import { Common } from '../../../shared/utilidades/common';

enum Modos {
  NUEVO,
  GUARDAR
}

@Component({
  selector: 'ubicacion-favorita',
  templateUrl: 'ubicacion-favorita.page.html',
})
export class UbicacionFavoritaPage {

  @Input() ubicacion: Ubicacion;
  item: any = {};
  categorias:any[];
  idCategoriaSelected:any;

  titulo: string = "Ubicaciones Favoritas";

  modo: any = Modos.NUEVO;
  root: any;
  seleccionarMapaModalFavorito: boolean = true;
  modalFavorito: boolean = true;
  @Output() clickGuardarUbicacionFavorita?= new EventEmitter();
  @Output() clickSeleccionarUbicacionMapaFavorita?= new EventEmitter();
  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    public favoritos: FavoritosProvider,
    public wsMovilidad: WsMovilidad,
    public googleMaps: GoogleMaps,
    private utilidades: Common,
    public alertCtrl: AlertController
  ) {
    if (this.navParams.get("ubicacion")) {
      let data=  this.navParams.get("ubicacion") as UbicacionFavorita;
      let ubi = new Ubicacion();
      ubi.latitud=data.coordenada[1];
      ubi.longitud=data.coordenada[0];
      ubi.descripcion=data.descripcion;
      this.idCategoriaSelected=data.idCategoria;
      this.item = {
        nombre: data.nombre,
        ubicacion: ubi,
        id:data.id
      };
      this.modo = Modos.GUARDAR;
      this.item.ubicacion.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
      //this.ubicacion = this.navParams.get("ubicacion").ubicacion;
    } else {
      let ubi = new Ubicacion();
      ubi.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
      this.item = {
        nombre: null,
        ubicacion: ubi,
        id:0
      };
      this.item.ubicacion.txtPlaceholder = "Ubicación";
      this.modo = Modos.NUEVO;
    }
    this.utilidades.presentLoading();
    this.onListarCategoriasUbicacionFavorita();

  }

  onChange(event){
    this.idCategoriaSelected=event;
  }

  clickSeleccionarPrediccion(event) {
    this.utilidades.dismissLoading();
  }

  ionViewDidLeave(){
    this.utilidades.dismissLoading();
  }

  guardar() {
    console.log("UbicacionFavoritaPage:guardar", this.item);
    debugger;
      if (this.item.nombre != null && this.item.nombre != "" && this.item.ubicacion.descripcion 
          && this.idCategoriaSelected != null) {
            
        console.log(this.item)
        let coordenada = [];
        coordenada[1] = this.item.ubicacion.latitud;
        coordenada[0] = this.item.ubicacion.longitud;
        let descripcion = this.item.ubicacion.descripcion;
        let idUsuario = this.utilidades.obtenerUsuarioActivo().id;
        let ubicacionFavorita = new UbicacionFavorita(this.item.id,this.item.nombre, descripcion, coordenada, idUsuario,this.idCategoriaSelected);
        this.utilidades.presentLoading();
        if (this.modo == Modos.NUEVO) {
          this.onCrearUbicacionFavorita(ubicacionFavorita);
        } else {
          this.onActualizarUbicacionFavorita(ubicacionFavorita);
        }

      }else {
        this.mostrarAlerta('Faltan campos por diligenciar!');
      }

  }

  mostrarAlerta(message) {
    let alert = this.alertCtrl.create({
      title: 'Advertencia',
      subTitle: message,
      buttons: ['ACEPTAR']
    });
    alert.present();
  }

  ubicacionValida(item) {
    if (item != null) {
      return item.nombre != null;
    }
    return false;
  }

  mostrarErrores() {
    console.log("UbicacionFavoritaPage:mostrarErrores");
  }

  chooseItem2(item: any) {
    console.log('modal > chooseItemmmmm > item > ', this, item);
    this.item.ubicacion.descripcion = item.description
    this.item = item;
  }

  clickSeleccionarUbicacionMapa() {
    console.log("UbicacionFavoritaPage:clickSeleccionarUbicacionMapa", this);
  }

  clickAceptarUbicacion() {
    this.seleccionarMapaModalFavorito = false;
    this.modalFavorito = true;
  }

  crearMarkerFlotante() {
    console.log("BuscarUbicacionComponent:crearMarkerFlotante", this);
    var DomElementMarker = document.createElement('div');
    DomElementMarker.id = 'domElementMarker';
    DomElementMarker.className = 'Centrarpin';

  }


  onCrearUbicacionFavorita(ubicacionFavorita: UbicacionFavorita) {
    this.wsMovilidad.crearUbicacionFavorita(ubicacionFavorita)
      .subscribe(
      succces => {
        this.utilidades.dismissLoading();
        console.log('Exito servicio CrearUbicacionFavorita', succces);
        this.navCtrl.pop();
      },
      error => {
        this.utilidades.dismissLoading();
        if (error.status==409) {
          this.utilidades.basicAlert('Movilidad', 'Se excedió en el numero de ubicaciones para registrar');
        } else {
          this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
        }
        console.log('Error servicio rutas cercanas', error);


      }
      );
  }

  onActualizarUbicacionFavorita(ubicacionFavorita: UbicacionFavorita) {
    this.wsMovilidad.actualizarUbicacionFavorita(ubicacionFavorita)
      .subscribe(
      succces => {
        this.utilidades.dismissLoading();
        console.log('Exito servicio actualizarUbicacionFavorita', succces);
        this.navCtrl.pop();
      },
      error => {
        console.log('Error servicio actualizarUbicacionFavorita', error);
        this.utilidades.dismissLoading();
        this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
      }
      );
  }

  onListarCategoriasUbicacionFavorita() {
    this.wsMovilidad.obtenerCategoriasUbcacionFavorita()
      .subscribe(
      succces => {
        console.log("Favoritas", succces);
        this.utilidades.dismissLoading();
        this.categorias=succces;
      },
      error => {

        this.utilidades.dismissLoading();
        this.utilidades.basicAlert('Movilidad', 'Ocurrió un inconveniente inténtelo nuevamente');
      }
      );
  }

  seleccionarMapa(event:any){
    let params = {
      ubicacion: this.ubicacion,
      root: this.root,
      animate: false
    }
    let navOptions = {
      showBackdrop: false,
      enableBackdropDismiss: false
    };

    let establecerUbicacionModal = this.utilidades.createModal(SeleccionarMapaPage,params,navOptions)
    
    establecerUbicacionModal.onDidDismiss(data=>{
      this.item.ubicacion = data
      console.log(data)
    })

    establecerUbicacionModal.present()
  }
}
