package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Article;

public interface ArticleDao {
	
	List<Article> selectArticle(Article article); //根据作者输入的内容查询
	
	List<Article> selectList(); //查询文章列表
	
	Article selectDetail(int aid);
	
	List<Article> selectAll(); //查询文章列表,没有内容

	List<Article> selectByPvalue(String pvalue); //根据pvalue查询

	List<Article> selectArticles(Map<String, Object> map); //分页 查询 文章列表

	List<Map<String, Object>> appSelectTitle(int type); //app获取文章的title 和  aid

	Article appSelectByAid(int aid); //app根据aid获取文章
	
	
	
    int deleteByPrimaryKey(Integer aid);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);




}