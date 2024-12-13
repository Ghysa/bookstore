package com.capgemini.bookstore.service;

import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Order;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.repository.CartRepository;
import com.capgemini.bookstore.repository.OrderRepository;
import com.capgemini.bookstore.sample.CartSample;
import com.capgemini.bookstore.sample.OrderSample;
import com.capgemini.bookstore.sample.UserSample;
import com.capgemini.bookstore.service.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testGetOrderById() {
        int id = OrderSample.ID;
        Order entity = OrderSample.getEntity();
        OrderDto dto = OrderSample.getDto();

        when(orderRepository.getReferenceById(id)).thenReturn(entity);
        when(orderMapper.toDto(entity)).thenReturn(dto);

        OrderDto result = orderService.getOrderById(id);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    public void testCreateOrder() {
        String token = UserSample.generateToken();
        int cartId = CartSample.ID;
        Order entity = OrderSample.getEntity();
        OrderDto dto = OrderSample.getDto();
        Cart cart = CartSample.getEntity();

        when(cartRepository.getReferenceById(cartId)).thenReturn(cart);
        when(orderMapper.toEntity(cart, UserSample.EMAIL)).thenReturn(entity);
        when(orderRepository.save(entity)).thenReturn(entity);
        when(orderMapper.toDto(entity)).thenReturn(dto);

        OrderDto result = orderService.createOrder(cartId, token);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(dto);
    }
}
