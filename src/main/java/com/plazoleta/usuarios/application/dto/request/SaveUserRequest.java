package com.plazoleta.usuarios.application.dto.request;

import java.time.LocalDate;

public record SaveUserRequest (long id,
                               String name,
                               String lastname,
                               String document,
                               String phoneNumber,
                               LocalDate dateOfBirth,
                               String email,
                               String password,
                               Long idRestaurant
         ){
}
