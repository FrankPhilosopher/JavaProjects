package com.ztdz.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ztdz.pojo.TUser;

public class LoginFilter extends HttpServlet implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpSession session = request.getSession();
		String url = request.getServletPath();
		String contextPath = request.getContextPath();
		if (url.equals(""))
			url += "/";
		if (url.startsWith("/") && !url.startsWith("/login.jsp")
				&& !url.startsWith("/User_login") && !url.startsWith("/image")
				&& !url.startsWith("/code.jsp") && !url.endsWith("/errorSession.jsp")) {// �����ʺ�̨��Դ
			// ���˵�login
			TUser user = (TUser) session.getAttribute("user");
			if (user == null) {// ת�����Ա��½ҳ��
				response.sendRedirect(contextPath + "/errorSession.jsp");
				return;
			}
		}
		filterChain.doFilter(sRequest, sResponse);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}