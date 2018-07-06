package com.app.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface AidgoldService {
	
	DataGrid selectList(HttpServletRequest request); //列表展示
	
	/**催收次数 +1*/
	void addcs(HttpServletRequest request);
	
	/**根据主键查找*/
	JSON selectByPid(HttpServletRequest request);

	/**更新状态*/
	JSON updateStatus(HttpServletRequest request);
	
	/**更新状态2*/
	JSON updateStatus2(HttpServletRequest request);

	/**跨域访问的地址*/
	JSON getApplyResult(HttpServletRequest request);

	/**通用跨域访问*/
	JSON callUrl(HttpServletRequest request);

	/**跨域上传图片*/
	JSON appUploadIdPic(HttpServletRequest request, HttpServletResponse response)  throws IOException;

	/**查询用户刷卡，额度信息*/
	String selLogCon(HttpServletRequest request);

	/**修改jkmoney*/
	JSON updateJKMoney(HttpServletRequest request);
	
	/**重置客户身份信息*/
	JSON resetIdentity(HttpServletRequest request);

	/**查询是否满足体验贷要求*/
	JSON tyCheck(HttpServletRequest request);

	/**助理金 待审核 查看的资料*/
	JSON selectDetailInfo(HttpServletRequest request);

	/**统计jkmoney*/
	JSON countJKmoney(HttpServletRequest request);

	/**根据tid更新 助力金表 agentid*/
	JSON setAgent(HttpServletRequest request);

	/**u 31 to 21*/
	JSON u31to21(HttpServletRequest request);

	/**查询card*/
	JSON selectCard(HttpServletRequest request);

	/**添加备注*/
	JSON updateDeclare(HttpServletRequest request);

	/**提额*/
	JSON increaseAmount(HttpServletRequest request);

	/**添加黑名单*/
	JSON lahei(HttpServletRequest request);

	/**黑名单列表*/
	JSON blacklist(HttpServletRequest request);

	/**移除黑名单*/
	JSON removeBlacklist(HttpServletRequest request);

	/**捷安*/
	JSON jiean(HttpServletRequest request);
	
	/**定期 移除黑名单*/
	void removeBlacklistTiming();

	/**处理助力金 agentid为0的数据*/
	JSON dealAgentid(HttpServletRequest request);

	/** 新颜爬虫 提额 */
	JSON xyUpdateBalance(HttpServletRequest request);

}
