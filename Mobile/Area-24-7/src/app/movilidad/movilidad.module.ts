import { MenuModalComponent } from './../../components/menu-modal/menu-modal';
import { LocationProvider } from './providers/location';
import { ConsultaViaje } from './pages/consulta-viajes/consulta-viajes.page';
import { MenuOpcionesComponent } from './components/menu-opciones/menu-opciones.component';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpModule } from '@angular/http';
import { IonicModule } from 'ionic-angular';

import { BuscarUbicacionComponent } from './components/buscar-ubicacion/buscar-ubicacion.component';
import { ChekboxModosTransportesComponent } from './components/chekbox-modos-trasnportes/chekbox-modos-trasnportes.component';
import { AutocompletadoLineasRutasComponent } from './components/autocompletado-lineas-rutas/autocompletado-lineas-rutas.component';
import { BusquedaViajesComponent } from './components/busqueda-viajes/busqueda-viajes.component';
import { DetalleRutaComponent } from './components/detalle-ruta/detalle-ruta.component';
import { LineasYRutasComponent } from './components/lineas-y-rutas/lineas-y-rutas.component';
import { MenuFavoritoPopoverComponent } from './components/menu-favorito-popover/menu-favorito-popover.component';
import { RutasCercanasComponent } from './components/rutas-cercanas/rutas-cercanas.component';
import { RutasPopoverComponent } from './components/rutas-popover/rutas-popover.component';
import { ViajesSugeridosComponent } from './components/viajes-sugeridos/viajes-sugeridos.component';
import { ModoTransportePage } from './pages/modo-transporte/modo-transporte.page';
import { MovilidadPage } from './pages/movilidad-page/movilidad-page';
import { PreferenciasMovilidadPage } from './pages/preferencias-movilidad/preferencias-movilidad.page';
import { RadioAccionPage } from './pages/radio-accion/radio-accion.page';
import { VistaViajesPage } from "./pages/vista-viajes/vista-viajes.page";
import { UbicacionFavoritaPage } from "./pages/ubicacion-favorita/ubicacion-favorita.page";
import { DetalleViajePage } from "./pages/detalle-viaje/detalle-viaje.page";
import { SeleccionarMapaPage } from "./pages/seleccionar-mapa/seleccionar-mapa.page";
import { MenuRutasPage } from "./pages/menu-rutas/menu-rutas.page";
import { LineasYRutasMapaPage } from "./pages/lineas-y-rutas-mapa/lineas-y-rutas-mapa.page";
import { EstablecerUbicacionPage } from "./pages/establecer-ubicacion/establecer-ubicacion.page";

import { PuntosProvider } from './providers/PuntosProvider/puntos-provider';
import { FavoritosProvider } from "./providers/favoritos/favoritos"
import { CoreModule } from "../core/core.module";
import { DeshabilitarSearchbarDirective } from "./directives/deshabilitar-searchbar.directive";
import { MapaDirective, ContenidoMapaDirective } from "./directives/mapa.directive";



import { WsMovilidad } from './providers/wsMovilidad';
import { GmapsMovilidad } from './providers/gmapsMovilidad';
import { DatePipe, DecimalPipe } from '@angular/common';
import { LineasYRutasDetalleComponent } from './components/lineas-rutas-detalle/lineas-rutas-detalle.component';
import { DetalleRutasCercanasComponent } from './components/detalle-rutas-cercanas/detalle-rutas-cercanas.component';

@NgModule({
  declarations: [
    BuscarUbicacionComponent,
    AutocompletadoLineasRutasComponent,
    ChekboxModosTransportesComponent,
    EstablecerUbicacionPage,
    BusquedaViajesComponent,
    MenuOpcionesComponent,
    DetalleRutaComponent,
    LineasYRutasComponent,
    ConsultaViaje,
    MenuFavoritoPopoverComponent,
    RutasCercanasComponent,
    RutasPopoverComponent,
    ViajesSugeridosComponent,
    VistaViajesPage,
    UbicacionFavoritaPage,
    DetalleViajePage,
    SeleccionarMapaPage,
    ModoTransportePage,
    MovilidadPage,
    PreferenciasMovilidadPage,
    RadioAccionPage,
    MenuRutasPage,
    LineasYRutasMapaPage,
    LineasYRutasDetalleComponent,
    DetalleRutasCercanasComponent,
    DeshabilitarSearchbarDirective,
    MapaDirective,
    ContenidoMapaDirective,
    MenuModalComponent
  ],
  imports: [IonicModule, HttpModule, CoreModule],
  exports: [BusquedaViajesComponent,AutocompletadoLineasRutasComponent,ChekboxModosTransportesComponent, MovilidadPage, DetalleRutaComponent],
  entryComponents: [
    BuscarUbicacionComponent,
    AutocompletadoLineasRutasComponent,
    ChekboxModosTransportesComponent,
    EstablecerUbicacionPage,
    BusquedaViajesComponent,
    DetalleRutaComponent,
    LineasYRutasComponent,
    MenuFavoritoPopoverComponent,
    RutasCercanasComponent,
    RutasPopoverComponent,
    ViajesSugeridosComponent,
    VistaViajesPage,
    UbicacionFavoritaPage,
    DetalleViajePage,
    SeleccionarMapaPage,
    ModoTransportePage,
    MovilidadPage,
    PreferenciasMovilidadPage,
    RadioAccionPage,
    MenuRutasPage,
    LineasYRutasMapaPage,
    LineasYRutasDetalleComponent,
    ConsultaViaje,
    MenuModalComponent,
    DetalleRutasCercanasComponent
  ],
  schemas: [
    NO_ERRORS_SCHEMA // IMPORTANT: need that for AoT compilation
  ],
  providers: [
    PuntosProvider,
    FavoritosProvider,
    WsMovilidad,
    GmapsMovilidad,
    DatePipe,
    DecimalPipe,
    LocationProvider
  ]

})
export class MovilidadModule { }
