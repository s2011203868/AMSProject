<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择角色页</title>
</head>
<body>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form action="selectRoleSave.action" class="datagrid-edit-form" data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
	            <c:forEach var="role" items="${AllRole }" varStatus = "status">
	            	<label class="row-label">角色名:</label>
	                <div class="row-input">
	                	
	                	<c:if test="${role.hasRole == '1' }">
	                		<input name="role" id="${role.roleid }" value="${role.roleid }" data-toggle="icheck" data-label="${role.rolename }" checked="" type="checkbox">
	                	</c:if>
	                	<c:if test="${role.hasRole == '' || role.hasRole == null && role.status ==1 }">
	                    	<input name="role" id="${role.roleid }" value="${role.roleid }" data-toggle="icheck" data-label="${role.rolename }"  type="checkbox">
	                	</c:if>
	                	<c:if test="${role.hasRole == '' || role.hasRole == null && role.status ==2 }">
	                    	<input name="role" id="${role.roleid }" value="${role.roleid }" data-toggle="icheck" data-label="${role.rolename }(停用)" disabled="" type="checkbox">
	                	</c:if>
	                	<c:if test="${role.status ==3 }">
	                    	<input name="role" id="${role.roleid }" value="${role.roleid }" data-toggle="icheck" data-label="${role.rolename }" disabled="" type="checkbox">
	                	</c:if>
	                </div>
	            </c:forEach>
	            <input type="hidden" name="status" value="${status }"></input>
	            <input type="hidden" name="account" value="${account }"></input>
	            <input type="hidden" name="userid" value="${userid }"></input>
            </div>
        </form>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-default" data-icon="save">保存</button></li>
    </ul>
</div>
</body>
</html>