package com.capgemini.bookstore.service.mapper;

import com.capgemini.bookstore.entity.User;
import com.capgemini.bookstore.generated.model.UserDto;
import com.capgemini.bookstore.sample.UserSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void testToDto() {
        User entity = UserSample.getEntity();
        UserDto expected = UserSample.getDto();

        UserDto result = userMapper.toDto(entity);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(expected);
        assertThat(result.getPassword()).isNullOrEmpty();
    }

    @Test
    public void testToEntity() {
        UserDto dto = UserSample.getDto();
        User expected = UserSample.getEntity();
        String encryptedPassword = "encrypted";

        when(passwordEncoder.encode(UserSample.PASSWORD)).thenReturn(encryptedPassword);

        User result = userMapper.toEntity(dto);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "password", "createdDate")
                .isEqualTo(expected);
        assertThat(result.getPassword()).isEqualTo(encryptedPassword);
        assertThat(result.getCreatedDate()).isCloseTo(expected.getCreatedDate(), within(1, ChronoUnit.SECONDS));
    }
}
