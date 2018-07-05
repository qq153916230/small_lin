package com.zjgj.manager.Exception;

import com.zjgj.manager.enums.ResultEnum;

//Spring 的事物回滚只有在抛出RuntimeException的时候
public class GirlException extends RuntimeException {

    private Integer code;

    public GirlException(ResultEnum resEnum) {
        super(resEnum.getMsg());
        this.code = resEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
