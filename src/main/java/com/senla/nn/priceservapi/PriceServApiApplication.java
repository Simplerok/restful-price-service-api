package com.senla.nn.priceservapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PriceServApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceServApiApplication.class, args);
	}

}
