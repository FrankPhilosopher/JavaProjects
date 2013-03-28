package com.yinger.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class PreAddBlogServlet extends HttpServlet {

	private static final long serialVersionUID = 6182228147626178762L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		listCategory(request, response);
	}
	
	// 类别列表
	public void listCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "SELECT id,name,level from category order by id desc";
		try {
			List<Category> categorylist = (List<Category>) qr.query(sql,new BeanListHandler(Category.class));
			request.setAttribute("categorylist", categorylist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/AddBlog.jsp").forward(request,response);
	}

}
