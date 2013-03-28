<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
			window.alert("请输入查询值！");
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
		window.open ('Admin_addYunwei', 'newwindow', 'height=400, width=480, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
	}
	//提示确定删除
	function delConfirm(){
		if(confirm("如果删除该客户信息，则所有关于该用户的记录都将被删除，确定删除么?")){
			return true;
		}else{
			return false;
		}
	}
</script>
</head>
<body>
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />你当前的位置：[系统管理 >终端查询]</div>
		<div class="content">
		<form action="Group_terminalSearch" method="post" name="searchForm">
		  <div class="tool"> 
		   	<table border="0" >
			  <tr>
			   <td>按：</td>
			    <td>
			   <select name="option" >
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
			     <input type="text" name="searchValue" value="<s:property value="searchValue"/>" maxlength="20"/>
			    </td>
			    <td>
			    <a href="#" onclick="submitSearchForm()" class="cssbtn">
			    <img src="<%=path%>/images/search.gif"/>查询</a>
			    </td>
			  </tr>
			</table>
		  </div>
		</form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable">
	  <tr>
	    <th scope="col">终端编号</th>
	    <th scope="col">目标编号</th>
	    <th scope="col">联系人</th>
	    <th scope="col">责任人</th>
	    <th scope="col">
						所属地区
					</th>
	    <th scope="col">网络状态</th>
	    <th scope="col">工时累计</th>
	    <th scope="col">电瓶电压</th>
	    <th scope="col">信号强度</th>
	    <th scope="col">锁机状态</th>
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
	    <td><s:property value="#terminal.username"/></td>
	    <td><s:property value="#terminal.principal"/></td>
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
	    <td><s:property value="#terminal.worktime/60"/>小时<s:property value="#terminal.worktime%60"/>分</td>
	    <td><s:property value="#terminal.elepress"/></td>
	    <td><s:property value="#terminal.signal"/></td>
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
	    <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Terminal_locationCheck"><s:param name="sim" value="#terminal.sim"/></s:url>'>定位查询</a>&nbsp; &nbsp;
	    <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Terminal_moveSecond"><s:param name="sim" value="#terminal.sim"/></s:url>' >锁定跟踪</a>&nbsp; &nbsp;
	    <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Terminal_orbitCheck"><s:param name="sim" value="#terminal.sim"/></s:url>'>轨迹查询</a>&nbsp;&nbsp;
	    <s:if test="#session.user.oilele == 1">
	    <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Terminal_priority"><s:param name="sim" value="#terminal.sim"/></s:url>'>特权操作</a>
	    </s:if>
	    </td>
	  </tr>
	  </s:iterator>
	</table>
	
	<form action="Group_terminalSearch" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="Group_terminalSearch"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="Group_terminalSearch"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="Group_terminalSearch"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="Group_terminalSearch"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
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
