<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统操作日志页</title>
<script type="text/javascript">

$('#systemexecuterecordtable').datagrid({
    height: '100%',
    showToolbar: true,
    //toolbarItem: 'all',
   // local: 'remote',
    columnLock:false,
    //delPK:'roleid',
    dataUrl: 'findSystemExecuteRecord.action',
    columns: [
        {
            name: 'id',
            label: '编号',
            hide:true,
            align: 'center',
        },
        {
           name: 'description',
           label: '操作描述',
           align: 'center',
           width:'17%' 
        }
        ,
        {
           name:'uri',
           label:'请求URI',
           align:'center',
           width:'32%',
           
        },
        {
            name: 'usetime',
            label: '用时',
            align: 'center',
            width:'10%'
         },
        {
            name:'ipaddress',
            label:'IP地址',
            align:'center',
            width:'13%',
            
         }
        ,
        {
            name:'executor',
            label:'操作人',
            align:'center',
            width:'8%',
            
         }
        ,
        {
            name:'executetime',
            label:'操作日期',
            align:'center',
            type: 'date',
            pattern: 'yyyy-MM-dd HH:mm:ss',
            width:'20%',
         }
        
    ],
    fieldSortable:false,
    filterThead:false,
    //showToolbar:true,
    //toolbarCustom:'<button type="button" class="btn-default">自定义按钮</button>',
    //editMode: {dialog:{width:'550',height:'300',title:'角色信息',mask:true}},
   // editUrl: 'rolelistedit.action',/*../../html/datagrid/datagrid-edit.html?code={code}*/
   // delUrl:'UserServlet?method=del',
    paging:{pageSize:100,selectPageSize:'200,300', showPagenum:5},
    linenumberAll: true,
    hScrollbar:false,
    tableWidth:'100%',
    contextMenuB:true,
    noremove: false,
    showCheckboxcol:true,
    toolbarItem:'refresh',
    afterSave:function(){
    	$('#systemexecuterecordtable').datagrid('refresh',true);
    }
});
</script>
</head>
<body>
    <div class="bjui-pageContent">
    	<table id="systemexecuterecordtable" class="table table-bordered">
	    </table>
	</div>
</body>
</html>