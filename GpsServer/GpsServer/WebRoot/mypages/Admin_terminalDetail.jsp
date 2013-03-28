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
		<sx:head />
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
		
		</script>
	</head>

	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[系统管理 > 终端详情查询]
		</div>
	<form action="Admin_terminalDetailSearch" method="post" name="searchForm">
		<div class="content">
			<h6>
				搜索条件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</h6>
			<div class="b1" id="searchDiv" style="display:block;">
				<table width="96%" class="inputtable">
					<tr>
						<th style="color:red;">搜索条件：</th>
						<td>
							<select name="searchWeiyi" style="float:left;">
								<option value="0" <s:if test='searchWeiyi==0'>selected</s:if>>
									-请选择搜索类型-
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
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text" name="searchString" value="<s:property value="searchString"/>" size=40 style="float:left;margin-left:10px;"/>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="submitSearchForm(searchForm)" class="cssbtn" style="float:left;"><img src="<%=path%>/images/search.gif"/>搜索</a>							
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	
	<div class="content" style="margin:auto auto;">
		<h6>
			终端详细信息
		</h6>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="inputtable">
			<tr>
				<th scope="row">
					终端ID：
				</th>
				<td>
					<s:property value="terminal.id"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					终端编号：
				</th>
				<td>
					<s:property value="terminal.sim"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					终端SIM卡号：
				</th>
				<td>
					<s:property value="terminal.phone"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					目标序列号：
				</th>
				<td>
					<s:property value="terminal.carnumber"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					目标类型：
				</th>
				<td> 
					<s:property value="terminal.TCarInfo.typeName"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					目标型号：
				</th>
				<td> 
					<s:property value="terminal.model"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					注册入网时间：
				</th>
				<td>
					<s:date name="terminal.registertime" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<th scope="row">
					服务开始时间：
				</th>
				<td>
					<s:date name="terminal.startTime" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<th scope="row">
					服务结束时间：
				</th>
				<td>
					<s:date name="terminal.endTime" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<th scope="row">
					锁机状态：
				</th>
				<td>
					<s:if test="terminal.lock == 1">低级锁机</s:if>
					<s:elseif test="terminal.lock == 2">高级锁机</s:elseif>
					<s:elseif test="terminal.lock == 0">正常</s:elseif>
					<s:else></s:else>
				</td>
			</tr>
			<tr>
				<th scope="row">
					用户名：
				</th>
				<td>
					<s:property value="terminal.username"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					联系手机：
				</th>
				<td>
					<s:property value="terminal.cellphone"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					债券责任人：
				</th>
				<td>
					<s:property value="terminal.principal"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					所属集团：
				</th>
				<td>
					<s:property value="terminal.TOrgainzation.TOrgainzation.name"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					所属分组：
				</th>
				<td>
					<s:property value="terminal.TOrgainzation.name"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					工作状态：
				</th>
				<td>
					<s:if test="terminal.status == 1">工作</s:if>
					<s:elseif test="terminal.status == 0">空闲</s:elseif>   
					   <s:else></s:else>
				</td>
			</tr>
			<tr>
				<th scope="row">
					工作时间积累：
				</th>
				<td>
					<s:property value="terminal.worktime"/> &nbsp;&nbsp;&nbsp;&nbsp;分钟
				</td>
			</tr>
			<tr>
				<th scope="row">
					电瓶电压：
				</th>
				<td>
					<s:property value="terminal.elepress"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					信号强度：
				</th>
				<td>
					<s:property value="terminal.signal"/>
				</td>
			</tr>
			<tr>
				<th scope="row">
					网络状态：
				</th>
				<td>
					<s:if test="terminal.netstatus == 1">在线</s:if>
					<s:elseif test="terminal.netstatus == 0">离线</s:elseif>
					<s:else></s:else>
				</td>
			</tr>
			<tr>
				<th scope="row">
					离线时间：
				</th>
				<td>
					<s:property value="terminal.remark"/>
				</td>
			</tr>
		</table>
	</div>
		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
