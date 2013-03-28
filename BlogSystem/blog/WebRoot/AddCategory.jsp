<%@ page contentType="text/html; charset=GBK" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Add New BlogCategory</title>
	</head>

	<body>
		<form id="form1" name="form1" method="get" action="/blog/servlet/AdminCategoryServlet">
			<input type="hidden" name="method" id="method" value="add" />
			<table width="402" border="0" align="center">
				 <tr>
					<td width="150">
						类别名称:
					</td>
					<td width="335">
						<label for="title"></label>
						<input name="cname" type="text" id="cname" size="40" />
					</td>
				</tr>
				<tr>
					<td width="150">
						类别级别：
					</td>
					<td width="335">
						<label for="title"></label>
						<input name="clevel" type="text" id="clevel" size="40" />
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