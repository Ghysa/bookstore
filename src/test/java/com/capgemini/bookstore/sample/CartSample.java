package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.entity.Cart;
import com.capgemini.bookstore.generated.model.CartDto;

import java.util.List;

public class CartSample {

    public static final int ID = 54;

    public static Cart getEntity() {
        Cart cart = new Cart();
        cart.setId(ID);
        cart.setUser(UserSample.getEntity());
        cart.setItems(List.of(ItemSample.getEntity()));
        return cart;
    }

    public static CartDto getDto() {
        CartDto cart = new CartDto();
        cart.setId(ID);
        cart.setUser(UserSample.EMAIL);
        cart.setItems(List.of(ItemSample.getDto()));
        cart.setTotal(BookSample.PRICE);
        return cart;
    }
}
