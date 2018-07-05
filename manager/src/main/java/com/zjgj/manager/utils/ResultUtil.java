package com.zjgj.manager.utils;

import com.zjgj.manager.domain.Result;
import com.zjgj.manager.enums.ResultEnum;

public class ResultUtil {

    public static Result success(Object obj){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("SUCCESS");
        result.setData(obj);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result success(ResultEnum en, Object obj){
        Result result = new Result();
        result.setCode(en.getCode());
        result.setMsg(en.getMsg());
        result.setData(obj);
        return result;
    }

    public static Result success(ResultEnum en){
        return success(en,null);
    }

    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(ResultEnum resEnum){
        return error(resEnum.getCode(), resEnum.getMsg());
    }
}
