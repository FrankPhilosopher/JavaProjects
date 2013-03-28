package com.yinger.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

public class AddBlogServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8290624928248763724L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("GBK");//为了防止传入的参数乱码
		
		String title=request.getParameter("title");
		String categoryId=request.getParameter("category");
		String content=request.getParameter("content");
		
		int res=0;
		String message="";
		QueryRunner qr=Dbhelper.getQueryRunner();
		String sql="insert into blog(categoryid,title,content,created_time) values(?,?,?,NOW());";
		String[] param={categoryId,title,content};
		
		try {
			//调用它的update方法，其他的SQL语句包括update，delete，insert into也是执行此方法
			res=qr.update(sql, param);//如果生成qr时没有传递ds参数，那么这里的第一个参数就是conn（Connection对象）
			//并且最后要自己关闭连接，conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(res==1){
			message="Insert Blog sucessfully!";
		}else{
			message="Insert Blog fails!";
		}
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("/ShowResult.jsp").forward(request, response);//用于页面跳转
		//注意这里跳转到的页面一定要加上jsp，否则会报404错误
	}

}
