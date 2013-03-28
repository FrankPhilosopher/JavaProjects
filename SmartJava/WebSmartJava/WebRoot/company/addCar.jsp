<%@page import="entity.Distribution"%>
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
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<script type="text/javascript">
	//验证表单
	function checkForm() {
		if (document.getElementById("number").value == "") {
			document.getElementById("message").innerHTML = "车牌号码不能为空！";
			return;
		}
		if (document.getElementById("driver").value == "") {
			document.getElementById("message").innerHTML = "司机姓名不能为空！";
			return;
		}
		if (document.getElementById("weight").value == "") {
			document.getElementById("message").innerHTML = "承载重量不能为空！";
			return;
		}
		if (isNaN(document.getElementById("weight").value)) {
			document.getElementById("message").innerHTML = "承载重量必须为数字！";
			return;
		}

		if (document.getElementById("volumn").value == "") {
			document.getElementById("message").innerHTML = "承载体积不能为空！";
			return;
		}
		if (isNaN(document.getElementById("volumn").value)) {
			document.getElementById("message").innerHTML = "承载体积必须为数字！";
			return;
		}
		document.form1.submit();
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>增加车辆</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a href="carManager.jsp"
				target="_top">车辆管理</a>
		</div>
	</div>
	<form name="form1" method="post" action="actionCar.jsp?action=add">
		<div class="select-bar">
			<table class="select-table">
				<tr>
					<th class="first">车牌号码(*)</th>
					<td><input type="text" name="number" id="number" class="text" />
					</td>
					<th>司机姓名(*)</th>
					<td class="last"><input name="driver" id="driver" type="text"
						class="text" /></td>
				</tr>
				<tr>
					<th class="first">承载重量(*)</th>
					<td><input name="weight" id="weight" type="text" class="text" />
					</td>
					<th>承载体积(*)</th>
					<td class="last"><input id="volumn" name="volumn" type="text"
						class="text" /></td>
				</tr>
				<tr>
					<th class="first">当前配送点(*)</th>
					<td><select name="currentDistribution"
						id="currentDistribution" style="width:130px">
							<%
								List<Distribution> distributionList = (List<Distribution>) request
										.getAttribute("distributionList");
								for (int i = 0; i < distributionList.size(); i++) {
									Distribution distribution = distributionList.get(i);
									out.print("<option value='" + distribution.getId() + "'>"
											+ distribution.getName() + "</option>");
								}
							%>
					</select></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<th><input type="button" onclick="checkForm()" value="保 存"
						class="button" /></th>
					<th><label id="message" style="color: red; float: left;">
							<%=request.getAttribute("message")%> </label></th>
					<th></th>
					<th></th>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
