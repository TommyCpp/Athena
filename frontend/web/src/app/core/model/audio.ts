import {Publication} from './publication';
import {Publisher} from './publisher';
import {Copy} from './copy';
import {Model} from './model';

export class Audio extends Model implements Publication {
  isrc: string = null;
  authors: string[] = null;
  translators: string[] = null;
  title: string = null;
  subtitle: string = null;
  coverUrl: string = null;
  introduction: string = null;
  titlePinyin: string = null;
  titleShortPinyin: string = null;
  publisher: Publisher = null;
  copies: Copy[] = null;
  price: number = null;
  language: string = null;
  publishDate: Date = null;

}
