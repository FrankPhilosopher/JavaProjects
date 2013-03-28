<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@page import="entity.User"%>
<%@page import="manager.UserManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	System.out.println(action);
	if (action != null) 
	{
		if (action.equals("add")) 
		{
            String name = request.getParameter("name");
			String address = request.getParameter("address");
			String adminName = request.getParameter("adminName");
			String adminPassword = request
					.getParameter("adminPassword");
			String adminRealname = request
					.getParameter("adminRealname");
			String adminPhone = request.getParameter("adminPhone");

			// 首先保存一个配送点
			DistributionManager distributionManager = new DistributionManager();
			Distribution distribution = new Distribution();
			distribution.setName(name);
			distribution.setAddress(address);
			distributionManager.add(distribution);
			distribution = distributionManager
					.searchByName(distribution.getName());

			// 保存一个用户
			UserManager userManager = new UserManager();
			User user = new User();
			user.setName(adminName);
			user.setPassword(adminPassword);
			user.setPhone(adminPhone);
			user.setRealname(adminRealname);
			user.setPriority(User.DISTRIBUTION_ADMIN);
			user.setDistributionId(distribution.getId());
			userManager.add(user);
			request.setAttribute("message", "插入成功！");
			RequestDispatcher rd = request
					.getRequestDispatcher("addDistribution.jsp");
			rd.forward(request, response);

		}
		if(action.equals("search"))
		{
		    String value=request.getParameter("type");
		    String year=request.getParameter("year");
		    System.out.print(year);
		   if(year!="")
		    {
		        if (value.equals("1"))
		        {
		            DistributionManager dm=new DistributionManager();
		            Distribution dt=new Distribution();
		            dt=dm.searchById(Integer.parseInt(year)); 
		            if(dt!=null)
		            {
		            UserManager um=new UserManager();
		            User us=new User();
		            us=um.searchByditributionId(Integer.parseInt(year));
		            ArrayList list1=new ArrayList();
		            ArrayList list2=new ArrayList();
		            list1.add(dt); list2.add(us);
		            request.setAttribute("list1", list1);
		            request.setAttribute("list2", list2);
			        RequestDispatcher rd = request
					   .getRequestDispatcher("searchDitribution.jsp");
			        rd.forward(request, response);
			        }
			        else
			        {
			        RequestDispatcher rd = request
					   .getRequestDispatcher("noResult.jsp");
			        rd.forward(request, response);
			        }
		        }
		        if(value.equals("0"))
		        {
		            DistributionManager dm=new DistributionManager();
		            Distribution dt=dm.searchByName(year);
		            if(dt!=null)
		            { System.out.print(dt.getId());
		             UserManager um=new UserManager();
		            User us=new User();
		            us.setDistributionId(dt.getId());
		            us=um.searchByditributionId(us.getDistributionId());
		            ArrayList list1=new ArrayList();
		            ArrayList list2=new ArrayList();
		            list1.add(dt); list2.add(us);
		            request.setAttribute("list1", list1);
		            request.setAttribute("list2", list2);
			        RequestDispatcher rd = request
					   .getRequestDispatcher("searchDitribution.jsp");
			        rd.forward(request, response);
			        }
			       if(dt==null)
			        {
			        RequestDispatcher rd = request
					   .getRequestDispatcher("noResult.jsp");
			        rd.forward(request, response);
			        }
		        }
		     }
		     if(year.equals(""))
		     {
		       RequestDispatcher rd = request
					.getRequestDispatcher("listDistribution.jsp");
			     rd.forward(request, response);
		     }
	    }
	    if(action.equals("del"))
	    {
	        String sid=request.getParameter("id");
	        DistributionManager dm=new DistributionManager();
	        int count1=dm.delete(Integer.parseInt(sid));
	        UserManager um=new UserManager();
	        int conut2=um.del(Integer.parseInt(sid));
	        RequestDispatcher rd = request
					.getRequestDispatcher("listDistribution.jsp");
			     rd.forward(request, response);
	    }
	    if(action.equals("update"))
	    {
	       String sid=request.getParameter("id");
	       RequestDispatcher rd = request
					.getRequestDispatcher("updateDistribution.jsp?id="+sid);
			     rd.forward(request, response);
	    }
	    if(action.equals("upd"))
	    {
	       String ssid=request.getParameter("id");
	       String suserid=request.getParameter("userId");
	       System.out.print(suserid);
	        String name = request.getParameter("name");
			String address = request.getParameter("address");
			String adminName = request.getParameter("adminName");
			String adminPassword = request
					.getParameter("adminPassword");
			String adminRealname = request
					.getParameter("adminRealname");
			String adminPhone = request.getParameter("adminPhone");
			Distribution distribution = new Distribution();
			distribution.setId(Integer.parseInt(ssid));
			distribution.setName(name);
			distribution.setAddress(address);
			DistributionManager dm = new DistributionManager();
			int cont1=dm.update(distribution);
			User user = new User();
			user.setId(Integer.parseInt(suserid));
			user.setName(adminName);
			user.setPassword(adminPassword);
			user.setRealname(adminRealname);
			user.setPhone(adminPhone);
			UserManager um = new UserManager();
			int cont2=um.update(user);
			RequestDispatcher rd = request
					.getRequestDispatcher("listDistribution.jsp");
			     rd.forward(request, response);
	       
	    }
	} 
%>