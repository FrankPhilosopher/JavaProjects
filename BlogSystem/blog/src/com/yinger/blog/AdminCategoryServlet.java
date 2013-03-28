package com.yinger.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class AdminCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 5321508514261978668L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if (method == null) {
			method = "list";
		}
		if (method.equalsIgnoreCase("add")) {
			addCategory(request, response);
		} else if (method.equalsIgnoreCase("delete")) {
			deleteCategory(request, response);
		} else if (method.equalsIgnoreCase("preModify")) {
			preModifyCategory(request, response);
		} else if (method.equalsIgnoreCase("postModify")) {
			postModifyCategory(request, response);
		} else if (method.equalsIgnoreCase("list")) {
			listCategory(request, response);
		}

	}

	// 类别列表
	public void listCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "SELECT id,name,level from category order by id desc";
		try {
			List<Category> categorylist = (List<Category>) qr.query(sql,
					new BeanListHandler(Category.class));
			request.setAttribute("categorylist", categorylist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/AdminCategory.jsp").forward(request,
				response);
	}

	// 添加类别
	public void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");// 为了防止传入的参数乱码

		String cname = request.getParameter("cname");
		String clevel = request.getParameter("clevel");

		int res = 0;
		String message = "";

		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "insert into category(name,level) values(?,?);";
		String[] param = { cname, clevel };
		try {
			res = qr.update(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res == 1) {
			message = "Add Category sucessfully!";
		} else {
			message = "Add Category fails!";
		}

		request.setAttribute("message", message);
		request.getRequestDispatcher("/ShowResult.jsp").forward(request,
				response);// 用于页面跳转
	}

	// 删除类别
	public void deleteCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "delete from category where id=" + cid;
		try {
			qr.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		listCategory(request, response);
	}

	// 修改类别之前
	public void preModifyCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "SELECT id,name,level from category where id=" + cid;
		try {
			List<Category> categorylist = (List<Category>) qr.query(sql,
					new BeanListHandler(Category.class));
			Category category = categorylist.get(0);
			request.setAttribute("category", category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/ModifyCategory.jsp").forward(request,
				response);
	}

	// 修改类别信息
	public void postModifyCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("GBK");// 为了防止传入的参数乱码
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		String clevel = request.getParameter("clevel");

		int res = 0;
		String message = "";

		QueryRunner qr = Dbhelper.getQueryRunner();
		String sql = "update category set name=?,level=? where id=?";
		String[] param = { cname, clevel, cid };
		try {
			res = qr.update(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res == 1) {
			message = "Modify Category sucessfully!";
		} else {
			message = "Modify Category fails!";
		}

		request.setAttribute("message", message);
		request.getRequestDispatcher("/ShowResult.jsp").forward(request,
				response);// 用于页面跳转

	}
}
