<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html lang="en">
<head>
<meta charset="utf-8">
<title><spring:message code="position_config"></spring:message></title>
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
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400" rel="stylesheet" type="text/css" />
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:700" rel="stylesheet" type="text/css" />
    <link href="http://fonts.googleapis.com/css?family=Droid+Sans:400" rel="stylesheet" type="text/css" />
    <link href="http://fonts.googleapis.com/css?family=Droid+Sans:700" rel="stylesheet" type="text/css" />
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
</head>
<body>
	<!-- Start #header -->
	<div id="header">
		<div class="container-fluid">
			<div class="navbar">
				<div class="navbar-header">
					<a class="navbar-brand" href="../market/getMarketInfo"> <img
						src="../images/svalogo.png"
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
					<li class="dropdown"><a href="#" data-toggle="dropdown"> <img
							class="user-avatar" src="../plugins/newJs/img/avatars/48.jpg"
							alt="${sessionScope.userName}">${sessionScope.userName}
					</a>
						<ul class="dropdown-menu right" role="menu">
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
                <li><a 
                    href="<c:url value='/market/getMarketInfo'/>"><spring:message
                            code="mall1"></spring:message><i class="im-office"></i></a></li>
                <li><a  href="<c:url value='/home/floor'/>"><spring:message
                            code="floor1"></spring:message><i class="fa-building"></i></a></li>                           
                <li><a href="<c:url value='/home/shop'/>"><spring:message
                            code="shop1"></spring:message><i class="br-basket"></i></a></li>
                <li><a href="<c:url value='/home/operation'/>"><spring:message
                            code="operation"></spring:message> <i class="br-stats"></i></a>
                    </li>
				<!-- <li><a href="../home/user">User<i class="im-screen"></i></a>
                    </li>-->
				<li class="hasSub"><a class="active-state expand" href="#"><spring:message
							code="configuration"></spring:message><i
						class="im-paragraph-justify"></i></a>
					<ul class="nav sub show">
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
						<li><a  href="<c:url value='/home/ticketMng'/>"><spring:message
									code="ticket_config"></spring:message><i class="st-chart"></i></a>
						</li>
                        <li><a class="active" href="<c:url value='/home/mixingMng'/>"><spring:message
                                    code="position_config"></spring:message><i class="ec-support"></i>
                        </a></li>						
					</ul></li>
			</ul>
			<!-- End #sideNav -->
			<!-- Start .sidebar-panel -->
			<div class="sidebar-panel">
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
						<i class="ec-support"></i>
						<spring:message code="position_config"></spring:message>
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
				<div id="editBox" class="myhide">
					<div class="row">
						<div class="col-lg-12 col-md-12">
							<div class="panel panel-success toggle  ">
								<!-- Start .panel -->
								<div class="panel-heading">
									<h4 class="panel-title">
										<spring:message code="position_config"></spring:message>
									</h4>
								</div>
								<div class="panel-body">
									<form action="/sva/mixing/api/saveData" method="post"
										enctype="multipart/form-data">
										<div class="form-group has-success">
											<div class="col-lg-12 col-md-12 " style="height: 40px;">
												<div class="row">
													<div class="col-lg-6 col-md-6">
														<label
															style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
															class="col-lg-3 col-md-3 control-label"><spring:message
																code="mall_name"></spring:message></label>
														<div class="col-lg-8 col-md-8">
															<select id="storeId" name="storeId"
																class="form-control"
																style="border-color: #71d398; width: 55%; float: left">
															</select>
														</div>
													</div>
                                                    <div class="col-lg-6 col-md-6">
                                                        <label
                                                            style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
                                                            class="col-lg-3 col-md-3 control-label"><spring:message
                                                                code="position_store"></spring:message></label>
                                                        <div class="col-lg-8 col-md-8">
                                                            <input id="storeCodeId" name="storeCode" type="text"
                                                                style="border-color: #71d398; width: 50%; float: left"
                                                                class="form-control"
                                                                onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')">
                                                        </div>
                                                    </div>
												</div>
											</div>
											<div class="col-lg-12 col-md-12 " style="height: 40px;">
												<div class="row">
													<div class="col-lg-6 col-md-6">
														<label
															style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
															class="col-lg-3 col-md-3 control-label"><spring:message
																code="map_floor"></spring:message></label>
														<div class="col-lg-8 col-md-8">
															<select id="floorId" name="mapId"
																class="form-control"
																style="border-color: #71d398; width: 55%; float: left">
															</select>
														</div>
													</div>
													<div class="col-lg-6 col-md-6">
														<label
															style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
															class="col-lg-3 col-md-3 control-label"><spring:message
																code="position_floor"></spring:message></label>
														<div class="col-lg-8 col-md-8">
															<input id="floorCodeId" name="floorCode" type="text"
																style="border-color: #71d398; width: 50%; float: left"
																class="form-control"
																onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')">
														</div>
													</div>
												</div>
											</div>
											<div class="col-lg-12 col-md-12 " style="height: 40px;">
												<div class="col-lg-6 col-md-6">
													<div class="myhide">
														<input id="idid" name="id">
													</div>
												</div>
											</div>
											<div class="col-lg-12 col-md-12"
												style="text-align: center; margin-top: 1%;">
												<button style="" type="submit" id="Confirm"
													class="btn btn-success">
													<spring:message code="store_confirm"></spring:message>
												</button>
												<button style="" type="button" id="Cancel"
													class="btn btn-success">
													<spring:message code="store_cancle"></spring:message>
												</button>
											</div>
										</div>
									</form>
								</div>
							</div>

						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<!-- col-lg-12 start here -->
						<div class="panel panel-default plain toggle ">
							<!-- Start .panel -->
							<div class="panel-heading white-bg">
								<h4 class="panel-title">
									<spring:message code="position_config"></spring:message>
								</h4>
							</div>
							<div class="panel-body">
								<div class="row operationBtn">
									<button class="btn btn-success" id="add">
										<spring:message code="store_add"></spring:message>
									</button>
								</div>
								<table class="table display" id="table">
									<thead>
										<tr>
											<th></th>
											<th><spring:message code="mall_name"></spring:message></th>
											<th><spring:message code="map_floor"></spring:message></th>
											<th><spring:message code="position_store"></spring:message></th>
											<th><spring:message code="position_floor"></spring:message></th>
											<th><spring:message code="store_createTime"></spring:message></th>
											<th><spring:message code="store_opreration"></spring:message></th>
										</tr>
									</thead>

								</table>
							</div>
						</div>
						<!-- End .panel -->
					</div>
					<!-- col-lg-12 end here -->
				</div>
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
	<script src="../plugins/newJs/plugins/core/moment/moment.min.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/sparklines/jquery.sparkline.js"></script>
	<script
		src="../plugins/newJs/plugins/charts/pie-chart/jquery.easy-pie-chart.js"></script>
	<script src="../plugins/newJs/plugins/forms/icheck/jquery.icheck.js"></script>
	<script
		src="../plugins/newJs/plugins/forms/tags/jquery.tagsinput.min.js"></script>
	<script src="../plugins/newJs/plugins/forms/tinymce/tinymce.min.js"></script>
	<script
		src="../plugins/newJs/plugins/tables/datatables/jquery.dataTables.min.js"></script>
	<script
		src="../plugins/newJs/plugins/tables/datatables/jquery.dataTablesBS3.js"></script>
	<script
		src="../plugins/newJs/plugins/tables/datatables/tabletools/ZeroClipboard.js"></script>
	<script
		src="../plugins/newJs/plugins/tables/datatables/tabletools/TableTools.js"></script>
	<script src="../plugins/newJs/plugins/misc/highlight/highlight.pack.js"></script>
	<script src="../plugins/newJs/plugins/misc/countTo/jquery.countTo.js"></script>
	<script src="../plugins/newJs/js/jquery.sprFlat.js"></script>
	<script src="../plugins/newJs/js/app.js"></script>
	<script src="../plugins/newJs/js/jquery-ui.js"></script>
	<script src="<c:url value='/js/config/mixingConfig.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/util.js'/>" type="text/javascript"></script>
	<script type="text/javascript">
		var home = '<spring:message code="home"/>',
	        table_language = '<spring:message code="common_table_lang"/>';
		var iedit = '<spring:message code="store_edt"/>', idelete = '<spring:message code="store_delete"/>', inotnull = '<spring:message code="map_not_null"/>', ipass = '<spring:message code="ticket_pass"/>', inotgl = '<spring:message code="ticket_p100"/>', isearch = '<spring:message code="mall_search"/>', ientries = '<spring:message code="mall_entries"/>', ipathf = '<spring:message code="ticket_pathformat"/>';

		var oTable;
		$(document).ready(function() {
			Ticket.initTable();
			Ticket.initSelect();
			Ticket.bindClickEvent();
			initPieChart();
		});
		var initPieChart = function() {
			$(".pie-chart").easyPieChart({
				barColor : '#5a5e63',
				borderColor : '#5a5e63',
				trackColor : '#d9dde2',
				scaleColor : false,
				lineCap : 'butt',
				lineWidth : 10,
				size : 40,
				animate : 1500
			});
		}
	</script>
</body>
</html>