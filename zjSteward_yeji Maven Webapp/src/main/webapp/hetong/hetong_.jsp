<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.getSession().setAttribute("url", "/fadada/syncPersonAuto");//作为上传文件servlet里面的请求转发url
// 获取合同编号 单号
String contractId = request.getParameter("contract_id"); 
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
    <style>
		.idImgBorder{
			width: 310px;
			height: 215px;
			border: 1px dashed gray;
			margin: 0 auto;
		}
		#idimg1,#idimg2{
			margin: 5px;
		}

    </style>
</head>

<body>
<header>
    <p>合同提交</p>
</header>
<form id="upload_form" action="<%=path %>/upload" enctype="multipart/form-data" method="post">

<div id="zlj_regist" >
    <div class="zlj_process" >
        <p style="display:inline-block;border:1px solid #ccc;padding:5px 10px;border-radius: 20px;background-color: #fff">
            <span class="done"><img src="<%=path %>/hetong/img/gou.png" alt=""/> 贷款申请</span>
            <img src="<%=path %>/hetong/img/right.png" alt=""/>
            <span class="doing"><img src="<%=path %>/hetong/img/quan.png" alt=""/> 合同提交 </span>
            <img src="<%=path %>/hetong/img/right.png" alt=""/>
            <span class="not"><img src="<%=path %>/hetong/img/quan.png" alt=""/> 贷款发放</span>
        </p>
    </div>
    <div id="zlj_regist_form">
        <div class="zlj_content">
            <p>实名认证</p>
            <span>真实姓名</span><input type="text" name="customer_name" value=""><br/>
            <span>手机号码</span><input type="text" name="mobile" value=""><br/>
            <span>身份证号</span><input type="text" name="id_card" value=""><br/>
			<span class="idDes" style="padding:0;">身份证正面照</span>
			<input id="selece-files" type="file" name="idfront" value="123" onchange="showIdImg('selece-files','idimg1')"/><br/>
			<div class="idImgBorder"><img src="<%=path %>/hetong/img/sfz.png" id='idimg1' style="width:300px;"></div>
			<span class="idDes" style="padding:0;">身份证背面照</span>
			<input id="select-files" type="file" name="idback" onchange="showIdImg('select-files','idimg2')" style="border: 0;"/><br/>
			<div class="idImgBorder"><img src="<%=path %>/hetong/img/sfz.png" id='idimg2' style="width:300px;"></div>
            <!-- <span>注册邮箱</span> --><input type="hidden" name="email" value="">
            <!-- <span>证件类型</span> --><input type="hidden" name="ident_type" value="1">
            <!-- <span>合同编号 单号</span> --><input type="hidden" name="contract_id" value="<%=contractId %>">
            <!-- <span>合同标题</span> --><input type="hidden" name="doc_title" value="综金管家贷款合同">
            <!-- <span>合同类型</span> --><input type="hidden" name="doc_type" value=".pdf">
        </div>
        <div  class="zlj_content">
            <p>合同文件</p>

			<input id="selece-files" type="file" name="file" accept=".pdf" placeholder="fdjakl"/><br/>

            <a id="contract_dl" href="<%=path %>/download/application.zip">(合同模板下载)</a>
        </div>
    </div>
    <div class="btn-box">
        <button type="submit" id="zlj_contract_btn" class="btn-next" style="margin-bottom: 50px;">签署合同</button>
    </div>
</div>
</form>

</body>
<script type="text/javascript">
	function showIdImg(fileId, imgId) {
		var r= new FileReader();
		f=document.getElementById(fileId).files[0];
		r.readAsDataURL(f);
		r.onload=function  (e) {
			document.getElementById(imgId).src=this.result;
		};
	}
</script>
</html>