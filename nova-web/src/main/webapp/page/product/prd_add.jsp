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
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>

<![endif]-->
<link rel="stylesheet" type="text/css" href="css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="css/H-ui.admin.css" />
<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->
    <style>
        .error {
            color: red;
            border-color:red;
        }

        .valid {
            color: black;
        }
    </style>
<style>
.btn-upload{position: relative; display:inline-block;height:36px; *display:inline;overflow:hidden;vertical-align:middle;cursor:pointer}
.upload-url{cursor: pointer}
.input-file{position:absolute; right:0; top:0; cursor: pointer; z-index:1; font-size:30em; *font-size:30px;opacity:0;filter: alpha(opacity=0)}
.btn-upload .input-text{ width:auto}
.form-group .upload-btn{ margin-left:-1px}
</style>
</head>
<body>
<div class="page-container">
	<form action="AddOrUpdateproduct.do" method="post" class="form form-horizontal" id="form-product-add" enctype="multipart/form-data">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>产品款号：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" class="input-text required" value="${product.prdNo }" placeholder="" id="prdNo" name="prdNo" <c:if test="${not empty product.id }">readonly="true"</c:if> >
				<input type="hidden" id="id" name="id" value="${product.id }" />
			</div>
			<label class="form-label col-xs-4 col-sm-1"><span class="c-red">*</span>产品类别：</label>
			<div class="formControls col-xs-4 col-sm-4"> <span class="select-box">
				<select name="catalog" class="select">
					<c:if test="${catalogs!=null && fn:length(catalogs) > 0}">
					<c:forEach items="${catalogs}" var="t">
					<option value="${t.dictKey}" <c:if test="${t.dictval == product.catalog}">selected</c:if>>${t.dictval}</option>
					</c:forEach>
					</c:if>
				</select>
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">产品名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${product.prdName }" placeholder="" id="prdName" name="prdName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">是否上架：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="check-box">
					<input type="checkbox" id="shelf" name="shelf" <c:if test="${product.shelf == 0}">checked</c:if>>
					<label for="checkbox-1">&nbsp;</label>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">缩略图：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<span class="btn-upload form-group">
				  <input class="input-text upload-url radius" type="text" name="uploadfile-1" id="uploadfile-1" readonly> <a href="javascript:void();" class="btn btn-primary radius"><i class="iconfont">&#xe642;</i> 浏览文件</a>
				  <input type="file" multiple name="prdSmallImg1" id="prdSmallImg1" class="input-file">
				</span>
			</div>
			<label class="form-label col-xs-4 col-sm-1">展示图片：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<span class="btn-upload form-group">
				  <input class="input-text upload-url radius" type="text" name="uploadfile-1" id="uploadfile-1" readonly> <a href="javascript:void();" class="btn btn-primary radius"><i class="iconfont">&#xe642;</i> 浏览文件</a>
				  <input type="file" multiple name="prdImg1" id="prdImg1" class="input-file">
				</span>
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"></label>
			<div class="formControls col-xs-8 col-sm-4" >
				<span class="btn-upload form-group"  style="height:65px;">
				 <img id="pic1" width="60px" height="60px" src="${product.prdSmallImg }" />
				</span>
			</div>
			<label class="form-label col-xs-4 col-sm-1"></label>
			<div class="formControls col-xs-8 col-sm-4">
				<span class="btn-upload form-group"  style="height:65px;">
				  <img id="pic2" width="60px" height="60px" src="${product.prdImg }" />
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>市场价格：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" name="price" id="price" placeholder="" value="${product.price }" class="input-text" style="width:60%">
				元</div>
			<label class="form-label col-xs-4 col-sm-1"><span class="c-red">*</span>折扣价格：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" name="batchPrice" id="batchPrice" placeholder="" value="${product.batchPrice }" class="input-text" style="width:60%">
				元</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">产品材质：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="mtlQty" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符" datatype="*10-100" dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)">${product.mtlQty }
				</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">色彩比例：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" name="colorRatio" id="colorRatio" placeholder="" value="${product.colorRatio }" class="input-text" style="width:90%">
				</div>
			<label class="form-label col-xs-4 col-sm-1">上机重量：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" name="matWgt" id="matWgt" placeholder="" value="${product.matWgt }" class="input-text" style="width:60%">
				g</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">来样时间：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" value="${product.sampleTime }" onFocus="WdatePicker({ dateFmt:'yyyy-MM-dd',sampleTime:'#F{$dp.$D(\'sampleTime\')||\'%y-%M-%d\'}' })" id="sampleTime" name="sampleTime" class="input-text Wdate" style="width:180px;">
			</div>
			<label class="form-label col-xs-4 col-sm-1">样品重量：</label>
			<div class="formControls col-xs-8 col-sm-4">
				<input type="text" name="sampleWgt" id="sampleWgt" placeholder="" value="${product.sampleWgt }" class="input-text" style="width:60%">
				g</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">产品摘要：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="remark" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符" datatype="*10-100" dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)">${product.remark }</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
			</div>
		</div>
	
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存并提交</button>
				<button class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script> 
