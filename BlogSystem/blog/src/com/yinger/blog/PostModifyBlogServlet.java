package com.yinger.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

public class PostModifyBlogServlet extends HttpServlet {

	private static final long serialVersionUID = 7524983976687287817L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");// 为了防止传入的参数乱码
		String blogid=request.getParameter("blogid");
		String title = request.getParameter("title");
		String categoryId = request.getParameter("category");
		String content = request.getParameter("content");

		int res = 0;
		String message = "";
		QueryRunner qr=Dbhelper.getQueryRunner();
//		String sql = "UPDATE blog set title='"+title+"',content='"+content+"',catogory="+categoryId+" WHERE id="+id;
		String sql = "UPDATE blog set categoryId=?,title=?,content=? WHERE id=?";
		String[] param = { categoryId, title, content, blogid };

		try {
			res = qr.update(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res == 1) {
			message = "Modify blog sucessfully!";
		} else {
			message = "Modify blog fails!";
		}

		request.setAttribute("message", message);
		request.setAttribute("blogid", blogid);
		request.getRequestDispatcher("/ShowResult.jsp").forward(request,response);
	}

}
