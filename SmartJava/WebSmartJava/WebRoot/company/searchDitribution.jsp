<%@page import="entity.Distribution"%>
<%@page import="entity.User"%>
<%@page import="manager.UserManager"%>
<%@page import="manager.DistributionManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
  ArrayList list1=(ArrayList) request.getAttribute("list1");
  ArrayList list2=(ArrayList) request.getAttribute("list2");
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
<script type="text/javascript">
	function deleteOrder() {
		if (confirm("您确定要删除该配送点吗？")) {
			alert("是的");
		}
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>配送点列表</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a
				href="distributionManager.html" target="_top">配送点管理</a>
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
		</div>
	</form>
	<div class="table">
		<img src="../style/img/bg-th-left.gif" width="8" height="7" alt=""
			class="left" /> <img src="../style/img/bg-th-right.gif" width="7"
			height="7" alt="" class="right" />
		<table class="listing form" cellpadding="0" cellspacing="0">
			<tr>
				<th>配送点ID</th>
				<th>配送点名称</th>
				<th>配送点地址</th>
				<th>管理员账号</th>
				<th>管理员密码</th>
				<th>联系人姓名</th>
				<th>联系人电话</th>
				<th>操作</th>
			</tr>
			<% 
			for(int i=0;i<list1.size();i++)
			{
			    Distribution db=(Distribution) list1.get(i);
			    User us=(User) list2.get(i);
			    out.print("<tr>");
				out.print("<td>"+db.getId()+"</td>");
				out.print("<td>"+db.getName()+"</td>");
				out.print("<td>"+db.getAddress()+"</td>");
				out.print("<td>"+us.getName()+"</td>");
				out.print("<td>"+us.getPassword()+"</td>");
				out.print("<td>"+us.getRealname()+"</td>");
				out.print("<td>"+us.getPhone()+"</td>");
				out.print("<td><a href='Distribution_point_update.html' class='table_op_a'><img src='../style/img/edit-icon.gif' onclick='' width='16' height='16' alt='' />修改</a><a class='table_op_a' onclick='deleteOrder();'><img src='../style/img/hr.gif' width='16' height='16' alt='' />删除</a></td> ");
			    out.print("</tr>");
			  }
			%>
		</table>
		<!-- <div class="pager">
			共 59 条记录 每页 <input type="text" class="text" size="2" value="10" /> 条
			第1页/共5页 <a href="">第一页</a> <a href="">上一页</a> <a href="">下一页</a> <a
				href="">最后一页</a> 转到 <input type="text" class="text" size="1"
				value="1" /> 页 <input type="button" class="button" onclick=""
				value="GO" />
		</div> -->
	</div>
</body>
</html>
