package com.senla.nn.priceservapi.service.impl;

import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.entity.Role;
import com.senla.nn.priceservapi.entity.User;
import com.senla.nn.priceservapi.exception.AccessDeniedException;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.UserMapper;
import com.senla.nn.priceservapi.repository.RoleRepository;
import com.senla.nn.priceservapi.repository.UserRepository;
import com.senla.nn.priceservapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Data
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ViewUserDTO createUser(CreateUserDTO createUserDTO) {
        log.info("In UserServiceImpl method <createUser> create user={}", createUserDTO);
        if(userRepository.existsByUsername(createUserDTO.getUsername())){
            throw new AlreadyExistsException(String.format("User with  username=%s is already exist", createUserDTO.getUsername()));
        }
        Role roleUser = roleRepository.getByName("ROLE_USER");
        User user = userMapper.toUser(createUserDTO);
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        userRepository.save(user);
        log.info("In UserServiceImpl user={} successfully created", createUserDTO);
        return userMapper.toView(user);
    }

    @Override
    @Transactional
    public ViewUserDTO addRoles(Long userId, Long roleId) {
        log.info("In UserServiceImpl add role with id={}",roleId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(String.format("Role with id=%s not found", roleId)));
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("In UserServiceImpl adding role with id={} is successful",roleId);
        return userMapper.toView(user);
    }


    @Override
    public ViewUserDTO update(Long userId, CreateUserDTO createUserDTO) {
        log.info("In UserServiceImpl method <update> is updating user={} with id={}", createUserDTO, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));
        if(!userId.equals(user.getId())) {
            throw new AccessDeniedException(String.format("User with id=%s can't update another user", userId));
        }
        user.setUsername(createUserDTO.getUsername());
        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());
        user.setBirthDate(createUserDTO.getBirthDate());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        userRepository.save(user);
        log.info("In UserServiceImpl user={} successfully updated", createUserDTO);
        return userMapper.toView(user);
    }

    @Override
    public ResponseEntity<String> delete(Long userId) {
        log.info("In UserServiceImpl method <delete> is managed to delete user with id={}",userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));
        userRepository.deleteById(userId);
        log.info("In UserServiceImpl user with id={} is deleted successfully",userId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("User with id=%s is deleted successfully", userId));
    }

    @Override
    public Page<ViewUserDTO> findAll(Pageable pageable) {
        log.info("In UserServiceImpl method <findAll> is getting all users");
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper :: toView);
    }

    @Override
    public ViewUserDTO getById(Long userId) {
        log.info("In UserServiceImpl method <getById> is getting user with id={}",userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s not found", userId)));
        return userMapper.toView(user);
    }

    @Override
    public User getByUsername(String name) {
        log.info("In UserServiceImpl method <getByUsername> is getting user with name={}",name);
        User user = userRepository.getByUsername(name)
                .orElseThrow(() -> new NotFoundException(String.format("User with name=%s not found", name)));
        return user;
    }


}
