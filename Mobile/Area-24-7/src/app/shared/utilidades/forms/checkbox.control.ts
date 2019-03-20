import { ControlBase } from './control-base';

export class CheckboxControl extends ControlBase<string> {
  controlType = 'checkbox';
  type: string;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}