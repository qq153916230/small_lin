package com.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.TradedataDao;
import com.app.entity.Tradedata;
import com.app.service.TradedataService;

@Service
public class TradedataServiceImpl implements TradedataService {
	
	@Resource
	TradedataDao tradedataDao;
	
	//	批量修改状态
	@Override
	public void updateBySidSelective(String[] sidArray,int status) {
		int[] sids = new int[sidArray.length];
		for (int i = 0; i < sidArray.length; i++) {
			sids[i] = Integer.parseInt(sidArray[i]);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("sids", sids);
		this.tradedataDao.updateStatusBySids(map);
	}
	
	/*从t_zjgj_member 表获取 商户号/手机号*/
	@Override
	public Long selectMC(String mName) {
		String name = this.tradedataDao.selectMC(mName);
		Long nameL = null;
		if (name != null) {
			nameL = Long.parseLong(name);
		}
		return nameL;
	}

	@Override
	public void insertList(List<Tradedata> list) {
		this.tradedataDao.insertList(list);
	}

	@Override
	public List<Tradedata> selectInsert() {
		return this.tradedataDao.selectInsert();
	}

}
