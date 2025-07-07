package com.plazoleta.usuarios.domain.exceptions;

public class UnderAgeException extends RuntimeException {
    public UnderAgeException(String message) {
        super(message);
    }
}
