var charTime1,totalTimer,mapIds, shopIds, heatMapData, optionsss, endTimes, timeData, origX, origY, bgImg, scale, coordinate, imgHeight, imgWidth, imgScale, heatmap, heatmap1, timer, floorLoop, floorData;
var nowDate1, nowDate2, sign1, sign2, lastNewDate1;
var showColors;
var pointVal = 20;
var configObj = {
	container : document.getElementById("heatmap"),
	maxOpacity : .6,
	radius : 20,
	blur : .90,
	backgroundColor : 'rgba(0, 58, 2, 0)'
};
var configObj1 = {
	container : document.getElementById("heatmap1"),
	maxOpacity : .6,
	radius : 20,
	blur : .90,
	backgroundColor : 'rgba(0, 58, 2, 0)'
};


var refreshHeatmapData = function() {
	// var times = $("#time").val($.cookie("times"));
	// var times = $.cookie("times");
	$
			.post(
					"/sva/heatmap/api/getShopData?shopId=" + shopIds,
					function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter(data.data, origX,
										origY, scale, imgWidth, imgHeight,
										coordinate, imgScale);
								var dataObj = {
									// max : pointVal,
									min : 2,
									data : points
								};
								heatmap.setData(dataObj);
//								document.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
//										+ data.data.length;
								// $("#legend").show();
							} else {
								var canvas = document
										.getElementsByTagName('canvas')[0];
								var ctx = canvas.getContext('2d');
								ctx.clearRect(0, 0, imgWidth, imgHeight);
								// $("#legend").hide();
							}
							// $("#count").text(data.data.length);
						}
						// heatMap.initTotalData;
						timer = setTimeout("refreshHeatmapData();", 4000);
					});
};
var mapToarray = function(map) {
	var array = [];
	for ( var key in map) {
		var key = key;
		var value = map[key];
		array.push(value);
	}
	return array;
}
var mapToarray2 = function(map) {
	var array = [];
	for ( var key in map) {
		var key = key;
		var value = map[key];
		var reslut = [];
		reslut.push(key);
		reslut.push(value);
		array.push(reslut);
	}
	return array;
}

var mapToarray22 = function(map) {
	var array = [];
	for ( var key in map) {
		var key = key;
		var value = map[key];
		var reslut = [];
		reslut.push(parseInt(key) + 28800000);
		reslut.push(value);
		array.push(reslut);
	}
	return array;
}
var mapToarray3 = function(map) {
	var array = [];
	for ( var key in map) {
		var key = key;
		var value = map[key];
		var reslut = [];
		reslut.push(parseInt(key));
		reslut.push(value);
		array.push(reslut);
	}
	return array;
}
var timeToVarchar = function(time) {
	var result;
	var timeArray = String(time).split(".");
	if (timeArray.length > 1) {
		result = timeArray[0] + "m " + parseInt((timeArray[1] * 6) / 10)
				+ "s";
	} else {
		result = time + "m";
	}
	return result;
}
// id:当前数据，id1:百分比;id2：曲线图，nowArray:曲线图数据，nowPeople：今天人数,yesPeople:昨天人数，type:0为人，1位驻留，type1：0位曲线图，1为柱状图
var fuzhiFunction = function(id, id1, id2, nowArray, nowPeople, yesPeople,
		type, type1) {
	var downs = title_yes+'<i class="im-arrow-down-right2 color-white"></i>';
	var ups = title_yes+'<i class="im-arrow-up-right3 color-white"></i>';
	var peoples = '<i class="ec-users"></i>';
	var times = '<i class="ec-clock"></i>';
	var nowPeopleTemp;
	var nowBaifen;
	var types;
	var nowDatas = [];
	var yanse;
	if (type == 0) {
		types = peoples;
	} else {
		types = times;
	}
	if (parseInt(nowPeople) > parseInt(yesPeople)) {
		nowPeopleTemp = ups;
		yanse = colours.green;
		if (nowPeople == 0) {
			nowBaifen = 0;
		}
		if (!nowPeople == 0 && yesPeople == 0) {
			nowBaifen = 100;
		}
		if (nowPeople > 0 && yesPeople > 0) {
			nowBaifen = (((nowPeople-yesPeople) / yesPeople)*100).toFixed(2);
		}
	} else if(nowPeople < yesPeople) {
		yanse = colours.red;
		nowPeopleTemp = downs;
		nowBaifen = ((-((nowPeople-yesPeople) / yesPeople))*100).toFixed(2);
	}
	else if(nowPeople == yesPeople) {
		yanse = colours.red;
		nowPeopleTemp = title_yes+"=";
		nowBaifen = "0.00";
	}
	if (type == 0) {
		if(id!="nowPeople")
			{
			document.getElementById(id).innerHTML = types + nowPeople;
			}
			
	} else {
		if(id!="nowPeople")
			{
			document.getElementById(id).innerHTML = types
			+ timeToVarchar(nowPeople);
			}
	}

	document.getElementById(id1).innerHTML = nowPeopleTemp + nowBaifen
			+ '%';
	if (type1 == 0) {
		$("#" + id2).sparkline(nowArray, {
			width : '55px',
			height : '20px',
			lineColor : colours.white,
			fillColor : false,
			spotColor : false,
			minSpotColor : false,
			maxSpotColor : false,
			lineWidth : 2
		});
	} else {
		$("#" + id2).sparkline(nowArray, {
			type : 'bar',
			width : '100%',
			height : '18px',
			barColor : colours.white,
		});
	}
}
var refreshTotalData = function() {
	$.post("/sva/shop/getNewTotal",
					{
						shopId : shopIds
					},
					function(data) {
						if (data.error == 200) {
//							var newData = data.newData;
							var allData = data.allData;
							var timeData = data.timeData;
//							var newPeople = data.newPeople;
//							var yesNewPeople = data.yesNewPeople;
							var nowAllPeople = data.nowAllPeople;
							var yesAllPeople = data.yesAllPeople;
							var nowTime = data.nowTime;
							var yesTime = data.yesTime;
							var nowPeople = data.nowPeople;
							var yesPeople = data.yesPeople;

//							var newArray = mapToarray(newData);
							var allArray = mapToarray(allData);
							var timeArray = mapToarray(timeData);
//							fuzhiFunction("newPeople", "newPeopleId",
//									"newData", newArray, newPeople,
//									yesNewPeople, 0, 1);
							fuzhiFunction("nowPeople", "nowPeopleId",
									"nowData", allArray, nowPeople,
									yesPeople, 0, 0);
							fuzhiFunction("nowAllcount", "nowAllcountId",
									"allDataId", allArray, nowAllPeople,
									yesAllPeople, 0, 1);
							fuzhiFunction("nowTime", "nowTimeId",
									"timeDataId", timeArray, nowTime,
									yesTime, 1, 0);
						}
					});
	
	totalTimer = setTimeout("refreshTotalData();",600000);
	
};
var initMyPieChart = function(lineWidth, size, animateTime, colours) {
	$(".pie-chart").easyPieChart({
		barColor : colours.dark,
		borderColor : colours.dark,
		trackColor : '#d9dde2',
		scaleColor : false,
		lineCap : 'butt',
		lineWidth : lineWidth,
		size : size,
		animate : animateTime
	});
};


