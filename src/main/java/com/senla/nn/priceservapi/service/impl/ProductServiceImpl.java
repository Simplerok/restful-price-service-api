package com.senla.nn.priceservapi.service.impl;

import com.senla.nn.priceservapi.dto.CreateProductDTO;
import com.senla.nn.priceservapi.dto.ViewProductDTO;
import com.senla.nn.priceservapi.entity.Brand;
import com.senla.nn.priceservapi.entity.Category;
import com.senla.nn.priceservapi.entity.Product;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.ProductMapper;
import com.senla.nn.priceservapi.repository.BrandRepository;
import com.senla.nn.priceservapi.repository.CategoryRepository;
import com.senla.nn.priceservapi.repository.ProductRepository;
import com.senla.nn.priceservapi.service.FileDownloadUtil;
import com.senla.nn.priceservapi.service.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;


    @Override
    @Transactional
    public ViewProductDTO createProduct(CreateProductDTO createProductDTO) {
        log.info("In ProductServiceImpl method <createProduct> create product={}", createProductDTO);

        if (!productRepository.existsByNameAndBrandId(createProductDTO.getName(), createProductDTO.getBrandId())) {
            throw new AlreadyExistsException(String.format("Product with such parameters=%s is already exist", createProductDTO.getName()));
        }

        Brand brand = brandRepository.findById(createProductDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id=%s not found", createProductDTO.getBrandId())));
        Category category = categoryRepository.findById(createProductDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%s not found", createProductDTO.getCategoryId())));
        Product product = Product.builder()
                        .name(createProductDTO.getName())
                                .brand(brand)
                                        .category(category)
                                                        .build();
        productRepository.save(product);
        log.info("In CategoryServiceImpl category={} successfully created", createProductDTO);
        return productMapper.toView(product);
    }

    @Override
    public ResponseEntity<String> delete(Long productId) {
        log.info("In ProductServiceImpl method <delete> is managed to delete product with id={}",productId);
        productRepository.deleteById(productId);
        log.info("In ProductServiceImpl product with id={} is deleted successfully",productId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Product with id=%s is deleted successfully", productId));
    }

    @Override
    @Transactional
    public ViewProductDTO update(Long productId, CreateProductDTO createProductDTO) {
        log.info("In ProductServiceImpl method <update> is updating product={} with id={}", createProductDTO, productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id=%s not found", productId)));
        Brand brand = brandRepository.findById(createProductDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id=%s not found", createProductDTO.getBrandId())));
        Category category = categoryRepository.findById(createProductDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%s not found", createProductDTO.getCategoryId())));
        product.setName(createProductDTO.getName());
        product.setBrand(brand);
        product.setCategory(category);
        productRepository.save(product);
        log.info("In ProductServiceImpl product={} successfully updated", createProductDTO);
        return productMapper.toView(product);
    }

    @Override
    public ViewProductDTO getById(Long productId) {
        log.info("In ProductServiceImpl method <getById> is getting product with id={}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id=%s not found", productId)));
        return productMapper.toView(product);
    }

    @Override
    public Page<ViewProductDTO> getByFilter(String name, Long categoryId, Long brandId, Pageable pageable) {
        log.info("In ProductServiceImpl method <getByFilter> is getting filtered products");
        Page<Product> products;

        if (categoryId != null && brandId != null && name == null) {
            if(!categoryRepository.existsById(categoryId)){
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            products = productRepository.findAllByCategory_IdAndBrand_Id(categoryId, brandId, pageable);
        } else if (categoryId != null && brandId == null && name == null) {
            if(!categoryRepository.existsById(categoryId)){
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            products = productRepository.findAllByCategory_Id(categoryId, pageable);
        } else if (categoryId == null && brandId != null && name == null) {
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            products = productRepository.findAllByBrand_Id(brandId, pageable);
        } else if (categoryId == null && brandId == null && name != null) {
            if(!productRepository.existsByName(name)){
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            products = productRepository.findAllByName(name, pageable);
        } else if (categoryId == null && brandId != null && name != null) {
            if(!productRepository.existsByName(name)){
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            products = productRepository.findAllByNameAndBrand_Id(name, brandId, pageable);

        } else if (categoryId != null && brandId == null && name != null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if(!categoryRepository.existsById(categoryId)){
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            products = productRepository.findAllByNameAndCategory_Id(name, categoryId, pageable);
        }else if (categoryId != null && brandId != null && name != null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if(!categoryRepository.existsById(categoryId)){
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            products = productRepository.findAllByNameAndBrand_IdAndCategory_Id(name, brandId, categoryId, pageable);
        }
        else
        {
            products = productRepository.findAll(pageable);
        }

        return products.map(productMapper :: toView);
    }

    @Override
    public ResponseEntity<String> saveProductsDataFromExcel(MultipartFile multipartFile) {
        String filename = StringUtils.getFilename(multipartFile.getOriginalFilename());
        log.info("In ProductServiceImpl method <saveProductsDataFromExcel> is saving prices from file={}",filename);
        if (FileDownloadUtil.isValidExcelFile(multipartFile)) {
            try(InputStream inputStream = multipartFile.getInputStream()){
                List<CreateProductDTO> products = FileDownloadUtil.getProductsDataFromExcel(inputStream);
                for (CreateProductDTO product : products){
                    createProduct(product);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("The file with name=%s is not a valid excel file", filename));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("Products from file with name=%s is created successfully", filename));
    }

    @Override
    public ResponseEntity<String> saveProductsDataFromCSV(MultipartFile multipartFile) {
        String filename = StringUtils.getFilename(multipartFile.getOriginalFilename());
        log.info("In ProductServiceImpl method <saveProductsDataFromCSV> is saving prices from file={}",filename);
        if (FileDownloadUtil.isValidCSVFile(multipartFile)) {
            try(InputStream inputStream = multipartFile.getInputStream()){
                List<CreateProductDTO> products = FileDownloadUtil.getProductsDataFromCSV(inputStream);
                for (CreateProductDTO product : products){
                    createProduct(product);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("The file with name=%s is not a valid .csv file", filename));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("Products from file with name=%s is created successfully", filename));
    }


}
