import {Publication} from './publication';
import {Publisher} from './publisher';
import {Copy} from './copy';

export class Book implements Publication {
  public isbn: number;
  public title: string;
  public subtitle: string;
  public directory: string;
  public introduction: string;
  public preface: string;
  public coverUrl: URL;
  public titlePinyin: string;
  public titleShortPinyin: string;
  public publisher: Publisher;
  public copies: Copy[];
  public price: number;
  public language: string;
  public authors: string[];
  public translators: string[];
  public publishDate: Date;
  public categoryId: number;
  public version: number;

}
