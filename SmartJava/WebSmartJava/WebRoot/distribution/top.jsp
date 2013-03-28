<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entity.*,manager.*,java.util.*"%>
<% ArrayList<Car> list=(ArrayList<Car>)session.getAttribute("carList"); 
System.out.println("list:----"+list);
   ArrayList<Order> list1=(ArrayList<Order>)request.getAttribute("orderList");
   Car c=(Car)session.getAttribute("loadCar");
%>
<html>
<head>
<title>装货配送</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
 @import "../style/jquery/ui.all.css";
</style>
</head>
<body style="background: none">
<div class="top-bar">
  <h1>装货配送</h1>
  <div class="breadcrumbs"> <a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="goodsManage.jsp" target="_top">装货管理</a> </div>
</div>
<div class="top-bar">
  <table class="select-table" id="search" >
    <tr>
      <th>选择装货车辆：车牌号</th>
      <form name="num" action="actionGoods.jsp?action=uploadNumber" method="post">
      <td><%out.print("<select style='width:auto' name='car_number' id='car_num'> " );
      		for(int i=0;i<list.size();i++){
      		out.print(" <option>"+list.get(i).getNumber()+"</option>");
      		}
      		out.print("</select>");      			      	
      %></td>
        <td><input type="button" onclick="num.submit();"  value="查询"></td></form>
      <td><form name="load" action="actionGoods.jsp?action=load" method="post"><input type="button" onclick="load.submit();"  value="配货"></form></td>
    </tr>
  </table>
</div>
<div class="table">
  <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
      <th class="full" colspan="4">车辆信息</th>
    </tr>
    <tr>
      <td class="first">车牌号码</td>
      <td><%if(c!=null){
      	out.print("<input type='text' name='customerName' class='text' value='"+c.getNumber()+"' /></td>");
      }else{
      	out.print("<input type='text' name='customerName' class='text' /></td>");
      } %>
      <td>司机名称</td>
      <td class="last"><%if(c!=null){
      	out.print("<input name='idcard' type='text' class='text' value='"+c.getDriver()+"' /></td>");
      }else{
      	out.print("<input type='text' name='driver' class='text' /></td>");
      } %>      
    </tr>
    <tr>
      <td class="first">可承载重量（单位：KG）</td>
      <td><%if(c!=null){
      	out.print("<input type='text' class='text' value='"+c.getWeight()+"' /></td>");
      }else{
      	out.print("<input type='text' name='weight' class='text' /></td>");
      } %>
      <td>承载体积（单位：M3）</td>
      <td class="last"><%if(c!=null){
      	out.print("<input type='text' class='text' value='"+c.getVolumn()+"' /></td>");
      }else{
      	out.print("<input type='text' name='volumn' class='text' /></td>");
      } %>     
    </tr>
  </table>
</div>
</body>