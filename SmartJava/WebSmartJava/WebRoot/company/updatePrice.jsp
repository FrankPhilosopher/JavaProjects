<%@page import="entity.DistributionPrice"%>
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
<title>配送点间价格修改-省公司- 物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";

@import "../style/jquery/ui.all.css";
</style>
<script type="text/javascript" src="../script/jquery-1.3.2.min.js"></script>
<script type="text/javascript"
	src="../script/jquery-ui-personalized-1.6rc6.min.js"></script>
<script type="text/javascript" src="../script/date.js"></script>
<script type="text/javascript" src='../script/common.js'></script>
<script type="text/javascript">
	//验证表单
	function checkForm() {
		if (document.getElementById("firstKilo").value == "") {
			document.getElementById("message").innerHTML = "首公斤价格不能为空！";
			return;
		}
		if (isNaN(document.getElementById("firstKilo").value)) {
			document.getElementById("message").innerHTML = "首公斤价格必须为数字！";
			return;
		}
		if (document.getElementById("secondKilo").value == "") {
			document.getElementById("message").innerHTML = "次公斤价格不能为空！";
			return;
		}
		if (isNaN(document.getElementById("secondKilo").value)) {
			document.getElementById("message").innerHTML = "次公斤价格必须为数字！";
			return;
		}
		document.form1.submit();
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>修改配送点间价格</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a href="priceManager.jsp"
				target="_top">价格管理</a>
		</div>
	</div>
	<form name="form1" method="post" action="actionPrice.jsp?action=save">
		<div class="select-bar">
			<table class="select-table">
				<tr>
					<th>起始配送点</th>
					<td align="center"><input
						value="<%=request.getAttribute("sDistribution")%>" type="text"
						name="sDistribution" id="sDistribution" class="text"
						disabled="disabled" /></td>

					<th>终点配送点</th>
					<td align="center"><input
						value="<%=request.getAttribute("tDistribution")%>" type="text"
						name="tDistribution" id="tDistribution" class="text"
						disabled="disabled" /></td>
				</tr>
				<tr>
					<th>首公斤价格</th>
					<td align="center"><input
						value="<%=request.getAttribute("firstKilo")%>" type="text"
						name="firstKilo" id="firstKilo" class="text" /></td>
					<th>次公斤价格</th>
					<td align="center"><input
						value="<%=request.getAttribute("secondKilo")%>" type="text"
						name="secondKilo" id="secondKilo" class="text" /></td>
				</tr>
				<tr>
					<th><input type="button" onclick="checkForm()" value="保 存"
						class="button" /><input type="button" class="button"
						onclick="window.location.href='actionPrice.jsp?action=forwardList&pageIndex=1'"
						value="返 回" style="cursor:pointer;" /></th>
					<th><label id="message" style="color: red; float: left;">
							<%=request.getAttribute("message")%> </label></th>
					<th><input name="id" value="<%=request.getAttribute("id")%>"
						type="text" class="text" style="display:none" /></th>
					<th></th>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>
