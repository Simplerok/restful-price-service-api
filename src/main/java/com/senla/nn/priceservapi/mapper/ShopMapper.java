package com.senla.nn.priceservapi.mapper;

import com.senla.nn.priceservapi.dto.ShopDTO;
import com.senla.nn.priceservapi.entity.Shop;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel ="spring")
public interface ShopMapper {
    Shop toShop(ShopDTO shopDTO);
    @InheritConfiguration
    ShopDTO toDTO(Shop shop);
}
