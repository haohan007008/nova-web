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

<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />

<title>数据查询-新老系数对比</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 数据查询 <span class="c-gray en">&gt;</span> 新老系数对比<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-10">
    <div class="text-c"> 
        <input type="text" name="jjdm" id="jjdm" placeholder=" 基金代码" style="width:250px" class="input-text" value="001801">
        <div id="searchresult" style="display: none;">
        <button name="sub" id="sub" class="btn btn-success" type="button"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="mt-20">
        <div id="alpha" style="width: 100%;height:200px;"></div>
        <div id="belta1" style="width: 100%;height:200px;"></div>
        <div id="belta2" style="width: 100%;height:200px;"></div>
        <div id="belta3" style="width: 100%;height:200px;"></div>
        <div id="belta4" style="width: 100%;height:200px;"></div>
    </div>
</div>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<script type="text/javascript" src="../lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/H-ui.js"></script> 
<script type="text/javascript" src="../js/H-ui.admin.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/echarts/3.4.0/echarts.min.js"></script>

<script type="text/javascript">
//设置查询结果div坐标  
	function ChangeCoords() {   
	// var left = $("#txt_search")[0].offsetLeft; 	//获取距离最左端的距离，像素，整型   
	// var top = $("#txt_search")[0].offsetTop + 26; 	//获取距离最顶端的距离，像素，整型（20为搜索输入框的高度）   
		var left = $("#jjdm").position().left; 	//获取距离最左端的距离，像素，整型   
		var top = $("#jjdm").position().top + 20; ; 	//获取距离最顶端的距离，像素，整型（20为搜索输入框的高度）   
		$("#searchresult").css("left", left + "px"); //重新定义CSS属性
		$("#searchresult").css("top", top + "px"); 
	}
	
	$(document).ready(function() {
		
		$("#jjdm").keyup(function (evt) {    
			ChangeCoords(); //控制查询结果div坐标   
			var k = window.event ? evt.keyCode : evt.which;    
			//输入框的id为txt_search，这里监听输入框的keyup事件    //不为空 && 不为上箭头或下箭头或回车   
			if ($("#jjdm").val() != "" && k != 38 && k != 40 && k != 13) {     
				//
			}else if (k == 38) {//上箭头     
				$('#aa tr.hover').prev().addClass("hover");     
			    $('#aa tr.hover').next().removeClass("hover");     
			    $('#jjdm').val($('#aa tr.hover').text());    
			} else if (k == 40) {//下箭头    
				$('#aa tr.hover').next().addClass("hover");     
				$('#aa tr.hover').prev().removeClass("hover");     
				$('#jjdm').val($('#aa tr.hover').text());    
			} else if (k == 13) {//回车    
				$('#jjdm').val($('#aa tr.hover').text());     
				$("#searchresult").empty();     
				$("#searchresult").css("display", "none");    
			}else {     
				$("#searchresult").empty();    
				$("#searchresult").css("display", "none");    
			}  
		});   
		
		$("#searchresult").bind("mouseleave", function () {    
			$("#searchresult").empty();    
			$("#searchresult").css("display", "none");  
	    });
		
		var alpha = echarts.init(document.getElementById('alpha'));
		var belta1 = echarts.init(document.getElementById('belta1'));
		var belta2 = echarts.init(document.getElementById('belta2'));
		var belta3 = echarts.init(document.getElementById('belta3'));
		var belta4 = echarts.init(document.getElementById('belta4'));
	    // 指定图表的配置项和数据
	    var option = {
	    	    tooltip: {
	    	        trigger: 'axis'
	    	    },
	    	    legend: {
	    	        data:['最高气温']
	    	    },
	    	   
	    	    xAxis:  {
	    	        type: 'category',
	    	        boundaryGap: false,
	    	        data: ['周一','周二','周三','周四','周五']
	    	    },
	    	    yAxis: {
	    	        type: 'value',
	    	        axisLabel: {
	    	            formatter: '{value}'
	    	        }
	    	    },
	    	    series: [
	    	        {
	    	            name:'差异',
	    	            type:'line',
	    	            smooth:true,
	    	            data:[],
	    	               markLine: {
	    	                   data: [
	    	                       {type: 'average', name: '平均值'},
	    	                       [{
	    	                           symbol: 'none',
	    	                           x: '90%',
	    	                           yAxis: 'max'
	    	                       }, {
	    	                           symbol: 'circle',
	    	                           label: {
	    	                               normal: {
	    	                                   position: 'start',
	    	                                   formatter: '最大值'
	    	                               }
	    	                           },
	    	                           type: 'max',
	    	                           name: '最高点'
	    	                       }]
	    	                   ]
	    	               }
	    	        }
	    	    ]
	    	};
	    alpha.setOption(option);
	    belta1.setOption(option);
	    belta2.setOption(option);
	    belta3.setOption(option);
	    belta4.setOption(option);
	    loadData($('#jjdm').val());
	    function loadData(jjdm){
	    	$.get('/web-console/factor/getfactordiff.do?jjdm='+jjdm).done(function (data) {
	            //var str = '{"alpha":[0.000770,0.000969,0.000342,0.000501,0.000670]}'
	            var dat = JSON.parse(data);
	            setOP(alpha,'ALPHA(α)',dat.categories,dat.alpha);
	            setOP(belta1,'十大重仓(β1)',dat.categories,dat.belta1);
	            setOP(belta2,'沪深300(β2)',dat.categories,dat.belta2);
	            setOP(belta3,'中小板(β3)',dat.categories,dat.belta3);
	            setOP(belta4,'创业板(β4)',dat.categories,dat.belta4);
	        });
	    }
	    function setOP(chart,name,categories,ds){
	    	chart.setOption({
	    		legend: {
	    	        data:[name]
	    	    },
    	    	xAxis:  {
        	        type: 'category',
        	        boundaryGap: false,
        	        data: categories
        	    },
    	    	series: [
    		    	        {
    		    	            name:name,
    		    	            type:'line',
    		    	            data:ds
    		    	        }
    		    	    ]
    	    });
        }
	    
	    $("#sub").click( function() {
	    	loadData($('#jjdm').val());
	    });
	});
</script>

</body>
</html>