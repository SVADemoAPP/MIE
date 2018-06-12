var MapMng = function () {
	
	var updateList = function(renderId,data,selectTxt,callback){
	    var sortData = data.sort();
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	if(selectTxt == sortData[i]){
	    		options += '<option class="addoption" selected=true value="'+sortData[i].name+'">' + sortData[i].name +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+sortData[i].name+'">' + sortData[i].name +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
    var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};
	var updatePosition = function(renderId,data,selectTxt,callback){
	    var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].name){
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
	var clacImgZoomParam = function( maxWidth, maxHeight, width, height ){  
	    var param = {top:0, left:0, width:width, height:height};  
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
	var previewImage = function(file)  
	{  

		$('#textfield').val(file.value);
		$('#textfield').blur();
 		var val = $("#scaleId").val();
 		var textfield = $("#textfield").val();
 		var coorSel = $("#coorSel").val();
 		if(val != "" && textfield != "" && coorSel != ""){
 			$('a[data-type="point"]').attr("disabled",false);
 		}else{
 			$('a[data-type="point"]').attr("disabled","disabled");
 		}
		var MAXWIDTH  = 868;  
		var MAXHEIGHT = 500;  
		var div = document.getElementById('preview'); 
		var image=new Image(); 
		if (file.files && file.files[0]){  
			$('a[data-type="scale"]').attr("disabled",false);
			div.innerHTML = '<img id=imghead>';  
		    var img = document.getElementById('imghead'); 
			img.onload = function(){	
				
				rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, image.width, image.height);  
				img.width = rect.width;  
				img.height = rect.height;
				$("#preview").css({
					"width" : rect.width + "px",
					"height" : rect.height + "px",
					"margin-left" : rect.left + 'px',
					"margin-top" : rect.top + 'px'
				});
			}; 
			var reader = new FileReader();  
			reader.onload = function(evt){
				img.src = evt.target.result;
				image.src = evt.target.result;
			};
			reader.readAsDataURL(file.files[0]);
		} else 
			//兼容IE          
			{            
				$(".preview_size_fake").show();
				var objPreviewSizeFake = $(".preview_size_fake").get(0);
				objPreviewSizeFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = file.value; 
				image.src = file.value;
				rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, objPreviewSizeFake.offsetWidth, objPreviewSizeFake.offsetHeight);            
				$('a[data-type="scale"]').attr("disabled",false);
				$("#preview").css({
					"height" : rect.height + "px",
					"width" : rect.width + "px",
					"margin-left" : rect.left + 'px',
					"margin-top" : rect.top + 'px',
					"filter" : "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src='" + file.value + "'"
				});
			}
	};
    return {        
    	initSelect: function(){
    		
    		$.post("/sva/market/api/getData?t="+Math.random(),function(data){
        		if(!data.error){
        			updatePosition("placeIdId",data.data);
        		}
        	});
    	},
        initMapTable:function(){
        	$.post("/sva/map/api/getTableData?t="+Math.random(),function(data){
        		if(!data.error){
        			if(oTableMap){oTableMap.fnDestroy();};
        			oTableMap = $('#tableMap').dataTable({
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
        						"mData": "floor"
        					},
        					{ 
        						"aTargets": [2],
        						"mData": "mapId"
        					},
        					{ 
        						"aTargets": [3],
        						"mData": "xo"
        					},
        					{ 
        						"aTargets": [4],
        						"mData": "yo"
        					},
        					{ 
        						"aTargets": [5],
        						"mData": "scale"
        					},
        					{
        	                    "aTargets": [6],
        	                    "bSortable": true,
        	                    "bFilter": false,
        	                    "mData": function(source, type, val) {
        	                        return "";
        	                    },
        	                    "mRender": function ( data, type, full ) {
        	                    	var html ;
        		        			if (full.coordinate=="ul") {
        		        			html = 'Upper left'; 
        							}
        		        			if (full.coordinate=="ll") {
        		        			html = 'Lower left'; 
        							}
        		        			if (full.coordinate=="ur") {
        		        			html = 'Upper right'; 
        							}
        		        			if (full.coordinate=="lr") {
        		        			html = 'Lower right'; 
        							}
        	                    	
        	                        return html;
        	                      }
        	                },
        					{ 
        						"aTargets": [7],
        						"mData": "angle"
        					},
        					{ 
        						"aTargets": [8],
        						"mData": "path",
        						"mRender": function ( data, type, full ) {
        							if (data!=null) {
        								if (data.length>6) {
        									var html = data.substring(0,6)+"...";
        									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
        									return html;
        								}
        								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
									}else
										{
										return '<span></span>';
										}
    							}
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
        	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="fuzhi" data-id="'+full.id+'" value="'+i18n_fuzhi+'">' +
        	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="edit" data-id="'+full.id+'" value="'+i18n_edit+'">' +
        	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="del" id="'+full.id+'" data-id="'+full.id+'" data-floorno="'+full.mapId+'" value="'+i18n_delete+'">';
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
        },
        deleteMap:function(mapId,id){
            if(confirm(i18n_deleteInfo)){
	        	$.post("/sva/map/api/deleteByFloor",{mapId:mapId},function(data){
	        		if(!data.error){
	        			MapMng.initMapTable();
	        		}
	        	});
	        }
        },
        editPreviewImage:function(path,imgWidth,imgHeight){
        	var row = $(this).parent().parent();
//        	var data = oTableMap.fnGetData(row[0]);
    		var MAXWIDTH  = 868;  
    		var MAXHEIGHT = 500;  	
    		rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, imgWidth, imgHeight);  
    		$("#preview").empty();
			$("#preview").css({
				"width" : rect.width + "px",
				"height" : rect.height + "px",
				"margin-left" : rect.left + 'px',
				"margin-top" : rect.top + 'px',
				"background-image": "url(../upload/map/" + path + ")",
				"background-size":"cover",
				"-moz-background-size": "cover"
			});
    		$('a[href="#myModal"]').attr("disabled",false);
     	},
        bindClickEvent:function(){
         	$("input[data-type='del']").live("click",function(e){
             	var mapId = $(this).data("floorno"),
             		id = $(this).data("id");
             	MapMng.deleteMap(mapId,id);
         	});
         	
        	$("#fileField").on("change",function(e){
        		var file = this;
        		previewImage(file);
        	});
        	
        	$("#svgField").on("change",function(e){
        		var file = this;
        		$('#textsvg').val(file.value);
        		$('#textsvg').blur();
        	});
        	$("#naviField").on("change",function(e){
        		var file = this;
        		$('#textnavi').val(file.value);
        		$('#textnavi').blur();
        	});
        	$('a[href="#myModal"]').on("click",function(e){
        		if(typeof($(this).attr("disabled"))!="undefined"){
        			e.preventDefault();
        			return false;
        		}
        	});        	

         	$('#preview').click(function(e){

                var left=e.pageX;
                var top=e.pageY;

                var o={
                    left:left,
                    top:top
                };
                var datas = Ploy.getData();
                var type = $("#typeModal").val();
                if(type=="scale"){
	                if(datas.length<2){
	                	var lengths=Ploy.makePoly('#preview',o);
	                	$("#pixDis").val(lengths);
	                    console.log(lengths);
	                }
                }else{
                	if(datas.length<1){
	                	Ploy.addPoint(top,left);
	                	var t=top-$('#preview').offset().top;
	                    var l=left-$('#preview').offset().left;
	                    $("#pointX").val(l);
	             		$("#pointY").val(t);
	                }
                }

            });
         	
         	$(".clearPaper").on("click",function(e){
         		Ploy.clearPaper();
         		$("#pixDis").val("");
     			$("#pointX").val("");
         		$("#pointY").val("");
         	});
         	
         	$("#scaleOk").on("click",function(e){
         		var coordinate = $("#coorSel").val();
				var type = $("#typeModal").val();
         		var real = $("#realDis").val();
         		var pix = $("#pixDis").val();
         		//判断原点位置
         		switch (coordinate){
	         		case "ul":
	         			var px =$("#pointX").val();
	             		var py =$("#pointY").val();
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			var px =$("#pointX").val();
	             		var py =imagey-$("#pointY").val();
	         			break;
	         		case "ur":
	         			imagex = rect.width;
	         			var px =imagex-$("#pointX").val();
	             		var py =$("#pointY").val();
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var px =imagex-$("#pointX").val();
	             		var py =imagey-$("#pointY").val();
	         			break;
         		}
         		if(type=="scale"){
	         		if(real&&pix){
	         			var scaleVal = (parseFloat(pix)*rect.zoomScale/parseFloat(real)).toFixed(2);
	         			$("#scaleId").val(scaleVal);
	         			$("#scaleId").blur();
//	         			$("#scaleId").change();
	         			$("#myModal").modal('hide');
	         			$("#alertBoxScale").hide();
	         		}else{
	         			
	         			$("#infoScale").text('Enter the actual distance(greater than zero), and choose the length on the map');
	         			$("#alertBoxScale").show();
	         		}
         		}else{
         			if(px){
         				var scale = $("#scaleId").val();
	         			$("#xId").val((parseFloat(px)*rect.zoomScale/parseFloat(scale)).toFixed(2));
	         			$("#yId").val((parseFloat(py)*rect.zoomScale/parseFloat(scale)).toFixed(2));
	         			$("#xId").blur();
	         			$("#yId").blur();
	         			$("#myModal").modal('hide');
	         			$("#alertBoxScale").hide();
	         		}else{	         			
	         			$("#infoScale").text('Please select the origin location on the map!');
	         			$("#alertBoxScale").show();
	         		}
         		}
         	});
         	
         	
         	$('a[href="#myModal"]').on("click",function(e){
         		$("#infoScale").text("");
         		$("#alertBoxScale").hide();
         		var type = $(this).data("type");
         		$("#typeModal").val(type);
         		Ploy.clearPaper();
         		$("#realDis").val("");
     			$("#pixDis").val("");
     			$("#pointX").val("");
         		$("#pointY").val("");
         		if(type=="scale"){
         			$("#modalHeader2").hide();
         			$("#modalHeader1").show();
         		}else{
         			$("#modalHeader1").hide();
         			$("#modalHeader2").show();
         		}
         	});
         	
         	$("#scaleId").on("change",function(e){
         		$("#infoScale").text("");
         		$("#alertBoxScale").hide();
         		var val = $("#scaleId").val();
         		var textfield = $("#textfield").val();
         		var coorSel = $("#coorSel").val();
//         		var check = validForm.check(false,$("#scaleId"),$("#textfield"),$("#coorSel"));
         		if(val != "" && textfield != "" && coorSel != ""){
         			$('a[data-type="point"]').attr("disabled",false);
         		}else{
         			$('a[data-type="point"]').attr("disabled","disabled");
         		}
         	});
         	$("#coorSel").on("change",function(e){
         		
         		var val = $("#scaleId").val();
         		var textfield = $("#textfield").val();
         		var coorSel = $("#coorSel").val();
         		if(val != "" && textfield != "" && coorSel != ""){
         			$('a[data-type="point"]').attr("disabled",false);
         		}else{
         			$('a[data-type="point"]').attr("disabled","disabled");
         		}
         	});
         	
         	$("input[data-type='edit']").live("click",function(e){
         		$("i[data-type='tip']").remove();
            	$(".demoform").Validform().resetForm();
            	var row = $(this).parent().parent();
            	var data = oTableMap.fnGetData(row[0]);
            	$("#placeIdId").val(data.storeId);
        		$("#floorNoId").val(data.floor);
        		$("#floorNameId").val(data.mapId);
        		$("#scaleId").val(data.scale);
        		$("#xId").val(data.xo);
        		$("#yId").val(data.yo);
        		$("#textfield").val(data.path);
        		$("#pathId").val(data.path);
        		$("#coorSel").val(data.coordinate);
        		$("#angleId").val(data.angle);
        		$("#idid").val(data.id);
        		$("#editBox").show();
        		$(".sameInfo").removeClass("Validform_wrong");
        		$(".sameInfo").text("");
        		//
        		var MAXWIDTH  = 868;  
        		var MAXHEIGHT = 500;  	
        		rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, data.imgWidth, data.imgHeight);  
        		$("#preview").empty();
				$("#preview").css({
					"width" : rect.width + "px",
					"height" : rect.height + "px",
					"margin-left" : rect.left + 'px',
					"margin-top" : rect.top + 'px',
					"background-image": "url(../upload/" + data.path + ")",
					"background-size":"cover",
					"-moz-background-size": "cover"
				});
        		$('a[href="#myModal"]').attr("disabled",false);
            });
         	
         	$("input[data-type='fuzhi']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	var row = $(this).parent().parent();
            	var data = oTableMap.fnGetData(row[0]);
            	$("#placeIdId").val(data.storeId);
//            	$("#svgfield").val(data.svg);
        		$("#floorNoId").val(data.floor);
        		$("#floorNameId").val(data.mapId);
//        		$("#scaleId").val(data.scale);
//        		$("#xId").val(data.xo);
//        		$("#yId").val(data.yo);
//        		$("#textfield").val(data.path);
        		$("#coorSel").val(data.coordinate);
        		$("#angleId").val(data.angle);
        		$("#editBox").show();
        		$(".sameInfo").removeClass("Validform_wrong");
        		$(".sameInfo").text("");
        		//
        		var MAXWIDTH  = 868;  
        		var MAXHEIGHT = 500;  	
        		rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, data.imgWidth, data.imgHeight);  
        		$("#preview").empty();
				$("#preview").css({
					"width" : rect.width + "px",
					"height" : rect.height + "px",
					"margin-left" : rect.left + 'px',
					"margin-top" : rect.top + 'px',
					"background-image": "url(../upload/" + data.path + ")",
					"background-size":"cover",
					"-moz-background-size": "cover"
				});
            	$('a[href="#myModal"]').attr("disabled","disabled");
            });
        }
        
        
    };

}();

 function hanshu(str){
	 var scalevalue = str.value;	
	 if(scalevalue == 0 || scalevalue <0){
		 alert(i18n_isscale);
		 $("input[name='scale']").val("");		
		 return false;
	 }
 }
function estimateOnkeyup(str)
{
	var realDis = $("#realDis").val();
	if(realDis == 0){
		//alert(i18n_msg_distance);
		$("#infoScale").text('The distance must be greater than zero');
		$("#realDis").val("");
		//$("#scaleOk").attr("disabled","disabled");
		return false;
	}
	if(isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;
	}
	if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1))){
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
//实际距离小数点后面只能够保存一位
function estimateOnkeyupOne(str)
{
	if(isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
		str.value = str.value.substring(0,str.value.length-1);
		str.focus();
		return false;
	}
	if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1))){
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
		if (c.length>1) 
		{
			str.value = str.value.substring(0,b.length+2);
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
 

function mapinfo(str)
{
	document.getElementById("mapImage").src = str.getAttribute("id");
}
function onDown()
{
	if (event.button==1) {
		var x = event.clientX;;
		var y = event.clientY;
		alert(x+y);
	}

}
function addMap()
{
//	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").show();
	$(".sameInfo").removeClass("Validform_wrong");
	$(".sameInfo").text("");
}
function hideAdd()
{
//	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
}
function clearinfo()
{
	$("i[data-type='tip']").remove();
	$("#storeId").val("");
	$("#floor").val("");
	$("#mapId").val("");
	$("#scaleId").val("");
	$("#xo").val("");
	$("#yo").val("");
	$("#textfield").val("");
	$("#textsvg").val("");
	$("#textnavi").val("");
	$("#fileField").val("");
	$("#svgField").val("");
	$("#naviField").val("");
	$("#coorSel").val("");	
	$("#angle").val("");
//	$("#placeIdId").val("");	
//	$("#routefield").val("");	
	$("#idid").val("");	
	$('a[href="#myModal"]').attr("disabled","disabled");
   	$(".sameInfo").removeClass("Validform_wrong");
   	$(".sameInfo").text("");
   	
   	$("#formParams i").remove();
//	$("#pathId").val("");
}
