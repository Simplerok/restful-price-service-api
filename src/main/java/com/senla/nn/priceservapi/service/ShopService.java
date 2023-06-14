package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.ShopDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShopService {

    ShopDTO createShop(ShopDTO shopDTO);
    ResponseEntity<String> delete(Long shopId);
    Page<ShopDTO> findAll(Pageable pageable);
    ShopDTO update(Long shopId, ShopDTO shopDTO);
    ShopDTO getById(Long shopId);
    ShopDTO getByName(String name);
}
