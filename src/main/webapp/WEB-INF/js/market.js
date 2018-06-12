//------------- Dashboard.js -------------//
$(document).ready(function() {
	heatMap.initShopName();
	heatMap.bindClickEvent();
	//get object with colros from plugin and store it.
	var objColors = $('body').data('sprFlat').getColors();
	var colours = {
		white: objColors.white,
		dark: objColors.dark,
		red : objColors.red,
		blue: objColors.blue,
		green : objColors.green,
		yellow: objColors.yellow,
		brown: objColors.brown,
		orange : objColors.orange,
		purple : objColors.purple,
		pink : objColors.pink,
		lime : objColors.lime,
		magenta: objColors.magenta,
		teal: objColors.teal,
		textcolor: '#5a5e63',
		gray: objColors.gray
	}

	//------------- Add carouse to tiles -------------//
	$('.carousel-tile').carousel({
		interval:   6000
	});

 	//generate random number for charts
	randNum = function(){
		return (Math.floor( Math.random()* (1+150-50) ) ) + 80;
	}



	//------------- Pageview chart -------------//
	$(function() {
		var data =  [82,88,93,76,85,88,75];
		var allData  = [];
		for(var i=0;i<data.length;i++){
			var beginDt = new Date();
			var vasw = 7-i;
			beginDt.setDate(beginDt.getDate() - vasw)
			var dataArray  = [];
		  var d = beginDt.getYear() + "/" +(beginDt.getMonth() + 1) + "/" + beginDt.getDate();
		  dataArray.push(d.substring(1,d.length));
		  dataArray.push(data[i]);
		  allData.push(dataArray);
		}
		
		//visiotrs
//		var d1 = [["MON", randNum()], ["TUE", randNum()], ["WED", randNum()], ["THU", randNum()], ["FRI", randNum()], ["SAT", randNum()], ["SUN", randNum()]];
//		var d2 = [["MON", randNum()], ["TUE", randNum()], ["WED", randNum()], ["THU", randNum()], ["FRI", randNum()], ["SAT", randNum()], ["SUN", randNum()]];
		
    	var options = {
    		grid: {
				show: true,
			    labelMargin: 20,
			    axisMargin: 40, 
			    borderWidth: 0,
			    borderColor:null,
			    minBorderMargin: 20,
			    clickable: true, 
			    hoverable: true,
			    autoHighlight: true,
			    mouseActiveRadius: 100
			},
			series: {
				grow: {
		            active: true,
		     		duration: 1000
		        },
	            lines: {
            		show: true,
            		fill: false,
            		lineWidth: 2.5
	            },
	            points: {
	            	show:true,
	            	radius: 5,
	            	lineWidth: 3.0,
	            	fill: true,
	            	fillColor: colours.red,
	            	strokeColor: colours.white 
	            }
	        },
	        colors: [colours.dark, colours.blue],
	        legend: { 
	        	show:true,
	        	position: "ne", 
	        	margin: [0,-25], 
	        	noColumns: 0,
	        	labelBoxBorderColor: null,
	        	labelFormatter: function(label, series) {
				    return '&nbsp;'+label+'&nbsp;&nbsp;';
				},
				width: 40,
				height: 1
	    	},
	        shadowSize:0,
	        tooltip: true, //activate tooltip
			tooltipOpts: {
				content: "%y.0",
				shifts: {
					x: -45,
					y: -50
				},
				defaultTheme: false
			},
			yaxis: { 
				show:false
			},
			xaxis: { 
	        	mode: "categories",
	        	tickLength: 0
	        }
    	}

		var plot = $.plot($("#stats-pageviews"),[
			{
    			label: "Passenger number", 
    			data: allData
    		}
    		], options
    	);

	});

	//------------- Weather icons -------------//
	var today = new Skycons({
		"color": colours.white,
		"resizeClear": true
	});
	today.set("weather-now", "rain");
	today.play();
   	//set forecast icons too
   	var forecast = new Skycons({
		"color": colours.dark,
		"resizeClear": true
	});
   	forecast.set("forecast-now", "rain");
   	forecast.set("forecast-day1", "partly-cloudy-day");
   	forecast.set("forecast-day2", "clear-day");
   	forecast.set("forecast-day3", "wind");
   	forecast.play();

   	//------------- Add sortable function to todo -------------//
	$(function() {
	    $( "#today, #tomorrow" ).sortable({
	    	connectWith: ".todo-list",
	    	placeholder: 'placeholder',  
        	forcePlaceholderSize: true, 
	    }).disableSelection();
	});

	//------------- Instagram widget carousel -------------//
	$('#instagram-widget').carousel({
		interval:   4000
	});

	//------------- Full calendar -------------//
	$(function(){
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			buttonText: {
	        	prev: '<i class="en-arrow-left8 s16"></i>',
	        	next: '<i class="en-arrow-right8 s16"></i>',
	        	today:'Today'
	    	},
			editable: true,
			events: [
				{
					title: 'All Day Event',
					start: new Date(y, m, 1),
					backgroundColor: colours.green,
					borderColor: colours.green
				},
				{
					title: 'Long Event',
					start: new Date(y, m, d-5),
					end: new Date(y, m, d-2),
					backgroundColor: colours.red,
					borderColor: colours.red
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d-3, 16, 0),
					allDay: false
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d+4, 16, 0),
					allDay: false
				},
				{
					title: 'Meeting',
					start: new Date(y, m, d, 10, 30),
					allDay: false,
					backgroundColor: colours.orange,
					borderColor: colours.orange
				},
				{
					title: 'Lunch',
					start: new Date(y, m, d, 12, 0),
					end: new Date(y, m, d, 14, 0),
					allDay: false,
					backgroundColor: colours.teal,
					borderColor: colours.teal
				},
				{
					title: 'Birthday Party',
					start: new Date(y, m, d+1, 19, 0),
					end: new Date(y, m, d+1, 22, 30),
					allDay: false,
					backgroundColor: colours.pink,
					borderColor: colours.pink
				},
				{
					title: 'Click for SuggeElson',
					start: new Date(y, m, 28),
					end: new Date(y, m, 29),
					url: 'http://suggeelson.com/',
					backgroundColor: colours.dark,
					borderColor: colours.dark
				}
			]
		});
	});
	$(function () {
		// we use an inline data source in the example, usually data would
	    // be fetched from a server
	    var data = [], totalPoints = 300;
	    var res = [];
	    while (data.length < totalPoints) {
            var prev = data.length > 0 ? data[data.length - 1] : 50;
            var y = prev + Math.random() * 10 - 5;
            if (y < 0)
                y = 0;
            if (y > 100)
                y = 100;
            data.push(y);
        }
	    var timestamps = new Date().getTime()+28800000-600000
        for (var i = 0; i < data.length; ++i){
            res.push([timestamps+i*2000, data[i]]);
        }
	    function getRandomData() {
	        if (data.length > 0)
	            data = data.slice(1);

	        // do a random walk
	        while (data.length < totalPoints) {
	            var prev = data.length > 0 ? data[data.length - 1] : 50;
	            var y = prev + Math.random() * 10 - 5;
	            if (y < 0)
	                y = 0;
	            if (y > 100)
	                y = 100;
	            data.push(y);
	        }

	        // zip the generated y values with the x values
	        res.shift();
	        res.push([res[res.length-1][0]+2000,Math.random() * 10 - 5]);
//	        var timestamps = new Date().getTime()+28800000-600000
//	        for (var i = 0; i < data.length; ++i)
//	            res.push([timestamps+i*2000, data[i]])
	        return res;
	    }

	    // Update interval
	    var updateInterval = 2000;

	    // setup plot
	    var options = {
	        series: { 
	        	shadowSize: 0, // drawing is faster without shadows
	        	lines: {
            		show: true,
            		fill: true,
            		lineWidth: 3.5,
            		steps: false
	            }
	        },
	        grid: {
				show: true,
			    aboveData: true,
			    color: colours.textcolor ,
			    labelMargin: 20,
			    axisMargin: 0, 
			    borderWidth: 0,
			    borderColor:null,
			    minBorderMargin: 5 ,
			    clickable: true, 
			    hoverable: true,
			    autoHighlight: true,
			    mouseActiveRadius: 100,
			},
			colors: [colours.blue],
	        tooltip: true, //activate tooltip
			tooltipOpts: {
				content: "Value is : %y.0",
				shifts: {
					x: -30,
					y: -50
				}
			},	
	        yaxis: { min: 0, max: 100 },
	        xaxis: { mode: "time",show: true}
	    };
	    var plot = $.plot($("#autoupdate-chart"), [ getRandomData() ], options);

	    function update() {
	    	 var plot = $.plot($("#autoupdate-chart"), [ getRandomData() ], options);
//	        plot.setData([ getRandomData() ]);
	        // since the axes don't change, we don't need to call plot.setupGrid()
//	        plot.draw();
	        
	        setTimeout(update, updateInterval);
	    }

	    update();
	});
	
	$(function() {
		var data =  [25.13,23.25,12.25,23.56,25.45,24.56,23.36,23];
		// line chart
		var d1 = [];
		//here we generate randomdata data for chart
		for (var i = -7; i < 0; i++) {
			d1.push([new Date(Date.today().add(i+1).days()).getTime(),data[i+7]]);
		}

		var chartMinDate = d1[0][0]; //first day
    	var chartMaxDate = d1[6][0];//last day

    	var tickSize = [1, "day"];
    	var tformat = "%y/%m/%d";

    	var total = 0;
    	//calculate total earnings for this period
    	for (var i = 0; i < 7; i++) {
			total = d1[i][1] + total;
		}

    	var options = {
    		grid: {
				show: true,
			    aboveData: false,
			    color: colours.textcolor ,
			    labelMargin: 20,
			    axisMargin: 0, 
			    borderWidth: 0,
			    borderColor:null,
			    minBorderMargin: 5 ,
			    clickable: true, 
			    hoverable: true,
			    autoHighlight: true,
			    mouseActiveRadius: 100,
			},
			series: {
				grow: {
		            active: true,
		     		duration: 1500
		        },
	            lines: {
            		show: true,
            		fill: true,
            		lineWidth: 2.5
	            },
	            points: {
	            	show:false
	            }
	        },
	        colors: [colours.blue],
	        legend: { 
	        	position: "ne", 
	        	margin: [0,-25], 
	        	noColumns: 0,
	        	labelBoxBorderColor: null,
	        	labelFormatter: function(label, series) {
				    return null;
				},
				backgroundColor: colours.white,
    			backgroundOpacity: 0.5,
    			hideSquare:true//hide square color helper 
	    	},
	        shadowSize:0,
	        tooltip: true, //activate tooltip
			tooltipOpts: {
				content: "%y.0",
				xDateFormat: "%d/%m",
				shifts: {
					x: -30,
					y: -50
				},
				theme: 'dark',
				defaultTheme: false
			},
			yaxis: { 
				min: 0
			},
			xaxis: { 
	        	mode: "time",
	        	minTickSize: tickSize,
	        	timeformat: tformat,
	        	min: chartMinDate,
	        	max: chartMaxDate,
	        	tickLength: 0,
	            
	        }
    	}

		var plot = $.plot($("#line-chart-filled"),[{
    			label: "Earnings", 
    			data: d1
    		}], options
    	);
		
	});
	
	$('.sparkline-positive').sparkline([5,12,18,15,22, 14,26,28,32,34], {
		width: '55px',
		height: '20px',
		lineColor: colours.green,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

	$('.sparkline-negative').sparkline([17,3,9,12,8,4,9,5,2,5], {
		width: '55px',
		height: '20px',
		lineColor: colours.red,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});
	$('.sparkline-bar-positive').sparkline([5,12,18,15,22, 14,26,28,32,34], {
		type: 'bar',
		width: '100%',
		height: '18px',
		barColor: colours.green,
	});

	$('.sparkline-bar-negative').sparkline([17,3,9,12,8,4,9,5,2,5], {
		type: 'bar',
		width: '100%',
		height: '18px',
		barColor: colours.red,
	});
	
	//------------- Sparklines -------------//
	$('#usage-sparkline').sparkline([35,46,24,56,68, 35,46,24,56,68], {
		width: '180px',
		height: '30px',
		lineColor: colours.dark,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

	$('#cpu-sparkline').sparkline([22,78,43,32,55, 67,83,35,44,56], {
		width: '180px',
		height: '30px',
		lineColor: colours.dark,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

	$('#ram-sparkline').sparkline([12,24,32,22,15, 17,8,23,17,14], {
		width: '180px',
		height: '30px',
		lineColor: colours.dark,
		fillColor: false,
		spotColor: false,
		minSpotColor: false,
		maxSpotColor: false,
		lineWidth: 2
	});

    //------------- Init pie charts -------------//
	initPieChart(10,40, 1500, colours);

 	
});

//Setup easy pie charts in sidebar
var initPieChart = function(lineWidth, size, animateTime, colours) {
	$(".pie-chart").easyPieChart({
        barColor: colours.dark,
        borderColor: colours.dark,
        trackColor: '#d9dde2',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: lineWidth,
        size: size,
        animate: animateTime
    });
}