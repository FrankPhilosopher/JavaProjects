<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<title>服务费充值管理</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
	function submitRechargeFrom() {
		 if (isNaN(document.rechargeForm.expense.value)) {
			document.getElementById("rechargeFormMessage").innerHTML = "充值金额必须为数字";
		} else if (document.rechargeForm.expense.value == null
				|| document.rechargeForm.expense.value <= 0) {
			document.getElementById("rechargeFormMessage").innerHTML = "充值金额必须大于零";
		} else {
			document.rechargeForm.submit();
		}
	}
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[费用管理 > 服务费充值管理]
		</div>
		<div class="content">
			<ul class="newobj">
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li class="sel">
					<a href="#">服务费充值</a>
				</li>
				<li class="gray">
					&nbsp;
				</li>
			</ul>

			<div class="b1">
				<form action="Admin_recharge" method="post" name="rechargeForm">
					<table width="96%" class="datatable">
						<tr>
							<th width="30%">
								集团名称
							</th>
							<td width="70%" colspan="2">
								<input type="text" size="50"  name="jituan.name"
									value="<s:property value="jituan.name"/>" disabled="disabled" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<th>
								当前费用余额
							</th>
							<td colspan="2">
								<input type="text" size="50"
									value="<s:property value="jituan.balance"/>"
									disabled="disabled" maxlength="7"/>
							</td>
						</tr>
						<tr>
							<th>
								充值金额
							</th>
							<td>
								<input type="text" id="expense" value="" name="expense" maxlength="6"/>
								RMB
							</td>
							<td>
								<a href="#" class="cssbtn" onclick="submitRechargeFrom()"><img
										src="<%=path%>/images/msg.gif" />立即充值</a>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="2">
								<label id="rechargeFormMessage" style="color: red; float: left;">
									<s:property value="#request.result2" />
								</label>
							</td>
						</tr>
					</table>
				</form>
			</div>

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
							账单事件
						</th>
						<th scope="col">
							账单日期
						</th>
						<th scope="col">
							充值金额
						</th>
						<th scope="col">
							充值对象
						</th>
						<th scope="col">
							操作人
						</th>
					</tr>

					<tr>
						<td>
							<s:property value="account.id" />
						</td>
						<td>
							<s:property value="account.remark" />
						</td>
						<td>
							<s:property value="account.paiddate" />
						</td>
						<td>
							<s:property value="account.expense" />
						</td>
						<td>
							<s:property value="account.TOrgainzation.name" />
						</td>
						<td>
							<s:property value="account.paider" />
						</td>
					</tr>
				</table>
			</div>
		</div>


		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
