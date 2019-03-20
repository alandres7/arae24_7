import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Response } from '@angular/http';

import { GoogleGeocoderProvider } from "../../providers/google-geocoder/google-geocoder";
import { LayerProvider } from '../../providers/layer/layer';
import { Common } from '../../app/shared/utilidades/common';
import { GoogleMaps } from '../../app/shared/utilidades/googleMaps';

import { Suggestion } from '../../entities/suggestion';
import { MessageProvider } from '../../providers/message/message';

@Component({
    selector: 'mixed-typeahead',
    templateUrl: 'mixed-typeahead.html'
})
export class MixedTypeaheadComponent {

    @Input()
    private color: string; 

    @Output()
    private clickGoogleSuggestion = new EventEmitter<{ lat: number, lng: number }>();

    @Output()
    private clickApiSuggestion = new EventEmitter<number>();

    static readonly OFFSET_PREDICTION: number = 3;
    static readonly OFFSET_ADDRESS: number = 5;
    private static amvaCenterLat: number = 6.273357;
    private static amvaCenterLng: number = -75.46679;
    private static amvaRadius: number = 40000;

    private googlePlacesSuggestions: Suggestion[];
    private suggestions: Suggestion[];
    private inputValue: string = "";
    private autocompleteService: google.maps.places.AutocompleteService;

    constructor(private layerProvider: LayerProvider
              , private googleGeocoderProvider: GoogleGeocoderProvider
              , private common: Common
              , private messageProvider: MessageProvider) 
    {
        this.autocompleteService = new google.maps.places.AutocompleteService();
    }

    onInputSearchChange(event: any): void {
        console.log('onInputSearchChange ' + this.inputValue);

        if (this.inputValue.length == 0) {
            this.inputValue = "";
            this.suggestions = [];
            this.googlePlacesSuggestions = [];
        }
        if (this.inputValue.length < MixedTypeaheadComponent.OFFSET_PREDICTION) return;

        if (this.common.activeLayers.level 
            && this.common.activeLayers.level != ''
            && this.common.activeLayers.ids 
            && this.common.activeLayers.ids.length > 0) 
        {
            this.layerProvider.getSuggestions(this.common.activeLayers.level, this.common.activeLayers.ids.toString(), this.inputValue).subscribe(
                (response: Response) => {
                    console.log(MixedTypeaheadComponent.name + ' onInputSearchChange getSuggestions ' + JSON.stringify(response));
                    this.suggestions = Suggestion.parse(response.json()).slice(0, 3);
                },
                (error: any) => console.log(MixedTypeaheadComponent.name + ' onInputSearchChange getSuggestions error' + JSON.stringify(error))
            );        
        }

        this.getGoogleSuggestions(this.inputValue);

      /*  this.googleGeocoderProvider.getSuggestions(this.inputValue).subscribe(
            (response: Response) => {
                console.log('googleGeocoderProvider getSuggestions ' + JSON.stringify(response.json()));
                this.googlePlacesSuggestions = Suggestion.parseFromGoogleAutocomplete(response.json()).slice(0, 3);
            },
            (error: any) => console.log('googleGeocoderProvider getSuggestions error ' + JSON.stringify(error))
        );*/
    }   

    onEnter(): void {
        console.log('onEnter ' + this.inputValue);

        if (this.inputValue.length < MixedTypeaheadComponent.OFFSET_ADDRESS) return;

        this.googleGeocoderProvider.geocodeAddress(this.inputValue).subscribe(
            (response: Response) => {
                let jsonResponse = response.json();
                
                console.log('googleGeocoderProvider geocodeAddress: ' + JSON.stringify(jsonResponse));
                if (jsonResponse.status == 'OK') {
                    let latLng = jsonResponse.results[0].geometry.location;
                    this.clickGoogleSuggestion.emit(latLng);
                    this.inputValue = "";
                    this.suggestions = [];
                    this.googlePlacesSuggestions = [];
                }
                else {
                    console.log(MixedTypeaheadComponent.name + ' onEnter ' + JSON.stringify(jsonResponse));
                    this.messageProvider.getByNombreIdentificador('Login->google').subscribe(
                        response => {
                            console.log(MixedTypeaheadComponent.name + ' onEnter getByNombreIdentificador ' + JSON.stringify(response));
                            let msg = response.json();
                            this.common.presentAcceptAlert(msg.titulo, msg.descripcion);
                        },
                        error => console.log(MixedTypeaheadComponent.name + ' onEnter getByNombreIdentificador error ' + JSON.stringify(error))
                    );
                }
            },
            (error: any) => console.log('googleGeocoderProvider geocodeAddress error ' + JSON.stringify(error))
        );
    }

    onBlurInputSearch(): void {
        console.log('onBlurInputSearch');
        this.suggestions = [];
        this.googlePlacesSuggestions = [];
    }

    onFocusInputSearch(): void { 
        console.log('onFocusInputSearch');
        this.onInputSearchChange(null);
    }

    onMouseDownGooglePlacesSuggestion(id: string): void {
        this.googleGeocoderProvider.getDetails(id).subscribe(
            (response: Response) => {
                console.log('onMouseDownGooglePlacesSuggestion ' + JSON.stringify(response.json()));
                let latLng = response.json().result.geometry.location;
                this.clickGoogleSuggestion.emit(latLng);
                this.inputValue = "";
                this.suggestions = [];
                this.googlePlacesSuggestions = [];
            },
            (error: any) => console.log('onClickGooglePlacesSuggestion error ' + JSON.stringify(error))   
        );
    }

    onMouseDownApiSuggestion(id: number): void {
        this.clickApiSuggestion.emit(id);
        this.inputValue = "";
        this.suggestions = [];
        this.googlePlacesSuggestions = [];
    }

    autocompletionRequest(query: string): google.maps.places.AutocompletionRequest {
        let componentRestrictions: google.maps.places.ComponentRestrictions = {
            country: 'co'
        };
        return {
              input: query
            , location: new google.maps.LatLng(MixedTypeaheadComponent.amvaCenterLat, MixedTypeaheadComponent.amvaCenterLng)
            , radius: MixedTypeaheadComponent.amvaRadius
            , offset: 0
            , componentRestrictions: componentRestrictions
        };
    } 

    getGoogleSuggestions(query: string) {
        let autocompletionRequest = this.autocompletionRequest(query);
        this.autocompleteService.getPlacePredictions(autocompletionRequest, (autocompletePredictions) => {
            console.log('googleGeocoderProvider getSuggestions ' + JSON.stringify(autocompletePredictions));
            this.googlePlacesSuggestions = Suggestion.parseFromGoogleAutocomplete(autocompletePredictions).slice(0, 3);
        });
    }
}
