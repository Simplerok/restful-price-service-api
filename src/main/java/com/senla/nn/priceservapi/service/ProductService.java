package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.CreateProductDTO;
import com.senla.nn.priceservapi.dto.ViewProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ViewProductDTO createProduct(CreateProductDTO createProductDTO);
    ResponseEntity<String> delete(Long productId);
    ViewProductDTO update(Long productId, CreateProductDTO createProductDTO);
    ViewProductDTO getById(Long productId);
    Page<ViewProductDTO> getByFilter(String name, Long categoryId, Long brandId, Pageable pageable);
    ResponseEntity<String> saveProductsDataFromExcel(MultipartFile multipartFile);
    ResponseEntity<String> saveProductsDataFromCSV(MultipartFile multipartFile);

}
