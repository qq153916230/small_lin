<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.getSession().setAttribute("url", "/agentDetail/updateForm");
%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>新闻中心</title>
    <meta charset="utf-8">
    <link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<style type="text/css">
		iframe{
			margin:0px; 
			width:100%; 
			height:735px;
			overflow:hidden;
		}
		h1{
			font-size: 20px;
			font-family:"Times New Roman",Georgia,Serif;
			text-align: center;
			margin-top: 0;
		}
	</style>
  </head>

<body>
	<h1>龙川综金管家新闻中心</h1>
	<div>
		<div id="tt" class="easyui-tabs" style="width:100%;height:800px;">
			<div id="undistributed" title="贷款口子" style="padding:10px">
				<iframe id="undis" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""></iframe> 
			</div>
			<div id="standard" title="办法技术" style="padding:10px">
				<iframe id="sta" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""></iframe> 
			</div>
			<div id="activation" title="使用技巧" data-options="" style="padding:10px">
				<iframe id="act" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""></iframe> 
			</div>
			<div id="all" title="提额办法" style="padding:10px">
				<iframe id="al" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""></iframe> 
			</div>
		</div>
	</div>
	
</body>

<script type="text/javascript">
		
		$("#tt").tabs({
			onSelect : function(title,index){
				switch(index){
					case 0:
						var url = 'http://mobile.rczjgj.com:7897/zjgj2tj/school/loan.html';
						window.$("#undis").removeAttr("src");
						window.$("#undis").attr("src", url);
						break;
					case 1: 
						var url = 'http://mobile.rczjgj.com:7897/zjgj2tj/school/bkjs.html';
						window.$("#sta").removeAttr("src");
						window.$("#sta").attr("src", url);
						break;
					case 2: 
						var url = 'http://mobile.rczjgj.com:7897/zjgj2tj/school/syjq.html';
						window.$("#act").removeAttr("src");
						window.$("#act").attr("src", url);
						break;
					case 3: 
						var url = 'http://mobile.rczjgj.com:7897/zjgj2tj/school/tebf.html';
						window.$("#al").removeAttr("src");
						window.$("#al").attr("src", url);
						break;
				}
			}
		});
		
    	//判断达标时间
       function regTimeSelect() {
           var regTimeFrom = $('#regTimeFrom').datebox('getValue');
           var regTimeTo = $("#regTimeTo").datebox('getValue');
           if (regTimeTo < regTimeFrom && regTimeFrom != "" && regTimeTo != "") {
               $.messager.alert("提示", "截止时间必须大于开始时间", "info");
           }
       }
	</script>
	
</html>
