import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';

import { InicioPage } from "../../app/inicio/pages/inicio-page/inicio-page";
import { AvistamientoProvider } from '../../providers/avistamiento/avistamiento';
import { Municipality } from '../../entities/municipality';
import { TerritorioProvider } from '../../providers/territorio/territorio';
import { AbstractControl } from '@angular/forms/src/model';
import { ValidatorFn } from '@angular/forms/src/directives/validators';
import { Subject } from 'rxjs/Subject';
import { Common } from '../../app/shared/utilidades/common';

@Component({
    selector: 'busqueda-avistamientos',
    templateUrl: 'busqueda-avistamientos.html'
})
export class BusquedaAvistamientosComponent implements OnInit {

    @Input()
    private location: { lat: number, lng: number };
    
    private static municipalityCentroidChange = new Subject<{ lat: number, lng: number }>();

    static municipalityCentroidChange$ = BusquedaAvistamientosComponent.municipalityCentroidChange.asObservable();

    static readonly NAME_QUERY_OFFSET: number = 3;

    private formGroup: FormGroup;
    private avistamientoLayers: { id: number, name: string }[];    
    private municipalities: Municipality[];

    constructor(private formBuilder: FormBuilder
              , private territorioProvider: TerritorioProvider
              , private common: Common
              , private avistamientoProvider: AvistamientoProvider) 
    {
        this.formGroup = formBuilder.group({
            nameFormCtrl: ['', Validators.compose([])],
            municipalityFormCtrl: [null, Validators.compose([])]
            }, { 
            validator: this.eitherFieldValidator() 
        });
    }

    ngOnInit(): void {
        this.avistamientoProvider.getAvistamientoLayers().subscribe(
            (avistamientoLayers: { id: number, name: string }[]): void => {
                console.log(BusquedaAvistamientosComponent.name + ' getAvistamientoLayers ' + JSON.stringify(avistamientoLayers));
                this.avistamientoLayers = avistamientoLayers;
            },
            (error: any): void => console.log(BusquedaAvistamientosComponent.name + ' getAvistamientoLayers error ' + JSON.stringify(error)))
   
        this.municipalities = InicioPage.municipalities;
    }

    eitherFieldValidator(): ValidatorFn {
        return (group: FormGroup): { [ key: string ]: any } | null => {
            let nameFormCtrl: FormControl = <FormControl> group.get('nameFormCtrl');
            let municipalityFormCtrl: FormControl = <FormControl> group.get('municipalityFormCtrl');
            let valid : boolean = (nameFormCtrl.value
                && nameFormCtrl.value.length >= BusquedaAvistamientosComponent.NAME_QUERY_OFFSET)
                || municipalityFormCtrl.value;
            return (valid) 
                ? null
                : { 'formGroup': { value: group.value }};
        }
    }

    search(): void {
        if (!this.formGroup.valid) return;

        let municipality: Municipality = this.formGroup.get('municipalityFormCtrl').value;
        if (municipality) {
            // BusquedaAvistamientosComponent.municipalityCentroidChange.next({ lat: municipality.centroidLat, lng: municipality.centroidLng });
            this.common.dismissModal({ lat: municipality.centroidLat, lng: municipality.centroidLng });
        }
        else {
            let nameQuery: string = this.formGroup.get('nameFormCtrl').value;
            this.common.dismissModal({ name: nameQuery });            
        }
    }

    onHideSearchView(): void {
        this.common.dismissModal();
    }
}
