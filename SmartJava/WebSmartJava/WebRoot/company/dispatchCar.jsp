<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@page import="entity.Car"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>车辆调度-省公司-物流管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";

@import "../style/jquery/ui.all.css";
</style>

<script type="text/javascript">
	//验证表单
	function checkForm() {
		if (document.getElementById("searchKey").value == "") {
			alert("查询内容不能为空！");
			return;
		}
		document.form1.submit();
	}
	//验证表单
	function checkForm2() {
		var objs = document.getElementsByName('ToDispatchCars');
		var isSel = false;//判断是否有选中项，默认为无
		for ( var i = 0; i < objs.length; i++) {
			if (objs[i].checked == true) {
				isSel = true;
				break;
			}
		}
		if (isSel == false) {
			alert("请选择要派遣的车辆！");
			return;
		} else {
			document.form2.submit();
		}
	}
	function checkForm3() {
		if (document.getElementById("pageIndex").value == "") {
			alert("请输入页码！");
			return;
		}

		var myReg = /^[1-9]\d?$/;
		if (!myReg.test(document.getElementById("pageIndex").value)) {
			alert("页码请输入正整数");
			return;
		}
		document.form3.submit();
	}
</script>
</head>
<body style="background: none">
	<div class="top-bar">
		<h1>车辆调度</h1>
		<div class="breadcrumbs">
			<a href="../indexcompany.jsp" target="_top">首页</a> / <a
				href="carManager.jsp" target="_top">车辆管理</a>
		</div>
	</div>



	<form name="form1" method="post"
		action="actionCar.jsp?action=dispatchSearch">
		<div class="select-bar">
			<table class="select-table" id="search">
				<tr>
					<th>&nbsp;&nbsp;查询的车辆编号:</th>
					<td><input type='text' class="text" name='searchKey'
						id="searchKey" />
					</td>
					<td class="last" style="white-space:nowrap"><input
						type="button" value="查 询" class="button" onclick="checkForm()" />
					</td>
					<td></td>
				</tr>
			</table>
		</div>
	</form>

	<form name="form2" method="post" action="actionCar.jsp?action=dispatch">
		<div class="select-bar">
			<table class="select-table" id="search">
				<tr>
					<th>请选择要派遣到的配送点</th>
					<td><select name="distributeId" style='width:100px'>
							<%
								List<Distribution> distributionList = (List<Distribution>) request
										.getAttribute("distributionList");
								for (int i = 0; i < distributionList.size(); i++) {
									Distribution distribution = distributionList.get(i);
									out.print("<option value='" + distribution.getId() + "'>"
											+ distribution.getName() + "</option>");
								}
							%>
					</select></td>
					<td><input type="button" class="button" onclick="checkForm2()"
						value="派 遣" />
					</td>
					<td></td>
				</tr>
			</table>
		</div>
		
		<div class="table">
			<img src="../style/img/bg-th-left.gif" width="8" height="7" alt=""
				class="left" /> <img src="../style/img/bg-th-right.gif" width="7"
				height="7" alt="" class="right" />
			<table class="listing" cellpadding="0" cellspacing="0">
				<tr>
					<th class="first">车辆编号</th>
					<th>车牌号码</th>
					<th>承载重量</th>
					<th>承载体积</th>
					<th>司机姓名</th>
					<th>当前配送点</th>
					<th>当前状态</th>
					<th class="last">是否派遣</th>
				</tr>

				<%
					List<Car> carList = (List<Car>) request.getAttribute("carList");
					for (int i = 0; i < carList.size(); i++) {
						Car car = carList.get(i);
						out.print("<tr>");
						out.print("<td>" + car.getId() + "</td>");
						out.print("<td>" + car.getNumber() + "</td>");
						out.print("<td>" + car.getWeight() + "</td>");
						out.print("<td>" + car.getVolumn() + "</td>");
						out.print("<td>" + car.getDriver() + "</td>");

						out.print("<td>");
						out.print(DistributionManager.searchById(
								car.getCurrentDistribution()).getName());
						out.print("</td>");

						out.print("<td>");
						switch (car.getStatus()) {
						case 0:
							out.print("空闲的");
							break;
						case 1:
							out.print("在站点");
							break;
						case 2:
							out.print("在途中");
							break;
						}

						out.print("</td>");

						out.print("<td><input type='checkbox' name='ToDispatchCars' value ='");
						out.print(car.getId());
						out.print("'/>");
					}
				%>
			</table>
		</div>
	</form>
	
	<form name="form3" method="post"
		action="actionCar.jsp?action=forwardDispatch">
		<div class="pager">
			共
			<%=request.getAttribute("count")%>
			条记录 &nbsp;&nbsp; 每页10条&nbsp;&nbsp; 第
			<%=request.getAttribute("pageIndex")%>页&nbsp;/&nbsp;共
			<%=request.getAttribute("pages")%>
			页 &nbsp;&nbsp; <a
				href="actionCar.jsp?action=forwardDispatch&pageIndex=1">首页</a>
			&nbsp;<a
				href="actionCar.jsp?action=forwardDispatch&pageIndex=<%=(Integer) request.getAttribute("pageIndex") - 1%>">上一页</a>&nbsp;
			<a
				href="actionCar.jsp?action=forwardDispatch&pageIndex=<%=(Integer) request.getAttribute("pageIndex") + 1%>">下一页</a>&nbsp;
			<a
				href="actionCar.jsp?action=forwardDispatch&pageIndex=<%=(Integer) request.getAttribute("pages")%>">尾页
			</a>&nbsp; 转到 <input type="text" id="pageIndex" name="pageIndex"
				class="text" size="1" value="" /> 页 <input type="button"
				class="button" onclick="checkForm3()" value="GO" />
		</div>
	</form>
</body>
</html>
