<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
   DistributionManager dm=new  DistributionManager();
   ArrayList list=dm.search();
   String message=(String)request.getAttribute("message");
   if(message==null)  message="";
 %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>路线管理-客户-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<script type=text/javascript>
  function no_null()
  {
     var rN=document.getElementById("rName");
     var rL=document.getElementById("rLength");
     if(rN.value==""||rL.value=="")
     {
      if(rN.value!="") alert("距离不能为空！");
      else if(rL.value!="") alert("路线名不能为空！");
      else  alert("路线名、距离不能为空！");
     }
     else
     {
       frm1.action="actionRoute.jsp?action=add";
       frm1.submit();
     }
  }
</script>
</head>
<body style="background: none">
<div class="top-bar">
  <h1>新增路线</h1>
  <div class="breadcrumbs"> <a href="<%=path%>/indexcompany.jsp" target="_top">首页</a> / <a href="routeManager.jsp" target="_top">路线管理</a> </div>
</div>

<div class="select-bar"/>
<form name="frm1" method="post" id="addroute" action="">
  <div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
    <table class="listing" cellpadding="0" cellspacing="0">
      <tr>
        <th colspan="4">新增路线(*号为必填项)</th>
      </tr>
      <tr>
        <td class="first">路线名称(*)</td>
        <td><input type="text" id="rName" name="routename" class="text" value=<%=request.getAttribute("name")!=null?request.getAttribute("name"):""%>></input></td>
        <td class="first" >起点(*)</td>
        <td class="last">
        <select name="sDistribution" class="text">
          <% 
          for(int i=0;i<list.size();i++)
         {
           Distribution db=(Distribution) list.get(i);
           out.print("<option value='"+db.getId()+"'>"+db.getName()+"</option>");
         }
           
          %>
        </select>
        </td>
      </tr>
      <tr>
        <td class="first">终点(*)</td>
        <td>
        <select name="tDistribution" class="text">
           <% 
          for(int i=0;i<list.size();i++){
           Distribution db=(Distribution) list.get(i);
           out.print("<option value='"+db.getId()+"'>"+db.getName()+"</option>");
         }
           
          %>
        </select>
        </td>
        <td class="first">总长度(*)</td>
        <td><input type="text" id="rLength" name="length" class="text" value=<%=request.getAttribute("length")!=null?request.getAttribute("length"):""%>></input></td>
      </tr>
      <tr>
        <td class="first" colspan="4"><div class="but">
      <input type="submit" value="保 存" class="button" onclick="no_null()"/><span style="margin-left:10px;"><%=message %></span></div></td>
      </tr>
    </table>
    
  </div>
  </div>
</form>
</body>
</html>
