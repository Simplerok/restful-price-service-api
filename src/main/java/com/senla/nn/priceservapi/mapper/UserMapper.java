package com.senla.nn.priceservapi.mapper;

import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.entity.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserDTO createUserDTO);
    User toUser(ViewUserDTO viewUserDTO);

    ViewUserDTO toView(User user);
}
