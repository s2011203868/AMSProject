<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源修改页面</title>
</head>
<body>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form action="permissioneditsave.action" class="datagrid-edit-form" data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
                <label class="row-label">资源名称</label>
                <div class="row-input required">
                	<input type="hidden" name="id" value="${SysPermission.id }"></input>
                    <input type="text" name="name" readonly="" value="${SysPermission.name }" >
                </div>
                <label class="row-label">资源类别</label>
                <div class="row-input required">
                    <input type="text" name="pertype" readonly="" value="${SysPermission.pertype }">
                </div>
                <label class="row-label">资源地址</label>
                <div class="row-input required">
                    <input type="text" name="url" readonly="" value="${SysPermission.url }" >
                </div>
                <label class="row-label">层级</label>
                <div class="row-input required">
                    <input type="text" name="level" readonly="" value="${SysPermission.level }" >
                </div>
                <label class="row-label">状态</label>
                <div class="row-input required">
                    <select name="status" data-toggle="selectpicker" value="${SysPermission.status }" data-rule="required" data-width="100%">
                        <c:if test="${SysPermission.status == '1' }">
                        	<option value="1" selected="selected">启用</option>
                        	<option value="2" >停用</option>
                        </c:if>
                        <c:if test="${SysPermission.status == '2' }">
                        	<option value="1" >启用</option>
                        	<option value="2" selected="selected">停用</option>
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
        <li><button type="submit" class="btn-default" data-icon="save">保存</button></li>
    </ul>
</div>
</body>
</html>