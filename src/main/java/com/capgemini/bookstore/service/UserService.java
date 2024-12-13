package com.capgemini.bookstore.service;

import com.capgemini.bookstore.entity.User;
import com.capgemini.bookstore.generated.model.UserDto;
import com.capgemini.bookstore.repository.UserRepository;
import com.capgemini.bookstore.service.mapper.UserMapper;
import com.capgemini.bookstore.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto getUser(String authorization) {
        String email = JwtUtil.verifyAndExtractUsername(authorization);
        User user = userRepository.findUserByEmail(email);
        return userMapper.toDto(user);
    }
}
