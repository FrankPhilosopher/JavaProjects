<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.PlacePrice"%>
<%@page import="java.util.ArrayList"%>
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
			if(confirm("您确定要删除该订单信息吗？")){
				alert("是的");
			}
		}
		
		
		function submit1(){
			form1.submit();
			
		}
		function submit2(){
			form2.submit();
			
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>订单列表</h1>
      <div class="breadcrumbs"> <a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="orderManager.jsp" target="_top">订单管理</a> </div>
    </div>
<form name="form1" action="actionorder.jsp?action=place_choose" method="post">
<form name="form2" action="actionorder.jsp?action=place_choose" method="post">
      <div class="select-bar"/>
      <table class="select-table">

<% 
	ArrayList<PlacePrice> list=(ArrayList<PlacePrice>)request.getAttribute("PlacePriceList");
%>

			<tr>
				<th>派送范围</th>
				<td>
					<select name="placevalue" style="width:70px">
					<%
						int i=0;
						out.print("<option value=''></option>");
						for(i=0;i<list.size();i++)
						{
							out.print("<option value="+list.get(i).getPlaceName()+">"+list.get(i).getPlaceName()+"</option>");
						}	
					 %>
					</select>
				</td>
					<th><input type="button" name="Submit"  value="查询派送订单" class="button" onclick="submit1();"/></th>
					<th><input type="button" name="Submit"  value="派送订单" class="button" onclick="submit2();"/></th>
			</tr>
  </table>
    </form>
    </form>
<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
      <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">订单号码</th>
          <th>收件人姓名</th>
          <th>收件人地址</th>
          <th>订单重量</th>
          <th>订单体积</th>
          <th>订单状态</th>
          <th>是否派件</th>
        </tr>
       <tr>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>    	
   	    </tr>
      </tr>
  </table>
      <div class="pager">每页<input type="text" class="text" size="2" value="10" disabled/>条
    	  第1页 <a href="#">第一页</a> 
    				<a href="#">上一页</a>
    	 			<a href="#">下一页</a> 
    	 			<a href="#">最后一页</a>
    	 			转到<input type="text" class="text" size="1" value="1"/>
    	 			页<input type="button" class="button" onclick="" value="GO"/>
  	</div>
    </div>
</body>
</html>

