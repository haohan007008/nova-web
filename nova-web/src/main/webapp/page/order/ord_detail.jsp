<%@page import="com.howbuy.fp.product.Product"%>
<%@page import="com.howbuy.fp.order.Order"%>
<%@page import="com.howbuy.fp.product.ProductColorItem"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Iterator"%>
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
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
		<% 
			Order order =  (Order)request.getAttribute("order");
			int curNodeId  = order.getCurNodeId();
		%>
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">
		<a class="btn btn-primary radius" onclick="order(7)"  href="javascript:;">
		<i class="Hui-iconfont">&#xe600;</i> 订单取消</a>&nbsp;
		<a href="javascript:;" onclick="order(3)" class="btn btn-danger radius">
		<i class="Hui-iconfont">&#xe672;</i> 审核通过</a> </span> 
		<span class="l">订货总数：
		<strong id="l_total_cnt">${order.totalPrd}</strong> 件，合计金额：
		<strong id="l_total_m" class="c-red"><input type="text" class="input-text text-c" style="width:90px" id="i_total_m" value="${order.totalPay}"></strong>元</span>，应付定金：
		<strong id="l_total_d" class="c-red"><input type="text" class="input-text text-c" style="width:90px" id="i_total_d" value="${order.prePay}"></strong>元</span> </div>
		
		<div class="row cl mt-20">
					<label class="form-label col-3 text-l"><span class="c-red">*</span>订单编号：${order.orderNo}</label>
					<label class="form-label col-3 text-l"><span class="c-red">*</span>订单提交人：${order.creatorName}</label>
					<label class="form-label col-3 text-l"><span class="c-red">*</span>提交时间：${order.createDate}</label>
		</div>
				<div class="row cl mt-20 ">
					<label class="form-label col-3 text-l>
					<input type="hidden" id="nodeId" value="<%=curNodeId %>">
					<input type="hidden" id="orderId" value="<%=order.getId() %>">
					<span class="c-red">*</span>客户名称：${order.custName}</label>
					<label class="form-label col-3 text-l"><span class="c-red">*</span>当前处理人：${order.curOperatorName}</label>
					<label class="form-label col-3 text-l"><span class="c-red">*</span>当前处理环节：<span class="label label-danger radius">${order.curNodeName}</span></label>
		</div>
		<div class="row cl  mt-20">
					<label class="form-label col-8 text-l"><span class="c-red">*</span>地址：${order.shipAddress}</label>
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
						<th width="100">合计金额</th>
						<th width="60">实际价格</th>
						<th>单价(元/件)</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<Product> products = (List<Product>)order.getPrds();
						if(products == null || products.size() <= 0){
					%>
					 <tr class="text-c va-m">
					 		<td class="text-c" colspan="16">该订单没有商品信息?！</td>
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
							<td rowspan="<%=itemSize %>"><a onClick="" href="javascript:;"><img width="60"  height="60" class="product-thumb" src="/nova-web/<%=product.getPrdSmallImg() %>"></a></td>
							<td rowspan="<%=itemSize %>"><a style="text-decoration:none" onClick="" href="javascript:;"><%=product.getPrdName() %></td>
							<%} %>
							<td><%=pItem.getColorName() %><input type="hidden" value="<%=product.getId() %>"></td>
							<td><%=pItem.getColorNo() %><input type="hidden" value="<%=pItem.getId() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNs() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNm() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNl() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNxl() %>"></td>
							<td><input type="text" class="input-text text-c" value="<%=pItem.getNxxl() %>"></td>
							<td ><%=pItem.getTotalCount() %></td>
							<td>
								 <input type="text" name="subtotal" class="input-text text-c" value="<%=pItem.getSubTotal() %>">
							 </td>
							<td>
								<% 
								if(pItem.getTotalCount() >=30){
									%>
									<input type="text"  name="price" class="input-text text-c" value="<%=new java.text.DecimalFormat("#.00").format(product.getBatchPrice()) %>">
									<%
								}else{%>
								<input type="text"  name="price" class="input-text text-c" value="<%=new java.text.DecimalFormat("#.00").format(product.getPrice()) %>">
								<%} %>
							 </td>
							<td><span class="price"><%=new java.text.DecimalFormat("#.00").format(product.getBatchPrice()) %>/<%=new java.text.DecimalFormat("#.00").format(product.getPrice()) %></span></td>
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
						<th><span class="price c-red" id="total_m"></span></th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</tfoot>
				</tbody>
			</table>
		</div>
		<%
			List<Hashtable<String, Object>> logs = order.getLogs();
			if(logs != null || logs.size() > 0){
				for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
					Hashtable<String, Object> ht = (Hashtable<String, Object>) iterator.next();
		%>
		<div class="row cl  mt-20">
			<ul class="commentList">
			  <li class="item cl comment-flip"> <a href="#"><i class="avatar size-L radius"><img alt="" src="http://static.h-ui.net/h-ui/images/ucnter/avatar-default.jpg"></i></a>
			    <div class="comment-main">
			      <header class="comment-header">
			        <div class="comment-meta"><a class="comment-author" href=""><%=ht.get("userName").toString() %></a>【<%=ht.get("nodeName").toString() %>】于
			          <time title="<%=ht.get("timestamp").toString() %>" datetime="<%=ht.get("timestamp").toString() %>"><%=ht.get("timestamp").toString() %></time>
			        </div>
			      </header>
			      <div class="comment-body">
			        <p> <%=ht.get("remark").toString() %></p>
			      </div>
			    </div>
			  </li>
			</ul>
		</div>	
		<%}} %>
		<div class="row cl  mt-20">
					<div class="formControls col-12">
						<input type="text" id="cust_remark" placeholder="需要特别说明的内容" value="${order.remark}" class="textarea radius">
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

