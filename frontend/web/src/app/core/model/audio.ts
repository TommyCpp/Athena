

import {Publication} from './publication';
import {Publisher} from './publisher';
import {Copy} from './copy';

export class Audio implements Publication{
  isrc: string;
  authors: string[];
  translators: string[];
  title: string;
  subtitle: string;
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
