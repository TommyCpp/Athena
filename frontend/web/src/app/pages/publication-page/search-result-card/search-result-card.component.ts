import {Component, OnInit} from '@angular/core';
import {isAudio, isBook, isJournal, Publication} from '../../../core/model/publication';
import {EndPointService} from '../../../core/service/end-point.service';
import {isType} from '@angular/core/src/type';
import {Book} from '../../../core/model/book';
import {Journal} from '../../../core/model/Journal';
import {Audio} from '../../../core/model/Audio';

@Component({
  selector: 'app-search-result-card',
  inputs: ['publication'],
  templateUrl: './search-result-card.component.html',
  styleUrls: ['./search-result-card.component.scss']
})
export class SearchResultCardComponent implements OnInit {
  publication: Publication;

  constructor(private endPointService: EndPointService) {
  }

  ngOnInit() {
  }

  getDetailUrl() {
    if (isBook(this.publication)) {
      return this.endPointService.getRelativeUrl('Book', {'isbn': (this.publication as Book).isbn.toString()})
    }
    else if (isJournal(this.publication)) {
      return this.endPointService.getRelativeUrl('Journal', {
        'issn': (this.publication as Journal).issn.toString(),
        'year': (this.publication as Journal).year.toString(),
        'issue': (this.publication as Journal).issue.toString()
      })
    }
    else if (isAudio(this.publication)) {
      return this.endPointService.getRelativeUrl('Audio', {'isrc': (this.publication as Audio).isrc.toString()})
    }
  }


}
