<%@page import="manager.DistributionManager"%>
<%@page import="entity.Distribution"%>
<%@page import="entity.Car"%>
<%@page import="manager.CarManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	System.out.println(action);

	if (action != null) {
		if (action.equals("forwardAdd")) {
			request.setAttribute("message", "");
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);

			RequestDispatcher rd = request
					.getRequestDispatcher("addCar.jsp");
			rd.forward(request, response);
		}
		if (action.equals("forwardDispatch")) {
			List<Car> carList = CarManager.search();
			
			int pageIndex = Integer.parseInt(request
					.getParameter("pageIndex"));
			int count = carList.size();
			int pages = count / 10 + 1;

			if (pages == 1) {
				pageIndex = 1;
				carList = carList.subList(0, count - 1);
			} else if (pageIndex >= pages) {
				pageIndex = pages;
				carList = carList.subList((pages - 1) * 10, count - 1);
			} else {
				carList = carList.subList((pageIndex - 1) * 10 + 1,
						pageIndex * 10);
			}
			request.setAttribute("count", count);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("pages", pages);			
			
			request.setAttribute("carList", carList);     
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);

			RequestDispatcher rd = request
					.getRequestDispatcher("dispatchCar.jsp");
			rd.forward(request, response);
		}

		if (action.equals("forwardList")) {
			List<Car> carList = CarManager.search();

			int pageIndex = Integer.parseInt(request
					.getParameter("pageIndex"));
			int count = carList.size();
			int pages = count / 10 + 1;

			if (pages == 1) {
				pageIndex = 1;
				carList = carList.subList(0, count - 1);
			} else if (pageIndex >= pages) {
				pageIndex = pages;
				carList = carList.subList((pages - 1) * 10, count - 1);
			} else {
				carList = carList.subList((pageIndex - 1) * 10 + 1,
						pageIndex * 10);
			}
			request.setAttribute("count", count);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("pages", pages);

			request.setAttribute("carList", carList);
			RequestDispatcher rd = request
					.getRequestDispatcher("listCar.jsp");
			rd.forward(request, response);
		}
		if (action.equals("search")) {
			String select = request.getParameter("select");
			String searchKey = request.getParameter("searchKey");
			List<Car> carList = new ArrayList<Car>();
			if (select.equals("0")) {
				System.out.println(searchKey);
				Car car = CarManager.searchById(Integer
						.parseInt(searchKey));
				carList.add(car);
			} else if (select.equals("1")) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("number", "'" + searchKey + "'");
				carList = CarManager.searchByMap(hashMap);
			}

			request.setAttribute("carList", carList);
			RequestDispatcher rd = request
					.getRequestDispatcher("listCar.jsp");
			rd.forward(request, response);
		}
		if (action.equals("dispatchSearch")) {
			String searchKey = request.getParameter("searchKey");
			List<Car> carList = new ArrayList<Car>();
			Car car = CarManager
					.searchById(Integer.parseInt(searchKey));
			carList.add(car);
			request.setAttribute("carList", carList);

			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("dispatchCar.jsp");
			rd.forward(request, response);
		}

		if (action.equals("add")) {
			String number = request.getParameter("number");
			String driver = request.getParameter("driver");
			String weight = request.getParameter("weight");
			String volumn = request.getParameter("volumn");
			String currentDistribution = request
					.getParameter("currentDistribution");

			Car car = new Car();
			car.setNumber(number);
			car.setDriver(driver);
			car.setWeight(Float.parseFloat(weight));
			car.setVolumn(Float.parseFloat(volumn));
			car.setCurrentDistribution(Integer
					.parseInt(currentDistribution));
			car.setStatus(Car.AVAILABLE);

			CarManager.add(car);

			request.setAttribute("message", "插入成功！");
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);
			RequestDispatcher rd = request
					.getRequestDispatcher("addCar.jsp");
			rd.forward(request, response);

		}
		if (action.equals("dispatch")) {
			String distributeId = request.getParameter("distributeId");
			String[] ToDispatchCars = request
					.getParameterValues("ToDispatchCars");

			for (int i = 0; i < ToDispatchCars.length; i++) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("currentDistribution", distributeId);
				HashMap<String, String> hashMap2 = new HashMap<String, String>();
				hashMap2.put("id", ToDispatchCars[i]);
				CarManager.update(hashMap, hashMap2);
			}

			RequestDispatcher rd = request
					.getRequestDispatcher("actionCar.jsp?action=forwardDispatch");
			rd.forward(request, response);
		}
		if (action.equals("delete")) {
			String id = request.getParameter("id");
			CarManager.delete(Integer.parseInt(id));

			RequestDispatcher rd = request
					.getRequestDispatcher("actionCar.jsp?action=forwardList");
			rd.forward(request, response);
		}
		if (action.equals("update")) {
			String id = request.getParameter("id");
			Car car = CarManager.searchById(Integer.parseInt(id));
			request.setAttribute("car", car);

			if (request.getAttribute("message") == null) {
				request.setAttribute("message", "");
			}
			List<Distribution> distributionList = DistributionManager
					.search();
			request.setAttribute("distributionList", distributionList);

			RequestDispatcher rd = request
					.getRequestDispatcher("updateCar.jsp");
			rd.forward(request, response);
		}
		if (action.equals("save")) {
			String number = request.getParameter("number");
			String driver = request.getParameter("driver");
			String weight = request.getParameter("weight");
			String volumn = request.getParameter("volumn");
			String status = request.getParameter("status");
			System.out.println(status);
			String currentDistribution = request
					.getParameter("currentDistribution");
			String id = request.getParameter("id");

			Car car = new Car();
			car.setId(Integer.parseInt(id));
			car.setNumber(number);
			car.setDriver(driver);
			car.setWeight(Float.parseFloat(weight));
			car.setVolumn(Float.parseFloat(volumn));
			car.setCurrentDistribution(Integer
					.parseInt(currentDistribution));
			car.setStatus(Integer.parseInt(status));

			CarManager.update(car);

			request.setAttribute("message", "修改成功！");

			RequestDispatcher rd = request
					.getRequestDispatcher("actionCar.jsp?action=update&id="
							+ id);
			rd.forward(request, response);
		}

	}
%>