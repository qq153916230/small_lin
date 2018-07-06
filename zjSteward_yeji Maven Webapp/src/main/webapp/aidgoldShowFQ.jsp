<%@ page language="java" import="java.util.*,com.app.util.ReadHTMLFilePath" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String tid = request.getParameter("tid");
	String managerPass = new ReadHTMLFilePath().getHTMLFilePath("managerPass");	//配置文件的密码
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分期详情</title>
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
		&nbsp;&nbsp;
		<!-- <a href="javascript:;" class="easyui-linkbutton"  onclick="hkOpen(1,0)" style="margin: 2px;">全额还款</a> -->
		<a href="javascript:;" class="easyui-linkbutton"  onclick="resetFQList()" style="margin: 2px;">重置列表</a>
		<!-- 内容显示表 -->
		<table id="dg"></table>
		
		<!-- 修改状态2 -->
		<div title="用户已还款" id="hkConfirm" class="easyui-dialog" style="width:300px;height:140px;padding:10px 20px" closed="true" buttons="#hk_btn" data-mainkey="">
			
			<div id="" style="margin-top: 3px;">
				<span style="color: red;">管理员密码：</span>
				<input id="comfirmPassInp" type="password">
				<div id="passMsg" style="color:red; margin-top: 5px;"></div>
			</div>
		</div>
		<div id="hk_btn">
			<a id="agreeBtn" href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="hkAlready()">已还款</a>
			<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#hkConfirm').dialog('close');">取消</a>
		</div>
		
</body>

<script type="text/javascript">

	var hkType;	// 还款类型，分期/全额
	var sno;	//分期期数
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?tid="+<%=tid %>;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/aidgoldFQHK/showFQ' + searchCodition,
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : false, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							remoteSort : false,
							//pagination : true, //分页  
							//rownumbers : true,//行数 
							columns : [ [
					//{ field: '', checkbox: 'true' },
					{ field: 'rid', title: 'rid', hidden:'true'},
					{ field: 'status', title: '状态', width: 100,  align: 'center', formatter:formatHKstatus},
					{ field: 'sno', title: '分期期数', width: 80,  align: 'center'},
					{ field: 'hkmoney', title: '需还款金额', width: 100, align: 'center',formatter : formatprice},
                    { field: 'everymoney', title: '分期本金', width: 100, align: 'center',formatter : formatprice},
                    { field: 'fee', title: '利息', width: 100,  align: 'center',formatter :formatprice},
					{ field: 'hkdate', title: '还款日期', width: 150,  align: 'center',formatter : formatDate},
							] ],
						});
					//设置分页控件 
					/* var p = $('#dg').datagrid('getPager');
					$(p).pagination({
						beforePageText : '第', //页数文本框前显示的汉字 
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录',
					}); */
	}
	//格式化类型
	function formatHKstatus(val,obj){
		switch(val){
			case 0: return "<a href='javascript:;' class='easyui-linkbutton'  onclick='hkOpen(2,"+obj.sno+")'>未还款</a>"; break;
			case 1: return "<img style='width:20px;' src='<%=path %>/js/img/icon_ok_small.png'>"; break;
		}}
		
	//	打开 分期/全额还款 弹出框
	function hkOpen(type,hksno){
		$('#passMsg').html("");	//清空密码提示
		$('#comfirmPassInp').val("");
		$("#hkConfirm").dialog("open");
		hkType = type;	//给还款类型赋值
		sno = hksno;
	}
	//	确认已还款按钮
	function hkAlready(){
		if($('#comfirmPassInp').val() == "<%=managerPass %>" ){
			
			if(hkType == 1){
				sno = 0;
			}
			var url = "<%=path %>/aidgoldFQHK/updateStatus?tid="+<%=tid %> + "&sno="+sno +"&hkType="+hkType;
			$.ajax({
				type:"post",
				url:url,
				dataType:"json",
				complete:function(){},//ajax请求完成时隐藏loading提示
				success:function(data){
					if(data.ret=1){
						$.messager.alert("提示",data.msg,"info");
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("提示",data.msg,"info");
					}
				},
				error:function(){
				}
			});
			$('#hkConfirm').dialog('close');
		} else{
			$('#passMsg').html("密码错误！");
		}
	}
	
	//重置分期列表
	function resetFQList(){
		var url = "<%=path %>/aidgoldFQHK/deleteFQList?tid="+<%=tid %>;
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			complete:function(){},//ajax请求完成时隐藏loading提示
			success:function(data){
				if(data.status=11){
					$.messager.alert("提示",data.msg,"info");
					$('#dg').datagrid('reload');
				} else {
					$.messager.alert("提示",data.msg,"info");
				}
			},
			error:function(){
			}
		});
	}
	
</script>

</html>

