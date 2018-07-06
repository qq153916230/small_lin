package com.app.controller;

import java.io.InputStream;  
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;  
  
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.ResponseBody;  
import org.springframework.web.multipart.MultipartFile;  
import org.springframework.web.multipart.MultipartHttpServletRequest;  
  
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.service.TradedataService;
import com.app.util.DataGrid;
import com.app.util.ImportExcelUtil;  
import com.app.entity.Tradedata;
  
@Controller  
@RequestMapping("/uploadExcel/*")    
public class UploadExcelControl {  
	
	@Resource
	TradedataService tradedataService;
	
	@ResponseBody  
    @RequestMapping(value="selectAll.do",method={RequestMethod.GET,RequestMethod.POST})  
    public DataGrid selectAll() { 
		DataGrid dg = new DataGrid();
        List<Tradedata> tradedatas = this.tradedataService.selectInsert();
        dg.setTotal((long)tradedatas.size());
        dg.setRows(tradedatas);
        return dg;
    }
	
	//更新状态
		@ResponseBody
		@RequestMapping("updateStatus.do")
		public JSON updateStatus(HttpServletRequest request){
			//获取json传过来的数据
			String sidString = request.getParameter("sids");//sids是一个逗号分隔的字符串 1,2
			String[] sids = sidString.split(",");
			int status = Integer.parseInt(request.getParameter("newStatus"));
			
			//执行更新操作
			this.tradedataService.updateBySidSelective(sids,status);
			
			//向页面返回数据
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ret", 1);
			jsonObject.put("msg", "更新成功");
			return jsonObject;
		}
      
    /** 
     * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件 
     * @param request 
     * @param response 
     * @throws Exception 
     */  
    @ResponseBody  
    @RequestMapping(value="ajaxUpload.do",method={RequestMethod.GET,RequestMethod.POST})  
    public DataGrid ajaxUploadExcel(HttpServletRequest request) throws Exception { 
//    	System.out.println("通过 jquery.form.js 提供的ajax方式上传文件！");  
          
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        List<List<Object>> listob = null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在！");  
        }  
          
        in = file.getInputStream();  
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());  
        
        DataGrid dg = new DataGrid();//用于页面数据显示
        
        List<Tradedata> ups = new ArrayList<Tradedata>();
        
        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
        for (int i = 0; i < listob.size(); i++) {  
            List<Object> lo = listob.get(i);  
            Tradedata td = new Tradedata();
            td.setMerchantname(String.valueOf(lo.get(0)));
            td.setSkcard(String.valueOf(lo.get(1)));
            td.setPaymoney(new BigDecimal(String.valueOf(lo.get(2))));
            td.setPaytimes(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String)lo.get(3)));
            td.setResult(String.valueOf(lo.get(4)));
            td.setShrate(new BigDecimal(String.valueOf(lo.get(5))));
            td.setHandcharge(new BigDecimal(String.valueOf(lo.get(6))));
            td.setJsprice(new BigDecimal(String.valueOf(lo.get(7))));
            td.setAgent(String.valueOf(lo.get(8)));
            td.setSkbank(String.valueOf(lo.get(9)));
            td.setPosno(String.valueOf(lo.get(10)));
            td.setPossn(String.valueOf(lo.get(11)));
            td.setModo(String.valueOf(lo.get(12)));
            td.setTxrate(new BigDecimal(String.valueOf(lo.get(13))));
            td.setMerchantcode(this.tradedataService.selectMC(td.getMerchantname())); //查询获取商户号
            //如果商户号不存在则设置为 0 （数据库商户号not null）
            if (td.getMerchantcode() == null) {
				td.setMerchantcode((long)0);
			}
            ups.add(td);
        }  
        this.tradedataService.insertList(ups);
        List<Tradedata> tradedatas = this.tradedataService.selectInsert();
        dg.setTotal((long)tradedatas.size());
        dg.setRows(tradedatas);
          
//        PrintWriter out = null;  
//        response.setCharacterEncoding("utf-8");  //防止ajax接受到的中文信息乱码  
//        out = response.getWriter();  
//        out.print(dg);  
//        out.flush();  
//        out.close();  
        return dg;
    }  
    
    /**  
     * 描述：通过传统方式form表单提交方式导入excel文件  
     * @param request  
     * @throws Exception  
     */  
    @RequestMapping(value="upload.do",method={RequestMethod.GET,RequestMethod.POST})  
    public  String  uploadExcel(HttpServletRequest request) throws Exception {  
    	System.out.println("通过传统方式form表单提交方式导入excel文件！");  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        List<List<Object>> listob = null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在！");  
        }  
        in = file.getInputStream();  
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());  
        in.close();  
          
        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
        for (int i = 0; i < listob.size(); i++) {  
            List<Object> lo = listob.get(i);  
//            InfoVo vo = new InfoVo();  
//            vo.setCode(String.valueOf(lo.get(0)));  
//            vo.setName(String.valueOf(lo.get(1)));  
//            vo.setDate(String.valueOf(lo.get(2)));  
//            vo.setMoney(String.valueOf(lo.get(3)));  
//              
//            System.out.println("打印信息-->商户名称:"+vo.getCode()+"  交易金额："+vo.getName()+"   交易时间："+vo.getDate()+"   消费结果："+vo.getMoney());  
        }  
        return "result";  
    }  
  
  
} 