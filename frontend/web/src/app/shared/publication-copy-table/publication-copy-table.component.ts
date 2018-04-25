import {Component, Input, OnInit} from '@angular/core';
import {Copy} from '../../core/model/copy';

@Component({
  selector: 'app-publication-copy-table',
  templateUrl: './publication-copy-table.component.html',
  styleUrls: ['./publication-copy-table.component.scss'],
})
export class PublicationCopyTableComponent implements OnInit {

  @Input()
  copy: Copy[];
  @Input()
  showDates: boolean = true;

  displayedColumns = ['id', 'status', 'createAt', 'updateAt'];

  constructor() {
  }

  ngOnInit() {
    if (this.showDates) {
      this.displayedColumns = ['id', 'createAt', 'updateAt', 'status'];
    }
    else {
      this.displayedColumns = ['id', 'status']
    }
  }

}
