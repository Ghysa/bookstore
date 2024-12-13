package com.capgemini.bookstore.service.mapper;

import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.entity.Order;
import com.capgemini.bookstore.entity.User;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.repository.UserRepository;
import com.capgemini.bookstore.sample.CartSample;
import com.capgemini.bookstore.sample.ItemSample;
import com.capgemini.bookstore.sample.OrderSample;
import com.capgemini.bookstore.sample.UserSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    public void testToDto() {
        Order entity = OrderSample.getEntity();
        OrderDto expected = OrderSample.getDto();
        Item item = ItemSample.getEntity();
        ItemDto itemDto = ItemSample.getDto();

        when(itemMapper.toDto(item)).thenReturn(itemDto);

        OrderDto result = orderMapper.toDto(entity);

        assertThat(result).usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }



    @Test
    public void testToEntity() {
        Cart cart = CartSample.getEntity();
        Item item = ItemSample.getEntity();
        Order expected = OrderSample.getEntity();
        User user = UserSample.getEntity();

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        when(itemMapper.toEntity(item)).thenReturn(item);

        Order result = orderMapper.toEntity(cart, user.getEmail());

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }
}
