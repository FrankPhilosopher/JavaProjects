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
				return true;
			}
				return false;
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
						<select name="scity" style="width:70px">
					<%
						int i=0;
						ArrayList<Distribution> listdis=new ArrayList<Distribution>();
						DistributionManager dm=new DistributionManager();
						listdis=(ArrayList<Distribution>)dm.search();
						out.print("<option></option>");
						for(i=0;i<listdis.size();i++)
						{
							out.print("<option value="+listdis.get(i).getAddress()+">"+listdis.get(i).getAddress()+"</option>");
						}	
					 %>	
					 </select>	
					</td>					
					<th>配送地</th>
					<td>
					<select name="tcity" style="width:70px">
					<%
						out.print("<option></option>");
						User user=(User)session.getAttribute("loginuser");					
						for(i=0;i<listdis.size();i++)
						{
							out.print("<option value="+listdis.get(i).getAddress()+">"+listdis.get(i).getAddress()+"</option>");
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

<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
      <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">订单号码</th>
          <th>寄件人姓名</th>
          <th>寄件人地址</th>
          <th>收件人地址</th>          
          <th>订单重量</th>
          <th>订单体积</th>         
          <th>订单价格</th>
          <th>揽件日期</th>         
          <th class="last">操作</th>
   </tr>
<%
	ArrayList<Order> list=(ArrayList<Order>)request.getAttribute("OrderList");
	if(list.size()>0)
	{
		for(i=0;i<list.size();i++)
		{
			out.print("<tr>");
				out.print("<td heigth=20>"+list.get(i).getId()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getReceiver()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getsAddress()+"</td>");
				out.print("<td heigth=20>"+list.get(i).gettAddress()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getWeight()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getVolumn()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getPrice()+"</td>");
				out.print("<td heigth=20>"+list.get(i).getWorkDate()+"</td>");				
				if(list.get(i).getStatus()==8)
				{
					out.print("<td><a href='actionorder.jsp?action=order_update&flag=0&id="+list.get(i).getId()+"'/><img src='../style/img/edit-icon.gif'  width='16' height='16' alt='' />修改 </a>   <a href='actionorder.jsp?action=order_delete&id="+list.get(i).getId()+"'  onclick='return deleteOrder()'/><img src='../style/img/hr.gif' width='16' height='16' alt='' />删除</a></td>");
				}else {
					out.print("<td><a href='actionorder.jsp?action=order_update&flag=0&id="+list.get(i).getId()+"'/><img src='../style/img/edit-icon.gif'  width='16' height='16' alt='' />修改 </a></td>");
	
				}
			out.print("</tr>");	
			if(i+1>10)
			{
				break;
			}			
		}	
	}else {
			out.print("<tr>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
				out.print("<td heigth=20></td>");
			out.print("</tr>");		
			
		out.print("<table align='center'>");
		out.print("<tr>");		
			out.print("<td><h2>抱歉!没有查找到满足条件的订单信息!</h2></td>");	
			out.print("<br/><br/><br/><br/><br/><br/><br/>");				
		out.print("</tr>");
		out.print("</table>");	
	
		out.print("<table>");	
			out.print("<tr>");
			out.print("<th></th>");		
			out.print("<br/><br/><br/><br/><br/><br/><br/>");				
			out.print("</tr>");
		out.print("</table>");			
}
%>        
  </table>
      <div class="pager"> 
      <%
      out.print("共"+list.size()+"条记录");
      %>每页<input type="text" class="text" size="2" value="10"/>条 第1页
    <%
    	if(list.size()<10)
    	{
    		out.print("共1页");
    	}else {
    		out.print("共"+(list.size()/10+1)+"页");
    	} 
    %>
     	<a href="#">第一页</a> 
     	<a href="#">上一页</a> 
     	<a href="#">下一页</a> 
    	<a href="#">最后一页
    	</a>转到<input type="text" class="text" size="1" value="1"/>页
     	<input type="button" class="button" onclick="" value="GO"/>
  </div>
    </div>
</body>
</html>

