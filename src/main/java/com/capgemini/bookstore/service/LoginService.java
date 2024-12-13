package com.capgemini.bookstore.service;

import com.capgemini.bookstore.generated.model.LoginDto;
import com.capgemini.bookstore.generated.model.TokenDto;
import com.capgemini.bookstore.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public static final String TOKEN_TYPE = "Bearer";
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;

    public LoginService(AuthenticationProvider authenticationProvider, UserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    public TokenDto loginAndCreateJwt(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        String jwt = JwtUtil.generateToken(loginDto.getEmail(), authentication.getAuthorities());

        TokenDto token = new TokenDto();
        token.setAccessToken(jwt);
        token.setTokenType(TOKEN_TYPE);
        token.setExpiresIn(String.valueOf(JwtUtil.EXPIRATION_TIME / 1000));
        return token;
    }

    public void verifyJwtAndLogin(String authHeader) {
        String email = JwtUtil.verifyAndExtractUsername(authHeader);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }
}
