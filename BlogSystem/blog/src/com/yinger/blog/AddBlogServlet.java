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
		
		request.setCharacterEncoding("GBK");//Ϊ�˷�ֹ����Ĳ�������
		
		String title=request.getParameter("title");
		String categoryId=request.getParameter("category");
		String content=request.getParameter("content");
		
		int res=0;
		String message="";
		QueryRunner qr=Dbhelper.getQueryRunner();
		String sql="insert into blog(categoryid,title,content,created_time) values(?,?,?,NOW());";
		String[] param={categoryId,title,content};
		
		try {
			//��������update������������SQL������update��delete��insert intoҲ��ִ�д˷���
			res=qr.update(sql, param);//�������qrʱû�д���ds��������ô����ĵ�һ����������conn��Connection����
			//�������Ҫ�Լ��ر����ӣ�conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(res==1){
			message="Insert Blog sucessfully!";
		}else{
			message="Insert Blog fails!";
		}
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("/ShowResult.jsp").forward(request, response);//����ҳ����ת
		//ע��������ת����ҳ��һ��Ҫ����jsp������ᱨ404����
	}

}
