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
		<title>特权设置</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/priority.js"></script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[特权设置]
		</div>
		<form method="post" action="YunWei_saveBasicInfo" id=form1
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">终端特权设置</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						基本资料
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								用户姓名：
							</th>
							<td>
								<s:property value="terminal.username"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								终端编号：
							</th>
							<td>
								<s:property value="terminal.sim"/>
								<input type="hidden" value="<s:property value="terminal.sim"/>" id="hidid"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								SIM卡号：
							</th>
							<td>
								<s:property value="terminal.phone"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								定位时间间隔：
							</th>
							<td>
							    <s:if test="terminal.PPeriod == -1">
							    	关闭定位
							    </s:if>
							    <s:else>
								<s:property value="terminal.PPeriod"/>*10秒
								</s:else>
							</td>
						</tr>
						<tr>
							<th scope="row">
								系统报警号：
							</th>
							<td>
								<s:property value="terminal.TOrgainzation.warnphone"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								特权号码1：
							</th>
							<td>
								<s:property value="terminal.privilege1"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								特权号码2：
							</th>
							<td>
								<s:property value="terminal.privilege2"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								特权号码3：
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
							<input type="button" value=" 绑定特权号码 " id="b1" onclick="setPrivilege('<s:property value="terminal.sim"/>','<s:property value="terminal.privilege1"/>','<s:property value="terminal.privilege2"/>','<s:property value="terminal.privilege3"/>')"/>
							&nbsp;&nbsp;<input type="button" value=" 设置报警号码 " id="b2" onclick="setWarnPhone('<s:property value="terminal.sim"/>','<s:property value="terminal.TOrgainzation.warnphone"/>')"/>
							&nbsp;&nbsp;<input type="button" value=" 设置定位时间间隔 " id="b3" onclick="setPeriod('<s:property value="terminal.sim"/>','<s:property value="terminal.PPeriod"/>')" />
							&nbsp;&nbsp;<input type="button" value=" 低级锁机 " id='b4' onclick="stopOil('<s:property value="terminal.sim"/>','2')"/>
							&nbsp;&nbsp;<input type="button" value=" 高级锁机 " id='b5' onclick="stopOil('<s:property value="terminal.sim"/>','1')"/>
							&nbsp;&nbsp;<input type="button" value=" 解锁 " id='b6' onclick="recoverOil('<s:property value="terminal.sim"/>')"/>
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