package com.ztdz.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TArea;
import com.ztdz.pojo.TOrgainzation;
import com.ztdz.pojo.TTerminal;
import com.ztdz.pojo.TUser;
import com.ztdz.service.impl.AreaServiceImpl;
import com.ztdz.service.impl.LogServiceImpl;
import com.ztdz.service.impl.OrganizationServiceImpl;
import com.ztdz.service.impl.TerminalServiceImpl;
import com.ztdz.service.impl.UserServiceImpl;
import com.ztdz.tools.OrgLevel;

/**
 * 该Action用于操作通用的action响应 例如：用户登陆，用户信息查询，定位查询等等...
 * 
 * @author wuxuehong
 * 
 *         2012-5-15
 */
public class UserAction extends ActionSupport implements SessionAware,
		ServletRequestAware {

	private HttpServletRequest request;// request
	private Map<String, Object> session;
	private final String TARGET = "target"; // 当返回的页面是用户自定义页面的时候 不返回SUCCESS
											// 而是返回TARGET
	private TUser user; // 用于获取前台表单里的用户信息
	private String code; // 用户获取登陆页面验证码
	private String page; // 用户自定义返回页面
	private String url;// 处理top.jsp中的“个人资料修改” 点击事件，将url传给top.jsp
	private String url2;// 用来处理center.jsp

	/**
	 * 由spring 完成服务注入
	 */
	private UserServiceImpl userService;
	private LogServiceImpl logService;

	private OrganizationServiceImpl organizationServiceImpl;
	private AreaServiceImpl areaServiceImpl;
	private TerminalServiceImpl terminalServiceImpl;

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String login() {
		if (!code.equals(session.get("code"))) {
			request.setAttribute("result", "验证码错误!");
			session.remove("code");
			return "login";
		}
		user = userService.login(user);
		if (user == null) {
			request.setAttribute("result", "用户名或密码错误!");
			return "login";
		}
		session.put("user", user);

		// 处理top.jsp中的“个人资料修改” 点击事件，将url传给top.jsp
		switch (user.getTOrgainzation().getOrgLevel()) {
		case OrgLevel.ADMIN_LEVEL:
			url = "Admin_basicInfo";
			url2 = "Admin_jituanManager?pageIndex=1";
			session.put("url", url);
			session.put("url2", url2);
			break;
		case OrgLevel.YUNWEI_LEVLE:
			url = "YunWei_basicInfo";
			url2 = "YunWei_groupManager";// 各自修改为各自默认界面需要请求的action
			session.put("url", url);
			session.put("url2", url2);
			break;
		case OrgLevel.GROUP_LEVEL:
			url = "Group_basicInfo";
			url2 = "Group_userManager";// 各自修改为各自默认界面需要请求的action
			session.put("url", url);
			session.put("url2", url2);

			break;
		case OrgLevel.JITUAN_LEVEL:
			url = "JiTuan_basicInfo";
			url2 = "JiTuan_groupManager";// 各自修改为各自默认界面需要请求的action
			session.put("url", url);
			session.put("url2", url2);
			break;
		default:
		}
		return SUCCESS;
	}

	public OrganizationServiceImpl getOrganizationServiceImpl() {
		return organizationServiceImpl;
	}

	public void setOrganizationServiceImpl(
			OrganizationServiceImpl organizationServiceImpl) {
		this.organizationServiceImpl = organizationServiceImpl;
	}

	public AreaServiceImpl getAreaServiceImpl() {
		return areaServiceImpl;
	}

	public void setAreaServiceImpl(AreaServiceImpl areaServiceImpl) {
		this.areaServiceImpl = areaServiceImpl;
	}

	/******************************* getter and setters *************************************************/
	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public LogServiceImpl getLogService() {
		return logService;
	}

	public void setLogService(LogServiceImpl logService) {
		this.logService = logService;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTARGET() {
		return TARGET;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public TerminalServiceImpl getTerminalServiceImpl() {
		return terminalServiceImpl;
	}

	public void setTerminalServiceImpl(TerminalServiceImpl terminalServiceImpl) {
		this.terminalServiceImpl = terminalServiceImpl;
	}

}
