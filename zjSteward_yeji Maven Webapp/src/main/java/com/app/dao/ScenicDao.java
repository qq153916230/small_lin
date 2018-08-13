package com.app.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.app.entity.Aidgold;
import com.app.entity.Scenic;

public interface ScenicDao {
	
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<Scenic> selectList(Map<String, Object> map); 

	/**获取用户余额*/
	BigDecimal selectScenicBalanceFromUser(int umid);

	/**更新余额*/
	void updateScenicBalanceFromUser(@Param("mid")int mid, @Param("ubalance")BigDecimal ubalance);
	
	/**插入购票历史*/
	void insertScenicToHis(@Param("umid")int umid, @Param("sid")int sid, @Param("tNum")int tNum, @Param("tPrice")BigDecimal tPrice);

	/**select t_zjgj_scenic_user*/
	Map<String, Object> selectScenicUser(int mid);

	/**获取所有景区*/
	List<Scenic> selectScenicAll();

	/**获取票列表*/
	List<Map<String, Object>> selectTicketList(int mid);

	/**根据手机获取mid*/
	Integer selectMidByMobileFromMember(String mobile);

	/***/
	void insertScenicUser(Map<String, Object> map);

	void resetBalance(Map<String, Object> map);

	/**统计当日购票次数*/
	int selectTicketExistToday(Map<String, Object> map);

    /**根据tid修改票历史的tvalid*/
	void updateTicketValid(int tid);

	/**获取已添加亲属数量*/
	int selectRelativeCount(int mid);
	/** 添加亲属 */
	void insertRelative(Map<String, Object> map);
	/** 查询亲属列表 */
	List<Map<String, Object>> selectRelative(int mid);
	
	/** 查询工作人员账号 */
	Integer selectScenicManager(@Param("user")String user, @Param("pass")String pass);

    /**  景区人员获取门票  */
	List<Map<String, Object>> selectTicketByManager(@Param("sid")Integer sid, @Param("mid")Integer mid);

	/** 景区人员条件查询票列表 */
	List<Map<String, Object>> selectAllTicketByManager(Map<String, Object> map);

    /** 查看用户已经购买门票数 */
	int selectCountTicketByMid(int mid);

    /** 1元购票预订单创建 */
	int insertOneOrder(@Param("order")String order, @Param("mobile")String mobile, @Param("sid")int sid, @Param("ptype") int ptype);

	/** 根据订单号查询 mobile sid */
	Map<String, Object> selectOrder(String order);

	/** 根据订单 获取 `t_zjgj_epay_payment` */
	Map<String, Object> select_epay_payment_by_orderid(String order);
	
	/** 获取景区 */
	Map<String, Object> selectScenicBySid(int sid);

	/** 查询绑定的人数 */
	int selectCountRelativeByMid(int mid);
	
	
    int deleteByPrimaryKey(Integer sid);

    int insert(Scenic record);

    int insertSelective(Scenic record);

    Scenic selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(Scenic record);

    int updateByPrimaryKey(Scenic record);







}