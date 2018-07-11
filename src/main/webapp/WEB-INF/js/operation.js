var showColors, nowDate2;
var storeIds, categoryIds, shopIds, startTimes, endTimes;

var updateStoreList = function(renderId, data, selectTxt, callback) {
	var sortData = data.sort(function(a, b) {
		return a.name - b.name;
	});
	var len = sortData.length;
	var options = '';
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

var updateShopList = function(renderId, data, selectTxt, callback) {
	var sortData = data.sort(function(a, b) {
		return a.shopName - b.shopName;
	});
	var len = sortData.length;
	var options = '<option class="addoption" value="">' + operation_all
			+ '</option>';
	for (var i = 0; i < len; i++) {
		var info = sortData[i];
		if (selectTxt == info.shopName) {
			options += '<option class="addoption" selected=true value="'
					+ info.id + '">' + HtmlDecode3(info.shopName) + '</option>';
		} else {
			options += '<option class="addoption" value="' + info.id + '">'
					+ HtmlDecode3(info.shopName) + '</option>';
		}
	}
	removeOption(renderId);
	$('#' + renderId).append(options);
	if (callback) {
		callback();
	}
	return;
};

var updateCategoryList = function(renderId, data, selectTxt, callback) {
	var sortData = data.sort(function(a, b) {
		return a.name - b.name;
	});
	var len = sortData.length;
	var options = '<option class="addoption" value="">' + operation_all
			+ '</option>';
	;
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

var removeOption1 = function(renderId) {
	$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
};

var refreshCategorys = function() {
	$.post("../category/getCategoryDataByStore", {
		id : storeIds
	}, function(data) {
		if (data.data != null && data.data.length > 0) {
			updateCategoryList("categoryId", data.data);
			// shop = data.data[0].mapId;
		} else {
			// alert("无类别数据！");
			// updateMapList("mapId", []);
			// mapIds = -1;
			updateCategoryList("categoryId", []);
		}
	});
};
var refreshShops = function() {
	$.post("../shop/getShopDataByStore", {
		id : storeIds,
		categoryId : categoryIds
	}, function(data) {
		if (data.data != null && data.data.length > 0) {
			updateShopList("shopId", data.data);
			// shop = data.data[0].mapId;
		} else {
			// alert("无类别数据！");
			// updateMapList("mapId", []);
			// mapIds = -1;
			updateShopList("shopId", []);
		}
	});
};

var getAllData = function() {
	var params;
	if (shopIds != null && shopIds != "") {
		params = {
			field : 'shopId',
			id : shopIds,
			startTime : startTimes,
			endTime : endTimes
		};

	} else if (categoryIds != null && categoryIds != "") {
		params = {
			field : 'categoryId',
			id : categoryIds,
			storeId : storeIds,
			startTime : startTimes,
			endTime : endTimes
		};
	} else {
		params = {
			field : 'storeId',
			id : storeIds,
			startTime : startTimes,
			endTime : endTimes
		};
	}
//	$("#divCon1").css(
//			{
//			"background-size": "100% auto",
//			"background-repeat": "repeat-x",
////				"margin" : "0 auto",
//				"background-position":"center ",
//			"background-image":"url("+"../image/loading4.gif"+")"
//			});	
//	$("#divCon2").css(
//			{
//			"background-size": "100% auto",
//			"background-repeat": "repeat-x",
////				"margin" : "0 auto",
//				"background-position":"center ",
//			"background-image":"url("+"../image/loading4.gif"+")"
//			});	
//	$("#divCon3").css(
//			{
//			"background-size": "100% auto",
//			"background-repeat": "repeat-x",
////				"margin" : "0 auto",
//				"background-position":"center ",
//			"background-image":"url("+"../image/loading4.gif"+")"
//			});	
//	$("#divCon4").css(
//			{
//			"background-size": "100% auto",
//			"background-repeat": "repeat-x",
////				"margin" : "0 auto",
//				"background-position":"center ",
//			"background-image":"url("+"../image/loading4.gif"+")"
//			});	
//	$("#divCon5").css(
//			{
//			"background-size": "100% auto",
//			"background-repeat": "repeat-x",
////				"margin" : "0 auto",
//				"background-position":"center ",
//			"background-image":"url("+"../image/loading4.gif"+")"
//			});	
//	$("#divCon6").css(
//			{
//			"background-size": "100% auto",
//			"background-repeat": "repeat-x",
////				"margin" : "0 auto",
//				"background-position":"center ",
//			"background-image":"url("+"../image/loading4.gif"+")"
//			});	
	$(".loadingtext").show();
	$.post("../visitor/getData", params, function(data) {
		if (data.status == 200) {
//			$("#divCon1").css("background-image","");
//			$("#divCon2").css("background-image","");
//			$("#divCon3").css("background-image","");
//			$("#divCon4").css("background-image","");
//			$("#divCon5").css("background-image","");
//			$("#divCon6").css("background-image","");
			$(".loadingtext").hide();
			showPieCharts(data.genderList, "genderCharts", 100);
			showPieCharts(data.ageList, "ageCharts", 100);
			showBarCharts(data.localAddressList, "localAddressCharts",
					[ colours.blue ]);
			showBarCharts(data.homeAddressList, "homeAddressCharts",
					[ colours.red ]);
			showBarCharts(data.workAddressList, "workAddressCharts",
					[ colours.green ]);
			showPieCharts(data.abilityList, "abilityCharts", 100);
		}

	});
};

var operation = function() {

	/* legend code end */

	return {
		// 初始化下拉列表
		initShopName : function() {
			$.post("../market/api/getData", function(data) {
				if (data.status == 200) {
					if (data.data != null && data.data.length > 0) {
						updateStoreList("mallId", data.data);
						storeIds = data.data[0].id;
						getAllData();
						refreshCategorys();
						refreshShops();
					}

				}
			});

		},
		initDropdown : function() {
			initMapId(initFloor);
		},
		bindClickEvent : function() {
			$("select[data-type='mallSelect']").live("change", function(e) {
				storeIds = $(this).children('option:selected').val();
				categoryIds = "";
				shopIds = "";
				refreshCategorys();
				refreshShops();
				getAllData();
			});

			$("select[data-type='categorySelect']").live("change", function(e) {
				categoryIds = $(this).children('option:selected').val();
				shopIds = "";
				refreshShops();
				getAllData();
			});

			$("select[data-type='shopSelect']").live("change", function(e) {
				shopIds = $(this).children('option:selected').val();
				getAllData();
			});
			$("#confirmId").on("click", function(e) {
				// var startTime = $("#select_time_begin_tab1").val();
				// var endTime = $("#select_time_end_tab1").val();
				if (startTimes > endTimes) {
					endTimes = startTimes;
					$("#select_time_end_tab1").val(endTimes);
				}
				getAllData();
			});
			// $('#mallId').change(function() {
			//			
			// });
		}

	};

}();

function showPieCharts(list, target, size) {
	
	var dataX = [];
	for (i = 0; i < list.length; i++) {
		dataX.push(list[i].name);
	}
	var pieChart = echarts.init(document.getElementById(target));
	option = {
		legend : {
			x : '80%',
			y : '10%',
			orient : 'vertical',
			data : dataX
		},
		color : showColors,
		 tooltip : {
		        trigger: 'item',
		        formatter: operation_count+" : {c}"
		    },

		calculable : true,
		series : [

		{
			name : '面积模式',
			type : 'pie',
			radius : [ 10, size ],
			center : [ '50%', '50%' ],
			roseType : 'radius',
			label : {
				normal : {
					show : true,
					textStyle : {
						fontSize : 16
					}
				},
				emphasis : {
					show : true
				}
			},
			itemStyle : {
				normal : {
					shadowBlur : 30,
					shadowColor : 'rgba(0, 0, 0, 0.3)'
				}
			},
			lableLine : {
				normal : {
					show : false
				},
				emphasis : {
					show : true
				}
			},
			data : list
		} ]
	};
	pieChart.setOption(option);

}

function showBarCharts(list, target, thisColor) {
	
	var pieChart = echarts.init(document.getElementById(target));
	var dataX = [];
	var dataY = [];
	var dataValue = [];
	for (i = 0; i < list.length; i++) {
		dataX.push(list[i].name);
		dataY.push(list[i].value);
		dataValue.push({
			value : list[i].value,
			symbol : '',
			symbolSize : [ 0 ]
		});
	}
	option = {
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'none'
			},
			formatter : function(params) {
				return params[0].axisValue + ': ' + params[0].value;
			}
		},
		xAxis : {
			data : dataX,
			axisTick : {
				show : false
			},
			axisLine : {
				show : false
			},
			axisLabel : {
				textStyle : {
					color : thisColor
				}
			}
		},
		axisLabel: {  
            interval: 0,  
            formatter:function(value)  
            {  
                return  value.replace(":","\n");  
            }  
        },
		yAxis : {
			splitLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			axisLine : {
				show : false
			},
			axisLabel : {
				show : false
			}
		},
		color : thisColor,
		series : [
				{
					name : 'hill',
					type : 'pictorialBar',
					barCategoryGap : '-130%',
					symbol : 'path://M0,10 L10,10 C5.5,10 5.5,5 5,0 C4.5,5 4.5,10 0,10 z',
					itemStyle : {
						normal : {
							opacity : 0.5
						},
						emphasis : {
							opacity : 1
						}
					},
					data : dataY,
					z : 10
				}, {
					name : 'glyph',
					type : 'pictorialBar',
					barGap : '-100%',
					symbolPosition : 'end',
					symbolSize : 50,
					symbolOffset : [ 0, '-120%' ],
					data : dataValue
				} ]
	};
	pieChart.setOption(option);
}

