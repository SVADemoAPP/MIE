//------------- Dashboard.js -------------//
var nowUserId;
function loadData() {
	var nowUserName = document.getElementById("info_userName").innerHTML;
	if (nowUserName != "admin") {
		requestData(nowUserName);
	}
}
$('#userName_search').bind('click', function() {
	requestData($("#userName").val());
});
document.onkeydown = function() {
	var evt = window.event || arguments[0];
	if (evt && evt.keyCode == 13 && $("#userName").is(":focus")) {
		requestData($("#userName").val());
		return false;
	}

}
function requestData(nowUserName) {
	$
			.post(
					"/sva/user/getUserByUserName",
					{
						userName : nowUserName
					},
					function(data) {
						if (data.data == null) {
							$('#div_noUser').show();
							$("#userName").val("");
						} else {
							$('#div_noUser').hide();
							nowUserId = data.data.id;
							document.getElementById("info_userName").innerHTML = data.data.userName;
							document.getElementById("info_age").innerHTML = getAgeByBirth(data.data.birthday);
							document.getElementById("info_gender").innerHTML = data.data.gender == 0 ? "男"
									: "女";
							document.getElementById("info_profession").innerHTML = data.data.profession;
							document.getElementById("info_hobby").innerHTML = data.data.hobby;
							$
									.post(
											"/sva/cost/getUserAttrNowDay",
											{
												userId : nowUserId
											},
											function(response) {
												if (response.status != 200) {
													return;
												}
												document
														.getElementById("info_todayCost").innerHTML = response.totalSum;
												document
														.getElementById("info_todayStay").innerHTML = response.delayTime;
												if (response.sumByShop.length != 0) {
													document
															.getElementById("info_shop").innerHTML = response.sumByShop[0].name;
												}
											});

							$
									.post(
											"/sva/cost/getUserAttrLastMonth",
											{
												userId : data.data.id
											},
											function(response) {
												if (response.status != 200) {
													return;
												}
												var dataSumEveryDay = response.sumEveryDay;
												var dataDelayTimeEveryDay = response.delayTimeEveryDay;
												showDemo1("#monthCostSum",
														dataSumEveryDay);
												// showDemo("#monthStaySum");
												showDemo2("#monthStaySum",
														dataDelayTimeEveryDay);

											});

							$
									.post(
											"/sva/cost/getUserAttrLastYear",
											{
												userId : data.data.id
											},
											function(response) {
												if (response.status != 200) {
													return;
												}
												var dataSumEveryMonth = response.sumEveryMonth;
												var dataDelayTimeEveryMonth = response.delayTimeEveryMonth;
												// showFirstChart("line-chart-filled",dataSumEveryMonth);
												// showFirstChart("line-chart-dots2",dataDelayTimeEveryMonth);
												// showDemo("#line-chart-filled");
												// showDemo("#line-chart-dots2");
												showDemo3("#line-chart-filled",
														dataSumEveryMonth);
												showDemo4("#line-chart-dots2",
														dataDelayTimeEveryMonth);
											});
							$
									.post(
											"/sva/cost/getUserHobby",
											{
												userId : data.data.id
											},
											function(response) {
												if (response.status != 200) {
													return;
												}
												var monthSumByCategory = response.monthSumByCategory;

												var yearSumByCategory = response.yearSumByCategory;

												var monthStaySumByCategory = response.monthStaySumByCategory;

												var yearStaySumByCategory = response.yearStaySumByCategory;

												var monthCountByCategory = response.monthCountByCategory;

												var yearCountByCategory = response.yearCountByCategory;

												var monthStayCountByCategory = response.monthStayCountByCategory;
												var yearStayCountByCategory = response.yearStayCountByCategory;
												showHobbyByCategoryChart(monthSumByCategory);
												showStayByCategoryChart(monthStaySumByCategory);

											});

						}

					});
}

