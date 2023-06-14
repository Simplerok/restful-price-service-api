package com.senla.nn.priceservapi.repository;

import com.senla.nn.priceservapi.entity.Price;
import com.senla.nn.priceservapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Page<Price> findAllByShop_IdAndProduct_Brand_Id(Long shopId, Long brandId, Pageable pageable);
    Page<Price> findAllByShop_Id(Long shopId, Pageable pageable);
    Page<Price> findAllByProduct_Category_Id(Long categoryId, Pageable pageable);
    Page<Price> findAllByProduct_Brand_Id(Long brandId, Pageable pageable);
    Page<Price> findAllByProduct_Category_IdAndProduct_Brand_Id(Long categoryId, Long brandId, Pageable pageable);
    Page<Price> findAllByShop_IdAndProduct_Category_Id(Long shopId, Long categoryId, Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCaseAndShop_IdAndProduct_Brand_Id(String name, Long shopId, Long brandId, Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCase(String name,Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCaseAndProduct_Category_Id(String name,Long categoryId, Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCaseAndShop_Id(String name, Long shopId, Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCaseAndShop_IdAndProduct_Category_Id(String name, Long shopId, Long categoryId, Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCaseAndProduct_Category_IdAndProduct_Brand_Id(String name, Long categoryId, Long brandId, Pageable pageable);
    Page<Price> findAllByShop_IdAndProduct_Category_IdAndProduct_Brand_Id(Long shopId, Long categoryId, Long brandId, Pageable pageable);
    Page<Price> findAllByProduct_NameContainingIgnoreCaseAndProduct_Brand_Id(String name, Long brandId, Pageable pageable);
    @Query("select p from Price p where p.shop.id = :shopId and p.product.id = :productId and p.createdDate between :from and :to")
    List<Price> findPricesBetweenDates(@Param("shopId") Long shopId,
                                       @Param("productId") Long productId,
                                       @Param("from") LocalDateTime from,
                                       @Param("to") LocalDateTime to);


}
