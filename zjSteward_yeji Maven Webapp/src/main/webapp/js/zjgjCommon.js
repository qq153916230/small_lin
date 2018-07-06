var datagrid;
	
var regTimeFrom = '';	//日期from
var regTimeTo = '';		//日期to
var status = '';	//审核状态 通过/不通过
var phone = '';			//电话号码
var mobile = '';
var tel = '';
var isreward = '';		//处理状态
var bluetype = '';		//抵押状态
var possn = '';			//机身号
var merchantname = '';	//商户名 
var merchantcode = '';	//商户号
var openid = '';	//微信号
var user_id = '';	//日期from
var aidgold_id = '';		//日期to
var sname = '';
var skname = '';	//收款账户名
var jkcheck = ''; //审核
var acctname = '';	//开户名
var mid = '';

var searchCodition = "?regTimeFrom="+regTimeFrom+"&regTimeTo="+regTimeTo+"&status="+status+
	"&phone="+phone+"&mobile="+mobile+"&isreward="+isreward+"&bluetype="+bluetype+
	"&possn="+possn+"&merchantname="+merchantname+"&merchantcode="+merchantcode;

//搜索
function searchdata() {
	regTimeFrom = $('#regTimeFrom').datebox('getValue');
	regTimeTo = $('#regTimeTo').datebox('getValue');
	
	status = $("#status").val();	//审核状态 通过/不通过
	phone = $("#phone").val();			//电话号码
	mobile = $("#mobile").val();
	tel = $("#tel").val();
	isreward = $("#isreward").val();		//处理状态
	bluetype = $("#bluetype").val();		//抵押状态
	possn = $("#possn").val();			//机身号
	merchantname = $("#merchantname").val();	//商户名 
	merchantcode = $("#merchantcode").val();	//商户号
	openid = $("#openid").val();	//微信号
	user_id = $("#userId").val();	//用户编号
	aidgold_id = $("#aidgoldId").val();	//助力金编号
	sname = $("#sname").val();
	skname = $("#skname").val();
	jkcheck = $("#jkcheck").val();
	acctname = $("#acctname").val();	//开户名
	mid = $("#mid").val();
	
	bindDatagrid();	//刷新页面
}

$(function() {
	bindDatagrid();
});

