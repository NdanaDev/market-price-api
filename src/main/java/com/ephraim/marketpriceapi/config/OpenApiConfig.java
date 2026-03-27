package com.ephraim.marketpriceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Zambian Market Price API")
                        .description("""
                                A read-heavy backend API that tracks commodity prices across \
                                Zambian markets such as Soweto Market and City Market.

                                Trusted field agents submit market prices. \
                                Clients can query latest prices, price history, \
                                and compare prices across markets in a city.
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Ephraim")
                                .email("ndanadev@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local development")
                ));
    }
}
