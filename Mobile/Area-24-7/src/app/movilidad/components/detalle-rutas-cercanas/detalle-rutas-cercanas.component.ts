import { GmapsMovilidad } from './../../providers/gmapsMovilidad';
import { Common } from './../../../shared/utilidades/common';
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';


@Component({
    selector: 'detalle-rutas-cercanas',
    templateUrl: 'detalle-rutas-cercanas.component.html'
})

export class DetalleRutasCercanasComponent{

    public data:any;
    public icon = '';
    public title = '';
    public lineas:[any] = [0];
    public iconClass: string = 'arrow-dropdown-circle'
    public showDetail:boolean = false;
    public desc:{'desc':String,id:String,'rutas':any} = {'desc':'','id':'','rutas':[]};
    public isEnciclaDetail:boolean = false;
    public isrecargaDetail:boolean = false;
    public tipoEstacionEnCiclaIcon:String = '';


    constructor(
        private navParams : NavParams,
        public common: Common
    ){
        this.data = navParams.get('data')
        this.title= this.obtenerTituloInfoWindows(this.data)
        this.icon = GmapsMovilidad.obtenerIconoInfoWindow(this.data)
        console.log(this.data)
    }

    ngOnInit() {
     this.desc = this.parseData(this.data)
    }

    ionViewDidLoad(){
      if (this.data.idEstacion || this.data.idEstacion != null) {
        if (this.data.idModoEstacion == 2 && this.data.nombreModoEstacion == "ENCICLA") {
          // titulo = "Estación EnCicla";

          if(this.data.tipoEstacion != undefined && this.data.tipoEstacion == 'A'){
            document.getElementById('bannerIcon').classList.add('bannerAuto')
          }else if (this.data.tipoEstacion != undefined && this.data.tipoEstacion == 'M'){
            document.getElementById('bannerIcon').classList.add('bannerManual')
          }
        }
      }
    }

    public parseData(data:any):{'desc':String,'id':String,'rutas':any}{
      let detailInfo: {'desc':String,'id':String,'rutas':any} = {'desc':'','id':'','rutas':[]}
      // debugger;

      if (data.idEstacion || data.idEstacion != null) {
        if (data.idModoEstacion == 2 && data.nombreModoEstacion == "ENCICLA") {
          // titulo = "Estación EnCicla";
          this.isEnciclaDetail = true;
          return detailInfo;

        }else if(data.codigoParadero != undefined && data.idParadero >= 0) {
          detailInfo.desc = data.descripcion
          detailInfo.rutas = data.rutas
        }else{
          // Transportes Metro
          detailInfo.desc = `Estación ${data.descripcion}`
          detailInfo.rutas = data.lineas

          if(data.nombreModoEstacion == 'METRO'){
            detailInfo.id = 'iconMetro'
          }else if(data.nombreModoEstacion == 'METRO_CABLE'){
            detailInfo.id = 'iconMetroCable'
          }else if(data.nombreModoEstacion == 'TRANVIA'){
            detailInfo.id = 'iconTranvia'
          }else if (data.nombreModoEstacion == 'METRO_PLUS'){
            detailInfo.id = 'iconMetroPlus'
          }
          return detailInfo
        }
      }

      if(data.idPunto && data.tipoPunto){
        this.isrecargaDetail = true;
        detailInfo.desc = data.descripcion
        return detailInfo;
      }
        
      if(data.idRuta){
        detailInfo.desc = data.descripcion
        detailInfo.rutas = data.rutas
        detailInfo.id = 'iconRutas'
        return detailInfo;
      }
      
      return detailInfo;
    }
  

    public toggleDetalle(event:any){
      // debugger;
      if(!this.showDetail){
        this.iconClass = "arrow-dropdown-circle";
        document.getElementById(`${event}`).classList.remove('hideDetalle');
        this.showDetail = !this.showDetail;
      }else{
        this.iconClass = "arrow-dropup-circle";
        document.getElementById(`${event}`).classList.add('hideDetalle')
        this.showDetail = !this.showDetail;
      }
    }

    public obtenerTituloInfoWindows(data: any): string {
        let titulo = "";
    
        if (data.idEstacion || data.idEstacion == null) {
          if (data.idModoEstacion == 2 && data.nombreModoEstacion == "ENCICLA") {
            titulo = "Estación EnCicla";
          }else{
            titulo = "Estación Metro";
          }
    
          if (data.idModoEstacion == 1) {
            titulo = "Estación Autobus";
          }
    
          if (data.idModoEstacion == 0) {
            titulo = "Estación Tranvia";
          }
    
          if (data.idModoEstacion == 6) {
            titulo = "Estación Metrocable";
          }
    
          if (data.idModoEstacion == 3) {
            titulo = "Estación Metroplús";
          }
        }
    
        if (data.codigoParadero) {
          titulo = "Paradero Bus";
        }
    
        if (data.codigoRuta) {
          titulo = "Ruta Bus";
        }
    
        if (data.idModoLinea) {
          titulo = "Linea Metro";
        }
    
        if (data.idCiclovia) {
          titulo = "Cicloruta";
        }
    
        if (data.idPunto) {
    
          if (data.tipoPunto == 'R') {
            titulo = "Punto Recarga Cívica";
          } else {
            titulo = "Punto Expedición Cívica";
          }
    
        }

        return titulo;
    
    }

    public closeModal(): void {
        this.common.dismissModal();
    } 
}
