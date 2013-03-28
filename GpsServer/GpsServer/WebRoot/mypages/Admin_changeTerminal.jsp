<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<title>编辑终端用户</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/jquery-1.7.js">
		</script>

		<script type="text/javascript">
//重置表单
function resetForm() {
	//document.getElementById("formMessage").innerHTML = "";
	//document.basicInfoForm.reset();
	//window.close();
	window.location.href = "Admin_terminalManager";
}

//验证表单
function checkForm() {
	if (document.getElementById("groupID").value == 0) {
		document.getElementById("formMessage").innerHTML = "请选择所属分组!";
		return;
	}

	document.basicInfoForm.submit();
}
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[系统管理>集团用户管理>分组用户管理>终端用户管理 >转移终端]
		</div>
		<form method="post" action="Admin_transferTerminal" id="form1"
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">转移终端</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						终端用户基本资料 &nbsp;&nbsp;
						<label id="formMessage" style="color: red;">
							<s:property value="#request.result" />
						</label>
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								终端号码：
							</th>
							<td>
								<s:property value="terminal.sim" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								SIM卡号：
							</th>
							<td>
								<s:property value="terminal.phone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								机器编号：
							</th>
							<td>
								<s:property value="terminal.carnumber" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								机器类型：
							</th>
							<td>
								<s:property value="terminal.TCarInfo.typeName" />

							</td>
						</tr>
						<tr>
							<th scope="row">
								所属分组：
							</th>
							<td>
								<select id="jituanId">
									<s:iterator value="orgs" var="jituan">
										<option value="<s:property value="#jituan.orgId" />"
											<s:if
												test="terminal.TOrgainzation.TOrgainzation.orgId==#jituan.orgId">selected</s:if>>
											<s:property value="#jituan.name" />
										</option>
									</s:iterator>

								</select>

								<select name="groupID" id="groupID">
								</select>
							</td>
						</tr>

					</table>
				</div>
				<table border="0" class="btn_table">
					<tr>
						<td>
							<a href="#" class="cssbtn" onclick="checkForm()"><img
									src="<%=path%>/images/save.gif" />提交</a>
							<a href="#" class="cssbtn" onclick="resetForm()"><img
									src="<%=path%>/images/reset.gif" />返回</a>

						</td>
					</tr>

				</table>
			</div>
		</form>
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
		<script type="text/javascript">
var jituanId = $("select[id='jituanId'][@selected]").val();
$.ajax( {
	type : "POST",
	url : "ChangeGroupAjaxAction?jituanId=" + jituanId,
	dataType : "json",
	success : function(json) {
		for (i = 0; i < json.groups.length; i++) {
			var group = json.groups[i];
			if (group.orgId != <s:property value="terminal.TOrgainzation.orgId" />) {
				$('#groupID').append(
						"<option value='" + group.orgId + "'>" + group.name
								+ "</option>");
			}else{
			$('#groupID').append(
						"<option value='" + group.orgId + "' selected  >" + group.name
								+ "</option>");
			}
		}
	},
	error : function(json) {
		alert("页面异常，请刷新页面");
		return false;
	}
});
$("#jituanId").change(
		function() {
			var jituanId = $("select[id='jituanId'][@selected]").val();
			$.ajax( {
				type : "POST",
				url : "ChangeGroupAjaxAction?jituanId=" + jituanId,
				dataType : "json",
				success : function(json) {
					$("#groupID").empty();
					$('#groupID').append(
							"<option value='0'>--请选择分组--</option>");
					for (i = 0; i < json.groups.length; i++) {
						var group = json.groups[i];
						$('#groupID').append(
								"<option value='" + group.orgId + "'>"
										+ group.name + "</option>");
					}
				},
				error : function(json) {
					alert("页面异常，请刷新页面");
					return false;
				}
			});
		});
</script>
	</body>

</html>
