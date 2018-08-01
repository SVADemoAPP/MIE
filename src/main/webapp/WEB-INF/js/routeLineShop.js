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

		if (divWidth / 480 > width / height) {
			newHeight = 480;
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
	
	
	// 轨迹渲染
	var paintPeopleRoute = function(data, shopInfo, ctx){
			// 店铺轨迹
			paintPeopleRouteShop(data, shopInfo, ctx);
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
				initRoutemap(selectedMapObj, shopObj);
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
				initRoutemap(selectedMapObj, shopObj);
			})
		});
	};

	// 初始化动线图
	var initRoutemap = function(mapInfo, shopInfo) {
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
		var startTime = dateFormat(new Date(new Date().getTime() - 37*24*60*60*1000), "yyyy-MM-dd HH:mm:ss"),
			endTime = dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		// test
		//startTime = "2016-06-14 16:30:20";
		getPeopleRoute(mapInfo.mapId, startTime, endTime, function(data){
			paintPeopleRoute(data.data, shopInfo, ctx);
		})
	};
	
	return {
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