import { LocationProvider } from './../../providers/location';
import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  ElementRef,
  AfterViewInit
} from "@angular/core";
import {NavController, NavParams, ViewController,ToastController, ModalController } from "ionic-angular";
import { Geolocation, Geoposition } from '@ionic-native/geolocation';
import { GoogleMaps } from "../../../shared/utilidades/googleMaps";
import { FavoritosProvider } from "../../providers/favoritos/favoritos";
import { Ubicacion, MODOS_BUSQUEDA } from "../../models/ubicacion.model";
import { SeleccionarMapaPage } from "../../pages/seleccionar-mapa/seleccionar-mapa.page";
import { Common } from "../../../shared/utilidades/common";
import { GmapsMovilidad } from "../../providers/gmapsMovilidad";
declare var google: any;

@Component({
  selector: "buscar-ubicacion",
  templateUrl: "buscar-ubicacion.component.html"
})
export class BuscarUbicacionComponent implements AfterViewInit {
  @Input() animate?: any;
  @Input() ubicacion: Ubicacion;
  @Input() botonAtras?: boolean;
  @Input() root?: any;
  @Input() dismissComponent?: boolean;
  @Input() editable?: boolean;
  @Output() clickSeleccionarUbicacionMapa? = new EventEmitter();
  @Output() clickSeleccionarPrediccion? = new EventEmitter();
  @Output() clickAceptarUbicacionMapa? = new EventEmitter();
  @Input() showSeleccionarEnMapa?: boolean;

  autocomplete: any;
  geocoder: any;
  autocompleteItems: any;
  acService: any;

  placesService: any;
  location: any;
  radius: any;
  marker: any;
  ubicacionesFavoritas: any;
  ubicacionFavoritaPage: any;

  public modos = MODOS_BUSQUEDA;
  public LOGO = ".assets/movilidad/iconos/bus.png";

  constructor(
    public viewCtrl: ViewController,
    public navParams: NavParams,
    private navCtrl: NavController,
    public favoritosProvider: FavoritosProvider,
    public googleMaps: GoogleMaps,
    private utilidades: Common,
    private elRef: ElementRef,
    public geolocation: Geolocation,
    public toastCtrl: ToastController,
    private locationProvider : LocationProvider
  ) {
    this.showSeleccionarEnMapa = true;
    this.autocompleteItems = [];
    this.acService = new google.maps.places.AutocompleteService();
    this.geocoder = new google.maps.Geocoder();
    this.editable = true;
  }

  ngAfterViewInit() {
    if (
      this.elRef.nativeElement.querySelector(
        "#sbDireccion .searchbar-search-icon "
      ) != null
    ) {
      this.elRef.nativeElement
        .querySelector("#sbDireccion .searchbar-search-icon")
        .addEventListener("click", () => {
          this.utilidades.presentLoading();
          this.obtenerUbicacionActual();
        });
    }
  }

  updateSearch() {
    try {
        if (this.ubicacion.descripcion == "") {
          this.autocompleteItems = [];
          return;
        }
        let self = this;
        
        let defaultBounds = new google.maps.LatLngBounds(
          new google.maps.LatLng(6.075967, -75.633433),
          new google.maps.LatLng(6.450092, -75.323971)
        );
          
        let config = {
          input: self.ubicacion.descripcion,
          bounds: defaultBounds,
          componentRestrictions: { country: "CO" }
        };
          
        this.acService.getPlacePredictions(config, function(predictions, status) {
          self.autocompleteItems = [];
          if (predictions != null) {
              predictions.forEach(function(prediction) {
              self.autocompleteItems.push(prediction);
            });
          }
      });
    } catch (error) {
      console.log(error);
    }
  }

  seleccionarPrediccion(item: any) {
    this.utilidades.presentLoading();
    GmapsMovilidad.codificarDireccion(item.description, "address")
      .then(data => {
        this.utilidades.dismissLoading();
        this.ubicacion.latitud = data.latitud;
        this.ubicacion.longitud = data.longitud;
        this.ubicacion.descripcion = item.description;
        this.autocompleteItems = [];
        if (this.clickSeleccionarPrediccion) {
          this.clickSeleccionarPrediccion.emit();
        }
      })
      .catch(error => {
        this.utilidades.dismissLoading();
        this.utilidades.basicAlert(
          "Movilidad",
          "Ocurrió un inconveniente inténtelo nuevamente"
        );
      });
  }



  obtenerUbicacionActual() {
    this.ubicacion.descripcion = "Mi ubicación";
    this.autocompleteItems = [];

    let resp:Geoposition = this.locationProvider.getMyLocation();
    this.ubicacion.latitud = resp.coords.latitude;
    this.ubicacion.longitud = resp.coords.longitude;
    if (this.clickSeleccionarPrediccion) {
      console.log("ClickMiUbicacionSeleccionada", this.ubicacion.latitud + " " + this.ubicacion.longitud);
      this.clickSeleccionarPrediccion.emit();
    }
}

  buscarDireccion(event: any) {
    // debugger;
    if (event.keyCode == 13 || event.keyCode == undefined) {
      let item = { description: this.ubicacion.descripcion };
      this.seleccionarPrediccion(item);
    }
  }


  search(event: any){
    let item = { description: this.ubicacion.descripcion };
    this.seleccionarPrediccion(item);
  }


  seleccionarUbicacionMapa() {
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
      this.utilidades.dismissModal(data);
    })

    establecerUbicacionModal.present()
  }

  setEmpty(event:any){
    this.ubicacion.descripcion = '';
  }
}


