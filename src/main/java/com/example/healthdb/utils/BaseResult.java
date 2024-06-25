package com.example.healthdb.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author yupi
 */
@Data
public class BaseResult<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResult(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResult(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResult(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResult(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}