
import { Common } from './../shared/utilidades/common';
import { GoogleMaps } from './../shared/utilidades/googleMaps';
import { NgModule, ModuleWithProviders } from '@angular/core';

@NgModule({})
export class SharedServicesModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedServicesModule,
            providers: [ GoogleMaps
            ]
        };
    }

}