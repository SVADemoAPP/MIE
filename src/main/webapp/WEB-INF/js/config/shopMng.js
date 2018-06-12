var ShopMng = function () {
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == info.name){
	    		options += '<option class="addoption" selected=true value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateFloorList = function(renderId,data,selectTxt,callback){
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	
	var updateFloorList1 = function(renderId,data,selectTxt,callback){
		$("#zSel").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '<option value=""></option>';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	
	var clacImgZoomParam = function( maxWidth, maxHeight, width, height,x,y,coordinate){  
	    var param = {top:0, left:0, width:width, height:height,x:x,y:y,coordinate:coordinate};  
	    rateWidth = width / maxWidth;  
	    rateHeight = height / maxHeight;  
	      
	    if( rateWidth > rateHeight ){  
	        param.width =  maxWidth;  
	        param.height = Math.round(height / rateWidth);  
	    }else{  
	        param.width = Math.round(width / rateHeight);  
	        param.height = maxHeight;  
	    }
	      
	    param.left = Math.round((maxWidth - param.width) / 2);  
	    param.top = Math.round((maxHeight - param.height) / 2); 
	    param.zoomScale = width / param.width;
	    return param;  
	};
    
    var deleteInput = function(id){
    	if(confirm(i18n_deleteInfo))
    	{
    	$.post("/sva/shop/api/deleteData",{id:id},function(data){
    		if(!data.error){
    			ShopMng.initTable();
			}
    	});
    }else
    	{
    	return false;
    	}
    };
    
    var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};

    return {
    	initSelect: function(){
    		$.post("/sva/market/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList("placeSel",data.data);
    			}
    		});
    		$.post("/sva/category/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList("category",data.data);
    			}
    		});
    	},
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#placeSel").on("change", function(){
    			var placeId = $("#placeSel").val();
    			yanzheng(this);
				$.post("/sva/map/api/getMapDataByStore",{id:placeId}, function(data){
					if(!data.error){
						updateFloorList1("zSel",data.data);
					}
				});
	    		$('a[href="#myModal1"]').attr("disabled","disabled");
