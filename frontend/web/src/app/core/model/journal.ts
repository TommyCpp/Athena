import {Publication} from './publication';
import {Publisher} from './publisher';
import {Copy} from './copy';
import {Model} from './model';


export class Journal extends Model implements Publication {
  issn: string = null;
  year: number = null;
  issue: number = null;
  authors: string[] = null;
  translators: string[] = null;
  title: string = null;
  coverUrl: URL = null;
  introduction: string = null;
  titlePinyin: string = null;
  titleShortPinyin: string = null;
  publisher: Publisher = null;
  copies: Copy[] = null;
  price: number = null;
  language: string = null;
  publishDate: Date = null;

}
