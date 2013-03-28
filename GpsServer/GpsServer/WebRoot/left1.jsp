<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
        <title>left</title>
        <link href="<%=path %>/css/css.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
        <!-- -->
        </style>
    </head>
    <body style="height:100%;">
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td style="background:url(<%=path %>/images/main_40.gif); height:30px; color:#fff; text-align:center;">
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td valign="top" height="99%">
                    <dl class="sidemenu">
                        <dt id="imgmenu1" onClick="showsubmenu(1)">
                            <img src="<%=path %>/images/i1.gif"/>系统管理 
                        </dt>
                        <dd id="submenu1">
                            <a href="YunWei_basicInfo" target="I2">基本资料</a>
                            <a href="YunWei_groupManager" target="I2">分组机构管理</a>
                            <a href="YunWei_logManager" target="I2">日志管理 </a>
                            <!-- 
                            <s:if test="#session.user.export == 1">
                            <a href="YunWei_excelManager" target="I2">数据报表管理</a>
                            </s:if>
                             -->
                        </dd>
                        <dt id="imgmenu2" onClick="showsubmenu(2)">
                            <img src="<%=path %>/images/i2.gif"/>服务费管理
                        </dt>
                        <dd id="submenu2"> <!-- style="display:none;" -->
                            <a href="YunWei_groupExpenseManager" target="I2">费用充值</a>
                        </dd>
                    </dl>
                </td>
            </tr>
            <tr>
                <td height="18" background="<%=path %>/images/main_58.gif" align="center">
                    版本：2012新锐版
                </td>
            </tr>
        </table>
        <script>
            function showsubmenu(sid){
                whichEl = eval("submenu" + sid);
                imgmenu = eval("imgmenu" + sid);
                if (whichEl.style.display == "none") {
                    eval("submenu" + sid + ".style.display=\"\";");
                }
                else {
                    eval("submenu" + sid + ".style.display=\"none\";");
                }
            }
            
        </script>
    </body>
</html>
