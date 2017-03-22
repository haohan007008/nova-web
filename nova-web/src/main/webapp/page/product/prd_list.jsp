<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
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
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>建材列表</title>
</head>
<body class="pos-r">

<div style="margin-left:10px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 产品管理 <span class="c-gray en">&gt;</span> 产品列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		<div class="text-c"> 
			<input type="text" name="prdName" id="prdName" placeholder="款号" style="width:250px" class="input-text" value="<c:out value="${prdName}" />">
			<button name="search" id="search" class="btn btn-success" type="button" onclick="goPage(1)"><i class="Hui-iconfont">&#xe665;</i> 搜产品</button>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20"> <!-- 
		<span class="r"> 
			<a class="btn btn-primary radius" onclick="selectProduct()" 
				href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加到购物车</a>&nbsp;
			<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
			<i class="Hui-iconfont">&#xe672;</i> 购物车</a> </span>  -->
			<span class="l">共有数据：<strong><c:out value="${prdtotal}" /></strong> 条</span>.
			共<strong><c:out value="${pageCount}" /></strong>页,当前第<strong><c:out value="${curpage}" /></strong>页 </div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th width="40"></th>
						<th width="40">ID</th>
						<th width="60">缩略图</th>
						<th width="100">产品名称</th>
						<th>材质</th>
						<th width="100">类别</th>
						<th width="100">价格
							<img src="/nova-web/images/tips_icon.png" title="30件以下价格"/></th>
						<th width="100">折扣价
							<img src="/nova-web/images/tips_icon.png" title="30件及以上价格"/></th>
						<th width="100">上机重量</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${prdlist!=null && fn:length(prdlist) > 0}">
					<c:forEach items="${prdlist}" var="t">   
						<tr class="text-c va-m">
							<td><input name="product" type="checkbox" value="<c:out value="${t.Id}" />"></td>
							<td><c:out value="${t.Id}" /></td>
							<td><a onClick="detail(<c:out value="${t.Id}" />,'<c:out value="${t.prdName}" />')" href="javascript:;"><img class="product-thumb" src="/nova-web/<c:out value="${t.prdSmallImg}" />"></a></td>
							<td class="text-c"><a style="text-decoration:none" onClick="detail(<c:out value="${t.Id}" />,'<c:out value="${t.prdName}" />')" href="javascript:;">
								<b class="text-success"><span class="label label-success radius"><c:out value="${t.catalog_name}" /></span> <c:out value="${t.prdName}" /></b> </a></td>
							<td class="text-l"><c:out value="${t.mtlQty}" /></td>
							<td class="text-c"><c:out value="${t.catalog_name}" /></td>
							<td><span class="price"><c:out value="${t.price}" /></span> </td>
							<td><span class="price"><c:out value="${t.batchPrice}" /></span> </td>
							<td><span class="price"><c:out value="${t.matWgt}" /></span> </td>
						</tr>
					 </c:forEach>
				 </c:if>
				 <c:if test="${prdlist==null || fn:length(prdlist) == 0}">
				 	<tr class="text-c va-m">
				 		<td class="text-c" colspan="10">没有查询到您需要的商品！</td>
				 	</tr>
				 </c:if>
				</tbody>
			</table>
			
		</div>
		<div class="mt-20 text-c">
		<%
		    Integer pageNo = (Integer) request.getAttribute("curpage");
		    Integer pageCount = (Integer) request.getAttribute("pageCount");
		%>
			<div id="DataTables_Table_0_info">
			<% if (pageNo == 1) { %>
				<input class="btn disabled radius" type="button" value="首页">
				<input class="btn disabled radius" type="button" value="上一页">
			<% } else { %>
				<input class="btn radius" type="button" onclick="goPage(1)" value="首页">
				<input class="btn radius" type="button" onclick="goPage(<%=pageNo - 1%>)" value="上一页">
			<%
			    }
			%>
			
			<%
			    if (pageNo == pageCount) {
			%>
			    <input class="btn disabled radius" type="button" value="下一页">
				<input class="btn disabled radius" type="button" value="末页">
			<%
			    } else {
			%>
				<input class="btn radius" type="button" onclick="goPage(<%=pageNo + 1%>)" value="下一页">
				<input class="btn radius" type="button" onclick="goPage(<%=pageCount%>)" value="末页">
			<%
			    }
			%>
				<span>           
				<form style="display:inline;">
                <select name="pageNo" id="pageNo" style="height:30px;">
				<%
				    for (int i = 1; i <= pageCount; i++) {
				%>
				      <option value="<%=i%>" <%=(i == pageNo ? "selected" : "")%>><%=i%></option>
				<%
				    }
				%>
                </select>
                <input type="button" class="btn btn-success radius" value="go" onclick="goPage()" />
            </form></span>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<script type="text/javascript">

function goPage(pageNo){
	var prdName = $('#prdName').val();
	if(pageNo){
		location.replace("getproducts.do?prdName="+encodeURI(prdName)+"&pg="+pageNo);
	}else {
		location.replace("getproducts.do?prdName="+encodeURI(prdName)+"&pg="+$('#pageNo').val());
	}
}

function selectProduct(){
	var prds ="";
	$("input[name='product']:checkbox:checked").each(function(){ 
		prds+=","+$(this).val();
	});
	alert(prds);
}

function detail(prdId,prdNo){
	var t=window.parent; 
	if(t)
	t.postMessage({id:'prd'+prdId,title:prdNo,url:'prddetail.do?pid='+ prdId}, '*'); 
}

/*图片-删除*/
function product_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
</script>
</body>
</html>