import {Publication} from './publication';
import {Publisher} from './publisher';
import {Copy} from './copy';


export class Journal implements Publication{
  issn: string;
  year: number;
  issue: number;
  authors: string[];
  translators: string[];
  title: string;
  coverUrl: URL;
  introduction: string;
  titlePinyin: string;
  titleShortPinyin: string;
  publisher: Publisher;
  copies: Copy[];
  price: number;
  language: string;
  publishDate: Date;

}
