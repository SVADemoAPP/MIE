var id_interval1,totalTimer,id_interval2, heatMapData, mapIds, storeIds, origX, origY, bgImg, scale, coordinate, imgHeight, imgWidth, imgScale, heatmap, heatmap1, timer, floorLoop, floorData;
var nowDate1, nowDate2, sign1, sign2, lastNewDate1, mapIds2, mapIds3,mapIds4;
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

//var refreshHeatmapData = function() {
//	// var times = $("#time").val($.cookie("times"));
//	// var times = $.cookie("times");
//	$
//			.post(
//					"../heatmap/api/getStoreHeatMap?storeId=" + storeIds,
//					function(data) {
//						if (!data.error) {
//							
//								document.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
//										+ data.data;
//							
//						}
//						// heatMap.initTotalData;
//						timer = setTimeout("refreshHeatmapData();", 4000);
//					});
//};
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

// float时间转字符串
var timeToString = function(f) {
	var a = parseInt(f);
	var b = Math.round((f - a) * 60);
	var s = '';
	if (a != 0) {
		s += (a + 'm');
	}
	;
	if (b != 0) {
		s += (b + 's');
	}
	;
	if (s == "") {
		return "0";
	}
	return s;
}

// id:当前数据，id1:百分比;id2：曲线图，nowArray:曲线图数据，nowPeople：今天人数,yesPeople:昨天人数，type:0为人，1位驻留，type1：0位曲线图，1为柱状图
var fuzhiFunction = function(id, id1, id2, nowArray, nowPeople, yesPeople,
		type, type1) {
	var downs = '<i class="im-arrow-down-right2 color-white"></i>';
	var ups = '<i class="im-arrow-up-right3 color-white"></i>';
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
	if (nowPeople >= yesPeople) {
		nowPeopleTemp = ups;
		yanse = colours.green;
		if (nowPeople == 0) {
			nowBaifen = 0;
		}
		if (!nowPeople == 0 && yesPeople == 0) {
			nowBaifen = 100;
		}
		if (nowPeople > 0 && yesPeople > 0) {
			nowBaifen = (((nowPeople) / yesPeople)).toFixed(2)*100;
		}
	} else {
		yanse = colours.red;
		nowPeopleTemp = downs;
		nowBaifen = (((yesPeople-nowPeople) / yesPeople)*100).toFixed(2);
	}
	if(id!="nowPeople")
	{
	document.getElementById(id).innerHTML = types + nowPeople;
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
			barColor : colours.white
		});
	}
}

