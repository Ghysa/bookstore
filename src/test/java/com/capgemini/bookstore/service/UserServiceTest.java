package com.capgemini.bookstore.service;

import com.capgemini.bookstore.entity.User;
import com.capgemini.bookstore.generated.model.UserDto;
import com.capgemini.bookstore.repository.UserRepository;
import com.capgemini.bookstore.sample.UserSample;
import com.capgemini.bookstore.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUser() {
        String jwt = UserSample.generateToken();
        User entity = UserSample.getEntity();
        UserDto dto = UserSample.getDto();

        when(userRepository.findUserByEmail(UserSample.EMAIL)).thenReturn(entity);
        when(userMapper.toDto(entity)).thenReturn(dto);

        UserDto userDto = userService.getUser(jwt);

        assertThat(userDto).isEqualTo(dto);
    }

    @Test
    public void testCreateUser() {
        User entity = UserSample.getEntity();
        UserDto dto = UserSample.getDto();

        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toDto(entity)).thenReturn(dto);

        UserDto result = userService.createUser(dto);

        assertThat(result).isEqualTo(dto);
    }
}
