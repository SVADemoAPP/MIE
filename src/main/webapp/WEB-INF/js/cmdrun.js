$(document).ready(function(){
 	
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
	//------------- Sparklines -------------//
		$('#usage-sparkline').sparkline([35,46,24,56,68, 35,46,24,56,68], {
			width: '180px',
			height: '30px',
			lineColor: '#00ABA9',
			fillColor: false,
			spotColor: false,
			minSpotColor: false,
			maxSpotColor: false,
			lineWidth: 2
		});

		$('#cpu-sparkline').sparkline([22,78,43,32,55, 67,83,35,44,56], {
			width: '180px',
			height: '30px',
			lineColor: '#00ABA9',
			fillColor: false,
			spotColor: false,
			minSpotColor: false,
			maxSpotColor: false,
			lineWidth: 2
		});

		$('#ram-sparkline').sparkline([12,24,32,22,15, 17,8,23,17,14], {
			width: '180px',
			height: '30px',
			lineColor: '#00ABA9',
			fillColor: false,
			spotColor: false,
			minSpotColor: false,
			maxSpotColor: false,
			lineWidth: 2
		});

	    //------------- Init pie charts -------------//
		initPieChart();
 
});

//Setup easy pie charts
var initPieChart = function(lineWidth, size, animateTime, colours) {
	$(".pie-chart").easyPieChart({
        barColor: '#5a5e63',
        borderColor: '#5a5e63',
        trackColor: '#d9dde2',
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: 10,
        size: 40,
        animate: 1500
    });
}