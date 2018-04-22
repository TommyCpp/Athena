import { Pipe, PipeTransform } from '@angular/core';
import {CopyStatus} from '../model/copy';

@Pipe({
  name: 'copyStatus'
})
export class CopyStatusPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return CopyStatus[value];
  }

}
