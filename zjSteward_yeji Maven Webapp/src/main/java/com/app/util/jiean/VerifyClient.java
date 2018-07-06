package com.app.util.jiean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;



/**
 * 验证接口对接客户端演示类
 * @author fajor.fan
 */
public class VerifyClient {
	
	/**
	 * 商户MAC_KEY(私钥)值
	 * 	商户开户时，由本平台进行提供，生产对接中，请商户替换为自己的值
	 */
	private static final String DEFAUL_TEST_MER_MAC_KEY = "238d357862bd5db1288283571a85a32c";//测试
	
	private static final String URL = "http://api.jieandata.com/vpre/ccmn/verify";//
	
	/**
	 * 接口入口
	 * @param args
	 */
	public JSON  jieanVerify(Map<String, String> param) {
	//public static void main(String[] args) {
	
		DefaultHttpClient defaultHttpClient = null;
		try{
			VerifyParam verifyParam = new VerifyParam();
			//verifyParam.setCERT_NAME("林峰");
			//verifyParam.setCERT_ID("3501234567857".trim());
			//涉法涉诉统计
			//verifyParam.setPROD_ID("SFSUTJ");
			
			//在网时长
			//verifyParam.setMP("18612345203");
			//verifyParam.setPROD_ID("MPTIME");
			
			//三要素认证
			//verifyParam.setMP("18612345203");
			//verifyParam.setPROD_ID("MP3");
				
			//四要素
			//verifyParam.setCARD_ID("6222370209898234");
			//verifyParam.setPROD_ID("CARD4");
			String proType = param.get("type");
			
			if ("SFSUTJ".equals(proType)) {//涉法涉诉统计
				verifyParam.setCERT_NAME(param.get("uname"));
				verifyParam.setCERT_ID(param.get("idno").trim());
				verifyParam.setPROD_ID("SFSUTJ");
			} else if ("MPTIME".equals(proType)) { //在网时长
				verifyParam.setMP(param.get("mobile"));
				verifyParam.setPROD_ID("MPTIME");
			} else if ("MP3".equals(proType)){
				verifyParam.setCERT_NAME(param.get("uname"));
				verifyParam.setCERT_ID(param.get("idno").trim());
				verifyParam.setMP(param.get("mobile"));
				verifyParam.setPROD_ID("MP3");
			}
			
			VerifyRequest verifyRequest = new VerifyRequest();
			verifyRequest.setVersionId("01");
			verifyRequest.setChrSet("UTF-8");
			verifyRequest.setCustId("6000001343"); //商户号
			String orderid = "LCZJGJ" + VerifyClientUtil.getCurrentDate() + VerifyClientUtil.getCurrentTime(); //龙川综金管家+时间
			//System.out.println(orderid);
			verifyRequest.setOrdId(orderid);
			if ("MP3".equals(proType)) {
				verifyRequest.setTransType("STD_VERI");	//三要素
			} else {
				verifyRequest.setTransType("REPORT");	// 在网时长/涉法涉诉
			}
			verifyRequest.setBusiType("");
			verifyRequest.setMerPriv("");
			verifyRequest.setRetUrl("");
			verifyRequest.setJsonStr(JSONObject.fromObject(verifyParam).toString());
			verifyRequest.setMacStr(VerifyClientUtil.getMacStr(verifyRequest, DEFAUL_TEST_MER_MAC_KEY));
			
			HttpPost httpPost = new HttpPost(URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("versionId", verifyRequest.getVersionId()));
			nvps.add(new BasicNameValuePair("chrSet", verifyRequest.getChrSet()));
			nvps.add(new BasicNameValuePair("custId", verifyRequest.getCustId()));
			nvps.add(new BasicNameValuePair("ordId", verifyRequest.getOrdId()));
			nvps.add(new BasicNameValuePair("transType", verifyRequest.getTransType()));
			nvps.add(new BasicNameValuePair("busiType", verifyRequest.getBusiType()));
			nvps.add(new BasicNameValuePair("merPriv", verifyRequest.getMerPriv()));
			nvps.add(new BasicNameValuePair("retUrl", verifyRequest.getRetUrl()));
			nvps.add(new BasicNameValuePair("jsonStr", verifyRequest.getJsonStr()));
			nvps.add(new BasicNameValuePair("macStr", verifyRequest.getMacStr()));
			//System.out.println(nvps.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			
			//System.out.println(httpPost.getEntity());
			defaultHttpClient = new DefaultHttpClient();
			HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
			//System.out.println(nvps);
			StringBuilder responseXmlText = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent()),"utf-8"));
			String output = null;
			while ((output = br.readLine()) != null) {
				responseXmlText.append(output);
			}
			//System.out.println(responseXmlText.toString());
			
			JAXBContext jaxbContent = JAXBContext.newInstance(VerifyResponse.class);
			Unmarshaller unmarshaller = jaxbContent.createUnmarshaller();
			StringReader stringReader = new StringReader(responseXmlText.toString());
			VerifyResponse verifyResponse = (VerifyResponse)unmarshaller.unmarshal(stringReader);
			
			JSON json = null;
			
//			if ("MP3".equals(proType)) {
//				json = (JSON) JSON.toJSON(verifyResponse);
//				return json;
//			}
			
			//String jsonStr = verifyResponse.getJsonStr();
			//System.out.println("JsonStr=" + verifyResponse.getJsonStr());
			//System.out.println("respCode=" + verifyResponse.getRespCode());
			//System.out.println("respDesc=" + verifyResponse.getRespDesc());
			
			//JSON json = (JSON) JSON.toJSON(jsonStr);
			//json = (JSON) JSON.parse(jsonStr);
			json = (JSON) JSON.toJSON(verifyResponse);
			
			defaultHttpClient.getConnectionManager().shutdown();
			return json;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
