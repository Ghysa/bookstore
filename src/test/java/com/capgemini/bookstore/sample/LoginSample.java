package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.generated.model.LoginDto;

public class LoginSample {

    public static final String EMAIL = "samghysels@capgemini.com";
    public static final String PASSWORD = "password";

    public static LoginDto getDto() {
        LoginDto login = new LoginDto();
        login.setEmail(EMAIL);
        login.setPassword(PASSWORD);
        return login;
    }
}
