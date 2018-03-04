import {Publisher} from './publisher';
import {Copy} from './copy';


export interface Publication {
  authors: string[]
  translators: string[]
  title: string
  titlePinyin: string
  titleShortPinyin: string
  publisher: Publisher
  copies: Copy[]
  price: number
  language: string
  publishDate: Date

}
