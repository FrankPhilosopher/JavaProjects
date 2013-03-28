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
		<title>编辑分组机构</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			//重置表单
			function resetForm(form) {
				document.getElementById("formMessage").innerHTML = "";
				form.reset();
			}
			
			// 验证电话号码
			function validateTel(input){
				var myReg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
				if(myReg.test(input)){
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
				if(!validateMobile(document.getElementById("telphone").value) && !validateTel(document.getElementById("telphone").value)){
					document.getElementById("formMessage").innerHTML = "联系电话不符合要求！";
					return;
				}else if(!validateMobile(document.getElementById("cellphone").value) && !validateTel(document.getElementById("cellphone").value)){
					document.getElementById("formMessage").innerHTML = "联系手机不符合要求！";
					return;
				}else if(!validateMobile(document.getElementById("warnphone").value) && !validateTel(document.getElementById("warnphone").value)){
					document.getElementById("formMessage").innerHTML = "报警电话不符合要求！";
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
			你当前的位置：[系统管理 > 分组用户管理> 编辑分组机构]
		</div>
		<form method="post" action="YunWei_saveGroupInfo" id="form"
			name="form">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">分组机构基本信息</a>
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
								所属集团：
							</th>
							<td>
								<input type="text" size="50" disabled="disabled"
									value="<s:property value="jituan.name" />"
									 />
								
								<!-- <select name="selectOrgId" style="" disabled="disabled">
									<s:iterator value="jituanList" var="tempjituan">
										<option value="<s:property value="#tempjituan.orgId"/>"
											<s:if test='jituan.orgId==#tempjituan.orgId'>selected</s:if>>
											<s:property value="#tempjituan.name" />
										</option>
									</s:iterator>
								</select>
								-->
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位名称：
							</th>
							<td>
								<input type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.name" />"
									name="orgainzation.name" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位简称：
							</th>
							<td>
								<input type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.shortName" />"
									name="orgainzation.shortName" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								单位地址：
							</th>
							<td>
								<input type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.address" />"
									name="orgainzation.address" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系电话：
							</th>
							<td>
								<input id="telphone" type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.telephone" />"
									name="orgainzation.telephone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								年费标准：
							</th>
							<td>
								<input id="feestandard" type="text" size="30"
									disabled="disabled"
									value="<s:property value="jituan.feestandard" />"
									name="orgainzation.feestandard" />
								&nbsp;&nbsp;&nbsp;&nbsp;(RMB/年)
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系人：
							</th>
							<td>
								<input type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.linkman" />"
									name="orgainzation.linkman" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系手机：
							</th>
							<td>
								<input id="cellphone" type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.cellphone" />"
									name="orgainzation.cellphone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								报警电话：
							</th>
							<td>
								<input id="warnphone" type="text" size="50" disabled="disabled"
									value="<s:property value="orgainzation.warnphone" />"
									name="orgainzation.warnphone" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								备注：
							</th>
							<td>
								<input id="remark" type="text" size="100" disabled="disabled"
									value="<s:property value="orgainzation.remark" />"
									name="orgainzation.remark" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								注册时间：
							</th>
							<td>
								<input type="text" disabled="disabled" size="50"
									value="<s:date name="orgainzation.registertime" format="yyyy-MM-dd hh:mm" />"
									name="orgainzation.registertime" />
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
			</div>
		</form>
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
