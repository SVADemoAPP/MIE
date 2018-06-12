var Ticket = function(){
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,callback,selectTxt){
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '<option value=""></option>';
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
	
	var updateFloorList = function(renderId,data,selectTxt,callback){
		$("#floorId").find("option").remove(); 
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
	var updateShopList = function(renderId,data,selectTxt,callback){
		$("#shopId").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '<option value=""></option>';
	    for(var i=0;i<len;i++){ 
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].id){
	    		options += '<option class="addoption"  selected=true value="'+sortData[i].id+'">' + sortData[i].shopName +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+sortData[i].id+'">' + sortData[i].shopName +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};	
    var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};
	var clearData = function()
	{
		$("i[data-type='tip']").remove();
		$("#storeId").val("");
	    $("#floorId").val("");
		$("#ticketId").val("");
		$("#chanceId").val("");
		$("#idid").val("");
		$("#shopId").val("");
	}
	var getTableData = function(data)
	{
		var len = data.data.length;
		var data = data.data;
		var option = '';
		var classDatd;
	    for(var i=0;i<len;i++){
	    	var shopName = data[i].shopName;
	    	var floorName = data[i].floorName;
	    	var storeName = data[i].storeName;
	    	var creatTime = data[i].creatTime;
	    	var ticketName = data[i].ticketName;
	    	var id = data[i].id;
	    	var chanceName = data[i].chanceName;
	    	if (i%2==0) {
	    		classDatd = "odd";
			}else
				{
				classDatd = "even";
				}
	    	if (i==(len-1)) {
	    		option += '<tbody><tr class="'+classDatd+'"><td>'+storeName+'</td><td>'+floorName+'</td><td>'+shopName+'</td><td>'+ticketName+'</td><td>'+chanceName+'</td><td>'+creatTime+'</td></tr></tbody>';
			}else
				{
				option +='<tbody><tr class="'+classDatd+'"><td>'+storeName+'</td><td>'+floorName+'</td><td>'+shopName+'</td><td>'+ticketName+'</td><td>'+chanceName+'</td><td>'+creatTime+'</td></tr>';
				}
	    }
	    if (len>0) {
	    	$(".dataTables_empty").parent().remove();
	    	$("#datatable").append(option);
		}
	}
	var refreshTable = function ()
	{
		$.post("/sva/ticket/api/getData",function(data){
			if (!data.error) {
    			if(oTable){oTable.fnDestroy();};
				 oTable = $('#table').dataTable({
					"sPaginationType": "bs_full", //"bs_normal", "bs_two_button", "bs_four_button", "bs_full"
					"fnPreDrawCallback": function( oSettings ) {
				    	$('.dataTables_filter input').addClass('form-control input-large').attr('placeholder', isearch);
						$('.dataTables_length select').addClass('form-control input-small');
				    },
				    "oLanguage": {
					    "sSearch": "",
					    "sLengthMenu": "<span>_MENU_ "+ientries+"</span>"
					},
    				"oLanguage":{
    					"sUrl":table_language
    				},
					"bJQueryUI": false,
					"bAutoWidth": false,
					"sDom": "<'row'<'col-lg-6 col-md-6 col-sm-12 text-center'l><'col-lg-6 col-md-6 col-sm-12 text-center'f>r>t<'row-'<'col-lg-6 col-md-6 col-sm-12'i><'col-lg-6 col-md-6 col-sm-12'p>>",
					"bProcessing": true,
					"aaData":data.data,
					"bStateSave": true,
					"aaSorting" : [ [ 6, "desc" ] ],
					"aoColumnDefs": [
						{ 
							"aTargets": [0],
							"bVisible": false,
							"bSearchable": false,
							"mData": "id" 
						},
							{ 
							"aTargets": [1],
							"mData": "storeName",
							"mRender": function ( data, type, full ) {
								if(data==null){
									return "";
								}
								if(data.length > 40){
									var html = data.substring(0,40)+"...";
									html = '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return HtmlDecode3(data);
							}
						},
						{ 
							"aTargets": [2],
							"mData": "floorName"
						},
						{ 
							"aTargets": [3],
							"mData": "shopName"
						},
						{ 
							"aTargets": [4],
							"mData": "ticketName"
						},
						{ 
							"aTargets": [5],
							"mData": "chanceName"
						},
						{ 
							"aTargets": [6],
							"mData": "creatTime",
							"mRender": function ( data, type, full ) {

								return data.substring(0,19);
							}
						},
    					{
    	                    "aTargets": [7],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "bSearchable": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html = "" +
    	                    		'<input type="button" class="btn btn-warning" style="width: 49px;"  data-type="edt" onclick="editData(this)" id="editId" data-id="'+full.id+'" value="'+iedit+'">' +
    	                    		'<input type="button" class="btn btn-danger" style="width: 65px;margin-left:2px" data-type="del" id="deleteId"  data-id="'+full.id+'" value="'+idelete+'">' ;
    	                    		
    	                        return html;
    	                      }
    	                }
					]
				});
//				getTableData(data);
			}
		});
	};
	
	
	return {
		initSelect:function()
		{
			$.post("/sva/market/api/getData?t="+Math.random(),function(data){
				if (data.data.length>0) {
					updateList("storeId",data.data);
				}
			});
		},
		initTable:function(){
			refreshTable();
		},
		bindClickEvent:function()
		{
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#storeId").on("change", function(){
    			var storeId = $("#storeId").val();
				$.post("/sva/map/api/getMapDataByStore",{id:storeId}, function(data){
					if(!data.error){
						updateFloorList("floorId",data.data);
					}
				});
			});
    		$("#floorId").on("change", function(){
    			var mapId = $("#floorId").val();
				$.post("/sva/shop/getShopDataByMapId",{mapId:mapId}, function(data){
						updateShopList("shopId",data.data);
				});
			});
        	$("#ticketFile").on("change",function(e){
        		var file = this;
        		$("#ticketId").val(file.value);
        	});
         	$("input[data-type='del']").live("click",function(e){
             	var	id = $(this).data("id");
             	var param = {id:id};
                if(confirm("delete data?")){
                	$.post("/sva/ticket/api/deleteData",param,function(data){
                		if (data.reslut!=0) {
                			refreshTable();
						}else
							{
							alert("delete data failed");
							}
                	});
					
				}

         	});
         	
        	
            $("#Confirm").on("click",function(e){
            	var fi = $("#ticketId").val();
            	var mapId = $("#floorId").val();
            	var storeId = $("#storeId").val();
            	var shopId = $("#shopId").val();
            	var chanceId = $("#chanceId").val().trim();
          	  	var check = true;
          	  	if (storeId=="") {
              	  	setTip("notPass",inotnull,"storeId");
              	  	check = false;
              	  	}else
              	  		{
                  	  	setTip("pass",ipass,"storeId");
              	  		}
          	  	if (mapId==null||mapId=="") {
              	  	setTip("notPass",inotnull,"floorId");
              	  	check = false;
              	  	}else
          	  		{
                  	  	setTip("pass",ipass,"floorId");
              	  		}
          	  	if (shopId==null||shopId=="") {
              	  	setTip("notPass",inotnull,"shopId");
              	  	check = false;
              	  	}else
          	  		{
                  	  	setTip("pass",ipass,"shopId");
              	  		}
          	  	if (fi==null) {
              	  	setTip("notPass",inotnull,"selectId");
              	  	check = false;
              	  	}else
          	  		{
                  	  	setTip("pass",ipass,"selectId");
              	  		}
          	  	if (chanceId=="") {
          	  		setTip("notPass",inotnull,"chanceId");
              	  	check = false;
          	  		}else
          	  		{
          	  			if (!isNaN(chanceId)) {
							if (chanceId>0&&chanceId<=100) {
								setTip("pass",ipass,"chanceId");
							}else
								{
          	          	  		setTip("notPass",inotgl,"chanceId");
          	              	  	check = false;
								}
							
						}else
							{
      	          	  		setTip("notPass",inotgl,"chanceId");
      	              	  	check = false;
							}
              	  		}
          	  	var fi =fi.split(".")[fi.split(".").length-1];
          	  	if (fi!="jpg"&&fi!="png"&&fi!="PNG"&&fi!="JPG")
          	  		{
          	  	setTip("notPass",ipathf,"selectId");
          	  		check = false;
          	  		}else
          	  		{
                  	  	setTip("pass",ipass,"selectId");
              	  		}
            	if(check){
            		return true;
              		}else
              			{
              			return false;
              			}
            });

            $("#add").on("click",function(e){
            	clearData();
            	$("#editBox").show();
            });
            $("#Cancel").on("click",function(e){
            	clearData();
            	$("#editBox").hide();
            });            
		}
	}
}();
function editData(e)
{
	$("i[data-type='tip']").remove();
	$("#editBox").show();
	var row = $(e).parent().parent();
	var data = oTable.fnGetData(row[0]);
	$("#storeId").val(data.storeId);
	var storeId = $("#storeId").val();
	$.post("/sva/map/api/getMapDataByStore",{id:storeId}, function(data1){
			if(!data1.error){
				$("#floorId").find("option").remove(); 
			    var sortData = data1.data.sort(function(a,b){return a.floor - b.floor;});
			    var len = sortData.length;
			    var options = '<option value=""></option>';
			    for(var i=0;i<len;i++){
			    	var info = sortData[i];
			    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].mapId+'">' + sortData[i].floor +'</option>';
			    }
			    $('#floorId .addoption').remove().trigger("liszt:updated");
			    $('#floorId').append(options);
			    $("#floorId").val(data.mapId);
    			var mapId = $("#floorId").val();
				$.post("/sva/shop/getShopDataByMapId",{mapId:mapId}, function(data2){
					if(!data2.error){
						$("#shopId").find("option").remove(); 
					    var sortData = data2.data.sort(function(a,b){return a.floor - b.floor;});
					    var len = sortData.length;
					    var options = '<option value=""></option>';
					    for(var i=0;i<len;i++){
					    	var info = sortData[i];

					    		options += '<option class="addoption" value="'+sortData[i].id+'">' + sortData[i].shopName +'</option>';
					    	
					    }
					    $('#shopId .addoption').remove().trigger("liszt:updated");
					    $('#shopId').append(options);
						$("#shopId").val(data.shopId);
					}
				});
			}
			
		});

	$("#ticketId").val(data.ticketName);
	$("#chanceId").val(data.chanceName);
	$("#idid").val(data.id);
	
}
