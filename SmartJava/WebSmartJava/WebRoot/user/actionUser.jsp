
<%@page import="manager.OrdersManager"%>
<%@page import="entity.Orders"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String action = request.getParameter("action");
	System.out.print(action);
	if(action !=null)
	{
	if(action.equals("orderManager"))
	{
	//跳转至ordeManager.jsp
	RequestDispatcher rm = request.getRequestDispatcher("orderManager.jsp");
	rm.forward(request, response);
	
	}
	if(action.equals("addOrder"))
	{
	//收件人信息
	String receiver = request.getParameter("receiver");
	String tprovince = request.getParameter("tprovince");
	String tcity = request.getParameter("tcity");
	String tcountry = request.getParameter("tcountry");
	String ttown = request.getParameter("ttown");
	String tAddress = tprovince+tcity+tcountry+ttown;
	
	String tPhone = request.getParameter("sPhone");
	String tCode = request.getParameter("sCode");
	//寄件人信息
	String sender = request.getParameter("sender");
	String sprovince = request.getParameter("sprovince");
	String scity = request.getParameter("scity");
	String scoutry = request.getParameter("scoutry");
	String stown = request.getParameter("stown");
	String sAddress = sprovince+scity+scoutry+stown;
	String sPhone = request.getParameter("sPhone");
	String sCode = request.getParameter("sCode");
	
	//寄件物品信息
	
	String sweight = request.getParameter("weight");
	String svolumn = request.getParameter("volumn");
	String squantity = request.getParameter("quantity");
	String sgoodsType = request.getParameter("goodsType");
	String remark= request.getParameter("remark");
	//price在线生成
	
	Orders orders = new Orders();
	OrdersManager om = new OrdersManager();
	
	
	float weight = Float.parseFloat(sweight);
	float volumn = Float.parseFloat(svolumn);
	int quantity = Integer.parseInt(squantity);
	int goodsType = Integer.parseInt(sgoodsType);
	
	
	orders.setReceiver(receiver);
	orders.settAddress(tAddress);
	orders.settPhone(tPhone);
	orders.settCode(tCode);
	
	orders.setSender(sender);
	orders.setsAddress(sAddress);
	orders.setsPhone(sPhone);
	orders.setsCode(sCode);
	orders.setWeight(weight);
	orders.setVolumn(volumn);
	orders.setQuantity(quantity);
	orders.setGoodsType(goodsType);
	orders.setRemark(remark);
	//orders.setPrice(price);
	int i =om.add(orders);
	
	
   if(i>0){

	RequestDispatcher rd = request.getRequestDispatcher("addOrder.jsp");
				rd.forward(request,response);
	}else {
	//错误提示}
	
	}
	}
	
	if(action.equals("updateOrder"))
	{
	//修改订单
	String iid = request.getParameter("id");
	if(iid!=null){
	int id = Integer.parseInt(iid);
	ArrayList list = new ArrayList();
	Orders orders= new Orders();
	OrdersManager om = new OrdersManager();
	orders = om.searchById(id);
	if(orders!=null){
	request.setAttribute("orders", orders);
	RequestDispatcher rm = request.getRequestDispatcher("updateOrder.jsp");
	rm.forward(request, response);
	}else{//错误提示}
	
	}
	
	
	
	
	}
	if(action.equals("viewOrder"))
	{
	
	
	//跳转至viewOrder.jsp
	RequestDispatcher rm = request.getRequestDispatcher("viewOrder.jsp");
	rm.forward(request, response);
	
	
	}
	if(action.equals("deleteOrder")){
	//删除订单
	String sid = request.getParameter("id");
	
	if(iid!=null){
	ArrayList list = new ArrayList();
	Orders orders = new Orders();
	OrdersManager om = new OrdersManager();
	int id = Integer.parseInt(sid);
    int	i = om.delete(orders);
	if(i==1){
	list = om.search("select * from orders");
	request.setAttribute("orders",list);
	RequestDispatcher rm = request.getRequestDispatcher("listOrder.jsp");
	rm.forward(request, response);
	}
	
	}
	}
	
	
	if(action.equals("listOrder"))
	{
	ArrayList list = new ArrayList();
	Orders orders = new Orders();
	OrdersManager om = new OrdersManager();
	list = om.search("select * from orders where userId="+orders.getUserId()+" and sender = "+orders.getSender()+"");
	request.setAttribute("orders",list);
	//跳转至listOrder.jsp
	RequestDispatcher rm = request.getRequestDispatcher("listOrder.jsp");
	rm.forward(request, response);
	
	}
	}






}







%>