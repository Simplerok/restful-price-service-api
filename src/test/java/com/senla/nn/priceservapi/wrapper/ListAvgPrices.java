package com.senla.nn.priceservapi.wrapper;

import com.senla.nn.priceservapi.dto.AvgPriceDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListAvgPrices implements Serializable {
    private List<AvgPriceDTO> list;
}
