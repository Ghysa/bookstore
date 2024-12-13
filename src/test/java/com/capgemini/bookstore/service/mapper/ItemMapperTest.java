package com.capgemini.bookstore.service.mapper;

import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.repository.BookRepository;
import com.capgemini.bookstore.sample.BookSample;
import com.capgemini.bookstore.sample.ItemSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemMapperTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ItemMapper itemMapper;

    @Test
    public void testToDto() {
        Item entity = ItemSample.getEntity();
        ItemDto expected = ItemSample.getDto();

        ItemDto result = itemMapper.toDto(entity);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testToEntity() {
        ItemDto dto = ItemSample.getDto();
        Item expected = ItemSample.getEntity();
        Book book = BookSample.getEntity();

        when(bookRepository.getReferenceById(BookSample.ID)).thenReturn(book);

        Item result = itemMapper.toEntity(dto);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testToEntityFromEntity() {
        Item item = ItemSample.getEntity();

        Item result = itemMapper.toEntity(item);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(item);
        assertThat(result.getId()).isNull();
    }
}
