export class ControlBase<T> {
  value: T;
  key: string;
  label: string;
  required: boolean;
  order: number;
  controlType: string;
  selected: boolean;
  multiple: boolean;
  placeholder: string;



  constructor(options: {
    value?: T,
    key?: string,
    label?: string,
    required?: boolean,
    order?: number,
    controlType?: string,
    selected?: boolean,
    multiple?: boolean,
    placeholder?: string,
    type?:{
      descripcion: string,
      id: number,
      nombre: string
    }

  } = {}) {
    this.value = options.value;
    this.key = options.key || '';
    this.label = options.label || '';
    this.placeholder = options.placeholder || '';
    this.required = !!options.required;
    this.selected = !!options.selected;
    this.multiple = !!options.multiple;
    this.order = options.order === undefined ? 1 : options.order;
    this.controlType = options.type.nombre || '';
  }
}