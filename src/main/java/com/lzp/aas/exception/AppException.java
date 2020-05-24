package com.lzp.aas.exception;

public class AppException extends RuntimeException {

    private AppError error;

    public AppException(AppError error) {
        this.error = error;
    }

    public AppException(AppError error, String message) {
        super(message);
        this.error = error;
    }

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
//
//    @Override
//    public StackTraceElement[] getStackTrace() {
//        return null;
//    }


    AppError getError() {
        return error;
    }

}
