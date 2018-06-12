var thisData;
function deleteData(mapId) {
	MapConfig.deleteTable(mapId);
};
function editData(arr) {
	$("i[data-type='tip']").remove();
	var arr = arr.split(",");
	$("#editBox").show();
	$("#idid").val(arr[0]);
	$("#storeId").val(arr[1]);
	$("#floor").val(arr[2]);
	$("#mapId").val(arr[3]);
	$("#angle").val(arr[4]);
	$("#coorSel").val(arr[5]);
	$("#textfield").val(arr[6]);
	$("#textsvg").val(arr[7]);
	$("#scaleId").val(arr[8]);
	$("#xo").val(arr[9]);
	$("#yo").val(arr[10]);
	$("#textnavi").val(arr[13]);
	$("#mapType").val(arr[14]);
	// previewImage();
	MapMng.editPreviewImage(arr[6], arr[11], arr[12]);
};
var MapConfig = function() {
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

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	var deleteTableData = function(targetId) {
		var a = confirm("Confirm delete？");
		if (a == true) {
			$.post("../map/api/deleteByFloor", {
				mapId : targetId
			}, function(data) {
				if (data.error) {
					for (i = 0; i < thisData.length; i++) {
						if (thisData[i].mapId == targetId) {
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
		    				"oLanguage":{
		    					"sUrl":table_language
		    				},
							"bJQueryUI" : false,
							"bAutoWidth" : false,
							"sDom" : "<'row'<'col-lg-6 col-md-6 col-sm-12 text-center'l><'col-lg-6 col-md-6 col-sm-12 text-center'f>r>t<'row-'<'col-lg-6 col-md-6 col-sm-12'i><'col-lg-6 col-md-6 col-sm-12'p>>",
							"bProcessing" : true,
							"aaData" : dataList,
							"bStateSave" : true,
							"aaSorting" : [ [ 12, "desc" ] ],
							"aoColumnDefs" : [
									{
										"aTargets" : [ 0 ],
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
										"aTargets" : [ 1 ],
										"mData" : "floor",
									},
									{
										"aTargets" : [ 2 ],
										"mData" : "mapId",
									},
									{
										"aTargets" : [ 3 ],
										"mData" : "xo",
									},
									{
										"aTargets" : [ 4 ],
										"mData" : "yo",
									},
									{
										"aTargets" : [ 5 ],
										"mData" : "scale",
									},
									{
										"aTargets" : [ 6 ],
										"mData" : "coordinate",
										"mRender" : function(data, type, full) {
											switch (full.coordinate) {
											case 'ul':
												return 'upper left';
												break;
											case 'll':
												return 'lower left';
												break;
											case 'ur':
												return 'upper right';
												break;
											case 'lr':
												return 'lower right';
												break;
											default:
												return 'unknown';
											}
										}
									},
									{
										"aTargets" : [ 7 ],
										"mData" : "angle",
									},
									{
										"aTargets" : [ 8 ],
										"mData" : "path",
									},
									{
										"aTargets" : [ 9 ],
										"mData" : "svg",
									},
									{
										"aTargets" : [ 10 ],
										"mData" : "updateTime",
										"mRender" : function(data, type, full) {
											var date = new Date();
											date.setTime(data);
											return dateFormat(date,
													"yyyy/MM/dd HH:mm:ss");
										}
									},
									{
										"aTargets" : [ 11 ],
										"mData" : "id",
										"bSearchable" : false, // 不能搜索
										"bSortable" : false, // 不能排序
										"bFilter" : false, // 不能过滤
										"mRender" : function(data, type, full) {
											var arr = [];
											arr.push(full.id);
											arr.push(full.storeId);
											arr.push(full.floor);
											arr.push(full.mapId);
											arr.push(full.angle);
											arr.push(full.coordinate);
											arr.push(full.path);
											arr.push(full.svg);
											arr.push(full.scale);
											arr.push(full.xo);
											arr.push(full.yo);
											arr.push(full.imgWidth);
											arr.push(full.imgHeight);
											arr.push(full.navi);
											arr.push(full.mapType);
											return '<button class="btn btn-warning" style="width:60px;" onclick="editData(\''
													+ arr
													+ '\')">'
													+ iedit
													+ '</button><button class="btn btn-danger" style="width:60px;margin-left:2px;" onclick="deleteData(\''
													+ full.mapId
													+ '\')">'
													+ idelete + '</button>';
										}
									}, {
										"aTargets" : [ 12 ],
										"mData" : "createTime",
										"bSearchable" : false,
										"bVisible" : false

									} ]

						});
	}

	var refreshTable = function() {
		$.post("../map/api/getAllTableData", function(data) {
			if (!data.error) {
				thisData = data.data;
				refreshData(thisData);
			}
		});
	};
	var clacImgZoomParam = function(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
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
	return {
		initSelect : function() {
			$.post("../market/api/getData?t=" + Math.random(), function(data) {
				if (data.data.length > 0) {
					updateList("storeId", data.data);
				} else {
					alert("无商场数据！");
				}
			});
		},
		initTable : function() {
			refreshTable();
		},
		deleteTable : function(mapId) {
			deleteTableData(mapId);
		},
		bindClickEvent : function() {
			$("#add").on("click", function(e) {

				addMap();
			});
			$("#cancel").on("click", function(e) {

				hideAdd();
			});
			$("#confirm").on("click", function(e) {
				var pass = true;
				if ($("#storeId").val() == "") {
					checkPass(false, inotnull, "storeId");
					pass = false;
				} else {
					checkPass(true, "", "storeId");
				}
				if ($("#floor").val() == "") {
					checkPass(false, inotnull, "floor");
					pass = false;
				} else {
					checkPass(true, "", "floor");
				}
				if ($("#mapId").val() == "") {
					checkPass(false, inotnull, "mapId");
					pass = false;
				} else {
					checkPass(true, "", "mapId");
				}
				var anglevalue = $("#angle").val();
				if (anglevalue != "") {
					if (anglevalue > 360) {
						checkPass(false, "0-360", "angle");
						pass = false;
					} else {
						checkPass(true, "", "angle");
					}
				} else {
					$("#angle").val("0");
					anglevalue = 0;
					checkPass(true, "", "angle");
				}
				var fi = $("#textfield").val();
				var fi = fi.split(".")[fi.split(".").length - 1];
				if (fi != "jpg" && fi != "png" && fi != "PNG" && fi != "JPG") {
					checkPass(false, ipathf, "textfield");
					pass = false;
				} else {
					checkPass("pass", "", "textfield");
				}
				var fisvg = $("#textsvg").val();
				if (fisvg != "") {
					var fisvg = fisvg.split(".")[fisvg.split(".").length - 1];
					if (fisvg != "svg" && fisvg != "SVG") {
						checkPass(false, ipathsvg, "textsvg");
						pass = false;
					} else {
						checkPass("pass", "", "textsvg");
					}

				}
				var fixml = $("#textnavi").val();
				if (fixml != "") {
					var fixml = fixml.split(".")[fixml.split(".").length - 1];
					if (fixml != "xml" && fixml != "XML") {
						checkPass(false, ipathxml, "textnavi");
						pass = false;
					} else {
						checkPass("pass", "", "textnavi");
					}

				}

				// if ( $("#textfield").val() == "") {
				// checkPass(false, inotnull, "textfield");
				// pass=false;
				// } else {
				// checkPass(true, "", "textfield");
				// }
				if ($("#scaleId").val() == "") {
					checkPass(false, inotnull, "scaleId");
					pass = false;
				} else {
					checkPass(true, "", "scaleId");
				}
				if ($("#xo").val() == "" || $("#yo").val() == "") {
					pass = checkPass(false, inotnull, "xo");
				} else {
					if (!isNaN($("#xo").val()) || !isNaN($("#yo").val())) {
						checkPass(true, "", "xo");
					} else {
						checkPass(false, ipathshuzi, "xo");
						pass = false;
					}
				}
				// $("#formId").submit();
				$.post("../map/api/check", {
					mapId : $("#mapId").val(),
					id : $("#idid").val()
				}, function(data) {
					if (data.data == false) {
						checkPass(false, imapid, "mapId");
						pass = false;
					}

					if (pass) {
						$("#formId").submit();
					}
				});
			});
		}
	}
}();