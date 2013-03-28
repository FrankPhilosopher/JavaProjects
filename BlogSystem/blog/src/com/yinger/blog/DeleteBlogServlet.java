package com.yinger.blog;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class DeleteBlogServlet extends HttpServlet {

	private static final long serialVersionUID = 7061771799703095531L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id=request.getParameter("blogid");
		
		QueryRunner qr=Dbhelper.getQueryRunner();
		String sql="delete from blog where id="+id;
		try {
			qr.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/servlet/AdminBlogListServlet").forward(request, response);//页面转到自身页面重新获取博文列表
	}

}
