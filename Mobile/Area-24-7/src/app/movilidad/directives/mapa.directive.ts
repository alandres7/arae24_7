import { Directive, ElementRef, Renderer, Input } from '@angular/core';
import { Searchbar } from "ionic-angular";
import { GoogleMaps } from "../../shared/utilidades/googleMaps";

@Directive({
  selector: '[mapa]'
})
export class MapaDirective {
  padreDOMMapa: any;
  mapaDOM: any;
  @Input() mapa: boolean;

  constructor(public el: ElementRef,
    public renderer: Renderer,
    public googleMaps: GoogleMaps,
  ) {
     this.mapaDOM =document.getElementById("map");
  ;
  }

  ngAfterContentInit() {
    console.log(this.el, this.renderer);

    this.padreDOMMapa = this.mapaDOM.parentNode;
    this.el
    .nativeElement
    .childNodes[1] // div.scroll-content
    .appendChild(this.mapaDOM);
  }

  ngOnDestroy() {
    //se clona el mapa y se coloca en el lugar donde esta el original.
    this.padreDOMMapa.appendChild(this.mapaDOM);

  }
}

@Directive({
  selector: '[contenido-mapa]'
})
export class ContenidoMapaDirective {
  constructor(public el: ElementRef,
    public renderer: Renderer,
    public googleMaps: GoogleMaps,
  ) {
    // style="position: absolute; z-index:999; width: 100%;"
    renderer.setElementStyle(el.nativeElement, 'position', 'absolute');
    renderer.setElementStyle(el.nativeElement, 'z-index', '999');
    renderer.setElementStyle(el.nativeElement, 'width', '100%');
  }
}
