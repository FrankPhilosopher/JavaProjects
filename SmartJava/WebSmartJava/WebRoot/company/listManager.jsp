<%@page import="manager.DistributionManager"%>
<%@page import="entity.Distribution"%>
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
    function deleteManager() {
	    if (confirm("您确定要删除该工作人员信息吗？")) {
		return true;
	 }
	 return false;
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
		<h1>管理员列表</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a href="userManager.jsp"
				target="_top">人员管理</a>
		</div>
	</div>


	<form name="form1" method="post"
		action="actionUser.jsp?action=searchManager">
		<div class="select-bar">
			<table class="select-table">
				<tr>
					<th>请选择配送点</th>
					<td><select style="width:130px" name="distribution">
							<%
								List<Distribution> distributionList = (List<Distribution>) request
										.getAttribute("distributionList");
								for (int i = 0; i < distributionList.size(); i++) {
									Distribution distribution = distributionList.get(i);
									out.print("<option value='" + distribution.getId() + "'>"
											+ distribution.getName() + "</option>");
								}
							%>
					</select>
					</td>
					<td><input type="button" class="button"
						onclick="form1.submit();" value="点击查询" />
					</td>
					<td></td>
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
				<th class="first">管理员账号</th>
				<th>管理员密码</th>
				<th>管理员姓名</th>
				<th>联系电话</th>
				<th>所在配送点</th>
				<th class="last">操作</th>
			</tr>

			<%
				List<User> managerList = (List<User>) request
						.getAttribute("managerList");
				for (int i = 0; i < managerList.size(); i++) {
					User user = managerList.get(i);
					out.print("<tr>");
					out.print("<td>" + user.getName() + "</td>");
					out.print("<td>" + user.getPassword() + "</td>");
					out.print("<td>" + user.getRealname() + "</td>");
					out.print("<td>" + user.getPhone() + "</td>");
					out.print("<td>");
					out.print(DistributionManager.searchById(
							user.getDistributionId()).getName());
					out.print("</td>");
					out.print("<td>"
							+ "<a href='actionUser.jsp?action=updateManager&id="
							+ user.getId()
							+ "' onclick=''  class='table_op_a'>"
							+ "<img src='../style/img/edit-icon.gif' width='16' height='16' />修改</a>"

							+ "<a href='actionUser.jsp?action=deleteManager&id="
							+ user.getId()
							+ "' onclick='return deleteManager();' class='table_op_a' >"
							+ "<img src='../style/img/hr.gif' width='16' height='16' />删除</a>"
							+ "</td>");
				}
			%>
		</table>
		<form name="form3" method="post"
			action="actionUser.jsp?action=forwardListManager">
		<div class="pager">
			共
			<%=request.getAttribute("count")%>
			条记录 &nbsp;&nbsp; 每页10条&nbsp;&nbsp; 第
			<%=request.getAttribute("pageIndex")%>页&nbsp;/&nbsp;共
			<%=request.getAttribute("pages")%>
			页 &nbsp;&nbsp; <a href="actionUser.jsp?action=forwardListManager&pageIndex=1">首页</a>
			&nbsp;<a
				href="actionUser.jsp?action=forwardListManager&pageIndex=<%=(Integer) request.getAttribute("pageIndex") - 1%>">上一页</a>&nbsp;
			<a
				href="actionUser.jsp?action=forwardListManager&pageIndex=<%=(Integer) request.getAttribute("pageIndex") + 1%>">下一页</a>&nbsp;
			<a
				href="actionUser.jsp?action=forwardListManager&pageIndex=<%=(Integer) request.getAttribute("pages")%>">尾页
			</a>&nbsp; 转到 <input type="text" id="pageIndex" name="pageIndex" class="text" size="1" value="" /> 页 <input
				type="button" class="button" onclick="checkForm3()" value="GO" />
		</div>
		</form>
	</div>
</body>
</html>
