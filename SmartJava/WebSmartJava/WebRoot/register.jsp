<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户注册-SmartJava物流管理系统</title>
    <link href="style/alogin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" >
		function check(){
			if(form1.realname.value.length==0 || form1.name.value.length==0 || form1.password.value.length==0){
				document.getElementById("error").innerHTML = "亲，你还没有输入完呢";
				return false;
			}else{
				form1.submit();				
			}
		}	
	</script>
</head>
<body>
    <form id="form1" action="actionLogin.jsp?action=register" method="post">
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
            
            <li class="topC">
			</li>
            <li class="topD">
                <ul class="login">
                	<li><label name="message" id="error">
						<%
							String message = (String)request.getAttribute("message");//null
							if(message!=null){
								out.print(message);
							}
						 %>
				</label><br/></li>
                    <li><span class="left">账 号</span> <span style="left">
                        &nbsp;&nbsp;<input id="Text1" type="text" class="txt" name="name" />  
                    </span><br/></li>
                    <li><span class="left">姓 名</span> <span style="left">
                       &nbsp;&nbsp;<input id="Text2" type="text" class="txt" name="realname"/>  
                    </span><br/></li>
                    <li><span class="left">密 码</span> <span style="left">
                       &nbsp;&nbsp;<input id="Text3" type="text" class="txt" name="password" />  &nbsp;&nbsp; 
                    </span><br/></li>
                </ul>
            </li>
            <li class="topE"></li>
            <li class="middle_A"></li>
            <li class="middle_B">
            	<span style="font-family:Georgia, 'Times New Roman', Times, serif;font-size:20px;text-align:center;margin-left:30px;color:#09F;"> Smart Java 物流管理系统 </span>
            </li>
            <li class="middle_C">
            <span class="btn" style="cursor:pointer;margin-top:20px;">               
                <img alt="login" src="images/register_22.gif"  onclick="check()"  />&nbsp;&nbsp;<a href="login.jsp" style="font-size:large;font-family:fantasy;">返回登录</a>
            </span>
            </li>
            <li class="middle_D"></li>
            <li class="bottom_A"></li>
            <li class="bottom_B">
            Copyright &copy;2012 中软实训第六小组
            </li>
        </ul>
    </div>
    </form>
</body>
</html>
