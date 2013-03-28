<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<title>中图电子科技有限公司</title>
		<link href="css/css.css" rel="stylesheet" type="text/css" />
		<style>
.navPoint {
	COLOR: white;
	CURSOR: hand;
	FONT-FAMILY: Webdings;
	FONT-SIZE: 9pt
}
</style>
		<script>
	function switchSysBar() {
		var osrc = document.all("img1").src;
		var ssrc = osrc.substring(osrc.indexOf("images"));
		if (ssrc == "images/main_55_1.gif") {
			document.all("img1").src = "images/main_55.gif";
			document.all("frmTitle").style.display = "";
		} else {
			document.all("img1").src = "images/main_55_1.gif";
			document.all("frmTitle").style.display = "none";
		}
	}
</script>
	</head>

	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" style="table-layout: fixed;">
			<tr>
				<td height="98" colspan="2">
					<iframe name="I3" height="98" width="100%" src="<%=path%>/a.jsp"
						border="0" frameborder="0" scrolling="no">
						浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。
					</iframe>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" height="100%" border="0" cellpadding="0"
						cellspacing="0" style="table-layout: fixed;">
						<tr>
							<td width="171" id=frmTitle noWrap name="fmTitle" align="center"
								valign="top">
								<table width="171" height="100%" border="0" cellpadding="0"
									cellspacing="0" style="table-layout: fixed;">
									<tr>
										<td bgcolor="#1873aa" width="6" style="width: 6px;">
											&nbsp;
										</td>
										<td width="165">
											<iframe name="I1" height="100%" width="165"
												src="<%=path%>/left<s:property value="#session.user.TOrgainzation.orgLevel"/>.jsp"
												border="0" frameborder="0" scrolling="no">
												浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。
											</iframe>
										</td>
									</tr>
								</table>
							</td>
							<td width="6" style="width: 6px;" valign="middle"
								bgcolor="1873aa" onclick=switchSysBar()>
								<SPAN class=navPoint id=switchPoint title=关闭/打开左栏><img
										src="images/main_55.gif" name="img1" width=6 height=40 id=img1>
								</SPAN>
							</td>
							<td align="center" valign="top">
								<iframe name="I2" height="100%" width="100%" border="0"
									frameborder="0" src="<s:property value="#session.url2"/> ">
									浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。
								</iframe>
							</td>
							<td bgcolor="#1873aa" width="6" style="width: 6px;">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="6" colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="6" background="images/main_59.gif"
								style="line-height: 6px;">
								<img src="images/main_59.gif" width="6" height="6">
							</td>
							<td background="images/main_61.gif" style="line-height: 6px;">
								&nbsp;
							</td>
							<td width="6" background="images/main_61.gif"
								style="line-height: 6px;">
								<img src="images/main_62.gif" width="6" height="6">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

