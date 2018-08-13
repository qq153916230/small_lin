package com.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.AidgoldDao;
import com.app.dao.CommonDao;
import com.app.entity.daystatic;
import com.app.service.CommonService;
import com.app.util.CallUrlByGet;
import com.app.util.CallUrlByPost;
import com.app.util.DataGrid;
import com.app.util.WriteExcel;
import com.app.util.zjgj.ZjUrl;
import com.app.util.zjgj.ZjUtils;

@Service
public class CommonServiceImpl extends ZjUtils implements CommonService {
	
	Logger log4j = Logger.getLogger("commonServiceImpl-aidgold");
	
	@Autowired
	CommonDao commonDao;
	@Autowired 
	AidgoldDao aidgoldDao;
	

	/** 登入/身份验证 */
	private boolean authentication(HttpServletRequest req) {
		String username = getStr(req, "mobile");
		String password = getStr(req, "sign");
		String mid = getStr(req, "mid");
		String type = getStr(req, "type");
		
		Map<String, Object> map = null;
		if ("".equals(type)) {
			map = this.commonDao.selectMemberByUnameAndPass(username, password);
		} else {
			map = this.commonDao.selectSysUserByUnameAndPass(username, password);
		}
		if (map != null && map.size() > 0 && mid.equals(map.get("mid") + "") ) {
			return true;
		} else {
			return false;
		}
	}

	
	/** 登入系统  */
	@Override
	public JSON login() {
		String username = getStr("username");
		String password = getStr("password");
		
		String type = getStr("type");	//app登入 还是 sys后台登入
		
		Map<String, Object> map = null;
		
		if ("app".equals(type)) { // APP 登入
			map = this.commonDao.selectMemberByUnameAndPass(username, password);
		} else if ("sys".equals(type)) { // 后台管理系统 登入
			map = this.commonDao.selectSysUserByUnameAndPass(username, password);
		}
		if (map == null || map.size() == 0) {
			return statusMsgJson(0, "登入失败");
		} else {
			//设置 session
			HttpSession session = getRequest().getSession();
			session.setAttribute("username", map.get("username"));	
			session.setAttribute("password", map.get("password"));
			return statusMsgJson(1, "登入成功");
		}
		
	}
	
	/** 获取信用卡卡列表 */
	@Override
	public JSON selectCardList(HttpServletRequest req) {
		List<Map<String, Object>> list = this.commonDao.selectCardList(getInt(req, "mid"));
		for (Map<String, Object> map : list) {
			//补充路径
			map.put("pic_url", getContextPath(req) + "/resource/bank/" + map.get("pic_url") + ".png");
		}
		return statusMsgJsonObj(1, JSON.toJSON(list));  
	}

	/** 记录登入设备信息 */
	@Override
	public JSON insertLoginMobile(HttpServletRequest req) {
		
		Map<String, Object> map = getHashMap();
		
		map.put("mid", getInt(req, "mid"));
		map.put("mobile_sys", getStr(req, "mobile_sys"));	//系统
		map.put("mobile_type", getStr(req, "mobile_type"));	//型号
		map.put("mobile_no", getStr(req, "mobile_no"));		//序列号
		map.put("login_area", getStr(req, "login_area"));	//登入地区
		map.put("login_date", new Date());					//登入时间
		
		this.commonDao.insertLoginMobile(map);
		
		return statusMsgJson(1, "success");
	}

	/** 获取app登入信息 */
	@Override
	public JSON selectLoginMobileByMid(HttpServletRequest req) {
		return statusMsgJsonObj(1, JSON.toJSON(this.commonDao.selectLoginMobileByMid(getInt(req, "mid"))));
	}

	/** 查询版本号 */
	@Override
	public JSON selectAppVersionBySystem(HttpServletRequest req) {
		return statusMsgJsonObj(1, JSON.toJSON(this.commonDao.selectAppVersionBySystem(getStr(req, "system"),getStr(req, "version"))));
	}

	/** 查询通讯爬虫结果 */
	@Override
	public JSON selectSpiderMobile(HttpServletRequest req) {
		return statusMsgJsonObj(1, JSON.toJSON(this.commonDao.selectSpiderMobile(getStr(req, "mobile"))));
	}

	/**修改密码*/
	@Override
	public JSON updatePass(HttpServletRequest req) {
		String mobile = getStr(req, "mobile");
		String code = getStr(req, "code");	//验证码
		HttpSession session = req.getSession();
		
		if ("".equals(code)) {
			int match = this.commonDao.matchMobile(mobile);	//匹配手机
			if (match == 1) {	//匹配成功
				String res = CallUrlByGet.callUrlByGet("http://mobile.rczjgj.com/DataHandler.ashx?action=forgetsmscode&mobile="+mobile,"UTF-8");
				//System.out.println(res); {"status":1, "msg":"17237"}
				com.alibaba.fastjson.JSONObject json = JSON.parseObject(res);
				if ((int)json.get("status") != 1) {
					return statusMsgJson(5, "验证码发送失败");
				}
				code = (String) json.get("msg");
				session.setAttribute("code", code);
				return statusMsgJson(1, "验证码已发送(30分钟内有效)");
			} else if(match == 0) {
				return statusMsgJson(3, "该用户未注册");
			} else { //match > 1
				return statusMsgJson(4, "请联系后台管理员");
			}
		}
		
		if (code.equals(session.getAttribute("code"))) {
			String pass = getStr(req, "pass").toUpperCase();
			this.commonDao.updateMemberPass(mobile, pass);
			session.removeAttribute("code");
			return statusMsgJson(1, "修改成功");
		}
		return statusMsgJson(2, "修改失败");
		
	}

	/** 根据手机查询通讯爬虫订单号 */
	@Override
	public JSON selectTradeNoByMobile(HttpServletRequest req) {
		String mobile = getStr(req, "mobile");
		String tradeNo = this.commonDao.selectTradeNoByMobile(mobile);
		return statusMsgJson(1, tradeNo);
	}

	/**查询通讯爬虫结果 通过mid*/
	@Override
	public JSON selectSpiderMobileByMid(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		String mobile = this.commonDao.selectMobileByMidFromMember(mid);
		Map<String, Object> map = this.commonDao.selectSpiderMobile(mobile);
		return statusMsgJsonObj(1, JSON.toJSON(map));
	}

	
	
	/** 查询信用信息 通过mid*/
	@Override
	public JSON selectCreditMsgByMid() {
		return statusMsgJsonObj(1, JSON.toJSON(this.commonDao.selectCreditMsgByMid(getInt("mid"))));
	}

	@Override
	public JSON update_credit_msg() {
		
		String credit_type = getStr("credit_type");
		if ("".equals(credit_type)) {
			return statusMsgJson(2, "信用类型有误");
		}
		
		int mid = getInt("mid");
		int count = getInt("count");
		
		Map<String, Object> map = this.commonDao.selectCreditMsgByMid(mid);
		
		if (map == null || map.size() == 0) {
			map = getHashMap();
			Integer i = this.commonDao.insert_credit_msg(mid);	//插入新记录
			System.out.println("=========:"+i);
		} else {
			map.clear();
		}
		if ("connect_6m_all".equals(credit_type)) {
			System.out.println(credit_type);
			System.out.println(count);
		}
		map.put("credit_type", credit_type);
		map.put("mid", mid);
		map.put("count", count);
		
		this.commonDao.update_credit_msg(map);
		
		return statusMsgJson(1, "更新成功");
	}

	/** 更新 t_xy_spider_person */
	@Override
	public JSON updateSpiderPersonByColumnName() {
		
		Map<String, Object> map = getHashMap();
		
		map.put("mid", getInt("mid"));
		map.put("txn_type", getStr("txn_type"));
		map.put("columnName", getStr("columnName"));
		map.put("value", getStr("value"));
		
		try {
			this.commonDao.updateSpiderPersonByColumnName(map);
		} catch (Exception e) {
			return statusMsgJson(2, "请传入正确参数或联系管理员(/app/updateSpiderPersonByColumnName)");
		}
		
		return statusMsgJson(1, "更新成功");
	}

