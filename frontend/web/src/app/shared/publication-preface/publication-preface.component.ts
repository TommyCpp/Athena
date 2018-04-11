import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-publication-preface',
  templateUrl: './publication-preface.component.html',
  styleUrls: ['./publication-preface.component.scss']
})
export class PublicationPrefaceComponent implements OnInit {
  @Input()
  source: string;

  constructor() { }

  ngOnInit() {
  }

}
