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
<title>机具分发管理</title>
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<script src="<%=path %>/js/zjgjCommon.js" type="text/javascript"></script>
	<style type="text/css">
		.condition{
			width:100px;
		}
	</style>
<script type="text/javascript">
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo+
			"&mobile="+mobile+"&isreward="+isreward+"&bluetype="+bluetype+
			"&possn="+possn+"&merchantname="+merchantname+"&merchantcode="+merchantcode;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/posAssign/selectList' + searchCodition,
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
					{ field: 'posid', title: '机身ID', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'posmode', title: '处理方式', width: 100,  align: 'center', formatter:posmodeFormat},
					{ field: 'isreward', title: '处理类型', width: 100,  align: 'center', formatter:isrewardFormat},
                    { field: 'bluetype', title: '抵押',  width: 100, align: 'center', formatter : bluetypeFormat},
					{ field: 'possn', title: '机身号', width: 180, align: 'center'},
                    { field: 'merchantcode', title: '商户号', width: 140,  align: 'center'},
					{ field: 'merchantname', title: '商户名', width: 100,  align: 'center'},
					{ field: 'mid', title: '代理商编号', width: 100,  align: 'center'},
					{ field: 'mobile', title: '代理商手机号', width: 120,  align: 'center'},
					{ field: 'postype', title: '机具类型', width: 100,  align: 'center'},
					{ field: 'vipMobile', title: '会员手机号', width: 120,  align: 'center'},
					{ field: 'vipNick', title: '会员名称', width: 100,  align: 'center'},
                    { field: 'dbvip', title: '达标VIP', width: 100,  align: 'center'},
                    { field: 'dbremark', title: '达标说明',  width: 200, align: 'center'},
					{ field: 'dbdate', title: '达标日期', width: 200,  align: 'center',formatter : formatTime},
					{ field: 'gdate', title: '更新日期', width: 200,  align: 'center',formatter : formatTime},
					{ field: 'remark', title: '备注', width: 200,  align: 'left'},
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
	
	function updateBluetype(){
		var row = $('#dg').datagrid('getSelections');
		
		var url = '<%=path %>/posAssign/updateStatus';
		var newStatus = $('#newStatus').val();
		var pnos = "";
		if(row.length>0){
			//设置pnos字符串
			if(row){
				for(var i=0;i<row.length;i++){
					if(i==0) pnos = row[i].possn;
						else pnos += "," + row[i].possn;
				}
			}
		}
		//调用ajax方法
		updatestatus(url, pnos, newStatus);
		//关闭dialog
		$("#updateStatus").dialog("close");
	}
	
</script>
</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<form  id="form1">
		<!-- 内容显示表 -->
		<table id="dg"></table>
		<!-- 操作 -->
		<div style="display:none;">
			<div id="toolbar">
					<select id="isreward">
						<option value=''>处理状态</option>
						<option value=0>未分配</option>
						<option value=1>已分配</option>
						<option value=2>已激活</option>
						<option value=3>已达标</option>
					</select>
					<select id="bluetype">
						<option value=''>抵押状态</option>
						<option value=0>未抵押</option>
						<option value=1>已抵押</option>
					</select>

				达标日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
						&nbsp;至&nbsp;
						<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
				
				<input type="text" class="condition" id="possn" placeholder="机身号" />&nbsp;&nbsp;
				<input type="text" class="condition" id="merchantname" placeholder="商户名" />&nbsp;&nbsp;
				<input type="text" class="condition" id="merchantcode" placeholder="商户号" />&nbsp;&nbsp;
				<input type="text" class="condition" id="mobile" placeholder="代理商手机号" />&nbsp;&nbsp;
				
					
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
 				<a href="javascript:;" class="easyui-linkbutton" onclick="openDialog()" >抵押管理</a>
					 
 			<!-- <a href="javascript:;" target="_blank" class="easyui-linkbutton" onclick="OutExplor()" >导出</a>&nbsp;&nbsp; -->
			</div>
		</div>
	</form>
	
	<!-- 显示提醒处理窗口 -->
	<div title="处理" id="updateStatus" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		变更状态为：
			<select id="newStatus">
				<option value=0>已退还</option>
				<option value=1>已抵押</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateBluetype()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	
	
</body>
</html>

