<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String koukuan = (String)request.getSession().getAttribute("koukuan");
	String cuishou = (String)request.getSession().getAttribute("cuishou");
%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>助力金</title>
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<script src="<%=path %>/js/zjgjCommon.js" type="text/javascript"></script>
	<style type="text/css">
		#csForm div{
			margin: 5px;
		}
		.tit{
			display: block;
    		float: left;
		}
		#selByMidContent div{
			float:left;
			width: 100%;
		}
		#selByMidContent div p{
			position:absolute;
			left: 25px;
			margin-top: 15px;
		}
		#selByMidContent div span{
			display:block;
			float:left;
			margin-top: 15px;
			margin-left: 120px;
		}
		#lmWrap{
			margin-top: 10px;
		}
		#lmWrap strong{
			color: red;
		}
		
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
					<input type="text" id="skname" placeholder="收款账户名" />&nbsp;&nbsp;
					<select id="jkcheck">
						<option value=''>全部</option>
						<option value=0>未借款</option>
						<option value=1>同意贷款</option>
						<option value=2>不同意贷款</option>
						<option value=3>通过签署合同</option>
						<option value=4>合同签署失败</option>
						<option value=5>等待放贷</option>
						<option value=6>确认放贷</option>
						<option value=7>已放贷</option>
						<option value=8>放贷失败</option>
						<option value=9>已过期</option>
						<option value=10>已还款</option>
					</select>
					<!-- <input type="text" id="mobile" placeholder="电话" />&nbsp;&nbsp;
					<input type="text" id="agent" placeholder="代理商" />&nbsp;&nbsp; -->
				
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
					 <label id="explorMsg"></label>
					 
 				<a href="javascript:;" class="easyui-linkbutton" onclick="openDialog()" >借款审核</a>
			</div>
		</div>
	</form>
	
	<!-- 催收 -->
	<div title="催收" id="cs" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px" closed="true" buttons="#csbtn" data-mainkey="">
		<form id="csForm" action="<%=path %>/aidgoldCS/createData" method="post" target="#">
			<input id="aid" name="aid" value="" style="display: none;">
			<input id="mid" name="mid" value="" style="display: none;">
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
	<!-- 查看催收内容 -->
	<div title="催收管理" id="csContent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="csFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	<!-- 查看还款内容 -->
	<div title="还款管理" id="hkContent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="hkFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	<!-- 扣款管理 -->
	<div title="扣款管理" id="kkContent" class="easyui-dialog" style="width: 400px; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="kkFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	<!-- 查看合同 -->
	<div title="合同管理" id="dialogFrame" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<a id="downContract" href="#">合同下载</a>
		<iframe  id="contentFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	<!-- 根据mid查询用户信息 -->
	<div title="用户详情" id="selByMidContent" class="easyui-dialog" style="width: 400px; height:500px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<div><p>助力金编号:</p><span class="stid"></span></div>
		<div><p>用户编号:</p><span class="smid"></span></div>
		<div><p>申请人:</p><span class="sna"></span></div>
		<div><p>手机号:</p><span class="mob"></span></div>
		<div><p>微信号:</p><span class="wei"></span></div>
		<div><p>邮箱:</p><span class="ema"></span></div>
		<div><p>联系人1:</p><span class="cn1"></span></div>
		<div><p>电话1:</p><span class="ct1"></span></div>
		<div><p>联系人2:</p><span class="cn2"></span></div>
		<div><p>电话2:</p><span class="ct2"></span></div>
	</div>
	
	<!-- 修改状态 -->
	<div title="处理" id="updateStatus" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		审核结果：
			<select id="newStatus">
				<!-- <option value=1>同意贷款</option> -->
				<option value=7>已放贷</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateJKCheck()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	<!-- 修改状态2 -->
	<div title="系统检测详情" id="applyResult" class="easyui-dialog" style="width:380px;height:200px;padding:10px 20px" closed="true" buttons="#applyResultBtn" data-mainkey="">
		<div id="applyResultMsg"></div>
		<div id="lmWrap">可贷款金额：<strong id="loanMoney"></strong></div>
		<div id="" style="margin-top: 15px;"><strong>是否同意贷款</strong></div>
	</div>
	<div id="applyResultBtn">
		<a id="agreeBtn" href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateJKCheck2(1)">同意</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="updateJKCheck2(2)">不同意</a>
	</div>
</body>

