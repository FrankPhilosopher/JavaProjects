<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>人员管理-省公司-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<script type="text/javascript">
	//验证表单
	function checkForm() {
		if (document.getElementById("name").value == "") {
			document.getElementById("message").innerHTML = "员工账号不能为空！";
			return;
		}
		if (document.getElementById("password").value == "") {
			document.getElementById("message").innerHTML = "员工密码不能为空！";
			return;
		}
		if (document.getElementById("realname").value == "") {
			document.getElementById("message").innerHTML = "真实姓名不能为空！";
			return;
		}
		if (document.getElementById("phone").value == "") {
			document.getElementById("message").innerHTML = "联系电话不能为空！";
			return;
		}
		document.form1.submit();
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>修改员工</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a href="userManager.jsp"
				target="_top">人员管理</a>
		</div>
	</div>
	<div class="select-bar" />
	<form name="form1" method="post"
		action="actionUser.jsp?action=saveUser">
		<div class="table">
			<table class="select-table">
				<tr>
					<th class="first">员工账号(*)</th>
					<td><input type="text" name="name" id="name"
						value="<%=((User) request.getAttribute("user")).getName()%>"
						class="text" />
					</td>
					<th>员工密码(*)</th>
					<td class="last"><input
						value="<%=((User) request.getAttribute("user")).getPassword()%>"
						name="password" id="password" type="text" class="text" />
					</td>
				</tr>
				<tr>
					<th class="first">真实姓名(*)</th>
					<td><input name="realname" id="realname"
						value="<%=((User) request.getAttribute("user")).getRealname()%>"
						type="text" class="text" />
					</td>
					<th>联系电话(*)</th>
					<td class="last"><input name="phone" id="phone"
						value="<%=((User) request.getAttribute("user")).getPhone()%>"
						type="text" class="text" />
					</td>
				</tr>
				<tr>
					<th class="full"><input type="button" class="button"
						onclick="checkForm()" value="保  存" style="cursor:pointer;" /> <input
						type="button" class="button"
						onclick="window.location.href='actionUser.jsp?action=forwardListUser&pageIndex=1'"
						value="返 回" style="cursor:pointer;" /></th>
					<th><label id="message" style="color: red; float: left;">
							<%=request.getAttribute("message")%> </label></th>
					<th><input name="id"
						value="<%=((User) request.getAttribute("user")).getId()%>"
						type="text" class="text" style="display:none" />
					</th>
					<th></th>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
