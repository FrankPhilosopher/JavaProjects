<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>车辆添加</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style media="all" type="text/css">
@import "../style/all.css";
</style>
<style media="all" type="text/css">
@import "../style/excel.css";
</style>
</head>
<body style="background: none">
<div class="top-bar">
  <h1>车辆进站</h1>
  <div class="breadcrumbs"> <a href="vehicleManage.jsp" target="_top">车辆进站</a> / <a href="vehicleManage.jsp" target="_top">车辆管理</a> </div> 
  
</div>

<table class="select-table" id="search" >
    <tr>
    <form name="formEnter" method="post" action="actionVehicel.jsp?action=vehicleEnter">
      <th>要进站的车辆车牌号</th>
      <td><input type='text' class="text" name="car_number"></td>
      <td colspan="2"><input type="button" class="button" width="200" value="进 站" onclick="formEnter.submit();" />
      <%String message=(String)request.getAttribute("message");
        if(message!=null){
        out.print(message);
        }
       %></td>
      </form>
    </tr>
  </table>

</body>
</html>
