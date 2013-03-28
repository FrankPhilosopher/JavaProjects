<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>终端用户管理</title>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
<script>
	//验证搜索表单，通过的话就提交表单
	function submitSearchForm(){
		if(document.searchForm.searchValue.value=="" || (/\s+/g.test(document.searchForm.searchValue.value))){
			window.alert("输入查询值!");
			return;
		}
		document.searchForm.submit();
	}

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
	//新增运维用户
	function openAddYunweiPage(){
		window.open ('Group_addTerminal', 'newwindow2', 'height=670, width=550, top=30,left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
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
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[系统管理 > 终端管理]</div>
		<div class="content">
		<form action="Group_userSearch" method="post" name="searchForm">
		  <div class="tool"> 
		   	<table border="0" >
			  <tr>
			    <td>按：</td>
			    <td>
			    <select name="option" >
								<option value="0" <s:if test='option==0'>selected</s:if>>
										-请选择搜索条件-
									</option>
									<option value="1" <s:if test='option==1'>selected</s:if> >
										目标编号
									</option>
									<option value="2" <s:if test='option==2'>selected</s:if>>
										联系人
									</option>
									<option value="3" <s:if test='option==3'>selected</s:if>>
										终端编号
									</option>
								</select>
			    </td>
			    <td>
			     <input type="text" value="<s:property value="value"/>" maxlength="" name="searchValue"/>
			    </td>
			    <td>
			    <a href="#" onclick="submitSearchForm()" class="cssbtn">
			    <img src="<%=path%>/images/search.gif"/>查询</a>
			    </td>
			    <td>
			    <a href="Group_addTerminal" class="cssbtn">
			    <img src="<%=path%>/images/add.gif"/>新增终端用户</a>
			    </td>
			  </tr>
			</table>
		  </div>
		</form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable">
	  <tr>
	    <th scope="col">终端编号</th>
	    <th scope="col">目标编号</th>
	    <th scope="col">SIM卡号</th>
	    <th scope="col">机器类型</th>
	    <th scope="col">机器型号</th>
	    <th scope="col">联系人</th>
	    <th scope="col">联系电话</th>
	    <th scope="col">责任人</th>
	    <th scope="col">入网时间</th>
	    <th scope="col">基本操作</th>
	  </tr>
	  
	  <s:iterator value="terminals" var="terminal" status="status">
	  <tr
	 	 <s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
          <s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>
          
          align="center"
       >
	    <td><s:property value="#terminal.sim"/></td>
	    <td><s:property value="#terminal.carnumber"/></td>
	    <td><s:property value="#terminal.phone"/></td>
	    <td><s:property value="#terminal.TCarInfo.typeName"/></td>
	    <td><s:property value="#terminal.model"/></td>
	    <td><s:property value="#terminal.username"/></td>
	    <td><s:property value="#terminal.cellphone"/></td>
	    <td><s:property value="#terminal.principal"/></td>
	    <td><s:property value="#terminal.registertime"/></td>
	    <td>
	    <s:if test="#session.user.modify == 1">
	    <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Group_editTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'>编辑</a>&nbsp; &nbsp;
	    <img src="<%=path%>/images/del.gif" width="16" height="16" />
	    	<a
								href='<s:url action="Group_deleteTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'
								onclick="return delConfirm()">删除</a>
	    </s:if>
	    <s:else>
	      <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Group_editTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'>查看</a>&nbsp; &nbsp;
	    </s:else>
	    </td>
	  </tr>
	  </s:iterator>
	</table>
	
	<form action="Group_userSearch" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="Group_userSearch"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="Group_userSearch"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="Group_userSearch"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="Group_userSearch"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
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
