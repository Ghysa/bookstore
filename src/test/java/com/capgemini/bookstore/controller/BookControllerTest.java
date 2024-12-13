package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.model.BookDto;
import com.capgemini.bookstore.generated.model.BooksDto;
import com.capgemini.bookstore.sample.BookSample;
import com.capgemini.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockitoBean
    private BookService service;

    private final MockMvcTester mockMvc;
    private final ObjectMapper objectMapper;

    public BookControllerTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = MockMvcTester.create(mockMvc);
        this.objectMapper = objectMapper;
    }

    @Test
    @WithMockUser
    public void testGetBookById() {
        int id = BookSample.ID;
        BookDto book = BookSample.getDto();

        when(service.getBookById(id)).thenReturn(book);

        assertThat(mockMvc.get().uri("/books/{id}", id))
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("sample/book.json");
    }

    @Test
    @WithMockUser
    public void testGetAllBooks() {
        BooksDto books = new BooksDto();
        books.setBooks(List.of(BookSample.getDto()));

        when(service.getAllBooks()).thenReturn(books);

        assertThat(mockMvc.get().uri("/books"))
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("sample/books.json");
    }

    @Test
    @WithMockUser
    public void testCreateBook() throws Exception {
        BookDto book = BookSample.getDto();
        String bookJson = objectMapper.writeValueAsString(book);

        when(service.createBook(any())).thenReturn(book);

        assertThat(mockMvc.post().with(csrf()).uri("/books")
                .content(bookJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .isStrictlyEqualTo("sample/book.json");
    }

    @Test
    @WithMockUser
    public void testCreateBookMissingTitle() throws Exception {
        BookDto book = BookSample.getDto();
        book.setTitle(null);
        String bookJson = objectMapper.writeValueAsString(book);

        assertThat(mockMvc.post().with(csrf()).uri("/books")
                .content(bookJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @WithMockUser
    public void testCreateBookMissingAuthor() throws Exception {
        BookDto book = BookSample.getDto();
        book.setAuthor(null);
        String bookJson = objectMapper.writeValueAsString(book);

        assertThat(mockMvc.post().with(csrf()).uri("/books")
                .content(bookJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }
}