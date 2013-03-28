<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entity.Order"%>
<%@ page import="manager.DistributionManager"%>
<%@  page import="java.util.ArrayList"%>
<%@ page import="entity.User"%>
<%@ page import="entity.Distribution"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>物流管理系统</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<style media="all" type="text/css">
		@import "../style/all.css";
		@import "../style/jquery/ui.all.css";
	</style>
	<script type="text/javascript" src="../script/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../script/jquery-ui-personalized-1.6rc6.min.js"></script>
	<script type="text/javascript" src="../script/date.js"></script>
	<script type="text/javascript" src='../script/common.js'></script>
	<script type="text/javascript">
		$(function(){
			datepicker('.date-six');
		});
		function del(url){
			if(window.confirm("确认删除吗？")){
				window.location.href=url;
			}
		}		
	</script>
</head>
<body style="background: none">
	
	<form action="actionorder.jsp?action=''" method="post" onsubmit="return CheckForm();">
		  <div class="select-bar">
<% 	
		out.print("<table align='center'>");
			out.print("<tr>");		
				out.print("<td><h2>温馨提示:操作失败!</h2></td>");					
			out.print("</tr>");
		out.print("</table>");		
	%>			  
			<table class="select-table">			
				<tr>				
					<th><input type="button" name="Submit" value="返回" class="button" onclick="javascript:history.go(-1);"/></th>
					<th></th>
				</tr>
			</table>
			<img src="../images/login/login_tp.jpg" alt="" />
		</div>
	</form>	  
</body>
</html>

