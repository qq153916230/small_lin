<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.getSession().setAttribute("url", "/fadada/syncPersonAuto");//作为上传文件servlet里面的请求转发url
// 获取助力金id
String aidgoldId = request.getParameter("tid");
request.getSession().setAttribute("aidgoldId", aidgoldId); //将助力金id保存到session，合同签署成功时用来修改 借款审核状态
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>合同</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="stylesheet" href="<%=path %>/hetong/style.css"/>
    <script src="<%=path %>/js/jquery-easyui-1.4.3/jquery-1.7.1.js"type="text/javascript"></script>
    <script src="<%=path %>/js/area.js"type="text/javascript"></script>
    <script src="<%=path %>/js/jquery.form.js"type="text/javascript"></script>
    <style>
	#submitMsg{
		display:block;
		width:100%;
		color:red;
		text-align: center;
	}

    </style>
</head>

<body>
<header>
    <p>合同提交</p>
</header>
<form id="upload_form" action="<%=path %>/upload" enctype="multipart/form-data" method="post">

    <div id="zlj_contract" >
        <div class="zlj_process" >
            <p style="">
                <span class="done"><img src="<%=path %>/hetong/img/gou.png" alt=""/>身份认证</span>
                <img src="<%=path %>/hetong/img/right.png" alt=""/>
                <span class="done"><img src="<%=path %>/hetong/img/gou.png" alt=""/>贷款申请</span>
                <img src="<%=path %>/hetong/img/right.png" alt=""/>
                <span class="doing"><img src="<%=path %>/hetong/img/quan.png" alt=""/>合同提交</span>
                <img src="<%=path %>/hetong/img/right.png" alt=""/>
                <span class="not"><img src="<%=path %>/hetong/img/quan.png" alt=""/>贷款发放</span>
            </p>
        </div>
        <div id="zlj_contract_form">
            <div class="zlj_content">
	            <p>实名认证</p>
	            <span>真实姓名</span><input id="customer_name" type="text" name="customer_name" value="" readonly><br/>
	            <span>手机号码</span><input id="mobile" type="text" name="mobile" value="" readonly><br/>
	            <span>身份证号</span><input id="id_card" type="text" name="id_card" value="" readonly><br/>
	            <!-- <span>注册邮箱</span> --><input type="hidden" name="email" value="">
	            <!-- <span>证件类型</span> --><input type="hidden" name="ident_type" value="1">
	            <!-- <span>合同类型</span> --><input type="hidden" name="doc_type" value=".pdf">
	            <!-- <span>合同编号 单号</span><input type="hidden" name="contract_id" value=""> -->
	            <!-- <span>合同标题</span><input type="hidden" name="doc_title" value="综金管家贷款合同"> -->
	        </div>
	        <div class="zlj_content">
	            <p>合同收货地址<span style="font-size: 14;color: red;"></span></p><!-- (与身份证地址一致) -->
	            <p id="addrDetail"></p>
	            <!-- <span>省份</span>
	            <select class="input_text" id="s_province" name="s_province" placeholder='省份'></select><br/><br/>
	            <span>城市</span>
	            <select class="input_text" id="s_city" name="s_city" placeholder='地级市'></select><br/><br/>
	            <span>区/县</span>
	            <select class="input_text" id="s_county" name="s_county" placeholder='县级市'></select><br/>
				<span style="">详细地址</span> -->
				<input style="display: none;" id="address" type="text" name="address" value="" placeholder="请填写合同收获地址"><br/>
	        </div>
            <%-- <div  class="zlj_content">
                <p>合同文件<a id="contract_dl" href="<%=path %>/download/application.zip" style="float: right;margin: 0 10px;font-size: 14px;">合同模板下载</a></p>
				
                <input id="selece-files" type="file" name="file" accept=".pdf"/><br/>
            </div> --%>
        </div>
        <span id="submitMsg"></span>
        <div class="btn-box">
            <button type="button" onclick="submitCheck()" id="zlj_contract_btn" class="btn-next" style="margin-bottom: 50px;">签署合同</button>
        </div>
    </div>
</form>

</body>
<script type="text/javascript">

	function submitCheck(){
		if($('#s_province').val() == "省份"){
			$("#submitMsg").html("请选择省份");
		} else if($('#s_city').val() == "地级市"){
			$("#submitMsg").html("请选择城市");
		} else if($('#s_county').val() == "县级市"){
			$("#submitMsg").html("请选择县级市/区");
		} else if($('#address').val() == ""){
			$("#submitMsg").html("请填写详细地址");
		} 
		/* else if($('#selece-files').val() == ""){
			$("#submitMsg").html("请选择合同文件(没有合同文件？点击<a href='/download/application.zip' style='font-size: 14px;'>下载</a>)");
		} */ 
		else {
			document.getElementById("upload_form").submit();
		}
	}

    function showIdImg(fileId, imgId) {
        var r= new FileReader();
        f=document.getElementById(fileId).files[0];
        r.readAsDataURL(f);
        r.onload=function  (e) {
            document.getElementById(imgId).src=this.result;
        };
    }
    
    $(function(){
    	//根据tid 去aidgold表查询mid
    	$.ajax({
			type:"post",
			url: '<%=path %>/aidgold/selectByPid?tid=<%=aidgoldId %>',
			dataType:"json",
			success:function(data){
				if(data.jkcheck == 21 || data.jkcheck == 32){ //21：已付手续费 32：客户签署合同失败
					//根据mid获取客户信息
					$.ajax({
						type:"post",
						url: '<%=path %>/memberAuthor/selectByMid?mid='+data.mid,
						dataType:"json",
						success:function(val){
							$('#customer_name').val(val.sname);
							$('#mobile').val(val.mobile);
							$('#id_card').val(val.idno);
						}
					});
					//根据mid查询地址 member_addr表
					$.ajax({
						type:"post",
						url: '<%=path %>/fadada/selectMemberAddr?mid='+data.mid,
						dataType:"json",
						success:function(val){
							if(val != null && val != ""){
								$('#addrDetail').html(val.address);
								$('#address').val(val.address);}
						}
					});
				} else {
					location.href = "<%=path %>/hetong/error.html";
				}
			},
			error:function(){}
		});
		
    })
    
</script>
<script language="javascript" type="text/javascript">  
  
    //_init_area();    

</script>
</html>