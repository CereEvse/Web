package com.example.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.UUID;

@Slf4j
@ControllerAdvice
@Component
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        String uuid = UUID.randomUUID().toString();
        log.error("Exception with uuid {}: {}", uuid, e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        500L,
                        "Что-то пошло не так, уже исправляем. Пожалуйста, обратитесь с номером ошибки: " + uuid
                )
        );
    }

}