var updateShopList = function(renderId, data, selectTxt, callback) {
	$("#shopId").find("option").remove();
	var sortData = data.sort(function(a, b) {
		return a.floor - b.floor;
	});
	var len = sortData.length;
	var options = '';
	if (len > 0) {
		mapIds = sortData[0].mapId;
		shopIds = sortData[0].id;
	}
	var floorName = "";
	var tempFloorName;
	for (var i = 0; i < len; i++) {
		var info = sortData[i];
		var mymapId = info.mapId;
		tempFloorName = info.storeName + "_" + info.floorName;
		if (floorName == tempFloorName) {
			options += '<option data-mapId="' + mymapId
					+ '" class="addoption" value="' + info.id + '">'
					+ info.shopName + '</option>';
		}
		if (floorName != "" && !(floorName == tempFloorName)) {
			options += '</optgroup><optgroup label="' + tempFloorName
					+ '"><option data-mapId="' + mymapId
					+ '" class="addoption" value="' + info.id + '">'
					+ info.shopName + '</option>';
			floorName = tempFloorName;
		}
		if (floorName == "") {
			floorName = tempFloorName;
			options += '<optgroup label="' + tempFloorName
					+ '"><option data-mapId="' + mymapId
					+ '" class="addoption" value="' + info.id + '">'
					+ info.shopName + '</option>';
		}

	}
	removeOption1(renderId);
	$('#' + renderId).append(options);
	return;
};
var removeOption1 = function(renderId) {
	$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
};

var initChartPeople = function(data1) {

	var options = {
		grid : {
			show : true,
			labelMargin : 20,
			axisMargin : 40,
			borderWidth : 0,
			borderColor : null,
			minBorderMargin : 20,
			clickable : true,
			hoverable : true,
			autoHighlight : true,
			mouseActiveRadius : 100
		},
		series : {
			grow : {
				active : true,
				duration : 1000
			},
			lines : {
				show : true,
				fill : false,
				lineWidth : 2.5
			},
			points : {
				show : true,
				radius : 5,
				lineWidth : 3.0,
				fill : true,
				fillColor : colours.red,
				strokeColor : colours.white
			}
		},
		colors : [ colours.dark, colours.blue ],
		legend : {
			show : true,
			position : "ne",
			margin : [ 0, -25 ],
			noColumns : 0,
			labelBoxBorderColor : null,
			labelFormatter : function(label, series) {
				return '&nbsp;' + label + '&nbsp;&nbsp;';
			},
			width : 40,
			height : 1
		},
		shadowSize : 0,
		tooltip : true, // activate tooltip
		tooltipOpts : {
			content : "%y.0",
			shifts : {
				x : -45,
				y : -50
			},
			defaultTheme : false
		},
		yaxis : {
			min : 0
		},
		xaxis : {
			mode : "categories",
			tickLength : 0
		}
	}

	var plot = $.plot($("#stats-pageviews"), [ {
		data : data1
	} ], options);
}

