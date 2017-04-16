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
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>test</title>
</head>
<body><div class="pd-20">
  <form action="" method="post" class="form form-horizontal" id="form-member-add">
    <div class="row cl">
      <label class="form-label col-3">姓名：</label>
      <div class="formControls col-5">
      <input type="hidden" value="${user.userId }" id="userId" name="userId">
        <input type="text" class="input-text" value="${user.userName }" placeholder="" id="userName" name="userName" readonly="true">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">登录名：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${user.loginName }" placeholder="" id="loginName" name="loginName" readonly="true">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">旧密码：</label>
      <div class="formControls col-5">
        <input type="password" class="input-text" value="" placeholder="" id="oldPwd" name="oldPwd">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">新密码：</label>
      <div class="formControls col-5">
        <input type="password" class="input-text" value="" placeholder="" id="userPwd" name="userPwd">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">确认密码</label>
      <div class="formControls col-5">
        <input type="password" class="input-text" value="" placeholder="" id="retryPwd" name="retryPwd">
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-3">联系电话：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${user.telphone1 }" placeholder="" id="telphone1" name="telphone1">
      </div>
      <div class="col-4"> </div>
    </div>
     <div class="row cl">
      <label class="form-label col-3">备用电话：</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${user.telphone2 }" placeholder="" id="telphone2" name="telphone2">
      </div>
      <div class="col-4"> </div>
    </div>
    
    <div class="row cl">
      <div class="col-9 col-offset-3">
        <input class="btn btn-primary radius" type="button" onclick="updateUser()" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
      </div>
    </div>
  </form>
</div>
</div>
<script type="text/javascript" src="../lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="../lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="../lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="../lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="../lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="../lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="../js/H-ui.js"></script> 
<script type="text/javascript" src="../js/H-ui.admin.js"></script> 
<script type="text/javascript">

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

function updateUser(){
	//alert(JSON.stringify(getDataset()));
	var user = getDataset();
	alert(JSON.stringify(user));
	//alert('rd.items.length'+rd.items.length);
	if(user){
		$.ajax({ 
	        type:"POST", 
	        url:"edit.do?dt="+new Date(), 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(user), 
	        success:function(data){ 
	        	if(data.success){
	        		layer.msg('修改成功！',{icon:1,time:2000}); 
	        	}else 
	        		layer.msg('修改异常！'+data.desc,{icon:2,time:2000}); 
	        } 
	     }); 
	}else layer.msg('请至少选择一件商品！',{icon:2,time:2000}); 
	
}

function getDataset(){
	var userInfo = {
			userId : $("#userId").val(),
			oldPwd : $("#oldPwd").val(),
			userPwd : $("#userPwd").val(),
			retryPwd : $("#retryPwd").val(),
			telphone1 : $("#telphone1").val(),
			telphone2 : $("#telphone2").val()
	};
	
	if(userInfo.oldPwd.length > 0){
		if(userInfo.userPwd.replace(/(^\s*)|(\s*$)/g, "").length < 6 || userInfo.retryPwd != userInfo.userPwd){
			layer.msg('两次输入密码不一致,同时长度需要大于6位',{icon:2,time:2000}); 
			userInfo = null;
		}
	}
	return userInfo;
}
</script>
</body>
</html>