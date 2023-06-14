package com.senla.nn.priceservapi.service;

import com.senla.nn.priceservapi.dto.CreatePriceDTO;
import com.senla.nn.priceservapi.dto.CreateProductDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class FileDownloadUtil {

    private static final String[] HEADERS_FOR_PRODUCTS = {"Name", "BrandId", "CategoryId"};
    private static final String[] HEADERS_FOR_PRICES = {"Value", "ShopId", "ProductId"};
    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static boolean isValidCSVFile(MultipartFile file) {

        if(file.getContentType().equals("text/csv") || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        else {
            return false;
        }
    }


    //Пакетное добавление продуктов из файла excel
    public static List<CreateProductDTO> getProductsDataFromExcel(InputStream inputStream) {
        List<CreateProductDTO> productList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheet("products");
            Iterator<Row> rowIterator = sheet.rowIterator();
            int rowIndex = 0;

            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                CreateProductDTO product = new CreateProductDTO();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            product.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            product.setBrandId((long) cell.getNumericCellValue());
                            break;
                        case 2:
                            product.setCategoryId((long) cell.getNumericCellValue());
                            break;
                        default:
                            break;

                    }
                    cellIndex++;
                }
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    //Пакетное добавление цен на продукты из файла excel
    public static List<CreatePriceDTO> getPricesDataFromExcel(InputStream inputStream) {
        List<CreatePriceDTO> priceList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheet("prices");
            Iterator<Row> rowIterator = sheet.rowIterator();
            int rowIndex = 0;

            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                CreatePriceDTO price = new CreatePriceDTO();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            price.setValue(BigDecimal.valueOf(Double.parseDouble(cell.getStringCellValue())));
                            break;
                        case 1:
                            price.setShopId((long) cell.getNumericCellValue());
                            break;
                        case 2:
                            price.setProductId((long) cell.getNumericCellValue());
                            break;
                        default:
                            break;

                    }
                    cellIndex++;
                }
                priceList.add(price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceList;
    }

    //Пакетное добавление продуктов из файла .csv
    public static List<CreateProductDTO> getProductsDataFromCSV(InputStream inputStream){
        List<CreateProductDTO> productList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader(HEADERS_FOR_PRODUCTS)
                    .setDelimiter(",")
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for(CSVRecord record : csvRecords) {
                CreateProductDTO productDTO = CreateProductDTO.builder()
                        .name(record.get("Name"))
                        .brandId(Long.valueOf(record.get("BrandId")))
                        .categoryId(Long.valueOf(record.get("CategoryId")))
                        .build();
                productList.add(productDTO);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }

    //Пакетное добавление цен на продукты из файла .csv
    public static List<CreatePriceDTO> getPricesDataFromCSV(InputStream inputStream){
        List<CreatePriceDTO> priceList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader(HEADERS_FOR_PRICES)
                    .setDelimiter(",")
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for(CSVRecord record : csvRecords) {
                CreatePriceDTO priceDTO = CreatePriceDTO.builder()
                        .value(BigDecimal.valueOf(Double.parseDouble(record.get("Value"))))
                        .shopId(Long.valueOf(record.get("ShopId")))
                        .productId(Long.valueOf(record.get("ProductId")))
                        .build();
                priceList.add(priceDTO);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return priceList;
    }
}
