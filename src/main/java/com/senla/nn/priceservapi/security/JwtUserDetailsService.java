package com.senla.nn.priceservapi.security;

import com.senla.nn.priceservapi.entity.User;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new NotFoundException(String.format("User with name=%s not found", username));
        }
        log.info("In JwtUserDetailsService method <loadUserByUsername> successfully loaded user with username={}",username);
        return JwtEntityFactory.create(user);
    }
}
