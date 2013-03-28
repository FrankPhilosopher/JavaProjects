<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>报表管理-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<style media="all" type="text/css">
@import "../style/excel.css";
</style>
<script language="javascript" type="text/javascript">
	function changeType() {
		var selectTag = document.getElementById("type");
		var value = selectTag.value;
		var yearSpan = document.getElementById("yearSpan");
		var seasonSpan = document.getElementById("seasonSpan");
		var monthSpan = document.getElementById("monthSpan");

		if (value == 0) {
			yearSpan.style.display = 'block';
			seasonSpan.style.display = 'none';
			monthSpan.style.display = 'none';
		} else if (value == 1) {
			yearSpan.style.display = 'block';
			seasonSpan.style.display = 'block';
			monthSpan.style.display = 'none';
		} else if (value == 2) {
			yearSpan.style.display = "block";
			seasonSpan.style.display = "none";
			monthSpan.style.display = "block";
		}
	}

	function search() {
		form.action = "actionExcel.jsp?action=search";
		form.submit();
	}
	function excel() {
		form.action = "actionExcel.jsp?action=excel";
		form.submit();
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>报表管理 </h1>
		<div class="breadcrumbs">
			<a href="<%=path%>/indexcompany.jsp" target="_top">首页</a> / <a
				href="<%=path%>/company/excelManager.jsp" target="_top">报表管理</a>
		</div>
	</div>
	<form name="form" method="post" action="actionExcel.jsp?action=search">
		<div class="select-bar" />
		<table class="select-table">
			<tr>
				<th>报表类型</th>
				<td><select style="width:130px" name="type" id="type"
					onchange="changeType();">
						<option value="0">按年</option>
						<option value="1">按季度</option>
						<option value="2">按月</option>
				</select>
				</td>
				<th>详细时间</th>
				<td><span id="yearSpan"> <input type="text" id="year"
						name="year" class="text" /> &nbsp;年</span> <span id="seasonSpan"
					style="display:none;"> <select style="width:130px"
						name="season" id="season">
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
					</select> &nbsp;季度 </span> <span id="monthSpan" style="display:none;"> <select
						style="width:130px" name="month" id="month">
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> &nbsp;月 </span>
				</td>
			</tr>
			<tr>
				<th colspan="4" style="text-align:left;vertical-align:middle;">
					<div class="selectbar_button_div">
						<input type="button" class="button" width="200"
							onclick="search();" value="查 询" /> 
						<input type="button" class="button" width="200" 
							onclick="excel();" value="导 出" />
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
				<th class="first">配送点名称</th>
				<th>配送货物总重量</th>
				<th>配送货物总体积</th>
				<th class="last">配送收入（RMB）</th>
			</tr>

			<c:forEach items="${list}" var="item">
				<tr>
					<td>${item.name }</td>
					<td>${item.weight }</td>
					<td>${item.volumn }</td>
					<td>${item.price }</td>
				</tr>
			</c:forEach>

		</table>

	</div>
</body>
</html>
