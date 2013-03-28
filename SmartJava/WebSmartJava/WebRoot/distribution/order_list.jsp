<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entity.PlacePrice"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entity.Order"%>
<%@ page import="manager.DistributionManager"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entity.User"%>
<%@ page import="entity.Distribution"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>订单管理-客户-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
@import "../style/all.css";
</style>
	<style media="all" type="text/css">
@import "../style/excel.css";
</style>
	<script type="text/javascript">
		function deleteOrder(){
			if(confirm("您确定要删除该信息吗？")){
				alert("是的");
			}
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>订单列表</h1>
      <div class="breadcrumbs"> <a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="orderManager.jsp" target="_top">订单管理</a> </div>
    </div>
<form action="actionorder.jsp?action=order_query" method="post">
      <div class="select-bar"/>
      <table class="select-table">
				<tr>
					<th>订单号</th>
					<td><input type="text" name="id" class="text"/></td>
					<th></th>
					<th></th>				
				</tr>
				<tr>
					<th>收寄地</th>
						<td>
						<select name="tcity" style="width:70px">
					<%
						int i=0;
						ArrayList<Distribution> list=new ArrayList<Distribution>();
						DistributionManager dm=new DistributionManager();
						list=(ArrayList<Distribution>)dm.search();
						out.print("<option></option>");
						for(i=0;i<list.size();i++)
						{
							out.print("<option value="+list.get(i).getAddress()+">"+list.get(i).getAddress()+"</option>");
						}	
					 %>	
					 </select>					 
					</td>					
					<th>配送地</th>
					<td>
					<select name="scity" style="width:70px">
					<%
						out.print("<option></option>");
						User user=(User)session.getAttribute("loginuser");					
						for(i=0;i<list.size();i++)
						{
							out.print("<option value="+list.get(i).getAddress()+">"+list.get(i).getAddress()+"</option>");
						}	
					 %>	
					 </select>
					</td>
				</tr>
				<tr>
					<th>收件人姓名</th>
					<td><input type="text" name="receiver" class="text"/></td>
					<th>订单状态</th>
					<td>
						<select name="goodsType" style="width:70px">
							<option value=""></option>
							<option value="0">未审核</option>
							<option value="1">在站点</option>
							<option value="2">在途中</option>
							<option value="3">待派件</option>
							<option value="5">已派件</option>						
						</select>
					</td>
				</tr>
				<tr>
					<th>开始日期</th>
					<td><input type="text" class="date-six text" name='starttime' readonly/></td>
					<th>结束日期</th>
					<td><input type="text" class="date-six text" name='endtime' readonly/></td>
				</tr>
				<tr>
					<th><input type="submit" name="Submit"  value="查询订单" class="button"/></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
  		</table>
    </form>
	<div class="table"> 
	</div>
</body>
</html>

