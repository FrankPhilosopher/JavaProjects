<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entity.*,java.util.*,java.text.*"%>
<%
	Car c = (Car) session.getAttribute("currentCar");
	System.out.println("司机名称为："+c.getDriver());
	Object alist=session.getAttribute("list");
	System.out.println("alist:"+alist);
	ArrayList<Order> list=null;
	if(alist!=null){
	list = (ArrayList<Order>)alist;
	}	 			
	User user=(User)session.getAttribute("loginuser");
	System.out.println("当前登录用户名为："+user.getName());
	Distribution currentDistribution=(Distribution)session.getAttribute("currentDistribution");
%>
<html>
<head>
<title>交接单管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<style media="all" type="text/css">
@import "../style/excel.css";
</style>
<script language="javascript" type="application/javascript">
    	
    </script>
</head>
<body style="background: none">
	<div class="top-bar">
		&rdquo;&ldquo;
		<!--a href="#" class="button">取 消</a><a href="#" class="button">保 存</a-->
		<h1>交接单管理</h1>
		<div class="breadcrumbs">
			<a href="index.html" target="_top">首页</a> / <a
				href="passingSheet.html" target="_top">交接单管理</a>
		</div>
	</div>
	<form name="excelForm" method="post"
		action="actionVehicel.jsp?action=queryOrder">
		<div class="select-bar" />
		<table class="select-table">
			<tr>
				<th>当前车辆编号：<%=c.getNumber()%></th>
				<td>
				<th><input type="button" onclick=excelForm.submit()
					value="查询订单">
				</th>
				<th><input type="button" onclick=uploadPassingSheet.submit()
					value="确认提交">
				</th>
		</table>
	</form>
	<div class="table">
		<table class="listing form" cellpadding="0" cellspacing="0">
			<tr>
				<th class="full" colspan="4">交接单编号</th>
			</tr>
			<tr>
				<td>交接地</td>
				<td class="last"><input name="idcard" type="text" class="text"
					value="<%=currentDistribution.getName()%>" />
				</td>
				<td class="first">日期</td>
				<td><input type="text" class="text" name="date" value="<%
				Date currentTime=new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String dateString = formatter.format(currentTime);
		        out.print(dateString);
				 %>"/>
				</td>
			</tr>
			<tr>

				<td>运输车号</td>
				<td class="last"><input name="officePhoneNumber" type="text"
					class="text" value="<%=c.getNumber()%>" />
				</td>
				<td class="first" style="text-align:left;">承运人</td>
				<td><input name="officePhoneNumber" type="text" class="text"
					value="<%=c.getDriver()%>" />
				</td>
			</tr>
		</table>
	</div>
	<div class="table">
		<img src="../style/img/bg-th-left.gif" width="8" height="7" alt=""
			class="left" /> <img src="../style/img/bg-th-right.gif" width="7"
			height="7" alt="" class="right" />
		<table class="listing form" cellpadding="0" cellspacing="0">
			<form action="actionVehicel.jsp?action=uploadPassingSheet" name="uploadPassingSheet" method="post">
				<tr>
					<th class="first">订单号</th>
					<th>订单总重量</th>
					<th>订单体积</th>
					<th>发件地</th>
					<th>收件地</th>
					<th>接收人签字</th>
				</tr>
				<%
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							Order od = list.get(i);
							out.println("<tr><td>" + od.getId() + "</td><td>"
									+ od.getWeight() + "</td><td>" + od.getVolumn()
									+ "</td><td>" + od.getsAddress() + "</td><td>"
									+ od.gettAddress() + "</td><td><input type='text' value='"+user.getName()+"'></td></tr>");
						}
					}
				%>

			</form>
		</table>
		<div class="pager">
			共 59 条记录 每页 <input type="text" class="text" size="2" value="10" /> 条
			第1页/共5页<a href="#">第一页</a> <a href="#">上一页</a> <a href="#">下一页</a> <a
				href="#">最后一页</a> 转到 <input type="text" class="text" size="1"
				value="1" /> 页 <input type="button" class="button" onclick=""
				value="GO" />
		</div>
	</div>
</body>
</html>
