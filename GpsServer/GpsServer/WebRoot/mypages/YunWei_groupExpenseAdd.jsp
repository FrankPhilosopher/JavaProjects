<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>服务费充值管理</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function checkMoney(form) {
				var mon = form.money.value;
				if (mon < 100) {
					document.getElementById("formMessage").innerHTML = "请注意充值最低限额";
					return;
				}
				form.submit();
			}
		</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[服务费管理 > 费用充值> 分组机构充值]
		</div>

		<div class="content">
			<ul class="newobj" style="width: 85%;">
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li class="sel">
					<a href="#">分组机构服务费充值</a>
				</li>
				<li class="gray">
					&nbsp;
				</li>
			</ul>
			<div class="cont90">
			<h6>
				分组机构信息
			</h6>
			<form action="YunWei_addGroupExpense" method="post" name="form"
				id="form">
					<div class="b1">
						<table width="100%" class="inputtable">
							<tr>
								<th width="30%">
									分组机构编号
								</th>
								<td colspan="2">
									<s:property value="orgainzation.orgId" />
								</td>
							</tr>
							<tr>
								<th width="30%">
									分组机构名称
								</th>
								<td colspan="2">
									<s:property value="orgainzation.name" />
								</td>
							</tr>
							<tr>
								<th>
									集团机构当前费用余额
								</th>
								<td colspan="2">
									<s:property value="orgainzation.TOrgainzation.balance" />
									&nbsp;&nbsp;RMB
								</td>
							</tr>
							
							<tr>
								<th>
									服务标准年费
								</th>
								<td colspan="2">
									<s:property value="orgainzation.feestandard" />
									&nbsp;&nbsp;RMB/年
								</td>
							</tr>
							
							<tr>
								<th>
									分组机构当前费用余额
								</th>
								<td colspan="2">
									<s:property value="orgainzation.balance" />
									&nbsp;&nbsp;RMB
								</td>
							</tr>
							
							<tr>
								<th>
									充值金额
								</th>
								<td>
									<input type="text" id="money" name="account.expense" />
									&nbsp;&nbsp;RMB
									<label style="color: red; margin-left: 10px;">
										[注意：最低限额是 100 RMB]
									</label>
								</td>
								<td>
									<a href="#" onclick="checkMoney(form)" class="cssbtn"><img
											src="<%=path%>/images/msg.gif" />立即充值</a>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;&nbsp;
								</th>
								<td colspan="2">
									&nbsp;&nbsp;
									<label id="formMessage" style="color: red; float: left;">
										<s:property value="#request.result" />
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
						class="datatable" style="text-align:center;">
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
		<div class="container_bottom" style="clear:none;">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
