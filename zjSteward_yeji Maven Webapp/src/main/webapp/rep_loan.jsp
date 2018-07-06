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
<title>惠龙贷</title>
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/uploadify/uploadify.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<script src="<%=path %>/js/uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="<%=path %>/js/Common.js" type="text/javascript"></script>
	<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	color: #666;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

#cardEdit input,select {
	width: 90px;
}
</style>
<script type="text/javascript">
	var stype = request("smonth");
	var datagrid;
	var showCount = "showNewFirst";//设置showCount不为空，首次展示只显示新申请的列表
	
	var regTimeFrom = ''; //日期from
	var regTimeTo = '';	//日期to
	var phone = '';	//电话号码
	var searchStatus = '';	//审核状态 通过/不通过
	
	$(function() {
		bindDatagrid();
	});
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		var stype = $("#hiddenstype").val();
		datagrid = $('#dg').datagrid({
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/loan/selectNewRegister?regTimeFrom='+regTimeFrom
											+'&regTimeTo='+regTimeTo+'&phone='+phone+'&searchStatus='+searchStatus,
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : false, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							remoteSort : false,
							columns : [ [
					{ field: '', checkbox: 'true' },
					{ field: 'tid', title: 'tid', width: 100,  align: 'center', sortable: 'true' ,hidden:'true'},
                    { field: 'status', title: '状态',  width: 100, align: 'center', sortable: 'true',
                    	formatter : function(value) {
				            switch(value){
				            	case 1: return "受理中";	break;
				            	case 2: return "通过";	break;
				            	case 3: return "未通过";	break;
				            	default: return "新申请";
				            }
				        }
                    },
                    { field: 'uname', title: '姓名', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'phone', title: '联系方式', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'work', title: '工作性质',  width: 200, align: 'center', sortable: 'true' },
                    { field: 'house', title: '房产信息',  width: 100, align: 'center', sortable: 'true' },
                    
					{ field: 'regdate', title: '日期', width: 200,  align: 'center', sortable: 'true' ,
					formatter : function(value) {
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
					},
                  

							] ],

							pagination : true, //分页  
							rownumbers : true,//行数 

						});
		//$('#dg').datagrid('', 'resetimage');

		//设置分页控件 
		var p = $('#dg').datagrid('getPager');
		$(p).pagination({
			beforePageText : '第', //页数文本框前显示的汉字 
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录',
			onSelectPage:function(pageNumber, pageSize){
		      /* alert('pageNumber:'+pageNumber+',pageSize:'+pageSize); */
				regTimeFrom = $('#regTimeFrom').datebox('getValue');
				regTimeTo = $('#regTimeTo').datebox('getValue');
				phone = $("#phone").val();
				searchStatus = $("#searchStatus").val();
		        var CountJson = { "regTimeFrom": regTimeFrom,  "regTimeTo": regTimeTo , "phone":phone, "searchStatus":searchStatus,
		        						"page":pageNumber,"rows":pageSize};
		
				$.ajax({
					type : "post",
					url : "<%=path %>/loan/search",
					data : CountJson,
					dataType : "json",
					complete : function() {
					}, //AJAX请求完成时隐藏loading提示
					success : function(data) {
						datagrid.datagrid('loadData', data);
					},
					error : function() {
					}

				});
			}
		});
		//隐藏ID列
		//$("#dg").datagrid('hideColumn', "editimage");
		//$("#dg").datagrid('hideColumn', "mid");
		// var columns = $("#dg").datagrid("Columns");
		
		showCount = ''; //关闭首次打开页面只展示新申请的列表
	}

 
	

	function formatprice(val, row) {
		return Number(val).toFixed(2).toString();
	}

	//搜索
	function searchdata() {
		regTimeFrom = $('#regTimeFrom').datebox('getValue');
		regTimeTo = $('#regTimeTo').datebox('getValue');
		phone = $("#phone").val();
		searchStatus = $("#searchStatus").val();
        var CountJson = { "regTimeFrom": regTimeFrom,  "regTimeTo": regTimeTo , "phone":phone, "searchStatus":searchStatus};

		$.ajax({
			type : "post",
			url : "<%=path %>/loan/search",
			data : CountJson,
			dataType : "json",
			complete : function() {
			}, //AJAX请求完成时隐藏loading提示
			success : function(data) {
				datagrid.datagrid('loadData', data);
			},
			error : function() {
			}

		});

	}
	
	
	//导出
	function OutExplor() {
		var tids = "";
		var row = $('#dg').datagrid('getSelections');
		if (row) {
			for (var i = 0; i < row.length; i++) {
				if (i == 0)
					tids = row[i].Tid;
				else
					tids += "," + row[i].Tid;
			}
		}
		load("正在导出数据，请稍等........");
		$.ajax({
			type : "post",
			url : "<%=path %>/loan/export",
			dataType:"json",
			data : '',
					/* + $("#keyWords").val() *//* action=memlist&Execl=dataexplor&keyWords= */
			complete : function() {
			}, //AJAX请求完成时隐藏loading提示
			success : function(data) {
				disLoad();
				if (data == "error") {
					$('#dg').datagrid('reload');
					$.messager.alert("导出失败");

				} else {
					
					//$.messager.alert("导出成功");
					location.href="<%=path %>/download/"+data.file; /* http://localhost:8080/zjSteward_yeji */

				}

			},
			error : function() {
				disLoad();
			}
		});
	}
	
	//打开弹出框
	function showstatus(){
		$("#updateStatus").dialog("open");
	}
	function updatestatus(){
		var row = $('#dg').datagrid('getSelections');
		var newStatus = $('#newStatus').val();
		if(row.length>0 && newStatus.length>0){
			/* var tid = row[0].tid;
			var json = {"tid":tid,"newStatus":newStatus}; */
			var tids = "";
			if(row){
				for(var i=0;i<row.length;i++){
					if(i==0) tids = row[i].tid;
						else tids += "," + row[i].tid;
				}
			}
			load(); 
			$.ajax({
				type:"post",
				url:"<%=path %>/loan/updateStatus",
				data:'tids=' + tids + '&newStatus=' + newStatus,
				dataType:"json",
				complete:function(){},//ajax请求完成时隐藏loading提示
				success:function(data){
					if(data.ret=1){
						$.messager.alert("提示",data.msg,"info");
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("提示",data.msg,"info");
					}
					disLoad();
				},
				error:function(){
					disLoad();
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
</script>
</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<form  id="form1">
		
		<table id="dg">
		</table>
		<div style="display:none;">
			<div id="toolbar">
				状态：<select id="searchStatus">
						<option value=0>新申请</option>
						<option value=1>受理中</option>
						<option value=2>审核通过</option>
						<option value=3>审核未通过</option>
						<option value=''>全部</option>
					</select>

				日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" data-options="onSelect:regTimeSelect"/>
						&nbsp;至&nbsp;
						<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" data-options="onSelect:regTimeSelect"/>&nbsp;&nbsp;
				
				电话：<input type="text" id="phone" placeholder="商户号/手机号" />&nbsp;&nbsp;
				
					
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
					 <label id="explorMsg"></label>
					 
 			<a href="javascript:;" target="_blank" class="easyui-linkbutton" onclick="OutExplor()" >导出</a>&nbsp;&nbsp;
 			<a href="javascript:;" class="easyui-linkbutton" onclick="showstatus()" >处理</a>
			</div>
		</div>
	</form>
	
	<!-- 显示提醒处理窗口 -->
	<div title="处理" id="updateStatus" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		变更状态为：
			<select id="newStatus">
				<!-- <option value=0>新申请</option> -->
				<option value=1>受理中</option>
				<option value=2>审核通过</option>
				<option value=3>审核未通过</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updatestatus()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	
	
</body>
</html>

