<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@page import="entity.Route"%>
<%@page import="manager.RouteManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<% 
    String sid=request.getParameter("id");
    RouteManager rm=new RouteManager();
    Route rt=rm.searchById(Integer.parseInt(sid));
    DistributionManager dm=new  DistributionManager();
    Distribution dt=dm.searchById(rt.getsDistribution());
    Distribution db=dm.searchById(rt.gettDistribution());
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>路线管理-客户-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
</head>
<body style="background: none">
<div class="top-bar">
  <h1>修改路线</h1>
  <div class="breadcrumbs"> <a href="../indexcompany.jsp" target="_top">首页</a> / <a href="routeManager.html" target="_top">路线管理</a> </div>
</div>
<div class="select-bar"/>
<%out.print("<form name='frm1' method='post' action='actionRoute.jsp?action=upd&id="+rt.getId()+"'>");%>
  <div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
    <table class="listing" cellpadding="0" cellspacing="0">
      <tr>
        <th colspan="4">修改路线(*号为必填项)</th>
      </tr>
      <tr>
        <td class="first">路线名称(*)</td>
        <td><input type="text" name="routeName" class="text" value=<%=rt.getName() %>></input></td>
        <td class="first" >起点(*)</td>
        <td class="last"><lable name="sDistribution" type="text" class="text" ><%=dt.getAddress() %></lable></td>
      </tr>
      <tr>
        <td class="first">终点(*)</td>
        <td>
        <lable name="tDistribution" type="text" class="text" ><%=db.getAddress() %></lable>
        </td>
        <td class="first">总长度(*)</td>
        <td><input type="text" name="routeLength" class="text" value=<%=rt.getLength() %>></input></td>
      </tr>
      <tr>
        <td class="first"  colspan="4"><div class="but">
            <input type="submit" value="更 新"  class="button"></input> <a href="actionRoute.jsp?action=front"> <input type="button" value="返 回"  class="button"></input></a>
          </div></td>
      </tr>
    </table>
  </div>
  </div>
</form>
</body>
</html>
