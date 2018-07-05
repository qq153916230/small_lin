package com.zjgj.manager.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc   //测试controller请求地址的注解
public class GirlControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void girlAdd() {
    }

    @Test
    public void girlAdd2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/girls2"))
                .andExpect(MockMvcResultMatchers.status().isOk())       //判断状态是否是200
                .andExpect(MockMvcResultMatchers.content()            //对返回内容的判断
                        .string("123"));
    }
}