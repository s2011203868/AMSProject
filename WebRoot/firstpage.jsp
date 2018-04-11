<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>档案管理系统首页</title>
<meta name="Keywords" content="js/B-JUI.1.31/B-JUI,Bootstrap,jquery,ui,前端,框架,开源,OSC,开源框架,knaan"/>
<meta name="Description" content="js/B-JUI.1.31/B-JUI(Best jQuery UI)前端管理框架。轻松开发，专注您的业务，从js/B-JUI.1.31/B-JUI开始！"/> 
<Link Rel="SHORTCUT ICON" href="js/B-JUI.1.31/favicon.ico">

<!-- init -->
<script type="text/javascript">

$(function() {
	$("#bjui-hnav-navbar").children().last().addClass("active");
    BJUI.init({
        JSPATH       : 'js/B-JUI.1.31/B-JUI/',         //[可选]框架路径
        PLUGINPATH   : 'js/B-JUI.1.31/B-JUI/plugins/', //[可选]插件路径
        loginInfo    : {id:'login_timeout_dialog',url:'/AMSProject/login_timeout.html',mask:false,width:350, height:240}, // 会话超时后弹出登录对话框
        statusCode   : {ok:200, error:300, timeout:301}, //[可选]js/B-JUI.1.31/login_timeout.html
        ajaxTimeout  : 300000, //[可选]全局Ajax请求超时时间(毫秒)
        alertTimeout : 3000,  //[可选]信息提示[info/correct]自动关闭延时(毫秒)
        pageInfo     : {total:'totalRow', pageCurrent:'pageCurrent', pageSize:'pageSize', orderField:'orderField', orderDirection:'orderDirection'}, //[可选]分页参数
        keys         : {statusCode:'statusCode', message:'message'}, //[可选]
        ui           : {
                         sidenavWidth     : 220,
                         showSlidebar     : true, //[可选]左侧导航栏锁定/隐藏
                         overwriteHomeTab : false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                       },
        debug        : true,    // [可选]调试模式 [true|false，默认false]
        theme        : 'purple' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    })
    //时钟
    var today = new Date(), time = today.getTime()
    $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
    setInterval(function() {
        today = new Date(today.setSeconds(today.getSeconds() + 1))
        $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
    }, 1000)
    
    
    
    
    //---------------------------图表-------------------------
    $('#firstchart').highcharts({
        chart: {
            type: 'area'
        },
        title: {
            text: '美苏核武器库存量'
        },
        subtitle: {
            text: '数据来源: <a href="https://thebulletin.metapress.com/content/c4120650912x74k7/fulltext.pdf">' +
            'thebulletin.metapress.com</a>'
        },
        xAxis: {
            allowDecimals: false,
            labels: {
                formatter: function () {
                    return this.value; // clean, unformatted number for year
                }
            }
        },
        yAxis: {
            title: {
                text: '核武库国家'
            },
            labels: {
                formatter: function () {
                    return this.value / 1000 + 'k';
                }
            }
        },
        tooltip: {
            pointFormat: '{series.name} 制造 <b>{point.y:,.0f}</b>枚弹头'
        },
        plotOptions: {
            area: {
                pointStart: 1940,
                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
        series: [{
            name: '美国',
            data: [null, null, null, null, null, 6, 11, 32, 110, 235, 369, 640,
                   1005, 1436, 2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,
                   27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,
                   26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,
                   24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344, 23586,
                   22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
                   10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104]
        }, {
            name: '苏联/俄罗斯',
            data: [null, null, null, null, null, null, null, null, null, null,
                   5, 25, 50, 120, 150, 200, 426, 660, 869, 1060, 1605, 2471, 3322,
                   4238, 5221, 6129, 7089, 8339, 9399, 10538, 11643, 13092, 14478,
                   15915, 17385, 19055, 21205, 23044, 25393, 27935, 30062, 32049,
                   33952, 35804, 37431, 39197, 45000, 43000, 41000, 39000, 37000,
                   35000, 33000, 31000, 29000, 27000, 25000, 24000, 23000, 22000,
                   21000, 20000, 19000, 18000, 18000, 17000, 16000]
        }]
    });
    
    
    $('#secondchart').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: '各洲不同时间的人口条形图'
        },
        subtitle: {
            text: '数据来源: Wikipedia.org'
        },
        xAxis: {
            categories: ['非洲', '美洲', '亚洲', '欧洲', '大洋洲'],
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '人口总量 (百万)',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: ' 百万'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true,
                    allowOverlap: true
                }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '1800 年',
            data: [107, 31, 635, 203, 2]
        }, {
            name: '1900 年',
            data: [133, 156, 947, 408, 6]
        }, {
            name: '2008 年',
            data: [973, 914, 4054, 732, 34]
        }]
    });
    //---------------------------图表结束---------------------
})


