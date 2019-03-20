import { LineasYRutasMapaPage } from './../../pages/lineas-y-rutas-mapa/lineas-y-rutas-mapa.page';
import { MenuRutasPage } from './../../pages/menu-rutas/menu-rutas.page';
import { ConsultaViaje } from './../../pages/consulta-viajes/consulta-viajes.page';
import { NavController } from 'ionic-angular';
import { Component, Input } from '@angular/core';

@Component({
    selector: 'menu-opciones',
    templateUrl: 'menu-opciones.component.html'
})

export class MenuOpcionesComponent{
    private appSettings: any = {
        'color' : 'rgb(0, 96, 182)'
    }
    private layerActive: number;
    @Input() activeLayer: number = 0
    

    constructor(private navCtrl: NavController){
    }

    ngOnInit() {
        this.layerActive = this.activeLayer;
    }

    isLayerActive(layer: number): boolean {
        return layer == this.layerActive;
      }

    getStyleClass(layer: number): string {
    return (layer == this.layerActive) ? 'layer-active' : 'layer-unactive';
    }

    goTo(page:number):void{
            let navParams:any;
            switch(page){
                case 1:
                    navParams = {'page-name': page, 'nav':this,'color':'rgb(0, 96, 182)'}
                    if(this.navCtrl.canGoBack){
                        this.navCtrl.popToRoot({animate:false}).then(()=>{
                            this.navCtrl.push(ConsultaViaje, navParams, {animate:false})
                        })
                    }
                    break;
                case 2:
                    navParams = {'page-name': page, 'nav':this,'color':'rgb(0, 96, 182)'}
                    if(this.navCtrl.canGoBack){
                        this.navCtrl.popToRoot({animate:false}).then(()=>{
                            this.navCtrl.push(MenuRutasPage, navParams, {animate:false})
                        })
                    }
                    break;
                case 3:
                    navParams = {'page-name': page, 'nav':this,'color':'rgb(0, 96, 182)'}
                    if(this.navCtrl.canGoBack){
                        this.navCtrl.popToRoot({animate:false}).then(()=>{
                            this.navCtrl.push(LineasYRutasMapaPage, navParams, {animate:false})
                        })
                    }
                    break;
                default:
                    break;
            }
    }

}