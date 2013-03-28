<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.yinger.blog.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Display Blog</title>
	</head>

	<body>
		<%
			Blog blog = (Blog) request.getAttribute("blog");
		%>
		<form action="/blog/servlet/AdminCommentServlet" name="form1"
			id="form1">
			<input type="hidden" name="blogid" id="blogid"
				value="<%=blog.getId()%>">
			<input type="hidden" name="method" id="method" value="add">
			<table align="center">
				<tr>
					<td align="center"><%=blog.getTitle()%></td>
				</tr>
				<tr>
					<td><%=blog.getContent()%></td>
				</tr>
				<tr>
					<td><%=blog.getCreateTime()%></td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>
					<td>
						<table>
							<%
								List clist = (List) request.getAttribute("commentlist");
								for (int i = 0, size = clist.size(); i < size; i++) {
									Comment c = (Comment) clist.get(i);
							%>
							<tr>
								<td>
									Commenter:<%=c.getUsername() %>
								</td>
							</tr>
							<tr>
								<td>
									Comment Time:<%=c.getCreatedtime() %>
								</td>
							</tr>
							<tr>
								<td>
									Comment content:
								</td>
							</tr>
							<tr>
								<td>
									<%=c.getContent() %>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>
					<td>
						<table>
							<tr>
								<td>
									Commenter:
								</td>
								<td>
									<input name="commenter" id="commenter" size="20" />
								</td>
							</tr>
							<tr>
								<td>
									Comment content:
								</td>
								<td>
									<label>
										&nbsp;
									</label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<textarea name="commentcon" id="commentcon" cols="60" rows="10"></textarea>
								</td>
							</tr>
							<tr>
								<td>
									<input type="submit" value="Submit" name="submit" id="submit" />
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
		</form>
	</body>
</html>
