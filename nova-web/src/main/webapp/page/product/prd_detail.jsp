<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<title>test</title>
</head>
<body class="pos-r">
<div>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 
	产品管理 <span class="c-gray en">&gt;</span> 
	产品详情<span class="c-gray en">&gt;</span><c:out value="${product.prdName}" />  
	<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" 
		href="javascript:location.replace(location.href);" title="刷新" >
	<i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-10">
		<div class="mt-10" style="border:1px solid #E8E8E8;height:320px;padding:5 5;">
			<div class="text-c " style="float:left; padding:10px 10px;">
				<img width="300" height="300" src="/nova-web/<c:out value="${product.prdImg}" />1">
				<input type="hidden" id="prdId" value="${product.id}">
			</div>
			<div class="text-l" style=" margin-left:320px;paddin:20px 20px; height:300px;">
				<div><h3><c:out value="${product.prdName}" /> <small>(<c:out value="${product.prdNo}" />)</small></h3></div>
				<div style="display:block;height:100px;">产品描述：<c:out value="${product.remark}" /></div>
				<div style="display:block;height:95px; border-top:1px dotted #E8E8E8; margin-right:10px;">
					<div style="float:left;width:45%;height:75px;
						text-align:center;padding-top:10px;">
						<span class="c"> <h4 id="bprice"><fmt:formatNumber value="${product.batchPrice}" pattern="#,#00.00#"/></h4></span>
						<span class="c"> <small>30件及以上价格</small></span>
					</div>
					<div style="float:left;width:45%;margin-left:20px;border-left:1px dotted #E8E8E8;height:75px;
						text-align:center;padding-top:10px;">
						<span class="c"> <h4 id="price"><fmt:formatNumber value="${product.price}" pattern="#,#00.00#"/></h4></span>
						<span class="c"> <small>30件以下价格</small></span>
					</div>
				</div>
				<div style="margin-right:10px;">
					
				</div>
				<div class="cl pd-10 bg-1 bk-gray va-m" style="height:35px;margin-right:10px;"> 
					<span class="r"> 
						<a class="btn btn-primary radius" onclick="addCart()" 
							href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加到购物车</a>&nbsp;
						<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
							<i class="Hui-iconfont">&#xe672;</i> 结算</a> 
					</span> 
				</div>
			</div>
		</div>
		<div class="mt-20 text-l">
			<div class="col-12">
				<table class="table table-border table-bg table-bordered text-c" id="prdItems">
					  <thead>
					    <tr class="text-c">
					    	<th width="10%">颜色</th>
					    	<th width="10%">色号</th>
					    	<th>S</th>
					    	<th>M</th>
					    	<th>L</th>
					    	<th>XL</th>
					    	<th>XXL</th>
					    	<th width="10%">数量</th>
					    	<th width="10%">合计</th>
					    	<th width="50px">操作</th>
					  	</tr>
					  </thead>
					  <tbody>
					  <c:forEach items="${product.items}" var="t"> 
					    <tr class="text-c">
						    <th><c:out value="${t.colorName}" /><input type="hidden" value="${t.id}"></th>
						    <th><c:out value="${t.colorNo}" /></th>
						    <td><input type="text" class="input-text text-c" value="0"></td>
						    <td><input type="text" class="input-text text-c" value="0"></td>
						    <td><input type="text" class="input-text text-c" value="0"></td>
						    <td><input type="text" class="input-text text-c" value="0"></td>
						    <td><input type="text" class="input-text text-c" value="0"></td>
						    <td><span class="price">0</span></td>
						    <td><span class="price">0</span></td>
						    <td><a style="text-decoration:none" class="ml-5" onClick="del_tr(this)" 
						    	href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
					    </tr>
					   </c:forEach>
					  </tbody>
					  <thead>
					    <tr class="text-c">
					    	<th colspan="2">合计</th>
					    	<th id="cnt_s"></th>
					    	<th id="cnt_m"></th>
					    	<th id="cnt_l"></th>
					    	<th id="cnt_xl"></th>
					    	<th id="cnt_xxl"></th>
					    	<th id="total_cnt"></th>
					    	<th colspan="2" id="total_m"></th>
					  	</tr>
					  </thead>
					</table>
			</div>
		</div>
		<div class="mt-20 text-l">
			<div class="col-12" style="margin-top:20px;border-top:1px solid #E8E8E8;margin-bottom:20px;
				border-bottom:1px solid #E8E8E8;height:50px;">
				商品描述级图片详情
			</div>
		</div>
		<footer class="footer mt-20">
			<div class="container-fluid">
				<nav> <a href="#" target="_blank">关于我们</a> <span class="pipe">|</span> <a href="#" target="_blank">联系我们</a> <span class="pipe">|</span> <a href="#" target="_blank">法律声明</a> </nav>
				<p>Copyright &copy;2016 H-ui.net All Rights Reserved. <br>
					<a href="http://www.miitbeian.gov.cn/" target="_blank" rel="nofollow">京ICP备1000000号</a><br>
				</p>
			</div>
		</footer>
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

