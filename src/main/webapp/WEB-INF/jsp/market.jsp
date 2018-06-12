 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<title>智慧商场</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/bootstrap-table.css">
	<link rel="stylesheet" href="../css/responsive-nav.css">
	<link rel="stylesheet" href="../css/styles.css">
	<link rel="stylesheet" href="../css/main.css">
	<style>
	.floor{
	    background-color: rgba(237, 237, 237, 0.7);
        border: 2px solid #b9bec5;
        color: black;
        cursor: pointer;
        position: absolute;
        line-height: 30px;
        width: 80px;
        text-align: center;
        margin-left: 10px;
	}
	.floor:hover,.floor.active{
	    background-color: rgba(255, 250, 250, 0.7);
	}
	</style>
</head>
<body>
	<!-- nav开始 -->
	<div role="navigation" id="foo" class="nav-collapse">
      <ul>
      	<div class="logo">智慧商场</div>
      	<div class="user">
      		<div class="photo"><img src="../photo/admin.png" alt=""></div>
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
        <li><a href="../user">用户画像</a></li>
        <li class="active"><a href="<c:url value='/market/getMarketInfo'/>">商场统计</a></li>
        <li><a href="<c:url value='/home/shop'/>">店铺统计</a></li>
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
				  	商场总览
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				  	<div class="col-md-3">
					    <div class="form-group" style="position: relative;">
						    	<label for="">当日人流量：</label>
						    	<span>${currentCounts}人次</span>
					  	</div>
				  	</div>
				  	<div class="col-md-3">
					    <div class="form-group" style="position: relative;">
						    	<label for="">平均驻留时长：</label>
						    	<span>${ivgResidentTime}分钟</span>
					  	</div>
				  	</div>
				  	<div class="col-md-3">
					    <div class="form-group" style="position: relative;">
						    	<label for="">当日销售额：</label>
						    	<span>$${revenue}万元</span>
					  	</div>
				  	</div>
				  	<div class="col-md-3">
					    <div class="form-group" style="position: relative;">
						    	<label for="">人均消费：</label>
						    	<span>$${ivgRrevenue}元</span>
					  	</div>
				  	</div>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	商场实时热力图
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
                   <div id="divCon" style="text-align: center; ">
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
				  	商场数据分析
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-12">
				    		<div id="statisticChart" style="height:400px;"></div>
				    	</div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	店铺TOP10
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-12">
				    		 <div class="form-group" style="position: relative;">
				    		 	<form action="">
							    	<label for="">统计类型：</label>
							    	<input class="type" type="radio" name="type" checked="checked" value="1"><span>按销售额</span>
						    		<input class="type" type="radio" name="type" value="2"><span>按人流量</span>
						    		<input class="type" type="radio" name="type" value="3"><span>按驻留时间</span>
					    		</form>
					  		</div>
				    	</div>
				    </div>
				    <div class="row">
				    	<div class="col-md-12">
				    		 <div class="form-group" style="position: relative;">
						    	<label for="">店铺类别：</label>
						    	<c:forEach items="${marketTypeList}" var="name">
						    		<input class="shopType" type="checkbox" checked="checked"><span>${name}</span>
						    	</c:forEach>
					  		</div>
				    	</div>
				    </div>
				    <div class="row">
				    	<div class="col-md-12">
				    		<div id="topChart" style="height:400px;">
				    		</div>
				    	</div>
				    </div>
				  </div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	店铺详情
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-12">
				    		<div id="topList" style="height:100%;">
				    			<table class="table" id="top10-table">
				    			</table>
				    		</div>
				    	</div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	店铺分布
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-12">
				    		 <div class="form-group" style="position: relative;">
				    		 	<form action="">
							    	<label for="">统计类型：</label>
							    	<input class="model" type="radio" name="type" checked="checked" value="1"><span>按面积</span>
						    		<input class="model" type="radio" name="type" value="2"><span>按类别</span>
					    		</form>
					  		</div>
				    	</div>
				    </div>
				    <div class="row">
				    	<div class="col-md-12">
				    		<div id="annulusChart" style="height:400px;">
				    		</div>
				    	</div>
				    </div>
				  </div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	分布详情
				  	<div class="btn-group">
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				    	<div class="col-md-12">
				    		<div id="shopList" style="height:100%;">
				    			<table id="anothor-table" class="table"></table>
				    		</div>
				    	</div>
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
				  	  <img src="../image/share.png" alt="" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  	  <ul class="dropdown-menu">
				  	    <li><a href="#"><img src="../image/icon_xinlang.png" alt="">新浪微博</a></li>
				  	    <li><a href="#"><img src="../image/icon_tengxun.png" alt="">腾讯微博</a></li>
				  	  </ul>
				  	</div>
				  </div>
				  <div class="panel-body">
				    <div class="row">
				        <div id="routeLine" style="height:500px;">
				            <canvas id="canvas"></canvas>
				        </div>
				        <div id="floorDiv">
				        </div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
		<!-- 图表内容结束 -->
	</div>
	<!-- main结束 -->
<script type="text/javascript" src="../plugins/jquery.min.js"></script>
<script type="text/javascript" src="../plugins/bootstrap.min.js"></script>
<script type="text/javascript" src="../plugins/bootstrap-table.js"></script>
<script type="text/javascript" src="../plugins/responsive-nav.js"></script>
<script type="text/javascript" src="../plugins/echarts3/echarts.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/market.js"></script>
<script type="text/javascript" src="../js/routeLine.js"></script>
<script type="text/javascript" src="../plugins/heatmap.min.js"></script>
<script type="text/javascript" src="../js/mall.js"></script>
<script>
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
		RouteLine.init();
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