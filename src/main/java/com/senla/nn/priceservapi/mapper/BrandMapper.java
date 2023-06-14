package com.senla.nn.priceservapi.mapper;

import com.senla.nn.priceservapi.dto.CreateBrandDTO;
import com.senla.nn.priceservapi.dto.ViewBrandDTO;
import com.senla.nn.priceservapi.entity.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    ViewBrandDTO toView(Brand brand);
    Brand toBrand(CreateBrandDTO createBrandDTO);
    Brand toBrand(ViewBrandDTO showBrandDTO);
    List<ViewBrandDTO> toView(List<Brand> brands);
}
