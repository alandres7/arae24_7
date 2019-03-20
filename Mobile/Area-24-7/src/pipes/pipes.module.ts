import { NgModule } from '@angular/core';
import { CapitalizePipe } from './capitalize/capitalize';
import { SubstrPipe } from './substr/substr';
import { UsernamePipe } from './username/username';
@NgModule({
  declarations: [CapitalizePipe,
    SubstrPipe,
    UsernamePipe],
  imports: [],
  exports: [CapitalizePipe,
    SubstrPipe,
    UsernamePipe]
})
export class PipesModule {}
