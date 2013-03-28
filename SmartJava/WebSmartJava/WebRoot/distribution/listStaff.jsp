<%@page import="entity.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
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
		function search(){
			if(form1.searchcontent.value.length == 0){
				document.getElementById("error").innerHTML = "请输入搜索内容！";
				return false;
			}else{
				form1.action = "actionStaff.jsp?action=search";
				form1.submit();
			}
		}
	
		function deleteUser(did){
			if(confirm("您确定要删除该用户信息吗？")){
				form1.action = "actionStaff.jsp?action=delete&id="+did;
				form1.submit();
			}
		}
	</script>
	</head>
	<body style="background: none">
<div class="top-bar">
      <h1>员工信息列表</h1>
      <div class="breadcrumbs">
		<a href="<%=path%>/indexdistribution.jsp" target="_top">首页</a> / <a href="<%=path%>/distribution/userManager.jsp" target="_top">人员管理</a>
	  </div>
    </div>
	<form name="form1" method="post" action="actionStaff.jsp?action=search"> 
      <div class="select-bar"/>
      <table class="select-table">
    <tr>
          <th colspan="4" class="full"><div class="search_table_row">按
            <select style="width:130px" name="type" id="type">
              <option value="0" >姓名</option>
              <option value="1">员工ID</option>
              <option value="2" selected="selected">登录名</option>
              <option value="3">手机号码</option>
            </select>  查询 &nbsp;
              <input type="text" id="year" name="searchcontent" value="" class="text" />
              <input type="button" class="button" width="200"  value="查 询" onclick="search()"/>
              <label name="message" id="error"></label>
            </div></th>
        </tr>
  </table>
      </div>
    </form>
<div class="table"> <img src="../style/img/bg-th-left.gif" width="8" height="7" alt="" class="left" /> <img src="../style/img/bg-th-right.gif" width="7" height="7" alt="" class="right" />
      <table class="listing form" cellpadding="0" cellspacing="0">
    <tr>
          <th class="first">员工ID</th>
          <th>姓名</th>
          <th>登录名</th>
          <th>手机号码</th>
          <th class="last">操作</th> 
    </tr>
    
    <%
    	int i,size;
    	ArrayList list = (ArrayList)request.getAttribute("userlist");
    	if(list != null){
	    	for(i=0,size=list.size();i<size;i++){
	    		User user = (User)list.get(i);
	    		out.print("<tr>");
	    		out.print("<td>"+user.getId()+"</td>");
	    		out.print("<td>"+user.getRealname()+"</td>");
	    		out.print("<td>"+user.getName()+"</td>");
	    		out.print("<td>"+user.getPhone()+"</td>");
	    		out.print("<td><a href='actionStaff.jsp?action=edit&id="+user.getId()+"' class='table_op_a'><img src='../style/img/edit-icon.gif' width='16' height='16'/>修改</a><a class='table_op_a' onclick='deleteUser("+user.getId()+");'> <img src='../style/img/hr.gif' width='16' height='16'/>删除</a> </td>");
	    	}
    	}
     %>
    
  </table>
</div>
</body>
</html>
