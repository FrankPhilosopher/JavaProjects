<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@page import="entity.User"%>
<%@page import="manager.UserManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	UserManager userManager = new UserManager();
	System.out.println(action);

	if (action != null) {

		if (action.equals("forwardAddUser")) {
			request.setAttribute("message", "");
			RequestDispatcher rd = request
					.getRequestDispatcher("addUser.jsp");
			rd.forward(request, response);
		}

		if (action.equals("forwardAddManager")) {
			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "");
			}

			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("addManager.jsp");
			rd.forward(request, response);
		}

		if (action.equals("forwardListUser")) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("priority", User.COMPANY_WORKER + "");
			List<User> userList = userManager.searchByMap(hashMap);

			int pageIndex = Integer.parseInt(request
					.getParameter("pageIndex"));
			int count = userList.size();
			int pages = count / 10 + 1;

			if (pages == 1) {
				pageIndex = 1;
				userList = userList.subList(0, count - 1);
			} else if (pageIndex >= pages) {
				pageIndex = pages;
				userList = userList
						.subList((pages - 1) * 10, count - 1);
			} else {
				userList = userList.subList((pageIndex - 1) * 10 + 1,
						pageIndex * 10);
			}
			request.setAttribute("count", count);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("pages", pages);

			request.setAttribute("userList", userList);
			RequestDispatcher rd = request
					.getRequestDispatcher("listUser.jsp");
			rd.forward(request, response);
		}

		if (action.equals("forwardListManager")) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("priority", User.DISTRIBUTION_ADMIN + "");
			List<User> managerList = userManager.searchByMap(hashMap);

			int pageIndex = Integer.parseInt(request
					.getParameter("pageIndex"));
			int count = managerList.size();
			int pages = count / 10 + 1;

			if (pages == 1) {
				pageIndex = 1;
				managerList = managerList.subList(0, count - 1);
			} else if (pageIndex >= pages) {
				pageIndex = pages;
				managerList = managerList
						.subList((pages - 1) * 10, count - 1);
			} else {
				managerList = managerList.subList((pageIndex - 1) * 10 + 1,
						pageIndex * 10);
			}
			request.setAttribute("count", count);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("pages", pages);

			request.setAttribute("managerList", managerList);

			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("listManager.jsp");
			rd.forward(request, response);
		}

		if (action.equals("search")) {
			String select = request.getParameter("select");
			String searchKey = request.getParameter("searchKey");
			List<User> userList = new ArrayList<User>();
			if (select.equals("0")) {
				System.out.println(searchKey);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("name", "'" + searchKey + "'");
				hashMap.put("priority", User.COMPANY_WORKER + "");
				userList = userManager.searchByMap(hashMap);
			} else if (select.equals("1")) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("realname", "'" + searchKey + "'");
				hashMap.put("priority", User.COMPANY_WORKER + "");
				userList = userManager.searchByMap(hashMap);
			}
			request.setAttribute("userList", userList);
			RequestDispatcher rd = request
					.getRequestDispatcher("listUser.jsp");
			rd.forward(request, response);
		}

		if (action.equals("searchManager")) {
			String distribution = request.getParameter("distribution");

			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("distributionId", distribution);
			hashMap.put("priority", User.DISTRIBUTION_ADMIN + "");
			List<User> managerList = userManager.searchByMap(hashMap);

			request.setAttribute("managerList", managerList);

			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("listManager.jsp");
			rd.forward(request, response);
		}
		if (action.equals("addUser")) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");

			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setRealname(realname);
			user.setPhone(phone);
			user.setPriority(User.COMPANY_WORKER);
			user.setCompanyId(1);

			userManager.add(user);

			request.setAttribute("message", "插入成功！");
			RequestDispatcher rd = request
					.getRequestDispatcher("addUser.jsp");
			rd.forward(request, response);

		}

		if (action.equals("addManager")) {
			String distribution = request.getParameter("distribution");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");

			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setRealname(realname);
			user.setPhone(phone);
			user.setPriority(User.DISTRIBUTION_ADMIN);
			user.setDistributionId(Integer.parseInt(distribution));

			userManager.add(user);

			request.setAttribute("message", "插入成功！");
			RequestDispatcher rd = request
					.getRequestDispatcher("actionUser.jsp?action=forwardAddManager");
			rd.forward(request, response);
		}

		if (action.equals("deleteUser")) {
			String id = request.getParameter("id");
			userManager.delete(Integer.parseInt(id));

			RequestDispatcher rd = request
					.getRequestDispatcher("actionUser.jsp?action=forwardListUser");
			rd.forward(request, response);
		}
		if (action.equals("deleteManager")) {
			String id = request.getParameter("id");
			userManager.delete(Integer.parseInt(id));

			RequestDispatcher rd = request
					.getRequestDispatcher("actionUser.jsp?action=forwardListManager");
			rd.forward(request, response);
		}
		if (action.equals("updateUser")) {
			String id = request.getParameter("id");
			User user = userManager.searchById(Integer.parseInt(id));
			request.setAttribute("user", user);
			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "");
			}
			RequestDispatcher rd = request
					.getRequestDispatcher("updateUser.jsp");
			rd.forward(request, response);
		}
		if (action.equals("updateManager")) {
			String id = request.getParameter("id");
			User user = userManager.searchById(Integer.parseInt(id));
			request.setAttribute("user", user);
			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "");
			}
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("updateManager.jsp");
			rd.forward(request, response);
		}
		if (action.equals("saveUser")) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");
			String id = request.getParameter("id");

			User user = new User();
			user.setId(Integer.parseInt(id));
			user.setName(name);
			user.setPassword(password);
			user.setRealname(realname);
			user.setPhone(phone);
			user.setPriority(User.COMPANY_WORKER);
			user.setCompanyId(1);

			userManager.update(user);

			request.setAttribute("message", "修改成功！");

			RequestDispatcher rd = request
					.getRequestDispatcher("actionUser.jsp?action=updateUser&id="
							+ id);
			rd.forward(request, response);
		}
		if (action.equals("saveManager")) {
			String distributionId = request
					.getParameter("distributionId");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");
			String id = request.getParameter("id");

			User user = new User();
			user.setId(Integer.parseInt(id));
			user.setName(name);
			user.setPassword(password);
			user.setRealname(realname);
			user.setPhone(phone);
			user.setPriority(User.DISTRIBUTION_ADMIN);
			user.setDistributionId(Integer.parseInt(distributionId));

			userManager.update(user);

			request.setAttribute("message", "修改成功！");

			RequestDispatcher rd = request
					.getRequestDispatcher("actionUser.jsp?action=updateManager&id="
							+ id);
			rd.forward(request, response);
		}
	}
%>