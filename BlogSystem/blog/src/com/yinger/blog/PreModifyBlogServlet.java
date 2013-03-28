package com.yinger.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class PreModifyBlogServlet extends HttpServlet {

	private static final long serialVersionUID = 7524983976687287817L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String blogid=request.getParameter("blogid");
		QueryRunner qr=Dbhelper.getQueryRunner();
		String sql="SELECT id,title,content,categoryid from blog where id="+blogid;
		try {
			List<Blog> bloglist=(List<Blog>)qr.query(sql, new BeanListHandler(Blog.class));
			Blog blog=bloglist.get(0);
			request.setAttribute("blog", blog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/ModifyBlog.jsp").forward(request, response);
	}
}
