package com.zjgj.manager.controller;

import com.zjgj.manager.Exception.GirlException;
import com.zjgj.manager.domain.Girl;
import com.zjgj.manager.domain.Result;
import com.zjgj.manager.enums.ResultEnum;
import com.zjgj.manager.repository.GirlDao;
import com.zjgj.manager.repository.GirlRepository;
import com.zjgj.manager.service.GirlService;
import com.zjgj.manager.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GirlController {

    @Autowired
    GirlRepository girlRepository;

    @Autowired
    GirlService girlService;

    @Autowired
    GirlDao girlDao;

    @PostMapping(value = "/girls")
    public Girl girlAdd(@RequestParam("cupSize") String cupSize,
                         @RequestParam("age") Integer age) {
        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);
        return  girlRepository.save(girl);
    }

    //@Valid 表示要验证这个对象是否满足条件，并在Girl里面添加 @Min 注解
    //如果验证未满足条件则会把错误信息传到第二个参数 BindingResult对象中
    @GetMapping(value = "/girls2")
    public Result<Girl> girlAdd2(@Valid Girl girl, BindingResult bindingResult) {

        Result result = new Result();

        if (bindingResult.hasErrors()){
            return ResultUtil.error(0, bindingResult.getFieldError().getDefaultMessage());
        }

        System.out.println("cupSize:" + girl.getCupSize() + " age:"+ girl.getAge());

        return ResultUtil.success(girl);
        //return  girlRepository.save(girl);
    }

    //统一异常处理
    @GetMapping(value = "/girl/check/{age}")
    public void ageCheck(@PathVariable("age") Integer age) throws Exception {
        girlService.checkAge(age);
    }

    @GetMapping(value = "/girl/list")
    public Result<List<Girl>> girlList(){
        return ResultUtil.success(ResultEnum.SUCCESS, girlDao.findAll());
    }

    @GetMapping(value = "/girl/save/{age}/{cupSize}")
    public Result saveGirl(@PathVariable(name = "age") Integer age,
                        @PathVariable(name = "cupSize") String cupSize){
        try {
            Girl girl = new Girl();
            girl.setAge(age);
            girl.setCupSize(cupSize);
            this.girlDao.save(girl);
            return ResultUtil.success(ResultEnum.SUCCESS);

        } catch (Exception e){
            throw new GirlException(ResultEnum.ERROR);
        }
    }


}
