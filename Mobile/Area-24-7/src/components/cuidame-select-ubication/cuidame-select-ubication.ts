import { Component, OnInit, OnDestroy } from '@angular/core';
import { Common } from '../../app/shared/utilidades/common';
import { MapSelectLocationComponent } from '../map-select-location/map-select-location';
import { GoogleGeocoderProvider } from '../../providers/google-geocoder/google-geocoder';
import { Geoposition } from '@ionic-native/geolocation';
import { NavParams } from 'ionic-angular/navigation/nav-params';
import { VigiaProvider } from '../../providers/vigia/vigia';
import { Vigia } from '../../entities/vigia';
import { CuidameDetailComponent } from '../cuidame-detail/cuidame-detail';
import { NavController } from 'ionic-angular/navigation/nav-controller';
import { DecisionTreeProvider } from '../../providers/decision-tree/decision-tree';
import { LocationProvider } from '../../app/movilidad/providers/location';
import { LocationChangeProvider } from '../../providers/location-change/location-change';
import { Subscription } from 'rxjs/Subscription';
import { TerritorioProvider } from '../../providers/territorio/territorio';

declare var google: any;

@Component({
  selector: 'cuidame-select-ubication',
  templateUrl: 'cuidame-select-ubication.html'
})
export class CuidameSelectUbicationComponent implements OnInit, OnDestroy {
  private loading: boolean = true;
  private getqry: Subscription;
  private currentPage: number = 5;
  private showMore: boolean = false;
  private geoposition: Geoposition;
  private address: any = {};
  private layerId: number;
  private color: string;
  private directions: any;
  private reports: any[] = [];
  constructor(private common: Common,
    private geocoder: GoogleGeocoderProvider,
    private navParams: NavParams,
    private vigiaProvider: VigiaProvider,
    private navCtrl: NavController,
    private decisionTreeProvider: DecisionTreeProvider,
    private locationChange: LocationChangeProvider,
    private territorioProvider: TerritorioProvider
  ) {
    //Variable que controlara la ubicacion
    this.directions = new google.maps.Geocoder;
    this.color = navParams.get('color');
  }

  ngOnInit(): void {
    this.address.direction = ''
    //Consultamos la ubicacion del usuario
    this.geoposition = this.locationChange.getGeoposition();
    let locationStr: string = this.geoposition.coords.latitude + ', ' + this.geoposition.coords.longitude;
    this.directions.geocode({ 'location': { lat: this.geoposition.coords.latitude, lng: this.geoposition.coords.longitude } }, (results: any) => {
      //Consultamos que la ubicacion que se selecciono esta dentro o fuera del AMVA
      this.territorioProvider.getInsideAmva(this.geoposition.coords.latitude, this.geoposition.coords.longitude).subscribe((response) => {
        if (response.json() == true) {
          this.address.direction = results[0].formatted_address;
          this.address.coordenadas = { lat: this.geoposition.coords.latitude, lng: this.geoposition.coords.longitude };
          this.getListReports();
        }
        else {
          this.common.basicAlePrtCss('Ubicación fuera del AMVA', 'Te encuentras fuera del AMVA seleccione una dirección para poder generar un reporte.', 'warning sGenRep btnSolo', 'Aceptar')
          this.address.direction = '';
          this.getListReports();
        }
      }, 
      error => console.log(CuidameSelectUbicationComponent.name + ' ngOnInit getInsideAmva error ' + JSON.stringify(error)));
    });
    this.layerId = this.navParams.get('layerId');
  }

  selectLocationFromMap() {
    //Se abre modal de mapa para que el usuario seleccione la ubicacion
    let mapSelectLocationModal = this.common.createModal(MapSelectLocationComponent, { desde: 'Vigia' }, { cssClass: 'mapaUbicacion' });
    mapSelectLocationModal.onDidDismiss((geoposition: Geoposition, role: string): void => {
      if (role == 'OK') {
        this.geoposition = geoposition;
        let locationStr: any = {
          lat: this.geoposition.coords.latitude,
          lng: this.geoposition.coords.longitude
        };
        //Consultamos que la ubicacion que se selecciono esta dentro o fuera del AMVA
        this.territorioProvider.getInsideAmva(this.geoposition.coords.latitude, this.geoposition.coords.longitude).subscribe((response) => {
          if (response.json() == true) {
            this.common.presentLoading();
            this.directions.geocode({ 'location': { lat: this.geoposition.coords.latitude, lng: this.geoposition.coords.longitude } }, (results: any) => {
              this.address.direction = results[0].formatted_address;
              this.address.coordenadas = locationStr;
              this.getListReports();
              setTimeout(() => {
                this.common.dismissLoading();
              }, 1000);
            });
          }
          else {
            this.common.basicAlePrtCss('Dirección fuera del AMVA', 'Seleccione una ubicación que se encuentre dentro del AMVA para poder generar un reporte.', 'warning sGenRep btnSolo', 'Aceptar')
            this.address.direction = '';
          }
        },
        error => console.log(CuidameSelectUbicationComponent.name + ' selectLocationFromMap getInsideAmva error ' + JSON.stringify(error)));
      }
    });
    mapSelectLocationModal.present();
  }
  closeModal() {
    this.common.dismissModal(this.address);
  }

  generateReport() {
    //Se cierra el modal y se envia el objeto con la direccion que selecciono el usuario
    this.common.dismissModal(this.address);
  }

  //Abre modal con informacion del reporte seleccionado
  viewReport(item: Vigia) {
    let detailsModal = this.common.createModal(CuidameDetailComponent, {
      markerId: item.id,
      color: this.color,
      from: 'select'
    });
    detailsModal.present();
  }

  getListReports() {
    this.reports = [];
    //Se cargan todos los reportes que esten cerca de la ubicacion del usuario
    this.vigiaProvider.getReports(this.address.coordenadas).subscribe((response: any) => {
      let list: any[] = [];
      this.loading = true;
      if(response.markersPoint){
        list = response.markersPoint;
      }
      else{
        list = response;
      }
      if(list.length == 0){
        this.loading = false;
      }
      list.forEach((report, i) => {
        this.vigiaProvider.getVigia(report.idMarker, 'marker').subscribe((vigia: any)=>{
          this.getqry = this.decisionTreeProvider.getNodo(vigia.idNodoArbol).subscribe((nodo: any) => {
            this.decisionTreeProvider.getNodo(nodo.idPadre).subscribe((padre: any) => {
              vigia.titulo = padre.descripcion;
              vigia.subtitulo = padre.nombre;
              vigia.rutaIcono = vigia.rutaIconoVentana;
              if(vigia.estado != 'PENDIENTE'){
                this.reports.push(vigia)
              }
              if (i + 1 == list.length) {
                this.loading = false;
                //Ordenamos los reportes de manera cronologica (por fecha)
                this.reports.sort((a, b) => a.fechaReporte > b.fechaReporte ? -1 : 1);
                //Si hay mas de 5 reportes activara la opcion de ver mas 
                if (this.reports.length > 5) {
                  this.showMore = true;
                }
              }
            })
          })
        })
      });
    })
  }

  //Despliega 5 reportes mas en la lista de sugerencias
  viewMore() {
    this.loading = true;
    setTimeout(() => {
      this.loading = false;
      this.currentPage = this.currentPage + 5;
      if (this.currentPage >= this.reports.length) {
        this.showMore = false;
      }
    }, 500);
  }

  ngOnDestroy() {
    // this.getqry.unsubscribe();
  }
}
