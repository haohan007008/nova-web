<%@page import="com.howbuy.fp.product.Product"%>
<%@page import="com.howbuy.fp.production.Production"%>
<%@page import="com.howbuy.fp.product.ProductColorItem"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	Production production = (Production)request.getAttribute("production");
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
<title>建材列表</title>
</head>
<body class="pos-r">

<div style="margin-left:2px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 产品管理 <span class="c-gray en">&gt;</span> 产品列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r"> 
		<a href="javascript:;" onclick="detail()" class="btn btn-success radius">
		<i class="Hui-iconfont">&#xe672;</i> 选择产品</a> 
		<a href="javascript:;" onclick="order(1)" class="btn btn-danger radius">
		<i class="Hui-iconfont">&#xe672;</i> 提交加工合同</a> 
		</span> 
		<span class="l">生产总数：<strong id="l_total_cnt">0</strong> 件，合计金额：<strong id="l_total_m" class="c-red">0</strong>元</span></span> </div>
		
		<div class="row cl mt-20">
					<input type="hidden" id="its" value="${its}">
					<input type="hidden" id="id" value="<%=production.getId() %>">
					<label class="form-label col-1 text-r"><span class="c-red">*</span>委托方名称：</label>
					<div class="formControls col-5">
						<input type="text" id="manufacturer" placeholder="控制在25个字、50个字节以内" value="<%=production.getManufacturer() %>" class="input-text radius">
					</div>
					<label class="form-label col-1 text-r"><span class="c-red">*</span>是否含税：</label>
					<div class="formControls col-5">
						<input type="checkbox" id="tax" name="tax">
					</div>
				</div>
		
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="mycart">
				<thead>
					<tr class="text-c">
						<th>款号</th>
						<th>缩略图</th>
						<th width="8%">线纱</th>
						<th width="5%">颜色</th>
						<th>S</th>
						<th>M</th>
						<th>L</th>
						<th>XL</th>
						<th>XXL</th>
						<th>总计</th>
						<th width="8%">单价(含税)</th>
						<th>总金额</th>
						<th width="10%">备注</th>
						<th width="10%">交期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<Product> products =  production.getProducts();
						if(products == null || products.size() <= 0){
					%>
					 <tr class="text-c va-m">
					 		<td class="text-c" colspan="17">您的购物车还没有商品，请添加！</td>
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
							<td rowspan="<%=itemSize %>"><a style="text-decoration:none" onClick="" href="javascript:;"><%=product.getPrdName() %></a><input id="p_<%=product.getId() %>_0" type="hidden" value=""></td>
							<td rowspan="<%=itemSize %>"><a onClick="" href="javascript:;"><img width="60"  height="60" class="product-thumb" src="/nova-web/<%=product.getPrdSmallImg() %>"></a></td>
							<td rowspan="<%=itemSize %>"><%=product.getMtlQty() %></td>
							<%} %>
							<td><%=pItem.getColorName() %><input type="hidden" value="<%=product.getId() %>"><input type="hidden" value="<%=pItem.getId() %>"><input type="hidden" value="<%=pItem.getIts() %>"></td>
							<td><%=pItem.getNs() %></td>
							<td><%=pItem.getNm() %></td>
							<td><%=pItem.getNl() %></td>
							<td><%=pItem.getNxl() %></td>
							<td><%=pItem.getNxxl() %></td>
							<td>
								<%=pItem.getPrdNum() %>
							 </td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getPrice() %>"></td>
							<td>
							 </td>
							<td><input type="text" class="bk input-text text-c" value="<%=pItem.getRemark() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getRemark() %>"></td>
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
						
						<th id="cnt_s"></th>
						<th id="cnt_m"></th>
						<th id="cnt_l"></th>
						<th id="cnt_xl"></th>
						<th id="cnt_xxl"></th>
						<th id="total_cnt"></th>
						<th></th>
						<th id="total_m"></th>
						<th></th>
						<th><span class="price c-red" id="total_m"></span></th>
						<th></th>
					</tr>
				</tfoot>
				</tbody>
			</table>
		</div>
		<div class="row cl mt-20">
					<label class="form-label col-1 text-r"><span class="c-red">*</span>开户银行：</label>
					<div class="formControls col-5">
						<input type="text" id="payBank" placeholder="控制在25个字、50个字节以内" value="<%=production.getPayBank() %>" class="input-text radius">
					</div>
					<label class="form-label col-1 text-r"><span class="c-red">*</span>付款账户：</label>
					<div class="formControls col-5">
						<input type="text" id="payNo" placeholder="控制在25个字、50个字节以内" value="<%=production.getPayNo() %>" class="input-text radius">
					</div>
				</div>
		<div class="row cl mt-20">
					<label class="form-label col-1 text-r"><span class="c-red">*</span>联系人：</label>
					<div class="formControls col-5">
						<input type="text" id="linkStaff" placeholder="控制在25个字、50个字节以内" value="<%=production.getLinkStaff() %>" class="input-text radius">
					</div>
					<label class="form-label col-1 text-r"><span class="c-red">*</span>联系电话：</label>
					<div class="formControls col-5">
						<input type="text" id="telphone" placeholder="控制在25个字、50个字节以内" value="<%=production.getTelphone() %>" class="input-text radius">
					</div>
				</div>
		<div class="row cl mt-20">
					<label class="form-label col-1 text-r"><span class="c-red">*</span>工厂地址：</label>
					<div class="formControls col-11">
						<input type="text" id="manufacturerAddress" placeholder="控制在25个字、50个字节以内" value="<%=production.getManufacturerAddress() %>" class="input-text radius">
					</div>
				</div>
		<div class="row cl  mt-20">
					<div class="formControls col-12">
						<input type="text" id="remark" placeholder="备注：需要特别说明的内容" value="<%=production.getManufacturerAddress() %>" class="textarea radius">
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
Array.prototype.unique3 = function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
	   res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	 return res;
}
/*图片-删除*/
function product_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}

