package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.generated.model.TokenDto;

public class TokenSample {

    public static final String ACCESS_TOKEN = "Token";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String EXPIRES_IN = "600";

    public static TokenDto getDto() {
        TokenDto login = new TokenDto();
        login.setAccessToken(ACCESS_TOKEN);
        login.setTokenType(TOKEN_TYPE);
        login.setExpiresIn(EXPIRES_IN);
        return login;
    }
}
