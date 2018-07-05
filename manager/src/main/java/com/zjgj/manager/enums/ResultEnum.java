package com.zjgj.manager.enums;

public enum ResultEnum {
    UNKONW_ERROR(0,"未知错误"),
    ERROR(0,"执行异常"),
    SUCCESS(1, "SUCCESS"),
    ONT_TEN(100, "小于10岁"),
    TEN_TWENTY(200, "10岁到20岁之间");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
