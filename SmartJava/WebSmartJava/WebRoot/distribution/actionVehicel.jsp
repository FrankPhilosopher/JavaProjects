<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="manager.*,entity.*,java.util.*,java.text.*"%>
<%
User loginuser=(User)session.getAttribute("loginuser");
Distribution currentDistribution=(Distribution)session.getAttribute("currentDistribution");
%>
<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	System.out.print("值为：" + action);
	if (action != null) {
		if (action.equals("vehicleEnter")) {//车辆进站
			String number = request.getParameter("car_number");		
			System.out.println("当前编号为："+number);	
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("number",number);
			ArrayList<Car> carList=CarManager.searchByMap(hm);
			System.out.println("--"+carList);
			if(carList.size()==0){//车牌号不存在
			System.out.println("进入此逻辑");
				request.setAttribute("message", "车牌号码输入错误,查无此车");
				RequestDispatcher rd = request
						.getRequestDispatcher("vehicleEnter.jsp");
				rd.forward(request, response);
			}else{
			Car c=carList.get(0);
			hm = new HashMap<String, String>();
			HashMap<String, String> hm1 = new HashMap<String, String>();
			hm.put("status",""+Car.ATSTOP);
			hm.put("currentDistribution",""+currentDistribution.getId());
			hm1.put("number",number);
			int result =CarManager.update(hm, hm1);
			if (result == 0) {//车辆进站失败
			request.setAttribute("message", "车牌号码输入错误,查无此车");
				RequestDispatcher rd = request
						.getRequestDispatcher("vehicleEnter.jsp");
				rd.forward(request, response);

			} else {//有此车
				System.out.println("该车进站成功");
				request.setAttribute("message", "该车进站成功");
				hm=new HashMap<String,String>();
				hm1=new HashMap<String,String>();								
				hm.put("status",""+Order.WAITUNLOADED);
				hm.put("currentDistribution",""+currentDistribution.getId());
				hm1.put("carId",""+c.getId());
				hm1.put("status",""+Order.ATTRAVEL);				
				OrderManager.update(hm,hm1);				
				Distribution current=DistributionManager.searchById(currentDistribution.getId());
				System.out.println("current:"+current.getName());
				session.setAttribute("currentCar",c);	
				session.setAttribute("currentDistribution",current);			
				RequestDispatcher rd = request
						.getRequestDispatcher("passingSheet.jsp");
				rd.forward(request, response);
			}
			}		
						
			
		}else if(action.equals("queryOrder")){//查询指定车辆上的订单
			Car c=(Car)session.getAttribute("currentCar");
			String sql="select * from orders where status='"+Order.WAITUNLOADED+"' and"+" carId='"+c.getId()+"'";
		    ArrayList<Order> list=OrderManager.search(sql);
		    session.setAttribute("list",list);
		    RequestDispatcher rd = request
						.getRequestDispatcher("passingSheet.jsp");
				rd.forward(request, response);
		}else if(action.equals("uploadPassingSheet")){
			ArrayList<Order> list=(ArrayList<Order>)session.getAttribute("list");
			Date currentTime = new Date();
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String dateString = formatter.format(currentTime);
			for(int i=0;i<list.size();i++){
			String sql="insert into passingSheet values('"+list.get(i).getId()+"','"+list.get(i).getCarId()+"','"
			    +currentDistribution.getId()+"','"+dateString+"')";			
			int result=PassingSheetManager.add(sql);
			if(result!=1){
			System.out.println("插入失败");
			 }
			}
		    RequestDispatcher rd = request
						.getRequestDispatcher("vehicleEnter.jsp");
				rd.forward(request, response);
		}
	}
%>