var initChartLine = function(data1) {

	var chartMinDate = data1[0][0]; // first day
	var chartMaxDate = data1[6][0];// last day

	var tickSize = [ 1, "day" ];
	var tformat = "%y/%m/%d";

	var total = 0;
	// calculate total earnings for this period
	for (var i = 0; i < 7; i++) {
		total = data1[i][1] + total;
	}

	var options = {
		grid : {
			show : true,
			aboveData : false,
			color : colours.textcolor,
			labelMargin : 20,
			axisMargin : 0,
			borderWidth : 0,
			borderColor : null,
			minBorderMargin : 5,
			clickable : true,
			hoverable : true,
			autoHighlight : true,
			mouseActiveRadius : 100,
		},
		series : {
			grow : {
				active : true,
				duration : 1500
			},
			lines : {
				show : true,
				fill : true,
				lineWidth : 2.5
			},
			points : {
				show : false
			}
		},
		colors : [ colours.blue ],
		legend : {
			position : "ne",
			margin : [ 0, -25 ],
			noColumns : 0,
			labelBoxBorderColor : null,
			labelFormatter : function(label, series) {
				return null;
			},
			backgroundColor : colours.white,
			backgroundOpacity : 0.5,
			hideSquare : true
		// hide square color helper
		},
		shadowSize : 0,
		tooltip : true, // activate tooltip
		tooltipOpts : {
			content : "%y.2",
			xDateFormat : "%d/%m",
			shifts : {
				x : -30,
				y : -50
			},
			theme : 'dark',
			defaultTheme : false
		},
		yaxis : {
			min : 0
		},
		xaxis : {
			mode : "time",
			minTickSize : tickSize,
			timeformat : tformat,
			min : chartMinDate,
			max : chartMaxDate,
			tickLength : 0,

		}
	}

	var plot = $.plot($("#line-chart-filled"), [ {
		label : "Earnings",
		data : data1
	} ], options);

}


