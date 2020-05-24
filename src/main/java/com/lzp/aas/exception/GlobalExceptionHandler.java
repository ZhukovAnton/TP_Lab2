package com.lzp.aas.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static ResponseError processException(AppError error, Throwable e, HttpServletResponse response) {
        response.setStatus(error.getStatus());
        return new ResponseError(error.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    ResponseError handleException(UndeclaredThrowableException e, HttpServletResponse response) {
        if (e.getUndeclaredThrowable() instanceof AppException) {
            log.error(e.getMessage(), e);
            return processException(((AppException) e.getUndeclaredThrowable()).getError(), e, response);
        } else {
            log.error(e.getMessage(), e);
            return processException(HttpAppError.UNKNOWN_ERROR, e.getUndeclaredThrowable(), response);
        }
    }

    @ExceptionHandler(ValidationException.class)
    Map<String, List<String>> handleValidationException(ValidationException e, HttpServletResponse response) {
        response.setStatus(e.getError().getStatus());
        return e.getErrorMap();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    ResponseError handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletResponse response) {
        return processException(HttpAppError.BAD_REQUEST, e, response);
    }

    @ExceptionHandler({ServletException.class})
    ResponseError handleServletException(ServletException e, HttpServletResponse response) {
        return processException(HttpAppError.UNKNOWN_ERROR, e, response);
    }

    @ExceptionHandler({Exception.class, Throwable.class, RuntimeException.class})
    ResponseError handleException(Exception e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return processException(HttpAppError.UNKNOWN_ERROR, e, response);
    }

    @ExceptionHandler(AppException.class)
    ResponseError handleException(AppException e, HttpServletResponse response) {
        return processException(e.getError(), e, response);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseError handleException(MethodArgumentNotValidException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return processException(HttpAppError.VALIDATION_ERROR, e, response);
    }

    @ExceptionHandler({BindException.class})
    ResponseError handleExceptionBind(BindException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return processException(HttpAppError.VALIDATION_ERROR, e, response);
    }
}
