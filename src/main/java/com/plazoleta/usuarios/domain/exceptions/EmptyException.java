package com.plazoleta.usuarios.domain.exceptions;

public class EmptyException extends RuntimeException {
    public EmptyException(String message) {
        super(message);
    }
}
