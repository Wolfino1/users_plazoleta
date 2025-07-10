package com.plazoleta.usuarios.common.configurations.beans;

import org.mapstruct.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient restaurantWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081/api/v1/restaurant")
                .build();
    }
}
