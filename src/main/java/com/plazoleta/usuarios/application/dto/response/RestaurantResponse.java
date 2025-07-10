package com.plazoleta.usuarios.application.dto.response;

public record RestaurantResponse(
        Long id,
        String name,
        String nit,
        String address,
        String phoneNumber,
        String logoUrl,
        Long ownerId
) {}
