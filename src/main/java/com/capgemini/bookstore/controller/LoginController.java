package com.capgemini.bookstore.controller;

import com.capgemini.bookstore.generated.api.LoginApi;
import com.capgemini.bookstore.generated.model.LoginDto;
import com.capgemini.bookstore.generated.model.TokenDto;
import com.capgemini.bookstore.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginApi {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<TokenDto> login(LoginDto loginDto) {
        TokenDto token = loginService.loginAndCreateJwt(loginDto);
        return ResponseEntity.ok(token);
    }
}
