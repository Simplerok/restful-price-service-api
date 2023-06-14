package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.CreateBrandDTO;
import com.senla.nn.priceservapi.dto.ViewBrandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BrandService {
    ViewBrandDTO createBrand(CreateBrandDTO createBrandDTO);
    ResponseEntity<String> delete(Long brandId);
    Page<ViewBrandDTO> findAll(Pageable pageable);
    ViewBrandDTO update(Long brandId, CreateBrandDTO createBrandDTO);
    ViewBrandDTO getById(Long brandId);
    ViewBrandDTO getByName(String name);
}
