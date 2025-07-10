package com.plazoleta.usuarios.infrastructure.client;

import com.plazoleta.usuarios.application.dto.response.RestaurantResponse;

public interface RestaurantApiClient {
    RestaurantResponse getById(Long restaurantId);
}
