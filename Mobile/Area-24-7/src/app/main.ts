import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { AppModule } from './app.module';
import { Common } from './shared/utilidades/common';

enableProdMode();

platformBrowserDynamic().bootstrapModule(AppModule)
    .then((moduleRef) => {
        Common.setInjector(moduleRef.injector);
    });
