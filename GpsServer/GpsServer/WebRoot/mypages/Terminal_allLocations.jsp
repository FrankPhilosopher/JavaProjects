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
<title>所有终端定位</title>
<script type="text/javascript" src="<%=path%>/js/key.js"></script>
<script type="text/javascript" src="<%=path%>/js/allLocations.js"></script>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[终端定位]</div>

<div class="content">
<s:iterator value="tpositions" var="o" status="status">
<script>
arrPoints[<s:property value="#status.index"/>]=new google.maps.LatLng(<s:property value="#o.latitude"/>, <s:property value="#o.longitude"/>);
mylat[<s:property value="#status.index"/>]=<s:property value="#o.latitude"/>;
mylong[<s:property value="#status.index"/>]=<s:property value="#o.longitude"/>;
sims[<s:property value="#status.index"/>]="<s:property value="#o.sim"/>";
speeds[<s:property value="#status.index"/>]=<s:property value="#o.speed"/>;
directions[<s:property value="#status.index"/>]="<s:property value="#o.direction"/>";
times[<s:property value="#status.index"/>]="<s:date format="yyyy-MM-dd HH:mm:ss" name="#o.PTime"/>";
statuss[<s:property value="#status.index"/>]=<s:property value="#o.TTerminal.netstatus"/>;
carnumbers[<s:property value="#status.index"/>]="<s:property value="#o.TTerminal.carnumber"/>";
</script>
</s:iterator>
  <div align="center"><h2>所有终端定位信息</h2></div>
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable">
  <tr>
    <th scope="col">总的终端总数</th>
    <th scope="col">当前含定位信息终端总数</th>
    <th scope="col">当前在线终端数</th>
    <th scope="col">当前离线终端数</th>
  </tr>
  <tr align="center">
    <td><s:property value="total"/></td>
    <td><s:property value="ptotal"/></td>
    <td><s:property value="online"/></td>
    <td><s:property value="outline"/></td>
  </tr>
</table>
<div class="tablefoot">
     <table width="100%" border="0">
      <tr>
        <td width="100%"><div style="width:100%;height:600px;border:1px solid gray;" id="container"></div></td>
      </tr>
    </table>
  </div>
</div>
<div class="container_bottom"><span class="c_b_l"></span><span class="c_b_r"></span></div>
<script type="text/javascript">
google_location("container",4);
</script>
</body>
</html>
