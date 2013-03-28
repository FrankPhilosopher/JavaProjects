<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%
	String path = request.getContextPath();
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
		function check(){
			if(form1.realname.value.length==0 || form1.phone.value.length==0 || form1.name.value.length==0 || form1.password.value.length==0){
				document.getElementById("error").innerHTML = "亲，你还没有输入完呢！";
				return false;
			}else{
				form1.submit();	
			}
		}
	</script>
</head>

<%
	User user = (User)request.getAttribute("editUser");
 %>

<body style="background: none">
	<div class="top-bar">
				<h1>修改员工信息</h1>
				<div class="breadcrumbs">
					<a href="<%=path%>/indexdistribution.jsp" target="_top">首页</a> / <a href="<%=path%>/distribution/userManager.jsp" target="_top">人员管理</a>
				</div>
			</div>
	<form name="form1" method="post" action="actionStaff.jsp?action=update">
		  <input type="hidden" name="id" value="<%=user.getId()%>"/>
		  <div class="select-bar">
			<table class="select-table">
				<tr>
					<th>员工ID</th>
					<td><%=user.getId() %></td>
					<th>姓名</th>
					<td><input type="text" name="realname" value="<%=user.getRealname() %>" class="text"/></td>	
				</tr>
				<tr>
					<th>输入新登录名(*)</th>
					<td><input type="text" name="name" value="<%=user.getName() %>" class="text"/></td>				
					<th>输入新登录密码(*)</th>
					<td><input type="text" name="password" value="<%=user.getPassword() %>" class="text"/></td>	
				</tr>
				<tr>
		
					<th>输入新手机号码(*)</th>
					<td><input type="text" name="phone" value="<%=user.getPhone() %>" class="text"/></td>	
					<th></th>
					<th></th>
				</tr>
				<tr>				
					<th><input type="button" name="submitbutton" onclick="check()" value="修改员工信息" class="button"/></th>				
					<td><label name="message" id="error">
						<%
							String message = (String)request.getAttribute("message");
							if(message!=null){
								out.print(message);
							}
						 %></label></td>
					<th></th>
					<th></th>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
