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
		<title>编辑地区</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript">
	//重置表单
	function resetForm() {
		document.getElementById("formMessage").innerHTML = "";
		document.basicInfoForm.reset();
	}

	//验证表单
	function checkForm() {
		if (document.getElementById("areaname").value == null
				|| document.getElementById("areaname").value == "") {
			document.getElementById("formMessage").innerHTML = "地区名称不能为空！";
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
			你当前的位置：[地区管理 > 编辑地区]
		</div>
		<form method="post" action="Admin_saveArea" id="form1"
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">编辑地区</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						地区资料
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						<tr>
							<th scope="row">
								地区名称：
							</th>
							<td>
								<input id="areaname" type="text" size="50" name="area.name"
									value="<s:property value="area.name" />" name="area.name"
									maxlength="40" />
							</td>
						</tr>

						<tr>
							<td>
							</td>
							<td align="left">
								<label id="formMessage" style="color: red;">
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
									src="<%=path%>/images/save.gif" />提交</a>
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
