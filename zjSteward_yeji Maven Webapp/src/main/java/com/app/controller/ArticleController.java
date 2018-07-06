package com.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.entity.Article;
import com.app.service.ArticleService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/article/*")
public class ArticleController {
	@Resource
	ArticleService articleService;
	
	@RequestMapping("insertArticle")
	public String insertArticle(HttpServletRequest request, Model model){
		this.articleService.insertArticle(request);
		
		/*List<Article> articles = this.articleService.selectList();
		model.addAttribute("articles", articles);*/
		return "opeSuccess";
	}
	
	//分页 查询 文章列表
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.articleService.selectArticles(request);
	}
	
	//文章详细
	@RequestMapping("articleDetail")
	public String articleDetail(int aid, Model model){
		Article article = this.articleService.selectArticleByID(aid);
		model.addAttribute("article",article);
		return "articleDetail";
	}
	
	//修改之前把数据显示
	@RequestMapping("articleUpdate")
	public String articleUpdate(int aid, Model model){
		//查询文章内容
		Article article = this.articleService.selectArticleByID(aid);
		model.addAttribute("article",article);
		/*//查询param表里面的栏目
		List<Param> params = this.paramService.selectByPgroup(102);
		model.addAttribute("params", params);*/
		return "articleUpdate";
	}
	//修改
	@RequestMapping("updateArticle")
	public String updateArticle(Article article, HttpServletRequest request){
		this.articleService.updateArticle(article, request);
		
		/*List<Article> articles = this.articleService.selectList();
		model.addAttribute("articles", articles);*/
		return "opeSuccess";
	}
	
	//删除
	@RequestMapping("deleteByID")
	public String deleteByID(int aid,Model model,HttpServletRequest request){
		this.articleService.deleteByID(aid, request);
		/*List<Article> articles = this.articleService.selectList();
		model.addAttribute("articles", articles);*/
		return "opeSuccess";
	}
	
	//app获取文章的title 和  aid
	@ResponseBody
	@RequestMapping(value="appSelectTitle",produces="charset=utf-8")
	public JSON appSelectTitle(HttpServletRequest request){
		return this.articleService.appSelectTitle(request);
	}
	
	//app根据aid获取文章
	@ResponseBody
	@RequestMapping("appSelectByAid")
	public JSON appSelectByAid(HttpServletRequest request){
		return this.articleService.appSelectByAid(request);
	}
	
	/*//获取param表的102栏目一级栏目
	@RequestMapping("selectParam")
	public String selectParam(Model model){
		List<Param> params = this.paramService.selectByPgroup(102);
		model.addAttribute("params", params);
		return "insertArticle";
	}
	//获取param表的102栏目的二级栏目
	@ResponseBody
	@RequestMapping("selectParamValue")
	public JSON selectParamValue(HttpServletRequest request){
		JSON json = this.paramService.selectByGV(request);
		return json;
	}
	//获取三级栏目（系列）
	@ResponseBody
	@RequestMapping("selectSeriesByPval")
	public JSON selectSeriesByPval(HttpServletRequest request){
		JSON json = this.paramService.selectSeriesByPval(request);
		return json;
	}*/
	
	/*app请求article*/
	/*@ResponseBody
	@RequestMapping("appArticleList")
	public JSON appArticleList(HttpServletRequest request){
		JSON json = this.articleService.appSelectArticle(request);
		System.out.println(json);
		return json;
	}*/
	/*app请求article详细内容*/
	/*@ResponseBody
	@RequestMapping("appArticleDetai")
	public JSON appArticleDetai(int aid){
		Article article = this.articleService.selectArticleByID(aid);
		JSON json = (JSON) JSON.toJSON(article);
		return json;
	}*/
	
	/*//订单审核结果访问地址
	@ResponseBody
	@RequestMapping("appPay")
	public void appPay(HttpServletRequest request){
		System.out.println("付款审核结果处理");
		this.orderService.operateOrder(request);
		return;
	}*/
	
	
}
