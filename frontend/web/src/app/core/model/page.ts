export class Page<T> {
  protected content: T[];
  protected totalElements: number;
  protected numberOfElements: number;
  protected last: boolean;
  protected first: boolean;
  protected totalPages: number;
  protected number: number;
  protected size: number;
  protected sort: any;
}
