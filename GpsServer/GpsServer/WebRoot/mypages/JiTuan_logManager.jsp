<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>日志管理</title>
	<s:head theme="xhtml"/>
    <sx:head parseContent="true" extraLocales="UTF-8"/>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
<script>
	
	//验证搜索表单
	function submitSearchForm(){
		//TODO:最好是可以判断后面的日期大于前面的日期
		document.searchForm.submit();
	}

	//验证分页表单
	function submitPageForm(){
		if(document.pageForm.pageIndex.value=="" || (/\s+/g.test(document.pageForm.pageIndex.value))){
		   window.alert("输入页码");
		   return;
		}
		if(isNaN(document.pageForm.pageIndex.value)){
			window.alert("页码需要为数字");
			return;
		}
		document.pageForm.submit();
	}
</script>
</head>
<body>
	
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[系统管理 >日志管理]</div>
		<div class="content">
		<form action="JiTuan_logSearch" method="post" name="searchForm">
		  <div class="tool"> 
		    <table border="0" >
			  <tr>
			    <td>日志时间：</td>
			    <td><sx:datetimepicker name="searchStartDate"
				displayFormat="yyyy-MM-dd" value="%{searchStartDate}" language="UTF-8"/> —— <sx:datetimepicker name="searchEndDate"
				displayFormat="yyyy-MM-dd" value="%{searchEndDate}" language="UTF-8"/> 
				</td>
			    <td><a href="#" class="cssbtn" onclick="submitSearchForm()"><img src="images/search.gif"/>查询</a></td>
			  	<td>&nbsp;&nbsp;<label id="formMessage" style="color: red; float: left;"><s:property value="#request.result" /></label></td>
			  </tr>
			</table>
	    </div>
	   </form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable" style="text-align:center;">
	  <tr>
	    <th scope="col">日志编号</th>
	    <th scope="col">操作账户</th>
	    <th scope="col">操作员名称</th>
	    <th scope="col">日志事件</th>
	    <th scope="col">日志时间</th>
	  </tr>
	  
	  <s:iterator value="logList" var="log" status="status">
	  <tr
	 	 <s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
          <s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>
       >
	    <td><s:property value="#log.logId"/></td>
	    <td><s:property value="#log.TUser.userid"/></td>
	    <td><s:property value="#log.TUser.name"/></td>
	    <td><s:property value="#log.logEvent"/></td>
	    <td><s:date name="#log.logTime" format="yyyy-MM-dd hh:mm" /></td>
	  </tr>
	  </s:iterator>
	</table>
	
	<form action="JiTuan_findLogList" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="JiTuan_findLogList"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="JiTuan_findLogList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="JiTuan_findLogList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="JiTuan_findLogList"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
		                  <td width="100"><div align="center"><span class="STYLE1">转到第
		                    <input name="pageIndex" type="text" size="4" maxlength="4" style="height:12px; width:20px; border:1px solid #999999;" /> 
		                    	页 </span></div></td>
		                  <td width="40">
		                  	<a href="#" onclick="submitPageForm()"><img src="images/go.gif" width="37" height="15"/></a>
		                  </td>
		                </tr>
		            </table>
		            &nbsp;&nbsp;共有 <s:property value="total"/> 条记录，当前第<s:property value="pageIndex"/>/<s:property value="pages"/> 页
		</div>
	</form>
</div>


<div class="container_bottom"><span class="c_b_l"></span><span class="c_b_r"></span></div>
</body>
</html>