	/** 获取 t_xy_spider_person */
	@Override
	public JSON selectSpiderPersonMsgByMid() {
		
		int mid = getInt("mid");
		List<Map<String, Object>> list_person = this.commonDao.select_spider_person_msg_by_mid(mid);
		List<Map<String, Object>> list_email = this.commonDao.selectSpiderEmail(mid);
		
		//获取是否有已经验证过的邮箱
		int status = 0;
		for (Map<String, Object> email : list_email) {
			if ( ((int)email.get("status")) == 1) {
				status = 1;
				break;
			}
		}
		
		//移除原有的邮箱
		for (int i = 0; i < list_person.size(); i++) {
			if ( "email".equals((String)list_person.get(i).get("txn_type")) ) {
				list_person.remove(list_person.remove(i));
			}
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//{"is_done":0,
		//"is_used":0,
		//"trade_no":"201805291828130986526769",
		//"mid":1548,
		//"cdate":1527589638000,
		//"txn_type":"taobao",
		//"is_valid":0}
		dataMap.put("txn_type", "email");
//		email.put("trade_no", "");
//		email.put("mid", "");
//		email.put("cdate", "");
//		email.put("is_valid", "");
		dataMap.put("is_done", status);
		dataMap.put("is_valid", status);
		
		list_person.add(dataMap);
		String mobile = this.commonDao.selectMobileByMidFromMember(mid);
		if (notEmpty(mobile)) {
			dataMap = this.commonDao.selectSpiderMobile(mobile);
			if (dataMap != null) {
				status = (int) dataMap.get("is_done");
				
				dataMap.clear();
				dataMap.put("txn_type", "mobile");
				dataMap.put("is_done", status);
				dataMap.put("is_valid", status);
				
				list_person.add(dataMap);
			}
		}
		
		return statusMsgJsonObj(1, JSON.toJSON(list_person));
	}

	@Override
	public JSON getMemberServerList() {
		try {
			int mid = getInt("mid");
			int version = getInt("new_version");	// 1为新版本
			
			mid = dealMid(mid);	//判断用户类型
			if (version == 1) {
				mid -= 4;
			}
			
			return statusMsgJsonObj(1, this.commonDao.selectServerListByMid(mid));
		} catch (Exception e) {
			return statusMsgJson(2, "请求有误");
		}
	}
	
	/**
	 * 判断用户类型
	 * @return 0:普通用户 -1:苹果测试账号 -2:vip用户 -3:代理商
	 */
	private int dealMid(int mid){
		Map<String, Object> member = this.commonDao.selectMemberByMid(mid);
		
		int rserRole = 0;
		if (member != null) {
			rserRole = (int) member.get("userrole");
		}
		
		if (rserRole == 1) {	//	vip用户
			mid = -2;
		} else if (rserRole == 2) {	//代理商
			mid = -3;
		} else if (rserRole == 0) {			//普通用户
			mid = 0;
			String mobile = (String) member.get("mobile");
			if (mobile != null) {
				int i = this.commonDao.selectCountPos(mobile);
				//int i = this.commonDao.selectCountPosAssign(mobile);
				if (i > 0) {
					mid = -2;
				}
			}
		} else {
			mid = -1;	//苹果测试账号
		}
		
		return mid;
	}
	

	
	/** 查看该卡是否是新卡 */
	@Override
	public JSON newCardCheck() {
		String cardNo = getStr("cardno");
		if ("".equals(cardNo)) {
			return statusMsgJson(2, "请输入正确的卡号");
		}
		int count = commonDao.selectCountByCardNo(cardNo);
		if (count > 0) {
			return statusMsgJson(2, "该卡不是新卡");
		}
		return statusMsgJson(1, "该卡为新卡");
	}

	/** 查询必填个人信息 */
	@Override
	public JSON selectAidgoldMustFill(HttpServletRequest req) {
		String mobile = getStr(req, "mobile");
		int mid = getInt(req, "mid");
		
		Map<String, Object> mobile_res = this.commonDao.selectSpiderMobile(mobile);
		if (mobile_res == null || ( (int)mobile_res.get("is_done") != 1) ) {
			return statusMsgJson(0, "通讯录信息未采集");
		}
		
		List<Map<String, Object>> list = this.commonDao.selectSpiderEmail(mid);
		for (Map<String, Object> email : list) {
			if (email != null &&  ( (int)email.get("status") == 1 ) ) {
				return statusMsgJson(1, "已通过");
			}
		}
		
		return statusMsgJson(0, "邮箱未采集");
	}

	/** 查询邮箱 */
	@Override
	public JSON selectSpiderEmail(HttpServletRequest req) {
		return statusMsgJsonObj(1, JSON.toJSON(this.commonDao.selectSpiderEmail(getInt(req, "mid"))));
	}

	/** 查询个人信息 */
	@Override
	public JSON selectSpiderPerson(HttpServletRequest req) {
		return statusMsgJsonObj(1, JSON.toJSON(this.commonDao.selectSpiderPerson(getInt(req, "mid"), getStr(req, "type"))));
	}

	/** 插入邮箱账号 */
	@Override
	public JSON insertSpiderEmailAccount(HttpServletRequest req) {
		
		String mid = getStr(req, "mid");
		String trade_no = getStr(req, "trade_no");
		String account = getStr(req, "account");
		String type = getStr(req, "type");

		Map<String, Object> map = new HashMap<>();
		map.put("trade_no", trade_no);
		map.put("account", account);
		
		int i = 0;
		if ("email".equals(type)) {
			i = this.commonDao.insertSpiderEmailAccount(map);
		}
		
		if (i >= 1) {
			return statusMsgJson(1, "操作成功");
		} else {
			return statusMsgJson(0, "操作失败");
		}
		
	}
	
	/** 微信支付 */
	@Override
	@Transactional
	public JSON wxpay(HttpServletRequest req) {
		JSONObject json = null;
		int mid = getInt(req, "mid");	//用户mid
		
		try {
			int rid = getInt(req, "rid");	//助力金id
			int ptype = getInt(req, "ptype");	//付款类型
			int overday = getInt(req, "overday");	//逾期天数
			String overmoney = getStr(req, "overmoney");	//逾期金额
			overmoney = overmoney.equals("")? "0":overmoney;
			String period = getStr(req, "period");
			
			
			String body = getStr(req, "body");
			
			String total_fee = getStr(req, "total_fee");
			String spbill_create_ip = getStr(req, "spbill_create_ip");
			
			
			String out_trade_no = "AP" + ptype + System.currentTimeMillis();
			
			Map<String, Object> map = this.commonDao.selectMemberBankByMid(mid);	//获取memberBank
			
			String url = ZjUrl.BASE_SERVER_URL_AUTO + "/wxpay/pay";
			String param = "?body="+URLEncoder.encode(body,"UTF-8")+"&out_trade_no="+out_trade_no+
					"&total_fee="+total_fee+"&spbill_create_ip="+spbill_create_ip+"&mid="+mid;
			String resp = CallUrlByGet.callUrlByGet(url + param, "UTF-8");
			
			json = JSONObject.parseObject(resp);
			if (!"SUCCESS".equalsIgnoreCase((String) json.get("result_code"))) {	//请求失败直接返回
				return json;
			}
			map.put("tradetype", json.get("trade_type"));
			map.put("orderid", out_trade_no);
			map.put("body", body);
			map.put("openid", "");	//json.get("openid") 此参数必传，此参数为微信用户在商户对应appid下的唯一标识
			map.put("totalfee", total_fee);
			map.put("time_start", "");
			map.put("time_expire", "");
			map.put("goodstag", "");
			map.put("orderdate", new Date());
			map.put("ispay", 0);
			map.put("appid", json.get("appid"));
			map.put("mchid", json.get("mch_id"));
			map.put("prepay_id", json.get("prepay_id"));
			map.put("rid", rid);
			map.put("ptype", ptype);
			map.put("overday", overday);
			map.put("overmoney", overmoney);
			map.put("period", period);
			
			this.commonDao.insertWXOrder(map);	//添加预订单 wx_order
			
			//金额转成元为单位
			BigDecimal amout = null;
			if (!"".equals(total_fee)) { 
				amout = new BigDecimal(total_fee).divide(new BigDecimal(100));
			}
			map.put("totalfee", amout);
			this.commonDao.insertEpayPayment(map);	//添加 epay_payment表数据
		} catch (Exception e) {
			e.printStackTrace();
			log(mid + " 微信请求预订单异常：" + e);
		}
		return json;
	}
	
	/** 微信支付异步通知 */
	@Override
	public String wxpay_notify(HttpServletRequest req) {
		
		log("微信支付异步通知 :");
		String body = readHttpBody(); //"<xml><appid><![CDATA[wxe8316119b19a6731]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[2]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1508200841]]></mch_id><nonce_str><![CDATA[eaba2442d17d45a590c24a5991fa41d8]]></nonce_str><openid><![CDATA[oOxT70djuUGwqYIROFOEvvcJ4DSI]]></openid><out_trade_no><![CDATA[AP31529740577248]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[9BE92E75EA63ECAEB6E67690998820E96A79D5A88305A677B12A796D4D8EC3AE]]></sign><time_end><![CDATA[20180623155627]]></time_end><total_fee>2</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[4200000150201806234746588822]]></transaction_id></xml>";	//
		log(body);
		
		if (body != null && body.length() > 0) {
			/*<xml><appid><![CDATA[wxe8316119b19a6731]]></appid>
			<bank_type><![CDATA[CFT]]></bank_type>
			<cash_fee><![CDATA[1]]></cash_fee>
			<fee_type><![CDATA[CNY]]></fee_type>
			<is_subscribe><![CDATA[N]]></is_subscribe>
			<mch_id><![CDATA[1508200841]]></mch_id>
			<nonce_str><![CDATA[de06183470d34c8cbaca330ff6af19a0]]></nonce_str>
			<openid><![CDATA[oOxT70eWM3rkyV0nRjUsvpEaDxJk]]></openid>
			<out_trade_no><![CDATA[LC1529724909467]]></out_trade_no>
			<result_code><![CDATA[SUCCESS]]></result_code>
			<return_code><![CDATA[SUCCESS]]></return_code>
			<sign><![CDATA[215F0BDD650176A06EB77BE10BFEF540EB6488BB4E9E0CFE4E34944D16B1B7C8]]></sign>
			<time_end><![CDATA[20180623113516]]></time_end>
			<total_fee>1</total_fee>
			<trade_type><![CDATA[APP]]></trade_type>
			<transaction_id><![CDATA[4200000142201806239993646985]]></transaction_id>
			</xml>*/
			
			try {
				//将xml转为json jar包有问题，所以用网络请求转化
				//String respStr = CallUrlByGet.callUrlByGet(ZjUrl.BASE_SERVER_URL_AUTO + "/wxpay/xml2json?xml="+body, "UTF-8");
				String respStr = CallUrlByPost.sendPost(ZjUrl.BASE_SERVER_URL_AUTO + "/wxpay/xml2json", "xml="+body);
				JSONObject resp = JSONObject.parseObject(respStr); 
				String out_trade_no = resp.getString("out_trade_no");	//订单
				
				int status = this.commonDao.selectWXOrderStatus(out_trade_no);	//查看状态
				if (status == 0) {
					//根据订单修改wx_order状态 为1
					this.commonDao.updateWXOrderStatus(out_trade_no);
					//根据订单修改epay_payment状态 
					//this.commonDao.updateEpayPaymentStatus(out_trade_no);
				}
				
				//请求总监的接口 修改epay_payment状态，助力金状态...
				CallUrlByGet.callUrlByGet("http://mobile.rczjgj.com/wxpayok.aspx?orderid="+out_trade_no, "UTF-8");
			} catch (Exception e) {
				log("微信支付异步通知异常：" + e.getMessage());
			}
		} else {
			log("微信支付异步通知无返回信息");
		}
		
		
		return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}
	

	
	/** ************** `tx` **************** */
	
	/** ************** `t_zjgj_tx_reward` **************** */
	/** 根据ordercode查询 */
	@Override
	public DataGrid txRewardDetail() {
		
		int page = getPages("page");
		int rows = getInt("rows");
		
		String possn = getStr("possn");
		String ordercode = getStr("ordercode");
		String mobile = getStr("mobile");
		int modeBegin = getInt("mode");
		int modeEnd = modeBegin + 99;
		
		String startDate = getStr("startDate");
		String endDate = getStr("endDate");
		if (!"".equals(endDate)) {
			endDate += " 23:59:59.999";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("possn", possn);
		map.put("ordercode", ordercode);
		map.put("mobile", mobile);
		map.put("modeBegin", modeBegin);
		map.put("modeEnd", modeEnd);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("page", page);
		map.put("rows", rows);
		
		DataGrid dg = new DataGrid();
		List<Map<String, Object>> list = 
				this.commonDao.selectTXRewardByOrdercode(map);
		dg.setRows(list);
		dg.setTotal((long) this.commonDao.selectCountTXReward(map));
		return dg;
	}
	

	/** 导出 `t_zjgj_tx_reward` */
	@Override
	public JSON txRewardExport() {

		int page = getPages("page");
		int rows = getInt("rows");
		
		String possn = getStr("possn");
		String ordercode = getStr("ordercode");
		String mobile = getStr("mobile");
		int modeBegin = getInt("mode");
		int modeEnd = modeBegin + 99;
		
		String startDate = getStr("startDate");
		String endDate = getStr("endDate");
		if (!"".equals(endDate)) {
			endDate += " 23:59:59";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("possn", possn);
		map.put("ordercode", ordercode);
		map.put("mobile", mobile);
		map.put("modeBegin", modeBegin);
		map.put("modeEnd", modeEnd);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("page", page);
		map.put("rows", rows);
		
		List<Map<String, Object>> data = this.commonDao.selectTXReward(map);
		
		InputStream is = null;
		OutputStream os = null;
		String fileName = System.currentTimeMillis()+"";
		String filePath = "E:\\tomcat\\tomcatZJ2Manager\\webapps\\zjgj2tj\\download\\txreward\\"+fileName+".xls";
		String serverPath = getContextPath(getRequest()) + "download/txreward/"+fileName+".xls";
		
		try {
			is = getInputStream(data);
			
			os = new FileOutputStream(new File(filePath));
			IOUtils.copy(is, os);
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMsgJson(1, serverPath);
	}
	
	public InputStream getInputStream(List<Map<String, Object>> list) throws Exception {
		String[] title = new String[]{"tid","提现订单号","金额","机具号","用户编号",
				"姓名","手机","用户类型","提现类型", "费率", "创建时间", "更新时间"};
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); /*HH:mm:ss*/
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Object[] obj = new Object[title.length];
			
			obj[0] = map.get("tid");
			obj[1] = map.get("ordercode");
			obj[2] = map.get("reward");
			obj[3] = map.get("possn");
			obj[4] = map.get("mid");
			obj[5] = map.get("nick");
			obj[6] = map.get("mobile");
			obj[7] = map.get("role");
			obj[8] = map.get("posnum");
			obj[9] = map.get("rate");
			obj[10] = map.get("rdate");
			obj[11] = map.get("gdate");
			
			dataList.add(obj);
		}
		
		WriteExcel excel = new WriteExcel(title, dataList);
		InputStream inputStream;
		inputStream = excel.export();
		
		return inputStream;
	}

	
	/** 财富通提现申请列表 */
	@Override
	public DataGrid txApply_cft() {
		
		int page = getPages("page");
		int rows = getInt("rows");
		String dateFrom = getStr("regTimeFrom");
		String dateTo = getStr("regTimeTo");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("page", page);
		param.put("rows", rows);
		param.put("status", 0);
		
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		
		DataGrid dg = new DataGrid();
		List<Map<String, Object>> list = this.commonDao.selectTXApply_cft(param);
		dg.setRows(list);
		dg.setTotal((long) list.size());
		return dg;
	}
	
	/** 财富通提现申请处理 */
	@Override
	@Transactional
	public JSON txApply_cft_deal() {
		String[] tids = getStr("tids").split(",");
		int status = getInt("status");
		if (status > 0) {
			
			Map<String, Object> param = new HashMap<>();
			param.put("tids", tids);
			param.put("status", status);
			this.commonDao.updateDrpTixianApplyStatus(param);	//修改`t_drp_tixian_apply`表状态
			
			List<Map<String, Object>> data = 
					this.commonDao.selectDrpTixianApplyStatusByTids(tids);	//查询t_drp_tixian_apply记录
			for (Map<String, Object> map : data) {
				map.put("dzmoney", ((BigDecimal)map.get("money")).subtract(new BigDecimal(2)));
				map.put("source", "0");	//提现前原有金额
				map.put("period", "0");	//补贴期数
				map.put("cdate", new Date());
				map.put("del_flag", "0");
				map.put("txtype", "300");				
			}
			
			this.commonDao.insertZjgjTX(data);	//批量保存 t_zjgj_tx
			
			return statusMsgJson(1, "处理成功");
		} else {
			return statusMsgJson(2, "状态不能为 “0” 或 ''");			
		}
	}
	
	/** ******* pos ********* */

	/** 扣费贴息激活 机具列表 */
	@Override
	public DataGrid subsidyPosList() {
		
		int page = getPages("page");
		int rows = getInt("rows");
		String dateFrom = getStr("regTimeFrom");
		String dateTo = getStr("regTimeTo");
		String possn = getStr("possn");
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("page", page);
		param.put("rows", rows);
		
		param.put("possn", possn);
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		
		DataGrid dg = new DataGrid();
		List<Map<String, Object>> list = this.commonDao.selectSubsidyPosList(param);	//`t_zjgj_subsidy_pos` 列表
		dg.setRows(list);
		dg.setTotal((long) list.size());
		return dg;
	}

	/** 保存 代理商报名参与活动 */
	@Override
	public JSON posActivitySave(HttpServletRequest req) {
		
		int mid = getInt(req, "mid");
		String mobile = getStr(req, "mobile");
		String marketid = "" + System.currentTimeMillis();	//活动编号
		int market_group = 0;	//活动分组，同参数表group
		String market_param = getStr(req, "market_param");	//参与活动的参数
		int result = 0;
		
		String money = getStr(req, "reward_money");
		BigDecimal reward_money = new BigDecimal("".equals(money) ? "0":money);	//奖励金额
		
		int reward_num = 0;	//奖励奖品个数
		String remark = getStr(req, "remark");
		int dabiao_num = 0;
		
		Calendar cal = Calendar.getInstance();
		Date begindate = cal.getTime();		//活动开始时间
		
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 90);
		Date enddate = cal.getTime();		//活动截止时间
		
		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);
		param.put("mobile", mobile);
		param.put("marketid", marketid);
		param.put("market_group", market_group);
		param.put("market_param", market_param);
		param.put("result", result);
		param.put("reward_money", reward_money);
		param.put("reward_num", reward_num);
		param.put("remark", remark);
		param.put("dabiao_num", dabiao_num);
		param.put("begindate", begindate);
		param.put("enddate", enddate);
		
		int ind = this.commonDao.insertPlatMarket(param);
		
		if (ind > 0) {
			return statusMsgJson(1, "报名成功");				
		} else {
			return statusMsgJson(2, "报名失败");
		}
		
	}

