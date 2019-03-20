import { Injectable } from '@angular/core';
import { ControlDescriptor } from './control';
import { SelectControl } from './select.control';
import { TextareaControl } from './textarea.control';
import { TextboxControl } from './textbox.control';
import { CheckboxControl } from './checkbox.control';
import { LabelControl } from './label.control';
import { DatatimeControl } from './datatime.control';



@Injectable()
export class ControlsService {
  getControls(descriptors: any[]) {
    let controls = descriptors.map((descriptor, index) => {
      let options = {
        ...descriptor,
        type: descriptor.type,
        key: descriptor.name,
        label: descriptor.title,
        placeholder: descriptor.placeholder,
        value: '',
        required: descriptor.required,
        order: index,
        selected: descriptor.selected,
        multiple: descriptor.multiple
      };

      switch (descriptor.type) {
        case 'text':
        case 'Texto':
          return new TextboxControl(options);
        case 'textarea':
          return new TextareaControl(options);
        case 'Lista':
          return new SelectControl(options);
          case 'Numero':
          return new SelectControl(options);
        case 'checkbox':
          return new CheckboxControl(options);
        case 'label':
          return new LabelControl(options);
        case 'datatime':
          return new DatatimeControl(options);
        default:
          console.error(`${descriptor.type} is not supported`);
      }
    });

    return controls.filter(x => !!x).sort((a, b) => a.order - b.order);
  }
}