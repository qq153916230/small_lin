package com.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.AidgoldAgentDao;
import com.app.dao.AidgoldDao;
import com.app.dao.AidgoldLogDao;
import com.app.dao.CommonDao;
import com.app.dao.MemberAddrDao;
import com.app.dao.MemberAuthorDao;
import com.app.dao.daytradeMapper;
import com.app.entity.Aidgold;
import com.app.entity.AidgoldAgent;
import com.app.entity.AidgoldLog;
import com.app.entity.MemberAddr;
import com.app.service.AidgoldService;
import com.app.util.CallUrlByGet;
import com.app.util.DataGrid;
import com.app.util.LogTableInsert;
import com.app.util.jiean.VerifyClient;
import com.app.util.zjgj.ZjUtils;


@Service
public class AidgoldServiceImpl extends ZjUtils implements AidgoldService {
	
	@Autowired
	AidgoldDao aidgoldDao;
	@Autowired
	AidgoldLogDao aidgoldLogDao;
	@Autowired
	AidgoldAgentDao aidgoldAgentDao;
	@Autowired
	MemberAuthorDao memberAuthorDao;
	@Autowired 
	daytradeMapper daytradeDao;
	@Autowired
	MemberAddrDao memberAddrDao;
	
	@Autowired
	CommonDao commonDao;
	
	
	//列表展示
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = getStr(request, "regTimeFrom");
		String regTimeTo = getStr(request, "regTimeTo");
		String skname = getStr(request, "skname");
		String jkcheck = getStr(request, "jkcheck");
		String orderid = request.getParameter("orderid");
		int page = getInt(request, "page");
		int rows = getInt(request, "rows");
		
		int mid = getInt(request, "mid");
		
		DataGrid dg = new DataGrid();
		
		//如果jkcheck为80表示查询即将还款记录
		if ("80".equals(jkcheck)) {
			dg.setTotal(this.aidgoldDao.selectHKTotle());
			dg.setRows(this.aidgoldDao.selecHKtList());
			return dg;
		}
		//如果jkcheck为81表示查询逾期记录
		if ("81".equals(jkcheck)) {
			dg.setTotal(this.aidgoldDao.selectOverdueTotle());
			dg.setRows(this.aidgoldDao.selectOverduetList());
			return dg;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("skname", skname);
		map.put("jkcheck", jkcheck);
		map.put("orderid", orderid);

		map.put("mid", mid == 0 ? null:mid);
		
		int pag = (page-1)*100;
		map.put("page", pag);
		map.put("rows", rows);
		
		dg.setTotal(this.aidgoldDao.selectTotle(map));
		dg.setRows(this.aidgoldDao.selectList(map));
		return dg;
	}
	
	/**查询card*/
	@Override
	public JSON selectCard(HttpServletRequest request) {
		
		int mid = getInt(request, "mid");
		
		List list = this.aidgoldDao.selectCardList(mid);
		JSON json = (JSON) JSON.toJSON(list);
		return json;
	}

	/**催收次数 +1*/
	@Override
	public void addcs(HttpServletRequest request) {
		int tid = getInt(request, "tid");
		this.aidgoldDao.addcs(tid);
	}
	
	/**根据主键查找*/
	@Override
	public JSON selectByPid(HttpServletRequest request) {
		String rid = request.getParameter("tid");
		int tid = 0;
		if (rid != null && !"".equals(rid) && !"null".equals(rid)) {
			tid = Integer.parseInt(rid);
		}
		Aidgold aidgold = this.aidgoldDao.selectByPrimaryKey(tid) ;
		JSON json = (JSON) JSON.toJSON(aidgold);
		return json;
	}
	
