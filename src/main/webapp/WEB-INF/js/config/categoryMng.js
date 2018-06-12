var Category = function () {
	var refreshTable = function(){
		$.post("/sva/category/api/doquery",function(data){
			if(!data.error){
				if(oTable){oTable.fnDestroy();};
				oTable = $('#cate-datatable').dataTable({
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
					"aaSorting" : [ [ 2, "desc" ] ],
					"aoColumnDefs": [
						{ 
							"aTargets": [0],
							"bVisible": false,
							"bSearchable": false,
							"mData": "id" 
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
							"mData": "x",
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
							"aTargets": [5],
							"mData": "y",
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
							"aTargets": [6],
							"mData": "deepTime",
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
							"aTargets": [7],
							"mData": "visitorNumber",
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
							"aTargets": [8],
							"bSortable": false,
							"bFilter": false,
							"bSearchable": false,
							"mData": function(source, type, val) {
								return "";
							},
							"mRender": function ( data, type, full ) {
								var html = "" +
									'<input type="button" class="btn btn-warning" data-type="edit" style="width: 49px;" data-id="'+full.id+'" id="edit" value="'+iedit+'">' +
									'<input type="button" class="btn btn-danger" data-type="del" style="width: 65px;margin-left:2px" data-id="'+full.id+'" id="delete" value="'+idelete+'">';
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
		$("#categoryId").val("");
		$("#categoryName").val("");
		$("#categoryName").removeClass("Validform_error");
		$(".Validform_checktip").removeClass("Validform_right");
		$(".Validform_checktip").text("");
		$(".Validform_checktip").removeClass("Validform_wrong");
		$(".sameInfo").removeClass("Validform_wrong");
		$(".sameInfo").text("");
	};
	
	var checkInput = function(){
		var name=$("#categoryName").val();
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
            $("input[data-type='del']").live("click",function(e){
            	if(confirm("delete data?")){
	            	var id = $(this).data("id");	           	 
	        		$.post("/sva/category/api/deleteData",{id:id},function(data){
	        			if(data.error){
	        				$("#info").text(data.error);
	            			$(".alert").removeClass("alert-info");
	            			$(".alert").addClass("alert-error");
	            			$("#alertBox").show();
		           		}else{
	            			refreshTable();
		           		}
	        		});
            	}
            });
            
            $("#confirm").on("click",function(e){
            	if(!checkInput()){
            		$(".Validform_checktip").addClass("Validform_wrong");
            		$(".Validform_checktip").text(i18n_storeinput);
            		$("#categoryName").addClass("Validform_error");
            		
            	}
            var	a= $("#categoryName").val();
            	if (a=="") {
            		return false;
				}
            	var param ={
        			id : $("#id").val(),
        			name : $("#categoryName").val()
            	};
            	
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/category/api/saveData", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".Validform_checktip").removeClass("Validform_right");
		            		  $(".Validform_checktip").text("");
		            		  $(".sameInfo").addClass("Validform_wrong");
		            		  $(".sameInfo").text(i18n_categorySame);
		            		  $("#info").text(data.error);
		            		  $(".alert").removeClass("alert-info");
		            		  $(".alert").addClass("alert-error");
		            		 // $("#alertBox").show();
		            	  }else{
		            		  $("#editBox").hide();
		            		  clearInput();
		            		  refreshTable();
		            	  }
		              }
		        });
            	
            });
            
            $("#add").on("click",function(e){
            	clearInput();
            	$("#editBox").show();
//            	$("#updateBox").hide();
            });
            $("#Confirm").on("click",function(e){
            	var pass = true;
				if ($("#categoryId").val() == "") {
					checkPass(false, inotnull, "categoryId");
					pass = false;
				} else {
					checkPass(true, "", "categoryId");
				}

				if ($("#deepTime").val() == "") {
					checkPass(false, inotnull, "deepTime");
					pass = false;
				} else {
					checkPass(true, "", "deepTime");
				}
				
				if ($("#visitorNumber").val() == "") {
					checkPass(false, inotnull, "visitorNumber");
					pass = false;
				} else {
					checkPass(true, "", "visitorNumber");
				}
				if ($("#x").val() == "" || $("#y").val() == "") {
					checkPass(false, inotnull, "x");
					pass=false;
				} else {
					if (!isNaN($("#x").val()) || !isNaN($("#y").val())) {
						checkPass(true, "", "x");
					} else {
						checkPass(false, inotshuzi, "x");
						pass = false;
					}
				}
				// $("#formId").submit();
				$.post("../category/api/check", {
					categoryName : $("#categoryId").val(),
					id : $("#idid").val()
				}, function(data) {
					if (data.data == false) {
						checkPass(false, inotsame, "categoryId");
						pass = false;
					}

					if (pass) {
						$("#formId").submit();
					}
				});
            	
            }); 
            $("#Cancel").on("click",function(e){
            	clearInput();
            	$("#editBox").hide();
            });
            $("input[data-type='edit']").live("click",function(e){
            	clearInput();
            	var row = $(this).parent().parent();
				var data = oTable.fnGetData(row[0]);
            	$("#idid").val(data.id);
            	$("#categoryId").val(data.name);
            	$("#x").val(data.x);
            	$("#y").val(data.y);
            	$("#deepTime").val(data.deepTime);
            	$("#visitorNumber").val(data.visitorNumber);
            	$("#editBox").show();
            });
    	}
    };
    
}();

