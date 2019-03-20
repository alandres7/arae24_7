import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'substr'
})
export class SubstrPipe implements PipeTransform {
  transform(value: string, cut: number = 80) {
    if (value.length > cut) {
      let str = value.substr(0, cut) + '...';
      return str;
    } else {
      return value;
    }
  }
}
