package com.capgemini.bookstore.service.mapper;


import com.capgemini.bookstore.entity.Book;
import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.entity.Order;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {

    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

    public OrderMapper(ItemMapper itemMapper, UserRepository userRepository) {
        this.itemMapper = itemMapper;
        this.userRepository = userRepository;
    }

    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setUser(entity.getUser().getEmail());
        dto.setOrderDate(entity.getOrderDate());
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

    public Order toEntity(Cart cart, String email) {
        Order entity = new Order();
        entity.setOrderDate(LocalDateTime.now());
        entity.setUser(userRepository.findUserByEmail(email));
        List<Item> items = cart.getItems().stream()
                .map(itemMapper::toEntity)
                .toList();
        entity.setItems(items);
        return entity;
    }
}
