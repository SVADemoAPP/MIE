<!DOCTYPE html>
<%@page import="com.bis.common.Util"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.bis.model.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title><spring:message code="mall" /></title>
<!-- Mobile specific metas -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- Force IE9 to render in normal mode -->
<!--[if IE]><meta http-equiv="x-ua-compatible" content="IE=9" /><![endif]-->
<meta name="author" content="SuggeElson" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="application-name" content="sprFlat admin template" />
<!-- Import google fonts - Heading first/ text second -->
<link rel='stylesheet' type='text/css' />
<!--[if lt IE 9]>

<![endif]-->
<!-- Css files -->
<!-- Icons -->
<link href="../plugins/newJs/css/icons.css" rel="stylesheet" />
<!-- jQueryUI -->
<link href="../plugins/newJs/css/sprflat-theme/jquery.ui.all.css"
	rel="stylesheet" />
<!-- Bootstrap stylesheets (included template modifications) -->
<link href="../plugins/newJs/css/bootstrap.css" rel="stylesheet" />
<!-- Plugins stylesheets (all plugin custom css) -->
<link href="../plugins/newJs/css/plugins.css" rel="stylesheet" />
<!-- Main stylesheets (template main css file) -->
<link href="../plugins/newJs/css/main.css" rel="stylesheet" />
<!-- Custom stylesheets ( Put your own changes here ) -->
<link href="../plugins/newJs/css/custom.css" rel="stylesheet" />
<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../plugins/newJs/img/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../plugins/newJs/img/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../plugins/newJs/img/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../plugins/newJs/img/ico/apple-touch-icon-57-precomposed.png">
<link rel="icon" href="../plugins/newJs/img/ico/favicon.ico"
	type="image/png">
