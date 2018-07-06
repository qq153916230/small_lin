package com.app.service;

import java.io.InputStream;

import com.app.util.DataGrid;

public interface DaystaticService {
	
	public void insertBySelectJoin(); /*向日统计表插入数据*/
	
	public DataGrid listDataGrid(String paydate,String merchantcode);/*查询业绩总条数*/
	
	public InputStream getInputStream() throws Exception;/*导出Excel*/
}
