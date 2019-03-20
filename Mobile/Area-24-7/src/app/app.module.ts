import { ConfigEnvService } from './shared/config-env-service/config-env.service';
import { SharedServicesModule } from './provider/shered-services.module';
import { menuDinamico } from './menu/provider/menu';
import { MenuModule } from './menu/menu.module';
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule, LOCALE_ID } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule, App } from 'ionic-angular';

import { Amva_247_App } from './app.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { LoginModule } from './login/login.module';
import { InicioModule } from './inicio/inicio.module';
import { MiEntornoModule } from './mi-entorno/mi-entorno.module';
import { VigiasMedioAmbienteModule } from './vigias-medio-ambiente/vigias-medio-ambiente.module';
import { FormRegisterModule } from './registro/form-register.module';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { Network } from '@ionic-native/network';
import { Diagnostic } from '@ionic-native/diagnostic';
import { HttpModule, Http, XHRBackend, RequestOptions } from '@angular/http';
import { SideMenu } from './menu/page/side-menu';
import { ProviderModule } from './provider/provider.module';
import { OneSignal } from '@ionic-native/onesignal';
import { Facebook, FacebookLoginResponse } from '@ionic-native/facebook';
import { NativeStorage } from '@ionic-native/native-storage';
import { GooglePlus } from '@ionic-native/google-plus';
import { ChartsModule } from 'ng2-charts';
import { Media } from '@ionic-native/media';
import { File } from '@ionic-native/file';
import { Geolocation } from '@ionic-native/geolocation';
import { SocialSharing } from '@ionic-native/social-sharing';
import { MediaCapture } from '@ionic-native/media-capture';
import { InterceptorHttp } from './shared/utilidades/interceptorHttp';
import { Common } from './shared/utilidades/common';
import { MovilidadModule } from './movilidad/movilidad.module';
// Import the AF2 Module
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabaseModule } from 'angularfire2/database';
import { NativeGeocoder } from '@ionic-native/native-geocoder';
import { AuthenticationProvider } from '../providers/authentication/authentication';
import { TerritorioProvider } from '../providers/territorio/territorio';

import { Environments } from './shared/config-env-service/environment';
import { GoogleGeocoderProvider } from '../providers/google-geocoder/google-geocoder';
import { LayerProvider } from '../providers/layer/layer';
import { LocationChangeProvider } from '../providers/location-change/location-change';
import { DecisionTreeProvider } from '../providers/decision-tree/decision-tree';
import { AvistamientoProvider } from '../providers/avistamiento/avistamiento';
import { Camera } from '@ionic-native/camera';
import { FileTransfer } from '@ionic-native/file-transfer';
import { InAppBrowser } from '@ionic-native/in-app-browser';
import { Clipboard } from '@ionic-native/clipboard';
import { ImageResizer } from '@ionic-native/image-resizer';
import { VideoEditor } from '@ionic-native/video-editor';

// Cambio del idiomas de DatePipe
import localeES from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
import { EstacionDisfrutameProvider } from '../providers/estacion-disfrutame/estacion-disfrutame';
import { RegistroPageModule } from '../pages/registro/registro.module';
import { MenuMainPageModule } from '../pages/menu-main/menu-main.module';
import { TerritorioComponent } from '../components/territorio/territorio';
import { MiEntornoComponent } from '../components/mi-entorno/mi-entorno';
import { AvistamientoComponent } from '../components/avistamiento/avistamiento';
import { CuidameComponent } from '../components/cuidame/cuidame';
import { MovilidadPage } from './movilidad/pages/movilidad-page/movilidad-page';
import { HuellasComponent } from '../components/huellas/huellas';
import { PerfilPageModule } from '../pages/perfil/perfil.module';
import { TerminosCondicionesPageModule } from '../pages/terminos-condiciones/terminos-condiciones.module';
import { PreferencesProvider } from '../providers/preferences/preferences';
import { UpdatePasswordPageModule } from '../pages/update-password/update-password.module';
import { GeneralConfigurationComponent } from "../components/general-configuration/general-configuration";
import { TerritorioDetailComponent } from '../components/territorio-detail/territorio-detail';
import { LocationUpdateProvider } from '../providers/location-update/location-update';
import { LocationAccuracy } from '@ionic-native/location-accuracy';
import { VigiaProvider } from '../providers/vigia/vigia';
import { UploadServiceProvider } from '../providers/upload-service/upload-service';
import { PostconsumoMidemeProvider } from '../providers/postconsumo-mideme/postconsumo-mideme';
import { MessageProvider } from '../providers/message/message';
// import { DynamicFormModule } from './shared/utilidades/forms/dynamic-form.module';
import { DynamicControlsService } from './shared/utilidades/forms/dynamic-controls.service';
import { ControlsService } from './shared/utilidades/forms/controls.service';
import { MidemeProvider } from '../providers/mideme/mideme';

