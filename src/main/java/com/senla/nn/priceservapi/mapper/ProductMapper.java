package com.senla.nn.priceservapi.mapper;

import com.senla.nn.priceservapi.dto.ViewProductDTO;
import com.senla.nn.priceservapi.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BrandMapper.class, CategoryMapper.class, ShopMapper.class})
public interface ProductMapper {

    ViewProductDTO toView(Product product);
    List<ViewProductDTO> toView(List<Product> products);
}
