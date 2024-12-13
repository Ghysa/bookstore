package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.entity.Order;
import com.capgemini.bookstore.generated.model.OrderDto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderSample {

    public static final int ID = 89;

    public static Order getEntity() {
        Order order = new Order();
        order.setId(ID);
        order.setUser(UserSample.getEntity());
        order.setOrderDate(LocalDateTime.now());
        order.setItems(List.of(ItemSample.getEntity()));
        return order;
    }

    public static OrderDto getDto() {
        OrderDto order = new OrderDto();
        order.setId(ID);
        order.setUser(UserSample.EMAIL);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(List.of(ItemSample.getDto()));
        order.setTotal(BookSample.PRICE);
        return order;
    }
}
