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
<title>股东管理</title>
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
		body{
			padding: 0px; 
			margin: 0px;
		}
	</style>

</head>
<body>
	<!-- 内容显示表 -->
	<table id="dg"></table>
	<!-- 操作 -->
	<div style="display:none;">
		<div id="toolbar">
			&nbsp;&nbsp;
			<input type="text" class="condition" id="uname" placeholder="用户名" />&nbsp;&nbsp;
			<input type="text" class="condition" id="mobile" placeholder="手机号" />&nbsp;&nbsp;
			
			<a href="javascript:;" class="easyui-linkbutton"  onclick="bindDatagrid()">搜索</a>&nbsp;&nbsp;
			<a href="javascript:;" class="easyui-linkbutton"  onclick="beShareholder()">成为股东</a>&nbsp;&nbsp;
		</div>
	</div>
	
</body>

<script type="text/javascript">

	var mobile = '';
	var uname = '';
	
	function bindDatagrid() {
		mobile = $('#mobile').val();
		uname = $('#uname').val();
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?mobile="+mobile+"&uname="+uname;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/common/ship/member/list' + searchCodition,
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : true, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							remoteSort : false,
							pagination : true, //分页  
							rownumbers : true,//行数 
							columns : [ [
					{ field: '', checkbox: 'true' },
                    { field: 'mid', title: '用户编号', width: 80,  align: 'center'},
					{ field: 'mobile', title: '手机', width: 100,  align: 'center'},
					{ field: 'nick', title: '用户名', width: 100,  align: 'center'},
					{ field: 'agent_nick', title: '推荐人', width: 100,  align: 'center'},
                    { field: 'userrole', title: '用户类型', width: 80, align: 'center',formatter : userType},
					{ field: 'regdate', title: '注册时间', width: 150, sortable: 'true',  align: 'center',formatter : formatTime},
					
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
	
	function userType(val){
		switch(val){
			case 0: return ""; break;
			case 1: return "v1"; break;
			case 2: return "v2"; break;
			case 3: return "v3"; break;
			case 4: return "v4"; break;
			case 5: return "股东"; break;
			case 9: return "企业"; break;
			default : return ""; break;
		}
	}
	
	function beShareholder(){
		var row = $('#dg').datagrid('getSelections');
		if(row.length > 1 || row.length == 0){
			layer.open({title: '提示',content: '一次只能设置一个人'});
			return;
		}
		if(row[0].userrole == 5 || row[0].userrole == 9){
			layer.open({title: '提示',content: '该客户已经是股东了'});
			return;
		}
		layer.confirm('设置 ' + row[0].nick + ' 成为股东', {icon: 3, title:'提示'}, function(index){
			$.ajax({ 
				type : "post",
				url : "<%=path %>/common/ship/be_shareholder",
				dataType:"json",
				data : {"mid":row[0].mid},
				success : function(data) {
					layer.open({title: '提示',content: data.msg});
					if(data.status == 1)
						bindDatagrid();
				}
			});
		  layer.close(index);
		});
		
	}
	
	
</script>

</html>

