package com.app.util;

import javax.servlet.*;  
import javax.servlet.http.*;  

import java.io.*;  
import java.text.SimpleDateFormat;
import java.util.*;  

import org.apache.commons.fileupload.*;  
import org.apache.commons.fileupload.servlet.*;  
import org.apache.commons.fileupload.disk.*;  

/**
 * Servlet 文件上传  
 * 固定url
 */
public class UploadServletFixedUrl extends HttpServlet  
{  
    private String filePath;    // 文件存放目录  
    private String tempPath;    // 临时文件目录  
 
    // 初始化  
    public void init(ServletConfig config) throws ServletException  
    {  
        super.init(config);  
        // 从配置文件中获得初始化参数  
        filePath = config.getInitParameter("filepath");  
        tempPath = config.getInitParameter("temppath");  
       /* File fi;
        if (!(fi = new File(filePath)).exists()) {
			fi.mkdir();
		}
        if (!(fi = new File(tempPath)).exists()) {
			fi.mkdir();
		}*/
 
        ServletContext context = getServletContext();  
 
        filePath = context.getRealPath(filePath);  
        tempPath = context.getRealPath(tempPath);  
        /*System.out.println("文件存放目录、临时文件目录准备完毕 ...");  */
    }  
      
    // doPost  
    public void doPost(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
    	
    	/*req.setCharacterEncoding("utf-8");*/
        res.setContentType("text/plain;charset=utf-8"); 
        PrintWriter pw = res.getWriter();  
        try{  
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();  
            // threshold 极限、临界值，即硬盘缓存 1M  
            diskFactory.setSizeThreshold(4 * 1024);  
            // repository 贮藏室，即临时文件目录  
            diskFactory.setRepository(new File(tempPath));  
          
            ServletFileUpload upload = new ServletFileUpload(diskFactory);  
            // 设置允许上传的最大文件大小 1G  
            upload.setSizeMax(1 * 1024 * 1024 * 1024);  
            // 解析HTTP请求消息头  
            List fileItems = upload.parseRequest(req);  
            Iterator iter = fileItems.iterator();  
            while(iter.hasNext())  
            {  
                FileItem item = (FileItem)iter.next();  
                if(item.isFormField())  
                {  
                    /*System.out.println("处理表单内容 ...");  */
                    processFormField(item, pw, req);  
                }else{  
                    /*System.out.println("处理上传的文件 ..."); */ 
                    processUploadFile(item, pw, req);  
                }  
            }// end while()  
            //String furl = (String) req.getSession().getAttribute("furl");
            req.getRequestDispatcher("/fadada/syncPersonAuto").forward(req, res);
            pw.close();  
            
            
        }catch(Exception e){  
            System.out.println("使用 fileupload 包时发生异常 ...");  
            e.printStackTrace();  
        }// end try ... catch ...  
    }// end doPost()  
 
 
    // 处理表单内容  
    private void processFormField(FileItem item, PrintWriter pw, HttpServletRequest request)  
        throws Exception  
    {  
        String name = item.getFieldName();  
        String value = item.getString("UTF-8");
        /*System.out.println("name:" + value);*/
        request.setAttribute(name, value);
        /*pw.println(name + " : " + value + "\r\n");*/  
    }  
      
    // 处理上传的文件  
    private void processUploadFile(FileItem item, PrintWriter pw, HttpServletRequest request)  
        throws Exception  
    {  
    	Date date = new Date();
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	String time = df.format(date);
    	
    	String name = item.getFieldName();
        // 此时的文件名包含了完整的路径，得注意加工一下  
        String filename = item.getName();         
        /*System.out.println("完整的文件名：" + filename);  */
        int index = filename.lastIndexOf("\\");  
        long fileSize = item.getSize();  
 
        if("".equals(filename) && fileSize == 0)  
        {             
            System.out.println("文件名为空 ...");  
            return;  
        }  
        filename =  time + filename.substring(index + 1, filename.length()); //文件拼接时间戳，防止同名文件覆盖
        filename = filename.toLowerCase(); //把名字设置全小写
 
        File uploadFile = new File(filePath + "/" + filename);  
        item.write(uploadFile);  
        
		request.setAttribute(name, filename);
		
        /*pw.println(filename + " 文件保存完毕 ...");  
        pw.println("文件大小为 ：" + fileSize + "\r\n"); */ 
    }  
      
    // doGet  
    public void doGet(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        doPost(req, res);  
    }  
} 
