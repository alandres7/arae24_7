
export enum MODOS_BUSQUEDA {
  PREDICCION_GOOGLE,
  RUTAS_Y_LINEAS
};

export class Ubicacion {
  private _descripcion: string;
  private _modoBusqueda: MODOS_BUSQUEDA;
  public txtPlaceholder: any;
  public prediccion: any;
  public cbDescripcionChange: any;
  public ruta: Ruta;

  public get descripcion(): string {
    return this._descripcion;
  }

  public set descripcion(value: string) {
    this._descripcion = value;
    console.log("Ubicacion:setDescripcion", value)
    if (this.cbDescripcionChange) {
      this.cbDescripcionChange();
    }
  }

  public get modoBusqueda(): MODOS_BUSQUEDA {
    return this._modoBusqueda;
  }

  public set modoBusqueda(modo: MODOS_BUSQUEDA) {
    switch (modo) {
      case MODOS_BUSQUEDA.PREDICCION_GOOGLE:
        console.log("Ubicacion en modo prediccion de google")
        break;

      case MODOS_BUSQUEDA.RUTAS_Y_LINEAS:
        console.log("ubicaciaon en modo lineas y rutas")
        this.ruta = new Ruta();
        break;

      default:
        console.log("ubicacion en modo default preddccion de google.")
        this.modoBusqueda = MODOS_BUSQUEDA.PREDICCION_GOOGLE;
        break;
    }
    this._modoBusqueda = modo;
  }

  constructor() {
  }

}

export class Ruta {
  private nombre: string;
  private horario: string;
  private frecuencia: string;
  private tarifa: string;
}