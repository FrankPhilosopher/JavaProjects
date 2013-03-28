<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="entity.PlacePrice"%>
<%@page import="java.util.ArrayList"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
      <h1>配送范围及价格列表</h1>
       <div class="breadcrumbs"> 
       		<a href="<%=path%>/indexdistribution.jsp" target="_top">首页/</a> 
       		<a href="<%=path%>/distribution/placepriceManager.jsp" target="_top">配送范围及价格管理</a>
       </div>
    </div>
    
    
<form action="actionplaceprice.jsp?action=placeprice_query" method="post" >
      <div class="select-bar"/>
      <table class="select-table">
	    <tr>
          <th colspan="4" class="full">
	          <div class="search_table_row">按
		              <select style="width:130px" name="type" id="type" onchange="changeType();">
		              	  <option value="">查询所有</option>
			              <option value="placeName" >配送范围</option>
			              <option value="placeTime">配送时间</option>
			              <option value="placePrice">配送价格</option>

		              </select>查询 &nbsp;
		              <input type="text" name="values" class="text" />
		              <input type="submit" class="button" value="查 询"/>
		     </div>
         </th>
	   </tr>
  	</table>
</form>

<div class="table"> 
	<img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> 
	<img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
	<table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">配送范围</th>
          <th>配送价格(元)</th>
          <th>配送时间</th>
          <th>备注信息</th>
          <th class="last">操作</th>
    </tr>
    <tr>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    	<td height=20></td>
    </tr>
    
  </table>
     <div class="pager">
                    每页<input type="text" class="text" size="2" value="10"/>条 第1页/共1页
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


