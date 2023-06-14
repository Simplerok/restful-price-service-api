package com.senla.nn.priceservapi.mapper;

import com.senla.nn.priceservapi.dto.CategoryDTO;
import com.senla.nn.priceservapi.entity.Category;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    @InheritConfiguration
    Category toCategory(CategoryDTO categoryDTO);
}
