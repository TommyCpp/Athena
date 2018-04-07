import {Publisher} from './publisher';
import {Copy} from './copy';


export interface Publication {
  authors: string[]
  translators: string[]
  title: string
  subtitle?:string
  coverUrl: string;
  introduction: string
  titlePinyin: string
  titleShortPinyin: string
  publisher: Publisher
  copies: Copy[]
  price: number
  language: string
  publishDate: Date

}


export function isBook(publication: Publication): boolean {
  return 'isbn' in publication;
}

export function isJournal(publication: Publication): boolean {
  return 'issn' in publication;
}

export function isAudio(publication: Publication): boolean {
  return 'isrc' in publication;
}
