import {
  Http,
  Response,
  Headers,
  RequestOptions,
  URLSearchParams
} from "@angular/http";
import { Observable } from "rxjs/Rx";
import { Injectable } from "@angular/core";
import "rxjs/add/operator/map";
import "rxjs";
import { RutasCercanasRequest } from "../models/rutasCercanasRequest.model";
import { DisponibilidadEnciclaRequest } from "../models/disponibilidadEnciclaRequest.model";
import { UbicacionFavorita } from "../models/UbicacionFavorita.model";
import { CONFIG_ENV } from "../../shared/config-env-service/const-env";
import { RutasCercanasReponse } from "../models/rutasCercanasReponse.model";

@Injectable()
export class WsMovilidad {
  private headers: Headers;
  private options: RequestOptions;
  private urlApi: string;

  public responseRutasCercanas: RutasCercanasReponse;

  public ubicacionesFavoritas: UbicacionFavorita[];

  constructor(private http: Http) {
    let envQuipux = false;
    if (envQuipux) {
      this.setEnvironment(1);
      CONFIG_ENV.REST_BASE_URL = this.urlApi;
    }
  }

  obtenerRutasCercanas(data:RutasCercanasRequest) {
    this.resetRequestOptions("application/json");
    // console.log("d", data);

    return this.http
      .get(
        CONFIG_ENV.REST_BASE_URL +
          "/rutas/viajes/" +
          data.fecha +
          "/" +
          data.latitudOrigen +
          "/" +
          data.longitudOrigen +
          "/" +
          data.radio +
          "/" +
          data.modosTransporte,
        this.options
      )
      .map((res: Response) => res.json());
  }

  obtenerDisponibilidadEncicla(data: DisponibilidadEnciclaRequest) {
    this.resetRequestOptions("application/json");

    return this.http
      .get(
        CONFIG_ENV.REST_BASE_URL +
          "/rutas/encicla/disponibilidad/" +
          data.idEstacion,
        this.options
      )
      .map((res: Response) => res.json());
  }

  public obtenerRutasyLineas(data: any) {
    this.resetRequestOptions("application/json");

    return this.http
      .get(CONFIG_ENV.REST_BASE_URL + "/rutas/lineas/" + data, this.options)
      .map((res: Response) => res.json());
  }

  public obtenerRutaLineaDetalle(tipo: any, id: any) {
    this.resetRequestOptions("application/json;charset=UTF-8");

    return this.http
      .get(
        CONFIG_ENV.REST_BASE_URL + "/rutas/lineas/" + id + "/" + tipo,
        this.options
      )
      .map((res: Response) => res.json());
  }

  public obtenerRutasyLineasAutocompletado(data: any) {
    this.resetRequestOptions("application/json");

    return this.http
      .get(
        CONFIG_ENV.REST_BASE_URL + "/rutas/autocompletado/" + data,
        this.options
      )
      .map((res: Response) => res.json());
  }

  public crearUbicacionFavorita(data: UbicacionFavorita) {
    let urlSearchParams = new URLSearchParams();
    urlSearchParams.append("nombre", data.nombre);
    urlSearchParams.append("descripcion", data.descripcion);
    urlSearchParams.append("latitud", data.coordenada[1]);
    urlSearchParams.append("longitud", data.coordenada[0]);
    urlSearchParams.append("idUsuario", "" + data.idUsuario);
    urlSearchParams.append("idCategoria", "" + data.idCategoria);
    let body = urlSearchParams.toString();

    this.resetRequestOptions("application/x-www-form-urlencoded");

    return this.http
      .post(CONFIG_ENV.REST_BASE_URL + "/ubicaciones/add", body, this.options)
      .map((res: Response) => res.json());
  }

  public obtenerUbicacionesFavoritas(data: any) {
    this.resetRequestOptions("application/json");

    return this.http
      .get(CONFIG_ENV.REST_BASE_URL + "/ubicaciones/" + data, this.options)
      .map((res: Response) => res.json());
  }

