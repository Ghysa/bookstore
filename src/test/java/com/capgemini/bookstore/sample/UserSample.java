package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.entity.User;
import com.capgemini.bookstore.generated.model.UserDto;
import com.capgemini.bookstore.util.JwtUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

public class UserSample {

    public static final int ID = 7;
    public static final String EMAIL = "samghysels@capgemini.com";
    public static final String PASSWORD = "password";
    public static final String FIRSTNAME = "Sam";
    public static final String LASTNAME = "Ghysels";

    public static User getEntity() {
        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setFirstname(FIRSTNAME);
        user.setLastname(LASTNAME);
        user.setCreatedDate(LocalDateTime.now());
        return user;
    }

    public static UserDto getDto() {
        UserDto user = new UserDto();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setFirstname(FIRSTNAME);
        user.setLastname(LASTNAME);
        return user;
    }

    public static String generateToken() {
        return "Bearer " + JwtUtil.generateToken(UserSample.EMAIL, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
