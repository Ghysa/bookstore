package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.model.CartDto;
import com.capgemini.bookstore.generated.model.ItemDto;
import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.sample.CartSample;
import com.capgemini.bookstore.sample.ItemSample;
import com.capgemini.bookstore.sample.OrderSample;
import com.capgemini.bookstore.sample.UserSample;
import com.capgemini.bookstore.service.CartService;
import com.capgemini.bookstore.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean(name = "request")
    private HttpServletRequest request;

    private final MockMvcTester mockMvc;
    private final ObjectMapper objectMapper;

    public CartControllerTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = MockMvcTester.create(mockMvc);
        this.objectMapper = objectMapper;
    }

    @Test
    @WithMockUser
    public void testGetCartById() {
        int id = CartSample.ID;
        CartDto cart = CartSample.getDto();

        when(cartService.getCartById(id)).thenReturn(cart);

        assertThat(mockMvc.get().uri("/carts/{id}", id))
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("sample/cart.json");
    }

    @Test
    @WithMockUser
    public void testCreateCart() throws Exception {
        String token = UserSample.generateToken();
        CartDto cart = CartSample.getDto();

        when(request.getHeader("Authorization")).thenReturn(token);
        when(cartService.createCart(token)).thenReturn(cart);

        assertThat(mockMvc.post().with(csrf()).uri("/carts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .isStrictlyEqualTo("sample/cart.json");
    }

    @Test
    @WithMockUser
    public void testAddCartItem() throws Exception {
        int id = CartSample.ID;
        CartDto cart = CartSample.getDto();
        ItemDto item = ItemSample.getDto();
        String itemJson = objectMapper.writeValueAsString(item);

        when(cartService.addCartItem(id, item)).thenReturn(cart);

        assertThat(mockMvc.post().with(csrf()).uri("/carts/{id}/item", id)
                .content(itemJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .isStrictlyEqualTo("sample/cart.json");
    }

    @Test
    @WithMockUser
    public void testCreateOrder() throws Exception {
        int id = CartSample.ID;
        String token = UserSample.generateToken();
        OrderDto order = OrderSample.getDto();
        order.setOrderDate(null);


        when(request.getHeader("Authorization")).thenReturn(token);
        when(orderService.createOrder(id, token)).thenReturn(order);

        assertThat(mockMvc.post().with(csrf()).uri("/carts/{id}/order", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .isStrictlyEqualTo("sample/order.json");
    }
}