var refreshTotalData = function() {
	$
			.post(
					"../market/getNewWeekTotal",
					{
						storeId : storeIds
					},
					function(data) {
						if (data.error == 200) {
//							var newData = data.weekNewUsercount;
							var allData = data.weekUsercount;
							var timeData = data.weekDelaytime;
//							var newPeople = data.todayNewUser;
							var nowAllPeople = data.todayUser;
							var nowTime = data.todayAvgDelay;
//							var newArray = mapToarray(newData);
//							newArray.reverse();
							var allArray = mapToarray(allData);
							var timeArray = mapToarray(timeData);
//							var yesNewPeople = newArray[5];
							var yesAllPeople = allArray[6];
							var yesTime = timeArray[6];
//							fuzhiFunction("newPeople", "newPeopleId",
//									"newData", newArray, newPeople,
//									yesNewPeople, 0, 1);
							fuzhiFunction("nowAllcount", "nowAllcountId",
									"allDataId", allArray, nowAllPeople,
									yesAllPeople, 0, 1);
							fuzhiFunction("nowTime", "nowTimeId",
									"timeDataId", timeArray, nowTime,
									yesTime, 1, 0);
							document.getElementById("nowTime").innerHTML = '<i class="ec-clock"></i>'
									+ timeToString(nowTime); // 显示m+s格式的时间字符串
						}
					});
	getTodayTopData();
	clearTimeout(totalTimer);
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
var updateStoreList = function(renderId, data, selectTxt, callback) {
	var sortData = data.sort(function(a, b) {
		return a.name - b.name;
	});
	var len = sortData.length;
	// if (len>0) {
	// mapIds = "2";
	// storeIds = sortData[0].id;
	// }
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

var getTodayTopData = function() {
	$.post("../visitor/getNewTodayTop", {storeId:storeIds}, function(data) {
		if (data.status == 200) {
			showPieCharts(data.mapData, "mall_floor_visitors", 100);
			showBarCharts(data.shopData, "mall_shop_visitors",
					[ colours.red ]);
		}

	});
};

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
		color : [ colours.blue, colours.green, colours.red,
					colours.brown, colours.dark, objColors.yellow,
					objColors.purple, colours.orange ],
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
				return params[0].axisValue.split(":")[0] + ': ' + params[0].value;
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
			show : false
		},
		xaxis : {
			mode : "categories",
			tickLength : 0
		}
	}

	var plot = $.plot($("#stats-pageviews"), [ {
		label : passenger,
		data : data1
	} ], options);
}
var initChartBar = function()
{
	  $.post("/sva/market/getRates?storeId="+storeIds,function(data) {
//		  for (var i = 0; i <= data.eRate.length; i += 1){
//				d1.push([data.shopName[i], data.eRate[i]]);
//				d2.push([data.shopName[i],data.oRate[i]]);
//				d3.push([data.shopName[i], data.dRate[i]]);
//		  }
		  var shopName = data.shopName;
		  var eRate = data.eRate; //进店率
		  var oRate = data.oRate;//溢出率
		  var dRate = data.dRate;//深访率
		  var titile = [EnteringRate,OverflowRate,DeepRate]; 
		  var myChart = echarts.init(document.getElementById("ordered-bars-chart")); 
		  option = {
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:titile
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
				            data : shopName
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name : echardanwei+'(%)'
				        }
				    ],
				    series : [
				        {
				            name:EnteringRate,
				            type:'bar',
						    label:{ 
						    	normal:{ 
						    		show: true,
						    		position:"top"
						    	} 
						    },
				            data:eRate,
				            markLine : {
				                data : [
					                    {type : 'average', name : echarAverage}
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
					                    {type : 'average', name : echarAverage}
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
	 
	   /* var d2 = [];
	    for (var i = 0; i <= 10; i += 1)
	        d2.push([i, parseInt(Math.random() * 30)]);
	 
	    var d3 = [];
	    for (var i = 0; i <= 10; i += 1)
	        d3.push([i, parseInt(Math.random() * 30)]);
	 */
	    
}
var initChartLine = function(data1) {

	// var chartMinDate = data1[0][0]; //first day
	// var chartMaxDate = data1[6][0];//last day
	//
	// var tickSize = [1, "day"];
	// var tformat = "%y/%m/%d";
	//
	// var total = 0;
	// //calculate total earnings for this period
	// for (var i = 0; i < 7; i++) {
	// total = data1[i][1] + total;
	// }

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

	var plot = $.plot($("#line-chart-filled"), [ {
		label : "Average stay",
		data : data1
	} ], options);

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

	var initPeriodHeatmap = function(param) {
		$("#mapContainer1").css("background-image", "");
		var dataObj = {
			// max : pointVal,
			min : 1,
			data : []
		};
		heatmap1.setData(dataObj); // 先清空
		$.post("../heatmap/api/getMapInfoByPosition?mapId=" + mapIds4, function(
				data) {
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
					console.log(imgInfo);
					$("#mapContainer1").css(
							{
								"width" : imgWidth + "px",
								"height" : imgHeight + "px",
								"background-image" : bgImgStr,
								"background-size" : imgWidth + "px "
										+ imgHeight + "px",
								"margin" : "0 auto"
							});

					// configObj1.onExtremaChange = function(data) {
					// };
					// // var times = $.cookie("times");
					// // var times = $("#time").val($.cookie("times"));
					// heatmap1 = h337.create(configObj1);
					$.post("../heatmap/api/getPeriodMapHeatMap", param,
							function(data) {
								if (!data.error) {
									if (data.data && data.data.length > 0) {
										// var points =
										// {max:1,data:dataFilter(data)};
										var points = dataFilter(data.data,
												origX, origY, scale, imgWidth,
												imgHeight, coordinate,
												imgInfo[0]);
										var dataObj = {
											// max : pointVal,
											min : 1,
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
	var initHeatMapChart = function() {
		$(function() {
			// we use an inline data source in the example, usually data would
			// be fetched from a server
			var data = [], totalPoints = 120;
			var res = [];
			while (data.length < totalPoints) {
				data.push(0);
			}
			// var timestamps = new Date().getTime() + 28800000 - 600000
			// for (var i = 0; i < data.length; ++i) {
			// res.push([ timestamps + i * 2000, nowUserCount ]);
			// }
			$.post("/sva/heatmap/api/getMallHeatMap?storeId=" + storeIds,
					function(data) {
						var data = data.data;
						res = mapToarray22(data);
						var options = {
							series : {
								shadowSize : 0, // drawing is faster without
								// shadows
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
								content : passenger+" : %y.0",
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
						var plot = $.plot($("#autoupdate-chart"), [ res ],
								options);
						document.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
							+res[res.length - 1][1];
						// Update interval
						var updateInterval = 4000;

						function update() {
							var timestamp = res[res.length - 1][0] + updateInterval;
							$.post("../heatmap/api/getStoreMomentCount", {
								storeId : storeIds,
								endTime : timestamp - 28800000
							}, function(data) {
								// if (data.error == 200) {
								// nowUserCount = data.todayMomentCount;
								// if (data.length > 0)
								// data = data.slice(1);
								//
								// // do a random walk
								// while (data.length < totalPoints) {
								// // var prev = data.length > 0 ?
								// data[data.length
								// // - 1] : 50;
								// // var y = prev + Math.random() * 10 - 5;
								// // if (y < 0)
								// // y = 0;
								// // if (y > 100)
								// // y = 100;
								// data.push(0);
								// }

								// zip the generated y values with the x values
								document.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
									+data.data;
								res.shift();
								res.push([ timestamp, data.data ]);
								// setup plot
								var options = {
									series : {
										shadowSize : 0, // drawing is faster
										// without
										// shadows
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
										content :  passenger+" : %y.0",
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
								var plot = $.plot($("#autoupdate-chart"),
										[ res ], options);
								// }
							});

						}
						// update();

						id_interval1 = setInterval(update, updateInterval);
					});

		});
	}
	var initTotalData = function() {
		$
				.post(
						"../market/getNewWeekTotal",
						{
							storeId : storeIds
						},
						function(data) {
							if (data.error == 200) {
//								var newData = data.weekNewUsercount;
								var allData = data.weekUsercount;
								var nowUser = data.nowUser;
								var timeData = data.weekDelaytime;
//								var newPeople = data.todayNewUser;
								var nowAllPeople = data.todayUser;
								var nowTime = data.todayAvgDelay;
//								var newArray = mapToarray(newData);
//								newArray.reverse();
								var allArray = mapToarray(allData);
								var timeArray = mapToarray(timeData);
//								var yesNewPeople = newArray[5];
								var yesAllPeople = allArray[6];
								var yesTime = timeArray[6];
								var weekTime = data.allWeekAvgDelay;
								var allArrays = mapToarray2(allData);
								var timeArrays = mapToarray2(timeData);
//								fuzhiFunction("newPeople", "newPeopleId",
//										"newData", newArray, newPeople,
//										yesNewPeople, 0, 1);
								fuzhiFunction("nowAllcount", "nowAllcountId",
										"allDataId", allArray, nowAllPeople,
										yesAllPeople, 0, 1);
								fuzhiFunction("nowPeople", "nowPeopleId", "nowData",
										allArray, nowUser, yesAllPeople, 0, 0);
								fuzhiFunction("nowTime", "nowTimeId",
										"timeDataId", timeArray, nowTime,
										yesTime, 1, 0);
								initMyPieChart(10, 40, 1500, colours);
								initChartPeople(allArrays);
								initChartLine(timeArrays);
								initChartBar();
								document.getElementById("countToday").innerHTML = nowAllPeople;
								document.getElementById("countYesterday").innerHTML = yesAllPeople;
								document.getElementById("countTotal").innerHTML = data.allWeekCount;
								document.getElementById("countToday2").innerHTML = timeToString(nowTime);
								document.getElementById("countYesterday2").innerHTML = timeToString(yesTime);
								document.getElementById("countTotal2").innerHTML = timeToString(weekTime);
								document.getElementById("nowTime").innerHTML = '<i class="ec-clock"></i>'
										+ timeToString(nowTime); // 显示m+s格式的时间字符串
								initHeatMapChart();
							}
						});

		// Update interval
//		var updateDelay = 30000; // 30秒更新一次实时统计
//
//		updateMoment();
//		function updateMoment() {
//			$.post("../market/getMallTotal", {
//				storeId : storeIds
//			}, function(data) {
//				if (data.error == 200) {
//					var nowArray = data.weekMomentCounts;
//					var nowPeople = data.todayMomentCount;
//					var yesPeople = nowArray[6];
//					fuzhiFunction("nowPeople", "nowPeopleId", "nowData",
//							nowArray, nowPeople, yesPeople, 0, 0);
//				}
//			});
//		}
//
//		id_interval2 = setInterval(updateMoment, updateDelay);
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
									console.log(imgInfo);
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
//									$("#mapContainer1").css(
//											{
//												"width" : imgWidth + "px",
//												"height" : imgHeight + "px",
//												"background-image" : bgImgStr,
//												"background-size" : imgWidth
//														+ "px " + imgHeight
//														+ "px",
//												"margin" : "0 auto"
//											});
									configObj.onExtremaChange = function(data) {
									};
									// var times = $.cookie("times");
									// var times =
									// $("#time").val($.cookie("times"));
									heatmap = h337.create(configObj);
									$
											.post(
													"../heatmap/api/getMapHeatMap?mapId="
															+ mapIds,
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
																		imgScale);
																var dataObj = {
																	// max :
																	// pointVal,
																	min : 1,
																	data : points
																};
																heatmap
																		.setData(dataObj);
																document
																		.getElementById("nowPeople").innerHTML = '<i class="ec-users"></i>'
																		+ data.data.length;
																// $("#legend").show();
															}
														}
														// heatMap.initTotalData;
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
	
	var initHeatmapTime = function(callback) {
		$("#mapContainer1").css("background-image", "");
		$("#heatmap1").empty();
		$
				.post(
						"/sva/heatmap/api/getMapInfoByPosition?mapId=" + mapIds4,
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
									console.log(imgInfo);
//									$("#mapContainer").css(
//											{
//												"width" : imgWidth + "px",
//												"height" : imgHeight + "px",
//												"background-image" : bgImgStr,
//												"background-size" : imgWidth
//														+ "px " + imgHeight
//														+ "px",
//												"margin" : "0 auto"
//											});
									$("#mapContainer1").css(
											{
												"width" : imgWidth + "px",
												"height" : imgHeight + "px",
												"background-image" : bgImgStr,
												"background-size" : imgWidth
														+ "px " + imgHeight
														+ "px",
												"margin" : "0 auto"
											});
			

								}
							}
						});
	};
	var bindFloorsClickEvent = function() {
		$(document).on("click", "#divCon .floors", function(e) {
			mapIds = $(this).data("mapid");
//			initHeatmap();
//			getFiveMinuteHeatmap();
			$("#divCon .floors").removeClass("active");
			$(this).addClass("active");
		});
		$(document).on("click", "#divCon1 .floors", function(e) {
			mapIds4 = $(this).data("mapid");
			initHeatmapTime();
			getFiveMinuteHeatmap();
			$("#divCon1 .floors").removeClass("active");
			$(this).addClass("active");
		});
		$(document).on("click", "#divCon2 .floors", function(e) {
			mapIds2 = $(this).data("mapid");
			$("#divCon2 .floors").removeClass("active");
			$(this).addClass("active");
			showMapTrend(0, sign1, nowDate1, "mapTrendByHour", mapIds2);
		});
		$(document).on("click", "#divCon3 .floors", function(e) {
			mapIds3 = $(this).data("mapid");
			$("#divCon3 .floors").removeClass("active");
			$(this).addClass("active");
			showMapTrend(1, sign2, nowDate2, "mapTrendByDay", mapIds3);
		});
	}
	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};

	/* legend code end */
	var cancelDatePick = function() {
		$("#mapContainer1").css("background-image", "");
		$("#select_time_begin_tab1").val("");
		$("#select_time_end_tab1").val("");
		$("#timeId").hide();
		$("#ConfirmId").hide();
		if (heatmap1 != null) {
			var dataObj = {
				// max : pointVal,
				// min : 1,
				data : []
			};
			heatmap1.setData(dataObj);
		}
	}
	var getFiveMinuteHeatmap = function() {
		heatmap1 = h337.create(configObj1); 
		cancelDatePick();
		$.post("../heatmap/api/getFiveMuniteMapHeatMap?mapId=" + mapIds4,
				function(data) {
					if (!data.error) {
						if (data.data && data.data.length > 0) {
							// var points =
							// {max:1,data:dataFilter(data)};
							var points = dataFilter(data.data, origX, origY,
									scale, imgWidth, imgHeight, coordinate,
									imgScale);
							var dataObj = {
								// max : pointVal,
								min : 1,
								data : points
							};
							heatmap1.setData(dataObj);
						}
					}
				});
	}
	var addFloors = function(data) {
		removeFloors();
		for (i = 0; i < data.length; i++) {
			var floor = data[i];
			var html;
			if (i == 0) {
				html = '<div class="floors active" name="floorName" data-mapid="'
						+ floor.mapId
						+ '" style="top:'
						+ (100 + i * 50)
						+ 'px;">' + floor.floor + '</div>';

			} else {
				html = '<div class="floors" name="floorName" data-mapid="'
						+ floor.mapId + '" style="top:' + (100 + i * 50)
						+ 'px;">' + floor.floor + '</div>';

			}
			$('#divCon1').append(html);
			$('#divCon').append(html);
			$('#divCon2').append(html);
			$('#divCon3').append(html);
		}

	}
	var removeFloors = function() {
		$('.floors').remove();
	}
	return {
		// 初始化下拉列表
		initStoreName : function() {
			$.post("../market/api/getData", function(data) {
				if (data.status == 200) {
//					configObj1.onExtremaChange = function(data) {
//					};
					bindFloorsClickEvent(); // 只生成一次
					if (data.data != null && data.data.length > 0) {
						updateStoreList("storeId", data.data);
						storeIds = data.data[0].id;
						getTodayTopData();
						$.post("../map/api/getMapDataByStore", {
							id : storeIds
						}, function(data) {
							if (data.data != null && data.data.length > 0) {
								// updateMapList("mapId", data.data);
								mapIds = data.data[0].mapId;
//								addFloors(data.data);
							} else {
								// alert("无类别数据！");
								// updateMapList("mapId", []);
//								mapIds = -1;
//								removeFloors();
							}
							mapIds4 = mapIds;
							initTotalData();
//							initHeatmap();
//							initHeatmapTime();
//							getFiveMinuteHeatmap();
							mapIds2 = mapIds;
							mapIds3 = mapIds;
//							refreshHeatmapData();
							// 今天
							var date1 = new Date();
							nowDate1 = date1.format("yyyy-MM-dd");
							sign1 = date1.getHours();
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
//							showMapTrend(0, sign1, nowDate1, "mapTrendByHour",
//									mapIds2);
//							showMapTrend(1, sign2, nowDate2, "mapTrendByDay",
//									mapIds3);
						});

					}

				}
			});
		},
		// initDropdown : function() {
		// initMapId(initFloor);
		// },
		bindClickEvent : function() {
			$("select[data-type='storeSelect']").live(
					"change",
					function(e) {
						// mapIds = "2";
						clearTimeout(timer);
						storeIds = $("#storeId").val();
						getTodayTopData();
						clearInterval(id_interval1);
						clearInterval(id_interval2);
						// nowUserCount = null;
//						RouteLine.changeStore(storeIds);
						$.post("../map/api/getMapDataByStore", {
							id : storeIds
						}, function(data) {
							if (data.data != null && data.data.length > 0) {
								// updateMapList("mapId", data.data);
								mapIds = data.data[0].mapId;
//								addFloors(data.data);
							} else {
								// alert("无类别数据！");
								// updateMapList("mapId", []);
//								mapIds = -1;
//								removeFloors();
							}
							initTotalData();
//							initHeatmap();
							mapIds2 = mapIds;
							mapIds3 = mapIds;
							mapIds4 = mapIds;
//							getFiveMinuteHeatmap();
//							showMapTrend(0, sign1, nowDate1, "mapTrendByHour",
//									mapIds2);
//							showMapTrend(1, sign2, nowDate2, "mapTrendByDay",
//									mapIds3);
						});

					});

			$("#heatMapConfirm").on("click", function(e) {
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
					mapId : mapIds4,
					startTime : startTime,
					endTime : endTime
				};
				initPeriodHeatmap(param);
			});
		}

	};

}();
$(document).ready(function() {
	heatMap.initStoreName();
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
		barColor:'#97d3c5',
		gray : objColors.gray
	}
	showColors = [ colours.blue, colours.yellow, colours.red, colours.green,colours.dark,colours.brown,colours.orange,colours.purple,colours.pink,colours.lime,colours.magenta,colours.teal, colours.gray];
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

/* 楼层动向统计饼图 */
function showMapTrend(myType, mySign, myTime, target, myMapId) {
	$.post("../map/api/getMapTrend", {
		mapId : myMapId,
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
						titleText = "hour-" + mySign;
					} else if (myType == 1) {
						titleText = "day-" + mySign;
					}
					option = {
						backgroundColor : '#fff',
						title : {
							text : titleText,
							subtext : myTime,
							x : 'center',
							y : 'center',
							textStyle : {
								fontWeight : 'normal',
								fontSize : 16
							}
						},
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
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
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
		showMapTrend(0, sign1, nowDate1, "mapTrendByHour", mapIds2);
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
		showMapTrend(1, sign2, nowDate2, "mapTrendByDay", mapIds3);
	}
}