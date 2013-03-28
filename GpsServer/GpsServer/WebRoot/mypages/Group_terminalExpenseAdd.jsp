<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>服务费充值管理</title>
		<s:head theme="xhtml"/>
    <sx:head parseContent="true" extraLocales="UTF-8"/>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function submitForm(form){
				form.submit();
			}
		</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[服务费管理 > 费用充值> 终端充值 ]
		</div>

		<div class="content">
			<ul class="newobj" style="width: 85%;">
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li class="sel">
					<a href="#">终端服务费充值</a>
				</li>
				<li class="gray">
					&nbsp;
				</li>
			</ul>
			<div class="cont90">
				<h6>
					终端信息
				</h6>
				<form action="Group_addTerminalExpense" method="post" name="form"
					id="form">
					<div class="b1">
						<table width="100%" class="inputtable">
							<tr>
								<th width="30%">
									所属分组
								</th>
								<td colspan="2">
									<s:property value="terminal.TOrgainzation.name" />
								</td>
							</tr>
							<tr>
								<th width="30%">
									终端编号
								</th>
								<td colspan="2">
									<s:property value="terminal.id" />
								</td>
							</tr>
							<tr>
								<th>
									分组机构服务标准年费
								</th>
								<td colspan="2">
									<input name="account.expense" type="hidden" value="<s:property value="terminal.TOrgainzation.feestandard" />" />
									<s:property value="terminal.TOrgainzation.feestandard" />
									&nbsp;&nbsp;RMB/年
								</td>
							</tr>
							<tr>
								<th>
									分组机构当前费用余额
								</th>
								<td colspan="2">
									<s:property value="terminal.TOrgainzation.balance" />
									&nbsp;&nbsp;RMB
								</td>
							</tr>
							<tr>
								<th>
									终端服务起始日期
								</th>
								<td colspan="2">
									<!-- 
								<s:date name="terminal.startTime" format="yyyy-MM-dd hh:mm" />
								 -->
									<sx:datetimepicker name="serviceStartDate"
										displayFormat="yyyy-MM-dd" value="%{terminal.startTime}" language="UTF-8"/>
								</td>
							</tr>
							<tr>
								<th>
									终端服务结束日期
								</th>
								<td colspan="2">
									<!-- 
									<s:date name="terminal.endTime" format="yyyy-MM-dd hh:mm" />
								 -->
									<sx:datetimepicker name="serviceEndDate"
										displayFormat="yyyy-MM-dd" value="%{terminal.endTime}" language="UTF-8"/>
									<label style="color: red; margin-left: 10px;">
										[注意：时间间隔只能是<strong> 1 </strong>年]
									</label>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;&nbsp;
								</th>
								<td>
									<a href="#" onclick="submitForm(form)" class="cssbtn"><img
											src="<%=path%>/images/msg.gif" />立即充值</a>
									<label id="formMessage" style="color: red; float: left;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#request.result" />
									</label>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>

			<div class="cont90">
				<h6>
					本次充值账单信息
				</h6>
				<div class="b1">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="datatable" style="text-align: center;">
						<tr>
							<th scope="col">
								账单编号
							</th>
							<th scope="col">
								账单日期
							</th>
							<th scope="col">
								充值金额
							</th>
							<th scope="col">
								操作人
							</th>
							<th scope="col">
								备注
							</th>
						</tr>
						<tr bgcolor="#ffffff">
							<td>
								<s:property value="account.id" />
							</td>
							<td>
								<s:date name="account.paiddate" format="yyyy-MM-dd hh:mm" />
							</td>
							<td>
								<s:property value="account.expense" />
							</td>
							<td>
								<s:property value="user.name" />
							</td>
							<td>
								<s:property value="account.remark" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="container_bottom" style="clear: none;">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