var initChartBar = function()
{
	var mydiv = document.getElementById("ordered-bars-chart");
		//some data
		var param = $("#shopId").val();
//		$("#ordered-bars-chart").css("background-image","url("+"../image/loading.gif"+")")
//		$("#ordered-bars-chart").css(
//				{
//				"background-size": "100% auto",
//				"background-repeat": "repeat-x",
////					"margin" : "0 auto",
//					"background-position":"center ",
//				"background-image":"url("+"../image/loading4.gif"+")"
//				});	
		$("#loadingId").show();
		$.post("/sva/heatmap/api/getRates",{id:param},function(data){
//			for (var i = 0; i <= data.date.length; i += 1){
//				d1.push([data.date[i], data.eRate[i]]);
//				d2.push([data.date[i],data.oRate[i]]);
//				d3.push([data.date[i], data.dRate[i]]);
//		  }
			$("#loadingId").hide();
			  var shopName = data.date;
			  var eRate = data.eRate; //进店率
			  var oRate = data.oRate;//溢出率
			  var dRate = data.dRate;//深访率
			  var titile = [EnteringRate,OverflowRate,DeepRate]; 
//			  var titile1 = ["进店率：进入商铺的人数总和/进入商铺周围大区域的人数总和","深访率：进入商铺驻留时长大于深访基本时间的人数总和/进入商铺的人数总和","溢出率：进入商铺驻留时长小于溢出基本时间的人数总和/进入商铺的人数总和"]; 
//			  进店率：进入商铺的人数总和/进入商铺周围大区域的人数总和
//			  深访率：进入商铺驻留时长大于深访基本时间的人数总和/进入商铺的人数总和
//			  溢出率：进入商铺驻留时长小于溢出基本时间的人数总和/进入商铺的人数总和
			  var myChart = echarts.init(mydiv); 
			  option = {
//					    tooltip : {
//					        trigger: 'axis',
//					        formatter: '{a0}: {c0}<br />{a1}: {c1}<br />{a2}: {c2}'
//					    },
					    tooltip: {
							trigger: 'axis',
					        formatter: function (params, ticket, callback) {
					            //x轴名称
//					            var name = params[0].name
					            //图表title名称
					        	var myShopName = params[0].axisValue;
					            var seriesName = params[0].seriesName.split(":")[0]+":"
					            //值
					            var value = params[0].value
					            var seriesName1 = params[1].seriesName.split(":")[0]+":"
					            //值
					            var value1 = params[1].value
					            var seriesName2 = params[2].seriesName.split(":")[0]+":"
					            //值
					            var value2 = params[2].value
					            var lengths = myShopName+'<br />'+seriesName+value+'<br />'+seriesName1+value1+'<br />'+seriesName2+value2
					            return lengths
					        }
					    },
					    
//					    legend: {
//					        data:titile
//					    },
					    legend: {
					    	data:titile,
					        formatter: function (name) {
					            return name.split(":")[0];
					        },
					        tooltip: {
					            show: true
					        }
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: false},
					            dataView : {show: false, readOnly: true},
					            magicType : {show: true, type: ['line', 'bar']},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    color : ['#ff7f50','#87cefa', '#91c7ae', '#d48265', '#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'],				    
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            data : shopName,
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            name : echardanwei+'(%)',
					            splitLine: {
					                show: false
					            }
					        }
					    ],
					    series : [
					        {
					            name:EnteringRate,
					            type:'bar',
							    label:{ 
							    	normal:{ 
							    		show: false,
							    		position:"top"
							    	} 
							    },
					            data:eRate,
					            markLine : {
					                data : [
					                    {
					                    	type : 'average',
					                    	name : echarAverage	
					                    
					                    }
					                ]
							    	
					            }
					        },
					        {
					            name:OverflowRate,
					            type:'bar',
							    label:{ 
							    	normal:{ 
							    		show: true,
							    		position:"top"
							    	} 
							    },
					            data:oRate,
					            markLine : {
					                data : [
					                    {
					                    	type : 'average', 
					                    	name : echarAverage	
					                    }
					                ]
					            }
					        },
					        {
					            name:DeepRate,
					            type:'bar',
							    label:{ 
							    	normal:{ 
							    		show: true,
							    		position:"top"
							    	} 
							    },
					            data:dRate,
					            markLine : {
					                data : [
						                    {type : 'average', name : echarAverage}
					                ]
					            }
					        }
					    ]
					};
			  myChart.setOption(option); 
			
		});
		
}
var dataFilter = function(data, xo, yo, scale, width, height, coordinate,
		imgScale) {
	var list = [];
	xo = parseFloat(xo);
	yo = parseFloat(yo);
	scale = parseFloat(scale);
	switch (coordinate) {
	case "ul":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale
						+ Math.random() / 10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale
						+ Math.random() / 10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale
						+ Math.random() / 10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale
						+ Math.random() / 10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale
						+ Math.random() / 10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale
						+ Math.random() / 10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale
						+ Math.random() / 10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale
						+ Math.random() / 10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};

var calImgSize = function(width, height) {
	var newWidth, newHeight, imgScale;
	var divWidth = parseInt($("#divCon").css("width").slice(0, -2));

	if (divWidth / 400 > width / height) {
		newHeight = 400;
		imgScale = height / newHeight;
		newWidth = width / imgScale;
	} else {
		newWidth = divWidth;
		imgScale = width / newWidth;
		newHeight = height / imgScale;
	}

	return [ imgScale, newWidth, newHeight ];
};

var heatMap = function() {

	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '';
		for (var i = 0; i < len; i++) {
			if (sortData[i].id == selectTxt) {
				options += '<option class="addoption" selected value="'
						+ sortData[i].id + '">' + HtmlDecode3(sortData[i].name)
						+ '</option>';
			} else {
				options += '<option class="addoption" value="' + sortData[i].id
						+ '">' + HtmlDecode3(sortData[i].name) + '</option>';
			}
		}
		removeOption(renderId);
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};

	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateFloorList = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.floor - b.floor;
		});
		var len = sortData.length;
		var options = '';
		for (var i = 0; i < len; i++) {
			if (sortData[i].mapId == selectTxt) {
				options += '<option class="addoption" selected value="'
						+ sortData[i].mapId + '">' + sortData[i].floor
						+ '</option>';
			} else {
				options += '<option class="addoption" value="'
						+ sortData[i].mapId + '">' + sortData[i].floor
						+ '</option>';
			}
		}
		removeOption(renderId);
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	var reflashHeatMapChart = function() {
		$.post("/sva/heatmap/api/getShopChartsData?shopId=" + shopIds
				+ "&&endTime=" + endTimes, function(data) {
			heatMapData.shift();
			heatMapData.push([ endTimes + 28800000, data.data ]);
			var plot = $.plot($("#autoupdate-chart"), [ heatMapData ],
					optionsss);
		});

	}
	var intiHeatMapChart = function() {
		$.post("/sva/heatmap/api/getShopHeatMap?shopId=" + shopIds, function(
				data) {
			var data = data.data;
			heatMapData = mapToarray22(data);

			var updateInterval = 4000;

			// setup plot
			optionsss = {
				series : {
					shadowSize : 0, // drawing is faster without shadows
					lines : {
						show : true,
						fill : true,
						lineWidth : 3.5,
						steps : false
					}
				},
				grid : {
					show : true,
					aboveData : true,
					color : colours.textcolor,
					labelMargin : 20,
					axisMargin : 0,
					borderWidth : 0,
					borderColor : null,
					minBorderMargin : 5,
					clickable : true,
					hoverable : true,
					autoHighlight : true,
					mouseActiveRadius : 100,
				},
				colors : [ colours.blue ],
				tooltip : true, // activate tooltip
				tooltipOpts : {
					content : "  %y.0",
					shifts : {
						x : -30,
						y : -50
					}
				},
				yaxis : {
					min : 0
				},
				xaxis : {
					mode : "time",
					show : true
				}
			};
			document.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
				+heatMapData[heatMapData.length - 1][1];
			function update() {
				endTimes = heatMapData[heatMapData.length - 1][0] + updateInterval;
				$.post("/sva/heatmap/api/getShopChartsData?shopId=" + shopIds
						+ "&&endTime=" + endTimes, function(data) {
					document.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
						+data.data;
					heatMapData.shift();
					heatMapData.push([ endTimes, data.data ]);
					var plot = $.plot($("#autoupdate-chart"), [ heatMapData ],
							optionsss);
					charTime1 = setTimeout(update, updateInterval);
				});
			}
			var plot = $.plot($("#autoupdate-chart"), [ heatMapData ],
					optionsss);
			charTime1 = setTimeout(update, updateInterval);

		});
	}
	var initTotalData = function() {
		$.post("/sva/shop/getNewTotal",
						{
							shopId : shopIds
						},
						function(data) {
							if (data.error == 200) {
								var weekCount = data.allWeekCount;
								var weekTime =  data.allWeekAvgDelay;
//								var yesss = data.yesTime1;
//								var nowData = data.nowData;
//								var newData = data.newData;
								var allData = data.allData;
								var timeData = data.timeData;
								var nowPeople = data.nowPeople;
								var yesPeople = data.yesPeople;
//								var yesPeople = data.yesPeople;
//								var newPeople = data.newPeople;
//								var yesNewPeople = data.yesNewPeople;
								var nowAllPeople = data.nowAllPeople;
								var yesAllPeople = data.yesAllPeople;
								var nowTime = data.nowTime;
								var yesTime = data.yesTime;
//								var newAllPeople = data.newAllPeople;

								var nowArray = mapToarray(allData);
//								var newArray = mapToarray(newData);
								var allArray = mapToarray(allData);
								var timeArray = mapToarray(timeData);
//								var yesTime = timeArray[6];
								var yesss = timeArray[6];
//								var yesAllPeople = allArray[6];
								var nowArrays = mapToarray2(allData);
								var timeArrays = mapToarray2(timeData);
								fuzhiFunction("nowPeople", "nowPeopleId",
										"nowData", nowArray, nowPeople,
										yesPeople, 0, 0);
//								fuzhiFunction("newPeople", "newPeopleId",
//										"newData", newArray, newPeople,
//										yesNewPeople, 0, 1);
								fuzhiFunction("nowAllcount", "nowAllcountId",
										"allDataId", allArray, nowAllPeople,
										yesAllPeople, 0, 1);
								fuzhiFunction("nowTime", "nowTimeId",
										"timeDataId", timeArray, nowTime,
										yesTime, 1, 0);
								initMyPieChart(10, 40, 1500, colours);
								initChartPeople(nowArrays);
								initChartLine(timeArrays);
								initChartBar();
								if (nowTime == 0) {
									nowTime = "0.00";
								}
								if (yesss == 0) {
									yesss = "0.00";
								} else {
									yesss = yesss.toFixed(2);
								}
								document.getElementById("countToday").innerHTML = nowAllPeople;
								document.getElementById("countYesterday").innerHTML = yesAllPeople;
								document.getElementById("countTotal").innerHTML = weekCount;
								document.getElementById("countTodayTime").innerHTML = timeToVarchar(nowTime);
								document.getElementById("countYesTodayTime").innerHTML = timeToVarchar(yesss);
								document.getElementById("allCountToday").innerHTML = timeToVarchar(weekTime.toFixed(2));
								clearTimeout(charTime1);
								intiHeatMapChart();
								RouteLine.changeShop(shopIds,mapIds);
							}
						});
		clearTimeout(totalTimer);
		totalTimer = setTimeout("refreshTotalData();",600000);
		
	};

	var initHeatmap = function(callback) {
		$("#mapContainer").css("background-image", "");
		$("#heatmap").empty();
		$
				.post(
						"/sva/heatmap/api/getMapInfoByPosition?mapId=" + mapIds,
						function(data) {
							if (data.data.length > 0) {
								if (data.data[0].path) {
									var data = data.data[0];
									// 全局变量赋值
									origX = data.xo;
									origY = data.yo;
									bgImg = data.path;
									bgImgWidth = data.imgWidth;
									bgImgHeight = data.imgHeight;
									scale = data.scale;
									coordinate = data.coordinate;
									// 设置背景图片
									// var bgImgStr = "url(upload/" + bgImg +
									// ")";
									var bgImgStr = "url(../upload/map/" + bgImg
											+ ")";
									var imgInfo = calImgSize(bgImgWidth,
											bgImgHeight);
									imgScale = imgInfo[0];
									imgWidth = imgInfo[1];
									imgHeight = imgInfo[2];
									$("#mapContainer").css(
											{
												"width" : imgWidth + "px",
												"height" : imgHeight + "px",
												"background-image" : bgImgStr,
												"background-size" : imgWidth
														+ "px " + imgHeight
														+ "px",
												"margin" : "0 auto"
											});

									configObj.onExtremaChange = function(data) {
									};
									// var times = $.cookie("times");
									// var times =
									// $("#time").val($.cookie("times"));
									heatmap = h337.create(configObj);
									$
											.post(
													"/sva/heatmap/api/getShopData?shopId="
															+ shopIds,
													function(data) {
														if (!data.error) {
															if (data.data
																	&& data.data.length > 0) {
																// var points =
																// {max:1,data:dataFilter(data)};
																var points = dataFilter(
																		data.data,
																		origX,
																		origY,
																		scale,
																		imgWidth,
																		imgHeight,
																		coordinate,
																		imgInfo[0]);
																var dataObj = {
																	// max :
																	// pointVal,
																	min : 2,
																	data : points
																};
																heatmap
																		.setData(dataObj);
//																document
//																		.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
//																		+ data.data.length;
																// $("#legend").show();
															}
															// $(".countInfo").show();
															// $("#count").text(data.data.length);
														}
													});
									clearTimeout(timer);
									timer = setTimeout("refreshHeatmapData();",
											4000);
									// refreshHeatmapData()
									if (callback)
										callback();
								}
							}
						});
	};
	var initPeriodHeatmap1 = function(param) {
		$("#mapContainer1").css("background-image", "");
		$("#heatmap1").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition?mapId=" + mapIds,
				function(data) {
					if (data.data.length > 0) {
						if (data.data[0].path) {
							var data = data.data[0];
							// 全局变量赋值
							origX = data.xo;
							origY = data.yo;
							bgImg = data.path;
							bgImgWidth = data.imgWidth;
							bgImgHeight = data.imgHeight;
							scale = data.scale;
							coordinate = data.coordinate;
							// 设置背景图片
							// var bgImgStr = "url(upload/" + bgImg + ")";
							var bgImgStr = "url(../upload/map/" + bgImg + ")";
							var imgInfo = calImgSize(bgImgWidth, bgImgHeight);
							imgScale = imgInfo[0];
							imgWidth = imgInfo[1];
							imgHeight = imgInfo[2];
							$("#mapContainer1").css(
									{
										"width" : imgWidth + "px",
										"height" : imgHeight + "px",
										"background-image" : bgImgStr,
										"background-size" : imgWidth + "px "
												+ imgHeight + "px",
										"margin" : "0 auto"
									});
						}
					}
				});
	};
	

	var initPeriodHeatmap = function(param) {
		$("#mapContainer1").css("background-image", "");
		$("#heatmap1").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition?mapId=" + mapIds,
				function(data) {
					if (data.data.length > 0) {
						if (data.data[0].path) {
							var data = data.data[0];
							// 全局变量赋值
							origX = data.xo;
							origY = data.yo;
							bgImg = data.path;
							bgImgWidth = data.imgWidth;
							bgImgHeight = data.imgHeight;
							scale = data.scale;
							coordinate = data.coordinate;
							// 设置背景图片
							// var bgImgStr = "url(upload/" + bgImg + ")";
							var bgImgStr = "url(../upload/map/" + bgImg + ")";
							var imgInfo = calImgSize(bgImgWidth, bgImgHeight);
							imgScale = imgInfo[0];
							imgWidth = imgInfo[1];
							imgHeight = imgInfo[2];
							$("#mapContainer1").css(
									{
										"width" : imgWidth + "px",
										"height" : imgHeight + "px",
										"background-image" : bgImgStr,
										"background-size" : imgWidth + "px "
												+ imgHeight + "px",
										"margin" : "0 auto"
									});

							configObj1.onExtremaChange = function(data) {
							};
							// var times = $.cookie("times");
							// var times = $("#time").val($.cookie("times"));
							heatmap1 = h337.create(configObj1);
							$.post("/sva/heatmap/api/getPeriodShopData", param,
									function(data) {
										if (!data.error) {
											if (data.data
													&& data.data.length > 0) {
												// var points =
												// {max:1,data:dataFilter(data)};
												var points = dataFilter(
														data.data, origX,
														origY, scale, imgWidth,
														imgHeight, coordinate,
														imgInfo[0]);
												var dataObj = {
													// max : pointVal,
													min : 2,
													data : points
												};
												heatmap1.setData(dataObj);
											}
										}
									});
						}
					}
				});
	};
	
	
	var initMapId = function(callback) {
		$.post(rootPath + "/map/api/getMapInfoOfFirstStore", function(data) {
			floorData = data.data;
			if (floorData.length > 0) {
				mapIds = data.data[0].mapId;
			} else {
				mapIds = 0;
			}
			initHeatmap(callback);
		});
	}
	var bindClickEvent = function() {
		$(document).on("click", ".floors", function(e) {
			mapIds = $(this).data("mapid");
			initHeatmap();
			$(".floors").removeClass("active");
			$(this).addClass("active");
		});
	}
	var initFloor = function() {
		for (var i = 0; i < floorData.length; i++) {
			var mapTemp = floorData[i]

			// 楼层按钮初始化
			var top = 100 + 50 * i;
			var floorHtml = "<div class='floors' style='top:" + top
					+ "px' data-mapid='" + mapTemp.mapId + "'>" + mapTemp.floor
					+ "</div>"
			$("#mapContainer").append(floorHtml);
		}
		bindClickEvent();

	}
	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};

	/* legend code end */

	return {
		// 初始化下拉列表
		initShopName : function() {
			$.post("/sva/shop/getAllShopData", function(data) {
				if (data.error == 200) {
					updateShopList("shopId", data.data);
					initHeatmap();
					initTotalData();
					var timestamps = new Date().getTime();
					var startTimestamp = timestamps - 300000;
					var param = {
						type : 1,
						shopId : shopIds,
						startTime : startTimestamp,
						endTime : timestamps
					};
					initPeriodHeatmap1(param);
					// 今天
					var date1 = new Date();
					nowDate1 = date1.format("yyyy-MM-dd");
					sign1 = date1.getHours()-1;
					// 昨天
					var date2 = new Date();
					date2.setDate(date2.getDate() - 1);
					nowDate2 = date2.format("yyyy-MM-dd");
					sign2 = date2.getDate();
					// 如果sign1位0，则变为昨天和24
					if (sign1 == 0) {
						sign1 == 24;
						nowDate1 = nowDate2;
					}
					showShopTrend(0, sign1, nowDate1, "shopTrendByHour");
					showShopTrend(1, sign2, nowDate2, "shopTrendByDay");
				}

			});
		},
		initDropdown : function() {
			initMapId(initFloor);
		},
		bindClickEvent : function() {
			$("select[data-type='shopSelect']").live("change", function(e) {
				mapIds = $("#shopId").find("option:selected").data("mapid");
				shopIds = $("#shopId").val();

				showShopTrend(0, sign1, nowDate1, "shopTrendByHour");
				showShopTrend(1, sign2, nowDate2, "shopTrendByDay");
				initHeatmap();
				initTotalData();
				var timestamps = new Date().getTime();
				var startTimestamp = timestamps - 300000;
				var param = {
					type : 1,
					shopId : shopIds,
					startTime : startTimestamp,
					endTime : timestamps
				};
				initPeriodHeatmap1(param);
				clearTimeout(charTime1);
				intiHeatMapChart();
//				RouteLine.changeShop(shopIds,mapIds);
				initChartBar();
	
			});

			$("#heatMapConfirm").on("click", function(e) {
//				$("#divCon1").css(
//						{
//						"background-size": "100% auto",
//						"background-repeat": "repeat-x",
////							"margin" : "0 auto",
//							"background-position":"center ",
//						"background-image":"url("+"../image/loading4.gif"+")"
//						});	
				var startTime = $("#select_time_begin_tab1").val();
				var endTime = $("#select_time_end_tab1").val();
				var startSplit = startTime.split(" ");
				var endSplit = endTime.split(" ");
				var endChange = false;
				if (endSplit[0] != startSplit[0]) {
					endSplit[0] = startSplit[0];
					endChange = true;
				}
				if (endSplit[1] < startSplit[1]) {
					endSplit[1] = startSplit[1];
					endChange = true;
				}
				if (endChange) {
					endTime = endSplit[0] + " " + endSplit[1];
					$("#select_time_end_tab1").val(endTime);
				}
				var param = {
					type : 0,
					shopId : shopIds,
					startTime : startTime,
					endTime : endTime
				};
				initPeriodHeatmap(param);
			});
		}

	};

}();

