package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface CommonService {
	
	/** 登入系统  */
	JSON login();

	/** 获取信用卡卡列表 */
	JSON selectCardList(HttpServletRequest req);

	/** 记录登入设备信息 */
	JSON insertLoginMobile(HttpServletRequest req);

	/** 获取app登入信息 */
	JSON selectLoginMobileByMid(HttpServletRequest req);

	/** 查询版本号 */
	JSON selectAppVersionBySystem(HttpServletRequest req);

	/**查询通讯爬虫结果*/
	JSON selectSpiderMobile(HttpServletRequest req);

	/**修改密码*/
	JSON updatePass(HttpServletRequest req);

	/** 根据手机查询通讯爬虫订单号 */
	JSON selectTradeNoByMobile(HttpServletRequest req);

	/**查询通讯爬虫结果 通过mid*/
	JSON selectSpiderMobileByMid(HttpServletRequest req);

	/** 个人信用爬取结果异步通知地址 */
	void personMsgOrder(HttpServletRequest req);

	/** 查询信用信息 通过mid*/
	JSON selectCreditMsgByMid();

	/** 更新信用信息 */
	JSON update_credit_msg();

	/** 更新 t_xy_spider_person */
	JSON updateSpiderPersonByColumnName();

	/** 获取 t_xy_spider_person */
	JSON selectSpiderPersonMsgByMid();

	/** 通讯录爬取异步通知 */
	void mobileMsgOrder(HttpServletRequest req);
	
	/** 医社保异步通知 */
	void notifySecurity(HttpServletRequest req);
	
	/** 公积金异步通知 */
	void notifyFund(HttpServletRequest req);
	
	/** 车险保单异步通知 */
	void notifyInsurance(HttpServletRequest req);

	/** app获取服务列表 */
	JSON getMemberServerList();

	/** 查看该卡是否是新卡 */
	JSON newCardCheck();

	/** 查询必填个人信息 */
	JSON selectAidgoldMustFill(HttpServletRequest req);

	/** 访问新颜接口 获取新颜个人信息订单号 */
	JSON createXinyanTradeNo(HttpServletRequest req);
	
	@Deprecated
	/** 访问新颜接口 获取新颜个人信息订单号(通用)(弃用) */
	JSON xyCommonCreateTradeNo(HttpServletRequest req);

	/** 查询邮箱 */
	JSON selectSpiderEmail(HttpServletRequest req);

	/** 查询个人信息 */
	JSON selectSpiderPerson(HttpServletRequest req);

	/** 插入邮箱账号 */
	JSON insertSpiderEmailAccount(HttpServletRequest req);

	/** 访问新颜接口 获取新颜医社保订单号 */
	JSON createXinyanSecurityTradeNo(HttpServletRequest req);

	/** 访问新颜接口 获取新颜公积金订单号 */
	JSON createXinyanFundTradeNo(HttpServletRequest req);

	/** 访问新颜接口 获取新颜车险保单订单号 */
	JSON createXinyanInsuranceTradeNo(HttpServletRequest req);

	/** 微信支付 */
	JSON wxpay(HttpServletRequest req);

	/** 微信支付异步通知 */
	String wxpay_notify(HttpServletRequest req);

	/** 根据ordercode查询 */
	DataGrid txRewardDetail();

	/** 财富通提现申请列表 */
	DataGrid txApply_cft();

	/** 财富通提现申请处理 */
	JSON txApply_cft_deal();

	/** 扣费贴息激活 机具列表 */
	DataGrid subsidyPosList();

	/** 保存 代理商报名参与活动 */
	JSON posActivitySave(HttpServletRequest req);

	/** 获取新颜邮箱账单 */
	JSON xyEmailCardBills();


}
