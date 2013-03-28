<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>电子栅栏</title>
<script type="text/javascript" src="js/key.js"></script>
<script type="text/javascript" src="js/fence.js"></script>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[电子栅栏]</div>

<div class="content">
  <div align="center"><h2>电子栅栏</h2></div>
<div class="tablefoot">
     <table width="100%" border="0">
      <tr>
        <td width="100%"><div style="width:100%;height:600px;border:1px solid gray;" id="container"></div></td>
      </tr>
    </table>
  </div>
</div>
<div class="container_bottom"><span class="c_b_l"></span><span class="c_b_r"></span></div>
<s:if test="position != null && position.latitude != -1">
<script type="text/javascript">
google_location(<s:property value="position.longitude"/>, <s:property value="position.latitude"/>, 15, "container", 2,1)
</script>
</s:if>
<s:else>
<script type="text/javascript">
google_location(116.404, 39.92, 15, "container", 2,2)
alert("暂无定位信息!");
</script>
</s:else>
</body>
</html>
