package com.senla.nn.priceservapi.service.impl;

import com.senla.nn.priceservapi.dto.CreateBrandDTO;
import com.senla.nn.priceservapi.dto.ViewBrandDTO;
import com.senla.nn.priceservapi.entity.Brand;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.BrandMapper;
import com.senla.nn.priceservapi.repository.BrandRepository;
import com.senla.nn.priceservapi.service.BrandService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
@Validated
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;


    @Override
    public ViewBrandDTO createBrand(CreateBrandDTO createBrandDTO) {
        log.info("In BrandServiceImpl method <createBrand> create brand={}", createBrandDTO);
        Optional<Brand> isExist = brandRepository.getByName(createBrandDTO.getName());
        if (isExist.isPresent()) throw new AlreadyExistsException(String.format("Brand with name=%s is already exist", createBrandDTO.getName()));
        Brand brand = brandMapper.toBrand(createBrandDTO);
        brandRepository.save(brand);
        log.info("In BrandServiceImpl brand={} successfully created", createBrandDTO);
        return brandMapper.toView(brand);
    }

    @Override
    public ResponseEntity<String> delete(Long brandId) {
        log.info("In BrandServiceImpl method <delete> is managed to delete brand with id={}",brandId);
        brandRepository.deleteById(brandId);
        log.info("In BrandServiceImpl brand with id={} is deleted successfully",brandId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Brand with id=%s is deleted successfully", brandId));
    }

    @Override
    public Page<ViewBrandDTO> findAll(Pageable pageable) {
        log.info("In BrandServiceImpl method <findAll> is getting all brands");
        Page<Brand> brands = brandRepository.findAll(pageable);
        return brands.map(brandMapper :: toView);
    }

    @Override
    public ViewBrandDTO update(Long brandId, CreateBrandDTO createBrandDTO) {
        log.info("In BrandServiceImpl method <update> is updating brand={} with id={}", createBrandDTO, brandId);
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id=%s not found", brandId)));
        brand.setName(createBrandDTO.getName());
        brandRepository.save(brand);
        log.info("In BrandServiceImpl brand={} successfully updated", createBrandDTO);
        return brandMapper.toView(brand);
    }

    @Override
    public ViewBrandDTO getById(Long brandId) {
        log.info("In BrandServiceImpl method <getById> is getting brand with id={}", brandId);
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id=%s not found", brandId)));
        return brandMapper.toView(brand);
    }

    @Override
    public ViewBrandDTO getByName(String name) {
        log.info("In BrandServiceImpl method <getByName> is getting brand with name={}", name);
        Brand brand = brandRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with name=%s not found", name)));
        return brandMapper.toView(brand);
    }
}
