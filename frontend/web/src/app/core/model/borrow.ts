import {Publication} from './publication';
import {Time} from '@angular/common';
import {User} from './user';
import {Copy} from './copy';

export class Borrow {
  protected id: string;
  protected enable: boolean;
  protected createdDate: Date;
  protected updatedDate: Date;
  protected type: string;
  protected user: User;
  protected copy: Copy;
}
