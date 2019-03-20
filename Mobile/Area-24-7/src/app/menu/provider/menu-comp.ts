import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { MenuOptionModel } from '../model/menu-model';

@Injectable()
export class MenuComProvider {
    private otenerItem = new Subject<any>();
    private apagarItem = new Subject<any>();

    obtenerItems$ = this.otenerItem.asObservable();
    apagarItem$ = this.apagarItem.asObservable();

    fAgregarItems(otenerItem: any) {
        this.otenerItem.next(otenerItem);
    }

    fApagarItems(apagarItem: any) {
        this.apagarItem.next(apagarItem);
    }


}