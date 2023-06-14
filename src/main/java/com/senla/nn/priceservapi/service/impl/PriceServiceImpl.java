package com.senla.nn.priceservapi.service.impl;

import com.senla.nn.priceservapi.dto.*;
import com.senla.nn.priceservapi.entity.Price;
import com.senla.nn.priceservapi.entity.Product;
import com.senla.nn.priceservapi.entity.Shop;
import com.senla.nn.priceservapi.exception.NotFoundException;
import com.senla.nn.priceservapi.mapper.PriceMapper;
import com.senla.nn.priceservapi.repository.*;
import com.senla.nn.priceservapi.service.CollectorBigDecimalUtil;
import com.senla.nn.priceservapi.service.FileDownloadUtil;
import com.senla.nn.priceservapi.service.PriceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Pair;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.senla.nn.priceservapi.service.CollectorBigDecimalUtil.averagingBigDecimal;
import static java.util.stream.Collectors.*;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    @Transactional
    public ViewPriceDTO createPrice(CreatePriceDTO createPriceDTO) {
        log.info("In PriceServiceImpl method <createPrice> create price={}", createPriceDTO);
        Shop shop = shopRepository.findById(createPriceDTO.getShopId())
                .orElseThrow(() -> new NotFoundException(String.format("Shop with id=%s not found", createPriceDTO.getShopId())));
        Product product = productRepository.findById(createPriceDTO.getProductId())
                .orElseThrow(() -> new NotFoundException(String.format("Product with id=%s not found", createPriceDTO.getProductId())));

        Price price = Price.builder()
                .value(createPriceDTO.getValue())
                .shop(shop)
                .product(product)
                .build();

        LocalDateTime createdDate = createPriceDTO.getCreatedDate();
        if(createdDate != null) {
            price.setCreatedDate(createdDate);
        }
        priceRepository.save(price);
        return priceMapper.toView(price);
    }

    @Override
    public ResponseEntity<String> delete(Long priceId) {
        log.info("In PriceServiceImpl method <delete> is managed to delete price with id={}", priceId);
        priceRepository.deleteById(priceId);
        log.info("In PriceServiceImpl price with id={} is deleted successfully", priceId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Price with id=%s is deleted successfully", priceId));
    }


    @Override
    public Page<ViewPriceDTO> getByFilter(String name, Long shopId, Long categoryId, Long brandId, Pageable pageable) {
        log.info("In PriceServiceImpl method <findByFilter> is getting filtered prices on products");
        Page<Price> prices;
        if(name  != null && brandId != null && shopId != null && categoryId == null){

            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            prices = priceRepository.findAllByProduct_NameContainingIgnoreCaseAndShop_IdAndProduct_Brand_Id(name, shopId, brandId, pageable);
        } else if (name != null && shopId != null && categoryId == null && brandId == null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            prices = priceRepository.findAllByProduct_NameContainingIgnoreCaseAndShop_Id(name, shopId, pageable);
        } else if (name != null && shopId != null && categoryId != null && brandId == null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            prices = priceRepository
                    .findAllByProduct_NameContainingIgnoreCaseAndShop_IdAndProduct_Category_Id(name, shopId, categoryId, pageable);
        }
        else if (name != null && shopId == null && categoryId != null && brandId == null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            prices = priceRepository
                    .findAllByProduct_NameContainingIgnoreCaseAndProduct_Category_Id(name, categoryId, pageable);
        }
        else if (name != null && brandId != null && categoryId == null && shopId == null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            prices = priceRepository.findAllByProduct_NameContainingIgnoreCaseAndProduct_Brand_Id(name, brandId, pageable);
        } else if (name != null && brandId != null && categoryId != null && shopId == null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            prices = priceRepository
                    .findAllByProduct_NameContainingIgnoreCaseAndProduct_Category_IdAndProduct_Brand_Id(name, categoryId,brandId, pageable);
        }
        else if (name != null && brandId == null && categoryId == null && shopId == null) {
            if (!productRepository.existsByName(name)) {
                throw new NotFoundException(String.format("Product with name=%s not found", name));
            }
            prices = priceRepository.findAllByProduct_NameContainingIgnoreCase(name, pageable);
        }
        else if (name == null && shopId != null && categoryId != null && brandId == null) {
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            prices = priceRepository.findAllByShop_IdAndProduct_Category_Id(shopId, categoryId, pageable);

        } else if (name == null && shopId != null && categoryId != null && brandId != null) {
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            prices = priceRepository.findAllByShop_IdAndProduct_Category_IdAndProduct_Brand_Id(shopId,categoryId,brandId,pageable);

        }
        else if (shopId != null && name == null && categoryId == null && brandId == null) {
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            prices = priceRepository.findAllByShop_Id(shopId, pageable);
        } else if (categoryId != null && brandId == null && name == null && shopId == null) {
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            prices = priceRepository.findAllByProduct_Category_Id(categoryId, pageable);
        }
        else if (categoryId == null && brandId != null && name == null && shopId == null) {
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            prices = priceRepository.findAllByProduct_Brand_Id(brandId, pageable);
        } else if (categoryId != null && brandId != null && name == null && shopId == null) {
            if (!categoryRepository.existsById(categoryId)) {
                throw new NotFoundException(String.format("Category with id=%s not found", categoryId));
            }
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            prices = priceRepository.findAllByProduct_Category_IdAndProduct_Brand_Id(categoryId, brandId, pageable);
        }
        else if (categoryId == null && brandId != null && name == null && shopId != null) {
            if(!brandRepository.existsById(brandId)){
                throw new NotFoundException(String.format("Brand with id=%s not found", brandId));
            }
            if (!shopRepository.existsById(shopId)) {
                throw new NotFoundException(String.format("Shop with id=%s not found", shopId));
            }
            prices = priceRepository.findAllByShop_IdAndProduct_Brand_Id(shopId, brandId, pageable);
        }
        else {
            prices = priceRepository.findAll(pageable);
        }
        return prices.map(priceMapper::toView);
    }

    @Override
    public List<ShortPriceDTO> getPricesBetweenDates(Long shopId, Long productId, LocalDateTime from, LocalDateTime to) {
        log.info("In PriceServiceImpl method <getPricesBetweenDates> is getting prices between {} and {}",from, to);
        return priceMapper.toShortView(priceRepository.findPricesBetweenDates(shopId, productId, from, to));
    }

    @Override
    public ResponseEntity<String> savePricesDataFromExcel(MultipartFile multipartFile) {
        String filename = StringUtils.getFilename(multipartFile.getOriginalFilename());
        log.info("In PriceServiceImpl method <savePricesDataFromExcel> is saving prices from file={}",filename);
        if (FileDownloadUtil.isValidExcelFile(multipartFile)) {
            try(InputStream inputStream = multipartFile.getInputStream()){
                List<CreatePriceDTO> prices = FileDownloadUtil.getPricesDataFromExcel(inputStream);
                for (CreatePriceDTO price : prices){
                    createPrice(price);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("The file with name=%s is not a valid excel file", filename));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("Prices from file with name=%s is created successfully", filename));
    }

    @Override
    public ResponseEntity<String> savePricesDataFromCsv(MultipartFile multipartFile) {
        String filename = StringUtils.getFilename(multipartFile.getOriginalFilename());
        log.info("In PriceServiceImpl method <savePricesDataFromCsv> is saving prices from file={}",filename);
        if (FileDownloadUtil.isValidCSVFile(multipartFile)) {
            try(InputStream inputStream = multipartFile.getInputStream()){
                List<CreatePriceDTO> prices = FileDownloadUtil.getPricesDataFromCSV(inputStream);
                for (CreatePriceDTO price : prices){
                    createPrice(price);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("The file with name=%s is not a valid .csv file", filename));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("Prices from file with name=%s is created successfully", filename));
    }

    @Override
    public List<AvgPriceDTO> getAvgPricesGroupByMonth(Long shopId, Long productId, LocalDateTime from, LocalDateTime to) {

        LocalDateTime dateFrom = from.withDayOfMonth(1).with(LocalTime.MIN);
        LocalDateTime dateTo = to.withDayOfMonth(to.getMonth().maxLength()).with(LocalTime.MAX);
        List<ShortPriceDTO> listOfRawPrices = getPricesBetweenDates(shopId, productId, dateFrom, dateTo);

        Map<LocalDate, BigDecimal> avgPricesByDate = listOfRawPrices.stream()
                .map(price -> new Pair<>(price.getCreatedDate().toLocalDate().withDayOfMonth(1), price.getValue()))
                .collect(groupingBy(Pair::getKey, TreeMap::new, averagingBigDecimal(Pair::getValue, 2, RoundingMode.HALF_UP)));

        if(avgPricesByDate.size() == 1) {
            return avgPricesByDate.entrySet().stream()
                    .map(element -> new AvgPriceDTO(element.getValue(), element.getKey()))
                    .collect(toList());
        }
        LocalDate minDateWithValue = avgPricesByDate.keySet().stream().min(LocalDate::compareTo).orElseThrow(IllegalArgumentException::new);

        return Stream.iterate(minDateWithValue, d -> d.plusMonths(1))
                .limit(ChronoUnit.MONTHS.between(minDateWithValue, dateTo))
                .map(date -> new AvgPriceDTO(getValue(date, avgPricesByDate, minDateWithValue), date))
                .collect(toList());


    }

    private BigDecimal getValue(LocalDate date, Map<LocalDate, BigDecimal> map, LocalDate min) {
        if(date.isBefore(min)) return BigDecimal.ZERO;
        BigDecimal price = map.get(date);
        if(price == null ) {
            return getValue(date.minusMonths(1L), map, min);
        }
        return price;
    }


}
