<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%ArrayList list = (ArrayList)request.getAttribute("list"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>订单管理-客户-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">@import "../style/all.css";</style>
	
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>新增订单</h1>
		<div class="breadcrumbs">
			<a href="index.jsp" target="_top">首页</a> /
			<a href="index.jsp" target="_top">订单管理</a>
		</div>
	</div>
<div class="select-bar"/>
<form  action="action.jsp?action=addOrder" mothed="post">
		  <div class="select-bar">
			<table class="select-table">
				<tr>
					<th>收件人姓名(*)</th>
					<td><input type="text" name="receiver" class="text"/></td>
					<th>收件人地址(*)</th>
					<td>
						<select style="width:70px" name="tprovince">
							<option>广东</option>
							<option>湖南省</option>
						</select>
						<select style="width:70px" name="tcity">
							<option>广州</option>
							<option>长沙市</option>							
						</select>
						<select style="width:70px" name="tcountry">
							<option>越秀区</option>
							<option>岳麓区</option>							
						</select>
						<select style="width:70px" name="ttown">
							<option>白云镇</option>
							<option>雷锋镇</option>
						</select>
					</td>					
				</tr>
				<tr>
					<th>收件人电话号码(*)</th>
					<td><input type="text" name="tPhone"  class="text"/></td>
					<th>收件人邮编</th>
					<td><input type="text" name="tCode"  class="text"/></td>
				</tr>
				<tr>
					<th>寄件人姓名(*)</th>
					<td><input type="text" name="sender"  class="text"/></td>
					<th>寄件人地址(*)</th>
					<td>
						<select style="width:70px"  name="sprovince">
							<option>湖南省</option>
							<option>广东省</option>
						</select>
						<select style="width:70px" name="scity">

							<option>长沙市</option>
							<option>广州市</option>
						</select>
						<select style="width:70px" name="scoutry">
							<option>岳麓区</option>
							<option>越秀区</option>
						</select>
						<select style="width:70px" name="stown">
							<option>雷锋镇</option>
							<option>白云镇</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>寄件人电话号码(*)</th>
					<td><input type="text" name="sPhone"  class="text"/></td>
					<th>寄件人邮编</th>
					<td><input type="text" name="sCode"  class="text"/></td>
				</tr>
				<tr>
					<th>寄件重量(*)</th>
					<td><input type="text" name="weight"  class="text"/>(单位:KG)</td>
					<th>寄件体积(*)</th>
					<td><input type="text" name="volumn"  class="text"/>(单位:M3)</td>
				</tr>
				<tr>
					<th>寄件数量(*)</th>
					<td><input type="text" name="quantity"  class="text"/></td>
					</td>
					<th>寄件物品类别(*)</th>
					<td>
						<select style="width:70px" name="goodsType">
							<option value="1">易碎</option>
							<option value="2">危险</option>
							<option value="3">快件</option>
							<option value="4">普邮</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td><input type="text" name="remark"  class="text"/></td>
					<th></th>
					<th></th>
				</tr>				
				<tr>
					<th>价格在线计算</th>
					<td> </td>
				</tr>				
				<tr>
					<th><input type="button" name="Submit" value="提交订单" onclick="javascript:window.location.href='searchAdd.htmlss'" class="button"/></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</table>
			</div>
	</form>
<blockquote>
  <blockquote>
    <blockquote>
      <p>
        </div>
      </p>
    </blockquote>
  </blockquote>
</blockquote>
		<p>注:价格计算方法(单位:元)</p>
		<p>货物运送价格=货物运输费用+货物配送费用</p>
		<p>货物运输费用=货物首公斤价格+(货物重量-1)*货物次公斤价格(不足一公斤按一公斤算)</p>
</body>
</html>
