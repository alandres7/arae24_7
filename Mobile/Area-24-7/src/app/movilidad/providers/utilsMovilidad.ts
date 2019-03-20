import { ModoTrasporteStringsViajes } from "./constantesMovilidad";

export class UtilsMovilidad {

    public static convertTime24to12(time24:string):string {
      let  tmpArr = time24.split(':'), time12;
      if(+tmpArr[0] == 12) {
      time12 = tmpArr[0] + ':' + tmpArr[1] + ' pm';
      } else {
      if(tmpArr[0] == '00') {
      time12 = '12:' + tmpArr[1] + ' am';
      } else {
      if(+tmpArr[0] > 12) {
      time12 = (+tmpArr[0]-12) + ':' + tmpArr[1] + ' pm';
      } else {
      time12 = (+tmpArr[0]) + ':' + tmpArr[1] + ' am';
      }
      }
      }
      return time12;
    }


    public static obtenerUsuarioActivo():any {
      return JSON.parse(localStorage.getItem('usuario'));
    }

    /**
     * name
     */
    public static obtenerModoTransporteHuella(modo) {
      let res='';
      switch (modo) {
        case ModoTrasporteStringsViajes.METRO:
        res = 'METRO'
        break;

        case ModoTrasporteStringsViajes.TRANVIA:
        res = 'TRANVIA'
        break;

        case ModoTrasporteStringsViajes.CAMINAR:
        res = 'A PIE'
        break;

        case ModoTrasporteStringsViajes.BICICLETA_PARTICULAR:
        res = 'BICI'
        break;

        case ModoTrasporteStringsViajes.METRO_PLUS:
        res = 'METRO_PLUS'
        break;

        case ModoTrasporteStringsViajes.METRO_CABLE:
        res = 'METRO_CABLE'
        break;

        case ModoTrasporteStringsViajes.ALIMENTADOR:
        res = 'ALIMENTADOR'
        break;

        case ModoTrasporteStringsViajes.AUTOBUS:
        res = 'BUS'
        break;

     case ModoTrasporteStringsViajes.ENCICLA:
        res = 'ENCICLA'
        break;


      }
     return res;
    }

  }