$(document).ready(function() {
	heatMap.initShopName();
	heatMap.bindClickEvent();
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
	showColors = [ colours.blue, colours.yellow, colours.red, colours.green,colours.dark,colours.brown,colours.orange,colours.purple,colours.pink,colours.lime,colours.teal, colours.gray];
	initPieChart();
	setTimes();
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

/* 店铺动向统计饼图 */
function showShopTrend(myType, mySign, myTime, target) {
	$.post("../shop/getShopTrend", {
		shopId : shopIds,
		type : myType,
		sign : mySign,
		time : myTime
	},
			function(response) {
				if (response != null) {
					var dataValue = response.data;
					var pieChart = echarts
							.init(document.getElementById(target));
					var dataX = [];
					var dataColor = [];
					var colorLength = showColors.length;
					for (i = 0; i < dataValue.length; i++) {
						dataX.push(dataValue[i].name);
						if (i != 0 && i == dataValue.length - 1
								&& i % colorLength == 0) {
							dataColor.push(showColors[myType == 0 ? 1
									: colorLength - 2]); // 防止最后一个颜色和最前一个相同
						} else {
							dataColor.push(showColors[myType == 0 ? i
									% colorLength : colorLength - 1 - i
									% colorLength]);
						}
					}
					var titleText;
					if (myType == 0) {
						dataColor.reverse();
						titleText={
								text : mySign+":00",
								subtext : myTime,
								x : 'center',
								y : 'center',
								textStyle : {
									fontWeight : 'normal',
									fontSize : 16
								}};
					} else if (myType == 1) {
						titleText={
								text:"",
								subtext : myTime,
								x : 'center',
								y : 'center',
								textStyle : {
									fontWeight : 'normal',
									fontSize : 16
								}};
					}
					option = {
						backgroundColor : '#fff',
						title : titleText,
						tooltip : {
							show : true,
							trigger : 'item',
							formatter : "{b}: {c} ({d}%)"
						},
						color : dataColor,
						legend : {
							orient : 'horizontal',
							bottom : '0%',
							data : dataX
						},
						series : [ {
							type : 'pie',
							selectedMode : 'single',
							radius : [ '30%', '80%' ],

							label : {
								normal : {
									position : 'inner',
									formatter : '{d}%',

									textStyle : {
										color : '#fff',
										fontWeight : 'bold',
										fontSize : 14
									}
								}
							},
							labelLine : {
								normal : {
									show : false
								}
							},
							data : dataValue
						} ]
					}
					pieChart.setOption(option);
				}
			});
}

function getDatePicker(id) {
	WdatePicker({
		el : document.getElementById(id),
		lang : 'en',
		isShowClear : false,
		isShowToday : false,
		readOnly : true,
		dateFmt : 'yyyy-MM-dd HH:00:00',
		maxDate : '%y-%M-%d %H:%m'
	});
	var startTime = $("#select_time_begin_tab1").val();
	if (startTime != "") {
		$("#timeId").show();
	}
}
function getDatePicker1(id) {
	var startTime = $("#select_time_begin_tab1").val();
	var riqi;
	if (startTime != "") {
		riqi = startTime.split(" ")[0];
		WdatePicker({
			el : document.getElementById(id),
			lang : 'en',
			isShowClear : false,
			isShowToday : false,
			readOnly : true,
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			maxDate : riqi + ' 23:59:59',
			minDate : startTime
		});
		$("#ConfirmId").show();
	}
}
function getDatePicker2(id) {
	WdatePicker({
		el : document.getElementById(id),
		lang : 'en',
		isShowClear : false,
		isShowToday : false,
		readOnly : true,
		dateFmt : 'yyyy-MM-dd HH',
		maxDate : '%y-%M-%d %H '
	});
	var newDate1 = $("#select_time_begin_tab2").val();
	if (newDate1 != "" && newDate1 != lastNewDate1) {
		lastNewDate1 = newDate1;
		nowDate1 = newDate1.split(" ")[0];
		sign1 = parseInt(newDate1.split(" ")[1]);
		if (sign1 == 0) {
			sign1 = 24; // 变为昨天24
			var temp = nowDate1.split("-");
			var date = new Date();
			date.setFullYear(parseInt(temp[0]));
			date.setMonth(parseInt(temp[1]) - 1);
			date.setDate(parseInt(temp[2]) - 1); // nowDate1变成其昨天
			nowDate1 = date.format("yyyy-MM-dd");
		}
		showShopTrend(0, sign1, nowDate1, "shopTrendByHour");
	}
}
function getDatePicker3(id) {

	WdatePicker({
		el : document.getElementById(id),
		lang : 'en',
		isShowClear : false,
		isShowToday : false,
		readOnly : true,
		dateFmt : 'yyyy-MM-dd',
		maxDate : '%y-%M-{%d-1}'
	});
	var newDate2 = $("#select_time_begin_tab3").val();
	if (newDate2 != "" && newDate2 != nowDate2) {
		nowDate2 = newDate2;
		sign2 = nowDate2.split("-")[2];
		showShopTrend(1, sign2, nowDate2, "shopTrendByDay");
	}
}

function setTimes()
{
	var myDate = new Date();
	var yuefen = Number(myDate.getMonth())+1;
	var xiaoshi = Number(myDate.getHours())+1;
	var endTime =  myDate.getFullYear()+"-"+yuefen+"-"+myDate.getDate()+" "+xiaoshi+":"+myDate.getMinutes()+":"+myDate.getSeconds();
	var starTimes = myDate.getFullYear()+"-"+yuefen+"-"+myDate.getDate()+" 00:00:00";
	$("#select_time_begin_tab1").val(starTimes);	
	$("#select_time_end_tab1").val(endTime);	
}