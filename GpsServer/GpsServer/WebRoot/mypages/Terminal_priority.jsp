<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<title>��Ȩ����</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/priority.js"></script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			�㵱ǰ��λ�ã�[��Ȩ����]
		</div>
		<form method="post" action="YunWei_saveBasicInfo" id=form1
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">�ն���Ȩ����</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						��������
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								�û�������
							</th>
							<td>
								<s:property value="terminal.username"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								�ն˱�ţ�
							</th>
							<td>
								<s:property value="terminal.sim"/>
								<input type="hidden" value="<s:property value="terminal.sim"/>" id="hidid"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								SIM���ţ�
							</th>
							<td>
								<s:property value="terminal.phone"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								��λʱ������
							</th>
							<td>
							    <s:if test="terminal.PPeriod == -1">
							    	�رն�λ
							    </s:if>
							    <s:else>
								<s:property value="terminal.PPeriod"/>*10��
								</s:else>
							</td>
						</tr>
						<tr>
							<th scope="row">
								ϵͳ�����ţ�
							</th>
							<td>
								<s:property value="terminal.TOrgainzation.warnphone"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								��Ȩ����1��
							</th>
							<td>
								<s:property value="terminal.privilege1"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								��Ȩ����2��
							</th>
							<td>
								<s:property value="terminal.privilege2"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								��Ȩ����3��
							</th>
							<td>
								<s:property value="terminal.privilege3"/>
							</td>
						</tr>
					</table>
				</div>
				<table border="0" class="btn_table">
					<tr>
					   <td align="center">
					     <span id="msg"></span>
					   </td>	
					</tr>
					<tr>
						<td>
							<label id="formMessage" style="color: red; float: left;">
								<s:property value="result" />
							</label>
							<input type="button" value=" ����Ȩ���� " id="b1" onclick="setPrivilege('<s:property value="terminal.sim"/>','<s:property value="terminal.privilege1"/>','<s:property value="terminal.privilege2"/>','<s:property value="terminal.privilege3"/>')"/>
							&nbsp;&nbsp;<input type="button" value=" ���ñ������� " id="b2" onclick="setWarnPhone('<s:property value="terminal.sim"/>','<s:property value="terminal.TOrgainzation.warnphone"/>')"/>
							&nbsp;&nbsp;<input type="button" value=" ���ö�λʱ���� " id="b3" onclick="setPeriod('<s:property value="terminal.sim"/>','<s:property value="terminal.PPeriod"/>')" />
							&nbsp;&nbsp;<input type="button" value=" �ͼ����� " id='b4' onclick="stopOil('<s:property value="terminal.sim"/>','2')"/>
							&nbsp;&nbsp;<input type="button" value=" �߼����� " id='b5' onclick="stopOil('<s:property value="terminal.sim"/>','1')"/>
							&nbsp;&nbsp;<input type="button" value=" ���� " id='b6' onclick="recoverOil('<s:property value="terminal.sim"/>')"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>