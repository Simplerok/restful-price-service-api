package com.senla.nn.priceservapi.service.impl;

import com.senla.nn.priceservapi.dto.CategoryDTO;
import com.senla.nn.priceservapi.entity.Category;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.CategoryMapper;
import com.senla.nn.priceservapi.repository.CategoryRepository;
import com.senla.nn.priceservapi.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Data
@AllArgsConstructor
@Validated
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        log.info("In CategoryServiceImpl method <createCategory> create category={}",categoryDTO);
        Optional<Category> isExist = categoryRepository.getByName(categoryDTO.getName());
        if (isExist.isPresent()) throw new AlreadyExistsException(String.format("Category with name=%s is already exist", categoryDTO.getName()));
        Category category = categoryMapper.toCategory(categoryDTO);
        categoryRepository.save(category);
        log.info("In CategoryServiceImpl category={} successfully created",categoryDTO);
        return categoryMapper.toDTO(category);
    }

    @Override
    public ResponseEntity<String> delete(Long categoryId) {
        log.info("In CategoryServiceImpl method <delete> is managed to delete category with id={}",categoryId);
        categoryRepository.deleteById(categoryId);
        log.info("In CategoryServiceImpl category with id={} is deleted successfully",categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Category with id=%s is deleted successfully", categoryId));
    }

    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.info("In CategoryServiceImpl method <findAll> is getting all categories");
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper :: toDTO);
    }

    @Override
    public CategoryDTO update(Long categoryId,CategoryDTO categoryDTO) {
        log.info("In CategoryServiceImpl method <update> is updating category={} with id={}",categoryDTO, categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%s not found", categoryId)));
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        log.info("In CategoryServiceImpl category={} successfully updated",categoryDTO);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO getById(Long categoryId) {
        log.info("In CategoryServiceImpl method <getById> is getting category with id={}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%s not found", categoryId)));
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO getByName(String name) {
        log.info("In CategoryServiceImpl method <getByName> is getting category with name={}", name);
        Category category = categoryRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Category with name=%s not found", name)));
        return categoryMapper.toDTO(category);
    }
}
