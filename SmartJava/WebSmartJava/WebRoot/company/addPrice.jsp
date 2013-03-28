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
<title>新增价格 - 物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
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
		<h1>新增配送点间价格</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a href="priceManager.jsp"
				target="_top">价格管理</a>
		</div>
	</div>
	<form name="form1" method="post" action="actionPrice.jsp?action=add">
		<div class="select-bar">
			<table class="select-table">
				<tr>
					<th>选择起始配送点</th>
					<td><select name="sDistribution" id="sDistribution"
						style="width:130px">
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
					<th>选择终点配送点</th>
					<td><select name="tDistribution" id="tDistribution"
						style="width:130px">
							<%
								for (int i = 0; i < distributionList.size(); i++) {
									Distribution distribution = distributionList.get(i);
									out.print("<option value='" + distribution.getId() + "'>"
											+ distribution.getName() + "</option>");
								}
							%>
					</select>
					</td>

				</tr>
				<tr>
					<th>首公斤价格</th>
					<td><input type="text" name="firstKilo" id="firstKilo"
						class="text" /> 元</td>
					<th>次公斤价格</th>
					<td><input type="text" name="secondKilo" id="secondKilo"
						class="text" /> 元</td>
				</tr>
				<tr>
					<th><input type="button" onclick="checkForm()" value="保 存"
						class="button" /><input type="button" class="button"
						onclick="window.location.href='actionPrice.jsp?action=forwardList&pageIndex=1'"
						value="查  看" style="cursor:pointer;" /></th>
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