<script type="text/javascript" src="js/H-ui.admin.js"></script> 
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
<script type="text/javascript">
//建立一個可存取到該file的url
function getObjectURL(file) {
	var url = null ;
	if (window.createObjectURL!=undefined) { // basic
	url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
	url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
	url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
}


$().ready(function() {
	
	jQuery.validator.addMethod("checkPic", function(value, element) {
		
		if(!value)
			return true;
	    var filepath=value;
	    //获得上传文件名
	    var fileArr=filepath.split("\\");
	    var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");
	    var filetype=fileTArr[fileTArr.length-1].toUpperCase();
	    //切割出后缀文件名
	    if(filetype != "PNG" && filetype != "GIF" && filetype != "JPG" && filetype != "JPEG"){
	        return false;
	    }else{
	        return true;
	    }
	}, "上传图片格式不适合");
	
	jQuery.validator.addMethod("checkPicSize", function(value,element) {
		if(!element.files[0])
			return true;
	    var fileSize=element.files[0].size;
	    var maxSize = 1*1024*1024;
	    if(fileSize > maxSize){
	        return false;
	    }else{
	        return true;
	    }
	}, "请上传大小在1M以下的图片");
	
	
	$("#prdSmallImg1").on("change",function(){
		var objUrl = getObjectURL(this.files[0]) ; //获取图片的路径，该路径不是图片在本地的路径
		if (objUrl) {
		$("#pic1").attr("src", objUrl) ; //将图片路径存入src中，显示出图片
		}
	});
	$("#prdImg1").on("change",function(){
		var objUrl = getObjectURL(this.files[0]) ; //获取图片的路径，该路径不是图片在本地的路径
		if (objUrl) {
		$("#pic2").attr("src", objUrl) ; //将图片路径存入src中，显示出图片
		}
	});
	
	$("#form-product-add").validate({
		debug: true, //调试模式取消submit的默认提交功能   
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应 
		errorClass: "error",
		onkeyup   :true,
		success: 'valid',
        rules:{
        	prdNo:{required:true,minlength: 5},
        	price:{required:true,number:true},
        	batchPrice:{required:true,number:true},
        	prdSmallImg1:{checkPicSize:true,checkPic:true},
        	prdImg1:{checkPicSize:true,checkPic:true}
        },
        messages:{
        	prdNo:{required:"款号不能为空",minlength: "款号不能小于5个字 符"},
        	price:{required:"价格不能为空", number: "价格必须为数字" },
        	batchPrice:{required:"折扣价不能为空", number: "折扣价格必须为数字" }
        },
        submitHandler:function(form) {
        	//alert($("#id").val());
        	if($("#id").val() == '' )
        		$("#id").val(-1);
        	form.submit();
		}
    });
	var str = '${status}';
	
    if(str){ 
    	var status = JSON.parse(str);
    	layer.confirm('操作成功！', {
    		  btn: ['继续添加','完成添加'] //按钮
    	},function(index){
    		if(index == 1){
    			location.href = 'product.do';
    			//parent.removeIframe();
    		}else{
    			parent.removeIframe();
    		}
    	},function(index){
    		parent.removeIframe();
    	});
    }	
});
</script>
</body>
</html>