/*图片-删除*/
function product_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
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
	  refresh_table();
}

function clearNoNum(obj)
{
   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
   obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
   
}

$(function () {
	$("input[class='input-text text-c']").on('keyup',function(){
		
		if(this.name && (this.name == 'subtotal' || this.name == 'price')){
			//alert(this.name);
			clearNoNum(this);
			if(this.value != '' && this.value.substring(this.value.length-1,this.value.length) !='.' ){
				 var tr = this.parentElement.nextElementSibling;
				 var tr1 = this.parentElement.previousElementSibling;
				 if(tr1.innerText*1 != 0)
				 	tr.children[0].value = this.value / (tr1.innerText*1);
				 else tr.children[0].value = 0;
				 refresh_table();
			}
		}else if(this.id && (this.id == 'i_total_m' || this.id == 'i_total_d')){
			clearNoNum(this);
		}else {
			this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
			refresh_table();
		}
	});
	//refresh_table();
});

function order(nextAction){
	//alert($("#l_total_cnt").html());
	var order = {
			id: $('#orderId').val(),
			//shipAddress: $('#cust_addr').val(),
			remark: $('#cust_remark').val(),
			totalPay:$('#i_total_m').val(),
			prePay:$('#i_total_d').val(),
			curNode:nextAction,
			totalPrd:$("#l_total_cnt").html(),
			items : getDataSet()
			
	};
	//alert(JSON.stringify(order));
	if(order){
		$.ajax({ 
	        type:"POST", 
	        url:"auditorder.do?dt="+new Date(), 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(order), 
	        success:function(data){ 
	        	//alert(JSON.stringify(data));
	        	if(data.success){
	        		layer.msg('审核完成！',{icon:1,time:2000}); 
	        		//var t=window.parent; 
	        		//if(window.parent)
	        		//	window.parent.postMessage({type:'mycart',total:0}, '*');
	        		location.replace(location.href);
	        	}else 
	        		layer.msg('审核失败！请联系管理员！-'+data.desc,{icon:2,time:2000}); 
	        } 
	     }); 
	}else layer.msg('请至少选择一件商品！',{icon:2,time:2000}); 
	
}

