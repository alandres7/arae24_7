import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { NavParams, Nav, NavController, AlertOptions, AlertController } from 'ionic-angular';
import { Common } from '../../app/shared/utilidades/common';
import { Vigia } from '../../entities/vigia';
import { LayerProvider } from '../../providers/layer/layer';
import { VigiaProvider } from '../../providers/vigia/vigia';
import { DecisionTree } from '../../entities/decision-tree';
import { FileUploadResult } from '@ionic-native/file-transfer';
import { TerritorioProvider } from '../../providers/territorio/territorio';
import { Subscription } from 'rxjs';
import { DecisionTreeProvider } from '../../providers/decision-tree/decision-tree';
@Component({
  selector: 'cuidame-report',
  templateUrl: 'cuidame-report.html'
})
export class CuidameReportComponent {
  @ViewChild(Nav) nav: Nav;
  private postVigiaSubscribe: Subscription
  private color: string;
  private infoReport: any;
  private municipio: string;
  private validateZone: boolean = true;
  private pictures: any;
  private coordenadas: any;
  private vigia: Vigia;
  private arbol: DecisionTree
  private territorio: any = {};
  private autoridad: any = {};


  constructor(private navParams: NavParams,
    private common: Common,
    private navCtrl: NavController,
    private layerProvider: LayerProvider,
    private vigiaProvider: VigiaProvider,
    private alertCtrl: AlertController,
    private territorioProvider: TerritorioProvider,
    private decisionTreeProvider: DecisionTreeProvider) {
    //Recibimos los parametros con la informacion del reporte
    this.color = navParams.get('color');
    this.infoReport = navParams.get('infoReport');
    this.municipio = navParams.get('municipio');
    this.pictures = navParams.get('pictures');
    this.coordenadas = navParams.get('coordenadas');
    this.infoReport.locationFormCtrl.value = this.infoReport.locationFormCtrl.value.replace(/#/g,'');
    this.infoReport.locationFormCtrl.value = this.infoReport.locationFormCtrl.value.replace(/-/g,'')

    //Informacion del nodo del arbol seleccionado
    this.arbol = navParams.get('arbol');
    this.decisionTreeProvider.getChildren(this.arbol.id).subscribe(
      (response: any): void => {
        this.arbol.id = response.json()[0].id;
        if(navParams.get('reportar') == true){
          console.log('entro')
          this.generateReport('NOAMVA');
        }
        if(response.json()[0].idAutoridadCompetente != null){
          this.vigiaProvider.getAuthority(response.json()[0].idAutoridadCompetente).subscribe((autoridad: any)=>{
            this.validateZone = false;
            this.autoridad = {
              nombre: autoridad.nombre,
              municipio: autoridad.municipio,
              correo: autoridad.correo,
              direccion: autoridad.direccion,
              telefono: autoridad.telefono,
              horario: autoridad.horario,
            }
            console.log(this.autoridad)
          })
        }
        else{
          //Consultamos que tipo de suelo es la ubicacion seleccionada (Rural o Urbano)
          this.territorioProvider.getCharacterizationCard(this.coordenadas.lat, this.coordenadas.lng).subscribe((response) => {
            this.territorio.municipio = response.json().nombreMunicipio;
            if (response.json().tipoSuelo == 'Urbano') {
              this.autoridad = {nombre: 'Área Metropolitana del Valle de Aburrá', municipio: ''}
              this.validateZone = true;
            }
            else {
              if (response.json().tipoSuelo == 'Rural') {
                this.territorio.suelo = 'Rural';

              }
            }
          })
        }
      })
    //Consultamos que la ubicacion que se selecciono esta dentro o fuera del AMVA
    // this.territorioProvider.getInsideAmva(this.coordenadas.lat, this.coordenadas.lng).subscribe((response) => {
    //   if (response.json() == true) {
    //     this.validateZone = true;
    //   }
    //   else {
    //     this.validateZone = false;
    //   }
    // },
    // error => console.log(CuidameReportComponent.name + ' constructor getInsideAmva error ' + JSON.stringify(error)));
    //Consultamos que tipo de suelo es la ubicacion seleccionada (Rural o Urbano)
    this.territorioProvider.getCharacterizationCard(this.coordenadas.lat, this.coordenadas.lng).subscribe((response) => {
      this.territorio.municipio = response.json().nombreMunicipio;
      if (response.json().tipoSuelo == 'Urbano') {
        this.territorio.suelo = 'Urbano';
      }
      else {
        if (response.json().tipoSuelo == 'Rural') {
          this.territorio.suelo = 'Rural';
        }
      }
    },
    error => console.log(CuidameReportComponent.name + ' constructor getCharacterizationCard error ' + JSON.stringify(error)))
  }

  ngOnDestroy() {
    // this.postVigiaSubscribe.unsubscribe();
  }

  closeModal() {
    if(this.navParams.get('reportar') == true){
      this.common.dismissModal('ok');
    }
    else{
      this.common.dismissModal();
    }
  }

  generateReport(typeReport: string) {
    this.vigia = new Vigia(null);
    this.vigia.description = this.infoReport.descriptionFormCtrl.value;
    this.vigia.urlMedia = this.pictures;
    this.vigia.lat = this.coordenadas.lat;
    this.vigia.lng = this.coordenadas.lng;
    this.vigia.layerId = 2;
    
    //Armamos el reporte para postearlo
    if (typeReport == 'AMVA') {
      this.common.presentLoading();
          // this.common.confirmAlertCss('Haz seleccionado una opción', '¿Estás seguro? una vez aceptes se generará el reporte.', 'warning sGenRep')
          // .then((response) => {
          //Si el reporte le corresponde al AMVA
          this.common.dismissModal('ok');
          setTimeout(() => {
            this.postVigiaSubscribe = this.vigiaProvider.postVigia(this.vigia, this.infoReport.locationFormCtrl.value, this.arbol)
              .subscribe((result: FileUploadResult): void => {
                //Se recibe el numero de radicado devuelto por el servicio del AMVA
                let radicado: number = 123456
                this.common.dismissLoading();
                this.common.basicAlePrtCss('Tu reporte ha sido aceptado Estado: Abierto.', `El número de radicado es <strong>#${radicado}</strong>, con este número podrás hacer seguimiento a tu reporte en la página: <strong>www.reportesamva.gov</strong>.`, 'success sGenRep btnSolo', 'Aceptar')
              });
            ;
          }, 500);
        // }).catch((response) => {
        //   this.common.dismissLoading();
        // });
        }
        else {
          //si el reporte no le corresponde al amva 
          setTimeout(() => {
            this.postVigiaSubscribe = this.vigiaProvider.postVigia(this.vigia, this.infoReport.locationFormCtrl.value, this.arbol)
              .subscribe((result: FileUploadResult): void => {
                this.common.basicAlePrtCss('Tu reporte ha sido generado', `Reporte generado`, 'success sGenRep btnSolo', 'Aceptar')
              });
            ;
          }, 500);
        }
  }
}
