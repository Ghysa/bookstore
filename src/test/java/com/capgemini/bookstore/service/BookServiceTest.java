package com.capgemini.bookstore.service;

import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.generated.model.BookDto;
import com.capgemini.bookstore.generated.model.BooksDto;
import com.capgemini.bookstore.repository.BookRepository;
import com.capgemini.bookstore.sample.BookSample;
import com.capgemini.bookstore.service.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookById() {
        int id = BookSample.ID;
        Book entity = BookSample.getEntity();
        BookDto dto = BookSample.getDto();

        when(bookRepository.getReferenceById(id)).thenReturn(entity);
        when(bookMapper.toDto(entity)).thenReturn(dto);

        BookDto result = bookService.getBookById(id);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    public void testCreateBook() {
        int id = BookSample.ID;
        Book entity = BookSample.getEntity();
        BookDto dto = BookSample.getDto();

        when(bookMapper.toEntity(dto)).thenReturn(entity);
        when(bookRepository.save(entity)).thenReturn(entity);
        when(bookMapper.toDto(entity)).thenReturn(dto);

        BookDto result = bookService.createBook(dto);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    public void testGetAllBooks() {
        Book entity = BookSample.getEntity();
        BookDto dto = BookSample.getDto();

        when(bookRepository.findAll()).thenReturn(List.of(entity));
        when(bookMapper.toDto(entity)).thenReturn(dto);

        BooksDto result = bookService.getAllBooks();

        assertThat(result.getBooks()).containsExactly(dto);
    }
}
