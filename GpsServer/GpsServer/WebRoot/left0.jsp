<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<title>left</title>
		<link href="<%=path%>/css/css.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
<!--
-->
</style>
	</head>
	<body style="height: 100%;">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td
					style="background:url(<%=path%>/images/main_40.gif); height:30px; color:#fff; text-align:center;">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td valign="top" height="99%">
					<dl class="sidemenu">
						<dt id="imgmenu1" onClick="showsubmenu(1)">
							<img src="<%=path%>/images/i1.gif" />
							ϵͳ����
						</dt>
						<dd id="submenu1">
							<a href="Admin_basicInfo" target="I2">�˻���������</a>
							<a href="Admin_yunweiManager?pageIndex=1" target="I2">��ά�û�����</a>
							<a href="Admin_jituanManager?pageIndex=1" target="I2">�����û�����
							</a>
							<a href="Admin_carManager?pageIndex=1" target="I2">������Ϣ����</a>
							<a href="Admin_terminalDetail" target="I2">�ն������ѯ</a>
							<s:if test="#session.user.export == 1">
							<a href="Admin_excelManager?pageIndex=1" target="I2">���ݱ������</a>
							</s:if>
							<a href="Admin_logManager?pageIndex=1" target="I2">��־���� </a>
						</dd>
						<dt id="imgmenu2" onClick="showsubmenu(2)">
							<img src="<%=path%>/images/i2.gif" />
							����ѹ���
						</dt>
						<dd  id="submenu2">
							<a href="Admin_expenseManager" target="I2">���ó�ֵ</a>
							<a href="Admin_accountManager?pageIndex=1" target="I2">��ֵ�˵���ѯ</a>
						</dd>
					</dl>
				</td>
			</tr>
			<tr>
				<td height="18" background="<%=path%>/images/main_58.gif"
					align="center">
					��ͼ�������޹�˾
				</td>
			</tr>
		</table>
		<script>
	function showsubmenu(sid) {
		whichEl = eval("submenu" + sid);
		imgmenu = eval("imgmenu" + sid);
		if (whichEl.style.display == "none") {
			eval("submenu" + sid + ".style.display=\"\";");
		} else {
			eval("submenu" + sid + ".style.display=\"none\";");
		}
	}
</script>
	</body>
</html>
