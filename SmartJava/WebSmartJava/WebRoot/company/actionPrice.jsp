<%@page import="entity.Distribution"%>
<%@page import="manager.DistributionManager"%>
<%@page import="entity.DistributionPrice"%>
<%@page import="manager.DistributionPriceManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	DistributionPriceManager dPriceManager = new DistributionPriceManager();
	System.out.println(action);

	if (action != null) {

		if (action.equals("forwardAdd")) {
			request.setAttribute("message", "");

			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);

			RequestDispatcher rd = request
					.getRequestDispatcher("addPrice.jsp");
			rd.forward(request, response);
		}
		if (action.equals("forwardList")) {
			List<DistributionPrice> priceList = dPriceManager.search();

			int pageIndex = Integer.parseInt(request
					.getParameter("pageIndex"));
			int count = priceList.size();
			int pages = count / 10 + 1;

			if (pages == 1) {
				pageIndex = 1;
				priceList = priceList.subList(0, count - 1);
			} else if (pageIndex >= pages) {
				pageIndex = pages;
				priceList = priceList.subList((pages - 1) * 10,
						count - 1);
			} else {
				priceList = priceList.subList((pageIndex - 1) * 10 + 1,
						pageIndex * 10);
			}
			request.setAttribute("count", count);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("pages", pages);

			request.setAttribute("priceList", priceList);
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);

			RequestDispatcher rd = request
					.getRequestDispatcher("findPrice.jsp");
			rd.forward(request, response);
		}
		if (action.equals("search")) {
			String sDistribution = request
					.getParameter("sDistribution");
			String tDistribution = request
					.getParameter("tDistribution");

			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("sDistribution", "'" + sDistribution + "'");
			hashMap.put("tDistribution", "'" + tDistribution + "'");
			List<DistributionPrice> priceList = dPriceManager
					.searchByMap(hashMap);

			request.setAttribute("priceList", priceList);

			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("findPrice.jsp");
			rd.forward(request, response);
		}
		if (action.equals("add")) {
			String sDistribution = request
					.getParameter("sDistribution");
			String tDistribution = request
					.getParameter("tDistribution");
			String firstKilo = request.getParameter("firstKilo");
			String secondKilo = request.getParameter("secondKilo");

			DistributionPrice dPrice = new DistributionPrice();
			dPrice.setsDistribution(Integer.parseInt(sDistribution));
			dPrice.setDistribution(Integer.parseInt(tDistribution));
			dPrice.setFirstKilo(Float.parseFloat(firstKilo));
			dPrice.setSecondKilo(Float.parseFloat(secondKilo));

			dPriceManager.add(dPrice);

			request.setAttribute("message", "插入成功！");
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("addPrice.jsp");
			rd.forward(request, response);

		}
		if (action.equals("delete")) {
			String id = request.getParameter("id");
			dPriceManager.delete(Integer.parseInt(id));
			RequestDispatcher rd = request
					.getRequestDispatcher("actionPrice.jsp?action=forwardList");
			rd.forward(request, response);
		}
		if (action.equals("update")) {
			String id = request.getParameter("id");
			DistributionPrice dPrice = dPriceManager.searchById(Integer
					.parseInt(id));
			request.setAttribute("id", dPrice.getId());
			request.setAttribute("sDistribution", DistributionManager
					.searchById(dPrice.getsDistribution()).getName());
			request.setAttribute("tDistribution", DistributionManager
					.searchById(dPrice.getDistribution()).getName());
			request.setAttribute("firstKilo", dPrice.getFirstKilo());
			request.setAttribute("secondKilo", dPrice.getSecondKilo());

			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "");
			}
			RequestDispatcher rd = request
					.getRequestDispatcher("updatePrice.jsp");
			rd.forward(request, response);
		}
		if (action.equals("save")) {
			String firstKilo = request.getParameter("firstKilo");
			String secondKilo = request.getParameter("secondKilo");
			String id = request.getParameter("id");

			dPriceManager.updateById(id, firstKilo, secondKilo);

			request.setAttribute("message", "修改成功！");

			RequestDispatcher rd = request
					.getRequestDispatcher("actionPrice.jsp?action=update&id="
							+ id);
			rd.forward(request, response);
		}
	}
%>