/*图片-删除*/
function product_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
function del_tr(obj){
	var trs = $("#prdItems > tbody").find('tr');
	if(trs.length > 1){
		$(obj).parent().parent().remove();
		refresh_table();
	}else 
		layer.msg('至少选择一个色号!',{icon:2,time:1000});
}
$(function () {
	$("input[class='input-text text-c']").on('keyup',function(){
		this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
		refresh_table();
	});
	refresh_table();
});

function refresh_table(){
	var trs = $("#prdItems > tbody").find('tr');
	var price = $("#price").html()*1;
	var bprice = $("#bprice").html()*1;
	console.log('price : bprice = '+price + ":" + bprice);
	var cnt_s=0,cnt_m=0,cnt_l=0,cnt_xl=0,cnt_xxl=0;
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		var s_cnt = 0;
		if(tds[2].children[0].value || tds[2].children[0].value !=''){
			cnt_s += tds[2].children[0].value*1;
			s_cnt += tds[2].children[0].value*1;
		}
		if(tds[3].children[0].value || tds[3].children[0].value !=''){
			cnt_m += tds[3].children[0].value*1;
			s_cnt += tds[3].children[0].value*1;
		}
		if(tds[4].children[0].value || tds[4].children[0].value !=''){
			cnt_l +=tds[4].children[0].value*1;
			s_cnt += tds[4].children[0].value*1;
		}
		if(tds[5].children[0].value || tds[5].children[0].value !=''){
			cnt_xl +=tds[5].children[0].value*1;
			s_cnt += tds[5].children[0].value*1;
		}
		if(tds[6].children[0].value || tds[6].children[0].value !=''){
			cnt_xxl +=tds[6].children[0].value*1;
			s_cnt += tds[6].children[0].value*1;
		}
		tds[7].innerHTML= '<span class="price">'+s_cnt+'</span>';
		tds[8].innerHTML= '<span class="price">'+s_cnt*price+'/'+s_cnt*bprice+'</span>';
		
	}
	
	var total = cnt_s+ cnt_m +cnt_l+cnt_xl+cnt_xxl;
	console.log("total:"+total);
	$("#cnt_s").html(cnt_s);
	$("#cnt_m").html(cnt_m);
	$("#cnt_l").html(cnt_l);
	$("#cnt_xl").html(cnt_xl);
	$("#cnt_xxl").html(cnt_xxl);
	$("#total_cnt").html(total);
	if(total >= 30){
		$("#total_m").html(total*bprice);
	}else {
		$("#total_m").html(total*price);
	}
}

function addCart(){
	//alert(JSON.stringify(getDataset()));
	$.ajax({ 
        type:"POST", 
        url:"product/addcart.do?dt="+new Date(), 
        dataType:"json",      
        contentType:"application/json",               
        data:JSON.stringify(getDataset()), 
        success:function(data){ 
        	if(data.success){
        		var t=window.parent; 
        		if(t)
        		t.postMessage({type:'mycart',total:data.total}, '*');
        		layer.msg('添加到购物车成功',{icon:1,time:1000}); 
        	}else 
        		layer.msg('添加到购物车失败！'+data.desc,{icon:2,time:2000}); 
        } 
     }); 
	
}

function getDataset(){
	var trs = $("#prdItems > tbody").find('tr');
	var prdId = $("#prdId").val();
	var data = [];
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		var rd = {};
		rd.prdId = prdId;
		rd.colorName = tds[0].innerText;
		rd.colorNo = tds[1].innerText;
		rd.id = tds[0].children[0].value;
		
		if(tds[2].children[0].value || tds[2].children[0].value !=''){
			rd.ns = tds[2].children[0].value*1;
		}
		if(tds[3].children[0].value || tds[3].children[0].value !=''){
			rd.nm = tds[3].children[0].value*1;
		}
		if(tds[4].children[0].value || tds[4].children[0].value !=''){
			rd.nl = tds[4].children[0].value*1;
		}
		if(tds[5].children[0].value || tds[5].children[0].value !=''){
			rd.nxl = tds[5].children[0].value*1;
		}
		if(tds[6].children[0].value || tds[6].children[0].value !=''){
			rd.nxxl = tds[6].children[0].value*1;
		}
		data[i] = rd;
	}
	alert(JSON.stringify(data)); 
	return {id:prdId,items:data};
}
</script>
</body>
</html>