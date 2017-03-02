<%@page import="java.io.File"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
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

<link href="lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title></title>
</head>
<body>
<div class="pd-20" style="padding-top:20px;">
  <p class="f-20 text-success">Table: T_FUND_ESTIMATE</p>
  <p>表中文名：盘中估值系数表 </p>
  <p>创建时间：2014-6-14 11:19:55       数据更新频次：<span class="label label-success radius">每天</span></p>
  <p>描述：盘中估值系数表盘中估值系数表盘中估值系数表盘中估值系数表盘中估值系数表盘中估值系数表 </p>
  
  <table class="table table-border table-bordered table-bg">
    <col style="width: 20%" />
	<col style="width: 20%" />
	<col style="width: 5%" />
	<col style="width: 5%" />
	<col style="width: 5%" />
	<col style="width: 5%" />
	<col style="width: 40%" />
    <thead>
      <tr>
        <th colspan="8" scope="col">Table Info</th>
      </tr>
      <tr class="text-c">
        <th>列名</th>
        <th>数据类型</th>
        <th>长度</th>
        <th>精度</th>
        <th>P</th>
        <th>F</th>
        <th>描述</th>
      </tr>
    </thead>
    <tbody>
      <tr class="text-l">
        <td>ID</td>
        <td>NUMBER</td>
        <td>18</td>
        <td></td>
        <td></td>
        <td></td>
		<td><span class="text-r"><a style="text-decoration:none" onClick="product_brand_edit('编辑','codeing.html','1')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a> </span></td>
      </tr>
      <tr class="text-l">
        <td>SECU_CODE</td>
        <td>VARCHAR2</td>
        <td>10</td>
        <td></td>
        <td></td>
        <td></td>
		<td><span class="text-r"><a style="text-decoration:none" onClick="product_brand_edit('编辑','codeing.html','1')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a></span> </td>
      </tr>
      <tr class="text-l">
        <td>SECU_MARKET</td>
        <td>VARCHAR2</td>
        <td>10</td>
        <td></td>
        <td></td>
        <td></td>
		<td></td>
      </tr>
      <tr class="text-l">
        <td>CHANGERATIO</td>
        <td>NUMBER</td>
        <td>18</td>
        <td>10</td>
        <td></td>
        <td></td>
		<td></td>
      </tr>
	  <tr class="text-l">
        <td>PORT10R</td>
        <td>NUMBER</td>
        <td>18</td>
        <td>10</td>
        <td></td>
        <td></td>
		<td></td>
      </tr>
    </tbody>
  </table>
  <table class="table table-border table-bordered table-bg mt-20">
    <thead>
      <tr>
        <th colspan="2" scope="col">服务器信息</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th width="200">服务器计算机名</th>
        <td><span id="lbServerName">http://127.0.0.1/</span></td>
      </tr>
      <tr>
        <td>服务器IP地址</td>
        <td>192.168.1.1</td>
      </tr>
      <tr>
        <td>服务器域名</td>
        <td>www.h-ui.net</td>
      </tr>
      <tr>
        <td>服务器端口 </td>
        <td>80</td>
      </tr>
      <tr>
        <td>服务器IIS版本 </td>
        <td>Microsoft-IIS/6.0</td>
      </tr>
      <tr>
        <td>本文件所在文件夹 </td>
        <td>D:\WebSite\HanXiPuTai.com\XinYiCMS.Web\</td>
      </tr>
      <tr>
        <td>服务器操作系统 </td>
        <td>Microsoft Windows NT 5.2.3790 Service Pack 2</td>
      </tr>
      <tr>
        <td>系统所在文件夹 </td>
        <td>C:\WINDOWS\system32</td>
      </tr>
      <tr>
        <td>服务器脚本超时时间 </td>
        <td>30000秒</td>
      </tr>
      <tr>
        <td>服务器的语言种类 </td>
        <td>Chinese (People's Republic of China)</td>
      </tr>
      <tr>
        <td>.NET Framework 版本 </td>
        <td>2.050727.3655</td>
      </tr>
      <tr>
        <td>服务器当前时间 </td>
        <td>2014-6-14 12:06:23</td>
      </tr>
      <tr>
        <td>服务器IE版本 </td>
        <td>6.0000</td>
      </tr>
      <tr>
        <td>服务器上次启动到现在已运行 </td>
        <td>7210分钟</td>
      </tr>
      <tr>
        <td>逻辑驱动器 </td>
        <td>C:\D:\</td>
      </tr>
      <tr>
        <td>CPU 总数 </td>
        <td>4</td>
      </tr>
      <tr>
        <td>CPU 类型 </td>
        <td>x86 Family 6 Model 42 Stepping 1, GenuineIntel</td>
      </tr>
      <tr>
        <td>虚拟内存 </td>
        <td>52480M</td>
      </tr>
      <tr>
        <td>当前程序占用内存 </td>
        <td>3.29M</td>
      </tr>
      <tr>
        <td>Asp.net所占内存 </td>
        <td>51.46M</td>
      </tr>
      <tr>
        <td>当前Session数量 </td>
        <td>8</td>
      </tr>
      <tr>
        <td>当前SessionID </td>
        <td>gznhpwmp34004345jz2q3l45</td>
      </tr>
      <tr>
        <td>当前系统用户名 </td>
        <td>NETWORK SERVICE</td>
      </tr>
    </tbody>
  </table>
</div>
<footer class="footer">
  <p>感谢jQuery、layer、laypage、Validform、UEditor、My97DatePicker、iconfont、Datatables、WebUploaded、icheck、highcharts、bootstrap-Switch<br>Copyright &copy;2015 H-ui.admin v2.3 All Rights Reserved.<br>
    本后台系统由<a href="http://www.h-ui.net/" target="_blank" title="H-ui前端框架">H-ui前端框架</a>提供前端技术支持</p>
</footer>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>