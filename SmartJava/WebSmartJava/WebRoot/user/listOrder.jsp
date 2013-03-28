<%@page import="entity.Orders"%>
<%@page import="entity.Order"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	ArrayList list = (ArrayList)request.getAttribute("list");
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
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>订单列表</h1>
      <div class="breadcrumbs"> <a href="index.jsp" target="_top">首页</a> / <a href="actionUser.jsp?action=orderManager" target="_top">订单管理</a> </div>
    </div>
<form name="frm1" method="post">
      <div class="select-bar"/>
      <table class="select-table">
    <tr>
          <th colspan="4" class="full"><div class="search_table_row">按
              <select style="width:130px" name="type" id="type" onchange="changeType();">
              <option value="0" selected="selected">发件人</option>
              <option value="1">订单编号</option>
            </select>
              查询 &nbsp;
              <input type="text" id="year" name="year"  class="text" />
              <input type="button" class="button" width="200"  value="查 询"/>
            </div></th>
        </tr>
  </table>
      </div>
    </form>
<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
      <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">订单号码</th>
          <th class="first">收件人姓名</th>
          <th>收件人地址</th>
          <th>发件人姓名</th>
          <th>订单重量</th>
          <th>订单体积</th>
          <th>订单状态</th>
          <th class="last">操作</th>
        </tr>
  // 	<% 
	//		for(int i =0;i<list.size();i++){
		//		Orders orders = (Orders)list.get(i);
		//		out.print("<tr>");
		//		out.print("<td>"+orders.getUserId()+"</td>");
		//		out.print("<td>" +orders.getReceiver()+"</td>");
		//		out.print("<td>"+orders.getsAddress()+"</td>");
		//		out.print("<td>" +orders.getSender()+"</td>");
		//		out.print("<td>"+orders.getWeight()+"</td>");
			//	out.print("<td>" +orders.getVolumn()+"</td>");
				
			//	if(orders.getStatus()==0){
			//		out.print("<td>未审核 </td>");
				
				
			//	}else if(orders.getStatus()==1){
				//	out.print("<td>在站点</td> ");
		
				//}else if(orders.getStatus()==2){
			//	out.print("<td>在途中 </td>");
				//}else if(orders.getStatus()==3){
				//	out.print("<td>未派件  </td>");
		
				//}else if(orders.getStatus()==4){
				//	out.print("<td>派件中</td>");
		
				//}else {
				//out.print("<td>已收件 </td>");
				//}
				
       
//}
  // %>
  </table>
   <div class="pager"> 共 条记录 每页
   <input type="text" class="text" size="2" />
    10条
    第1页/共页 <a href="#">第一页</a> <a href="#">上一页</a> <a href="#">下一页</a> <a href="#">最后一页</a> 转到
    <input type="text" class="text" size="1" />
    页
  <input type="button" class="button" onclick="" value="GO"/>
  </div>
  </div>
</body>
</html>
