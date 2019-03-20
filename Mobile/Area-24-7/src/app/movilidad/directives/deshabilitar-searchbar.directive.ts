import { Directive, ElementRef, Renderer, Input } from '@angular/core';
import { Searchbar } from "ionic-angular";

@Directive({
  selector: '[deshabilitar-searchbar]'
})
export class DeshabilitarSearchbarDirective {
  @Input() deshabilitarSearchbar: boolean;

  constructor(public el: ElementRef, public renderer: Renderer) { }

  ngAfterContentInit() {
    console.log("DIRECTIVE:::::", this.deshabilitarSearchbar, this.el, this.renderer);
    try {
      this.el.nativeElement.childNodes[2].childNodes[0].childNodes[2].disabled = true;
    } catch (error) {

    }

  }

}
