<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
		<title>终端管理</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script>
//验证搜索表单，通过的话就提交表单
function submitSearchForm(searchForm) {
	///if(searchForm.searchString.value=="" || (/\s+/g.test(searchForm.searchString.value))){
	//window.alert("请输入要搜索的分组机构名称");
	//return;
	//}
	searchForm.submit();
}

//验证分页表单，通过的话就提交表单
function submitPageForm() {
	if (document.pageForm.pageIndex.value == ""
			|| (/\s+/g.test(document.pageForm.pageIndex.value))) {
		window.alert("输入页码");
		return;
	}
	if (isNaN(document.pageForm.pageIndex.value)) {
		window.alert("页码需要为数字");
		return;
	}
	document.pageForm.submit();
}
//提示确定删除
function delConfirm() {
	if (confirm("如果删除该客户信息，则所有关于该用户的记录都将被删除，确定删除么?")) {
		return true;
	} else {
		return false;
	}
}
</script>
	</head>
	<body>
		<div class="container_top">
			<span class="c_t_l"></span><span class="c_t_r"></span>
			<img src="<%=path%>/images/tb.gif" width="16" height="16" />
			你当前的位置：[分组机构管理 > 终端管理]
		</div>
		<div class="content">
			<div class="tool">
				<form action="JiTuan_terminalSearch" method="post" name="searchForm">
					<table border="0">
						<tr>
							<td>
								<select name="searchType">
									<option value="0" <s:if test='searchType==0'>selected</s:if>>
										-请选择搜索条件-
									</option>
									<option value="1" <s:if test='searchType==1'>selected</s:if>>
										终端编号
									</option>
									<option value="2" <s:if test='searchType==2'>selected</s:if>>
										目标编号
									</option>
								</select>
							</td>
							<td>
								<input type="text" name="searchString"
									value="<s:property value="searchString"/>" />
							</td>
							<td>
								<a href="#" onclick="submitSearchForm(searchForm)"
									class="cssbtn"><img src="<%=path%>/images/search.gif" />查询</a>
							</td>
							<td>
								<a href='<s:url action="JiTuan_beforeAddTerminal"></s:url>'
									class="cssbtn"><img src="<%=path%>/images/adduser.png" />新增终端</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="datatable" style="text-align: center;">
				<tr>
					<th scope="col">
						终端编号
					</th>
					<th scope="col">
						目标编号
					</th>
					<th scope="col">
						联系人
					</th>
					<th scope="col">
						责任人
					</th>
					<th scope="col">
						所属地区
					</th>
					<th scope="col">
						网络状态
					</th>
					<th scope="col">
						工时累计
					</th>
					<th scope="col">
						电瓶电压
					</th>
					<th scope="col">
						信号强度
					</th>
					<th scope="col">
						锁机状态
					</th>
					<th scope="col">
						基本操作
					</th>
				</tr>

				<s:iterator value="terminalList" var="terminal" status="status">
					<tr
						<s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
						<s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>>
						<td>
							<s:property value="#terminal.sim" />
						</td>
						<td>
							<s:property value="#terminal.carnumber" />
						</td>
						<td>
							<s:property value="#terminal.username" />
						</td>
						<td>
							<s:property value="#terminal.principal" />
						</td>
						<td>
							<s:property value="#terminal.TArea.name" />
						</td>
						<td>
							<s:if test="#terminal.netstatus == 1">
	    						在线
	    					</s:if>
							<s:else>
	    						信号弱或丢失电源
	    					</s:else>
						</td>
						<td>
							<s:property value="#terminal.worktime/60" />
							小时
							<s:property value="#terminal.worktime%60" />
							分
						</td>
						<td>
							<s:property value="#terminal.elepress" />
						</td>
						<td>
							<s:property value="#terminal.signal" />
						</td>
						<td>
							<s:if test="#terminal.lock == 2">
					    		低级锁机
					    	</s:if>
							<s:elseif test="#terminal.lock == 1">
					    		高级锁机
					    	</s:elseif>
							<s:else>
					    		正常
					    	</s:else>
						</td>
						<td>
							<img src="<%=path%>/images/edt.gif" width="16" height="16" />
							<a
								href='<s:url action="Terminal_locationCheck"><s:param name="sim" value="#terminal.sim"/></s:url>'>定位查询</a>&nbsp;
							<img src="<%=path%>/images/edt.gif" width="16" height="16" />
							<a
								href='<s:url action="Terminal_moveSecond"><s:param name="sim" value="#terminal.sim"/></s:url>'>锁定跟踪</a>&nbsp;
							<img src="<%=path%>/images/edt.gif" width="16" height="16" />
							<a
								href='<s:url action="Terminal_orbitCheck"><s:param name="sim" value="#terminal.sim"/></s:url>'>轨迹查询</a>&nbsp;
							<s:if test="#session.user.oilele == 1">
							<img src="<%=path%>/images/edt.gif" width="16" height="16" />
							<a
								href='<s:url action="Terminal_priority"><s:param name="sim" value="#terminal.sim"/></s:url>'>特权操作</a>&nbsp;
							</s:if>
							<s:if test="#session.user.modify == 1">
								<img src="<%=path%>/images/edt.gif" width="16" height="16" />
								<a
									href='<s:url action="JiTuan_editTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'>编辑</a>&nbsp;
							<img src="<%=path%>/images/del.gif" width="16" height="16" />
								<a
									href='<s:url action="JiTuan_deleteTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'
									onclick="return delConfirm()">删除</a>&nbsp;
								</s:if>
						</td>
					</tr>
				</s:iterator>
			</table>

			<form action="JiTuan_findTerminalList" method="post" name="pageForm">
				<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0"
						class="right">
						<tr>
							<td width="40">
								<a
									href="<s:url action="JiTuan_findTerminalList"><s:param name="pageIndex" value="1"/></s:url>"><img
										src="<%=path%>/images/first.gif" /> </a>
							</td>
							<td width="45">
								<a
									href="<s:url action="JiTuan_findTerminalList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img
										src="<%=path%>/images/back.gif" /> </a>
							</td>
							<td width="45">
								<a
									href="<s:url action="JiTuan_findTerminalList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img
										src="<%=path%>/images/next.gif" /> </a>
							</td>
							<td width="40">
								<a
									href="<s:url action="JiTuan_findTerminalList"><s:param name="pageIndex" value="pages"/></s:url>"><img
										class="btnImg" src="<%=path%>/images/last.gif" /> </a>
							</td>
							<td width="100">
								<div align="center">
									<span class="STYLE1">转到第 <input name="pageIndex"
											type="text" size="4" maxlength="4"
											style="height: 12px; width: 20px; border: 1px solid #999999;" />
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
