<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>法大大数据测试</title>
    <meta charset="utf-8">
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/third-party/jquery.min.js"></script>
    
    <style type="text/css">
    	div{
    		margin-top: 10px;
    	}
    </style>
  </head>
  
  <body><!-- article/insertArticle -->
	<form action="<%=path %>/uploadFixedUrl" enctype="multipart/form-data" method="post" style="padding-left: 5px;">
		
		姓名<input id="" class="" type="text" name="customer_name" value="">
		<!-- 邮箱 --><input id="" class="" type="hidden" name="email" value="">
		身份证<input id="" class="" type="text" name="id_card" value="">
		<!-- 证件类型 --><input id="" class="" type="hidden" name="ident_type" value="1">
		手机号<input id="" class="" type="text" name="mobile" value="">
		<!-- 合同编号 单号 --><input id="" class="" type="hidden" name="contract_id" value="">
		<!-- 合同标题  --><input id="" class="" type="hidden" name="doc_title" value="">
		合同文件<input id="" class="" type="file" name="file" value="" accept=".pdf">
		合同类型<input id="" class="" type="text" name="doc_type" value=".pdf" readonly="readonly">
		
		<input type="submit" value="发表" style="width: 100px;background-color: #de5347;font-size: 20px;">
	</form>
  </body>
  
	<script type="text/javascript">
    	
	</script>
	
</html>
