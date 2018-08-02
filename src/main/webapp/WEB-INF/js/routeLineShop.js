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
		// 数据为空时，直接返回
		if(data.length == 0){
			return;
		}
		var selectShopId = shopInfo.selectedShopId;
		//保存客流路径的json，先查询出所有终点为要查看商铺的路径
		var pathArray = []; 
		var lastUserId=-1;
		var lastShopId=-1;
		var tempPath;
		//默认未开始查询路径
		var inPath=false;
		for(var i=1; i<data.length; i++){
			var tempUserId=data[i]["userId"];
			if(tempUserId==lastUserId){
				var tempShopId=data[i]["shopId"];
				if(tempShopId!=lastShopId){
					if(tempShopId==selectShopId){
						inPath=false;
						tempPath=tempPath+'_'+tempShopId;
						pathArray.push(tempPath);
					}else{
						if(inPath){
							tempPath=tempPath+'_'+tempShopId;
						}else{
							inPath=true;
							tempPath=tempShopId;
						}
					}
					lastShopId=tempShopId;
				}
			}else{
				lastUserId=tempUserId;
				lastShopId=data[i]["shopId"];
				inPath=false;
				tempPath='';
				if(lastShopId!=selectShopId){
					inPath=true;
					tempPath=lastShopId;
				}
			}
		}	
		//保存有序客流方向的json，用箭头画出
		var arrowMap = {}; 
		var tempSplit;
		var tempKey;
		for(i = 0,len=pathArray.length; i < len; i++) {
			tempSplit=pathArray[i].split("_");
			for(j=0,len2=tempSplit.length;j<len2-1;j++){
				//从前往后，统计两两配对的数量
				tempKey=tempSplit[j]+"_"+tempSplit[j+1];
				if(arrowMap[tempKey]){
					arrowMap[tempKey]++;
				}else{
					arrowMap[tempKey] = 1;
				}
			}
		}
		for(var k in arrowMap){
			var pair=k.split("_");  //拆分出两个shopId
			var x0=parseInt(shopInfo[pair[0]].x);
			var y0=parseInt(shopInfo[pair[0]].y);
			var x1=parseInt(shopInfo[pair[1]].x);
			var y1=parseInt(shopInfo[pair[1]].y);
			//接下来做一定的位置偏移，是为了清晰明了的画出两点之间正反两条线
			var length=Math.sqrt((x1-x0)*(x1-x0)+(y1-y0)*(y1-y0));
			var cos=((x1-x0)/length).toFixed(2);
			var sin=((y1-y0)/length).toFixed(2);
			x0=x0+cos*15+sin*5;
			x1=x1-cos*15+sin*5;
			y0=y0+sin*15-cos*5;
			y1=y1-sin*15-cos*5;
			var sac=getStrokeAndColor(arrowMap[k]);
			drawArrow(ctx, x0, y0, 
					x1,y1,15,10,sac.stroke*0.5,sac.color);
			  ctx.fillText(arrowMap[k],x1-(x1-x0)/8,y1-(y1-y0)/8,200);
		}
	};
	
	var getStrokeAndColor = function(num){
		var s,c;
		if(num<11){
			s=1;
			c='#8DB4E2';
		}else if(num<51){
			s=3;
			c='#92D050';
		}else if(num<101){
			s=5;
			c='#FFFF00';
		}else if(num<201){
			s=7;
			c='#FFC000';
		}else if(num<501){
			s=9;
			c='#FF0000';
		}else{
			s=11;
			c='#C00000';
		}
		return {stroke:s, color:c};
	};
	
//	ctx：Canvas绘图环境
//	fromX, fromY：起点坐标（也可以换成 p1 ，只不过它是一个数组）
//	toX, toY：终点坐标 (也可以换成 p2 ，只不过它是一个数组)
//	theta：三角斜边一直线夹角
//	headlen：三角斜边长度
//	width：箭头线宽度
//	color：箭头颜色
	function drawArrow(ctx, fromX, fromY, toX, toY,theta,headlen,width,color) {
		  var theta = theta || 30,
		      headlen = headlen || 10,
		      width = width || 1,
		      color = color || '#000',
		      angle = Math.atan2(fromY - toY, fromX - toX) * 180 / Math.PI,
		      angle1 = (angle + theta) * Math.PI / 180,
		      angle2 = (angle - theta) * Math.PI / 180,
		      topX = headlen * Math.cos(angle1),
		      topY = headlen * Math.sin(angle1),
		      botX = headlen * Math.cos(angle2),
		      botY = headlen * Math.sin(angle2);
		  ctx.save();
		  ctx.beginPath();
		  var arrowX, arrowY;
		  ctx.moveTo(fromX, fromY);
		  ctx.lineTo(toX, toY);
		  arrowX = toX + topX;
		  arrowY = toY + topY;
		  ctx.moveTo(arrowX, arrowY);
		  ctx.lineTo(toX, toY);
		  arrowX = toX + botX;
		  arrowY = toY + botY;
		  ctx.lineTo(arrowX, arrowY);
		  ctx.strokeStyle = color;
		  ctx.lineWidth = width;
		  ctx.stroke();
		  ctx.restore();
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
		var startTime = dateFormat(new Date(new Date().getTime() - 97*24*60*60*1000), "yyyy-MM-dd HH:mm:ss"),
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