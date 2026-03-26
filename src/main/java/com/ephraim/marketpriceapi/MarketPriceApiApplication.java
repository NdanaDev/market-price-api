package com.ephraim.marketpriceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MarketPriceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketPriceApiApplication.class, args);
    }
}
