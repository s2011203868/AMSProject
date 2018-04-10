<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>档案管理系统登录</title>
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="static/login/tips/normalize.css" />
<link rel="stylesheet" type="text/css" href="static/login/tips/demo.css" />
<link rel="stylesheet" type="text/css" href="static/login/tips/ns-default.css" />
<link rel="stylesheet" type="text/css" href="static/login/tips/ns-style-bar.css" />

<link rel="stylesheet" href="static/login/bootstrap.min.css" />
<link rel="stylesheet" href="static/login/css/camera.css" />
<link rel="stylesheet" href="static/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="static/login/matrix-login.css" />
<link href="static/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="static/login/js/jquery-1.5.1.min.js"></script>
<script src="static/login/tips/modernizr.custom.js"></script>

<script type="text/javascript">
var COOKIE_NAME = 'sys_em_username';

function login(){
 $("#usernamecheck").html("");
	$("#passwordcheck").html("");
	var username = $("#j_username").val();
	var password = $("#j_password").val();
	if(username =='' || username ==null){
		var notification = new NotificationFx({
			message : '<p style="text-align:center;display:block;font-size:18px;color:Fuchsia">用户名不可为空</p>',
			layout : 'bar',
			effect : 'slidetop',
			type : 'warning', // notice, warning or error
		});

		notification.show();
		return
	}
	if(password =='' || password ==null){
		var notification = new NotificationFx({
			message : '<p style="text-align:center;display:block;font-size:18px;color:Fuchsia">密码不可为空</p>',
			layout : 'bar',
			effect : 'slidetop',
			type : 'warning', 
		});

		notification.show();
		return
	}
	$("#login_ok").attr("disabled", true);
	$.ajax({
         type: "POST",
         url: "login.action",
         dataType: "json",
         data: $('#login_form').serialize(),
         success: function (result) {
        	
        	 var json = eval(result);
        	 //alert(json.message)
        	 if(json.message == '用户名不存在' || json.message == '密码错误'){
        		$("#login_ok").attr("disabled", false);
        		var notification = new NotificationFx({
        			message : '<p style="text-align:center;display:block;font-size:18px;color:Fuchsia">用户名密码不正确或账号被禁用</p>',
        			layout : 'bar',
        			effect : 'slidetop',
        			type : 'warning', 
        		});

        		notification.show();
        	 }else if(json.message == '错误次数过多'){
        		 $("#login_ok").attr("disabled", false);
        		 var notification = new NotificationFx({
                     message : '<p style="text-align:center;display:block;font-size:18px;color:Fuchsia">错误次数过多，请5分钟后再试!</p>',
                     layout : 'bar',
                     effect : 'slidetop',
                     type : 'warning', 
                 });

                 notification.show();
        	 }else if(json.message == '账号被禁用'){
        		 $("#login_ok").attr("disabled", false);
        		 var notification = new NotificationFx({
                     message : '<p style="text-align:center;display:block;font-size:18px;color:Fuchsia">账号被禁用</p>',
                     layout : 'bar',
                     effect : 'slidetop',
                     type : 'warning', 
                 });

                 notification.show();
        	 }else if(json.message == 'gotofirst'){
        		 var notification = new NotificationFx({
        				message : '<p style="text-align:center;display:block;font-size:18px;color:MediumSpringGreen">登陆成功</p>',
        				layout : 'bar',
        				effect : 'slidetop',
        				type : 'notice', 
        			});

        		 notification.show();
        		 setTimeout(function(){
        			 window.location.href = "/AMSProject/firstpage.jsp";
        		 },300)
        	 }
         }
    });
};
</script>
</head>
<body ondragstart="return false">
<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
	<div id="loginbox">
		<form action="#" id="login_form">
			<div class="control-group normal_text">
				<h3>
					<img src="js/B-JUI.1.31/images/logo3.png" alt="Logo" />
					<input type="hidden"  name="macheinecode" id="macheinecode" value="${macheinecode }"/>
				</h3>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box" style="overflow:hidden;height:30px">
						<span class="add-on bg_lg">
						<i><img style=" height:14px"  src="static/login/user.png" /></i>
						</span><input type="text"  name="username" id="j_username" value="" placeholder="请输入用户名" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box" style="overflow:hidden;height:30px">
						<span class="add-on bg_ly">
						<i><img style=" height:14px"  src="static/login/suo.png" /></i>
						</span><input type="password" name="password" id="j_password" placeholder="请输入密码" value="" />
					</div>
				</div>
			</div>
			<div class="form-actions">
				<div style="width:86%;padding-left:8%;">
					<input type="button" style="outline:none;" id="login_ok" onclick="login()" class="btn btn-primary btn-lg" value="登  录">&nbsp;&nbsp;&nbsp;&nbsp;
	                <button type="reset" style="outline:none;" class="btn btn-default btn-lg">&nbsp;重&nbsp;置&nbsp;</button>
				</div>
			</div>

		</form>

		<div class="controls">
			<div class="main_input_box">
				<font color="white"><span id="nameerr">Copyright © 档案管理系统  2014-2018</span></font>
			</div>
		</div>
	</div>
</div>
<div id="templatemo_banner_slide" class="container_wapper">
	<div class="camera_wrap camera_emboss" id="camera_slide">
		<div data-src="static/login/images/banner_slide_01.jpg"></div>
		<div data-src="static/login/images/banner_slide_02.jpg"></div>
		<div data-src="static/login/images/banner_slide_03.jpg"></div>
	</div>
	<!-- #camera_wrap_3 -->
</div>
<script src="static/login/bootstrap.min.css"></script>
<script src="static/login/tips/classie.js"></script>
<script src="static/login/tips/notificationFx.js"></script>
<script src="static/login/jquery-1.7.2.js"></script>
<script src="static/login/js/jquery.easing.1.3.js"></script>
<script src="static/login/js/jquery.mobile.customized.min.js"></script>
<script src="static/login/js/camera.min.js"></script>
<script src="static/login/js/templatemo_script.js"></script>
<script type="text/javascript" src="static/login/js/jquery.cookie.js"></script>
</body>
</html>