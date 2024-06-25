package com.example.healthdb.utils;

/**
 * 返回工具类
 *
 * @author yupi
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(200, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResult error(ErrorCode errorCode) {
        return new BaseResult<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResult error(int code, String message, String description) {
        return new BaseResult(code, null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResult error(ErrorCode errorCode, String message, String description) {
        return new BaseResult(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResult error(ErrorCode errorCode, String description) {
        return new BaseResult(errorCode.getCode(), errorCode.getMessage(), description);
    }
}