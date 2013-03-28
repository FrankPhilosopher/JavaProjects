<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.yinger.blog.*,java.util.List"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Display BlogList</title>
	</head>

	<body>
		<%
			List<Blog> bloglist = (List<Blog>) request.getAttribute("bloglist");
			Blog blog = null;
		%>
		<%
			for (int i = 0, size = bloglist.size(); i < size; i++) {
				blog = bloglist.get(i);
		%>
		<table border="0" width="100%">
			<tr>
				<td><%=blog.getCreateTime()%></td>
			</tr>
			<tr>
				<td><a href="http://localhost:8080/blog/servlet/GetBlogServlet?blogid=<%=blog.getId() %>"><%=blog.getTitle()%></a></td>
			</tr>
			<tr>
				<td><%=blog.getContent()%></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
		<p>
			&nbsp;
		</p>
		<%
			}
		%>
	</body>
</html>
