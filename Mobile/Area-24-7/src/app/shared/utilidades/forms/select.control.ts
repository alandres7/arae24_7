import { ControlBase } from './control-base';

export class SelectControl extends ControlBase<string> {
  controlType = 'select';
  type:string
  options: { key: string, value: string }[] = [];
  numeros: any[]= []
  constructor(options: any = {}) {
    super(options);
    if(options.type == 'Numero'){
      this.options = this.numeros;
    }
    else{
      this.options = options['options'] || [];
    }
    for (let index = 0; index < 100; index++) {
      this.numeros.push({
        key: index,
        label: index
      })  
    }
  }
}