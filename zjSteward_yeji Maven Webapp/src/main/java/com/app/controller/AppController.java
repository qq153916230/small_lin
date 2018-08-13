package com.app.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.CommonService;
import com.app.util.zjgj.ZjUrl;
import com.app.util.zjgj.webSocket.WsPool;

@Controller
@RequestMapping(value="/app/*",method={RequestMethod.POST,RequestMethod.GET})
public class AppController {
	
	@Autowired
	CommonService commonService;
	
	/** 登入系统  */
	@ResponseBody
	@RequestMapping(value="login")
	public JSON login(){
		return this.commonService.login();
	}
	
	/** 获取信用卡卡列表  */
	@ResponseBody
	@RequestMapping(value="selectCardList")
	public JSON selectCardList(HttpServletRequest req){
		return this.commonService.selectCardList(req);
	}
	
	/** 记录登入设备信息 */	//http://localhost:8080/zjgj2tj/app/insertLoginMobile?mid=1548&mobile_sys=android&mobile_type=meizu&mobile_no=99999999&login_area=福州
	@ResponseBody
	@RequestMapping(value="insertLoginMobile")
	public JSON insertLoginMobile(HttpServletRequest req){
		return this.commonService.insertLoginMobile(req);
	}
	
	/** 获取app登入信息 */	//http://localhost:8080/zjgj2tj/app/selectLoginMobileByMid?mid=1548
	@ResponseBody
	@RequestMapping(value="selectLoginMobileByMid")
	public JSON selectLoginMobileByMid(HttpServletRequest req){
		return this.commonService.selectLoginMobileByMid(req);
	}
	
	/** 查询版本号 */	//http://localhost:8080/zjgj2tj/app/selectAppVersionBySystem?system=ios
	@ResponseBody
	@RequestMapping(value="selectAppVersionBySystem")
	public JSON selectAppVersionBySystem(HttpServletRequest req){
		return this.commonService.selectAppVersionBySystem(req);
	}
	
	/** 向页面返回服务器时间 */
	@ResponseBody
	@RequestMapping(value="getTime")
	public JSON getServerTimestamp(HttpServletResponse resp){
		return (JSON) JSON.parse("{\"status\":1,\"msg\":"+new Date().getTime()+"}");
	}
	
	/**修改密码*/
	@ResponseBody
	@RequestMapping(value="updatePass")
	public JSON updatePass(HttpServletRequest req,HttpServletResponse resp){
		return this.commonService.updatePass(req);
	}
	
	/** 查询信用信息 通过mid*/
	@ResponseBody
	@RequestMapping(value="selectCreditMsgByMid")
	public JSON selectCreditMsgByMid(){
		return this.commonService.selectCreditMsgByMid();
	}
	
	/** 更新信用信息 通过mid*/
	@ResponseBody
	@RequestMapping(value="update_credit_msg")
	public JSON update_credit_msg(){
		return this.commonService.update_credit_msg();
	}

	/** ↓↓↓↓↓↓↓↓↓↓↓ 新颜 ↓↓↓↓↓↓↓↓↓  */
	
	/**查询通讯爬虫结果*/
	@ResponseBody
	@RequestMapping(value="selectSpiderMobile")
	public JSON selectSpiderMobile(HttpServletRequest req){
		return this.commonService.selectSpiderMobile(req);
	}
	
	/** 查询必填个人信息(通讯录, 邮箱账单) */
	@ResponseBody
	@RequestMapping(value="selectAidgoldMustFill")
	public JSON selectAidgoldMustFill(HttpServletRequest req){
		return this.commonService.selectAidgoldMustFill(req);
	}
	
	/**查询通讯爬虫结果 通过mid*/
	@ResponseBody
	@RequestMapping(value="selectSpiderMobileByMid")
	public JSON selectSpiderMobileByMid(HttpServletRequest req){
		return this.commonService.selectSpiderMobileByMid(req);
	}
	
	/** 根据手机查询通讯爬虫订单号 */
	@ResponseBody
	@RequestMapping(value="selectTradeNoByMobile")
	public JSON selectTradeNoByMobile(HttpServletRequest req){
		return this.commonService.selectTradeNoByMobile(req);
	}
	
	
	
