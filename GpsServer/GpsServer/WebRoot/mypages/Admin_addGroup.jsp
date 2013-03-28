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
		<script type="text/javascript" src="<%=path%>/js/jquery-1.7.js">
		</script>
		<script type="text/javascript">
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
		
			//验证表单
			function checkForm(form) {
				if(!validateTel(document.getElementById("telphone").value)&&!validateMobile(document.getElementById("telphone").value)){
					document.getElementById("formMessage").innerHTML = "联系电话不符合要求！";
					return;
				}else if(!validateMobile(document.getElementById("cellphone").value)){
					document.getElementById("formMessage").innerHTML = "联系手机不符合要求！";
					return;
				}else if(!validateTel(document.getElementById("warnphone").value)&&!validateMobile(document.getElementById("warnphone").value)){
					document.getElementById("formMessage").innerHTML = "报警电话不符合要求！";
					return;
				}else if(document.getElementById("selectAreaId").value==0){
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
			你当前的位置：[系统管理 >集团管理> 分组用户管理> 新建分组机构]
			<form method="post" action="Admin_addGroup" id="form" name="form">
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
							<!-- 
						<tr>
							<th scope="row">
								所属集团：
							</th>
							<td>
								<select name="selectOrgId" style="">
									<s:iterator value="jituanList" var="tempjituan">
										<option value="<s:property value="#tempjituan.orgId"/>"
											<s:if test='jituan.orgId==#tempjituan.orgId'>selected</s:if>>
											<s:property value="#tempjituan.name" />
										</option>
									</s:iterator>
								</select>
							</td>
						</tr>
						 -->
							<tr>
								<th scope="row">
									单位名称：
								</th>
								<td>
									<input type="text" size="50" name="orgainzation.name"
										maxlength="30" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									单位简称：
								</th>
								<td>
									<input type="text" size="50" name="orgainzation.shortName"
										maxlength="30" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									单位地址：
								</th>
								<td>
									<input type="text" size="50" name="orgainzation.address"
										maxlength="30" />
								</td>
							</tr>

							<tr>
								<th scope="row">
									联系电话：
								</th>
								<td>
									<input id="telphone" type="text" size="50"
										name="orgainzation.telephone" maxlength="20" />
								</td>
							</tr>
							<!-- 
						<tr>
							<th scope="row">
								年费标准：
							</th>
							<td>
								<input id="feestandard" type="text" size="30" disabled="disabled"
									value="<s:property value="jituan.feestandard" />"
									name="jituan.feestandard" />&nbsp;&nbsp;&nbsp;&nbsp;(RMB/年)
							</td>
						</tr>
						 -->
							<tr>
								<th scope="row">
									联系人：
								</th>
								<td>
									<input type="text" size="50" name="orgainzation.linkman"
										maxlength="20" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									联系手机：
								</th>
								<td>
									<input id="cellphone" type="text" size="50"
										name="orgainzation.cellphone" maxlength="20" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									报警电话：
								</th>
								<td>
									<input id="warnphone" type="text" size="50"
										name="orgainzation.warnphone" maxlength="20" />
								</td>
							</tr>
							<tr>
								<th scope="row">
									备注：
								</th>
								<td>
									<input id="remark" type="text" size="100"
										name="orgainzation.remark" maxlength="200" />
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
										<s:iterator value="areas" var="area" status="status">
											<option value="<s:property value="#area.areaId"/>">
												<s:property value="#area.name" />
											</option>
										</s:iterator>
									</select>

									<select name="selectAreaId" id="selectAreaId">
										<option value="0" selected="selected">
											--请选择城市--
										</option>
									</select>
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
								<a
									href="<s:url action="Admin_groupManager?pageIndex=1"></s:url>"
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
			$.ajax( {
				type : "POST",
				url : "AreaAjax?provinceId=" + provinceId,
				dataType : "json",
				success : function(json) {
					$("#selectAreaId").empty();
					$('#selectAreaId').append(
							"<option value='0'>--请选择城市--</option>");
					for (i = 0; i < json.cities.length; i++) {
						var city = json.cities[i];
						$('#selectAreaId').append(
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
