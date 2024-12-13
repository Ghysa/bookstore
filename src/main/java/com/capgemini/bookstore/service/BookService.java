package com.capgemini.bookstore.service;


import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.generated.model.BookDto;
import com.capgemini.bookstore.generated.model.BooksDto;
import com.capgemini.bookstore.repository.BookRepository;
import com.capgemini.bookstore.service.mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto getBookById(Integer id) {
        Book book = bookRepository.getReferenceById(id);
        return bookMapper.toDto(book);
    }

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public BooksDto getAllBooks() {
        List<Book> books = bookRepository.findAll();
        BooksDto booksDto = new BooksDto();
        booksDto.setBooks(
                books.stream()
                        .map(bookMapper::toDto)
                        .toList());
        return booksDto;
    }
}
