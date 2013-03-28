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
		<title>编辑账户信息</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path %>/js/validate.js"></script>
		<script type="text/javascript">
			//改变checkbox对应的隐藏域的值，本来是写在一个方法中的，但是使用sibling方法没有成功
			function changeStateOilele(check){
				var node = document.getElementById("oil");
				if(node.value==1) {
					node.value = 0;
				}else {
					node.value = 1;
				}
			}
			
			function changeStateModify(check){
				var node = document.getElementById("mod");
				if(node.value==1) {
					node.value = 0;
				}else {
					node.value = 1;
				}
			}
			
			function changeStateExport(check){
				var node = document.getElementById("exp");
				if(node.value==1) {
					node.value = 0;
				}else {
					node.value = 1;
				}
			}
		
			//重置表单
			function resetForm(form) {
				document.getElementById("formMessage").innerHTML = "";
				form.reset();
			}
		
			//验证表单
			function checkForm(form) {
				if(!validatePhone(document.getElementById("cellphone").value)){
					document.getElementById("formMessage").innerHTML = "联系电话不符合要求！";//TODO:可能这里的联系电话可以是手机或者电话
					return;
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
			你当前的位置：[系统管理 > 分组用户管理> 账户管理> 编辑账户]
		</div>
		<form method="post" action="JiTuan_saveGroupUserInfo" id="infoform"
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
						基本信息修改
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								管理员账户：
							</th>
							<td>
								<input type="text" size="50" disabled="disabled" maxlength="20"
									value="<s:property value="groupUser.userid" />"
									name="groupUser.userid" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系人：
							</th>
							<td>
								<input type="text" size="50" maxlength="20"
									value="<s:property value="groupUser.name" />"
									name="groupUser.name" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								密码：
							</th>
							<td>
								<input type="text" size="50" maxlength="30"
									value="<s:property value="groupUser.pwd" />"
									name="groupUser.pwd" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系电话：
							</th>
							<td>
								<input id="cellphone" type="text" size="50" maxlength="20"
									value="<s:property value="groupUser.cellphone" />"
									name="groupUser.cellphone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								权限：
							</th>
							<td>
								<input type="checkbox" onclick="changeStateOilele(this)"
									<s:if test="groupUser.oilele==1">checked="checked"</s:if>>
								</input>
								<input type="hidden" name="groupUser.oilele" id="oil"
									value="<s:if test="groupUser.oilele==1">1</s:if><s:else>0</s:else>" />
								断油断电权限
								<input type="checkbox" onclick="changeStateModify(this)"
									<s:if test="groupUser.modify==1">checked="checked"</s:if>>
								</input>
								<input type="hidden" name="groupUser.modify" id="mod"
									value="<s:if test="groupUser.modify==1">1</s:if><s:else>0</s:else>" />
								修改信息权限
								<input type="checkbox" onclick="changeStateExport(this)"
									<s:if test="groupUser.export==1">checked="checked"</s:if>>
								</input>
								<input type="hidden" name="groupUser.export" id="exp"
									value="<s:if test="groupUser.export==1">1</s:if><s:else>0</s:else>" />
								导出数据权限
							</td>
						</tr>
						<tr>
							<th scope="row">
								注册时间：
							</th>
							<td>
								<input type="text" disabled="disabled" size="50"
									value="<s:date name="groupUser.registertime" format="yyyy-MM-dd hh:mm" />"
									name="groupUser.registertime" />
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
							<a href="<s:url action="JiTuan_groupUserManager"></s:url>"
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
