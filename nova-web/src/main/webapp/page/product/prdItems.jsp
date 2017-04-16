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
	<div class="pd-10">
		<input type="hidden" id="prdId" name="prdId" value="${prdId }" />
		<div class="mt-20 text-l">
			<div class="col-12">
				<table class="table table-border table-bg table-bordered text-c" id="prdItems">
					  <thead>
					    <tr class="text-c">
					    	<th width="20%">颜色</th>
					    	<th width="20%">色号</th>
					    	<th width="40%">备注</th>
					    	<th width="10%">是否上市</th>
					    	<th width="10%">操作</th>
					  	</tr>
					  </thead>
					  <tbody>
					  <c:forEach items="${items}" var="t"> 
					    <tr class="text-c">
						    <td><c:out value="${t.colorName}" /><input type="hidden" value="${t.id}"></td>
						    <td><c:out value="${t.colorNo}" /></td>
						    <td><input type="text" class="input-text text-l" value="${t.remark}"></td>
						    <td><input type="checkbox" <c:if test="${t.status==0}">checked</c:if>></td>
						    <td></td>
					    </tr>
					   </c:forEach>
					    <tr class="text-c">
						    <td><input type="text" class="input-text text-c" value=""><input type="hidden" value=""></td>
						    <td><input type="text" class="input-text text-c" value=""></td>
						    <td><input type="text" class="input-text text-l" value=""></td>
						    <td><input type="checkbox" ></td>
						    <td><a style="text-decoration:none" class="ml-5" onClick="add_tr(this)" 
						    	href="javascript:;" title="添加"><i class="Hui-iconfont">&#xe604;</i></a>
						    	<a style="text-decoration:none" class="ml-5" onClick="del_tr(this)" 
						    	href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
					    </tr>
					  </tbody>
					 
					</table>
			</div>
			<div class="row cl">
			<div class="mt-20 col-12 text-c">
				<button class="btn btn-primary radius" type="button" onclick="updateItems()">
				<i class="Hui-iconfont">&#xe632;</i> 保存</button>
			</div>
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

/*图片-删除*/
function product_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
function del_tr(obj){
	$(obj).parent().parent().remove();
}

function add_tr(obj){
	var row = '<tr class="text-c"><td><input type="text" class="input-text text-c" value="">'
		+'<input type="hidden" value=""></td>'
		+'<td><input type="text" class="input-text text-c" value=""></td>'
		+'<td><input type="text" class="input-text text-l" value=""></td>'
		+'<td><input type="checkbox" checked></td>'
		+'<td><a style="text-decoration:none" class="ml-5" onClick="add_tr(this)" '
		+'	href="javascript:;" title="添加"><i class="Hui-iconfont">&#xe604;</i></a>'
		+'	<a style="text-decoration:none" class="ml-5" onClick="del_tr(this)" '
		+'	href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>'
		+'</tr>'
	
	$(obj).parent().parent().after(row);  
}
$(function () {
	$("input[class='input-text text-c']").on('keyup',function(){
		//this.value = this.value.replace(/[^\d]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
		//refresh_table();
	});
	//refresh_table();
});

function refresh_table(){
	var trs = $("#prdItems > tbody").find('tr');
	var price = $("#price").html()*1;
	var bprice = $("#bprice").html()*1;
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
	//console.log("total:"+total);
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

function updateItems(){
	//alert(JSON.stringify(getDataset()));
	var rd = getDataset();
	//alert(JSON.stringify(rd));
	//alert('rd.items.length'+rd.items.length);
	if(rd){
		$.ajax({ 
	        type:"POST", 
	        url:"product/items.do?dt="+new Date(), 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(rd), 
	        success:function(data){ 
	        	if(data.success){
	        		layer.msg('保存成功！',{icon:1,time:1000}); 
	        	}else 
	        		layer.msg('保存成功！'+data.desc,{icon:2,time:2000}); 
	        } 
	     }); 
	}else layer.msg('请至少选择一件商品！',{icon:2,time:2000}); 
	
}

function getDataset(){
	var trs = $("#prdItems > tbody").find('tr');
	var prdId = $("#prdId").val();
	var data = [];
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].children;
		var rd = {};
		rd.prdId = prdId;
		//rd.colorName = tds[0].innerText;
		//rd.colorNo = tds[1].innerText;
		if(tds[0].children[1]){
			if(!tds[0].children[0].value) continue;
			rd.colorName = tds[0].children[0].value;
			rd.colorNo = tds[1].children[0].value;
			rd.remark = tds[2].children[0].value;
			rd.status = tds[3].children[0].checked?0:1;
		}else {
			rd.id = tds[0].children[0].value;
			rd.remark = tds[2].children[0].value;
			rd.status = tds[3].children[0].checked?0:1;
		}
		
		data[data.length] = rd;
	}
	//alert(JSON.stringify(data));
	return data;
}
</script>
</body>
</html>