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
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>订单统计</title>
</head>
<body class="pos-r">

<div style="margin-left:10px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 统计报表 <span class="c-gray en">&gt;</span> 订单统计 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	
	<div class="pd-20">
	<!-- 
		<div class="text-c"> 
			<input type="text" name="" id="" placeholder="订单状态" style="width:250px" class="input-text">
			<input type="text" name="custName" id="custName" placeholder="客户名称" style="width:250px" class="input-text" value="<c:out value="${custName}" />">
			<button name="search" id="search" class="btn btn-success" type="button" onclick="goPage(1)"><i class="Hui-iconfont">&#xe665;</i> 搜订单</button>
		</div>
		 -->
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
			
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th width="20"></th>
						<th width="50">日期</th>
						<th width="150" colspan="3">下单中</th>
						<th width="150" colspan="3">经理审核</th>
						<th width="150" colspan="3">业务审核</th>
						<th width="150" colspan="3">财务审核</th>
						<th width="150" colspan="3">生产确认</th>
						<th width="150" colspan="3">归档</th>
						<th width="150" colspan="3">订单取消</th>
					</tr>
				</thead>
				<tbody>
				 <c:if test="${orders!=null && fn:length(orders) > 0}">
					<c:forEach items="${orders}" var="t" varStatus="h"> 
					<c:if test="${fn:length(orders)>h.count}"> 
					<tr class="text-c va-m">
							<td><input name="product" type="checkbox" value=""></td>
							<td class="text-r">${t.dt}</td>
							<td class="text-r">${t.s1_cnt}</td>
							<td class="text-r">${t.t1_cnt}</td>
							<td class="text-r">${t.p1_cnt}</td>
							<td class="text-r">${t.s2_cnt}</td>
							<td class="text-r">${t.t2_cnt}</td>
							<td class="text-r">${t.p2_cnt}</td>
							<td class="text-r">${t.s3_cnt}</td>
							<td class="text-r">${t.t3_cnt}</td>
							<td class="text-r">${t.p3_cnt}</td>
							<td class="text-r">${t.s4_cnt}</td>
							<td class="text-r">${t.t4_cnt}</td>
							<td class="text-r">${t.p4_cnt}</td>
							<td class="text-r">${t.s5_cnt}</td>
							<td class="text-r">${t.t5_cnt}</td>
							<td class="text-r">${t.p5_cnt}</td>
							<td class="text-r">${t.s6_cnt}</td>
							<td class="text-r">${t.t6_cnt}</td>
							<td class="text-r">${t.p6_cnt}</td>
							<td class="text-r">${t.s7_cnt}</td>
							<td class="text-r">${t.t7_cnt}</td>
							<td class="text-r">${t.p7_cnt}</td>
				</tr>
				</c:if>
				<c:if test="${fn:length(orders)==h.count}"> 
					<tr class="text-c va-m">
							<td><input name="product" type="checkbox" value=""></td>
							<td class="text-r"><strong>${t.dt}</strong></td>
							<td class="text-r"><strong>${t.s1_cnt}</strong></td>
							<td class="text-r"><strong>${t.t1_cnt}</strong></td>
							<td class="text-r"><strong>${t.p1_cnt}</strong></td>
							<td class="text-r"><strong>${t.s2_cnt}</strong></td>
							<td class="text-r"><strong>${t.t2_cnt}</strong></td>
							<td class="text-r"><strong>${t.p2_cnt}</strong></td>
							<td class="text-r"><strong>${t.s3_cnt}</strong></td>
							<td class="text-r"><strong>${t.t3_cnt}</strong></td>
							<td class="text-r"><strong>${t.p3_cnt}</strong></td>
							<td class="text-r"><strong>${t.s4_cnt}</strong></td>
							<td class="text-r"><strong>${t.t4_cnt}</strong></td>
							<td class="text-r"><strong>${t.p4_cnt}</strong></td>
							<td class="text-r"><strong>${t.s5_cnt}</strong></td>
							<td class="text-r"><strong>${t.t5_cnt}</strong></td>
							<td class="text-r"><strong>${t.p5_cnt}</strong></td>
							<td class="text-r"><strong>${t.s6_cnt}</strong></td>
							<td class="text-r"><strong>${t.t6_cnt}</strong></td>
							<td class="text-r"><strong>${t.p6_cnt}</strong></td>
							<td class="text-r"><strong>${t.s7_cnt}</strong></td>
							<td class="text-r"><strong>${t.t7_cnt}</strong></td>
							<td class="text-r"><strong>${t.p7_cnt}</strong></td>
				</tr>
				</c:if>
				</c:forEach>
				</c:if>
				</tbody>
			</table>
			
		</div>
		
		</div>
	</div>
</div>
<script type="text/javascript" src="../lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="../lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="../lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="../lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="../js/H-ui.js"></script> 
<script type="text/javascript" src="../js/H-ui.admin.js"></script> 
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