
<%@page import="manager.DistributionManager"%>
<%@page import="entity.Distribution"%>
<%@page import="entity.Route"%>
<%@page import="manager.RouteManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	System.out.println(action);
	if (action != null) {
		if (action.equals("add")) {
			String name = request.getParameter("routename");
			String sD = request.getParameter("sDistribution");
			String tD = request.getParameter("tDistribution");
			String length = request.getParameter("length");
			if (sD != "" && tD != "") {
				DistributionManager dmg = new DistributionManager();
				//Distribution  ds=dmg.searchByAddress(sD);
				//Distribution  dt=dmg.searchByAddress(tD);
				int ds = Integer.parseInt(sD);
				int dt = Integer.parseInt(tD);
				RouteManager rm = new RouteManager();
				Route rt = new Route();
				rt.setName(name);
				rt.setsDistribution(ds);
				rt.settDistribution(dt);
				rt.setLength(Integer.parseInt(length));
				rm.add(rt);
				request.setAttribute("message", "插入成功！");
				RequestDispatcher rd = request
						.getRequestDispatcher("addroute.jsp");
				rd.forward(request, response);
			}
			if (sD.equals("") || tD.equals("")) {
				request.setAttribute("name", name);
				request.setAttribute("length", length);
				request.setAttribute("message", "*起点或终点不能为空！");
				RequestDispatcher rd = request
						.getRequestDispatcher("addroute.jsp");
				rd.forward(request, response);
			}
		}
		if (action.equals("front")) {
			ArrayList list1 = new ArrayList();
			RouteManager rm = new RouteManager();
			list1 = rm.search();
			request.setAttribute("list", list1);
			RequestDispatcher rd = request
					.getRequestDispatcher("searchroute.jsp");
			rd.forward(request, response);
		}
		if (action.equals("search")) {
			String sD = request.getParameter("sD");
			String tD = request.getParameter("tD");
			DistributionManager dmg = new DistributionManager();
			Distribution ds = dmg.searchByName(sD);
			Distribution dt = dmg.searchByName(tD);
			if (ds != null && dt != null) {
				RouteManager rm = new RouteManager();
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("sDistribution", "" + ds.getId());
				hashMap.put("tDistribution", "" + dt.getId());
				ArrayList list = rm.searchByMap(hashMap);
				System.out.print(list.size());
				if (list.size() != 0) {
					request.setAttribute("list", list);
					RequestDispatcher rd = request
							.getRequestDispatcher("searchroute.jsp");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request
							.getRequestDispatcher("noroute.jsp");
					rd.forward(request, response);
				}
			}
			if (ds == null || dt == null) {
				RequestDispatcher rd = request
						.getRequestDispatcher("noroute.jsp");
				rd.forward(request, response);
			}
		}
		if (action.equals("del")) {
			String sid = request.getParameter("id");
			RouteManager rm = new RouteManager();
			int cont = rm.delete(Integer.parseInt(sid));
			System.out.print(cont);
			ArrayList list1 = new ArrayList();
			list1 = rm.search();
			request.setAttribute("list", list1);
			RequestDispatcher rd = request
					.getRequestDispatcher("searchroute.jsp");
			rd.forward(request, response);

		}
		if (action.equals("update")) {
			String sid = request.getParameter("id");
			RequestDispatcher rd = request
					.getRequestDispatcher("updateroute.jsp?id=" + sid);
			rd.forward(request, response);
		}
		if (action.equals("upd")) {
			String sid = request.getParameter("id");
			int intid = Integer.parseInt(sid);
			String routeName = request.getParameter("routeName");
			String routeLength = request.getParameter("routeLength");

			RouteManager rm = new RouteManager();
			Route rt = rm.searchById(intid);
			rt.setName(routeName);
			rt.setLength(Integer.parseInt(routeLength));

			int cont = rm.update(rt);
			ArrayList list2 = rm.search();
			request.setAttribute("list", list2);
			RequestDispatcher rd = request
					.getRequestDispatcher("searchroute.jsp?id=" + intid);
			rd.forward(request, response);
		}

	}
%>