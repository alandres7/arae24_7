import { LocationChangeProvider } from './../../../providers/location-change/location-change';
import { LocationUpdateProvider } from './../../../providers/location-update/location-update';
import { Injectable, OnInit, OnDestroy } from '@angular/core';
import { Geolocation, GeolocationOptions, Geoposition, Coordinates } from '@ionic-native/geolocation';
import { Platform } from 'ionic-angular';
import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';

interface SubjectSettings {
      subject: Subject<any>
    , changeDistance: number
    , changePeriod: number
};

@Injectable()
export class LocationProvider {

    private currentGeoposition: Geoposition = {
        coords: {
            accuracy: -1,
            altitude: -1,
            altitudeAccuracy: -1,
            heading: -1,
            latitude: 6.255389,
            longitude: -75.5855671,
            speed: -1
        },
        timestamp: -1
    };

    private currentLocation: { lat: number, lng: number };
    private lastUpdateTimestamp: number;
    private locationSubscription: Subscription;
    private subjectsSettings: SubjectSettings[] = [];

    locationUpdateSubscription: Subscription
    private firstLocationCenterMap: boolean = false;
    private LOCATION_UPDATES_INTERVAL: number = 500;
    private DISTANCE_TOLERANCE: number = 5;

    constructor(private platform: Platform
                , private geolocation: Geolocation
                , private locationChange: LocationChangeProvider
                , private locationUpdate: LocationUpdateProvider)
    {   
        this.turnOnLocationUpdates();
    }

    getGeoposition(): Geoposition { 
        return this.currentGeoposition
    }

    wathLocation(changeDistance: number, changePeriod: number): Observable<{ lat: number, lng: number}> {
        let subject: Subject<{ lat: number, lng: number }> = new Subject<{ lat: number, lng: number }>();
        let subjectSettings: SubjectSettings = {
              subject: subject
            , changeDistance: changeDistance
            , changePeriod: changePeriod
        };
        this.subjectsSettings.push(subjectSettings);
        return subject.asObservable();
    }

    public getPosition(){
        let options = {
            timeout:30000,
            enableHighAccuracy:false
          };
        return this.geolocation.getCurrentPosition(options)
    }


    turnOnLocationUpdates(){
        if (this.locationUpdateSubscription) return;
        this.locationUpdateSubscription = this.locationUpdate
            .getObservable(this.DISTANCE_TOLERANCE, this.LOCATION_UPDATES_INTERVAL)
            .subscribe(
                (latLng: { lat: number, lng: number }): void => {
                    // debugger;
                    if (!this.firstLocationCenterMap) {
                        this.firstLocationCenterMap = true;
                    }
                }
        );
        console.log('turnOnLocationUpdates ' + this.locationUpdateSubscription.closed);
    }


    public getMyLocation(): Geoposition {
        let geoposition: Geoposition = this.locationChange.getGeoposition();
        this.turnOnLocationUpdates();

        return geoposition;
    }

    private emitLocationChange(latLng: { lat: number, lng: number }, newTimestamp: number): void {
        let distance: number;
        let timeElapsed: number;
        if (this.currentLocation && this.lastUpdateTimestamp) {
            let currentLocation = new google.maps.LatLng(this.currentLocation.lat, this.currentLocation.lng);
            let newLocation = new google.maps.LatLng(latLng.lat, latLng.lng);
            distance = google.maps.geometry.spherical.computeDistanceBetween(currentLocation, newLocation);
            timeElapsed = newTimestamp - this.lastUpdateTimestamp;
        }
        else {
            distance = Number.MAX_VALUE;
            timeElapsed = Number.MAX_VALUE;
        }

        this.subjectsSettings.forEach((subjectSettings: SubjectSettings) => {
            if     (distance >= subjectSettings.changeDistance
                || timeElapsed >= subjectSettings.changePeriod) {
                    subjectSettings.subject.next(latLng);
                }
        });
        this.currentLocation = latLng;
        this.lastUpdateTimestamp = newTimestamp;
    }
    
}
