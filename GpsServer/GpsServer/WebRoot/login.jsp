<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>中图科技-GPS定位服务平台</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<link href="<%=path %>/css/public.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/login.css" rel="stylesheet" type="text/css" />
<STYLE type=text/css>
body				{margin:0px; font-size:12px; background-image:url(<%=path %>/images/bg_login.png); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
td, div				{font-size:12px;}		
#login				{color:white; background-image:url(<%=path %>/images/bg_login.png); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
#login #root		{background-image:url(<%=path %>/images/login_root.png);}
#login #main		{background-image:url(<%=path %>/images/login_main.png);}
#login * td			{color:white;}
#login a			{color:white; text-decoration:none;}
#login .textbox		{width:180px}
</STYLE>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
</HEAD>
<BODY>
<form name="form1" action="User_login" method="post">
<DIV id=div1>
  <TABLE id=login height="100%" cellSpacing=0 cellPadding=0 width=800 
align=center>
    <TBODY>
      <TR id=main>
        <TD>
          <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
              <TR height=30>
                <TD width=380>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD rowSpan=4>&nbsp;</TD>
                <TD>用户名：</TD>
                <TD>
                  <INPUT type="text" id="TxtUserName" maxLength="20" name="user.userid">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD>密　码：</TD>
                <TD>
                  <INPUT  type="password" id="TxtPassword" name="user.pwd" maxlength="20">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD>验证码：</TD>
                <TD vAlign=center colSpan=2>
                  <INPUT type="text" size="10" name="code" id="code1" maxlength="4">
                  &nbsp; <img src="code.jsp" style="width: 82px; height: 24px; line-height: 28px; background: url(image/inputbg.gif) repeat-x; border: solid 1px #d1d1d1; font-size: 9pt;">
				</TD>
              </TR>
              <TR height=40>
                <TD></TD>
                <TD align=right>
                  <INPUT id=btnLogin type=submit value=" 登 录 " name=btnLogin onClick="goFunction()">
                  <label id="formMessage" style="color: red;" style="height:30px">
						<s:property value="#request.result" />
					</label>
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=110>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
        </TD>
      </TR>
      <TR id=root height=104>
        <TD>&nbsp;</TD>
      </TR>
    </TBODY>
  </TABLE>
</DIV>
<DIV id=div2 style="DISPLAY: none"></DIV>
</form>
<script type="text/javascript">
	if (document.addEventListener) {//如果是Firefox
		document.addEventListener("keypress", goFunction2, true);
	} else {
		document.attachEvent("onkeypress", goFunction2);
	}
	function goFunction(d) {
		if (document.getElementById("TxtUserName").value == "") {
			alert("请输入用户名");
			return;
		}
		if (document.getElementById("TxtPassword").value == "") {
			alert("请输入密码");
			return;
		}
		if (document.getElementById("code1").value == "") {
			alert("请输入验证码");
			return;
		}
		document.form1.submit();
	}
	function goFunction2(evt) {
		if (evt.keyCode != 13)
			return;
		if (document.getElementById("TxtUserName").value == "") {
			alert("请输入用户名");
			return;
		}
		if (document.getElementById("TxtPassword").value == "") {
			alert("请输入密码");
			return;
		}

		if (document.getElementById("code1").value == "") {
			alert("请输入验证码");
			return;
		}
		document.form1.submit();
	}
	function selectit() {
		document.getElementById("TxtUserName").focus();
	}
</script>
</BODY>
</HTML>