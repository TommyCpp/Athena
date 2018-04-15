import {Component, Input, OnInit} from '@angular/core';
import {Publication} from '../../core/model/publication';

@Component({
  selector: 'app-publication-basic-info',
  templateUrl: './publication-basic-info.component.html',
  styleUrls: ['./publication-basic-info.component.scss']
})
export class PublicationBasicInfoComponent implements OnInit {
  @Input()
  publication: Publication;

  constructor() {
  }

  ngOnInit() {
  }

}
