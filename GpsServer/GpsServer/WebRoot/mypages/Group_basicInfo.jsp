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
		<title>基本资料</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
	//重置表单
	function resetForm() {
		document.basicInfoForm.reset();
		document.getElementById("formMessage").innerHTML = "";
	}

	//验证表单
	function checkForm() {
		if (document.basicInfoForm.oldPwd.value == null || document.basicInfoForm.oldPwd.value == ""
				|| document.basicInfoForm.newPwd.value == null || document.basicInfoForm.newPwd.value == "") {
			document.getElementById("formMessage").innerHTML = "密码不能为空！";
		} else {
			if (document.basicInfoForm.newPwd.value != document.basicInfoForm.confirmPwd.value) {
				document.getElementById("formMessage").innerHTML = "两次输入的密码不相同！";
			} else {
				document.getElementById("formMessage").innerHTML = "";
				document.basicInfoForm.submit();
			}
		}
	}
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[系统管理 > 基本资料]
		</div>
		<form method="post" action="YunWei_saveBasicInfo" id=form1
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">基本资料</a>
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
								单位名称：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.name" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位简称：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.shortName" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位地址：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.address" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系电话：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.telephone" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系人：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.linkman" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系人手机：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.cellphone" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								用户账号：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.userid" />" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								旧密码：
							</th>
							<td>
								<input type="password" size="50" name="oldPwd" maxlength="28"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								新密码：
							</th>
							<td>
								<input type="password" size="50" name="newPwd" maxlength="28"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								确认密码：
							</th>
							<td>
								<input type="password" size="50" name="confirmPwd" maxlength="28"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								所有者：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.name"/>" disabled="disabled" />
							</td>
						</tr>
					</table>
				</div>
				<table border="0" class="btn_table">
					<tr>
						<td>
							<label id="formMessage" style="color: red; float: left;">
								<s:property value="result" />
							</label>
							<a href="#" class="cssbtn"
								onclick="return checkForm();"><img
									src="<%=path%>/images/save.gif" />保存</a>
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