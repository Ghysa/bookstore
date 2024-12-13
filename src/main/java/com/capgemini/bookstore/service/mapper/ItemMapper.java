package com.capgemini.bookstore.service.mapper;


import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    private final BookRepository bookRepository;

    public ItemMapper(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ItemDto toDto(Item entity) {
        ItemDto dto = new ItemDto();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setBookId(entity.getBook().getId());
        return dto;
    }

    public Item toEntity(ItemDto dto) {
        Item entity = new Item();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        Book book = bookRepository.getReferenceById(dto.getBookId());
        entity.setBook(book);
        return entity;
    }

    public Item toEntity(Item item) {
        Item entity = new Item();
        entity.setQuantity(item.getQuantity());
        entity.setBook(item.getBook());
        return entity;
    }
}
