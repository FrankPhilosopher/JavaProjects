<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,entity.*,manager.*" %>
<%
	ArrayList<String> nextStop=(ArrayList<String>)session.getAttribute("nextTop");
	System.out.println("下一站队列为："+nextStop);
	ArrayList<Order> orderList=(ArrayList<Order>)request.getAttribute("orderList");
	System.out.println("传过来的订单队列为:"+orderList);
	Car c=(Car)session.getAttribute("loadCar");
	System.out.println("车辆为："+c);
 %>
<html>
<head>
<title>装货配送</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
 @import "../style/all.css";
 @import "../style/jquery/ui.all.css";
</style>
<script type="text/javascript">
  function count(){
  <%out.print("var weight="+c.getCurrentWeight()+"; var volumn="+c.getCurrentVolumn()+";");%>    
    var order=document.getElementsByName('order');
    var current=0;
    var currentVolumn=0;
   for(var i=0;i<order.length;i++){
	if(order[i].checked==true){
		current=current+parseFloat(order[i].nextSibling.value);
		currentVolumn=currentVolumn+parseFloat(order[i].nextSibling.nextSibling.value);	
		}
	}
	var restWeight=weight-current;
	var resVolumn=volumn-currentVolumn; 
	if(restWeight<0){
	alert("车辆超载");
	}
	if(resVolumn<0){
	alert("体积超出车容量");
	}
	document.getElementById("restWeight").value=restWeight;
	document.getElementById("restVolumn").value=resVolumn;  
   }
   function test(){
   document.check_load.h1.value=document.selectNext.restWeight.value;   
   document.check_load.h2.value=document.selectNext.restVolumn.value;
   alert("restWeight:"+document.check_load.h1.value+"--restVolumn"+document.check_load.h2.value);
   document.check_load.submit();
   }
</script>
</head>
<body style="background: none">
<div class="breadcrumbs">=选择装货订单<br/>
</div>
<div class="select-bar">
 <form name="selectNext" action="actionGoods.jsp?action=select" method="post">
  <table class="select-table" id="search" >
   <tr><th colspan='2'>车辆剩余装载量为(单位：KG)：<input type="text" name="restWeight" id="restWeight"></th><th colspan='2'>剩余体积为(单位：M3)：<input type="text" name="restVolumn" id="restVolumn"></th></tr>
    <tr>
     <!-- <th>查询的订单编号:</th>
      <td><input type='text' class="text" name=''></td>-->
      <th>下一站配送点名称:</th>
      <td><select style='width:auto' name="next">
      <%if(nextStop!=null){
      	for(int i=0;i<nextStop.size();i++){
      		out.print("<option>"+nextStop.get(i)+"</option>");
      	}
      } %>          
        </select></td>
      <td class="last" style="white-space:nowrap"><input type="button" onclick=selectNext.submit() value="查 询" class="button"/>
        <input type="button" class="button" width="200" onclick=test() value="装 货" /></form></td>
        <td><form action="actionGoods.jsp?action=carSend" name="carSend" method="post"><input type="submit" class="button" width="200"  value="发车" /></form></td>
    </tr>
  </table>
 
</div>
<div class="table" style="width:800;height:400;overflow:auto"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
  <table class="listing" cellpadding="0" cellspacing="0">
    <tr>
      <th class="first">订单号</th>
      <th >下一站配送点名称</th>
      <th>订单总重量</th>
      <th>订单总体积</th>
      <th>订单起点站</th>
      <th>订单终点站</th>
      <th class="last">是否装货</th>
    </tr>
    <form action="actionGoods.jsp?action=commit_load" name="check_load" id="check_load" method="post">
    <%if(orderList!=null){
    	DistributionManager dm=new DistributionManager();    	
    	for(int i=0;i<orderList.size();i++){    	
    		out.print("<tr><td>"+orderList.get(i).getId()+"</td>");
    		out.print("<td>"+session.getAttribute("next")+"</td>");
    		out.print("<td>"+orderList.get(i).getWeight()+"</td>");
    		out.print("<td>"+orderList.get(i).getVolumn()+"</td>");
    		out.print("<td>"+dm.searchById(orderList.get(i).getsDistribution()).getName()+"</td>");
    		out.print("<td>"+dm.searchById(orderList.get(i).gettDistribution()).getName()+"</td>");
    		out.print("<td><input type='checkbox' name='order' onchange='count();' value='"+orderList.get(i).getId()+"'/><input type='hidden' name='hiddenid' value='"+orderList.get(i).getWeight()+"' /><input type='hidden' name='hiddenid' value='"+orderList.get(i).getWeight()+"' /><input type='hidden' name='hiddenid2' value='"+orderList.get(i).getVolumn()+"' /></td></tr>"); 		    		
    	}    
    } %> 
     <input type='hidden' name='h1' />
     <input type='hidden' name='h2'/>
    </form>       
  </table>

  </div>
  <div class="pager"> 共59条记录 每页
    <input type="text" class="text" size="2" value="5"/>
条
       第1页/共5页<a href="#">第一页</a> <a href="#">上一页</a> <a href="#">下一页</a> <a href="#">最后一页</a>转到
    <input type="text" class="text" size="1" value="1"/>
    页
    <input type="button" class="button" onclick="" value="GO"/>
  </div>
</div>
</body>
</html>
