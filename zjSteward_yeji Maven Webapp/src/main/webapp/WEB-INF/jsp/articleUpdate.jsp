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
    <title>发表文章</title>
	<link href="<%=path %>/resource/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/third-party/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/third-party/template.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resource/umeditor1.2.3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" class="resources library" src="<%=path %>/resource/area.js"></script>
    <style type="text/css">
    	div{
    		padding-top: 5px;
    		padding-bottom: 5px;
    	}
    </style>
  </head>
  
  <body>
	<form action="<%=path %>/article/updateArticle" method="post" ><!-- enctype="multipart/form-data" -->
		<div style="display: none;"><input value="<c:out value="${article.aid }"/>" name="aid"> </div>
		<div>标题：<input type="text" name="title" style="width: 400px;margin-top: 5px;" value="<c:out value="${article.title }"/>"></div>
		<div>作者：<input type="text" name="author" style="width: 240px;margin: 5px 0;" value="<c:out value="${article.author }"/>"></div>
		
		<div>
			类别：<select id="select3" name="pvalue">
				<option value='103'>最新贷款口子</option>
				<option value='101'>信用卡提额办法</option>
				<option value='102'>信用卡使用技巧</option>
				<option value='104'>信用卡办卡技术</option>
			</select> &nbsp;&nbsp;&nbsp;
			
		置顶级别：<!-- <input type="text" name="charge" style="width: 100px;margin: 5px 0;"> -->
			<select name="charge" id="upLevel">
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
		<!-- 用于修改article表的programa赋值 -->
		<div id="programa" style="display: none;"><input name='programa' value='贷款口子'></div>
		
		
		<%-- <div>栏目：
			<select id="select1">
				<option>请选择</option>
				<c:forEach items="${params }" var="p">
					<option value="<c:out value="${p.pvalue }" />">
						<c:out value="${p.pname }" />
					</option>
				</c:forEach>
			</select>
			<select id="select2">
				<option value=" "></option>
			</select>
		</div>
		<div>系列：
			<select id="select3" name="pvalue">
				<option value=" "></option>
			</select>
		</div> --%>
		
		<!-- 用于给article表的programa赋值 -->
		<!-- <div id="programa" style="display: none;"><input name='programa' value=''></div> -->
				
		<!-- <div style="margin-top: 5px;">地区：
	        <select class="input_text" id="s_province" name="s_province" placeholder='省份'></select>
	        <select class="input_text" id="s_city" name="area" placeholder='地级市'></select>
	        <div style="display: none;"><select class="input_text" id="s_county" name="s_county" placeholder='县级市'></select></div>
        </div> -->
        
		<%-- <div>费用：<input type="text" name="charge" style="width: 100px;margin: 5px 0;" value="<c:out value="${article.charge }"></c:out>"></div> --%>
		<div>
			概要：<textarea rows="5" cols="60" name="summary" style="width: 100%"><c:out value="${article.summary }"></c:out></textarea>
		</div><br>
		
		<!-- 加载编辑器的容器 -->
    	<!--style给定宽度可以影响编辑器的最终宽度-->
    	<!-- 音频 -->
		<!-- 上传音频：<input type="file" name="audio"> -->
		<!-- 视频 -->
		<!-- 上传视频：<input type="file" name="video"> -->
		<!-- 聊天框 -->
		<script type="text/plain" id="myEditor" name="content" style="width:100%;height:240px;margin: 5px 0;">
			<c:out value="${article.content }"  escapeXml="false" ></c:out>
		</script>
	    
		<input  type="submit" value="修改" style="width: 100px;background-color: #de5347;font-size: 20px;">
	</form>
  </body>
  
   <script type="text/javascript">
    	//实例化编辑器
    	var um = UM.getEditor('myEditor');
    	
    	//初始化地区
    	_init_area();
   </script>
   
   <script type="text/javascript">
	//给置顶级别设值
   	var charge = <c:out value="${article.charge }"/>; //获取置顶级别的值
   	var upLevel = document.getElementById("upLevel"); //获取置顶级别select元素
   	upLevel.value = charge; //设值
   	/* upLevel.options[charge].selected = true; */
   	//给类别设值
   	var pvalue = <c:out value="${article.pvalue }"/>; //获取类别的值
   	var val = document.getElementById("select3"); //获取类别select元素
   	val.value = pvalue; //设值
   	changePV(); //修改栏目值
   	
   	document.getElementById("select3").onchange = new Function("changePV()"); //给隐藏的programa赋值
   	//设置programa
	function changePV(){
		var prog = $('#select3 option:selected').text();
		var tag = "<input name='programa' value='" + prog + "'>";
		$("#programa").html(tag);
	}
   </script>
   
   <script type="text/javascript">
    	//document.getElementById("select1").onchange=new Function("changeP()");//给栏目第二个select赋值
    	//document.getElementById("select2").onchange=new Function("changeP2()");//给栏目第三个select赋值
    	//document.getElementById("select3").onchange=new Function("changePV()");//给隐藏的pvalue赋值
		//查找第二个栏目
    	<%-- function changeP(){
    		var pvalue = $("#select1").val();
    		var tag = "";
    		var ind;
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
						ind = "a" + i;
						var v = "v" + i;
						tag += "<option value='" + data[v] + "'>" + data[ind] + "</option>";
					}}});
			$("#select2").html(tag);
		} --%>
		//查找第三个栏目(系列)
		<%-- function changeP2(){
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
					$("#select3").html(tag);} --%>
		//设置pvalue
		/* function changePV(){
			var pvalue = $('#select3 option:selected').text();
			var tag = "<input name='programa' value='" + pvalue + "'>";
			$("#programa").html(tag);
		} */
		
		
	</script>
	    
</html>
