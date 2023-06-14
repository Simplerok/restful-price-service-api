package com.senla.nn.priceservapi.mapper;

import com.senla.nn.priceservapi.dto.ShortPriceDTO;
import com.senla.nn.priceservapi.dto.ViewPriceDTO;
import com.senla.nn.priceservapi.entity.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    ViewPriceDTO toView(Price price);
    List<ViewPriceDTO> toView(List<Price> prices);
    @Mapping(target = "shopId", source = "price.shop.id")
    @Mapping(target = "productId", source = "price.product.id")
    ShortPriceDTO toShortView(Price price);

    List<ShortPriceDTO> toShortView(List<Price> prices);

}
