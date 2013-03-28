<%@ page contentType="text/html; charset=GBK" language="java"	import="java.sql.*" errorPage=""%>
<%@ page import="com.yinger.blog.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ModifyBlog</title>
	</head>
<%
	Blog blog=(Blog)request.getAttribute("blog");
%>
	<body>
	<p>Modify Blog</p>
		<form id="form1" name="form1" method="post" action="/blog/servlet/PostModifyBlogServlet">
		<input type="hidden" name="blogid" value="<%=blog.getId() %>"/>
			<table width="402" border="0">
				<tr>
					<td width="51">
						主题:
					</td>
					<td width="335">
						<input name="title" type="text" id="title" size="40" value="<%=blog.getTitle() %>"/>
					</td>
				</tr>
				<tr>
					<td width="51">
						类别：
					</td>
					<td>
						<select name="category" id="category">
							<option value="1">
								Java
							</option>
							<option value="2">
								ACM
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="51">
						内容：
					</td>
					<td width="335">
						<label for="content"></label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="content" id="content" cols="60" rows="16"><%=blog.getContent() %></textarea>
					</td>
				</tr>
				<tr>
					<td width="51" height="36">
						<input type="reset" name="reset" id="reset" value="重置" />
					</td>
					<td>
						<input type="submit" name="submit" id="submit" value="提交" />
					</td>
				</tr>
			</table>
		</form>
		<p>
			&nbsp;
		</p>
	</body>
</html>