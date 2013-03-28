<%@page import="entity.User"%>
<%@page import="jxl.write.Label"%>
<%@page import="jxl.Workbook"%>
<%@page import="java.io.OutputStream"%>
<%@page import="entity.ExcelItem"%>
<%@page import="jxl.write.WritableSheet"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="manager.ExcelManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//配送点excel
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	
	if (action != null) {
		if (action.equals("search")) {
			int type = Integer.parseInt(request.getParameter("type"));
			session.setAttribute("type", type);
			int year = Integer.parseInt(request.getParameter("year"));
			int season = 0,month=0;
			if(request.getParameter("season")!=null){
				season = Integer.parseInt(request.getParameter("season"));				
			}
			if(request.getParameter("month")!=null){
				month = Integer.parseInt(request.getParameter("month"));
			}
			//System.out.println(type+" "+year+" "+season+" "+month);
			ExcelManager excelManager = new ExcelManager();
			int distributionId = ((User)session.getAttribute("loginuser")).getDistributionId();
			ArrayList list = excelManager.callProcExcel(type, year, season, month,distributionId);
			request.setAttribute("list", list);
			session.setAttribute("list", list);//保存到session中，保证后面可以excel
			RequestDispatcher rd = request.getRequestDispatcher("excel.jsp");
			rd.forward(request, response);
		}else if(action.equals("excel")){
			response.setContentType("application/vnd.ms-excel");  //保证不乱码
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
			String name = "报表";
			String fileName="报表"+dateFormat.format(new Date())+".xls";
			response.setHeader("Content-Disposition", "attachment;"+ " filename="+ new String(fileName.getBytes(), "ISO-8859-1"));
			OutputStream os = response.getOutputStream();
			out.clear();
			out = pageContext.pushBody();//这2句一定要，不然会报错。
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet(name,0);
			for(int i=0;i<4;i++){
				ws.setColumnView(i, 25);//设置列宽
			}
			ExcelItem item = new ExcelItem();
			ArrayList<ExcelItem> list = (ArrayList<ExcelItem>)session.getAttribute("list");
			
			Label label = new Label(0,0,"配送点名称");
			ws.addCell(label);
			label = new Label(1,0,"配送总重量");
			ws.addCell(label);
			label = new Label(2,0,"配送总体积");
			ws.addCell(label);
			label = new Label(3,0,"配送总收入");
			ws.addCell(label);
			
			for(int i=0;i<list.size();i++){
				item = list.get(i);
				label = new Label(0,(i+1),item.getName());
				ws.addCell(label);
				label = new Label(1,(i+1),String.valueOf(item.getWeight()));
				ws.addCell(label);
				label = new Label(2,(i+1),String.valueOf(item.getVolumn()));
				ws.addCell(label);
				label = new Label(3,(i+1),String.valueOf(item.getPrice()));
				ws.addCell(label);
			}
			wwb.write();
			wwb.close();
			os.close();
		}
	}
%>