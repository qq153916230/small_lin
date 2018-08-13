package com.app.util.zjgj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.util.DataGrid;

public class ZjUtils {
	
	//static String reqParam;		//请求参数
	//static DataGrid dg = new DataGrid();	//返回页面的数据
	//static Map<String, Object> map = new HashMap<String, Object>();	//查询的map
	
	static Logger log4j = Logger.getLogger("zjUtils");	//com.app.util.zjgj.ZjUtils
	
	public final static String REQUEST_LIMIT_MSG = "{\"status\":0,\"msg\":\"请求不被接受\"}";
	public final static String SYSTEM_ERROR = "{\"status\":555,\"msg\":\"系统异常,请联系管理员\"}";
	
	/** {"status":0, "msg":"拒绝请求"} */
	public final static JSON ILLEGAL_REQUEST = statusMsgJson(0, "拒绝请求");
	
	public static void log(String str){
		log4j.info(str);
	}
	
	/**
	 * @param str
	 * @return str != null && !"".equals(str)
	 */
	public static boolean notEmpty(String str){
		return str != null && !"".equals(str);
	}
	
	/** 返回空为 "" */
	public static String getStr(String param){
		String reqParam = getReqParam(getRequest(), param);
		return reqParam == null ? "" : reqParam;
	}
	
	/** null或"" 返回  0 */
	public static int getInt(String param){
		String reqParam = getReqParam(getRequest(), param);
		int result = 0;
		if ( reqParam != null && !"".equals(reqParam) && !"null".equals(reqParam) ) {
			result = Integer.parseInt(reqParam);
		}
		return result;
	}
	
	/** 获取 HttpServletRequest */
	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/** 返回空为 "" */
	public static String getStr(HttpServletRequest request, String param){
		String reqParam = getReqParam(request, param);
		return reqParam == null ? "" : reqParam;
	}
	
	/** null或"" 返回  0 */
	public static int getInt(HttpServletRequest request, String param){
		String reqParam = getReqParam(request, param);
		int result = 0;
		if ( reqParam != null && !"".equals(reqParam) ) {
			result = Integer.parseInt(reqParam);
		}
		return result;
	}
	
	public static short getShort(HttpServletRequest request, String param){
		String reqParam = getReqParam(request, param);
		short result = 0;
		if ( reqParam != null && !"".equals(reqParam) ) {
			result = Short.parseShort(reqParam);
		}
		return result;
	}
	/** 返回空为null*/
	public static String getReqParam(HttpServletRequest request, String param){
		return request.getParameter(param);
	}
	
	public static String empty2null(String str){
		return "".equals(str) ? null : str;
	}
	
