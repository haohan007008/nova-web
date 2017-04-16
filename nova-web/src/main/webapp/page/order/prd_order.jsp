<%@page import="com.howbuy.fp.user.UserVO"%>
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
UserVO user =  (UserVO)request.getAttribute("user");
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
			<a style="text-decoration:none" class="ml-5" 
			<% if(isPrintPage) {%> onClick="window.print()"<%} %> <% if(!isPrintPage) {%>href="javascript:window.open(location.href+'&act=print')" target="_blank"<%} %>title="打印当前页面">
		<i class="Hui-iconfont">&#xe652;</i>
		</a>
		</span>
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="mycart">
				<thead>
					<tr class="text-c">
						<th width="7%">款号</th>
						<th width="7%">缩略图</th>
						<th width="10%">总数量</th>
						<% if(user.getDeptId() == 3){ %>
						<th width="12%">汇总金额</th>
						<%}else { %>
							<th width="12%">材质</th>
						<%} %>
						<th width="9%">颜色</th>
						<th width="7%">S</th>
						<th width="7%">M</th>
						<th width="7%">L</th>
						<th width="7%">XL</th>
						<th width="7%">XXL</th>
						<th width="10%">订单数量/生产数量</th>
						<% if(user.getDeptId() == 3){ %>
						<th width="15%">金额</th>
						<%} %>
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
							<td rowspan="<%=itemSize %>"><%=product.getPrdNum() %>/<span style="color:red;"><%=product.getPrdingNumCount() %></span>件</td>
							<% if(user.getDeptId() == 3){ %>
							<td rowspan="<%=itemSize %>"><%=product.getTotal() %>元</td>
							<%}else { %>
							<td rowspan="<%=itemSize %>"><%=product.getMtlQty() %></td>
							<%}} %>
							<td><%=pItem.getColorName() %><input type="hidden" value="<%=product.getId() %>"><input type="hidden" value="<%=pItem.getId() %>"></td>
							<td><%=pItem.getNs() %></td>
							<td><%=pItem.getNm() %></td>
							<td><%=pItem.getNl() %></td>
							<td><%=pItem.getNxl() %></td>
							<td><%=pItem.getNxxl() %></td>
							<td ><%=pItem.getTotalCount() %>/<span style="color:red;"><%=pItem.getPrdingNum() %></span></td>
							<% if(user.getDeptId() == 3){ %>
							<td>
								<%=pItem.getSubTotal() %>
							 </td>
							<%} %>
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
						<% if(user.getDeptId() == 3){ %>
						<th><%=total_money %></th>
						<%}else{ %>
						<th></th>
						<%} %>
						<th></th>
						<th><%=s_cnt %></th>
						<th><%=m_cnt %></th>
						<th><%=l_cnt %></th>
						<th><%=xl_cnt %></th>
						<th><%=xxl_cnt %></th>
						<th><%=total_num %></th>
						<% if(user.getDeptId() == 3){ %>
						<th><%=total_money %></th>
						<%} %>
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
	location.replace("productorders.do?prdNo="+encodeURI(prdNo));
}

</script>
</body>
</html>