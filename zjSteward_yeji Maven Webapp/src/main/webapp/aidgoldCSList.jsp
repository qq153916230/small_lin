<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String tid = request.getParameter("tid");
	String mid = request.getParameter("mid");
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>催收管理</title>
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<style type="text/css">

	</style>

</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<form  id="form1">
		
		<table id="dg">
		</table>
		<div style="display:none;">
			<div id="toolbar">
				日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
						&nbsp;至&nbsp;
					<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
					<!-- <input type="text" id="skname" placeholder="收款账户名" />&nbsp;&nbsp; -->
					<!-- <select id="jkcheck">
						<option value=''>全部</option>
						<option value=0>新申请</option>
						<option value=1>通过</option>
						<option value=2>未通过</option>
					</select> -->
					<!-- <input type="text" id="agent" placeholder="代理商" />&nbsp;&nbsp; -->
				
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
				<a href="javascript:;" class="easyui-linkbutton"  onclick="cuishou()">添加催收</a>&nbsp;&nbsp;
					 
 			<!-- <a href="javascript:;" class="easyui-linkbutton" onclick="showstatus()" >冻结/解冻</a> -->
			</div>
		</div>
	</form>
	
	
	<!-- 催收 -->
	<div title="催收" id="cs" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px;" closed="true" buttons="#csbtn" data-mainkey="">
		<form id="csForm" action="<%=path %>/aidgoldCS/createData" method="post" target="#">
			<input id="csaid" name="aid" value="" type="hidden">
			<input id="csmid" name="mid" value="" type="hidden">
			<div>催收模式：
				<select id="csmodo" class="com" name="csmodo" >
					<option value=1>系统警告</option>
					<option value=2>短信警告</option>
					<option value=3>电话警告</option>
				</select>
			</div>
			<!-- <div>催收时间：<input id="csdate" class="easyui-datetimebox com" name="csdate" value=""></div> -->
			<div>
				<span class="tit">催收内容：</span>
				<textarea id="cscont" class="com" name="cscont" placeholder="100字以内" cols="30" rows="5" maxlength="100"></textarea>
			</div>
			<div>
				<span class="tit">添加备注：</span>
				<textarea id="remark" class="com" name="remark" placeholder="50字以内" cols="30" rows="4" maxlength="50"></textarea>
			</div>
		</form>
	</div>
	<div id="csbtn">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="csConfirm()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#cs').dialog('close');">取消</a>
	</div>
	
	<!-- 修改状态 -->
	<div title="处理" id="updateStatus" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		变更状态为：
			<select id="newStatus">
				<option value=0>冻结</option>
				<option value=1>正常</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updatestatus()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	
</body>

