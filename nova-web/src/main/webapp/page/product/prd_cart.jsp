<%@page import="com.howbuy.fp.product.Product"%>
<%@page import="com.howbuy.fp.product.ProductColorItem"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<div style="margin-left:2px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 产品管理 <span class="c-gray en">&gt;</span> 产品列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		
		<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="r"> <a class="btn btn-primary radius" onclick="refresh_table()" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加到购物车</a>&nbsp;<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe672;</i> 提交下单</a> </span> <span class="l">共有数据：<strong>54</strong> 条</span> </div>
		
		<div class="row cl mt-20">
					<label class="form-label col-1 text-r"><span class="c-red">*</span>网站名称：</label>
					<div class="formControls col-5">
						<input type="text" id="website-title" placeholder="控制在25个字、50个字节以内" value="" class="input-text">
					</div>
					<label class="form-label col-1 text-r"><span class="c-red">*</span>网站名称：</label>
					<div class="formControls col-5">
						<input type="text" id="website-title" placeholder="控制在25个字、50个字节以内" value="" class="input-text">
					</div>
				</div>
				<div class="row cl  mt-20">
					<label class="form-label col-2"><span class="c-red">*</span>关键词：</label>
					<div class="formControls col-10">
						<input type="text" id="website-Keywords" placeholder="5个左右,8汉字以内,用英文,隔开" value="" class="input-text">
					</div>
				</div>

		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="mycart">
				<thead>
					<tr class="text-c">
						<th width="40"><input name="" type="checkbox" value=""></th>
						<th width="40">ID</th>
						<th width="60">缩略图</th>
						<th width="60">款号</th>
						<th width="60">颜色</th>
						<th width="40">色号</th>
						<th width="40">S</th>
						<th width="40">M</th>
						<th width="40">L</th>
						<th width="40">XL</th>
						<th width="40">XXL</th>
						<th width="40">合计</th>
						<th>折扣价(元/件)</th>
						<th>单价(元/件)</th>
						<th>合计金额</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<Product> products = (List<Product>)request.getAttribute("products");
						if(products == null || products.size() <= 0){
					%>
					 <tr class="text-c va-m">
					 		<td class="text-c" colspan="16">您的购物车还没有商品，请添加！</td>
					 </tr>
					 <%}else {
						for(int i=0;i<products.size(); i++){ 
							Product product = (Product)products.get(i);
							if(product != null && product.getItems() != null &&  product.getItems().size() >0){
								int itemSize = product.getItems().size();
								for(int j=0; j< itemSize ;j++){
									ProductColorItem pItem = (ProductColorItem)product.getItems().get(j);
									
					  %>
						<tr class="text-c va-m" id="p_<%=product.getId() %>_<%=pItem.getId() %>" >
							<%
								if(j == 0){
							%>
							<td rowspan="<%=itemSize %>"><input id="p_<%=product.getId() %>_0" type="checkbox" value=""></td>
							<td rowspan="<%=itemSize %>"><%=product.getId() %></td>
							<td rowspan="<%=itemSize %>"><a onClick="" href="javascript:;"><img width="60"  height="60" class="product-thumb" src="/nova-web/<%=product.getPrdImg() %>"></a></td>
							<td rowspan="<%=itemSize %>"><a style="text-decoration:none" onClick="" href="javascript:;"><%=product.getPrdName() %></td>
							<%} %>
							<td><%=pItem.getColorName() %></td>
							<td><%=pItem.getColorNo() %></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNs() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNm() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNl() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNxl() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNxxl() %>"></td>
							<td ><%=pItem.getTotalCount() %></td>
							<td>
								<span class="price"><%=new java.text.DecimalFormat("#.00").format(product.getBatchPrice()) %></span>
							 </td>
							<td>
								<span class="price"><%=new java.text.DecimalFormat("#.00").format(product.getPrice()) %></span>
							 </td>
							<td><span class="price"><%=pItem.getTotalCost(product.getBatchPrice().doubleValue()) %>/<%=pItem.getTotalCost(product.getPrice().doubleValue()) %></span></td>
							<td class="f-14"><a style="text-decoration:none" class="ml-5" onClick="del_tr('<%=product.getId() %>','<%=pItem.getId() %>')" href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
						</tr>
					<%	}
							}
						}
					} 
					%>
					<tfoot>
					<tr class="text-c">
						<th></th>
						<th></th>
						<th>合计</th>
						<th></th>
						<th></th>
						<th></th>
						<th id="cnt_s"></th>
						<th id="cnt_m"></th>
						<th id="cnt_l"></th>
						<th id="cnt_xl"></th>
						<th id="cnt_xxl"></th>
						<th id="total_cnt"></th>
						<th></th>
						<th></th>
						<th><span class="price c-red" id="total_m">92125.00</span></th>
						<th></th>
					</tr>
				</tfoot>
				</tbody>
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

