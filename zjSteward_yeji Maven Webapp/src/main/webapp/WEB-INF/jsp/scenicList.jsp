<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>景区管理</title>
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<script src="<%=path %>/js/layer/layer.js"type="text/javascript"></script>
	<script src="<%=path %>/js/zjgjCommon.js" type="text/javascript"></script>
	<style type="text/css">
		.condition{
			width:100px;
		}
	</style>

</head>
<body id="" style="padding: 0px; margin: 0px;">
	
	<!-- 内容显示表 -->
	<table id="dg"></table>
	<!-- 操作 -->
	<div id="toolbar">
		日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
				&nbsp;至&nbsp;
				<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
		
		<input type="text" class="condition" id="sname" placeholder="景区名" />&nbsp;&nbsp;
		<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
		<a href="javascript:;" class="easyui-linkbutton"  onclick="createScenicFun()">添加景区</a>&nbsp;&nbsp;
	</div>
		
	<div style="display: none">
		<!-- 弹出url链接 -->
		<div title="标题" id="createScenic" class="easyui-dialog" style="width: 420px; height:430px; overflow:hidden; padding: 0px;" closed="true" data-mainkey="" data-options="resizable:true">
			 <form style="margin: 5% 5%;"> 
			 	<table border="1" cellpadding="5" cellspacing="">
			 		<tbody>
			 			<tr><td>景区名</td>
			 				<td><input class="sname" name="sname"></td></tr>
			 			<tr><td>票价</td>
			 				<td><input class="tprice" name="tprice"></td></tr>
			 			<tr><td>景区链接</td>
			 				<td><input class="surl" name="surl"></td></tr>
			 			<tr><td>景区图片</td>
			 				<td><input class="spicture" name="spicture" type="file" onchange="getPicBase64(this)"></td></tr>
			 			<tr><td>地区</td>
			 				<td><input class="sarea" name="sarea"></td></tr>
			 			<tr><td>景区简介</td>
			 				<td><textarea class="sintroduce" rows="8" cols="40" name="sintroduce" maxlength="100" placeholder="100字以内"></textarea></td></tr>
			 		</tbody>
			 	</table>
			 	<a class="easyui-linkbutton" href="javascript:;" onclick="submitData()" style="width: 100px; margin-top: 5px; color: blue;">
			 		提交</a>
			 	
			 </form>
		</div>
	</div>
	
	<!-- 弹出url链接 -->
	<div title="标题" id="urlContent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" data-mainkey="" data-options="resizable:true">
		<iframe id="urlFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
</body>

<script type="text/javascript">

	var spicture;	//景区图片
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo+"&sname="+sname;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/scenic/selectList' + searchCodition,
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : false, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							remoteSort : false,
							pagination : true, //分页  
							rownumbers : true,//行数 
							columns : [ [
					{ field: '', checkbox: 'true' },
					{ field: 'sid', title: 'sid', hidden:'true'},
					{ field: 'surl', title: '网址', width: 80,  align: 'center',formatter :formatUrl},
					{ field: 'sname', title: '景区名', width: 200,  align: 'center'},
                    { field: 'tprice', title: '票价', width: 100,  align: 'center',formatter :formatprice},
					{ field: 'sarea', title: '地区', width: 100,  align: 'center'},
					{ field: 'cdate', title: '日期', width: 180,  align: 'center',formatter : formatTime},
					{ field: 'delete', title: '删除', width: 40,  align: 'center',formatter :formatDelete},
					
							] ],
						});
					//设置分页控件 
					var p = $('#dg').datagrid('getPager');
					$(p).pagination({
						beforePageText : '第', //页数文本框前显示的汉字 
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录',
					});
	}
	//格式化url
	function formatUrl(val, obj){
		return "<a href='"+ val +"' target='_blank'>查看</a>";
		//return "<a href='javascript:;' target='_blank' onclick='openUrlContent("+JSON.stringify(val)+",\"景区详情\")'>查看</a>";	
	}
	//格式化删除按钮
	function formatDelete(val, obj){
		return "<a href='javascript:;' onclick='deleteFun("+obj.sid+")'><img src='<%=path %>/js/jquery-easyui-1.4.3/themes/icons/no.png'></a>";
	}
	
	function openUrlContent(url, title){
		setTitle(title);
		$("#urlFrame").removeAttr("src");
		$("#urlFrame").attr("src", url);
		$("#urlContent").dialog('open'); 
	}
	//添加景区
	function createScenicFun() {
		setTitle("添加景区");
		$('#createScenic').dialog('open');
	}
	//删除景区
	function deleteFun(sid) {
		layer.confirm('是否删除该景区信息？', {icon: 3, title:'提示'}, function(index){
			sendRequest("<%=path %>/scenic/scenicDelete", {"sid":sid}, "json");
		  	layer.close(index);
			bindDatagrid();
		});
	}
	
		var image = new Image();
	//获取图片base64字符串
	function getPicBase64(imgFile) {
		image.src = window.URL.createObjectURL(imgFile.files[0]);
		image.onload = function() {
			spicture = getBase64Image(image);
		};
	}
	
	//提交form表单数据
	function submitData() {
		var sname = $(".sname").val();
		var tprice = $(".tprice").val();
		var surl = $(".surl").val();
		var sarea = $(".sarea").val();
		var sintroduce = $(".sintroduce").val();
		
		var url = "<%=path %>/scenic/scenicInsert";
		var data = {"sname":sname,"tprice":tprice,"surl":surl,
				"sarea":sarea,"sintroduce":sintroduce,"spicture":spicture};
		
		//zjgjCommon 发送请求
		sendRequest(url, data, "json");
		$('#createScenic').dialog('close');
		bindDatagrid();
	}
</script>

</html>