//打开弹出框
function openDialog(){
	$("#updateStatus").dialog("open");
}
//弹出框展示内容
function dialogShowContent(url){
	$("#contentFrame").removeAttr("src");
	$("#contentFrame").attr("src",url);
	$("#dialogFrame").dialog("open");
}
//修改状态 zjgjPOSMS POSStoreController
function updatestatus(url){
	var row = $('#dg').datagrid('getSelections');
	var newStatus = $('#newStatus').val();
	if(row.length>0){
		var pids = "";
		if(row){
			for(var i=0;i<row.length;i++){
				if(i==0) pids = row[i].pid;
					else pids += "," + row[i].pid;
			}
		}
		$.ajax({
			type:"post",
			url:url,
			data:'pids=' + pids + '&newStatus=' + newStatus,
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
//修改状态(带自定义id,status)
function updatestatus(url, ids, newStatus){
	ids = ids +"";
	if(ids.length>0){
		$.ajax({
			type:"post",
			url:url,
			data:'ids=' + ids + '&newStatus=' + newStatus,
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
}
//根据传入值查询一个对象
function getObj(url){
	var obj = null;
	$.ajax({
		async:false,
		type:"post",
		url:url,
		//data:'pid=' + pid,
		dataType:"json",
		complete:function(){},//ajax请求完成时隐藏loading提示
		success:function(data){
			obj = data;
		},
		error:function(){
			console.log("zjgjCommon.js/getObj(url) ==> error");
		}
	});
	return obj;
}
	
//格式化金钱
function formatprice(value, row) {
	return Number(value).toFixed(2).toString();
}
//格式化金钱
function formatprice2(value, row) {
	return value*0.01;
}
//格式化费率
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
//格式化日期（不含时间）
function formatDate(value, row){
	var date = new Date(value);
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1);
	var day = date.getDate().toString();
	
	if (month < 10) {
	    month = "0" + month;
	}if (day < 10) {
	    day = "0" + day;
	}
	return year + "-" + month + "-" + day;
}

function formatterdate(val, row) {
	if (val != null) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
}

//判断达标时间
function regTimeSelect() {
    var regTimeFrom = $('#regTimeFrom').datebox('getValue');

    var regTimeTo = $("#regTimeTo").datebox('getValue');

    if (regTimeTo < regTimeFrom && regTimeFrom != "" && regTimeTo != "") {

        $.messager.alert("提示", "截止时间必须大于开始时间", "info");
    }
}

//导出
function OutExplor(url) {
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
		data : '',// + $("#keyWords").val()  action=memlist&Execl=dataexplor&keyWords= 
		complete : function() {}, //AJAX请求完成时隐藏loading提示
		success : function(data) {
			disLoad();
			if (data == "error") {
				$('#dg').datagrid('reload');
				$.messager.alert("导出失败");
			} else {
				//$.messager.alert("导出成功");
				location.href="<%=path %>/download/"+data.file; // http://localhost:8080/zjSteward_yeji
			}
		},
		error : function() {
			disLoad();
		}
	});
}

//统计总额, 以分为单位, * 0.01才是 元
function countMoney(url, unit){
	$.ajax({
		url : url,
		type:'post',
		dataType:'text',
		success:function(data){
			if(data.length > 0){
				data = data.substring(1,(data.length-1)) * unit;
				data = formatprice(data, 0);
				$('#countMoney').html(data);
			} else {
				$('#countMoney').html('0.00');
			}
		},
		error:function(data){
			alert('error：' + data);
		},
	});
}

//发送请求
function sendRequest(url, data, dataType){
	$.ajax({
		type:"post",
		url : url,
		data: data,
		dataType: dataType,
		success:function(data){
			if(data.status == 1){
				layer.open({title: '提示',content: data.msg});
			} else {
				console.log("sendRequest() -->" + data);
			}
		},
		error:function(data){
			layer.open({title: 'warming',content: "出错了，脑阔疼！"});
		},
	});
}

//格式化状态
function formatStatus1(val) {
    switch(val){
    	case 1: return "受理中";	break;
    	case 2: return "通过";	break;
    	case 3: return "未通过";	break;
    	default: return "新申请";
    }
}
//处理方式
function posmodeFormat(val) {
    switch(val){
    	case 1: return "下发";	break;
    	case 2: return "赠送";	break;
    	case 3: return "抵押";	break;
    	default: return "未知";
    }
}
//处理状态
function isrewardFormat(val) {
    switch(val){
    	case 1: return "已分配";	break;
    	case 2: return "已激活";	break;
    	case 3: return "已达标";	break;
    	default: return "未分配";
    }
}
//抵押类型
function bluetypeFormat(val) {
    switch(val){
    	case 1: return "已抵押";	break;
    	default: return "";
    }
}
//是否关注
function attentionFormat(val) {
    switch(val){
    	case 1: return "已关注";	break;
    	default: return "取消关注";
    }
}
//红包类型
function ptypeFormat(val) {
    switch(val){
    	case 1: return "关注";	break;
    	case 2: return "分享";	break;
    	default: return "";
    }
}

//传入image对象 获取base64字符串
function getBase64Image(img) {
	var wid = 300;
	var hei = 200;
	var canvas = document.createElement("canvas");
	canvas.width = wid;	//img.width
	canvas.height = hei;	//img.height
	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0, wid, hei);	//img.width img.height
	var ext = img.src.substring(img.src.lastIndexOf(".") + 1).toLowerCase();
	var dataURL = canvas.toDataURL("image/" + ext);
	return dataURL;
}


//设置dialog 标题
function setTitle(val){
	$('.panel-title').html(val);
}

//格式化字体颜色
function formatFontColor(val, color){
	return "<span style='color:"+color+"' >"+val+"</span>";
}
