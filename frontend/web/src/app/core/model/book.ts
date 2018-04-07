import {Publication} from './publication';
import {Publisher} from './publisher';
import {Copy} from './copy';
import {JsonProperty} from '../decorator/jsonProperty';
import {Model} from "app/core/model/model";

export class Book extends Model implements Publication {

  public isbn: string = null;
  public title: string = null;
  public subtitle: string = null;
  public directory: string = null;
  public introduction: string = null;
  public preface: string = null;
  public coverUrl: string = null;
  public titlePinyin: string = null;
  public titleShortPinyin: string = null;
  public publisher: Publisher = null;
  public copies: Copy[] = null;
  public price: number = null;
  public language: string = null;
  public authors: string[] = null;
  public translators: string[] = null;
  public publishDate: Date = null;
  public categoryId: number = null;
  public version: number = null;

}
