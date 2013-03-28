package com.yinger.blog;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

public class AddCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = -8399841395984149243L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GBK");// 为了防止传入的参数乱码

		String cname = request.getParameter("cname");
		String clevel = request.getParameter("clevel");

		int res = 0;
		String message = "";

		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "insert into category(name,level) values(?,?);";
		String[] param = { cname,clevel };
		try {
			res = qr.update(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res == 1) {
			message = "Insert Category sucessfully!";
		} else {
			message = "Insert Category fails!";
		}

		request.setAttribute("message", message);
		request.getRequestDispatcher("/ShowResult.jsp").forward(request,response);// 用于页面跳转
	}

}
