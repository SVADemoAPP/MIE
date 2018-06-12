var ctx;
var mapObj;
var shopObj;
var rootPath = "/sva";
// 动线图类
var RouteLine = function() {
	// 计算地图缩放比例
	var calImgSize = function(width, height) {
		var newWidth, newHeight, imgScale;
		var divWidth = parseInt($("#routeLine").css("width").slice(0, -2));

		if (divWidth / 430 > width / height) {
			newHeight = 430;
			imgScale = height / newHeight;
			newWidth = width / imgScale;
		} else {
			newWidth = divWidth;
			imgScale = width / newWidth;
			newHeight = height / imgScale;
		}

		return [ imgScale, newWidth, newHeight ];
	};
	
	// 店铺实际坐标转换为地图坐标
	var shopPositionConvert = function(data, mapObject) {
		var result = {};
		for ( var i in data) {
			var mapId = data[i].mapId;
			for ( var j in mapObject) {
				if (mapObject[j].mapId==mapId) {
					var mapInfo = mapObject[j],
					scale = mapInfo.scale,
					xo = mapInfo.xo,
					yo = mapInfo.yo,
					width = mapInfo.imgDisplayWidth,
					height = mapInfo.imgDisplayHeight,
					coordinate = mapInfo.coordinate,
					imgScale = mapInfo.imgScale;
					var point = {};
					switch (coordinate){
					case "ul":
						var x1 = (data[i].xSpot * scale + xo * scale) / imgScale,
						y1 = (data[i].ySpot * scale + xo * scale) / imgScale,
						x2 = (data[i].x1Spot * scale + xo * scale) / imgScale,
						y2 = (data[i].y1Spot * scale + xo * scale) / imgScale;
						point = {
								name:data[i].shopName,
								x : (x1 + x2) / 2,
								y : (y1 + y2) / 2,
								x1 : x1,
								x2 : x2,
								y1 : y1,
								y2 : y2
						};
						break;
					case "ll":
						var x1 = (data[i].xSpot * scale + xo * scale) / imgScale,
						y1 = height - (data[i].ySpot * scale + yo * scale) / imgScale,
						x2 = (data[i].x1Spot * scale + xo * scale) / imgScale,
						y2 = height - (data[i].y1Spot * scale + yo * scale) / imgScale;
						point = {
								name:data[i].shopName,
								x : (x1 + x2) / 2,
								y : (y1 + y2) / 2,
								x1 : x1,
								x2 : x2,
								y1 : y1,
								y2 : y2
						};
						break;
					case "ur":
						var x1 = width - (data[i].xSpot * scale + xo * scale) / imgScale,
						y1 = (data[i].ySpot * scale + yo * scale) / imgScale,
						x2 = width - (data[i].x1Spot * scale + xo * scale) / imgScale,
						y2 = (data[i].y1Spot * scale + yo * scale) / imgScale;
						point = {
								name:data[i].shopName,
								x : (x1 + x2) / 2,
								y : (y1 + y2) / 2,
								x1 : x1,
								x2 : x2,
								y1 : y1,
								y2 : y2
						};
						break;
					case "lr":
						var x1 = width - (data[i].xSpot * scale + xo * scale) / imgScale,
						y1 = height - (data[i].ySpot * scale + yo * scale) / imgScale,
						x2 = width - (data[i].x1Spot * scale + xo * scale) / imgScale,
						y2 = height - (data[i].y1Spot * scale + yo * scale) / imgScale;
						point = {
								name:data[i].shopName,
								x : (x1 + x2) / 2,
								y : (y1 + y2) / 2,
								x1 : x1,
								x2 : x2,
								y1 : y1,
								y2 : y2
						};
						break;
					}
					result[data[i].id] = point;
				}
			}
		}

		return result;
	};
	
	// 清空画布
	var clearCanvas = function(ctx){
		var c = document.getElementById("canvas");
		ctx.clearRect(0,0,c.width,c.height);  
	};
	
	// 获取指定楼层的用户店铺轨迹信息
	var getPeopleRoute = function(mapId, startTime, endTime, callback){
		$.post(rootPath+"/route/api/getRouteData", {mapId:mapId, startTime:startTime, endTime:endTime},function(data){
			callback(data);
		});
	};
	
	// 获取店铺信息
	var getShopInfo = function(callback){
		$.post(rootPath+"/shop/getShopInfo",function(data){
			callback(data);
		});
	};
	
	var paintPeopleEcharts = function(data, shopInfo, url, id){
		// 店铺坐标
		var shopCord = {};
		// 店铺名列表
		var shopNameList = [];
		for(var k in shopInfo){
			var v = shopInfo[k];
			var name = v.name;
			var x = v.x;
			var y = v.y;
			shopCord[name] = [x,y];
			shopNameList.push({name:name});
		}
		// 将路径组装成数据对
		var linePairs = [];
		var map = {};
		for(var i=1; i<data.length; i++){
			var temp = data[i];
			var shopTemp = shopInfo[temp["shopId"]];
			if(data[i+1] && data[i+1].userId==temp.userId){
				var pair = [];
				pair.push({name:shopTemp.name});
				pair.push({name:shopInfo[data[i+1]["shopId"]].name});
				linePairs.push(pair);
			}
		}
		// 调用echarts完成画图
		renderEcharts(shopCord, shopNameList, linePairs, url, id);
	};
	
	var renderEcharts = function(shopCord, shopNameList, datas, mapUrl, id){
		echarts.util.mapData.params.params.baiduBuilding = {
		    getGeoJson: function (callback) {
		        $.ajax({
		            url: mapUrl,
		            dataType: 'xml',
		            success: function(xml) {
		                callback(xml)
		            }
		        });
		    }
		};
		var option = {
		    backgroundColor:'#eee',
		    title : {
		        text : 'Routeline',
		        textStyle: {
		            color: '#000'
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: '{b}'
		    },
		    color: ['rgba(218, 70, 214, 1)', 'rgba(100, 149, 237, 1)', 'green'],
		    legend: {
		        data: ['嘿嘿', '高经', '会议室']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            name: '嘿嘿',
		            type: 'map',
		            mapType: 'baiduBuilding',
		            roam:true,
		            itemStyle:{
		                normal:{label:{show:true}},
		                emphasis:{label:{show:true}}
		            },
		            data: [],
		            geoCoord: shopCord,
		            markPoint : {
		                symbolSize : 3,
		                data : shopNameList
		            },
		            markLine : {
		                smooth:true,
		                effect : {
		                    show: true,
		                    scaleSize: 1,
		                    period: 20,
		                    color: '#fff',
		                    shadowBlur: 5
		                },
		                symbol: ['none'],
		                itemStyle : {
		                    normal: {
		                        borderWidth:1,
		                        lineStyle: {
		                            type: 'solid'
		                        }
		                    }
		                },
		                data : datas
		            }
		        },
		        {
		            type: 'map',
		            mapType: 'baiduBuilding',
		            data: [],
		            markPoint: {
		                symbol: 'emptyCircle',
		                effect: {
		                    show: true,
		                    color: 'rgba(218, 70, 214, 1)'
		                },
		                data: shopNameList
		            }
		        }
		    ]
		};
		var myChart = echarts.init(document.getElementById(id)); 
		myChart.setOption(option); 
	}
	
	// 轨迹渲染
	var paintPeopleRoute = function(data, shopInfo, ctx, type){
		if(type=="store"){
			// 商场轨迹
			paintPeopleRouteStore(data, shopInfo, ctx);
		}else{
			// 店铺轨迹
			paintPeopleRouteShop(data, shopInfo, ctx);
		}
	};
	
	// 商场店铺轨迹渲染
	var paintPeopleRouteStore = function(data, shopInfo, ctx){
		var map = {};
		ctx.strokeStyle = "#ff4c4c";
//		ctx.globalCompositeOperation = "lighter";
		// 数据为空时，直接返回
		if(data.length == 0){
			return;
		}
		// 开始第一个path的第一个点
		map[data[0]["userId"]] = true;
		ctx.beginPath();
	    ctx.moveTo(parseInt(shopInfo[data[0]["shopId"]].x), parseInt(shopInfo[data[0]["shopId"]].y));
		for(var i=1; i<data.length; i++){
			var temp = data[i];
			var shopTemp = shopInfo[temp["shopId"]];
			if(map[temp["userId"]]){
				ctx.lineTo(parseInt(shopTemp.x), parseInt(shopTemp.y));
			}else{
				// 结束上一条path
				ctx.closePath();
				ctx.stroke();
				// 开始新的path
				ctx.beginPath();
			    ctx.moveTo(parseInt(shopTemp.x), parseInt(shopTemp.y));
			    
			    map[temp["userId"]] = true;
			}
		}
		// 结束最后一条path
		ctx.closePath();
		ctx.stroke();
		
	};
	
	// 单个店铺轨迹渲染
	var paintPeopleRouteShop = function(data, shopInfo, ctx){
		ctx.strokeStyle = "#fd0b0c";
		ctx.globalCompositeOperation = "lighter";
		// 计算进出指定店铺的数据
		var mapTemp = {};
		var shopList = [];
		var shopId = shopInfo.selectedShopId;
		for(var i=0; i<data.length; i++){
			var temp = data[i];
			if(temp.shopId == shopId){
				if(i-1 >=0 && data[i-1].userId == temp.userId){
					if(mapTemp[data[i-1].shopId]){
						mapTemp[data[i-1].shopId]++;
					}else{
						mapTemp[data[i-1].shopId] = 1;
						shopList.push(data[i-1].shopId);
					}
					
				}
				if(i!=0&&i+1 < data.length && data[i-1].userId == temp.userId){
					if(mapTemp[data[i+1].shopId]){
						mapTemp[data[i+1].shopId]++;
					}else{
						mapTemp[data[i+1].shopId] = 1;
						shopList.push(data[i+1].shopId);
					}
				}
			}
		}
		
		// 画线
		for(var j=0; j<shopList.length; j++){
			var shopIdTemp = shopList[j];
			var count = mapTemp[shopIdTemp];
			for(var k=0; k<count; k++){
				var randomPoint = generateRandomPoint(shopInfo[shopId]);
				// 开始新的path
				ctx.beginPath();
			    ctx.moveTo(parseInt(shopInfo[shopIdTemp].x), parseInt(shopInfo[shopIdTemp].y));
			    ctx.lineTo(parseInt(randomPoint.x), parseInt(randomPoint.y))
				// 结束path
				ctx.closePath();
				ctx.stroke();
			}
		}
	};
	
	var generateRandomPoint = function(shopInfo){
		var x1 = shopInfo.x1,
			x2 = shopInfo.x2,
			y1 = shopInfo.y1,
			y2 = shopInfo.y2;
		var x = x1 + Math.random()*(x2 - x1)/2 + (x2 - x1)/4;
		var y = y1 + Math.random()*(y2 - y1)/2 + (y2 - y1)/4;
		return {x:x, y:y};
	}
	
	// 画布初始化
	var initCanvas = function(){
		var canvas = document.getElementById('canvas');
		if (canvas.getContext){
			// 全局变量赋值
			ctx = canvas.getContext('2d');
		}
	}
	
	// 初始化楼层信息
	var initFloor = function(){
		// 获取地图信息
		$.post(rootPath+"/map/api/getMapInfoOfFirstStore",function(data){
			mapObj = {};
			var firstMapObj = data.data[0] || {};
			for(var i=0; i<data.data.length; i++){
				var mapTemp = data.data[i]
				// 计算图片缩放信息
				var imgInfo = calImgSize(mapTemp.imgWidth, mapTemp.imgHeight);
				mapTemp.imgScale = imgInfo[0],
				mapTemp.imgDisplayWidth = imgInfo[1],
				mapTemp.imgDisplayHeight = imgInfo[2];
				// 全局地图信息赋值
				mapObj[data.data[i]["mapId"]] = mapTemp;
				// 楼层按钮初始化
//				var top = 100 + 50*i;
//				var floorHtml = "<div class='floor' style='top:"+top+"px' data-mapid='"+mapTemp.mapId+"'>"+mapTemp.floor+"</div>"
//				$("#floorDiv").append(floorHtml);
			}
			// 获取店铺信息
			getShopInfo(function(data){
				// 全局店铺信息赋值
				shopObj = shopPositionConvert(data.data, mapObj);
				// 默认第一层选中，并绘制动线图
				initRoutemap(firstMapObj, shopObj, "store");
//				initRoutemapEcharts(firstMapObj, shopObj);
				$(".floor:first").addClass("active");
			})
		});
	};
	
	var initFloors = function(mapId){
		// 获取地图信息
		$.post(rootPath+"/map/api/getMapInfoOfFirstStores",{"mapId":mapId},function(data){
			$("#floorDiv").empty();
			mapObj = {};
			var firstMapObj = data.data[0] || {};
			for(var i=0; i<data.data.length; i++){
				var mapTemp = data.data[i]
				// 计算图片缩放信息
				var imgInfo = calImgSize(mapTemp.imgWidth, mapTemp.imgHeight);
				mapTemp.imgScale = imgInfo[0],
				mapTemp.imgDisplayWidth = imgInfo[1],
				mapTemp.imgDisplayHeight = imgInfo[2];
				// 全局地图信息赋值
				mapObj[data.data[i]["mapId"]] = mapTemp;
				// 楼层按钮初始化
//				var top = 100 + 50*i;
//				var floorHtml = "<div class='floor' style='top:"+top+"px' data-mapid='"+mapTemp.mapId+"'>"+mapTemp.floor+"</div>"
//				$("#floorDiv").append(floorHtml);
			}
			// 获取店铺信息
			getShopInfo(function(data){
				// 全局店铺信息赋值
				shopObj = shopPositionConvert(data.data, mapObj);
				// 默认第一层选中，并绘制动线图
				initRoutemap(firstMapObj, shopObj, "store");
//				initRoutemapEcharts(firstMapObj, shopObj);
				$(".floor:first").addClass("active");
			})
		});
	};
	
	// 初始化店铺信息
	var initShopFloor = function(shopId, mapId){
		// 获取地图信息
		$.post(rootPath+"/map/api/getMapInfoOfFirstStore",function(data){
			mapObj = {};
			var selectedMapObj = {};
			for(var i=0; i<data.data.length; i++){
				var mapTemp = data.data[i]
				// 计算图片缩放信息
				var imgInfo = calImgSize(mapTemp.imgWidth, mapTemp.imgHeight);
				mapTemp.imgScale = imgInfo[0],
				mapTemp.imgDisplayWidth = imgInfo[1],
				mapTemp.imgDisplayHeight = imgInfo[2];
				// 全局地图信息赋值
				mapObj[data.data[i]["mapId"]] = mapTemp;
				// 楼层按钮初始化
				if(mapId == mapTemp.mapId){
					var floorHtml = "<div class='floor' style='top:100px'>"+mapTemp.floor+"</div>"
					$("#floorDiv").append(floorHtml);
					selectedMapObj = mapTemp;
				}
			}
			// 获取店铺信息
			getShopInfo(function(data){
				// 全局店铺信息赋值
				shopObj = shopPositionConvert(data.data, mapObj);
				shopObj.selectedShopId = shopId;
				// 默认第一层选中，并绘制动线图
				initRoutemap(selectedMapObj, shopObj, "shop");
			})
		});
	};
	
	var initShopFloors = function(shopId, mapId){
		// 获取地图信息
		$.post(rootPath+"/map/api/getMapInfoByMapId",{"mapId":mapId},function(data){
			mapObj = {};
			var selectedMapObj = {};
			for(var i=0; i<data.data.length; i++){
				var mapTemp = data.data[i]
				// 计算图片缩放信息
				var imgInfo = calImgSize(mapTemp.imgWidth, mapTemp.imgHeight);
				mapTemp.imgScale = imgInfo[0],
				mapTemp.imgDisplayWidth = imgInfo[1],
				mapTemp.imgDisplayHeight = imgInfo[2];
				// 全局地图信息赋值
				mapObj[data.data[i]["mapId"]] = mapTemp;
				// 楼层按钮初始化
				if(mapId == mapTemp.mapId){
					var floorHtml = "<div class='floor' style='top:100px'>"+mapTemp.floor+"</div>"
					$("#floorDiv").append(floorHtml);
					selectedMapObj = mapTemp;
				}
			}
			// 获取店铺信息
			getShopInfo(function(data){
				// 全局店铺信息赋值
				shopObj = shopPositionConvert(data.data, mapObj);
				shopObj.selectedShopId = shopId;
				// 默认第一层选中，并绘制动线图
				initRoutemap(selectedMapObj, shopObj, "shop");
			})
		});
	};

	// 初始化动线图
	var initRoutemap = function(mapInfo, shopInfo, type) {
		// 清空画布
		$("#routeLine").css("background-image", "");
		clearCanvas(ctx);
		
		// 变量赋值
		var bgImg = mapInfo.path,
			imgWidth = mapInfo.imgDisplayWidth,
			imgHeight = mapInfo.imgDisplayHeight;
		// 设置背景图片
		var bgImgStr = "url(../upload/map/" + bgImg + ")";
		$("#routeLine").css({
			"width" : imgWidth + "px",
			"height" : imgHeight + "px",
			"background-image" : bgImgStr,
			"background-size" : imgWidth + "px " + imgHeight + "px",
			"margin" : "0 auto"
		});
		// 设置画布大小
		var c = document.getElementById("canvas");
		c.width = imgWidth;
		c.height = imgHeight;
		
		// 获取用户轨迹信息
		var startTime = dateFormat(new Date(new Date().getTime() - 7*24*60*60*1000), "yyyy-MM-dd HH:mm:ss"),
			endTime = dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		// test
		//startTime = "2016-06-14 16:30:20";
		getPeopleRoute(mapInfo.mapId, startTime, endTime, function(data){
			paintPeopleRoute(data.data, shopInfo, ctx, type);
		})
	};
	
	var initRoutemapEcharts = function(mapInfo, shopInfo, type){
		// 清空画布
		$("#routeLine").empty();
		
		// 变量赋值
		var bgImg = mapInfo.path,
			imgWidth = mapInfo.imgDisplayWidth,
			imgHeight = mapInfo.imgDisplayHeight;
		
		// 获取用户轨迹信息
		var startTime = dateFormat(new Date(new Date().getTime() - 7*24*60*60*1000), "yyyy-MM-dd HH:mm:ss"),
			endTime = dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		// test
		//startTime = "2016-06-14 16:30:20";
		getPeopleRoute(mapInfo.mapId, startTime, endTime, function(data){
			//TODO
			var url = "/sva/upload/"+bgImg;
			paintPeopleEcharts(data.data, shopInfo, url, "routeLine");
		})
	};
	
	// 绑定商场动线图事件
	var bindStoreEvent = function(){
		// 楼层切换事件
		$(document).on("click", ".floor", function(e) {
			var mapId = $(this).data("mapid");
			initRoutemap(mapObj[mapId], shopObj, "store");
			//initRoutemapEcharts(mapObj[mapId], shopObj, "store");
			$(".floor").removeClass("active");
			$(this).addClass("active");
		});
	};
	
	return {
		// 初始化
		init: function(){
			// 画布初始化
			initCanvas();
			// 地图信息初始化
			initFloor();
			// 绑定事件
			bindStoreEvent();
		},
		changeStore: function(storeId){
			// 画布初始化
			initCanvas();
			// 地图信息初始化
			initFloors(storeId);
		},
		initShop: function(shopId, mapId){
			// 画布初始化
			initCanvas();
			// 地图初始化
			initShopFloor(shopId, mapId);
		},
		changeShop: function(shopId, mapId){
			// 画布初始化
			initCanvas();
			// 地图初始化
			initShopFloors(shopId, mapId);
		},
		refreshShopRouteLine:function(shopId, mapId){
			// 地图刷新
			initShopFloor(shopId, mapId);
		}
	};

}();