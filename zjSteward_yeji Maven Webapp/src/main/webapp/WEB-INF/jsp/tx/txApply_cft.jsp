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
<title>财富通提现申请</title>
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
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<form  id="form1">
		<!-- 内容显示表 -->
		<table id="dg"></table>
		<!-- 操作 -->
		<div style="display:none;">
			<div id="toolbar">
				申请日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
						&nbsp;至&nbsp;
						<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
				
				<!-- <input type="text" class="condition" id="acctname" placeholder="开户名" />&nbsp;&nbsp;
				<input type="text" class="condition" id="mobile" placeholder="手机号" />&nbsp;&nbsp;
				状态:<select id="status">
					<option value=''>全部</option>
					<option value=0>新申请</option>
					<option value=10>已提交收款</option>
					<option value=11>收款成功</option>
					<option value=12>收款失败</option>
					<option value=20>已提交还款</option>
					<option value=21>还款成功</option>
					<option value=22>还款失败</option>
				</select>&nbsp;&nbsp;
				类型:<select id="ptype">
					<option value=''>全部</option>
					<option value=1>付手续费</option>
					<option value=2>全额还款</option>
					<option value=3>分期还款</option>
					<option value=4>代还还款</option>
					<option value=5>一元购票</option>
				</select>&nbsp;&nbsp; -->
				
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
				<a href="javascript:;" class="easyui-linkbutton" onclick="$('#deal').dialog('open');" >处理</a>
			</div>
		</div>
	</form>
	
	<!-- 显示提醒处理窗口 -->
	<div title="处理" id="deal" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		变更状态为：
			<select id="newStatus">
				<option value=1>审核通过</option>
				<option value=2>审核不通过</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="dealSelections()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#deal').dialog('close');">取消</a>
	</div>
	
</body>

<script type="text/javascript">
	
	var ptype;	//支付类型
	
	function bindDatagrid() {
		ptype = $("#ptype").val();
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo;
			//+"&mobile="+mobile+"&acctname="+acctname+"&status="+status+"&ptype="+ptype;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/common/tx/cft/apply' + searchCodition,
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
                    { field: 'mid', title: '用户编号', hidden:'true'},
                    { field: 'status', title: '状态', width: 80, align: 'center',formatter : formatStatus},
                    { field: 'txcode', title: '流水号',  width: 200, align: 'center'},
                    { field: 'money', title: '提现金额', width: 100,  align: 'center',formatter :formatprice},
					{ field: 'txtype', title: '提现类型', width: 100,  align: 'center'},
					{ field: 'sdate', title: '提现日期', width: 150,  align: 'center',formatter : formatTime},
                    
					/* { field: 'posmode', title: '处理方式', width: 100,  align: 'center', formatter:posmodeFormat},
					{ field: 'ptype', title: '类型', width: 100, align: 'center',formatter : formatPtype},
					{ field: 'mobile', title: '手机', width: 100,  align: 'center'},
					{ field: 'idno', title: '身份证', width: 180,  align: 'center'},
					{ field: 'acctbankname', title: '银行', width: 100,  align: 'center'},
					{ field: 'orderid', title: '订单编号', width: 200,  align: 'center'},
					{ field: 'acctno', title: '借记卡卡号', width: 240,  align: 'center'},
                    { field: 'thirdvoucher', title: '批次凭证号',  width: 200, align: 'center'},
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
	//格式化状态
	function formatStatus(val){
		switch(val){
			case 0: return "新申请"; break;
			case 1: return "审核通过"; break;
			case 2: return "审核不通过"; break;
			case 3: return "提现成功"; break;
			case 4: return "提现失败"; break;
			case 5: return "已退回"; break;
			default: return "其他";
		}}
		
	function dealSelections(){
		var row = $('#dg').datagrid('getSelections');
		
		var tids = "";
		if(row.length>0){
			//设置tids字符串
			if(row){
				for(var i=0;i<row.length;i++){
					if(i==0) tids = row[i].tid;
						else tids += "," + row[i].tid;
				}
			}
		} else {
			layer.open({title: '提示',content: "请选择要处理的数据"});
			$("#deal").dialog("close");
			return null;
		}
		var newStatus = $('#newStatus').val();
		var url = '<%=path %>/common/tx/cft/deal';
		//调用ajax方法
		//updatestatus(url, tids, newStatus);
		sendRequest(url, {"tids":tids, "status":newStatus}, "json");
		//关闭dialog
		$("#deal").dialog("close");
	}
	
</script>

</html>