<!-- Windows8 touch icon ( http://www.buildmypinnedsite.com/ )-->
<meta name="msapplication-TileColor" content="#3399cc" />
<style>
.floor {
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

.floor:hover, .floor.active {
	background-color: rgb(117, 185, 230, 1);
}

.floors {
	background-color: rgba(237, 237, 237, 0.7);
	border: 2px solid rgba(117, 185, 230, 0.5);
	color: black;
	cursor: pointer;
	position: absolute;
	line-height: 30px;
	width: 80px;
	text-align: center;
	margin-left: 10px;
}

.floors:hover, .floors.active {
	background-color: rgba(117, 185, 230, 0.5);
}
</style>
</head>
<body>
	<!-- Start #header -->
	<div id="header">
		<div class="container-fluid">
			<div class="navbar">
				<div class="navbar-header">
					<a class="navbar-brand" href="../market/getMarketInfo"> <img
						src="<c:url value='/images/svalogo.png'/>"
						style="margin: -7px 5px 0 20%; height: 35px;"></img><span
						class="text-logo">IVAS</span><span class="text-slogan"></span>
					</a>
				</div>
				<nav class="top-nav" role="navigation">
					<ul class="nav navbar-nav pull-left">
						<li id="toggle-sidebar-li"><a href="#" id="toggle-sidebar"><i
								class="en-arrow-left2"></i> </a></li>
						<li><a href="#" class="full-screen"><i
								class="fa-fullscreen"></i></a></li>
					</ul>
					<ul class="nav navbar-nav pull-right">
						<li class="dropdown"><a
							style="font-size: 15px; font-weight: bold;"
							href="/sva/home/changeLocal?local=zh">中文</a></li>
						<li class="dropdown"><a style="font-size: 15px;"
							href="/sva/home/changeLocal?local=en">English</a></li>
						<li class="dropdown"><a href="#" data-toggle="dropdown">
								<img class="user-avatar"
								src="../plugins/newJs/img/avatars/48.jpg" alt="${sessionScope.userName}">${sessionScope.userName}
						</a>
							<ul class="dropdown-menu right" role="menu">
								<!--  <li><a href="profile.html"><i class="st-user"></i> Profile</a>
                                    </li>
                                    <li><a href="file.html"><i class="st-cloud"></i> Files</a>
                                    </li>
                                    <li><a href="#"><i class="st-settings"></i> Settings</a>
                                    </li>-->
                                <li><a href="../account/logout"><i class="im-exit"></i> Logout</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</div>
		<!-- Start .header-inner -->
	</div>
	<!-- End #header -->
	<!-- Start #sidebar -->
	<div id="sidebar">
		<!-- Start .sidebar-inner -->
		<div class="sidebar-inner">
			<!-- Start #sideNav -->
			<ul id="sideNav" class="nav nav-pills nav-stacked">
				<li><a class="active"
					href="<c:url value='/market/getMarketInfo'/>"><spring:message
							code="mall"></spring:message><i class="st-chart"></i></a></li>
				<li><a href="<c:url value='/home/shop'/>"><spring:message
							code="shop"></spring:message><i class="im-images"></i></a></li>
				<li><a href="<c:url value='/home/operation'/>"><spring:message
							code="operation"></spring:message> <i class="im-screen"></i></a>
                    </li>
				<li><a href="#"><spring:message code="configuration"></spring:message><i
						class="im-paragraph-justify"></i></a>
					<ul class="nav sub">
						<li><a href="<c:url value='/home/storeMng'/>"><i
								class="ec-pencil2"></i>
							<spring:message code="store_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/mapMng'/>"><i
								class="im-checkbox-checked"></i>
							<spring:message code="map_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/categoryMng'/>"><i
								class="im-wand"></i>
							<spring:message code="category_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/shopMng'/>"><i
								class="fa-pencil"></i>
							<spring:message code="shop_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/ticketMng'/>"><spring:message
									code="ticket_config"></spring:message><i class="st-chart"></i>
						</a></li>
					</ul></li>
			</ul>
			<!-- End #sideNav -->
			<!-- Start .sidebar-
 -->
			<div class="sidebar-panel">
				<h4 class="sidebar-panel-title">
					<i class="im-fire"></i>
					<spring:message code="mall_user"></spring:message>
				</h4>
				<div class="sidebar-panel-content">
					<ul class="server-stats">
						<li><span class="txt"><spring:message
									code="mall_diskSpace"></spring:message></span> <span class="percent">${diskspace}</span>
							<div class="pie-chart" data-percent="${diskspace}"></div></li>
					</ul>
					<ul class="server-stats">
						<li><span class="txt"><spring:message code="mall_cpu"></spring:message></span>
							<span class="percent">${cpu}</span>
							<div class="pie-chart" data-percent="${cpu}"></div></li>
					</ul>
					<ul class="server-stats">
						<li><span class="txt"><spring:message
									code="mall_memory"></spring:message></span> <span class="percent">${memory}</span>
							<div class="pie-chart" data-percent="${memory}"></div></li>
					</ul>
				</div>
			</div>
			<!-- End .sidebar-panel -->
		</div>
		<!-- End .sidebar-panel -->
	</div>
	<!-- End .sidebar-inner -->
	</div>
	<!-- End #sidebar -->
	<!-- Start #content -->
	<div id="content">
		<!-- Start .content-wrapper -->
		<div class="content-wrapper">
			<div class="row">
				<!-- Start .row -->
				<!-- Start .page-header -->
				<div class="col-lg-12 heading">
					<h1 class="page-header">
						<i class="im-images"></i>
						<spring:message code="mall_statistics"></spring:message>
					</h1>
					<!-- Start .bredcrumb -->
					<ul id="crumb" class="breadcrumb">
					</ul>
					<!-- End .breadcrumb -->
				</div>
				<!-- End .page-header -->
			</div>
			<!-- End .row -->
			<div class="outlet">
				<div class="row" style="margin-bottom: 10px;">
					<label style="text-align: center; margin-top: 7px;"
						class="col-lg-1 col-md-1 col-sm-12 control-label"><spring:message
							code="mall_name"></spring:message> </label>
					<div class="col-lg-4 col-md-4">
						<select data-type="storeSelect" class="form-control select2"
							id="storeId">

						</select>
					</div>
				</div>
				<!-- Start .outlet -->
				<!-- Page start here ( usual with .row ) -->
				<div class="row">
					<!-- col-lg-6 end here -->
					<div class="col-lg-12 col-md-12">
						<!-- col-lg- start here -->
						<div class="panel-default toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title">
									<i class="im-bars"></i>
									<spring:message code="mall_situation"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
										<!-- col-lg-3 start here -->
										<div class="tile gray-spr m0 mb20">
											<div class="tile-content text-center clearfix">
												<div class="label">
													<spring:message code="mall_current"></spring:message>
												</div>
												<div id="nowPeople" class="spark-number">
													<i class="ec-users"></i>
												</div>
												<div class="spark clearfix">
													<div id="nowPeopleId" class="percent">
														<i class="im-arrow-up-right3 color-green"></i>%
													</div>
													<div id="nowData" class="sparkline sparkline-positive"></div>
												</div>
											</div>
										</div>
									</div>
									<!-- col-lg-3 end here -->

									<!-- col-lg-3 end here -->
									<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
										<!-- col-lg-3 start here -->
										<div class="tile gray-spr m0 mb20">
											<div class="tile-content text-center clearfix">
												<div class="label">
													<spring:message code="mall_newVisitors"></spring:message>
												</div>
												<div id="newPeople" class="spark-number">
													<i class="ec-users"></i>0
												</div>
												<div class="spark clearfix">
													<div id="newPeopleId" class="percent">
														<i class="im-arrow-up-right3 color-green"></i>%
													</div>
													<div id="newData" class="sparkline sparkline-bar-positive"></div>
												</div>
											</div>
										</div>
									</div>

									<!-- col-lg-3 end here -->
									<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
										<!-- col-lg-3 start here -->
										<div class="tile gray-spr m0 mb20">
											<div class="tile-content text-center clearfix">
												<div class="label">
													<spring:message code="mall_cumulativ"></spring:message>
												</div>
												<div id="nowAllcount" class="spark-number">
													<i class="ec-users"></i>
												</div>
												<div class="spark clearfix">
													<div id="nowAllcountId" class="percent">
														<i class="im-arrow-down-right2 color-red"></i>%
													</div>
													<div id="allDataId"
														class="sparkline sparkline-bar-negative"></div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
										<!-- col-lg-3 start here -->
										<div class="tile gray-spr m0 mb20">
											<div class="tile-content text-center clearfix">
												<div class="label">
													<spring:message code="mall_avrageTime"></spring:message>
												</div>
												<div id="nowTime" class="spark-number">
													<i class="ec-clock"></i>
												</div>
												<div class="spark clearfix">
													<div id="nowTimeId" class="percent">
														<i class="im-arrow-down-right2 color-red"></i>%
													</div>
													<div id="timeDataId" class="sparkline sparkline-negative"></div>
												</div>
											</div>
										</div>
									</div>
									<!-- col-lg-3 end here -->
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					<!-- col-lg-6 end here -->
				</div>
				<div class="row">
					<div class="col-lg-6 col-md-12">
						<!-- Start col-lg-6 -->
						<div class="panel-default toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title">
									<i class="im-bars"></i>
									<spring:message code="mall_heatMap"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon" style="text-align: center;">
									<div id="mapContainer" class="demo-wrapper">
										<div id="heatmap" class="heatmap" style="height: 400px"></div>

									</div>
									<!-- <div class="floors" style="top: 100px;">1F</div>
									<div class="floors" style="top: 150px;">2F</div> -->
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					<div class="col-lg-6 col-md-12">
						<!-- Start col-lg-6 -->
						<div class="panel-default toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<div class="col-lg-3 col-md-12">
									<h4 class="panel-title">
										<i class="im-bars"></i>
										<spring:message code="mall_time_heatMap"></spring:message>
									</h4>
								</div>
								<div class="col-lg-4 col-md-12" style="margin-top: 8px;">
									<input class="sang_Calender" style=""
										width: 62%;" id="select_time_begin_tab1"
										onclick="getDatePicker(this)" readonly /> <img
										src="../plugins/wDatePicker/skin/datePicker.gif"
										onclick="getDatePicker('select_time_begin_tab1');">-
								</div>
								<div id="timeId" class="col-lg-3 col-md-12 myhide"
									style="margin-top: 8px;">
									<input class="sang_Calender" style="width: 87%;"
										id="select_time_end_tab1" onclick="getDatePicker1(this)"
										readonly /> <img
										src="../plugins/My97DatePicker/skin/datePicker.gif"
										onclick="getDatePicker1('select_time_end_tab1');">
								</div>
								<div id="ConfirmId" class="col-lg-1 col-md-12 myhide"
									style="margin-top: 4px;">
									<button class="btn btn-success" id="heatMapConfirm">
										<spring:message code="mall_time_heatMap"></spring:message>
									</button>
								</div>
							</div>
							<div class="panel-body">
								<div id="divCon1" style="text-align: center;">
									<div id="mapContainer1" class="demo-wrapper">
										<div id="heatmap1" class="heatmap" style="height: 400px"></div>
									</div>
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
				</div>
				<div class="row">
					<!-- Start .row -->
					<div class="col-lg-12 col-md-12">
						<!-- Start col-lg-12 -->
						<div class="panel-default toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title">
									<i class="im-bars"></i>
									<spring:message code="mall_ten"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="autoupdate-chart" style="width: 100%; height: 250px;"></div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					<!-- End col-lg-12 -->
				</div>
				<!-- End .row -->
				<div class="row">
					<!-- Start .row -->
					<div class="col-lg-6 col-md-6 sortable-layout">
						<!-- Start col-lg-6 -->
						<div class="panel-teal toggle ">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title">
									<i class="im-bars"></i>
									<spring:message code="mall_week"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="stats-pageviews" style="width: 100%; height: 250px;"></div>
							</div>
							<div class="panel-footer teal-bg">
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
									<div class="tile teal m0">
										<div class="tile-content text-center pl0 pr0">
											<div id="countToday" class="number">0</div>
											<h3>
												<spring:message code="mall_today"></spring:message>
											</h3>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
									<div class="tile teal m0">
										<div class="tile-content text-center pl0 pr0">
											<div id="countYesterday" class="number">0</div>
											<h3>
												<spring:message code="mall_yestday"></spring:message>
											</h3>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
									<div class="tile teal m0">
										<div class="tile-content text-center pl0 pr0">
											<div id="countTotal" class="number">0</div>
											<h3>
												<spring:message code="mall_week_total"></spring:message>
											</h3>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-6 col-md-6 sortable-layout">
						<!-- Start col-lg-6 -->
						<div class="panel-primary plain toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title">
									<i class="im-bars"></i>
									<spring:message code="mall_week_time"></spring:message>
								</h4>
							</div>
							<div class="panel-body white-bg">
								<div id="line-chart-filled" style="width: 100%; height: 250px;"></div>
							</div>
							<div class="panel-body blue-bg" style="height: 127px;">
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
									<div class="tile body m0">
										<div class="tile-content text-center pl0 pr0">
											<div id="countToday2" class="spark-number">
												<i class="ec-clock"></i>0
											</div>
											<h3>
												<spring:message code="mall_today"></spring:message>
											</h3>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
									<div class="tile body m0">
										<div class="tile-content text-center pl0 pr0">
											<div id="countYesterday2" class="spark-number">
												<i class="ec-clock"></i>0
											</div>
											<h3>
												<spring:message code="mall_yestday"></spring:message>
											</h3>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
									<div class="tile body m0">
										<div class="tile-content text-center pl0 pr0">
											<div id="countTotal2" class="spark-number">
												<i class="ec-clock"></i>0
											</div>
											<h3>
												<spring:message code="mall_week_average"></spring:message>
											</h3>
										</div>
									</div>
								</div>

							</div>

						</div>
						<!-- End .panel -->
					</div>

					<!-- End col-lg-6 -->

					<div class="row">
						<div class="col-lg-6 col-md-12">
							<!-- Start col-lg-6 -->
							<div class="panel-default toggle ">
								<!-- Start .panel -->
								<div class="panel-heading">
									<div class="col-lg-3 col-md-12">
										<h4 class="panel-title">
											<i class="im-bars"></i>
											<spring:message code="trend_hour"></spring:message>
										</h4>
									</div>
									<div class="col-lg-4 col-md-12" style="margin-top: 8px;">
										<input class="sang_Calender" style="width: 75%;"
											id="select_time_begin_tab2" onclick="getDatePicker2(this)"
											readonly /> <img
											src="../plugins/wDatePicker/skin/datePicker.gif"
											onclick="getDatePicker2('select_time_begin_tab2');">
									</div>
								</div>
								<div class="panel-body">
									<div id="divCon2" style="text-align: center;">
										<div id="mapTrendByHour" style="height: 400px; "></div>
									</div>
								</div>
							</div>
							<!-- End .panel -->
						</div>

						<div class="col-lg-6 col-md-12">
							<!-- Start col-lg-6 -->
							<div class="panel-default toggle ">
								<!-- Start .panel -->
								<div class="panel-heading">
									<div class="col-lg-3 col-md-12">
										<h4 class="panel-title">
											<i class="im-bars"></i>
											<spring:message code="trend_day"></spring:message>
										</h4>
									</div>
									<div class="col-lg-4 col-md-12" style="margin-top: 8px;">
										<input class="sang_Calender" style="width: 75%;"
											id="select_time_begin_tab3" onclick="getDatePicker3(this)"
											readonly /> <img
											src="../plugins/wDatePicker/skin/datePicker.gif"
											onclick="getDatePicker3('select_time_begin_tab3');">
									</div>
								</div>
								<div class="panel-body">
									<div id="divCon3" style="text-align: center;">
										<div id="mapTrendByDay" style="height: 400px;"></div>
									</div>
								</div>
							</div>
							<!-- End .panel -->
						</div>

						<!-- End .row -->
						<!-- Page End here -->
					</div>
	
				</div>

				<div
					class="weather-widget panel-primary plain toggle panelMove panelClose panelRefresh">
					<!-- Start .panel -->
					<div class="panel-heading">
						<h4 class="panel-title">
							<spring:message code="mall_weather"></spring:message>
						</h4>
					</div>
					<div class="panel-body blue-bg text-center">
						<canvas id="weather-now" width="128" height="128"></canvas>
						<p class="weather-location"><strong id="date_0"></strong> <span id="week_0"></span></p>
						<p class="weather-location">
							<strong id="temperature"></strong> <span id="city"></span>
						</p>
					</div>
					<div class="panel-footer white-bg text-center" id="wea">
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
							<!-- col-lg-3 start here -->
							<p class="weather-day" id="week_1"></p>
							<p id="date_1" class="day"></p>
							<canvas id="forecast-now" width="64" height="64"></canvas>
							<p class="weather-degree" id="temperature_1"></p>
						</div>
						<!-- col-lg-3 end here -->
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
							<!-- col-lg-3 start here -->
							<p class="weather-day" id="week_2"></p>
							<p id="date_2" class="day"></p>
							<canvas id="forecast-day1" width="64" height="64"></canvas>
							<p class="weather-degree" id="temperature_2"></p>
						</div>
						<!-- col-lg-3 end here -->
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
							<!-- col-lg-3 start here -->
							<p class="weather-day" id="week_3"></p>
							<p id="date_3" class="day"></p>
							<canvas id="forecast-day2" width="64" height="64"></canvas>
							<p class="weather-degree" id="temperature_3"></p>
						</div>
						<!-- col-lg-3 end here -->
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
							<!-- col-lg-3 start here -->
							<p class="weather-day" id="week_4"></p>
							<p id="date_4" class="day"></p>
							<canvas id="forecast-day3" width="64" height="64"></canvas>
							<p class="weather-degree" id="temperature_4"></p>
						</div>
						<!-- col-lg-3 end here -->
					</div>
				</div>
				
				<!-- 柱状图 -->
	        	 <div class="col-lg-12 col-md-12">
                         <!-- Start col-lg-6 -->
                         <div class="panel panel-default toggle">
                             <!-- Start .panel -->
                             <div class="panel-heading">
                                 <h4 class="panel-title"><i class="im-bars"></i> Ordered Bars chart</h4>
                             </div>
                             <div class="panel-body">
                                 <div id="ordered-bars-chart" style="width: 100%; height:250px;"></div>
                             </div>
                         </div>
                         <!-- End .panel -->
                  </div>

                <div class="row">
                    <div class="col-lg-6 col-md-12">
                        <!-- Start col-lg-6 -->
                        <div class="panel panel-primary plain toggle">
                            <!-- Start .panel -->
                            <div class="panel-heading">
                                <h4 class="panel-title"><i class="im-bars"></i>Route map</h4>
                            </div>
                            <div class="panel-body">
                                <div id="routeLine" style="height:400px;">
                                    <canvas id="canvas"></canvas>
                                </div>
                                <div id="floorDiv">
                                </div>
                            </div>
                        </div>
                        <!-- End .panel -->
                    </div>                
                </div>


				<!-- End .row -->
				<!-- Page End here -->
			</div>
			<!-- End .outlet -->
		</div>
		<!-- End .content-wrapper -->
		<div class="clearfix"></div>
	</div>
	<!-- End #content -->
	<!-- Javascripts -->
	<!-- Load pace first -->
	<script src="../plugins/newJs/plugins/core/pace/pace.min.js"></script>
	<!-- Important javascript libs(put in all pages) -->
	<script src="../plugins/newJs/js/jquery-1.8.3.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../plugins/newJs/js/libs/jquery-2.1.1.min.js">\x3C/script>')
	</script>
	<script src="../plugins/newJs/js/jquery-ui.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../plugins/newJs/js/libs/jquery-ui-1.10.4.min.js">\x3C/script>')
	</script>
	<!--[if lt IE 9]>
  <script type="text/javascript" src="../plugins/newJs/js/libs/excanvas.min.js"></script>
  <script type="text/javascript" src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <script type="text/javascript" src="../plugins/newJs/js/libs/respond.min.js"></script>
<![endif]-->
	<!-- Bootstrap plugins -->
	<script src="../plugins/newJs/js/bootstrap/bootstrap.js"></script>
	<!-- Core plugins ( not remove ever) -->
	<!-- Handle responsive view functions -->
	<script src="../plugins/newJs/js/jRespond.min.js"></script>
	<!-- Custom scroll for sidebars,tables and etc. -->
	<script
		src="../plugins/newJs/plugins/core/slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="../plugins/newJs/plugins/core/slimscroll/jquery.slimscroll.horizontal.min.js"></script>
	<!-- Resize text area in most pages -->
	<script
		src="../plugins/newJs/plugins/forms/autosize/jquery.autosize.js"></script>
	<!-- Proivde quick search for many widgets -->
	<script
		src="../plugins/newJs/plugins/core/quicksearch/jquery.quicksearch.js"></script>
	<!-- Bootbox confirm dialog for reset postion on panels -->
	<script src="../plugins/newJs/plugins/ui/bootbox/bootbox.js"></script>
	<!-- Other plugins ( load only nessesary plugins for every page) -->
	<script src="../plugins/newJs/plugins/charts/flot/jquery.flot.js"></script>
	<script src="../plugins/newJs/plugins/charts/flot/jquery.flot.pie.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/flot/jquery.flot.resize.js"></script>
	<script src="../plugins/newJs/plugins/charts/flot/jquery.flot.time.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/flot/jquery.flot.growraf.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/flot/jquery.flot.categories.js"></script>
	<script src="../plugins/newJs/plugins/charts/flot/jquery.flot.stack.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/flot/jquery.flot.tooltip.min.js"></script>
	<script src="../plugins/newJs/plugins/charts/flot/date.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/sparklines/jquery.sparkline.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/pie-chart/jquery.easy-pie-chart.js"></script>
	<script src="../plugins/newJs/plugins/forms/icheck/jquery.icheck.js"></script>
	<script
		src="../plugins/newJs/plugins/forms/tags/jquery.tagsinput.min.js"></script>
	<script src="../plugins/newJs/plugins/forms/tinymce/tinymce.min.js"></script>
	<script src="../plugins/newJs/plugins/misc/highlight/highlight.pack.js"></script>
	<script src="../plugins/newJs/plugins/misc/countTo/jquery.countTo.js"></script>
	<script src="../plugins/newJs/plugins/ui/weather/skyicons.js"></script>
	<script src="../plugins/newJs/plugins/ui/notify/jquery.gritter.js"></script>
	<script src="../plugins/newJs/plugins/ui/calendar/fullcalendar.js"></script>
	<script src="../plugins/newJs/js/jquery.sprFlat.js"></script>
	<script src="../plugins/newJs/js/app.js"></script>
	<script src="../plugins/echarts3/echarts.min.js"></script>
	<script src="../js/heatMap2.js"></script>
	<script src="../js/routeLine.js"></script>
	<script src="../plugins/heatmap.min.js"></script>
	<script src="../plugins/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../js/util.js"></script>
	<script type="text/javascript">
		var passenger = '<spring:message code="passenger"/>';
		var home = '<spring:message code="home"/>';
		var EnteringRate = '<spring:message code="EnteringRate"/>';
		var OverflowRate ='<spring:message code="OverflowRate"/>';
		var DeepRate = '<spring:message code="DeepRate"/>';
		var objColors, colours;
		RouteLine.init();
		$(document).ready(function() {
							$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',function(_result) {
												var a
												var param
												if (remote_ip_info.ret == '1') {
													a = remote_ip_info.city
													param = {
														"name" : a
													};
												} else {
													a = "北京"
													param = {
														"name" : a
													};
												}
												$.ajax({
															"dataType" : 'json',
															"type" : "POST",
															"url" : "/sva/market/getWeatherInfo",
															"data" : param,
															"success" : function(data) {
																$("#date_0").html(data.date[0]);
																$("#week_0").html(data.week[0]);
																$("#city").html(data.city[0]);
																$("#temperature").html(data.temperature[0]);
																$("#date_1").html(data.date[1]);
																$("#temperature_1").html(data.temperature[1]);
																$("#week_1").html(data.week[1]);
																$("#date_2").html(data.date[2]);
																$("#temperature_2").html(data.temperature[2]);
																$("#week_2").html(data.week[2]);
																$("#date_3").html(data.date[3]);
																$("#temperature_3").html(data.temperature[3]);
																$("#week_3").html(data.week[3]);
																$("#date_4").html(data.date[4]);
																$("#temperature_4").html(data.temperature[4]);
																$("#week_4").html(data.week[4]);
															}
														});

											});

						});
	</script>
</body>
</html>