<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.PlacePrice"%>
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
		//查空
		function CheckForm(){ 
			var placeName = document.all.placeName; 
			var placePrice = document.all.placePrice; 
			var placeTime = document.all.placeTime; 	
			if(placePrice.value.length==0||placeTime.value.length==0){ 
				placeTime.focus(); 
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
				<h1>修改配送范围及价格</h1>
				<div class="breadcrumbs">
					<a href="index.jsp" target="_top">首页</a> / <a href="placepriceManager.jsp">配送范围及价格管理</a>
				</div>
			</div>
	<form action="actionplaceprice.jsp?action=placeprice_update&flag=1" method="post" onsubmit="CheckForm();">
		  <div class="select-bar">
			<table class="select-table">
				<tr>
				
					<%
						PlacePrice pp=(PlacePrice)request.getAttribute("PlacePrice");
						session.setAttribute("PlacePrice",pp);
					%>				
					<th>配送范围</th>
					<%
						out.print("<td>"+pp.getPlaceName()+"</td>");	
					%>				
					<th></th>
					<th></th>			
				</tr>
				<tr>
					<th>输入新配送价格(*)</th>
					<% 
						out.print("<td><input type='text' name='placePrice' value="+pp.getPlacePrice()+" />(单位:元)</td>");	
					%>
					<th>输入新配送时间(*)</th>
					<%
						out.print("<td><input type='text' name='placeTime' value="+pp.getPlaceTime()+" /></td>");
					 %>
				
				</tr>
				<tr>	
					<th>输入新的备注</th>		
					<%
						out.print("<td><input type='text' name='remark' value="+pp.getRemark()+" /></td>");
					 %>					
					<th></th>
					<th></th>					
				</tr>
				<tr>				
					<th><input type="submit" name="Submit" value="提交修改" class="button" /></th>				
					<th><input type="button" name="Submit" value="取消修改" class="button" onclick="javascript:history.go(-1);"/></th>
					<th></th>
					<th></th>
				</tr>
			</table>
		</form>
		    <label></label>
		  </div>
</body>
</html>
