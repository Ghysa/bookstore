package com.capgemini.bookstore.service.mapper;

import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.generated.model.BookDto;
import com.capgemini.bookstore.sample.BookSample;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookMapperTest {

    private final BookMapper bookMapper = new BookMapper();

    @Test
    public void testToDto() {
        Book entity = BookSample.getEntity();
        BookDto expected = BookSample.getDto();

        BookDto result = bookMapper.toDto(entity);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testToEntity() {
        BookDto dto = BookSample.getDto();
        Book expected = BookSample.getEntity();

        Book result = bookMapper.toEntity(dto);

        assertThat(result).isEqualTo(expected);
    }
}
