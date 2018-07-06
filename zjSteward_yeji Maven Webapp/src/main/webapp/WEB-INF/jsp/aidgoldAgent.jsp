<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String shouquan = (String)request.getSession().getAttribute("shouquan");	//授权按钮
	if(!shouquan.equals("")){
		shouquan = "{ field: 'sq', title: '授权', width: 80,  align: 'center', formatter: sqButton}";
	}
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代理商额度</title>
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
	<!-- 内容显示表 -->
	<table id="dg"></table>
	<!-- 操作 -->
	<div style="display:none;">
		<div id="toolbar">
			日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
					&nbsp;至&nbsp;
					<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
			
			<input type="text" class="condition" id="mid" placeholder="代理商编号" />&nbsp;&nbsp;
			<input type="text" class="condition" id="sname" placeholder="姓名" />&nbsp;&nbsp;
			<input type="text" class="condition" id="mobile" placeholder="手机" />&nbsp;&nbsp;
			
			<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
		</div>
	</div>
	
	
	<!-- 弹出一个网页 -->
	<div title="提示" id="showPageWrap" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="showPageContent" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	
	<!-- 修改代理商总额度/台数 -->
	<div title="提示" id="dialogWrap" class="easyui-dialog" style="width:300px;height:200px;padding:10px 20px" closed="true" buttons="#dialogWrapBtn" data-mainkey="">
		<div id="msgHead" style="color: red;">请输入增加或扣除(负数)的金额</div>	&nbsp;
		<div id="moneyOpe"><span>金额：</span><input id="moneyOpeIn"></div>&nbsp;
		<div id="posNumOpe"><span>台数：</span><input id="posNumOpeIn"></div>&nbsp;
		<div id="fapeiMobile"><span>手机：</span><input id="fapeiMobileIn" placeholder="额度/台数 分配到此手机"></div>
	</div>
	<div id="dialogWrapBtn">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="dialogWrapYes()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#dialogWrap').dialog('close');">取消</a>
	</div>
	
</body>

<script type="text/javascript">

	var mid;
	
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo+"&mid="+mid+"&nick="+sname+"&mobile="+mobile;
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/aidgoldAgent/selectList' + searchCodition,
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
                    { field: 'mid', title: '代理商编号', width: 80,  align: 'center'},
                    { field: 'nick', title: '姓名', width: 80,  align: 'center'},
                    { field: 'email', title: '邮箱', width: 140,  align: 'center'},
                    
                    { field: 'balance', title: '账户余额', width: 100,  align: 'center',formatter :formatprice},
                    { field: 'txreward', title: '总提现分润', width: 100,  align: 'center',formatter :formatprice},
                    { field: 'txservice', title: '总提现服务费', width: 100,  align: 'center',formatter :formatprice},
                    
                    { field: 'interest', title: '总利息分润', width: 80,  align: 'center',formatter :formatprice},
                    { field: 'poundage', title: '总手续费', width: 80,  align: 'center',formatter :formatprice},
                    { field: 'agentrate', title: '扣率', width: 80,  align: 'center',formatter :fAgentrate},
                    { field: 'bridgegd', title: '过桥押金', width: 100,  align: 'center',formatter :fbridgegd},
                    { field: 'totalgd', title: '总额度', width: 100,  align: 'center',formatter :formatTotalgd},
                    { field: 'usedgd', title: '已用额度', width: 100,  align: 'center',formatter :formatprice},
					{ field: 'gdate', title: '日期', width: 200,  align: 'center',formatter : formatTime},
					{ field: 'history', title: '历史记录', width: 140,  align: 'center',formatter : formatHistory},
					<%=shouquan %>
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
	//格式化金钱
	function formatTotalgd(value, row) {
		var money = Number(value).toFixed(2).toString();
		return "<a href='javascript:;' onclick=operate("+row.mid+")>"+money+"</a>";
	}
	function operate(opmid){
		mid = opmid;
		$("#moneyOpeIn").val("0");
		$("#posNumOpeIn").val("0");
		$("#fapeiMobileIn").val("");
		$("#dialogWrap").dialog("open");
		
	}
	
	//格式化历史记录
	function formatHistory(value, row) {
		return "<a href='javascript:;' onclick=showHistory("+row.mid+")>历史记录</a>";
	}
	function showHistory(shmid){
		mid = shmid;
		var url = '<%=path %>/aidgoldAgentLog.jsp?mid='+mid;
		//window.$("#showPageWrap").removeAttr("title");
		//window.$("#showPageWrap").attr("title", "历史记录");
		window.$("#showPageContent").removeAttr("src");
		window.$("#showPageContent").attr("src", url);
		window.$("#showPageWrap").dialog('open');
		
	}
	
	function dialogWrapYes(){
		$('#dialogWrap').dialog('close');
		var opeMoney = $("#moneyOpeIn").val();
		var opePosNum = $("#posNumOpeIn").val();
		var fapeiMobile = $("#fapeiMobileIn").val();
		if(opeMoney >= 0 || opeMoney <= 0){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgoldAgent/updateTotalgd",
				dataType:"json",
				data : {"mid":mid,"opeMoney":opeMoney,"opePosNum":opePosNum,"mobile":fapeiMobile},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '修改成功'});
						$('#dg').datagrid('reload');
					} else{
						layer.open({title: '提示',content: '修改失败'});
					}
				},
				error : function() {
					layer.open({title: '提示',content: '修改失败'});
				}
				});
			} else {layer.open({title: '提示',content: '请输入要增加或减少的金额(整数或负数)'}); }
	}
	
	function sqButton(val, obj){
		return "<a href='javascript:;' onclick='agentSQ("+obj.mid+")'>授权</a>";
	}
	//扣率按钮
	function fAgentrate(val, obj){
		return "<a href='javascript:;' onclick='agentrateFun("+obj.mid+")'>"+val+"</a>";
	}
	//过桥押金
	function fbridgegd(val, obj){
		var money = Number(val).toFixed(2).toString();
		return "<a href='javascript:;' onclick='fbridgegdFun("+obj.mid+")'>"+money+"</a>";
	}
	
	function agentSQ(mid){
		layer.prompt({title:"代理商邮箱",value:""},function(email, index, elem){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgoldAgent/updateMemberEmail",
				dataType:"json",
				data : {"mid":mid,"email":email},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '修改成功'});
						$('#dg').datagrid('reload');
					} else{
						layer.open({title: '提示',content: '修改失败'});
					}
				},
				error : function() {
					layer.open({title: '提示',content: '修改失败'});
				}
			});
			layer.close(index);
		});
	}
	function agentrateFun(mid){
		layer.prompt({title:"代理商扣率(整数)",value:""},function(value, index, elem){
			if("10" == value || "14" == value || "18" == value || "20" == value){
				$.ajax({
					type : "post",
					url : "<%=path %>/aidgoldAgent/updateAgentrate",
					dataType:"json",
					data : {"mid":mid,"agentrate":value},
					success : function(data) {
						if(data.status == 1){
							layer.open({title: '提示',content: '修改成功'});
							$('#dg').datagrid('reload');
						}
					}
				});
			} else {
				layer.open({title: '提示',content: '有效数字：10、14、18或20'});
			}
			layer.close(index);
		});
	}
	
	function fbridgegdFun(mid){
		layer.prompt({title:"请输入要增加或扣除(负数)的押金",value:""},function(value, index, elem){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgoldAgent/updateAgentBridgegd",
				dataType:"json",
				data : {"mid":mid,"bridgegd":value},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '修改成功'});
						$('#dg').datagrid('reload');
					}
				}
			});
			layer.close(index);
		});
	}
	
</script>

</html>