//	    		$("#search").hide();
			});
			// 楼层下拉列表修改 触发选择坐标时地图变化
			$("#zSel").on("change", function(){
				var lastVal = this.validform_lastval; 
				if (lastVal!=null) 
				{
					this.validform_lastval = null; 
				}
				var opts = $("#zSel option");
				var selectedOpt = opts[$(this)[0].selectedIndex];
				if($("#zSel").val() != " "){
					var width = $(selectedOpt).data("width"),
						height = $(selectedOpt).data("height"),
						path = $(selectedOpt).data("path"),
						scale = $(selectedOpt).data("scale");
						coordinate = $(selectedOpt).data("coordinate");
						x = $(selectedOpt).data("x");
						y = $(selectedOpt).data("y");
					var MAXWIDTH  = 868;  
					var MAXHEIGHT = 500;  	
					rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, width, height,x,y,coordinate);
					rect.scale = scale;
					$("#preview").empty();
					$("#preview").css({
						"width" : rect.width + "px",
						"height" : rect.height + "px",
						"x" : rect.x + "px",
						"y" : rect.y + "px",
						"coordinate" :rect.coordinate,
						"margin-left" : rect.left + 'px',
						"margin-top" : rect.top + 'px',
						"background-image": "url(../upload/" + path + ")",
						"background-size":"cover",
						"-moz-background-size": "cover"
					});
					$("#areapreview").empty();
					$("#areapreview").css({
						"width" : rect.width + "px",
						"height" : rect.height + "px",
						"x" : rect.x + "px",
						"y" : rect.y + "px",
						"coordinate" :rect.coordinate,
						"margin-left" : rect.left + 'px',
						"margin-top" : rect.top + 'px',
						"background-image": "url(../upload/" + path + ")",
						"background-size":"cover",
						"-moz-background-size": "cover"
					});
					$('a[href="#myModal1"]').attr("disabled",false);
				}else{
					$('a[href="#myModal1"]').attr("disabled","disabled");
				}
//				if($("#zSel").val() != " "){					
//					$("#search").show();
//				}else{
//					$("#search").hide();
//				}
				
			});		
			$("a[data-type='point']").on("click",function(e){
				$("#Ok").attr("disabled","disabled");
				if(typeof($(this).attr("disabled"))!="undefined"){
					e.preventDefault();
					return false;
				}
				Ploy.clearPaper();
//				$("#pointY2").val("");
			});
			
			$('#preview').click(function(e){
				
				var left=e.pageX;
				var top = e.pageY;
				var o = {
					left : left,
					top : top
				};
				var datas = Ploy.getData();
				if (datas.length < 2) {
					Ploy.makeRect('#preview', o);
					// Ploy.addPoint(top,left);
					var t = top - $('#preview').offset().top;
					var l = left - $('#preview').offset().left;
					if (datas.length < 1) {
						$("#x0").val(l);
						$("#y0").val(t);
					} else {
						$("#x1").val(l);
						$("#y1").val(t);
						$("#Ok").attr("disabled",false);
					}
				}
				
			});
			$(".clearPaper").on("click", function(e) {
				Ploy.clearPaper();
				$("#Ok").attr("disabled","disabled");
     			$("#alertBoxScale").hide();
				$("#x0").val("");
				$("#y0").val("");
				$("#x1").val("");
				$("#y1").val("");
			//	$("#Ok").attr("disabled","disabled");
			});
			
			$("#Ok").on("click",function(e){	
				//判断原点位置
				var px1 = $("#x0").val();
				var px2;
         		var py1;
         		var py2;
				if(px1){
					var coordinate = rect.coordinate;
					switch (coordinate){
					case "ul":
						 px1 = $("#x0").val();
	             		 py1 = $("#y0").val();
	             		 px2 = $("#x1").val();
	             		 py2 = $("#y1").val();
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			 px1 = $("#x0").val();
	             		 py1 = imagey-$("#y0").val();
	             		 px2 = $("#x1").val();
	             		if(px2){
	             			 py2 = imagey-$("#y1").val();
	             		}else{
	             			 py2 = $("#y1").val();
	             		}
	             		
	         			break;
	         		case "ur":
	         			imagex = rect.width ;
	             		 px1 =imagex-$("#x0").val();
	             		 py1 = $("#y0").val();
	             		 py2 = $("#y1").val();
	             		if(py2){
	             			 px2 = imagex-$("#x1").val();
	             		}else{
	             			 px2 = $("#x1").val();
	             		}
	             		
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var x1test = $("#x0").val();
	         			var x2test = $("#x1").val();
	         			if(x1test){
		             		 px1 = imagex-$("#x0").val();
		             		 py1 = imagey-$("#y0").val();
	         			}else{
	         				 px1 = $("#x0").val();
	         				 py1 = $("#y0").val();
	         			}
	         			if(x2test){
	         				 px2 = imagex-$("#x1").val();
		             		 py2 = imagey-$("#y1").val();
	         			}else{
	         				 px2 = $("#x1").val();
		             		 py2 = $("#y1").val();
	         			}
	         			
	         			break;
	         		}
					
	     				var scale = rect.scale;
	     				if(parseFloat(px1) <parseFloat(px2)){
	     					$("#pointX1").val(((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
	     					$("#pointX2").val(((parseFloat(px2)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
	     				}else{
	     					$("#pointX1").val(((parseFloat(px2)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
	     					$("#pointX2").val(((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
	     				}
	     				if(parseFloat(py1) < parseFloat(py2)){
	     					$("#pointY1").val(((parseFloat(py1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
	     					$("#pointY2").val(((parseFloat(py2)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
	     				}else{
	     					$("#pointY1").val(((parseFloat(py2)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
	     					$("#pointY2").val(((parseFloat(py1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
	     				}
	         			$("#pointX1").blur();
	         			$("#pointY1").blur();
	         			$("#pointX2").blur();
	         			$("#pointY2").blur();
	         			$("#myModal1").modal('hide');
	         			$("#alertBoxScale").hide();
         		}else{	         			
         			$("#infoScale").text(i18n_choose_title);
         			$("#alertBoxScale").show();
         		}
				
         	});
			function AreaMakeRect(el,x,y,x1,y1){
				//var width=document.getElementById("preview").style.width;
				//var height=document.getElementById("preview").style.height;
				//通过得到的实际米数计算出象数
				//$("#pointX1").val(((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
				var scale = rect.scale;
				px = ((parseFloat(x)+parseFloat(rect.x))*parseFloat(scale)/rect.zoomScale).toFixed(2);
				py = ((parseFloat(y)+parseFloat(rect.y))*parseFloat(scale)/rect.zoomScale).toFixed(2);
				px1 = ((parseFloat(x1)+parseFloat(rect.x))*parseFloat(scale)/rect.zoomScale).toFixed(2);
				py1 = ((parseFloat(y1)+parseFloat(rect.y))*parseFloat(scale)/rect.zoomScale).toFixed(2);
				var coordinate = rect.coordinate;
				switch (coordinate){
				case "ul":
					
         			break;
         		case "ll":
         			imagey = rect.height;
         			 px = px;
             		 py = imagey-py;
             		 px1 = px1;
             		if(px1){
             			 py1= imagey-py1;
             		}else{
             			 py1 = py1;
             		}
         			break;
         		case "ur":
         			imagex = rect.width ;
             		 px =imagex-px;
             		 py = py;
             		 py1 = py1;
             		if(py1){
             			 px1 = imagex-px1;
             		}else{
             			 px1 = px1;
             		}
             		
         			break;
         		case "lr":
         			imagex = rect.width ;
         			imagey = rect.height;
         			var x1test = px1;
         			var x2test = py1;
         			if(x1test){
	             		 px = imagex-px;
	             		 py = imagey-py;
         			}else{
         				 px = px;
	             		 py = py;
         			}
         			if(x2test){
         				 px1 = imagex-px1;
	             		 py1= imagey-py1;
         			}else{
         				 px1 = py1;
	             		 py1 = py1;
         			}
				}
			    //生成路径
			    	var path = "M"+px+","+py
			    				+" L"+px1+","+py
			    				+" L"+px1+","+py1
			    				+" L"+px+","+py1
			    				+" L"+px+","+py;
			   // 	Ploy.paper.clear();
			   // 	pager.remove();
			    	
			    	Ploy.paper.path(path).attr({	
			            stroke:'#1791fc', 	
			            'stroke-width':3,	
			            opacity:.7, 	
			            fill:"none"
			        });	    	
			}	
			
			$("a[data-type='preview']").on("click",function(e){
				if(typeof($(this).attr("disabled")) != "undefined"){
					e.preventDefault();
					return false;
				}
				Ploy.clearPaper();
				
				var mapId = $("#zSel").val();
				$.post("/sva/shop/api/getShopInfoByMapId",{mapId:mapId},function(data){
					if(!data.error){
						var ploy = Raphael("areapreview",rect.width,rect.height);
						Ploy.paper = ploy;	
						var areas = data.data
						
						for ( var i = 0; i < data.data.length; i++) {
						//	Ploy.paper.clear();
							var x = areas[i].xSpot;
							var y = areas[i].ySpot;
							var x1 =  areas[i].x1Spot;
							var y1 =  areas[i].y1Spot;
							AreaMakeRect('#areapreview',x,y,x1,y1);
							
						}
						
					}
				});
				
			});
		
            $("input[data-type='del']").live("click",function(e){
        		var id = $(this).data("id");
	           	 deleteInput(id);
            });
//            $("input[data-type='fuhzi']").live("click",function(e){
//            	var xSpot = $(this).data("xspot"),
//            	ySpot = $(this).data("yspot"),
//            	x1Spot = $(this).data("x1spot"),
//            	y1Spot = $(this).data("y1spot"),
//            	floorNo = $(this).data("floorno"),
//            	categoryId = $(this).data("categoryid");
//            	id = $(this).data("id");
//            	deleteInput(xSpot, ySpot, x1Spot,y1Spot,floorNo,categoryId,id);
//            });
            $("#confirm").on("click",function(e){
                var	areaName= $("#areaName").val();
                var	id= $("#idid").val();
            	var param ={
            			id : id,
            			name : areaName
                	};
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/shop/api/checkName", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".sameInfo").addClass("Validform_wrong"); 
		            		  $(".sameInfo").text(i18n_sameName); 
		            		  return false;
		            	  }else{
		          			$(".demoform").submit();
		            	  }
		              }
		        });
            	
            });
            
            $("input[data-type='edt']").live("click",function(e){
            	$("i[data-type='tip']").remove();
            	$(".demoform").Validform().resetForm();
            	$(".sameInfo").removeClass("Validform_wrong");
            	$(".sameInfo").text("");
            	$('a[href="#myModal1"]').attr("disabled",false);
            	var placeId = $(this).data("placeid");
            	var category = $(this).data("categoryid"); 
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
	        	console.log(rowObj);
	           	var floor = rowObj.childNodes[1].innerHTML,
	           		areaName = $(rowObj.childNodes[3].childNodes[0]).attr("title"),
	           		isVipId = rowObj.childNodes[4].innerHTML,
	           	    xSpot = rowObj.childNodes[5].innerHTML,
	           	 	ySpot = rowObj.childNodes[6].innerHTML,
	           	 	x1Spot = rowObj.childNodes[7].innerHTML,
	           	 	y1Spot = rowObj.childNodes[8].innerHTML
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
	           	$("#placeSel").val(placeId);
				$.post("/sva/map/api/getMapDataByStore",{id:placeId}, function(data){
					if(!data.error){
						
						$("#idid").val(data1.id);
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});
				
				$("input[name='shopName']").val(HtmlDecode2(areaName));
	           	$("input[name='xSpot']").val(xSpot);
	           	$("input[name='ySpot']").val(ySpot);
	           	$("input[name='x1Spot']").val(x1Spot);
	           	$("input[name='y1Spot']").val(y1Spot);
	           	if(isVipId=="是"){
	           		isVipId = "Y";
	           	}else {
	           		isVipId = "N";
	           	}
	           	$("#isVipId").val(isVipId);
	           	$("#category").val(category);
	           	$("#editBox").show();
           	 
            });
            
            $("input[data-type='fuzhi']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	$(".sameInfo").removeClass("Validform_wrong");
            	$(".sameInfo").text("");
            	$('a[href="#myModal1"]').attr("disabled",false);
            	var placeId = $(this).data("placeid");
            	var category = $(this).data("categoryid"); 
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
	           	var floor = rowObj.childNodes[1].innerHTML,
	           		areaName = $(rowObj.childNodes[3].childNodes[0]).attr("title"),
	           	    xSpot = rowObj.childNodes[5].innerHTML,
	           	 	ySpot = rowObj.childNodes[6].innerHTML,
	           	 	x1Spot = rowObj.childNodes[7].innerHTML,
	           	 	y1Spot = rowObj.childNodes[8].innerHTML
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
	           	$("#placeSel").val(placeId);
				$.post("/sva/map/api/getMapDataByStore",{id:placeId}, function(data){
					if(!data.error){
						
//						$("#idid").val(data1.id);
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});
				
				$("input[name='shopName']").val(HtmlDecode2(areaName));
	           	$("input[name='xSpot']").val(xSpot);
	           	$("input[name='ySpot']").val(ySpot);
	           	$("input[name='x1Spot']").val(x1Spot);
	           	$("input[name='y1Spot']").val(y1Spot);
	           	$("#category").val(category);
	           	$("#editBox").show();
           	 
            });
    	},
        
        initTable:function(){
        	$.post("/sva/shop/getShopInfo?t="+Math.random(),function(data){
        		if(data.error == 200){
        			console.log(data);
        			if(oTable){oTable.fnDestroy();};
        			oTable = $('#table').dataTable({
        				"bProcessing": true,
        				"sDom": 'rt<"toolbar"lp<"clearer">>',
        				"sPaginationType": "full_numbers",
        				"aaData":data.data,
        				"bStateSave": true,
        				"aoColumnDefs": [
        					{ 
        						"aTargets": [0],
//        						"bVisible": false,
        						"mData": "storeName",
        						"mRender": function ( data, type, full ) {
    								if (data.length>10) {
    									var html = data.substring(0,10)+"...";
    									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
    										return html;
    								}
    								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
    							}
        					},
        					{ 
        						"aTargets": [1],
        						"mData": "floorName"
        					},
        					{ 
        						"aTargets": [2],
        						"mData": "categoryName"
        					},
        					{ 
        						"aTargets": [3],
        						"mData": "shopName",
        						"mRender": function ( data, type, full ) {
    								if (data.length>10) {
    									var html = data.substring(0,10)+"...";
    									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
    										return html;
    								}
    								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
    							}
        					},
        					{ 
        						"aTargets": [4],
        						"mData": "isVip",
        						"mRender": function ( data, type, full ) {
    								if (data=="Y") {
										return "是";
    								}else {
    									return "否";
    								}
    							}
        					},
        					{ 
        						"aTargets": [5],
        						"mData": "xSpot"
        					},
        					{ 
        						"aTargets": [6],
        						"mData": "ySpot"
        					},
        					{ 
        						"aTargets": [7],
        						"mData": "x1Spot"
        					},
        					{ 
        						"aTargets": [8],
        						"mData": "y1Spot"
        					},
        					{
        	                    "aTargets": [9],
        	                    "bSortable": false,
        	                    "bFilter": false,
        	                    "mData": function(source, type, val) {
        	                        return "";
        	                    },
        	                    "mRender": function ( data, type, full ) {
        	                    	var html = "" +
        	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="fuzhi" data-placeid="'+full.storeId+'" data-categoryid="'+full.categoryId+'" data-id="'+full.mapId+'" value="'+i18n_fuzhi+'">' +
        	                    		'<input type="button" data-type="edt" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-placeid="'+full.storeId+'" data-categoryid="'+full.categoryId+'" data-xSpot="'+full.xSpot+'" data-x1Spot="'+full.x1Spot+'"data-ySpot="'+full.ySpot+'" data-y1Spot="'+full.y1Spot+'"data-floorno="'+full.mapId+'" value="'+i18n_edit+'" id="'+full.id+' ">' +
        	                    		'<input type="button" data-type="del" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-floorNo="'+full .mapId+'" data-categoryid="'+full.categoryId+'" id="'+full.id+'" data-id="'+full.id+'" data-xSpot="'+full.xSpot+'"data-x1Spot="'+full.x1Spot+'" data-ySpot="'+full.ySpot+'" data-y1Spot="'+full.y1Spot+'" value="'+i18n_delete+'">';
        	                    		//'<a data-type="preview" role="button" class="btn"  style="font-size:13px;" data-floorNo="'+full.floorNo+'" id="'+full.id+'"  data-xSpot="'+full.xSpot+'" data-ySpot="'+full.ySpot+'" data-pictrue="'+full.pictruePath+'">'+i18n_Preview+'</a>';
        	                    		
        	                        return html;
        	                      }
        	                }
        				],
        				"fnCookieCallback": function (sName, oData, sExpires, sPath) {      
        					// Customise oData or sName or whatever else here     
        					var newObj = {iLength:oData.iLength};
        					return sName + "="+JSON.stringify(newObj)+"; expires=" + sExpires +"; path=" + sPath;    
        				}
        			});
        		}        		
        	});
        }
    };

}();
function checkMsg()
{
	var placeSel =$("#placeSel").val();
	var floor=$("select[name='floorNo']").find("option:selected").text();
	var areaName=$("input[name='shopName']").val();
	var xSpot=$("input[name='xSpot']").val();
	var ySpot=$("input[name='ySpot']").val();
	var x1Spot=$("input[name='x1Spot']").val();
	var y1Spot=$("input[name='y1Spot']").val();
	
	if (xSpot==""||ySpot==""||floor==""||x1Spot == "" || y1Spot=="" || placeSel == ""||areaName== "") {
		alert(i18n_info);
		return false;
	}

}
function HtmlDecode2(str) {
	var str1 = str.replace(/&lt;/g,"<");
	var str2 = str1.replace(/&gt;/g,">");
	var str3 = str2.replace(/&amp;/g,"&");
	var str4 = str3.replace(/&quot;/g,"\"");
	var str5 = str4.replace(/&apos;/g,"\'");
    return str5;
     }

function estimateOnkeyup(str)
{
	if (isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
		str.value = str.value.substring(0,str.value.length-1);
		str.focus();
		return false;
	}
	if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1)))
	{
	str.value = "";
	str.focus();
	return false;
	}
	if (str.value.split(".").length<2) {
		var a = parseInt(str.value.substring(str.value.length-1,str.value.length));
		if (a<0)
		{
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;
		}
	}else
		{
		var c = str.value.split(".")[1];
		var b = str.value.split(".")[0];
		if (c.length>2) 
		{
			str.value = str.value.substring(0,b.length+3);
			str.focus();
			return false;	
		}else
			{
			if (isNaN(str.value))
			{
				str.value = str.value.substring(0,str.value.length-1);
				str.focus();
				return false;
			}else
				{
				if(str.value.split(".")[1]!=""&&str.value.split(".")[1]!="0"&&str.value.split(".")[1]!="00")
					{
					str.value = parseFloat(str.value);
					str.focus();
					return false;
					}
				return false;
				}
			
			
			}
		}
	}
	
function addMap()
{
//	$("#search").hide();
	$(".demoform").Validform().resetForm();
	$(".sameInfo").removeClass("Validform_wrong");
	$(".sameInfo").text("");
	clearinfo();
	$("#editBox").show();	
}
function hideAdd()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
}

function clearinfo()
{
	$("i[data-type='tip']").remove();
	$("#placeSel").val("");
	$("#zSel").val("");	
	$("#areaName").val("");	
	$("#pointX1").val("");
	$("#pointY1").val("");
	$("#pointX2").val("");
	$("#pointY2").val("");
	$("#idid").val("");	
	$("#zSel").find("option").remove();
}

function closeModel()
{
	$("#myModal").modal('hide');
	//$("#movie").empty();
}

