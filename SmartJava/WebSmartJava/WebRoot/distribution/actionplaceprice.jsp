<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="manager.PlacePriceManager"%>

<%@page import="entity.PlacePrice"%>
<%@page import="entity.User"%>

<%@page import="java.util.ArrayList"%>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8"); 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//处理业务逻辑的后台程序
	String action = request.getParameter("action");
	
	if(action!=null) {	
		
		//增加范围和价格
		if(action.equals("placeprice_add")) {
			//数据库操作标志
			int flag=0;
			//获取前台信息
			String placeName=request.getParameter("placeName");
			String spricePrice=request.getParameter("pricePrice");	
			//类型转换-后台可以不判空
			float pricePrice=Float.parseFloat(spricePrice);
			String placeTime=request.getParameter("placeTime");	
			String remark=request.getParameter("remark");	
		
			//实例化实体
			PlacePrice pp=new PlacePrice();	
			//获取session				
			User user=(User)session.getAttribute("loginuser");
			//数据填充
			pp.setDistributionId(user.getDistributionId());
			pp.setPlaceName(placeName);
			pp.setPlacePrice(pricePrice);
			pp.setPlaceTime(placeTime);
			pp.setRemark(remark);
			//数据库增加数据
			flag=PlacePriceManager.add(pp);
			//保存操作标志			
			session.setAttribute("FLAG",flag);	
			RequestDispatcher rd = request.getRequestDispatcher("placeprice_add.jsp");
			rd.forward(request,response);	
		}
		
		//删除范围和价格
		if(action.equals("placeprice_delete")) {
			//数据库操作标志
			int flag=0;
			//获取前台信息
			String sid=request.getParameter("id");		
			int id=Integer.parseInt(sid);
			//数据库删除数据
			flag=PlacePriceManager.delete(id);	
			//保存操作标志			
			session.setAttribute("FLAG",flag);
			ArrayList<PlacePrice> list = new ArrayList<PlacePrice>();
			PlacePriceManager ppm= new PlacePriceManager();
			//获取session				
			User user=(User)session.getAttribute("loginuser");		
			//显示所有的数据		
			list=ppm.search("select * from placePrice where distributionId="+user.getDistributionId()+"");									
			request.setAttribute("PlacePriceList",list);
			RequestDispatcher rd = request.getRequestDispatcher("placeprice_list_d.jsp");
			rd.forward(request,response);				
		}
		
		//修改范围和价格
		if(action.equals("placeprice_update")) {
		
			//数据库操作标志
			int flag=0;
			String sflag=request.getParameter("flag");	
			flag=Integer.parseInt(sflag);	
			if(flag==0){	
				//flag=0页面跳转	
				//获取前台信息
				String sid=request.getParameter("id");		
				int id=Integer.parseInt(sid);
				//数据库按ID查询
				PlacePrice pp=PlacePriceManager.searchById(id);	
				request.setAttribute("PlacePrice",pp);
				//跳转
				RequestDispatcher rd = request.getRequestDispatcher("placeprice_update.jsp");
				rd.forward(request,response);			
			}else if(flag==1) { 
			
				//flag=1正式修改
				int status=0;
				//获取前台信息
				String splacePrice=request.getParameter("placePrice");	
				float placePrice=Float.parseFloat(splacePrice);
				String placeTime=request.getParameter("placeTime");	
				String remark=request.getParameter("remark");	
				
				//获取本地配送点id(用session)				
				User user=(User)session.getAttribute("loginuser");					
				PlacePrice pp = (PlacePrice)session.getAttribute("PlacePrice");			
				pp.setId(pp.getId());
				pp.setPlacePrice(placePrice);
				pp.setPlaceTime(placeTime);
				pp.setRemark(remark);
				//根据id更新数据
				status=PlacePriceManager.update(pp);	
				//保存操作结果
				session.setAttribute("FLAG",status);		
				ArrayList<PlacePrice> list = new ArrayList<PlacePrice>();
				PlacePriceManager ppm= new PlacePriceManager();
				list=ppm.search("select * from placePrice where distributionId="+user.getDistributionId()+" order by placeTime ASC");									
				request.setAttribute("PlacePriceList",list);
				RequestDispatcher rd = request.getRequestDispatcher("placeprice_list_d.jsp");
				rd.forward(request,response);							
			}
		}
		
						
		//查询范围和价格
		if(action.equals("placeprice_query")) {
			//数据库操作标志
			int flag=0;
			//获取前台信息
			String type=request.getParameter("type");
			String values=request.getParameter("values");
			ArrayList<PlacePrice> list = new ArrayList<PlacePrice>();
			PlacePriceManager ppm= new PlacePriceManager();
			//获取session	
			User user=(User)session.getAttribute("loginuser");
			//没有输入值就查询全部
			if(values.equals("")) {
				//需要修改-加入当前配送点ID
				list=ppm.search("select * from placePrice where distributionId="+user.getDistributionId()+" order by placeTime ASC");		
			}else {
				//按范围名称
				if(type.equals("placeName")) {
					//需要修改-加入当前配送点ID
					list=ppm.search("select * from placePrice where distributionId="+user.getDistributionId()+" and placeName='"+values+"' order by placeTime ASC");							
				}
				//按配送时间
				if(type.equals("placeTime")) {
					//需要修改-加入当前配送点ID
					list=ppm.search("select * from placePrice where distributionId="+user.getDistributionId()+" and placetime='"+values+"' order by placeTime ASC");					
				}				
				//按配送价格
				if(type.equals("placePrice")) {	
					//值转换
					float pricePrice=Float.parseFloat(values);
					//需要修改-加入当前配送点ID
					list=ppm.search("select * from placePrice where distributionId="+user.getDistributionId()+" and placePrice<"+pricePrice+" order by placeTime ASC");
				}			
			}
			
			if(list!=null) {
				//成功页面跳转(还需隐藏域提醒增加成功)
				request.setAttribute("PlacePriceList",list);		
			}else {
				//出错加以提示	
				ArrayList<PlacePrice> listempty = new ArrayList<PlacePrice>();
				request.setAttribute("PlacePriceList",listempty);				
			}
			RequestDispatcher rd = request.getRequestDispatcher("placeprice_list_d.jsp");
			rd.forward(request,response);				
		}		
	}
%>


