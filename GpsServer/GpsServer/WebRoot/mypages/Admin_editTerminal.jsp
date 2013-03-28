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
		/^1[3-9]\d{9}$/
		if (!tmp.test(input)) {
			return false;
		}
		return true;
	}

	//验证表单
	function checkForm() {
		if(document.getElementById("phoneid").value == ""){
			document.getElementById("formMessage").innerHTML = "SIM卡号不可以为空!";
			return;
		}
		if(document.getElementById("machineid").value == 0){
			document.getElementById("formMessage").innerHTML = "请选择机器类型!";
			return;
		}
		 if(document.getElementById("selectAreaId").value==0){
		    document.getElementById("formMessage").innerHTML = "必须选择所属省份和城市！";
			return;
		}
		if (isNaN(document.getElementById("basekilo").value)||isNaN(document.getElementById("maintenance").value)) {
			document.getElementById("formMessage").innerHTML = "基础里程和保养里程必须为数字";
			return;
		}
		if(!validateMobile(document.getElementById("p1").value)||!validateMobile(document.getElementById("p2").value)||!validateMobile(document.getElementById("p3").value)){
			document.getElementById("formMessage").innerHTML = "特权操作号不符合手机号码格式";
			return;
		}
		if(!validateTel(document.getElementById("cellphone").value)&&!validateMobile(document.getElementById("cellphone").value)){
			document.getElementById("formMessage").innerHTML = "联系电话格式错误!";
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
			你当前的位置：[系统管理>集团用户管理>分组用户管理>终端用户管理 > 编辑终端用户]
		</div>
		<form method="post" action="Admin_updateTerminal" id="form1"
			name="basicInfoForm">
			<div class="content">
				<ul class="newobj">
					<li>
						<a href="#">&nbsp;</a>
					</li>
					<li class="sel">
						<a href="#">编辑终端用户</a>
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
								终端编号：
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
								<input id="phoneid" type="text" size="50" name="terminal.phone"
									maxlength="20" value="<s:property value="terminal.phone"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								目标编号：
							</th>
							<td>
								<input id="dasd" type="text" size="50" name="terminal.carnumber"
									maxlength="20" value="<s:property value="terminal.carnumber"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								机器类型：
							</th>
							<td>
								<select name="terminal.TCarInfo.carTypeId" id="machineid">
									<option value="0">
										--请选择--
									</option>
									<s:iterator value="cars" var="car" status="status">
										<option value="<s:property value="#car.carTypeId"/>"
											<s:if test="#car.carTypeId == terminal.TCarInfo.carTypeId">selected="selected"</s:if>>
											<s:property value="#car.typeName" />
										</option>
									</s:iterator>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">
								终端定位时间间隔：
							</th>
							<td>
							  <select name="terminal.PPeriod">
							  <option value="0" <s:if test='terminal.PPeriod == 0'>selected="selected"</s:if>>关闭定位</option>
								<s:bean name="org.apache.struts2.util.Counter" id="counter">
								   <s:param name="first" value="1" />
								   <s:param name="last" value="10" />
								   <s:iterator>
										 <option value="<s:property/>" <s:if test='terminal.PPeriod == current-1'>selected="selected"</s:if>><s:property/></option>
								   </s:iterator>
								</s:bean>
										</select>*10秒
							 </td>
						</tr>
						<tr>
							<th scope="row">
								所属地区：
							</th>
							<td>
								<select id="provinceId">
									<s:iterator value="areas" var="area">
										<option value="<s:property value="#area.areaId" />"
											<s:if
												test="terminal.TArea.TArea.areaId==#area.areaId">selected</s:if>>
											<s:property value="#area.name" />
										</option>
									</s:iterator>

								</select>

								<select name="selectAreaId" id="selectAreaId">
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">
								燃油类型：
							</th>
							<td>
								<input id="gas" type="text" size="50" name="terminal.gas"
									maxlength="20" value="<s:property value="terminal.gas"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								机器型号：
							</th>
							<td>
								<input id="model" type="text" size="50" name="terminal.model"
									maxlength="20" value="<s:property value="terminal.model"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								基础里程：
							</th>
							<td>
								<input id="basekilo" type="text" size="50"
									name="terminal.basekilo" maxlength="5"
									value="<s:property value="terminal.basekilo"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								保养里程：
							</th>
							<td>
								<input id="maintenance" type="text" size="50"
									name="terminal.maintenance" maxlength="5"
									value="<s:property value="terminal.maintenance"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								操作特权号1：
							</th>
							<td>
								<input id="p1" type="text" size="50" name="terminal.privilege1"
									maxlength="20"
									value="<s:property value="terminal.privilege1"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								操作特权号2：
							</th>
							<td>
								<input id="p2" type="text" size="50" name="terminal.privilege2"
									maxlength="20"
									value="<s:property value="terminal.privilege2"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								操作特权号3：
							</th>
							<td>
								<input id="p3" type="text" size="50" name="terminal.privilege3"
									maxlength="20"
									value="<s:property value="terminal.privilege3"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								用户姓名：
							</th>
							<td>
								<input id="orgname" type="text" size="50"
									name="terminal.username" maxlength="20"
									value="<s:property value="terminal.username"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								联系电话：
							</th>
							<td>
								<input id="cellphone" type="text" size="50"
									name="terminal.cellphone" maxlength="20"
									value="<s:property value="terminal.cellphone"/>" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								债券责任人：
							</th>
							<td>
								<input id="orgname" type="text" size="50"
									name="terminal.principal" maxlength="20"
									value="<s:property value="terminal.principal"/>" />
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td align="left">
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
var provinceId = $("select[id='provinceId'][@selected]").val();
$.ajax( {
	type : "POST",
	url : "AreaAjax?provinceId=" + provinceId,
	dataType : "json",
	success : function(json) {
		for (i = 0; i < json.cities.length; i++) {
			var city = json.cities[i];
			if (city.areaId != <s:property value="terminal.TArea.areaId" />) {
				$('#selectAreaId').append(
						"<option value='" + city.areaId + "'>" + city.name
								+ "</option>");
			}else{
			$('#selectAreaId').append(
						"<option value='" + city.areaId + "' selected  >" + city.name
								+ "</option>");
			}
		}
	},
	error : function(json) {
		alert("页面异常，请刷新页面");
		return false;
	}
});
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
