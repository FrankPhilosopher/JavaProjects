<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.yinger.blog.*,java.util.List"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Blog Management</title>
	</head>
	<%
		List<Blog> bloglist = (List<Blog>) request.getAttribute("bloglist");
		Blog blog = null;
	%>
	<body>
		<p align="center">
			Blog Management
		</p>
		<table border="1" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td width="80px">
					ID
				</td>
				<td width="140px">
					Title
				</td>
				<td width="250px">
					Content
				</td>
				<td width="120px">
					CreatedTime
				</td>
				<td width="100px">
					Delete
				</td>
			</tr>
			<%
				for (int i = 0, size = bloglist.size(); i < size; i++) {
					blog = bloglist.get(i);
			%>
			<tr>
				<td width="80px">
					<%=blog.getId()%>
				</td>
				<td width="140px">
					<a href="http://localhost:8080/blog/servlet/PreModifyBlogServlet?blogid=<%=blog.getId()%>"><%=blog.getTitle()%></a>
				</td>
				<td width="250px">
					<%=blog.getContent()%>
				</td>
				<td width="120px">
					<%=blog.getCreateTime()%>
				</td>
				<td width="100px">
					<a href="http://localhost:8080/blog/servlet/DeleteBlogServlet?blogid=<%=blog.getId()%>" onclick="return confirm('Do you want to delete this blog?')">Delete</a>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>
