<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加页</title>
<script type="text/javascript">
	function checkAccount(){
		var account = $('#userlistaddformaccount').val();
		$.ajax({
			type:'post',
			url:'checkAccount.action',
			data:{'account':account},
			dataType:'json',
			success:function(result){
				var json = eval(result);
				var statusCode = json.statusCode;
				var message = json.message;
				if(statusCode == '200'){
					$("#userlistaddsubmit").attr("disabled", false);
				}
				if(statusCode == '300'){
					$("#userlistaddsubmit").attr("disabled", true);
					BJUI.alertmsg('warn', message , {
				    	displayPosition:'middlecenter',
				    });
				    
				}
			}
		});
	}
	
</script>
</head>
<body>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form action="userlistAddSave.action" id="userlistaddform" class="datagrid-edit-form"   data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
                <label class="row-label">用户名:</label>
                <div class="row-input required">
                    <input type="text" id="userlistaddformaccount" name="account" onblur="checkAccount()"  data-rule="required;account" data-rule-account="[/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,16}$/,'5-16位字母数字组合']">
                </div>
                <label class="row-label">姓名:</label>
                <div class="row-input required">
                    <input type="text" name="username"  data-rule="required">
                </div>
				<label class="row-label">密码:</label>
				<div class="row-input required">
					<input type="password" id="j_userinfo_changepass_newpass" name="password" value="111111" data-rule="新密码:required;length(6~)">
				</div>
				<label class="row-label">确认密码:</label>
				<div class="row-input required">
					<input type="password" id="j_userinfo_changepass_confirmpass" name="" value="111111" data-rule="required;match(#j_userinfo_changepass_newpass)">
				</div>
				<label class="row-label">部门岗位</label>
			    <div class="row-input required">
			        <select name="deptduty" data-toggle="selectpicker" multiple="" data-rule="required" data-width="100%">
			            <c:forEach var="map" items="${deptdutymap }">
			               <option value="${map.key }">${map.value }</option>
			            </c:forEach>
			        </select>
			    </div>
                <label class="row-label">状态:</label>
                <div class="row-input required">
                    <select name="status" data-toggle="selectpicker" value="${status }" data-rule="required" data-width="100%">
                        <c:if test="${status == '1' }">
                        	<option value="1" selected="selected">启用</option>
                        	<option value="2" >注销</option>
                        </c:if>
                        <c:if test="${status == '2' }">
                        	<option value="1" >启用</option>
                        	<option value="2" selected="selected">注销</option>
                        </c:if>
                    </select>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" id="userlistaddsubmit" class="btn-default"  data-icon="save">保存</button></li>
    </ul>
</div>
</body>
</html>