registerLocaleData(localeES);

/*
export const firebaseConfig = {
    apiKey: "AIzaSyAXXkopxvVwyY5rDNB_8ORRIW5X2_LQqK0",
    authDomain: "xgep-91621.firebaseapp.com",
    databaseURL: "https://xgep-91621.firebaseio.com",
    projectId: "xgep-91621",
    storageBucket: "xgep-91621.appspot.com",
    messagingSenderId: "406029628587"
};
*/
export const firebaseConfig = {
    apiKey: 'AIzaSyAAqWHUKXPUkx9sBDwKaUjZu3fNeDySknU',
    authDomain: 'https://area-24-7-40242.firebaseio.com',
    databaseURL: 'https://area-24-7-40242.firebaseio.com',
    projectId: 'area-24-7-40242',
    storageBucket: 'area-24-7-40242.appspot.com',
    messagingSenderId: '692074027754',
};

@NgModule({
    declarations: [Amva_247_App, SideMenu, GeneralConfigurationComponent],
    imports: [
        // DynamicFormModule,
        BrowserModule,
        CoreModule,
        SharedModule,
        LoginModule,
        InicioModule,
        MenuMainPageModule,
        MiEntornoModule,
        MovilidadModule,
        VigiasMedioAmbienteModule,
        FormRegisterModule,
        IonicModule.forRoot(Amva_247_App, {
            platforms: {
                ios: {
                    backButtonText: ' ',
                },
            },
        }),
        AngularFireModule.initializeApp(firebaseConfig, 'area24-7'),
        AngularFireDatabaseModule,
        HttpModule,
        ProviderModule,
        MenuModule,
        SharedServicesModule.forRoot(),
        ChartsModule,
        RegistroPageModule,
        PerfilPageModule,
        TerminosCondicionesPageModule,
        UpdatePasswordPageModule
    ],
    bootstrap: [IonicApp],
    entryComponents: [
        Amva_247_App,
        TerritorioComponent,
        MiEntornoComponent,
        AvistamientoComponent,
        MovilidadPage,
        CuidameComponent,
        HuellasComponent,
        TerritorioDetailComponent
    ],
    providers: [
        StatusBar,
        SplashScreen,
        { provide: ErrorHandler, useClass: IonicErrorHandler },
        Network,
        Diagnostic,
        OneSignal,
        Facebook,
        GooglePlus,
        NativeStorage,
        menuDinamico,
        ConfigEnvService,
        Media,
        File,
        NativeGeocoder,
        AuthenticationProvider,
        Geolocation,
        TerritorioProvider,
        DynamicControlsService, ControlsService,
        {
            provide: Http,
            useFactory: httpFactory,
            deps: [App, XHRBackend, RequestOptions, Common],
        },
        GoogleGeocoderProvider,
        TerritorioProvider,
        LayerProvider,
        LocationChangeProvider,
        DecisionTreeProvider,
        AvistamientoProvider,
        Camera,
        FileTransfer,
        MediaCapture,
        InAppBrowser,
        Clipboard,
        ImageResizer,
        VideoEditor,
        // Cambio del DatePipe a español    
        { provide: LOCALE_ID, useValue: 'es' },
        SocialSharing,
        EstacionDisfrutameProvider,
    PreferencesProvider,
    LocationUpdateProvider,
    LocationAccuracy,
    VigiaProvider,
    UploadServiceProvider,
    PostconsumoMidemeProvider,
    MessageProvider,
    MidemeProvider,
    ],
})
export class AppModule {
    mykey = firebaseConfig.apiKey;
    constructor() {
        ConfigEnvService.setEnvironment(Environments.AmvaNuevo);
    }
}   
export function httpFactory(
    app: App,
    backend: XHRBackend,
    defaultOptions: RequestOptions,
    common: Common
) {
    return new InterceptorHttp(app, backend, defaultOptions, common);
}
