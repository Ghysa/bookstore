package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.generated.model.BookDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class BookSample {

    public static final int ID = 12;
    public static final String TITLE = "Java for Dummies";
    public static final String AUTHOR = "Berry Burd";
    public static final BigDecimal PRICE = new BigDecimal("29.42");
    public static final LocalDate RELEASE_DATE = LocalDate.of(2022, Month.APRIL, 12);

    public static Book getEntity() {
        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setPrice(PRICE);
        book.setReleaseDate(RELEASE_DATE);
        return book;
    }

    public static BookDto getDto() {
        BookDto book = new BookDto();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setPrice(PRICE);
        book.setReleaseDate(RELEASE_DATE);
        return book;
    }
}
