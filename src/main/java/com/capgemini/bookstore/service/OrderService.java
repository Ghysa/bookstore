package com.capgemini.bookstore.service;


import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Order;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.repository.CartRepository;
import com.capgemini.bookstore.repository.OrderRepository;
import com.capgemini.bookstore.service.mapper.OrderMapper;
import com.capgemini.bookstore.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository.getReferenceById(id);
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderDto createOrder(int cartId, String authorization) {
        String email = JwtUtil.verifyAndExtractUsername(authorization);
        Cart cart = cartRepository.getReferenceById(cartId);
        Order order = orderMapper.toEntity(cart, email);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }
}