	/** 插入邮箱账号 */
	@ResponseBody
	@RequestMapping(value="insertSpiderEmailAccount")
	public JSON insertSpiderEmailAccount(HttpServletRequest req){
		return this.commonService.insertSpiderEmailAccount(req);
	}
	
	/** 查询邮箱 */
	@ResponseBody
	@RequestMapping(value="selectSpiderEmail")
	public JSON selectSpiderEmail(HttpServletRequest req){
		return this.commonService.selectSpiderEmail(req);
	}
	
	/** 查询个人信息 */
	@ResponseBody
	@RequestMapping(value="selectSpiderPerson")
	public JSON selectSpiderPerson(HttpServletRequest req){
		return this.commonService.selectSpiderPerson(req);
	}
	
	/** 更新 t_xy_spider_person */ //http://mobile.rczjgj.com:7897/zjgj2tj/app/updateSpiderPersonByColumnName?mid=0&columnName=is_used&value=3&txn_type=alipay
	@ResponseBody
	@RequestMapping(value="updateSpiderPersonByColumnName")
	public JSON updateSpiderPersonByColumnName(){
		return this.commonService.updateSpiderPersonByColumnName();
	}
	
	/** 获取 t_xy_spider_person */	// http://mobile.rczjgj.com:7897/zjgj2tj/app/selectSpiderPersonMsgByMid?mid=0
	@ResponseBody
	@RequestMapping(value="selectSpiderPersonMsgByMid")
	public JSON selectSpiderPersonMsgByMid(){
		return this.commonService.selectSpiderPersonMsgByMid();
	}
	
	/** ↑↑↑↑↑↑↑↑↑↑ 新颜 ↑↑↑↑↑↑↑↑↑↑↑ */
	

	/** 微信支付 */
	@ResponseBody
	@RequestMapping(value="wxpay")
	public JSON wxpay(HttpServletRequest req){
		return this.commonService.wxpay(req);
	}
	
	/** 微信支付异步通知 
	 * @throws IOException */
	@RequestMapping(value="wxpay_notify")
	public void wxpay_notify(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.getWriter().write(this.commonService.wxpay_notify(req)); 
	}
	
	/** app获取服务列表 */
	@ResponseBody
	@RequestMapping(value="memberCon")
	public JSON getMemberServerList(){
		return this.commonService.getMemberServerList();
	}
	
	/** 查看该卡是否是新卡 */
	@ResponseBody
	@RequestMapping(value="newCardCheck")
	public JSON newCardCheck(){
		return this.commonService.newCardCheck();
	}
	
	/** websocket 向用户发送 消息 */
	@ResponseBody
    @RequestMapping("ws/send/{mid}/{message}")
    public String sendWs(@PathVariable(value="message")String message,
    									@PathVariable(value="mid")String mid) {
		if ("0".equals(mid)) {
			WsPool.sendMessageToAll(message);			
		} else {
			WsPool.sendMessageToUser(WsPool.getWsByUser(mid), message);
		}
        return message;
    }
	
	/** 获取新颜邮箱账单 */
	@ResponseBody
	@RequestMapping("xy/email/card/bills")
	public JSON xyEmailCardBills(){
		return this.commonService.xyEmailCardBills();
	}
	
	/** 保存 已移除的卡 */
	@ResponseBody
	@RequestMapping("xy/email/card/trash/save")
	public JSON xyEmailCardTrashSave(){
		return this.commonService.xyEmailCardTrashSave();
	}
	
	/** 移除的卡列表 */
	@ResponseBody
	@RequestMapping("xy/email/card/trash/list")
	public JSON xyEmailCardTrashList(){
		return this.commonService.xyEmailCardTrashList();
	}
	
