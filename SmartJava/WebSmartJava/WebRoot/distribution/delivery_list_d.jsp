<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.PlacePrice"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Order"%>
<%
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
			if(confirm("您确定要删除该订单信息吗？")){
				alert("是的");
			}
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>订单列表</h1>
      <div class="breadcrumbs"> <a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="orderManager.jsp" target="_top">订单管理</a> </div>
    </div>
<form action="actionorder.jsp?action=place_choose" method="post">
      <div class="select-bar"/>
      <table class="select-table">
<% 
	ArrayList<PlacePrice> listplace=(ArrayList<PlacePrice>)request.getAttribute("PlacePriceList");
%>      
			<tr>
				<th>派送范围</th>
				<td>
					<select name="placevalue" style="width:70px">
					<%
						int i=0;
						out.print("<option value=''></option>");
						for(i=0;i<listplace.size();i++)
						{
							out.print("<option value="+listplace.get(i).getPlaceName()+">"+listplace.get(i).getPlaceName()+"</option>");
						}	
					 %>					
					</select>
				</td>
					<th><input type="submit" name="Submit"  value="查询派送订单" class="button"/></th>
					<th><input type="submit" name="Submit"  value="派送订单" class="button"/></th>
			</tr>
  </table>
      </div>
    </form>
<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
      <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">订单号码</th>
          <th>收件人姓名</th>
          <th>收件人地址</th>
          <th>订单重量</th>
          <th>订单体积</th>
          <th>订单状态</th>
          <th>是否派件</th>
        </tr>
<%
	ArrayList<Order> list=(ArrayList<Order>)request.getAttribute("OrderList");
	if(list.size()>0)
	{
		for(i=0;i<list.size();i++)
		{
			out.print("<tr>");
				out.print("<td heigth=20>"+list.get(i).getId()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getReceiver()+"</td>");
				out.print("<td heigth=20>"+list.get(i).gettAddress()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getWeight()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getVolumn()+"</td>");
				out.print("<td heigth=20>待派件</td>");
				out.print("<td heigth=20><input type='checkbox' name='checkbox' value="+list.get(i).getId()+"></td>");
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
    	if(list.size()<10)
    	{
    		out.print("共1页");
    	}else {
    		out.print("共"+(list.size()/10+1)+"页");
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


