package com.app.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.annotations.Param;

import com.app.entity.Aidgold;

public interface AidgoldDao {
	
	Long selectTotle(Map<String, Object> map); //查询总记录数

	List<Aidgold> selectList(Map<String, Object> map); //查询列表
	
	/**催收次数 +1*/
	void addcs(int tid);
	
	/**批量更新状态*/
	void updateStatus(Map<String, Object> map); 
	
	/**把查询代理商邮箱的sql语句写到助力金表的映射*/
	String selectAgentEmail(int agentMid);

	/**查询用户刷卡，额度信息*/
	String selLogCon(@Param("mid")String mid, @Param("stype")String stype);
	
	/**查询总交易额 和 总交易笔数*/
	Map<String, Object> selectTradeMap(@Param("uname")String uname, @Param("umobile")String umobile);

	/**查询还款总数*/
	Long selectHKTotle();
	/**查询还款列表*/
	List selecHKtList();
	
	/**查询逾期列表*/
	Long selectOverdueTotle();
	List selectOverduetList();

	/**查询卡表的ischeck是否经过二要素验证*/
	String selectCardIscheck(int imid);

	/**查询Daytrade表近一个月的总交易额*/
	BigDecimal selectDaytradeRecentlyPaymoney(@Param("uname")String uname, @Param("umobile")String umobile);

	/**根据mid修改*/
	void updateByMidSelective(Aidgold record);

	/**查找member表数据*/
	Map<String, String> selectPhoneName(int imid);
	
	/**查询借款次数*/
	int selectJkNumByMid(int mid);
	
	/*t_zjgj_member_bank数据*/
	Map<String, String> selectBankData(int mid);
	
	/**更新 t_zjgj_member 的 verfiy_flag 为 0*/
	void updateMemberFlag(int mid);
	
	/**查询总借款额*/
	String sumJKmoney(Map<String, Object> map);
	
	/**查询总借款额 jkcheck = 80*/
	String sumJKmoney80();
	/**查询总借款额 jkcheck = 81*/
	String sumJKmoney81();
	
	/**根据手机查询t_zjgj_agent 的 mid*/
	int selectMidByMobileFromAgent(String mobile);

	/***/
	void updateAidgoldInfoIsupdateTo1(int mid);
	
	/**查询card*/
	List selectCardList(int mid);
	
	/**修改member表xygd*/
	void updateMemberXYGD(@Param("mid") int mid, @Param("jkmoney") int jkmoney);

	/**查询当月的提额数量*/
	int selectHasTE(@Param("mid") int mid, @Param("searchData") String searchData);

	/**插入t_zjgj_aidgold_telog 提额记录*/
	void insertTelog(Map<String, Object> teMap);
	
	/**添加黑名单*/
	void insertBlacklist(Map<String, Object> map);
	
	/**黑名单列表*/
	List<Map<String, Object>> selectBlacklist(int mid);
	
	/**移除黑名单*/
	void deleteBlacklist(int tid);

	/**查询黑名单tid date*/
	List<Map<String, Object>> selectBlacklistTidDate();
	
	/**批量删除 黑名单*/
	void deleteBlacklistBatch(List<Integer> tids);

	/**查询数据库捷安对象*/
	Map<String, String> selectJieAnResp(Map<String, String> map);
	
	/**插入捷安返回数据*/
	void insertJieAnResp(Map<String, String> map);
	
	/**根据 手机 查找t_rc_pos_assign表mid*/
	int selectMidFromAssign(String mobile);

	
	
	
    int deleteByPrimaryKey(Integer tid);

    int insert(Aidgold record);

    int insertSelective(Aidgold record);

    Aidgold selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(Aidgold record);

    int updateByPrimaryKey(Aidgold record);

}