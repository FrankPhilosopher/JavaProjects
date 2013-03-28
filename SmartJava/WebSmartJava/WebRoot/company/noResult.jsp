<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配送点管理-省公司-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<style media="all" type="text/css">
@import "../style/excel.css";
</style>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>配送点列表</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a
				href="distributionManager.jsp" target="_top">配送点管理</a>
		</div>
	</div>
	<form name="frm1" method="post" action="actionDistribution.jsp?action=search">
		<div class="select-bar" />
		<table class="select-table">
			<tr>
				<th colspan="4" class="full"><div class="search_table_row">
						按 <select style="width:130px" name="type" id="type"
							onchange="changeType();">
							<option value="0" selected="selected">配送点名称</option>
							<option value="1">配送点ID</option>
						</select> 查询 &nbsp; <input type="text" id="year" name="year" class="text" />
						<input type="submit" class="button" width="200" onclick=""
							value="查 询" />
					</div></th>
			</tr>
		</table>
		<div style="margin-left:300px; margin-top:50px; font-size:20px;"><a href="listDistribution.jsp"><img src="../style/img/back.gif"/><span>返回</span></a></div>
		</div>
		<script type="text/javascript">
	    alert("无此纪录！");
</script>
</body>
</html>



