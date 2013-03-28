<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="entity.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
</head>

<%
	User loginUser = (User) session.getAttribute("loginuser");
%>

<body>
	<div id="main">

		<div id="header">
			<a href="../indexcompany.jsp" class="logo"><img
				src="../style/img/logo.png" width="200" height="29" alt="" /> </a>
			<ul id="top-navigation">
				<li><span><span><a
							href="<%=path%>/indexcompany.jsp">首页</a> </span> </span></li>
				<li><span><span><a
							href="<%=path%>/company/distributionManager.jsp">配送点管理</a> </span> </span></li>
				<li><span><span><a
							href="<%=path%>/company/routeManager.jsp">路线管理</a> </span> </span></li>
				<li><span><span><a
							href="<%=path%>/company/carManager.jsp">车辆管理</a> </span> </span></li>
				<li><span><span><a
							href="<%=path%>/company/priceManager.jsp">价格管理</a> </span> </span></li>
				<%
					if (loginUser.getPriority() == 0) {
				%>
				<li class="active"><span><span><a
							href="<%=path%>/company/userManager.jsp">人员管理</a>
					</span>
				</span>
				</li>
				<li><span><span><a
							href="<%=path%>/company/excelManager.jsp">报表管理</a>
					</span>
				</span>
				</li>
				<%
					}
				%>
			</ul>
			<div id="operator" class="active">
				<span>省公司&nbsp;&nbsp;<%=loginUser.getName()%></span><a
					href="<%=path%>/login.jsp">退出</a>
			</div>
		</div>

		<div id="middle">
			<div id="left-column">
				<h3>省公司员工管理</h3>
				<ul class="nav">
					<li><a href="actionUser.jsp?action=forwardAddUser"
						target="center-frame">新增员工</a>
					</li>
					<li><a
						href="actionUser.jsp?action=forwardListUser&pageIndex=1"
						target="center-frame">员工列表</a>
					</li>
				</ul>
				<h3>配送点管理员管理</h3>
				<ul class="nav">
					<li><a href="actionUser.jsp?action=forwardAddManager"
						target="center-frame">新增管理员</a>
					</li>
					<li><a
						href="actionUser.jsp?action=forwardListManager&pageIndex=1"
						target="center-frame">管理员列表</a>
					</li>
				</ul>

			</div>
			<div id="center-column">
				<iframe id="frame_content" name="center-frame"
					src="actionUser.jsp?action=forwardAddUser" frameborder="0"
					scrolling="no"
					style="width:100%;height:500px;margin:0 0;padding:0 0;"></iframe>
			</div>

		</div>
		<div id="footer"></div>
	</div>
	<div id="copyright">Copyright &copy;2012 中软实训第六小组</div>

</body>
</html>
