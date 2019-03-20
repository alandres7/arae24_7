import { Injectable } from '@angular/core';

@Injectable()
export class PuntosProvider {

  private posicionInicial;
  private posicionFinal;

  constructor() {
    this.init;
  }

  init() {
    this.posicionInicial = {};
    this.posicionFinal = {};
  }

  guardarPosicion(lati: any, lon: any) {
    this.posicionInicial = {
      lat: lati,
      lng: lon
    };
    console.log(lati, lon);
    this.posicionInicial = { lat: lati, lng: lon };
  }

  guardarPosicionDestino(lati: any, lon: any) {
    this.posicionInicial = {
      lat: lati,
      lng: lon
    };
    console.log(lati, lon);
    this.posicionFinal = { lat: lati, lng: lon };
  }
  getPosicionFinal() {
    return this.posicionFinal;
  }
  getPosicionInicial() {
    return this.posicionInicial;
  }

  get() {
    return this.posicionInicial;
  }

}
