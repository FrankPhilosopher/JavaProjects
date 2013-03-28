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
		<title>账户基本资料</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript">
	//重置表单
	function resetForm() {
		document.basicInfoForm.reset();
		document.getElementById("formMessage").innerHTML = "";
	}
	//验证用户名和密码是否是字母或数字
function validateNameAndPwd(input){
    var tmp = /^[A-Za-z0-9]+$/; //用户名和密码必须为字母或数字
				if (!tmp.test(input)) {
					return false;
				}
				return true;
}

	//验证表单
	function checkForm() {
		if (document.basicInfoForm.oldPwd.value == null
				|| document.basicInfoForm.oldPwd.value == "") {
			document.getElementById("formMessage").innerHTML = "旧密码不能为空！";
		} else if (document.basicInfoForm.newPwd.value == null
				|| document.basicInfoForm.newPwd.value == "") {
			document.getElementById("formMessage").innerHTML = "新密码不能为空！";
		}else if(!validateNameAndPwd(document.basicInfoForm.newPwd.value)||!validateNameAndPwd(document.getElementById("pwd").value)){
					document.getElementById("formMessage").innerHTML = "用户名和密码必须由字母或数字组成！";
				}
		 else {
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
		<form method="post" action="Admin_saveBasicInfo" id="form1" 
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">账户基本资料</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						基本资料修改
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								账户名称：
							</th>
							<td>
								<input type="text" size="50" id="userid"
									value="<s:property value="user.userid"/>" disabled="disabled" maxlength="20"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								旧密码：
							</th>
							<td>
								<input type="password" size="50" name="oldPwd" maxlength="20"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								新密码：
							</th>
							<td>
								<input type="password" size="50" id="pwd" name="newPwd" maxlength="20"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								确认密码：
							</th>
							<td>
								<input type="password" size="50" name="confirmPwd" maxlength="20"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								管理员姓名：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.name"/>" disabled="disabled" maxlength="20"/>
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位：
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="user.TOrgainzation.name"/>"
									disabled="disabled" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<label id="formMessage" style="color: red; float: left;">
									<s:property value="#request.result" />
								</label>
							</td>
						</tr>
					</table>
				</div>
				<table border="0" class="btn_table">
					<tr>
						<td>

							<a href="#" class="cssbtn" onclick="resetForm()"><img
									src="<%=path%>/images/reset.gif" />重置</a>
							<a href="#" class="cssbtn" onclick="checkForm()"><img
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
