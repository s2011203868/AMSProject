<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户修改页</title>
<script type="text/javascript">
$(function(){
	var deptDutys = $('#deptDutys').val();
	var ids=new Array();
	var obj = document.getElementById("deptdutyid");
	if(deptDutys.indexOf(",") > 0){
		ids = deptDutys.split(",");
		
		for(var i = 0 ; i<obj.options.length;i++){
	        for(var j = 0 ; j < ids.length ; j++){
	            if(ids[j] == obj.options[i].value){
	            	obj.options[i].selected=true;
	            }
	        }
	    }
	}else{
		for(var i = 0 ; i<obj.options.length;i++){
			if(deptDutys == obj.options[i].value){
                obj.options[i].selected=true;
            }
        }
	}
	
	
});	

function closeuserlistEditDialog(){
	BJUI.dialog('closeCurrent');
}
</script>
</head>
<body>
<div class="bjui-pageContent">
    <div class="bs-example">
        <form action="userlistEditSave.action" id="userlisteditform" class="datagrid-edit-form"   data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
                <label class="row-label">用户名:</label>
                <div class="row-input required">
                    <input type="text"  disabled="" value="${account }" data-rule="required;account" >
                	<input type="hidden" name="userid" value="${userid }" >
                	<input type="hidden" name="status" value="${status }" >
                	<input type="hidden" name="account" value="${account }" >
                	<input type="hidden" id ="deptDutys" value="${deptDutys }" >
                </div>
                <label class="row-label">姓名:</label>
                <div class="row-input required">
                    <input type="text" name="username" disabled="" value="${username }" data-rule="required">
                </div>
                <label class="row-label">部门岗位</label>
                <div class="row-input required">
                    <select name="deptduty" id="deptdutyid" data-toggle="selectpicker" multiple="" data-rule="required" data-width="100%">
                        <c:forEach var="map" items="${deptdutymap }">
                           <option value="${map.key }">${map.value }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" onclick="closeuserlistEditDialog();" id="userlisteditsubmit" class="btn-default"  data-icon="save">保存</button></li>
    </ul>
</div>
</body>
</html>