<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统资源管理页</title>
<script type="text/javascript">
// department
function datagrid_tree_type() {
    return [{'topmenu':'TopMenu'},{'leftmenu':'LeftMenu'},{'button':'Button'}]
}

function datagrid_tree_status(value,data) {
	if(value == 1){
        return '<span style="color:green">正常</span>'
    }
    if(value == 2){
        return '<span style="color:red">停用</span>'
    }
    return 
   //return [{'1':'正常'},{'2':'停用'}]
}

function datagrid_tree_level() {
    return [{'1':'子级一层'},{'2':'子级二层'},{'3':'子级三层'},{'4':'子级四层'},{'5':'子级五层'}]
}

function tree_operation() {
    var html = '<button type="button" class="btn-orange" data-toggle="edit.datagrid.tr">编辑资源</button>'
    return html
}

$('#permissionManagerListTree').datagrid({
	height: '100%',
    dataUrl: 'findPermissionList.action',
    inlineEditMult: false,
    editUrl: 'goPermissionEditPage.action',
    isTree: 'name',
    editMode: {dialog:{width:'600',height:'350',title:'资源修改',mask:true}},
    addLocation: 'last',
    fieldSortable: false,
    filterThead:false,
    //columnMenu: false,
    paging:{pageSize:100,selectPageSize:'120,150', showPagenum:1},
    afterSave:function(){
		$('#permissionManagerListTree').datagrid('refresh',true);
		$('#permissionListTree').datagrid('refresh',true);
    },
    treeOptions: {
        
        simpleData: true,
        keys: {
            parentKey: 'parentid',
        }
    },
    columns:[
    	{
            name: 'name',
            label: '资源名称',
            align:'center', 
            width:200, 
            rule:'required'
         },
         {
             name: 'pertype',
             label: '资源类别',
             align:'center', 
             width:80,align:'center',
             type:'select', 
             items:datagrid_tree_type
          },
          {
              name: 'url',
              label: '资源地址',
              align:'center', 
              width:150,
              hide:true
          },
          {
             name: 'pertype',
             label: '体系代码',
             align:'center',
             width:90,
             hide:true
          },
          {
             name: 'id',
             label: '编号',
             align:'center',
             width:90,
             hide:true
          },
          {
              name: 'parentid',
              label: '父级编号',
              align:'center',
              width:90,
              hide:true
         },
         {
             name: 'level',
             label: '层级',
             align:'center',
             width:100,
             items:datagrid_tree_level
        },
        {
            name: 'order',
            label: '排序',
            align:'center', 
            width:50
         },
         {
           name: 'perCode',
           label: '权限代码',
           align:'center', 
           width:120,
           hide:true
         },
         {
             name: 'status',
             label: '状态',
             align:'center', 
             width:60, 
             render:datagrid_tree_status
         },
         {
             label: '操作',
             render:tree_operation, 
             align:'center',
             width:60
         },
    ]
})

</script>
</head>
<body>
<div class="bjui-pageContent">
    <table id="permissionManagerListTree" style="table-layout: fixed;" >
        <!-- <thead>
            <tr>
                <th data-options="{name:'name', align:'center', width:200, rule:'required'}">资源名称</th>
                <th data-options="{name:'pertype',width:80,align:'center', type:'select', items:datagrid_tree_type}">资源类别</th>
                <th data-options="{name:'url', align:'center', width:150,hide:true}">资源地址</th>
                <th data-options="{name:'pertype', align:'center', width:90,hide:true}">体系代码</th>
                <th data-options="{name:'id', align:'center', width:90,hide:true}">编号</th>
                <th data-options="{name:'parentid', align:'center', width:90,hide:true}">父级编号</th>
                <th data-options="{name:'level', align:'center', width:100,items:datagrid_tree_level}">层级</th>
                <th data-options="{name:'order', align:'center', width:50}">排序</th>
                <th data-options="{name:'perCode', align:'center', width:120,hide:true}">权限代码</th>
                <th data-options="{name:'status', align:'center', width:60, render:datagrid_tree_status}">状态</th>
            	<th data-options="{render:tree_operation, align:'center',width:60}">操作列</th>
            </tr>
        </thead> -->
    </table>
</div>
</body>
</html>