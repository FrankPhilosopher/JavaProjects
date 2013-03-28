<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entity.*"%>
<%
	User loginuser = (User) session.getAttribute("loginuser");
	Distribution currentDistribution = (Distribution) session
			.getAttribute("currentDistribution");
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title>货物管理-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<script type="text/javascript" src="../script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--
	function reinitIframe() {
		var iframe = document.getElementById("frame_content");
		try {
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.style.height = height;

		} catch (ex) {
		}
	}
	$(function() {
		window.setInterval("reinitIframe()", 200);
		$("#left-column h3").bind("click", function() {
			var t = $(this);
			t.toggleClass('link');
			t.next().toggle();
		});
	});
//-->
</script>
</head>
<body>
	<div id="main">
		<div id="header">
			<a href="<%=path%>/distribution/indexdistribution.jsp" class="logo"><img src="../style/img/logo.png"
				width="200" height="29" alt="" />
			</a>
			<ul id="top-navigation">
				<li><span><span><a
							href="<%=path%>/distribution/indexdistribution.jsp">首页</a>
					</span>
				</span>
				</li>
				<li><span><span><a href="<%=path%>/distribution/placepriceManager.jsp">配送范围及价格管理</a>
					</span>
				</span>
				</li>
				<li><span><span><a href="<%=path%>/distribution/orderManager.jsp">订单管理</a>
					</span>
				</span>
				</li>
				<li><span><span><a href="<%=path%>/distribution/vehicleManage.jsp">车辆管理</a>
					</span>
				</span>
				</li>
				<li class="active"><span><span><a
							href="<%=path%>/distribution/goodsManage.jsp">货物管理</a>
					</span>
				</span>
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
				<span> <%
 	if (loginuser.getPriority() == User.DISTRIBUTION_ADMIN) {
 		out.print("管理员");
 	} else {
 		out.print("工作人员");
 	}
 	out.print("&nbsp" + loginuser.getName() + "登录中");
 %> &nbsp;</span>&nbsp;<a href="<%=path%>/login.jsp">退出</a>
			</div>
		</div>
		<div id="middle">
			<div id="left-column">
				<h3>卸货管理</h3>
				<ul class="nav">
					<li><a href="actionGoods.jsp?action=unload"
						target="center-frame">车辆卸货</a>
					</li>
				</ul>
				<h3>装货管理</h3>
				<ul class="nav">
					<li><a href="actionGoods.jsp?action=distribute"
						target="center-frame">装货配送</a>
					</li>
				</ul>

			</div>
			<div id="center-column">
				<iframe id="frame_content" name="center-frame"
					src="actionGoods.jsp?action=unload" frameborder="0" scrolling="no"
					allowTransparency="true"
					style="width:100%;height:500px;margin:0 0;padding:0 0;"></iframe>
			</div>
		</div>
		<div id="footer"></div>
	</div>
	<div id="copyright">Copyright &copy;2012 中软实训第六小组</div>
</body>
</html>