package com.lzp.aas.exception;

import java.io.Serializable;

public interface AppError extends Serializable {

    Integer getStatus();

    String getErrorCode();

}
