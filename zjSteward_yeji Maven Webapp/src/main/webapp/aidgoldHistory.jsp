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
		<!-- <div style="display:none;">
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
		</div> -->
	</form>
</body>

<script type="text/javascript">
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		//searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo+"&status="+status+"&tid="+;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/aidgold/selectList?mid=<%=mid %>',
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
					{ field: 'mid', title: 'mid', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'tid', title: '编号', width: 0,  align: 'center', sortable: 'true'},
					{ field: 'jkcheck', title: '状态',  width: 120, align: 'center', formatter : jkcheckStatus },
                    { field: 'skname', title: '借款用户',  width: 60, align: 'center', sortable: 'true' },
                    { field: 'cstimes', title: '催收次数',  width: 55, align: 'center', sortable: 'true' },
					{ field: 'declare', title: '备注',  width: 200, align: 'left'},
					{ field: 'jkmoney', title: '借款金额', width: 100,  align: 'center', sortable: 'true',formatter:formatprice },
					{ field: 'hkmoney', title: '已还金额',  width: 70, align: 'center', sortable: 'true' },
					{ field: 'userName', title: '代理商',  width: 80, align: 'center' },
                    { field: 'remark', title: '系统审核结果',  width: 150, align: 'left', sortable: 'true' },
					{ field: 'jkdate', title: '开始日期', width: 100,  align: 'center', sortable: 'true' , formatter : formatterdate  },
					{ field: 'enddate', title: '结束日期', width: 100,  align: 'center', sortable: 'true' , formatter : formatterdate },
					{ field: 'jkdays', title: '借款天数', width: 80,  align: 'center' },
					{ field: 'jkrate', title: '利息', width: 80,  align: 'center', sortable: 'true',formatter:formatRate },
					{ field: 'cgrate', title: '违约利息', width: 80,  align: 'center', sortable: 'true',formatter:formatRate },
					{ field: 'skbankno', title: '收款卡号',  width: 180, align: 'center', sortable: 'true' },
                    { field: 'skbank', title: '收款银行',  width: 200, align: 'left', sortable: 'true' },
                    { field: '  ', title: '  ', width: 300,  align: 'center'},
                    
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
	
	//格式化借款状态
		function jkcheckStatus(value,row) {
			switch(value){
			//case 11: return "同意贷款";	break;
			case 11: return "同意贷款";	break;
			case 12: return "不同意贷款";	break;
			case 21: return "已付手续费";	break;
			case 22: return "手续费支付失败";	break;
			case 31: return "客户已签署合同";	break;
			case 32: return "客户签署合同失败";	break;
			//case 33: return "公司签署合同成功";	break;
			case 33: return "公司签署合同成功";	break;
			case 34: return "公司签署合同失败";	break;
			case 41: return "代理商同意放贷";	break;
			case 42: return "代理商不同意放贷";	break;
			case 43: return "已放贷";	break;
			case 44: return "放贷失败";	break;
			case 50: return "待还款";	break;
			case 51: return "还款中";	break;
			case 52: return "还款失败";	break;
			case 99: return "已还清";	break;
			default: return "待审核";
			} 
		}
		
		function formatterdate(val, row) {
			if (val != null) {
				var date = new Date(val);
				return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
			}
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

