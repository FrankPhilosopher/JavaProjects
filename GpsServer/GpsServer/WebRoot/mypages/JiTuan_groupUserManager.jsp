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
<title>��������˻�����</title>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
<script>
	//��֤��������ͨ���Ļ����ύ��
	function submitSearchForm(searchForm){
		//if(searchForm.searchString.value=="" || (/\s+/g.test(searchForm.searchString.value))){
			//window.alert("������Ҫ�����������˻�����");
			//return;
		//}
		searchForm.submit();
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
	
	//��ʾȷ��ɾ��
	function delConfirm(){
		if(confirm("���ɾ�����˻���Ϣ�������й��ڸ��˻��ļ�¼������ɾ����ȷ��ɾ��ô?")){
			return true;
		}else{
			return false;
		}
	}
</script>
</head>
<body>
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />�㵱ǰ��λ�ã�[ϵͳ���� > �����û�����> �˺Ź���]</div>
		<div class="content">
		  <div class="tool"> 
			<form action="JiTuan_groupUserSearch" method="post" name="searchForm">
			   	<table border="0" >
				  <tr>
				  <!-- 
				    <td> �˺����ƣ�</td>
				    <td><input type="text" name="searchString" value="<s:property value="searchString"/>" /></td>
				    <td><a href="#" onclick="submitSearchForm(searchForm)" class="cssbtn"><img src="<%=path%>/images/search.gif"/>��ѯ</a></td>
				   -->
				  	<td><a href='<s:url action="JiTuan_beforeAddGroupUser"></s:url>' class="cssbtn"><img src="<%=path%>/images/adduser.png"/>�����˺�</a></td>
				  </tr>
				</table>
			</form>
		  </div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable" style="text-align:center;">
	  <tr>
	    <th scope="col">����Ա�˺�</th>
	    <th scope="col">��ϵ��</th>
	    <th scope="col">����</th>
	    <th scope="col">��ϵ�绰</th>
	    <th scope="col">���Ͷϵ�Ȩ��</th>
	    <th scope="col">�޸���ϢȨ��</th>
	    <th scope="col">��������Ȩ��</th>
	    <th scope="col">��������</th>
	  </tr>
	  
	  <s:iterator value="groupUserList" var="groupUser" status="status">
	  <tr
	 	 <s:if test="#status.even">
           bgcolor="#dddddd"
          </s:if>
          <s:elseif test="#status.odd">
           bgcolor="#ffffff"
          </s:elseif>
       >
	    <td><s:property value="#groupUser.userid"/></td>
	    <td><s:property value="#groupUser.name"/></td>
	    <td><s:property value="#groupUser.pwd"/></td>
	    <td><s:property value="#groupUser.cellphone"/></td>
	    <td><s:property value="#groupUser.oilele"/></td>
	    <td><s:property value="#groupUser.modify"/></td>
	    <td><s:property value="#groupUser.export"/></td>
	    <td><img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="JiTuan_editGroupUserInfo"><s:param name="groupUserId" value="#groupUser.id"/></s:url>'>�༭</a>&nbsp; &nbsp;<img src="<%=path%>/images/del.gif" width="16" height="16" /><a href='<s:url action="JiTuan_deleteGroupUser"><s:param name="groupUserId" value="#groupUser.id"/></s:url>' onclick="return delConfirm()">ɾ��</a>&nbsp; &nbsp;</td>
	  </tr>
	  </s:iterator>
	</table>
	
	<form action="JiTuan_findGroupUserList" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="JiTuan_findGroupUserList"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="JiTuan_findGroupUserList"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="JiTuan_findGroupUserList"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="JiTuan_findGroupUserList"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
		                </tr>
		            </table>
		            &nbsp;&nbsp;���� <s:property value="total"/> ����¼����ǰ��<s:property value="pageIndex"/>/<s:property value="pages"/> ҳ
		</div>
	</form>
</div>


<div class="container_bottom"><span class="c_b_l"></span><span class="c_b_r"></span></div>
</body>
</html>
