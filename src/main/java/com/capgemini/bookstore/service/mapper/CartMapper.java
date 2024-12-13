package com.capgemini.bookstore.service.mapper;


import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.generated.model.CartDto;
import com.capgemini.bookstore.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartMapper {

    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

    public CartMapper(ItemMapper itemMapper, UserRepository userRepository) {
        this.itemMapper = itemMapper;
        this.userRepository = userRepository;
    }

    public CartDto toDto(Cart entity) {
        CartDto dto = new CartDto();
        dto.setId(entity.getId());
        dto.setUser(entity.getUser().getEmail());
        dto.setItems(entity.getItems().stream()
                .map(itemMapper::toDto)
                .toList());
        dto.setTotal(entity.getItems().stream()
                .map(item -> {
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                    BigDecimal price = item.getBook().getPrice();
                    return quantity.multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return dto;
    }

    public Cart toEntity(String email) {
        Cart entity = new Cart();
        entity.setUser(userRepository.findUserByEmail(email));
        return entity;
    }
}
