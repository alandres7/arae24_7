import { Estacion } from "./estacion.model";
import { Paradero } from "./paradero.model";
import { Ruta } from "./ruta.model";
import { Linea } from "./linea.model";

export class RutasCercanasReponse {

    estaciones:  Estacion[];
    rutas:  Ruta[];
    lineas: Linea[];
    puntosCivica: any[];
    paraderos: Paradero[];
    ciclovias: any[];
    puntosTarjetaCivica:any[];

  }