//菜单-事件-zTree
function MainMenuClick(event, treeId, treeNode) {
    if (treeNode.target && treeNode.target == 'dialog' || treeNode.target == 'navtab')
        event.preventDefault()
    
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj(treeId)
        
        zTree.expandNode(treeNode)
        return
    }
    
    if (treeNode.target && treeNode.target == 'dialog')
        $(event.target).dialog({id:treeNode.targetid, url:treeNode.url, title:treeNode.name})
    else if (treeNode.target && treeNode.target == 'navtab')
        $(event.target).navtab({id:treeNode.targetid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
}

// 满屏开关
var bjui_index_container = 'container_fluid'

function bjui_index_exchange() {
    bjui_index_container = bjui_index_container == 'container_fluid' ? 'container' : 'container_fluid'
    
    $('#bjui-top').find('> div').attr('class', bjui_index_container)
    $('#bjui-navbar').find('> div').attr('class', bjui_index_container)
    $('#bjui-body-box').find('> div').attr('class', bjui_index_container)
}

</script>
</head>
<body onselectstart="return false" ondragstart="return false">
    <!--[if lte IE 7]>
        <div id="errorie"><div>您还在使用老掉牙的IE，正常使用系统前请升级您的浏览器到 IE8以上版本 <a target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>&nbsp;&nbsp;强烈建议您更改换浏览器：<a href="http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome</a></div></div>
    <![endif]-->
    <div id="bjui-top" class="bjui-header">
        <div class="container_fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapsenavbar" data-target="#bjui-top-collapse" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <nav class="collapse navbar-collapse" id="bjui-top-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="datetime"><a><span id="bjui-date">0000/00/00</span> <span id="bjui-clock">00:00:00</span></a></li>
                    <li><a href="javascript:;">账号：${ activeUser.username }</a></li>
                    <li><a href="javascript:;">部门：${ activeUser.dept }</a></li>
                    <li><a href="javascript:;">岗位：${ activeUser.role }</a></li>
                    <li><a href="changepassword.action?activeUser=${activeUser }" data-toggle="dialog" data-id="sys_user_changepass" data-mask="true" data-width="400" data-height="300">修改密码</a></li>
                    <li><a href="${basePath}logout.action" style="font-weight:bold;">&nbsp;<i class="fa fa-power-off"></i> 退出</a></li>
                    
                    
                    <li class="dropdown"><a href="#" class="dropdown-toggle bjui-fonts-tit" data-toggle="dropdown" title="更改字号"><i class="fa fa-font"></i> 大</a>
                        <ul class="dropdown-menu" role="menu" id="bjui-fonts">
                            <li><a href="javascript:;" class="bjui-font-a" data-toggle="fonts"><i class="fa fa-font"></i> 特大</a></li>
                            <li><a href="javascript:;" class="bjui-font-b" data-toggle="fonts"><i class="fa fa-font"></i> 大</a></li>
                            <li><a href="javascript:;" class="bjui-font-c" data-toggle="fonts"><i class="fa fa-font"></i> 中</a></li>
                            <li><a href="javascript:;" class="bjui-font-d" data-toggle="fonts"><i class="fa fa-font"></i> 小</a></li>
                        </ul>
                    </li>										
                    <li class="dropdown active" ><a href="#" class="dropdown-toggle theme" data-toggle="dropdown" title="切换皮肤"><i class="fa fa-tree"></i>&nbsp</a>
                        <ul class="dropdown-menu" role="menu" id="bjui-themes">
                            <li><a href="javascript:;" class="theme_purple" data-toggle="theme" data-theme="purple">&nbsp;<i class="fa fa-tree"></i> 紫罗兰</a></li>
                            <li><a href="javascript:;" class="theme_blue" data-toggle="theme" data-theme="blue">&nbsp;<i class="fa fa-tree"></i> 天空蓝</a></li>
                            <li><a href="javascript:;" class="theme_green" data-toggle="theme" data-theme="green">&nbsp;<i class="fa fa-tree"></i> 祖母绿</a></li>
                            <li class="active"><a href="javascript:;" class="theme_orange" data-toggle="theme" data-theme="orange">&nbsp;<i class="fa fa-tree"></i> 活力橙</a></li>
                        </ul> 
                    </li>
                    <li><a href="javascript:;" onclick="bjui_index_exchange()" title="横向收缩/充满屏幕"><i class="fa fa-exchange"></i>&nbsp</a></li>
                </ul>
            </nav>
        </div>
    </div>
    <header class="navbar bjui-header" id="bjui-navbar">
        <div class="container_fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" id="bjui-navbar-collapsebtn" data-toggle="collapsenavbar" data-target="#bjui-navbar-collapse" aria-expanded="false">
                    <i class="fa fa-angle-double-right"></i>
                </button>
                <a class="navbar-brand" href="http://b-jui.com"><img src="js/B-JUI.1.31/images/logo3.png" height="30"></a>
            </div>
            <nav class="collapse navbar-collapse" id="bjui-navbar-collapse">
                <ul class="nav navbar-nav navbar-right" id="bjui-hnav-navbar">
	                <c:forEach var="menu" items="${activeUser.menus }" varStatus = "status">
	                	<li><!-- class="active" -->
	                        <a href="${menu.url }?account=${activeUser.account}&menuid=${menu.id}" data-toggle="sidenav" data-id-key="targetid">${menu.name }</a>
	                    </li>
	                </c:forEach>
                </ul>
            </nav>
        </div>
    </header>
    <div id="bjui-body-box">
        <div class="container_fluid" id="bjui-body">
            <div id="bjui-sidenav-col">
                <div id="bjui-sidenav">
                    <div id="bjui-sidenav-arrow" data-toggle="tooltip" data-placement="left" data-title="隐藏左侧菜单">
                        <i class="fa fa-long-arrow-left" style="margin-top:9px"></i>
                    </div>
                   <div id="bjui-sidenav-box">
                   </div> 
                </div>
            </div>
            <div id="bjui-navtab" class="tabsPage">
                <div id="bjui-sidenav-btn" data-toggle="tooltip" data-title="显示左侧菜单" data-placement="right">
                    <i class="fa fa-bars" style="margin-top:9px"></i>
                </div>
                <div class="tabsPageHeader">
                    <div class="tabsPageHeaderContent">
                        <ul class="navtab-tab nav nav-tabs">
                            <li><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a></li>
                        </ul>
                    </div>
                    <div class="tabsLeft"><i class="fa fa-angle-double-left"></i></div>
                    <div class="tabsRight"><i class="fa fa-angle-double-right"></i></div>
                    <div class="tabsMore"><i class="fa fa-angle-double-down"></i></div>
                </div>
                <ul class="tabsMoreList">
                    <li><a href="javascript:;">#maintab#</a></li>
                </ul>
                <div class="navtab-panel tabsPageContent">
                    <div class="navtabPage unitBox">
                        <div class="bjui-pageContent">
                           <div id="firstchart" style="min-width:400px;height:350px" data-toggle="highcharts" ></div>
                           <div id="secondchart" style="min-width:400px;height:350px" data-toggle="highcharts" ></div>
                           <div style="min-width:400px;height:350px" data-toggle="highcharts" data-url="js/B-JUI.1.31/json/pie.json"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="js/B-JUI.1.31/B-JUI/other/ie10-viewport-bug-workaround.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
