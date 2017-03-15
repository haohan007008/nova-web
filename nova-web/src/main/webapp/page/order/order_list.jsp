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
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 销售管理 <span class="c-gray en">&gt;</span> 订单列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		<div class="text-c"> 
			<input type="text" name="" id="" placeholder="订单状态" style="width:250px" class="input-text">
			<input type="text" name="custName" id="custName" placeholder="客户名称" style="width:250px" class="input-text" value="<c:out value="${custName}" />">
			<button name="search" id="search" class="btn btn-success" type="button" onclick="goPage(1)"><i class="Hui-iconfont">&#xe665;</i> 搜订单</button>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l"> 
		<%
		 String orderType = (String)request.getAttribute("orderType");
		 if(orderType != null && !"".equals(orderType)){
		 	int ot = Integer.parseInt(orderType);
		%>
				<div class="btn-group">
				  <span class="btn <% if(ot ==1) {%>btn-primary<%}else{%>btn-default<%} %> radius" id="btn1" onclick="myorders(1)">待我处理的</span>
				  <span class="btn <% if(ot ==3) {%>btn-primary<%}else{%>btn-default<%} %> radius" id="btn3" onclick="myorders(3)">我创建的</span>
				  <span class="btn <% if(ot ==2) {%>btn-primary<%}else{%>btn-default<%} %> radius" id="btn2" onclick="myorders(2)">我经手的</span>
				</div>
				<input type="hidden" id="orderType" value="${orderType}">
				<%} %>
			</span> 
			<span class="r">共有数据：<strong><c:out value="${ordtotal}" /></strong> 条.
			共<strong><c:out value="${pageCount}" /></strong>页,当前第<strong><c:out value="${curpage}" /></strong>页 </span></div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th width="40"></th>
						<th width="60">订单编号</th>
						<th width="150">客户名称</th>
						<th width="80">下单数据</th>
						<th width="80">订单总额</th>
						<th width="80">当前状态</th>
						<th width="80">下单人</th>
						<th width="120">下单时间</th>
						<th width="100">当前操作人</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${ordlist!=null && fn:length(ordlist) > 0}">
					<c:forEach items="${ordlist}" var="t">  
				<tr class="text-c va-m">
							<td><input name="product" type="checkbox" value=""></td>
							<td>${t.orderNo} </td>
							<td><a onClick="detail(${t.id},'${t.orderNo}')" href="javascript:;">${t.custName}</a></td>
							<td class="text-c">${t.totalPrd}</td>
							<td class="text-c">${t.totalPay}</td>
							<td class="text-c"><span class="label label-danger radius">${t.nodeName}</span></td>
							<td class="text-c">${t.creator}</td>
							<td class="text-c">${t.timestamp}</td>
							<td class="text-c">${t.curOper}</td>
						</tr>
							 </c:forEach>
				 </c:if>
				 <c:if test="${ordlist==null || fn:length(ordlist) == 0}">
				 	<tr class="text-c va-m">
				 		<td class="text-c" colspan="9">没有查询到订单！</td>
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
function myorders(p){
	$('#orderType').val(p);
	$('#btn1').addClass("btn-default");
	$('#btn2').addClass("btn-default");
	$('#btn3').addClass("btn-default");
	$('#btn'+p).removeClass("btn-default");
	$('#btn'+p).addClass("btn-primary");
	goPage(1);
}

function goPage(pageNo){
	var custName = $('#custName').val();
	var orderType = $('#orderType').val();
	if(pageNo){
		location.replace("orders.do?custName="+encodeURI(custName)+"&pg="+pageNo+"&m="+orderType);
	}else {
		location.replace("orders.do?custName="+encodeURI(custName)+"&pg="+$('#pageNo').val()+"&m="+orderType);
	}
}

function selectProduct(){
	var prds ="";
	$("input[name='product']:checkbox:checked").each(function(){ 
		prds+=","+$(this).val();
	});
	alert(prds);
}

function detail(ordId,custName){
	var t=window.parent; 
	if(t)
	t.postMessage({id:'ord'+ordId,title:custName,url:'order/getorder.do?rid='+ ordId}, '*'); 
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