function showDemo1(target, dataValue) {
	// first line chart
	var d1 = [];
	// var xiaofeiData = [ 23, 131, 231, 231, 123, 213, 333 ];
	// here we generate randomdata data for chart
	for (var i = -30; i < 0; i++) {
		d1.push([ new Date(Date.today().add(i).days()).getTime(),
				dataValue[30 + i].value ]);
	}

	var chartMinDate = d1[0][0]; // first day
	var chartMaxDate = d1[29][0];// last day

	var tickSize = [ 3, "day" ];
	var tformat = "%m-%d";

	var total = 0;
	// calculate total earnings for this period
	for (var i = 0; i < 30; i++) {
		total = d1[i][1] + total;
	}

	var options = {
		grid : {
			show : true,
			aboveData : true,
			color : colours.white,
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
				fill : false,
				lineWidth : 2.5
			},
			points : {
				show : true,
				radius : 4,
				lineWidth : 2.5,
				fill : true,
				fillColor : colours.blue
			}
		},
		colors : [ '#fcfcfc' ],
		legend : {
			position : "ne",
			margin : [ 0, -25 ],
			noColumns : 0,
			labelBoxBorderColor : null,
			labelFormatter : function(label, series) {
				return '<div style="padding: 10px; font-size:20px;font-weight:bold;">'
						+ 'Total:' + total + '</div>';
			},
			backgroundColor : colours.blue,
			backgroundOpacity : 0.5,
			hideSquare : true
		// hide square color helper
		},
		shadowSize : 0,
		tooltip : true, // activate tooltip
		tooltipOpts : {
			content : "%y.0",
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

	var plot = $.plot($(target), [ {
		label : "Earnings",
		data : d1,
	} ], options);

};
function showDemo2(target, dataValue) {
	// first line chart
	var d1 = [];
	// var xiaofeiData = [ 23, 131, 231, 231, 123, 213, 333 ];
	// here we generate randomdata data for chart
	for (var i = -30; i < 0; i++) {
		d1.push([ new Date(Date.today().add(i).days()).getTime(),
				dataValue[30 + i].value ]);
	}

	var chartMinDate = d1[0][0]; // first day
	var chartMaxDate = d1[29][0];// last day

	var tickSize = [ 3, "day" ];
	var tformat = "%m-%d";

	var total = 0;
	// calculate total earnings for this period
	for (var i = 0; i < 30; i++) {
		total = d1[i][1] + total;
	}

	var options = {
		grid : {
			show : true,
			aboveData : true,
			color : colours.white,
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
				fill : false,
				lineWidth : 2.5
			},
			points : {
				show : true,
				radius : 4,
				lineWidth : 2.5,
				fill : true,
				fillColor : colours.green
			}
		},
		colors : [ '#fcfcfc' ],
		legend : {
			position : "ne",
			margin : [ 0, -10 ],
			noColumns : 0,
			labelBoxBorderColor : null,
			labelFormatter : function(label, series) {
				return '<div style="padding: 10px; font-size:20px;font-weight:bold;">'
						+ 'Total:' + total + '</div>';
			},
			backgroundColor : colours.green,
			backgroundOpacity : 0.5,
			hideSquare : true
		// hide square color helper
		},
		shadowSize : 0,
		tooltip : true, // activate tooltip
		tooltipOpts : {
			content : "%y.0",
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

	var plot = $.plot($(target), [ {
		label : "Earnings",
		data : d1,
	} ], options);

};

// ------------- Pageview chart -------------//
function showDemo3(target, dataValue) {
	// first line chart
	var d1 = [];
	var dataX = [];
	// var xiaofeiData = [ 23, 131, 231, 231, 123, 213, 333 ];
	// here we generate randomdata data for chart
	for (var i = 0; i < 12; i++) {
		dataX.push(dataValue[i].name);
		d1.push([ dataValue[i].name.substring(2), dataValue[i].value ]);
	}

	// var chartMinDate = d1[0][0]; // first day
	// var chartMaxDate = d1[29][0];// last day
	//
	// var tickSize = [ 3, "day" ];
	// var tformat = "%m-%d";

	// visiotrs
	// var d1 = [["MON", randNum()], ["TUE", randNum()], ["WED", randNum()],
	// ["THU", randNum()], ["FRI", randNum()], ["SAT", randNum()], ["SUN",
	// randNum()]];
	// var d2 = [["MON", randNum()], ["TUE", randNum()], ["WED", randNum()],
	// ["THU", randNum()], ["FRI", randNum()], ["SAT", randNum()], ["SUN",
	// randNum()]];

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
			tickLength : 0,
			data : dataX

		}
	}

	var plot = $.plot($(target), [ {
		label : "cost",
		data : d1,
	} ], options);

};

