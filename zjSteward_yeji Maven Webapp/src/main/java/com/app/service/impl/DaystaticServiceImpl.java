package com.app.service.impl;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.daystaticMapper;
import com.app.dao.daytradeMapper;
import com.app.dao.subsidyMapper;
import com.app.dao.tx_resultMapper;
import com.app.entity.daystatic;
import com.app.entity.daytrade;
import com.app.service.DaystaticService;
import com.app.util.DataGrid;
import com.app.util.WriteExcel;

@Service
public class DaystaticServiceImpl implements DaystaticService {
	

	@Resource
	daystaticMapper daystaticDao;	/*操作 日统计 表*/
	@Resource
	daytradeMapper daytradeDao;		/*操作 日交易记录 表*/
	@Resource
	subsidyMapper subsidyDao;		/*操作 补贴 表*/
	@Resource
	tx_resultMapper tx_resultDao;	/*操作 体现 表*/
	
	
	/*向日统计表插入数据(更新日统计表)*/
	@Override
	public void insertBySelectJoin() {
		
		//获取前一天日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String yesterday = df.format(new Date().getTime()-1000*60*60*24);

		this.daystaticDao.insertBySelectJoin();
		System.out.println("已生成日统计表：" + yesterday);
	}

	@Override
	public DataGrid listDataGrid(String paydate,String merchantcode) {
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal((long)this.daystaticDao.selectDaystaticCount());
		
		List<daystatic> list = new ArrayList<daystatic>();//用来保存daystatic的list
		
		//处理空指针异常
		merchantcode = merchantcode + "%";
		if (merchantcode.equals("null%") || merchantcode.equals("%")) {
			list = this.daystaticDao.selectDaystatic(paydate);
			
//			list.add(this.daystaticDao.selectColTotle(paydate));//添加总计列
			dataGrid.setRows(list);
		} else {	
			//如果merchantcode不为null 根据指定商户号查询
			merchantcode = merchantcode.substring(0, merchantcode.length()-1);
			daystatic d = new daystatic();
			d.setGdate(new Date());
			d.setT0Paymoney(this.daytradeDao.selectT0DealTotleMoney2(paydate,merchantcode));	/*设置 T0交易金额*/
			d.setT0Paynum(this.daytradeDao.selectT0DealTotleNum2(paydate,merchantcode));	/*设置 T0交易笔数*/
			d.setT1Paymoney(this.daytradeDao.selectT1DealTotleMoney2(paydate,merchantcode));/*设置 T1交易金额*/
			d.setT1Paynum(this.daytradeDao.selectT1DealTotleNum2(paydate,merchantcode));/*设置 T1交易笔数*/
			d.setPayTotle(this.daytradeDao.selectPayTotleMoney2(paydate,merchantcode));/*设置 总交易金额*/
			d.setMoneyBt(this.subsidyDao.selectSubsidyTotleMoney2(paydate,merchantcode));/*设置 补贴总额*/
			
			d.setMoneyTx(this.tx_resultDao.selectTXTotleMoney2(paydate,merchantcode));/*设置 提现总额*/
			d.setCountTx(this.tx_resultDao.selectTXTotleNum2(paydate,merchantcode));/*设置 提现笔数*/
			
			list.add(d);
//			list.add(this.daystaticDao.selectColTotle());
			dataGrid.setRows(list);
		}
		return dataGrid;
	}
	
	/*设置导出excel的内容*/
	@Override
	public InputStream getInputStream() throws Exception {
		String[] title = new String[]{"日期","T0交易金额","T0交易笔数","T1交易金额","T1交易笔数","总交易金额","补贴总额","提现总额","提现笔数"};
		List<daystatic> list =  this.daystaticDao.selectDaystatic("");
		for(daystatic d:list){
			System.out.println(d.getT0Paymoney());
		}
		List<Object[]> dataList = new ArrayList<Object[]>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); /*HH:mm:ss*/
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = new Object[title.length];
			
			obj[0] = df.format(list.get(i).getGdate()); 
			obj[1] = list.get(i).getT0Paymoney() + ""; 
			obj[2] = list.get(i).getT0Paynum();
			obj[3] = list.get(i).getT1Paymoney() + ""; 
			obj[4] = list.get(i).getT1Paynum(); 
			obj[5] = list.get(i).getPayTotle() + ""; 
			obj[6] = list.get(i).getMoneyBt() + ""; 
			obj[7] = list.get(i).getMoneyTx() + "";  
			obj[8] = list.get(i).getCountTx(); 
			
			dataList.add(obj);
		}
		WriteExcel excel = new WriteExcel(title, dataList);
		InputStream inputStream;
		inputStream = excel.export();
		
		return inputStream;
	}
	
}