/*图片-删除*/
function product_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}

function del_tr(prdId,colorId){
	  //$(obj).parent().parent();.remove();
	  var tds = $("#p_"+prdId+"_"+colorId).find('td');
	  
	  if(tds[0].rowSpan && tds[0].rowSpan > 1){
		  var tr = $("#p_"+prdId+"_"+colorId).next('tr');
		  tds[0].rowSpan = tds[0].rowSpan -1;
		  tds[1].rowSpan = tds[1].rowSpan -1;
		  tds[2].rowSpan = tds[2].rowSpan -1;
		  tds[3].rowSpan = tds[3].rowSpan -1;
		  tr.prepend(tds[3]);
		  tr.prepend(tds[2]);
		  tr.prepend(tds[1]);
		  tr.prepend(tds[0]);
	  }else {
		  tds = $("#p_"+prdId+"_0").parent().parent().find('td');
		  tds[0].rowSpan = tds[0].rowSpan -1;
		  tds[1].rowSpan = tds[1].rowSpan -1;
		  tds[2].rowSpan = tds[2].rowSpan -1;
		  tds[3].rowSpan = tds[3].rowSpan -1;
	  }
	  $("#p_"+prdId+"_"+colorId).remove();
	  refresh_table()
}



$(function () {
	$("input[class='input-text text-c']").on('keyup',function(){
		this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
		refresh_table();
	});
	refresh_table();
});

function refresh_table(){
	var trs = $("#mycart > tbody").find('tr');
	var price = 0;
	var bprice = 0;	
	var cnt_s=0,cnt_m=0,cnt_l=0,cnt_xl=0,cnt_xxl=0;
	var totalPay =0,totalDiscountPay =0;
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		var s_cnt = 0;
		var l = 0
		if(tds.length > 12)
			l = 4;
		if(tds[2+l].children[0].value || tds[2+l].children[0].value !=''){
			cnt_s += tds[2+l].children[0].value*1;
			s_cnt += tds[2+l].children[0].value*1;
		}
		if(tds[3+l].children[0].value || tds[3].children[0+l].value !=''){
			cnt_m += tds[3+l].children[0].value*1;
			s_cnt += tds[3+l].children[0].value*1;
		}
		if(tds[4+l].children[0].value || tds[4+l].children[0].value !=''){
			cnt_l +=tds[4+l].children[0].value*1;
			s_cnt += tds[4+l].children[0].value*1;
		}
		if(tds[5+l].children[0].value || tds[5+l].children[0].value !=''){
			cnt_xl +=tds[5+l].children[0].value*1;
			s_cnt += tds[5+l].children[0].value*1;
		}
		if(tds[6+l].children[0].value || tds[6+l].children[0].value !=''){
			cnt_xxl +=tds[6+l].children[0].value*1;
			s_cnt += tds[6+l].children[0].value*1;
		}
		
		bprice = tds[8+l].innerText*1;
		price = tds[9+l].innerText*1;
		
		totalPay += s_cnt*price; 
		totalDiscountPay += s_cnt*bprice;
		
		tds[7+l].innerHTML= '<span class="price">'+s_cnt+'</span>';
		tds[10+l].innerHTML= '<span class="price">'+s_cnt*price+'/'+s_cnt*bprice+'</span>';
		
	}
	
	var total = cnt_s+ cnt_m +cnt_l+cnt_xl+cnt_xxl;
	
	$("#cnt_s").html(cnt_s);
	$("#cnt_m").html(cnt_m);
	$("#cnt_l").html(cnt_l);
	$("#cnt_xl").html(cnt_xl);
	$("#cnt_xxl").html(cnt_xxl);
	$("#total_cnt").html(total);
	//alert(total +":" + totalPay + ":" + totalDiscountPay)
	if(total >= 30){
		$("#total_m").html(totalDiscountPay);
	}else {
		$("#total_m").html(totalPay);
	}
}

</script>
</body>
</html>