<%@page import="entity.User"%>
<%@page import="manager.*"%>
<%@page import="entity.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	System.out.println(action);
	if (action != null) {
		if (action.equals("login")) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String code = request.getParameter("code");		
			String realcode = (String)session.getAttribute("code");
			UserManager userManager = new UserManager();
			User user = userManager.searchByName(name);
			if(code ==null || !realcode.equalsIgnoreCase(code) || user == null || (user!=null && !user.getPassword().equalsIgnoreCase(password))){
				request.setAttribute("message", "登录失败！");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}else{
				session.setAttribute("loginuser", user);//将登录的用户保存到session中，属性名称是loginuser
				if(user.getPriority() == User.COMPANY_ADMIN || user.getPriority() == User.COMPANY_WORKER){//省公司管理员或省公司员工
					RequestDispatcher rd = request.getRequestDispatcher("indexcompany.jsp");
					rd.forward(request, response);					
				}else if(user.getPriority() == User.DISTRIBUTION_ADMIN || user.getPriority() == User.DISTRIBUTION_WORKER){//配送点管理员或配送点员工
					Distribution currentDistribution=DistributionManager.searchById(user.getDistributionId());
					session.setAttribute("currentDistribution",currentDistribution);//将当前配送点放入session
					RequestDispatcher rd = request.getRequestDispatcher("indexdistribution.jsp");
					rd.forward(request, response);					
				}else if(user.getPriority() == User.CUSTOMER){
					RequestDispatcher rd = request.getRequestDispatcher("indexcustomer.jsp");
					rd.forward(request, response);
				}
			}		
		}else if(action.equals("register")){
			String name = request.getParameter("name");
			String realname = request.getParameter("realname");
			String password = request.getParameter("password");
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setRealname(realname);
			user.setPriority(User.CUSTOMER);
			UserManager userManager = new UserManager();
			if(userManager.add(user)>0){
				request.setAttribute("message", "注册成功！");
			}else{
				request.setAttribute("message", "注册失败！");
			}
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
		}
	}
%>