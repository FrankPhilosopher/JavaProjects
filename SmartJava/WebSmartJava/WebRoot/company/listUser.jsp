<%@page import="manager.UserManager"%>
<%@page import="entity.User"%>
<%@page import="java.util.List"%>
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
<title>车辆管理-省公司-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<style media="all" type="text/css">
@import "../style/excel.css";
</style>
<script type="text/javascript">
	function deleteUser() {
		if (confirm("您确定要删除该工作人员信息吗？")) {
			return true;
		}
		return false;
	}
	//验证表单
	function checkForm() {
		if (document.getElementById("searchKey").value == "") {
			alert("查询内容不能为空！");
			return;
		}
        document.form1.submit();
	}
	function checkForm3() {
	    if (document.getElementById("pageIndex").value == "") {
			alert("请输入页码！");
			return;
		}
		
		var myReg = /^[1-9]\d?$/;
    	if (!myReg.test(document.getElementById("pageIndex").value)) {
		    alert("页码请输入正整数");
			return;
    	}
        document.form3.submit();
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>人员列表</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a
				href="userManager.jsp" target="_top">人员管理</a>
		</div>
	</div>
	<form name="form1" method="post" action="actionUser.jsp?action=search">
		<div class="select-bar">
			<table class="select-table">
				<tr>
					<th colspan="4" class="full"><div class="search_table_row">
							按 <select style="width:130px" name="select" id="type"
								onchange="changeType();">
								<option value="0" selected="selected">员工账号</option>
								<option value="1">员工姓名</option>
							</select> 查询 &nbsp; <input type="text" id="searchKey" name="searchKey"
								class="text" /> <input type="button" class="button"
								onclick="checkForm()" value="查 询" />
						</div></th>
				</tr>
			</table>
		</div>
	</form>
	<div class="table">
		<img src="../style/img/bg-th-left.gif" width="8" height="7" alt=""
			class="left" /> <img src="../style/img/bg-th-right.gif" width="7"
			height="7" alt="" class="right" />
		<table class="listing form" cellpadding="0" cellspacing="0">
			<tr>
				<th class="first">员工账号</th>
				<th>员工密码</th>
				<th>员工姓名</th>
				<th>联系电话</th>
				<th class="last">操作</th>
			</tr>

			<%
				List<User> userlist = (List<User>) request.getAttribute("userList");
				for (int i = 0; i < userlist.size(); i++) {
					User user = userlist.get(i);
					out.print("<tr>");
					out.print("<td>" + user.getName() + "</td>");
					out.print("<td>" + user.getPassword() + "</td>");
					out.print("<td>" + user.getRealname() + "</td>");
					out.print("<td>" + user.getPhone() + "</td>");
					out.print("<td>"
							+ "<a href='actionUser.jsp?action=updateUser&id="
							+ user.getId()
							+ "' onclick=''  class='table_op_a'>"
							+ "<img src='../style/img/edit-icon.gif' width='16' height='16' />修改</a>"

							+ "<a href='actionUser.jsp?action=deleteUser&id="
							+ user.getId()
							+ "' onclick='return deleteUser();' class='table_op_a' >"
							+ "<img src='../style/img/hr.gif' width='16' height='16' />删除</a>"
							+ "</td>");
				}
			%>

		</table>
		<form name="form3" method="post"
			action="actionUser.jsp?action=forwardListUser">
			<div class="pager">
				共
				<%=request.getAttribute("count")%>
				条记录 &nbsp;&nbsp; 每页10条&nbsp;&nbsp; 第
				<%=request.getAttribute("pageIndex")%>页&nbsp;/&nbsp;共
				<%=request.getAttribute("pages")%>
				页 &nbsp;&nbsp; <a
					href="actionUser.jsp?action=forwardListUser&pageIndex=1">首页</a>
				&nbsp;<a
					href="actionUser.jsp?action=forwardListUser&pageIndex=<%=(Integer) request.getAttribute("pageIndex") - 1%>">上一页</a>&nbsp;
				<a
					href="actionUser.jsp?action=forwardListUser&pageIndex=<%=(Integer) request.getAttribute("pageIndex") + 1%>">下一页</a>&nbsp;
				<a
					href="actionUser.jsp?action=forwardListUser&pageIndex=<%=(Integer) request.getAttribute("pages")%>">尾页
				</a>&nbsp; 转到 <input type="text" id="pageIndex" name="pageIndex"
					class="text" size="1" value="" /> 页 <input type="button"
					class="button" onclick="checkForm3()" value="GO" />
			</div>
		</form>
	</div>
</body>
</html>
