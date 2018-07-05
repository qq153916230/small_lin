package com.zjgj.manager.controller;

import com.zjgj.manager.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Value("${cupSize}")
    private String cupSize;

    @Value("${age}")
    private Integer age;

    @Value("${content}")
    private String content;

    @Autowired
    private GirlProperties girlProperties;  //此处如果报无法自动注入 则在GirlProperties里需加入 @Component

    @RequestMapping(value = {"/hello","/hi"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String say(){
        return cupSize + age + content + " " + girlProperties.getCupSize() + girlProperties.getAge();
    }

    @RequestMapping(value = {"/{mid}/say/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String say2(@PathVariable("id") Integer id, @PathVariable("mid") Integer id2,
                       @RequestParam(value = "tid", required = false, defaultValue = "0") Integer tid){
        return "id:"+id + " mid:"+id2 + " tid:"+tid;
    }

    @GetMapping(value = "/say3")
    public String say3(){
        return "hello world";
    }
}
