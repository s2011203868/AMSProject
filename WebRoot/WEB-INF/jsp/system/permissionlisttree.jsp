<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位授权状态页</title>
<script type="text/javascript">
var roleid;
$(function(){
	roleid = $("#roleidinput").val();
})
$('#permissionListTree').datagrid({
	toolbarCustom:'<button type="button" class="btn-green" onclick="pluspermission()" data-icon="plus-circle">赋予授权</button> <button type="button" onclick="minuspermission()" class="btn-blue" data-icon="minus-circle">取消授权</button>',
	tableWidth:'100%',
    height: '100%',
    local: 'local',
    dataUrl: 'findIsPermissionAuthorization.action?roleid='+roleid,
    inlineEditMult: false,
    showToolbar:true,
    showCheckboxcol:true,
   // editUrl: 'goPermissionEditPage.action',
    isTree: 'name',
   // editMode: {dialog:{width:'600',height:'350',title:'资源修改',mask:true}},
    addLocation: 'last',
    fieldSortable: false,
    filterThead:false,
    columnMenu: false,
    paging:{pageSize:100,selectPageSize:'120,150', showPagenum:1},
    afterSave:function(){
		$('#permissionListTree').datagrid('refresh',true);
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

function pluspermission(){
	if($("#permissionListTree").data('selectedDatas') == null){
		 BJUI.alertmsg('info', '未选择一项！');
		 return
	}
	if($("#permissionListTree").data('selectedDatas')[0].isAuthorization == '1'){
		BJUI.alertmsg('info', '已经授权！');
		return
	}
	if($("#permissionListTree").data('selectedDatas')[0].status == '2'){
		BJUI.alertmsg('info', '该资源已停用！');
		return
	}
	console.log($("#permissionListTree").data('selectedDatas'));
	var array = $("#permissionListTree").data('selectedDatas');
	var l = array.length;
	if(l == 1){
		var id = $("#permissionListTree").data('selectedDatas')[0].id;
	    BJUI.ajax('doajax', {
	        url: 'addPermissionForRole.action',
	        loadingmask: true,
	        confirmMsg:'你确定要授权【'+$("#permissionListTree").data('selectedDatas')[0].name+'】一项权限吗?',
	        data:{'roleid':roleid,'permissionid':id},
	        okCallback: function(json, options) {
	            $('#permissionListTree').datagrid('refresh',true);
	        },
	        errCallback:function(json,options){
	           $('#permissionListTree').datagrid('refresh',true);
	        }
	    })
	}else{
		var id = "";
		for(var i= 0;i<array.length;i++){  
            id += array[i].id +',';  
        } 
		id = id.substring(0,id.length-1);
		BJUI.ajax('doajax', {
            url: 'addPermissionForRole.action',
            loadingmask: true,
            confirmMsg:'你确定要授权【'+$("#permissionListTree").data('selectedDatas')[0].name+'】等多项权限吗?',
            data:{'roleid':roleid,'permissionid':id},
            okCallback: function(json, options) {
                $('#permissionListTree').datagrid('refresh',true);
            },
            errCallback:function(json,options){
               $('#permissionListTree').datagrid('refresh',true);
            }
        })
	}
}

function minuspermission(){
	if($("#permissionListTree").data('selectedDatas') == null){
		 BJUI.alertmsg('info', '未选择一项！');
		 return
	}
	if($("#permissionListTree").data('selectedDatas')[0].status == '2'){
		BJUI.alertmsg('info', '该资源已停用！');
		return
	}
	if($("#permissionListTree").data('selectedDatas')[0].isAuthorization == '2'){
		BJUI.alertmsg('info', '请先为资源授权！');
		return
	}
	console.log($("#permissionListTree").data('selectedDatas')[0]);
	console.log($("#permissionListTree").data('selectedDatas'));
    var array = $("#permissionListTree").data('selectedDatas');
    var l = array.length;
	if(l == 1){
		var id = $("#permissionListTree").data('selectedDatas')[0].id;
	    BJUI.ajax('doajax', {
	        url: 'removePermissionForRole.action',
	        loadingmask: true,
	        confirmMsg:'你确定要取消授权【'+$("#permissionListTree").data('selectedDatas')[0].name+'】一项权限吗?',
	        data:{'roleid':roleid,'permissionid':id},
	        okCallback: function(json, options) {
	            $('#permissionListTree').datagrid('refresh',true);
	        },
	        errCallback:function(json,options){
	           $('#permissionListTree').datagrid('refresh',true);
	        }
	    })
	}else{
		var id = "";
        for(var i= 0;i<array.length;i++){  
            id += array[i].id +',';  
        } 
        id = id.substring(0,id.length-1);
        BJUI.ajax('doajax', {
            url: 'removePermissionForRole.action',
            loadingmask: true,
            confirmMsg:'你确定要取消授权【'+$("#permissionListTree").data('selectedDatas')[0].name+'】等多项权限吗?',
            data:{'roleid':roleid,'permissionid':id},
            okCallback: function(json, options) {
                $('#permissionListTree').datagrid('refresh',true);
            },
            errCallback:function(json,options){
               $('#permissionListTree').datagrid('refresh',true);
            }
        })
	}
}
</script>
</head>
<body>
<div class="bjui-pageContent">
   <table id="permissionListTree" style="table-layout: fixed;"><!--  data-toggle="datagrid" data-options="{}"-->
       <thead>
           <tr>
               <th data-options="{name:'id', align:'center', width:80}">编号</th>
               <th data-options="{name:'name', align:'center', width:200, rule:'required'}">资源名称</th>
               <th data-options="{name:'isAuthorization', align:'center', width:70,render:formatauthorizationstatus, rule:'required'}">授权状态</th>
           </tr>
       </thead>
  </table>
</div>
<input type="hidden" id="roleidinput"  name="roleid" value="${roleid }">
</body>
</html>