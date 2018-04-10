<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>岗位角色列表页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">

$('#rolelisttable').datagrid({
    columns: [
        {
            name: 'roleid',
            label: '岗位编号',
            align: 'center',
            width: 70,
            hide:true
            
        },
        {
           name: 'rolename',
           label: '岗位名称',
           align: 'center',
           width: 50 
        },
        {
            name: 'deptid',
            label: '部门id',
            align: 'center',
            width: 50 ,
            hide:true
         },
        {
            name: 'deptname',
            label: '所属部门',
            align: 'center',
            width: 50 
         }
        ,
        {
           name: 'status',
           label: '岗位状态',
           align: 'center',
           width: 40,
           render: function(value) {
        	   if(value == 1){
        	        return '<span style="color:green">启用</span>'
        	    }
        	    if(value == 2){
        	        return '<span style="color:red">停用</span>'
        	    }
        	    return 
           }
        }
    ],
    height: '100%',
    showToolbar: true,
    columnLock:false,
    dataUrl: 'findDutyList.action',
    fieldSortable:false,
    filterThead:false,
    paging:{pageSize:50,selectPageSize:'70,100', showPagenum:5},
    linenumberAll: true,
    inlineEditMult :false,
    tableWidth:'100%',
    contextMenuB:true,
    contextMenuB:false,
    showCheckboxcol:true,
    toolbarItem:'refresh',
    toolbarCustom:'<button type="button" class="btn-orange" onclick="addNewDuty()" data-icon="plus-circle">新增岗位</button> <button type="button" onclick="editDuty()" class="btn-blue" data-icon="edit">编辑岗位</button> <button type="button" onclick="setingpermission()" class="btn-red" data-icon="cog">功能权限</button>',
    afterSave:function(){
    	$('#roledistributelisttable').datagrid('refresh',true);
    	$('#rolelisttable').datagrid('refresh',true);
    	$('#roleListTree').datagrid('refresh',true);
    }
});

function addNewDuty(){
    BJUI.dialog({
        id:'addNewDutyid',
        width:'700',
        height:'500',
        mask:true,
        url:'addNewDuty.action',
        title:'新增岗位',
        onClose:function(){
        	$('#rolelisttable').datagrid('refresh',true);
        }
    });
}

function editDuty(){
	
	if($("#rolelisttable").data('selectedDatas') == null){
        BJUI.alertmsg('info', '未选择一项！');
        return
    }
	var roleid = $("#rolelisttable").data('selectedDatas')[0].roleid;
	var deptid = $("#rolelisttable").data('selectedDatas')[0].deptid;
	var rolename = $("#rolelisttable").data('selectedDatas')[0].rolename;
	var status = $("#rolelisttable").data('selectedDatas')[0].status;
	BJUI.dialog({
        id:'editDutyid',
        width:'700',
        height:'500',
        mask:true,
        url:'editDuty.action',
        data:{'roleid':roleid,'deptid':deptid,'rolename':rolename,'status':status},
        title:'编辑岗位',
        onClose:function(){
            $('#rolelisttable').datagrid('refresh',true);
        }
    });
}

function setingpermission(){
	if($("#rolelisttable").data('selectedDatas') == null){
        BJUI.alertmsg('info', '未选择一项！');
        return
    }
	if($("#rolelisttable").data('selectedDatas')[0].status == 2){
        BJUI.alertmsg('info', '该岗位已停用！');
        return
    }
	var roleid = $("#rolelisttable").data('selectedDatas')[0].roleid;
	BJUI.dialog({
        id:'setingpermissionid',
        width:'700',
        height:'500',
        mask:true,
        url:'permissionlisttree.action',
        data:{'roleid':roleid},
        title:'功能权限设置',
        onClose:function(){
            $('#rolelisttable').datagrid('refresh',true);
        }
    });
	
}
</script>
  </head>
  <body>
     <div class="bjui-pageContent">
    	<table id="rolelisttable" class="table table-bordered">
	    </table>
	  </div>
  </body>
</html>
