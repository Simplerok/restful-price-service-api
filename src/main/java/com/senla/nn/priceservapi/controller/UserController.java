package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.dto.CreateUserDTO;
import com.senla.nn.priceservapi.dto.ViewUserDTO;
import com.senla.nn.priceservapi.dto.validation.OnUpdate;
import com.senla.nn.priceservapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@RestController()
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name="Контроллер пользователей", description = "Позволяет проводить операции CRUD с пользователями")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Список всех пользователей")
    @Secured(value = {"ROLE_ADMIN"})
    public Page<ViewUserDTO> getAll(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение юзера по Id")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewUserDTO getUser(
            @PathVariable @Parameter(description = "Id пользователя") @Min(value = 0, message = "Id cant be negative")Long id){
        return userService.getById(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление пользователя")
    @Secured(value = {"ROLE_USER"})
    public ViewUserDTO updateUser(
            @Valid @PathVariable @Parameter(description = "Id пользователя")
            @Min(value = 0, message = "Id cant be negative")Long id,
            @RequestBody CreateUserDTO createUserDTO){
        return userService.update(id, createUserDTO);
    }
    @PatchMapping("/{id}/addroles")
    @Operation(summary = "Добавление ролей пользователю")
    @Secured(value = {"ROLE_ADMIN"})
    public ViewUserDTO addRoles(
            @PathVariable @Parameter(description = "Id пользователя")
            @Min(value = 0, message = "Id cant be negative") Long id,
            @RequestBody @Parameter(description = "Id роли")
            @Min(value = 0, message = "Id cant be negative") Long roleId) {
        return userService.addRoles(id, roleId);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<String> deleteUser(
            @PathVariable @Parameter(description = "Id пользователя") @Min(value = 0, message = "Id cant be negative") Long id){
        return userService.delete(id);
    }

}
