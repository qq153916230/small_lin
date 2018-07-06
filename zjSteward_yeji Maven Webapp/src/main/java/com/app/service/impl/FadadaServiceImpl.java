package com.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.AidgoldDao;
import com.app.dao.AidgoldLogDao;
import com.app.dao.FDDContractDao;
import com.app.dao.MemberAddrDao;
import com.app.dao.MemberAuthorDao;
import com.app.dao.ParamDao;
import com.app.entity.Aidgold;
import com.app.entity.AidgoldLog;
import com.app.entity.FDDContract;
import com.app.entity.MemberAddr;
import com.app.entity.MemberAuthor;
import com.app.service.FadadaService;
import com.app.util.LogTableInsert;
import com.app.util.NumberToWord;
import com.app.util.ReadHTMLFilePath;
import com.app.util.SendEmail;
import com.fadada.sdk.client.FddClientBase;
@Service
public class FadadaServiceImpl implements FadadaService {
	
	@Autowired
	MemberAuthorDao memberAuthorDao;
	@Autowired
	FDDContractDao fddContractDao;
	@Autowired
	MemberAddrDao memberAddrDao;
	@Autowired
	AidgoldDao aidgoldDao;
	@Autowired
	ParamDao paramDao;
	@Autowired
	AidgoldLogDao aidgoldLogDao;
	
	private static String appId;
	private static String secret;
	private static String version;
	private static String fddurl;
	
	private static boolean on;	//日志开关
	
	static FddClientBase clientBase;
	static Logger log4j;
	
	//static Log logger2 = LogFactory.getLog("mylogger2");
	
	
	private static ReadHTMLFilePath rp = new ReadHTMLFilePath(); //读取配置文件对象
	//private static MemberAuthor memberAuthor = new MemberAuthor();
	//private static FDDContract fddContract = new FDDContract();
	
	Date cdate = new Date();
	
	HttpSession session;
	
	/** 初始化参数 和 法大大对象 */
	static {
		appId = rp.getHTMLFilePath("appId");
		secret = rp.getHTMLFilePath("secret");
		version = rp.getHTMLFilePath("version");
		fddurl = rp.getHTMLFilePath("fddurl");
		
		on = Boolean.parseBoolean(rp.getHTMLFilePath("button"));
		
		clientBase = new FddClientBase(appId, secret, version, fddurl);
		log4j = Logger.getLogger("mylogger2");
	}
	
