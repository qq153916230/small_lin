package com.app.service;

import java.io.InputStream;
import java.util.List;

import com.app.entity.Loan;


public interface LoanService {
	
	public List<Loan> selectAll_loans();//查询所有
	
	public List<Loan> selectNewRegister();//查询新申请用户
	
    public void updateByTidSelective(String[] tids,int status);//更新状态
    
    public List<Loan> search(String regTimeFrom,String regTimeTo,String phone,String status,String page,String rows); //搜索功能
    public int searchCount(String regTimeFrom,String regTimeTo,String phone,String status,String page,String rows); //搜索总数
    
    public InputStream getInputStream() throws Exception;/*导出Excel*/
}
