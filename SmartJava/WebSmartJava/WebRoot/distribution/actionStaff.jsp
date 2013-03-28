<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="manager.*,java.util.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	User loginUser = (User)session.getAttribute("loginuser");//得到登陆用户
	String action = request.getParameter("action");
	UserManager userManager  = new UserManager();
	System.out.println("值为：" + action);
	if (action != null) {
		if (action.equals("addStaff")) {//添加工作员工
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");
			
            User user=new User();
            user.setName(name);
            user.setPassword(password);
            user.setRealname(realname);
            user.setPhone(phone);
            user.setPriority(User.DISTRIBUTION_WORKER);
            //user.setDistributionId(loginUser.getDistributionId());
            user.setDistributionId(27);//TODO:hjw
            
            if(userManager.add(user)>0){
            	request.setAttribute("message", "添加成功！");
            }else{
             	request.setAttribute("message", "添加失败！");
            }
			RequestDispatcher rd = request.getRequestDispatcher("addStaff.jsp");
			rd.forward(request, response);
		}else if(action.equals("list")){//列表显示所有配送点员工
			ArrayList list = userManager.searchDistributionUser(27,0, null, null);//TODO:hjw
			request.setAttribute("userlist", list);
			RequestDispatcher rd = request.getRequestDispatcher("listStaff.jsp");
			rd.forward(request, response);
		}else if(action.equals("search")){//搜索配送点员工
			int type = Integer.parseInt(request.getParameter("type"));
			String value = request.getParameter("searchcontent");
			String key = "id";
			if(type==0){
				key = "realname";
			}else if(type==1){
				key = "id";
			}else if(type==2){
				key = "name";
			}else if(type==3){
				key = "phone";
			}
			ArrayList list = userManager.searchDistributionUser(27,1, key, value);//TODO:hjw
			request.setAttribute("userlist", list);
			RequestDispatcher rd = request.getRequestDispatcher("listStaff.jsp");
			rd.forward(request, response);
		}else if(action.equals("edit")){//编辑配送点员工
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println("id="+id);
			User user = userManager.searchById(id);
			request.setAttribute("editUser", user);
			RequestDispatcher rd = request.getRequestDispatcher("updateStaff.jsp");
			rd.forward(request, response);
		}else if(action.equals("update")){//更新配送点员工
			int id = Integer.parseInt(request.getParameter("id"));
			User user = userManager.searchById(id);
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");
			user.setName(name);
            user.setPassword(password);
            user.setRealname(realname);
            user.setPhone(phone);
            if(userManager.update(user)>0){
            	request.setAttribute("message", "更新成功");
            }else{
				request.setAttribute("message", "更新失败");
            }
            request.setAttribute("editUser", user);
			RequestDispatcher rd = request.getRequestDispatcher("updateStaff.jsp");
			rd.forward(request, response);
		}else if(action.equals("delete")){//删除配送点员工
			int id = Integer.parseInt(request.getParameter("id"));
			userManager.delete(id);
            ArrayList list = userManager.searchDistributionUser(27,0, null, null);//TODO:hjw
			request.setAttribute("userlist", list);
			RequestDispatcher rd = request.getRequestDispatcher("listStaff.jsp");
			rd.forward(request, response);
		}
		
		
	}
%>
