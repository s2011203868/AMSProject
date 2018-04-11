<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位角色添加页</title>

<script type="text/javascript">

var setting = {
        check: {
            enable: true,
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onCheck: onCheck
        }
    };


function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("permissionzTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
	
    var zTree = $.fn.zTree.getZTreeObj("permissionzTree"),
    
    nodes = zTree.getCheckedNodes(true),
  
    v= "";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].id + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    var Obj = $("#permissionManage");
    
    Obj.attr("value", v);
}

function showMenu() {
    $('#menuContent').css('display','block'); 
    $("body").bind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	console.log(event.target.id)
    if (!(event.target.id == "permissionManage" || event.target.id == "menuContent" || event.target.id == "permissionzTree" ||$(event.target).parents("#menuContent").length>0)) {
        hideMenu();
    }
}

function hideMenu() {
	$('#menuContent').css('display','none'); 
    $("body").unbind("mousedown", onBodyDown);
}


function inItSelect(obj,isdept){
	for(var i = 0 ; i<obj.options.length;i++){
        if(isdept === obj.options[i].value){
            obj.options[i].selected=true;
        }
    }
}

$(document).ready(function(){
	
	var isdept = $('#isdept').val();
    var obj = document.getElementById("deptsid");
    inItSelect(obj,isdept);
    
	var zNodes = [];
	$.ajax({
		url:'getPermissionTree.action',
		type:'post',
		dataType:'json',
		success:function(result){
			zNodes = result;
			console.log(zNodes);
		}
	});
	
	setTimeout(function(){
		console.log(zNodes)
		$.fn.zTree.init($("#permissionzTree"), setting, zNodes);
	},500)
    
});
</script>
</head>
<body>
<div class="bjui-pageContent">
   <div  class="bs-example">
        <form action="rolelistAddSave.action" class="datagrid-edit-form" data-toggle="validate" data-data-type="jsonp">
            <div class="bjui-row col-2">
                <label class="row-label">岗位名称</label>
                <div class="row-input required">
                    <input type="text" name="rolename"  data-rule="required">
                    <input type="hidden" id ="isdept" value="${deptid }" >
                </div>
                <label class="row-label">所属部门</label>
                <div class="row-input required">
                    <select name="duty" id="deptsid" data-rule="required" data-toggle="selectpicker">
                        <option value="">--选择部门--</option>
                        <c:forEach var="dept" items="${deptList }" varStatus="status">
                             <option value="${dept.id }">${ dept.name }</option>
                        </c:forEach>
                    </select>
                </div>
                <label class="row-label">岗位状态</label>
                <div class="row-input required">
                    <select name="status" data-toggle="selectpicker"  data-rule="required" data-width="100%">
                      <option value="1" selected="selected">启用</option>
                      <option value="2" >停用</option>
                    </select>
                </div>
                <label class="row-label">权限设置</label>
                <div class="row-input required" style="position:relative;">
	                <input id="permissionManage" readonly="" name="permissions" type="text" value="" style="width:120px;" onclick="showMenu();" />
	                <div id="menuContent" class="menuContent" style=" background:#F0F6E4; overflow-y:scroll; overflow-x:hidden;width:230px;height:280px; display:none; position: absolute;top:28;left:0">
	                   <ul id="permissionzTree" class="ztree"  style="margin-top:0; width:180px; height: 300px;"></ul>
	                </div>
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