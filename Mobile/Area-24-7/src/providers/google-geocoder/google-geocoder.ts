import { Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';

import { CONFIG_ENV } from '../../app/shared/config-env-service/const-env';

import { Observable } from "rxjs/Observable";
import { Suggestion } from '../../entities/suggestion';
import { analyzeNgModules } from '@angular/compiler';
declare var google: any;

interface DataFromPlacesAPI {
    predictions: [
        {
            description: string,
            place_id: string
        }
    ]
}

interface DataFromGeocodingAPI {
    results: [
        {
            place_id: string,
            geometry: {
                location: {
                    lat: number,
                    lng: number
                }
            }
        }
    ]
}

@Injectable()
export class GoogleGeocoderProvider {
   /* private autocompleteService = new google.maps.places.AutocompleteService();
    private amvaBounds = new google.maps.LatLngBounds(
          new google.maps.LatLng(6.075967, -75.633433)
        , new google.maps.LatLng(6.450092, -75.323971)
    );
    private componentRestrictions: google.maps.places.ComponentRestrictions = {
        country: 'CO'
    }*/
    private static amvaCenterLat: number = 6.273357;
    private static amvaCenterLng: number = -75.46679;
    private static amvaRadius: number = 40000;

    constructor(private http: Http) {}

    autocompletionRequest(query: string): google.maps.places.AutocompletionRequest {
        let componentRestrictions: google.maps.places.ComponentRestrictions = {
            country: 'co'
        };
        return {
              input: query
            , location: new google.maps.LatLng(GoogleGeocoderProvider.amvaCenterLat, GoogleGeocoderProvider.amvaCenterLng)
            , radius: GoogleGeocoderProvider.amvaRadius
            , offset: 0
            , componentRestrictions: componentRestrictions
        };
    } 

    getDetails(placeId: string): Observable<Response> {
        let url = 'https://maps.googleapis.com/maps/api/place/details/json'
                + '?place_id=' + placeId
                + '&key=' + CONFIG_ENV.GOOGLE_MAPS_KEY;
        console.log(GoogleGeocoderProvider.name + '');
        return this.http.get(url);
    }
    
    geocodeAddress(address: string): Observable<Response> {
        let url = 'https://maps.googleapis.com/maps/api/geocode/json'
                + '?address=' + encodeURIComponent(address)
                + '&components=country:co'
                + '&language=es'    
                + '&region=co'            
                + '&key=' + CONFIG_ENV.GOOGLE_MAPS_KEY;
        return this.http.get(url);
    }



}

