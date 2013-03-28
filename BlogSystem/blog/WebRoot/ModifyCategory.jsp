<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.yinger.blog.Category"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Modify BlogCategory</title>
	</head>
<%
	Category category=(Category)request.getAttribute("category");
%>
	<body>
		<form id="form1" name="form1" method="get" action="/blog/servlet/AdminCategoryServlet">
		<input name="method" id="method" type="hidden" value="postModify"/>
		<input name="cid" id="cid" type="hidden" value="<%=category.getId() %>" />
			<table width="402" border="0" align="center">
				<tr>
					<td width="51">
						类别名称:
					</td>
					<td width="335">
						<label for="title"></label>
						<input name="cname" type="text" id="cname" size="40" value="<%=category.getName() %>"/>
					</td>
				</tr>
				<tr>
					<td width="51">
						类别级别：
					</td>
					<td width="335">
						<label for="title"></label>
						<input name="clevel" type="text" id="clevel" size="40" value="<%=category.getLevel() %>" />
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