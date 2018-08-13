package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface ScenicService {

	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);

	/**新增景区*/
	JSON scenicInsert(HttpServletRequest request);

	/**买票*/
	String buyTicket(HttpServletRequest req);

	/**删除景区*/
	JSON scenicDelete(HttpServletRequest request);

	/**jsonp获取额度*/
	String getBalance(HttpServletRequest req);

	/**jsonp获取景区列表*/
	String getScenicList(HttpServletRequest req);

	/**jsonp方式根据sid获取景区*/
	String getScenicBySid(HttpServletRequest req);

	/**jsonp方式获取票列表*/
	String getTicketList(HttpServletRequest req);

	/**重置额度*/
	JSON resetBalance(HttpServletRequest req);

	/**验票*/
	String checkTicket(HttpServletRequest req);

	/**jsonp方式添加亲属*/
	String addRelative(HttpServletRequest req);
	
	/**jsonp方式获取亲属*/
	String selectRelative(HttpServletRequest req);

	/** 工作人员登入 */
	JSON login(HttpServletRequest request);

	/** 景区人员获取门票 */
	JSON selectTicketByManager(HttpServletRequest request);

	/** 景区人员验票 */
	JSON checkTicketByManager(HttpServletRequest request);

	/** 景区人员获取所有的票 条件查询 */
	JSON selectAllTicketByManager(HttpServletRequest request);

	/** 1元购票预订单创建 */
	JSON preOrder();
	
	/** 1元购票活动 */
	JSON buyTicketOne();

	/** 根据mid 获取票列表  */
	JSON selectTicketByMid();

	/** 添加亲属 */
	JSON saveRelative(HttpServletRequest req);

	/** 获取亲属 */
	JSON findRelative(HttpServletRequest req);

	
	

}
