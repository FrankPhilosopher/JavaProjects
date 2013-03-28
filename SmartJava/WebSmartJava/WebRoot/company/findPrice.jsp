<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@page import="manager.DistributionPriceManager"%>
<%@page import="entity.DistributionPrice"%>
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
<title>配送点间价格列表 - 物流管理系统</title>
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
	function deletePrice() {
		if (confirm("您确定要删除该价格吗？")) {
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
		<h1>查询配送点间价格</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a
				href="priceManager.jsp" target="_top">价格管理</a>
		</div>
	</div>
	<form name="form1" method="post" action="actionPrice.jsp?action=search">
		<div class="select-bar">
			<table class="select-table">
				<tr>
					<th>选择起始配送点</th>
					<td><select style="width:130px" name="sDistribution">
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
					<th>选择终点配送点</th>
					<td><select style="width:130px" name="tDistribution">
							<%
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
					<th><input type="button" class="button"
						onclick="form1.submit();" value="点击查询" />
					</th>
					<td></td>
					<td></td>
					<td></td>
				</tr>

			</table>
		</div>

	</form>
	<div class="table">
		<img src="../style/img/bg-th-left.gif" width="8" height="7" alt=""
			class="left" /> <img src="../style/img/bg-th-right.gif" width="7"
			height="7" alt="" class="right" />
		<table class="listing" cellpadding="0" cellspacing="0">
			<tr>
				<th>配送点价格编号</th>
				<th>起始配送点名称</th>
				<th>终点配送点名称</th>
				<th>首公斤价格</th>
				<th>次公斤价格</th>
				<th>操作</th>
			</tr>

			<%
				List<DistributionPrice> priceList = (List<DistributionPrice>) request
						.getAttribute("priceList");
				for (int i = 0; i < priceList.size(); i++) {
					DistributionPrice dPrice = priceList.get(i);
					out.print("<tr>");
					out.print("<td>" + dPrice.getId() + "</td>");

					out.print("<td>");
					out.print(DistributionManager.searchById(
							dPrice.getsDistribution()).getName());
					out.print("</td>");

					out.print("<td>");
					out.print(DistributionManager.searchById(
							dPrice.getDistribution()).getName());
					out.print("</td>");

					out.print("<td>" + dPrice.getFirstKilo() + "</td>");
					out.print("<td>" + dPrice.getSecondKilo() + "</td>");
					out.print("<td>"
							+ "<a href='actionPrice.jsp?action=update&id="
							+ dPrice.getId()
							+ "' onclick=''  class='table_op_a'>"
							+ "<img src='../style/img/edit-icon.gif' width='16' height='16' />修改</a>"

							+ "<a href='actionPrice.jsp?action=delete&id="
							+ dPrice.getId()
							+ "' onclick='return deletePrice();' class='table_op_a' >"
							+ "<img src='../style/img/hr.gif' width='16' height='16' />删除</a>"
							+ "</td>");
				}
			%>

		</table>
		<form name="form3" method="post"
			action="actionPrice.jsp?action=forwardList">
			<div class="pager">
				共
				<%=request.getAttribute("count")%>
				条记录 &nbsp;&nbsp; 每页10条&nbsp;&nbsp; 第
				<%=request.getAttribute("pageIndex")%>页&nbsp;/&nbsp;共
				<%=request.getAttribute("pages")%>
				页 &nbsp;&nbsp; <a
					href="actionPrice.jsp?action=forwardList&pageIndex=1">首页</a> &nbsp;<a
					href="actionPrice.jsp?action=forwardList&pageIndex=<%=(Integer) request.getAttribute("pageIndex") - 1%>">上一页</a>&nbsp;
				<a
					href="actionPrice.jsp?action=forwardList&pageIndex=<%=(Integer) request.getAttribute("pageIndex") + 1%>">下一页</a>&nbsp;
				<a
					href="actionPrice.jsp?action=forwardList&pageIndex=<%=(Integer) request.getAttribute("pages")%>">尾页
				</a>&nbsp; 转到 <input type="text" id="pageIndex" name="pageIndex"
					class="text" size="1" value="" /> 页 <input type="button"
					class="button" onclick="checkForm3()" value="GO" />
			</div>
		</form>
	</div>
</body>
</html>
