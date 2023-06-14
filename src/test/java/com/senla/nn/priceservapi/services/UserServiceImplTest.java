package com.senla.nn.priceservapi.services;

import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.entity.Brand;
import com.senla.nn.priceservapi.entity.User;
import com.senla.nn.priceservapi.mapper.UserMapper;
import com.senla.nn.priceservapi.repository.UserRepository;
import com.senla.nn.priceservapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldCreateUserSuccessfully(){
        //given
        Long userId = 1L;
        User user = User.builder()
                .id(1L)
                .username("Ganza25")
                .name("Макар")
                .surname("Макаревич")
                .birthDate(LocalDate.of(1985,5,14))
                .email("Makariy145@gmail.com")
                .password("QwE123.!23")
                .build();
        CreateUserDTO updateUser = CreateUserDTO.builder()
                .username("SnoopDog")
                .name("Максим")
                .surname("Будапештович")
                .birthDate(LocalDate.of(1994,8,28))
                .email("Makariy145@gmail.com")
                .password("Qiwi")
                .build();
        ViewUserDTO viewUser = ViewUserDTO.builder()
                .id(1L)
                .username("SnoopDog")
                .name("Максим")
                .surname("Будапештович")
                .birthDate(LocalDate.of(1994,8,28))
                .email("Makariy145@gmail.com")
                .build();
        when(userRepository.save(user))
                .thenReturn(User.builder()
                        .username("SnoopDog")
                        .name("Максим")
                        .surname("Будапештович")
                        .birthDate(LocalDate.of(1994,8,28))
                        .email("Makariy145@gmail.com")
                        .password("Qiwi")
                        .build());
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.encode(updateUser.getPassword())).thenReturn("Qiwi");
        when(userMapper.toView(any(User.class))).thenReturn(viewUser);



        //when
        ViewUserDTO updatedUser = userService.update(userId, updateUser);

        //then
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("SnoopDog", updatedUser.getUsername());
        Assertions.assertEquals("Максим", updatedUser.getName());
        Mockito.verify(userRepository).save(user);

    }
}
