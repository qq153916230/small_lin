<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>show</title>

  </head>
  
  <body>
  	<a class="up" href="<%=path %>/article/articleUpdate?aid=<c:out value="${article.aid }"></c:out>" 
  		style="margin: 0 15px 10px 0;display: block;text-decoration: none;float:left;">修改内容</a>
  	<a class="del" href="javascript:;" onclick="del()" style="text-decoration: none;color: red;">删除</a>
  	
		<div style="margin-top: 10px;">
			<span>标题：<c:out value="${article.title }"/></span>
			<span>作者：<c:out value="${article.author }"/></span>
			<span>栏目：<c:out value="${article.programa }"/></span>
			<span>地区：<c:out value="${article.area }"/></span>
			<span>置顶：<c:out value="${article.charge }"/></span>
			<div style="margin: 5px 0;">简介：<c:out value="${article.summary }"/></div>
			<%-- <div>音频：<audio controls="controls" src="uploadFile/<c:out value="${article.audio }"/>"></audio></div>
			<div>视频：<video controls="controls" src="uploadFile/<c:out value="${article.video }"/>"></video></div> --%>
			<div>图片：<img alt="" src="uploadFile/<c:out value="${article.image }"/>" style="width: 270px;"></div>
			<div style="margin: 5px 0;">内容：<c:out value="${article.content }"  escapeXml="false" /></div>
		</div>
  </body>
  
  <script type="text/javascript">
  	function del(){
  		var msg = "您真的确定要删除吗？"; 
		 if (confirm(msg)==true){ 
		 	location.href = "<%=path %>/article/deleteByID?aid=<c:out value='${article.aid }' />";
		 }
  	}
  	
  	$(".del2").click(function(){
  		alert("点击了修改");
  	});
  </script>
  
</html>