	/** 删除 已移除的卡 */
	@ResponseBody
	@RequestMapping("xy/email/card/trash/delete")
	public JSON xyEmailCardTrashDelete(){
		return this.commonService.xyEmailCardTrashDelete();
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//private final String SECURITY_
	
	/**  ================= 公积金/医社保 获取支持地区  ==================  */
	
	/** 获取社保支持地区信息 */
	@RequestMapping(value="arealist")
	public String arealist(HttpServletRequest req){
		//String url = "http://mobile.rczjgj.com:7897/xinYanAPI/spiderSecurity/SecurityAction?urlType=arealistUrl&txnType=security";
		return "forward:xy/common/arealist?txnType=security";
	}
	
	/** 获取公积金地区信息 */
	@RequestMapping(value="fundArealist")
	public String fundArealist(HttpServletRequest req){
		//String url = ZjUrl.XY_URL_AUTO + "/fund/FundAction?urlType=fundArealistUrl";
		//return "redirect:"+ZjUrl.XY_URL_AUTO + "/fund/FundAction?urlType=fundArealistUrl&txnType=fund";
		return "forward:xy/common/arealist?txnType=fund";
	}
	
	/** 新颜通用获取地区列表 */
	@RequestMapping(value="xy/common/arealist")
	public String xyCommonArealist(HttpServletRequest req){
		
		String txnType = req.getParameter("txnType");
		String url = ZjUrl.XY_URL_AUTO;
		switch (txnType) {
			case "fund":
				url += "/fund/FundAction?urlType=fundArealistUrl"; break;
			case "security":
				url += "/spiderSecurity/SecurityAction?urlType=arealistUrl"; break;
		}
		return "redirect:" + url;
	}
	
	
	
	/**  ================== 公积金/医社保 获取地区登入信息,登入所需字段 ===================  */
	
	/** 获取公积金登入信息 */
	@RequestMapping(value="fundLogin")
	public String fundLogin(HttpServletRequest req){
		return "redirect:"+ZjUrl.XY_URL_AUTO + "/fund/FundAction?urlType=fundLoginUrl&area_code="+req.getParameter("area_code");
	}

	/** 获取社保登入信息 */
	@RequestMapping(value="loginlist")
	public String loginlist(HttpServletRequest req){
		String url = "http://mobile.rczjgj.com:7897/xinYanAPI/spiderSecurity/SecurityAction?urlType=loginUrl&area_code="+req.getParameter("area_code");
		return "redirect:"+url;
	}
	
	/** 新颜通用 获取地区登入信息 */
	@RequestMapping(value="xy/common/loginlist")
	public String xyCommonLoginlist(HttpServletRequest req){
		
		String txnType = req.getParameter("txnType");
		String url = ZjUrl.XY_URL_AUTO;
		switch (txnType) {
			case "fund":
				url += "/fund/FundAction?urlType=fundLoginUrl&area_code="+req.getParameter("area_code"); break;
			case "security":
				url += "/spiderSecurity/SecurityAction?urlType=loginUrl&area_code="+req.getParameter("area_code"); break;
		}
		return "redirect:" + url;
	}
	
	/** ==================== 查询/获取 订单号 ================== */

	/** 访问新颜接口 获取新颜公积金订单号 */
	@ResponseBody
	@RequestMapping(value="createXinyanFundTradeNo")
	public JSON createXinyanFundTradeNo(HttpServletRequest req){
		return this.commonService.createXinyanFundTradeNo(req);
	}

	/** 访问新颜接口 获取新颜车险保单订单号 */
	@ResponseBody
	@RequestMapping(value="createXinyanInsuranceTradeNo")
	public JSON createXinyanInsuranceTradeNo(HttpServletRequest req){
		return this.commonService.createXinyanInsuranceTradeNo(req);
	}
	
	/** 访问新颜接口 获取新颜个人信息订单号 */
	@ResponseBody
	@RequestMapping(value="createXinyanTradeNo")
	public JSON createXinyanTradeNo(HttpServletRequest req){
		return this.commonService.createXinyanTradeNo(req);
	}
	
	/** 访问新颜接口 获取新颜医社保订单号 */
	@ResponseBody
	@RequestMapping(value="createXinyanSecurityTradeNo")
	public JSON createXinyanSecurityTradeNo(HttpServletRequest req){
		return this.commonService.createXinyanSecurityTradeNo(req);
	}
	
	/** 访问新颜接口 获取新颜个人信息订单号(通用) */
	@ResponseBody
	@RequestMapping(value="xy/common/create/trade_no")
	public JSON xyCommonCreateTradeNo(HttpServletRequest req){
		return this.commonService.xyCommonCreateTradeNo(req);
	}
	
	/** ==================== 验证码输入  ================== */

	/** 社保验证码输入 */
	@RequestMapping(value="securityInput")
	public String securityInput(HttpServletRequest req){
		String url = "http://mobile.rczjgj.com:7897/xinYanAPI/spiderSecurity/SecurityAction?urlType=taskinputUrl&tradeNo="
					+req.getParameter("tradeNo") + "&code="+req.getParameter("code");
		return "redirect:"+url;
	}
	
	/** 公积金验证码输入 */
	@RequestMapping(value="fundInput")
	public String fundInput(HttpServletRequest req){
		return "redirect:"+ZjUrl.XY_URL_AUTO + "/fund/FundAction?urlType=taskinputUrl&tradeNo="+
				req.getParameter("tradeNo") + "&code="+req.getParameter("code");
	}
	
	/** 车险保单验证码输入 */
	@RequestMapping(value="insuranceInput")
	public String insuranceInput(HttpServletRequest req){
		return "redirect:"+ZjUrl.XY_URL_AUTO + "/insurance/InsuranceAction?urlType=taskinputUrl&tradeNo="+
				req.getParameter("tradeNo") + "&code="+req.getParameter("code");
	}
	
	/** ==================== 订单状态查询  ================== */
	
	/** 查询公积金状态 */
	@RequestMapping(value="fundStatus")
	public String fundStatus(HttpServletRequest req){
		return "redirect:"+ZjUrl.XY_URL_AUTO + "/fund/FundAction?urlType=taskStatusUrl&tradeNo="+ 
				req.getParameter("tradeNo");
	}
	
	/** 查询车险保单状态 */
	@RequestMapping(value="insuranceStatus")
	public String insuranceStatus(HttpServletRequest req){
		return "redirect:"+ZjUrl.XY_URL_AUTO + "/insurance/InsuranceAction?urlType=taskStatusUrl&tradeNo="+ 
				req.getParameter("tradeNo");
	}
	
	/** 查询社保状态 */
	@RequestMapping(value="securityStatus")
	public String securityStatus(HttpServletRequest req){
		String url = "http://mobile.rczjgj.com:7897/xinYanAPI/spiderSecurity/SecurityAction?urlType=taskStatusUrl&tradeNo="+req.getParameter("tradeNo");
		return "redirect:"+url;
	}
	
	
	/** ========== 异步通知 ============  */
	
	/** 个人信息爬取异步通知 */
	@ResponseBody
	@RequestMapping(value="personMsgOrder")
	public void personMsgOrder(HttpServletRequest req){
		System.out.println("新颜 个人信息爬取异步通知");
		this.commonService.personMsgOrder(req);
	}
	
	/** 通讯录爬取异步通知 */
	@ResponseBody
	@RequestMapping(value="mobileMsgOrder")
	public void mobileMsgOrder(HttpServletRequest req){
		System.out.println("新颜 通讯录爬取异步通知");
		this.commonService.mobileMsgOrder(req);
	}
	
	/** 医社保异步通知 */
	@ResponseBody
	@RequestMapping(value="notifySecurity")
	public void notifySecurity(HttpServletRequest req){
		System.out.println("新颜 医社保异步通知");
		this.commonService.notifySecurity(req);
	}
	
	/** 公积金异步通知 */
	@ResponseBody
	@RequestMapping(value="notifyFund")
	public void notifyFund(HttpServletRequest req){
		System.out.println("新颜 公积金异步通知");
		this.commonService.notifyFund(req);
	}
	
	/** 车险保单异步通知 */
	@ResponseBody
	@RequestMapping(value="notifyInsurance")
	public void notifyInsurance(HttpServletRequest req){
		System.out.println("新颜 车险保单异步通知");
		this.commonService.notifyInsurance(req);
	}
	
	
	
}
