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
		<title>top</title>
		<link href="css/css.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
	color: #FFFFFF;
}

.STYLE2 {
	font-size: 9px
}

.STYLE3 {
	color: #033d61;
	font-size: 12px;
}

a {
	text-decoration: none;
	color: #033d61
}
</style>
		<script type="text/javascript">
	function back() {
		history.go(-1); //����1ҳ   
	}

	function forward() {
		history.go(+1); //ǰ��1ҳ   
	}

	function refresh() {
		history.go(-0); //ˢ��
	}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="70" background="images/main_05.gif">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="24">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="270" height="24" background="images/main_03.gif">
											&nbsp;
										</td>
										<td width="505" background="images/main_04.gif">
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td width="21">
											<img src="images/main_07.gif" width="27" height="24">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="38">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="270" height="38" background="images/main.jpg">
											&nbsp;
										</td>
										<td>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="77%" height="25" valign="bottom">
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="50" height="19">
																	<div align="center">
																		<a href="center.jsp" target="I2"><img
																				src="images/main_12.gif" width="49" height="19"
																				border="0"> </a>
																	</div>
																</td>
																<td width="50">
																	<div align="center">
																		<img src="images/main_14.gif" width="48" height="19"
																			onClick="back();" style="cursor: hand">
																	</div>
																</td>
																<td width="50">
																	<div align="center">
																		<img src="images/main_16.gif" width="48" height="19"
																			onClick="forward();" style="cursor: hand">
																	</div>
																</td>
																<td width="50">
																	<div align="center">
																		<img src="images/main_18.gif" width="48" height="19"
																			onClick="refresh();" style="cursor: hand">
																	</div>
																</td>
																<td width="50">
																	<div align="center">
																		<a href="login.jsp" target="_parent"><img
																				src="<%=path%>/images/main_20.gif" width="48"
																				height="19" border="0"> </a>
																	</div>
																</td>
																<td width="26">
																	<div align="center">
																		<img src="images/main_21.gif" width="26" height="19">
																	</div>
																</td>
																<td width="100">
																	<div align="center">
																		<a href="<s:property value="#session.url"/>"
																			target="I2"> <img src="images/main_22.gif"
																				width="98" height="19" style="cursor: hand"> </a>
																	</div>
																</td>
																<td>
																	&nbsp; />
																</td>
															</tr>
														</table>
													</td>
													<td width="220" valign="bottom" nowrap="nowrap">
														<div align="right">
															<span class="STYLE1"><span class="STYLE2"></span>
																<script language="JavaScript">
	var enabled = 0;
	today = new Date();
	var day;
	var date;
	if (today.getDay() == 0)
		day = "������";
	if (today.getDay() == 1)
		day = "����һ";
	if (today.getDay() == 2)
		day = "���ڶ�";
	if (today.getDay() == 3)
		day = "������";
	if (today.getDay() == 4)
		day = "������";
	if (today.getDay() == 5)
		day = "������";
	if (today.getDay() == 6)
		day = "������";
	var year1 = today.getYear();
	if (!window.ActiveXObject)
		year1 = year1 + 1900;

	document.fgColor = "000000";

	date = "�� ������ " + year1 + " ��" + (today.getMonth() + 1) + " �� "
			+ today.getDate() + " �� " + day + "";
	document
			.write("<CENTER><TABLE BORDER=2 BGCOLOR=000000><TH><FONT COLOR=ffffff>")
	document.write("<CENTER>" + date.fontsize(3) + "</CENTER>");
	document.write("</FONT></TH></TABLE></center><P>")
</script> </span>
														</div>
													</td>
												</tr>
											</table>
										</td>
										<td width="21">
											<img src="images/main_11.gif" width="25" height="38">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="8" style="line-height: 8px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="270" background="images/main_29.gif"
											style="line-height: 8px;">
											&nbsp;
										</td>
										<td width="505" background="images/main_30.gif"
											style="line-height: 8px;">
											&nbsp;
										</td>
										<td style="line-height: 8px;">
											&nbsp;
										</td>
										<td width="21" style="line-height: 8px;">
											<img src="images/main_31.gif" width="31" height="8">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table width="100%" height="32" cellpadding="0" cellspacing="0">
			<tr>
				<td width=13% height="28" align="center"
					style="background: url(images/main_32.gif) repeat" class="STYLE1">
					<s:if test="#session.user.TOrgainzation.orgLevel == 0">
					�����û�
					</s:if>
					<s:elseif test="#session.user.TOrgainzation.orgLevel == 1">
					��ά�û�
					</s:elseif>
					<s:elseif test="#session.user.TOrgainzation.orgLevel == 2">
					�����û�
					</s:elseif>
					<s:else>
					�����û�
					</s:else>
				</td>
				<td width=85.2% style="background: url(images/headerbg2.jpg) repeat">
					<MARQUEE class="menu" onMouseOut="this.start()" onMouseOver="this.stop()" scrolldelay="100">
					<!-- 	<s:property value="#session.user.name" />
						:
						 -->
						<s:property value="#session.user.TOrgainzation.name" />
					</MARQUEE>
				</td>
				<td width=1.8% style="background: url(images/main_37.gif) repeat"></td>
			</tr>
		</table>
	</body>
</html>
