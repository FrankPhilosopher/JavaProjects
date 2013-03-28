<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<title>地区信息管理</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script>

	//验证分页表单，通过的话就提交表单
	function submitPageForm(){
		// TODO:这里第二个判断没看懂
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

	//提示确定删除
	function delConfirm(){
		if(confirm("如果删除该地区信息，则所有关于该地区的记录都将被删除，确定删除么?")){
			return true;
		}else{
			return false;
		}
	}
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[系统管理 > 地区信息管理]
		</div>
		<div class="content">
			<form action="Admin_areaSearch" method="post" name="searchForm">
				<div class="tool">
					<table border="0">
						<tr>
							<td>
								<a href='<s:url action="Admin_forwardAddArea"></s:url>'
									class="cssbtn"> <img src="<%=path%>/images/add.gif" />新增地区信息</a>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="datatable" style="text-align:center;">
				<tr>
					<th scope="col">
						地区编号
					</th>
					<th scope="col">
						地区名称
					</th>
					<th scope="col">
						基本操作
					</th>
				</tr>

				<s:iterator value="areas" var="area" status="status">
					<tr
						<s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
						<s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>>
						<td>
							<s:property value="#area.areaId" />
						</td>
						<td>
							<s:property value="#area.name" />
						</td>
						<td>
							<img src="<%=path%>/images/edt.gif" width="16" height="16" />
							<a
								href='<s:url action="Admin_editArea"><s:param name="areaId" value="#area.areaId"/></s:url>'>编辑</a>&nbsp;
							&nbsp;
							<img src="<%=path%>/images/del.gif" width="16" height="16" />
							<a
								href='<s:url action="Admin_deleteArea"><s:param name="areaId" value="#area.areaId"/></s:url>'
								onclick="return delConfirm()">删除</a>
						</td>
					</tr>
				</s:iterator>
			</table>

			<form action="Admin_areaManager" method="post" name="pageForm">
				<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0"
						class="right">
						<tr>
							<td width="40">
								<a
									href="<s:url action="Admin_areaManager"><s:param name="pageIndex" value="1"/></s:url>"><img
										src="<%=path%>/images/first.gif" /> </a>
							</td>
							<td width="45">
								<a
									href="<s:url action="Admin_areaManager"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img
										src="<%=path%>/images/back.gif" /> </a>
							</td>
							<td width="45">
								<a
									href="<s:url action="Admin_areaManager"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img
										src="<%=path%>/images/next.gif" /> </a>
							</td>
							<td width="40">
								<a
									href="<s:url action="Admin_areaManager"><s:param name="pageIndex" value="pages"/></s:url>"><img
										class="btnImg" src="<%=path%>/images/last.gif" /> </a>
							</td>
							<td width="100">
								<div align="center">
									<span class="STYLE1">转到第 <input name="pageIndex"
											type="text" size="4"
											style="height: 12px; width: 20px; border: 1px solid #999999;" maxlength="4"/>
										页 </span>
								</div>
							</td>
							<td width="40">
								<a href="#" onclick="submitPageForm()"><img
										src="images/go.gif" width="37" height="15" /> </a>
							</td>
						</tr>
					</table>
					&nbsp;&nbsp;共有
					<s:property value="total" />
					条记录，当前第
					<s:property value="pageIndex" />
					/
					<s:property value="pages" />
					页
				</div>
			</form>
		</div>


		<div class="container_bottom">
			<span class="c_b_l"></span><span class="c_b_r"></span>
		</div>
	</body>
</html>
