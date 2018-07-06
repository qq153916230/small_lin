package com.app.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.dao.ArticleDao;
import com.app.entity.Article;
import com.app.service.ArticleService;
import com.app.util.DataGrid;
import com.app.util.ReadHTMLFilePath;


@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	ArticleDao articleDao;
 
	//插入文章
	@Transactional
	@Override
	public void insertArticle(HttpServletRequest request) {
		
		String programa = (String) request.getAttribute("programa");
		String charge = (String)request.getAttribute("charge");
		String pvalue = (String) request.getAttribute("pvalue");
		String content = (String) request.getAttribute("content");
		
		//content = content.replaceAll("style=&quot;white-space:&nbsp;nowrap;&quot;", ""); //替换style="white-space: nowrap;"
		//content = content.replaceAll("&lt;br/&gt;", ""); // 替换<br/>
		
		String htmlPath = ReadHtmlFilePath("htmlFilePath"); //获取配置文件里面的文件路径
		
		//文章内容
		//创建文件夹
		File dir = new File(htmlPath);  // "D:\\shangSchool\\files"
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = new Date().getTime() + ".html"; //文件名为 栏目名+时间戳 pvalue + "_" + 
		File file = new File(dir, fileName); //创建html文件
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
			if (!"".equals(content) &&  content != null) {
				osw.write(content);
			}
			osw.flush(); 
			
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		content = fileName;
		
		Article article = new Article();
		
		//给article对象设值
		if (charge != null && charge != "" && !charge.equals("")) {
			charge = (String)request.getAttribute("charge");
			article.setCharge(new BigDecimal(charge));
		} else {
			charge = "0";
			article.setCharge(new BigDecimal(charge));
		}
		article.setTitle((String) request.getAttribute("title"));
		article.setAuthor((String) request.getAttribute("author"));
		article.setPrograma(programa);
		/*article.setArea((String) request.getAttribute("area"));*/
		article.setSummary((String) request.getAttribute("summary"));
		/*article.setAudio((String) request.getAttribute("audio"));
		article.setVideo((String) request.getAttribute("video"));*/
		article.setImage((String) request.getAttribute("image"));
		article.setPvalue(pvalue);
		article.setContent(content);
		
		this.articleDao.insertSelective(article); //插入文章
		
	}
	
	//连接查询
	@Override
	public List<Article> selectList() {
		return this.articleDao.selectList();
	}

	@Override
	public Article selectArticleByID(int aid) {
		
		Article article = this.articleDao.selectDetail(aid);
		String fileName = article.getContent();
		
		String htmlPath = ReadHtmlFilePath("htmlFilePath"); //获取配置文件里面的文件路径
		File file = new File(htmlPath + fileName); //"D:\\shangSchool\\files\\"
		
		String content = "";
		try {
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
				BufferedReader br = new BufferedReader(isr);   
				String line = null;   
				while ((line = br.readLine()) != null) {   
					content += line;   
					//content += "\r\n"; // 补上换行符   
				} 
				br.close();
				isr.close();
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		article.setContent(content);
		return article;
	}
	
	/*修改*/
	@Transactional
	@Override
	public void updateArticle(Article article, HttpServletRequest request) {
		
		String content = article.getContent();
		int aid = article.getAid();
		
		Article art = this.articleDao.selectByPrimaryKey(aid);
		String fileName = art.getContent();
		
		String htmlPath = ReadHtmlFilePath("htmlFilePath"); //获取配置文件里面的文件路径
		
		File dir = new File(htmlPath); //"D:\\shangSchool\\files"
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(dir, fileName); //创建html文件
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
			if (!"".equals(content) && content != null) {
				osw.write(content);
			}
			osw.flush(); 
			
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		article.setContent(fileName);
		
		this.articleDao.updateByPrimaryKeySelective(article); //修改文章
		
	}
	
	//删除
	@Override
	@Transactional
	public void deleteByID(int aid, HttpServletRequest request) {
		//根据aid获取文章
		Article article = this.articleDao.selectByPrimaryKey(aid);
		String pvalue = article.getPvalue();
		BigDecimal charge = article.getCharge();
		//获取要删除的文件名
		String contFile = article.getContent(); //内容名
		//String vidFile = article.getVideo(); //视频名
		//String audFile = article.getAudio(); //音频名
		String imaFile = article.getImage(); //图片名
		
		this.articleDao.deleteByPrimaryKey(aid);
		
		String htmlPath = ReadHtmlFilePath("htmlFilePath"); //获取配置文件里面的文件路径
		
		File dir = new File(htmlPath); //"D:\\shangSchool\\files"
		
		String url = request.getSession().getServletContext().getRealPath("");
		File dir2 = new File(url + "uploadFile");
		//创建对应的文件对象
		File cfile = new File(dir, contFile);
		//如果存在，则删除
		/*if (vidFile != null) {
			File vfile = new File(dir2, vidFile); 
			if(vfile.exists()){
				vfile.delete();}
			}*/
		/*if (audFile != null) { 
			File afile = new File(dir2, audFile); 
			if(afile.exists()){
				afile.delete();}
			}*/
		if (imaFile != null) { 
			File ifile = new File(dir2, imaFile); 
			if(ifile.exists()){
				ifile.delete();}
			}
		if(cfile.exists()){cfile.delete();}
		
	}
	
	//app请求article
	@Override
	public JSON appSelectArticle(HttpServletRequest request) {
		String pvalue = request.getParameter("pvalue");
		List<Article> articles = this.articleDao.selectByPvalue(pvalue);
		JSON json = (JSON) JSON.toJSON(articles);
		return json;
	}

	//分页 查询 文章列表
	@Override
	public DataGrid selectArticles(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String title = request.getParameter("title");
		String pvalue = request.getParameter("pvalue");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		//String programa = "%" + request.getParameter("programa") + "%";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("from", regTimeFrom);
		map.put("to", regTimeTo);
		map.put("title", "%" + title + "%");
		map.put("pvalue", pvalue);
//		map.put("programa", programa);
		
		int pag = (Integer.parseInt(page)-1)*100;
		map.put("page", pag);
		map.put("rows", Integer.parseInt(rows));
		
		DataGrid dg = new DataGrid();
		dg.setTotal((long) this.articleDao.selectAll().size());
		dg.setRows(this.articleDao.selectArticles(map));
		return dg;
	}

	//读取html文件路径的方法
	public String ReadHtmlFilePath(String htmlPath){
		
		ReadHTMLFilePath readHFP = new ReadHTMLFilePath(); //读取配置文件的工具类
		String hPath = readHFP.getHTMLFilePath(htmlPath);
		/*System.out.println("获取到的文件路径：" + hPath);*/
		
		return hPath;
	}
	
	//app获取文章的title 和  aid
	@Override
	public JSON appSelectTitle(HttpServletRequest request) {
		int type = Integer.parseInt(request.getParameter("type"));
		List<Map<String, Object>> titles = this.articleDao.appSelectTitle(type);
		JSON json = (JSON) JSON.toJSON(titles);
		return json;
	}
	
	//app根据aid获取文章
	@Override
	public JSON appSelectByAid(HttpServletRequest request) {
		//根据aid获取文章对象
		int aid = Integer.parseInt(request.getParameter("aid"));
		Article article = this.articleDao.appSelectByAid(aid);
		//由工具类获取文章内容所在本地的文件夹
		ReadHTMLFilePath readPath = new ReadHTMLFilePath();
		String path = readPath.getHTMLFilePath("htmlFilePath");
		//获取文章的文件对象
		File file = new File(path + article.getContent());
		
		String content = "";//用来保存文件内容
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			BufferedReader br = new BufferedReader(isr);   
			String line = null;   
			while ((line = br.readLine()) != null) {   
				content += line;   
				//content += "\r\n"; // 补上换行符   
			  } 
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		article.setContent(content);//把内容由文件名替换为文件内容
		
		JSON json = (JSON) JSON.toJSON(article);
		return json;
	}

}
