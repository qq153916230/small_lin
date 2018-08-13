<%@ page language="java" import="java.util.*,com.app.util.ReadHTMLFilePath" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String koukuan = (String)request.getSession().getAttribute("koukuan");	//扣款按钮
	String cuishou = (String)request.getSession().getAttribute("cuishou");	//催收按钮
	if(!"".equals(koukuan) && !"".equals(cuishou)){
		koukuan = "{ field: 'kk', title: '扣款', width: 50,  align: 'center', formatter: kkButton},";
		cuishou = "{ field: 'cs', title: '催收', width: 50,  align: 'center', formatter: csButton},";
	}
	String managerPass = new ReadHTMLFilePath().getHTMLFilePath("managerPass");	//配置文件的密码
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
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"type="text/javascript"></script>
	<script src="<%=path %>/js/layer/layer.js"type="text/javascript"></script>
	<script src="<%=path %>/js/zjgjCommon.js" type="text/javascript"></script>
	<style type="text/css">
	
		#csForm div{ margin: 5px; }
		.tit{ display: block; float: left; }
		#userBaseMsg{ float: left; width: 34%; }
		#userIdPic{ margin-top:50px; float: right; width: 64%; }
		#userBaseMsg div{ height: 25px; }
		.ct{ display:inline-block; width: 22%; margin-left: 10px; margin-right: 5px; }
		#address{ display: block; width: 70%; float: right; padding-top: 12px;
		} 
		#userIdPic div{ float: left; }
		#lmWrap,#agremark{ margin-top: 10px; }
		
		#lmWrap strong{ color: red; }
		.idPic{ margin:3px; width: 170px; height: 226px; }
		
		#front,#back, #hand, #agentPic{ width: 172px;height: 228px; }
	
		a:link { color:#660000;  text-decoration:none;  }
		a:visited,a:hover,a:active { color:#660000;  text-decoration:none;  }
		
		#baseBody a{ 
			text-decoration:none;
		    margin: 3px;
			font-size: 12px; 
			display:block;
			color:#660000;
			height:20px;  
			padding: 3px; 
			text-align: center; 
			background-color: #ace2ff;
			border: 1px solid #0080c0; 
			border-radius: 3px;
			cursor: pointer;
			outline: 0; 
			clear:right;
		}
		.datagrid-body a{ display:block; height:20px; }
		.tradeHis div{ font-size: 14px;margin: 30px; } 
		#tradeNo,#blackListWrap,#credit_msg{ float:left; margin: 5px 15px;}  
		#tradeNo b,#blackListWrap b,#credit_msg b{ margin: 15px 0 0 15px; }
		
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
						<option value=80 selected = "selected">即将还款</option>
						<option value=81>逾期</option>
						<option value=0>待审核</option>
						<option value=11>同意贷款</option>
						<option value=12>不同意贷款</option>
						<option value=13>已驳回</option> 
						<option value=21>已付手续费</option>
						<option value=22>手续费支付失败</option>
						<option value=31>客户已签署合同</option>
						<option value=32>客户签署合同失败</option>
						<option value=33>公司签署合同成功</option>
						<option value=34>公司签署合同失败</option>
						<option value=41>代理商同意放贷</option>
						<option value=42>代理商不同意放贷</option>
						<option value=43>已放贷</option>
						<option value=44>放贷失败</option>
						<option value=50>待还款</option>
						<option value=51>还款中</option>
						<option value=52>还款失败</option>
						<option value=99>已还款</option>
					</select>
					<!-- <input type="text" id="mobile" placeholder="电话" />&nbsp;&nbsp;
					<input type="text" id="agent" placeholder="代理商" />&nbsp;&nbsp; -->
				
				<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
				<span style="font-size: 15px;margin-left: 20px;">
					借款总额：
					<span id="jktotle" style="color: red;font-size: 15px">0.00</span>
				</span>
				
				<!-- <a href="javascript:;" class="easyui-linkbutton"  onclick="openAidgoldAgent()">总账户管理</a>&nbsp;&nbsp; -->
					 <label id="explorMsg"></label>
					 
 				<!-- <a href="javascript:;" class="easyui-linkbutton" onclick="openDialog()" >借款审核</a> -->
			</div>
		</div>
	</form>

	
	<input type="hidden" id="mid">
	
	<!-- 查看催收内容 -->
	<div title="催收管理" id="csContent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="csFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	<!-- 查看还款内容 -->
	<div title="还款管理" id="hkContent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="hkFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	
	<!-- 借款历史 -->
	<div title="借款历史" id="jkhContent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<!-- <iframe  id="jkhFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> --> 
	</div>
	
	<!-- ************** -->
	<!-- 通用弹出页面 dialog -->
	<div title="提示" id="commonDialogWrap" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="commonDialogFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	<!-- ************** -->
	
	<!-- 打开助力金代理商总账户 -->
	<div title="助力金代理商总账户管理" id="dialogAidgoldAgent" class="easyui-dialog" style="width: 70%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="aidgoldAgentFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	
	<!-- 扣款管理 -->
	<div title="扣款管理" id="kkContent" class="easyui-dialog" style="width: 400px; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		<iframe  id="kkFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	</div>
	
	<!-- 修改状态 -->
	<div title="确认已放款" id="updateStatus" class="easyui-dialog" style="width:300px;height:170px;padding:10px 20px" closed="true" buttons="#dlg-updatetx" data-mainkey="">
		放款日期：
			<input type="text"  style="width:140px" class="easyui-datetimebox" id="fkdate" data-options="onSelect:regTimeSelect"/><br><br>
		放款结果：
			<select id="newStatus">
				<!-- <option value=1>同意贷款</option> -->
				<option value=43>已放贷</option>
				<option value=21>客户已付手续费(重新签署合同)</option>
			</select>
	</div>
	<div id="dlg-updatetx">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateJKCheck()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus').dialog('close');">取消</a>
	</div>
	<!-- 模拟代理商 同意/不同意 借款-->
	<div title="代理商处理" id="updateStatus3" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx3" data-mainkey="">
		审核结果：
			<select id="newStatus3">
				<option value=41>代理商同意放贷</option>
				<option value=42>代理商不同意放贷</option>
				<option value=21>客户已付手续费(重新签署合同)</option>
			</select>
	</div>
	<div id="dlg-updatetx3">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateJKCheck3()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus3').dialog('close');">取消</a>
	</div>
	<!-- u 31 to 21-->
	<div title="回滚" id="u31to21Wrap" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-u31to21" data-mainkey="">
		状态回滚：
			<select id="status21">
				<option value=21>客户已付手续费(重新签署合同)</option>
			</select>
	</div>
	<div id="dlg-u31to21">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="u31to21Fun()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#u31to21Wrap').dialog('close');">取消</a>
	</div>
	<!-- 微信转手续费处理 -->
	<div title="手续费支付结果" id="updateStatus4" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-updatetx4" data-mainkey="">
		审核结果：
			<select id="newStatus4">
				<option value=21>已付手续费</option>
				<option value=22>手续费支付失败</option>
			</select>
	</div>
	<div id="dlg-updatetx4">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="updateJKCheck4()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#updateStatus4').dialog('close');">取消</a>
	</div>
	<!-- 修改状态2 -->
	<div title="是否同意借款" id="applyResult" class="easyui-dialog" style="width:400px;height:250px;padding:10px 20px" closed="true" buttons="#applyResultBtn" data-mainkey="">
		<div id="applyResultMsg"></div>
		<div id="lmWrap">可贷款金额：<strong id="loanMoney"></strong></div>
		<div id="agremark"></div>&nbsp;
		<div id="comfirmPassDiv" style="display: none; margin-top: 3px;">
			<span style="color: red;">强制通过密码：</span>
			<input id="comfirmPassInp" type="password">
			<div id="passMsg" style="color:red; margin-top: 5px;"></div>
		</div>
	</div>
	<div id="applyResultBtn">
		<a id="agreeBtn" href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="inputPass()">同意</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="updateJKCheck2(12)">不同意</a>
	</div>
	
	<!-- 选择驳回类型-->
	<div title="重置信息" id="resetTypeWrap" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#resetTypeBtn" data-mainkey="">
		驳回类型：
			<select id="resetType">
				<!-- <option value=0>请选择</option> -->
				<option value=1001>实名(身份证号码+照片)</option>
				<option value=1002>联系人(含身份证地址)</option>
				<option value=1003>实名+联系人</option>
				<option value=1004>其他</option>
			</select>
	</div>
	<div id="resetTypeBtn">
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-ok" onclick="resetFun()">确定</a>
		<a href="javascript:;" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#resetTypeWrap').dialog('close');">取消</a>
	</div>
	
	<!-- 整合按钮 -->
	<div title="详细" id="detailWrap" class="easyui-dialog" style="width: 1230px; height:600px; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
		 <div id="tt" class="easyui-tabs" style="width:120%;">
		 	<div id="baseMsg" title="基本信息" style="overflow:auto;" data-options="iconCls:'icon-reload'">
		 		
		 		<table border="1" cellpadding="5" cellspacing="" style="margin: 15px;">
		 			<tbody id="baseBody">
		 				<tr>
		 					<td>用户名</td><td>状态</td><td>借款金额</td><td>备注</td><td>收款卡号</td>
		 					<td>收款银行</td><td>开始日期</td><td>结束日期</td><td>代理商</td><td>系统审核结果</td>
		 					<td>已还金额</td><td>催收次数</td><td>借款天数</td><td>利息</td><td>违约利息</td>
		 					<td></td>
		 				</tr>
		 				<tr>
			 				<td><span class="bName"></span></td>
		 					<td><span class="bStatus"></span></td>
			 				<td><span class="bMon"></span></td>
			 				<td><span class="bRem"></span></td>
			 				<td><span class="bNo"></span></td>
			 				<td><span class="bBan"></span></td>
			 				<td><span class="bSta"></span></td>
			 				<td><span class="bEnd"></span></td>
			 				<td><span class="bAge"></span></td>
			 				<td><span class="bRes"></span></td>
			 				<td><span class="bHK"></span></td>
			 				<td><span class="bCS"></span></td>
			 				<td><span class="bDay"></span></td>
			 				<td><span class="bRate"></span></td>
			 				<td><span class="bWY"></span></td>
			 				<td><span class="lahei"></span></td>
		 				</tr>
		 			
		 			</tbody>
		 		</table>
		 		
		 		<div id="blackListWrap">
			 		<b style="margin: 15px 0 0 15px;"><i>拉黑记录:</i></b><button onclick="blacklistFun(0)">查看所有</button>
			 		<table border="1" cellpadding="5" cellspacing="">
			 			<thead>
			 				<tr><td>用户名</td><td>备注</td><td>日期</td><td>操作</td></tr>
			 			</thead>
			 			<tbody id="blacklistBody"></tbody>
			 		</table>
			 	</div>
		 		<div id="tradeNo">
		 			<b><i><a href="http://mobile.rczjgj.com:7897/xinYanAPI/spiderCarrier.jsp" target="_blank" style="color:red;">查询并保存订单号 </a></i></b><button onclick="layer.open({title: '史希刚全身照',content: '　┏┓　　　┏┓<br>┏┛┻━━━┛┻┓<br>┃　　　　　　　┃<br>┃　　　━　　　┃<br>┃　┳┛　┗┳　┃<br>┃　　　　　　　┃<br>┃　　　┻　　　┃<br>┃　　　　　　　┃<br>┗━┓　　　┏━┛<br>　　┃　　　┃<br>　　┃　　　┃<br>　　┃　　　┗━━━┓<br>　　┃　　　　　　　┣┓<br>　　┃　　　　　　　┏┛<br>　　┗┓┓┏━┳┓┏┛<br>　　　┃┫┫　┃┫┫<br>　　　┗┻┛　┗┻┛<br>'});">呼叫网管</button>
		 			<button onclick="print_all_contact()">查看联系人</button>
		 			<table border="1" cellpadding="5" cellspacing="">
		 				<thead>
			 				<tr><td>订单类型</td><td>订单号</td><td>创建日期</td><td>状态</td></tr>
			 			</thead>
			 			<tbody id="email_list">
			 				<!-- <tr><td>邮箱账单</td><td id="spiderEmailTradeNo"></td>
			 					<td id="spiderEmailDate"></td><td id="spiderEmailStatus"></td></tr> -->
			 			</tbody>
		 			</table>
		 			&nbsp;
		 			<table border="1" cellpadding="5" cellspacing="">
		 				<thead>
			 				<tr><td>订单类型</td><td>订单号</td><td>创建日期</td><td>状态</td></tr>
			 			</thead>
			 			<tbody id="tradeNoBody">
			 				<tr><td>通讯爬虫</td><td id="spiderMobileTradeNo"></td>
			 					<td id="spiderMobileDate"></td><td id="spiderMobileStatus"></td></tr>
			 				<!-- <tr><td>邮箱账单</td><td id="spiderEmailTradeNo"></td>
			 					<td id="spiderEmailDate"></td><td id="spiderEmailStatus"></td></tr> -->
			 				<tr><td><b>提额类型</b></td><td id=""><b>订单号</b></td>
			 					<td id=""><b>创建日期</b></td><td id=""><b>操作</b></td></tr>
			 					
			 				<tr><td>支付宝</td><td id="spiderAlipayTradeNo"></td>
			 					<td id="spiderAlipayDate"></td><td id="spiderAlipayStatus"></td></tr>
			 				<tr><td>京东</td><td id="spiderJingdongTradeNo"></td>
			 					<td id="spiderJingdongDate"></td><td id="spiderJingdongStatus"></td></tr>
			 				<tr><td>医保社保</td><td id="spiderSecurityTradeNo"></td>
			 					<td id="spiderSecurityDate"></td><td id="spiderSecurityStatus"></td></tr>
			 				<tr><td>公积金</td><td id="spiderFundTradeNo"></td>
			 					<td id="spiderFundDate"></td><td id="spiderFundStatus"></td></tr>
			 				<tr><td>车险保单</td><td id="spiderInsuranceTradeNo"></td>
			 					<td id="spiderInsuranceDate"></td><td id="spiderInsuranceStatus"></td></tr>
			 				
			 			</tbody>
		 			</table>
		    	</div>
		    	<div id="credit_msg">
		 			<button onclick="setCredit_msg()">刷新信息</button><b><i>综合信用分 : </i></b>
		 			<b class="credit_score"></b>
		 			<table border="1" cellpadding="5" cellspacing="">
		 				<thead>
			 				<tr><th>类型</th> <th>记录</th> <th><a href="javascript:;" onclick="update_credit_msg()">更新所有</a></th></tr>
			 			</thead>
			 			<tbody id="credit_msg_body">
			 				<tr><td><i>诉讼执行</i></td> 	<td class="law_num"></td>		<th><a href="javascript:;" onclick="confirm_update('shefashesu')">更新</a></th></tr>
			 				<tr><td><i>手机在网时长</i></td> 	<td class="online_time"></td>	<th><a href="javascript:;" onclick="confirm_update('mobile_msg')">更新</a></th></tr>
			 				<tr><td><i>最近联系人</i></td>	<td class="connect_6m"></td>	<th><a href="javascript:;" onclick="layer.open({title: '提示',content: '请更新\'手机在网时长\' '});">..</a></th></tr>
			 				<tr><td><i>逾期未还款</i></td> <td class="overdue_no_repay"></td>	<th><a href="javascript:;" onclick="confirm_update('overdue')">更新</a></th></tr>
			 				
			 				<tr><td>逾期订单数(-1)</td> 	<td class="overdue_num"></td>	<th><a href="javascript:;" onclick="layer.open({title: '提示',content: '请更新\'逾期未还款\' '});">..</a></th></tr>
			 				<tr><td>常用联系人(-1)</td>	<td class="connect_num"></td>	<th><a href="javascript:;" onclick="layer.open({title: '提示',content: '请更新\'手机在网时长\' '});">..</a></th></tr>
			 				<tr><td>用户年龄(-1)</td> 	<td class="user_age"></td>		<th><a href="javascript:;" onclick="confirm_update('user_age')">更新</a></th></tr>
			 				<tr><td>共债详情(-0.5)</td> 	<td class="debt_count"></td>	<th><a href="javascript:;" onclick="confirm_update('debt')">更新</a></th></tr>
			 				<tr><td>借新还旧</td> 	<td class="debt_new_old"></td>	<th><a href="javascript:;" onclick="layer.open({title: '提示',content: '请更新\'共债详情\' '});">..</a></th></tr>
			 				<tr><td>风险通话</td> 	<td class="risk_call"></td>		<th><a href="javascript:;" onclick="layer.open({title: '提示',content: '请更新\'手机在网时长\' '});">..</a></th></tr>
			 				<tr><td>更新日期</td> 	<td class="credit_msg_date"></td>	<th>..</th></tr>
			 			</tbody>
		 			</table>
		    	</div>
		    </div>
		 
		    <div title="实名信息" data-options="iconCls:'icon-reload'" style="overflow:auto;">
				<!-- 根据mid查询用户信息 -->
				<div title="用户详情" id="selByMidContent" class="" style="width: 1100px; height:550px; /* overflow:hidden */; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
					<div id="userBaseMsg" style="width:300px;">
						<!-- <div><p class="ct">助力金编号:</p><span class="stid"></span></div> -->
						<div><p class="ct">用户编号:</p><span class="smid"></span></div>
						<div><p class="ct">申请人:</p><span class="sna"></span></div>
						<div><p class="ct">手机号:</p><span class="mob"></span></div>
						<div><p class="ct">微信号:</p><span class="wei"></span></div>
						<div><p class="ct">邮箱:</p><span class="ema"></span></div>
						<div><p class="ct">身份证:</p><span class="idno"></span></div>
						<div><p class="ct">身份证住址:</p> <span id="address"></span></div>
						
						<div>&nbsp;</div>
						<div><p class="ct">联系人1:</p><span class="cn1"></span>(<span class="cr1"></span>)</div>
						<div><p class="ct">电话1:</p><span class="ct1"></span></div>
						<div><p class="ct">身份证1:</p><span class="idno1"></span></div>
						<div><p class="ct">联系人2:</p><span class="cn2"></span>(<span class="cr2"></span>)</div>
						<div><p class="ct">电话2:</p><span class="ct2"></span></div>
						<div><p class="ct">身份证2:</p><span class="idno2"></span></div>
						<div style="height:5px"></div>
						<div style="background:#0066cc;color:#fff;height:20px;padding:5px;"> 收款银行 </div>
						<div><p class="ct">姓名:</p><span class="bankMaster"></span></div>
						<div><p class="ct">卡号:</p><span class="bankCardno"></span></div><!--  -->
						<div><p class="ct">开户行名称:</p><span class="bankname"></span></div>
						<div><p class="ct">预留手机号:</p><span class="bankMobile"></span></div>
					</div>
					<div id="userIdPic">
						<div id="front"></div>
						<div id="back"></div>
						<div id="hand"></div>
						<div id="agentPic"></div>
						<div>
						<br><br>
						<input type="button" value="  驳回，重置客户信息 " onclick="$('#resetTypeWrap').dialog('open')" style="width:200px;height:34px;">
						</div>
					</div>
				</div>
		    </div>
		    
		    <div title="交易记录" class="tradeHis" data-options="iconCls:'icon-reload'" >
				<div id="tradeRecord"></div>
				<div id="addAmount" >
					<b id="teMSG" style="font-size: 16px;"></b>
					<i>(1次/月)</i>
					<span id="teBtn"></span>
				</div>
		    </div>
		    
		    <div title="合同管理" data-options="iconCls:'icon-reload'" style="">
				<!-- 查看合同 -->
				<div title="合同管理" id="dialogFrame" class="" style="width: 100%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
					<a id="downContract" href="#">合同下载</a> &nbsp;<a id="signContract" href="#" target="_blank">合同签署</a> 
					<iframe  id="contractFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
				</div>
		    </div>
		    
		    <div title="还款历史" style="" data-options="iconCls:'icon-reload'">
		    	<iframe  id="hkHistory" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe>
		    </div>
		    
		    <div title="借款历史" style="" data-options="iconCls:'icon-reload'">
		    	<iframe  id="jkhFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe>
		    </div>
		    
		    <div title="信用卡" style="" data-options="iconCls:'icon-reload'">
		    	<iframe  id="creditFrame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe>
		    </div>
		    
		    <div title="分期记录" style="" data-options="iconCls:'icon-reload'">
		    	<iframe  id="fqRecord" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe>
		    </div>
		    
		    <div title="催收记录" style="" data-options="iconCls:'icon-reload'">
		    	<iframe  id="csRecord" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe>
		    </div>
		    
		    <div title="景区管理" style="" data-options="iconCls:'icon-reload'">
		    	<iframe  id="scenic" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe>
		    </div>
		    
		</div>
	</div>
	
</body>
<script type="text/javascript">
	var datagrid;
	
	var tid = '';	//助力金id
	var mid = '';	//用户mid
	var uptid = '';
	var ujctid = ''; //更新jkcheck的tid
	
	var jkcResult = ''; //借款审核的结果
	var jkmoney;	//用于判断是否是体验贷
	
	var tyCheck = 0; //判断是否满足体验贷，默认0不满足
	
	var aginfo;			//助力金info表
	var memberAuthor;	//memberAuthor表
	
	jkcheck = 80;
	
	//新颜API地址
	//var xyapi_url = "http://localhost:8080/xinYan";
	var xyapi_url = "http://mobile.rczjgj.com:7897/xinYanAPI";
	var server_url = "http://mobile.rczjgj.com:7897";
	
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
					{ field: 'tid', title: 'tid', width: 0,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'mid', title: 'mid', width: 100,  align: 'center', sortable: 'true', hidden:'true'},
					
					{ field: 'detail', title: '操作', width: 60,  align: 'center', sortable: 'true',formatter: fdetail },
					
                    { field: 'skname', title: '借款用户',  width: 60, align: 'center', sortable: 'true' },
					{ field: 'jkcheck', title: '状态',  width: 120, align: 'center', formatter : jkcheckStatus },
					{ field: 'jkmoney', title: '借款金额', width: 100,  align: 'center', sortable: 'true',formatter: fjkmoney },
					{ field: 'declare', title: '备注',  width: 200, align: 'left', formatter: fdeclare},
					
					{ field: 'jkdate', title: '开始日期', width: 80,  align: 'center', sortable: 'true' , formatter : formatDate  },
					{ field: 'enddate', title: '结束日期', width: 80,  align: 'center', sortable: 'true' , formatter : formatDate },
					{ field: 'userName', title: '代理商',  width: 60, align: 'center' },
                    { field: 'remark', title: '系统审核结果',  width: 150, align: 'left'},
					//{ field: 'midLink', title: '实名', width: 50,  align: 'center', formatter: midLinkBtn}, 
					//{ field: 'tdetail', title: '交易', width: 50,  align: 'center', formatter: ftdetail},
					//{ field: 'contract', title: '合同', width: 50,  align: 'center', formatter: contractBtn},
					//{ field: 'hk', title: '还款', width: 50,  align: 'center', formatter: hkButton}, 
                    //{ field: 'history', title: '历史', width: 50,  align: 'center', formatter: hisButton},
                    //{ field: 'creditCard', title: '信用卡', width: 60,  align: 'center', formatter: cardButton},
                    
					{ field: 'hkmoney', title: '已还金额',  width: 70, align: 'center', sortable: 'true', formatter: fhkmoney },
                    { field: 'cstimes', title: '催收次数',  width: 55, align: 'center', sortable: 'true', formatter: fcstimes },
                    
                    { field: 'jkdays', title: '借款天数', width: 80,  align: 'center' },
					{ field: 'jkrate', title: '利息', width: 80,  align: 'center', sortable: 'true',formatter:formatRate },
					{ field: 'cgrate', title: '违约利息', width: 80,  align: 'center', sortable: 'true',formatter:formatRate },
					
					{ field: 'skbankno', title: '收款卡号',  width: 200, align: 'center', sortable: 'true' },
                    { field: 'skbank', title: '收款银行',  width: 200, align: 'left', sortable: 'true' },
                    { field: '  ', title: '  ', width: 100,  align: 'center'},
					
							] ],
						});
						<%-- <%=koukuan %> --%>
						<%-- <%=cuishou %> --%>
		
		//设置分页控件 
		var p = $('#dg').datagrid('getPager');
		$(p).pagination({
			beforePageText : '第', //页数文本框前显示的汉字 
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录',
		});
		
		countJKmoney();	//统计借款金额 jkmoney
	}
	
	//格式化借款状态
	function jkcheckStatus(value,row) {
		switch(value){
		//case 11: return "同意贷款";	break;
		case 11: return "<a href='javascript:;' class='easyui-linkbutton' onclick='openDialog4("+row.tid+")' style='color:green;'>同意贷款</a>";	break;
		case 12: return "不同意贷款";	break; 
		case 13: return "已驳回";	break;
		//case 13: return "<a href='javascript:;' onclick=getApplyResult("+row.mid+","+row.tid+") style='color:#1d953f;'>已驳回</a>";	break;
		case 21: return "<a href='javascript:;' onclick=getApplyResult("+row.mid+","+row.tid+") style='color:#000;'>已付手续费</a>";	break;
		case 22: return "手续费支付失败";	break;
		case 31: return "<a href='javascript:;' class='easyui-linkbutton' onclick='u31to21("+row.tid+")' style='color:green;'>客户已签署合同</a>";	break;
		case 32: return "客户签署合同失败";	break;
		//case 33: return "公司签署合同成功";	break;
		case 33: return "<a href='javascript:;' class='easyui-linkbutton' onclick='openDialog3("+row.tid+")' style='color:green;'>公司签署合同成功</a>";	break;
		case 34: return "公司签署合同失败";	break;
		case 41: return "<a href='javascript:;' class='easyui-linkbutton' onclick='openDialog("+row.tid+")' style='color:green;'>代理商同意放贷</a>";	break;
		case 42: return "代理商不同意放贷";	break;
		case 43: return "已放贷";	break;
		case 44: return "放贷失败";	break;
		case 50: return "待还款";	break;
		case 51: return "还款中";	break;
		case 52: return "还款失败";	break;
		case 98: return "其他";	break;
		case 99: return "已还清";	break;
		default: return "<a href='javascript:;' onclick=getApplyResult("+row.mid+","+row.tid+") style='color:#1d953f;'>待审核</a>";
		} 
	}

	

	//打开弹出框
	function openDialog(odtid){
		ujctid = odtid;
		$("#updateStatus").dialog("open");
	}
	//打开弹出框 (公司合同签署成功按钮)
	function openDialog3(odtid){
		ujctid = odtid;
		$("#updateStatus3").dialog("open");
	}
	//打开弹出框 (已支付手续费按钮)
	function openDialog4(odtid){
		ujctid = odtid;
		$("#updateStatus4").dialog("open");
	}
	//31已签署合同改成21已付手续费
	function u31to21(odtid){
		ujctid = odtid;
		$("#u31to21Wrap").dialog("open");
	}
	//合同按钮
	function contractBtn(value, row, index){
		return "<a href='javascript:;' onclick=getContractUrl("+row.tid+") style='color:#000;'>合同</a>";
	}
	function getContractUrl(val){
		var url = '<%=path %>/fddContract/selectByAidgoldId?tid='+val;
		var obj = getObj(url);	//根据tid获取地址信息
		//公司签署合同地址
		var signUrl = "<%=path %>/fadada/syncPersonAutoCom?aidgold_id="+obj.aidgoldId+"&contract_id="+obj.contractId+"&doc_title="+obj.docTitle;
		//给下载合同按钮添加url
		$("#downContract").removeAttr("href");
		$("#downContract").attr("href",obj.downloadUrl);
		//给公司签署合同按钮添加url
		$("#signContract").removeAttr("href");
		$("#signContract").attr("href",signUrl);
		//展示合同
		//dialogShowContent(obj.viewpdfUrl);
		$("#contractFrame").removeAttr("src");
		$("#contractFrame").attr("src",obj.viewpdfUrl);
	}
	//查询用户编号相关信息
	function midLinkBtn(value, row, index){
		return "<a class='easyui-linkbutton' href='javascript:;' onclick='selByMid("+row.mid+")' style='color:#000;'>实名</a>";
	}
	//查询用户额度，笔数信息
	function ftdetail(value, row, index){
		return "<a class='easyui-linkbutton' href='javascript:;' onclick='selLogCon("+row.mid + "," + row.tid +")' style='color:#000;'>交易</a>";
	}

	//将催收次数变成链接
	function fcstimes(val, obj){
		return '<a href="javascript:;" onclick="csOpen('+ obj.tid +','+ obj.mid +')">' + val + '</a>';
	}
	//将还款金额变成链接
	function fhkmoney(val, obj){
		return '<a href="javascript:;" onclick="hkOpen('+ obj.tid +')">' + val + '</a>';
	}
	//格式化jkmoney
	function fjkmoney(val, obj){
		val = Number(val).toFixed(2).toString();
		return '<a href="javascript:;" onclick="cjkmoney('+ obj.tid + ',' + obj.mid +')">' + val + '</a>';
	}
	//详细按钮
	function fdetail(val, obj){
		return "<a href='javascript:;' onclick='detailFun("+ JSON.stringify(obj) +")'><img src='<%=path %>/js/jquery-easyui-1.4.3/themes/icons/pencil.png'></a>";
	}
	//备注按钮
	function fdeclare(val, obj){
		return "<a href='javascript:;' onclick='declareFun("+ JSON.stringify(obj) +")'>" + val + "</a>";
	}
	//拉黑按钮
	function fLaHei(fmid, fname){
		return "<a href='javascript:;' onclick='laheiFun("+ fmid + ",\"" + fname + "\")'>禁止借款</a>";
	}
 
	//扣款按钮
	function kkButton(value, row, index){
		return "<a href='javascript:;' onclick='koukuan("+row.tid+")' style='color:#000;'>扣款</a>";
	}

	//还款按钮
	function hkButton(value, row, index){
		return "<a href='javascript:;' onclick='huankuan("+row.tid+")' style='color:#000;'>还款</a>";
	}
	
	//历史记录
	function hisButton(value, row, index){
		return "<a href='javascript:;' onclick='jkhistory("+row.mid+")' style='color:#000;'>历史</a>";
	}
	
	//信用卡
	function cardButton(value, row, index){
		return "<a href='javascript:;' onclick='openDialogFun(" + row.mid + "," + "\"提示\"" +")'>信用卡</a>";
	}

	//催收按钮
	function csButton(value, row, index){
		return "<a href='javascript:;' onclick='cuishou("+row.tid+","+row.mid+")' style='color:#000;'>催收</a>";
	}
	
	//给详细框的基本信息赋值
	function baseValuation(obj){
	
		var status = jkcheckStatus(obj.jkcheck,obj);
		var startData = formatDate(obj.jkdate, obj);
		var endData = formatDate(obj.enddate, obj);
		var bvjkmoney = fjkmoney(obj.jkmoney, obj);
		var bvRemark = fdeclare(obj.declare, obj);
		var bvhkmoney = fhkmoney(obj.hkmoney, obj);
		var bvcstimes = fcstimes(obj.cstimes, obj);
		var bvLaHei = fLaHei(obj.mid, obj.skname);	//拉黑
		
		$(".bName").html(obj.skname); //用户名
		$(".bStatus").html(status); //状态
		$(".bMon").html(bvjkmoney); //借款金额
		$(".bRem").html(bvRemark); //备注
		$(".bNo").html(obj.skbankno); //收款卡号
		$(".bBan").html(obj.skbank); //收款银行
		$(".bSta").html(startData); //开始日期
		$(".bEnd").html(endData); //结束日期
		$(".bAge").html(obj.userName); //代理商
		$(".bRes").html(obj.remark); //系统审核结果
		$(".bHK").html(bvhkmoney); //已还金额
		$(".bCS").html(bvcstimes); //催收次数
		$(".bDay").html(obj.jkdays); //借款天数
		$(".bRate").html(obj.jkrate); //利息
		$(".bWY").html(obj.cgrate); //违约利息
		
		$(".lahei").html(bvLaHei); //拉黑
		
	}
	
	function blacklistFun(bmid){
		$.ajax({
			type : "post",
			url : "<%=path %>/aidgold/blacklist",
			dataType:"json",
			data : {"mid":bmid},
			success : function(data) {
				var len = data.length;
				var showData = "";	//
				var uname;
				var remark;
				var cdate;
				var btid;
				
				for(var i=0; i<len; i++){
					uname = data[i].uname;
					remark = data[i].remark;
					cdate = data[i].cdate;
					btid = data[i].tid;
					
					showData += "<tr> <td>"+uname+"</td> <td>"+remark+"</td> <td>"+cdate+"</td>";
					showData += '<td><a href="javascript:;" onclick="removeBlacklist('+ btid + ',' + bmid +')">移除</a></td> </tr>';
				}
				$("#blacklistBody").html(showData);
			}
		});
	}
	
	//详细
	function detailFun(obj){
		//var url = '/card.jsp?mid=' + cmid;
		//window.$("#commonDialogFrame").removeAttr("src");
		//window.$("#commonDialogFrame").attr("src", url);
		
		window.$("#detailWrap").dialog('open');
		
		tid = obj.tid;
		mid = obj.mid;
		
		sendRequest("<%=path %>/aidgold/dealAgentid", {"mid":mid,"tid":tid}, "json");	//处理 助理金 agentid为0的值
		
		$('#mid').val(obj.mid);	//给mid隐藏域赋值
		
		//给基本信息赋值
		baseValuation(obj);
		//黑名单列表
		blacklistFun(mid);
		
		setCredit_msg();	//设置信用信息
		setSpiderMobile(mid);	//设置通讯爬虫信息
		
		//查询地址
		var url = '<%=path %>/aidgoldInfo/selectByMid?mid=' + mid;	
		var memberAuthorUrl = '<%=path %>/memberAuthor/selectByMid?mid=' + mid;
		
		//调用ajax查询 (同步)
		aginfo = getObj(url); //aidgold_info
		memberAuthor = getObj(memberAuthorUrl);
		console.log("detailFun");
		//console.log(obj);
		//console.log(aginfo);
		//console.log(memberAuthor);
		
		// 获取选中的标签页面板（tab panel）和它的标签页（tab）对象
		var pp = $('#tt').tabs('getSelected');
		var tab = pp.panel('options').title; // 相应的标签页（tab）对象 
		
		freshTab(tab);
		
		//easyui 刷新标签
		$('#tt').tabs({
		    border:false,
		    onSelect:function(title){
		    	freshTab(title);
				//alert(title+' is selected');
		    }
		});
		
		
	}
	//刷新标签
	function freshTab(tabName){
		switch(tabName){
    		case "实名信息" : selByMid(mid); break;
    		case "还款历史" : huankuan(tid); break;
    		case "交易记录" : selLogCon(mid, tid); break;
    		case "合同管理" : getContractUrl(tid); break;
    		case "借款历史" : jkhistory(mid); break;
    		case "信用卡" : creditFun(mid); break;
    		case "分期记录" : fqRecord(tid); break;
    		case "催收记录" : csRecord(tid, mid); break;
    		case "景区管理" : scenicFun(); break;
    	}
	}
	
	//格式化jkmoney
	function cjkmoney(jktid, jkmid){
		layer.prompt({title:"修改用户借款金额为：",value:""},function(val, index, elem){
			if(val >= 2000){
				$.ajax({
					type : "post",
					url : "<%=path %>/aidgold/updateJKMoney",
					dataType:"json",
					data : {"tid":jktid,"jkmoney":val,"mid":jkmid},
					success : function(data) {
						if(data.status == 1){
							layer.open({title: '提示',content: '修改成功'});
							$('#dg').datagrid('reload');
						} else{
							layer.open({title: '提示',content: '修改失败'});
						}
					},
					error : function() {
						layer.open({title: '提示',content: '系统错误'});
					}
				});
			} else {
				layer.open({title: '提示',content: '请输入2000以上的额度'});
			}
			layer.close(index);
		});
	}
	//添加备注
	function declareFun(obj){
		layer.prompt({title:"添加备注：",value:""},function(val, index, elem){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgold/updateDeclare",
				dataType:"json",
				data : {"tid":obj.tid,"declare":val},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '添加成功'});
						$('#dg').datagrid('reload');
						//baseValuation(obj);
					}
				}
			});
			layer.close(index);
		});
	}
	//拉黑备注
	function laheiFun(lmid,lname){
		layer.prompt({title:"叫你皮，拉黑！",value:""},function(val, index, elem){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgold/lahei",
				dataType:"json",
				data : {"mid":lmid, "remark":val, "uname":lname},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '已黑，用户：'+lname});
						blacklistFun(lmid);	//刷新黑名单列表
					}
				}
			});
			layer.close(index);
		});
	}
	//移除黑名单
	function removeBlacklist(rtid, rmid){
		layer.confirm('确定给该用户一个重新做人的机会？', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgold/removeBlacklist",
				dataType:"json",
				data : {"tid":rtid},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '已移除'});
						blacklistFun(rmid);	//刷新黑名单列表
					}
				}
			});
		  layer.close(index);
		});
	}
	
	function clearSpiderContent(){
		//通讯爬虫
		$("#spiderMobileTradeNo").html("<a id='link_tradeNo' href='#'></a>");
		$("#spiderMobileDate").html("");
		$("#spiderMobileStatus").html("");
		//邮箱
		/* $("#spiderEmailTradeNo").html("");
		$("#spiderEmailDate").html("");
		$("#spiderEmailStatus").html(""); */
		$("#email_list").html("");
		//支付宝
		$("#spiderAlipayTradeNo").html("");
		$("#spiderAlipayDate").html("");
		$("#spiderAlipayStatus").html("");
		//京东
		$("#spiderJingdongTradeNo").html("");
		$("#spiderJingdongDate").html("");
		$("#spiderJingdongStatus").html("");
		//公积金
		$("#spiderFundTradeNo").html("");
		$("#spiderFundDate").html("");
		$("#spiderFundStatus").html("");
		//车险保单
		$("#spiderInsuranceTradeNo").html("");
		$("#spiderInsuranceDate").html("");
		$("#spiderInsuranceStatus").html("");
		//医保社保
		$("#spiderSecurityTradeNo").html("");
		$("#spiderSecurityDate").html("");
		$("#spiderSecurityStatus").html("");
	}
	//设置爬虫订单
	function setSpiderMobile(mid){
	
		//清空内容
		clearSpiderContent();
		
		//通讯爬虫
		$.ajax({
			type : "post",
			url : "<%=path %>/app/selectSpiderMobileByMid",
			dataType:"json",
			data : {"mid":mid},
			success : function(data) {
				if(data != null && data.status == 1 && data.msg != null){
					$("#spiderMobileTradeNo").html("<a id='link_tradeNo' href='"+server_url+ "/xinYan/spiderCarrier/CarrierDataAction?urlType=carrierReportUrl&orderNo="+ data.msg.trade_no +"' target='_blank'>" + data.msg.trade_no+ "</a>" );	// + "201805110949210145703744"
					$("#spiderMobileDate").html(formatDate(data.msg.gdate, null));
					$("#spiderMobileStatus").html(data.msg.is_done == 1?"有效":"无效");
				}
			}
		});
		
		//邮箱
		$.ajax({
			type : "post",
			url : "<%=path %>/app/selectSpiderEmail",
			dataType:"json",
			data : {"mid":mid},
			success : function(data) {
				if(data != null && data.status == 1 && data.msg != null){
					
					var email_data = "";
					var data = data.msg;
					var len = data.length;
					
					for(var i = 0; i < len; i++){	
						if(data[i].status == 1){
							//类型 订单 日期 操作
							email_data += '<tr> <td>邮 箱 账 单</td> <td>'+personTradeNo("billsUrl", data[i].trade_no)+'</td> <td>'+formatDate(data[i].cdate, null)+'</td> <td>已采集</td> </tr>';
						}
					}
					$('#email_list').html(email_data);
					
				}
			}
		});
		
		//其他个人信用信息
		$.ajax({
			type : "post",
			url : "<%=path %>/app/selectSpiderPersonMsgByMid",
			dataType:"json",
			data : {"mid":mid},
			success : function(data) {
				if(data != null && data.status == 1 && data.msg != null){
					console.log(data);
					var list = data.msg;
					var obj;	//保存单个对象
					var type;
					for(var i = 0; i<list.length; i++){
						obj = list[i];
						type = obj.txn_type;
						if(type == "email"){	//邮箱
							//邮箱
							/* $("#spiderEmailTradeNo").html(obj.trade_no);
							$("#spiderEmailDate").html(formatDate(obj.cdate, null));
							$("#spiderEmailStatus").html(personStatus(obj)); */
						} else if(type == "alipay"){	//支付宝
							//支付宝 http://mobile.rczjgj.com:7897/xinYanAPI/taobao/OrderNoAction?urlType=alipay_dataUrl&orderNo=201805191005550155000426
							$("#spiderAlipayTradeNo").html(personTradeNo("alipay_dataUrl", obj.trade_no));
							$("#spiderAlipayDate").html(formatDate(obj.cdate, null));
							$("#spiderAlipayStatus").html(personStatus(obj));
						} else if(type == "jingdong"){	//京东
							//京东
							$("#spiderJingdongTradeNo").html(personTradeNo("jingdong_detailsUrl", obj.trade_no));
							$("#spiderJingdongDate").html(formatDate(obj.cdate, null));
							$("#spiderJingdongStatus").html(personStatus(obj));
						} else if(type == "fund"){	//公积金
							//公积金
							$("#spiderFundTradeNo").html(personTradeNo("fund_detailsUrl", obj.trade_no));
							$("#spiderFundDate").html(formatDate(obj.cdate, null));
							$("#spiderFundStatus").html(personStatus(obj));
						} else if(type == "security"){	//医保社保
							//医保社保
							$("#spiderSecurityTradeNo").html(personTradeNo("security", obj.trade_no));
							$("#spiderSecurityDate").html(formatDate(obj.cdate, null));
							$("#spiderSecurityStatus").html(personStatus(obj));
						} else if(type == "insurance"){	//车险保单
							//车险保单
							$("#spiderInsuranceTradeNo").html(personTradeNo("insurance_detailsUrl", obj.trade_no));
							$("#spiderInsuranceDate").html(formatDate(obj.cdate, null));
							$("#spiderInsuranceStatus").html(personStatus(obj));
						}
					}
					/* $("#spiderMobileTradeNo").html("<a id='link_tradeNo' href='"+server_url+ "/xinYan/spiderCarrier/CarrierDataAction?urlType=carrierReportUrl&orderNo="+ data.msg.trade_no +"' target='_blank'>" + data.msg.trade_no+ "</a>" );	// + "201805110949210145703744"
					$("#spiderMobileDate").html(formatDate(data.msg.gdate, null));
					$("#spiderMobileStatus").html(data.msg.is_done == 1?"有效":"无效"); */
				}
			}
		});
	}
	
	//格式化个人信息成链接
	function personTradeNo(urlType, trade_no){
		var con = "";
		if(urlType == "jingdong_detailsUrl"){
			con = "&page=&pageSize=";
		} else if(urlType == "security"){
			return "<a href='"+server_url+ "/xinYanAPI/spiderSecurity/SecurityDataAction?urlType=infoUrl&orderNo="+ trade_no +"' target='_blank'>" + trade_no+ "</a>";
		} else if(urlType == "fund_detailsUrl"){
			return "<a href='"+server_url+ "/xinYanAPI/fund/FundDataAction?urlType=fundResultUrl&orderNo="+ trade_no +"' target='_blank'>" + trade_no+ "</a>";
		} else if(urlType == "insurance_detailsUrl"){
			return "<a href='"+server_url+ "/xinYanAPI/insurance/InsuranceDataAction?urlType=insurancepolicyUrl&orderNo="+ trade_no +"' target='_blank'>" + trade_no+ "</a>";
		}
		return "<a href='"+server_url+ "/xinYanAPI/taobao/OrderNoAction?urlType="+urlType+"&orderNo="+ trade_no + con +"' target='_blank'>" + trade_no+ "</a>";
	}
	
	//格式化个人信息的状态成链接
	function personStatus(obj){
		var sta = "";
		var used = obj.is_used;
		var done = obj.is_done;
		var valid = obj.is_valid;
		if (used == 1){
			return "已提额";
		} else if(done == 0) {
			return "未采集";
		} else if(valid == 0){
			return "待更新";
		} else if(done == 1 && used == 0){
			return "<a href='javascript:;' onclick='personStatusFun("+mid+ ",\""+ obj.trade_no + "\",\""+obj.txn_type+"\")'>可提额</a>";
		}
	}
	
	//更新 提额
	function personStatusFun(mid, tradeNo, type){
		layer.prompt({title:"请输入提升额度",value:""},function(value, index, elem){
			$.ajax({
				type:"post",
				url:'<%=path %>/aidgold/xy/update/balance',
				dataType:"json",
				data : {"money":value, "mid":mid, "txn_type":type, "tradeNo":tradeNo},
				success:function(data){
					if(data.status == 1){
						layer.open({title: '提示',content: data.msg});
					} else {
						layer.open({title: '提示',content: data.msg});
					}
				},
			});
			
			layer.close(index);
		});
	}
			<%-- $.ajax({
				type : "post",
				url : "<%=path %>/aidgold/setAgent",
				dataType:"json",
				data : {"tid":saTid,"mobile":value},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '修改成功'});
						$('#dg').datagrid('reload');
					} else{
						layer.open({title: '提示',content: '修改失败'});
					}
				},
				error : function() {
					layer.open({title: '错误',content: '请输入正确的代理商手机号'});
				}
			}); --%>
		<%-- layer.confirm('是否已提额', {icon: 3, title:'提示'}, function(index){
			sendRequest("<%=path %>/app/updateSpiderPersonByColumnName",
				{"mid":mid, "columnName":"is_used", "value":1, "txn_type":type}, "json");
				
				setTimeout(function(){
					setSpiderMobile(mid);
				},200);
		  layer.close(index);
		}); --%>
	
	//获取信用信息
	function setCredit_msg(){
		     
		//清空内容
		$(".overdue_num").html("");
		$(".overdue_no_repay").html("");
		$(".online_time").html("");
		$(".connect_num").html("");
		$(".connect_6m").html("");
		$(".risk_call").html("");
		$(".law_num").html("");
		$(".credit_msg_date").html("");
		$(".debt_count").html("");
		$(".debt_new_old").html("");
		$(".user_age").html("");
		$(".credit_score").html("");
		
	
		$.ajax({
			type : "post",
			url : "<%=path %>/app/selectCreditMsgByMid",
			dataType:"json",
			data : {"mid":mid},
			success : function(data) {
				if(data != null && data.status == 1 && data.msg != null){
				
					var law_num = data.msg.law_num;						//涉法涉诉
					var online_time = data.msg.online_time;				//在网时长
					var connect_6m_all = data.msg.connect_6m_all;		//最近联系人数
					var overdue_no_repay = data.msg.overdue_no_repay;	//逾期未还款数
					
					var overdue_num = data.msg.overdue_num;				//逾期数
					var connect_num = data.msg.connect_num;				//常用联系人数
					var user_age = data.msg.user_age;					//更新用户年龄
					var debt_count = data.msg.debt_count;				//共债详情条数
					
					var debt_new_old = data.msg.debt_new_old;			//借新还旧数
					var risk_call = data.msg.risk_call;					//风险电话
					
					//计算分数
					var credit_score = 10;
					if(law_num > 0 || online_time < 6 || connect_6m_all != 2 || overdue_no_repay > 0){
						credit_score = 0;
					} else {
						credit_score -= (overdue_num * 1);
						credit_score -= ((2-connect_num) * 1);
						if(46 <= user_age && user_age <= 55) credit_score -= 1; 
						credit_score -= (debt_count * 0.5);
						
						if(credit_score < 0) credit_score = 0; 
					}
					
					if(credit_score >= 5){
						$(".credit_score").html(formatFontColor(credit_score, "green"));
					} else {
						$(".credit_score").html(formatFontColor(credit_score, "red"));
					}
					
				
					//逾期数
					if(overdue_num == 0){
						$(".overdue_num").html(formatFontColor(overdue_num, "green"));
					} else {
						$(".overdue_num").html(formatFontColor(overdue_num, "red"));
					}
				
					//逾期未还款数
					if(overdue_no_repay == 0){
						$(".overdue_no_repay").html(formatFontColor(overdue_no_repay, "green"));
					} else {
						$(".overdue_no_repay").html(formatFontColor(overdue_no_repay, "red"));
					}
				
					//在网时长
					if(online_time >= 6){
						$(".online_time").html(formatFontColor(online_time, "green"));
					} else if(online_time == -1) {
						$(".online_time").html(formatFontColor("过户", "red"));
					} else if(online_time == -2) {
						$(".online_time").html(formatFontColor("沉默", "red"));
					} else {
						$(".online_time").html(formatFontColor(online_time, "red"));
					}
				
					//常用联系人数
					if(connect_num == 2){
						$(".connect_num").html(formatFontColor(connect_num,  "green"));
					} else {
						$(".connect_num").html(formatFontColor(connect_num,  "red"));
					}
				
					//最近联系人数
					if(connect_6m_all == 2){
						$(".connect_6m").html(formatFontColor(connect_6m_all,  "green"));
					} else {
						$(".connect_6m").html(formatFontColor(connect_6m_all,  "red"));
					}
				
					//风险电话
					if(risk_call == 0){
						$(".risk_call").html(formatFontColor(risk_call,  "green"));
					} else {
						$(".risk_call").html(formatFontColor(risk_call,  "red"));
					}
				
					//共债详情条数
					if(debt_count == 0){
						$(".debt_count").html(formatFontColor(debt_count,  "green"));
					} else {
						$(".debt_count").html(formatFontColor(debt_count,  "red"));
					}
				
					//借新还旧数
					if(debt_new_old == 0){
						$(".debt_new_old").html(formatFontColor(debt_new_old,  "green"));
					} else {
						$(".debt_new_old").html(formatFontColor(debt_new_old,  "red"));
					}
				
					//涉法涉诉 
					if(aginfo != null && memberAuthor != null){
						var sf_res = "";
						if(law_num > 0){
							sf_res = formatFontColor(law_num,  "red");
						} else {
							sf_res = formatFontColor(law_num,  "green");
						}
					
						$(".law_num").html("<a href='javascript:;' onclick='jieanFun("+
							JSON.stringify({"name":aginfo.sname, "idno":memberAuthor.idno, "mobile":aginfo.mobile})+
								",\"SFSUTJ\""+")' style='color:blue;'>"+sf_res+"</a>");
					}
					/* if(data.msg.law_num == 0){
						$(".law_num").html(formatFontColor(data.msg.law_num,  "green"));
					} else {
						$(".law_num").html(formatFontColor(data.msg.law_num,  "red"));
					} */
				
					//更新用户年龄
					if(46 <= user_age && user_age <= 55){
						$(".user_age").html(formatFontColor(user_age,  "red"));
					} else {
						$(".user_age").html(formatFontColor(user_age,  "green"));
					}
					
					$(".credit_msg_date").html(formatDate(data.msg.gdate, null));
					
				}
			}
		});
	}
	
	//更新信用信息
	function update_credit_msg(){
		
		layer.confirm('如果有记录就不建议更新', {icon: 3, title:'没记录或者记录太旧？'}, function(index){
			
			// 更新逾期记录
			update_overdue();
			
			// 1.更新手机在网时长 2.更新常用联系人
			update_mobile_msg();
			
			// 更新涉法涉诉次数
			update_jiean_shefashesu();
			
			// 更新共债信息
			update_debt_msg();
			
			// 更新年龄
			update_user_age();
			
			layer.close(index);
		});
		
	}
	
	//选择驳回类型
	function selectResetType(){
		window.$("#resetTypeWrap").dialog('open');
	}
	function resetFun(){
		cresetID($("#resetType").val());
		window.$("#resetTypeWrap").dialog('close');
	}
	//重置身份信息
	function cresetID(retype){
		var crmid = $('#mid').val();
		layer.confirm('是否驳回，并让客户修改信息('+crmid+')?', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgold/resetIdentity",
				dataType:"json",
				data : {"mid":crmid,"tid":tid,"retype":retype},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '驳回成功'});
						$('#dg').datagrid('reload');
					} else{
						layer.open({title: '提示',content: '驳回失败'});
					};
					if(retype == 1001){
						//清除照片
						$('#front').html("<img class='idPic' src=''>");
						$('#back').html("<img class='idPic' src=''>");
						$('#hand').html("<img class='idPic' src=''>");
						$('#agentPic').html("<img class='idPic' src=''>");
					}
				},
				error : function() {
					layer.open({title: '提示',content: '系统错误'});
				}
			});
		  layer.close(index);
		});
	}

	//根据mid查询
	function selByMid(mlbmid){
		console.log("selByMid");
		console.log(memberAuthor);
		console.log(aginfo);
		
		// t_zjgj_member_addr 表
		var detailInfo = getObj("<%=path %>/aidgold/selectDetailInfo?mid="+mlbmid);
		var bcNo = detailInfo.cardno;
		
		$('#address').html(detailInfo.address);	//身份证地址
		$('.bankMaster').html(detailInfo.master);	//银行卡姓名
		$('.bankname').html(detailInfo.bankname);	//银行名称
		$('.bankMobile').html(detailInfo.mobile);	//银行卡预留手机号
		$('.bankCardno').html("<a href='http://www.guabu.com/bank?cardid="+bcNo+"' target='_blank' style='display:inline-block; width:60%; color:blue;'>"+bcNo+"</a>");	//卡号
		
		
		var idno = "";
		if(memberAuthor != null && memberAuthor != ""){
			idno = memberAuthor.idno;
		}
		//身份证照片基础地址
		var idPicBasePath = '<%=path %>/uploadFile/idPicture/';
		//给页面赋值
		if(aginfo != null){
			
			$('#mid').val(aginfo.mid); 
			//$('.stid').html(aginfo.tid);
			$('.smid').html(aginfo.mid);
			$('.sna').html(aginfo.sname);
			$('.mob').html(aginfo.mobile);	//display:inline-block; width:60%; "<a href='javascript:;' onclick='jieanFun("+JSON.stringify(jieanParam)+",\"MPTIME\""+")' style='color:blue;'>"+aginfo.mobile+"</a>"
			$('.wei').html(aginfo.weixin);
			$('.ema').html(aginfo.email);
			$('.cn1').html(aginfo.contactName1);
			$('.cr1').html(aginfo.contactRela1);
			$('.ct1').html(aginfo.contactTele1);
			$('.idno1').html(aginfo.contactIdno1);
			$('.cn2').html(aginfo.contactName2);
			$('.cr2').html(aginfo.contactRela2);
			$('.ct2').html(aginfo.contactTele2);
			$('.idno2').html(aginfo.contactIdno2);
			$('.idno').html(idno);	//"<a href='javascript:;' onclick='jieanFun("+JSON.stringify(jieanParam)+",\"SFSUTJ\""+")' style='color:blue;'>"+idno+"</a>"
			if(idno != ""){
				//身份证正面照
				$('#front').html("<img class='idPic' src='"+idPicBasePath + aginfo.mobile + "/1.jpg" +"'>");
				//身份证背面照
				$('#back').html("<img class='idPic' src='"+idPicBasePath + aginfo.mobile + "/2.jpg" +"'>");
				//手持身份证照片
				$('#hand').html("<img class='idPic' src='"+idPicBasePath + aginfo.mobile + "/3.jpg" +"'>");
				//信审合照
				$('#agentPic').html("<img class='idPic' src='"+idPicBasePath + aginfo.mobile + "/4.jpg" +"'>");
			}
			//打开弹框
			//window.$("#selByMidContent").dialog('open'); 
		} else{
			//$.messager.alert("提示", "未查询到用户信息", "info");
			//window.$("#selByMidContent").dialog('open');
			$('#mid').val(0);
			//$('.stid').html("");
			$('.smid').html("");
			$('.sna').html("");
			$('.mob').html("");
			$('.wei').html("");
			$('.ema').html("");
			$('.cn1').html("");
			$('.ct1').html("");
			$('.idno1').html("");
			$('.cn2').html("");
			$('.ct2').html("");
			$('.idno2').html("");
			$('.idno').html("");
			$('.cr1').html("");
			$('.cr2').html("");
			//身份证正面照
			$('#front').html("");
			//身份证背面照
			$('#back').html("");
			//手持身份证照片
			$('#hand').html("");
			
			$('#address').html("");	//身份证地址
			$('.bankMaster').html("");	//银行卡姓名
			$('.bankname').html("");	//银行名称
			$('.bankMobile').html("");	//银行卡预留手机号
			$('.bankCardno').html("");	//卡号
		
		}
	}
	
	function jieanFun(jobj, jtype){
		var warn = "";
		var searchParam = "";
		if(jtype == "MPTIME"){
			warn = "在网时长";
			searchParam = "?mobile=" + jobj.mobile;
		} else if(jtype == "SFSUTJ"){
			warn = "涉法涉诉统计";
			searchParam = "?name=" + jobj.name + "&idno=" + jobj.idno; 
		}
		
		$.ajax({
			type : "post",
			url : "<%=path %>/aidgold/jiean"+searchParam,
			dataType:"json",
			data : {"type":jtype},
			success : function(data) {
				console.log(data);
				var con = "";
				if(jtype == "MPTIME"){
					con += "在网时常:"+data.OUTPUT1+"月";
				} else if(jtype == "SFSUTJ"){
					con += "案例：" + data.ANLI + "次<br>";
					con += "催钱：" + data.CUIQIAN + "次<br>";
					con += "失信：" + data.SHIXIN + "次<br>";
					con += "税务：" + data.SHUIWU + "次<br>";
					con += "网贷：" + data.WANGDAI + "次<br>";
					con += "执行：" + data.ZHIXIN + "次<br>";
				}
				layer.open({title: '提示',content: con});
			}
		}); 
		   
	}
	
	//查询用户刷卡，额度信息
	function selLogCon(slcmid, slctid){
		//查询地址
		var surl = '<%=path %>/aidgold/selLogCon?mid=' + slcmid + '&stype=xygd';	
		//查询用户刷卡，额度信息
		$.ajax({
			type:"post",
			url:surl,
			dataType: "",
			success:function(data){
				if(data == ""){
					//$.messager.alert("提示", "无记录", "info");
					$("#tradeRecord").html("未查询到记录");
				} else {
					data = data.substring(1,(data.length-1));
					//$.messager.alert("提示", data);
					$("#tradeRecord").html(data);
					
					var personMoney = getMiddleContent(data, ":" , "<br>", 1);	//个人刷卡量
					var totleMoney = getMiddleContent(data, "量" , "<br>", 2);	//刷卡总量
					if("" != totleMoney && "null" != personMoney){
						if(personMoney >= 30000 && totleMoney < 50000){
							$("#teMSG").html("满足提额条件（3%）");
							$("#teBtn").html(formatTEBtn(slcmid,slctid,3));
						} else if(personMoney >= 30000 && totleMoney >= 100000){
							$("#teMSG").html("满足提额条件（5%）");
							$("#teBtn").html(formatTEBtn(slcmid,slctid,5));
						} else if(personMoney >= 30000 && totleMoney >= 50000 && totleMoney < 100000){
							$("#teMSG").html("满足提额条件（10%）");
							$("#teBtn").html(formatTEBtn(slcmid,slctid,10));
						} else{
							$("#teMSG").html("未满足提额条件");
							$("#teBtn").html("");
						}
					}
				}
			},
		});
	}
	//提额按钮
	function formatTEBtn(fmid,ftid,fterate){
		return "<button onclick='increaseAmount(" + fmid + "," + ftid + "," + fterate + ")'>提额</button>";
	}
	//提额
	function increaseAmount(iamid, iatid, terate){
		layer.confirm('是否为用户提额(用户编号：'+iamid+')?', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:"post",
				url:'<%=path %>/aidgold/increaseAmount',
				dataType:"json",
				data : {"tid":iatid, "mid":iamid, "terate":terate},
				success:function(data){
					if(data.status == 1){
						layer.open({title: '提示',content: data.msg});
					} else {
						layer.open({title: '提示',content: data.msg});
					}
				},
			});
		  layer.close(index);
		});
	}
	//截取字符串
	function getMiddleContent(strCon, startStr, endStr, offset){
		var posStart = strCon.indexOf(startStr);
		var posEnd = strCon.indexOf(endStr,posStart);
		var resCon = "";
		if (posStart != -1 && posEnd != -1) {
			resCon = strCon.substring(posStart + offset, posEnd).trim();
		}
		console.log(posStart + " " + posEnd + " " + resCon);
		return resCon;
	}
	
	//打开助力金代理商总账户
	function openAidgoldAgent(){
		var url = '<%=path %>/aidgoldAgent.jsp';
		window.$("#aidgoldAgentFrame").removeAttr("src");
		window.$("#aidgoldAgentFrame").attr("src", url);
		window.$("#dialogAidgoldAgent").dialog('open'); 
	}
	//扣款详情弹出框
	function koukuan(tid){
		var url = '<%=path %>/koukuanCheck.jsp?tid=' + tid;
		window.$("#kkFrame").removeAttr("src");
		window.$("#kkFrame").attr("src", url);
		window.$("#kkContent").dialog('open'); 
	}
	//还款历史
	function huankuan(hktid){
		//var url = '/epayPaymentSimple.jsp?tid=' + tid;
		var url = '<%=path %>/aidgoldBack.jsp?tid=' + hktid;
		window.$("#hkHistory").removeAttr("src");
		window.$("#hkHistory").attr("src", url);
		//window.$("#hkContent").dialog('open'); 
	}
	//借款历史弹出框
	function jkhistory(jkhmid){
		//var url = '/epayPaymentSimple.jsp?tid=' + tid;
		var url = '<%=path %>/aidgoldHistory.jsp?mid=' + jkhmid;
		window.$("#jkhFrame").removeAttr("src");
		window.$("#jkhFrame").attr("src", url);
		//window.$("#jkhContent").dialog('open'); 
	} 
	//通用弹出dialog openDialogFun
	function creditFun(cmid){
		//$(".panel-title").html(tit);
	
		var url = '<%=path %>/card.jsp?mid=' + cmid;
		window.$("#creditFrame").removeAttr("src");
		window.$("#creditFrame").attr("src", url);
		//window.$("#commonDialogWrap").dialog('open'); 
	}
	//催收弹出框
	function csOpen(cstid, csmid){
		var url = '<%=path %>/aidgoldCSList.jsp?tid=' + cstid + "&mid=" + csmid;
		window.$("#csFrame").removeAttr("src");
		window.$("#csFrame").attr("src", url);
		window.$("#csContent").dialog('open'); 
	}
	function csRecord(CRTid, CRMid){
		var url = '<%=path %>/aidgoldCSList.jsp?tid=' + CRTid + "&mid=" + CRMid;
		window.$("#csRecord").removeAttr("src");
		window.$("#csRecord").attr("src", url);
	}
	function scenicFun(){
		var url = '<%=path %>/page?pageName=scenicList';
		window.$("#scenic").removeAttr("src");
		window.$("#scenic").attr("src", url);
	}
	
	//还款分期弹出框
	function hkOpen(HOtid){
		var url = '<%=path %>/aidgoldShowFQ.jsp?tid=' + HOtid;
		window.$("#hkFrame").removeAttr("src");
		window.$("#hkFrame").attr("src", url);
		window.$("#hkContent").dialog('open'); 
	}
	function fqRecord(FRtid){
		var url = '<%=path %>/aidgoldShowFQ.jsp?tid=' + FRtid;
		window.$("#fqRecord").removeAttr("src");
		window.$("#fqRecord").attr("src", url);
	}
	
	//getApplyResult
	function getApplyResult(umid,utid){
		$('#comfirmPassInp').val("");	//清楚强制通过密码
		
		var agobj = getObj("<%=path %>/aidgold/selectByPid?tid="+utid);
		<%-- var detailInfo = getObj("<%=path %>/aidgold/selectDetailInfo?tid="+utid+"&mid="+umid); --%>
		
		//没有代理商则需添加代理商
		if(agobj != null && agobj.agentid == 0){
			setAgent(utid);
		}
		
		//获取备注信息
		if (agobj != null && agobj.remark != null) {
			$('#agremark').html("备注信息："+agobj.remark);
		} else {
			$('#agremark').html("备注信息："+"未查询到结果");
		}
			
		jkmoney = agobj.jkmoney;	//用于判断是否是体验贷
		
		if(jkmoney < 2000){	//jkmoney < 2000为体验贷
			tyCheck = TYCheck(umid, utid);	//查询体验贷状态
		}
		/* if(detailInfo != null){
			$('#address').html("住址："+detailInfo.address);
		} else{
			$('#address').html("住址：未查询到住址");
		} */
	
		$('#comfirmPassDiv').hide();	//隐藏密码框
		$('#passMsg').html("");	//清空密码提示
		$("#applyResult").dialog("open");
		$.ajax({
			type: "post",
			dataType: "json",
			url: '<%=path %>/aidgold/getApplyResult?mid=' + umid,
			success:function(data){
				if(data != null){
					jkcResult = data.msg;
					if(data.msg == "true" || tyCheck == 1){
						//$('#agreeBtn').show();
						$('#applyResultMsg').html("系统检验贷款条件：<span style='color:green;'>满足</span>");
					} else if(data.msg == "false" && jkmoney >= 2000 || tyCheck == 0){
						//$('#agreeBtn').hide();
						$('#applyResultMsg').html("系统检验贷款条件：<span style='color:red;'>不满足</span>");//状态码："+data.status
					}
				} else {
					$('#applyResultMsg').html("系统检验贷款条件：未查询到结果");
				}
			},
			error:function(){
			}
		});
		uptid = utid;
		var urlAddress = "http://mobile.rczjgj.com/DataHandler.ashx?action=aidgold_houtai&mid="+umid;
		var moneyUrl = '<%=path %>/aidgold/callUrl?urlAddress=' + urlAddress;
		var moneyObj = getObj(moneyUrl);
		$('#loanMoney').html(moneyObj.xygd);
	}
	
	function setAgent(saTid){
		layer.prompt({title:"代理商手机",value:""},function(value, index, elem){
			$.ajax({
				type : "post",
				url : "<%=path %>/aidgold/setAgent",
				dataType:"json",
				data : {"tid":saTid,"mobile":value},
				success : function(data) {
					if(data.status == 1){
						layer.open({title: '提示',content: '修改成功'});
						$('#dg').datagrid('reload');
					} else{
						layer.open({title: '提示',content: '修改失败'});
					}
				},
				error : function() {
					layer.open({title: '错误',content: '请输入正确的代理商手机号'});
				}
			});
			layer.close(index);
		});
	}
	
	//点击同意借款检查
	function inputPass(){
		if(jkcResult == "true" || tyCheck == 1){	//系统检测合格可直接修改状态
			updateJKCheck2(11);
		} else if(jkcResult == "false" && jkmoney >= 2000 || tyCheck == 0){
			$('#comfirmPassDiv').show();	//系统检测不合格显示密码输入框
			var pass = $("#comfirmPassInp").val();	//获取密码框密码
			if(pass == ""){
    			//$('#passMsg').html("系统检测未通过，请输入管理员密码"); 
			} else{
				//和配置文件的密码匹配
				if(pass == "<%=managerPass %>"){	
					updateJKCheck2(11);
				} else {
					$('#passMsg').html("密码错误"); 
				}
			}
		}
	}
	//查询是否满足体验贷要求
	function TYCheck(tymid, tytid){
		var result;
		$.ajax({
			async: false,
			type: "post",
			dataType: "json",
			url: '<%=path %>/aidgold/tyCheck?mid=' + tymid + '&tid=' + tytid,
			success:function(data){
				result = data.status;
			},
			error:function(){
			}
		});
		return result;
	}
	//更新jkcheck同意11 不同意12
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
		var url = '<%=path %>/aidgold/updateStatus';
		var fkdate = $('#fkdate').datebox('getValue');	//借款日期
		var ujknewStatus = $('#newStatus').val() + "&fkdate="+fkdate; //在修改的状态后面拼接借款日期参数
		//console.log("获取记录的tid为:"+jkcs);
		
		//调用ajax方法
		updatestatus(url, ujctid, ujknewStatus);
		
		//关闭dialog
		$("#updateStatus").dialog("close");
		
		//放款后重新生成分期表，避免错误的日期和借款金额
		if ($('#newStatus').val() == 43) {
			setTimeout(function(){
				sendRequest("<%=path %>/aidgoldFQHK/deleteFQList", {"tid":ujctid}, "json");
				setTimeout(function(){
					sendRequest("<%=path %>/aidgoldFQHK/showFQ", {"tid":ujctid}, "json");
				},100);
			},100);
		}
	}
	//模拟代理商同意放款
	function updateJKCheck3(){
		var url = '<%=path %>/aidgold/updateStatus';
		
		var ujknewStatus = $('#newStatus3').val();
		
		//调用ajax方法
		updatestatus(url, ujctid, ujknewStatus);
		//关闭dialog
		$("#updateStatus3").dialog("close");
	}
	//微信手续费支付成功 处理
	function updateJKCheck4(){
		var url = '<%=path %>/aidgold/updateStatus';
		
		var ujknewStatus = $('#newStatus4').val();
		
		//调用ajax方法
		updatestatus(url, ujctid, ujknewStatus);
		//关闭dialog
		$("#updateStatus4").dialog("close");
	}
	//u 31 to 21
	function u31to21Fun(){
		/* u31to21 */
		var url = '<%=path %>/aidgold/updateStatus';	
		
		var ujknewStatus = $('#status21').val();
		
		//调用ajax方法
		updatestatus(url, ujctid, ujknewStatus);
		//关闭dialog
		$("#u31to21Wrap").dialog("close");
	}
	
	function countJKmoney(){
		regTimeFrom = $('#regTimeFrom').datebox('getValue');
		regTimeTo = $('#regTimeTo').datebox('getValue');
		
		skname = $("#skname").val();
		jkcheck = $("#jkcheck").val();
		
		//alert(regTimeFrom + " "+ regTimeTo + " "+skname + " " + jkcheck);
		
		$.ajax({
			type:"post",
			url:'<%=path %>/aidgold/countJKmoney',
			dataType:"json",
			data : {"regTimeFrom":regTimeFrom,"regTimeTo":regTimeTo,"skname":skname,"jkcheck":jkcheck},
			success:function(data){
				if(data.status=1){
					var mon = Number(data.sumJKmoney).toFixed(2).toString();
					$('#jktotle').html(mon);
				}
			}
		});
	}
	
	//更新逾期信息
	function update_overdue(){
		$.ajax({
			type:"post",
			url: xyapi_url + '/OverdueAction',
			dataType:"json",
			data : {"urlType":"Url_overdue",
					"id_no":memberAuthor.idno,
					"id_name":aginfo.sname},
			success:function(data){
				if(data.success == true){
				
					var count = 0;	//逾期记录数
					var count_no_repay = 0;	//逾期未还款订单数
					
					if(data.data.code == 0){
						count = data.data.result_detail.order_count;
						
						if(count > 0){	//有逾期记录，查看用户是否已还款
							var overdue_list = data.data.result_detail.details;
							for(var i = 0; i < count; i++){
								if(overdue_list[i].settlement == "N"){
									count_no_repay++;
								}
							}
						}
					}
					
					//保存逾期数
					sendRequest("<%=path %>/app/update_credit_msg", 
						{"mid":mid,"credit_type":"overdue_num","count":count}, "text");
					if(count == 0){
						$(".overdue_num").html(formatFontColor(count, "green"));
					} else {
						$(".overdue_num").html(formatFontColor(count, "red"));
					}
					
					//保存未还逾期数
					setTimeout(function(){
						sendRequest("<%=path %>/app/update_credit_msg", 
							{"mid":mid,"credit_type":"overdue_no_repay","count":count_no_repay}, "text");
					},100);
					
					if(count_no_repay == 0){
						$(".overdue_no_repay").html(formatFontColor(count_no_repay, "green"));
					} else {
						$(".overdue_no_repay").html(formatFontColor(count_no_repay, "red"));
					}
					
					
				} else {
					console.log("更新逾期信息 update_overdue() --》 失败 errorMsg:" + data.errorMsg + "  errorCode：" + data.errorCode);
				}
			},
			error : function(){
				console.log("更新逾期信息 update_overdue() --》 错误");
			}
		});
	}
	
	//1.更新手机在网时长 2.更新常用联系人
	function update_mobile_msg(){
		
		var spiderMobileTradeNo = $("#link_tradeNo").html();	//爬虫订单号
		
		if (spiderMobileTradeNo == "" || spiderMobileTradeNo.length == 0){
			return ;
		}
	
		$.ajax({
			type:"post",
			url: xyapi_url + '/spiderCarrier/CarrierDataAction',
			dataType:"json",
			data : {"urlType":"carrierReportUrl", "orderNo":spiderMobileTradeNo},
			success:function(data){
				if(data.success == true){
					var count = 0;	//
					if(true){	//运营商 姓名 和 客户提供的姓名 必须一致 (暂不判断) data.data.user_basic_info.name == data.data.user_basic_info.name_from_custom
						/* 
							越高越风险
							通话评分和短信评分都为0时，号码沉默度为10（不活跃用户）
							通话评分或短信评分一个为1一个为0，号码沉默度为6（正常活跃用户）
							通话评分和短信评分都为1时，号码沉默度为3（正常活跃用户）
							通话评分和短信评分只要一个为2时，号码沉默度为1（异常活跃用户）
						 */
						if(data.data.friend_circle.risk_analysis.mobile_silence_3m <= 6){	//
							count = data.data.friend_circle.risk_analysis.in_time;	//在网时长
						} else {	//静默号码
							$(".online_time").html(formatFontColor("静默号码", "red"));
							count = -2;
						}
					} else {
						$(".online_time").html(formatFontColor("过户", "red"));
						count = -1;
					}
					
					if(count >= 6){
						$(".online_time").html(formatFontColor(count, "green"));
					} else if(count == -1) {
						$(".online_time").html(formatFontColor("过户", "red"));
					} else if(count == -2) {
						$(".online_time").html(formatFontColor("沉默", "red"));
					} else {
						$(".online_time").html(formatFontColor(count, "red"));
					}
					
					//保存 在网时长
					sendRequest("<%=path %>/app/update_credit_msg", 
						{"mid":mid,"credit_type":"online_time","count":count}, "text");
					
					
					
					count = 0;
					
					var db_number = new Array(aginfo.contactTele1, aginfo.contactTele2);
					var number = new Array();
					
					//把近3个月最常联系的人放入数组
					for(var i=0;i<data.data.friend_circle.call_num_top3_3m.length; i++){
						number[i] = data.data.friend_circle.call_num_top3_3m[i].peer_number;
					}
					
					//把近6个月最常联系的人放入数组
					for(var i=0;i<data.data.friend_circle.call_num_top3_6m.length; i++){
						number[i + data.data.friend_circle.call_num_top3_3m.length] = 
							data.data.friend_circle.call_num_top3_6m[i].peer_number;
					}
					
					//匹配
					for(var i=0; i < 2; i++){
						for(var j=0;j < number.length; j++){
							if(db_number[i] == number[j]){
								count++;
								break;
							}
						}
					}
					
					if(count == 2){
						$(".connect_num").html(formatFontColor(count, "green"));
					} else{
						$(".connect_num").html(formatFontColor(count, "red"));
					}
					
					//保存 常用联系人 记录
					setTimeout(function(){
						sendRequest("<%=path %>/app/update_credit_msg", 
							{"mid":mid,"credit_type":"connect_num","count":count}, "text");
					},200);
					
					
					//查询用户填写的联系人是否在近6个月联系人内
					var count_6m = 0;
					var contact_list = data.data.friend_circle.call_contact_detail;
					var contact_num = contact_list.length;
					//匹配
					for(var i=0; i < 2; i++){
						for(var j=0;j < contact_num; j++){
							if(db_number[i] == contact_list[j].peer_num){
								console.log(db_number[i] + " " + contact_list[j].peer_num);
								count_6m++;
								break;
							}
						}
					}
					
					//保存
					setTimeout(function(){
						sendRequest("<%=path %>/app/update_credit_msg", 
							{"mid":mid,"credit_type":"connect_6m_all","count":count_6m}, "text");
					},200);
					
					if(count_6m == 2){
						$(".connect_6m").html(formatFontColor(count_6m, "green"));
					} else{
						$(".connect_6m").html(formatFontColor(count_6m, "red"));
					}
					
					
					
					//风险电话统计 110 120 贷款公司 信用卡 澳门地区 催收公司
					var count_risk = 0;
					for(var i=0; i<data.data.friend_circle.risk_analysis.risk_check_item.length; i++){
						count_risk += parseInt(data.data.friend_circle.risk_analysis.risk_check_item[i].call_num_6m);
					}
					
					if(count_risk == 0){
						$(".risk_call").html(formatFontColor(count_risk, "green"));
					} else{
						$(".risk_call").html(formatFontColor(count_risk, "red"));
					}
					
					//保存 风险通话 记录
					setTimeout(function(){
						sendRequest("<%=path %>/app/update_credit_msg", 
							{"mid":mid,"credit_type":"risk_call","count":count_risk}, "text");
					},200);
					
					
					
				} else {
					console.log("更爬虫信息 update_mobile_msg() --》 失败 errorMsg:" + data.errorMsg + "  errorCode：" + data.errorCode);
				}
			},
			error : function(){
				console.log("更爬虫信息 update_mobile_msg() --》 错误");
			}
		});
	}
	
	//更新涉法涉诉
	function update_jiean_shefashesu(){
	
		//var jieanParam = {"name":aginfo.sname, "idno":memberAuthor.idno, "mobile":aginfo.mobile};
		
		$.ajax({
			type : "post",
			url : "<%=path %>/aidgold/jiean?name=" + aginfo.sname + "&idno=" + memberAuthor.idno,
			dataType:"json",
			data : {"type":"SFSUTJ"},
			success : function(data) {
				console.log(data);
				
				sendRequest("<%=path %>/app/update_credit_msg", 
						{"mid":mid,"credit_type":"law_num","count":data.ZHIXIN}, "text");
					
				$(".law_num").html("<a href='javascript:;' onclick='jieanFun("+JSON.stringify({"name":aginfo.sname, "idno":memberAuthor.idno, "mobile":aginfo.mobile})+",\"SFSUTJ\""+")' style='color:blue;'>"+data.ZHIXIN+"</a>");
				
			}
		}); 
	}
	
	//更新共债信息
	function update_debt_msg(){
	
		//var jieanParam = {"name":aginfo.sname, "idno":memberAuthor.idno, "mobile":aginfo.mobile};
		//debt_count debt_new_old
		$.ajax({
			type : "post",
			url: xyapi_url + '/TotaldebtAction',
			dataType:"json",
			data : {"urlType":"Url_totalDebt", "id_no":memberAuthor.idno, "id_name":aginfo.sname, "versions":"1.1.0"},
			success : function(data) {
				console.log(data);
				
				var debtCount = 0;
				var debtNewOld = 0;	//借旧还新
				
				if(data.success){
					if(data.data.code == 0){
						debtCount = data.data.result_detail.totaldebt_detail.length; //近6个月共债数
						for(var i=0; i<debtCount; i++){
							if(data.data.result_detail.totaldebt_detail[i].new_or_old == "Y"){
								debtNewOld++;
							}
						}
						debtCount = data.data.result_detail.current_order_count;	//近一个月共债数
					}
				} else {
					console.log("更新共债信息 update_debt_msg() --》 失败 errorMsg:" + data.errorMsg + "  errorCode：" + data.errorCode);
				}
				
				sendRequest("<%=path %>/app/update_credit_msg", 
						{"mid":mid,"credit_type":"debt_count","count":debtCount}, "text");
						
				setTimeout(function(){
					sendRequest("<%=path %>/app/update_credit_msg", 
						{"mid":mid,"credit_type":"debt_new_old","count":debtNewOld}, "text");
				},200);
				
				
				//共债详情条数
				if(debtCount == 0){
					$(".debt_count").html(formatFontColor(debtCount,  "green"));
				} else {
					$(".debt_count").html(formatFontColor(debtCount,  "red"));
				}
				
				//借新还旧数
				if(debtNewOld == 0){
					$(".debt_new_old").html(formatFontColor(debtNewOld,  "green"));
				} else {
					$(".debt_new_old").html(formatFontColor(debtNewOld,  "red"));
				}
				
					
			}
		}); 
	}
	
	//更新年龄
	function update_user_age(){
	
		if(memberAuthor != null){
			var u_age = memberAuthor.idno.substring(6,10); //321123 **** ** ** 0012 "350301199502020057"
			
			var c_year = new Date().getFullYear();	//当前年份
			u_age = c_year - u_age;	//用户年龄
			
			setTimeout(function(){
				sendRequest("<%=path %>/app/update_credit_msg", 
					{"mid":mid,"credit_type":"user_age","count":u_age}, "text");
			},200);
			
			//用户年龄
			if(46 <= u_age || u_age <= 55 ){
				$(".user_age").html(formatFontColor(u_age,  "green"));
			} else {
				$(".user_age").html(formatFontColor(u_age,  "red"));
			}
			
		} else {
			layer.open({
			  type: 1 //Page层类型
			  ,area: ['260px', '180px']
			  ,title: '提示'
			  ,shade: 0.6 //遮罩透明度
			  //,maxmin: true //允许全屏最小化
			  ,anim: 1 //0-6的动画形式，-1不开启
			  ,content: '<div style="padding:30px;font-size:15px;">用户未实名，无法获取年龄(驳回用户实名信息需要重新实名)</div>'
			}); 
		}
		
		
	}
	
	function confirm_update(type){	//confirm_update('shefashesu')
		layer.confirm('确认更新', {icon: 3, title:'提示'}, function(index){ 
			switch(type){
				case "overdue":		update_overdue();			break;	// 更新逾期记录
				case "mobile_msg":	update_mobile_msg();		break;	// 1.更新手机在网时长 2.更新常用联系人
				case "shefashesu":	update_jiean_shefashesu();	break;	// 更新涉法涉诉次数
				case "debt":		update_debt_msg();			break;	// 更新共债信息
				case "user_age":	update_user_age();			break;	// 更新年龄
			}
			layer.close(index);
			setTimeout(function(){ alert("已更新"); },200);
		});
	}
	
	//打印出联系人
	function print_all_contact(){
		
		var spiderMobileTradeNo = $("#link_tradeNo").html();	//爬虫订单号
		
		if (spiderMobileTradeNo == "" || spiderMobileTradeNo.length == 0){
			return ;
		}
	
		$.ajax({
			type:"post",
			url: xyapi_url + '/spiderCarrier/CarrierDataAction',
			dataType:"json",
			data : {"urlType":"carrierReportUrl", "orderNo":spiderMobileTradeNo},
			success:function(data){
				if(data.success == true){
					var save_all_contact = "";
					//打印出联系人
					var contact_list = data.data.friend_circle.call_contact_detail;
					var contact_num = contact_list.length;
					
					for(var j=0;j < contact_num; j++){
						save_all_contact += ("<div style='padding: 2px;'>" + contact_list[j].peer_num + "</div>");
					}
					layer.open({
					  type: 1 //Page层类型
					  ,area: ['300px', '300px']
					  ,title: '联系人'
					  ,shade: 0.6 //遮罩透明度
					  //,maxmin: true //允许全屏最小化
					  ,anim: 1 //0-6的动画形式，-1不开启
					  ,content: save_all_contact
					});
				} else { }
			},
			error : function(){ }
		});
	}
	
	/* $(function(){
		
		
	}) */
	
	
</script>


</html>

