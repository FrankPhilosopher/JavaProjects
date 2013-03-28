<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="manager.OrderManager"%>
<%@ page import="manager.DistributionManager"%>
<%@ page import="manager.PlacePriceManager"%>
<%@ page import="manager.RouteManager"%>

<%@ page import="entity.PlacePrice"%>
<%@ page import="entity.Order"%>
<%@ page import="entity.Distribution"%>
<%@ page import="entity.User"%>

<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.*"%>  
<%@ page import="java.text.*"%>  


<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8"); 
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//处理业务逻辑的后台程序
	String action = request.getParameter("action");
	
	if(action!=null) {	
		//增加订单
		if(action.equals("order_add")) {
			//数据库操作标志
			int flag=0;
			
			//发件人信息
			String sName=request.getParameter("sName");
			//地址	
			String scity=request.getParameter("scity");	
			String scounty=request.getParameter("scounty");		
			//配送地字符串连接处理		
			String sAddress=scity+scounty;
			
			String sPhone=request.getParameter("sPhone");
			String sCode=request.getParameter("sCode");
			
			//收件人信息
			String tName=request.getParameter("tName");
			//地址	
			String tcity=request.getParameter("tcity");	
			String tcounty=request.getParameter("tcounty");		
			//收寄地字符串连接处理									
			String tAddress=tcity+tcounty;			
			String tPhone=request.getParameter("tPhone");
			String tCode=request.getParameter("tCode");					
							
			//物品信息-类型转换
			String sweight=request.getParameter("weight");
			float weight=Float.parseFloat(sweight);
			String svolumn=request.getParameter("volumn");
			float volumn=Float.parseFloat(svolumn);
			String sgoodsType=request.getParameter("goodsType");
			int goodsType=Integer.parseInt(sgoodsType);
			//其他信息
			String remark=request.getParameter("remark");
			String workDate=request.getParameter("workDate");	

			//实例化对象
			Order order=new Order();
			//获取session	
			User user=(User)session.getAttribute("loginuser");
			
			
			
			//订单信息添加				
			//添加发件人信息
			//寄件人编号--本地填写的订单暂时没寄件人编号
			//order.setUserId();
			order.setSender(sName);
			order.setsAddress(sAddress);
			order.setsPhone(sPhone);
			order.setsCode(sCode);
			//寄件人配送点的Id号-即当前Id
			order.setsDistribution(user.getDistributionId());
			
			//添加收件人信息
			order.setReceiver(tName);
			order.settAddress(tAddress);
			order.settPhone(tPhone);
			order.settCode(tCode);
			//找出收件人配送点的Id号
			DistributionManager dbm=new DistributionManager();
			Distribution db=new Distribution();
			db=dbm.searchByAddress(tcity);
			order.settDistribution(db.getId());
				
			//添加物品信息	
			//揽件人 -即当前的登录人员
			order.setWorker(user.getRealname());
			//揽件日期-即当前系统日期
			order.setWorkDate(workDate);
			
			//路线价格计算
			RouteManager routeManager = new RouteManager();	
			String pathString = routeManager.buildShortestPath(order);

			//价格price计算 
			//order.setPrice();
			
			
			//订单状态--在站点
			order.setStatus(1);
			
			//本地填写的订单暂时没有carId
			//order.setCarId(0);
			//订单当前配送点填写为当前配送点ID
			order.setCurrentDistribution(user.getDistributionId());
			order.setVolumn(volumn);
			order.setWeight(weight);			
			order.setGoodsType(goodsType);
			order.setRemark(remark);
					
			//执行数据库操作
			flag=OrderManager.add(order);
			if(flag==2) {
				//成功页面跳转(还需隐藏域提醒增加成功)
				request.setAttribute("Order", order);
				request.setAttribute("PATH", pathString);
				RequestDispatcher rd = request.getRequestDispatcher("order_display.jsp");
				rd.forward(request,response);			
			}else {
				//出错加以提示	
				RequestDispatcher rd = request.getRequestDispatcher("operate_error.jsp");
				rd.forward(request,response);	
			}			
		}
		
		
		//查询未审核订单
		if(action.equals("uncheck_order")) {		
			ArrayList<Order> list = new ArrayList<Order>();
			Order order= new Order();
			OrderManager om=new OrderManager();
			//获取session	
			User user=new User();
			user=(User)session.getAttribute("loginuser");			
			//执行数据库操作
			list=om.search("select * from orders where status=0 and sDistribution="+user.getDistributionId()+"order by id ASC");				
			request.setAttribute("Order",list);
			RequestDispatcher rd = request.getRequestDispatcher("uncheck_order.jsp");
			rd.forward(request,response);
		}
		
		
		//审核订单
		if(action.equals("check_order")) {
			String sid=request.getParameter("id");
			int id=Integer.parseInt(sid);
			ArrayList<Order> list = new ArrayList<Order>();
			Order order= new Order();
			//执行数据库操作
			order=OrderManager.searchById(id);				
			if(order==null) {
				//成功页面跳转(还需隐藏域提醒增加成功)
				//再次查询数据返回		
				request.setAttribute("Order",order);
				RequestDispatcher rd = request.getRequestDispatcher("order_check.jsp");
				rd.forward(request,response);		
			}else {
				//出错加以提示
				//RequestDispatcher rd = request.getRequestDispatcher("placeprice_list.jsp");
				//rd.forward(request,response);						
			}			
		}		
		
		
		//派件订单
		if(action.equals("place_choose")) {		
			//获取下拉列表的值
			ArrayList<PlacePrice> listplace = new ArrayList<PlacePrice>();
			PlacePriceManager ppm= new PlacePriceManager();
			//list=ppm.search("select * from placePrice where distributionId="+27+"");		
			listplace=ppm.search("select * from placePrice");	
			
			
			//获得选取的范围值
			String placevalue=request.getParameter("placevalue");
			ArrayList<Order> listorder = new ArrayList<Order>();
			Order order= new Order();
			OrderManager om=new OrderManager();			
			//需要对收件人地址做字符串处理
			//获取session中的配送点Id 
			User user=new User();
			user=(User)session.getAttribute("loginuser");
			String sql="";
			//还需修改
			if(placevalue.equals(""))
			{
				sql="select * from orders where status=1 and tDistribution="+user.getDistributionId()+"";
			}else {
				sql="select * from orders where status=1 and tAddress like '%"+placevalue+"%' and tDistribution="+user.getDistributionId()+"";			
			}
			//执行数据库操作
			listorder=om.search(sql);				
			request.setAttribute("OrderList",listorder);
			request.setAttribute("PlacePriceList",listplace);
			RequestDispatcher rd = request.getRequestDispatcher("delivery_list_d.jsp");
			rd.forward(request,response);					
		}	
		
				
		//派件下拉地址选择
		if(action.equals("delivery_order")) {
		

			//获取下拉列表的元素
			//id应该是当前session保存的id
			ArrayList<PlacePrice> list = new ArrayList<PlacePrice>();
			PlacePriceManager ppm= new PlacePriceManager();
			//list=ppm.search("select * from placePrice where distributionId="+1+"");		
			list=ppm.search("select * from placePrice");			
			//执行数据库操作
			if(list!=null) {
				//成功页面跳转(还需隐藏域提醒增加成功)
				//再次查询数据返回				
				request.setAttribute("PlacePriceList",list);
				RequestDispatcher rd = request.getRequestDispatcher("delivery_list.jsp");
				rd.forward(request,response);
			}else {
				//出错加以提示
				//ArrayList<PlacePrice> listerror = new ArrayList<PlacePrice>();
				//request.setAttribute("PlacePriceList",listerror);					
			}	
			
		}			
			
		//修改订单
		if(action.equals("order_update")) {
			int flag=0;
			String sflag=request.getParameter("flag");	
			flag=Integer.parseInt(sflag);
			if(flag==0)
			{
				String sid=request.getParameter("id");	
				int id=0;
				if(!sid.equals(""))	
				{
					id=Integer.parseInt(sid);
				}
				Order order= new Order();
				order=OrderManager.searchById(id);
				if(order!=null) {
						//成功页面跳转(还需隐藏域提醒增加成功)
						//再次查询数据返回								
						request.setAttribute("Order",order);
						RequestDispatcher rd = request.getRequestDispatcher("order_update.jsp");
						rd.forward(request,response);		
				}else {	
				
							
				}			
			}else if(flag==1)
			{
				//正式修改订单
				
				//订单Id					
				String sorderId=request.getParameter("orderid");
				int orderId=Integer.parseInt(sorderId);
	
				//发件人信息
				String sName=request.getParameter("sName");
				//地址	
				String scity=request.getParameter("scity");	
				String scounty=request.getParameter("scounty");	
				String stown=request.getParameter("stown");		
				//配送地字符串连接处理		
				String sAddress=scity+scounty+stown;
				String sPhone=request.getParameter("sPhone");
				String sCode=request.getParameter("sCode");
				
				//收件人信息
				String tName=request.getParameter("tName");
				//地址	
				String tcity=request.getParameter("tcity");	
				String tcounty=request.getParameter("tcounty");	
				String ttown=request.getParameter("ttown");		
				//收寄地字符串连接处理									
				String tAddress=tcity+tcounty+ttown;			
				String tPhone=request.getParameter("tPhone");
				String tCode=request.getParameter("tCode");					
								
				//物品信息-类型转换
				String sweight=request.getParameter("weight");
				float weight=Float.parseFloat(sweight);
				String svolumn=request.getParameter("volumn");
				float volumn=Float.parseFloat(svolumn);
				String sgoodsType=request.getParameter("goodsType");
				int goodsType=Integer.parseInt(sgoodsType);
				//其他信息
				String remark=request.getParameter("remark");
				String workDate=request.getParameter("workDate");	
	
				//实例化对象
				Order order=new Order();
				//获取session	
				User user=(User)session.getAttribute("loginuser");
								
				//添加发件人信息
				order.setId(orderId);
				//寄件人编号--无
				//order.setUserId();
				order.setSender(sName);
				order.setsAddress(sAddress);
				order.setsPhone(sPhone);
				order.setsCode(sCode);
				//寄件人配送点的Id号-即当前Id
				order.setsDistribution(user.getDistributionId());
				
				//添加收件人信息
				order.setReceiver(tName);
				order.settAddress(tAddress);
				order.settPhone(tPhone);
				order.settCode(tCode);
				//找出收件人配送点的Id号
				DistributionManager dbm=new DistributionManager();
				Distribution db=new Distribution();
				db=dbm.searchByAddress(tcity);
				order.settDistribution(db.getId());
					
				//添加物品信息	
				//揽件人 -即当前的登录人员
				order.setWorker(user.getRealname());
				//揽件日期-即当前系统日期
				order.setWorkDate(workDate);
				
				//路线价格计算
				RouteManager routeManager = new RouteManager();	
				String pathString = routeManager.buildShortestPath(order);
				
				//路径计算
				//order.setPath("PathTest");
				//价格price计算 
				//order.setPrice(20);
				//订单状态--在站点
				
				
				
				order.setStatus(1);
				//没有carId
				//order.setCarId();
				//订单当前配送点填写为当前配送点ID
				order.setCurrentDistribution(user.getDistributionId());
				order.setVolumn(volumn);
				order.setWeight(weight);			
				order.setGoodsType(goodsType);
				order.setRemark(remark);
						
				//执行数据库更新操作			
				int status=0;
				status=OrderManager.update(order);							
				if(status==1) {
					//成功页面跳转(还需隐藏域提醒增加成功)
					//再次查询数据返回	
					request.setAttribute("Order", order);				
					request.setAttribute("PATH", pathString);
				}else {
					//出错加以提示
					//实例化对象
					Order ordererror=new Order();
					request.setAttribute("Order", ordererror);			
				}	
				RequestDispatcher rd = request.getRequestDispatcher("order_display.jsp");
				rd.forward(request,response);	
			}
		}

		//删除订单
		if(action.equals("order_delete")) {
			//数据库操作标志
			int flag=0;		
			String sid=request.getParameter("id");	
			int id=0;
			if(!sid.equals(""))	
			{
				id=Integer.parseInt(sid);
			}
			flag=OrderManager.delete(id);
			String sql=(String)session.getAttribute("OrderSql");
			if(flag==1) {
					//成功页面跳转(还需隐藏域提醒增加成功)
					//再次查询数据返回											
				ArrayList<Order> orderlist = new ArrayList<Order>();
				OrderManager om=new OrderManager();
				//执行数据库操作
				orderlist=om.search(sql);
				request.setAttribute("OrderList",orderlist);
			}else {	
				ArrayList<Order> errorlist = new ArrayList<Order>();
				request.setAttribute("OrderList",errorlist);							
			}		
			RequestDispatcher rd = request.getRequestDispatcher("order_list_d.jsp");
			rd.forward(request,response);					
		}
		
				
						
		//订单模糊查询
		if(action.equals("order_query")) {
			//数据库操作标志
			int flag=0;
			String sid=request.getParameter("id");
			int id=0;
			if(!sid.equals(""))
			{
				id=Integer.parseInt(sid);	
			}	
						
			//收寄地  	
			String tAddress=request.getParameter("tcity");									
			//配送地 	
			String sAddress=request.getParameter("scity");		
		
			String receiver=request.getParameter("receiver");
			String sgoodsType=request.getParameter("goodsType");
			int goodsType=0;
			if(!sgoodsType.equals(""))
			{
				goodsType=Integer.parseInt(sgoodsType);	
			}
			String starttime=request.getParameter("starttime");
			String endtime=request.getParameter("endtime");
		
			String sql="select * from orders where 1=1 ";
			if(!sid.equals(""))
			{
				sql=sql+"and id="+id+" ";	
			}
			if(!sAddress.equals(""))
			{
				sql=sql+"and sAddress like '%"+sAddress+"%' ";		
			}
			if(!tAddress.equals(""))
			{
				sql=sql+"and tAddress like '%"+tAddress+"%' ";		
			}			
			if(!receiver.equals(""))
			{
				sql=sql+"and receiver like '%"+receiver+"'% ";	
			}			
			if(!sgoodsType.equals(""))
			{
				sql=sql+"and goodsType="+goodsType+" ";
			}	
			//sql语句可能有问题		
			if(!starttime.equals(""))
			{
				sql=sql+"and workdate>'"+starttime+"' ";
			}	
			if(!endtime.equals(""))
			{
				sql=sql+"and workdate<'"+endtime+"' ";	
			}	
			sql=sql+"order by workdate DESC";		
			ArrayList<Order> orderlist = new ArrayList<Order>();
			OrderManager om=new OrderManager();
			//执行数据库操作
			orderlist=om.search(sql);	
			session.setAttribute("OrderSql", sql);			
			if(orderlist.size()>0) {
				//成功页面跳转(还需隐藏域提醒增加成功)
				//再次查询数据返回		
				request.setAttribute("OrderList",orderlist);		
			}else {
				//出错加以提示
				ArrayList<Order> errorlist = new ArrayList<Order>();
				request.setAttribute("OrderList",errorlist);				
			}	
			RequestDispatcher rd = request.getRequestDispatcher("order_list_d.jsp");
			rd.forward(request,response);
		}
}	
%>


