package com.yinger.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class GetBlogListServlet extends HttpServlet {

	private static final long serialVersionUID = 6809043703654082445L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		QueryRunner qr=Dbhelper.getQueryRunner();
		String sql="SELECT id,title,content,created_time as createTime,categoryid from blog order by id desc";
		try {
			List<Blog> bloglist=(List<Blog>)qr.query(sql, new BeanListHandler(Blog.class));
			request.setAttribute("bloglist", bloglist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/DisplayBlogList.jsp").forward(request, response);
	}

}
