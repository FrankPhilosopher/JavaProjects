<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.yinger.blog.Category"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Add New Blog</title>
	</head>

	<body>
		<p align="center">
			请输入博文内容：
		</p>
		<p align="center">
			<a href="http://localhost:8080/blog/servlet/GetBlogListServlet">查看博文列表</a>
		</p>
		<form id="form1" name="form1" method="post"
			action="/blog/servlet/AddBlogServlet">
			<table width="402" border="0" align="center">
				<tr>
					<td width="51">
						主题:
					</td>
					<td width="335">
						<label for="title"></label>
						<input name="title" type="text" id="title" size="40" />
					</td>
				</tr>
				<tr>
					<td width="51">
						类别：
					</td>
					<td>
						<label for="category"></label>
						<select name="category" id="category">
							<%
								List list = (List) request.getAttribute("categorylist");
								Category c = null;
								for (int i = 0, size = list.size(); i < size; i++) {
									c = (Category) list.get(i);
							%>
							<option value="<%=c.getId()%>">
								<%=c.getName()%>
							</option>
							<%
								}
							%>
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
						<textarea name="content" id="content" cols="60" rows="16"></textarea>
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