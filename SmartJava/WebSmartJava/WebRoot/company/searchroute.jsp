
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
   ArrayList list1=(ArrayList)request.getAttribute("list");
   
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>路线管理-省公司-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
@import "../style/all.css";
</style>
	<style media="all" type="text/css">
@import "../style/excel.css";
</style>
	<script type="text/javascript">
		function deleteOrder(){
			if(!confirm("您确定要删除该路线信息吗？")){
			document.getElementById("delete").href="actionRoute.jsp?action=front";	
			}
		}
		function confirm_null(){
		 var s=document.getElementById("sDistribution");
		 var t=document.getElementById("tDistribution");
		
		 if(s.value!=""&&t.value!="")
		 {
		    frm1.action="actionRoute.jsp?action=search";
		    frm1.submit();
		 }
		  else
		 {
		   if(s.value!=""){alert("终点不能为空！");}
		   else if(t.value!="") {alert("起点不能为空！");}
		   else {alert("起点、终点不能为空！");}
		 }
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>路线列表</h1>
      <div class="breadcrumbs"> <a href="../indexcompany.jsp"  target="_top">首页</a> / <a href="distributionManager.jsp" target="_top">路线管理</a> </div>
</div>
<form name="frm1" method="post" action="" >
      <div class="select-bar"/>
      <table class="select-table">
    <tr>
          <th colspan="4" class="full"><div class="search_table_row">起始点
              <input type="text" id="sDistribution" name="sD" class="text" ></input>
              至终点 &nbsp;
              <input type="text" id="tDistribution" name="tD" class="text" ></input>
              <input type="submit" class="button" width="200"  value="查 询" onclick="confirm_null()"></input>
            </div></th>
        </tr>
  </table>
      </div>
    </form>
<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
      <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th>路线ID</th>
          <th>路线名称</th>
          <th>起点配送点</th>
          <th>终点配送点</th>
          <th>总长度</th>
          <th>操作</th>
        </tr>
   <% 
	   for(int i=0;i<list1.size();i++)
	   {
	      Route rt=(Route) list1.get(i);
	      DistributionManager dm=new DistributionManager();
	      Distribution dt=dm.searchById(rt.getsDistribution());
	      Distribution db=dm.searchById(rt.gettDistribution());
	      out.print("<tr>");
	      out.print("<td>"+rt.getId()+"</td>");
	      out.print("<td>"+rt.getName()+"</td>");
	      out.print("<td>"+dt.getAddress()+"</td>");
	      out.print("<td>"+db.getAddress()+"</td>");
	      out.print("<td>"+rt.getLength()+"</td>");
	      out.print("<td><a href='actionRoute.jsp?action=update&id="+rt.getId()+"' class='table_op_a'><img src='../style/img/edit-icon.gif' onclick='' width='16' height='16' alt='' />修改</a><a class='table_op_a' onclick='deleteOrder()' id='delete' href='actionRoute.jsp?action=del&id="+rt.getId()+"'> <img src='../style/img/hr.gif' width='16' height='16' alt='' />删除</a></td>");
	      out.print("</tr>");
	    }
      %>
  </table>
    </div>
</body>
</html>
