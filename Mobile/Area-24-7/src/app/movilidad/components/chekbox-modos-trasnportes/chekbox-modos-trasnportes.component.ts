import { Component, OnInit, Output, Input, EventEmitter } from "@angular/core";
import { ItemCheckBoxModoTransporte } from "../../models/itemCheckBoxModoTransporte.model";
import { Common } from "../../../shared/utilidades/common";
import { PosiblesViajesProvider } from "../../../shared/posibles-viajes.service";
import { Ubicacion } from "../../models/ubicacion.model";
import { SideMenu } from "../../../menu/page/side-menu";
import { PreferencesProvider } from "../../../../providers/preferences/preferences";
import { TransportPreferences } from "../../../../entities/preferences/transport-preferences";

/**
 * Generated class for the ChekboxModosTraportesComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: "chekbox-modos-trasnportes",
  templateUrl: "chekbox-modos-trasnportes.component.html"
})
export class ChekboxModosTransportesComponent implements OnInit {
  lsItemCheckBoxModoTransporte: ItemCheckBoxModoTransporte[];
  modoTransporteSelected: string;
  @Input() ubicacionOrigen: Ubicacion;
  @Input() ubicacionDestino: Ubicacion;
  @Output() onResponseViajesSugeridos = new EventEmitter();

  private preferenciasTransportes: any[];

  constructor(
    private utilidades: Common,
    private posiblesViajesProvider: PosiblesViajesProvider,
    private preferencesProvider: PreferencesProvider
  ) {
    this.modoTransporteSelected = "";

    this.preferencesProvider.transportsPreferences$.subscribe(
      (transportsPreferences: TransportPreferences[]) => {
          console.log('transportsPreferencesChanged$ ' + JSON.stringify(transportsPreferences));
          this.preferenciasTransportes = transportsPreferences;
      }
    );
  }

  ngOnInit(): void {
    this.lsItemCheckBoxModoTransporte = [];
    this.cargarModosTransportes();
  }

  cargarModosTransportes() {
    let modosTranportes = this.preferenciasTransportes;
    for (let index = 0; index < modosTranportes.length; index++) {
      let item = new ItemCheckBoxModoTransporte();
      item.idModoTransporte = this.utilidades.obtenerModoTransporteIdService(
        modosTranportes[index]
      );

      item.nombre = modosTranportes[index].nombre;
      item.activo = modosTranportes[index].activo;
      item.iconOn = modosTranportes[index].iconOn;
      item.iconOff = modosTranportes[index].iconOff;
      if (item.activo) {
        item.icon = item.iconOn;
      } else {
        item.icon = item.iconOff;
      }

      this.lsItemCheckBoxModoTransporte.push(item);
    }
  }

  onClickItem(item) {
    this.modoTransporteSelected = item.nombre;
    this.invertirActivos(
      this.lsItemCheckBoxModoTransporte,
      item.idModoTransporte
    );

    this.posiblesViajesProvider.cambiarPosiciones(
      this.ubicacionOrigen,
      this.ubicacionDestino
    );
    this.posiblesViajesProvider
      .obtenerviajesSugeridos('9,'+item.idModoTransporte)
      .then(data => {
        this.onResponseViajesSugeridos.emit(data);
      })
      .catch(error => {
        this.utilidades.basicAlert("Movilidad", error);
      });
  }

  invertirActivos(lsItems: any[], idModoTransporte) {
    for (let index = 0; index < lsItems.length; index++) {
      if (lsItems[index].idModoTransporte == idModoTransporte) {
        lsItems[index].activo = true;
        lsItems[index].icon = lsItems[index].iconOn;
      } else {
        lsItems[index].activo = false;
        lsItems[index].icon = lsItems[index].iconOff;
      }
    }
  }
}
