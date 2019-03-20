import { WsOrdenamiento } from './provider/wsOrdenamiento';
import { BuscadorComponent } from './components/buscador/buscador';
import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ItemComponent } from './components/item/item';
import { Ordenamiento } from './pages/ordenamiento-page';
import { ListaObjetosMapaComponent } from './components/lista/lista-objetos-mapa';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    declarations: [BuscadorComponent,
        ItemComponent,
        Ordenamiento,
        ListaObjetosMapaComponent],
    imports: [IonicPageModule.forChild(BuscadorComponent),
    IonicPageModule.forChild(ItemComponent),
    IonicPageModule.forChild(ListaObjetosMapaComponent)],
    entryComponents: [Ordenamiento],
    exports: [Ordenamiento],
    providers: [WsOrdenamiento]
})

export class OrdenamientoModule {

}