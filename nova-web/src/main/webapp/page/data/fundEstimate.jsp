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

<title>数据查询-系数查询</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 数据查询 <span class="c-gray en">&gt;</span> 系数查询<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-10">
    <div class="text-c">  交易日期：
        <input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'tradedate\')||\'%y%M%d\'}'})" id="tradedate" class="input-text Wdate" style="width:120px;">
        <input type="text" name="jjdm" id="jjdm" placeholder=" 基金代码" style="width:250px" class="input-text">
        <button name="sub" id="sub" class="btn btn-success" type="button"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th width="40">交易日期</th>
                    <th width="40">基金代码</th>
                    <th width="40" class="text-c">ALPHA(α)</th>
                    <th width="40">十大重仓(β1)</th>
                    <th width="40">沪深300(β2)</th>
                    <th width="40">中小板(β3)</th>
                    <th width="40">创业板(β4)</th>
                    <!-- 
                    <th width="40">净值估算值(元)</th>
                    <th width="40">当日涨跌幅</th>
                    <th width="40">十大重仓股涨跌幅</th>
                    <th width="40">沪深300涨跌幅</th>
                    <th width="40">中小板涨跌幅</th>
                    <th width="40">创业板涨跌幅</th>
                     -->
                     <th width="100">基金所属市场</th>
                    <th width="40">修改时间</th>
                </tr>
            </thead>

        </table>
    </div>
</div>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<script type="text/javascript" src="../lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../js/H-ui.js"></script> 
<script type="text/javascript" src="../js/H-ui.admin.js"></script> 

<script type="text/javascript">

$(document).ready(function() {
    //refreshDataTable();
    
    $('.table-sort').DataTable( {
    	"stateSave": true,
        "processing": true,
        "serverSide": true,
        "sAjaxSource": "/web-console/factor/query.do", // post请求方法
        "AutoWidth": false,
        "destroy":true,
        "iDisplayLength": 50,//每页显示10条数据
        "searching": false,//屏蔽datatales的查询框  
        "bLengthChange": false,//屏蔽tables的一页展示多少条记录的下拉列表  
        //"aLengthMenu":[50],//设置一页展示10条记录  
        "pagingType": "simple_numbers",
        "serverSide": true,//打开后台分页  
        "language": {
                'lengthMenu': '每页显示 _MENU_ 记录', 
                'zeroRecords': '没有数据', 
                'info': ' 第_PAGE_ 页/共 _PAGES_页,显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条', 
                'infoEmpty': '没有符合条件的记录', 
                'search': '查找', 
                'infoFiltered': '(从  _MAX_ 条记录中过滤)', 
                'paginate': { "first":  "首页 ", "last": "末页", "next": "下一页","previous": "上一页"} 
        },
        "fnServerParams": function (aoData) {
            aoData.push(
            { "name": "jjdm", "value": $('#jjdm').val() },
            { "name": "tradeDate", "value": $('#tradedate').val() }
           );
        },
        "aaSorting": [[ 1, "desc" ]],
        "columns": [ 
                    { "data": "tradeDate" },
                    { "data": "jjdm" },
                    { "data": "alpha"  ,"sClass":"text-center"},              
                    { "data": "belta1" ,"sClass":"center"},            
                    { "data": "belta2" ,"sClass":"center"},   
                    { "data": "belta3" ,"sClass":"center"},   
                    { "data": "belta4" ,"sClass":"center"},   
                    { "data": "market" }, 
                    { "data": "xgsj" }
         ] ,
         "aoColumnDefs": [
              { "sClass": "cell_right", "sType":"numeric","aTargets": [ 1 ] }
        ],
        "fnServerData": function (sUrl, aoData, fnCallback) {
        	//alert(JSON.stringify(aoData));
        	$.ajax({
                "url": sUrl,
                "data" :aoData,
                "success": fnCallback,
                "dataType": "json",
                "cache": false,
                "success" : function(resp) {  
                    fnCallback(resp);  
                    $("#datatable td").each(function(i){
		                      //给td设置title属性,并且设置td的完整值.给title属性.
		                      $(this).attr("title",$(this).text());
		             });
                }  
            });
        }
    } );
    
    $("#sub").click( function() {
    	$('.table-sort').DataTable().ajax.reload();
    });
});
/*
$('.table-sort').dataTable({
    "aaSorting": [[ 0, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[4]}// 制定列不参与排序
    ]
});
*/
//查询
function queryData(){
    var table=$('.table-sort').dataTable();
    if(table){
       table.fnDestroy();
    }
    
    var url= "/web-console/factor/query.do";
    alert(url);
    /*
    $('.table-sort').dataTable({
        "bProcessing": true,//开关，以指定当正在处理数据的时候，是否显示“正在处理”这个提示信息
        "sAjaxSource": url,
        "bServerSide": true,
        //"bInfo":false,//是否显示是否启用底边信息栏
        //"bAutoWidth": true,//是否自动计算表格各列宽度
        'pagingType': 'full_numbers',
        "language": {
                'lengthMenu': '每页显示 _MENU_ 记录', 
                'zeroRecords': '没有数据', 
                'info': ' 第_PAGE_ 页/共 _PAGES_页', 
                'infoEmpty': '没有符合条件的记录', 
                'search': '查找', 
                'infoFiltered': '(从  _MAX_ 条记录中过滤)', 
                'paginate': { "first":  "首页 ", "last": "末页", "next": "下一页","previous": "上一页"} 
        },
        "retrieve": true,
        "paging":   true,
        "ordering": true,
        "info":     true,
        "columns": [ 
                    { "data": "tradeDate" },
                    { "data": "jjdm" },
                    { "data": "alpha" },             
                    { "data": "paytype" },             
                    { "data": "belta1" },            
                    { "data": "belta2" },
                    { "data": "belta3" },
                    { "data": "belta4" }              
         ]    
    });
    */
    $('.table-sort').dataTable({
        "bProcessing": false,                   // 是否显示取数据时的那个等待提示    
        "bServerSide": true,                    //这个用来指明是通过服务端来取数据    
        "sAjaxSource": url,      //这个是请求的地址    
        "fnServerData": retrieveData            // 获取数据的处理函数    
    });
    alert(1);
}

//3个参数的名字可以随便命名,但必须是3个参数,少一个都不行    
function retrieveData( sSource,aoData, fnCallback) {    
    $.ajax({    
        url : sSource,                              //这个就是请求地址对应sAjaxSource    
        data : {"aoData":JSON.stringify(aoData)},   //这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数 ,分页,排序,查询等的值   
        type : 'post',    
        dataType : 'json',    
        async : false,    
        success : function(result) {    
            fnCallback(result);                     //把返回的数据传给这个方法就可以了,datatable会自动绑定数据的    
        },    
        error : function(msg) {    
        }    
    });    
}    
</script>

</body>
</html>