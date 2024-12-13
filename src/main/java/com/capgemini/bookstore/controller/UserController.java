package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.api.UserinfoApi;
import com.capgemini.bookstore.generated.api.UsersApi;
import com.capgemini.bookstore.generated.model.UserDto;
import com.capgemini.bookstore.service.UserService;
import com.capgemini.bookstore.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserController implements UsersApi, UserinfoApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        URI location = Util.createLocationURIfromContext("/userinfo");
        return ResponseEntity.created(location)
                .body(user);
    }

    @Override
    public ResponseEntity<UserDto> getUserInfo(String authorization) {
        UserDto user = userService.getUser(authorization);
        return ResponseEntity.ok(user);
    }
}
