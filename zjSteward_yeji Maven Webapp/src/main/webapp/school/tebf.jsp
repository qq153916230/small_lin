<%@ page language="java" import="java.util.*,com.app.util.ReadHTMLFilePath" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>信用卡提额办法</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
    <meta http-equiv="PRAGMA" content="NO-CACHE" /> 

    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/tebf.js" type="text/javascript"></script>

    <link rel="stylesheet" href="css/xyk.css"> 
    <link rel="stylesheet" href="css/xystyle.css">  

    <style type="text/css">
	.goback {
    display: block;
    width:60px;
    position:absolute;

	}
	.goback img{
		width: 20px;
		position:absolute;
		bottom:1px;
	}
	.goback b{
		margin-left: 20px;
		font-size: 15px;
		/*position: absolute;*/
		bottom: 13px;
		color: white;
		font-weight: normal;
	}
    </style>
    <script type="text/javascript"></script>
</head>
<body>

<div id="panel_main" > 
    <!-- <div class="navbar">
      <div class="navbar-inner"><a class="goback" href="http://mobile.rczjgj.com/mobile/school/index.html"><img src="http://mobile.rczjgj.com/mobile/images/back_white.png" alt=""/><b>返回</b></a>信用卡提额办法</div>
    </div>  -->
    <div class="page-content">  
      <div class="content">
        <div class="list-block sortable">
        
        <!-- js加载列表的容器 -->
          <ul>
          </ul>
        </div>  
<br><br><br><br>
                
       
    </div> 
 

    </div>
    </div>
 </body>
</html>
