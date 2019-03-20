import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'username'
})
export class UsernamePipe implements PipeTransform {
    transform(value: string) {
        if(value){
            let index = value.indexOf('@');
            let str = value.substring(0, index);
            return str;
        }
    }
}
