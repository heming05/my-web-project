package com.example.springboot_devtools_datasourceregister;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT) // 返回 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleConflict() {
        // 什么也不做，让 Spring Boot 返回 409 状态码
    }
}
