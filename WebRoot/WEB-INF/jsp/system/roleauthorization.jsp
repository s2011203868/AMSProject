<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色授权页</title>
<script type="text/javascript">

$('#roleListTree').datagrid({
	tableWidth: '97%',
    height: '100%',
    local: 'local',
    dataUrl: 'findroleList.action',
    inlineEditMult: false,
    isTree: 'rolename',
    addLocation: 'last',
    showToolbar:true,
	showCheckboxcol:true,
	toolbarCustom:'<button type="button" class="btn-green" id="roleListbutton" onclick="findPermissionList()" data-icon="plus-circle">查询权限</button>',
    fieldSortable: false,
    filterThead:false,
    columnMenu: false,
    hScrollbar:false,
    columnResize:true,
    paging: false,
    treeOptions: {
        expandAll: false,
        simpleData: true,
        keys: {
        	key:'roleid',
            parentKey: 'parentid',
        }
    }
})

function findPermissionList(){
	if($("#roleListTree").data('selectedDatas') == null){
		 BJUI.alertmsg('info', '未选择一项！');
		 return
	}
	if($("#roleListTree").data('selectedDatas')[0].status == '2'){
		BJUI.alertmsg('info', '该角色已被停用！');
		 return
	}
	var target = $("#roleListTree").data('selectedDatas')[0].target;
	var url = $("#roleListTree").data('selectedDatas')[0].uri;
	var roleid = $("#roleListTree").data('selectedDatas')[0].roleid;
	 $(this).bjuiajax('doLoad', {
	   type:'post',
	   url:'permissionlisttree.action',
	   data:{'roleid':roleid},
	   target:target,
	});
}

function role_tree_status(value,data) {
	if(value == 1){
        return '<span style="color:green">正常</span>'
    }
    if(value == 2){
        return '<span style="color:red">停用</span>'
    }
    return 
    //return [{'1':'正常'},{'2':'停用'}]
}

function role_tree_level() {
    return [{'1':'子级一层'},{'2':'子级二层'},{'3':'子级三层'},{'4':'子级四层'},{'5':'子级五层'}]
}

</script>
</head>
<body style="padding:0;margin:0">
<div style="height:100%;width:100%;">
	<div style="height:100%;width:54%;float:left">
		<div>
		   <table id="roleListTree" style="table-layout: fixed;" data-toggle="datagrid" >
		        <thead>
		            <tr>
		                <th data-options="{name:'rolename', align:'center', width:120, rule:'required'}">名称</th>
		                <th data-options="{name:'level', align:'center', width:90,items:role_tree_level}">层级</th>
		                <th data-options="{name:'order', align:'center', width:30}">排序</th>
		                <th data-options="{name:'status', align:'center', width:70, render:role_tree_status}">状态</th>
		            </tr>
		        </thead>
		    </table>
		</div>
	</div>
	<p style="height:100%;width:1%;float:left"></p>
	<div id="layout01" style="height:100%;width:45%;float:left">
	</div>
</div>
</body>
</html>