<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<title>智慧商场</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/responsive-nav.css">
	<link rel="stylesheet" href="css/styles.css">
	<link rel="stylesheet" href="css/main.css">
</head>
<body>
	<!-- nav开始 -->
	<div role="navigation" id="foo" class="nav-collapse">
      <ul>
      	<div class="logo">智慧商场</div>
      	<div class="user">
      		<div class="photo"><img src="photo/admin.png" alt=""></div>
      		<div class="info">
	            <a data-toggle="collapse" href="#collapseExample" class="collapsed">
	                admin
	                <b class="caret"></b>
	            </a>
	            <div class="collapse" id="collapseExample">
	                <ul class="nav">
	                    <li><a href="#">My Profile</a></li>
	                    <li><a href="#">Log Out</a></li>
	                </ul>
	            </div>
	        </div>
      	</div>
        <li><a href="user">用户画像</a></li>
        <li class="active"><a href="mall">商场统计</a></li>
        <li><a href="shop">店铺统计</a></li>
      </ul>
    </div>
    <!-- nav结束 -->
    <!-- main开始 -->
	<div class="main">
		<!-- nav-toggle开始 -->
		<a href="#nav" class="nav-toggle">Menu</a>
		<!-- nav-toggle结束 -->
		<!-- 图表内容开始 -->
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	商场实时热力图
				  	<div class="btn-group">
				  	  <img src="image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
	                <div id="divCon" style="text-align: center; height: 629px;">
	                    <div id="mapContainer" class="demo-wrapper">
	                        <div id="heatmap" class="heatmap"></div>
	                    </div>
	                </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	每小时商场客流分析
				  	<div class="btn-group">
				  	  <img src="image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-8"><img src="image/mall2_1.png" alt=""></div>
				    	<div class="col-md-4"><img src="image/mall2_2.png" alt=""></div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	商场平均驻留时长统计图
				  	<div class="btn-group">
				  	  <img src="image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-6"><img src="image/mall3_1.png" alt=""></div>
				    	<div class="col-md-6"><img src="image/mall3_2.png" alt=""></div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	商场动线图
				  	<div class="btn-group">
				  	  <img src="image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <img src="image/mall4.png" alt="">
				  </div>
				</div>
			</div>
		</div>
		<!-- 图表内容结束 -->
	</div>
	<!-- main结束 -->
<script type="text/javascript" src="plugins/jquery.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/responsive-nav.js"></script>
<script type="text/javascript" src="plugins/heatmap.min.js"></script>
<script type="text/javascript" src="js/mall.js"></script>
<script type="text/javascript">
var home = '<spring:message code="home"/>';
var mapId, origX, origY, bgImg, scale, coordinate, imgHeight, imgWidth, imgScale, heatmap, timer,floorLoop;
var pointVal = 20;
var configObj = {
        container : document.getElementById("heatmap"),
        maxOpacity : .6,
        radius : 20,
        blur : .90,
        backgroundColor : 'rgba(0, 58, 2, 0)'
    };
	$(function(){
		var navigation = responsiveNav("foo", {customToggle: ".nav-toggle"});
	});
    $(document).ready(function() {
    	Mall.initDropdown();
        //Heatmap.initDropdown();
        //Heatmap.bindClickEvent();
        //Heatmap.initHeatmap();
    });	
</script>
</body>
</html>