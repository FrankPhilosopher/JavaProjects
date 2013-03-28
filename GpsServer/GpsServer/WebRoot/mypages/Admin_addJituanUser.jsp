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
		<title>新建账户信息</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
//改变checkbox对应的隐藏域的值，本来是写在一个方法中的，但是使用sibling方法没有成功
function changeStateOilele(check) {
	var node = document.getElementById("oil");
	if (node.value == 1) {
		node.value = 0;
	} else {
		node.value = 1;
	}
}

function changeStateModify(check) {
	var node = document.getElementById("mod");
	if (node.value == 1) {
		node.value = 0;
	} else {
		node.value = 1;
	}
}

function changeStateExport(check) {
	var node = document.getElementById("exp");
	if (node.value == 1) {
		node.value = 0;
	} else {
		node.value = 1;
	}
}

//重置表单
function resetForm(form) {
	document.getElementById("formMessage").innerHTML = "";
	form.reset();
}

// 验证电话号码
function validateTel(input) {
	var myReg = /^(([0\+]\d{2,3}-{0,1})?(0\d{2,3})-{0,1})?(\d{7,8})(-(\d{3,}))?$/;
	if (myReg.test(input)) {
		return true;
	}
	return false;
}

// 验证手机号码
function validateMobile(input) {
				var tmp = /^1[3-9]\d{9}$/; //11位手机号码验证
				if (!tmp.test(input)) {
					return false;
				}
				return true;
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
			function checkForm(form) {
				if(!validateMobile(document.getElementById("cellphone").value)&&!validateTel(document.getElementById("cellphone").value)){
					document.getElementById("formMessage").innerHTML = "联系电话不符合要求！";//TODO:可能这里的联系电话可以是手机或者电话
					return;
				}else if(!validateNameAndPwd(document.getElementById("userid").value)||!validateNameAndPwd(document.getElementById("pwd").value)){
					document.getElementById("formMessage").innerHTML = "用户名和密码必须由字母或数字组成！";
				}else{
					form.submit();
				}
			}
	
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[系统管理 > 集团用户管理> 账户管理> 新建账户]
		</div>
		<form method="post" action="Admin_addJituanUser" id="infoform"
			name="infoform">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">基本信息</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						账户基本信息
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								管理员账户：
							</th>
							<td>
								<input type="text" size="50" id="userid"
									name="jituanUser.userid"
									value="<s:property value="jituanUser.userid" />" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系人：
							</th>
							<td>
								<input type="text" size="50" name="jituanUser.name"
									value="<s:property value="jituanUser.name" />" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								密码：
							</th>
							<td>
								<input type="text" size="50" id="pwd" name="jituanUser.pwd"
									value="<s:property value="jituanUser.pwd" />" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系电话：
							</th>
							<td>
								<input id="cellphone" type="text" size="50"
									value="<s:property value="jituanUser.cellphone" />"
									name="jituanUser.cellphone" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								权限：
							</th>
							<td>
								<input type="checkbox" onclick="changeStateOilele(this)" />
								<input type="hidden" name="jituanUser.oilele" id="oil" value="0" />
								锁机解锁权限
								<input type="checkbox" onclick="changeStateModify(this)" />
								<input type="hidden" name="jituanUser.modify" id="mod" value="0" />
								修改信息权限
								<input type="checkbox" onclick="changeStateExport(this)" />
								<input type="hidden" name="jituanUser.export" id="exp" value="0" />
								导出数据权限
							</td>
						</tr>
						<tr>
							<th scope="row">
								&nbsp;&nbsp;
							</th>
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
							<a href="#" class="cssbtn" onclick="resetForm(infoform)"><img
									src="<%=path%>/images/reset.gif" />重置</a>
							<a href="#" class="cssbtn" onclick="checkForm(infoform)"><img
									src="<%=path%>/images/save.gif" />保存</a>
							<a
								href="<s:url action="Admin_jituanUserManager?pageIndex=1"></s:url>"
								class="cssbtn"><img src="<%=path%>/images/search.gif" />查看</a>
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