<script type="text/javascript">
	var datagrid;
	var aid = getQueryString("tid");	//获取要查询催收记录的tid
	
	var regTimeFrom = ''; //日期from
	var regTimeTo = '';	//日期to
	var skname = '';	//收款账户名
	var jkcheck = ''; //审核
	
	var tid = '';	//助力金id
	var mid = '';	//用户mid
	
	$(function() {
		bindDatagrid();
	});
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
							url : '<%=path %>/aidgoldCS/selectList?regTimeFrom='+regTimeFrom
									+'&regTimeTo='+regTimeTo+'&skname='+skname+'&aid='+aid+'&jkcheck='+jkcheck,
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : false, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							
							pagination : true, //分页  
							rownumbers : true,//行数 
							
							remoteSort : false,
							columns : [ [
					{ field: '', checkbox: 'true' },
					{ field: 'tid', title: 'tid', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'mid', title: '用户id', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'aid', title: '助力金id', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'csmodo', title: '催收模式',  width: 100, align: 'center', 
						formatter : function(value) {
							switch(value){
							case 1: return "系统催收";	break;
							case 2: return "短信催收";	break;
							case 3: return "电话催收";	break; } } },
					{ field: 'csdate', title: '催收日期', width: 200,  align: 'center', sortable: 'true' , formatter : formatTime },
					{ field: 'cscont', title: '催收内容', width: 500,  align: 'left' },
					{ field: 'remark', title: '备注', width: 280,  align: 'left' },
					
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
	
	//催收
	function cuishou(){
		
		document.getElementById("cscont").value = "";
		document.getElementById("remark").value = "";
		
		$("#cs").dialog("open");
		tid = <%=tid %>; 
		mid = <%=mid %>;
		document.getElementById("csaid").value = tid;
		document.getElementById("csmid").value = mid;
	}
	//确认催收
	function csConfirm(){
		if(true){
			//document.getElementById("csForm").submit();	//提交表单内容
			var caid = $('#csaid').val();
			var cmid = $('#csmid').val();
			var csmodo = $('#csmodo').val();
			var cscont = $('#cscont').val();
			var cremark = $('#remark').val();
			$.ajax({
				type:"post",
				url:"<%=path %>/aidgoldCS/createData",
				data:{"aid":caid,"mid":cmid,"csmodo":csmodo,"cscont":cscont,"remark":cremark},
				dataType: "",
				success:function(){
					bindDatagrid();
				},
				error:function(){
				}
			});
			
			//催收次数 +1
			$.ajax({
				type:"post",
				url:"<%=path%>/aidgold/addcs",
				data:'tid=' + tid,
				dataType: "",
				success:function(){
					bindDatagrid();
				},
				error:function(){
				}
			});
		} else {
			$.messager.alert("提示", "请选择要处理的记录", "info");
		}
		$("#cs").dialog("close");
	};
	
	//格式化金钱
	function formatprice(value, row) {
		return Number(value).toFixed(2).toString();
	}
	//格式化金钱
	function formatRate(value, row) {
		return Number(value).toFixed(4).toString();
	}
	//格式化时间
	function formatTime(value, row){
		var date = new Date(value);
		var year = date.getFullYear().toString();
		var month = (date.getMonth() + 1);
		var day = date.getDate().toString();
		var hour = date.getHours().toString();
		var minutes = date.getMinutes().toString();
		var seconds = date.getSeconds().toString();
		if (month < 10) {
		    month = "0" + month;
		}if (day < 10) {
		    day = "0" + day;
		}if (hour < 10) {
		    hour = "0" + hour;
		}if (minutes < 10) {
		    minutes = "0" + minutes;
		}if (seconds < 10) {
		    seconds = "0" + seconds;
		}
		return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
	}

	//搜索
	function searchdata() {
		regTimeFrom = $('#regTimeFrom').datebox('getValue');
		regTimeTo = $('#regTimeTo').datebox('getValue');
		skname = $("#skname").val();
		jkcheck = $("#jkcheck").val();
		
        bindDatagrid();
	}
	
	//打开弹出框
	function showstatus(){
		$("#updateStatus").dialog("open");
	}
	function updatestatus(){
		var row = $('#dg').datagrid('getSelections');
		var newStatus = $('#newStatus').val();
		if(row.length>0){
			var mids = "";
			if(row){
				for(var i=0;i<row.length;i++){
					if(i==0) mids = row[i].mid;
						else mids += "," + row[i].mid;
				}
			}
			$.ajax({
				type:"post",
				url:"<%=path %>/agent/updateStatus",
				data:'mids=' + mids + '&newStatus=' + newStatus,
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
					alert("出错了！");
				}
			});
			
		} else {
			$.messager.alert("提示", "请选择要处理的记录", "info");
		}
		$("#updateStatus").dialog("close");
	}
	
	//判断达标时间
    function regTimeSelect() {
        var regTimeFrom = $('#regTimeFrom').datebox('getValue');

        var regTimeTo = $("#regTimeTo").datebox('getValue');

        if (regTimeTo < regTimeFrom && regTimeFrom != "" && regTimeTo != "") {

            $.messager.alert("提示", "截止时间必须大于开始时间", "info");
        }
    }
    //获取url的查询参数
	function getQueryString(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r!=null)return unescape(r[2]); return null;
	}
	
	
</script>

</html>

