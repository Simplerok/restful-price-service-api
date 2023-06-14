package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    ResponseEntity<String> delete(Long shopId);
    Page<CategoryDTO> findAll(Pageable pageable);
    CategoryDTO update(Long shopId, CategoryDTO categoryDTO);
    CategoryDTO getById(Long shopId);
    CategoryDTO getByName(String name);
}
