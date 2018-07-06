<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String scPvalue = (String)request.getParameter("scPvalue");
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>show</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/js/uploadify/uploadify.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/themes/icon.css" rel="stylesheet"type="text/css" />
	<link href="<%=path %>/js/jquery-easyui-1.4.3/demo.css" rel="stylesheet"type="text/css" />
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/jquery.easyui.min.js"type="text/javascript"></script>
	<script src="<%=path %>/js/uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="<%=path %>/js/Common.js" type="text/javascript"></script>
	<script src="<%=path %>/js/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"type="text/javascript"></script>

  </head>
  
  <body id="cardBody" style="padding: 0px; margin: 0px;">
  	
  		<div id="toolbar">
			日期：<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeFrom" />
			&nbsp;至&nbsp;
			<input type="text"  style="width:100px" class="easyui-datebox" id="regTimeTo" />&nbsp;&nbsp;
			
			类别：
				<!-- <select id="select1">
					<option value=''>请选择</option>
				</select>
				<select id="select2">
					<option value=''>请选择</option>
				</select> -->
				<select id="select3">
					<option value=''>全部</option>
					<option value='103'>最新贷款口子</option>
					<option value='101'>信用卡提额办法</option>
					<option value='102'>信用卡使用技巧</option>
					<option value='104'>信用卡办卡技术</option>
				</select>
			&nbsp;&nbsp;
		
			标题：<input type="text" id="title" placeholder="标题" />&nbsp;&nbsp;
			
			<a href="javascript:;" class="easyui-linkbutton"  onclick="searchdata()">搜索</a>&nbsp;&nbsp;
		
  			<a href="javascript:;" class="easyui-linkbutton" onclick="addArticle()">发表文章</a>
  			<!-- <a href="javascript:;" class="easyui-linkbutton" onclick="showParam()" >设置</a> -->
		</div>
        
        <!-- 内容显示区 -->  
        <table id="dg"></table>
        
       <!-- 查看文章详细内容dialog -->
       <div title="详细内容" id="quanContent" class="easyui-dialog" style="width: 90%; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
	     	<!-- <div id="quanman" style="height:30px;padding-top:10px;padding-left:20px;background:#ebebeb;"></div> -->
	        <iframe  id="extend_frame" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	   </div>
	   <!-- 查看修改栏目dialog -->
	   <div title="栏目设置" id="params" class="easyui-dialog" style="width: 800px; height:600px; overflow:hidden; padding: 0px" closed="true" buttons="#dlg-agentbuttons" data-mainkey="" data-options="resizable:true">
	        <iframe  id="paramsPages" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling=""  style="margin:0px; width:100%; height:535px;overflow:hidden;"></iframe> 
	   </div>
  </body>
  <script type="text/javascript">
	var datagrid;
	
	var regTimeFrom = ''; //日期from
	var regTimeTo = '';	//日期to
	var title = '';	//标题
	var pvalue = ''; //栏目的pvalue
	var scPvalue = <%=scPvalue %>; //school页面的pvalue
	
	var params = ''; //保存 ajax的data
	
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
							url : 'article/selectList?regTimeFrom='+regTimeFrom+'&regTimeTo='+regTimeTo+'&title='+title+'&pvalue='+pvalue, // +'&pvalue='+scPvalue
							loadMsg : '正在加载信息，请稍等......',
							singleSelect : true, //为true时只能选择单行  
							fitColumns : false, //允许表格自动缩放，以适应父容器  
							//sortName : 'xh',//当数据表格初始化时以哪一列来排序  
							//sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
							/* onLoadSuccess : compute, *///加载完毕后执行计算
							remoteSort : false,
							columns : [ [
					{ field: '', checkbox: 'true' },
					{ field: 'aid', title: '', width: 80,  align: 'center', sortable: 'true', hidden:'true'},
					{ field: 'detail', title: '详情', width: 80,  align: 'center', sortable: 'true' ,formatter : function(){return "详情";}},
                    { field: 'area', title: '地区', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'programa', title: '分类', width: 200,  align: 'center', sortable: 'true' },
					{ field: 'title', title: '标题', width: 400,  align: 'left', sortable: 'true' },
                    { field: 'author', title: '作者', width: 100,  align: 'center', sortable: 'true' },
                    { field: 'charge', title: '置顶级别', width: 100,  align: 'center', sortable: 'true' },					
					{ field: 'date', title: '发表时间', width: 200, align: 'center', sortable: 'true',
	                   formatter : function(value) {
				            var date = new Date(value);
				            var year = date.getFullYear().toString();
				            var month = (date.getMonth() + 1);
				            var day = date.getDate().toString();
				            var hour = date.getHours().toString();
				            var minutes = date.getMinutes().toString();
				            var seconds = date.getSeconds().toString();
				            if (month < 10) { month = "0" + month;
				            }if (day < 10) { day = "0" + day;
				            }if (hour < 10) { hour = "0" + hour;
				            }if (minutes < 10) { minutes = "0" + minutes;
				            }if (seconds < 10) { seconds = "0" + seconds; }
				            return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
				        }
                	},
							] ],
							pagination : true, //分页 
							rownumbers : true,//行数 
							
							onClickCell: function (rowIndex, field, value) {
								if(field == "detail"){
								var row = $('#dg').datagrid('getRows')[rowIndex];
								var aid = row.aid;
								
								var url = '<%=path %>/article/articleDetail?aid=' + aid;
								//window.parent. 调用父页面的东西
								window.$("#quanContent").dialog('open'); 
								/* var murl = 'https://'+url; */
								window.$("#extend_frame").removeAttr("src");
								window.$("#extend_frame").attr("src", url);
								}
							}
						});
					//设置分页控件 
					var p = $('#dg').datagrid('getPager');
					$(p).pagination({
						beforePageText : '第', //页数文本框前显示的汉字 
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to}  条记录   共 {total} 条记录'
					});
	}
	
	//搜索
	function searchdata() {
		//scPvalue = ''; //把school传过来的pvalue置空，不然会对后面的查询产生影响
		regTimeFrom = $('#regTimeFrom').datebox('getValue');
		regTimeTo = $('#regTimeTo').datebox('getValue');
		title = $("#title").val();
		pvalue = $('#select3 option:selected').val();
		//var sele = $("#select3").val();
		//var sele2 = $("#select2").val();
		/* if(sele != ''){
			programa = $('#select3 option:selected').text();
		} else if(sele2 != ''){
			programa = $('#select2 option:selected').text();
		} else {
			programa = '';
		}
        var searchJson = { "regTimeFrom": regTimeFrom,  "regTimeTo": regTimeTo , "phone":phone}; */
		
		bindDatagrid();
	}
	
	//在父页面的弹出框dialog里面添加文章
	function addArticle(){
		var url = '<%=path %>/articleInsert.jsp';
		//window.parent. 调用父页面的东西 title="详细内容"
		window.$("#quanContent").dialog('open'); 
		/* var murl = 'https://'+url; */
		window.$("#extend_frame").removeAttr("src");
		window.$("#extend_frame").attr("src", url);
	}
	
	//用dialog展示param页面
	function showParam(){
		var purl = '<%=path %>/pageChange?page=paramList';
		$("#params").dialog('open');
		$("#paramsPages").removeAttr("src");
		$("#paramsPages").attr("src", purl);
	}
	
	//展示栏目
	<%-- $(function() {
  		$.ajax({
            type:"post",
            url: "<%=path %>/param/appSelectAll",
            dataType:"json",
            success:function(data){
	           	params = data; //把data保存到全局变量中
	           	var s1 = "<option value=''>请选择</option>";
	           	for(var i=0; i<data.length; i++){
	           		if(data[i].pvalue.length == 2){
	           			s1 += "<option value='"+data[i].pvalue+"'>"+data[i].pname+"</option>" ;
	           		}
	           	}
	            $("#select1").html(s1); 
            }
         });
  	}); --%>
  	
  	//document.getElementById("select1").onchange = new Function("changeP(1)"); //给栏目第二个select赋值
   	//document.getElementById("select2").onchange = new Function("changeP(2)"); //给栏目第三个select赋值
   	
   	<%-- function changeP(val){
   		var sPvalue = '';
   		var sTag = "<option value=''>请选择</option>";
   		if(val == 1){ 
   			sPvalue = $("#select1").val();
   			for(var i=0; i<params.length; i++){
   				if(params[i].pvalue.length == 4){
   					sTag += "<option value='" + params[i].pvalue + "'>" + params[i].pname + "</option>";
   				}
   			}
   			$("#select2").html(sTag); 
   		} else if(val == 2){ 
   			sPvalue = $("#select2").val();
   			$.ajax({
   				type:"post",
   				url: "<%=path %>/param/selectSeriesByPvalue",
   				data: "pvalue=" + sPvalue,
	            dataType:"json",
	            success:function(data){
		           	for(var i=0; i<data.length; i++){
		           		sTag += "<option value='" + data[i].pvalue + "'>" + data[i].pname + "</option>";
		           	}
		            $("#select3").html(sTag); 
	            }
   			});
   		}
   	} --%>
	
	
	
</script>
</html>
