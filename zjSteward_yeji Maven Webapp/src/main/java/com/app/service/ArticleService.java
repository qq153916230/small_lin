package com.app.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.entity.Article;
import com.app.util.DataGrid;

public interface ArticleService {
	public void insertArticle(HttpServletRequest request); //插入文章内容
	
	public List<Article> selectList(); //链接查询

	public Article selectArticleByID(int aid); //通过id查询

	public void updateArticle(Article article, HttpServletRequest request);//修改

	public void deleteByID(int aid, HttpServletRequest request);//删除

	public JSON appSelectArticle(HttpServletRequest request); //app请求article

	public DataGrid selectArticles(HttpServletRequest request); //分页 查询 文章列表

	public JSON appSelectTitle(HttpServletRequest request); //app获取文章的title 和  aid

	public JSON appSelectByAid(HttpServletRequest request); //app根据aid获取文章

	
}
