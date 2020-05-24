package com.lzp.aas.exception;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {

    private final AppError error;

    private final Map<String, List<String>> errorMap;

    public ValidationException(AppError error, Map<String, List<String>> errorMap) {
        this.errorMap = errorMap;
        this.error = error;
    }

    public ValidationException(AppError error, Map<String, List<String>> errorMap, String message) {
        super(message);
        this.errorMap = errorMap;
        this.error = error;
    }

    AppError getError() {
        return error;
    }

    Map<String, List<String>> getErrorMap() { return errorMap; }
}
