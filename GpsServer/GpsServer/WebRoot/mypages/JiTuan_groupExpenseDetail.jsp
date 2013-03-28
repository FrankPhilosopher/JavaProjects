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
<title>费用查询</title>
<s:head theme="xhtml"/>
    <sx:head parseContent="true" extraLocales="UTF-8"/>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
<script>
	//验证搜索表单，通过的话就提交表单
	function submitSearchForm(){
		document.searchForm.submit();
	}

	//验证分页表单，通过的话就提交表单
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
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[服务费管理 > 费用充值]</div>
		<div class="content">
		  <div class="tool"> 
			<form action="JiTuan_groupExpenseDetailSearch" method="post" name="searchForm">
			  <div class="tool"> 
			    <table border="0" >
				  <tr>
				    <td>查询时间段：</td>
				    <td><sx:datetimepicker name="searchStartDate"
					displayFormat="yyyy-MM-dd" value="%{searchStartDate}" language="UTF-8"/> —— <sx:datetimepicker name="searchEndDate"
					displayFormat="yyyy-MM-dd" value="%{searchEndDate}" language="UTF-8"/> 
					</td>
				    <td><a href="#" class="cssbtn" onclick="submitSearchForm()"><img src="images/search.gif"/>查询</a></td>
				  </tr>
				</table>
		     </div>
		   </form>
		  </div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable" style="text-align:center;">
	  <tr>
	    <th scope="col">账单编号</th>
	    <th scope="col">账单时间</th>
	    <th scope="col">充值金额</th>
	    <th scope="col">单位名称</th>
	    <th scope="col">付款人</th>
	    <th scope="col">备注</th>
	  </tr>
	  
	  <s:iterator value="accountList" var="account" status="status">
	  <tr
	 	 <s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
          <s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>
       >
	    <td><s:property value="#account.id"/></td>
	    <td><s:date name="#account.paiddate" format="yyyy-MM-dd hh:mm" /></td>
	    <td><s:property value="#account.expense"/></td>
	    <td><s:property value="#account.TOrgainzation.name"/></td>
	    <td><s:property value="#account.paider"/></td>
	    <td><s:property value="#account.remark"/></td>
	  </tr>
	  </s:iterator>
	</table>
	
	<form action="JiTuan_findGroupExpenseDetailList" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="JiTuan_findGroupExpenseDetailList"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="JiTuan_findGroupExpenseDetailList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="JiTuan_findGroupExpenseDetailList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="JiTuan_findGroupExpenseDetailList"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
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
