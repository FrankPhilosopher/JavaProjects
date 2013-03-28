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
		<title>新建分组机构</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/validate.js">
		</script>
		<script type="text/javascript" src="<%=path%>/js/jquery-1.7.js">
		</script>
		<script type="text/javascript">
//重置表单
function resetForm(form) {
	document.getElementById("formMessage").innerHTML = "";
	form.reset();
}

//验证表单
function checkForm(form) {
	if (!validatePhone(document.getElementById("telphone").value)) {
		document.getElementById("formMessage").innerHTML = "联系电话不符合要求！";
		return;
	} else if (!validatePhone(document.getElementById("cellphone").value)) {
		document.getElementById("formMessage").innerHTML = "联系手机不符合要求！";
		return;
	} else if (!validatePhone(document.getElementById("warnphone").value)) {
		document.getElementById("formMessage").innerHTML = "报警电话不符合要求！";
		return;
	} else if (document.getElementById("areaId").value == 0) {
		document.getElementById("formMessage").innerHTML = "必须选择所属省份和城市！";
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
			你当前的位置：[系统管理 > 分组用户管理> 新建分组机构]
		</div>
		<form method="post" action="JiTuan_addGroup" id="form" name="form">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">新建分组机构</a>
					</li>
					<li class="gray">
						&nbsp;
					</li>
				</ul>

				<div class="cont90">
					<h6>
						填入分组机构基本信息
					</h6>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputtable">
						
						<tr>
							<th scope="row">
								单位名称：
							</th>
							<td>
								<input type="text" size="50" maxlength="30"
									value="<s:property value="orgainzation.name" />"
									name="orgainzation.name" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位简称：
							</th>
							<td>
								<input type="text" size="50" maxlength="30"
									value="<s:property value="orgainzation.shortName" />"
									name="orgainzation.shortName" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位地址：
							</th>
							<td>
								<input type="text" size="50" maxlength="30"
									value="<s:property value="orgainzation.address" />"
									name="orgainzation.address" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系电话：
							</th>
							<td>
								<input id="telphone" type="text" size="50" maxlength="20"
									value="<s:property value="orgainzation.telephone" />"
									name="orgainzation.telephone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系人：
							</th>
							<td>
								<input type="text" size="50" maxlength="20"
									value="<s:property value="orgainzation.linkman" />"
									name="orgainzation.linkman" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系手机：
							</th>
							<td>
								<input id="cellphone" type="text" size="50" maxlength="20"
									value="<s:property value="orgainzation.cellphone" />"
									name="orgainzation.cellphone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								报警电话：
							</th>
							<td>
								<input id="warnphone" type="text" size="50" maxlength="20"
									value="<s:property value="orgainzation.warnphone" />"
									name="orgainzation.warnphone" />
							</td>
						</tr>
						
						<tr>
							<th scope="row">
								所属省份：
							</th>
							<td>
								<select id="provinceId">
									<option value="0" selected="selected">
										--请选择省份--
									</option>
									<s:iterator value="areaList" var="area" status="status">
										<option value="<s:property value="#area.areaId"/>">
											<s:property value="#area.name" />
										</option>
									</s:iterator>
								</select>

								<select name="areaId" id="areaId">
									<option value="0" selected="selected">
										--请选择城市--
									</option>
								</select>
							</td>
						</tr>
						
						<tr>
							<th scope="row">
								备注：
							</th>
							<td>
								<input id="remark" type="text" size="100" maxlength="100"
									value="<s:property value="orgainzation.remark" />"
									name="orgainzation.remark" />
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
							<a href="#" class="cssbtn" onclick="resetForm(form)"><img
									src="<%=path%>/images/reset.gif" />重置</a>
							<a href="#" class="cssbtn" onclick="checkForm(form)"><img
									src="<%=path%>/images/save.gif" />添加</a>
							<a href="<s:url action="JiTuan_groupManager"></s:url>"
								class="cssbtn"><img src="<%=path%>/images/search.gif" />查看</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
		<script type="text/javascript">
$("#provinceId").change(
		function() {
			var provinceId = $("select[id='provinceId'][@selected]").val();
			$
					.ajax( {
						type : "POST",
						url : "AreaAjax?provinceId=" + provinceId,
						dataType : "json",
						success : function(json) {
							$("#areaId").empty();
							$('#areaId').append(
									"<option value='0'>--请选择城市--</option>");
							for (i = 0; i < json.cities.length; i++) {
								var city = json.cities[i];
								$('#areaId').append(
										"<option value='" + city.areaId + "'>"
												+ city.name + "</option>");
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
