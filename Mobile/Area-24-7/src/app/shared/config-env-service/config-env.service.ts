import { Injectable } from '@angular/core';

import { CONFIG_ENV } from './const-env';
import { Environments } from './environment';

@Injectable()
export class ConfigEnvService {

    public static setEnvironment(env: Environments) {
        //urlServidor:string = "http://201.184.243.195:8095"; // publica
        //urlServidor:string = "http://172.16.0.20:8095";
        switch (env) {
            case Environments.Dev_0://Ambiente Desarrollo
                CONFIG_ENV.REST_BASE_URL = 'http://172.16.0.20:8095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'd76f447f-86e3-470a-97c4-8823cf440aa9';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '406029628587';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment Dev');
                break;

            case Environments.Dev_1://Ambiente Pruebas
                //CONFIG_ENV.REST_BASE_URL = 'http://201.184.243.195:8095';
                //CONFIG_ENV.REST_BASE_URL = 'http://201.184.243.195:9095';
                CONFIG_ENV.REST_BASE_URL = 'http://localhost:9090';
                //CONFIG_ENV.REST_BASE_URL = 'http://apptest.quipux.com/amva';
                //CONFIG_ENV.REST_BASE_URL = 'http://10.125.30.27:9090';
                //CONFIG_ENV.REST_BASE_URL = 'http://192.168.1.73:9090';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'd76f447f-86e3-470a-97c4-8823cf440aa9';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '406029628587';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment Pruebas');
                break;

            case Environments.Amva://Ambiente Amva
                CONFIG_ENV.REST_BASE_URL = 'http://webservices.metropol.gov.co:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Ambiente Amva');
                break;

            case Environments.DevInternal://Ambiente interno desarrollo Pruebas
                CONFIG_ENV.REST_BASE_URL = 'http://172.16.0.20:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';        
                console.log('Enviroment interno desarrollo');
                break;

            case Environments.Dev_4://Ambiente desarrollo  equipo lucho
                CONFIG_ENV.REST_BASE_URL = 'http://172.16.201.36:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'd76f447f-86e3-470a-97c4-8823cf440aa9';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '406029628587';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment equipo lucho');
                break;

            case Environments.DevExternal://Ambiente desarrollo ip Publica
                CONFIG_ENV.REST_BASE_URL = 'http://201.184.243.195:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment desarrollo ip Publica');
                break;

            case Environments.DevLocalhost://Ambiente desarrollo ip local
                CONFIG_ENV.REST_BASE_URL = 'http://127.0.0.1:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment desarrollo maquina local');
                break;

            case Environments.Dev_7://Ambiente desarrollo virtual
                CONFIG_ENV.REST_BASE_URL = 'http://169.254.188.11:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'd76f447f-86e3-470a-97c4-8823cf440aa9';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '406029628587';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment desarrollo maquina local');
                break;

            case Environments.Test://Ambiente desarrollo virtual
                CONFIG_ENV.REST_BASE_URL = 'http://172.16.0.34:9096';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment Test');
            break;

            case Environments.Dev: //Ambiente de desarrollo con url pública
                CONFIG_ENV.REST_BASE_URL = 'http://area247-api.adacsc.co';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';        
                console.log('Environment Dev');
                break;

            case Environments.DevMario: //Ambiente de desarrollo Mario Fabián
                CONFIG_ENV.REST_BASE_URL = 'http://172.16.0.80:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';        
                console.log('Environment DevMario');
                break;

            case Environments.DevJulio://Ambiente desarrollo Julio Mulcue
                CONFIG_ENV.REST_BASE_URL = 'http://192.168.1.73:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'd76f447f-86e3-470a-97c4-8823cf440aa9';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '406029628587';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';
                console.log('Enviroment desarrollo maquina local');
                break;

            case Environments.QA: //Ambiente de QA con url pública
                CONFIG_ENV.REST_BASE_URL = 'http://area247-apiqa.adacsc.co';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';        
                console.log('Environment QA');
                break;
            
            case Environments.AmvaNuevo: //Ambiente de QA con url pública
                CONFIG_ENV.REST_BASE_URL = 'http://area247.metropol.gov.co:9095';
                CONFIG_ENV.ONESIGNAL_APPLICATION_ID = 'fb3d1a4b-95df-406c-8a76-46d7a6947dfc';
                CONFIG_ENV.ONESIGNAL_SENDER_ID = '692074027754';
                CONFIG_ENV.GOOGLE_MAPS_KEY = 'AIzaSyBYu46VlikN0QFUFMg7aOydSsBuehD1Lbc';        
                console.log('Environment QA');
                break;
        }
    }

    public static getEnvironment() {
        return CONFIG_ENV;
    }
}