<script type="text/javascript">
	var datagrid;
	
	var tid = '';	//助力金id
	var mid = '';	//用户mid
	var uptid = '';
	
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
							url : '<%=path %>/aidgold/selectList?regTimeFrom='+regTimeFrom
									+'&regTimeTo='+regTimeTo+'&skname='+skname+'&jkcheck='+jkcheck,
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
					{ field: 'mid', title: 'mid', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'midLink', title: '用户详情', width: 70,  align: 'center', formatter: midLinkBtn},
					{ field: 'tid', title: 'tid', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'contract', title: '查看合同', width: 80,  align: 'center', formatter: contractBtn},
					{ field: 'hk', title: '还款详情', width: 80,  align: 'center', formatter: hkButton},
					<%=koukuan %>
					<%=cuishou %>
					{ field: 'jkcheck', title: '借款审核',  width: 100, align: 'center', formatter : jkcheckStatus },
					{ field: 'jkdate', title: '开始日期', width: 200,  align: 'center', sortable: 'true' , formatter : formatTime },
					{ field: 'enddate', title: '结束时间', width: 200,  align: 'center', sortable: 'true' , formatter : formatTime },
					{ field: 'jkmoney', title: '借款金额', width: 120,  align: 'center', sortable: 'true',formatter:formatprice },
                    { field: 'cstimes', title: '催收次数',  width: 100, align: 'center', sortable: 'true', formatter:fcstimes },
					{ field: 'jkdays', title: '借款天数', width: 80,  align: 'center' },
					{ field: 'jkrate', title: '利息', width: 120,  align: 'center', sortable: 'true',formatter:formatRate },
					{ field: 'cgrate', title: '违约利息', width: 120,  align: 'center', sortable: 'true',formatter:formatRate },
                    { field: 'skname', title: '收款账户名',  width: 120, align: 'center', sortable: 'true' },
					{ field: 'skbankno', title: '收款卡号',  width: 180, align: 'center', sortable: 'true' },
                    { field: 'skbank', title: '收款银行',  width: 180, align: 'center', sortable: 'true' },
                    { field: 'remark', title: '备注',  width: 120, align: 'center', sortable: 'true' },
							] ],
						});
		//格式化借款状态
		function jkcheckStatus(value,row) {
			switch(value){
			case 1: return "同意贷款";	break;
			case 2: return "不同意贷款";	break;
			case 3: return "通过签署合同";	break;
			case 4: return "合同签署失败";	break;
			case 5: return "等待放贷";	break;
			case 6: return "确认放贷";	break;
			case 7: return "已放贷";	break;
			case 8: return "放贷失败";	break;
			case 9: return "已过期";	break;
			case 10: return "已还款";	break; 
			default: return "<a href='javascript:;' onclick=getApplyResult("+row.mid+","+row.tid+") style='color:#000;'>未借款</a>";
			} 
		}
		//设置分页控件 
		var p = $('#dg').datagrid('getPager');
		$(p).pagination({
			beforePageText : '第', //页数文本框前显示的汉字 
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录',
		});
	}
	//合同按钮
	function contractBtn(value, row, index){
		return "<a href='javascript:;' onclick=getContractUrl("+row.tid+") style='color:#000;'>查看合同</a>";
	}
	function getContractUrl(val){
		var url = '<%=path %>/fddContract/selectByAidgoldId?tid='+val;
		var obj = getObj(url);
		$("#downContract").removeAttr("href");
		$("#downContract").attr("href",obj.downloadUrl);
		dialogShowContent(obj.viewpdfUrl);
	}
	//查询用户编号相关信息
	function midLinkBtn(value, row, index){
		return "<a class='easyui-linkbutton' href='javascript:;' onclick='selByMid("+row.mid+")' style='color:#000;'>用户详情</a>";}
	//将催收次数变成链接
	function fcstimes(val, obj){
		return '<a href="javascript:;" onclick="csOpen('+ obj.tid +')">' + val + '</a>';
	}
	//扣款按钮
	function kkButton(value, row, index){
		return "<a href='javascript:;' onclick='koukuan("+row.tid+")' style='color:#000;'>扣款</a>";}
	//还款按钮
	function hkButton(value, row, index){
		return "<a href='javascript:;' onclick='huankuan("+row.tid+")' style='color:#000;'>还款详情</a>";}
	//催收按钮
	function csButton(value, row, index){
		return "<a href='javascript:;' onclick='cuishou("+row.tid+","+row.mid+")' style='color:#000;'>催收</a>";}
	//根据mid查询
	function selByMid(mlbmid){
		//查询地址
		var url = '<%=path %>/aidgoldInfo/selectByMid?mid=' + mlbmid;	
		//调用ajax查询
		var aginfo = getObj(url);
		//给页面赋值
		if(aginfo != null){
			$('.stid').html(aginfo.tid);
			$('.smid').html(aginfo.mid);
			$('.sna').html(aginfo.sname);
			$('.mob').html(aginfo.mobile);
			$('.wei').html(aginfo.weixin);
			$('.ema').html(aginfo.email);
			$('.cn1').html(aginfo.contactName1);
			$('.ct1').html(aginfo.contactTele1);
			$('.cn2').html(aginfo.contactName2);
			$('.ct2').html(aginfo.contactTele2);
			//打开弹框
			window.$("#selByMidContent").dialog('open'); 
		} else{
			$.messager.alert("提示", "未查询到用户信息", "info");
		}
	}
	//扣款详情弹出框
	function koukuan(tid){
		var url = '<%=path %>/koukuanCheck.jsp?tid=' + tid;
		window.$("#kkFrame").removeAttr("src");
		window.$("#kkFrame").attr("src", url);
		window.$("#kkContent").dialog('open'); 
	}
	//还款详情弹出框
	function huankuan(tid){
		var url = '<%=path %>/epayPaymentSimple.jsp?tid=' + tid;
		window.$("#hkFrame").removeAttr("src");
		window.$("#hkFrame").attr("src", url);
		window.$("#hkContent").dialog('open'); 
	}
	//催收弹出框
	function csOpen(tid){
		var url = '<%=path %>/aidgoldCSList.jsp?tid=' + tid;
		window.$("#csFrame").removeAttr("src");
		window.$("#csFrame").attr("src", url);
		window.$("#csContent").dialog('open'); 
	}
	//催收
	function cuishou(tid1,mid1){
		$("#cs").dialog("open");
		tid = tid1; 
		mid = mid1;
		document.getElementById("aid").value = tid;
		document.getElementById("mid").value = mid;
	}
	//确认催收
	function csConfirm(){
		if(true){
			document.getElementById("csForm").submit();	//提交表单内容
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
	//getApplyResult
	function getApplyResult(umid,utid){
		$("#applyResult").dialog("open");
		$.ajax({
			type: "post",
			dataType: "json",
			url: '<%=path %>/aidgold/getApplyResult?mid=' + umid,
			success:function(data){
				if(data.msg == "true"){
					$('#agreeBtn').show();
					$('#applyResultMsg').html("系统检验贷款条件：<span style='color:green;'>满足</span>");
				}
				if(data.msg == "false"){
					$('#agreeBtn').hide();
					$('#applyResultMsg').html("系统检验贷款条件：<span style='color:red;'>不满足</span>");
				}
			},
			error:function(){
			}
		});
		uptid = utid;
		var urlAddress = "http://mobile.rczjgj.com/DataHandler.ashx?action=aidgold_getgd&mid="+umid;
		var moneyUrl = '<%=path %>/aidgold/callUrl?urlAddress=' + urlAddress;
		var moneyObj = getObj(moneyUrl);
		console.log(moneyObj);
		$('#loanMoney').html(moneyObj.xygd);
	}
	function updateJKCheck2(jkStatus){
		var url = '<%=path %>/aidgold/updateStatus2?tid='+uptid+'&jkcheck='+jkStatus;
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			success:function(data){
				if(data.ret=1){
					$.messager.alert("提示",data.msg,"info");
					$('#dg').datagrid('reload');
				} else {
					$.messager.alert("提示",data.msg,"info");
				}
			}
		});
		//关闭dialog
		$("#applyResult").dialog("close");
	}
	//
	function updateJKCheck(){
		var row = $('#dg').datagrid('getSelections');
		
		var url = '<%=path %>/aidgold/updateStatus';
		var newStatus = $('#newStatus').val();
		var jkcs = "";
		if(row.length>0){
			//设置jkcs字符串
			if(row){
				for(var i=0;i<row.length;i++){
					if(i==0) jkcs = row[i].tid;
						else jkcs += "," + row[i].tid;
				}
			}
		}
		//调用ajax方法
		updatestatus(url, jkcs, newStatus);
		//关闭dialog
		$("#updateStatus").dialog("close");
	}
	
	
</script>

</html>

