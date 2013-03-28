<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
   String message=(String)request.getAttribute("message");
   if(message==null)  message="";
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配送点管理-省公司-物流管理系统</title>
<style media="all" type="text/css">
@import "../style/all.css";
</style>
</head>
<body style="background: none">
<div class="top-bar">
  <h1>新增配送点</h1>
  <div class="breadcrumbs"> <a href="../indexcompany.jsp" target="_top">首页</a> / <a href="distributionManager.jsp" target="_top">配送点管理</a> </div>
</div>
<div class="select-bar"/>
<form name="form1" method="post" action="actionDistribution.jsp?action=add">
  <div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
    <table class="listing form" cellpadding="0" cellspacing="0">
      <tr>
        <th colspan="4">新增配送点(*号为必填项)</th>
      </tr>
      <tr>
        <td class="first" >配送点名字(*)</td>
        <td ><input name="name" type="text" class="text" /></td>
        <td class="first" >配送点地址(*)</td>
        <td><input type="textarea" name="address" class="text"/></td>
      </tr>
      <tr>
        <td class="first" >管理员账号(*)</td>
        <td ><input name="adminName" type="text" class="text" /></td>
        <td class="first" >管理员密码(*)</td>
        <td><input type="textarea" name="adminPassword" class="text"/></td>
      </tr>
      <tr>
        <td class="first" >管理员姓名(*)</td>
        <td ><input name="adminRealname" type="text" class="text" /></td>
        <td class="first" >管理员电话(*)</td>
        <td><input type="textarea" name="adminPhone" class="text"/></td>
      </tr>
      <tr>
        <td class="first" colspan="4"><div class="but">
            <input type="button" value="保 存"  class="button" onclick="form1.submit();" /> <span> <%=message%> </span>
          </div></td>

      </tr>
    </table>
  </div>
</form>
</body>
</html>