function showDemo4(target, dataValue) {
	var d1 = [];
	var dataX = [];
	// var xiaofeiData = [ 23, 131, 231, 231, 123, 213, 333 ];
	// here we generate randomdata data for chart
	for (var i = 0; i < 12; i++) {
		dataX.push(dataValue[i].name);
		d1.push([ dataValue[i].name.substring(2), dataValue[i].value ]);
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
			minBorderMargin : 20,
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
			xDateFormat : "%d/%m",
			shifts : {
				x : -30,
				y : -50
			},
			theme : 'dark',
			defaultTheme : false
		},
		yaxis : {
			show : false
		},
		xaxis : {
			mode : "categories",
			tickLength : 0,
			data : dataX

		}
	}

	var plot = $.plot($(target), [ {
		label : "Stay Munites",
		data : d1
	} ], options);

};
/* 显示带一个属性的图表 */
function showFirstChart(target, dataValue) {

	var dataX = [];
	for (i = 0; i < dataValue.length; i++) {
		dataX.push(dataValue[i].name);
	}
	var oneChart = echarts.init(document.getElementById(target));

	option = {
		tooltip : {
			trigger : 'axis'
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : dataX
		},
		yAxis : {
			type : 'value',
		},
		series : {
			name : "消费额",
			type : 'line',
			data : dataValue
		}
	};
	oneChart.setOption(option);
};

