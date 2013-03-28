package com.yinger.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class AdminCommentServlet extends HttpServlet {

	private static final long serialVersionUID = -4536741637328841867L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equalsIgnoreCase("add")) {
			addComment(request, response);
		} else if (method.equalsIgnoreCase("delete")) {
			deleteComment(request, response);
		} else if (method.equalsIgnoreCase("preModify")) {
			preModifyComment(request, response);
		} else if (method.equalsIgnoreCase("postModify")) {
			postModifyComment(request, response);
		} else if (method.equalsIgnoreCase("list")) {
			listComment(request, response);
		}
	}

	private void addComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");// 为了防止传入的参数乱码
		String blogid = request.getParameter("blogid");
		String commenter = request.getParameter("commenter");
		String commentcon = request.getParameter("commentcon");

		int res = 0;
		String message = "";

		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "insert into COMMENT(blogid,username,content,createdtime) values(?,?,?,NOW())";
		String[] param = { blogid, commenter, commentcon };
		try {
			res = qr.update(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res == 1) {
			message = "Add comment sucessfully!";
		} else {
			message = "Add comment fails!";
		}

		request.setAttribute("message", message);
		request.getRequestDispatcher("/servlet/GetBlogServlet?blogid="+blogid).forward(request,
				response);// 用于页面跳转
	}

	private void listComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String blogid = request.getParameter("blogid");
		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "SELECT username,content,createdtime from comment where blogid="+blogid+" order by id desc";
		try {
			List<Comment> commentlist = (List<Comment>) qr.query(sql,
					new BeanListHandler(Comment.class));
			request.setAttribute("commentlist", commentlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/DisplsyBlog.jsp").forward(request,
				response);
	}

	private void postModifyComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	private void preModifyComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	private void deleteComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
