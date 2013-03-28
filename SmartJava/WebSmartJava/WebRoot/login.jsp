<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>SmartJava物流管理系统</title>
    <link href="style/alogin.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form name="form1" id="form1" method="post" action="actionLogin.jsp?action=login">
    <div class="Main">
        <ul>
            <li class="top"></li>
            <li class="top2"></li>
            <li class="topA"></li>
            <li class="topB"><span>
                <img src="images/login/car.png" width="200" height="140" alt="" style="margin-left:50px;" />
                <br/>                
            </span>
            </li>        
            
            <li class="topC"></li>
            <li class="topD">
                <ul class="login">
                	<li><%
                        	if(request.getAttribute("message") != null)
                        		out.print(request.getAttribute("message"));
                         %></li>
                    <li><span class="left">账户</span> <span style="left">
                        &nbsp;&nbsp;<input id="Text1" name="name" type="text" class="txt" />  
                    </span><br/></li>
                    <li><span class="left">密码</span> <span style="left">
                       &nbsp;&nbsp;<input id="Text2" name="password" type="password" class="txt" />  
                    </span><br/></li>
                         <li><span class="left">验证码</span> <span style="left">
                       &nbsp;&nbsp;<input id="Text3" type="text" class="txtCode" name="code" />
                       <img src="code.jsp" style="width: 82px; height: 22px; line-height: 22px; background: url(image/inputbg.gif) repeat-x; border: solid 1px #d1d1d1; font-size: 9pt;" />
                    </span>                             
                    </li>
                </ul>
            </li>
            <li class="topE"></li>
            <li class="middle_A">  </li>
            <li class="middle_B">
            	<span style="font-family:Georgia, 'Times New Roman', Times, serif;font-size:20px;text-align:center;margin-left:30px;color:#09F;"> Smart Java 物流管理系统 </span>
            </li>
            <li class="middle_C">
            <span class="btn" style="cursor:pointer;">               
                <img alt="login" src="images/login/btnlogin.gif" style="padding-top:20px;" onclick="form1.submit();"  />
                &nbsp;&nbsp;<a href="register.jsp" style="font-size:large;font-family:fantasy;">用户注册</a>
            </span>
            </li>
            <li class="middle_D">  </li>
            <li class="bottom_A">   </li>
            <li class="bottom_B">
            Copyright &copy;2012 中软实训第六小组
            </li>
        </ul>
    </div>
    </form>
</body>
</html>
