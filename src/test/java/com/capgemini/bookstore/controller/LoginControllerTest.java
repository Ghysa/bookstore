package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.model.LoginDto;
import com.capgemini.bookstore.generated.model.TokenDto;
import com.capgemini.bookstore.sample.LoginSample;
import com.capgemini.bookstore.sample.TokenSample;
import com.capgemini.bookstore.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(value = LoginController.class,
        excludeAutoConfiguration = { SecurityAutoConfiguration.class})
class LoginControllerTest {

    @MockitoBean
    private LoginService service;

    private final MockMvcTester mockMvc;
    private final ObjectMapper objectMapper;

    public LoginControllerTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = MockMvcTester.create(mockMvc);
        this.objectMapper = objectMapper;
    }

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception {
        LoginDto login = LoginSample.getDto();
        String userJson = objectMapper.writeValueAsString(login);
        TokenDto token = TokenSample.getDto();

        when(service.loginAndCreateJwt(login)).thenReturn(token);

        assertThat(mockMvc.post().with(csrf()).uri("/login")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("sample/token.json");
    }
}