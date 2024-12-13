package com.capgemini.bookstore.service.mapper;


import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.generated.model.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setPrice(entity.getPrice());
        dto.setReleaseDate(entity.getReleaseDate());
        return dto;
    }

    public Book toEntity(BookDto dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setPrice(dto.getPrice());
        entity.setReleaseDate(dto.getReleaseDate());
        return entity;
    }
}
