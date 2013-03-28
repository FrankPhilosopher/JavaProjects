<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>

<body>
<p>请输入博客内容：</p>
<form id="form1" name="form1" method="post" action="">
  <table width="402" border="1">
    <tr>
      <td width="51">主题:</td>
      <td width="335"><label for="title"></label>
      <input name="title" type="text" id="title" size="40" /></td>
    </tr>
    <tr>
      <td width="51">类别：</td>
      <td><label for="category"></label>
        <select name="category" id="category">
          <option value="1">Java</option>
          <option value="2">Acm</option>
      </select></td>
    </tr>
    <tr>
      <td width="51" height="205">内容：</td>
      <td><label for="content"></label>
      <textarea name="content" id="content" cols="45" rows="16"></textarea></td>
    </tr>
    <tr>
      <td width="51" height="36"><input type="reset" name="reset" id="reset" value="重置" /></td>
      <td><input type="submit" name="submit" id="submit" value="提交" /></td>
    </tr>
  </table>
</form>
<p>&nbsp;</p>
</body>
</html>