/* 驻留关系图 */
function showStayByCategoryChart(dataValue) {
	var dataX = [];
	var dataArray = [];
	var linksArray = [];
	var categoriesArray = [];
	var dataColor = [];

	if (dataValue.length != 0) {
		dataColor.push(colours.red);
		dataArray.push({
			"name" : "allStay",
			"symbolSize" : 20,
			"draggable" : "true"
		});
	}
	for (i = 0; i < dataValue.length; i++) {
		if (dataValue[i].name != "other") {
			dataColor.push(showColors[i]);
		} else {
			dataColor.push(colours.brown);
		}
		dataX.push(dataValue[i].name);
		dataArray.push({
			"name" : dataValue[i].name,
			"symbolSize" : 60 - i * 6,
			"category" : dataValue[i].name,
			"draggable" : "true",
			"value" : dataValue[i].value
		});
		linksArray.push({
			"source" : "allStay",
			"target" : dataValue[i].name
		});
		categoriesArray.push({
			"name" : dataValue[i].name
		});
	}
	var pieChart = echarts.init(document.getElementById("monthStayCategory"));
	option = {
		title : {
			text : "",
			top : "top",
			left : "center"
		},
		tooltip : {},
		legend : [ {
			tooltip : {
				show : true
			},
			selectedMode : 'false',
			bottom : 20,
			data : dataX
		} ],
		color : dataColor,
		toolbox : {
			show : false,
			feature : {
				dataView : {
					show : true,
					readOnly : true
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		animationDuration : 3000,
		animationEasingUpdate : 'quinticInOut',
		calculable : false,
		series : [ {
			name : "",
			type : 'graph',
			layout : 'force',

			force : {
				repulsion : 600,
				edgeLength : 60
			// 默认距离
			},
			data : dataArray,
			links : linksArray,
			categories : categoriesArray,
			focusNodeAdjacency : true,
			roam : true,
			label : {
				normal : {

					show : true,
					position : 'top',

				}
			},
			lineStyle : {
				normal : {
					color : 'source',
					curveness : 0,
					type : "solid"
				}
			}
		} ]
	};

	pieChart.setOption(option);
	pieChart.on('click', function(params) {
		console.log(params);
		initStayShopChart(params.data.category, params.color, params.color);
	});
	if (dataX.length == 0) {
		echarts.init(document.getElementById("monthStayShop")).clear();
	} else {
		initStayShopChart(dataX[0], colours.blue, colours.blue);
	}
}

/* 加载右侧的shop分布表 */
function initStayShopChart(categoryName, startColor, endColor) {
	if (categoryName == "other") {
		return;
	}
	$.post("/sva/cost/getUserStayByCategory", {
		userId : nowUserId,
		selectType : 2,
		categoryName : categoryName
	}, function(response) {
		if (response.status != 200) {
			return;
		}
		if (response.data.length == 1) {
			$('#monthStayShop').height(185);
		} else {
		$('#monthStayShop').height(115 + 37 * response.data.length);
		};
		showStayShopChat("Now Category：" + categoryName, response.data,
				startColor, endColor);
	});
}
/* 类别下的店铺表 */
function showStayShopChat(titleText, dataValue, startColor, endColor) {
	var chart = echarts.init(document.getElementById("monthStayShop"));
	dataValue.reverse();
	var dataX = [];
	for (i = 0; i < dataValue.length; i++) {
		dataX.push(dataValue[i].name);
	}
	option = {
		title : {
			text : titleText,
			textStyle : {
				color : '#bcbfff',
				fontWeight : 'bold',
				fontSize : 20
			},
			top : '4%',
			left : '2.2%'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '10%',
			bottom : '3%',
			containLabel : true
		},
		yAxis : {
			type : 'category',
			data : dataX,
			axisLine : {
				show : false
			},
			axisTick : {
				show : false,
				alignWithLabel : true
			},
			axisLabel : {
				textStyle : {
					color : '#ffb069'
				}
			}
		},
		xAxis : [ {
			type : 'value',
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			axisLabel : {
				show : false
			},
			splitLine : {
				show : false
			}
		} ],

		series : [ {
			name : "Stay Minutes",
			type : 'bar',
			data : dataValue,
			barCategoryGap : '35%',
			label : {
				normal : {
					show : true,
					position : 'right',
					formatter : function(params) {
						return params.data.value;
					},
					textStyle : {
						color : '#bcbfff' // color of value
					}
				}
			},
			itemStyle : {
				normal : {
					color : new echarts.graphic.LinearGradient(0, 0, 1, 0, [ {
						offset : 0,
						color : startColor
					// 0% 处的颜色
					}, {
						offset : 1,
						color : endColor
					// 100% 处的颜色
					} ], false)
				}
			}
		} ]
	};
	chart.setOption(option);
}

/* 消费饼图 */
function showHobbyByCategoryChart(dataValue) {
	var dataX = [];
	var dataColor = [];
	for (i = 0; i < dataValue.length; i++) {
		dataX.push(dataValue[i].name);
		if (dataValue[i].name != "other") {
			dataColor.push(showColors[i]);
		} else {
			dataColor.push(colours.brown);
		}
	}
	var pieChart = echarts.init(document.getElementById("monthCostCategory"));
	option = {
		backgroundColor : '#fff',
		title : {
			text : "totalCost",
			subtext : "",
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
			radius : [ '25%', '65%' ],

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
	};

	pieChart.setOption(option);
	pieChart.on('click', function(params) {
		console.log(params);
		initHobbyShopChart(params.data.name, params.color, params.color);
	});
	if (dataX.length == 0) {
		echarts.init(document.getElementById("monthCostShop")).clear();
	} else {
		initHobbyShopChart(dataX[0], colours.blue, colours.blue);
	}
}
/* 加载右侧的shop分布表 */
function initHobbyShopChart(categoryName, startColor, endColor) {
	if (categoryName == "other") {
		return;
	}
	$.post("/sva/cost/getUserHobbyByCategory", {
		userId : nowUserId,
		selectType : 0,
		categoryName : categoryName
	}, function(response) {
		if (response.status != 200) {
			return;
		}
		if (response.data.length == 1) {
			$('#monthCostShop').height(185);
		} else {
			$('#monthCostShop').height(115 + 37 * response.data.length);
		}
		;
		showHobbyShopChat("Now Category：" + categoryName, response.data,
				startColor, endColor);
	});
}
/* 类别下的店铺表 */
function showHobbyShopChat(titleText, dataValue, startColor, endColor) {
	var chart = echarts.init(document.getElementById("monthCostShop"));
	dataValue.reverse();
	var dataX = [];
	for (i = 0; i < dataValue.length; i++) {
		dataX.push(dataValue[i].name);
	}
	option = {
		title : {
			text : titleText,
			textStyle : {
				color : '#bcbfff',
				fontWeight : 'bold',
				fontSize : 20
			},
			top : '4%',
			left : '2.2%'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '10%',
			bottom : '3%',
			containLabel : true
		},
		yAxis : {
			type : 'category',
			data : dataX,
			axisLine : {
				show : false
			},
			axisTick : {
				show : false,
				alignWithLabel : true
			},
			axisLabel : {
				textStyle : {
					color : '#ffb069'
				}
			}
		},
		xAxis : [ {
			type : 'value',
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			axisLabel : {
				show : false
			},
			splitLine : {
				show : false
			}
		} ],

		series : [ {
			name : "Stay Minutes",
			type : 'bar',
			data : dataValue,
			barCategoryGap : '35%',
			label : {
				normal : {
					show : true,
					position : 'right',
					formatter : function(params) {
						return params.data.value;
					},
					textStyle : {
						color : '#bcbfff' // color of value
					}
				}
			},
			itemStyle : {
				normal : {
					color : new echarts.graphic.LinearGradient(0, 0, 1, 0, [ {
						offset : 0,
						color : startColor
					// 0% 处的颜色
					}, {
						offset : 1,
						color : endColor
					// 100% 处的颜色
					} ], false)
				}
			}
		} ]
	};
	chart.setOption(option);
}
var colours;
var showColors;
$(document)
		.ready(
				function() {
					// get object with colros from plugin and store it.
					var objColors = $('body').data('sprFlat').getColors();
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
					showColors = [ colours.blue, colours.pink, colours.green,
							colours.yellow ];
					loadData();

					// ------------- Add carouse to tiles -------------//
					$('.carousel-tile').carousel({
						interval : 6000
					});

					// generate random number for charts
					randNum = function() {
						return (Math.floor(Math.random() * (1 + 150 - 50))) + 80;
					}
					// ------------- Earnings chart -------------//
					$(function() {

						// first line chart
						var d1 = [];
						// here we generate randomdata data for chart
						for (var i = 0; i < 8; i++) {
							d1.push([
									new Date(Date.today().add(i).days())
											.getTime(), randNum() ]);
						}

						var chartMinDate = d1[0][0]; // first day
						var chartMaxDate = d1[7][0];// last day

						var tickSize = [ 1, "day" ];
						var tformat = "%d/%m/%y";

						var total = 0;
						// calculate total earnings for this period
						for (var i = 0; i < 8; i++) {
							total = d1[i][1] + total;
						}

						var options = {
							grid : {
								show : true,
								aboveData : true,
								color : colours.white,
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
									fill : false,
									lineWidth : 2.5
								},
								points : {
									show : true,
									radius : 4,
									lineWidth : 2.5,
									fill : true,
									fillColor : colours.blue
								}
							},
							colors : [ '#fcfcfc' ],
							legend : {
								position : "ne",
								margin : [ 0, -25 ],
								noColumns : 0,
								labelBoxBorderColor : null,
								labelFormatter : function(label, series) {
									return '<div style="padding: 10px; font-size:20px;font-weight:bold;">'
											+ 'Total: $' + total + '</div>';
								},
								backgroundColor : colours.blue,
								backgroundOpacity : 0.5,
								hideSquare : true
							// hide square color helper
							},
							shadowSize : 0,
							tooltip : true, // activate tooltip
							tooltipOpts : {
								content : "$%y.0",
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

						var plot = $.plot($("#stats-earnings"), [ {
							label : "Earnings",
							data : d1,
						} ], options);

					});
					// second bars chart
					$(function() {

						var data = [ [ "JAN", 1500 ], [ "FEB", 1345 ],
								[ "MAR", 1800 ], [ "APR", 1670 ],
								[ "MAY", 1780 ], [ "JUN", 1500 ],
								[ "JUL", 1350 ], [ "AUG", 1700 ],
								[ "SEP", 1890 ], [ "OCT", 2000 ],
								[ "NOV", 1950 ], [ "DEC", 2000 ] ];

						// Replicate the existing bar data to reproduce bar fill
						// effect
						var arr = [];
						for (var i = 0; i <= data.length - 1; i++) {
							arr.push(data[i][1]);
						}
						;
						var largest = Math.max.apply(Math, arr) + 50;
						d1 = [];
						for (var i = 0; i <= data.length - 1; i++) {
							sum = largest - data[i][1];
							d1.push([ data[i][0], sum ]);
						}
						;

						var options = {
							series : {
								stack : true
							},
							bars : {
								show : true,
								barWidth : 0.6,
								fill : 1,
								align : "center"
							},
							grid : {
								show : true,
								hoverable : true,
								borderWidth : 0,
								borderColor : null
							},
							colors : [ colours.green, colours.gray ],
							tooltip : true, // activate tooltip
							tooltipOpts : {
								content : "$%y.0",
								shifts : {
									x : -30,
									y : -50
								}
							},
							yaxis : {
								tickLength : 0,
								show : false
							},
							xaxis : {
								mode : "categories",
								tickLength : 0
							}
						};

						$
								.plot($("#stats-earnings-bars"), [ data, d1 ],
										options);
					});

					// second donut chart
					$(function() {
						var options = {
							series : {
								pie : {
									show : true,
									innerRadius : 0.55,
									highlight : {
										opacity : 0.1
									},
									radius : 1,
									stroke : {
										width : 10
									},
									startAngle : 2.15
								}
							},
							legend : {
								show : true,
								labelFormatter : function(label, series) {
									return '<div style="font-weight:bold;font-size:13px;">'
											+ label + '</div>'
								},
								labelBoxBorderColor : null,
								margin : 50,
								width : 20,
								padding : 1
							},
							grid : {
								hoverable : true,
								clickable : true,
							},
							tooltip : true, // activate tooltip
							tooltipOpts : {
								content : "%s : %y.1" + "%",
								shifts : {
									x : -30,
									y : -50
								},
								theme : 'dark',
								defaultTheme : false
							}
						};
						var data = [ {
							label : "Coding",
							data : 68,
							color : colours.red
						}, {
							label : "Design",
							data : 20,
							color : colours.green
						}, {
							label : "SEO",
							data : 12,
							color : colours.blue
						},

						];
						$.plot($("#stats-category-earnings"), data, options);

					});

					// ------------- Pageview chart -------------//
					$(function() {

						// visiotrs
						var d1 = [ [ "MON", randNum() ], [ "TUE", randNum() ],
								[ "WED", randNum() ], [ "THU", randNum() ],
								[ "FRI", randNum() ], [ "SAT", randNum() ],
								[ "SUN", randNum() ] ];
						var d2 = [ [ "MON", randNum() ], [ "TUE", randNum() ],
								[ "WED", randNum() ], [ "THU", randNum() ],
								[ "FRI", randNum() ], [ "SAT", randNum() ],
								[ "SUN", randNum() ] ];

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
						};

						var plot = $.plot($("#stats-pageviews"), [ {
							label : "Visitors",
							data : d1
						}, {
							label : "Return visitors",
							data : d2
						} ], options);

					});

					// ------------- Weather icons -------------//
					var today = new Skycons({
						"color" : colours.white,
						"resizeClear" : true
					});
					today.set("weather-now", "rain");
					today.play();
					// set forecast icons too
					var forecast = new Skycons({
						"color" : colours.dark,
						"resizeClear" : true
					});
					forecast.set("forecast-now", "rain");
					forecast.set("forecast-day1", "partly-cloudy-day");
					forecast.set("forecast-day2", "clear-day");
					forecast.set("forecast-day3", "wind");
					forecast.play();

					// ------------- Add sortable function to todo
					// -------------//
					$(function() {
						$("#today, #tomorrow").sortable({
							connectWith : ".todo-list",
							placeholder : 'placeholder',
							forcePlaceholderSize : true,
						}).disableSelection();
					});

					// ------------- Instagram widget carousel -------------//
					$('#instagram-widget').carousel({
						interval : 4000
					});

					// ------------- Full calendar -------------//
					$(function() {
						var date = new Date();
						var d = date.getDate();
						var m = date.getMonth();
						var y = date.getFullYear();

						$('#calendar').fullCalendar({
							header : {
								left : 'prev,next today',
								center : 'title',
								right : 'month,agendaWeek,agendaDay'
							},
							buttonText : {
								prev : '<i class="en-arrow-left8 s16"></i>',
								next : '<i class="en-arrow-right8 s16"></i>',
								today : 'Today'
							},
							editable : true,
							events : [ {
								title : 'All Day Event',
								start : new Date(y, m, 1),
								backgroundColor : colours.green,
								borderColor : colours.green
							}, {
								title : 'Long Event',
								start : new Date(y, m, d - 5),
								end : new Date(y, m, d - 2),
								backgroundColor : colours.red,
								borderColor : colours.red
							}, {
								id : 999,
								title : 'Repeating Event',
								start : new Date(y, m, d - 3, 16, 0),
								allDay : false
							}, {
								id : 999,
								title : 'Repeating Event',
								start : new Date(y, m, d + 4, 16, 0),
								allDay : false
							}, {
								title : 'Meeting',
								start : new Date(y, m, d, 10, 30),
								allDay : false,
								backgroundColor : colours.orange,
								borderColor : colours.orange
							}, {
								title : 'Lunch',
								start : new Date(y, m, d, 12, 0),
								end : new Date(y, m, d, 14, 0),
								allDay : false,
								backgroundColor : colours.teal,
								borderColor : colours.teal
							}, {
								title : 'Birthday Party',
								start : new Date(y, m, d + 1, 19, 0),
								end : new Date(y, m, d + 1, 22, 30),
								allDay : false,
								backgroundColor : colours.pink,
								borderColor : colours.pink
							}, {
								title : 'Click for SuggeElson',
								start : new Date(y, m, 28),
								end : new Date(y, m, 29),
								url : 'http://suggeelson.com/',
								backgroundColor : colours.dark,
								borderColor : colours.dark
							} ]
						});
					});

					// ------------- Sparklines -------------//
					$('#usage-sparkline').sparkline(
							[ 35, 46, 24, 56, 68, 35, 46, 24, 56, 68 ], {
								width : '180px',
								height : '30px',
								lineColor : colours.dark,
								fillColor : false,
								spotColor : false,
								minSpotColor : false,
								maxSpotColor : false,
								lineWidth : 2
							});

					$('#cpu-sparkline').sparkline(
							[ 22, 78, 43, 32, 55, 67, 83, 35, 44, 56 ], {
								width : '180px',
								height : '30px',
								lineColor : colours.dark,
								fillColor : false,
								spotColor : false,
								minSpotColor : false,
								maxSpotColor : false,
								lineWidth : 2
							});

					$('#ram-sparkline').sparkline(
							[ 12, 24, 32, 22, 15, 17, 8, 23, 17, 14 ], {
								width : '180px',
								height : '30px',
								lineColor : colours.dark,
								fillColor : false,
								spotColor : false,
								minSpotColor : false,
								maxSpotColor : false,
								lineWidth : 2
							});

					// ------------- Init pie charts -------------//
					initPieChart(10, 40, 1500, colours);

				});

// Setup easy pie charts in sidebar
var initPieChart = function(lineWidth, size, animateTime, colours) {
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
}
/* 根据出生日期算出年龄 */
function getAgeByBirth(strBirthday) {
	var returnAge;
	var d1 = new Date(strBirthday);
	var birthYear = d1.getFullYear();
	var birthMonth = d1.getMonth() + 1;
	var birthDay = d1.getDate();
	d = new Date();
	var nowYear = d.getFullYear();
	var nowMonth = d.getMonth() + 1;
	var nowDay = d.getDate();
	if (nowYear == birthYear) {
		returnAge = 0;// 同年 则为0岁
	} else {
		var ageDiff = nowYear - birthYear; // 年之差
		if (ageDiff > 0) {
			if (nowMonth == birthMonth) {
				var dayDiff = nowDay - birthDay;// 日之差
				if (dayDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			} else {
				var monthDiff = nowMonth - birthMonth;// 月之差
				if (monthDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			}
		} else {
			returnAge = -1;// 返回-1 表示出生日期输入错误 晚于今天
		}
	}
	return returnAge;// 返回周岁年龄
}