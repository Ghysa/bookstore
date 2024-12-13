package com.capgemini.bookstore.service.mapper;

import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.entity.User;
import com.capgemini.bookstore.generated.model.CartDto;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.repository.UserRepository;
import com.capgemini.bookstore.sample.CartSample;
import com.capgemini.bookstore.sample.ItemSample;
import com.capgemini.bookstore.sample.UserSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartMapperTest {

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartMapper cartMapper;

    @Test
    public void testToDto() {
        Cart entity = CartSample.getEntity();
        CartDto expected = CartSample.getDto();
        Item item = ItemSample.getEntity();
        ItemDto itemDto = ItemSample.getDto();

        when(itemMapper.toDto(item)).thenReturn(itemDto);

        CartDto result = cartMapper.toDto(entity);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void testToEntity() {
        User user = UserSample.getEntity();

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        Cart result = cartMapper.toEntity(user.getEmail());

        assertThat(result.getUser()).usingRecursiveComparison()
                .isEqualTo(user);
    }
}
