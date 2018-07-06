<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String tid = request.getParameter("tid");
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>还款管理</title>
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
			display: none;
		}
	</style>

</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<form  id="form1">
		<!-- 内容显示表 -->
		<table id="dg"></table>
		<!-- 操作 -->
		<div style="display:none;">
			<div id="toolbar">
				收款日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
						&nbsp;至&nbsp;
						<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
				
				状态:<select id="status">
					<option value=''>全部</option>
					<option value=1>已还款</option>
					<option value=0>未还款</option>
				</select>
				
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
			</div>
		</div>
	</form>
</body>

<script type="text/javascript">
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo+"&status="+status+"&tid="+<%=tid %>;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/aidgoldBack/selectList' + searchCodition,
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
					{ field: 'tid', title: 'tid', hidden:'true'},
                    { field: 'aid', title: '助力金编号', width: 70,  align: 'center'},
                    { field: 'mid', title: '用户编号', width: 60,  align: 'center'},
                    { field: 'period', title: '分期期数', width: 60,  align: 'center'},
                    { field: 'hkmoney', title: '还款金额', width: 80,  align: 'center',formatter :formatprice},
					{ field: 'principal', title: '还款本金', width: 80,  align: 'center',formatter :formatprice},
					
					{ field: 'enddate', title: '最后还款日', width: 160,  align: 'center',formatter : formatTime},
					{ field: 'hkdate', title: '实际还款日期', width: 160,  align: 'center',formatter : formatTime},
					
					{ field: 'status', title: '还款状态', width: 70, align: 'center',formatter : formatSta},
                    { field: 'modo', title: '还款模式', width: 70, align: 'center',formatter : formatModo},
                    
                    { field: 'overdate', title: '逾期天数', width: 80,  align: 'center'},
                    { field: 'overmoney', title: '逾期金额', width: 80,  align: 'center',formatter :formatprice},
                    
					{ field: 'poundage', title: '手续费', width: 70,  align: 'center',formatter :formatprice},
					{ field: 'interest', title: '利息', width: 70,  align: 'center',formatter :formatprice},
					{ field: 'gdate', title: '日期', width: 180,  align: 'center',formatter : formatTime},
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
	//格式化还款状态
	function formatSta(val){
		switch(val){
			case 0: return "未还"; break;
			case 1: return "已还"; break;
		}}
	//格式化还款模式
	function formatModo(val){
		switch(val){
			case 1: return "全额"; break;
			case 2: return "分期"; break;
		}}
	
</script>

</html>