function detail(){
	//var t=window.parent; 
	//if(t)
	//t.layershow('选择产品','productorders1.do?q&act=print',1024,600); 
	selectHotel();
}

function del_tr(prdId,colorId){
	delOnView(prdId,colorId);
}
function delOnView(prdId,colorId){	  
	  //$(obj).parent().parent();.remove();
	  var tds = $("#p_"+prdId+"_"+colorId).find('td');
	  
	  if(tds[0].rowSpan && tds[0].rowSpan > 1){
		  var tr = $("#p_"+prdId+"_"+colorId).next('tr');
		  tds[0].rowSpan = tds[0].rowSpan -1;
		  tds[1].rowSpan = tds[1].rowSpan -1;
		  tds[2].rowSpan = tds[2].rowSpan -1;
		  //tds[3].rowSpan = tds[3].rowSpan -1;
		  //tr.prepend(tds[3]);
		  tr.prepend(tds[2]);
		  tr.prepend(tds[1]);
		  tr.prepend(tds[0]);
	  }else {
		  tds = $("#p_"+prdId+"_0").parent().parent().find('td');
		  tds[0].rowSpan = tds[0].rowSpan -1;
		  tds[1].rowSpan = tds[1].rowSpan -1;
		  tds[2].rowSpan = tds[2].rowSpan -1;
		  //tds[3].rowSpan = tds[3].rowSpan -1;
	  }
	  $("#p_"+prdId+"_"+colorId).remove();
	  //refresh_table();
}

function selectHotel(){  
    url = 'productorders1.do?q&act=print';  
    var hotelIdList = window.showModalDialog(url, "选择商品", "dialogWidth:1020px;dialogHeight:600px;help:no;resizable:no;center:yes;scroll:yes;status:no");  
    if(!has_showModalDialog) return;//chrome 返回 因为showModalDialog是阻塞的 open不一样;    
   	//alert('a:'+hotelIdList);//$("#content").append(hotelIdList);  
}  
function selectHotelChrome(option){//为打开的窗口定义方法，让打开的窗口关闭时通过window.opener赋值回来并执行  
	if($('#its').val())
		$('#its').val($('#its').val()+','+option);
	else $('#its').val(option);
	var it = $('#its').val().split(',').unique3().join(',');
	alert(it);
	location.replace('productionitems.do?its='+it);
}  

