<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.yinger.blog.*,java.util.List"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Category Management</title>
	</head>
	<%
		List<Category> categorylist = (List<Category>) request.getAttribute("categorylist");
		Category category = null;
	%>
	<body>
		<p align="center">
			Category Management
		</p>
		<table border="1" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td width="80px">
					ID
				</td>
				<td width="140px">
					Name
				</td>
				<td width="120px">
					Level
				</td>
				<td width="100px">
					Delete
				</td>
			</tr>
			<%
				for (int i = 0, size = categorylist.size(); i < size; i++) {
					category = categorylist.get(i);
			%>
			<tr>
				<td width="80px">
					<%=category.getId()%>
				</td>
				<td width="140px">
					<a href="http://localhost:8080/blog/servlet/AdminCategoryServlet?method=preModify&cid=<%=category.getId()%>"><%=category.getName()%></a>
				</td>
				<td width="120px">
					<%=category.getLevel()%>
				</td>
				<td width="100px">
					<a href="http://localhost:8080/blog/servlet/AdminCategoryServlet?method=delete&cid=<%=category.getId()%>" onclick="return confirm('Do you want to delete this category?')">Delete</a>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>
