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
    <title>信用卡提额</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE"/>
    <meta http-equiv="PRAGMA" content="NO-CACHE"/>
    <link rel="stylesheet" href="css/xyk.css">
    <link rel="stylesheet" href="css/xystyle.css">


    <style type="text/css">
        .goback {
            display: block;
            width: 60px;
            position: absolute;

        }

        .goback img {
            width: 20px;
            position: absolute;
            bottom: 1px;
        }

        .goback b {
            margin-left: 20px;
            font-size: 15px;
            /*position: absolute;*/
            bottom: 13px;
            color: white;
            font-weight: normal;
        }
    </style>
</head>
<body>

<div id="panel_main">
    <!-- <div class="navbar">
        <div class="navbar-inner"><a class="goback" href="index.html"><img src="../images/back_white.png"
                                                                           alt=""/><b>返回</b></a>信用卡提额
        </div>
    </div> -->
    <div class="page-content">
        <div class="content">
            <div class="list-block sortable">

                <ul>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/zhongxin.html">中信银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/guangda.html">中国光大银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/gongshang.html">中国工商银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/minsheng.html">中国民生银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/zhongguo.html">中国银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/jiaotong.html">交通银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/xingye.html">兴业银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/nongye.html">农业银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/huaxia.html">华夏银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/pingan.html">平安银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/guangfa.html">广发银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/jianshe.html">建设银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/zhaoshang.html">招商银行提额</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="label"><a href="tg/pufa.html">浦发银行提额</a></div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>

</html>
