package com.capgemini.bookstore.controller;


import com.capgemini.bookstore.generated.api.BooksApi;
import com.capgemini.bookstore.generated.model.BookDto;
import com.capgemini.bookstore.generated.model.BooksDto;
import com.capgemini.bookstore.service.BookService;
import com.capgemini.bookstore.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class BookController implements BooksApi {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<BooksDto> getAllBooks() {
        BooksDto books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<BookDto> createBook(BookDto book) {
        BookDto createdBook = bookService.createBook(book);
        URI location = Util.createLocationURI(createdBook.getId());
        return ResponseEntity.created(location)
                .body(createdBook);
    }

    @Override
    public ResponseEntity<BookDto> getBookById(Integer id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
}
