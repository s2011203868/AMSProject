<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>在线用户列表页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
$('#onlineusertable').datagrid({
    height: '100%',
    showToolbar: true,
    //toolbarItem: 'all',
   // local: 'remote',
    columnLock:false,
    //delPK:'userid',
    dataUrl: 'findOnlineUser.action',
    columns: [
        {
            name: 'sessionid',
            label: 'SessionID',
            align: 'center',
            width: 150,
        },
        {
            name: 'account',
            label: '账号',
            align: 'center',
            width: 40 
         },
         {
             name: 'hosts',
             label: '主机地址',
             align: 'center',
             width: 60 
          },
        {
           name: 'status',
           label: '用户状态',
           align: 'center',
           width: 30,
           render: function(value) {
               if(value == '1'){
                   return '<span style="color:green">在线</span>'
               }
               if(value =='2'){
                   return '<span style="color:red">下线</span>'
               }
           }
        }
       ,
       {
          name:'lastTime',
          label:'最后访问时间',
          align:'center',
          type: 'date',
          width: 90,
          pattern: 'yyyy-MM-dd HH:mm:ss',
       }
        
    ],
    editMode: {dialog:{width:'550',height:'300',title:'角色信息',mask:true}},
    editUrl: 'userlistedit.action',/*../../html/datagrid/datagrid-edit.html?code={code}*/
   // delUrl:'UserServlet?method=del',
    paging:{pageSize:50,selectPageSize:'80,100', showPagenum:5},
    linenumberAll: true,
    tableWidth:'100%',
    contextMenuB:true,
    noremove: false,
    showCheckboxcol:true,
    toolbarItem:'refresh',
    //toolbarCustom:'<button type="button" class="btn-Orange" id="originalpassword" onclick="originalpassword()" data-icon="window-restore">初始密码</button>',
    afterSave:function(){
        $('#onlineusertable').datagrid('refresh',true);
    }
});
</script>
  </head>
  <body>
      <div class="bjui-pageContent">
    	<table id="onlineusertable" class="table table-bordered">
	    </table>
	  </div>
  </body>
</html>
