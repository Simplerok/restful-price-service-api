package com.senla.nn.priceservapi.service;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction <T> {

    BigDecimal applyAsBigDecimal(T value);

}
