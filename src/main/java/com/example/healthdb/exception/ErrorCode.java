package com.example.healthdb.exception;

/**
 * 错误码
 *
 * @author yupi
 */
public enum ErrorCode {

    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN_ERROR(40100, "未登录",""),
    NOT_SET_PASSWORD(40101, "未设置密码", ""),

    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    PASSWORD_WRONG(40002,"密码错误",""),
    TELEPHONE_WRONG(40003,"电话号码错误",""),
    ID_WRONG(40004,"id参数错误",""),
    ORDER_NOTFINISH(40005,"订单未完成",""),

    ORDER_TIME_WRONG(40500,"订单时长超出时限",""),
    Money_WRONG(40006,"提现金额过多",""),
    NOT_ESCORT(40600,"当前用户未成为陪诊师","");





    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
