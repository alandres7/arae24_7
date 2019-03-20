import { Component, OnInit } from "@angular/core";
import { NavController, NavParams } from "ionic-angular";
import { LayerProvider } from "../../providers/layer/layer";
import { Common } from "../../app/shared/utilidades/common";
@Component({
  selector: "ficha-caracterizacion",
  templateUrl: "ficha-caracterizacion.html"
})
export class FichaCaracterizacionComponent implements OnInit {
  private characterizationCard = {};
  private currentApp;
  private isFullCharacterizationCard: boolean;

  datos = {
    municipio: "Medellin",
    capas: [
      {
        nombre: "Capa 1",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 3",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      },
      {
        nombre: "Capa 2",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      },
      {
        nombre: "Capa 2",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      },
      {
        nombre: "Capa 2",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      },
      {
        nombre: "Capa 2",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      },
      {
        nombre: "Capa 2",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      },
      {
        nombre: "Capa 2",
        icono: "http://172.16.0.20:9095/api/icono/167",
        categorias: [
          {
            nombre: "Categoria 1",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          },
          {
            nombre: "Categoria 2",
            icono: "http://172.16.0.20:9095/api/icono/167",
            nombreMarcador: "Nombre del marcador"
          }
        ]
      }
    ]
  };

  constructor(private navParams: NavParams
            , private layerProvider: LayerProvider
            , private common: Common) {}

  ngOnInit(): void {
    this.characterizationCard = this.navParams.get("characterizationCard");
    this.layerProvider.currentApp.subscribe(app => {
      this.currentApp = app;
    })
  }

  closeModal(): void {
    this.common.dismissModal();
  }
}
