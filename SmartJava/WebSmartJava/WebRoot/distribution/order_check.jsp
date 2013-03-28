<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.Order"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>订单修改 - 物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
		@import "../style/all.css";
		@import "../style/jquery/ui.all.css";
	</style>
	<script type="text/javascript" src="../script/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../script/jquery-ui-personalized-1.6rc6.min.js"></script>
	<script type="text/javascript" src="../script/date.js"></script>
	<script type="text/javascript" src='../script/common.js'></script>
	<script type="text/javascript">
		$(function(){
			datepicker('.date-six');
		});
		function del(url){
			if(window.confirm("确认删除吗？")){
				window.location.href=url;
			}
		}
	</script>
</head>
<% 
	Order order=(Order)request.getAttribute("Order");
%>
<body style="background: none">
	<div class="top-bar">
				<!--a href="#" class="button">ADD NEW </a-->
				<h1>审核订单</h1>
				<div class="breadcrumbs">
					<a href="../../index.html" target="_top">首页</a> / <a href="../index.html">订单管理</a>
				</div>
			</div>
	<form>
			<table class="select-table">
				<tr>
					<th>订单号</th>
					<%out.print("<td>"+order.getId()+"</td>");%>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<th>收件人姓名</th>
					<%out.print("<td>"+order.getReceiver()+"</td>");%>
					<th>收件人地址</th>
					<%out.print("<td>"+order.gettAddress()+"</td>");%>
				</tr>
				<tr>
					<th>收件人电话号码</th>
					<%out.print("<td>"+order.gettPhone()+"</td>");%>
					<th>收件人邮编</th>
					<%out.print("<td>"+order.gettCode()+"</td>");%>
				</tr>
				<tr>
					<th>寄件人姓名</th>
					<%out.print("<td>"+order.getSender()+"</td>");%>
					<th>寄件人地址</th>
					<%out.print("<td>"+order.getsAddress()+"</td>");%>
				</tr>
				<tr>
					<th>寄件人电话号码</th>
					<%out.print("<td>"+order.getsPhone()+"</td>");%>
					<th>寄件人邮编</th>
					<%out.print("<td>"+order.getsCode()+"</td>");%>
				</tr>
				<tr>
					<th>寄件重量</th>
					<%out.print("<td>"+order.getWeight()+"</td>");%>
					<th>寄件体积</th>
					<%out.print("<td>"+order.getVolumn()+"</td>");%>
				</tr>
				<tr>
					<th>备注信息</th>
					<%out.print("<td>"+order.getRemark()+"</td>");%>				
					<th>寄件物品类别</th>		
					<%
						if(order.getGoodsType()==0)
						{			
							out.print("<td>危险</td>");
						}else if(order.getGoodsType()==1)
						{			
							out.print("<td>易碎</td>");
						}else if(order.getGoodsType()==2)
						{			
							out.print("<td>快件</td>");
						}else if(order.getGoodsType()==3)
						{			
							out.print("<td>普邮</td>");
						}					
					%>
				</tr>
				<tr>
					<th>揽件日期</th>
					<%out.print("<td>"+order.getWorkDate()+"</td>");%>
					<th>揽件人姓名</th>
					<%out.print("<td>"+order.getWorker()+"</td>");%>
				</tr>
				<tr>
					<th>物流价格</th>
					<%out.print("<td>"+order.getPrice()+"</td>");%>
					<th></th>
					<th></th>
				</tr>
				<tr>				
					<th><input type="button" name="Submit"  value="提交订单" class="button"/></th>				
					<th></th>
					<th><input type="button" name="Submit"  value="发回订单" class="button"/></th>
					<th></th>
				</tr>
			</table>
		</form>
		    <label></label>
		  </div>
</body>
</html>

