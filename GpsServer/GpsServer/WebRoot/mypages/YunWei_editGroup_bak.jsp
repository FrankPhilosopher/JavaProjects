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
		<title>�༭�����û�</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
	//���ñ�
	function resetForm() {
		document.groupInfoForm.reset();
		document.getElementById("formMessage").innerHTML = "";
	}

	//��֤��
	function checkForm() {
		if (form.oldPwd.value == null || form.oldPwd.value == ""
				|| form.newPwd.value == null || form.newPwd.value == "") {
			document.getElementById("formMessage").innerHTML = "���벻��Ϊ�գ�";
		} else {
			if (form.newPwd.value != form.confirmPwd.value) {
				document.getElementById("formMessage").innerHTML = "������������벻��ͬ��";
			} else {
				document.getElementById("formMessage").innerHTML = "";
				form.submit();
			}
		}
	}
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			�㵱ǰ��λ�ã�[ϵͳ���� > �����û�����> �༭�����û�]
		</div>
		<form method="post" action="YunWei_saveGroupInfo" id=form1
			name="groupInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">������Ϣ</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						������Ϣ�޸�
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								�������ţ�
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="orgainzation.TOrgainzation.name" />" name="orgainzation.TOrgainzation.name" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								��λ���ƣ�
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="orgainzation.name" />" name="orgainzation.name" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								��λ��ƣ�
							</th>
							<td>
								<input type="text" size="50"
									value="<s:property value="orgainzation.shortName" />" name="orgainzation.shortName" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								��λ��ַ��
							</th>
							<td>
								<input type="text" size="50" value="<s:property value="orgainzation.address" />" name="orgainzation.address" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								��ϵ�绰��
							</th>
							<td>
								<input type="text" size="50" value="<s:property value="orgainzation.telephone" />" name="orgainzation.telephone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								��ϵ�ˣ�
							</th>
							<td>
								<input type="text" size="50" value="<s:property value="orgainzation.linkman" />" name="orgainzation.linkman" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								��ϵ�ֻ���
							</th>
							<td>
								<input type="text" size="50" value="<s:property value="orgainzation.cellphone" />" name="orgainzation.cellphone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								�����绰��
							</th>
							<td>
								<input type="text" size="50" value="<s:property value="orgainzation.warnphone" />" name="orgainzation.warnphone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								ע��ʱ�䣺
							</th>
							<td>
								<input type="text" disabled="disabled" size="50" value="<s:property value="orgainzation.registertime" />" name="orgainzation.registertime" />
							</td>
						</tr>
					</table>
				</div>
				<table border="0" class="btn_table">
					<tr>
						<td>
							<label id="formMessage" style="color: red; float: left;">
								<s:property value="#request.result" />
							</label>
							<a href="#" class="cssbtn" onclick="resetForm()"><img
									src="<%=path%>/images/reset.gif" />����</a>
							<a href="#" class="cssbtn"
								onclick="return checkForm();"><img
									src="<%=path%>/images/save.gif" />����</a>
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
