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
<title>项目列表</title>
<script type="text/javascript" src="<%=path%>/js/key.js"></script>
<script type="text/javascript" src="<%=path%>/js/googleMap.js"></script>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[终端定位]</div>

<div class="content">
  <div align="center"><h2>定位信息</h2></div>
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable">
  <tr>
    <th scope="col">终端号码</th>
    <th scope="col">定位模式</th>
    <th scope="col">最新定位时间</th>
    <th scope="col">纬度</th>
    <th scope="col">经度</th>
    <th scope="col">速度</th>
    <th scope="col">运动方向</th>
    <th scope="col">网络状态</th>
    <th scope="col">工作状态</th>
  </tr>
  <tr align="center">
    <td><s:property value="position.sim"/></td>
    <td>
    <s:if test="position.locationModel == 1">
          基站定位
    </s:if>
    <s:else>
    卫星定位
    </s:else>
    </td>
    <td><s:date name="position.PTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><s:property value="position.latiDirection"/><s:property value="position.latitude"/></td>
    <td><s:property value="position.longDirection"/><s:property value="position.longitude"/></td>
    <td><s:property value="position.speed"/></td>
    <td><s:property value="position.direction"/></td>
    <td>
    	<s:if test="position.TTerminal.netstatus == 1">
    		在线
    	</s:if>
    	<s:else>
    	    信号弱或电源丢失
    	</s:else>
    </td>
    <td>
    	<s:if test="position.TTerminal.status == 1">
    	  工作
    	</s:if>
    	<s:else>
    	空闲
    	</s:else>
    </td>
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
<s:if test="position != null && position.latitude != -1">
<script type="text/javascript">
google_location(<s:property value="position.longitude"/>, <s:property value="position.latitude"/>, 15, "container", <s:property value="position.TTerminal.netstatus"/>,1)
</script>
</s:if>
<s:else>
<script type="text/javascript">
google_location(116.404, 39.92, 10, "container", 2,2)
alert("暂无定位信息!");
</script>
</s:else>
</body>
</html>
