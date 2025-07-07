package com.plazoleta.usuarios.infrastructure.exceptionshandler;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
