<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理页</title>
<script type="text/javascript">


$('#deptListTree').datagrid({
	tableWidth: '80%',
    height: '100%',
    local: 'local',
    dataUrl: 'finddeptList.action',
    editUrl:'editdept.action',
    inlineEditMult: false,
    isTree: 'name',
    addLocation: 'last',
    showCheckboxcol:true,
    showToolbar:true,
    toolbarItem:'refresh,cancel,|,save',
    toolbarCustom:'<button type="button" onclick="setingdeptpermission()" class="btn-red" data-icon="cog">功能权限</button>',
    fieldSortable: false,
    filterThead:false,
    columnMenu: false,
    hScrollbar:false,
    columnResize:true,
    paging: false,
    treeOptions: {
        expandAll: false,
        simpleData: true,
        add:true,
        keys: {
        	key:'id',
            parentKey: 'parentid',
        }
    }
});

function dept_tree_operation() {
    var html = '<button type="button" class="btn-blue" data-toggle="edit.datagrid.tr">修改</button>'
    return html
}

function dept_tree_status() {
    return [{'1':'<span style="color:green">启用</span>'},{'2':'<span style="color:red">停用</span>'}]
}


function setingdeptpermission(){
    if($("#deptListTree").data('selectedDatas') == null){
        BJUI.alertmsg('info', '未选择一项！');
        return
    }
    if($("#deptListTree").data('selectedDatas')[0].status == '2'){
        BJUI.alertmsg('info', '该部门已停用！');
        return
    }
    var deptid = $("#deptListTree").data('selectedDatas')[0].id;
    BJUI.dialog({
        id:'setingdeptpermissionid',
        width:'700',
        height:'500',
        mask:true,
        url:'setingdeptpermission.action',
        data:{'deptid':deptid},
        title:'功能权限设置',
        onClose:function(){
            $('#deptListTree').datagrid('refresh',true);
        }
    });
    
}

</script>
</head>
<body style="padding:0;margin:0">
<div>
   <table id="deptListTree" style="table-layout: fixed;" data-toggle="datagrid" >
        <thead>
            <tr>
                <th data-options="{name:'name', align:'center', width:140, rule:'required'}">名称</th>
                <th data-options="{name:'status', align:'center',type:'select', width:100, items:dept_tree_status}">状态</th>
                <th data-options="{width:20, align:'center',render:dept_tree_operation}">操作</th>
            </tr>
        </thead>
    </table>
</div>
</body>
</html>