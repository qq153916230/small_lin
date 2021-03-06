package com.app.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonDao {
	
	/** t_zjgj_card 拼接 t_bank 银行的 url */
	List<Map<String, Object>> selectCardList(int mid);
	
	/** t_app_login_check 插入app登入设备信息 */
	void insertLoginMobile(Map<String, Object> map);
	
	/** t_app_login_check 获取app登入信息 */
	List<Map<String, Object>> selectLoginMobileByMid(int mid);
	
	/** t_app_version 查询版本号 */
	List<Map<String, Object>> selectAppVersionBySystem(@Param("sys") String sys, @Param("ver") String ver);

	/** t_xy_spider_mobile 查询通讯爬虫结果*/
	Map<String, Object> selectSpiderMobile(String mobile);
	
	/** t_xy_spider_email 查询邮箱爬取结果 */
	List<Map<String, Object>> selectSpiderEmail(int mid);

	/** t_zjgj_member 修改密码 */
	int updateMemberPass(@Param("mobile")String mobile, @Param("pass")String pass);

	/** t_zjgj_member 匹配手机 */
	int matchMobile(String mobile);

	/** t_xy_spider_mobile 根据手机查询通讯爬虫订单号 */
	String selectTradeNoByMobile(String mobile);

	/** `t_zjgj_member` 根据mid查手机 */
	String selectMobileByMidFromMember(int mid);
	
	/** 根据mobile查询mid */
	Integer selectMidByMobileFromMember(String mobile);

	/** `t_zjgj_member` 根据账号密码查对象 */
	Map<String, Object> selectMemberByUnameAndPass(@Param("username")String username, @Param("password")String password);

	/** `t_zjgj_user` 根据账号密码查对象 */
	Map<String, Object> selectSysUserByUnameAndPass(@Param("username")String username, @Param("password")String password);

	/** t_credit_msg 查询信用信息 通过mid */
	Map<String, Object> selectCreditMsgByMid(int mid);

	/** t_credit_msg 插入新记录 */
	Integer insert_credit_msg(int mid);

	/** t_credit_msg 更新记录 */
	Integer update_credit_msg(Map<String, Object> map);

	/** 更新 t_xy_spider_person is_done is_valid 为1 */
	void update_spider_person_status(String trade_no);
	
	/** 更新 `t_xy_spider_email` 的status 为1 */
	void update_spider_email_status(@Param("trade_no")String trade_no, @Param("status")int status);
	
	/** 更新 t_xy_spider_mobile is_done is_valid 为1 */
	void update_spider_mobile_status(String trade_no);
	
	/** 通过复合主键 更新 t_xy_spider_person */
	void updateSpiderPersonByColumnName(Map<String, Object> map);
	
	/** 根据mid 获取t_xy_spider_person 信息  */
	List<Map<String, Object>> select_spider_person_msg_by_mid(int mid);

	/** app获取服务列表 */
	String selectServerListByMid(int mid);

	/** `t_zjgj_card` 根据卡号查询卡数 */
	int selectCountByCardNo(String cardno);

	/** `t_xy_spider_email` 插入邮箱 */
	int insertXinyanEmail(Map<String, Object> map);

	/** t_xy_spider_person 查询是否存在记录 */
	int selectCountSpiderPerson(@Param("mid")int mid, @Param("txn_type")String txn_type);

	/** t_xy_spider_person 插入数据 */
	void insertSpiderPerson(Map<String, Object> map);

	/** t_xy_spider_person 替换数据 */
	void replaceSpiderPerson(Map<String, Object> map);
	
	/** t_xy_spider_person 查询个人信息 */
	Map<String, Object> selectSpiderPerson(@Param("mid")int mid, @Param("type")String type);

	/** t_zjgj_member */
	Map<String, Object> selectMemberByMid(int mid);

	/** 插入邮箱账号 */
	int insertSpiderEmailAccount(Map<String, Object> map);

	/** 根据手机号 查询 `t_rc_pos_assign` */
	int selectCountPosAssign(String mobile);
	
	/** 根据手机号 查询 `t_rc_pos` */
	int selectCountPos(String mobile);

	/** t_xy_spider_person 根据订单号查询mid */
	Integer selectMidFromSpiderPersonByOrder(String order);
	
	/** t_zjgj_member 根据mid获取用户额度 */
	Integer selectMemberXygdByMid(int mid);

	/** `t_zjgj_aidgold_telog_xy` */
	int selectCountFromTelogXyByMidType(@Param("mid")int mid, @Param("type")String type);
	
	/** t_zjgj_aidgold_telog_xy */
	void insertTelogXy(Map<String, Object> map);

	/** `t_zjgj_member_bank` */
	Map<String, Object> selectMemberBankByMid(int mid);

	/** `t_zjgj_member_bank` 查询询卡列表 */
	List<Map<String, Object>> selectMemberBank(Map<String, Object> param);
	
	/** `t_zjgj_member_bank` 查询总数 */
	int selectCountMemberBank(Map<String, Object> param);

	/**  */
	int selectCountMemberBankByMid(int mid);
	
	/**  */
	void updateMemberBank(Map<String, Object> params);
	
	/**  */
	void insertMemberBank(Map<String, Object> params);
	


	/** `wx_order` 添加  */
	void insertWXOrder(Map<String, Object> map);

	/** 根据订单修改wx_order状态 */
	void updateWXOrderStatus(String orderid);
	
	/** 查看状态 */
	int selectWXOrderStatus(String orderid);
	
	/** `t_zjgj_epay_payment` 添加 */
	void insertEpayPayment(Map<String, Object> map);

	/** t_zjgj_tx_reward 根据ordercode查询 */
	List<Map<String, Object>> selectTXRewardByOrdercode(Map<String, Object> map);
	/** t_zjgj_tx_reward 查询总数 */
	int selectCountTXReward(Map<String, Object> map);	
	
	/** 查询要导出的数据 */
	List<Map<String, Object>> selectTXReward(Map<String, Object> map);

	/** t_drp_tixian_apply */
	List<Map<String, Object>> selectTXApply_cft(Map<String, Object> param);

	/**修改`t_drp_tixian_apply`表状态*/
	int updateDrpTixianApplyStatus(Map<String, Object> param);

	/**根据tid 查询t_drp_tixian_apply记录*/
	List<Map<String, Object>> selectDrpTixianApplyStatusByTids(String[] tids);

	/** 批量保存 t_zjgj_tx  */
	int insertZjgjTX(List<Map<String, Object>> data);

	/** `t_zjgj_subsidy_pos` 列表 */
	List<Map<String, Object>> selectSubsidyPosList(Map<String, Object> param);

	/** t_plat_market 保存 */
	int insertPlatMarket(Map<String, Object> param);

	/** t_zjgj_card 查询已实名的卡 */
	List<String> selectAutonymCardByMid(int mid);

	/** t_rc_t0 统计交易额 */
	BigDecimal selectSumPaymoney(Map<String, Object> params);
	
	/** t_rc_t0 统计最大交易额 */
	BigDecimal selectMaxPaymoney(Map<String, Object> params);

	/** t_zjgj_aidgold 查询已成功借款数量 */
	int selectCountAidgoldByMid(int mid);

	/** `t_zjgj_aidgold_blacklist` 统计黑名单列表 */
	int selectCountBlackListByMid(int mid);

	/** `t_app_news` 获取新闻，公告 */
	List<Map<String, Object>> selectNewsList(@Param("type") int type, @Param("permission") int permission);

	/** `t_ship_member` 列表 */
	List<Map<String, Object>> selectShipMemberList(Map<String, Object> map);

	/** `t_ship_member` 总数 */
	int selectCountShipMember(Map<String, Object> map);

	/** `t_ship_member` */
	Map<String, Object> selectShipMemberByMid(int mid);

	/** `t_ship_member` 更新userrole */
	int updateUserroleByMidFromShipMember(@Param("mid")int mid, @Param("userrole")int userrole);

	/** `t_ship_member`保存 */
	int insertShipOption(Map<String, Object> map);
	
	/** `t_ship_option` 列表 */
	List<Map<String, Object>> selectShipOptionList(Map<String, Object> map);

	/** `t_ship_option` 总数 */
	int selectCountShipOption(Map<String, Object> map);
	
	/** `t_ship_option` 更新isreward */
	int updateIsrewardByTidFromShipOption(@Param("tid")int tid, @Param("isreward")int isreward);
	
	/** `t_ship_option` */
	Map<String, Object> selectShipOptionByTid(int tid);

	/** `t_app_email_bill_trash` 保存 已移除的卡 */
	void insertEmailCardTrash(@Param("mid")int mid, @Param("bank")int bank_id, @Param("card")String card_number);

	/** 删除 已移除的卡 */
	void deleteEmailCardTrash(@Param("mid")int mid, @Param("bank")int bank_id, @Param("card")String card_number);

	/** 移除的卡列表 */
	List<Map<String, Object>> selectEmailCardTrashList(int mid);

	/** `t_ship_tixian` 列表 */
	List<Map<String, Object>> selectShipTixianList(Map<String, Object> map);

	/** `t_ship_提现` 统计 */
	int selectCountShipTixian(Map<String, Object> map);

	/** t_ship_tixian 提现审核 */
	int updateStatusByTidsFromShipTixian(@Param("status") int status, @Param("tids") String[] tids);

	/** `t_ship_member_account` 将 freeze的钱转移到 tixian */
	int moveFreezeToTixianFromMemberAccountByMids(String[] mids);







}
