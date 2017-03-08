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
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="product_list">
				<thead>
					<tr class="text-c">
						<th width="40"><input name="" type="checkbox" value=""></th>
						<th width="40">ID</th>
						<th width="60">缩略图</th>
						<th width="60">款号</th>
						<th width="60">颜色</th>
						<th width="40">色号</th>
						<th width="20">S</th>
						<th width="20">M</th>
						<th width="20">L</th>
						<th width="20">XL</th>
						<th width="20">XXL</th>
						<th width="60">合计</th>
						<th width="100">单价(元/件)</th>
						<th width="100">折扣价(元/件)</th>
						<th width="100">合计金额</th>
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
						<tr class="text-c va-m">
							<%
								if(j == 0){
							%>
							<td  width="20" rowspan="<%=itemSize %>"><input name="" type="checkbox" value=""></td>
							<td rowspan="<%=itemSize %>"><%=product.getId() %></td>
							<td rowspan="<%=itemSize %>"><a onClick="" href="javascript:;"><img width="60"  height="60" class="product-thumb" src="/nova-web/<%=product.getPrdImg() %>"></a></td>
							<td rowspan="<%=itemSize %>"><a style="text-decoration:none" onClick="" href="javascript:;"><%=product.getPrdName() %></td>
							<%} %>
							<td  width="60"><%=pItem.getColorName() %></td>
							<td  width="40"><%=pItem.getColorNo() %></td>
							<td  width="50"><input type="text" class="input-text text-c" value="<%=pItem.getNs() %>"></td>
							<td  width="50"><input type="text" class="input-text text-c" value="<%=pItem.getNm() %>"></td>
							<td  width="50"><input type="text" class="input-text text-c" value="<%=pItem.getNl() %>"></td>
							<td  width="50"><input type="text" class="input-text text-c" value="<%=pItem.getNxl() %>"></td>
							<td  width="50"><input type="text" class="input-text text-c" value="<%=pItem.getNxxl() %>"></td>
							<td  width="40"><%=pItem.getTotalCount() %></td>
							<td width="60"><input type="text" class="input-text text-c" value="<%=new java.text.DecimalFormat("#.00").format(product.getBatchPrice()) %>">
							 </td>
							<td width="60"><input type="text" class="input-text text-c" value="<%=new java.text.DecimalFormat("#.00").format(product.getPrice()) %>">
							 </td>
							<td width="100"><span class="price"><%=pItem.getTotalCost(product.getBatchPrice().doubleValue()) %>/<%=pItem.getTotalCost(product.getPrice().doubleValue()) %></span></td>
							<td class="f-14"><a style="text-decoration:none" class="ml-5" onClick="del_tr(this)" href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
						</tr>
					<%	}
							}
						}
					} 
					%>
					<tfoot>
					<tr class="text-c">
						<th width="40"></th>
						<th width="40"></th>
						<th width="60">合计</th>
						<th width="60"></th>
						<th width="60"></th>
						<th width="40"></th>
						<th width="20">5</th>
						<th width="20">5</th>
						<th width="20">5</th>
						<th width="20">5</th>
						<th width="20">5</th>
						<th width="60">25</th>
						<th width="100"></th>
						<th width="100"></th>
						<th width="100"><span class="price c-red">92125.00</span></th>
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

function del_tr(obj){
	  //$(obj).parent().parent();.remove();
	  var tds = $($(obj).parent().parent()).find('td');
	  //$("table tr").eq($(obj).parent().parent()).remove();
	  //
	  var tds1 = ($(obj).parent().parent().prev('tr').prev('tr')).find('td');

	  if(tds[0].rowSpan && tds[0].rowSpan > 1 ){
	  	alert(tds[0].rowSpan);
	  }else {
	    tds1[0].rowSpan = tds1[0].rowSpan -1;
	    tds1[1].rowSpan = tds1[1].rowSpan -1;
	    tds1[2].rowSpan = tds1[2].rowSpan -1;
	    tds1[3].rowSpan = tds1[3].rowSpan -1;
	  	$(obj).parent().parent().remove();
	  }
}

var products = [{
	id:'0001',
	imgurl:'aa.png',
	prdno:'17D005',
	items:[{
		color:'玛莎紫1',
		colorno:'30',
		s:1,m:1,l:1,xl:1,xxl:1,
		price:145.00,
		bprice:175.00,
		total:1000
	},{color:'浅花灰色',
		colorno:'81',
		s:1,m:1,l:1,xl:1,xxl:1,
		price:145.00,
		bprice:175.00,
		total:1000},{
		color:'焦糖色',
		colorno:'85',
		s:1,m:1,l:1,xl:1,xxl:1,
		price:145.00,
		bprice:175.00,
		total:1000}]
},{
	id:'0002',
	imgurl:'aa.png',
	prdno:'17D007',
	items:[{
		color:'黑色+酒红',
		colorno:'30',
		s:1,m:1,l:1,xl:1,xxl:1,
		price:145.00,
		bprice:175.00,
		total:1000
	},{color:'黑色+焦糖',
		colorno:'81',
		s:1,m:1,l:1,xl:1,xxl:1,
		price:145.00,
		bprice:175.00,
		total:1000}]
}];


function refresh_table(){
	$('#product_list > tbody').remove();
	var tbody = document.createElement("tbody"); 
	var bdStr = '';
	for (var i = 0; i < products.length ; i++) {
		
		for (var j = 0; j < products[i].items.length ; j++) {
			if(j == 0 ){
				bdStr +=  '<tr class="text-c va-m"> '+
						'<td rowspan="'+products[i].items.length+'"><input name="" type="checkbox" value=""></td>'+
						'<td rowspan="'+products[i].items.length+'">'+products[i].id+'</td>'+
						'<td rowspan="'+products[i].items.length+'"><a onClick="" '+
						'href="javascript:;"><img width="60" class="product-thumb" src="pic/product/Thumb/6204.jpg"></a></td>'+
						'<td rowspan="'+products[i].items.length+'"><a style="text-decoration:none" onClick="" href="javascript:;">'+products[i].prdno+'</td>';
			}else{
				bdStr +=  '<tr class="text-c va-m">';
			}
			
			bdStr  += '<td  width="60">'+products[i].items[j].color+'</td>'+
						'<td  width="40">'+products[i].items[j].colorno+'</td>'+
						'<td  width="20"><input type="text" class="input-text text-c" value="'+products[i].items[j].s+'"></td>'+
						'<td  width="20"><input type="text" class="input-text text-c" value="'+products[i].items[j].m+'"></td>'+
						'<td  width="20"><input type="text" class="input-text text-c" value="'+products[i].items[j].l+'"></td>'+
						'<td  width="20"><input type="text" class="input-text text-c" value="'+products[i].items[j].xl+'"></td>'+
						'<td  width="20"><input type="text" class="input-text text-c" value="'+products[i].items[j].xxl+'"></td>'+
						'<td  width="60">'+(products[i].items[j].s + products[i].items[j].m+ products[i].items[j].l + products[i].items[j].xl + 
							products[i].items[j].xxl)+'</td>'+
						'<td width="100"><span class="price">'+products[i].items[j].price+'</span> </td>'+
						'<td width="100"><span class="price">'+products[i].items[j].bprice+'</span> </td>'+
						'<td width="100"><span class="price">'+products[i].items[j].total+'</span></td>'+
						'<td class="f-14"><a style="text-decoration:none" class="ml-5" onClick="del_tr(this)" href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>'+
					'</tr>';

		}
	};
	tbody.innerHTML = bdStr;
	//alert(bdStr);
	bdStr = '';
	$('#product_list').append(tbody);
}



</script>
</body>
</html>