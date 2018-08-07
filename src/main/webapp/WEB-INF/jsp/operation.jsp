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
<title><spring:message code="operation" /></title>
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
<link href="../css/style-loading.css" rel="stylesheet" />
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
	background-color: rgba(255, 250, 250, 0.7);
}

.floors {
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

.floors:hover, .floors.active {
	background-color: rgba(255, 250, 250, 0.7);
}
</style>
</head>
<body>
	<!-- Start #header -->
	<div id="header">
		<div class="container-fluid">
			<div class="navbar">
				<div class="navbar-header">
					<a class="navbar-brand" href="../market/getMarketInfo"><span
						class="text-logo" style="margin: 17px 5px 0 5%;height: 35px;color: #ffffff; "><spring:message code="hangyang_title"></spring:message> </span><span class="text-slogan"></span>
					</a>
				</div>
				<nav class="top-nav" role="navigation">
					<ul class="nav navbar-nav pull-left"style="margin-top: 3px">
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
								src="../plugins/newJs/img/avatars/48.jpg"
								alt="${sessionScope.userName}">${sessionScope.userName}
						</a>
							<ul class="dropdown-menu right" role="menu">
								<!--  <li><a href="profile.html"><i class="st-user"></i> Profile</a>
                                    </li>
                                    <li><a href="file.html"><i class="st-cloud"></i> Files</a>
                                    </li>
                                    <li><a href="#"><i class="st-settings"></i> Settings</a>
                                    </li>-->
								<li><a href="../account/logout"><i class="im-exit"></i>
										Logout</a></li>
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
                <li><a 
                    href="<c:url value='/market/getMarketInfo'/>"><spring:message
                            code="mall1"></spring:message><i class="im-office"></i></a></li>
                <li><a  href="<c:url value='/home/floor'/>"><spring:message
                            code="floor1"></spring:message><i class="fa-building"></i></a></li>                           
                <li><a href="<c:url value='/home/shop'/>"><spring:message
                            code="shop1"></spring:message><i class="br-basket"></i></a></li>
                <li><a class="active" href="<c:url value='/home/operation'/>"><spring:message
                            code="operation"></spring:message> <i class="br-stats"></i></a>
                    </li>
                  
				<li><a href="#"><spring:message code="configuration"></spring:message><i
						class="im-paragraph-justify"></i></a>
					<ul class="nav sub">
					<c:if test="${sessionScope.userName== 'admin'}">     
						<li><a href="<c:url value='/home/storeMng'/>"><i
								class="ec-pencil2"></i> <spring:message code="store_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/mapMng'/>"><i
								class="im-checkbox-checked"></i> <spring:message
									code="map_config"></spring:message></a></li>
									</c:if>
						<li><a href="<c:url value='/home/categoryMng'/>"><i
								class="im-wand"></i> <spring:message code="category_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/shopMng'/>"><i
								class="fa-pencil"></i> <spring:message code="shop_config"></spring:message></a></li>
								<c:if test="${sessionScope.userName== 'admin'}">     
						<li><a href="<c:url value='/home/ticketMng'/>"><spring:message
									code="ticket_config"></spring:message> <i class="st-chart"></i>
						</a></li>
                        <li><a href="<c:url value='/home/mixingMng'/>"><spring:message
                                    code="position_config"></spring:message><i class="ec-support"></i>
                        </a></li>	
                        </c:if>					
					</ul></li>
					
			</ul>
			<!-- End #sideNav -->
			<!-- Start .sidebar-panel -->
		<!-- 	<div class="sidebar-panel">
				<h4 class="sidebar-panel-title">
					<i class="im-meter"></i>
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
			</div> -->
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
					<!--<h1 class="page-header">
						<i class="br-stats"></i>
						<spring:message code="operation"></spring:message>
					</h1>
					 Start .bredcrumb -->
					<ul id="crumb" class="breadcrumb">
					</ul>
					<!-- End .breadcrumb -->
				</div>
				<!-- End .page-header -->
			</div>
			<!-- End .row -->
			<div class="outlet" >
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-lg-6 col-md-6">
						<label style="text-align: center; margin-top: 9px;font-size: 13px;"
							class="col-lg-1 col-md-1 control-label"><spring:message
								code="operation_mall"></spring:message></label>
						<div class="col-lg-3 col-md-3">
							<select data-type="mallSelect" class="form-control select2"
								id="mallId">
							</select>
						</div>
						<label style="text-align: center; margin-top: 9px;font-size: 13px;"
							class="col-lg-1 col-md-1 control-label"><spring:message
								code="operation_category"></spring:message></label>
						<div class="col-lg-3 col-md-3">
							<select data-type="categorySelect" class="form-control select2"
								id="categoryId">
							</select>
						</div>
						<label style="text-align: center; margin-top: 9px;font-size: 13px;"
							class="col-lg-1 col-md-1 control-label"><spring:message
								code="operation_shop"></spring:message></label>
						<div class="col-lg-3 col-md-3">
							<select data-type="shopSelect" class="form-control select2"
								id="shopId">
							</select>
						</div>
					</div>
					<div class="col-lg-6 col-md-6">
						<label
							style="margin-top: 9px;font-size: 13px;"
							class="col-lg-2 col-md-2 control-label"><spring:message
								code="operation_datePicker"></spring:message></label>
						<div class="col-lg-4 col-md-4" style="margin-top: 6px;">
							<input class="sang_Calender " style="width: 60%;"
								id="select_time_begin_tab1" onclick="getDatePicker(this)"
								readonly /> <img
								src="../plugins/wDatePicker/skin/datePicker.gif"
								onclick="getDatePicker('select_time_begin_tab1');">~
						</div>
						<div id="timeId" class="col-lg-4 col-md-4 myhide"
							style="margin-top: 6px;">
							<input class="sang_Calender" style="width: 60%;"
								id="select_time_end_tab1" onclick="getDatePicker1(this)"
								readonly /> <img
								src="../plugins/My97DatePicker/skin/datePicker.gif"
								onclick="getDatePicker1('select_time_end_tab1');">
						</div>
						<div id="confirmId" style="margin-top: 4px;" class="col-lg-2 col-md-2 myhide">
							<button class="btn btn-success" id="heatMapConfirm" style="padding-top:2%;padding-bottom:2%;">
								<spring:message code="store_confirm"></spring:message>
							</button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 col-md-12 left-css">
						<!-- Start col-lg-6 -->
						<div class="panel-info toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title"style="font-size: 15px;color: white;">
									<i class="fa-female"></i>
									<spring:message code="operation_gender"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon1" style="text-align: center;">
									<div id="genderCharts" style="height: 400px;"></div>
                                <h1  id="loadinggenderCharts"class="loadingtext" style="display:none;margin-top: -210px;position: absolute;margin-left: 40%;">
                                  <span>加</span>
                                  <span>载</span>
                                  <span>中</span>
                                  <span>请</span>
                                  <span>稍</span>
                                  <span>后</span>
                                  <span>...</span>
                                </h1>									
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					</div>
					<div class="row">
					<div class="col-lg-12 col-md-12 left-css">
						<!-- Start col-lg-6 -->
						<div class="panel-success toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title"style="font-size: 15px;color: white;">
									<i class="en-pie"></i>
									<spring:message code="operation_age"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon2" style="text-align: center;">
									<div id="ageCharts" style="height: 400px;"></div>
                                <h1  id="loadingageCharts"class="loadingtext" style="display:none;margin-top: -210px;position: absolute;margin-left: 40%;">
                                  <span>加</span>
                                  <span>载</span>
                                  <span>中</span>
                                  <span>请</span>
                                  <span>稍</span>
                                  <span>后</span>
                                  <span>...</span>
                                </h1>									
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12 col-md-12 left-css">
						<!-- Start col-lg-6 -->
						<div class="panel-primary toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title"style="font-size: 15px;color: white;">
									<i class="fa-suitcase"></i>
									<spring:message code="operation_localAddress"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon3" style="text-align: center;">
									<div id="localAddressCharts" style="height: 400px;"></div>
                                <h1  id="loadinglocalAddressCharts"class="loadingtext" style="display:none;margin-top: -210px;position: absolute;margin-left: 40%;">
                                  <span>加</span>
                                  <span>载</span>
                                  <span>中</span>
                                  <span>请</span>
                                  <span>稍</span>
                                  <span>后</span>
                                  <span>...</span>
                                </h1>									
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					</div>
						<div class="row">
					<div class="col-lg-12 col-md-12 left-css">
						<!-- Start col-lg-6 -->
						<div class="panel-danger toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title"style="font-size: 15px;color: white;">
									<i class="fa-plane"></i>
									<spring:message code="operation_homeAddress"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon4" style="text-align: center;">
									<div id="homeAddressCharts" style="height: 400px;"></div>
                                <h1  id="loadinghomeAddressCharts"class="loadingtext" style="display:none;margin-top: -210px;position: absolute;margin-left: 40%;">
                                  <span>加</span>
                                  <span>载</span>
                                  <span>中</span>
                                  <span>请</span>
                                  <span>稍</span>
                                  <span>后</span>
                                  <span>...</span>
                                </h1>									
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
				</div>
				
				<div class="row">
					<div class="col-lg-12 col-md-12 left-css">
						<!-- Start col-lg-6 -->
						<div class="panel-success toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title"style="font-size: 15px;color: white;">
									<i class="fa-plane"></i>
									<spring:message code="operation_workAddress"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon5" style="text-align: center;">
									<div id="workAddressCharts" style="height: 400px;"></div>
                                <h1  id="loadingworkAddressCharts"class="loadingtext" style="display:none;margin-top: -210px;position: absolute;margin-left: 40%;">
                                  <span>加</span>
                                  <span>载</span>
                                  <span>中</span>
                                  <span>请</span>
                                  <span>稍</span>
                                  <span>后</span>
                                  <span>...</span>
                                </h1>									
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					</div>
						<div class="row">
					<div class="col-lg-12 col-md-12 left-css">
						<!-- Start col-lg-6 -->
						<div class="panel-info toggle">
							<!-- Start .panel -->
							<div class="panel-heading">
								<h4 class="panel-title"style="font-size: 15px;color: white;">
									<i class="en-pie"></i>
									<spring:message code="operation_ability"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div id="divCon6" style="text-align: center;">
									<div id="abilityCharts" style="height: 400px;"></div>
                                <h1  id="loadingabilityCharts"class="loadingtext" style="display:none;margin-top: -210px;position: absolute;margin-left: 40%;">
                                  <span>加</span>
                                  <span>载</span>
                                  <span>中</span>
                                  <span>请</span>
                                  <span>稍</span>
                                  <span>后</span>
                                  <span>...</span>
                                </h1>									
								</div>
							</div>
						</div>
						<!-- End .panel -->
					</div>
				</div>
			</div>
			<!-- End .outlet -->
		</div>
		<!-- End .content-wrapper -->
		<div class="clearfix"></div>
	</div>
	<div class="modal-dialog" role="document" id="myId"></div>
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
	<script src="../plugins/My97DatePicker/WdatePicker.js"></script>
	<script src="../js/operation.js"></script>
	<script src="../plugins/heatmap.min.js"></script>
	<script type="text/javascript" src="../js/util.js"></script>
	<script type="text/javascript">
		var home = '<spring:message code="home"/>';
		var passenger = '<spring:message code="passenger"/>';
		var operation_all = '<spring:message code="operation_all"/>';
		var operation_count = '<spring:message code="operation_count"/>';
		var objColors, colours;
		/* $(document).ready(function() {
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

		}); */
	</script>
</body>
</html>