import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'toString',
  standalone: true
})
export class ToStringPipe implements PipeTransform {

  transform(value?: number | null, ...args: any[]): string {
    return value?.toString() || '';
  }

}
