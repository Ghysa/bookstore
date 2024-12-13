package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.model.OrderDto;
import com.capgemini.bookstore.sample.OrderSample;
import com.capgemini.bookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockitoBean
    private OrderService service;

    private final MockMvcTester mockMvc;

    public OrderControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = MockMvcTester.create(mockMvc);
    }

    @Test
    @WithMockUser
    public void testGetUserinfo() {
        int id = OrderSample.ID;
        OrderDto order = OrderSample.getDto();
        order.setOrderDate(null);

        when(service.getOrderById(id)).thenReturn(order);

        assertThat(mockMvc.get().uri("/orders/{id}", id))
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("sample/order.json");
    }
}