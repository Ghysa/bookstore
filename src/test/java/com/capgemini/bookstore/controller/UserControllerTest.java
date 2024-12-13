package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.model.UserDto;
import com.capgemini.bookstore.sample.UserSample;
import com.capgemini.bookstore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService service;

    private final MockMvcTester mockMvc;
    private final ObjectMapper objectMapper;

    public UserControllerTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = MockMvcTester.create(mockMvc);
        this.objectMapper = objectMapper;
    }

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception {
        UserDto user = UserSample.getDto();
        String userJson = objectMapper.writeValueAsString(user);
        UserDto returnedUser = UserSample.getDto();
        returnedUser.setPassword(null);

        when(service.createUser(user)).thenReturn(returnedUser);

        assertThat(mockMvc.post().with(csrf()).uri("/users")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .isStrictlyEqualTo("sample/user.json");
    }

    @Test
    @WithMockUser
    public void testCreateUserMissingEmail() throws Exception {
        UserDto user = UserSample.getDto();
        user.setEmail(null);
        String userJson = objectMapper.writeValueAsString(user);

        assertThat(mockMvc.post().with(csrf()).uri("/users")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @WithMockUser
    public void testCreateUserMissingPassword() throws Exception {
        UserDto user = UserSample.getDto();
        user.setPassword(null);
        String userJson = objectMapper.writeValueAsString(user);

        assertThat(mockMvc.post().with(csrf()).uri("/users")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .exchange())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @WithMockUser
    public void testGetUserinfo() {
        String token = UserSample.generateToken();
        UserDto user = UserSample.getDto();
        user.setPassword(null);

        when(service.getUser(token)).thenReturn(user);

        assertThat(mockMvc.get().uri("/userinfo").header("Authorization", token))
                .hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo("sample/user.json");
    }
}