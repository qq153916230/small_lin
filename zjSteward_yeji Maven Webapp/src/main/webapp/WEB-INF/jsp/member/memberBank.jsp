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
<title>用户银行卡</title>
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
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<!-- 内容显示表 -->
	<table id="dg"></table>
	<!-- 操作 -->
	<div id="toolbar">&nbsp;&nbsp;
		<input type="text" class="condition" id="sname" placeholder="姓名" />&nbsp;&nbsp;
		<input type="text" class="condition" id="mobile" placeholder="手机号" />&nbsp;&nbsp;
		
		<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="openUpdateBank()">新增/修改</a>
	</div>
	<!-- 新增/更新银行 -->
	<div title="新增/更新" id="updateBank" class="easyui-dialog" style="width:350px;height:250px;padding:10px 20px" closed="true" buttons="#updateBankBtn" data-mainkey="">
		用户姓名：<input type="text" style="width:180px" id="updateUserName"/><br><br>
		手机号码：<input type="text" style="width:180px" id="updateMobile"/><br><br>
		银行卡号：<input type="text" style="width:180px" id="updateBankAccount"/><br><br>
		银行名称：<input type="text" style="width:180px" id="updateBankName"/><br><br>
	</div>
	<div id="updateBankBtn">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="saveBank()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateBank').dialog('close');">取消</a>
	</div>
</body>

<script type="text/javascript">

	var mobile = '';
	var sname = '';
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?mobile="+mobile+"&userName="+sname;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/common/member/bank/list/pro' + searchCodition,
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
                    { field: 'mid', title: '用户编号', width: 80,  align: 'center'},
					{ field: 'mobile', title: '手机', width: 100,  align: 'center'},
					{ field: 'account_name', title: '开户名', width: 80,  align: 'center'},
					{ field: 'account_number', title: '借记卡卡号', width: 210,  align: 'center'},
					{ field: 'bank_open', title: '开户行', width: 240,  align: 'center'},
                    { field: 'isauth', title: '实名', width: 80, align: 'center',formatter : formatIsauth},
					{ field: 'gdate', title: '日期', width: 150,  align: 'center',formatter : formatTime},
					
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
	
	//搜索
	function searchdata() {
		mobile = $("#mobile").val();
		sname = $("#sname").val();
		
		bindDatagrid();	//刷新页面
	}
	
	var bankSaveType; // 0 保存 ， 1 更新
	//打开弹出框
	function openUpdateBank(){
		
		$('#updateBank').dialog('close');
		
		var row = $('#dg').datagrid('getSelections');
		if(row.length > 1){
			alert("一次只能更新一条数据");
			return;
		}
		if (row.length == 1) {
			var mid = row[0].mid;
			$('#updateMobile').val(row[0].mobile);
			$('#updateBankAccount').val(row[0].account_number);
			$('#updateBankName').val(row[0].bank_open);
			$('#updateUserName').val(row[0].account_name);
			bankSaveType = 1;	//  更新
		} else {
			$('#updateMobile').val('');
			$('#updateBankAccount').val('');
			$('#updateBankName').val('');
			bankSaveType = 0;	// 保存 
		}
		$("#updateBank").dialog("open");
	}
	
	//保存银行
	function saveBank(){	// 0 保存 ， 1 更新
		var mobile = $('#updateMobile').val();
		var bankAccount = $('#updateBankAccount').val();
		var bankName = $('#updateBankName').val();
		var userName = $('#updateUserName').val();
		
		$.ajax({
			url: '<%=path %>/common/member/bank/save',
			type: 'post',
			data: {"mobile":mobile, "bankAccount":bankAccount, "bankName":bankName, "bankSaveType":bankSaveType, "userName":userName},
			dataType: 'json',
			success: function (json){
				alert(json.msg);
				$('#updateBank').dialog('close');
				bindDatagrid();	//刷新页面
			},
			error: function (){
				alert("哎呀，请求被外星人劫持了，请与后台管理员联系");
			}
		})
	}
	
	//格式化实名
	function formatIsauth(val){
		switch(val){
			case 0: return "否"; break;
			case 1: return "是"; break;
			default: return "其他";
		}
	}
	
</script>

</html>

