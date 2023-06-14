package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.AvgPriceDTO;
import com.senla.nn.priceservapi.dto.CreatePriceDTO;
import com.senla.nn.priceservapi.dto.ShortPriceDTO;
import com.senla.nn.priceservapi.dto.ViewPriceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceService {
    ViewPriceDTO createPrice(CreatePriceDTO createPriceDTO);
    ResponseEntity<String> delete(Long priceId);
    Page<ViewPriceDTO> getByFilter(String name, Long shopId, Long categoryId, Long brandId, Pageable pageable);
    List<ShortPriceDTO> getPricesBetweenDates(Long shopId, Long productId, LocalDateTime from, LocalDateTime to);
    ResponseEntity<String> savePricesDataFromExcel(MultipartFile multipartFile);
    ResponseEntity<String> savePricesDataFromCsv(MultipartFile multipartFile);
    List<AvgPriceDTO> getAvgPricesGroupByMonth(Long shopId, Long productId, LocalDateTime from, LocalDateTime to);
}
