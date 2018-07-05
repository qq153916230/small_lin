package com.zjgj.manager.repository;

import com.zjgj.manager.domain.Girl;
import org.springframework.stereotype.Repository;

@Repository
public class GirlRepository {

    public Girl findOne(Integer id) {
        Girl girl = new Girl();
        girl.setId(id);
        girl.setAge(18);
        girl.setCupSize("E");
        return girl;
    }

    public Girl save(Girl girl){

        return null;
    }
}
