var Store = function () {
	
	var refreshTable = function(){
		$.post("/sva/market/api/getData?t="+Math.random(),function(data){
			if(data.status==200){
				if(oTable){oTable.fnDestroy();};
				oTable = $('#table').dataTable({
					"sPaginationType": "bs_full", // "bs_normal",
													// "bs_two_button",
													// "bs_four_button",
													// "bs_full"
					"fnPreDrawCallback": function( oSettings ) {
				    	$('.dataTables_filter input').addClass('form-control input-large').attr('placeholder',isearch);
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
					"aaSorting" : [ [ 2, "desc" ] ],
					"bStateSave": true,
					"aoColumnDefs": [
						{ 
							"aTargets": [0],
							"bVisible": false,
							"mData": "updateTime" 
						},
							{ 
							"aTargets": [1],
							"mData": "name",
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
							"mData": "createTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{ 
							"aTargets": [3],
							"mData": "updateTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{
							"aTargets": [4],
							"bSortable": false,
							"bFilter": false,
							"bSearchable": false,
							"mData": function(source, type, val) {
								return "";
							},
							"mRender": function ( data, type, full ) {
								var html = "" +
									'<input type="button" data-type="edit" style="width: 49px;" class="btn btn-warning"  data-id="'+full.id+'" value="'+iedit+'">' +
									'<input type="button" data-type="del" style="width: 65px;margin-left:2px" class="btn btn-danger" data-id="'+full.id+'" value="'+idelete+'">';
								
								return html;
							}
						}
					]
				});
			}
		});
	};
	
	var clearInput = function(){
		$("i[data-type='tip']").remove();
		$("#id").val("");
		$("#storeName").val("");
	};
	
	var checkInput = function(){
		var name=$("#storeName").val();
		if (name=="") {
			return false;
		}else{
			return true;
		}
	};

	return {
        
    	initTable:function(){
    		refreshTable();
        },	
        
		bindClickEvent: function(){
			$("input[data-type='edit']").live("click",function(e){
				$("i[data-type='tip']").remove();
				var row = $(this).parent().parent();
				var data = oTable.fnGetData(row[0]);
				$("#idid").val(data.id);
    			$("#storeName").val(data.name);
    			$("#editBox").show();
            });
            
            $("input[data-type='del']").live("click",function(e){
            	if(confirm("delete data?")){
	            	var id = $(this).data("id");	           	 
	        		$.post("/sva/market/api/deleteData",{id:id},function(data){
	        			if(data.error){
		           		}else{
	            			refreshTable();
		           		}
	        		});
            	}
            });
            
            $("#confirm").on("click",function(e){
        		var name=$("#storeName").val().trim();
        		var id = $("#idid").val().trim();
        		var check = false;
        		if (name=="") {
        			setTip("notPass",inotnull,"storeName");
        			return false;
        		}else{
	        		$.post("/sva/market/api/checkName",{name:name,id:id},function(data){
	        			if(data.status==0){
	        				setTip("notPass",isame,"storeName");
	        				return false;
		           		}else{
		        			setTip("pass",ipass,"storeName");
		           			check =true;
		           			$.post("/sva/market/api/saveData",{name:name,id:id},function(data){
		           				location.reload() 
		           			});
		           		}
	        		});

        		}
            	
            });
            
            $("#cancel").on("click",function(e){
            	$("#editBox").hide();
            	clearInput();
            });
            
            $("#add").on("click",function(e){
            	clearInput();
            	$("#editBox").show();
            });
    	}
    };

}();