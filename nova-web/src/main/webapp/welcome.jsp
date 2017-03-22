<%@page import="java.io.File"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />

<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/jquery-ui.css">
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>我的桌面</title>
</head>
<body>
<div class="pd-20"  style="padding-top:20px;padding-bottom:0px;">
<p class="f-20 text-success">欢迎使用傲奇服饰订单管理系统 <span class="f-14">v1.0</span>！<span class="r mr-30"> 

			<input type="text" name="prdno" id="prdno" placeholder=" 产品查询" style="width:250px" class="input-text">
			<button name="prdrearch" id="prdrearch" class="btn btn-success" type="button" onclick="search()">
			<i class="Hui-iconfont">&#xe665;</i> </button>
			<button name="refresh" id="refresh" class="btn btn-success" type="button">
			<i class="Hui-iconfont">&#xe68f;</i> </button>
			</span></p>
  <p>登录次数：${user.loginCount} </p>
  <p>上次登录IP：${user.lastLoginIP}   上次登录时间：${user.lastLoginDate}</p>
		 </div>
<div class="pd-20" style="padding-top:0px;height:340px">

 
</div>
<footer class="footer">
<p>
Copyright &copy;2015 H-ui.admin v2.3 All Rights Reserved.<br>
    本后台系统由<a href="http://www.h-ui.net/" target="_blank" title="H-ui前端框架">H-ui前端框架</a>提供前端技术支持</p>
</footer>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript">
	function search(){
		var t=window.parent; 
		if(t)
		t.postMessage({id:'product_list',title:'商品查询',url:'getproducts.do?prdName='+ $('#prdno').val()}, '*'); 
	}
	
</script>
</body>
</html>