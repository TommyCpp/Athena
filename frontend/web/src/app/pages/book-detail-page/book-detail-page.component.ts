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
    this.route.params.subscribe(params => {
      this.bookService.get(params["isbn"]).subscribe((books: Book[]) => {
        if (books.length == 1) {
          this.book = books[0];
        }
        else {
          throw new Error("multi books found for one ISBN");
        }
      })
    })
  }

}
