<%@page import="java.io.File"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"/>
	<title>status</title>
	<meta name="description" content="">
	<meta name="keyword" content="">	
	<link href="css/jquery.snippet.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/base.css">
	<script src="js/jtopo/jquery.js"></script>
	<script type="text/javascript" src="js/jtopo/snippet/jquery.snippet.min.js"></script>
	<script type="text/javascript" src="js/jtopo/jtopo-min.js"></script>
	<script type="text/javascript" src="js/jtopo/toolbar.js"></script>
	
	<script id='code'>	
		var map = {};
		$(document).ready(function(){					
			var canvas = document.getElementById('canvas');
			var stage = new JTopo.Stage(canvas);
			//显示工具栏
			//showJTopoToobar(stage);
			
			var scene = new JTopo.Scene();	
			//scene.background = 'images/bg.jpg';
			
			function node(id,x, y, img, name,parent){
				
				var node = new JTopo.Node();
				node.setImage('images/pstn/' + img, true);				
				node.setLocation(x, y);
				
				if(parent)
					parent.add(node);
				node.mouseover(function(){
                    this.text = name;
                });
                node.mouseout(function(){
                    this.text = null;
                });
                map[id] = node;
				scene.add(node);
				return node;
			}				
			
			function linkNode(nodeA, nodeZ,text){
				if(!text) text = '';
				var link = new JTopo.Link(nodeA, nodeZ,text);
				link.lineWidth = 1;
				link.arrowsRadius = 5; //箭头大小
				link.strokeColor = '255,255,0';
				scene.add(link);
				return link;
			}
			
			var master = new JTopo.Container('主节点');
			master.textPosition = 'Middle_Center';
			master.fontColor = '100,255,0';
			master.font = '18pt 微软雅黑';
			master.borderColor = '255,0,0';
			master.borderRadius = 30; // 圆角
			scene.add(master);
			var standby = new JTopo.Container('备节点');
			standby.textPosition = 'Middle_Center';
			standby.fontColor = '200,255,0';
			standby.font = '18pt 微软雅黑';
			standby.borderColor = '255,0,0';
			standby.borderRadius = 30; // 圆角
			scene.add(standby);
			
			var gzxs1 = node('gzxs1',300, 300, 'msc.png', '估值系数');
			var gzxs2 = node('gzxs2',900, 300, 'msc.png', '估值系数');
			var factor1 = node('factor1',400, 315, 'router.png', '系数计算服务',master);
			var factor2 = node('factor2',800, 315, 'router.png', '系数计算服务',standby);
			var funddb = node('funddb',630, 360, 'server.png', '金融数据库');
			linkNode(funddb, factor1);
			linkNode(factor1, gzxs1);
			linkNode(funddb, factor2);
			linkNode(factor2, gzxs2);
			
			var calcCache1 = node('calcCache1',420, 270, 'center.png', '计算缓存',master);
			var calcCache2 = node('calcCache2',820, 270, 'center.png', '计算缓存',standby);
			linkNode(factor1, calcCache1,'1d');
			linkNode(factor2, calcCache2,'1d');
			
			var stock1 = node('stock1',550, 260, 'left_stock.png', '行情读取服务',master);
			var stock2 = node('stock2',710, 260, 'right_stock.png', '行情读取服务',standby);
			linkNode(stock1, calcCache1);
			linkNode(stock2, calcCache2);
			
			var file1 = node('file1',630, 270, 'modem.png', '行情文件1');
			var file2 = node('file2',630, 290, 'modem.png', '行情文件2');
			linkNode(file1, stock1,'2s');
			linkNode(file2, stock1,'2s');
			linkNode(file1, stock2,'2s');
			linkNode(file2, stock2,'2s');
			
			//stock2.alarm = '1 M';
			
			var hqsj1 = node('hqsj1',550, 180, 'msc.png', '行情数据');
			var hqsj2 = node('hqsj2',710, 180, 'msc.png', '行情数据');
			linkNode(stock1, hqsj1,'30s');
			linkNode(stock2, hqsj2,'30s');
			
			var calc1 = node('calc1',420, 180, 'cartridge_system.png', '估值计算服务',master);
			var calc2 = node('calc2',820, 180, 'cartridge_system.png', '估值计算服务',standby);
			linkNode(calcCache1,calc1,'30s');
			linkNode(calcCache2,calc2,'30s');
			
			var gzsj1 = node('gzsj1',300, 180, 'msc.png', '估值数据落库');
			var gzsj2 = node('gzsj2',900, 180, 'msc.png', '估值数据落库');
			linkNode(calc1,gzsj1);
			linkNode(calc2,gzsj2);
			
			var valCache1 = node('valCache1',420, 120, 'center.png', '估值缓存',master);
			var valCache2 = node('valCache2',820, 120, 'center.png', '估值缓存',standby);
			linkNode(calc1,valCache1);
			linkNode(calc2,valCache2);
			
			var push1 = node('push1',410, 50, 'satellite_antenna.png', '推送服务',master);
			var push2 = node('push2',810, 50, 'satellite_antenna.png', '推送服务',standby);
			linkNode(valCache1,push1);
			linkNode(valCache2,push2);
			
			var ec = node('ec',610, 10, 'cloud.png', 'EC Server');
			var mp = linkNode(push1,ec);
			linkNode(push2,ec);
			
			function getNodeById(name){
				return map[name];
			}
			
			function msg(x, y, msg){
                var node = new JTopo.TextNode(msg);
                node.setLocation(x, y);
                node.font = 'italic bold 20px 微软雅黑';
                scene.add(node);    
            }
			
			function tips(name,tip){
				var nd = getNodeById(name);
				setInterval(function(){
					//nd.alarmColor = "123,249,0"
					if(nd.alarm == tip){
						nd.alarm = null;
					}else{
						nd.alarm = tip
					}
				}, 600);
			}
			
			/*
			setInterval(function(){
				push2.alarmColor = "123,249,0"
				if(push2.alarm == '我是备胎'){
					push2.alarm = null;
				}else{
					push2.alarm = '我是备胎'
				}
			}, 600);
			*/
			tips('push2','我是备胎');
			setInterval(function(){
				push1.alarmColor = "123,249,0"
				if(push1.alarm == '我在推送'){
					push1.alarm = null;
				}else{
					push1.alarm = '我在推送'
				}
			}, 600);
			
			setInterval(function(){
				if(stock2.alarm == '二级告警'){
					stock2.alarm = null;
				}else{
					stock2.alarm = '二级告警'
				}
			}, 600);
			msg(20, 20, '当前状态：盘后运行中....');
			stage.add(scene);
		});
	</script>
  </head>
<body style="width:100%;">
		<div id="content" style="width:100%;">
			<canvas width="1280" height="800" id="canvas"></canvas>	
		</div>
</body>

</html>