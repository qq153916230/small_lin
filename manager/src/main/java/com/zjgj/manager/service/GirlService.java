package com.zjgj.manager.service;

import com.zjgj.manager.Exception.GirlException;
import com.zjgj.manager.domain.Girl;
import com.zjgj.manager.enums.ResultEnum;
import com.zjgj.manager.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GirlService {

    @Autowired
    GirlRepository girlRepository;

    @Transactional
    @org.springframework.transaction.annotation.Transactional
    public void insert(){}

    public void checkAge(Integer age) throws Exception {
        if (age < 10){
            throw new GirlException(ResultEnum.ONT_TEN);
        } else if (10 <= age && age <= 20){
            throw new GirlException(ResultEnum.TEN_TWENTY);
        } else{
            int i = 1/0;
        }
    }

    public Girl findOne(Integer id){
        return girlRepository.findOne(id);
    }
}
