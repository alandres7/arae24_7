import { CONFIG_ENV } from "./../../shared/config-env-service/const-env";
import { Common } from "./../../shared/utilidades/common";
import { NativeStorage } from "@ionic-native/native-storage";

import { WebService } from "../../provider/webService/ws";
import { Observable } from "rxjs/Observable";
import { Injectable } from "@angular/core";
import { MenuOptionModel } from "../model/menu-model";

/**
 * autor Milker Sanchez
 */
@Injectable()
export class menuDinamico {
  private collectionMenuPreferencias: Array<MenuOptionModel>;
  //private collectionMenu: Array<MenuOptionModel>;
  public observableMenu: Observable<any>;
  public header: any;
  public menuPreferencias: any;
  public menuWeb: any;
  private usuario: any;
  public listaMenu: Array<any>;

  constructor(
    private ws: WebService,
    private nativeStorage: NativeStorage,
    private utilidad: Common
  ) {}

  /**
   * cargarMenuWeb
   */
  public cargarMenuWeb(endPoint: any): any {
    this.header = {
      "Content-Type": "application/json"
    };

    return new Promise((resolve, reject) => {
      this.ws.url = CONFIG_ENV.REST_BASE_URL + endPoint;
      this.ws
        .wsGet(this.header, "")
        //.map(data => Array<MenuOptionModel>())
        .subscribe(
          pArray => {
            resolve(pArray);
            console.log(pArray);
          },
          err => {
            console.log("error al cargar la aplicacion", err);
            //this.utilidad.generalAlert('Info', 'Se ha perdido conexión con servidor, intentelo más tarde!');
            reject(err);
          }
        );
    });
  }

  /*public cargarMenuWeb(token: string, data: any): any {
        this.header = {
            'Content-Type': 'application/json',
            'Autorization': token
        };

        return new Promise((resolve, reject) => {
            this.observableMenu.subscribe(
                () => {
                    this.ws.wsGet(this.header, data)
                        .map(data => Array<MenuOptionModel>())
                        .subscribe((pArray) => resolve(pArray));
                },
                (err) => {
                    console.log('error al cargar la aplicacion', err);
                    reject(err);
                }
            );
        });
    }*/
/*
  public cargarPreferenciasMenu(token, url: any) {
    this.header = {
      "Content-Type": "application/json",
      Authorization: token
    };
    this.usuario = JSON.parse(localStorage.getItem("Usuario"));
    this.menuPreferencias = this.usuario.menuPreferencias;

    if (
      this.menuPreferencias.equals(null) ||
      this.menuPreferencias.equals(" ")
    ) {
    } else {
    }
  }

  public actualizarMenu(token: string, url: any, data: any): any {
    this.header = {
      "Content-Type": "application/json",
      Authorization: token
    };
    this.ws.url = CONFIG_ENV.REST_BASE_URL + url;
    this.ws
      .wsPut(this.header, data)
      // .map(data => Array<MenuOptionModel>())
      .subscribe(
        response => {},
        err => {
          console.log("error al cargar la aplicacion", err);
          this.utilidad.generalAlert(
            "Info",
            "Se ha perdido conexión con servidor, intentelo más tarde!"
          );
          //reject(err);
        }
      );
  }

  public obtenerBearer(): string {
    var bearer: string;
    this.nativeStorage
      .getItem("bearer")
      .then(
        resultado => {
          bearer = resultado;
        },
        () => {
          bearer = null;
        }
      )
      .catch(error => console.log("Error buscando registro " + error));
    return bearer;
  }

  public obtenerBearerPrueba(): string {
    var bearer: string;
    bearer = localStorage.getItem("bearer");
    return bearer;
  }

  public modelDinamico(arreglo1: Array<any>) {
    arreglo1.forEach(element => {
      this.utilidad.menuPreferencias[element.id] = {};
      this.utilidad.menuPreferencias[element.id][element.id] = element.activo;
      if (element.capas.length > 0) {
        this.utilidad.menuPreferencias[element.id]["capas"] = {};
        var aCapas = <Array<any>>element.capas;
        aCapas.forEach(item => {
          this.utilidad.menuPreferencias[element.id]["capas"][item.id] =
            item.activo;
        });
      }
    });
  }

  public compararOpcionesMenu(arreglo1: Array<any>, arreglo2: Array<any>) {
    if (arreglo1 == undefined || arreglo2.length == undefined) {
      return;
    }
    arreglo1.forEach((element, index) => {
      if (index > -1) {
        if (!element.activo) {
          this.utilidad.menuPreferencias[element.id][element.id] =
            element.activo;
          if (element.capas.length > 0 && arreglo2[index].capas.length > 0) {
            element.capas.forEach(i => {
              this.utilidad.menuPreferencias[element.id]["capas"][i.id] =
                i.activo;
            });
          }
        } else {
          this.utilidad.menuPreferencias[element.id][element.id] =
            arreglo2[index].activo;
          //arreglo2[index].capas = <Array<any>>JSON.parse(arreglo2[index].capas);
          if (element.capas.length > 0 && arreglo2[index].capas.length > 0) {
            try {
              element.capas.forEach((i, posicion) => {
                this.utilidad.menuPreferencias[element.id]["capas"][i.id] =
                  arreglo2[index].capas[posicion].activo;
              });
            } catch (e) {
              console.log("error capas menu", e);
            }
            //var itemArrego2: Array<any> = arreglo2[index].capas;
          }
        }
      }
    });
  }

  guardarPreferenciasUsuario(token, url) {
    var pusuario: any = JSON.parse(localStorage.getItem("usuario"));
    let objPreferencias = JSON.parse(pusuario.preferencias);

    objPreferencias.preferencias.forEach((item, index) => {
      item.activo = this.utilidad.menuPreferencias[item.id][item.id];
      if (item.capas.length > 0) {
        item.capas.forEach((i, posicion) => {
          i.activo = this.utilidad.menuPreferencias[item.id]["capas"][i.id];
        });
      }
    });

    var pusuarioWeb: any = JSON.parse(localStorage.getItem("usuarioWeb"));
    //if (objPreferencias.preferencias != JSON.parse(pusuario.preferencias)) {
    pusuario.preferencias = JSON.stringify(objPreferencias);
    localStorage.setItem("usuario", JSON.stringify(pusuario));
    pusuarioWeb.preferencias = JSON.stringify(objPreferencias);
    this.actualizarMenu(token, url, pusuarioWeb);
    //}
  }
*/
  /*cambiarFavorito(idItem, idApp) {
        
        var pusuario: any = JSON.parse(localStorage.getItem("usuario"));
        let objPreferencias = JSON.parse(pusuario.preferencias);
        objPreferencias.preferencias.forEach((data) => {
            if (data.id == idApp && data.capas.length > 0) {
                data.capas.forEach((item, posicion) => {
                    if (item.id == idItem) {
                        if (item.favorito) {
                            item.favorito = false;
                        }
                        else {
                            item.favorito = true;
                        }
                    }
                })
            }
        });

        //this.listaMenu = objPreferencias;
        pusuario.preferencias = objPreferencias;
        //localStorage.setItem("usuario", JSON.stringify(pusuario));
    }*/
}