  public eliminarUbicacionFavorita(data: any) {
    this.resetRequestOptions("application/json");

    return this.http
      .delete(
        CONFIG_ENV.REST_BASE_URL + "/ubicaciones/delete/" + data,
        this.options
      )
      .map(this.extractData);
  }

  public actualizarUbicacionFavorita(data: any) {
    this.resetRequestOptions("application/x-www-form-urlencoded");

    let urlSearchParams = new URLSearchParams();
    urlSearchParams.append("id", data.id);
    urlSearchParams.append("nombre", data.nombre);
    urlSearchParams.append("descripcion", data.descripcion);
    urlSearchParams.append("latitud", data.coordenada[1]);
    urlSearchParams.append("longitud", data.coordenada[0]);
    urlSearchParams.append("idUsuario", "" + data.idUsuario);
    urlSearchParams.append("idCategoria", "" + data.idCategoria);
    let body = urlSearchParams.toString();

    return this.http
      .put(CONFIG_ENV.REST_BASE_URL + "/ubicaciones/update", body, this.options)
      .map((res: Response) => res.json());
  }

  obtenerPosiblesViajes(data) {
    debugger; 
    this.resetRequestOptions("application/json;charset=UTF-8");
    return this.http
      .get(CONFIG_ENV.REST_BASE_URL + "/viajes/" + data, this.options)
      .map((res: Response) => res.json());
  }

  obtenerPronosticoSiata(data) {
    this.resetRequestOptions("application/json;charset=UTF-8");
    return this.http
      .get(CONFIG_ENV.REST_BASE_URL + "/pronostico/" + data, this.options)
      .map((res: Response) => res.json());
  }

  public obtenerHuellaCarbno(data: any) {
    this.resetRequestOptions("application/json");
    return this.http
      .post(
        CONFIG_ENV.REST_BASE_URL + "/api/huellas/carbono/emision",
        data,
        this.options
      )
      .map((res: Response) => res.json());
  }


  obtenerHuellaCarbonoPronostico(dataHuella,dataPronositco) {
    return Observable.forkJoin(
      this.obtenerHuellaCarbno(dataHuella),
      this.obtenerPronosticoSiata(dataPronositco)
    );
  }

  obtenerCategoriasUbcacionFavorita() {
    let body={};
    this.resetRequestOptions('application/json');
    return this.http.post(
      CONFIG_ENV.REST_BASE_URL + '/ubicaciones/categorias',
      body,
      this.options
    ).map((res: Response) => res.json());
  }



  removerUbicacionFavorita(idUbicacionFavorita: number) {
    this.ubicacionesFavoritas.forEach((item, index) => {
      if (item.id === idUbicacionFavorita)
        this.ubicacionesFavoritas.splice(index, 1);
    });
  }

  private resetRequestOptions(contentType: string): void {
    this.headers = new Headers();
    console.log("Bearer", localStorage.getItem("bearer"));

    this.headers.append("Content-Type", contentType);
    this.headers.append("Authorization", localStorage.getItem("bearer"));
    this.options = new RequestOptions({ headers: this.headers });
  }

  private extractData(res: Response) {
    return res.text() ? res.json() : {};
  }

  private setEnvironment(env: number) {
    switch (env) {
      case 1: //Ambiente desarrollo publico
        this.urlApi = "http://apptest.quipux.com/amva";
        console.log("Enviroment desarrollo");
        break;

      case 2: //Ambiente desarrollo local
        //this.urlApi = 'http://10.125.30.27:9090';
        this.urlApi = "/api";
        console.log("Enviroment desarrollo");
        break;

      case 3: //Ambiente local
        this.urlApi = "http://localhost:9090";
        console.log("Enviroment local");
        break;

      case 4: //Ambiente desarrollo ada
        this.urlApi = "http://201.184.243.195:9095";
        console.log("Enviroment desarrollo ada");
        break;
    }
  }
}
