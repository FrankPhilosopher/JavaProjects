<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>配送点-物流管理系统-人员管理</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">@import "../style/all.css";</style>
	<script type="text/javascript" >
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
<body style="background: none">
			<div class="top-bar">
				<h1>新增员工</h1>
				<div class="breadcrumbs">
					<a href="<%=path%>/indexdistribution.jsp" target="_top">首页</a> / <a href="<%=path%>/distribution/userManager.jsp" target="_top">人员管理</a>
				</div>
			</div>
	<form name="form1" method="post" action="actionStaff.jsp?action=addStaff">
		  <div class="select-bar">
			<table class="select-table">
				<tr>
					<th>姓名(*)</th>
					<td><input type="text" name="realname" value="" class="text"/></td>
					<th>电话号码(*)</th>
					<td><input type="text" name="phone" value="" class="text"/></td>
				</tr>
				<tr>
					<th>登录名(*)</th>
					<td><input type="text" name="name" value="" class="text"/></td>
					<th>登录密码(*)</th>
					<td><input type="text" name="password" value="" class="text"/></td>
				</tr>
				<tr>
					<th><input type="button" name="submitbutton" value="提交信息" onclick="check()" class="button"/></th>
					<td><label name="message" id="error">
						<%
							String message = (String)request.getAttribute("message");
							if(message!=null){
								out.print(message);
							}
						 %>
						 </label></td>
					<th></th>
					<th></th>
				</tr>
			</table>
			</div>
	</form>
			
</body>
</html>
