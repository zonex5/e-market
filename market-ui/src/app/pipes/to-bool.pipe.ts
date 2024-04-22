import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'toBool',
  standalone: true
})
export class ToBoolPipe implements PipeTransform {

  transform(value: number | null): boolean {
    return !!value && value > 0
  }

}
