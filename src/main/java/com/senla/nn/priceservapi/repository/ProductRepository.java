package com.senla.nn.priceservapi.repository;

import com.senla.nn.priceservapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p where p.name like %:name%")
    Page<Product> findAllByName(@Param("name") String name, Pageable pageable);

    default boolean existsByNameAndBrandId(String name, Long brandId) {
        return true;
    }

    default boolean existsByName(String name) {
        return true;
    }

    Page<Product> findAllByCategory_Id(Long categoryId, Pageable pageable);
    Page<Product> findAllByBrand_Id(Long brandId, Pageable pageable);
    Page<Product> findAllByNameAndBrand_Id(String name, Long brandId, Pageable pageable);
    Page<Product> findAllByNameAndCategory_Id(String name, Long categoryId, Pageable pageable);
    Page<Product> findAllByCategory_IdAndBrand_Id(Long categoryId, Long brandId, Pageable pageable);
    Page<Product> findAllByNameAndBrand_IdAndCategory_Id(String name, Long brandId, Long categoryId, Pageable pageable);

}
