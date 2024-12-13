package com.capgemini.bookstore.service;

import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.generated.model.CartDto;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.repository.CartRepository;
import com.capgemini.bookstore.service.mapper.CartMapper;
import com.capgemini.bookstore.service.mapper.ItemMapper;
import com.capgemini.bookstore.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ItemMapper itemMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper, ItemMapper itemMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.itemMapper = itemMapper;
    }

    public CartDto getCartById(Integer id) {
        Cart cart = cartRepository.getReferenceById(id);
        return cartMapper.toDto(cart);
    }

    @Transactional
    public CartDto createCart(String authorization) {
        String email = JwtUtil.verifyAndExtractUsername(authorization);
        Cart cart = cartMapper.toEntity(email);
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    @Transactional
    public CartDto addCartItem(Integer id, ItemDto itemDto) {
        Cart cart = cartRepository.getReferenceById(id);
        List<Item> items = new ArrayList<>(cart.getItems());
        items.add(itemMapper.toEntity(itemDto));
        cart.setItems(items);
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }
}
