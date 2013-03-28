<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��Ŀ�б�</title>
 <s:head theme="xhtml"/>
    <sx:head parseContent="true" extraLocales="UTF-8"/>
<script type="text/javascript" src="js/key.js"></script>
<script type="text/javascript" src="js/orbit.js"></script>
<script>
   function submitSearchForm1(myform){
	   //alert(myform.searchStartDate.value);
	   //alert(document.getElementById("startid").value);
	   //if(document.getElementById("startid").value=="" || document.getElementById("endid").value=""){
		//   alert("��������ʼʱ��!");
		//   return;
	  // }
	   myform.action = "Terminal_orbitCheckSearch";
	   myform.submit();
   }
   
   function submitSearchForm2(myform){
	   myform.action = "Terminal_stopPositionSearch";
	   myform.submit();
   }
</script>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />�㵱ǰ��λ�ã�[�켣��ѯ]</div>
<div class="content">
  <div align="center"><h2>�켣��ѯ</h2></div>
   <form method="post" name="searchForm">
		  <div class="tool"> 
		    <table border="0" >
			  <tr>
			    <td>
			    &nbsp;&nbsp;
			    <input type="hidden" name="sim" value="<s:property value="sim"/>"/> 
				</td>
			    <td>ѡ��켣��ʼʱ�䣺</td>
			    <td><sx:datetimepicker name="searchStartDate" id="startid"
				displayFormat="yyyy-MM-dd" value="%{searchStartDate}" language="UTF-8"/> <input type="text" value="00:00:00" size="10" name="startTime"/>���� <sx:datetimepicker name="searchEndDate"
				id="endid" displayFormat="yyyy-MM-dd" value="%{searchEndDate}" language="UTF-8"/> <input type="text" value="00:00:00" size="10" name="endTime"/>
				</td>
			    <td><a href="#" class="cssbtn" onclick="submitSearchForm1(searchForm)"><img src="<%=path%>/images/search.gif"/>�켣��ѯ</a></td>
			    <td><a href="#" class="cssbtn" onclick="submitSearchForm2(searchForm)"><img src="<%=path%>/images/search.gif"/>ͣ����¼��ѯ</a></td>
			    <td>&nbsp;&nbsp;*ע��,�뽫��ѯʱ�������3����!</td>
			  	<td>&nbsp;&nbsp;<label id="formMessage" style="color: red; float: left;"><s:property value="result" /></label></td>
			  </tr>
			</table>
	    </div>
	   </form>
<div class="tablefoot">
     <table width="100%" border="0">
      <tr>
        <td width="100%"><div style="width:100%;height:600px;border:1px solid gray;" id="container"></div></td>
      </tr>
    </table>
  </div>
</div>
<div class="container_bottom"><span class="c_b_l"></span><span class="c_b_r"></span></div>

<s:if test="positions!=null&&positions.size()>=0">
    <!-- ��ʼ�����нڵ� -->
	<s:iterator value="positions" var="o" status="status">
	<script>
	arrPoints[<s:property value="#status.index"/>]=new google.maps.LatLng(<s:property value="#o.latitude"/>, <s:property value="#o.longitude"/>);
	//speeds[<s:property value="#status.index"/>]=<s:property value="#o.speed"/>;
	times[<s:property value="#status.index"/>] ="<s:date name="#o.PTime" format="yyyy-MM-dd HH:mm:ss"/>";
	</script>
	</s:iterator>
	
	<s:if test="flag == 0">
	<script>
	      initPath(15, "container");
	</script>
	</s:if>
	<s:elseif test="flag == 1">
	<script>
	      initStops(15, "container");
	</script>
	</s:elseif>
	
	<s:if test="positions!=null&&positions.size()==0">
	  <s:if test="flag == 0">
	    <script>
	       alert("��ʱ����������˶��Ĺ켣��Ϣ!");
	       mapinit(116.404, 39.92, 15, "container", 2)
	    </script>
	   </s:if> 
	   
	    <s:if test="flag == 1">
	    <script>
	       alert("��ʱ���������ͣ����¼!");
	       mapinit(116.404, 39.92, 15, "container", 2)
	    </script>
	   </s:if> 
	</s:if>
</s:if>
<s:elseif test="position != null || position.latitude != -1">
	<script type="text/javascript">
	google_location(<s:property value="position.longitude"/>, <s:property value="position.latitude"/>, 15, "container", <s:property value="position.TTerminal.netstatus"/>,1)
	</script>
</s:elseif>
<s:else>
	<script type="text/javascript">
	//google_location(106.784467, 26.53877, 15, "container", 2)
	</script>
</s:else>
</body>
</html>
