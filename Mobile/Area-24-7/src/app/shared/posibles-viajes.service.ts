import { TransportPreferences } from './../../entities/preferences/transport-preferences';
import { PreferencesProvider } from './../../providers/preferences/preferences';
import { Injectable } from "@angular/core";
import { WsMovilidad } from "../movilidad/providers/wsMovilidad";
import { Common } from "./utilidades/common";
import { VistaViajesPage } from "../movilidad/pages/vista-viajes/vista-viajes.page";
import { App } from "ionic-angular";
import { SideMenu } from "../menu/page/side-menu";


@Injectable()
export class PosiblesViajesProvider {
  public origen = {
    lat: 0,
    lon: 0,
    descripcion: ""
  };

  public destino = {
    lat: 0,
    lon: 0,
    descripcion: ""
  };
  private preferenciasTransportes: any[];
  
  constructor(
    public wsMovilidad: WsMovilidad,
    public common: Common,
    private preferencesProvider:PreferencesProvider,
    protected app: App
  ) {
    this.preferencesProvider.transportsPreferences$.subscribe(
      (transportsPreferences: TransportPreferences[]) => {
          this.preferenciasTransportes = transportsPreferences;
      }
    );


  }
  //setear posiciones
  cambiarPosiciones(origen1, destino1): void {
    this.origen.lat = origen1.latitud;
    this.origen.lon = origen1.longitud;
    this.origen.descripcion = origen1.descripcion;

    this.destino.lat = destino1.latitud;
    this.destino.lon = destino1.longitud;
    this.destino.descripcion = destino1.descripcion;
  }

  obtenerviajesSugeridos(modoTransporte?: string) {
    if(this.origen.lon == this.destino.lon && this.origen.lat == this.destino.lat){
      return new Promise((resolve, reject)=>{
        reject('El destino debe ser diferente al origen')
      })

    }
    return new Promise((resolve, reject) => {
      // debugger
      let modosTransportes = "";
      this.common.presentLoading();
      if (modoTransporte) {
        modosTransportes = modoTransporte;
      } else {
        modosTransportes = this.common.obtenerModosTransportesActivos(this.preferenciasTransportes);
      }

      let data =
        this.origen.lon +
        "/" +
        this.origen.lat +
        "/" +
        this.destino.lon +
        "/" +
        this.destino.lat +
        "/" +
        modosTransportes;

      this.wsMovilidad.obtenerPosiblesViajes(data).subscribe(
        succes => {
          this.common.dismissLoading();
          console.log("ObtenerPosiblesViajesViajesSugeridosSucces", succes);
          if (succes.codigo == 200 || succes.codigo == 303) {
            let lat_origen = this.origen.lat;
            let lon_origen = this.origen.lon;
            let lat_destino = this.destino.lat;
            let lon_destino = this.destino.lon;
            let ori_desc = this.origen.descripcion;
            let des_desc = this.destino.descripcion;

            let mensaje = null;

            if (succes.codigo == 303) {
              mensaje =
                "No fue posible el calculo de posibilidades de viajes teniendo encuentra únicamente los modos de transportes establecidos,sin embargo se sugieren las siguientes posibilidades de viaje";
            }


            let response={
              data: succes,
              mensaje: mensaje,
              lat_origen: lat_origen,
              lon_origen: lon_origen,
              lat_destino: lat_destino,
              lon_destino: lon_destino,
              ori_desc: ori_desc,
              des_desc: des_desc
            };

            resolve(response);
          } else {
            let mensaje = "";
            if (succes.codigo == 404) {
              mensaje =
                "No se encontraron posibles viajes  con las preferencias seleccionadas";
            }

            if (succes.codigo == 409) {
              mensaje = "Ocurrió un inconveniente inténtelo nuevamente";
            }

            reject(mensaje);
          }
        },
        error => {
          this.common.dismissLoading();
          reject("Ocurrió un inconveniente inténtelo nuevamente");
        }
      );
    });
  }


  private obtenerModoTransporte(modoTransporteIn: any) {
    let modoTransporte = null;
    if (modoTransporteIn.id == 1 && modoTransporteIn.activo) {
      modoTransporte = "2";
    }

    if (modoTransporteIn.id == 2 && modoTransporteIn.activo) {
      modoTransporte = "0";
    }

    if (modoTransporteIn.id == 3 && modoTransporteIn.activo) {
      modoTransporte = "6";
    }

    if (modoTransporteIn.id == 4 && modoTransporteIn.activo) {
      modoTransporte = "3";
    }

    if (modoTransporteIn.id == 5 && modoTransporteIn.activo) {
      modoTransporte = "4,7";
    }

    if (modoTransporteIn.id == 6 && modoTransporteIn.activo) {
      modoTransporte = "1";
    }

    if (modoTransporteIn.id == 7 && modoTransporteIn.activo) {
      modoTransporte = "5";
    }

    if (modoTransporteIn.id == 8 && modoTransporteIn.activo) {
      modoTransporte = "8";
    }

    if (modoTransporteIn.id == 9 && modoTransporteIn.activo) {
      modoTransporte = "7";
    }

    return modoTransporte;
  }
}
