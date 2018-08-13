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
<title>付款管理</title>
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
			<select id="isreward">
				<option value=''>全部</option>
				<option value=9 selected = "selected">待确认</option>
				<!-- <option value=0>已确认</option>
				<option value=1>已处理</option> -->
			</select>&nbsp;&nbsp;
			
			<a href="javascript:;" class="easyui-linkbutton"  onclick="bindDatagrid()">搜索</a>&nbsp;&nbsp;
			<a href="javascript:;" class="easyui-linkbutton"  onclick="confirmPay()">确认到账</a>&nbsp;&nbsp;
		</div>
	</div>
	
</body>

<script type="text/javascript">

	var mobile = '';
	var uname = '';
	var isreward = '';
	
	function bindDatagrid() {
		mobile = $('#mobile').val();
		uname = $('#uname').val();
		isreward = $('#isreward').val();
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?mobile="+mobile+"&uname="+uname+"&isreward="+isreward;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/common/ship/option/list' + searchCodition,
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
					{ field: 'tid', hidden: 'true'},
                    { field: 'mid', title: '用户编号', width: 80,  align: 'center'},
					{ field: 'mobile', title: '手机', width: 100,  align: 'center'},
					{ field: 'nick', title: '用户名', width: 100,  align: 'center'},
					{ field: 'price', title: '支付金额', width: 100,  align: 'center',formatter :formatprice},
                    { field: 'stype', title: '支付类型', width: 80, align: 'center',formatter : formatStype},
                    { field: 'isreward', title: '支付结果', width: 80, align: 'center',formatter : formatIsreward},
                    { field: 'remark', title: '备注', width: 120,  align: 'left'},
					{ field: 'sdate', title: '日期', width: 150, sortable: 'true',  align: 'center',formatter : formatTime},
					
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
	
	function formatStype(val){
		switch(val){
			case 1: return "v1"; break;
			case 6: return "股东"; break;
			default : return ""; break;
		}
	}
	
	function formatIsreward(val){
		switch(val){
			case 9: return "待确认"; break;
			case 0: return "已确认"; break;
			case 1: return "已处理"; break;
			default : return ""; break;
		}
	}
	
	function confirmPay(){
		var row = $('#dg').datagrid('getSelections');
		if(row.length > 1 || row.length == 0){
			layer.open({title: '提示',content: '一次只能确认一个人'});
			return;
		}
		if(row[0].isreward != 9){
			layer.open({title: '提示',content: '该记录已确认过了'});
			return;
		}
		layer.confirm('客户 ' + row[0].nick + ' 已成功付款？', {icon: 3, title:'提示'}, function(index){
			$.ajax({ 
				type : "post",
				url : "<%=path %>/common/ship/confirm",
				dataType:"json",
				data : {"tid":row[0].tid},
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

