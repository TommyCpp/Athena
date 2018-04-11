import {Component, OnInit} from '@angular/core';
import {Book} from '../../core/model/book';
import {BookService} from '../../core/service/book.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-book-detail-page',
  templateUrl: './book-detail-page.component.html',
  styleUrls: ['./book-detail-page.component.scss']
})
export class BookDetailPageComponent implements OnInit {
  book: Book;

  constructor(private bookService: BookService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    //todo: get book info
    this.route.params.subscribe(params => {
      this.bookService.get(params["isbn"]).subscribe((book: Book) => {
        this.book = book;
      })
    })
  }

}