	public static Object getObj(Class<?> clazz){
		
		Object object = null;
		try {
			if (clazz != null) {
				object = clazz.newInstance();
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	/** @return yyyy-MM-dd / yyyy-MM-dd HH:mm:ss(实时时间) */
	public static String getDateStr(String dateType){
		return getDateStr(null, dateType);
	}
	
	/** @return yyyy-MM-dd / yyyy-MM-dd HH:mm:ss(指定时间) */
	public static String getDateStr(Date date, String dateType){
		SimpleDateFormat sdf = getDateFormat(dateType);
		String result = null; 
		if (date == null) {
			result = sdf.format(new Date());
		} else {
			result = sdf.format(date);
		}
		return result;
	}
	
	/** @param dateType yyyy-MM-dd / yyyy-MM-dd HH:mm:ss  
	 * @throws ParseException */
	public static Date getDate(String dateStr, String dateType) throws ParseException{
		return getDateFormat(dateType).parse(dateStr);
	}
	
	/** @param type yyyy-MM-dd / yyyy-MM-dd HH:mm:ss  */
	public static SimpleDateFormat getDateFormat(String type){
		return new SimpleDateFormat(type);
	}
	
	/** 向页面返回json
	 *  @param status 返回状态
	 *  @param msg 返回消息
	 */
	public static JSON statusMsgJson(int status, String msg){
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("msg", msg);
		return json;
	}
	public static JSON statusMsgJsonParseStr(Integer status, String str){
		return statusMsgJsonObj(status, JSON.parse(str));
	}
	public static JSON statusMsgJsonParseObj(Integer status, Object obj){
		return statusMsgJsonObj(status, JSON.toJSON(obj));
	}
	public static JSON statusMsgJsonObj(Object status, Object msg){
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("msg", msg);
		return json;
	}
	public static JSON json_xy_defaut(boolean success, Object data, String errorCode, String errorMsg){
		JSONObject json = new JSONObject();
		json.put("success", success);
		json.put("data", data);
		json.put("errorCode", errorCode);
		json.put("errorMsg", errorMsg);
		return json;
	}
	public static JSON statusMsg_xy(boolean success, String errorMsg){
		return json_xy_defaut(success, null, "555", errorMsg);
	}
	/**jsonp数据*/
	public static String JsonpData(String callbackFunName,Object status, Object msg){
		return callbackFunName + "([{\"status\":" + status + "," + "\"msg\":\"" + msg + "\"}])";
	}
	public static String JsonpDataObj(String callbackFunName, Object obj){
		return callbackFunName + "(" + obj + ")";
	}
	
	/**
	 * list > 0 返回 1  否则返回 0
	 * @return list.size() > 0 ? 1 : 0
	 */
	public static int getStatus(List list){
		if (list != null) {
			return list.size() > 0 ? 1 : 0;
		}
		return 0;
	}
	
	
	
	public static String getLastSecond(String time, boolean isNull){
		if (isNull == true) {
			return "".equals(time) ? null : time + " 23:59:59";
		} else {
			return "".equals(time) || time == null ? "" : time + " 23:59:59"; 
		}
	}
	
	public static int getPage(int page){
		return (page-1)*100;
	}
	
	public static DataGrid getDG(){
		return new DataGrid();
	}
	
	public static Map<String, Object> getHashMap(){
		return new HashMap<String, Object>();
	}
	
	
	/**
	 * @throws IOException 
	 * */
	public static void uploadFileByBase64Str(String contextPath,String base64Str,String fileName) throws IOException{
		//获取请求参数
		base64Str = base64Str.split(",")[1];
		String filePath = contextPath + "/" + fileName;	//上传图片保存在服务器的位置
		
		File dir = new File(contextPath);	//保存图片的文件夹路径
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(filePath);	//图片路径
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
	}
	
	/** 读取http body的内容 */
	public static String readHttpBody(){
		
		String body = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getRequest().getInputStream(),"utf-8"));
			
			String line;
			while((line = reader.readLine())!= null){  
				body += line;  
				body += "\n";  
            }  
            if (reader != null) {
            	reader.close();
			}
			
			log("body:" + body);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		return body;
	}
	
	/**获取服务器 项目地址 http:// */
	public static String getContextPath(HttpServletRequest request){
		String path = "http://" + request.getServerName() //服务器地址  
                + ":" + request.getServerPort()           //端口号  
                + request.getContextPath() + "/";      //项目名称
		return path;
	}
	
	/**获取服务器 项目工程目录 E:\tomcat\tomcatZJ2Manager\bin E:\\tomcat\\tomcatZJ2Manager\\webapps\\zjgj2tj\\ */
	public static String getContextRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("");
	}
	
	/** 获取ip地址 */
	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip != null && !"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(ip != null && !"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	/** 获取服务器端口号 */
	public static int getPort(HttpServletRequest req){
		return req.getServerPort();
	}
	
	/**
	 * 获取列表 的 分页
	 * @param page
	 * @return	page > 0 ? (page - 1) * 100 : 0;
	 */
	public static int getPages(String pages){
		int page = getInt(pages);
		return page > 0 ? (page - 1) * 100 : 0;
	}
	
	public static String encode(String str){
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return str != null && !"".equals(str)
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && !"".equals(str)) ;
	}
	/**
	 * @return str == null || "".equals(str)
	 */
	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}
	
	/** 把空对象转成 "" 
	 * return obj == null || "".equals((String)obj) ? " " : obj;
	 */
	public static Object dealNullExcel(Object obj){
		return obj == null || "".equals(String.valueOf(obj)) ? " " : obj;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		Calendar calendar = Calendar.getInstance();
		//calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-30);
		System.out.println(calendar.DATE + " " + calendar.get(Calendar.DATE));
	}
}

