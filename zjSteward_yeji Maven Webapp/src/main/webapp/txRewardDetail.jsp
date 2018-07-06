<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//获取要查询的订单号
	String ordercode = request.getParameter("ordercode");
	String possn = request.getParameter("possn");
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提现详情</title>
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

</head>
<body id="" style="padding: 0px; margin: 0px;">
	<!-- 内容显示表 -->
	<table id="dg"></table>
</body>

<script type="text/javascript">
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/common/tx/reward/detail?ordercode=<%=ordercode %>&possn=<%=possn%>',
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
					{ field: 'mid', title: '用户编号', width: 100,  align: 'center'},
					{ field: 'ordercode', title: '订单号', width: 180,  align: 'center'},
					{ field: 'reward', title: '提现金额', width: 80,  align: 'center'},
					{ field: 'possn', title: '机具号', width: 160,  align: 'center'},
                    { field: 'nick', title: '用户名', width: 80,  align: 'center'},
					{ field: 'mobile', title: '用户手机', width: 100,  align: 'center'},
					{ field: 'role', title: '角色类型', width: 80,  align: 'center'},
                    { field: 'rate', title: '费率',  width: 80, align: 'center'},
					{ field: 'rdate', title: '日期', width: 150,  align: 'center',formatter : formatTime},
                    //{ field: 'posnum', title: 'posnum',  width: 200, align: 'center'},
					
					
					/* { field: 'posmode', title: '处理方式', width: 100,  align: 'center', formatter:posmodeFormat},
                    { field: 'status', title: '状态', width: 80, align: 'center',formatter : formatStatusEP},
					{ field: 'ptype', title: '类型', width: 100, align: 'center',formatter : formatPtype},
                    { field: 'amount', title: '金额', width: 100,  align: 'center',formatter :formatprice},
                    { field: 'ptimes', title: '查询次数',  width: 80, align: 'center'},
					{ field: 'isreward', title: '处理类型', width: 100,  align: 'center', formatter:isrewardFormat},
                    { field: 'bluetype', title: '抵押',  width: 100, align: 'center', formatter : bluetypeFormat},
					{ field: 'gdate', title: '更新日期', width: 200,  align: 'center',formatter : formatTime},
					{ field: 'remark', title: '备注', width: 120,  align: 'left'},
					 */
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
	//格式化类型
	function formatPtype(val){
		switch(val){
			case 1: return "付手续费"; break;
			case 2: return "全额还款"; break;
			case 3: return "分期还款"; break;
			case 4: return "代还还款"; break;
			case 5: return "一元购票"; break;
		}}
	//格式化状态
	function formatStatusEP(val){
		switch(val){
			case 10: return "已提交收款"; break;
			case 11: return "收款成功"; break;
			case 12: return "收款失败"; break;
			case 20: return "已提交还款"; break;
			case 21: return "还款成功"; break;
			case 22: return "还款失败"; break;
			default: return "新申请";
		}}
	
</script>

</html>

