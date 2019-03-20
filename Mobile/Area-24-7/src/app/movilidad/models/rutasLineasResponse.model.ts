import { Estacion } from "./estacion.model";
import { Paradero } from "./paradero.model";
import { Ruta } from "./ruta.model";
import { Linea } from "./linea.model";

export class RutasLineasResponse {

    estaciones:  Estacion[];
    rutas:  Ruta[];
    lineas: Linea[];
    paraderos: Paradero[];

  }