function cancelOrder(){
	var order = {
			id: $('#orderId').val(),
			remark: $('#cust_remark').val()
	};
	if(order){
		$.ajax({ 
	        type:"POST", 
	        url:"cancelOrder.do?dt="+new Date(), 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(order), 
	        success:function(data){ 
	        	if(data.success){
	        		layer.msg('审核完成！',{icon:1,time:2000}); 
	        		location.replace(location.href);
	        	}else 
	        		layer.msg('提交失败！请联系管理员！-'+data.desc,{icon:2,time:2000}); 
	        } 
	     }); 
	}else layer.msg('莫名其妙的错误！',{icon:2,time:2000}); 
}

function getDataSet(){
	var trs = $("#mycart > tbody").find('tr');
	var data = [];
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		var s_cnt = 0;
		var l = 0
		if(tds.length > 12)
			l = 4;
		var rd = {};
		rd.prdId = tds[0+l].children[0].value;
		rd.id = tds[1+l].children[0].value;
		rd.colorName = tds[0+l].innerText;
		rd.colorNo = tds[1+l].innerText;
		var cnt = 0;
		if(tds[2+l].children[0].value || tds[2+l].children[0].value !=''){
			rd.ns= tds[2+l].children[0].value*1;
			cnt += rd.ns;
		}
		if(tds[3+l].children[0].value || tds[3].children[0+l].value !=''){
			rd.nm= tds[3+l].children[0].value*1;
			cnt += rd.nm;
		}
		if(tds[4+l].children[0].value || tds[4+l].children[0].value !=''){
			rd.nl= tds[4+l].children[0].value*1;
			cnt += rd.nl;
		}
		if(tds[5+l].children[0].value || tds[5+l].children[0].value !=''){
			rd.nxl =tds[5+l].children[0].value*1;
			cnt += rd.nxl;
		}
		if(tds[6+l].children[0].value || tds[6+l].children[0].value !=''){
			rd.nxxl =tds[6+l].children[0].value*1;
			cnt += rd.nxxl;
		}
		if(tds[8+l].children[0].value || tds[6+l].children[0].value !=''){
			rd.subTotal =tds[8+l].children[0].value*1;
		}
		if(tds[9+l].children[0].value || tds[6+l].children[0].value !=''){
			rd.price = tds[9+l].children[0].value*1;
			//cnt += rd.nxxl;
		}
		
		if(cnt > 0 )
			data[data.length] = rd;
	}
	return data;
}

function refresh_table(){
	var trs = $("#mycart > tbody").find('tr');
	var price = 0;
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
		
		if(tds[9+l].children[0].value || tds[9+l].children[0].value !=''){
			price = tds[9+l].children[0].value*1;
		}
		
		//bprice = tds[8+l].innerText*1;
		//price = tds[9+l].innerText*1;
		//alert(s_cnt*price);
		tds[8+l].children[0].value = s_cnt*price;
		//$(tds[8+l].children[0]).val(s_cnt*price);
		totalPay += s_cnt*price; 
		//totalDiscountPay += s_cnt*bprice;
		
		tds[7+l].innerHTML= '<span class="price">'+s_cnt+'</span>';
		//tds[10+l].innerHTML= '<span class="price">'+s_cnt*price+'/'+s_cnt*bprice+'</span>';
		
	}
	
	var total = cnt_s+ cnt_m +cnt_l+cnt_xl+cnt_xxl;
	
	$("#cnt_s").html(cnt_s);
	$("#cnt_m").html(cnt_m);
	$("#cnt_l").html(cnt_l);
	$("#cnt_xl").html(cnt_xl);
	$("#cnt_xxl").html(cnt_xxl);
	$("#total_cnt").html(total);
	$("#l_total_cnt").html(total);
	//alert(total +":" + totalPay + ":" + totalDiscountPay)
	$("#total_m").html(totalPay.toFixed(2));
	$("#i_total_m").val(totalPay.toFixed(2));
	$("#i_total_d").val((totalPay*0.4).toFixed(2));
	
}

</script>
</body>
</html>