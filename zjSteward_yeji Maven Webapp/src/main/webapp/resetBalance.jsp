<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mid = request.getParameter("mid");
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信用卡管理</title>
	
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/layer/layer.js"type="text/javascript"></script>
	
</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<div style="margin:50px;">
		手机号：<input class="mobile"  >
		<button onclick="resetBalance()">提交</button>
	</div>
</body>

<script type="text/javascript">
	function resetBalance(){
		var mobile = $(".mobile").val();
		if(mobile == "" || mobile.length != 11){
			alert("手机号有误");
			return;
		}
		
		$.ajax({
		type:"post",
		url : "<%=path %>/scenic/resetBalance",
		data: {"mobile":mobile},
		dataType: "json",
		success:function(data){
			layer.open({title: '提示',content: data.msg});
		},
		error:function(data){
			layer.open({title: 'warming',content: "出错了，脑阔疼！"});
		},
	});
	}
</script>

</html>

