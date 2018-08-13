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
<title>提现管理</title>
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
		日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
				&nbsp;至&nbsp;
				<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
		
			&nbsp;&nbsp;
			<input type="text" class="condition" id="uname" placeholder="用户名" />&nbsp;&nbsp;
			<input type="text" class="condition" id="mobile" placeholder="手机号" />&nbsp;&nbsp;
			<select id="status">
				<option value=''>全部</option>
				<option value=0 selected = "selected">新申请</option>
				<option value=2>通过</option>
				<option value=1>不通过</option>
				<option value=3>已发放</option>
			</select>&nbsp;&nbsp;
			
			<a href="javascript:;" class="easyui-linkbutton"  onclick="bindDatagrid()">搜索</a>&nbsp;&nbsp;
			<a href="javascript:;" class="easyui-linkbutton"  onclick="$('#updateStatus').dialog('open')">处理</a>&nbsp;&nbsp;
			<a href="javascript:;" class="easyui-linkbutton" onclick="outExport()">导出</a>&nbsp;&nbsp;
		</div>
	</div>
	
	<!-- 修改状态 -->
	<div title="审核结果" id="updateStatus" class="easyui-dialog" style="width:200px;height:130px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		是否通过：<select id="newStatus">
			<option value=2>通过</option>
			<option value=1>不通过</option>
			<option value=3>已发放</option>
		</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateStatus()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	
</body>

<script type="text/javascript">

	var regTimeFrom = '';
	var regTimeTo = '';
	var mobile = '';
	var uname = '';
	var status = '';
	
	function bindDatagrid() {
	
		mobile = $('#mobile').val();
		uname = $('#uname').val();
		status = $('#status').val();
		regTimeFrom = $('#regTimeFrom').datebox('getValue');
		regTimeTo = $('#regTimeTo').datebox('getValue');
		
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?mobile="+mobile+"&uname="+uname+"&status="+status+"&startDate="+regTimeFrom+"&endDate="+regTimeTo;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/common/ship/tixian/list' + searchCodition,
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
					{ field: 'tid', hidden: 'true'},
                    { field: 'mid', title: '用户编号', width: 80,  align: 'center'},
                    { field: 'status', title: '状态', width: 80, align: 'center',formatter : formatStatus},
					{ field: 'nick', title: '申请人', width: 100,  align: 'center'},
					{ field: 'mobile', title: '手机', width: 100,  align: 'center'},
					{ field: 'money_tx', title: '提现金额', width: 100,  align: 'center',formatter :formatprice},
                    { field: 'bank_user', title: '收款人', width: 80, align: 'center'},
                    { field: 'bank_name', title: '收款行', width: 80, align: 'center'},
                    { field: 'bank_number', title: '收款卡号', width: 120,  align: 'left'},
					{ field: 'cdate', title: '申请日期', width: 150, sortable: 'true',  align: 'center',formatter : formatTime},
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
	
	function formatStatus(val){
		switch(val){
			case 0: return "新申请"; break;
			case 1: return "不通过"; break;
			case 2: return "通过"; break;
			case 3: return "已发放"; break;
			default : return ""; break;
		}
	}
	
	function updateStatus(){
		$("#updateStatus").dialog("close");
		
		var row = $('#dg').datagrid('getSelections');
		
		if (row.length == 0) {
			layer.open({title: '提示', icon: 0, content: '请选择要处理的记录'});
			return ;
		}
		
		var status = $('#newStatus').val();	//新状态
		
		var tids = "";
		var mids = "";
		for (var i = 0; i < row.length; i++) {
			
			if(status <= row[i].status){
				layer.open({title: '提示', icon: 0, content: row[i].nick + ' 状态有误'});
				return;
			}
			
			if (i == 0){
				tids = row[i].tid;
				mids = row[i].mid;
			} else {
				tids += "," + row[i].tid;
				mids += "," + row[i].mid;
			}
		}
		
		$.ajax({ 
			type : "post",
			url : "<%=path %>/common/ship/tixian/update_status",
			dataType:"json",
			data : {"tids":tids, "status":status, "mids":mids},
			success : function(data) {
				layer.open({title: '提示',content: data.msg});
				if(data.status == 1)
					bindDatagrid();
			}
		});
		
	}
	
	//导出
	function outExport(){
		$.ajax({
			type : "post",
			url : '<%=path %>/common/ship/tixian/export?mobile='+mobile+'&uname='+uname+'&status='+status+'&startDate='+regTimeFrom+'&endDate='+regTimeTo,
			dataType:"json",
			success : function(data) {
				if (data.status == "1") {
					location.href=data.msg;
				} else {
					alert("请求被外星人拐走了，请与后台人员联系！");
				}
			},
			error : function() {
				alert("请求被外星人拐走了，请与后台人员联系！！");
			}
		});
	}
	
	
</script>

</html>

