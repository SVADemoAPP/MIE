        $(document).ready(function() {
            heatMap.initShopName();
            heatMap.bindClickEvent();
            //get object with colros from plugin and store it.
             objColors = $('body').data('sprFlat').getColors();
             colours = {
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
//                  var timestamps = new Date().getTime()+28800000-600000
//                  for (var i = 0; i < data.length; ++i)
//                      res.push([timestamps+i*2000, data[i]])
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
                        content: passenger+" : %y.0",
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
//                  plot.setData([ getRandomData() ]);
                    // since the axes don't change, we don't need to call plot.setupGrid()
//                  plot.draw();
                    
                    setTimeout(update, updateInterval);
                }

                update();
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

        });