package com.example.healthdb.common;

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
    NOT_WX_REGISTER_ERROR(40102, "小程序用户未注册",""),

    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    NOT_FOUND_ERROR(40400, "请求数据不存在",""),
    FORBIDDEN_ERROR(40300, "禁止访问",""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    OPERATION_ERROR(50001, "操作失败",""),
    STATUS_ERROR(50002,"操作对象状态异常",""),
    TASK_ALLOCATION_ERROR(50003, "任务分配操作失败", ""),
    USER_LOCATION_NOT_EXIT(50004, "用户常用位置未设置", ""),
    ORDER_TIME_WRONG(40500,"订单时长超出时限",""),
    NOT_ESCORT(40600,"当前用户为成为陪诊师","");
    ;

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