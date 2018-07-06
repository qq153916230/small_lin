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
<title>上传</title>
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/uploadify/uploadify.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="<%=path %>/js/Common.js" type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery.form.js"type="text/javascript"></script>
	<style type="text/css"></style>
<script type="text/javascript">
	var stype = request("smonth");
	var datagrid;
	
	var searchStatus = '';	//审核状态 通过/不通过
	
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
							url : 'uploadExcel/selectAll.do',
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : false, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							remoteSort : false,
							columns : [ [
					{ field: '', checkbox: 'true' },
					{ field: 'sid', title: 'sid', width: 100,  align: 'center', sortable: 'true' ,hidden:'true'},
					{ field: 'ischeck', title: '状态',  width: 100, align: 'center', sortable: 'true',
                    	formatter : function(value) {
				            switch(value){
				            	case true: return "通过";	break;
				            	case false: return "未通过";	break;
				            	default: return "新申请";
				            }
				        }
                    },
					{ field: 'merchantname', title: '商户名', width: 80,  align: 'center', sortable: 'true' },
                    { field: 'merchantcode', title: '商户号', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'skcard', title: '收款卡号', width: 120,  align: 'center', sortable: 'true' },
					{ field: 'paymoney', title: '交易金额', width: 80,  align: 'center', sortable: 'true' },
					{ field: 'paydate', title: '交易日期', width: 160,  align: 'center', sortable: 'true' ,
					formatter : function(value) {
				            var date = new Date(value);
				            var year = date.getFullYear().toString();
				            var month = (date.getMonth() + 1);
				            var day = date.getDate().toString();
				            var hour = date.getHours().toString();
				            var minutes = date.getMinutes().toString();
				            if (month < 10) { month = "0" + month;
				            }if (day < 10) { day = "0" + day;
				            }if (hour < 10) { hour = "0" + hour;
				            }if (minutes < 10) { minutes = "0" + minutes;}
				            return year + "-" + month + "-" + day/*  + " " + hour + ":" + minutes  */;
				        }
					},
                    { field: 'result', title: '消费结果',  width: 80, align: 'center', sortable: 'true' },
                    { field: 'shrate', title: '商户费率',  width: 100, align: 'center', sortable: 'true' },
                    { field: 'handcharge', title: '商户总手续费',  width: 100, align: 'center', sortable: 'true' },
                    { field: 'jsprice', title: '商户结算金额',  width: 100, align: 'center', sortable: 'true' },
                    { field: 'agent', title: '代理商',  width: 100, align: 'center', sortable: 'true' },
                    { field: 'skbank', title: '收款银行',  width: 100, align: 'center', sortable: 'true' },
                    { field: 'posno', title: '刷卡号',  width: 120, align: 'center', sortable: 'true' },
                    { field: 'possn', title: '机身号',  width: 120, align: 'center', sortable: 'true' },
                    { field: 'modo', title: '模式',  width: 80, align: 'center', sortable: 'true' },
                    { field: 'txrate', title: '提现费',  width: 80, align: 'center', sortable: 'true' }
							] ],
							pagination : true, //分页 
							rownumbers : true,//行数 
						});
					//设置分页控件 
					var p = $('#dg').datagrid('getPager');
					$(p).pagination({
						beforePageText : '第', //页数文本框前显示的汉字 
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录'
					});
	}
	
	//格式化金钱
	function formatprice(val, row) {
		return Number(val).toFixed(2).toString();
	}
	
	//打开弹出框
	function showstatus(){
		$("#updateStatus").dialog("open");
	}
	function updatestatus(){
		var row = $('#dg').datagrid('getSelections');
		var newStatus = $('#newStatus').val();
		if(row.length>0 && newStatus.length>0){
			var sids = "";
			if(row){
				for(var i=0;i<row.length;i++){
					if(i==0) sids = row[i].sid;
						else sids += "," + row[i].sid;
				}
			}
			load(); 
			$.ajax({
				type:"post",
				url:"uploadExcel/updateStatus.do",
				data:'sids=' + sids + '&newStatus=' + newStatus,
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
</script>
</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<!-- <form  id="form1">
		
		
	</form> -->
	<form method="POST"  enctype="multipart/form-data" id="form1" action="uploadExcel/upload.do"> 
        <table>  
         <tr>  
         	<div id="toolbar">
	            <td>上传文件: </td>  
	            <td> <input id="upfile" type="file" name="upfile" style="border:1px solid #A5A5A5;width:80%;margin-left:20px;" ></td>  
	            <td><input type="button" value="导入Excel" id="btn" name="btn" ></td>  
	            <td><input type="button" value="处理"  onclick="showstatus()" ></td>
	            <td><a href="<%=path %>/download/temp.xls">下载模板</a></td>
            </div> 
         </tr>
        </table>  
        
        <!-- 内容显示区 -->  
        <table id="dg"></table>
        
    </form>  
	
	<!-- 显示提醒处理窗口 -->
	<div title="处理" id="updateStatus" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		变更状态为：
			<select id="newStatus">
				<!-- <option value=0>新申请</option> -->
				<option value=1>审核通过</option>
				<option value=0>审核未通过</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updatestatus()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	
	
</body>

<script type="text/javascript">  
            //ajax 方式上传文件操作  
             $(document).ready(function(){  
                $('#btn').click(function(){  
                    if(checkData()){  
                        $('#form1').ajaxSubmit({    
                            url:'uploadExcel/ajaxUpload.do',  
                            dataType: 'json',  
                            success: resutlMsg,  
                            error: errorMsg  
                        });   
                        function resutlMsg(date){
                        	
                        	datagrid.datagrid('loadData', date);
                            alert("文件导入成功！");     
                            $("#upfile").val("");  
                        }  
                        function errorMsg(){   
                            alert("导入excel出错！");      
                        }  
                    }  
                });  
             });  
               
             //JS校验form表单信息  
             function checkData(){  
                var fileDir = $("#upfile").val();  
                var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
                if("" == fileDir){  
                    alert("选择需要导入的Excel文件！");  
                    return false;  
                }  
                if(".xls" != suffix && ".xlsx" != suffix ){  
                    alert("选择Excel格式的文件导入！");  
                    return false;  
                }  
                return true;  
             }  
    </script>

</html>

