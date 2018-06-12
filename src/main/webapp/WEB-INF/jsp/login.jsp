<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title><spring:message code="login_0" /></title>
<!-- Mobile specific metas -->
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- Force IE9 to render in normal mode -->
<!--[if IE]><meta http-equiv="x-ua-compatible" content="IE=9" /><![endif]-->
<meta name="author" content="SuggeElson" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="application-name" content="sprFlat admin template" />
<link href="../plugins/newJs/css/bootstrap.css" rel="stylesheet" />
<link rel="stylesheet" href="../css/index.css" rel="stylesheet" />
<link rel="icon" href="../plugins/newJs/img/ico/favicon.ico"
    type="image/png">
</head>
<body>
	   <div id="myDiv" class="section1">
        <div id="myDiv1"class="section1_left">
            <div class="logo"><i></i></div>
            <form method="post" action="../account/login">
            <div class="login">
                <div class="input-group input-group-lg" style="margin-bottom: 20px;">
                  <span class="input-group-addon"><i class="userIcon"></i></span>
                  <input type="text"  id="loginUserName" name="userName" class="form-control" placeholder="<spring:message code="login_8" />" aria-describedby="">
                </div>
                <div class="input-group input-group-lg">
                  <span class="input-group-addon" ><i class="passwordIcon"></i></span>
                  <input type="password" id="loginPassWord" name="password" class="form-control" placeholder="<spring:message code="login_9" />" aria-describedby="">
                </div>
                 <div id="checkDiv" style="display: none;" class="input-group input-group-lg">
                 <label style="color: red;text-align:center;width:100%;line-height: 80px;background: transparent;border: 2px solid #e7e7e2;border-left: 1;font-size: 20px;" id="checkId"></label>
                </div>               
                <button id ="loginId" type="submit" class="loginBtn"><spring:message code="login_0" /></button>
            </div>
            </form>
        </div>
        <div id="myDiv2" class="section1_right">
            <div class="content">
                <h3>Intelligent business</h3>
                <h3>information service specialist</h3>
                <p>Shopping flow analysis   Personnel traiectory</p>
                <p>Historical data statistics   User behavior sumnary</p>
            </div>
        </div>
    </div>
   <!--  <div class="section2">
        <img src="../image/home-7.png" alt="">
    </div>
    <div class="section3">
        <img src="../image/home_3.2.png" alt="">
    </div> --> 
    <script type="text/javascript" src="../plugins/jquery.min.js"></script>
	<script type="text/javascript">
		var home = '<spring:message code="home"/>';
		var passenger = '<spring:message code="passenger"/>';
        var login_error = '<spring:message code="login_5"/>';
        var login_username = '<spring:message code="login_6"/>';
        var login_password = '<spring:message code="login_7"/>';
		var objColors, colours;
	   var url = window.location.href;
	   var valuess = url.split("=");
	    var info;
	    var info1;
	   if(valuess.length>1){
		   info = valuess[1].split("&")[0];
		   info1 = valuess[2];
	   }
       if(info=="error")
       {
    	   $("#checkId").html(login_error);
    	   $("#checkDiv").show()
       }
       if(info1!="")
       {
           $("#loginUserName").val(info1);
       }
	    $(document).ready(function() {
	    	var clientHeight = document.documentElement.clientHeight;
	    	var clientWidth = document.documentElement.clientWidth;
	    	var o = document.getElementById('myDiv');
	    	var o1 = document.getElementById('myDiv1');
	    	var o2 = document.getElementById('myDiv2');
	    	o.style.width = clientWidth+'px';
	    	o.style.height = clientHeight+'px';
	    	o1.style.width = clientWidth*0.4+'px';
	    	o1.style.height = clientHeight+'px';
	    	o2.style.width = clientWidth*0.6+'px';
	    	o2.style.height = clientHeight+'px';
	    	$("#loginId").click(function(e){
	    		var userName = $("#loginUserName").val();
	    		var password = $("#loginPassWord").val();
	    		var check =true;
	    		if(userName=="")
	    		{
    	            $("#checkId").html(login_username);
    	            $("#checkDiv").show()	    			
	    			check = false;
	    			return false;
	    		}
	             if(password=="")
                 {
                     $("#checkId").html(login_password);
                     $("#checkDiv").show()
	                 check = false;
	                 return false;
                 }
	             if(check)
	            	{
	            	 $("#checkId").html("");
	            	 $("#checkDiv").hide()
	            	 return true;          	 
	            	}    
	    	});
	    }); 
	</script>
</body>
</html>