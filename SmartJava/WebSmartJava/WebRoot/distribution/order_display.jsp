<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entity.Order"%>
<%@ page import="manager.DistributionManager"%>
<%@  page import="java.util.ArrayList"%>
<%@ page import="entity.User"%>
<%@ page import="entity.Distribution"%>
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
		//判空
		function CheckForm(){ 
			//收件人
			var tName = document.all.tName; 
			var tPhone = document.all.tPhone; 
			//寄件人	
			var sName = document.all.sName; 	
			var sPhone = document.all.sPhone; 
			//重量体积
			var weight = document.all.weight; 
			var volumn = document.all.volumn; 	
			//类别
			var goodsType = document.all.goodsType;	
			
			if(tName.value.length==0||tPhone.value.length==0||
					sName.value.length==0||sPhone.value.length==0||
					weight.value.length==0||volumn.value.length==0||
					goodsType.value==0){ 
				tName.focus(); 
				alert("温馨提示:\n带*内容必填"); 
				return false; 
			}else{
				return true;
			}
		}		
	</script>
</head>
<body style="background: none">
	<div class="top-bar">
				<!--a href="#" class="button">ADD NEW </a-->
				<h1>订单预览</h1>
				<div class="breadcrumbs">
					<a href="../../index.html" target="_top">首页</a> / <a href="../index.html">订单管理</a>
				</div>
			</div>

<%
	Order order=(Order)request.getAttribute("Order");
%>	
		
	<form action="actionorder.jsp?action=''" method="post" onsubmit="return CheckForm();">
		  <div class="select-bar">
<% 
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
				out.print("<td><h2>订单操作成功!订单详情如下</h2></td>");					
			out.print("</tr>");
		out.print("</table>");	
		
		out.print("<table>");	
		out.print("<tr>");
			out.print("<th></th>");					
		out.print("</tr>");
	out.print("</table>");		
	%>			  
			<table class="select-table">
				<tr>
					<th>订单号</th>
					<%
						out.print("<td>"+order.getId()+"</td>");
					 %>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<th>收件人姓名</th>
					<%
						out.print("<td>"+order.getReceiver()+"</td>");
					 %>
					 <th>收件人地址</th>
					<%
						out.print("<td>"+order.gettAddress()+"</td>");
					 %>						
				</tr>
				<tr>
					<th>收件人电话号码</th>
					<%
						out.print("<td>"+order.gettPhone()+"</td>");
					 %>
					<th>收件人邮编</th>
					<%
						out.print("<td>"+order.gettCode()+"</td>");
					 %>
				</tr>
				<tr>
					<th>寄件人姓名</th>
					<%
						out.print("<td>"+order.getSender()+"</td>");
					 %>
					<th>寄件人地址</th>
					<%
						out.print("<td>"+order.getsAddress()+"</td>");
					 %>
				</tr>
				<tr>
					<th>寄件人电话号码</th>
					<%
						out.print("<td>"+order.getsPhone()+"</td>");
					 %>
					<th>寄件人邮编</th>
					<%
						out.print("<td>"+order.getsCode()+"</td>");
					 %>
				</tr>
				<tr>
					<th>寄件重量</th>
					<%
						out.print("<td>"+order.getWeight()+"</td>");
					 %>
					<th>寄件体积</th>
					<%
						out.print("<td>"+order.getVolumn()+"</td>");
					 %>
				</tr>
				<tr>
 
					<th>寄件物品类别</th>
						<%
							if(order.getGoodsType()==0)
							{
								out.print("<td>易碎</td>");						
							}else if(order.getGoodsType()==1)
							{
								out.print("<td>危险</td>");								
							}else if(order.getGoodsType()==2)
							{
								out.print("<td>快件</td>");								
							}else if(order.getGoodsType()==3)
							{
								out.print("<td>普邮</td>");		
							}
						 %>
						<th>备注信息</th>
						<%
							out.print("<td>"+order.getRemark()+"</td>");
						 %>
				</tr>
				<tr>
					<th>寄件重量</th>
					<%
						out.print("<td>"+order.getWeight()+"</td>");
					 %>
					<th>寄件体积</th>
					<%
						out.print("<td>"+order.getVolumn()+"</td>");
					 %>
				</tr>				
				<tr>
					<th>订单路径</th>
					<%
						String pathstring=(String)request.getAttribute("PATH");	
						out.print("<td>"+pathstring+"</td>");
					 %>					
					<th>订单价格</th>
					<%
						out.print("<td>"+order.getPrice()+"</td>");
					 %>	
				</tr>
				<tr>
					<th>揽件日期</th>
					<%
						out.print("<td>"+order.getWorkDate()+"</td>");
					 %>					
					<th></th>
					<th></th>					
				</tr>				
				<tr>				
					<th><input type="button" name="Submit" value="打印订单" class="button" /></th>				
					<th></th>
					<th><input type="button" name="Submit" value="返回" class="button" onclick="javascript:history.go(-1);"/></th>
					<th></th>
				</tr>
			</table>
		</div>
	</form>
		    <label></label>
		<p>注:运输价格计算方法(单位:元)</p>
		<p>货物运送价格=货物运输费用+货物配送费用</p>
		<p>货物运输费用=货物首公斤价格+(货物重量-1)*货物次公斤价格(不足一公斤按一公斤算)</p>
		<p>另:货物配送费用为各配送点自行决定</p>		  
</body>
</html>

