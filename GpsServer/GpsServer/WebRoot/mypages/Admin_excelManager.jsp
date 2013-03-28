<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ztdz.pojo.TUser"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>数据报表管理</title>
		<s:head theme="xhtml"/>
    <sx:head parseContent="true" extraLocales="UTF-8"/>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		// 收缩和展开搜索条件
		function sliderDiv(){
			var div = document.getElementById("searchDiv");
			if(div.style.display == "block"){
				div.style.display = "none";
				document.getElementById("searchA").innerHTML = "[展开搜索条件]";
			}else{
				div.style.display = "block";
				document.getElementById("searchA").innerHTML = "[隐藏搜索条件]";
			}
		}
		
		//验证搜索表单，通过的话就提交表单
		function submitSearchForm(searchForm,type){
			if(type ==  "group"){
				searchForm.action = "Admin_findJiTuanGroupList";
				searchForm.submit();
			}
			if(type ==  "submit"){
				searchForm.action = "Admin_terminalExcelSearch";
				searchForm.submit();
			}
		}
		
		function checkForm(exportForm){
			//从session中获取数据
			var cuser = "<%=((TUser)session.getAttribute("user")).getExport()%>";
			if(cuser == 0){
				window.alert("对不起，您不具有数据导出的权限！");
				return;
			}
			var c = document.getElementsByName("colcheck");
			var value = "";
			var flag = 0;
			for(i=0;i<c.length;i++)
			{
			      if(c[i].checked==true)
			      {
			    	 flag = 1;
			    	 value += 1+",";//选中的话就是1
			      }else{
			    	 value += 0+",";//没有选中就是0
			      }
			}
			//alert(value);
			if(flag == 0){
				alert("请选择要导出的列项！");
				return;
			}
			document.getElementById("cols").value = value;
			exportForm.submit();
		}
		</script>
	</head>

	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[系统管理 > 数据报表管理]
		</div>
	<form action="Admin_terminalExcelSearch" method="post" name="searchForm">
	
		<div class="content">
			<h6>
				搜索条件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="searchA" onclick="sliderDiv()" style="text-decoration:underline;color:red;cursor:pointer;">[隐藏搜索条件]</span>
			</h6>
			<div class="b1" id="searchDiv" style="display:block;">
				<table width="96%" class="inputtable">
					<tr>
						<th>入网时间</th>
						<td><sx:datetimepicker name="searchFromDate"
							displayFormat="yyyy-MM-dd" value="%{searchFromDate}" language="UTF-8"/> —— <sx:datetimepicker name="searchEndDate"
							displayFormat="yyyy-MM-dd" value="%{searchEndDate}" language="UTF-8"/> 
						</td>
					</tr>
					<tr>
						<th width="20%">
							所属集团
						</th>
						<td width="80%">
								<select name="jituanId" onchange="submitSearchForm(searchForm,'group')">
									<option value="0" <s:if test='jituanId==0'>selected</s:if>>
										-请选择集团-
									</option>
									<s:iterator value="jituanList" var="jituan">
										<option value="<s:property value="#jituan.orgId"/>" 
										<s:if test='jituanId==#jituan.orgId'>selected</s:if>>
											<s:property value="#jituan.name" />
										</option>
									</s:iterator>
								</select>
						</td>
					</tr>
					<tr>
						<th width="20%">
							所属分组机构
						</th>
						<td width="80%">
							<select name="searchGroupId">
								<option value="0" <s:if test='searchGroupId==0'>selected</s:if>>
									-请选择分组机构-
								</option>
								<s:iterator value="jituanGroupList" var="group">
									<option value="<s:property value="#group.orgId"/>" 
									<s:if test='searchGroupId==#group.orgId'>selected</s:if>>
										<s:property value="#group.name" />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							目标类型
						</th>
						<td>
							<select name="carType">
								<option value="0" <s:if test='carType==0'>selected</s:if>>
									-请选择目标类型-
								</option>
								<s:iterator value="carInfoList" var="carInfo">
										<option value="<s:property value="#carInfo.carTypeId"/>"
										<s:if test='carType==#carInfo.carTypeId'>selected</s:if> >
											<s:property value="#carInfo.typeName" />
										</option>
								</s:iterator>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							网络状态
						</th>
						<td>
							<select name="onoff">
								<option value="2" <s:if test='onoff==2'>selected</s:if>>
									-请选择网络状态-
								</option>
								<option value="1" <s:if test='onoff==1'>selected</s:if>>
									在线
								</option>
								<option value="0" <s:if test='onoff==0'>selected</s:if>>
									离线
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<a href="#" onclick="submitSearchForm(searchForm,'submit')" class="cssbtn"><img src="<%=path%>/images/search.gif"/>搜索</a>							
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	
	<form action="Admin_excelExport" method="post" name="exportForm">
	<input type="hidden" id="cols" name="cols" value="" />
	<div class="content" style="margin:auto auto;">
	<h6 >
		<span style="float:left;">选择导出项</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="checkForm(exportForm)" class="cssbtn" style="float:left;margin-top:8px;">
		<img src="<%=path%>/images/edit.gif"/>导出数据</a>
	</h6>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="inputtable">
		<tr>
			<td><input type="checkbox" name="colcheck" value="1" />&nbsp;终端编号</td>
			<td><input type="checkbox" name="colcheck" value="2" />&nbsp;终端SIM卡号</td>
			<td><input type="checkbox" name="colcheck" value="3" />&nbsp;目标序列号</td>
			<td><input type="checkbox" name="colcheck" value="4" />&nbsp;目标类型</td>
			<td><input type="checkbox" name="colcheck" value="5" />&nbsp;目标型号</td>
			<td><input type="checkbox" name="colcheck" value="6" />&nbsp;注册日期</td>
			<td><input type="checkbox" name="colcheck" value="7" />&nbsp;服务起始日期</td>
			<td><input type="checkbox" name="colcheck" value="8" />&nbsp;服务结束日期</td>
			<td><input type="checkbox" name="colcheck" value="9" />&nbsp;锁机状态</td>
			<td><input type="checkbox" name="colcheck" value="10" />&nbsp;用户名</td>
			<td><input type="checkbox" name="colcheck" value="11" />&nbsp;联系电话</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="colcheck" value="12" />&nbsp;债权责任人</td>
			<td><input type="checkbox" name="colcheck" value="13" />&nbsp;所属集团</td>
			<td><input type="checkbox" name="colcheck" value="14" />&nbsp;所属分组</td>
			<td><input type="checkbox" name="colcheck" value="15" />&nbsp;工作状态</td>
			<td><input type="checkbox" name="colcheck" value="16" />&nbsp;工作时间累积(分钟)</td>
			<td><input type="checkbox" name="colcheck" value="17" />&nbsp;电瓶电压</td>
			<td><input type="checkbox" name="colcheck" value="18" />&nbsp;信号强度</td>
			<td><input type="checkbox" name="colcheck" value="19" />&nbsp;网络状态</td>
			<td><input type="checkbox" name="colcheck" value="20" />&nbsp;离线时间</td>
			<td><input type="checkbox" name="colcheck" value="21" />&nbsp;当前定位信息</td>
		</tr>
	</table>
	</div>
	<h6 style="float:left;display:inline;">
		预览结果
	</h6>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable" style="text-align:center;">
	  <tr>
	    
	    <th scope="col">&nbsp;终端编号</th>
	    <th scope="col">&nbsp;目标序列号</th>
	    <th scope="col">&nbsp;目标类型</th>
	    <th scope="col">&nbsp;入网时间</th>
	    <th scope="col">&nbsp;所属集团</th>
	    <th scope="col">&nbsp;所属分组</th>
	    
	  </tr>
	  
	  <s:iterator value="terminalExcelList" var="terminal" status="status">
	  <tr
	 	 <s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
          <s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>
       >
	    <td><s:property value="#terminal.sim"/></td>
	    <td><s:property value="#terminal.carnumber"/></td>
	    <td><s:property value="#terminal.TCarInfo.typeName"/></td>
	    <td><s:date name="#terminal.registertime" format="yyyy-MM-dd" /></td>
	    <td><s:property value="#terminal.TOrgainzation.TOrgainzation.name"/></td>
	    <td><s:property value="#terminal.TOrgainzation.name"/></td>
	  </tr>
	  </s:iterator>
	</table>
	</form>
	<form action="Admin_findTerminalExcelList" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="Admin_findTerminalExcelList"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="Admin_findTerminalExcelList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="Admin_findTerminalExcelList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="Admin_findTerminalExcelList"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
		                  <td width="100"><div align="center"><span class="STYLE1">转到第
		                    <input name="pageIndex" type="text" size="4" style="height:12px; width:20px; border:1px solid #999999;" /> 
		                    	页 </span></div></td>
		                  <td width="40">
		                  	<a href="#" onclick="submitPageForm()"><img src="images/go.gif" width="37" height="15"/></a>
		                  </td>
		                </tr>
		            </table>
		            &nbsp;&nbsp;共有 <s:property value="total"/> 条记录，当前第<s:property value="pageIndex"/>/<s:property value="pages"/> 页
		</div>
	</form>
			
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
