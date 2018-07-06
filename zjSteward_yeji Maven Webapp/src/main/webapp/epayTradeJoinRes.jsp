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
<title>对账表</title>
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
	var datagrid;
	
	var regTimeFrom = ''; //日期from
	var regTimeTo = '';	//日期to
	var mobile = '';	//电话号码
	var searchStatus = ''; //状态查询
	
	$(function() {
		bindDatagrid();
		countMoney(); //统计钱
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
							url : '<%=path %>/epayTradeJoinRes/selectList?regTimeFrom='+regTimeFrom
											+'&regTimeTo='+regTimeTo+'&mobile='+mobile+'&searchStatus='+searchStatus,
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
					{ field: 'status', title: '收款状态',  width: 100, align: 'center', sortable: 'true',
                    	formatter : function(value) {
				            switch(value){
				            	case 0: return "新申请";	break;
				            	case 10: return "已提交收款，待用户短信确认";	break;
				            	case 11: return "收款成功";	break;
				            	case 12: return "收款失败";	break;
				            	case 20: return "已提交还款";	break;
				            	case 21: return "还款成功";	break;
				            	case 22: return "还款失败";	break;
				            	default: return "-";
				            }
				        }
                    },
					{ field: 'orderid', title: '订单流水号', width: 180,  align: 'center', sortable: 'true' },
					{ field: 'amount', title: '收款金额', width: 100, formatter: formatprice, align: 'center', sortable: 'true' },
					{ field: 'master', title: '姓名', width: 100,  align: 'center', sortable: 'true' },
                    //{ field: 'mobile', title: '联系方式', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'skdate', title: '收款日期', width: 200,  align: 'center', sortable: 'true' ,
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
                    
					//分隔
                    { field: ' ', title: '匹配', width: 80,  align: 'center', sortable: 'true',
                    formatter : function(d,obj){
                    	if(obj.amount == obj.r_money){
                    		return "<img style='width:25%;' alt='' src='<%=path %>/js/img/icon_ok_small.png'>";
                    	}else{
                    		return "<img style='width:25%;' alt='' src='<%=path %>/js/img/icon_warn_small.png'>";
                    	}
                    	
                    } },
                    //result表
                    { field: 'r_status', title: '还款状态',  width: 100, align: 'center', sortable: 'true',
                    	formatter : function(value,obj) {
                    		if(obj.amount == obj.r_money){
		                    		switch(value){
					            	case 0: return "新增";	break;
					            	case 1: return "审核通过";	break;
					            	case 2: return "审核不通过";	break;
					            	case 3: return "清分成功";	break;
					            	case 4: return "清分失败";	break;
					            	case 5: return "交易受理成功";	break;
					            	case 6: return "交易受理不通过";	break;
					            	case 7: return "确认提现成功";	break;
					            	case 8: return "确认提现失败";	break;
					            	default: return "-";
					            }
	                    	}else{
	                    		return "";
	                    	}
							
				        }
                    },
					{ field: 'r_orderid', title: '订单流水号', width: 180,  align: 'center', sortable: 'true' },
					{ field: 'r_money', title: '还款金额', width: 100, formatter: formatprice, align: 'center', sortable: 'true' },
					{ field: 'r_acctname', title: '姓名', width: 100,  align: 'center', sortable: 'true' },
                    //{ field: 'r_mobile', title: '联系方式', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'r_statusDate', title: '还款日期', width: 200,  align: 'center', sortable: 'true' ,
					formatter : function(value,obj) {
						if(obj.amount == obj.r_money){
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
	                    	}else{
	                    		return "";
	                    	}
				            
				        }
					},
                    //{ field: 'cardno', title: '信用卡卡号', width: 180,  align: 'center', sortable: 'true' },
					//{ field: 'bank', title: '信用卡银行', width: 100,  align: 'center', sortable: 'true' },
                    //{ field: 'zdmonth', title: '账单日期',  width: 100, align: 'center', sortable: 'true' },
					//{ field: 'acctname', title: '借记卡开户姓名', width: 180,  align: 'center', sortable: 'true' },
					//{ field: 'acctno', title: '借记卡卡号', width: 180,  align: 'center', sortable: 'true' },
					//{ field: 'acctbank', title: '借记卡银行代码', width: 180,  align: 'center', sortable: 'true' },
					//{ field: 'acctbankname', title: '借记卡银行名称', width: 100,  align: 'center', sortable: 'true' },
                    //{ field: 'idno', title: '身份证', width: 180,  align: 'center', sortable: 'true' },
					
                    //{ field: 'remark', title: '备注',  width: 100, align: 'center', sortable: 'true' },
                    //{ field: 'thirdvoucher', title: '批次凭证号', width: 180,  align: 'center', sortable: 'true' },
                    //{ field: 'bussseqno', title: '交易流水号', width: 180,  align: 'center', sortable: 'true' },
                    //{ field: 'ptimes', title: '提交后查询处理次数',  width: 180, align: 'center', sortable: 'true' },
					
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
			displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录'
		});
		
	}

	function formatprice(val, row) {
		return (Number(val).toFixed(2).toString()) == 0 ? "" : (Number(val).toFixed(2).toString());
	}

	//搜索
	function searchdata() {
		regTimeFrom = $('#regTimeFrom').datebox('getValue');
		regTimeTo = $('#regTimeTo').datebox('getValue');
		mobile = $("#mobile").val();
		searchStatus = $("#searchStatus").val();
		bindDatagrid();
		countMoney(); //统计钱
	}
	
	//判断达标时间
       function regTimeSelect() {
           var regTimeFrom = $('#regTimeFrom').datebox('getValue');
           var regTimeTo = $("#regTimeTo").datebox('getValue');
           if (regTimeTo < regTimeFrom && regTimeFrom != "" && regTimeTo != "") {
               $.messager.alert("提示", "截止时间必须大于开始时间", "info");
           }
       }
       
       //统计总额
      function countMoney(){
      	$.ajax({
      		url : '<%=path %>/epayTradeJoinRes/selectCountMoney?regTimeFrom='+regTimeFrom
				+'&regTimeTo='+regTimeTo+'&mobile='+mobile+'&searchStatus='+searchStatus,
			type:'post',
			dataType:'json',
			success:function(data){
				var trade_money = data.trade_money; //trade表的总金额
				var result_money = data.result_money; //result表的总金额
				if(trade_money > 0){
					$('#countMoney1').html(trade_money);
	               } else {
	               	$('#countMoney1').html('0.00');
	               }
              if(result_money > 0){
				$('#countMoney2').html(result_money);
               } else {
               	$('#countMoney2').html('0.00');
               }
			},
			error:function(data){
			 alert('error：' + data);
			},
	      	});
	      }
</script>
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
				
				电话：<input type="text" id="mobile" placeholder="手机号" />&nbsp;&nbsp;
				
				<!-- 状态： --><select id="searchStatus" style="display: none;">
						<option value=''>全部</option>
						<option value=0>新申请</option>
						<option value=10>已提交收款，待用户短信确认</option>
						<option value=11>收款成功</option>
						<option value=12>收款失败</option>
						<option value=20>已提交还款</option>
						<option value=21>还款成功</option>
						<option value=22>还款失败</option>
					</select>
				
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
					 <label id="explorMsg"></label>
				<span>收款总额：</span><span id="countMoney1" style="color:red;">0.00</span>元&nbsp;&nbsp;
				<span>还款总额：</span><span id="countMoney2" style="color:red;">0.00</span>元
			</div>
		</div>
	</form>
	
</body>
</html>

