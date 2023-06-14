package com.senla.nn.priceservapi.service.impl;

import com.senla.nn.priceservapi.dto.ShopDTO;
import com.senla.nn.priceservapi.entity.Shop;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.ShopMapper;
import com.senla.nn.priceservapi.repository.ShopRepository;
import com.senla.nn.priceservapi.service.ShopService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Data
@AllArgsConstructor
@Validated
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;


    @Override
    public ShopDTO createShop(ShopDTO shopDTO) {
        log.info("In ShopServiceImpl method <createShop> create shop={}",shopDTO);
        Optional<Shop> isExist = shopRepository.getByName(shopDTO.getName());
        if (isExist.isPresent()) throw new AlreadyExistsException(String.format("Shop with name=%s is already exist", shopDTO.getName()));
        Shop shop = shopMapper.toShop(shopDTO);
        shopRepository.save(shop);
        log.info("In ShopServiceImpl shop={} successfully created",shopDTO);
        return shopMapper.toDTO(shop);
    }

    @Override
    public ResponseEntity<String> delete(Long shopId) {
        log.info("In ShopServiceImpl method <delete> is managed to delete shop with id={}",shopId);
        shopRepository.deleteById(shopId);
        log.info("In ShopServiceImpl shop with id={} is deleted successfully",shopId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("User with id=%s is deleted successfully", shopId));
    }

    @Override
    public Page<ShopDTO> findAll(Pageable pageable) {
        log.info("In ShopServiceImpl method <findAll> is getting all shops");
        Page<Shop> shops = shopRepository.findAll(pageable);
        return shops.map(shopMapper :: toDTO);
    }

    @Override
    public ShopDTO update(Long shopId, ShopDTO shopDTO) {
        log.info("In ShopServiceImpl method <update> is updating shop={} with id={}",shopDTO, shopId);
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException(String.format("Shop with id=%s not found", shopId)));

        shop.setName(shopDTO.getName());
        shopRepository.save(shop);
        log.info("In ShopServiceImpl shop={} successfully updated",shopDTO);
        return shopMapper.toDTO(shop);
    }

    @Override
    public ShopDTO getById(Long shopId) {
        log.info("In ShopServiceImpl method <getById> is getting shop with id={}", shopId);
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException(String.format("Shop with id=%s not found", shopId)));
        return shopMapper.toDTO(shop);
    }

    @Override
    public ShopDTO getByName(String name) {
        log.info("In ShopServiceImpl method <getByName> is getting shop with name={}", name);
        Shop shop = shopRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Shop with name=%s not found", name)));
        return shopMapper.toDTO(shop);
    }


}
