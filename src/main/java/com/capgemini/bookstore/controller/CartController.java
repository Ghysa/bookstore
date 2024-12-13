package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.api.CartsApi;
import com.capgemini.bookstore.generated.model.CartDto;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.service.CartService;
import com.capgemini.bookstore.service.OrderService;
import com.capgemini.bookstore.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CartController implements CartsApi {

    private final CartService cartService;
    private final OrderService orderService;
    private final HttpServletRequest request;

    public CartController(CartService cartService, OrderService orderService, HttpServletRequest request) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.request = request;
    }

    @Override
    public ResponseEntity<CartDto> getCart(Integer id) {
        CartDto cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<CartDto> createCart() {
        String authorization = request.getHeader("Authorization");
        CartDto createdCart = cartService.createCart(authorization);
        URI location = Util.createLocationURI(createdCart.getId());
        return ResponseEntity.created(location)
                .body(createdCart);
    }

    @Override
    public ResponseEntity<CartDto> addCartItem(Integer id, ItemDto itemDto) {
        CartDto cart = cartService.addCartItem(id, itemDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cart);
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(Integer id) {
        String authorization = request.getHeader("Authorization");
        OrderDto createdOrder = orderService.createOrder(id, authorization);
        URI location = Util.createLocationURIfromContext("/orders/{id}/", createdOrder.getId());
        return ResponseEntity.created(location)
                .body(createdOrder);
    }
}
