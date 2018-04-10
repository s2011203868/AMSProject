<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门授权状态页</title>
<script type="text/javascript">
var deptid;
$(function(){
	deptid = $("#deptidinput").val();
})
$('#deptPermissionTree').datagrid({
	toolbarCustom:'<button type="button" class="btn-green" onclick="plusdeptpermission()" data-icon="plus-circle">赋予授权</button> <button type="button" onclick="minusdeptpermission()" class="btn-blue" data-icon="minus-circle">取消授权</button>',
	tableWidth:'100%',
    height: '100%',
    local: 'local',
    dataUrl: 'findDeptIsPermissionAuthorization.action?deptid='+deptid,
    inlineEditMult: false,
    showToolbar:true,
    showCheckboxcol:true,
    isTree: 'name',
    addLocation: 'last',
    fieldSortable: false,
    filterThead:false,
    columnMenu: false,
    paging:{pageSize:100,selectPageSize:'120,150', showPagenum:1},
    afterSave:function(){
		$('#deptPermissionTree').datagrid('refresh',true);
    },
    treeOptions: {
        expandAll: false,
        simpleData: true,
        keys: {
            parentKey: 'parentid',
        }
    }
})

function formatauthorizationstatus(value,data){
	if(value == 1){
		return '<span style="color:green">已授权</span>'
	}
	if(value == 2){
		return '<span style="color:red">未授权</span>'
	}
	 return 
}

function plusdeptpermission(){
	if($("#deptPermissionTree").data('selectedDatas') == null){
		 BJUI.alertmsg('info', '未选择一项！');
		 return
	}
	if($("#deptPermissionTree").data('selectedDatas')[0].isAuthorization == '1'){
		BJUI.alertmsg('info', '已经授权！');
		return
	}
	if($("#deptPermissionTree").data('selectedDatas')[0].status == '2'){
		BJUI.alertmsg('info', '该资源已停用！');
		return
	}
	console.log($("#deptPermissionTree").data('selectedDatas'));
	var array = $("#deptPermissionTree").data('selectedDatas');
	var l = array.length;
	if(l == 1){
		var id = $("#deptPermissionTree").data('selectedDatas')[0].id;
	    BJUI.ajax('doajax', {
	        url: 'addPermissionForDept.action',
	        loadingmask: true,
	        confirmMsg:'你确定要授权【'+$("#deptPermissionTree").data('selectedDatas')[0].name+'】一项权限吗?',
	        data:{'deptid':deptid,'permissionid':id},
	        okCallback: function(json, options) {
	            $('#deptPermissionTree').datagrid('refresh',true);
	        },
	        errCallback:function(json,options){
	           $('#deptPermissionTree').datagrid('refresh',true);
	        }
	    })
	}else{
		var id = "";
		for(var i= 0;i<array.length;i++){  
            id += array[i].id +',';  
        } 
		id = id.substring(0,id.length-1);
		BJUI.ajax('doajax', {
            url: 'addPermissionForDept.action',
            loadingmask: true,
            confirmMsg:'你确定要授权【'+$("#deptPermissionTree").data('selectedDatas')[0].name+'】等多项权限吗?',
            data:{'deptid':deptid,'permissionid':id},
            okCallback: function(json, options) {
                $('#deptPermissionTree').datagrid('refresh',true);
            },
            errCallback:function(json,options){
               $('#deptPermissionTree').datagrid('refresh',true);
            }
        })
	}
}

function minusdeptpermission(){
	if($("#deptPermissionTree").data('selectedDatas') == null){
		 BJUI.alertmsg('info', '未选择一项！');
		 return
	}
	if($("#deptPermissionTree").data('selectedDatas')[0].status == '2'){
		BJUI.alertmsg('info', '该资源已停用！');
		return
	}
	if($("#deptPermissionTree").data('selectedDatas')[0].isAuthorization == '2'){
		BJUI.alertmsg('info', '请先为资源授权！');
		return
	}
	console.log($("#deptPermissionTree").data('selectedDatas')[0]);
	console.log($("#deptPermissionTree").data('selectedDatas'));
    var array = $("#deptPermissionTree").data('selectedDatas');
    var l = array.length;
	if(l == 1){
		var id = $("#deptPermissionTree").data('selectedDatas')[0].id;
	    BJUI.ajax('doajax', {
	        url: 'removePermissionForDept.action',
	        loadingmask: true,
	        confirmMsg:'你确定要取消授权【'+$("#deptPermissionTree").data('selectedDatas')[0].name+'】一项权限吗?',
	        data:{'deptid':deptid,'permissionid':id},
	        okCallback: function(json, options) {
	            $('#deptPermissionTree').datagrid('refresh',true);
	        },
	        errCallback:function(json,options){
	           $('#deptPermissionTree').datagrid('refresh',true);
	        }
	    })
	}else{
		var id = "";
        for(var i= 0;i<array.length;i++){  
            id += array[i].id +',';  
        } 
        id = id.substring(0,id.length-1);
        BJUI.ajax('doajax', {
            url: 'removePermissionForDept.action',
            loadingmask: true,
            confirmMsg:'你确定要取消授权【'+$("#deptPermissionTree").data('selectedDatas')[0].name+'】等多项权限吗?',
            data:{'deptid':deptid,'permissionid':id},
            okCallback: function(json, options) {
                $('#deptPermissionTree').datagrid('refresh',true);
            },
            errCallback:function(json,options){
               $('#deptPermissionTree').datagrid('refresh',true);
            }
        })
	}
}
</script>
</head>
<body>
<div class="bjui-pageContent">
   <table id="deptPermissionTree" style="table-layout: fixed;"><!--  data-toggle="datagrid" data-options="{}"-->
       <thead>
           <tr>
               <th data-options="{name:'id', align:'center', width:80}">编号</th>
               <th data-options="{name:'name', align:'center', width:200, rule:'required'}">资源名称</th>
               <th data-options="{name:'isAuthorization', align:'center', width:70,render:formatauthorizationstatus, rule:'required'}">授权状态</th>
           </tr>
       </thead>
  </table>
</div>
<input type="hidden" id="deptidinput"  name="edptid" value="${deptid }">
</body>
</html>