	@Override
	public List<Map<String, Object>> memberBankList(HttpServletRequest req) {
		Map<String, Object> param = new HashMap<>();
		param.put("page", getInt(req, "offset"));
		param.put("rows", getInt(req, "limit"));
		return this.commonDao.selectMemberBank(param);
	}

	@Override
	public DataGrid memberBankListPro(HttpServletRequest req) {
		
		String userName = getStr(req, "userName");
		String mobile = getStr(req, "mobile");
		int page = getPages("page");
		int rows = getInt(req, "rows");
		
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		params.put("mobile", mobile);
		params.put("page", page);
		params.put("rows", rows);
		
		DataGrid dg = new DataGrid();
		dg.setRows(this.commonDao.selectMemberBank(params));
		dg.setTotal((long) this.commonDao.selectCountMemberBank(params));
		return dg;
	}
	

	@Override
	public JSON memberBankSave(HttpServletRequest req) {
		
		int bankSaveType = getInt(req, "bankSaveType");	// 0保存， 1更新
		String mobile = getStr(req, "mobile");
		String bankAccount = getStr(req, "bankAccount");
		String bankName = getStr(req, "bankName");
		String userName = getStr(req, "userName");
		
		Integer mid = this.commonDao.selectMidByMobileFromMember(mobile);
		
		if (mid == null || mid <= 0) {
			return statusMsgJson(0, "用户不存在");
		}
		
		int memberBankCount = this.commonDao.selectCountMemberBankByMid(mid);
		
		Map<String, Object> params = new HashMap<>();
		params.put("mid", mid);
		params.put("mobile", mobile);
		params.put("bankAccount", bankAccount);
		params.put("bankName", bankName);
		params.put("userName", userName);
		params.put("isauth", 0);
		
		if (bankSaveType == 1) {
			if (memberBankCount == 1) {
				this.commonDao.updateMemberBank(params);
			} else {
				return statusMsgJson(0, "查询到多条记录");
			}
		} else if(bankSaveType == 0) {
			if (memberBankCount == 0) {
				//params.put("userName", this.commonDao.selectMemberByMid(mid).get("nick"));
				this.commonDao.insertMemberBank(params);
			} else {
				return statusMsgJson(0, "已存在记录，不可保存");
			}
		} else {
			return statusMsgJson(0, "未知保存类型（bankSaveType）");
		}
		
		return statusMsgJson(1, "已保存");
	}

	
	/** 查看是否满足贷款条件 */
	@Override
	public JSON aidgoldPrecheck(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		String type = getStr(req, "type");
		String sys = getStr(req, "sys");
		
		if (!"zlj".equals(type) && !"tyd".equals(type)) {
			log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 类型有误  --%s", mid, type, sys));
			return statusMsgJson(0, "类型有误");
		}
		
		if (mid == 620 || mid == 1548) {
			log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 测试账号  --%s", mid, type, sys));
			return statusMsgJson(1, "测试账号");
		}
		
		int blackList = this.commonDao.selectCountBlackListByMid(mid);	//查询是否在助力金黑名单
		if (blackList > 0) {
			return statusMsgJson(0, "贷款受限，请联系客服！");
		}
		
		int tradeMoneyLimit = 0;	//体验贷需满足交易1W
		int countAidgold = this.commonDao.selectCountAidgoldByMid(mid);	//查询是否成功借过款
		
		if ("zlj".equals(type)) {
			if (countAidgold == 0) {
				log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 首次贷款仅可申请体验贷  --%s", mid, type, sys));
				return statusMsgJson(0, "首次使用仅可申请体验贷");
			}
			tradeMoneyLimit = 50000;	//助力金需满足交易5W
		} else {
			if (countAidgold != 0) {
				log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 体验贷仅可申请一次  --%s", mid, type, sys));
				return statusMsgJson(0, "您已享受过体验贷服务，如需再次使用，请选择我要借款");
			}
			tradeMoneyLimit = 10000; //体验贷需满足交易1W
		}
		
		String mobile = this.commonDao.selectMobileByMidFromMember(mid);
		
		List<String> cards = this.commonDao.selectAutonymCardByMid(mid);	//查询已实名的卡
		int cardNum = cards.size();
		if (cardNum == 0) {
			log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 本人卡：%s张   --%s",
					mid, type, cardNum, sys));
			return statusMsgJson(0, "您未认证信用卡，点击下方认证本人信用卡");
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 30);	//一个月前
		Date staTime = calendar.getTime();
		
		Map<String, Object> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("staTime", staTime);
		params.put("cards", cards);
		
		BigDecimal maxTrade = this.commonDao.selectMaxPaymoney(params);	//获取一个月内最大交易额
		if ("tyd".equals(type)) {
			if (maxTrade == null || maxTrade.compareTo(new BigDecimal(5000)) == -1) {
				log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 本人卡：%s张 最大交易：%s  --%s",
						mid, type, cardNum, maxTrade, sys));
				return statusMsgJson(0, "体验贷需要最近一个月有一笔交易超过5000元");
			}
		}
		
		BigDecimal sumTrade = this.commonDao.selectSumPaymoney(params);	//统计一个月内交易额
		if (sumTrade == null || sumTrade.compareTo(new BigDecimal(tradeMoneyLimit)) == -1) {
			log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 本人卡：%s张 总交易：%s 最大交易：%s  --%s",
					mid, type, cardNum, sumTrade, maxTrade, sys));
			return statusMsgJson(0, String.format("借款需满足最近一个月总交易金额超过%s元", tradeMoneyLimit));
		}
		
		log4j.info(String.format("【助力金预检】用户mid：%s 申请类型：%s 本人卡：%s张 总交易：%s 最大交易：%s  --%s",
				mid, type, cardNum, sumTrade, maxTrade, sys));
		return statusMsgJson(1, "满足贷款条件");
	}

	/** 查看服务器是否存在照片 */
	@Override
	public JSON aidgoldCheckPic(HttpServletRequest req) {
		String mobile = getStr(req, "mobile");
		String path = getContextRealPath(req) + "/uploadFile/idPicture/" + mobile;
		File file;
		for (int i = 1; i <= 3; i++) {
			String filePath = path + "/" + i + ".jpg" ;
			file = new File(filePath);
			if (!file.exists()) {
				return statusMsgJson(0, mobile+" "+ i + ".jpg不存在");
			}
		}
		return statusMsgJson(1, "身份证照片已上传");
	}
	/** 查询公告，新闻文章 */
	@Override
	public JSON newsList(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		int type = getInt(req, "type");
		
		if (mid == 0 || type == 0) {
			return statusMsgJson(0,"参数不正确");
		}
		
		mid = dealMid(mid);	//判断用户类型
		
		int permission = 1;	//权限
		if (mid == -2) {
			permission = 2;
		} else if (mid == -3) {
			permission = 3;
		}
		
		int status = 0;
		List<Map<String, Object>> news = this.commonDao.selectNewsList(type, permission);
		
		if (type == 2) {
			List<Map<String, Object>> alertPic = this.commonDao.selectNewsList(1, permission);
			JSONObject json = new JSONObject();
			
			json.put("notice", news);
			json.put("pic", alertPic);
			return statusMsgJsonObj(1, json);
		}
		
		if (news != null && news.size() > 0) {
			status = 1;
		}
		
		return statusMsgJsonObj(status, news);
	}
	
	/** `t_ship_member` 列表 */
	@Override
	public DataGrid shipMemberList() {

		int page = getPages("page");
		int rows = getInt("rows");
		
		String uname = getStr("uname");
		String mobile = getStr("mobile");
		
		Map<String, Object> map = new HashMap<>();
		map.put("uname", uname);
		map.put("mobile", mobile);
		map.put("page", page);
		map.put("rows", rows);
		
		DataGrid dg = new DataGrid();
		List<Map<String, Object>> list = 
				this.commonDao.selectShipMemberList(map);
		dg.setRows(list);
		dg.setTotal((long) this.commonDao.selectCountShipMember(map));
		return dg;
	}
	
	/** 设置股东 */
	@Transactional
	public JSON setShareholder(HttpServletRequest req){
		int mid = getInt(req, "mid");
		
		/** 查询是否是新用户 userrole = 0 */
		Map<String, Object> member = this.commonDao.selectShipMemberByMid(mid);
		if (member == null) 
			return statusMsgJson(0, "用户不存在");
		
		int userrole = (int)member.get("userrole");
		if (userrole == 5 || userrole == 9) 
			return statusMsgJson(0, "该用户已经是股东了");
		
		/** 修改状态 */
		int userrole2 = 5;
		int success = this.commonDao.updateUserroleByMidFromShipMember(mid,userrole2);
		log(String.format("【设置股东】mid： %s 更新userrole： %s -> %s", mid, userrole, userrole2));
		
		/** 保存option */
		if (success > 0) {
			Map<String, Object> option = new HashMap<>();
			option.put("mid", mid);
			option.put("mobile", member.get("mobile"));
			option.put("nick", member.get("nick"));
			option.put("price", 50000);
			option.put("stype", 6);
			option.put("sdate", new Date());
			option.put("remark", "股东款50000");
			option.put("isreward", 9);
			
			this.commonDao.insertShipOption(option);
			log(String.format("【设置股东】mid： %s 保存option完成：%s",mid ,option.toString()));
		}
		
		return statusMsgJson(1, "设置成功");
	}

	/** `t_ship_option` 列表 */
	@Override
	public DataGrid shipOptionList() {

		int page = getPages("page");
		int rows = getInt("rows");
		
		String uname = getStr("uname");
		String mobile = getStr("mobile");
		int isreward = getInt("isreward");
		
		Map<String, Object> map = new HashMap<>();
		map.put("uname", uname);
		map.put("mobile", mobile);
		map.put("isreward", isreward);
		map.put("page", page);
		map.put("rows", rows);
		
		DataGrid dg = new DataGrid();
		List<Map<String, Object>> list = 
				this.commonDao.selectShipOptionList(map);
		dg.setRows(list);
		dg.setTotal((long) this.commonDao.selectCountShipOption(map));
		return dg;
	}
	
	/** t_ship_option 财务确认到款 */
	@Override
	public JSON shipConfirm() {
		int tid = getInt("tid");
		
		/** 查询用户 isreward = 0 */
		Map<String, Object> option = this.commonDao.selectShipOptionByTid(tid);
		if (option == null) 
			return statusMsgJson(0, "没有该记录");
		
		int isreward = (int)option.get("isreward");
		if (isreward != 9) 
			return statusMsgJson(0, "该记录已确认过了");
		
		/** 修改状态 */
		int isreward2 = 0;
		int success = this.commonDao.updateIsrewardByTidFromShipOption(tid, isreward2);
		log(String.format("【财务确认股东款】tid： %s 更新isreward： %s -> %s", tid, isreward, isreward2));
		
		return statusMsgJson(1, "确认成功");
	}
	
	/** 保存 已移除的卡 */
	@Override
	public JSON xyEmailCardTrashSave() {
		int mid = getInt("mid");
		int bank_id = getInt("bank_id");
		String card_number = getStr("card_number");
		this.commonDao.insertEmailCardTrash(mid, bank_id, card_number);
		return statusMsgJson(1, "已转移到回收箱");
	}
	
	/** 删除 已移除的卡 */
	@Override
	public JSON xyEmailCardTrashDelete() {
		int mid = getInt("mid");
		int bank_id = getInt("bank_id");
		String card_number = getStr("card_number");
		this.commonDao.deleteEmailCardTrash(mid, bank_id, card_number);
		return statusMsgJson(1, "已恢复");
	}
	
	/** 移除的卡列表 */
	@Override
	public JSON xyEmailCardTrashList() {
		List<Map<String, Object>> card = this.commonDao.selectEmailCardTrashList(getInt("mid"));
		return statusMsgJsonObj(getStatus(card), card);
	}

	/** `t_ship_tixian` 列表 */
	@Override
	public DataGrid shipTixianList() {

		int page = getPages("page");
		int rows = getInt("rows");
		
		String uname = getStr("uname");
		String mobile = getStr("mobile");
		String status = getStr("status");
		
		String startDate = getStr("startDate");
		String endDate = getStr("endDate");
		if (!"".equals(endDate)) {
			endDate += " 23:59:59.999";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("uname", uname);
		map.put("mobile", mobile);
		map.put("status", status);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("page", page);
		map.put("rows", rows);
		
		DataGrid dg = new DataGrid();
		dg.setRows(this.commonDao.selectShipTixianList(map));
		dg.setTotal((long) this.commonDao.selectCountShipTixian(map));
		return dg;
	}

	/** t_ship_tixian 提现审核 */
	@Override
	@Transactional
	public JSON shipTixianUpdateStatus() {
		
		int status = getInt("status");
		String tidStr = getStr("tids");
		
		if (status == 0 || tidStr.length() == 0) {
			return statusMsgJson(0, "参数有误");
		}
		
		String[] tids = tidStr.split(",");
		
		int res = this.commonDao.updateStatusByTidsFromShipTixian(status, tids);
		
		//已放款 修改用户额度
		if (status == 3) {
			String[] mids = getStr("mids").split(",");
			this.commonDao.moveFreezeToTixianFromMemberAccountByMids(mids);
		}
		
		return statusMsgJson(1, String.format("成功更新 %s 条记录", res));
	}
	
	/** 导出 `t_ship_tixian` */
	@Override
	public JSON shipTixianExport() {

		String uname = getStr("uname");
		String mobile = getStr("mobile");
		String status = getStr("status");
		
		String startDate = getStr("startDate");
		String endDate = getStr("endDate");
		if (!"".equals(endDate)) {
			endDate += " 23:59:59.999";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("uname", uname);
		map.put("mobile", mobile);
		map.put("status", status);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("page", 0);
		map.put("rows", 1000);
		
		List<Map<String, Object>> data = this.commonDao.selectShipTixianList(map);
		
		InputStream is = null;
		OutputStream os = null;
		String fileName = System.currentTimeMillis()+"";
		String filePath = "E:\\tomcat\\tomcatZJ2Manager\\webapps\\zjgj2tj\\download\\txreward\\"+fileName+".xls";
		String serverPath = getContextPath(getRequest()) + "download/txreward/"+fileName+".xls";
		
		try {
			is = shipTixianExport(data);
			
			os = new FileOutputStream(new File(filePath));
			IOUtils.copy(is, os);
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMsgJson(1, serverPath);
	}
	
	public InputStream shipTixianExport(List<Map<String, Object>> list) throws Exception {
		String[] title = new String[]{"用户编号","申请人","手机","提现金额","收款人",
				"收款行","收款卡号","申请日期"};
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); /*HH:mm:ss*/
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Object[] obj = new Object[title.length];
			
			obj[0] = dealNullExcel(map.get("mid"));
			obj[1] = dealNullExcel(map.get("nick"));
			obj[2] = dealNullExcel(map.get("mobile"));
			obj[3] = dealNullExcel(map.get("money_tx"));
			obj[4] = dealNullExcel(map.get("bank_user"));
			obj[5] = dealNullExcel(map.get("bank_name"));
			obj[6] = dealNullExcel(map.get("bank_number"));
			obj[7] = dealNullExcel(map.get("cdate"));
			
			dataList.add(obj);
		}
		
		WriteExcel excel = new WriteExcel(title, dataList);
		InputStream inputStream;
		inputStream = excel.export();
		
		return inputStream;
	}

	
	// TODO 




	
	
	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** ==================== 查询/获取 订单号 ================== */
	
	
	/** 访问新颜接口 获取新颜公积金订单号 */
	@Override
	public JSON createXinyanFundTradeNo(HttpServletRequest req) {
		
		int mid = getInt(req, "mid");
		String txnType = "fund";
		
		//5分钟可采集一次
		Map<String, Object> fund_db = this.commonDao.selectSpiderPerson(mid, txnType);
		if (fund_db != null) {
			Date gdate = (Date)fund_db.get("gdate");
			if( (System.currentTimeMillis() - gdate.getTime()) < (1000 * 1 * 60 * 5) ){ //每隔5分钟可以采集一次
				if ((int)fund_db.get("is_done") == 1) {	//已经采集完
					//{"success":false,"data":null,"errorCode":"0016","errorMsg":"提交过于频繁"}
					return statusMsg_xy(false, "请求过于频繁，请5分钟后再试！");
				} else {
					return json_xy_defaut(true, JSON.parse("{\"tradeNo\":\""+fund_db.get("trade_no")+"\"}"), null, null);
				}
			}
		}
		
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String login_type = req.getParameter("login_type");
		String origin = req.getParameter("origin");
		String id_card = req.getParameter("id_card");
		String mobile = req.getParameter("mobile");
		//String notify_url=req.getParameter("notify_url");
		String area_code2=req.getParameter("area_code");
		String real_name = getStr("real_name");
		try {
			real_name = URLEncoder.encode(real_name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		String transId = "LC"+System.currentTimeMillis();// 商户订单号
		//this
		String url = "http://" + req.getServerName() + ":" + req.getServerPort()+"/xinYanAPI/fund/FundAction?"
				+"account="+account + "&password="+password + "&login_type="+login_type +"&origin="+origin
				+"&id_card=" + id_card + "&mobile="+ mobile+"&area_code="+area_code2 + "&urlType=fundTaskCreateUrl"
				+ "&member_trans_id=" + transId + "&real_name=" + real_name;
		
		String res = CallUrlByGet.callUrlByGet(url, "UTF-8");
		
		JSONObject jsonObject = JSONObject.parseObject(res);
		if (! ((boolean)jsonObject.get("success"))) {
			return jsonObject;
		}
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		String tradeNo = jsonObject2.getString("tradeNo");
		
		Map<String, Object> map = new HashMap<>();

		int count = this.commonDao.selectCountSpiderPerson(mid, txnType);	//查询是否存在记录
		
		map.put("mid", mid);
		map.put("txn_type", txnType);
		map.put("trans_id", transId);
		map.put("trade_no", tradeNo);
		map.put("is_done", 0);
		map.put("is_valid", 0);
		map.put("cdate", getDateStr("yyyy-MM-dd HH:mm:ss"));
		
		if (count == 0) {
			this.commonDao.insertSpiderPerson(map);
		} else {
			this.commonDao.replaceSpiderPerson(map);
		}
		
		return jsonObject;
	}
	
	/** 访问新颜接口 获取新颜车险保单订单号 */
	@Override
	public JSON createXinyanInsuranceTradeNo(HttpServletRequest req) {
		
		int mid = getInt(req, "mid");
		String txnType = "insurance";
		
		//5分钟可采集一次
		Map<String, Object> fund_db = this.commonDao.selectSpiderPerson(mid, txnType);
		if (fund_db != null) {
			Date gdate = (Date)fund_db.get("gdate");
			if( (System.currentTimeMillis() - gdate.getTime()) < (1000 * 1 * 60 * 5) ){ //每隔5分钟可以采集一次
				if ((int)fund_db.get("is_done") == 1) {	//已经采集完
					//{"success":false,"data":null,"errorCode":"0016","errorMsg":"提交过于频繁"}
					return statusMsg_xy(false, "请求过于频繁，请5分钟后再试！");
				} else {
					return json_xy_defaut(true, JSON.parse("{\"tradeNo\":\""+fund_db.get("trade_no")+"\"}"), null, null);
				}
			}
		}
		
		String account = req.getParameter("account");
		
		try {
			account = URLEncoder.encode(account,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String id_card = req.getParameter("id_card");
		String login_type = req.getParameter("login_type");
		String insurer_code = req.getParameter("insurer_code");
		
		String transId = "LC"+System.currentTimeMillis();// 商户订单号
		
		String url = "http://" + req.getServerName() + ":" + req.getServerPort()+"/xinYanAPI/insurance/InsuranceAction?"
				+"account="+account + "&login_type="+login_type +"&insurer_code="+insurer_code
				+"&id_card=" + id_card + "&urlType=taskCreateUrl"
				+ "&member_trans_id=" + transId;
		//url = encode(url);
		
		String res = CallUrlByGet.callUrlByGet(url, "UTF-8");
		
		JSONObject jsonObject = JSONObject.parseObject(res);
		if (jsonObject == null || ! ((boolean)jsonObject.get("success"))) {
			return jsonObject;
		}
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		String tradeNo = jsonObject2.getString("tradeNo");
		
		Map<String, Object> map = new HashMap<>();

		int count = this.commonDao.selectCountSpiderPerson(mid, txnType);	//查询是否存在记录
		
		map.put("mid", mid);
		map.put("txn_type", txnType);
		map.put("trans_id", transId);
		map.put("trade_no", tradeNo);
		map.put("is_done", 0);
		map.put("is_valid", 0);
		map.put("cdate", getDateStr("yyyy-MM-dd HH:mm:ss"));
		
		if (count == 0) {
			this.commonDao.insertSpiderPerson(map);
		} else {
			this.commonDao.replaceSpiderPerson(map);
		}
		
		return jsonObject;
	}

	/** 访问新颜接口 获取新颜个人信息订单号 */
	@Override
	@Transactional
	public JSON createXinyanTradeNo(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		String txnType = getStr(req, "txnType");
		
		
		//5分钟可采集一次
		if ("email".equals(txnType)) {
			List<Map<String, Object>> email_list = this.commonDao.selectSpiderEmail(mid);
			if (email_list.size() > 0) {
				Map<String, Object> email_late = email_list.get(email_list.size() - 1);
				Date gdate = (Date)email_late.get("gdate");
				if( (System.currentTimeMillis() - gdate.getTime()) < (1000 * 1 * 60 * 5) ){ //每隔5分钟可以采集一次
					if ((int)email_late.get("status") == 1) {	//已经采集完
						//{"success":false,"data":null,"errorCode":"0016","errorMsg":"提交过于频繁"}
						return statusMsgJson(0, "请求过于频繁，请5分钟后再试！");
					} else {
						return statusMsgJson(1, (String)email_late.get("trade_no"));
					}
				}
			}
		} else {
			Map<String, Object> fund_db = this.commonDao.selectSpiderPerson(mid, txnType);
			if (fund_db != null) {
				Date gdate = (Date)fund_db.get("gdate");
				if( (System.currentTimeMillis() - gdate.getTime()) < (1000 * 1 * 60 * 5) ){ //每隔5分钟可以采集一次
					if ((int)fund_db.get("is_done") == 1) {	//已经采集完
						//{"success":false,"data":null,"errorCode":"0016","errorMsg":"提交过于频繁"}
						//return statusMsg_xy(false, "请求过于频繁，请5分钟后再试！");
						return statusMsgJson(0, "请求过于频繁，请5分钟后再试！");
					} else {
						//return json_xy_defaut(true, fund_db.get("trade_no"), null, null);
						return statusMsgJson(1, (String)fund_db.get("trade_no"));
					}
				}
			}
		}
		
		String transId = "LC" + System.currentTimeMillis();// 商户订单号
		String url = "http://localhost:"+req.getServerPort()+"/xinYanAPI/taobao/PreOrderActionOriginal?mid="+mid+
				"&txnType="+txnType+"&transId="+transId;
		
		//{"errorCode":null,"data":"201805291600140161511349","errorMsg":null,"success":true}
		String res = CallUrlByGet.callUrlByGet(url, "UTF-8");	
		
		JSONObject jsonObject = JSON.parseObject(res);
		
		String trade_no;
		boolean success = (boolean) jsonObject.get("success");
		
		if (!success) {	//获取新颜订单失败
			return statusMsgJsonObj(0, jsonObject.get("errorMsg"));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		String dateStr = getDateStr("yyyy-MM-dd HH:mm:ss");
		trade_no = (String) jsonObject.get("data");
		
		if ("email".equals(txnType)) {	//邮箱
			map.put("mid", mid);
			map.put("trans_id", transId);
			map.put("trade_no", trade_no);
			map.put("status", 0);
			map.put("cdate", dateStr);
			this.commonDao.insertXinyanEmail(map);	
			
		} else {
			int count = this.commonDao.selectCountSpiderPerson(mid, txnType);	//查询是否存在记录
			
			map.put("mid", mid);
			map.put("txn_type", txnType);
			map.put("trans_id", transId);
			map.put("trade_no", trade_no);
			map.put("is_done", 0);
			map.put("is_valid", 0);
			map.put("cdate", dateStr);
			
			if (count == 0) {
				this.commonDao.insertSpiderPerson(map);
			} else {
				this.commonDao.replaceSpiderPerson(map);
			}
		}
		return statusMsgJson(1, trade_no);
	}

	/** 访问新颜接口 获取新颜医社保订单号 */
	@Override
	public JSON createXinyanSecurityTradeNo(HttpServletRequest req) {
		
		int mid = getInt(req, "mid");
		String txnType = "security";
		
		Map<String, Object> fund_db = this.commonDao.selectSpiderPerson(mid, txnType);
		if (fund_db != null) {
			Date gdate = (Date)fund_db.get("gdate");
			if( (System.currentTimeMillis() - gdate.getTime()) < (1000 * 1 * 60 * 5) ){ //每隔5分钟可以采集一次
				if ((int)fund_db.get("is_done") == 1) {	//已经采集完
					//{"success":false,"data":null,"errorCode":"0016","errorMsg":"提交过于频繁"}
					return statusMsg_xy(false, "请求过于频繁，请5分钟后再试！");
				} else {
					return json_xy_defaut(true, JSON.parse("{\"tradeNo\":\""+fund_db.get("trade_no")+"\"}"), null, null);
				}
			}
		}
		
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String login_type = req.getParameter("login_type");
		String origin = req.getParameter("origin");
		String id_card = req.getParameter("id_card");
		String mobile = req.getParameter("mobile");
		//String notify_url=req.getParameter("notify_url");
		String area_code2=req.getParameter("area_code");
		
		String real_name = req.getParameter("real_name");
		try {
			real_name = URLEncoder.encode(real_name,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//real_name = chineseToUnicode(real_name);
		
		String transId = "LC"+System.currentTimeMillis();// 商户订单号
		
		String url = "http://" + req.getServerName() + ":" + req.getServerPort()+"/xinYanAPI/spiderSecurity/SecurityAction?"
				+"account="+account + "&password="+password + "&login_type="+login_type +"&origin="+origin
				+"&id_card=" + id_card + "&mobile="+ mobile+"&area_code="+area_code2 + "&urlType=taskCreateUrl"
				+"&real_name="+real_name + "&member_trans_id=" + transId;
		
		String res = CallUrlByGet.callUrlByGet(url, "UTF-8");
		
		JSONObject jsonObject = JSONObject.parseObject(res);
		
		if (! ((boolean)jsonObject.get("success"))) {
			return jsonObject;
		}
		
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		String tradeNo = jsonObject2.getString("tradeNo");
		
		Map<String, Object> map = new HashMap<>();

		int count = this.commonDao.selectCountSpiderPerson(mid, txnType);	//查询是否存在记录
		
		map.put("mid", mid);
		map.put("txn_type", txnType);
		map.put("trans_id", transId);
		map.put("trade_no", tradeNo);
		map.put("is_done", 0);
		map.put("is_valid", 0);
		map.put("cdate", getDateStr("yyyy-MM-dd HH:mm:ss"));
		
		if (count == 0) {
			this.commonDao.insertSpiderPerson(map);
		} else {
			this.commonDao.replaceSpiderPerson(map);
		}
		
		return jsonObject;
	}
	
	
	/** 访问新颜接口 获取新颜个人信息订单号(通用 )(弃用) */
	@Deprecated
	@Override
	@Transactional
	public JSON xyCommonCreateTradeNo(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		String txnType = getStr(req, "txnType");
		
//		if (mid == 0 || "".equals(txnType)) {
//			return json_xy_defaut(false, null, "555", "用户id或者请求类型有误");
//		}
		
		
		//5分钟可采集一次
		Map<String, Object> data_db = null;	//获取数据库已创建订单
		if ("email".equals(txnType)) {
			List<Map<String, Object>> email_list = this.commonDao.selectSpiderEmail(mid);
			if (email_list.size() > 0) 
				data_db = email_list.get(email_list.size() - 1);
		} else {
			data_db = this.commonDao.selectSpiderPerson(mid, txnType);
		}
		
		if (data_db != null) {	//存在已创建订单
			Date gdate = (Date)data_db.get("gdate");
			if( (System.currentTimeMillis() - gdate.getTime()) < (1000 * 1 * 60 * 5) ){ //每隔5分钟可以采集一次
				if ( "1".equals(data_db.get("status") + "")	//已经采集完 //邮箱状态
						|| "1".equals(data_db.get("is_done") + "") ) {	//其他类型订单 京东，淘宝，支付宝，医社保，公积金..
					return statusMsg_xy(false, "请求过于频繁，请5分钟后再试！");
				} else {
					return json_xy_defaut(true, data_db.get("trade_no"), null, null);
				}
			}
		}
		
		String transId = "LC" + System.currentTimeMillis();// 商户订单号
		String url = ZjUrl.BASE_SERVER_URL_LOCAL;
		//this
		if ("fund".equals(txnType)) {
			String account = req.getParameter("account");
			String password = req.getParameter("password");
			String login_type = req.getParameter("login_type");
			String origin = req.getParameter("origin");
			String id_card = req.getParameter("id_card");
			String mobile = req.getParameter("mobile");
			//String notify_url=req.getParameter("notify_url");
			String area_code2=req.getParameter("area_code");
			
			url += "/xinYanAPI/fund/FundAction?"+"account="+account + "&password="+password 
					+ "&login_type="+login_type +"&origin="+origin
					+"&id_card=" + id_card + "&mobile="+ mobile+"&area_code="+area_code2 + "&urlType=fundTaskCreateUrl"
					+ "&member_trans_id=" + transId;
			
			Map<String, Object> map = new HashMap<>();

			int count = this.commonDao.selectCountSpiderPerson(mid, txnType);	//查询是否存在记录
			
			
			map.put("txn_type", txnType);
			map.put("trans_id", transId);
			
			map.put("is_done", 0);
			map.put("is_valid", 0);
			map.put("cdate", getDateStr("yyyy-MM-dd HH:mm:ss"));
			
			if (count == 0) {
				this.commonDao.insertSpiderPerson(map);
			} else {
				this.commonDao.replaceSpiderPerson(map);
			}
		} else {
			url += "/xinYanAPI/taobao/PreOrderActionOriginal?mid="+mid+
					"&txnType="+txnType+"&transId="+transId;
		}
		
		
		//{"errorCode":null,"data":"201805291600140161511349","errorMsg":null,"success":true}
		String res = CallUrlByGet.callUrlByGet(url, "UTF-8");	
		
		//JSONObject jsonObject = JSON.parseObject(res);
		JSONObject jsonObject = JSONObject.parseObject(res);
		
		String trade_no;
		
		if (! ((boolean)jsonObject.get("success"))) {
			return jsonObject;
		}
		
		//"data":"string" 结果是 订单字符串
		trade_no = (String) jsonObject.get("data");
		try {
			//"data":{"tradeNo": "string"} 结果是订单对象
			String tradeNo = ((JSONObject) jsonObject.get("data")).getString("tradeNo");
			trade_no = (tradeNo != null? tradeNo:trade_no);
		} catch (Exception e) {
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		String dateStr = getDateStr("yyyy-MM-dd HH:mm:ss");
		
		map.put("mid", mid);
		map.put("trade_no", trade_no);
		map.put("trans_id", transId);
		map.put("cdate", dateStr);
		
		if ("email".equals(txnType)) {	//邮箱
			map.put("status", 0);
			this.commonDao.insertXinyanEmail(map);	
			
		} else {
			int count = this.commonDao.selectCountSpiderPerson(mid, txnType);	//查询是否存在记录
			
			map.put("txn_type", txnType);
			map.put("is_done", 0);
			map.put("is_valid", 0);
			
			if (count == 0) {
				this.commonDao.insertSpiderPerson(map);
			} else {
				this.commonDao.replaceSpiderPerson(map);
			}
		}
		return jsonObject;
	}
	
	
	/** ============= 异步通知 ============== */
	
	/** 个人信用爬取异步通知地址 */
	@Override
	public void personMsgOrder(HttpServletRequest req) {
		
		log("个人信用爬取异步通知地址 :");
		
		//this.commonDao.update_spider_email_status("201805311223476828680951", 1);
		/**
		 * {"desc":"任务采集完成",
		 * 	"finished":true,
		 * 	"member_trans_id":"LC1526460607858",
		 * 	"phase_type":"TASK",
		 * 	"result":true,
		 * 	"task_content":{"user_id":"111111"},
		 * 	"task_type":"ALIPAY",
		 * 	"trade_no":"201805161650070155877400"}
		 * 
		 * */
		String body = readHttpBody();	//
		log("body:" + body);
		
		//Enumeration<String> enumeration= req.getHeaderNames();
		
		//header中包含商户号和终端号
		/*while (enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			log("模拟商户通知 header key:{"+key+"},value:{"+req.getHeader(key)+"}");
		}*/
		
		if (body != null && body.length() > 0) {
			JSONObject bodyObj = JSONObject.parseObject(body);
			if ((boolean) bodyObj.get("finished") && (boolean) bodyObj.get("result")) {
				String trade_no = (String)bodyObj.get("trade_no");
				String task_type = (String) bodyObj.get("task_type");
				if ("email".equalsIgnoreCase(task_type)) {
					this.commonDao.update_spider_email_status(trade_no, 1);	// 更新 `t_xy_spider_email` 表
				} else {
					this.commonDao.update_spider_person_status(trade_no);	// 更新 t_xy_spider_person 表					
					teAuto(task_type,trade_no);	//提额
				}
			}
		} else {
			log("个人信用爬取无返回信息");
		}
	}
	
	
	/** 通讯录爬取异步通知 */
	@Override
	public void mobileMsgOrder(HttpServletRequest req) {
		log("通讯录爬取异步通知地址 :");
		/**
		 * {"desc":"任务采集完成",
		 * 	"finished":true,
		 * 	"member_trans_id":"1526626794186",
		 * 	"phase_type":"TASK",
		 * 	"result":true,
		 * 	"task_content":{"user_id":"6d564e9cd2fc4d3093f713f6adc38c07"},
		 * 	"task_type":"CARRIER",
		 * 	"trade_no":"201805181459550160970403"}
		 * */
		String body = readHttpBody();	//
		
		if (body != null && body.length() > 0) {
			JSONObject bodyObj = JSONObject.parseObject(body);
			if ((boolean) bodyObj.get("finished") && (boolean) bodyObj.get("result")) {
				this.commonDao.update_spider_mobile_status((String)bodyObj.get("trade_no"));	// 更新 `t_xy_spider_mobile` 表
			}
		} else {
			log("通讯录爬取无返回信息");
		}
	}
	
	/** 医社保异步通知 */
	@Override
	public void notifySecurity(HttpServletRequest req) {
		log("医社保异步通知地址 :");
		/**
		 * {"desc":"任务采集完成",
		 * 	"finished":true,
		 * 	"member_trans_id":"1526626794186",
		 * 	"phase_type":"TASK",
		 * 	"result":true,
		 * 	"task_content":{"user_id":"6d564e9cd2fc4d3093f713f6adc38c07"},
		 * 	"task_type":"CARRIER",
		 * 	"trade_no":"201805181459550160970403"}
		 * */
		String body = readHttpBody();	//
		log(body);
		
		if (body != null && body.length() > 0) {
			JSONObject bodyObj = JSONObject.parseObject(body);
			if ((boolean) bodyObj.get("finished") && (boolean) bodyObj.get("result")) {
				String trade_no = (String)bodyObj.get("trade_no");
				this.commonDao.update_spider_person_status(trade_no);	// 更新 t_xy_spider_person 表					
				teAuto((String)bodyObj.get("task_type"), trade_no);	//提额
			}
		} else {
			log("医社保爬取无返回信息");
		}
	}
	
	/** 公积金异步通知 */
	@Override
	public void notifyFund(HttpServletRequest req) {
		log("公积金异步通知地址 :");
		String body = readHttpBody();	//
		log(body);
		
		if (body != null && body.length() > 0) {
			JSONObject bodyObj = JSONObject.parseObject(body);
			if ((boolean) bodyObj.get("finished") && (boolean) bodyObj.get("result")) {
				String trade_no = (String)bodyObj.get("trade_no");
				this.commonDao.update_spider_person_status(trade_no);	// 更新 t_xy_spider_person 表					
				teAuto((String)bodyObj.get("task_type"), trade_no);	//提额
			}
		} else {
			log("公积金爬取无返回信息");
		}
	}
	
	/** 车险保单异步通知 */
	@Override
	public void notifyInsurance(HttpServletRequest req) {
		log("车险保单异步通知 :");
		String body = readHttpBody();	//
		log(body);
		
		if (body != null && body.length() > 0) {
			JSONObject bodyObj = JSONObject.parseObject(body);
			if ((boolean) bodyObj.get("finished") && (boolean) bodyObj.get("result")) {
				String trade_no = (String)bodyObj.get("trade_no");
				this.commonDao.update_spider_person_status(trade_no);	// 更新 t_xy_spider_person 表					
				teAuto((String)bodyObj.get("task_type"), trade_no);	//提额
			}
		} else {
			log("车险保单爬取无返回信息");
		}
	}
	
	/** 自动提额操作 */
	@Transactional
	private void teAuto(String te_type, String te_order){
		try {
			Integer mid = this.commonDao.selectMidFromSpiderPersonByOrder(te_order);	//根据订单获取mid
			if (mid != null) {
				int count = this.commonDao.selectCountFromTelogXyByMidType(mid, te_type);	//查询是否已经提额过
				if (count == 0) {
					int add = 200;	//要增加的额度
					int xygd = this.commonDao.selectMemberXygdByMid(mid);	//根据mid获取用户额度
					xygd += add;
					Map<String, Object> map = new HashMap<>();
					map.put("mid", mid);
					map.put("te_type", te_type);
					map.put("te_order", te_order);
					map.put("totle_limit", xygd);
					map.put("add_limit", add);
					map.put("remark", "异步通知");
					this.commonDao.insertTelogXy(map);	//插入提额日志
					this.aidgoldDao.updateMemberXYGD(mid, xygd);	//更新额度
					log("提额成功："+te_order);
				} else {
					log("不可重复提额："+te_order);
				}
			}
		} catch (Exception e) {
			log("提额异常：" + te_order + " " + e.getMessage());
		}
	}
	
	/** 获取新颜邮箱账单 */
	@Override
	public JSON xyEmailCardBills() {
		String tradeNo = getStr("tradeNo");
		String url = ZjUrl.XY_URL_AUTO + "taobao/OrderNoAction?urlType=bills_data&orderNo="+tradeNo;
		String res = CallUrlByGet.callUrlByGet(url, "UTF-8");
		
		return deal_email_order(res);
	}

	/** 处理邮箱账单 */
	private JSON deal_email_order(String jsonStr) {
		
		JSONObject jsonObject = null;
		
		if (isNotEmpty(jsonStr)) {
			jsonObject = JSONObject.parseObject(jsonStr);
			
			if ((boolean) jsonObject.get("success") && jsonObject.get("data") != null) {	//返回结果是true
				JSONObject data = jsonObject.getJSONObject("data");
				
				int ind = (int)data.get("total_size");	//所有账单数
				if ( ind > 0 ) {
					JSONArray originalJsonArray = data.getJSONArray("bills");	//原账单数组
					
					//获取被移除到垃圾箱的卡
					List<Map<String, Object>> trashCards = this.commonDao.selectEmailCardTrashList(getInt("mid"));
					
					JSONArray dataArr = new JSONArray();	//重组账单
					int size;	//重组账单数
					
					JSONObject obj;	//原始每期账单
					JSONObject json_card = new JSONObject();
					for (Object originalJson : originalJsonArray) {
						obj = (JSONObject) originalJson;
						
						//移除 被删除的订单
						if (trashCards != null && trashCards.size() > 0) {
							Map<String, Object> trashCard = null;
							boolean existTrash = false;	//是否存在垃圾箱
							for (int i = 0; i < trashCards.size(); i++) {
								trashCard = trashCards.get(i);
								if ((int)trashCard.get("bank_id") == obj.getInteger("bank_id") 
										&& obj.getString("card_number").equals((String)trashCard.get("card_number"))) {
									existTrash = true;	//匹配到垃圾箱
									break;
								}
							}
							if (existTrash) {
								continue;
							}
						}
						
						JSONObject billObj = new JSONObject();	//每期账单对象
						billObj.put("bill_id", obj.get("bill_id"));		//账单标识
						billObj.put("bill_date", obj.get("bill_date"));	//账单日
						billObj.put("payment_due_date", obj.get("payment_due_date"));	//还款日
						billObj.put("new_balance", obj.get("new_balance"));	//账单金额
						billObj.put("min_payment", obj.get("min_payment"));	//最低还款额
						//billObj.put("", );
						//cardObj = obj.
						if (!dataArr.isEmpty()) {
							size = dataArr.size();
							for (int i = 0; i < size; i++) {
								json_card = dataArr.getJSONObject(i);
								if (json_card.getInteger("bank_id") == obj.getInteger("bank_id") &&
										json_card.getString("card_number").equals(obj.getString("card_number"))) {
									//遍历的账单已存在卡
									JSONArray billArr = json_card.getJSONArray("bills"); 
									billArr.add(billObj);
									break;
								} else {
									if (i == size - 1) {
										//遍历的账单是新卡
										dealNewCard(billObj, obj, dataArr);
									}
								}
							}
						} else {
							//创建第一张卡
							dealNewCard(billObj, obj, dataArr);
						}
					}
					//替换账单
					jsonObject.put("data", dataArr);
				}
			}
		}
		return jsonObject;
	}
	
	/**
	 * 处理新卡
	 * @param billObj	每期账单对象
	 * @param obj		原始每期账单
	 * @param dataArr	最终账单数据
	 */
	private void dealNewCard(JSONObject billObj, JSONObject obj, JSONArray dataArr) {
		JSONObject cardObj = new JSONObject();		//卡对象
		JSONArray billArr = new JSONArray();		//卡的账单数组
		billArr.add(billObj);	//往账单数组添加账单
		
		//填充卡对象
		cardObj.put("bank_id", obj.get("bank_id"));				//银行ID
		cardObj.put("card_number", obj.get("card_number"));		//卡号
		cardObj.put("name_on_card", obj.get("name_on_card"));	//持卡人姓名
		cardObj.put("new_balance", obj.get("new_balance"));		//账单金额
		cardObj.put("min_payment", obj.get("min_payment"));		//最低还款额
		
		//添加卡账单
		cardObj.put("bills", billArr);
		
		//添加卡到最终数组（返回到页面的数组）
		dataArr.add(cardObj);
	}
	
	


	public static void main(String[] args) {
		String jsonstr = "{\"success\":true,\"data\":{\"total_size\":31,\"size\":31,\"bills\":[{\"bill_id\":\"6101408640350273076_179921751\",\"original\":\"1\",\"bank_id\":1,\"bill_start_date\":\"1900-01-01\",\"bill_date\":\"2018-06-05\",\"name_on_card\":\"noname\",\"card_no\":null,\"card_number\":\"****\",\"payment_due_date\":\"2018-06-23\",\"credit_limit\":0.00,\"usd_credit_limit\":0.00,\"new_balance\":6467.97,\"usd_new_balance\":0.00,\"min_payment\":\"1048.26\",\"usd_min_payment\":0.00,\"past_due_amount\":0.00,\"usd_past_due_amount\":0.00,\"cash_limit\":0.00,\"usd_cash_limit\":0.00,\"cash_amount\":0.00,\"usd_cash_amount\":0.00,\"last_balance\":0.00,\"usd_last_balance\":0.00,\"last_payment\":0.00,\"usd_last_payment\":0.00,\"new_charges\":0.00,\"usd_new_charges\":0.00,\"adjustment\":0.00,\"usd_adjustment\":0.00,\"interest\":0.00,\"usdInterest\":null,\"create_time\":\"2018-06-07 11:22:16\",\"currency_type\":1,\"email_id\":\"6101408640350273076\",\"failure_point\":0,\"feature_point\":0,\"feature_point_add\":0,\"voucher_balance\":0.00,\"voucher_valid_date\":\"1900-01-01\",\"point\":0,\"point_add\":0,\"point_adjust\":0,\"point_lose_date\":\"2018-06-07\",\"point_reward\":0,\"point_travel\":0,\"point_used\":0,\"point_valid_date\":\"1900-01-01\",\"point_valid_date2\":\"2018-06-07\",\"point_valid_date3\":\"2018-06-07\",\"point_valid_until\":0,\"point_valid_until2\":0,\"point_valid_until3\":0,\"last_point\":0,\"last_modify_time\":\"2018-06-07 11:22:16\",\"mail_id\":\"6101408640350273076_193366563\",\"return_amount\":null,\"status\":0},{\"bill_id\":\"6101408640350273076_171906148\",\"original\":\"1\",\"bank_id\":1,\"bill_start_date\":\"1900-01-01\",\"bill_date\":\"2018-05-05\",\"name_on_card\":\"noname\",\"card_no\":null,\"card_number\":\"****\",\"payment_due_date\":\"2018-05-23\",\"credit_limit\":0.00,\"usd_credit_limit\":0.00,\"new_balance\":1394.07,\"usd_new_balance\":0.00,\"min_payment\":\"540.87\",\"usd_min_payment\":0.00,\"past_due_amount\":0.00,\"usd_past_due_amount\":0.00,\"cash_limit\":0.00,\"usd_cash_limit\":0.00,\"cash_amount\":0.00,\"usd_cash_amount\":0.00,\"last_balance\":0.00,\"usd_last_balance\":0.00,\"last_payment\":0.00,\"usd_last_payment\":0.00,\"new_charges\":0.00,\"usd_new_charges\":0.00,\"adjustment\":0.00,\"usd_adjustment\":0.00,\"interest\":0.00,\"usdInterest\":null,\"create_time\":\"2018-05-19 15:01:39\",\"currency_type\":1,\"email_id\":\"6101408640350273076\",\"failure_point\":0,\"feature_point\":0,\"feature_point_add\":0,\"voucher_balance\":0.00,\"voucher_valid_date\":\"1900-01-01\",\"point\":0,\"point_add\":0,\"point_adjust\":0,\"point_lose_date\":\"2018-05-19\",\"point_reward\":0,\"point_travel\":0,\"point_used\":0,\"point_valid_date\":\"1900-01-01\",\"point_valid_date2\":\"2018-05-19\",\"point_valid_date3\":\"2018-05-19\",\"point_valid_until\":0,\"point_valid_until2\":0,\"point_valid_until3\":0,\"last_point\":0,\"last_modify_time\":\"2018-05-19 15:01:39\",\"mail_id\":\"6101408640350273076_182813078\",\"return_amount\":null,\"status\":0}]},\"errorCode\":null,\"errorMsg\":null}";
		//deal_email_order(jsonstr);
	}

	@Override
	public JSON posSaleType(HttpServletRequest req) {
		
		return null;
	}

	





	

	

	











	




}
