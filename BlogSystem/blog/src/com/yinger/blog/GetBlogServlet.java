package com.yinger.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class GetBlogServlet extends HttpServlet {

	private static final long serialVersionUID = 6809043703654082445L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String blogid = request.getParameter("blogid");
		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql_blog = "SELECT id,title,content,created_time as createTime,categoryid from blog where id="
				+ blogid;
		String sql_comment = "SELECT username,content,createdtime from comment where blogid="+blogid+" order by id desc";
		try {
			List<Blog> bloglist = (List<Blog>) qr.query(sql_blog,
					new BeanListHandler(Blog.class));
			Blog blog = bloglist.get(0);
			request.setAttribute("blog", blog);
			List<Comment> commentlist = (List<Comment>) qr.query(sql_comment,
					new BeanListHandler(Comment.class));
			request.setAttribute("commentlist", commentlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/DisplayBlog.jsp").forward(request,
				response);
	}

}
