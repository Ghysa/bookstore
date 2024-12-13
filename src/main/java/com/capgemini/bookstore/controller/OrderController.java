package com.capgemini.bookstore.controller;


import com.capgemini.bookstore.generated.api.OrdersApi;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDto> getOrderById(Integer id) {
        OrderDto book = orderService.getOrderById(id);
        return ResponseEntity.ok(book);
    }
}
