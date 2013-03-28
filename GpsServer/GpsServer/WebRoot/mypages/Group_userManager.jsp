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
<title>�ն��û�����</title>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
<script>
	//��֤��������ͨ���Ļ����ύ��
	function submitSearchForm(){
		if(document.searchForm.searchValue.value=="" || (/\s+/g.test(document.searchForm.searchValue.value))){
			window.alert("�����ѯֵ!");
			return;
		}
		document.searchForm.submit();
	}

	//��֤��ҳ����ͨ���Ļ����ύ��
	function submitPageForm(){
		// TODO:����ڶ����ж�û����
		if(document.pageForm.pageIndex.value=="" || (/\s+/g.test(document.pageForm.pageIndex.value))){
		   window.alert("����ҳ��");
		   return;
		}
		if(isNaN(document.pageForm.pageIndex.value)){
			window.alert("ҳ����ҪΪ����");
			return;
		}
		document.pageForm.submit();
	}
	//������ά�û�
	function openAddYunweiPage(){
		window.open ('Group_addTerminal', 'newwindow2', 'height=670, width=550, top=30,left=150, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
	}
	//��ʾȷ��ɾ��
function delConfirm() {
	if (confirm("���ɾ���ÿͻ���Ϣ�������й��ڸ��û��ļ�¼������ɾ����ȷ��ɾ��ô?")) {
		return true;
	} else {
		return false;
	}
}
</script>
</head>
<body>
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />�㵱ǰ��λ�ã�[ϵͳ���� > �ն˹���]</div>
		<div class="content">
		<form action="Group_userSearch" method="post" name="searchForm">
		  <div class="tool"> 
		   	<table border="0" >
			  <tr>
			    <td>����</td>
			    <td>
			    <select name="option" >
								<option value="0" <s:if test='option==0'>selected</s:if>>
										-��ѡ����������-
									</option>
									<option value="1" <s:if test='option==1'>selected</s:if> >
										Ŀ����
									</option>
									<option value="2" <s:if test='option==2'>selected</s:if>>
										��ϵ��
									</option>
									<option value="3" <s:if test='option==3'>selected</s:if>>
										�ն˱��
									</option>
								</select>
			    </td>
			    <td>
			     <input type="text" value="<s:property value="value"/>" maxlength="" name="searchValue"/>
			    </td>
			    <td>
			    <a href="#" onclick="submitSearchForm()" class="cssbtn">
			    <img src="<%=path%>/images/search.gif"/>��ѯ</a>
			    </td>
			    <td>
			    <a href="Group_addTerminal" class="cssbtn">
			    <img src="<%=path%>/images/add.gif"/>�����ն��û�</a>
			    </td>
			  </tr>
			</table>
		  </div>
		</form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable">
	  <tr>
	    <th scope="col">�ն˱��</th>
	    <th scope="col">Ŀ����</th>
	    <th scope="col">SIM����</th>
	    <th scope="col">��������</th>
	    <th scope="col">�����ͺ�</th>
	    <th scope="col">��ϵ��</th>
	    <th scope="col">��ϵ�绰</th>
	    <th scope="col">������</th>
	    <th scope="col">����ʱ��</th>
	    <th scope="col">��������</th>
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
	    <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Group_editTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'>�༭</a>&nbsp; &nbsp;
	    <img src="<%=path%>/images/del.gif" width="16" height="16" />
	    	<a
								href='<s:url action="Group_deleteTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'
								onclick="return delConfirm()">ɾ��</a>
	    </s:if>
	    <s:else>
	      <img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="Group_editTerminal"><s:param name="sim" value="#terminal.sim"/></s:url>'>�鿴</a>&nbsp; &nbsp;
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
		                  <td width="100"><div align="center"><span class="STYLE1">ת����
		                    <input name="pageIndex" type="text" size="4" maxlength="4" style="height:12px; width:20px; border:1px solid #999999;" /> 
		                    	ҳ </span></div></td>
		                  <td width="40">
		                  	<a href="#" onclick="submitPageForm()"><img src="images/go.gif" width="37" height="15"/></a>
		                  </td>
		                </tr>
		            </table>
		            &nbsp;&nbsp;���� <s:property value="total"/> ����¼����ǰ��<s:property value="pageIndex"/>/<s:property value="pages"/> ҳ
		</div>
	</form>
</div>


<div class="container_bottom"><span class="c_b_l"></span><span class="c_b_r"></span></div>
</body>
</html>
