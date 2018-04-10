<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色修改页</title>
</head>
<body>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form action="rolelisteditSave.action" class="datagrid-edit-form" data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
                <label class="row-label">岗位名称</label>
                <div class="row-input required">
                	<input type="hidden" name="roleid" value="${roleid }"></input>
                    <input type="text" name="rolename" value="${rolename }"  data-rule="required">
                </div>
                <label class="row-label">所属部门</label>
                <div class="row-input required">
                    <select name="duty" data-rule="required" data-toggle="selectpicker">
                        <c:forEach var="dept" items="${deptList }" varStatus="status">
                             <c:if test="${deptid eq dept.id }">
                                <option value="${dept.id }" selected="">${ dept.name }</option>
                             </c:if>
                             <option value="${dept.id }">${ dept.name }</option>
                        </c:forEach>
                    </select>
                </div>
                <label class="row-label">岗位状态</label>
                <div class="row-input required">
                    <select name="status" data-toggle="selectpicker" value="${status }" data-rule="required" data-width="100%">
                        <c:if test="${status == '1' }">
                        	<option value="1" selected="selected">启用</option>
                        	<option value="2" >停用</option>
                        </c:if>
                        <c:if test="${status == '2' }">
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