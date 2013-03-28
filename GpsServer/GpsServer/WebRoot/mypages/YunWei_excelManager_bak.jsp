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
		function submitSearchForm(searchForm){
			searchForm.submit();
		}
		
		//验证表单
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
	<form action="YunWei_searchTerminalExcelList" method="post" name="searchForm">
		<div class="content">
			<h6>
				搜索条件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="searchA" onclick="sliderDiv()" style="text-decoration:underline;color:red;cursor:pointer;">[隐藏搜索条件]</span>
				&nbsp;&nbsp;&nbsp;&nbsp;<label style="color:red;margin-top:10px;line-height:40px;">如果选中模糊搜索，组合搜索的条件将失效！</label>
			</h6>
			<div class="b1" id="searchDiv" style="display:block;">
				<table width="96%" class="inputtable">
					<tr>
						<th style="color:red;">模糊搜索条件：</th>
						<td>
							<select name="searchWeiyi">
								<option value="0" <s:if test='searchWeiyi==0'>selected</s:if>>
									-请选择模糊搜索类型-
								</option>
								<option value="1" <s:if test='searchWeiyi==1'>selected</s:if>>
									终端编号
								</option>
								<option value="2" <s:if test='searchWeiyi==2'>selected</s:if>>
									终端手机号
								</option>
								<option value="3" <s:if test='searchWeiyi==3'>selected</s:if>>
									目标序列号
								</option>
								<option value="4" <s:if test='searchWeiyi==4'>selected</s:if>>
									用户名称
								</option>
								<option value="5" <s:if test='searchWeiyi==5'>selected</s:if>>
									债券责任人
								</option>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text" name="searchString" value="<s:property value="searchString"/>" size=40/>
						</td>
					</tr>
					<tr>
						<th style="color:red;">组合性搜索条件：</th>
						<td>入网时间&nbsp;&nbsp;&nbsp;&nbsp;<sx:datetimepicker name="searchFromDate"
							displayFormat="yyyy-MM-dd" value="%{searchFromDate}" language="UTF-8"/> —— <sx:datetimepicker name="searchToDate"
							displayFormat="yyyy-MM-dd" value="%{searchToDate}" language="UTF-8"/> 
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
								<s:iterator value="groupList" var="group">
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
							在线状态
						</th>
						<td>
							<select name="onoff">
								<option value="2" <s:if test='onoff==2'>selected</s:if>>
									-请选择在线状态-
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
							<a href="#" onclick="submitSearchForm(searchForm)" class="cssbtn"><img src="<%=path%>/images/search.gif"/>搜索</a>							
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	<form action="YunWei_excelExport" method="post" name="exportForm">
	<input type="hidden" id="cols" name="cols" value="" />
	<h6 style="float:left;display:inline;">
		搜索结果
	</h6>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="checkForm(exportForm)" class="cssbtn" style="margin-top:10px;"><img src="<%=path%>/images/edit.gif"/>导出数据</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label style="color:red;margin-top:10px;line-height:40px;">选中表格中的第一行表示要导出该列数据</label>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable" style="text-align:center;">
	  <tr>
	   <th scope="col"><input type="checkbox" name="colcheck" value="0" />&nbsp;ID</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="1" />&nbsp;终端编号</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="2" />&nbsp;目标序列号</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="3" />&nbsp;目标类型</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="4" />&nbsp;入网时间</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="5" />&nbsp;服务结束时间</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="6" />&nbsp;债券责任人</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="7" />&nbsp;所属集团</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="8" />&nbsp;所属分组</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="9" />&nbsp;定位信息</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="10" />&nbsp;工作状态</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="11" />&nbsp;电瓶电压</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="12" />&nbsp;信号强度</th>
	    <th scope="col"><input type="checkbox" name="colcheck" value="13" />&nbsp;离线时间</th>
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
	    <td><s:property value="#terminal.id"/></td>
	    <td><s:property value="#terminal.sim"/></td>
	    <td><s:property value="#terminal.carnumber"/></td>
	    <td><s:property value="#terminal.TCarInfo.typeName"/></td>
	    <td><s:date name="#terminal.registertime" format="yyyy-MM-dd" /></td>
	    <td><s:date name="#terminal.endTime" format="yyyy-MM-dd" /></td>
	    <td><s:property value="#terminal.principal"/></td>
	    <td><s:property value="#terminal.TOrgainzation.TOrgainzation.name"/></td>
	    <td><s:property value="#terminal.TOrgainzation.name"/></td>
	    <td><s:property value="#terminal.TTempPositions[0].locationModel"/></td>
	    <td><s:property value="#terminal.status"/></td>
	    <td><s:property value="#terminal.elepress"/></td>
	    <td><s:property value="#terminal.signal"/></td>
	    <td><s:property value="#terminal.remark"/></td>
	  </tr>
	  </s:iterator>
	</table>
	</form>
	<form action="YunWei_searchTerminalExcelList" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="YunWei_searchTerminalExcelList"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="YunWei_searchTerminalExcelList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="YunWei_searchTerminalExcelList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="YunWei_searchTerminalExcelList"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
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
			
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