	/**批量修改状态*/
	@Override
	@Transactional
	public JSON updateStatus(HttpServletRequest request) {
		
		//获取json传过来的数据
		String idString = getStr(request, "ids");//ids是一个逗号分隔的字符串
		String[] idArray = idString.split(",");
		int status = getInt(request, "newStatus");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//时间格式化
		Calendar currentDate = new GregorianCalendar(); //当前日期
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("ids", idArray);
		map.put("jkdate", sdf.format(currentDate.getTime()));	//开始借款日期
		currentDate.set(Calendar.DATE, currentDate.get(Calendar.DATE)+14);	//设置成15天后日期
		map.put("enddate", sdf.format(currentDate.getTime()));	//借款结束日期
		
		int idLenth = idArray.length;
		
		Aidgold aidgoldDB = null;
		if (idLenth == 1 && (status == 43 || status == 21) ) {
			aidgoldDB = this.aidgoldDao.selectByPrimaryKey(Integer.parseInt(idArray[0]));	//数据库助理金对象
		}
		if(status == 43 && idLenth == 1){ //已放贷要修改日期
			
			String fkdate = getStr(request, "fkdate");	//放款日期
			
			Date date = null;
			try {
				date = sdf.parse(fkdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = new GregorianCalendar();
	        //cal.set(Calendar.HOUR_OF_DAY, 0);
	        //cal.set(Calendar.MINUTE, 0);
	        //cal.set(Calendar.SECOND, 0);
	        //cal.set(Calendar.MILLISECOND, 0);
	        //cal.set(Calendar.DATE, 2);	//今天的日期
	        //String jkdate = sdf.format(cal.getTime());
			cal.setTime(date);
	        
	        cal.set(Calendar.DATE, cal.get(Calendar.DATE)+14);	//借款日期为15天
	        String enddate = sdf.format(cal.getTime());
			
			map.put("jkdate", fkdate);
			map.put("enddate", enddate);
			
			System.out.println("接收的tid字符串："+idString + " 处理过的id字符串：" +idArray[0]);
			
			int jkMoney = aidgoldDB.getJkmoney();	//借款金额
			int mid = aidgoldDB.getMid();	//mid
			int agentID = aidgoldDB.getAgentid();	//代理商对象
			
			AidgoldAgent aDB = this.aidgoldAgentDao.selectByPrimaryKey(agentID);	//客户总账户表对象
			AidgoldAgent aData = new AidgoldAgent();	//保存操作数据
			
			if (aDB != null) {
				aData.setMid(agentID);	//设置mid
				aData.setUsedgd(aDB.getUsedgd().add(new BigDecimal(jkMoney)));	//设置已用金额
				
				this.aidgoldAgentDao.updateByPrimaryKeySelective(aData);	//更新已用金额
			} else {
				System.out.println("没有查询到账户表信息 ID：" + agentID);
			}
			
		}
		
		//状态回滚到21 已付手续费
		if(status == 21 && idLenth == 1){ 
			this.aidgoldDao.updateAidgoldInfoIsupdateTo1(aidgoldDB.getMid());
		}
		
		this.aidgoldDao.updateStatus(map);
		
		//发送邮件
		//if(status == 41 && idArray.length == 1){}
		
		//插入日志表
		HttpSession session = request.getSession();	//用来获取系统管理员用户名 插入日志表
		String mid = (String)session.getAttribute("systemManagerUserName");
		String content = "管理员："+mid + ",修改助力金的jkcheck为："+ status + " 助力金编号："+idString;
		AidgoldLog aidgoldLog = LogTableInsert.insert(mid, "aidgold", content);
		this.aidgoldLogDao.insertSelective(aidgoldLog);
		
		return statusMsgJson(1, "更新成功"); 
	}
	/**修改状态2*/
	@Override
	@Transactional
	public JSON updateStatus2(HttpServletRequest request) {
		
		HttpSession session = request.getSession();	//用来获取系统管理员用户名 插入日志表
		
		int tid = getInt(request, "tid");
		short jkcheck = getShort(request, "jkcheck");
		Aidgold ag = new Aidgold();
		ag.setTid(tid);
		ag.setJkcheck(jkcheck);
		this.aidgoldDao.updateByPrimaryKeySelective(ag);
		
		//插入日志表
		String mid = (String)session.getAttribute("systemManagerUserName");
		String content = "管理员："+mid + ",修改助力金的jkcheck为："+ jkcheck + " 助力金编号："+tid;
		AidgoldLog aidgoldLog = LogTableInsert.insert(mid, "aidgold", content);
		this.aidgoldLogDao.insertSelective(aidgoldLog);
		
		Aidgold agDB = this.aidgoldDao.selectByPrimaryKey(tid);
		if (jkcheck == 12) {
			this.aidgoldDao.updateAidgoldInfoIsupdateTo1(agDB.getMid());
		}
		
		return statusMsgJson(1, "更新成功");
	}
	
	/**u 31 to 21 没用*/
	@Override
	@Transactional
	public JSON u31to21(HttpServletRequest request) {
		
		int tid = getInt(request, "ids");
		short jkcheck = getShort(request, "newStatus");
		Aidgold ag = new Aidgold();
		ag.setTid(tid);
		ag.setJkcheck(jkcheck);
		this.aidgoldDao.updateByPrimaryKeySelective(ag);
		
		Aidgold agDB = this.aidgoldDao.selectByPrimaryKey(tid);
		
		//插入日志表
		String content = "管理员修改助力金的jkcheck为："+ jkcheck + " 助力金编号："+tid;
		AidgoldLog aidgoldLog = LogTableInsert.insert("0", "aidgold", content);
		this.aidgoldLogDao.insertSelective(aidgoldLog);
		
		this.aidgoldDao.updateAidgoldInfoIsupdateTo1(agDB.getMid());
		
		return statusMsgJson(1, "更新成功");
	}
	
	/**跨域访问的地址*/
	@Override
	public JSON getApplyResult(HttpServletRequest request) {

		final String urlAddres = "http://localhost:8055/DataHandler.ashx?action=aidgold_applyresult&mid=";
		
		String mid = getStr(request, "mid");
		String url = urlAddres+mid;	System.out.println("==="+url+"===");
		String result = CallUrlByGet.callUrlByGet(url, "GBK");
 
		//request.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		//PrintWriter out = response.getWriter();  
		//out.print(result);  
		//out.close(); 
        JSONObject jsonObject = JSONObject.parseObject(result);
        
		return jsonObject;
	
	}
	/**通用跨域访问*/
	@Override
	public JSON callUrl(HttpServletRequest request) {
		String urlAddress = getStr(request, "urlAddress");
		String mid = getStr(request, "mid");
		String url = urlAddress+"&mid="+mid;
		String result = CallUrlByGet.callUrlByGet(url, "GBK");
		//String jsonStr = "{\"money\":" + result + "}";
		JSON json = JSON.parseObject(result);
		return json;
	}
	
	/**跨域上传图片
	 * @throws IOException */
	@Override
	public JSON appUploadIdPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//获取请求参数
		String mobile = getStr(request, "mobile");
		String type = getStr(request, "type");
		String base64Str = getStr(request, "base64Str");
		base64Str = base64Str.split(",")[1];
		//System.out.println("获取的参数：" + mobile + " " + type+ " " + base64Str);
		
		HttpServletRequest httpRequest=(HttpServletRequest)request;  
        
		String strBackUrl = "http://" + request.getServerName() //服务器地址  
		                    + ":"   
		                    + request.getServerPort()           //端口号  
		                    + httpRequest.getContextPath();      //项目名称
		
		//String webPath = System.getProperty("user.dir")+ "/idPicture/" + mid;
		String webPath = request.getSession().getServletContext().getRealPath("") + "\\uploadFile\\idPicture\\" + mobile;
		//System.out.println("工程目录：" + webPath);	// E:\tomcat\tomcatZJ2Manager\bin
		String fileName = type + ".jpg";
		
		String filePath = strBackUrl + "/uploadFile/idPicture/" + mobile + "/" + fileName;	//上传图片保存在服务器的位置
		
		
		File dir = new File(webPath);	//保存图片的文件夹路径
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(webPath + "/" + fileName);	//图片路径
        if (!file.exists()){
        	file.createNewFile();
        }
		
		FileOutputStream fos;
		
		BASE64Decoder decoder = new BASE64Decoder();
		
		byte[] bytes = decoder.decodeBuffer(base64Str);
		fos = new FileOutputStream(file);
		fos.write(bytes);
		
		fos.flush();
		fos.close();
		System.out.println("文件上传成功 用户手机：" + mobile);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileName", fileName);
		jsonObject.put("filePath", filePath);
		jsonObject.put("status", 1);
		return jsonObject;
	}
	
	/**查询用户刷卡，额度信息*/
	@Override
	public String selLogCon(HttpServletRequest request) {
		String mid = getStr(request, "mid");
		String stype = getStr(request, "stype");
		
		//生成详细资料里面的额度信息
		final String urlAddres = "http://mobile.rczjgj.com/DataHandler.ashx?action=aidgold_houtai&mid=";	//&reset=1 aidgold_realgd
		CallUrlByGet.callUrlByGet(urlAddres+mid, "GBK");
		
		int imid = 0;	//用来查找 用户名 和 手机
		//用来查找日交易记录
		String uname = "";
		String umobile = "";
		
		if (mid != null && !mid.equals("")) {
			imid = Integer.parseInt(mid);
		}
		
		Map<String, String> maDB = this.aidgoldDao.selectPhoneName(imid);	//查找member表数据
		if (maDB != null) {
			uname = maDB.get("nick");
			umobile = maDB.get("mobile");
		}
		
		Map<String, Object> map = this.aidgoldDao.selectTradeMap(uname, umobile);	//查询总交易额 和 总交易笔数
		
		
		//BigDecimal paymoney = (BigDecimal) map.get("money");
		//String paynum = (String) map.get("num");
		
		String content = this.aidgoldDao.selLogCon(mid, stype);
		
		String cont = "【本人刷卡】" + "<br>" + (content == null?"暂无数据":content);	//查找日志内容
		/*String[] strs = cont.split(" ");
		for (String str : strs) {
			content += str + "<br>";
		}*/
		if (map != null) {
			cont += "<br><br>" + "【所有刷卡】" + "<br>" + "刷卡总量：" + map.get("money") + "<br>" + "总笔数：" + map.get("num");
		} else {
			cont += "<br><br>" + "【所有刷卡】" + "<br>" + "刷卡总量：" + "暂无数据" + "<br>" + "总笔数：" + "暂无数据";
		}
		
		//从日志字符串截取总交易额 用于提额
		if (content != null) {
			int posStart = content.indexOf(':');
			int posEnd = content.indexOf('总',posStart);
			String tradeTotle = "";
			if (posStart != -1 && posEnd != -1) {
				tradeTotle = content.substring(posStart + 1, posEnd).trim();
			}
		}
		
		return cont;
	}
	
	/**修改jkmoney 和 member表的xygd*/
	@Override
	@Transactional
	public JSON updateJKMoney(HttpServletRequest request) {

		int tid = getInt(request, "tid");	//获取tid
		int mid = getInt(request, "mid");	//获取mid
		int jkmoney = getInt(request, "jkmoney");	//修改的金额
		
		Aidgold agData = new Aidgold();
		agData.setTid(tid);
		agData.setJkmoney(jkmoney);
		
		this.aidgoldDao.updateByPrimaryKeySelective(agData);	//修改助力金 金额
		this.aidgoldDao.updateMemberXYGD(mid, jkmoney);	//修改member表real_xygd
		
		return statusMsgJson(1, "操作成功");
	}
	
	/**添加备注*/
	@Override
	public JSON updateDeclare(HttpServletRequest request) {

		int tid = getInt(request, "tid");	//获取tid
		String declare = getStr(request, "declare");	//
		
		Aidgold agData = new Aidgold();
		agData.setTid(tid);
		agData.setDeclare(declare);
		
		this.aidgoldDao.updateByPrimaryKeySelective(agData);	//
		
		return statusMsgJson(1, "操作成功");
	}
	
	/**重置客户身份信息*/
	@Override
	@Transactional
	public JSON resetIdentity(HttpServletRequest request) {
		
		int type = getInt(request, "retype");
		int tid = getInt(request, "tid");
		
		if (type == 1001) {
			int mid = getInt(request, "mid");	//获取mid
			
			this.aidgoldDao.updateMemberFlag(mid);	//更新 t_zjgj_member 的 verfiy_flag 为 0
			this.memberAuthorDao.deleteByPrimaryKey(mid);	//删除member_author表客户信息
			
		}
		//设置驳回类型
		Aidgold agData = new Aidgold();
		agData.setTid(tid);
		agData.setJkcheck((short)13);	//助力金状态13为驳回
		agData.setRetype(type);
		this.aidgoldDao.updateByPrimaryKeySelective(agData);
		
		return statusMsgJson(1, "操作成功");
	}
	
	
	/**查询是否满足体验贷要求*/
	@Override
	@Transactional
	public JSON tyCheck(HttpServletRequest request) {
		
		int imid = getInt(request, "mid");	//用来查找 用户名 和 手机
		int tid = getInt(request, "tid");
		//用来查找日交易记录
		String uname = "";
		String umobile = "";
		
		Aidgold agData = this.aidgoldDao.selectByPrimaryKey(tid);
		
		/*Map<String, String> maDB = this.aidgoldDao.selectPhoneName(imid);	//查找member表数据
		if (maDB != null) {
			uname = maDB.get("nick");
			umobile = maDB.get("mobile");
		}
		String ischeckStr = this.aidgoldDao.selectCardIscheck(imid);	//查询卡表的ischeck是否经过二要素验证
		*/
		int ischeck = 0;	
		if (agData != null) {
			ischeck = agData.getJkcheck();
		}
		//BigDecimal payMoney = this.aidgoldDao.selectDaytradeRecentlyPaymoney(uname, umobile);	//查询Daytrade表近一个月的总交易额
		
		JSONObject json = new JSONObject();
		
		/*boolean moneyCheck = false;	//检测刷卡金额是不是大于10000
		if (payMoney != null) {
			moneyCheck = payMoney.compareTo(new BigDecimal(10000)) >= 0;
		}*/
		
		if (ischeck == 1) {
			json.put("status", 1);
			json.put("result", "success");
		} else {
			json.put("status", 0);
			json.put("result", "failed");
		}
		
		//String res = ischeck==0?"否":"是";
		
		//String msg = "是否经过二要素验证:"+ res + " 最近一个月刷卡总额:" + payMoney;
		//json.put("msg", msg);
		
		//修改备注
		//Aidgold agData = new Aidgold();
		//agData.setMid(imid);
		//agData.setTid(tid);
		//agData.setRemark(msg);
		
		//this.aidgoldDao.updateByMidSelective(agData);
		//this.aidgoldDao.updateByPrimaryKeySelective(agData);
		
		return json;
	}
	
	/**助理金 实名 查看的资料*/
	@Override
	public JSON selectDetailInfo(HttpServletRequest request) {
		
		int mid = getInt(request, "mid");
		int tid = getInt(request, "tid");
		
		MemberAddr maDB = this.memberAddrDao.selectByMidOnly(mid);	//根据mid获取地址信息
		Map<String, String> bankDB = this.aidgoldDao.selectBankData(mid);	//t_zjgj_member_bank数据
		
		String address = "";
		if (maDB != null) {
			address = maDB.getAddress();
		}
		
		int jkNum = this.aidgoldDao.selectJkNumByMid(mid);	//查询借款次数
		
		JSONObject json = new JSONObject();
		json.put("address", address);
		json.put("jkNum", jkNum);
		
		String cardno = "";
		String master = "";
		String mobile = "";
		String bankname = "";
		if (bankDB != null) {
			cardno = bankDB.get("cardno");
			master = bankDB.get("master");
			mobile = bankDB.get("mobile");
			bankname = bankDB.get("bankname");
		}
		json.put("cardno", cardno);
		json.put("master", master);
		json.put("mobile", mobile);
		json.put("bankname", bankname);
		
		return json;
	}
	
	/**统计jkmoney*/
	@Override
	public JSON countJKmoney(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = getStr(request, "regTimeFrom");
		String regTimeTo = getStr(request, "regTimeTo");
		String skname = getStr(request, "skname");
		String jkcheck = getStr(request, "jkcheck");
		
		DataGrid dg = new DataGrid();
		//向页面返回数据
		JSONObject jsonObject = new JSONObject();
		String sumJKmoney = "0";
		
		//如果jkcheck为80表示查询即将还款记录
		if (jkcheck != null && "80".equals(jkcheck)) {
			sumJKmoney = this.aidgoldDao.sumJKmoney80();
			jsonObject.put("sumJKmoney", sumJKmoney);
			jsonObject.put("status", 1);
			return jsonObject;
		}
		
		//如果jkcheck为81表示查询逾期记录
		if (jkcheck != null && "81".equals(jkcheck)) {
			sumJKmoney = this.aidgoldDao.sumJKmoney81();
			jsonObject.put("sumJKmoney", sumJKmoney);
			jsonObject.put("status", 1);
			return jsonObject;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("skname", skname);
		map.put("jkcheck", jkcheck);

		sumJKmoney = this.aidgoldDao.sumJKmoney(map);
		jsonObject.put("sumJKmoney", sumJKmoney);
		jsonObject.put("status", 1);
		return jsonObject;
	}

	/**根据tid更新 助力金表 agentid*/
	@Override
	public JSON setAgent(HttpServletRequest request) {
		
		String mobile = getStr(request, "mobile");
		
		int tid = getInt(request, "tid");
		
		//根据手机查询t_zjgj_agent 的 mid
		int agentid = this.aidgoldDao.selectMidByMobileFromAgent(mobile);
		
		Aidgold agData = new Aidgold();
		agData.setTid(tid);
		agData.setAgentid(agentid);
		
		this.aidgoldDao.updateByPrimaryKeySelective(agData);
		
		return statusMsgJson(1, "success");
	}

	/**提额*/
	@Override
	@Transactional
	public JSON increaseAmount(HttpServletRequest request) {
		
		int tid = getInt(request, "tid");
		int mid = getInt(request, "mid");
		int terate = getInt(request, "terate");
		
		String currentData = getDateStr("yyyy-MM-dd");
		String searchData = currentData.substring(0, currentData.length()-3) + "%";	//用于查询当月是否有提额记录
		
		int hasTE = this.aidgoldDao.selectHasTE(mid, searchData);	//查询是否有当前月的提额记录
		
		JSONObject json = new JSONObject();
		if (hasTE > 0) {
			json.put("status", 2);
			json.put("msg", "当月已经提额过了呦！");
			return json;
		}
		
		Aidgold agDB = this.aidgoldDao.selectByPrimaryKey(tid);
		int jkMoney = agDB.getJkmoney();	//原借款额度
		int teMoney = (int) Math.ceil (jkMoney * ((double)terate/1000)) * 10;	//提升的金额
		int currentMoney = jkMoney + teMoney;	//提额后的金额
		
		//助力金提额
		Aidgold agData = new Aidgold();
		agData.setTid(tid);
		agData.setJkmoney(currentMoney);
		this.aidgoldDao.updateByPrimaryKeySelective(agData);
		
		//t_zjgj_member表额度
		this.aidgoldDao.updateMemberXYGD(mid, currentMoney);
		
		//插入t_zjgj_aidgold_telog 提额记录
		Map<String, Object> teMap = new HashMap<>();
		teMap.put("mid", mid);
		teMap.put("aid", tid);
		teMap.put("terate", terate);
		teMap.put("sumamount", currentMoney);
		teMap.put("addamount", teMoney);
		teMap.put("adata", currentData);
		
		this.aidgoldDao.insertTelog(teMap);
		
		json.put("status", 1);
		json.put("msg", "提额成功");
		
		return json;
	}

	/**添加黑名单*/
	@Override
	public JSON lahei(HttpServletRequest request) {

		int mid = getInt(request, "mid");	//获取mid
		String remark = getStr(request, "remark");	//
		String uname = getStr(request, "uname"); //
		
		Map<String, Object> map = new HashMap<>();
		map.put("mid", mid);
		map.put("remark", remark);
		map.put("uname", uname);
		map.put("cdate", getDateStr("yyyy-MM-dd") );
		
		this.aidgoldDao.insertBlacklist(map);	//拉黑
		
		return statusMsgJson(1, "success");
	}
	
	/**黑名单列表*/
	@Override
	public JSON blacklist(HttpServletRequest request) {
		
		int mid = getInt(request, "mid");
		
		List<Map<String, Object>> res = this.aidgoldDao.selectBlacklist(mid);
		JSON json = (JSON) JSON.toJSON(res);
		return json;
	}

	/**移除黑名单*/
	@Override
	public JSON removeBlacklist(HttpServletRequest request) {
		
		int tid = getInt(request, "tid");
		
		this.aidgoldDao.deleteBlacklist(tid);
		
		return statusMsgJson(1, "success");
	}

	/**定期 移除黑名单*/
	@Override
	public void removeBlacklistTiming() {
		List<Map<String, Object>> blacklist = this.aidgoldDao.selectBlacklistTidDate();	//查询出黑名单列表 只查tid 和 日期
		
		if(blacklist != null){
			List<Integer> tids = new ArrayList<>();
			
			long currentms = new Date().getTime();	//当前时间戳
			long blms;	//拉黑日期时间戳
			int days;	//拉黑天数
			
			for (Map<String, Object> map : blacklist) {
				blms = ((Date)map.get("cdate")).getTime();
				days = (int) ( (currentms-blms) / (1000*60*60*24));
				if (days >= 7) {	//拉黑天数超过7天的自动删除
					tids.add( (int) map.get("tid") );
				}
			}
			this.aidgoldDao.deleteBlacklistBatch(tids);
		}
	}
	
	/**捷安*/
	@Override
	public JSON jiean(HttpServletRequest request) {

		String type = getStr(request, "type");
		String mobile = getReqParam(request, "mobile");
		String uname = getReqParam(request, "name");
		String idno = getReqParam(request, "idno");

		JSON json = null;	//向页面返回的json
		String jsonStr = null;	//存取数据库 json字符串
		Map<String, String> DBMap = null;	//据库捷安对象
		
		Map<String, String> map = new HashMap<>();	//查询数据保存的map
		map.put("type", type);
		map.put("cdate", getDateStr("yyyy-MM") + "%");
		
		if ("MPTIME".equals(type)) {
			map.put("mobile", mobile);
		} else if("SFSUTJ".equals(type)) {
			map.put("uname", uname);
			map.put("idno", idno);
		} else if("MP3".equals(type)) {
			map.put("mobile", mobile);
			map.put("uname", uname);
			map.put("idno", idno);
		}
		
		DBMap = this.aidgoldDao.selectJieAnResp(map);
		
		if (DBMap != null) {	//查询到数据库对象，则直接返回
			jsonStr = DBMap.get("jsonstr");
			json = (JSON) JSON.parse(jsonStr);
			return json;
		}
		
		/*======== 数据库没有查询到结果 =========*/
		JSONObject jsonObj = (JSONObject) new VerifyClient().jieanVerify(map);	//捷安返回的json
		
		jsonStr = jsonObj.getString("jsonStr");
		
		map.put("respCode", jsonObj.getString("respCode"));
		map.put("ordId", jsonObj.getString("ordId"));
		map.put("jsonstr", jsonStr);
		map.put("prod_id", type);
		
		if ("SFSUTJ".equals(type)) {
			map.put("uni_sign", idno);
		} else {
			map.put("uni_sign", mobile);
		}
		
		this.aidgoldDao.insertJieAnResp(map);
		
		return (JSON) JSON.parse(jsonStr);
	}

	/**处理助力金 agentid为0的数据*/
	@Override
	public JSON dealAgentid(HttpServletRequest request) {
		
		try {
			int mid = getInt(request, "mid");
			int tid = getInt(request, "tid");
			
			int agentid = this.aidgoldDao.selectByPrimaryKey(tid).getAgentid();
			
			if (agentid == 0) {
				String mobile = this.aidgoldDao.selectPhoneName(mid).get("mobile");
				agentid = this.aidgoldDao.selectMidFromAssign(mobile);
				Aidgold agData = new Aidgold();
				agData.setTid(tid);
				agData.setAgentid(agentid);
				this.aidgoldDao.updateByPrimaryKeySelective(agData);
			}
		} catch (Exception e) {
		}finally{
			return statusMsgJson(0, "");
		}
		
	}

	/** 新颜爬虫 提额 */
	@Override
	public JSON xyUpdateBalance(HttpServletRequest req) {
		
		int mid = getInt(req, "mid");
		int money = getInt(req, "money");
		String txn_type = getStr(req, "txn_type");
		String tradeNo = getStr(req, "tradeNo");
		
		try {
			int count = this.commonDao.selectCountFromTelogXyByMidType(mid, txn_type);	//查询是否已经提额过
			if (count <= 1) { 	//没提额过，或者系统自动提额过一次就可提额
				int add = money;	//要增加的额度
				int xygd = this.commonDao.selectMemberXygdByMid(mid);	//根据mid获取用户额度
				xygd += add;
				Map<String, Object> map = new HashMap<>();
				map.put("mid", mid);
				map.put("te_type", txn_type);
				map.put("te_order", tradeNo);
				map.put("totle_limit", xygd);
				map.put("add_limit", add);
				map.put("remark", "管理员提额");
				this.commonDao.insertTelogXy(map);	//插入提额日志
				this.aidgoldDao.updateMemberXYGD(mid, xygd);	//更新额度
				
				/** 更新 t_xy_spider_person 表状态 */
				map.put("txn_type", txn_type);
				map.put("columnName", "is_used");
				map.put("value", 1);
				this.commonDao.updateSpiderPersonByColumnName(map);
				
				log("提额成功："+tradeNo);
				return statusMsgJson(1, "提额成功");
			} else {
				log("不可重复提额："+tradeNo);
				return statusMsgJson(0, "不可重复提额");
			}
		} catch (Exception e) {
			log("提额异常：" + tradeNo + " " + e.getMessage());
		}
		return statusMsgJson(0, "提额失败");
	}
	
}
