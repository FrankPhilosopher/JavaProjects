<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entity.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User loginuser = (User) session.getAttribute("loginuser");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配送点-报表管理-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<script type="text/javascript" src="../script/jquery-1.3.2.min.js"></script>
</head>
<body>
	<div id="main">
		<div id="header">
			<a href="<%=path%>/indexdistribution.jsp" class="logo"><img
				src="../style/img/logo.png" width="200" height="29" alt="" /> </a>
			<ul id="top-navigation">
				<li><span><span><a
							href="<%=path%>/indexdistribution.jsp">首页</a> </span> </span>
				</li>
				<li><span><span><a
							href="<%=path%>/distribution/placepriceManager.jsp">配送范围及价格管理</a>
					</span> </span>
				</li>
				<li><span><span><a
							href="<%=path%>/distribution/orderManager.jsp">订单管理</a> </span> </span>
				</li>
				<li><span><span><a
							href="<%=path%>/distribution/vehicleManage.jsp">车辆管理</a> </span> </span>
				</li>
				<li><span><span><a
							href="<%=path%>/distribution/goodsManage.jsp">货物管理</a> </span> </span>
				</li>
				<%
					if (loginuser.getPriority() == 2) { //如果用户为配送点管理员
						out.print("<li><span><span><a href='");
				%>
				<%=path%>
				<%
					out.print("/distribution/userManager.jsp'>人员管理</a></span></span></li><li><span><span><a href='");
				%>
				<%=path%>
				<%
					out.print("/distribution/excelManager.jsp'>报表管理</a></span></span></li>");
					}
				%>
			</ul>
			<div id="operator" class="active">
				<%
					if (loginuser.getPriority() == User.DISTRIBUTION_ADMIN) {
						out.print("管理员");
					} else {
						out.print("工作人员");
					}
				%>&nbsp;<span> <%
 	out.print(loginuser.getName() + "登录中");
 %> </span>&nbsp;<a href="<%=path%>/login.jsp">退出</a>
			</div>
		</div>
		<div id="middle">
			<div id="left-column">
				<h3>报表管理</h3>
				<ul class="nav">
					<li><a href="<%=path%>/distribution/excel.jsp"
						target="center-frame">生成报表</a>
					</li>
				</ul>
			</div>
			<div id="center-column">
				<iframe id="center-frame" name="center-frame"
					src="<%=path%>/distribution/excel.jsp" frameborder="0"
					scrolling="no" allowTransparency="true"
					style="width:100%;height:500px;margin:0 0;padding:0 0;"></iframe>
			</div>
		</div>
		<div id="footer"></div>
	</div>
	<div id="copyright">Copyright &copy;2012 中软实训第六小组</div>
</body>
</html>