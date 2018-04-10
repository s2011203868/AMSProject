<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>系统用户列表页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">

$(function(){
	document.onkeydown = function(e) {
	    e = e || window.event;
	    if(e.keyCode == 13) {
	    	$('#sysuserlisttable').datagrid('saveAll');
	    }
	}
})

$('#sysuserlisttable').datagrid({
   
    columns: [
        {
            name: 'userid',
            label: '用户编号',
            align: 'center',
            width: 20,
            hide:true,
            edit:false
        },
        {
           name: 'username',
           label: '姓名',
           align: 'center',
           width: 70 ,
           edit:false
        },
        {
            name: 'account',
            label: '账号',
            align: 'center',
            width: 70,
            edit:false
         },
         {
             name: 'deptid',
             label: '部门编号',
             align: 'center',
             width: 70 ,
             hide:true,
             edit:false
          },
          {
              name: 'dept',
              label: '部门',
              align: 'center',
              width: 70 ,
              edit:false
           },
           {
               name: 'dutyid',
               label: '岗位编号',
               align: 'center',
               width: 70 ,
               hide:true,
               edit:false
            },
            {
                name: 'duty',
                label: '岗位',
                align: 'center',
                width: 70 ,
                edit:false
             },
          {
           name: 'status',
           label: '用户状态',
           align: 'center',
           width: 50,
           type:'select',
           items: function() {
        	   return [{'1':'<span style="color:green">正常</span>'}, {'2':'<span style="color:red">禁用</span>'}]
           }
        }
       ,
        {
           name:'begintime',
           label:'创建日期',
           align:'center',
           type: 'date',
           width: 90,
           pattern: 'yyyy-MM-dd HH:mm:ss',
           edit:false
        }
       ,
       {
          name:'lasttime',
          label:'最后一次登录',
          align:'center',
          type: 'date',
          width: 90,
          pattern: 'yyyy-MM-dd HH:mm:ss',
          edit:false
       }
       ,
       {
          label:'操作',
          align:'center',
          width: 50,
          render:function(){
        	  var html = '<button type="button"  class="btn-red" data-toggle="edit.datagrid.tr">状态修改</button>'
        	  return html
          }
       }
        
    ],
    height: '100%',
    showToolbar: true,
    showLinenumber :false,
    fieldSortable :false,
    filterThead:false,
    hScrollbar :true,
    columnLock:false,
    editUrl:'EditUserStatus.action',
    dataUrl: 'getSysUserList.action',
    paging:{pageSize:50,selectPageSize:'100,150', showPagenum:5},
    tableWidth:'100%',
    contextMenuB:true,
    noremove: false,
    showCheckboxcol:true,
    toolbarItem:'cancel,save,refresh',
    toolbarCustom:'<button type="button" class="btn-red" onclick="plusNewUser()" data-icon="add">新增用户</button> <button type="button" class="btn-blue" onclick="edituser()" data-icon="edit">用户变更</button> <button type="button" class="btn-Orange" id="originalpassword" onclick="originalpassword()" data-icon="window-restore">初始密码</button>',
    afterSave:function(){
    	$('#sysuserlisttable').datagrid('refresh',true);
    }
});

function plusNewUser(){
	BJUI.dialog({
        id:'addNewUserid',
        width:'700',
        height:'500',
        mask:true,
        url:'userlistedit.action',
        title:'新增用户',
        onClose:function(){
            $('#sysuserlisttable').datagrid('refresh',true);
        }
    });
}

function edituser(){
	if($("#sysuserlisttable").data('selectedDatas') == null){
        BJUI.alertmsg('info', '未选择一项！');
        return
   }
   var account = $("#sysuserlisttable").data('selectedDatas')[0].account;
   var userid = $("#sysuserlisttable").data('selectedDatas')[0].userid;
   var username = $("#sysuserlisttable").data('selectedDatas')[0].username;
	BJUI.dialog({
        id:'editUserid',
        width:'700',
        height:'500',
        mask:true,
        url:'userlistedit.action',
        data:{'userid':userid,'account':account,'username':username},
        title:'变更用户信息',
        onClose:function(){
            $('#sysuserlisttable').datagrid('refresh',true);
        }
    });
}


function originalpassword(){
	if($("#sysuserlisttable").data('selectedDatas') == null){
        BJUI.alertmsg('info', '未选择一项！');
        return
   }
   var account = $("#sysuserlisttable").data('selectedDatas')[0].account;
   var userid = $("#sysuserlisttable").data('selectedDatas')[0].userid;
   $(this).bjuiajax('doajax', {
       type:'post',
       url:'originalpassword.action',
       confirmMsg:'你确定要为用户['+account+']初始化密码为[111111]吗？',
       data:{'account':account,'userid':userid},
    });
}
</script>
  </head>
  <body>
     <div class="bjui-pageContent">
    	<table id="sysuserlisttable" class="table table-bordered">
	    </table>
	  </div>
  </body>
</html>