	/**2.调用个人CA注册接口*/
	@Override
	@Transactional
	public String syncPersonAuto(HttpServletRequest request, HttpServletResponse response) {
		log("用户签署:", "开始");
		//logger2.info("abc");
		
		session = request.getSession();	//创建session
		String tidPA = (String)session.getAttribute("aidgoldId");	//从session中获取助理金id
		request.setAttribute("aidgoldId", tidPA);	//给request设置助力金id
		
		String customer_name = (String) request.getAttribute("customer_name");
		String email = (String) request.getAttribute("email");
		String id_card = (String) request.getAttribute("id_card");
		String ident_type = (String) request.getAttribute("ident_type");
		String mobile = (String) request.getAttribute("mobile");
		
		//==String s_province = (String) request.getAttribute("s_province");	//省份
		//==String s_city = (String) request.getAttribute("s_city");	//城市
		//==String s_county = (String) request.getAttribute("s_county"); //用来获取邮政编码
		String address = (String) request.getAttribute("address"); //收获地址
		//==String addStr = s_province + s_city + s_county + address;	//完整地址
		
		//session.setAttribute("addStr", addStr);//session完整地址
		//==request.setAttribute("addStr", addStr);	//把完整地址写入请求
		request.setAttribute("addStr", address);	//把完整地址写入请求
		
		Map<String, String> addMap = new HashMap<>();
		/*===== DAO =====*/
		//==String gpcode = this.memberAddrDao.selectRegionCode(s_province);	//查询省份的邮编
		//==addMap.put("parentCode", gpcode);
		//==addMap.put("region", s_city);
		//==String pcode = this.memberAddrDao.selectRegionCodeByParent(addMap);	//根据省份查询城市的邮编
		
		//==addMap.put("parentCode", pcode);
		//==addMap.put("region", s_county);
		//==String zip_code = this.memberAddrDao.selectRegionCodeByParent(addMap);	//根据城市查询邮编
		
		//String idfront = (String) request.getAttribute("idfront");
		//String idback = (String) request.getAttribute("idback");
		
		String customer_id = null; //返回的客户编号
		String result = null; //返回success/error
		
		
		//默认证件类型为 0(身份证)
		if (ident_type == null || "".equals(ident_type) || "undefined".equals(ident_type)) {
			ident_type = "0";
		}
		
		//查询数据库是否已经存在用户
		Map<String, String> map = new HashMap<String, String>();
		map.put("sname", customer_name);
		map.put("idno", id_card);
		map.put("mobile", mobile);
		/*===== DAO =====*/
		//FDDUser fu = this.fddUserDao.selectMatch(map);
		//memberAuthor = this.memberAuthorDao.selectMatch(map);
		
		/*if (memberAuthor != null) {
			request.setAttribute("userId", memberAuthor.getMid());	//合同表需要用插入用户id
			return "forward:/fadada/uploadDocs";
		}*/
		
		String jsonStr = clientBase.invokeSyncPersonAuto(customer_name, email, id_card, ident_type, mobile);
		log("调用个人ca注册返回结果：", jsonStr);
		JSONObject jsonObject = new JSONObject().parseObject(jsonStr);
		
		result = jsonObject.getString("result");
		
		if ("success".equals(result)) {
			//获取客户编号
			customer_id = jsonObject.getString("customer_id");
			log("法大大生成客户编号：", customer_id);
			
			//memberAuthor = new MemberAuthor();
			
			//组装插入数据库的数据
			//memberAuthor.setSname(customer_name);
			//memberAuthor.setIdno(id_card);
			//memberAuthor.setIdtype(Short.parseShort(ident_type));
			//memberAuthor.setMobile(mobile);
			//memberAuthor.setCdate(cdate);
			
			//memberAuthor.setEmail(email);
			//memberAuthor.setIdfront(idfront);
			//memberAuthor.setIdback(idback);
			
			//将客户数据加入session
			//session.setAttribute("fddUser", fddUser);
			
			/*===== DAO =====*/
			//this.fddUserDao.insertSelective(fddUser);
			
			MemberAuthor memberAuthor = this.memberAuthorDao.selectMatch(map);
			memberAuthor.setCustomerId(customer_id);
			
			MemberAuthor ma = new MemberAuthor();
			ma.setMid(memberAuthor.getMid());
			ma.setCustomerId(customer_id);
			/*===== DAO =====*/
			this.memberAuthorDao.updateByPrimaryKeySelective(ma);	//更新用户信息 添加客户编号
			//session.setAttribute("userId", fddUser.getPid());	//合同表需要用插入用户id
			
			//int memberAddrMid = memberAuthor.getMid(); //用户地址的mid
			
			//==MemberAddr maData = new MemberAddr();
			/*===== DAO =====*/
			//MemberAddr memberAddr = this.memberAddrDao.selectByMid(memberAddrMid);
			
			//==maData.setMid(memberAddrMid);
			//==maData.setTel(memberAuthor.getMobile());
			//==maData.setAddress(address);
			//==maData.setZipCode(zip_code);
			
			//==maData.setContactName(" ");
			//==maData.setProvince(0);
			//==maData.setCity(0);
			//==maData.setCounty(0);
			
			//==if (memberAddr != null) {
				//用户地址数据
			//==maData.setId(memberAddr.getId());
				/*===== DAO =====*/
			//==this.memberAddrDao.updateByPrimaryKeySelective(maData);
			//==} else {
			//==this.memberAddrDao.insertSelective(maData);
			//==}
			
			request.setAttribute("userId", memberAuthor.getMid());	//合同表需要用插入用户id
			//return "forward:/fadada/uploadDocs";	//调用文档传输接口
			return "forward:/fadada/uploadTemplate";	//调用模板传输接口
		} else {
			//注册出错，返回json
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(jsonStr);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	/**10.调用文档传输接口 (未使用)*/
	@Override
	@Transactional
	public String uploadDocs(HttpServletRequest request, HttpServletResponse response) {
		String contract_id = (String) request.getAttribute("contract_id");
		String doc_title = (String) request.getAttribute("doc_title");
		//String filePath = (String) request.getAttribute("file");
		//int userId = (int) session.getAttribute("userId");	//从session中获取用户id
		int userId = (int)request.getAttribute("userId");
		log("调用文档传输接口：", "合同编号:"+contract_id+" 合同标题:" +doc_title+ " 用户编号:"+userId);
		
		//生成合同名，合同编号
		cdate = new Date();
		String customer_name = (String) request.getAttribute("customer_name"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String ctime = sdf.format(cdate);
		
		contract_id = userId + ctime; 
		doc_title = customer_name + "贷款合同  " + contract_id;
		
		
		//获取web项目在本地的绝对路径
		String webRealPath = request.getSession().getServletContext().getRealPath("");
		File file = new File(webRealPath + "/download/application.pdf");
		String result;
		
		String jsonStr = clientBase.invokeUploadDocs(contract_id, doc_title, file, "", ".pdf");
		log("调用文档传输接口：", jsonStr);
		
		JSONObject jsonObject = new JSONObject().parseObject(jsonStr);
		result = jsonObject.getString("result");
		if (result.equals("success")) {
			FDDContract fddContract = new FDDContract();
			//组装插入数据库的数据
			fddContract.setContractId(contract_id);
			fddContract.setDocTitle(doc_title);
			//fddContract.setDocFile(filePath);
			fddContract.setUserId(userId);
			fddContract.setCdate(cdate);
			//设置交易号为当前时间戳
			String transaction_id = String.valueOf(cdate.getTime());
			fddContract.setTransactionId(transaction_id);
			fddContract.setAidgoldId(Integer.parseInt((String) session.getAttribute("aidgoldId")));//设置助力金id
			
			
			//将对象保存到session，给签署接口提供数据
			//session.setAttribute("fddContract", fddContract);
			
			/*===== DAO =====*/
			this.fddContractDao.insertSelective(fddContract);
			return "forward:/fadada/ExtSign";
		} else {
			//注册出错，返回json
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(jsonStr);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**调用模板传输接口*/
	@Override
	public String uploadTemplate(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = (int)request.getAttribute("userId"); //用户编号
		log("调用模板传输接口 获取用户编号：", userId);
		//获取web项目在本地的绝对路径
		String webRealPath = request.getSession().getServletContext().getRealPath("");
		File file = new File(webRealPath + "/download/application.pdf");	//合同模板文件
		
		//生成合同名，合同编号
		cdate = new Date();
		//String customer_name = (String) request.getAttribute("customer_name"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String ctime = sdf.format(cdate);
		
		//contract_id = ctime + userId; 
		//doc_title = customer_name + "贷款合同  " + ctime;
		
		String doc_url = "";	//文档地址
		String template_id = userId + ctime;	//模板id
		//session.setAttribute("template_id", template_id);	//传给合同生成接口
		request.setAttribute("template_id", template_id);	//传给合同生成接口
		
		String response1 = clientBase.invokeUploadTemplate(template_id, file, doc_url);
		log("调用合同模板传输接口返回结果：", response1);
		
		JSONObject jsonObject = JSONObject.parseObject(response1);
		if (jsonObject.get("result").equals("success")) {
			return "forward:/fadada/generateContract";
		} else {
			//调用合同模板传输接口，返回json
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(jsonObject);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**调用合同生成接口*/
	@Override
	@Transactional
	public String generateContract(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = (int)request.getAttribute("userId"); //用户编号
		String customer_name = (String) request.getAttribute("customer_name");	//用户名
		log("调用合同生成接口获取:", "用户编号："+userId + " 用户名："+customer_name);
		
		//String template_id = (String) session.getAttribute("template_id");	//模板id
		String template_id = (String) request.getAttribute("template_id");	//模板id
		String contract_id = template_id;	//合同编号 设置成和合同模板一样 用户编号 + 时间
		String doc_title = customer_name + "贷款合同  " + template_id;	//文档标题
		String font_size = "10";	//字体大小
		String font_type = "0";	//字体类型
		String parameter_map = "";	//填充内容
		String dynamic_tables = "";	//动态表单
		
		//System.out.println("session获取到的助力金id："+(String)session.getAttribute("aidgoldId"));
		//int tid = Integer.parseInt((String)session.getAttribute("aidgoldId"));	//hetong.jsp设置的
		log("获取到的助力金id：", (String)request.getAttribute("aidgoldId"));
		int tid = Integer.parseInt((String)request.getAttribute("aidgoldId"));	//助力金id改成从request获取
		
		Aidgold aidgold = this.aidgoldDao.selectByPrimaryKey(tid);
		
		Calendar cal = new GregorianCalendar();
		
		Calendar cal1 = new GregorianCalendar();
		cal1.set(Calendar.DATE, cal.get(Calendar.DATE)+1);	//一天后
		
		Calendar cal15 = new GregorianCalendar();
		cal15.set(Calendar.DATE, cal.get(Calendar.DATE)+14);	//15天后
		
		JSONObject jsonObject = new JSONObject();
		
		//jsonObject.put("1", "");	//甲方姓名
		//jsonObject.put("2", "");	//甲方身份证
		//jsonObject.put("3", "");	//甲方通讯地址
		//jsonObject.put("4", "");	//甲方联系电话
		//借款方
		jsonObject.put("5", (String)request.getAttribute("customer_name"));	//乙方姓名
		jsonObject.put("6", (String) request.getAttribute("id_card"));	//乙方身份证
		jsonObject.put("7", (String)request.getAttribute("addStr"));	//乙方通讯地址
		jsonObject.put("8", (String) request.getAttribute("mobile"));	//乙方联系方式
		
		jsonObject.put("9", NumberToWord.transfer(aidgold.getJkmoney()+""));	//人民币汉字
		jsonObject.put("10", String.format("%.2f", (double)aidgold.getJkmoney())+"");	//人名币数字
		
		jsonObject.put("11", cal.get(Calendar.YEAR) + "");	//借款开始年份
		jsonObject.put("12", cal.get(Calendar.MONTH) + 1 + "");	//借款开始月份
		jsonObject.put("13", cal.get(Calendar.DAY_OF_MONTH) + "");	//借款开始日期
		
		jsonObject.put("14", cal15.get(Calendar.YEAR) + "");	//借款结束年份
		jsonObject.put("15", cal15.get(Calendar.MONTH) + 1 + "");	//借款结束月份
		jsonObject.put("16", cal15.get(Calendar.DAY_OF_MONTH) + "");	//借款结束日期
		
		jsonObject.put("17", cal1.get(Calendar.YEAR) + "");	//放款年份
		jsonObject.put("18", cal1.get(Calendar.MONTH) + 1 + "");	//放款月份
		jsonObject.put("19", cal1.get(Calendar.DAY_OF_MONTH) + "");	//放款日期
		
		jsonObject.put("20", aidgold.getSkname());	//户名
		jsonObject.put("21", aidgold.getSkbankno());	//账户
		jsonObject.put("22", aidgold.getSkbank());	//开户行
		
		jsonObject.put("23", cal.get(Calendar.YEAR) + "");	//签订年份
		jsonObject.put("24", cal.get(Calendar.MONTH) + 1 + "");	//签订月份
		jsonObject.put("25", cal.get(Calendar.DAY_OF_MONTH) + "");	//签订日期
		//jsonObject.put("", "");	//
		
		parameter_map = jsonObject.toJSONString();
		log("合同生成接口填充内容：", parameter_map);
		
		String response1 = clientBase.invokeGenerateContract(template_id, contract_id, doc_title, 
				font_size, font_type, parameter_map, dynamic_tables);
		log("调用合同生成接口返回结果：", response1);
		
		jsonObject = new JSONObject().parseObject(response1);
		String result = jsonObject.getString("result");
		if (result.equals("success")) {
			cdate = new Date();
			//组装插入数据库的数据
			FDDContract fddContract = new FDDContract();
			fddContract.setContractId(contract_id);
			fddContract.setDocTitle(doc_title);
			//fddContract.setDocFile(filePath);
			fddContract.setUserId(userId);
			fddContract.setCdate(cdate);
			//设置交易号为当前时间戳
			String transaction_id = String.valueOf(cdate.getTime());
			log("用户签署产生的交易号：", transaction_id);
			fddContract.setTransactionId(transaction_id);
			int agId = Integer.parseInt((String) session.getAttribute("aidgoldId"));//助理金id
			fddContract.setAidgoldId(agId);//设置助力金id
			
			//将对象保存到session，给签署接口提供数据
			//session.setAttribute("fddContract", fddContract);
			
			/*===== DAO =====*/
			FDDContract fcCheck = this.fddContractDao.selectByAidgoldId(agId);
			if (fcCheck != null) {
				this.fddContractDao.updateByAidgoldIdSelective(fddContract);
			} else {
				this.fddContractDao.insertSelective(fddContract);
			}
			return "forward:/fadada/ExtSign";
		} else {
			//注册出错，返回json
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(response1);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**14.调用手动签署接口*/
	@Override
	public String ExtSign(HttpServletRequest request, HttpServletResponse response) {
		
		//FDDUser fddUser = (FDDUser)session.getAttribute("fddUser");
		//FDDContract fddContract = (FDDContract)session.getAttribute("fddContract");
        
		String appUrl =request.getHeader("host") + "/zjgj2tj";
		
		String contract_id = (String) request.getAttribute("template_id");	//合同编号 设置成和合同模板一样 用户编号 + 时间
		FDDContract contractES = this.fddContractDao.selectByContractID(contract_id);	//根据合同号获取合同对象
		
		int midES = contractES.getUserId();	//用户编号
		MemberAuthor maES = this.memberAuthorDao.selectByPrimaryKey(midES);
		
		String transaction_id = contractES.getTransactionId();
		String customer_id = maES.getCustomerId();
		//String contract_id = fddContract.getContractId();
		String doc_title = contractES.getDocTitle();
		log("调用手动签署接口：", "交易号："+transaction_id+" 客户编号："+customer_id+ " 合同编号："+contract_id + " 合同标题："+doc_title);
		//从配置文件获取数据
		String sign_keyword = "乙方盖章";//rp.getHTMLFilePath("signKeyword");受托方签字  电子签章位置
		String returnUrl = rp.getHTMLFilePath("returnUrl"); 
		log("法大大跳转地址：", returnUrl);
		String notifyUrl = "http://" + appUrl + rp.getHTMLFilePath("notifyUrl"); 
		log("法大大异步地址：", notifyUrl);
		
		String signUrl = clientBase.invokeExtSign(transaction_id, customer_id, contract_id, doc_title, sign_keyword, returnUrl, notifyUrl);
		log("调用签署接口：", signUrl);
		
		/*response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/plain; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(signUrl);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return signUrl;
	}
	
	/**24.调用归档接口 (未使用)*/
	@Override
	public void contractFilling(HttpServletRequest request) {
//		String contract_id = fddContract.getContractId();
//		System.out.println("归档接口合同编号：" + contract_id);
//		String response = clientBase.invokeContractFilling(contract_id);
//		log("调用归档接口：", response);
	}
	
	/**跳转地址*/
	@Override
	public String returnUrl(HttpServletRequest request) {
		
		System.out.println("法大大调用跳转地址");
		log("法大大调用跳转地址","");
		
		return "opeSuccess";
	}
	/**异步通知地址*/
	@Override
	@Transactional
	public String notifyUrl(HttpServletRequest request) {
		log("法大大调用异步通知地址","");
		
		//String queryString = request.getQueryString(); //获取法大大返回参数
		//log("法大大返回参数：",queryString);
		
		//获取url参数
		int result_code;
		String resultCode = request.getParameter("result_code");	//从法大大返回的url获取result_code
		String contract_id = request.getParameter("contract_id");	//从法大大返回的url获取contract_id合同id
		
		FDDContract fddContract = this.fddContractDao.selectByContractID(contract_id);	//根据合同编号获取合同对象
		
		int aid = fddContract.getAidgoldId();//(String) session.getAttribute("aidgoldId"); //从session获取助理金id
		//log("客户签署完毕，发大大调用异步通知从session中获取aidgoldId：", aidgoldId);
		//int aid = Integer.parseInt(aidgoldId);
		
		//修改助力金状态的数据
		Aidgold aidgold = new Aidgold();
		aidgold.setTid(aid);
		aidgold.setJkcheck((short)32); //默认设置客户签署合同失败
		
		if (resultCode != null && !"".equals(resultCode)) {
			result_code = Integer.parseInt(resultCode);
			if ( result_code == 3000 ) {	//3000:签章成功 3001:签章失败
				
				//log("归档接口合同编号：" , contract_id);
				//String response = clientBase.invokeContractFilling(contract_id);//合同归档接口
				//log("调用归档接口：", response);
				
				//获取预览，下载pdf地址
				String download_url = request.getParameter("download_url");
				log("下载合同地址为：" , download_url);
				String viewpdf_url = request.getParameter("viewpdf_url");
				log("预览合同地址为：" , viewpdf_url);
				
				//更新 预览，下载pdf地址 的数据
				fddContract = new FDDContract();
				fddContract.setAidgoldId(aid);
				fddContract.setDownloadUrl(download_url);
				fddContract.setViewpdfUrl(viewpdf_url);
				
				aidgold.setJkcheck((short)31);//从21(已付手续费)变31(客户已签署合同)
			}
			/*===== DAO =====*/
			this.aidgoldDao.updateByPrimaryKeySelective(aidgold);//更新助力金签署状态
			this.fddContractDao.updateByAidgoldIdSelective(fddContract);//更新下载，预览pdf 地址
			
			//插入日志表
			session = request.getSession();	//用来获取系统管理员用户名 插入日志表
			String content = "用户签署合同" + ",修改助力金的jkcheck为："+ aidgold.getJkcheck() + " 助力金编号："+aidgold.getTid();
			AidgoldLog aidgoldLog = LogTableInsert.insert("fdd_notify", "aidgold", content);
			this.aidgoldLogDao.insertSelective(aidgoldLog);
			
			log("修改助力金借款审核状态为：", aidgold.getJkcheck());
			log("用户签署:", "结束");
		}
		return null;
	}
	
	/**记录日志*/
	private void log(String msg, Object res){
		if (on) {
			log4j.info(msg + res.toString());
		}
	}
	
	/**2.调用个人CA注册接口-公司*/
	@Override
	public String syncPersonAutoCom(HttpServletRequest request, HttpServletResponse response) {
		
		log("公司签署:", "开始");
		
		String customer_name = this.paramDao.selectByPname("zjgjfr_name");	//"林柳菁";
		String email = "";
		String id_card = this.paramDao.selectByPname("zjgjfr_idno");	//"350103197510010126";
		String ident_type = "0";
		String mobile = this.paramDao.selectByPname("sign_mobile");	//13655032530
		
		session = request.getSession();	//创建session
		
		String customer_id = null; //返回的客户编号
		String result = null; //返回success/error
		
		String jsonStr = clientBase.invokeSyncPersonAuto(customer_name, email, id_card, ident_type, mobile);
		log("获取公司法人用户编号：", jsonStr);
		JSONObject jsonObject = new JSONObject().parseObject(jsonStr);
		
		result = jsonObject.getString("result");
		
		if ("success".equals(result)) {
			//获取客户编号
			customer_id = jsonObject.getString("customer_id");
			log("法大大生成公司法人客户编号：", customer_id);
			//session.setAttribute("customer_id_com", customer_id);
			request.setAttribute("customer_id_com", customer_id);
			
			return "forward:/fadada/ExtSignCom";	//调用模板传输接口
		} else {
			//注册出错，返回json
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(jsonStr);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**14.调用手动签署接口-公司*/
	@Override
	public String ExtSignCom(HttpServletRequest request, HttpServletResponse response) {
		
		String aidgold_id = request.getParameter("aidgold_id");	//助力金id
		session.setAttribute("aidgold_id", aidgold_id);
		
		String appUrl =request.getHeader("host") + "/zjgj2tj";
		
		//String customer_id = (String) session.getAttribute("customer_id_com");
		String customer_id = (String) request.getAttribute("customer_id_com");
		
		String transaction_id = new Date().getTime()+"";//request.getParameter("transaction_id");
		log("公司签署产生的交易号：", transaction_id);
		String contract_id = request.getParameter("contract_id");
		String doc_title = request.getParameter("doc_title");
		//从配置文件获取数据
		String sign_keyword = "";//rp.getHTMLFilePath("signKeyword");受托方签字  电子签章位置
		String returnUrl = "http://" + appUrl + "/fadada/returnUrl"; 
		log("法大大跳转地址-公司签署：", returnUrl);
		String notifyUrl = "http://" + appUrl + "/fadada/notifyUrlCom"; 
		log("法大大异步地址-公司签署：", notifyUrl);
		
		String signUrl = clientBase.invokeExtSign(transaction_id, customer_id, contract_id, doc_title, sign_keyword, returnUrl, notifyUrl);
		log("调用签署接口-公司签署：", signUrl);
		
		return signUrl;
	}
	
	/**公司签署异步通知地址*/
	@Override
	@Transactional
	public String notifyUrlCom(HttpServletRequest request) {
		log("法大大调用公司签署异步通知地址","");
		
		//String queryString = request.getQueryString(); //获取法大大返回参数
		//log("法大大返回参数：",queryString);
		//int aidgold_id = Integer.parseInt((String) session.getAttribute("aidgold_id")); //助力金id
		
		//获取url参数
		int result_code;
		String resultCode = request.getParameter("result_code");
		String contract_id = request.getParameter("contract_id");	//从法大大返回的url获取contract_id合同id
		
		FDDContract fddContract = this.fddContractDao.selectByContractID(contract_id);	//根据合同编号获取合同对象
		
		int aid = fddContract.getAidgoldId();//(String) session.getAttribute("aidgoldId"); //从session获取助理金id
		

		Aidgold agDB = this.aidgoldDao.selectByPrimaryKey(aid);
		//数据库里
		if (agDB != null && agDB.getJkcheck() >= 33) {
			log("公司已签署完，不可重复签署","助理金编号：" + aid);
			return null;
		}
		
		//修改助力金状态的数据
		Aidgold aidgold = new Aidgold();
		aidgold.setTid(aid);
		aidgold.setJkcheck((short)34); //默认设置公司签署合同失败
		
		if (resultCode != null && !"".equals(resultCode)) {
			result_code = Integer.parseInt(resultCode);
			if ( result_code == 3000 ) {	//3000:签章成功 3001:签章失败
				
				//获取预览，下载pdf地址
				String download_url = request.getParameter("download_url");
				log("下载合同地址为：" , download_url);
				String viewpdf_url = request.getParameter("viewpdf_url");
				log("预览合同地址为：" , viewpdf_url);
				
				log("归档接口合同编号：" , contract_id);
				String response = clientBase.invokeContractFilling(contract_id);//合同归档接口
				log("调用归档接口返回结果：", response);
				
				aidgold.setJkcheck((short)33);//33(公司签署合同成功)
				
				Aidgold aidgoldDB = this.aidgoldDao.selectByPrimaryKey(aid);	//获取数据库助理金对象
				int agentMid = aidgoldDB.getAgentid();	//获取助理金的代理商mid
				String email = this.aidgoldDao.selectAgentEmail(agentMid);	//把查询代理商邮箱的sql语句写到助力金表的映射
				log("获取代理商邮箱：", email + " 收款姓名：" + aidgoldDB.getSkname());
				//发送邮件
				//int aidgold_id = Integer.parseInt(idArray[0]);
				//获取助力金对象
				//Aidgold aginfo = this.aidgoldDao.selectByPrimaryKey(aid);
				String emailDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				String emailContent = "账号："+aidgoldDB.getSkbankno() + "<br>"
									+ "开户行：" +aidgoldDB.getSkbank() + "<br>"
									+ "收款姓名：" + aidgoldDB.getSkname() + "<br>"
									+ "金额：" + aidgoldDB.getJkmoney() + "元" + "<br>"
									+ "申请放款日期：" + emailDate;
				//公司签署成功发送邮件
				Map<String, String> emailMap = new HashMap<>();
				emailMap.put("fromName", "综金平台");	//发件人
				emailMap.put("toName", "助理金代理商");	//收件人
				emailMap.put("subject", emailDate + " " + aidgoldDB.getSkname() + " 申请放款");	//邮件主题
				emailMap.put("content", emailContent);	//邮件内容
				emailMap.put("agentEmail", email);	//代理商邮件
				
				log("邮件主题：", emailDate + " " + aidgoldDB.getSkname() + " 申请放款");
				log("邮件内容：", emailContent);
				
				SendEmail se = new SendEmail();
				String remark = aidgoldDB.getRemark();
				try {
					if(email != null && !email.equals("")){
						
						se.send(emailMap);	//发给代理商
						emailMap.remove("agentEmail");
						emailMap.put("agentEmail", "rcjrgj@163.com");	//公司邮箱
						se.send(emailMap);	//发回公司
						
						remark += "(已发邮件通知代理商)";
						
						Aidgold agData  = new Aidgold();
						agData.setTid(aid);
						agData.setRemark(remark);
						this.aidgoldDao.updateByPrimaryKeySelective(agData);	//更新备注信息
						log("发送邮件成功：", email);
					} else {
						log("发送邮件失败：", "未找到代理商Email");
					}
				} catch (Exception e1) {
					/*if (!remark.contains("(发送邮件异常)")) {
						remark += "(发送邮件异常)";
					}*/
					log("发送邮件异常：", email + " " + e1.getMessage());
					e1.printStackTrace();
				}
				
				
			}
			
			this.aidgoldDao.updateByPrimaryKeySelective(aidgold);//更新助力金签署状态
			
			
			//插入日志表
			session = request.getSession();	//用来获取系统管理员用户名 插入日志表
			String mid = (String)session.getAttribute("systemManagerUserName");
			String content = "公司签署合同管理员："+mid + ",修改助力金的jkcheck为："+ aidgold.getJkcheck() + " 助力金编号："+aidgold.getTid();
			AidgoldLog aidgoldLog = LogTableInsert.insert(mid, "aidgold", content);
			this.aidgoldLogDao.insertSelective(aidgoldLog);
			
			log("修改助力金借款审核状态为：", aidgold.getJkcheck());
			
			log("公司签署:", "结束");
		}
		return null;
	}
	
	/**根据mid查询 member_addr表
	 * @return */
	@Override
	public JSON selectMemberAddr(HttpServletRequest request) {
		String sid = request.getParameter("mid");
		int mid = 0;
		if (sid != null && !sid.equals("")) {
			mid = Integer.parseInt(sid);
		}
		MemberAddr maDB = this.memberAddrDao.selectByMidOnly(mid);
		
		if (maDB != null) {
			return (JSON) JSON.toJSON(maDB);
		}
		return null;
	}
	
	
}
