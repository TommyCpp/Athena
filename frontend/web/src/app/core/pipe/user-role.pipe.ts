import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'userRole'
})
export class UserRolePipe implements PipeTransform {

  transform(value: string, args?: any): any {
    return value.replace('ROLE_','');
  }

}
