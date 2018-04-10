<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色分配页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">


$('#roledistributelisttable').datagrid({
    height: '100%',
    showToolbar: true,
    //toolbarItem: 'all',
    //local: 'remote',
    columnLock:false,
    delPK:'id',
    dataUrl: 'getRoleDistributeList.action',
    columns: [
    	{
            name: 'userid',
            label: 'id',
            hide:true 
         },
    	{
            name: 'username',
            label: '姓名',
            align: 'center',
            width: 50 
         },
    	{
            name: 'account',
            label: '账号',
            align: 'center',
            width: 50,
            
        },
        {
           name: 'status',
           label: '状态',
           align: 'center',
           width: 40,
           render: function(value) {
        	   if(value == '1'){
                   return '<span style="color:green">正常</span>'
               }
               if(value =='2'){
                   return '<span style="color:red">注销</span>'
               }
               //return String(value) == '1' ? '正常' : '注销'
           }
        },
        {
            name: 'role',
            label: '拥有的角色',
            align: 'center',
            width: 100
         }/* ,
         {
            name: '',
            label: '操作列',
            align: 'center',
            width: 40,
            render:function(){
            	var html = '<a href="getRoleList.action?id={userid}" class="btn btn-blue" >选择角色</a>'
            	return html
            }
         } */
        
       /*  ,
        {
           name:'birthday',
           label:'生日',
           align:'center',
           type: 'date',
           pattern: 'yyyy-MM-dd',
           
        } */
        
    ],
    editMode: {dialog:{width:'600',height:'350',title:'选择角色',mask:true}},
    editUrl: 'selectrole.action',/*../../html/datagrid/datagrid-edit.html?code={code}*/
    //delUrl:'UserServlet?method=del',
    paging:{pageSize:10,selectPageSize:'20,30', showPagenum:5},
    linenumberAll: true,
    tableWidth:'100%',
    contextMenuB:true,
    noremove: false,
    showCheckboxcol:true,
    toolbarItem:'edit,|,refresh',
    afterSave:function(){
    	$('#roledistributelisttable').datagrid('refresh',true);
    }
});

</script>
  </head>
  <body>
     <div class="bjui-pageContent">
    	<table id="roledistributelisttable" class="table table-bordered">
	    </table>
	  </div>
  </body>
</html>
