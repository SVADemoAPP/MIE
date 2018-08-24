var thisData;
var allMapData;
function deleteData(id) {
	ShopConfig.deleteTable(id);
};
function editData(arr) {
	$("i[data-type='tip']").remove();
	var arr = arr.split(",");
	$("#editBox").show();
	$("#idid").val(arr[0]);
	$("#shopNameId").val(arr[1]);
	$("#categoryId").val(arr[2]);
	$("#storeId").val(arr[3]);
	// $("#mapId").val(arr[4]);
	$("#statusId").val(arr[5]);
//	$("#x1Id").val(arr[6]);
//	$("#y1Id").val(arr[7]);
//	$("#x2Id").val(arr[8]);
//	$("#y2Id").val(arr[9]);
//	$("#pointsArray").val(arr[6]);
	ShopConfig.setMapDataByStore(arr[3], arr[4]);
	ShopConfig.initPreview(arr[4]);
	// previewImage();
	$.post("/sva/shop/api/getPointsArrayById", {
		id : arr[0]
	}, function(data) {
		$("#pointsArray").val(data);
	});
};
function setPreview(select) {
	if (select != "") {
		ShopConfig.initPreview(select);
	} else {
		$('a[href="#myModal1"]').attr("disabled", "disabled");
//		 $("#search").hide();
	}
};

function addShop() {
	// $(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").show();
	$(".sameInfo").removeClass("Validform_wrong");
	$(".sameInfo").text("");
};
function hideAdd() {
	// $(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
};

function clearinfo() {
	$("i[data-type='tip']").remove();
	$("#shopNameId").val("");
	$("#categoryId").val("");
	$("#storeId").val("");
	$("#mapId").val("");
	$("#statusId").val("");
//	$("#x1Id").val("");
//	$("#y1Id").val("");
//	$("#x2Id").val("");
//	$("#y2Id").val("");
	$("#pointsArray").val("");
	$("#idid").val("");
	$('a[href="#myModal1"]').attr("disabled", "disabled");
//	 $("#search").hide();
	$("#formParams i").remove();
};

