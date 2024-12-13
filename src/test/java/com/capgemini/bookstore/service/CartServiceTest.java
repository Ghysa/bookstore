package com.capgemini.bookstore.service;

import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.generated.model.CartDto;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.repository.CartRepository;
import com.capgemini.bookstore.sample.CartSample;
import com.capgemini.bookstore.sample.ItemSample;
import com.capgemini.bookstore.sample.UserSample;
import com.capgemini.bookstore.service.mapper.CartMapper;
import com.capgemini.bookstore.service.mapper.ItemMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testGetCartById() {
        int id = CartSample.ID;
        Cart entity = CartSample.getEntity();
        CartDto dto = CartSample.getDto();

        when(cartRepository.getReferenceById(id)).thenReturn(entity);
        when(cartMapper.toDto(entity)).thenReturn(dto);

        CartDto result = cartService.getCartById(id);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    public void testAddCartItem() {
        int id = CartSample.ID;
        Cart entity = CartSample.getEntity();
        CartDto dto = CartSample.getDto();
        Item item = ItemSample.getEntity();
        ItemDto itemDto = ItemSample.getDto();

        when(cartRepository.getReferenceById(id)).thenReturn(entity);
        when(itemMapper.toEntity(itemDto)).thenReturn(item);
        when(cartRepository.save(entity)).thenReturn(entity);
        when(cartMapper.toDto(entity)).thenReturn(dto);

        CartDto result = cartService.addCartItem(id, itemDto);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    public void testCreateCart() {
        Cart entity = CartSample.getEntity();
        CartDto dto = CartSample.getDto();

        when(cartMapper.toEntity(UserSample.EMAIL)).thenReturn(entity);
        when(cartRepository.save(any(Cart.class))).thenReturn(entity);
        when(cartMapper.toDto(entity)).thenReturn(dto);

        CartDto result = cartService.createCart(UserSample.generateToken());

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(dto);
    }
}
