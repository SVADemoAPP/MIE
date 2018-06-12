<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<html lang="en">
<head>
<meta charset="utf-8">
<title><spring:message code="shop_config"></spring:message></title>
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
<style type="text/css">
.demo-wrapper {
	position: relative;
}

.icon-question-sign {
	background-position: 16px 16px;
}

div.point {
	position: absolute;
	background-color: #f68484;
	border-radius: 3px;
	width: 6px;
	height: 6px;
}

div.point1 {
	position: absolute;
	background-color: #0CD4F7;
	border-radius: 3px;
	width: 6px;
	height: 6px;
}

div.svgCotainer {
	position: absolute;
}

.preview_size_fake { /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
	height: 1px;
	visibility: hidden;
	overflow: hidden;
	display: none;
}

#mapPloy {
	position: absolute;
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
                    <li class="dropdown">
                        <a style="font-size: 15px;font-weight: bold;" href="/sva/home/changeLocal?local=zh">中文</a> 
                    </li>
                    <li class="dropdown">
                        <a style="font-size: 15px;" href="/sva/home/changeLocal?local=en">English</a>
                    </li>  				
					<li class="dropdown"><a href="#" data-toggle="dropdown"> <img
							class="user-avatar" src="../plugins/newJs/img/avatars/48.jpg"
							alt="${sessionScope.userName}">${sessionScope.userName}
					</a>
						<ul class="dropdown-menu right" role="menu">
                            <li><a href="../account/logout"><i class="im-exit"></i> Logout</a>
							</li>
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
				<!-- <li><a href="../home/user">User<i class="im-screen"></i></a></li>-->
				<li><a class="expand active-state" href="#"><spring:message code="configuration"></spring:message><i
						class="im-paragraph-justify"></i></a>
					<ul class="nav sub show" style="display: block; overflow: hidden;">
						<li><a href="<c:url value='/home/storeMng'/>"><i
								class="ec-pencil2"></i><spring:message code="store_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/mapMng'/>"><i
								class="im-checkbox-checked"></i><spring:message code="map_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/categoryMng'/>"><i
								class="im-wand"></i><spring:message code="category_config"></spring:message></a></li>
						<li><a class="active" href="<c:url value='/home/shopMng'/>"><i
								class="fa-pencil"></i><spring:message code="shop_config"></spring:message></a></li>
						<li><a href="<c:url value='/home/ticketMng'/>"><spring:message code="ticket_config"></spring:message><i class="st-chart"></i>
						</a></li>
                        <li><a href="<c:url value='/home/mixingMng'/>"><spring:message
                                    code="position_config"></spring:message><i class="ec-support"></i>
                        </a></li>						
					</ul></li>
			</ul>
			<!-- End #sideNav -->
			<!-- Start .sidebar-panel -->
                <div class="sidebar-panel">
                    <h4 class="sidebar-panel-title"><i class="im-meter"></i><spring:message code="mall_user"></spring:message></h4>
                    <div class="sidebar-panel-content">
                        <ul class="server-stats">
                            <li>
                                <span class="txt"><spring:message code="mall_diskSpace"></spring:message></span>
                                <span class="percent">${diskspace}</span>
                                <div class="pie-chart" data-percent="${diskspace}"></div>
                            </li>
                        </ul>
                        <ul class="server-stats">
                            <li>
                                <span class="txt"><spring:message code="mall_cpu"></spring:message></span>
                                <span class="percent">${cpu}</span>
                                <div class="pie-chart" data-percent="${cpu}"></div>
                            </li>
                        </ul>
                        <ul class="server-stats">
                            <li>
                                <span class="txt"><spring:message code="mall_memory"></spring:message></span>
                                <span class="percent">${memory}</span>
                                <div class="pie-chart" data-percent="${memory}"></div>
                            </li>
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
						<i class="fa-pencil"></i><spring:message code="shop_config"></spring:message>
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
				<!-- Start .outlet -->
				<div id="editBox" class="myhide">
					<div class="row">
						<div class="col-lg-12 col-md-12">
							<div class="panel panel-success toggle ">
								<!-- Start .panel -->
								<div class="panel-heading">
									<h4 class="panel-title"><spring:message code="shop_config"></spring:message></h4>
								</div>
								<div class="panel-body">
									<form id="formId" action="../shop/api/saveData" method="post">
										<div class="form-group has-success" id="formParams">
											<div class="col-lg-12 col-md-12" style="height:40px;">
												<label
													style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
													class="col-lg-2 col-md-2 control-label"><spring:message code="shop_name"></spring:message></label>
												<div class="col-lg-4 col-md-4">
													<input type="text"
														style="border-color: #71d398; width: 60%; float: left;"
														id="shopNameId" class="form-control" name="shopName"
														placeholder="<spring:message code="shop_name"></spring:message>"> <input type="hidden"
														name="id" id="idid">
												</div>
												<label
													style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
													class="col-lg-2 col-md-2 control-label"><spring:message code="shop_status"></spring:message></label>
												<div class="col-lg-4 col-md-4">
													<select id="statusId" name="status" class="form-control"
														style="border-color: #71d398; width: 60%;">
														<option value="0"><spring:message code="shop_open"></spring:message></option>
														<option value="1"><spring:message code="shop_rest"></spring:message></option>
														<option value="2"><spring:message code="shop_transfer"></spring:message></option>
														<option value="3"><spring:message code="shop_deleted"></spring:message></option>
														<option value="4"><spring:message code="shop_unknown"></spring:message></option>
													</select>
												</div>
											</div>
											<div class="col-lg-12 col-md-12" style="height:40px;">
											<label
												style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
												class="col-lg-2 col-md-2 control-label"><spring:message code="category"></spring:message></label>
											<div class="col-lg-4 col-md-4">
												<select id="categoryId" name="categoryId"
													class="form-control"
													style="border-color: #71d398; width: 60%; float: left;">
												</select>
											</div>
											<label
												style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
												class="col-lg-2 col-md-2 control-label"><spring:message code="shop_select"></spring:message>
												<spring:message code="shop_position"></spring:message></label>
											<div class="col-lg-4 col-md-4" >
												<a data-toggle="modal" data-type="point" href="#myModal1"
													role="button" class="btn btn-default" disabled="disabled"
													style="width: 25%; "><spring:message code="shop_select"></spring:message></a> <a
													id="search" data-toggle="modal" href="#areaModel"
													data-type="areapreview" role="button"
													class="btn btn-default"
													style="width:25%; ">
													<spring:message code="shop_show"></spring:message></a>
											</div>
												</div>
											<div class="col-lg-12 col-md-12 " style="height:40px;">
												<label
													style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
													class="col-lg-2 col-md-2 control-label"><spring:message code="store"></spring:message></label>
												<div class="col-lg-4 col-md-4">
													<select id="storeId" class="form-control"
														style="border-color: #71d398; width: 60%; float: left;">
													</select>
												</div>
												<label
													style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
													class="col-lg-2 col-md-2 control-label"><spring:message code="shop_position"></spring:message>1</label>
												<div class="col-lg-4 col-md-4" >
													<a style="width: 16%; ">x1:</a> <input
														type="text" name="xSpot" maxlength="15"
														style="width: 18%; height: 28px; " id="x1Id"
														onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />
													<a style="width: 16%; margin-left: 2%;">y1:</a> <input
														type="text" name="ySpot" maxlength="15"
														style="width: 18%; height: 28px;" id="y1Id"
														onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />
												</div>

											</div>

											<div class="col-lg-12 col-md-12 " style="height:40px;">

												<label
													style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
													class="col-lg-2 col-md-2 control-label"><spring:message code="shop_floor"></spring:message></label>
												<div class="col-lg-4 col-md-4">
													<select class="form-control" name="mapId"
														style="border-color: #71d398; width: 60%; float: left;"
														id="mapId">
													</select>
												</div>
												<label
													style="color: #71d398; text-align: center; margin-top: 6px; font-size: 16px;"
													class="col-lg-2 col-md-2 control-label"><spring:message code="shop_position"></spring:message>2</label>
												<div class="col-lg-4 col-md-4" >
													<a style="width: 16%;">x2:</a> <input
														type="text" name="x1Spot" maxlength="15"
														style="width: 18%; height: 28px;" id="x2Id"
														onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />
													<a style="width: 16%; margin-left: 2%;">y2:</a> <input
														type="text" name="y1Spot" maxlength="15"
														style="width: 18%; height: 28px;" id="y2Id"
														onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />
												</div>
											</div>
										</div>
										<div class="col-lg-12 col-md-12"
											style="text-align: center; margin-top: 1%;">
											<button style="" type="button" class="btn btn-success"
												id="confirm"><spring:message code="store_confirm"></spring:message></button>
											<button style="" type="button" class="btn btn-success"
												id="cancel"><spring:message code="store_cancle"></spring:message></button>

										</div>
									</form>
								</div>
							</div>
						</div>

					</div>

				</div>
			</div>


			<!-- Page start here ( usual with .row ) -->
			<div class="row">
				<div class="col-lg-12">
					<!-- col-lg-12 start here -->
					<div class="panel panel-default plain toggle ">
						<!-- Start .panel -->
						<div class="panel-heading white-bg">
							<h4 class="panel-title"><spring:message code="shop_config"></spring:message></h4>
						</div>
						<div class="panel-body">
							<div class="row operationBtn">
								<button class="btn btn-success" id="add"><spring:message code="store_add"></spring:message></button>
							</div>
							<table class="table display" id="dataTable">
								<thead>
									<tr>
										<th><spring:message code="shop_name"></spring:message></th>
										<th><spring:message code="category"></spring:message></th>
										<th><spring:message code="store"></spring:message></th>
										<th><spring:message code="shop_floor"></spring:message></th>
										<th><spring:message code="shop_status"></spring:message></th>
										<th>X1(m)</th>
										<th>Y1(m)</th>
										<th>X2(m)</th>
										<th>Y2(m)</th>
										<th><spring:message code="store_updateTime"></spring:message></th>
										<th style="width:160px;"><spring:message code="store_opreration"></spring:message></th>
									</tr>
								</thead>
								<tbody></tbody>
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
	<div class="modal fade" data-backdrop="static" id="myModal1"
		style="display: none; margin-top: 50px;">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<div style="padding-top: 25px;">
						<spring:message code="shop_click_to_choose"></spring:message>
						<button class="btn btn-small clearPaper"
							style="float: right; margin-right: 40px; margin-top: -10px;"><spring:message code="map_clera"></spring:message></button>
					</div>
				</div>

				<div class="modal-body">
					<div id="alertBoxScale" style="display: none;">
						<div class="alert fade in">
							<div id="infoScale"></div>
						</div>
					</div>
					<div id="divCon" style="text-align: center; height: 500px;">
						<div id="preview" class="demo-wrapper"
							style="outline: 1px solid #B3B1B1;"></div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="hide">
						<input id="x0" type="text"> <input id="y0" type="text">
						<input id="x1" type="text"> <input id="y1" type="text">
					</div>
					<button id="Ok" data-dismiss="modal" aria-hidden="true"
						disabled="disabled" class="btn"><spring:message code="shop_ok"></spring:message></button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" data-backdrop="static" id="areaModel"
		style="display: none; margin-top: 50px;">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header"
					style="height: 45px; background: #75b9e6; border-top-left-radius: 4px; border-top-right-radius: 4px;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<div id="alertBoxScale" class="hide">
						<div class="alert fade in">
							<div id="areainfoScale"></div>
						</div>
					</div>
					<div id="areadivCon" style="text-align: center; height: 500px;">
						<div id="areapreview" class="demo-wrapper"
							style="outline: 1px solid #B3B1B1;"></div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="hide"></div>

				</div>
			</div>
		</div>
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

	<script src="../plugins/newJs/js/jquery-ui.js"></script>
	<script src="../plugins/newJs/js/app.js"></script>
	<script src="<c:url value='/plugins/raphael-min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/util.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/config/mapEdit.js'/>"
		type="text/javascript"></script>

	<script src="<c:url value='/js/config/shopConfig.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript">
		var home = '<spring:message code="home"/>';
		var iedit = '<spring:message code="store_edt"/>',
			idelete = '<spring:message code="store_delete"/>',
			notnull = '<spring:message code="shop_not_null"/>',
			exist =  '<spring:message code="shop_exists"/>',
			shop_pass = '<spring:message code="shop_pass"/>',
	        table_language = '<spring:message code="common_table_lang"/>',
	       isearch = '<spring:message code="mall_search"/>',
	       ientries = '<spring:message code="mall_entries"/>';

		$(document).ready(function() {
			ShopConfig.initSelect();
			ShopConfig.bindClickEvent();
			ShopConfig.initTable();
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