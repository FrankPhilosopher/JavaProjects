<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>路线管理-省公司-物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
@import "../style/all.css";
</style>
	<style media="all" type="text/css">
@import "../style/excel.css";
</style>
	<script type="text/javascript">
		function confirm_null(){
		 var s=document.getElementById("sDistribution");
		 var t=document.getElementById("tDistribution");
		
		 if(s.value!=""&&t.value!="")
		 {
		    document.forms.frm1.action="actionRoute.jsp?action=search";
		 }
		  else
		 {
		   if(s.value!=""){alert("终点不能为空！");}
		   else if(t.value!="") {alert("起点不能为空！");}
		   else {alert("起点、终点不能为空！");}
		 }
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>路线列表</h1>
      <div class="breadcrumbs"> <a href="../indexcompany.jsp target="_top">首页</a> / <a href="distributionManager.jsp" target="_top">路线管理</a> </div>
</div>
<form name="frm1" method="post" action="" >
      <div class="select-bar"/>
      <table class="select-table">
    <tr>
          <th colspan="4" class="full"><div class="search_table_row">起始点
              <input type="text" id="sDistribution" name="sD" class="text" ></input>
              至终点 &nbsp;
              <input type="text" id="tDistribution" name="tD" class="text" ></input>
              <input type="submit" class="button" width="200"  value="查 询" onclick="confirm_null()"></input>
            </div></th>
        </tr>
  </table>
      </div>
    </form>
    <div style="margin-left:250px;font-size:14px;margin-top:30px;"><a href="actionRoute.jsp?action=front"><img src="../style/img/back.gif"/><span>返回</span></a></div>
 <div><script>alert("路线不存在！")</script></div>
</body>
</html>