$(function () {
	$("input[class='input-text text-c']").on('keyup',function(){
		this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
		refresh_table();
	});
	refresh_table();
});

function order(nextAction){
	if($('#manufacturer').val() =="" || $('#linkStaff').val()== "" || $('#telphone').val()== "")
		layer.msg('委托方名称、联系人和联系方式不能为空！',{icon:2,time:2000}); 
	else{
	var order = getDataSet();
	//alert(JSON.stringify(order));
	if(order){
		$.ajax({ 
	        type:"POST", 
	        url:"production/add.do?dt="+new Date(), 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(order), 
	        success:function(data){ 
	        	if(data.success){
	        		layer.msg('生产计划安排成功！',{icon:1,time:2000}); 
	        		//var t=window.parent; 
	        		
	        		//location.replace(location.href);
	        	}else 
	        		layer.msg('生产计划安排失败！请联系管理员！-'+data.desc,{icon:2,time:2000}); 
	        } 
	     }); 
	}else layer.msg('请至少选择一件商品！',{icon:2,time:2000}); 
	}
}

function getDataSet(){
	var trs = $("#mycart > tbody").find('tr');
	
	var production ={};
	production.manufacturer = $('#manufacturer').val();
	production.tax = $('#tax').val();
	production.payBank = $('#payBank').val();
	production.payNo = $('#payNo').val();
	production.linkStaff = $('#linkStaff').val();
	production.telphone = $('#telphone').val();
	production.manufacturerAddress = $('#manufacturerAddress').val();
	production.remark = $('#remark').val();
	production.its = $('#its').val();
	
	var data = [];
	var cnt = 0;
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		var s_cnt = 0;
		var l = 0
		if(tds.length > 12)
			l = 3;
		var rd = {};
		rd.prdId = tds[0+l].children[0].value;
		rd.colorName = tds[0+l].innerText;
		rd.prdColorId = tds[0+l].children[1].value;
		//rd.prdColorId = tds[0+l].children[1].value;
		rd.its = tds[0+l].children[2].value;
		
		rd.ns = tds[1+l].innerText;
		rd.nm = tds[2+l].innerText;
		rd.nl = tds[3+l].innerText;
		rd.nxl = tds[4+l].innerText;
		rd.nxxl = tds[5+l].innerText;
		
		rd.prdNum = tds[6+l].innerText;
		rd.price = tds[7+l].children[0].value;
		rd.remark = tds[9+l].children[0].value;
		rd.deliveryTime = tds[10+l].children[0].value;
		
		data[data.length] = rd;
	}
	//$('#total_cnt').html(cnt);
	production.items = data;
	alert(JSON.stringify(production));
	return production;
}

function refresh_table(){
	var trs = $("#mycart > tbody").find('tr');
	var data = [];
	var cnt = 0,totalMoney=0;
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		
		if(tds.length <= 2) return;
		var s_cnt = 0;
		var l = 0
		if(tds.length > 12)
			l = 3;
		//var rd = {};
		//rd.prdId = tds[0+l].children[0].value;
		//rd.colorName = tds[0+l].innerText;
		//rd.id = tds[0+l].children[1].value;
		//rd.its = tds[0+l].children[2].value;
		//rd.price = tds[7+l].children[0].value;
		//rd.remark = tds[9+l].children[0].value;
		//rd.diliverytime = tds[10+l].children[0].value;
		
		tds[8+l].innerHTML = tds[6+l].innerHTML * tds[7+l].children[0].value;
		cnt += tds[6+l].innerHTML *1;
		totalMoney += tds[8+l].innerHTML*1;
		
		//if(cnt > 0 )
		//	data[data.length] = rd;
	}
	$('#total_cnt').html(cnt);
	$('#total_m').html(totalMoney);
	$('#l_total_cnt').html(cnt);
	$('#l_total_m').html(totalMoney);
	//alert(JSON.stringify(data));
	//return data;
}

</script>
</body>
</html>