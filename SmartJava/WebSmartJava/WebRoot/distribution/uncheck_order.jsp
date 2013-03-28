<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.Order"%>
<%@page import="java.util.ArrayList"%>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8"); 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>订单管理-客户-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
		@import "../style/all.css";
	</style>
	<style media="all" type="text/css">
		@import "../style/excel.css";
	</style>
	<script type="text/javascript">
		function deleteOrder(){
			if(confirm("您确定要删除该车辆信息吗？")){
				alert("是的");
			}
		}
	</script>
	</head>
	<body style="background: none">
	<div class="top-bar">
      <h1>未审核订单<br /></h1>
      <div class="breadcrumbs"> <a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="orderManager.jsp" target="_top">订单管理</a> </div>
    </div>
<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />    
		    <table class="listing form" cellpadding="0" cellspacing="0">
		    	<tr>
		          <th class="first">订单号码</th>
		          <th>寄件人姓名</th>
		          <th>寄件人地址</th>
		          <th>订单重量</th>
		          <th>订单体积</th>
		          <th>订单状态</th>
		          <th class="last">操作</th>
		        </tr>		       			  
<%
	ArrayList<Order> list=(ArrayList<Order>)request.getAttribute("Order");
	if(list.size()>0)
	{
		int i=0;
		for(i=0;i<list.size();i++)
		{
			out.print("<tr>");
				out.print("<td heigth=20>"+list.get(i).getId()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getSender()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getsAddress()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getWeight()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getVolumn()+"</td>");
				out.print("<td heigth=20>未审核</td>");
				out.print("<td><a href='actionorder.jsp?action=check_order&id="+list.get(i).getId()+"'/><img src='../style/img/edit-icon.gif'  width='16' height='16' alt='' />审核 </a></td>");
			out.print("</tr>");
		}	
	}else {
		out.print("<tr>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
		out.print("</tr>");		
		out.print("<table align='center'>");
			out.print("<tr>");		
				out.print("<td><h2>抱歉!没有查找到未审核的订单!</h2></td>");	
				out.print("<br/><br/><br/><br/><br/><br/><br/>");				
			out.print("</tr>");
		out.print("</table>");	
		
		out.print("<table>");	
		out.print("<tr>");
			out.print("<th></th>");		
			out.print("<br/><br/><br/><br/><br/><br/><br/>");				
		out.print("</tr>");
	out.print("</table>");			
	}
%>	  
	 </table>
      <div class="pager"> 
      <%
      out.print("共"+list.size()+"条记录");
      %>每页<input type="text" class="text" size="2" value="10"/>条 第1页
    <%
    	if(list.size()%10<10)
    	{
    		out.print("共1页");
    	}else {
    	
    		out.print("共"+list.size()%10+"页");
    	} 
    %>
     	<a href="#">第一页</a> 
     	<a href="#">上一页</a> 
     	<a href="#">下一页</a> 
    	<a href="#">最后一页
    	</a>转到<input type="text" class="text" size="1" value="1"/>页
     	<input type="button" class="button" onclick="" value="GO"/>
  </div>	  
	</div>	  
</body>
</html>

