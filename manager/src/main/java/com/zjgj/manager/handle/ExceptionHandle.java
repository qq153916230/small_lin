package com.zjgj.manager.handle;

import com.zjgj.manager.Exception.GirlException;
import com.zjgj.manager.domain.Result;
import com.zjgj.manager.enums.ResultEnum;
import com.zjgj.manager.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice   //
public class ExceptionHandle {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    //捕获异常的方法
    @ExceptionHandler(value = Exception.class) //声明要捕获的异常类 现在要捕获Exception异常
    @ResponseBody   //返回页面json格式
    public Result handle(Exception e){
        if (e instanceof GirlException){
            GirlException ge = (GirlException) e;
            return ResultUtil.error(ge.getCode(),ge.getMessage());
        } else{
            logger.error("【系统异常】{}", e);
            return ResultUtil.error(ResultEnum.UNKONW_ERROR);
        }
    }

}
