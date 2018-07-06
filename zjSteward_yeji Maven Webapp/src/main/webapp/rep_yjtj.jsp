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
<title>业绩统计列表</title>
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
	var sytpe = request("smonth");
	var datagrid;
	$(function() {
		bindDatagrid();
	})
	function bindDatagrid() {
		var sheight = document.documentElement.clientHeight - 10 + "px";
		var stype = $("#hiddenstype").val();
		datagrid = $('#dg')
				.datagrid(
						{
							width : "100%",
							height : sheight,
							pageSize : 100, //默认选择的分页是每页5行数据  
							pageList : [ 100, 200, 500 ], //可以选择的分页集合  
							nowrap : true, //设置为true，当数据长度超出列宽时将会自动截取  
							striped : true, //设置为true将交替显示行背景。  
							collapsible : false, //显示可折叠按钮  
							toolbar : "#toolbar", //在添加 增添、删除、修改操作的按钮要用到这个  
							url : '<%=path %>/daystatic/list?smonth=' + formatterDate(new Date()),
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : false, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							onLoadSuccess : compute,//加载完毕后执行计算
							remoteSort : false,
							columns : [ [
					/* formatter: function (value, row, index) { return value.replace("00:00:00",""); }, */
					{ field: 'gdate', title: '日期', width: 100,  align: 'center', sortable: 'true' ,
					formatter : function(value) {
				            var date = new Date(value);
				            var year = date.getFullYear().toString();
				            var month = (date.getMonth() + 1);
				            var day = date.getDate().toString();
				            if (month < 10) {
				                month = "0" + month;
				            }if (day < 10) {
				                day = "0" + day;
				            }if(value = null || value == ''){
				            	return '<b>合计：</b>';
				            }else{
				            	return year + "-" + month + "-" + day ;
				            }
				        }
					},
					/* { field: 'gdate', title: '日期', width: 100, formatter: function (value, row, index) { return value.replace("00:00:00",""); }, align: 'center', sortable: 'true' }, */
                    { field: 't0Paymoney', title: 'T0交易金额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 't0Paynum', title: 'T0交易笔数', width: 100,  align: 'center', sortable: 'true' },
                    { field: 't1Paymoney', title: 'T1交易金额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 't1Paynum', title: 'T1交易笔数', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'payTotle', title: '总交易金额', width: 100, formatter:formatprice, align: 'right', sortable: 'true' },
                    { field: 'moneyBt', title: '补贴总额', width: 100, formatter:formatprice, align: 'right', sortable: 'true' },
                    { field: 'moneyTx', title: '提现总额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 'countTx', title: '提现笔数', width: 100, align: 'center', sortable: 'true' },
                    /* { field: 'reward', title: '平台分润总额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 'p_reward', title: '分润拨出总额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 'all_reward', title: '总分润金额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 'hs_reward', title: '华势分润', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 'dif_reward', title: '分润差异', width: 100, formatter: formatprice, align: 'right', sortable: 'true' }, */
                    /* { field: 'czmoney', title: '充值金额', width: 100, formatter: formatprice, align: 'right', sortable: 'true' },
                    { field: 'cznum', title: '充值笔数', width: 100, align: 'center', sortable: 'true' }, */

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
		//隐藏ID列
		//$("#dg").datagrid('hideColumn', "editimage");
		//$("#dg").datagrid('hideColumn', "mid");
		// var columns = $("#dg").datagrid("Columns");

	}

	function compute() {//计算函数
		var rows = $('#dg').datagrid('getRows');//获取当前的数据行
		var t0Paymoney = 0, t0Paynum = 0, t1Paymoney = 0, t1Paynum = 0, payTotle = 0, moneyBt = rows[rows.length-1]['moneyBt'] , moneyTx = 0, countTx = 0;
		for (var i = 0; i < rows.length; i++) {
			t0Paymoney += rows[i]['t0Paymoney'] * 1;
			t0Paynum += rows[i]['t0Paynum'] * 1;
			t1Paymoney += rows[i]['t1Paymoney'] * 1;
			t1Paynum += rows[i]['t1Paynum'] * 1;
			payTotle += rows[i]['payTotle'] * 1;
			/* moneyBt += rows[i]['moneyBt'] * 1; */
			moneyTx += rows[i]['moneyTx'] * 1;
			countTx += rows[i]['countTx'] * 1;
			
			
		}

		//新增一行显示统计信息
		$('#dg').datagrid('appendRow', {
			gdate: '', 
			t0Paymoney	: t0Paymoney, formatter: function (value) { return ; },
			t0Paynum : t0Paynum,
			t1Paymoney : t1Paymoney,
			t1Paynum : t1Paynum,
			payTotle : payTotle,
			moneyBt : moneyBt,
			moneyTx : moneyTx,
			countTx : countTx
			
		});

		$('#datagrid-row-r1-2-' + (rows.length - 1)).attr("style",
				"font-weight:bold");
		//document.getElementById(tt).style.cssText="color:red;"
	}

	function formatprice(val, row) {
		return Number(val).toFixed(2).toString();
	}

	//搜索
	function searchdata() {
		var smonth = $('#makedate').datebox('getValue');
        var CountJson = { "smonth":smonth ,  "merchantcode": $("#merchantcode").val()};

		$.ajax({
			type : "post",
			url : "<%=path %>/daystatic/list",
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
	
	function iniUpLoadify() {
		$('#file_upload').uploadify({
			'method' : "post",
			'height' : '20',
			'width' : '100',
			'multi' : false,
			'fileTypeDesc' : 'Excel Files',
			'fileTypeExts' : '*.xls',
			'swf' : '<%=path %>/js/uploadify/uploadify.swf',
			'uploader' : '<%=path %>/js/uploadify/uploadify.ashx?action=upload',
			'removeTimeout' : '1',
			'auto' : false,
			'fileSizeLimit' : '5000KB', //限制上传图片大小
			'buttonText' : '请选择导入文件', //按钮名称
			'onUploadSuccess' : function(file, data, response) {
				setTimeout(function() {
					$("#uploadMsg").show();
				}, 1000);
				setTimeout(function() {
					cardExplor(data);
				}, 2000);
			},
			'onUploadStart' : function(file) {
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
			url : "<%=path %>/daystatic/export",
			dataType:"json",
			data : '',
					/* + $("#makedate").val() *//* action=memlist&Execl=dataexplor&makedate= */
			complete : function() {
			}, //AJAX请求完成时隐藏loading提示
			success : function(data) {
				disLoad();
				if (data == "error") {
					$('#dg').datagrid('reload');
					$.messager.alert("导出失败");

				} else {
					
					//$.messager.alert("导出成功");
					location.href="<%=path %>/download/"+data.file;

				}

			},
			error : function() {
				disLoad();
			}
		});

	}
</script>
</head>
<body id="cardBody" style="padding: 0px; margin: 0px;">
	<form  id="form1">
		<div class="aspNetHidden">
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE"
				value="/wEPDwUKLTI3ODAyNzA2NmRkQSq6W4hzdCV6gQJZd3nbouTgwGKClW9nrJlTWwuRE4E=" />
		</div>

		<div class="aspNetHidden">

			<input type="hidden" name="__VIEWSTATEGENERATOR"
				id="__VIEWSTATEGENERATOR" value="F1CEB187" /> <input type="hidden"
				name="__EVENTVALIDATION" id="__EVENTVALIDATION"
				value="/wEdAAPhy17Ou4U7MNYc9G2YNQKexB348PZ+eoFXBcu0RrGwa834O/GfAV4V4n0wgFZHr3fOW4qfURDuhIU9MBWasRs5zv/dhEiIzQDUbuwvsAKrBQ==" />
		</div>
		<table id="dg">
		</table>
		<div style="display:none;">
			<div id="toolbar">
				<!-- placeholder="格式如: 2017-01" makedate -->
				<!-- editable="false" -->
					<span>月份:</span>
					<input type="text" id="makedate" class="easyui-datebox" value="${makedate}" name="makedate"
					data-options="formatter:myformatter,parser:myparser"
					 data-options="required:true"
					style="width:80px;"></input>
				
				商户号：<input type="text" id="merchantcode" placeholder="商户号/手机号" />
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>
					 <label id="explorMsg"></label>
				
 			<a href="javascript:;" target="_blank" class="easyui-linkbutton" onclick="OutExplor()" >导出</a>
			</div>
		</div>
	</form>
	
	
</body>


<script>
 $(function () {
            $('#makedate').datebox({
                onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                    span.trigger('click'); //触发click事件弹出月份层
                    if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                        tds = p.find('div.calendar-menu-month-inner td');
                        tds.click(function (e) {
                            e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                            var year = /\d{4}/.exec(span.html())[0]//得到年份
                            , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                            $('#makedate').datebox('hidePanel')//隐藏日期对象
                            .datebox('setValue', year + '-' + month); //设置日期的值
                            searchdata();
                        });
                    }, 0);
                    yearIpt.unbind();//解绑年份输入框中任何事件
                },
                parser: function (s) {
                    if (!s) return new Date();
                    var arr = s.split('-');
                    return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
                },
                formatter: function (d) {
                var a = parseInt(d.getMonth())<parseInt('9')?'0'+parseInt(d.getMonth()+ 1):d.getMonth() + 1;
                return d.getFullYear() + '-' +a; }
            });
            var p = $('#makedate').datebox('panel'), //日期选择对象
                tds = false, //日期选择对象中月份
                yearIpt = p.find('input.calendar-menu-year'),//年份输入框
                span = p.find('span.calendar-text'); //显示月份层的触发控件
            console.log(yearIpt);
           
        });
        
        
		//得到当前日期
		formatterDate = function(date) {
		/* var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate(); */
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
		return date.getFullYear() + '-' + month;/*  + '-' + day */
		};
        
        /* 设置默认日期框默认日期 */
		window.onload = function() {
			$('#makedate').datebox('setValue', formatterDate(new Date()));
		};
        
        
</script>



</html>

