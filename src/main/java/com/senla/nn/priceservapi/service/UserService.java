package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ViewUserDTO createUser(CreateUserDTO createUserDTO);
    ViewUserDTO addRoles(Long userId, Long roleId);
    ViewUserDTO update(Long userId, CreateUserDTO createUserDTO);
    ResponseEntity<String> delete(Long userId);
    Page<ViewUserDTO> findAll(Pageable pageable);
    ViewUserDTO getById(Long userId);
    User getByUsername(String name);
}