$(document).ready(
		function() {
			operation.initShopName();
			operation.bindClickEvent();
			// get object with colros from plugin and store it.
			objColors = $('body').data('sprFlat').getColors();
			colours = {
				white : objColors.white,
				dark : objColors.dark,
				red : objColors.red,
				blue : objColors.blue,
				green : objColors.green,
				yellow : objColors.yellow,
				brown : objColors.brown,
				orange : objColors.orange,
				purple : objColors.purple,
				pink : objColors.pink,
				lime : objColors.lime,
				magenta : objColors.magenta,
				teal : objColors.teal,
				textcolor : '#5a5e63',
				gray : objColors.gray
			}
			showColors = [ colours.blue, colours.green, colours.red,
					colours.brown, colours.dark, objColors.yellow,
					objColors.purple, colours.orange ];
		});

function getDatePicker(id) {
	WdatePicker({
		el : document.getElementById(id),
		lang : 'en',
		isShowClear : false,
		isShowToday : false,
		readOnly : true,
		dateFmt : 'yyyy-MM-dd',
		maxDate : '%y-%M-%d'
	});
	startTimes = $("#select_time_begin_tab1").val();
	if (startTimes != "") {
		$("#timeId").show();
	}
}

function getDatePicker1(id) {
	if (startTimes != "") {
		WdatePicker({
			el : document.getElementById(id),
			lang : 'en',
			isShowClear : false,
			isShowToday : false,
			readOnly : true,
			dateFmt : 'yyyy-MM-dd',
			maxDate : '%y-%M-%d',
			minDate : startTimes
		});
		endTimes = $("#select_time_end_tab1").val();
		$("#confirmId").show();
	}
}