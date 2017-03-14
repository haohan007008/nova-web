<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link href="skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>HowBuy-Valuation.admin v1.0</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
<header class="Hui-header cl"> <a class="Hui-logo l" title="H-ui.admin v2.3" href="/">盘中估值  - admin</a> <a class="Hui-logo-m l" href="/" title="H-ui.admin">Monitor</a> <span class="Hui-subtitle l">V1.0</span>
	<c:if test="${user!=null && user.perms!=null && fn:length(user.perms) > 0}">
	<nav class="mainnav cl" id="Hui-nav">
		<ul>
			<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 快捷入口 <i class="Hui-iconfont">&#xe6d5;</i></a>
				<ul class="dropDown-menu radius box-shadow">
				   <c:forEach items="${user.perms}" var="p">
				   <c:if test="${p!=null && p.permType != null &&p.permType eq 'shortcut'}">
					<li><a href="javascript:;" onclick="addTab('shortcut${p.id}','${p.permName}','${p.permUrl}')"><i class="Hui-iconfont">${p.icon}</i> ${p.permName}</a></li>
					</c:if>
				</c:forEach>
				</ul>
			</li>
		</ul>
	</nav>
	</c:if>
	<ul class="Hui-userbar">
		<li>${user.userName}</li>
		
		<li id="Hui-shop"> <a href="#" onclick="addTab('mycart','购物车','productcart.do')" title="我的下单">
			<span class="badge badge-danger" id="mycart"></span>
			<i class="Hui-iconfont" style="font-size:18px">&#xe6b9;</i></a> </li>
		<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger"  id="myorder">${user.myOrderCount} </span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A"><i class="Hui-iconfont" style="font-size:18px">&#xe705;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="#">个人信息</a></li>
				<li><a href="#">切换账户</a></li>
				<li><a href="logout.do">退出</a></li>
			</ul>
		</li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul>
		</li>
	</ul>
	<a href="javascript:;" class="Hui-nav-toggle Hui-iconfont" aria-hidden="false">&#xe667;</a> </header>
	
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
	<c:if test="${user!=null && user.perms!=null && fn:length(user.perms) > 0}">
		<c:forEach items="${user.perms}" var="p">
			<c:if test="${p!=null && p.pId == 0}">
			<dl id="menu-repush">
				<dt><i class="Hui-iconfont">${p.icon}</i> ${p.permName}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
					<c:forEach items="${user.perms}" var="p1">
						<c:if test="${p1!=null && p1.pId == p.id}">
							<li><a _href="${p1.permUrl}" data-title="${p1.permName}" href="javascript:void(0)">${p1.permName}</a></li>
						</c:if>
					</c:forEach>
					</ul>
				</dd>
			</dl>
			</c:if>
		</c:forEach>
	</c:if>
		</div>		
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="status.jsp">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="welcome.do"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<!--
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.core-3.5.js"></script>-->
<script type="text/javascript">
/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}

if (window.parent !== window.self) {
    window.parent.location.reload();
}

</script> 

</html>