var ShopConfig = function() {
	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, callback, selectTxt) {
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '<option value=""></option>';
		for (var i = 0; i < len; i++) {
			var info = sortData[i];
			if (selectTxt == info.name) {
				options += '<option class="addoption" selected=true value="'
						+ info.id + '">' + HtmlDecode3(info.name) + '</option>';
			} else {
				options += '<option class="addoption" value="' + info.id + '">'
						+ HtmlDecode3(info.name) + '</option>';
			}
		}
		removeOption(renderId);
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	var clacImgZoomParam = function(maxWidth, maxHeight, width, height, x, y,
			coordinate) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height,
			x : x,
			y : y,
			coordinate : coordinate
		};
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		} else {
			param.width = Math.round(width / rateHeight);
			param.height = maxHeight;
		}

		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		param.zoomScale = width / param.width;
		return param;
	};
	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateMapList = function(renderId, data, select) {
		$('#' + renderId).children().remove();
		var sortData = data.sort(function(a, b) {
			return a.floor - b.floor;
		});
		var len = sortData.length;
		var options = '<option value=""></option>';
		for (var i = 0; i < len; i++) {
			var info = sortData[i];
			if (select == info.mapId) {
				options += '<option class="addoption" selected=true value="'
						+ info.mapId + '">' + HtmlDecode3(info.floor)
						+ '</option>';
			} else {
				options += '<option class="addoption" value="' + info.mapId
						+ '">' + HtmlDecode3(info.floor) + '</option>';
			}
		}
		removeOption(renderId);
		$('#' + renderId).append(options);
		return;
	};
	var getMapDataByMapId = function(mapId) {
		for (i = 0; i < allMapData.length; i++) {
			if (allMapData[i].mapId == mapId) {
				return allMapData[i];
			}
		}
		return "";
	};
	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	var deleteTableData = function(targetId) {
		var a = confirm(confirm_delete);
		if (a == true) {
			$.post("../shop/api/deleteData", {
				id : targetId
			}, function(data) {
				if (data.error) {
					for (i = 0; i < thisData.length; i++) {
						if (thisData[i].id == targetId) {
							thisData.splice(i, 1);
							refreshData(thisData);
						}
					}
				}
			});
		}
	};
	var refreshData = function(dataList) {
		$('#dataTable').dataTable().fnDestroy();
		var oTable = $('#dataTable')
				.dataTable(
						{
							"sPaginationType" : "bs_full", // "bs_normal",
							// "bs_two_button",
							// "bs_four_button",
							// "bs_full"
							"fnPreDrawCallback" : function(oSettings) {
								$('.dataTables_filter input').addClass(
										'form-control input-large').attr(
										'placeholder', isearch);
								$('.dataTables_length select').addClass(
										'form-control input-small');
							},
							"oLanguage" : {
								"sSearch" : "",
								"sLengthMenu" : "<span>_MENU_ " + ientries
										+ "</span>"
							},
							"oLanguage" : {
								"sUrl" : table_language
							},
							"bJQueryUI" : false,
							"bAutoWidth" : false,
							"sDom" : "<'row'<'col-lg-6 col-md-6 col-sm-12 text-center'l><'col-lg-6 col-md-6 col-sm-12 text-center'f>r>t<'row-'<'col-lg-6 col-md-6 col-sm-12'i><'col-lg-6 col-md-6 col-sm-12'p>>",
							"bProcessing" : true,
							"aaData" : dataList,
							"bStateSave" : true,
							"aaSorting" : [ [ 11, "desc" ] ],
							"aoColumnDefs" : [
									{
										"aTargets" : [ 0 ],
										"mData" : "shopName",
									},
									{
										"aTargets" : [ 1 ],
										"mData" : "categoryName",
									},
									{
										"aTargets" : [ 2 ],
										"mData" : "storeName",
										"mRender" : function(data, type, full) {
											if (data == null) {
												return "";
											}
											if (data.length > 40) {
												var html = data
														.substring(0, 40)
														+ "...";
												html = '<span title="'
														+ HtmlDecode3(data)
														+ '">'
														+ HtmlDecode3(html)
														+ '</span>';
												return html;
											}
											return HtmlDecode3(data);
										}
									},
									{
										"aTargets" : [ 3 ],
										"mData" : "floorName",
									},
									{
										"aTargets" : [ 4 ],
										"mData" : "status",
										"mRender" : function(data, type, full) {
											switch (full.status) {
											case '0':
												return 'open';
												;
												break;
											case '1':
												return 'rest';
												break;
											case '2':
												return 'transfer';
												break;
											case '3':
												return 'deleted';
												break;
											default:
												return 'unknown';
											}
										}
									},
									{
										"aTargets" : [ 5 ],
										"mData" : "pointsArray",
									},
									{
										"aTargets" : [ 6 ],
										"mData" : "updateTime",
										"mRender" : function(data, type, full) {
											var date = new Date();
											date.setTime(data);
											return dateFormat(date,
													"yyyy/MM/dd HH:mm:ss");
										}
									},
									{
										"aTargets" : [ 7 ],
										"mData" : "id",
										"bSearchable" : false,
										"bSortable" : false,
										"bFilter" : false,
										"mRender" : function(data, type, full) {
											var arr = [];
											arr.push(full.id);
											arr.push(full.shopName);
											arr.push(full.categoryId);
											arr.push(full.storeId);
											arr.push(full.mapId);
											arr.push(full.statusId);
//											arr.push(full.xSpot);
//											arr.push(full.ySpot);
//											arr.push(full.x1Spot);
//											arr.push(full.y1Spot);
//											arr.push(full.pointsArray);
											return '<button class="btn btn-warning" style="width:60px;" onclick="editData(\''
													+ arr
													+ '\')" />'
													+ iedit
													+ '</button><button class="btn btn-danger"  style="width:60px;margin-left:2px;" onclick="deleteData(\''
													+ full.id
													+ '\')">'
													+ idelete + '</button>';
										}
									}, {
										"aTargets" : [ 8 ],
										"mData" : "createTime",
										"bSearchable" : false,
										"bVisible" : false

									} ]

						});
	}

	var refreshTable = function() {
		$.post("../shop/getShopInfo", function(data) {
			if (data.error == 200) {
				thisData = data.data;
				refreshData(thisData);
			}
		});
	};

	return {
		initSelect : function() {
			$.post("../market/api/getData?t=" + Math.random(), function(data) {
				if (data.data.length > 0) {
					updateList("storeId", data.data);
				} else {
					// alert("无商场数据！");
				}
			});

			$.post("../category/api/doquery", function(data) {
				if (data.data.length > 0) {
					updateList("categoryId", data.data);
				} else {
					// alert("无类别数据！");
				}
			});
			$.post("../map/api/getAllTableData", function(data) {
				allMapData = data.data;
				if (data.data.length > 0) {
					updateMapList("mapId", data.data);
				} else {
					// alert("无类别数据！");
					updateMapList("mapId", []);
				}
			});
		},
		initTable : function() {
			refreshTable();
		},
		deleteTable : function(id) {
			deleteTableData(id);
		},
		initPreview : function(select) {
			var selectedOpt = getMapDataByMapId(select);
			// var opts = $("#mapId option");
			// var selectedOpt = opts[$(this)[0].selectedIndex];
			var width = selectedOpt.imgWidth, height = selectedOpt.imgHeight, path = selectedOpt.path, scale = selectedOpt.scale;
			coordinate = selectedOpt.coordinate;
			x = selectedOpt.xo;
			y = selectedOpt.yo;
			var MAXWIDTH = 868;
			var MAXHEIGHT = 500;
			rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, width, height, x, y,
					coordinate);
			rect.scale = scale;
			$("#preview").empty();
			$("#preview").css({
				"width" : rect.width + "px",
				"height" : rect.height + "px",
				"x" : rect.x + "px",
				"y" : rect.y + "px",
				"coordinate" : rect.coordinate,
				"margin-left" : rect.left + 'px',
				"margin-top" : rect.top + 'px',
				"background-image" : "url(../upload/map/" + path + ")",
				"background-size" : "cover",
				"-moz-background-size" : "cover"
			});
			$("#areapreview").empty();
			$("#areapreview").css({
				"width" : rect.width + "px",
				"height" : rect.height + "px",
				"x" : rect.x + "px",
				"y" : rect.y + "px",
				"coordinate" : rect.coordinate,
				"margin-left" : rect.left + 'px',
				"margin-top" : rect.top + 'px',
				"background-image" : "url(../upload/map/" + path + ")",
				"background-size" : "cover",
				"-moz-background-size" : "cover"
			});
			$('a[href="#myModal1"]').attr("disabled", false);
//			 $("#search").show();

		},
		setMapDataByStore : function(sid, mapId) {
			$.post("../map/api/getMapDataByStore", {
				id : sid
			}, function(data) {
				if (data.data.length > 0) {
					updateMapList("mapId", data.data, mapId);
				} else {
					// alert("无类别数据！");
					updateMapList("mapId", []);
				}
			});
		},
		bindClickEvent : function() {
			$("#add").on("click", function(e) {
				updateMapList("mapId", allMapData);
				addShop();
			});
			$("#cancel").on("click", function(e) {

				hideAdd();
			});
			$("a[data-type='point']").on(
					"click",
					function(e) {
						$("#Ok").attr("disabled", "disabled");
						if (typeof ($(this).attr("disabled")) != "undefined") {
							e.preventDefault();
							return false;
						}
						Ploy.clearPaper();
						isDuobianxing=false;
						$(".duobianxing").css("background-color","white");
						$(".duobianxing").html(map_duobianxing);
						pointsArray=[];
//						Ploy.clearPaper1();
						// $("#pointY2").val("");
						$("#pointsArray").blur();
//						$("#y1Id").blur();
//						$("#x2Id").blur();
//						$("#y2Id").blur();
						
//						if ($("#x1Id").val() != "" && $("#y1Id").val() != ""
//								&& $("#x2Id").val() != ""
//								&& $("#y2Id").val() != "") {
////							Ploy.initPaper('#preview');
//							AreaMakeRect($("#x1Id").val(), $(
//									"#y1Id").val(), $("#x2Id").val(),
//									$("#y2Id").val());
//						}
						setTimeout(function(){
							var tempArray=JSON.parse($("#pointsArray").val());
							//校验位置字符串格式
							var tempLen=tempArray.length;
							if(tempLen&&tempLen>2){
								var myPoints=[];
								var coordinate = rect.coordinate;
								var scale = rect.scale;
								var imagex = rect.width;
								var imagey = rect.height;
								var rectx=rect.x;
								var recty=rect.y;
								var zoomScale=rect.zoomScale;
								var leftX= $('#preview').offset().left;
								var topY=$('#preview').offset().top;
								var px,py;
								for(i=0;i<tempLen;i++){
									var tempO=tempArray[i];
									px = ((parseFloat(tempO.x) + parseFloat(rectx)) * parseFloat(scale) / zoomScale)
									.toFixed(2);
									py = ((parseFloat(tempO.y) + parseFloat(recty)) * parseFloat(scale) / zoomScale)
									.toFixed(2);
									switch (coordinate) {
									case "ul":
										break;
									case "ll":
										py = imagey - py;
										break;
									case "ur":
										px = imagex - px;
										break;
									case "lr":
										px = imagex - px;
										py = imagey - py;
									}
									myPoints.push({left:parseFloat(px)+leftX,top:parseFloat(py)+topY});
								}
								Ploy.addOnePoint('#preview', myPoints[0]);
								for(j=1,len=myPoints.length;j<len;j++){
									Ploy.makePoly('#preview', myPoints[j]);
								}
								Ploy.makePoly('#preview', myPoints[0]);
							}
						},500);
					});
//			$("a[data-type='areapreview']").on(
//					"click",
//					function(e) {
////						$("#Ok").attr("disabled", "disabled");
//						if (typeof ($(this).attr("disabled")) != "undefined") {
//							e.preventDefault();
//							return false;
//						}
////						Ploy.clearPaper();
//						Ploy.clearPaper1();
////						pointsArray=[];
//						// $("#pointY2").val("");
////						$("#x1Id").blur();
////						$("#y1Id").blur();
////						$("#x2Id").blur();
////						$("#y2Id").blur();
//						setTimeout(function(){
//							var tempArray=JSON.parse($("#pointsArray").val());
//							//校验位置字符串格式
//							var tempLen=tempArray.length;
//							if(tempLen&&tempLen>2){
//								var myPoints=[];
//								var coordinate = rect.coordinate;
//								var scale = rect.scale;
//								var imagex = rect.width;
//								var imagey = rect.height;
//								var rectx=rect.x;
//								var recty=rect.y;
//								var zoomScale=rect.zoomScale;
//								var leftX= $('#areapreview').offset().left;
//								var topY=$('#areapreview').offset().top;
//								var px,py;
//								for(i=0;i<tempLen;i++){
//									var tempO=tempArray[i];
//									px = ((parseFloat(tempO.x) + parseFloat(rectx)) * parseFloat(scale) / zoomScale)
//									.toFixed(2);
//									py = ((parseFloat(tempO.y) + parseFloat(recty)) * parseFloat(scale) / zoomScale)
//									.toFixed(2);
//									switch (coordinate) {
//									case "ul":
//										break;
//									case "ll":
//										py = imagey - py;
//										break;
//									case "ur":
//										px = imagex - px;
//										break;
//									case "lr":
//										px = imagex - px;
//										py = imagey - py;
//									}
//									myPoints.push({left:parseFloat(px)+leftX,top:parseFloat(py)+topY});
//								}
//								Ploy.addOnePoint1('#areapreview', myPoints[0]);
//								for(j=1,len=myPoints.length;j<len;j++){
//									Ploy.makePoly1('#areapreview', myPoints[j]);
//								}
//								
//							}else{
//								alert(format_erros);
//							}
//						},1000);
//						
//					});
			$('#preview').click(function(e) {

				var left = e.pageX;
				var top = e.pageY;
				var o = {
					left : left,
					top : top
				};
				var len=pointsArray.length;
				if(isDuobianxing){
				
				if(len==0){
					Ploy.clearPaper();
					Ploy.addOnePoint('#preview', o);
					pointsArray.push(o);
				}else if(len>15){
					alert(points_over);
				}else{
//					Ploy.addOnePoint('#preview', o);
					if(len>1){
						$("#Ok").attr("disabled", false);
					}
					Ploy.makePoly('#preview',o);
					pointsArray.push(o);
				}
				}else{
					if(len==0){
						Ploy.clearPaper();
						Ploy.addOnePoint('#preview', o);
						pointsArray.push(o);
					}else if(len==1){
					    var o1=pointsArray[0];
					    var o3=o;
					    var o2={
								left : o1.left,
								top : o3.top
							};
					    var o4={
								left : o3.left,
								top : o1.top
							};
					    Ploy.makeRect('#preview', o3);
					    pointsArray.push(o2);
					    pointsArray.push(o3);
					    pointsArray.push(o4);
					    $("#Ok").attr("disabled", false);
					}
				}
				
				
				
//				var datas = Ploy.getData();
//				if (datas.length < 2) {
//					Ploy.makeRect('#preview', o);
//					// Ploy.addPoint(top,left);
//					var t = top - $('#preview').offset().top;
//					var l = left - $('#preview').offset().left;
//					if (datas.length < 1) {
//						$("#x0").val(l);
//						$("#y0").val(t);
//					} else {
//						$("#x1").val(l);
//						$("#y1").val(t);
//						$("#Ok").attr("disabled", false);
//					}
//				}

			});
			$(".clearPaper").on("click", function(e) {
				Ploy.clearPaper();
				pointsArray=[];
				$("#Ok").attr("disabled", "disabled");
				$("#alertBoxScale").hide();
//				$("#x0").val("");
//				$("#y0").val("");
//				$("#x1").val("");
//				$("#y1").val("");
//				$("#pointsArray").val("");
				// $("#Ok").attr("disabled","disabled");
			});
			$(".duobianxing").on("click", function(e) {
				if(isDuobianxing){
					isDuobianxing=false;
					$(".duobianxing").css("background-color","white");
					$(".duobianxing").html(map_duobianxing);
				}else{
					isDuobianxing=true;
					$(".duobianxing").css("background-color","red");
					$(".duobianxing").html(map_changfangxing);
				}
				Ploy.clearPaper();
				pointsArray=[];
				$("#Ok").attr("disabled", "disabled");
				$("#alertBoxScale").hide();
			});


			$("#Ok")
					.on(
							"click",
							function(e) {
								if(isDuobianxing){
								Ploy.makePoly('#preview',pointsArray[0]);
								}
								var myArray=[];
								var leftX=$('#preview').offset().left;
								var topY=$('#preview').offset().top;
								var imagex=rect.width;
								var imagey=rect.height;
								var rectx=rect.x;
								var recty=rect.y;
								var scale = rect.scale;
								var zoomScale=rect.zoomScale;
								var coordinate = rect.coordinate;
								for(i=0,len=pointsArray.length;i<len;i++){
									var tempO=pointsArray[i];
									var px=tempO.left-leftX;
									var py=tempO.top-topY;
									switch (coordinate) {
									case "ul":
										break;
									case "ll":
										py = imagey - py;
										break;
									case "ur":
										px = imagex - px;
										break;
									case "lr":
										px = imagex - px;
										py = imagey - py;
										break;
									}
									myArray.push({x:((parseFloat(px) * zoomScale)
											/ parseFloat(scale) - parseFloat(rectx))
											.toFixed(2),y:((parseFloat(py) * zoomScale)
													/ parseFloat(scale) - parseFloat(recty))
													.toFixed(2)});
								}
								$("#pointsArray").val(JSON.stringify(myArray));
								$("#myModal1").modal('hide');
								$("#alertBoxScale").hide();
							});
			function AreaMakeRect(x, y, x1, y1) {
				// var width=document.getElementById("preview").style.width;
				// var height=document.getElementById("preview").style.height;
				// 通过得到的实际米数计算出象数
				// $("#pointX1").val(((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
				var scale = rect.scale;
				px = ((parseFloat(x) + parseFloat(rect.x)) * parseFloat(scale) / rect.zoomScale)
						.toFixed(2);
				py = ((parseFloat(y) + parseFloat(rect.y)) * parseFloat(scale) / rect.zoomScale)
						.toFixed(2);
				px1 = ((parseFloat(x1) + parseFloat(rect.x))
						* parseFloat(scale) / rect.zoomScale).toFixed(2);
				py1 = ((parseFloat(y1) + parseFloat(rect.y))
						* parseFloat(scale) / rect.zoomScale).toFixed(2);
				var coordinate = rect.coordinate;
				switch (coordinate) {
				case "ul":

					break;
				case "ll":
					imagey = rect.height;
					px = px;
					py = imagey - py;
					px1 = px1;
					if (px1) {
						py1 = imagey - py1;
					} else {
						py1 = py1;
					}
					break;
				case "ur":
					imagex = rect.width;
					px = imagex - px;
					py = py;
					py1 = py1;
					if (py1) {
						px1 = imagex - px1;
					} else {
						px1 = px1;
					}

					break;
				case "lr":
					imagex = rect.width;
					imagey = rect.height;
					var x1test = px1;
					var x2test = py1;
					if (x1test) {
						px = imagex - px;
						py = imagey - py;
					} else {
						px = px;
						py = py;
					}
					if (x2test) {
						px1 = imagex - px1;
						py1 = imagey - py1;
					} else {
						px1 = py1;
						py1 = py1;
					}
				}
//				var datas = Ploy.getData1();
//				if (datas.length < 2) {
				
				Ploy.drawRect(parseFloat(px+$('#areapreview').offset().left),parseFloat(py+$('#areapreview').offset().top),
						parseFloat(px1+$('#areapreview').offset().left),parseFloat(py1+$('#areapreview').offset().top));
//						Ploy.makeRect1('#areapreview', {
//							left : px+$('#areapreview').offset().left,
//							top : py+$('#areapreview').offset().top
//						});
//				}
//				var datas = Ploy.getData1();
//				if (datas.length < 2) {
//						Ploy.makeRect1('#areapreview', {
//							left : px1+$('#areapreview').offset().left,
//							top : py1+$('#areapreview').offset().top
//						});
//				}
//				// 生成路径
//				var path = "M" + (px+$('#areapreview').offset().left) + "," + (py+$('#areapreview').offset().top) + " L" + (px1+$('#areapreview').offset().left) + "," + (py+$('#areapreview').offset().top) + " L"
//						+ (px1+$('#areapreview').offset().left) + "," + (py1+$('#areapreview').offset().top) + " L" + (px+$('#areapreview').offset().left) + "," + (py1+$('#areapreview').offset().top) + " L" + px
//						+ "," + py;
//				 Ploy.paper.clear();
////				 pager.remove();
////
//				Ploy.paper.path(path).attr({
//					stroke : '#1791fc',
//					'stroke-width' : 3,
//					opacity : .7,
//					fill : "none"
//				});
			}

			$("a[data-type='preview']").on(
					"click",
					function(e) {
						if (typeof ($(this).attr("disabled")) != "undefined") {
							e.preventDefault();
							return false;
						}
						Ploy.clearPaper();
//						isDuobianxing=false;
//						$(".duobianxing").css("background-color","white");
						pointsArray=[];
						var mapId = $("#mapId").val();
						$.post("/sva/shop/api/getShopInfoByMapId", {
							mapId : mapId
						}, function(data) {
							if (!data.error) {
								var ploy = Raphael("areapreview", rect.width,
										rect.height);
								Ploy.paper = ploy;
								var areas = data.data

								for (var i = 0; i < data.data.length; i++) {
									// Ploy.paper.clear();
									var x = areas[i].xSpot;
									var y = areas[i].ySpot;
									var x1 = areas[i].x1Spot;
									var y1 = areas[i].y1Spot;
									AreaMakeRect('#areapreview', x, y, x1, y1);

								}

							}
						});

					});
			$("#confirm").on("click", function(e) {
				var pass = true;
				if ($("#shopNameId").val() == "") {
					checkPass(false, notnull, "shopNameId");
					pass = false;
				} else {
					checkPass(true, "", "shopNameId");
				}
				if ($("#categoryId").val() == "") {
					checkPass(false, notnull, "categoryId");
					pass = false;
				} else {
					checkPass(true, "", "categoryId");
				}
				if ($("#storeId").val() == "") {
					checkPass(false, notnull, "storeId");
					pass = false;
				} else {
					checkPass(true, "", "storeId");
				}
				if ($("#mapId").val() == "") {
					checkPass(false, notnull, "mapId");
					pass = false;
				} else {
					checkPass(true, "", "mapId");
				}
				if ($("#pointsArray").val() == "") {
					checkPass(false, notnull, "pointsArray");
					pass = false;
				} else {
					var tempArray=JSON.parse($("#pointsArray").val());
					//校验位置字符串格式
					var tempLen=tempArray.length;
					if(tempLen&&tempLen>2){
						checkPass(true, "", "pointsArray");
					}else{
						checkPass(false, format_error, "pointsArray");
						pass = false;
					}
				}
				$.post("../shop/api/checkName", {
					name : $("#shopNameId").val(),
					id : $("#idid").val(),
					storeId : $("#storeId").val()
				}, function(data) {
					if (data.error) {
						checkPass(false, exist, "shopNameId");
						pass = false;
					}
					if (pass) {
						$("#formId").submit();
					}
				});
			});
			$('#storeId').change(function() {
				var select = $(this).children('option:selected').val();
				$('a[href="#myModal1"]').attr("disabled", "disabled");
//				 $("#search").hide();
				if (select == "") {
					updateMapList("mapId", allMapData);
				} else {
					$.post("../map/api/getMapDataByStore", {
						id : select
					}, function(data) {
						if (data.data.length > 0) {
							updateMapList("mapId", data.data);
						} else {
							// alert("无类别数据！");
							updateMapList("mapId", []);
						}
					});
				}
			});
			$('#mapId').change(function() {
				var select = $(this).children('option:selected').val();
				if (select != "" && $('#storeId').val() == "") {
					$.post("../map/api/getStoreIdByMapId", {
						mapId : select
					}, function(data) {
						if (data.data != null) {
							$('#storeId').val(data.data);
							$.post("../map/api/getMapDataByStore", {
								id : data.data
							}, function(data) {
								if (data.data.length > 0) {
									updateMapList("mapId", data.data, select);
								} else {
									// alert("无类别数据！");
									updateMapList("mapId", []);
								}
							});
						}
					});
				}
				;
				// if ($('#mapId').val() != "") {
				setPreview(select);
				// } else {
				// $('a[href="#myModal1"]').attr("disabled", "disabled");
//				 $("#search").hide();
				// }
//				 if ($("#mapId").val() != "") {
//				 $("#search").show();
//				 } else {
//				 $("#search").hide();
//				 }
			})
		}
	}
}();