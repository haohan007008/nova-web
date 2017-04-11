<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link href="css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error {
            color: red;
            border-color:red;
        }

        .valid {
            color: black;
        }
    </style>

<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>后台登录 </title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" id="loginUser" action="" method="post">
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="username" name="username" type="text" placeholder="账户" class="input-text size-L" data-toggle="tooltip" data-placement="bottom">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="userpwd" name="userpwd" type="password" placeholder="密码" class="input-text size-L" data-toggle="tooltip" data-placement="bottom" autocomplete="off">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input id="authCode" name="authCode" class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;" data-toggle="tooltip" data-placement="bottom">
          <img src="authcode.do" id="codeImage"> <a id="kanbuq" href="javascript:;" onclick="chageCode()">看不清，换一张</a> </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态</label>
        </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input id="btn_sub" name="btn_sub" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" data-toggle="tooltip">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright meng.lv by H-ui.admin.v2.3</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="lib\jquery.validation\1.14.0\jquery.validate.min.js"></script>
<script type="text/javascript" src="lib\jquery.validation\1.14.0\messages_zh.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript">
function chageCode(){
	    $('#codeImage').attr('src','authCode.do?abc='+Math.random());//链接后添加Math.random，确保每次产生新的验证码，避免缓存问题。
}
if (window.parent !== window.self) {
    window.parent.location.reload();
}
$().ready(function() {
	jQuery.validator.addMethod("checkCode", function(value, element) {
		var checkcode;
		$.ajax({  
		    url : "getauthcode.do?a="+Math.random(),// 请求的URL  
		    data : {  
		    	authCode : value
		    },// 传递给action的参数{dictTypeId:'PC_STATE'}  
		    async : false, // 改异步为同步  
		    dataType : 'json',  
		    success : function(records) {  
		    	checkcode = records;
		    }  
		});
	        if(checkcode.success == false){
	        	chageCode();
	            return false;
	            //用后台的字符与页面输入的验证码进行比较
	        }else if(checkcode.success == true){
	            return true;
	        }
	 }, "验证码不正确"); 
	
	$("#loginUser").validate({
		debug: true, //调试模式取消submit的默认提交功能   
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应 
		errorClass: "error",
		success: 'valid',
        rules:{
        	username:{ required:true,minlength: 5},
        	userpwd:{required:true,minlength: 6},
            authCode:{required:true,minlength:4,checkCode:true}
        },
        messages:{
        	username:{required:"用户名不能为空",minlength: "用户名不能小于5个字 符"},
            userpwd:{required:"密码不能为空", minlength: "密码不能小于6个字 符" },
            authCode:{required:"验证码不能为空", minlength: "验证码只需4位" }
        },
        unhighlight: function (element, errorClass, validClass) { //验证通过
            $(element).tooltip('destroy').removeClass(errorClass);
        },
        //highlight: function (element, errorClass, validClass) { //未通过验证
        //    // TODO
        //}
        //,
        errorPlacement: function (label, element) {
            $(element).tooltip('destroy'); /*必需*/
            $(element).attr('title', $(label).text()).tooltip('show'); 
        },
        submitHandler: function (form) {
        	$.ajax({  
    		    url : "user/dologin.do?a="+Math.random(),// 请求的URL  
    		    data : {
    		    	username : function (){return $("#username").val()},
    		    	userpwd  : function (){return $("#userpwd").val()},
    		    	authCode : function (){return $("#authCode").val()}
    		    },// 传递给action的参数{dictTypeId:'PC_STATE'}  
    		    async : true, // 改异步为同步  
    		    dataType : 'json',  
    		    success : function(obj) {  
    		    	if(obj.success == false){
    		    		//$("#warning-block").show();
    		    		alert(obj.desc);
    		        }else if(obj.success == true){
    		        	window.location.href="index.do"; 
    		        }
    		    }
    		});
    	        
        }

    });
});
</script>
</body>
</html>