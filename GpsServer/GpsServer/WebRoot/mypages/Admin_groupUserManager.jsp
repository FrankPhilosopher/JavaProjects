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
		<title>分组机构账户管理</title>
		<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
		<script>
//验证搜索表单，通过的话就提交表单
function submitSearchForm(searchForm) {
	if (searchForm.searchString.value == ""
			|| (/\s+/g.test(searchForm.searchString.value))) {
		window.alert("请输入要搜索的输入账户名称");
		return;
	}
	searchForm.submit();
}

//验证分页表单，通过的话就提交表单
function submitPageForm() {
	// TODO:这里第二个判断没看懂
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
	if (confirm("如果删除该账户信息，则所有关于该账户的记录都将被删除，确定删除么?")) {
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
			你当前的位置：[系统管理 > 分组用户管理> 账号管理]
		</div>
		<div class="content">
			<div class="tool">
				<form action="Admin_groupUserSearch?pageIndex=1" method="post"
					name="searchForm">
					<table border="0">
						<tr>
							<td>
								<a href='<s:url action="Admin_beforeAddGroupUser"></s:url>'
									class="cssbtn"><img src="<%=path%>/images/adduser.png" />新增账号</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="datatable" style="text-align: center;">
				<tr>
					<th scope="col">
						管理员账号
					</th>
					<th scope="col">
						联系人
					</th>
					<th scope="col">
						密码
					</th>
					<th scope="col">
						联系电话
					</th>
					<th scope="col">
						断油断电权限
					</th>
					<th scope="col">
						修改信息权限
					</th>
					<th scope="col">
						导出数据权限
					</th>
					<th scope="col">
						基本操作
					</th>
				</tr>

				<s:iterator value="groupUserList" var="groupUser" status="status">
					<tr
						<s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
						<s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>>
						<td>
							<s:property value="#groupUser.userid" />
						</td>
						<td>
							<s:property value="#groupUser.name" />
						</td>
						<td>
							<s:property value="#groupUser.pwd" />
						</td>
						<td>
							<s:property value="#groupUser.cellphone" />
						</td>
						<td>
							<s:property value="#groupUser.oilele" />
						</td>
						<td>
							<s:property value="#groupUser.modify" />
						</td>
						<td>
							<s:property value="#groupUser.export" />
						</td>
						<td>
							<img src="<%=path%>/images/edt.gif" width="16" height="16" />
							<a
								href='<s:url action="Admin_editGroupUserInfo"><s:param name="groupUserId" value="#groupUser.id"/></s:url>'>编辑</a>&nbsp;
							&nbsp;
							<img src="<%=path%>/images/del.gif" width="16" height="16" />
							<a
								href='<s:url action="Admin_deleteGroupUser"><s:param name="groupUserId" value="#groupUser.id"/></s:url>'
								onclick="return delConfirm()">删除</a>&nbsp; &nbsp;
						</td>
					</tr>
				</s:iterator>
			</table>

			<form action="Admin_findGroupUserList" method="post" name="pageForm">
				<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0"
						class="right">
						<tr>
							<td width="40">
								<a
									href="<s:url action="Admin_findGroupUserList"><s:param name="pageIndex" value="1"/></s:url>"><img
										src="<%=path%>/images/first.gif" />
								</a>
							</td>
							<td width="45">
								<a
									href="<s:url action="Admin_findGroupUserList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img
										src="<%=path%>/images/back.gif" />
								</a>
							</td>
							<td width="45">
								<a
									href="<s:url action="Admin_findGroupUserList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img
										src="<%=path%>/images/next.gif" />
								</a>
							</td>
							<td width="40">
								<a
									href="<s:url action="Admin_findGroupUserList"><s:param name="pageIndex" value="pages"/></s:url>"><img
										class="btnImg" src="<%=path%>/images/last.gif" />
								</a>
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
								<a href="#"><img src="images/go.gif" width="37" height="15" />
								</a>
							</td>
							<!-- 不跳转，使其失效 -->
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
