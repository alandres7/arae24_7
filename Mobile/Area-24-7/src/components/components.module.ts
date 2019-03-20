import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IonicModule } from 'ionic-angular';

import { MixedTypeaheadComponent } from './mixed-typeahead/mixed-typeahead';
import { LayerComponent } from './layer/layer';
import { LayerManagerComponent } from './layer-manager/layer-manager';
import { MyLocationComponent } from './my-location/my-location';
import { TerritorioComponent } from './territorio/territorio';
import { FichaCaracterizacionComponent } from './ficha-caracterizacion/ficha-caracterizacion';
import { OtherLayerComponent } from './other-layer/other-layer';
import { GeoLayerComponent } from './geo-layer/geo-layer';
import { AvistamientoComponent } from './avistamiento/avistamiento';
import { DecisionTreeComponent } from './decision-tree/decision-tree';
import { AvistamientoDetailComponent } from './avistamiento-detail/avistamiento-detail';
import { StoryListComponent } from './story-list/story-list';
import { StoryDetailComponent } from './story-detail/story-detail';
import { CommentComponent } from './comment/comment';
import { StoryCreateComponent } from './story-create/story-create';
import { AvistamientoCreateComponent } from './avistamiento-create/avistamiento-create';
import { GeoLayerDynamicComponent } from './geo-layer-dynamic/geo-layer-dynamic';
import { GeoLayerStaticComponent } from './geo-layer-static/geo-layer-static';
import { FusionLayerComponent } from './fusion-layer/fusion-layer';
import { BusquedaAvistamientosComponent } from './busqueda-avistamientos/busqueda-avistamientos';
import { PipesModule } from '../pipes/pipes.module';
//import { SubstrPipe } from '../pipes/substr/substr';
import { MiEntornoComponent } from './mi-entorno/mi-entorno';
import { DetalleEstacionComponent } from './detalle-estacion/detalle-estacion';
import { ClimaDetalleComponent } from './clima-detalle/clima-detalle';
import { HuellasComponent } from './huellas/huellas';
import { VigiaComponent } from './vigia/vigia.component';
import { TerritorioDetailComponent } from './territorio-detail/territorio-detail';
import { MapSelectLocationComponent } from './map-select-location/map-select-location';
import { CuidameComponent } from './cuidame/cuidame';
import { CuidameCreateComponent } from './cuidame-create/cuidame-create';
import { DecisionTreeVigiaComponent } from './decision-tree-vigia/decision-tree-vigia';
import { CuidameSelectUbicationComponent } from './cuidame-select-ubication/cuidame-select-ubication';
import { CuidameReportComponent } from './cuidame-report/cuidame-report';
import { CuidameDetailComponent } from './cuidame-detail/cuidame-detail';
import { MidemeOptionsComponent } from './mideme-options/mideme-options';
import { MidemeCalculatorComponent } from './mideme-calculator/mideme-calculator';
import { MidemeResultComponent } from './mideme-result/mideme-result';
import { MidemeChallengeComponent } from './mideme-challenge/mideme-challenge';
import { MidemeCurrentChallengeComponent } from './mideme-current-challenge/mideme-current-challenge';
import { MidemeHistoryComponent } from './mideme-history/mideme-history';
import { MidemeModalSelectChallengeComponent } from './mideme-modal-select-challenge/mideme-modal-select-challenge';
import { MidemeModalPersonalChallengeComponent } from './mideme-modal-personal-challenge/mideme-modal-personal-challenge';
import { MidemeModalCalculationSaveComponent } from './mideme-modal-calculation-save/mideme-modal-calculation-save';
import { MidemeModalChangeChallengeComponent } from './mideme-modal-change-challenge/mideme-modal-change-challenge';
import { MidemeModalCheckChallengeComponent } from './mideme-modal-check-challenge/mideme-modal-check-challenge';
import { DynamicFormComponent } from '../app/shared/utilidades/forms/dynamic-form.component';

