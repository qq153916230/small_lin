package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface AidgoldAgentService {

	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);

	/**修改totalgd总额度额*/
	JSON updateTotalgd(HttpServletRequest request);

	/**查询权限*/
	JSON selectUserRole(HttpServletRequest request);

	/**修改member表邮箱*/
	JSON updateMemberEmail(HttpServletRequest request);

	/**修改代理商扣率*/
	JSON updateAgentrate(HttpServletRequest request);

	/**修改代理商过桥手续费*/
	JSON updateAgentBridgegd(HttpServletRequest request);

}
