<%@page import="com.howbuy.fp.product.Product"%>
<%@page import="com.howbuy.fp.order.Order"%>
<%@page import="com.howbuy.fp.product.ProductColorItem"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	int s_cnt=0,m_cnt=0,l_cnt =0,xl_cnt=0,xxl_cnt=0,total_num=0,total_money=0;
boolean isPrintPage = false;
String act = request.getParameter("act");
if(act != null && "print".equals(act))
	isPrintPage = true;
%>
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
<title>订单汇总</title>
</head>
<body class="pos-r">

<div style="margin-left:2px;">
<% if(!isPrintPage) {%>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 销售管理 <span class="c-gray en">&gt;</span> 订单汇总查询 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<%} %>
	<div class="pd-20">
	<% if(!isPrintPage) {%>
		<div class="text-c"> 
			<input type="text" name="prdNo" id="prdNo" placeholder="款号" style="width:250px" class="input-text" value="<c:out value="${prdNo}" />">
			<button name="search" id="search" class="btn btn-success" type="button" onclick="query()"><i class="Hui-iconfont">&#xe665;</i> 搜产品</button>
		</div>
		<%} %>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">当前查询销售合同已经到生产确认环节，未进入生产计划中订单数据</span>
		<span class="r">
		<a href="javascript:;" onclick="order()" class="btn btn-danger radius">
		<i class="Hui-iconfont">&#xe672;</i> 生产计划安排</a> 
		</span> 
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="mycart">
				<thead>
					<tr class="text-c">
						<th rowspan="2" width="8%">款号</th>
						<th rowspan="2" width="8%">缩略图</th>
						<th rowspan="2" width="12%">总数量</th>
						<th rowspan="2" width="10%">颜色</th>
						<th colspan="5">订单</th>
						<th rowspan="2">数量</th>
					</tr>
					<tr class="text-c">
						<th>S</th>
						<th>M</th>
						<th>L</th>
						<th>XL</th>
						<th>XXL</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<Product> products = (List<Product>)request.getAttribute("list");
						if(products == null || products.size() <= 0){
					%>
					 <tr class="text-c va-m">
					 		<td class="text-c" colspan="16">没有商品信息?！</td>
					 </tr>
					 <%}else {
						for(int i=0;i<products.size(); i++){ 
							Product product = (Product)products.get(i);
							if(product != null && product.getItems() != null &&  product.getItems().size() >0){
								int itemSize = product.getItems().size();
								for(int j=0; j< itemSize ;j++){
									ProductColorItem pItem = (ProductColorItem)product.getItems().get(j);
									s_cnt += pItem.getNs();
									m_cnt += pItem.getNm();
									l_cnt += pItem.getNl();
									xl_cnt += pItem.getNxl();
									xxl_cnt += pItem.getNxxl();
									total_num += pItem.getPrdNum();
									total_money += pItem.getSubTotal();
					  %>
						<tr class="text-c va-m" id="p_<%=product.getId() %>_<%=pItem.getId() %>" >
							<%
								if(j == 0){
							%>
							<td rowspan="<%=itemSize %>"><a style="text-decoration:none" onClick="" href="javascript:;"><b><%=product.getPrdName() %></b></td>
							<td rowspan="<%=itemSize %>"><a onClick="" href="javascript:;"><img class="product-thumb" src="/nova-web/<%=product.getPrdSmallImg() %>"></a></td>
							<td rowspan="<%=itemSize %>"><%=product.getPrdNum() %>件</td>
							<%} %>
							<td><%=pItem.getColorName() %><input type="hidden" value="<%=product.getId() %>"><input type="hidden" value="<%=pItem.getId() %>"></td>
							<td><%=pItem.getNs() %></td>
							<td><%=pItem.getNm() %></td>
							<td><%=pItem.getNl() %></td>
							<td><%=pItem.getNxl() %></td>
							<td><%=pItem.getNxxl() %></td>
							<td><input name="prdColorId" type="checkbox" value="<%=pItem.getId() %>"><input id="it_<%=pItem.getId() %>" type="hidden" value="<%=pItem.getIts() %>"></td>
						</tr>
					<%	}
							}
						}
					} 
					%>
					
				</tbody>
				<tfoot>
					<tr class="text-c">
						<th colspan=2>合计</th>
						<th><%=total_num %></th>
						<th></th>
						<th><%=s_cnt %></th>
						<th><%=m_cnt %></th>
						<th><%=l_cnt %></th>
						<th><%=xl_cnt %></th>
						<th><%=xxl_cnt %></th>
						<th></th>
					</tfoot>
				</thead>
			</table>
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
function query(){
	var prdNo = $('#prdNo').val();
	location.replace("productorders1.do?prdNo="+encodeURI(prdNo));
}

function order(){
	var str = '';
	
	$("input[name='prdColorId']:checkbox:checked").each(function(){ 
		var it = $(this).val();
		if(str)
			str +=  "," + $('#it_'+it).val();
		else str = $('#it_'+it).val();
	});
	alert(str);
	//alert(parent.$('#mainframe').src);
	
	if (window.opener != undefined) { //forchrome   
        window.opener.selectHotelChrome(str); //关闭前调用父窗口方法  
    }    
    else {    
        window.returnValue = str;
    }  
    window.close();  
}

$(function () {
	$("input[type='checkbox']").click(function(){
		var bc = $(this).parent().parent().attr("bgColor");
		if(!bc || bc != 'yellow')
			$(this).parent().parent().attr("bgColor", "yellow");
		else $(this).parent().parent().attr("bgColor", "");
	});
});
</script>
</body>
</html>