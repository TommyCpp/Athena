import {Timestamp} from 'rxjs/Rx';

export enum CopyStatus {
  CREATED = 0,
  AVAILABLE = 1,
  BOOKED = 2,
  CHECKED_OUT = 3,
  RESERVED = 4,
  DAMAGED = 5,
  WAIT_FOR_VERIFY = 6

}

export class Copy {
  protected id: number = null;
  protected createDate: Date = null;
  protected updatedDate: Date = null;
  protected status: CopyStatus = CopyStatus.AVAILABLE;
}
