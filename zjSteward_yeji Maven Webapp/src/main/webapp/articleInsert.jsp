<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.getSession().setAttribute("url", "/article/insertArticle");//作为上传文件serclet里面的请求转发url
%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>发表文章</title>
    <meta charset="utf-8">
	<link href="<%=path %>/resource/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/third-party/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/third-party/template.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
    <%-- <script type="text/javascript" src="<%=path %>/resource/UEditor-jsp/ueditor.config.js"></script>
    <script type="text/javascript" src="<%=path %>/resource/UEditor-jsp/ueditor.all.js"></script>
    <script type="text/javascript" src="<%=path %>/resource/jquery.min.js"></script> --%>
    <script type="text/javascript" class="resources library" src="<%=path %>/resource/area.js"></script>
    
    <style type="text/css">
    	div{
    		margin-top: 10px;
    	}
    </style>
  </head>
  
  <body><!-- article/insertArticle -->
	<form action="upload" enctype="multipart/form-data" method="post" style="padding-left: 5px;">
	
		<div>类型：
			<select id="select3" name="pvalue">
				<option value=''>请选择</option>
				<option value='103'>最新贷款口子</option>
				<option value='101'>信用卡提额办法</option>
				<option value='102'>信用卡使用技巧</option>
				<option value='104'>信用卡办卡技术</option>
			</select> &nbsp;&nbsp;&nbsp;
		置顶级别：<!-- <input type="text" name="charge" style="width: 100px;margin: 5px 0;"> -->
			<select name="charge">
				<option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
			</select><span style="color: gray;">(值越大越靠前)</span>
		</div>
		<!-- 用于给article表的programa赋值 -->
		<div id="programa" style="display: none;"><input name='programa' value=''></div>
	
		<div>标题：<input type="text" name="title" style="width: 400px;margin-top: 5px;"></div>
		<div style="display: none;">作者：<input type="text" name="author" style="width: 240px;margin: 5px 0;" value="0"></div>
		
		<%-- <div>栏目：
			<select id="select1">
				<option>请选择</option>
				<c:forEach items="${params }" var="p"> <c:out value="${p.pvalue }" /> <c:out value="${p.pname }" /> </c:forEach>
				<option value="栏目1">栏目1</option>
			</select>
			
			<select id="select2">
				<option value="栏目2">栏目2</option>
			</select>
		</div> --%>
		
		<!-- <div style="margin-top: 5px;">地区：
	        <select class="input_text" id="s_province" name="s_province" placeholder='省份'></select>
	        <select class="input_text" id="s_city" name="area" placeholder='地级市'></select>
	        <div style="display: none;"><select class="input_text" id="s_county" name="s_county" placeholder='县级市'></select></div>
        </div> -->
		
		
		<div>
			<span style="vertical-align:top;">概要：</span><textarea rows="5" cols="60" name="summary" ></textarea>
		</div><br>
		
		<!-- 加载编辑器的容器 -->
    	<!--style给定宽度可以影响编辑器的最终宽度-->
    	<!-- 音频 -->
		<!-- <div>上传音频：<input type="file" name="audio"></div> -->
		<!-- 视频 -->
		<!-- <div style="margin: 5px 0;">上传视频：<input type="file" name="video"></div> -->
		<!-- 图片 -->
		<div style="margin: 5px 0;">上传图片：<input type="file" name="image"></div>
		<!-- 聊天框 -->
		<script type="text/plain" id="myEditor" name="content" style="width:95%;height:240px;margin: 5px 0;"></script>
	    
		<input type="submit" value="发表" style="width: 100px;background-color: #de5347;font-size: 20px;">
	</form>
  </body>
  
   <script type="text/javascript">
    	//实例化编辑器
    	var um = UM.getEditor('myEditor');
    	
    	//初始化地区
    	_init_area();
   </script>
	<script type="text/javascript">
    	document.getElementById("select3").onchange = new Function("changePV()"); //给隐藏的pvalue赋值
    	
    	//设置pvalue
		function changePV(){
			var pvalue = $('#select3 option:selected').text();
			var tag = "<input name='programa' value='" + pvalue + "'>";
			$("#programa").html(tag);
		}
    	<%-- document.getElementById("select1").onchange = new Function("changeP()"); //给栏目第二个select赋值
    	document.getElementById("select2").onchange = new Function("changeP2()"); //给栏目第三个select赋值
    	//查找第二个栏目
    	function changeP(){
    		var pvalue = $("#select1").val();
    		var tag = "";
    		$.ajax({
    			async:false,
				type:"post",
				url:"<%=path %>/article/selectParamValue",
				data:'pvalue=' + pvalue,
				dataType:"json",
				complete:function(){},//ajax请求完成时隐藏loading提示
				success:function(data){
					tag += "<option value=''>必选</option>";
					for (var i = 0; i < data.num; i++) {
						var ind = "a" + i;
						var v = "v" + i;
						tag += "<option value='" + data[v] + "'>" + data[ind] + "</option>";
					}}});
					$("#select2").html(tag);}
		//查找第三个栏目(系列)
		function changeP2(){
    		var pvalue = $("#select2").val();
    		var tag = "";
    		$.ajax({
    			async:false,
				type:"post",
				url:"<%=path %>/article/selectSeriesByPval",
				data:'pvalue=' + pvalue,
				dataType:"json",
				complete:function(){},//ajax请求完成时隐藏loading提示
				success:function(data){
					tag += "<option value=''>必选</option>";
					for (var i = 0; i < data.num; i++) {
						var ind = "a" + i;
						var tvalue = "v" + i;
						tag += "<option value='" + data[tvalue] + "'>" + data[ind] + "</option>";
					}}});
					$("#select3").html(tag);}
		 --%>
	</script>
	
</html>
