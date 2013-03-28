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
<title>���ù���</title>
<link href="<%=path%>/css/page.css" rel="stylesheet" type="text/css" />
<script>
	//��֤��������ͨ���Ļ����ύ��
	function submitSearchForm(searchForm){
		if (document.searchForm.value.value == ""
			|| (/\s+/g.test(document.searchForm.value.value))) {
		window.alert("���������ѯֵ");
		return;
	}
	document.searchForm.submit();
	}

	//��֤��ҳ����ͨ���Ļ����ύ��
	function submitPageForm(){
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
	
</script>
</head>
<body>
	<div class="container_top"><span class="c_t_l"></span><span class="c_t_r"></span><img src="<%=path%>/images/tb.gif" width="16" height="16" />�㵱ǰ��λ�ã�[����ѹ��� > ���ó�ֵ> �ն˳�ֵ>�������]</div>
		<div class="content">
		  <div class="tool"> 
			<form action="YunWei_terminalExpenseSearch?pageIndex=1" method="post" name="searchForm">
			   	<table border="0" >
				  <tr>
				   <td>
								����
							</td>
							<td>
								<select name="option">
									<option value="0" <s:if test='option==0'>selected</s:if>>
										-��ѡ����������-
									</option>
									<option value="1" <s:if test='option==1'>selected</s:if>>
										Ŀ����
									</option>
									
									<option value="3" <s:if test='option==3'>selected</s:if>>
										�ն˱��
									</option>
								</select>
							</td>
							<td>
								<input type="text" value="<s:property value="value"/>"
									maxlength="" name="value" />
							</td>
							<td>
								<a href="#" onclick="submitSearchForm()" class="cssbtn"> <img
										src="<%=path%>/images/search.gif" />��ѯ</a> &nbsp;&nbsp;
							</td>
				  </tr>
				</table>
			</form>
		  </div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datatable" style="text-align:center;">
	  <tr>
	    <th scope="col">�ն˱��</th>
	    <th scope="col">Ŀ����</th>
	    <th scope="col">Ŀ������</th>
	    <th scope="col">ע��ʱ��</th>
	    <th scope="col">����ʼʱ��</th>
	    <th scope="col">�������ʱ��</th>
	    <th scope="col">��������</th>
	  </tr>
	  
	  <s:iterator value="terminalList" var="terminal" status="status">
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
	    <td><s:date name="#terminal.registertime" format="yyyy-MM-dd hh:mm" /></td>
	    <td><s:date name="#terminal.startTime" format="yyyy-MM-dd hh:mm" /></td>
	    <td><s:date name="#terminal.endTime" format="yyyy-MM-dd hh:mm" /></td>
	    <td><img src="<%=path%>/images/edt.gif" width="16" height="16" /><a href='<s:url action="YunWei_terminalExpenseAdd"><s:param name="terminalId" value="#terminal.id"/></s:url>'>��ֵ</a>&nbsp; &nbsp;</td>
	  </tr>
	  </s:iterator>
	</table>
	
	<form action="YunWei_terminalExpenseSearch" method="post" name="pageForm">
		<div class="tablefoot">
					<table border="0" align="right" cellpadding="0" cellspacing="0" class="right">
		                <tr>
		                  <td width="40"><a href="<s:url action="YunWei_terminalExpenseSearch"><s:param name="pageIndex" value="1"/></s:url>"><img src="<%=path%>/images/first.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="YunWei_terminalExpenseSearch"><s:param name="pageIndex" value="pageIndex-1"/></s:url>"><img src="<%=path%>/images/back.gif"/></a></td>
		                  <td width="45"><a href="<s:url action="YunWei_terminalExpenseSearch"><s:param name="pageIndex" value="pageIndex+1"/></s:url>"><img src="<%=path%>/images/next.gif"/></a></td>
		                  <td width="40"><a href="<s:url action="YunWei_terminalExpenseSearch"><s:param name="pageIndex" value="pages"/></s:url>"><img class="btnImg" src="<%=path%>/images/last.gif"/></a></td>
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
