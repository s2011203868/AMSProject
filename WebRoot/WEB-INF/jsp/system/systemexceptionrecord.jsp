<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统异常日志页</title>
<script type="text/javascript">

$('#systemexceptionrecordtable').datagrid({
    height: '100%',
    showToolbar: true,
    //toolbarItem: 'all',
   // local: 'remote',
    columnLock:false,
    //delPK:'roleid',
    dataUrl: 'findSystemExceptionRecord.action',
    columns: [
        {
            name: 'id',
            label: '编号',
            hide:true,
            align: 'center',
        },
        {
           name: 'exceptiontype',
           label: '异常类型',
           align: 'center',
           width:'20%' 
        },
        {
           name: 'exceptioncause',
           label: '异常原因',
           align: 'center',
           width:'35%'
        }
        ,
        {
           name:'uri',
           label:'请求URI',
           align:'center',
           width:'17%',
           
        },
        {
            name:'ipaddress',
            label:'IP地址',
            align:'center',
            width:'10%',
            
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
            name:'datetime',
            label:'操作日期',
            align:'center',
            type: 'date',
            pattern: 'yyyy-MM-dd HH:mm:ss',
            width:'10%',
         }
        
    ],
    fieldSortable:false,
    filterThead:false,
    //showToolbar:true,
    //toolbarCustom:'<button type="button" class="btn-default">自定义按钮</button>',
    editMode: {dialog:{width:'550',height:'300',title:'角色信息',mask:true}},
    editUrl: 'rolelistedit.action',/*../../html/datagrid/datagrid-edit.html?code={code}*/
   // delUrl:'UserServlet?method=del',
    paging:{pageSize:10,selectPageSize:'20,30', showPagenum:5},
    linenumberAll: true,
    hScrollbar:false,
    tableWidth:'100%',
    contextMenuB:true,
    noremove: false,
    showCheckboxcol:true,
    toolbarItem:'export,|,refresh',
    exportOption: {type:'file', options:{url:'systemexceptionrecordexport.action', loadingmask:false}},
    afterSave:function(){
    	$('#systemexceptionrecordtable').datagrid('refresh',true);
    }
});
</script>
</head>
<body>
    <div class="bjui-pageContent">
    	<table id="systemexceptionrecordtable" class="table table-bordered">
	    </table>
	</div>
</body>
</html>