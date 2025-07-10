package com.plazoleta.usuarios.infrastructure.client.Impl;

import com.plazoleta.usuarios.infrastructure.client.PagedResult;
import com.plazoleta.usuarios.application.dto.response.RestaurantResponse;
import com.plazoleta.usuarios.domain.exceptions.ResourceNotFoundException;
import com.plazoleta.usuarios.infrastructure.client.RestaurantApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RestaurantApiClientImpl implements RestaurantApiClient {

    private final @Qualifier("restaurantWebClient") WebClient webClient;

    @Override
    public RestaurantResponse getById(Long restaurantId) {
        PagedResult<RestaurantResponse> page = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("id", restaurantId)
                        .queryParam("page", 0)
                        .queryParam("size", 1)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PagedResult<RestaurantResponse>>() {})
                .block();

        if (page == null || page.content().isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found: " + restaurantId);
        }
        return page.content().get(0);
    }

}