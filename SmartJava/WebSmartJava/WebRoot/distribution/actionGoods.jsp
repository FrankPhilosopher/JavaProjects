<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="manager.*,java.util.*,entity.*,manager.*"%>
<%
User loginuser=(User)session.getAttribute("loginuser");
Distribution currentDistribution=(Distribution)session.getAttribute("currentDistribution");
%>
<%
	request.setCharacterEncoding("UTF-8");
	String action =request.getParameter("action").toString();
	System.out.print("值为：" + action);
	if (action != null) {
		if (action.equals("distribute")) {//车辆装货		    			
			CarManager cm = new CarManager();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("currentDistribution",
					""+currentDistribution.getId());
			hashMap.put("status", "" + Car.ATSTOP);
			ArrayList<Car> list = cm.searchByMap(hashMap);//查出所有在本配送点且状态为在站的车辆
			session.setAttribute("carList", list);
			RequestDispatcher rd = request
					.getRequestDispatcher("top.jsp");
			rd.forward(request, response);
		} else if (action.equals("uploadNumber")) {//提交指定车辆编号
			ArrayList<Car> list = (ArrayList<Car>) session
					.getAttribute("carList");
			String car_number = request.getParameter("car_number");
			System.out.println("提交的车辆编号为;" + car_number);
			for (int i = 0; i < list.size(); i++) {
				Car c = list.get(i);
				if (c.getNumber().equals(car_number)) {
					session.setAttribute("loadCar", c);					
					RequestDispatcher rd = request
							.getRequestDispatcher("top.jsp");
					rd.forward(request, response);
				}
			}
		} else if (action.equals("load")) {//跳转到配货界面
			//首先取到当前配送点ID，查询所有当前配送点相邻的配送点
			RouteManager rm = new RouteManager();
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("sDistribution", ""+currentDistribution.getId());
			ArrayList<Route> route = rm.searchByMap(hm);
			DistributionManager dm = new DistributionManager();
			ArrayList<String> dDistribution = new ArrayList<String>();
			for (int i = 0; i < route.size(); i++) {
				Route r = route.get(i);
				dDistribution.add(dm.searchById(r.gettDistribution())
						.getName());
			}
			session.setAttribute("nextTop", dDistribution);
			RequestDispatcher rd = request
					.getRequestDispatcher("below.jsp");
			rd.forward(request, response);
		} else if (action.equals("select")) {//选定下一站，查询当前位置为本配送点且下一站为指定位置的订单
			String next = (String) request.getParameter("next");
			System.out.println("传过来下一站为：" + next);
			DistributionManager dm = new DistributionManager();
			Distribution dis = dm.searchByName(next);
			int id = dis.getId();
			int currentID = (Integer) session.getAttribute("currentID");//当前配送点ID
			String sql = "select * from orders where currentDistribution='"
					+ currentDistribution.getId()
					+ "'and  tDistribution not in ('"
					+ currentDistribution.getId() + "') and status ='1'";
			OrderManager om = new OrderManager();
			ArrayList<Order> list = om.search(sql);
			System.out.print("订单数量：" +list.size()+"-------");
			ArrayList<Order> orderList = new ArrayList<Order>();
			for (int i = 0; i < list.size(); i++) {
				String path = list.get(i).getPath();
				System.out.println("路径为："+path+"--");
				String[] ids = path.split("-");
				for (int j = 0; j < ids.length; j++) {
					if (Integer.parseInt(ids[j]) == currentDistribution.getId()) {
						if (Integer.parseInt(ids[j + 1]) == id) {//下一站为选定下一站的订单
							orderList.add(list.get(i));
						}
					}
				}
			}
			System.out.print("执行完下一站判断时间：" + new Date());
			request.setAttribute("orderList", orderList);
			session.setAttribute("next", next);
			RequestDispatcher rd = request
					.getRequestDispatcher("below.jsp");
			rd.forward(request, response);
		} else if (action.equals("commit_load")) {//提交对应车辆的装货订单
			String[] ids = request.getParameterValues("order");
			float weight=Float.parseFloat(request.getParameter("h1"));
			float volumn=Float.parseFloat(request.getParameter("h2"));
			System.out.println("获取体积为："+volumn);
			OrderManager om = new OrderManager();
			Car c = (Car) session.getAttribute("loadCar");
			c.setCurrentWeight(weight);
			c.setCurrentVolumn(volumn);
			HashMap<String,String> hm= new HashMap<String,String>();
			HashMap<String,String> hm1= new HashMap<String,String>();
			hm.put("currentWeight",""+weight);
			hm.put("currentVolumn",""+volumn);
			hm1.put("id",""+c.getId());
			CarManager.update(hm,hm1);
			
			for (int i = 0; i < ids.length; i++) {
				hm = new HashMap<String, String>();
				hm1 = new HashMap<String, String>();
				hm.put("status", "" + Order.LOADED);
				hm.put("carId", "" + c.getId());
				hm1.put("id", ids[i]);
				om.update(hm, hm1);
			}
			System.out.println("提交的订单数量为：" + ids.length + "第一个为："
					+ ids[0]);
			RequestDispatcher rd = request
					.getRequestDispatcher("below.jsp");
			rd.forward(request, response);

		} else if (action.equals("carSend")) {
			Car c = (Car) session.getAttribute("loadCar");
			DistributionManager dm = new DistributionManager();
			int id = dm.searchByName(
					(String) session.getAttribute("next")).getId();
			CarManager cm = new CarManager();
			HashMap<String, String> hm = new HashMap<String, String>();
			HashMap<String, String> hm1 = new HashMap<String, String>();
			// hm.put("CurrentDistribution",""+id);
			hm.put("status", "" + Car.TRAVEL);
			hm1.put("id", "" + c.getId());
			cm.update(hm, hm1);
			hm = new HashMap<String, String>();
			hm.put("status", ""+Order.ATTRAVEL);
			hm.put("currentDistribution",""+id);
			hm1 = new HashMap<String, String>();
			hm1.put("carId", ""+c.getId());
			OrderManager.update(hm, hm1);
			RequestDispatcher rd = request
					.getRequestDispatcher("actionGoods.jsp?action=distribute");
			rd.forward(request, response);
		}
		
		
		if (action.equals("unload")) {//车辆卸货入口			
			CarManager cm = new CarManager();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("currentDistribution",
					""+currentDistribution.getId());
			hashMap.put("status", "" + Car.ATSTOP);
			ArrayList<Car> list = cm.searchByMap(hashMap);//查出所有在本配送点且状态为在站的车辆
			session.setAttribute("unLoadcarList", list);
			RequestDispatcher rd = request
					.getRequestDispatcher("unLoadcar.jsp");
			rd.forward(request, response);
		} else if (action.equals("uploadUnLoadNumber")) {//提交待卸货车辆编号
			ArrayList<Car> list = (ArrayList<Car>) session
					.getAttribute("unLoadcarList");
			String car_number = request.getParameter("car_number");
			System.out.println("提交的车辆编号为;" + car_number);
			for (int i = 0; i < list.size(); i++) {
				Car c = list.get(i);
				if (c.getNumber().equals(car_number)) {
					session.setAttribute("unLoadCar", c);					
					RequestDispatcher rd = request
							.getRequestDispatcher("unLoadcar.jsp");
					rd.forward(request, response);
				}
			}
		}else if (action.equals("unLoad")) {//跳转到卸货界面
			//首先取到当前配送点ID，查询所有当前配送点相邻的配送点
		    String name=DistributionManager.searchById(currentDistribution.getId()).getName();//当前配送点名称
			RouteManager rm = new RouteManager();
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("sDistribution", ""+currentDistribution.getId());
			ArrayList<Route> route = rm.searchByMap(hm);			
			ArrayList<String> dDistribution = new ArrayList<String>();
			dDistribution.add("本地");
			for (int i = 0; i < route.size(); i++) {
				Route r = route.get(i);
				dDistribution.add(DistributionManager.searchById(r.gettDistribution())
						.getName());
			}
			session.setAttribute("unLoadnextTop", dDistribution);
			RequestDispatcher rd = request
					.getRequestDispatcher("unLoadOrder.jsp");
			rd.forward(request, response);
		} else if (action.equals("unLoad_select")) {//选定下一站，查询当前位置为本配送点且下一站为指定位置的订单
			String next = (String) request.getParameter("next");
			session.setAttribute("currentNext",next);
			System.out.println("传过来下一站为：" + next);
			ArrayList<Order> orderList=null;			
			Car c=(Car)session.getAttribute("unLoadCar");
			if(next.equals("本地")==false){//如果订单没有到站，则执行如下方法			
			Distribution dis = DistributionManager.searchByName(next);
			int id = dis.getId();			
			String sql = "select * from orders where currentDistribution='"
					+ currentDistribution.getId()
					+ "'and  tDistribution not in ('"
					+ currentDistribution.getId() + "') and status in ('"+Order.WAITUNLOADED+"','"+Order.LOADED+"') and carId='"+c.getId()+"'";
			OrderManager om = new OrderManager();
			ArrayList<Order> list = om.search(sql);
			System.out.print("执行完查找时间：" + new Date());
			orderList = new ArrayList<Order>();
			for (int i = 0; i < list.size(); i++) {
				String path = list.get(i).getPath();
				String[] ids = path.split("-");
				for (int j = 0; j < ids.length; j++) {
					if (Integer.parseInt(ids[j]) == currentDistribution.getId()) {
						if (Integer.parseInt(ids[j + 1]) == id) {//下一站为选定下一站的订单
							orderList.add(list.get(i));
						}
					}
				}
			  }
			}else{//订单到站
			  String sql = "select * from orders where currentDistribution='"
					+ currentDistribution.getId()
					+ "'and  tDistribution='"
					+ currentDistribution.getId() + "' and status ='"+Order.WAITUNLOADED+"' and carId='"+c.getId()+"'";
			OrderManager om = new OrderManager();
			ArrayList<Order> list = om.search(sql);
			System.out.print("执行完查找时间：" + list);			
	        orderList=list;					
			}
			System.out.print("执行完下一站判断时间：" + new Date());
			request.setAttribute("orderList", orderList);
			session.setAttribute("next", next);
			RequestDispatcher rd = request
					.getRequestDispatcher("unLoadOrder.jsp");
			rd.forward(request, response);
		} else if (action.equals("commit_unLoad")) {//提交对应车辆的卸货订单
			String[] ids = request.getParameterValues("order");
			Car c = (Car) session.getAttribute("unLoadCar");
			float weight=Float.parseFloat(request.getParameter("h1"));
			float volumn=Float.parseFloat(request.getParameter("h2"));
			System.out.println("获取体积为："+volumn);						
			c.setCurrentWeight(weight);
			c.setCurrentVolumn(volumn);
			HashMap<String,String> hm= new HashMap<String,String>();
			HashMap<String,String> hm1= new HashMap<String,String>();
			hm.put("currentWeight",""+weight);
			hm.put("currentVolumn",""+volumn);
			hm1.put("id",""+c.getId());
			CarManager.update(hm,hm1);
			String next=session.getAttribute("currentNext").toString();			
			for (int i = 0; i < ids.length; i++) {
				hm = new HashMap<String, String>();
				hm1 = new HashMap<String, String>();
				if(next.equals("本地")){
				hm.put("status", "" + Order.UNDISPATCH);
				}else{
				hm.put("status", "" + Order.ATSTOP);
				}								
				hm1.put("id",ids[i]);
				OrderManager.update(hm, hm1);
			}
			System.out.println("提交的订单数量为：" + ids.length + "第一个为："
					+ ids[0]);
			RequestDispatcher rd = request
					.getRequestDispatcher("actionGoods.jsp?action=unload");
			rd.forward(request, response);
		}				
	}
%>
