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
<link href="css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>FP-MetaData</title>
</head>
<body>
<table class="table" style="height:673px">
	<tr>
		<td width="200" class="va-t" style="margin:0px;padding:0px;BORDER-RIGHT: #eee 1px solid;">
		<nav class="breadcrumb1" role="navigation"> <i class="Hui-iconfont">&#xe67f;</i> 导航树 
		<div class="btn-group">
		   <button type="button" class="btn btn-primary dropdown-toggle" 
		      data-toggle="dropdown">
		      原始 <span class="caret"></span>
		   </button>
		   <ul class="dropdown-menu" role="menu">
		      <li><a href="#">功能</a></li>
		      <li><a href="#">另一个功能</a></li>
		      <li><a href="#">其他</a></li>
		      <li class="divider"></li>
		      <li><a href="#">分离的链接</a></li>
		   </ul>
		</div>
		</nav>
		<ul id="treeDemo" class="ztree"></ul>
		</td>
		<td class="va-t"><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 
			SCROLLING=AUTO width=100%  height=100% SRC="welcome.jsp"></IFRAME></td>
	</tr>
</table>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<script type="text/javascript">
var setting = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false
	},
	async: {
				enable: true,
				url:"http://localhost:8080/TaskMonitor/node.html?",
				autoParam:["id=pid"],
				otherParam:{"otherParam":"zTreeAsyncTest"}
			},
	callback: {
		beforeClick: function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);
				return false;
			} else {
				demoIframe.attr("src",treeNode.name + ".html");
				return true;
			}
		}
	}
};


		
var code;
		
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}

$(document).ready(function(){
	var t = $("#treeDemo");
	t = $.fn.zTree.init(t, setting);
	demoIframe = $("#testIframe");
	demoIframe.bind("load", loadReady);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.selectNode(zTree.getNodeByParam("id",'11'));
});
</script>
</body>
</html>