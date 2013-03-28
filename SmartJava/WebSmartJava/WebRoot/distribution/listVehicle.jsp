<%@page import="java.util.ArrayList"%>
<%@page import="manager.CarManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,entity.*"%>

<html>
	<head>
	<title>车辆管理-省公司-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
@import "../style/all.css";
</style>
	<style media="all" type="text/css">
@import "../style/excel.css";
</style>
	<script type="text/javascript">
		function deleteCar(){
			if(confirm("您确定要删除该车辆信息吗？")){
				alert("是的");
			}
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>车辆列表</h1>
      <div class="breadcrumbs"> <a href="../indexdistribution.jsp" target="_top">首页</a> / <a href="vehicleManage.jsp" target="_top">车辆管理</a> </div>
    </div>
<div class="table"> 
<img src="../style/img/bg-th-left.gif" width="8" height="7"  class="left" /> 
<img src="../style/img/bg-th-right.gif" width="7" height="7"  class="right" />
<table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">车辆编号</th>
          <th>司机姓名</th>
          <th>车牌号码</th>
          <th>承载重量(单位：KG)</th>
          <th>承载体积(单位：M3)</th>
          <th class="last">车辆状态</th>
        </tr>
   <%    		
   		CarManager cm=new CarManager();   		
   		HashMap<String,String> hashMap=new HashMap<String,String>();
   		Distribution currentDistribution=(Distribution)session.getAttribute("currentDistribution");
   		hashMap.put("currentDistribution",""+currentDistribution.getId());
   		hashMap.put("status", ""+Car.ATSTOP);
   		ArrayList<Car> list=cm.searchByMap(hashMap);
   		for(int i=0;i<list.size();i++){
   		out.print("<tr>");
   		out.print("<td>"+list.get(i).getId()+"</td>");
   		out.print("<td>"+list.get(i).getDriver()+"</td>");
   		out.print("<td>"+list.get(i).getNumber()+"</td>");
   		out.print("<td>"+list.get(i).getWeight()+"</td>");
   		out.print("<td>"+list.get(i).getVolumn()+"</td>");
   		if(list.get(i).getStatus()==1){
   		out.print("<td>在站点</td></tr>");
   		}else if(list.get(i).getStatus()==0){
   		out.print("<td>空闲</td></tr>");
   		}else if(list.get(i).getStatus()==2){
   		out.print("<td>在途中</td></tr>");
   		}   		   		
   		}
    %>
    
  </table>
      <div class="pager"> 共 59 条记录 每页
    <input type="text" class="text" size="2" value="10"/>
  条
   第1页/共5页<a href="#">第一页</a> <a href="#">上一页</a> <a href="#">下一页</a> <a href="#">最后一页</a> 转到
    <input type="text" class="text" size="1" value="1"/>
   页
    <input type="button" class="button" onclick="" value="GO"/>
  </div>
    </div>
</body>
</html>