//import { ModalCaracterizacionPage } from "../pages/modal-caracterizacion/modal-caracterizacion";
@NgModule({
    declarations: [
        MixedTypeaheadComponent,
        LayerComponent,
        LayerManagerComponent,
        MyLocationComponent,
        DynamicFormComponent,
        TerritorioComponent,
        FichaCaracterizacionComponent,
        OtherLayerComponent,
        GeoLayerComponent,
        AvistamientoComponent,
        DecisionTreeComponent,
        DecisionTreeVigiaComponent,
        CuidameSelectUbicationComponent,
        CuidameReportComponent,
        AvistamientoDetailComponent,
        StoryListComponent,
        StoryDetailComponent,
        CommentComponent,
        StoryCreateComponent,
        AvistamientoCreateComponent,
        GeoLayerDynamicComponent,
        GeoLayerStaticComponent,
        FusionLayerComponent,
        BusquedaAvistamientosComponent,
        MiEntornoComponent,
        DetalleEstacionComponent,
        ClimaDetalleComponent,
        HuellasComponent,
        CuidameCreateComponent,
        VigiaComponent,
        CuidameDetailComponent,
    TerritorioDetailComponent,
    MapSelectLocationComponent,
    CuidameComponent,
    MidemeOptionsComponent,
    MidemeCalculatorComponent,
    MidemeResultComponent,
    MidemeChallengeComponent,
    MidemeCurrentChallengeComponent,
    MidemeHistoryComponent,
    MidemeModalSelectChallengeComponent,
    MidemeModalPersonalChallengeComponent,
    MidemeModalCalculationSaveComponent,
    MidemeModalChangeChallengeComponent,
    MidemeModalCheckChallengeComponent,
    ],
    imports: [CommonModule, IonicModule, PipesModule],
    exports: [
        MixedTypeaheadComponent,
        LayerComponent,
        LayerManagerComponent,
        MyLocationComponent,
        DynamicFormComponent,
        TerritorioComponent,
        FichaCaracterizacionComponent,
        OtherLayerComponent,
        GeoLayerComponent,
        AvistamientoComponent,
        DecisionTreeComponent,
        DecisionTreeVigiaComponent,
        CuidameSelectUbicationComponent,
        CuidameReportComponent,
        AvistamientoDetailComponent,
        StoryListComponent,
        StoryDetailComponent,
        CommentComponent,
        StoryCreateComponent,
        AvistamientoCreateComponent,
        CuidameCreateComponent,
        GeoLayerDynamicComponent,
        GeoLayerStaticComponent,
        FusionLayerComponent,
        BusquedaAvistamientosComponent,
        MiEntornoComponent,
        DetalleEstacionComponent,
        ClimaDetalleComponent,
        HuellasComponent,
        VigiaComponent,
        CuidameDetailComponent,
    TerritorioDetailComponent,
    MapSelectLocationComponent,
    CuidameComponent,
    MidemeOptionsComponent,
    MidemeCalculatorComponent,
    MidemeResultComponent,
    MidemeChallengeComponent,
    MidemeCurrentChallengeComponent,
    MidemeHistoryComponent,
    MidemeModalSelectChallengeComponent,
    MidemeModalPersonalChallengeComponent,
    MidemeModalCalculationSaveComponent,
    MidemeModalChangeChallengeComponent,
    MidemeModalCheckChallengeComponent,
    ],
    entryComponents: [
        FichaCaracterizacionComponent,
        AvistamientoCreateComponent,
        CuidameCreateComponent,
        AvistamientoDetailComponent,
        StoryListComponent,
        StoryDetailComponent,
        CommentComponent,
        StoryCreateComponent,
        DecisionTreeComponent,
        DecisionTreeVigiaComponent,
        CuidameSelectUbicationComponent,
        CuidameReportComponent,
        BusquedaAvistamientosComponent,
        DetalleEstacionComponent,
        ClimaDetalleComponent,
        CuidameDetailComponent,
        MapSelectLocationComponent,
        MidemeOptionsComponent,
        MidemeCalculatorComponent,
        MidemeHistoryComponent,
        MidemeChallengeComponent,
        MidemeResultComponent,
        MidemeCurrentChallengeComponent,
        MidemeModalSelectChallengeComponent,
        MidemeModalPersonalChallengeComponent,
        MidemeModalCalculationSaveComponent,
        MidemeModalChangeChallengeComponent,
        MidemeModalCheckChallengeComponent,
    ],
})
export class ComponentsModule {}
