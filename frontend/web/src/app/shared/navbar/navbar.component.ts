import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  private sections;
  private sectionKeys;

  constructor() {
    this.sections = {};
    this.sectionKeys = [];
  }

  ngOnInit() {
    this.sections = {
      'publication': 'Publication',
      'admin': 'Admin'
    };
    this.sectionKeys = Object.keys(this.sections);
  }

}
