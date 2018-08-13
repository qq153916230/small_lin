package com.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.app.dao.CommonDao;
import com.app.dao.ScenicDao;
import com.app.entity.Scenic;
import com.app.service.ScenicService;
import com.app.util.DataGrid;
import com.app.util.MD5;
import com.app.util.zjgj.ZjUtils;

@Service
public class ScenicServiceImpl extends ZjUtils implements ScenicService {
	
	@Autowired
	ScenicDao scenicDao;
	
	@Autowired
	CommonDao commonDao;
	
	private final String CLASSNAME = "ScenicServiceImpl";
	
	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = getStr(request, "regTimeFrom");
		String regTimeTo = getStr(request, "regTimeTo");
		String sname = getStr(request, "sname");
		int page = getInt(request, "page");
		int rows = getInt(request, "rows");
		
		Map<String, Object> map = getHashMap();
		
		map.put("from", regTimeFrom);
		map.put("to", getLastSecond(regTimeTo,false));
		map.put("sname", "%" + sname + "%");
		map.put("page", getPage(page));
		map.put("rows", rows);
		
		DataGrid dg = getDG();
		dg.setTotal(this.scenicDao.selectTotle(map));
		dg.setRows(this.scenicDao.selectList(map));
		return dg;
	}

	/**新增景区*/
	@Override
	public JSON scenicInsert(HttpServletRequest request) {
		
		Date date = new Date();
		String extraPath = "/uploadFile/scenicPic/";
		
		//上传图片
		String spicture =  getStr(request, "spicture");
		String filePath = getContextRealPath(request) + extraPath;
		String fileName = getDateFormat("yyyyMMddHHmmss").format(date) + ".jpg";
		String serverPath = getContextPath(request) + extraPath;
		
		try {
			uploadFileByBase64Str(filePath, spicture, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scenic sc = new Scenic();
			sc.setSname( getStr(request, "sname") );
			sc.setSurl( getStr(request, "surl") );
			sc.setSarea( getStr(request, "sarea") );
			sc.setSintroduce( getStr(request, "sintroduce") );
			sc.setTprice( getInt(request, "tprice") );
			sc.setSpicture(serverPath + fileName); 
			sc.setCdate(date);
		
		//入库
		this.scenicDao.insertSelective(sc);
		return statusMsgJson(1, "success");
	}

	/**买票*/
	@Override
	@Transactional
	public String buyTicket(HttpServletRequest request) {
		
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int tNum = getInt(request, "num");	//票数
		int umid = getInt(request, "mid");	
		int sid = getInt(request, "sid");	//景区id
		int ImSupper = getInt(request, "ImSupper");
		
		int relativeNum = this.scenicDao.selectCountRelativeByMid(umid);
		if (tNum > relativeNum) {
			return JsonpData(callbackparam, 4, "请下载最新版APP购票");
		}
		
		if (ImSupper != 1) {
			Map<String, Object> map = getHashMap();
			map.put("mid", umid);
			map.put("sid", sid);
			map.put("date", getDateStr(new Date(), "yyyy-MM-dd")+" 00:00:00");
			int exist = this.scenicDao.selectTicketExistToday(map);
			if (exist >= 1) {
				return JsonpData(callbackparam, 3, "同一个景区一天只允许购买一次门票");
			}
		}
		
		Map<String, Object> scenic = this.scenicDao.selectScenicBySid(sid);	//获取景区
		//String tPrice = getStr(request, "price");	//票价
		int tPrice = (int) scenic.get("tprice");
		BigDecimal price = new BigDecimal(tPrice);
		
		int res = execute(umid, price.multiply(new BigDecimal(tNum)));
		
		if (res == 1) {
			this.scenicDao.insertScenicToHis(umid, sid, tNum, price);	//插入购票历史
			return JsonpData(callbackparam, 1, "购票成功");
		}
		return JsonpData(callbackparam, 2, "可用余额不足");
	}
	
	public synchronized int execute(int mid, BigDecimal price){
		BigDecimal ubalance = this.scenicDao.selectScenicBalanceFromUser(mid);	//获取用户余额
		if (ubalance != null && ubalance.compareTo(price) >= 0 ) {
			ubalance = ubalance.subtract(price);
			this.scenicDao.updateScenicBalanceFromUser(mid, ubalance);	//更新余额
			return 1;
		} 
		return 0;
	}

	/**删除景区 + 图片*/
	@Override
	public JSON scenicDelete(HttpServletRequest request) {
		int sid = getInt(request, "sid");	
		String contextPath = request.getContextPath();	//项目名称
		
		String picName = this.scenicDao.selectByPrimaryKey(sid).getSpicture();	//要删除的景区图片
		int index = picName.indexOf(contextPath);
		if (picName != null && index != -1) {
			picName = picName.substring(index + contextPath.length());	// "/20180329163000.jpg"
			String path = getContextRealPath(request) + picName;
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
		this.scenicDao.deleteByPrimaryKey(sid);
		
		return statusMsgJson(1, "success");
	}

	/**获取额度*/
	@Override
	public String getBalance(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int mid = getInt(request, "mid");	
		return JsonpDataObj(callbackparam, JSON.toJSON(this.scenicDao.selectScenicUser(mid)));
	}

	/**jsonp获取景区列表*/
	@Override
	public String getScenicList(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		return JsonpDataObj(callbackparam, JSON.toJSON(this.scenicDao.selectScenicAll()));
	}

	/**根据sid获取景区*/
	@Override
	public String getScenicBySid(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int sid = getInt(request, "sid");
		return JsonpDataObj(callbackparam, JSON.toJSON(this.scenicDao.selectByPrimaryKey(sid)));
	}

	/**jsonp方式获取票列表*/
	@Override
	public String getTicketList(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int mid = getInt(request, "mid");
		return JsonpDataObj(callbackparam, JSON.toJSON(this.scenicDao.selectTicketList(mid)));
	}

	/**重置额度*/
	@Override
	public JSON resetBalance(HttpServletRequest request) {
		String mobile = getStr(request, "mobile");
		Integer mid = this.scenicDao.selectMidByMobileFromMember(mobile);
		if (mid == null || mid == 0) {
			return statusMsgJson(2, "该用户未注册");
		}
		Map<String, Object> map = getHashMap();
		map = this.scenicDao.selectScenicUser(mid);
		if (map == null) {
			log( CLASSNAME + ".resetBalance() insert a new record. mobile:" + mobile + " mid:" + mid);
			map = getHashMap();
			map.put("mid", mid);
			map.put("account", 10000);
			map.put("balance", 10000);
			map.put("cdate", new Date());
			this.scenicDao.insertScenicUser(map);
		} else {
			log( CLASSNAME + ".resetBalance() reset the record. mobile:" + mobile + " mid:" + mid + " balance:" + map.get("balance"));
			BigDecimal account = (BigDecimal) map.get("account");
			BigDecimal balance = (BigDecimal) map.get("balance");
			
			map.put("mid", mid);
			map.put("cdate", new Date());
			if (account.compareTo(new BigDecimal(0)) == 0 && balance.compareTo(new BigDecimal(0)) == 0) {
				System.out.println(1);
				map.put("account", 10000);
				map.put("balance", 10000);
			} else if (account.compareTo(new BigDecimal(2000)) == 0 && balance.add(new BigDecimal(8000)).compareTo(new BigDecimal(10000)) <= 0) {
				System.out.println(2);
				map.put("account", 8000);
				map.put("balance", 8000);
			} else if(account.compareTo(new BigDecimal(10000)) == 0 ||
					balance.add(new BigDecimal(8000)).compareTo(new BigDecimal(10000)) > 0) {
				return statusMsgJson(2, "不可重复添加");
			}
			this.scenicDao.resetBalance(map);
		}
		
		return statusMsgJson(1, "success");
	}

	/**验票*/
	@Override
	public String checkTicket(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int tid = getInt(request, "tid");
		//this.scenicDao.updateTicketValid(tid);
		return JsonpData(callbackparam, 2, "请从“龙川综金管家”微信公众号验票");
	}

	/**jsonp方式添加亲属*/
	@Override
	public String addRelative(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int mid = getInt(request, "mid");
		
		int relNum = this.scenicDao.selectRelativeCount(mid);
		
		if (relNum >= 4) {
			return JsonpData(callbackparam, 2, "人员超过限制");
		}
		
		String name = getStr(request, "name");
		String idno = getStr(request, "idno");
		String mobile = getStr(request, "mobile");
		
		Map<String, Object> map = getHashMap();
		map.put("mid", mid);
		map.put("name", name);
		map.put("idno", idno);
		map.put("mobile", mobile);
		this.scenicDao.insertRelative(map);
		
		return JsonpData(callbackparam, 1, "添加成功");
	}
	
	/** 添加亲属 */
	@Override
	public JSON saveRelative(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		
		int relNum = this.scenicDao.selectRelativeCount(mid);
		
		if (relNum >= 5) {
			return statusMsgJson(0, "最多只可添加5个人");
		}
		
		String name = getStr(req, "name");
		String idno = getStr(req, "idno");
		String mobile = getStr(req, "mobile");
		
		Map<String, Object> map = getHashMap();
		map.put("mid", mid);
		map.put("name", name);
		map.put("idno", idno);
		map.put("mobile", mobile);
		this.scenicDao.insertRelative(map);
		
		return statusMsgJson(1, "添加成功");
	}
	
	/**jsonp方式获取亲属*/
	@Override
	public String selectRelative(HttpServletRequest request) {
		String callbackparam = getStr(request, "callbackparam");	//得到js函数名称  
		int mid = getInt(request, "mid");
		return JsonpDataObj(callbackparam, JSON.toJSON(this.scenicDao.selectRelative(mid)));
	}
	
	/** 获取亲属 */
	@Override
	public JSON findRelative(HttpServletRequest req) {
		int mid = getInt(req, "mid");
		String mobile = getStr(req, "mobile");
		
		if (mid == 0 && !"".equals(mobile)) {
			mid = this.commonDao.selectMidByMobileFromMember(mobile);
		}
		
		return statusMsgJsonParseObj(1, JSON.toJSON(this.scenicDao.selectRelative(mid)));
	}

	/** 工作人员登入 */
	@Override
	public JSON login(HttpServletRequest request) {
		String user = getStr(request, "user");
		String pass = getStr(request, "pass");
		if ("".equals(user) && "".equals(pass)) {
			return statusMsgJson(2, "账号或密码不能为空");
		}
		Integer sid = this.scenicDao.selectScenicManager(user, pass);	//查询工作人员账号 
		if (sid != null && sid > 0) {
			
			//设置登入信息到session
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60 * 30);
			session.setAttribute("user", user);
			
			return statusMsgJson(1, sid.toString());
		}
		return statusMsgJson(3, "账号或密码错误");
	}

	/** 景区人员获取门票 */
	@Override
	public JSON selectTicketByManager(HttpServletRequest request) {
		
		int sid = getInt(request, "sid");
		String mobile = getStr(request, "mobile");
		
		if (sid == 0 && "".equals(mobile)) {
			return statusMsgJson(2, "参数有误");
		}
		Integer mid = this.scenicDao.selectMidByMobileFromMember(mobile);	
		
		if (mid == null) {
			return statusMsgJson(3, "该用户不存在");
		}
		
		return statusMsgJson(1, JSON.toJSON(this.scenicDao.selectTicketByManager(sid,mid)).toString());
	}

	/** 景区人员验票 */
	@Override
	public JSON checkTicketByManager(HttpServletRequest request) {
		int tid = getInt(request, "tid");
		this.scenicDao.updateTicketValid(tid);
		return statusMsgJson(1, "success");
	}

	/** 景区人员获取所有的票 条件查询 */
	@Override
	public JSON selectAllTicketByManager(HttpServletRequest request) {
		int sid = getInt(request, "sid");
		String startDate = getStr(request, "startDate");
		String endDate = getStr(request, "endDate");
		
		Map<String, Object> map = getHashMap();
		if (sid != 0) {
			map.put("sid", sid);
		}
		if (!"".equals(startDate) && !"".equals(endDate)) {
			map.put("from", startDate);
			map.put("to", endDate);
		}
		
		return statusMsgJson(1, JSON.toJSON(this.scenicDao.selectAllTicketByManager(map)).toString());
	}

	/** 1元购票活动 */
	@Override
	@Transactional
	public JSON buyTicketOne() {
		
		String order = getStr("orderid");
		log("1元购票订单："+ order);
		
		Map<String, Object> map = this.scenicDao.select_epay_payment_by_orderid(order);	//根据订单 获取 `t_zjgj_epay_payment`
		
		if (map == null) {
			log("1元购票失败：订单号有误 "+ order);
			return statusMsgJson(3, "订单号有误");
		}
		
		String mobile = (String) map.get("mobile");
		String pass = new MD5().GetMD5Code("123456");
		
		int sid = (int) map.get("rid");	//景区id
		
		if (sid != 5 && sid != 6) {
			log("1元购票失败：无该景区，或景区不在活动范围内！" + " 用户手机:"+mobile);
			return statusMsgJson(3, "无该景区，或景区不在活动范围内！");
		}
		
		Map<String, Object> member = null;
		
		try {
			member = this.commonDao.selectMemberByUnameAndPass(mobile, pass);
		} catch (Exception e) {
			log("1元购票失败："+SYSTEM_ERROR + " 用户手机:"+mobile);
			return (JSON) JSON.parse(SYSTEM_ERROR);
		}
		
		//查看用户是否为新用户
		String cdate = getDateStr("yyyy-MM-dd");
		
		int mid = 0;	
		if (member != null && (mid = (int) member.get("mid")) > 5000) {
			int count = this.scenicDao.selectCountTicketByMid(mid);	//查看用户已经购买门票数
			if (count > 0) {
				log("1元购票失败：活动每人仅可参与一次！" + " 用户手机:"+mobile);
				return statusMsgJson(2, "活动每人仅可参与一次！");
			}
		} else {
			log("1元购票失败：您已经是会员！" + " 用户手机:"+mobile);
			return statusMsgJson(2, "您已经是会员！");
		}
		
		Scenic scenic = this.scenicDao.selectByPrimaryKey(sid);
		if (scenic == null) {
			log("1元购票失败：景区有误，请联系管理员！" + " 用户手机:"+mobile);
			return statusMsgJson(3, "景区有误，请联系管理员");
		}
		int tPrice = scenic.getTprice();	//票价
		
		if ("".equals(tPrice)) {
			log("1元购票失败：票价有误！" + " 用户手机:"+mobile);
			return statusMsgJson(3, "票价有误");
		}
		
		BigDecimal price = new BigDecimal(tPrice);
		
		this.scenicDao.insertScenicToHis(mid, sid, 1, price);	//插入购票历史
		log("1元购票成功："+mobile+ " 景区sid:"+sid);
		return statusMsgJson(1, "购票成功");
	}

	/** 1元购票预订单创建 */
	@Override
	public JSON preOrder() {
		
		String order = getStr("orderid");
		String mobile = getStr("mobile");
		int sid = getInt("sid");
		int ptype = 5;
		
		try {
			this.scenicDao.insertOneOrder(order, mobile, sid, ptype);	//一元购票预订单
		} catch (Exception e) {
			return statusMsgJson(2, "预订单创建失败！");
		}
		log("1元购票预订单创建："+mobile+ " 景区sid:"+sid);
		return statusMsgJson(1, "预订单创建成功！");
	}

	/** 根据mid 获取票列表  */
	@Override
	public JSON selectTicketByMid() {
		return statusMsgJsonObj(1, JSON.toJSON(this.scenicDao.selectTicketList(getInt("mid"))));
	}


	


}
