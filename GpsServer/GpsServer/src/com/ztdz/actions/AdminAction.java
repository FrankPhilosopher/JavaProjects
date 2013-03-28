package com.ztdz.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TAccount;
import com.ztdz.pojo.TArea;
import com.ztdz.pojo.TCarInfo;
import com.ztdz.pojo.TLog;
import com.ztdz.pojo.TOrgainzation;
import com.ztdz.pojo.TTempPosition;
import com.ztdz.pojo.TTerminal;
import com.ztdz.pojo.TUser;
import com.ztdz.service.impl.AccountServiceImpl;
import com.ztdz.service.impl.AreaServiceImpl;
import com.ztdz.service.impl.CarInfoServiceImpl;
import com.ztdz.service.impl.LogServiceImpl;
import com.ztdz.service.impl.OrganizationServiceImpl;
import com.ztdz.service.impl.TempPositionServiceImpl;
import com.ztdz.service.impl.TerminalServiceImpl;
import com.ztdz.service.impl.UserServiceImpl;
import com.ztdz.tools.OrgLevel;
import com.ztdz.tools.Page;
import com.ztdz.tools.PositionConvertUtil;

/**
 * 该action 用于服务系统管理员的特殊请求响应
 * 
 * @author weijiyuan
 * 
 *         2012-5-24
 */
public class AdminAction extends ActionSupport implements SessionAware,
		ServletRequestAware {
	/**
	 * 各个模块公用属性
	 */
	private HttpServletRequest request;// request
	private Map<String, Object> session; // session 会话
	private TUser user; // 用户信息
	private final String REDIRECT = "redirect";
	private String redActionName;// 重定向到的action的名字

	private String page; // 用户自定义返回页面
	private final String TARGET = "target";
	private final String SUCCESS = "success";

	// 分页变量
	private int total;// 总数
	private int pageIndex;// 当前页
	private int pages;// 页面总数

	// 由spring 完成服务注入
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private OrganizationServiceImpl organizationService;
	private AreaServiceImpl areaService;
	private CarInfoServiceImpl carInfoService;
	private AccountServiceImpl accountService;
	private TerminalServiceImpl terminalService;
	private TempPositionServiceImpl tempPositionService;

	// 基本资料界面
	private String oldPwd, newPwd; // 用户基本信息 密码修改

	// 运维与集团用户公用的属性
	private List<TOrgainzation> orgs; // 所有组织机构 集团/运维/组
	private List<TArea> areas; // 地区信息
	private int orgId;// 运维用户的机构id，这个是用于编辑，删除
	private int selectAreaId;
	private String orgSearchName;// 模糊查询
	private int provinceId;

	// 运维用户管理界面
	private TOrgainzation yunwei; // 运维机构信息
	private List<TUser> yunweiUserList;// 前台要显示的运维机构中的账户列表
	private int yunweiUserId;// 运维用户的账户id，这个是用于编辑，删除
	private TUser yunweiUser;// 运维用户的账户，把它提出来不用user是怕两者搞混了

	// 集团用户管理界面
	private TOrgainzation jituan;// 集团机构信息
	private List<TUser> jituanUserList;// 前台要显示的运维机构中的账户列表
	private int jituanUserId;// 集团用户的账户id，这个是用于编辑，删除
	private TUser jituanUser;// 集团用户的账户，把它提出来不用user是怕两者搞混了
	// 集团下的分组和终端
	private List<TUser> groupList;// 某个集团机构下的所有分组
	private List<TTerminal> terminals;// 某个组下的所有终端；
	private String sim;// 根据sim模糊查询终端
	private TOrgainzation orgainzation;
	private int groupId;
	private List<TUser> groupUserList;
	private TUser groupUser;
	private int groupUserId;
	private String option; // 搜索条件
	private String value; // 搜索值
	private int groupID;// 用于转移终端

	// 日志管理界面
	private List<TLog> logList;// 前台要显示的日志列表
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式实例
	private Date searchStartDate;
	private Date searchEndDate;

	// 地区管理界面
	private int areaId;
	private TArea area;

	// 车型信息界面
	private TCarInfo carInfo;
	private int carTypeId;
	private List<TCarInfo> cars; // 车型信息

	// 服务费管理界面
	private List<TAccount> accounts; // 账单信息
	private String orgName;// 集团name
	private TAccount account;
	private int expense;// 充值金额

	// 数据报表管理
	private static final int PAGESIZE = 10;
	private int searchWeiyi;// 唯一搜索的条件
	private String searchTerminalNum;// 终端编号 或者 手机号
	private Date searchFromDate;// 开始时间
	private Date searchToDate;// 结束时间
	private int onoff;// 在线状态
	private int carType;// 目标类型
	private int searchGroupId;// 搜索的所属的分组机构
	private List<TTerminal> terminalExcelList;// 结果列表
	private List<TCarInfo> carInfoList;// 车类型列表
	private String cols;// 选中的要导出的列，用数字和逗号组成
	private InputStream excelStream; // excel输入流
	private int jituanId;// 所属集团的id
	private List<TOrgainzation> jituanGroupList;// 集团下的所有分组机构列表
	private List<TOrgainzation> jituanList;// 集团列表
	private TTerminal terminal;// 进行编辑的终端
	private boolean isSearching = false;// 这个属性表示是否当前的列表是处于搜索结果中，因为如果不设置的话那么搜索结果翻页无效
	private String searchString;// 要搜索的"名称"

	// 终端定位管理
	private List<TTempPosition> tpositions; // 所有客户定位信息
	private int ptotal; // 含定位信息终端数目
	private int outline; // 离线终端数
	private int online; // 在线终端数

	// ------------------------------------基本资料----------------------------------------//
	/**
	 * 保存系统用户基本资料
	 * 
	 * @return
	 */
	public String saveBasicInfo() {
		if (oldPwd == null || "".equals(oldPwd)
				|| !user.getPwd().equals(oldPwd)) {
			request.setAttribute("result", "旧密码输入错误！");
		} else if (newPwd == null || "".equals(newPwd)) {
			request.setAttribute("result", "新密码不能为空！");
		} else {
			user.setPwd(newPwd);
			userService.updateUser(user);
			request.setAttribute("result", "修改成功！");
			logService.addLog(new TLog(user, "【" + user.getName() + "】"
					+ "修改了基本资料", new Date()));
		}
		page = "Admin_basicInfo";
		return TARGET;
	}

	// ------------------------------------运维用户管理----------------------------------------//
	/**
	 * 跳转到增加运维机构的界面
	 * 
	 * @return
	 */
	public String forwardAddYunwei() {
		yunwei = new TOrgainzation();// 防止干扰页面
		areas = areaService.findAll();// 得到地区列表
		page = "Admin_addYunwei";
		return TARGET;
	}

	/**
	 * 添加新的运维机构
	 * 
	 * @return
	 */
	public String addYunwei() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		yunwei.setTArea(tArea);
		TOrgainzation rootOrg = organizationService.findRootOrg();// 找到root机构
		yunwei.setTOrgainzation(rootOrg);
		yunwei.setOrgLevel(OrgLevel.YUNWEI_LEVLE);// 运维机构
		yunwei.setRegistertime(new Date());
		organizationService.addOrganization(yunwei);
		request.setAttribute("result", "增加运维机构成功！");
		logService.addLog(new TLog(user, "【" + user.getName() + "】"
				+ "添加了一个运维机构，名称为【  " + yunwei.getName() + "】 id为 "
				+ yunwei.getOrgId(), new Date()));
		page = "Admin_addYunwei";
		return TARGET;
	}

	/**
	 * 删除一个运维机构
	 * 
	 * @return
	 */
	public String deleteYunwei() {
		user = (TUser) session.get("user");
		yunwei = organizationService.findById(orgId);
		organizationService.delOrganization(orgId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】"
				+ "删除了一个运维机构，名称为 【 " + yunwei.getName() + "】 id为 "
				+ yunwei.getOrgId(), new Date()));
		redActionName = "Admin_yunweiManager";
		return REDIRECT;
	}

	/**
	 * 编辑一个运维机构
	 * 
	 * @return
	 */
	public String editYunweiInfo() {
		yunwei = organizationService.findById(orgId);
		areas = areaService.findAll();// 得到地区列表
		System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa" + areas.size());
		page = "Admin_editYunwei";
		return TARGET;
	}

	/**
	 * 保存一个运维机构的基本信息
	 * 
	 * @return
	 */
	public String saveYunweiInfo() {

		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		yunwei.setTArea(tArea);
		organizationService.updateOrganization(yunwei);
		request.setAttribute("result", "修改成功");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了【  "
				+ yunwei.getName() + "】的信息", new Date()));
		return editYunweiInfo();
	}

	/**
	 * 得到运维机构中的账户列表
	 * 
	 * @return
	 */
	public String yunweiUserManager() {
		findYunweiUserList();
		page = "Admin_yunweiUserManager";
		return TARGET;
	}

	/**
	 * 得到运维机构中的账户列表
	 * 
	 * @return
	 */
	public String findYunweiUserList() {
		yunwei = organizationService.findById(orgId);
		List<TUser> list = userService.findByOrgId(orgId);
		// 得到总记录数
		total = list.size();
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		yunweiUserList = userService.findByOrgId(orgId);
		page = "Admin_yunweiUserManager";
		return TARGET;
	}

	/**
	 * 添加一个运维账户之前的操作
	 * 
	 * @return
	 */
	public String beforeAddYunweiUser() {
		yunweiUser = new TUser();
		page = "Admin_addYunweiUser";
		return TARGET;
	}

	/**
	 * 添加一个运维账户
	 * 
	 * @return
	 */
	public String addYunweiUser() {
		if (userService.findByUserId(yunweiUser.getUserid()) != null) {
			request.setAttribute("result", "用户名已存在");
			return SUCCESS;
		}
		user = (TUser) session.get("user");
		yunweiUser.setTOrgainzation(yunwei);
		yunweiUser.setRegistertime(new Date());
		userService.addUser(yunweiUser);
		request.setAttribute("result", "添加成功");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "为"
				+ yunwei.getName() + "添加了一个名为【  " + yunweiUser.getName()
				+ "】的账户", new Date()));
		page = "Admin_addYunweiUser";
		return TARGET;
	}

	/**
	 * 删除一个运维机构下的账户
	 * 
	 * @return
	 */
	public String deleteYunweiUser() {
		user = (TUser) session.get("user");
		yunwei = organizationService.findById(orgId);
		yunweiUser = userService.findById(yunweiUserId);
		userService.delUserById(yunweiUserId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了【"
				+ yunwei.getName() + "】的一个运维用户，名称为 【 " + yunweiUser.getName()
				+ "】id为 " + yunweiUser.getId(), new Date()));
		redActionName="Admin_yunweiUserManager";
		return REDIRECT;
	}

	/**
	 * 编辑一个运维机构下的账户
	 * 
	 * @return
	 */
	public String editYunweiUserInfo() {
		yunweiUser = userService.findById(yunweiUserId);
		page = "Admin_editYunweiUserInfo";
		return TARGET;
	}

	/**
	 * 保存一个运维机构的账户基本信息
	 * 
	 * @return
	 */
	public String saveYunweiUserInfo() {
		user = (TUser) session.get("user");
		yunwei = organizationService.findById(orgId);
		userService.updateUser(yunweiUser);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了【"
				+ yunwei.getName() + "】的一个运维用户  " + yunweiUser.getName()
				+ " 的信息", new Date()));
		request.setAttribute("result", "修改成功");
		yunweiUser = userService.findById(yunweiUserId);// 这里执行了很多条sql语句！user->org->users!
		page = "Admin_editYunweiUserInfo";
		return TARGET;
	}

	/**
	 * 按用户输入的运维机构名称模糊查询
	 */
	public String yunweiSearch() {
		total = organizationService.getCountYunweiMohu(orgSearchName); // 获取运维机构总数
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;
		orgs = organizationService.findYunweiMohu(orgSearchName,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_yunweiSearchResult";
		return TARGET;
	}

	// -----------------------------------集团用户管理----------------------------------------//

	/**
	 * 跳转到增加集团机构的界面
	 * 
	 * @return
	 */
	public String forwardAddJituan() {
		jituan = new TOrgainzation();// 防止干扰页面
		areas = areaService.findAll();// 得到地区列表
		page = "Admin_addJituan";
		return TARGET;
	}

	/**
	 * 添加新的集团机构
	 * 
	 * @return
	 */
	public String addJituan() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		jituan.setTArea(tArea);
		TOrgainzation rootOrg = organizationService.findRootOrg();// 找到root机构
		jituan.setTOrgainzation(rootOrg);
		jituan.setBalance(0);
		jituan.setOrgLevel(OrgLevel.JITUAN_LEVEL);// 集团机构
		jituan.setRegistertime(new Date());
		organizationService.addOrganization(jituan);
		request.setAttribute("result", "增加集团机构成功");
		logService.addLog(new TLog(user, "【" + user.getName() + "】"
				+ "添加了一个集团机构，名称为【  " + jituan.getName() + "】id为 "
				+ jituan.getOrgId(), new Date()));
		request.setAttribute("result", "增加集团机构成功！");
		page = "Admin_addJituan";
		return TARGET;
	}

	/**
	 * 删除一个集团机构
	 * 
	 * @return
	 */
	public String deleteJituan() {
		user = (TUser) session.get("user");
		jituan = organizationService.findById(orgId);
		organizationService.delOrganization(orgId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】"
				+ "删除了一个集团机构，名称为  【" + jituan.getName() + "】id为 "
				+ jituan.getOrgId(), new Date()));
		redActionName="Admin_jituanManager";
		return REDIRECT;
	}

	/**
	 * 编辑一个集团机构
	 * 
	 * @return
	 */
	public String editJituanInfo() {
		jituan = organizationService.findById(orgId);
		areas = areaService.findAll();// 得到地区列表
		page = "Admin_editJituan";
		return TARGET;
	}

	/**
	 * 保存一个集团机构的基本信息
	 * 
	 * @return
	 */
	public String saveJituanInfo() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		jituan.setTArea(tArea);
		organizationService.updateOrganization(jituan);
		request.setAttribute("result", "修改成功");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了 【 "
				+ jituan.getName() + "】的信息", new Date()));
		page = "Admin_editJituan";
		return TARGET;
	}

	/**
	 * 得到集团机构中的账户列表
	 * 
	 * @return
	 */
	public String jituanUserManager() {
		findJituanUserList();
		page = "Admin_jituanUserManager";
		return TARGET;
	}

	/**
	 * 得到集团机构中的账户列表
	 * 
	 * @return
	 */
	public String findJituanUserList() {
		jituan = organizationService.findById(orgId);
		List<TUser> list = userService.findByOrgId(orgId);
		// 得到总记录数
		total = list.size();
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		jituanUserList = userService.findByOrgId(orgId);
		page = "Admin_jituanUserManager";
		return TARGET;
	}

	/**
	 * 添加一个集团账户之前的操作
	 * 
	 * @return
	 */
	public String beforeAddJituanUser() {
		jituanUser = new TUser();
		page = "Admin_addJituanUser";
		return TARGET;
	}

	/**
	 * 添加一个集团账户
	 * 
	 * @return
	 */
	public String addJituanUser() {
		if (userService.findByUserId(jituanUser.getUserid()) != null) {
			request.setAttribute("result", "用户名已存在");
			return SUCCESS;
		}
		user = (TUser) session.get("user");
		jituanUser.setTOrgainzation(jituan);//
		jituanUser.setRegistertime(new Date());
		userService.addUser(jituanUser);
		request.setAttribute("result", "添加成功");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "为【"
				+ jituan.getName() + "】添加了一个名为 【 " + jituanUser.getName()
				+ "】的账户", new Date()));
		return SUCCESS;
	}

	/**
	 * 删除一个集团机构下的账户
	 * 
	 * @return
	 */
	public String deleteJituanUser() {
		user = (TUser) session.get("user");
		jituanUser = userService.findById(jituanUserId);
		jituan = organizationService.findById(orgId);
		userService.delUserById(jituanUserId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了【"
				+ jituan.getName() + "】的一个集团用户，名称为  【" + jituanUser.getName()
				+ "】id为 " + jituanUser.getId(), new Date()));
		redActionName="Admin_jituanUserManager";
		return REDIRECT;
	}

	/**
	 * 编辑一个集团机构下的账户
	 * 
	 * @return
	 */
	public String editJituanUserInfo() {
		jituanUser = userService.findById(jituanUserId);
		page = "Admin_editJituanUserInfo";
		return TARGET;
	}

	/**
	 * 保存一个集团机构的账户基本信息
	 * 
	 * @return
	 */
	public String saveJituanUserInfo() {
		user = (TUser) session.get("user");
		jituan = organizationService.findById(orgId);
		userService.updateUser(jituanUser);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了【"
				+ jituan.getName() + "】的一个集团用户 【 " + jituanUser.getName()
				+ "】 的信息", new Date()));
		request.setAttribute("result", "修改成功");
		page = "Admin_editJituanUserInfo";
		return TARGET;
	}

	/**
	 * 按用户输入的集团机构名称模糊查询
	 */
	public String jituanSearch() {
		total = organizationService.getCountJituanMohu(orgSearchName); // 获取运维机构总数
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		orgs = organizationService.findJituanMohu(orgSearchName,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_jituanSearchResult";
		return TARGET;
	}

	// --------------------------------集团下的分组----------------------------------------//
	/**
	 * 显示集团下的所有分组机构
	 * 
	 * @return
	 */
	public String groupManager() {
		findGroupList();
		page = "Admin_groupManager";
		return TARGET;
	}

	/**
	 * 查找当前集团用户管理下的分组机构（分页）
	 * 
	 * @return
	 */
	public void findGroupList() {
		jituan = organizationService.findById(orgId);
		total = organizationService.getCountAllGroupsByBloc(orgId);
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		groupList = organizationService.findAllGroupsByBloc(orgId,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
	}

	/**
	 * 添加新的分组机构之前的操作
	 * 
	 * @return
	 */
	public String beforeAddGroup() {
		orgainzation = new TOrgainzation();// 防止干扰页面
		areas = areaService.findAll();// 得到地区列表
		page = "Admin_addGroup";
		return TARGET;
	}

	/**
	 * 添加新的分组机构
	 * 
	 * @return
	 */
	public String addGroup() {
		orgainzation.setTOrgainzation(jituan);
		orgainzation.setFeestandard(jituan.getFeestandard());
		orgainzation.setOrgLevel(3);// 分组机构
		TArea area = areaService.findById(selectAreaId);
		orgainzation.setTArea(area);
		orgainzation.setBalance(0);
		orgainzation.setFeestandard(jituan.getFeestandard());
		orgainzation.setRegistertime(new Date());// 注册时间为当前时间
		organizationService.addOrganization(orgainzation);
		request.setAttribute("result", "增加分组机构成功");

		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "为【"
				+ jituan.getName() + "】添加了新的分组机构【" + orgainzation.getName()
				+ "】", new Date()));
		page = "Admin_addGroup";
		return TARGET;
	}

	/**
	 * 按用户输入的分组机构名称模糊查询
	 */
	public String groupSearch() {
		total = organizationService.getCountMohuByJituan(orgSearchName, orgId);
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		groupList = organizationService.findMohuByJituan(orgSearchName, orgId,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_groupSearchResult";
		return TARGET;
	}

	/**
	 * 删除一个分组机构
	 * 
	 * @return
	 */
	public String deleteGroup() {
		user = (TUser) session.get("user");
		orgainzation = organizationService.findById(groupId);
		organizationService.delOrganization(groupId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了【"
				+ jituan.getName() + "】下的分组机构【" + orgainzation.getName() + "】",
				new Date()));
		redActionName="Admin_groupManager";
		return REDIRECT;
	}

	/**
	 * 编辑一个分组机构
	 * 
	 * @return
	 */
	public String editGroupInfo() {
		orgainzation = organizationService.findById(groupId);
		areas = areaService.findAll();// 得到地区列表
		jituan = orgainzation.getTOrgainzation();// 得到分组的上一级集团
		page = "Admin_editGroupInfo";
		return TARGET;
	}

	/**
	 * 保存一个分组机构的基本信息
	 * 
	 * @return
	 */
	public String saveGroupInfo() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		orgainzation.setTArea(tArea);
		organizationService.updateOrganization(orgainzation);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了"
				+ jituan.getName() + "下的分组机构【" + orgainzation.getName() + "】",
				new Date()));
		request.setAttribute("result", "修改成功！");
		page = "Admin_editGroupInfo";
		return TARGET;
	}

	/**
	 * 得到分组机构中的账户列表
	 * 
	 * @return
	 */
	public String groupUserManager() {
		findGroupUserList();
		page = "Admin_groupUserManager";
		return TARGET;
	}

	/**
	 * 所有终端定位
	 * 
	 * @return
	 */
	public String allLocations() {
		List<TTerminal> terminals = terminalService.findByOrg(groupId);
		Iterator<TTerminal> it = terminals.iterator();
		tpositions = new ArrayList<TTempPosition>();
		total = terminals.size(); // 终端总数
		ptotal = 0;
		online = 0;
		outline = 0;
		while (it.hasNext()) {
			TTerminal p = it.next();
			if (p.getTTempPositions() != null
					&& p.getTTempPositions().size() == 1) {
				TTempPosition ttp = p.getTTempPositions().get(0);
				if (ttp.getLatitude() != -1) { // 如果当前终端含有定位信息
					tpositions.add(PositionConvertUtil.convert(ttp));
					ptotal++;
					if (p.getNetstatus() == 1)
						online++;
					else
						outline++;
				}
			}
		}
		System.err.println(tpositions.size() + "^^^^^^^^^^^^^^^^^6");
		page = "Terminal_allLocations";
		return TARGET;
	}

	/**
	 * 
	 * @return
	 */
	public String findGroupUserList() {
		orgainzation = organizationService.findById(groupId);
		// 得到总记录数
		total = userService.findByOrgId(groupId).size();
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		groupUserList = userService.findByOrgId(groupId);
		page = "Admin_groupUserManager";
		return TARGET;
	}

	/**
	 * 添加一个账户之前的操作
	 * 
	 * @return
	 */
	public String beforeAddGroupUser() {
		groupUser = new TUser();
		page = "Admin_addGroupUser";
		return TARGET;
	}

	/**
	 * 添加一个账户
	 * 
	 * @return
	 */
	public String addGroupUser() {
		if (userService.findByUserId(groupUser.getUserid()) != null) {
			request.setAttribute("result", "用户名已存在");
			return SUCCESS;
		}
		user = (TUser) session.get("user");
		groupUser.setTOrgainzation(orgainzation);// orgainzation中是有值的，必须滴！
		groupUser.setRegistertime(new Date());
		userService.addUser(groupUser);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "为【"
				+ jituan.getName() + "】下的分组机构【" + orgainzation.getName()
				+ "】添加了账户【" + groupUser.getName() + "】", new Date()));
		request.setAttribute("result", "添加成功");
		return SUCCESS;
	}

	/**
	 * 删除一个分组机构下的账户
	 * 
	 * @return
	 */
	public String deleteGroupUser() {
		user = (TUser) session.get("user");
		TUser tUser = userService.findById(groupUserId);
		userService.delUserById(groupUserId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】为【"
				+ jituan.getName() + "】删除了分组机构【" + orgainzation.getName()
				+ "】下的账户【" + tUser.getName() + "】", new Date()));
		redActionName="Admin_groupUserManager";
		return REDIRECT;
	}

	/**
	 * 编辑一个分组机构下的账户
	 * 
	 * @return
	 */
	public String editGroupUserInfo() {
		groupUser = userService.findById(groupUserId);
		page = "Admin_editGroupUserInfo";
		return TARGET;
	}

	/**
	 * 保存一个分组机构的账户基本信息
	 * 
	 * @return
	 */
	public String saveGroupUserInfo() {
		user = (TUser) session.get("user");
		userService.updateUser(groupUser);
		request.setAttribute("result", "修改成功！");
		logService.addLog(new TLog(user, "【" + user.getName() + "】为"
				+ jituan.getName() + "】修改了分组机构【" + orgainzation.getName()
				+ "】下的账户【" + groupUser.getName() + "】的信息", new Date()));
		page = "Admin_editGroupUserInfo";
		return TARGET;
	}

	// ---------------------集团下的分组机构下的终端------------------------------//
	/**
	 * 查看某个分组下的终端
	 * 
	 * @@return
	 */
	public String terminalManager() {
		total = terminalService.getCountByOrg(groupId);
		orgainzation = organizationService.findById(groupId);
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		terminals = terminalService.findByOrg(groupId, (pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_terminalManager";
		return TARGET;
	}

	/**
	 * 根据搜索条件查询终端
	 */
	public String terminalSearch() {
		if ("1".equals(option)) { // 机器编号
			total = terminalService.getCountMohuByOrgAndCarNumber(value,
					groupId);
		} else if ("2".equals(option)) {// 联系人
			total = terminalService
					.getCountMohuByOrgAndUserName(value, groupId);
		} else if ("3".equals(option)) { // 终端号码
			total = terminalService.getCountMohuByOrgAndSim(value, groupId);
		}
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;

		if ("1".equals(option)) { // 机器编号
			terminals = terminalService.findMohuByOrgAndCarNumber(value,
					groupId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("2".equals(option)) {// 联系人
			terminals = terminalService.findMohuByOrgAndUserName(value,
					groupId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("3".equals(option)) { // 终端号码
			terminals = terminalService.findMohuByOrgAndSim(value, groupId,
					(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		}
		page = "Admin_terminalSearchResult";
		return TARGET;
	}

	/**
	 * 跳往添加终端用户界面
	 * 
	 * @@return
	 */
	public String addTerminal() {
		areas = areaService.findAll();// 得到地区列表
		cars = carInfoService.findAll();
		terminal = new TTerminal();
		return SUCCESS;
	}

	/**
	 * 保存终端用户信息
	 * 
	 * @@return
	 */
	public String saveTerminal() {
		TTerminal temp = terminalService.findBySim(terminal.getSim());
		if (temp != null) {
			request.setAttribute("result", "终端编号已存在!");
			page = "Admin_addTerminal";
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "该SIM卡号已经存在!");
			page = "Admin_addTerminal";
			return TARGET;
		}
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminal.setRegistertime(new Date());
		TUser user = (TUser) session.get("user");
		terminal.setTOrgainzation(orgainzation);
		terminalService.addTerminal(terminal);

		// terminal = terminalService.findBySim(terminal.getSim());
		TTempPosition position = new TTempPosition();
		position.setSim(terminal.getSim());
		position.setTTerminal(terminal);
		position.setLatitude(-1);
		position.setLongitude(-1);
		position.setLocationModel("");
		position.setPTime(new Date());
		tempPositionService.addTempPosition(position);
		page = "Admin_addTerminal";
		request.setAttribute("result", "新增终端用户成功!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "新增了终端【"
				+ terminal.getSim() + "】", new Date()));
		return TARGET;
	}

	/**
	 * 删除终端用户信息
	 * 
	 * @return
	 */
	public String deleteTerminal() {
		// terminalService.delTerminalById(tid);
		terminalService.delTerminalBySim(sim);
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了终端【"
				+ sim + "】", new Date()));
		redActionName="Admin_terminalManager";
		return REDIRECT;
	}

	/**
	 * 编辑终端用户信息
	 * 
	 * @return
	 */
	public String editTerminal() {
		// 获取终端信息
		areas = areaService.findAll();// 得到地区列表
		terminal = terminalService.findBySim(sim);
		cars = carInfoService.findAll();
		return SUCCESS;
	}

	/**
	 * 更新终端用户信息
	 * 
	 * @return
	 */
	public String updateTerminal() {
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminalService.updateTerminal(terminal);
		page = "Admin_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了终端【"
				+ terminal.getSim() + "】", new Date()));
		request.setAttribute("result", "修改成功！");
		return TARGET;
	}

	// 跳转到转移终端页面
	public String changeTerminal() {

		terminal = terminalService.findBySim(sim);
		orgs = organizationService.findAllBloc();
		return SUCCESS;
	}

	// 转移终端
	public String transferTerminal() {
		TOrgainzation t1 = organizationService.findById(groupID);
		terminal.setTOrgainzation(t1);
		terminalService.updateTerminal(terminal);
		request.setAttribute("result", "转移终端成功！");

		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了终端【"
				+ terminal.getSim() + "】", new Date()));

		page = "Admin_changeTerminal";
		return TARGET;
	}

	// -----------------------------------日志管理----------------------------------------//
	/**
	 * 日志管理
	 * 
	 * @return
	 */
	public String logManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// 前三天的凌晨
		searchStartDate = calendar.getTime();// 设定开始和结束时间
		pageIndex = 0;
		findLogList();
		return SUCCESS;
	}

	/**
	 * 日志搜索
	 * 
	 * @return
	 */
	public String logSearch() {
		// 通过前台来设置开始和结束时间，但是要做一些判断
		if (searchEndDate.before(searchStartDate)) {
			request.setAttribute("result", "时间设置有误！请保证结束时间在开始时间之后！");
			page = "Admin_logManager";
			return TARGET;
		}
		findLogList();
		return TARGET;
	}

	/**
	 * 得到指定时间之内的日志列表，用于分页
	 * 
	 * @return
	 */
	public String findLogList() {
		// 得到总记录数
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// 后一天的凌晨
		searchEndDate = calendar.getTime();
		total = logService
				.getCountByTimeBetween(searchStartDate, searchEndDate);
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		logList = logService.findByTimeBetween(searchStartDate, searchEndDate,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		calendar.add(Calendar.DATE, -1);// 使页面显示符合人们习惯
		searchEndDate = calendar.getTime();
		page = "Admin_logManager";
		return TARGET;
	}

	// -----------------------------------数据报表管理---------------------------------------//
	// -----------------------------------车型信息管理---------------------------------------//
	/**
	 * 删除一个车型信息
	 * 
	 * @return
	 */
	public String deleteCarInfo() {
		user = (TUser) session.get("user");
		TCarInfo carInfo = carInfoService.findById(carTypeId);
		carInfoService.deleteCarInfo(carTypeId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了"
				+ "名为【 " + carInfo.getTypeName() + "】 的车型", new Date()));
		return carManager();
	}

	/**
	 * 添加一个车型信息之前的操作
	 * 
	 * @return
	 */
	public String forwardAddCarInfo() {
		carInfo = new TCarInfo();
		page = "Admin_addCarInfo";
		return TARGET;
	}

	/**
	 * 添加一个车型信息
	 * 
	 * @return
	 */
	public String addCarInfo() {
		user = (TUser) session.get("user");
		carInfoService.addCarInfo(carInfo);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "增加了"
				+ "名为【 " + carInfo.getTypeName() + "】 的车型", new Date()));
		return carManager();
	}

	/**
	 * 编辑一个车型
	 * 
	 * @return
	 */
	public String editCarInfo() {
		carInfo = carInfoService.findById(carTypeId);
		page = "Admin_editCarInfo";
		return TARGET;
	}

	/**
	 * 保存一个车型信息
	 * 
	 * @return
	 */
	public String saveCarInfo() {
		user = (TUser) session.get("user");
		carInfoService.updateCarInfo(carInfo);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了"
				+ "名为 【" + carInfo.getTypeName() + " 的车型信息", new Date()));
		return carManager();
	}

	// -----------------------------------地区信息管理---------------------------------------//
	/**
	 * 删除一个地区信息
	 * 
	 * @return
	 */
	public String deleteArea() {
		user = (TUser) session.get("user");
		TArea area = areaService.findById(areaId);
		areaService.deleleArea(areaId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了"
				+ "名为【 " + area.getName() + "】的地区信息", new Date()));
		return areaManager();
	}

	/**
	 * 添加一个地区信息之前的操作
	 * 
	 * @return
	 */
	public String forwardAddArea() {
		area = new TArea();
		page = "Admin_addArea";
		return TARGET;
	}

	/**
	 * 添加一个地区信息
	 * 
	 * @return
	 */
	public String addArea() {
		user = (TUser) session.get("user");
		areaService.addArea(area);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "增加了"
				+ "名为 【" + area.getName() + "】的地区信息", new Date()));
		return areaManager();
	}

	/**
	 * 编辑一个地区
	 * 
	 * @return
	 */
	public String editArea() {
		area = areaService.findById(areaId);
		page = "Admin_editArea";
		return TARGET;
	}

	/**
	 * 保存一个地区
	 * 
	 * @return
	 */
	public String saveArea() {
		user = (TUser) session.get("user");
		areaService.updateArea(area);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了"
				+ "名为【 " + area.getName() + "】的地区信息", new Date()));
		return areaManager();
	}

	// ------------------------------------服务费管理---------------------------------------//

	/**
	 * 跳到充值界面
	 */
	public String beforeRecharge() {
		jituan = organizationService.findById(orgId);
		page = "Admin_beforeRecharge";
		return TARGET;
	}

	/**
	 * 给集团充值
	 * 
	 * @return
	 */
	public String recharge() {
		user = (TUser) session.get("user");
		// 这个很重要 ，防止作弊。从数据库中重新取jituan的信息，而不用客户端返回的信息
		jituan = organizationService.findById(jituan.getOrgId());
		jituan.setBalance(jituan.getBalance() + expense);
		organizationService.updateOrganization(jituan);
		request.setAttribute("result2", "充值成功");

		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "为【"
				+ jituan.getName() + "】充值" + expense + "元", new Date()));

		account = new TAccount();
		account.setExpense(expense);
		account.setPaiddate(new Date());

		TUser user2 = (TUser) session.get("user");
		account.setPaider(user2.getName());
		account.setTOrgainzation(jituan);
		account.setRemark(user2.getName() + "给【" + jituan.getName() + "】充值"
				+ expense + "元");

		accountService.addAccount(account);
		page = "Admin_beforeRecharge";
		return TARGET;
	}

	/**
	 * 充值账单管理
	 * 
	 * @return
	 */
	public String accountManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// 前三天的凌晨
		searchStartDate = calendar.getTime();// 设定开始和结束时间
		findAccounts();
		return SUCCESS;
	}

	/**
	 * 充值账单搜索
	 * 
	 * @return
	 */
	public String accountSearch() {
		// 通过前台来设置开始和结束时间，但是要做一些判断
		if (searchEndDate.before(searchStartDate)) {
			request.setAttribute("result", "时间设置有误！请保证结束时间在开始时间之后！");
			page = "Admin_accountManager";
			return TARGET;
		}
		findAccounts();
		return TARGET;
	}

	/**
	 * 得到指定时间之内的充值账单列表，用于分页
	 * 
	 * @return
	 */
	public String findAccounts() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// 后一天的凌晨
		searchEndDate = calendar.getTime();
		// 得到总记录数
		total = accountService.getCountByOrgLevel(OrgLevel.JITUAN_LEVEL,
				searchStartDate, searchEndDate);
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		accounts = accountService.findByOrgLevel(OrgLevel.JITUAN_LEVEL,
				searchStartDate, searchEndDate, (pageIndex - 1)
						* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		calendar.add(Calendar.DATE, -1);// 使页面显示符合人们习惯
		searchEndDate = calendar.getTime();
		page = "Admin_accountManager";
		return TARGET;
	}

	// -------------------------------处理系统左侧的菜单点击事件-----------------------------------//
	// -------------------------------处理系统左侧的菜单点击事件-----------------------------------//
	// -------------------------------处理系统左侧的菜单点击事件-----------------------------------//
	/**
	 * 查询系统管理员基本资料
	 * 
	 * @return
	 */
	public String basicInfo() {
		// 直接从会话中获取
		user = (TUser) session.get("user");
		return SUCCESS;
	}

	/**
	 * 运维机构
	 * 
	 * @return
	 */
	public String yunweiManager() {
		total = organizationService.getCountAllOM(); // 获取运维机构总数
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;
		orgs = organizationService.findAllOM((pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_yunweiManager";
		return TARGET;
	}

	/**
	 * 集团机构管理
	 * 
	 * @return
	 */
	public String jituanManager() {
		total = organizationService.getCountAllBloc(); // 获取集团机构总数
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;
		orgs = organizationService.findAllBloc((pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_jituanManager";
		return TARGET;
	}

	/**
	 * 目标类型 车型信息管理
	 * 
	 * @return
	 */
	public String carManager() {
		total = carInfoService.getCountAll();
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;
		cars = carInfoService.findAll((pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_carManager";
		return TARGET;
	}

	/**
	 * 地区信息管理
	 * 
	 * @return
	 */
	public String areaManager() {
		total = areaService.getCountAll();
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		areas = areaService.findAll((pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_areaManager";
		return TARGET;
	}

	/**
	 * 费用充值
	 * 
	 * @return
	 */
	public String expenseManager() {
		total = organizationService.getCountAllBloc(); // 获取集团机构总数
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		orgs = organizationService.findAllBloc((pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_expenseManager";
		return TARGET;
	}

	/**
	 * 费用充值下的搜索集团
	 */
	public String expenseJituanSearch() {
		total = organizationService.getCountJituanMohu(orgSearchName); // 获取运维机构总数
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		orgs = organizationService.findJituanMohu(orgSearchName,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_expenseManagerSearchResult";
		return TARGET;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	// ------------------------------ 数据报表管理
	// --------------------------------------//

	/**
	 * 数据报表管理列表
	 */
	public String excelManager() {

		user = (TUser) session.get("user");
		isSearching = false;
		searchString = "";
		cols = "";
		searchWeiyi = -1;// 这个很重要，进入的时候不搜索！
		onoff = 2;
		pageIndex = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
		// 得到所有的集团
		jituanList = organizationService.findAllBloc();
		// 得到运维下所有的分组
		groupList = organizationService.findAllGroupsByBloc(user
				.getTOrgainzation().getOrgId());
		// 得到所有的车类型
		carInfoList = carInfoService.findAll();
		findTerminalExcelList();
		return SUCCESS;
	}

	/**
	 * 得到集团下的分组列表
	 */
	public String findJiTuanGroupList() {
		jituanGroupList = organizationService.findAllGroupsByBloc(jituanId);
		page = "Admin_excelManager";
		return TARGET;
	}

	public String terminalExcelSearch() {
		isSearching = true;
		findTerminalExcelList();
		page = "Admin_excelManager";
		return TARGET;
	}

	/**
	 * 得到搜索结果的终端列表
	 * 
	 * @return
	 */
	public String findTerminalExcelList() {
		terminalExcelList = new ArrayList<TTerminal>();
		if (!isSearching) {
			total = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
			pages = 0;
			pageIndex = 0;
			page = "Admin_excelManager";
			return TARGET;
		}

		if (searchEndDate == null && searchFromDate == null && onoff == 2
				&& jituanId == 0 && searchGroupId == 0 && carType == 0) {
			total = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
			pages = 0;
			pageIndex = 0;
			page = "Admin_excelManager";
			return TARGET;
		}

		DetachedCriteria detachedCriteria = getCurrentCriteria();
		DetachedCriteria detachedCriteria2 = getCurrentCriteria();
		// 得到总记录数
		total = terminalService.getCountByCriteria(detachedCriteria);
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		terminalExcelList = terminalService.findAllByCriteria(
				detachedCriteria2, PAGESIZE * (pageIndex - 1), PAGESIZE);
//	    Calendar calendar = Calendar.getInstance();
//		calendar.setTime(searchEndDate);
//		calendar.add(Calendar.DATE, -1);// 符合人们习惯
//		searchEndDate = calendar.getTime();

		page = "Admin_excelManager";
		return TARGET;
	}

	// // 得到当前的模糊查询条件
	// private DetachedCriteria getCurrentMohuCriteria() {
	// DetachedCriteria criteria = DetachedCriteria.forClass(TTerminal.class);
	// if (searchWeiyi == 1) {// 按照 终端编号 搜索
	// // terminal = terminalService.findBySim(searchString);
	// criteria.add(Restrictions.like("sim", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 2) {// 按照终端手机号搜索
	// // terminal = terminalService.findByPhone(searchString);
	// criteria.add(Restrictions.like("phone", searchString,
	// MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 3) {// 车号码 -- 目标序列号
	// // terminal = terminalService.findByCarnumber(searchString);
	// criteria.add(Restrictions.like("carnumber", searchString,
	// MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 4) {// 用户名称
	// // terminal = terminalService.findByUsername(searchString);
	// criteria.add(Restrictions.like("username", searchString,
	// MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 5) {//
	// // terminal = terminalService.findByPrincipal(searchString);
	// criteria.add(Restrictions.like("principal", searchString,
	// MatchMode.ANYWHERE));
	// }
	// return criteria;
	// }

	// 得到当前的组合查询条件
	private DetachedCriteria getCurrentCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(TTerminal.class);
		if (searchFromDate != null) {
			detachedCriteria.add(Restrictions.ge("registertime", searchFromDate));
		}
		Date dd;
		if (searchEndDate != null) {
			dd = searchEndDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DATE, 1);// 后一天的凌晨
			dd = calendar.getTime();
			detachedCriteria.add(Restrictions.le("registertime", dd));
		}

		if (onoff != 2) {
			detachedCriteria.add(Restrictions.eq("netstatus", onoff));
		}
		if (carType != 0) {
			detachedCriteria.createAlias("TCarInfo", "carInfo").add(
					Restrictions.eq("carInfo.carTypeId", carType));
		}
		// 这里需要注意，如果选择了集团而没有选中分组的话就搜索出集团下的所有的终端
		if (searchGroupId != 0) {
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId",
					searchGroupId));
		} else {
			if (jituanId != 0) {
				detachedCriteria.createAlias("TOrgainzation", "t")
						.createAlias("t.TOrgainzation", "tt")
						.add(Restrictions.eq("tt.orgId", jituanId));
			}
		}
		return detachedCriteria;
	}

	// 根据选中的列导出数据
	public String excelExport() {
		String[] colValues = cols.split(",");
		if (colValues.length == 0) {
			page = "Admin_excelManager";
			return TARGET;
		}
		// /////////////////////////////// 创建Excel表开始
		// /////////////////////////////////////////////
		jxl.write.Label label;// label
		WritableWorkbook workbook;// 工作簿
		ByteArrayOutputStream out = null;// 字节输出流
		try {
			out = new ByteArrayOutputStream();
			// 创建可写入的 Excel工作薄
			workbook = Workbook.createWorkbook(out);
			// 创建Excel工作表
			WritableSheet sheet = workbook.createSheet("终端信息统计", 0);
			// 标题
			String[] title = { "终端编号", "终端SIM卡号", "目标序列号", "目标类型", "目标型号",
					"入网时间", "服务开始时间", "服务结束时间", "锁机状态", "用户名", "联系电话", "债券责任人",
					"所属集团", "所属分组", "工作状态", "工作时间积累(分钟)", "电瓶电压", "信号强度",
					"网络状态", "离线时间", "定位模式", "基站编号", "小区单元号", "纬度方向", "纬度",
					"经度方向", "经度" };

			boolean[] t = new boolean[title.length];

			System.out.println(colValues.length);// 21
			System.out.println(title.length);// 27
			int i = 0;
			// 列是否导出
			for (; i < colValues.length - 1; i++) {
				if (Integer.parseInt(colValues[i]) == 1) {
					t[i] = true;
				} else {
					t[i] = false;
				}
			}

			System.out.println(i);

			// 定位信息是否导出
			if (Integer.parseInt(colValues[colValues.length - 1]) == 1) {
				for (; i < t.length; i++) {
					t[i] = true;
				}
			} else {
				for (; i < t.length; i++) {
					t[i] = false;
				}
			}
			int index = 0;
			// 创建label，其中保存标题，并将label添加到sheet中
			for (int k = 0; k < title.length; k++) {
				if (t[k]) {
					label = new jxl.write.Label(index, 0, title[k]);// 第一行，index列
					sheet.addCell(label);
					sheet.setColumnView(index, 25);//
					index++;
				}
			}

			DetachedCriteria detachedCriteria = getCurrentCriteria();

			terminalExcelList = terminalService
					.findAllByCriteria(detachedCriteria);
			for (int j = 0; j < terminalExcelList.size(); j++) {
				index = 0;
				terminal = terminalExcelList.get(j);
				if (t[0])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getSim()));
				if (t[1])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getPhone()));
				if (t[2])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getCarnumber()));
				if (t[3])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getTCarInfo().getTypeName()));

				if (t[4])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getModel()));
				if (t[5]) {
					if (terminal.getRegistertime() != null
							&& dateFormat.format(terminal.getRegistertime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								dateFormat.format(terminal.getRegistertime())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[6]) {

					if (terminal.getStartTime() != null && dateFormat.format(terminal.getStartTime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, dateFormat.format(terminal.getStartTime())));

					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[7]) {

					if (terminal.getEndTime() != null && dateFormat.format(terminal.getEndTime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, dateFormat.format(terminal.getEndTime())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}

				if (t[8]) {
					String lockString = "";
					if (terminal.getLock() == 0) {
						lockString = "正常";
					} else if (terminal.getLock() == 1) {
						lockString = "低级锁机";
					} else if (terminal.getLock() == 2) {
						lockString = "高级锁机";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1,
							lockString));
				}
				if (t[9])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getUsername()));
				if (t[10])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getCellphone()));
				if (t[11])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getPrincipal()));
				if (t[12])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getTOrgainzation().getTOrgainzation().getName()));
				if (t[13])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getTOrgainzation().getName()));

				if (t[14]) {
					String workString = "";
					if (terminal.getLock() == 0) {
						workString = "空闲";
					} else if (terminal.getLock() == 1) {
						workString = "工作";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1,
							workString));
				}
				if (t[15])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String
							.valueOf(terminal.getWorktime())));
				if (t[16])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getElepress() == null ? "" : terminal
							.getElepress()));
				if (t[17])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String
							.valueOf(terminal.getSignal()).equalsIgnoreCase(
									"null") ? "" : String.valueOf(terminal
							.getSignal())));

				if (t[18]) {
					String netString = "";
					if (terminal.getNetstatus() == 1) {
						netString = "在线";
					} else if (terminal.getNetstatus() == 0) {
						netString = "离线";
					} else {
						netString = "未知";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1, netString));
				}
				if (t[19])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getRemark() == null ? "" : terminal.getRemark()));

				if (t[20])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getLocationModel()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[21]) {
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getStationId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[22])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getPlotId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[23])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getLatiDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[24])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								String.valueOf((terminal.getTTempPositions()
										.get(0)).getLatitude())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}

				if (t[25])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getLongDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[26])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								String.valueOf((terminal.getTTempPositions()
										.get(0)).getLongitude())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
			}
			workbook.write();
			workbook.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		excelStream = new ByteArrayInputStream(out.toByteArray());
		return "excel";
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 终端查询，详细信息页
	 */
	public String terminalDetail() {
		searchWeiyi = 0;
		searchString = "";
		terminal = null;
		return SUCCESS;
	}

	/**
	 * 查询终端详情
	 */
	public String terminalDetailSearch() {
		// if (searchWeiyi == 1 || searchWeiyi == 2 || searchWeiyi == 3 ||
		// searchWeiyi == 4 || searchWeiyi == 5) {// 唯一性搜索
		if (searchWeiyi == 1) {// 按照 终端编号 搜索
			terminal = terminalService.findBySim(searchString);
		} else if (searchWeiyi == 2) {// 按照终端手机号搜索
			terminal = terminalService.findByPhone(searchString);
		} else if (searchWeiyi == 3) {// 车号码 -- 目标序列号
			terminal = terminalService.findByCarnumber(searchString);
		} else if (searchWeiyi == 4) {// 用户名称
			terminal = terminalService.findByUsername(searchString);
		} else if (searchWeiyi == 5) {// 债券责任人
			terminal = terminalService.findByPrincipal(searchString);
		}
		// }
		page = "Admin_terminalDetail";
		return TARGET;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	// ---------------------------getters and setters-----------------//
	// ---------------------------getters and setters-----------------//
	// ---------------------------getters and setters-----------------//
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
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

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(
			OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public List<TOrgainzation> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<TOrgainzation> orgs) {
		this.orgs = orgs;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public List<TArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TArea> areas) {
		this.areas = areas;
	}

	public TOrgainzation getYunwei() {
		return yunwei;
	}

	public void setYunwei(TOrgainzation yunwei) {
		this.yunwei = yunwei;
	}

	public List<TLog> getLogList() {
		return logList;
	}

	public void setLogList(List<TLog> logList) {
		this.logList = logList;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setJituanUser(TUser jituanUser) {
		this.jituanUser = jituanUser;
	}

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
	}

	public List<TCarInfo> getCars() {
		return cars;
	}

	public void setCars(List<TCarInfo> cars) {
		this.cars = cars;
	}

	public AccountServiceImpl getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountServiceImpl accountService) {
		this.accountService = accountService;
	}

	public List<TAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<TAccount> accounts) {
		this.accounts = accounts;
	}

	public int getSelectAreaId() {
		return selectAreaId;
	}

	public void setSelectAreaId(int selectAreaId) {
		this.selectAreaId = selectAreaId;
	}

	public List<TUser> getYunweiUserList() {
		return yunweiUserList;
	}

	public void setYunweiUserList(List<TUser> yunweiUserList) {
		this.yunweiUserList = yunweiUserList;
	}

	public int getYunweiUserId() {
		return yunweiUserId;
	}

	public void setYunweiUserId(int yunweiUserId) {
		this.yunweiUserId = yunweiUserId;
	}

	public TUser getYunweiUser() {
		return yunweiUser;
	}

	public void setYunweiUser(TUser yunweiUser) {
		this.yunweiUser = yunweiUser;
	}

	public String getTARGET() {
		return TARGET;
	}

	public String getPage() {
		return page;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getSUCCESS() {
		return SUCCESS;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public TOrgainzation getJituan() {
		return jituan;
	}

	public void setJituan(TOrgainzation jituan) {
		this.jituan = jituan;
	}

	public List<TUser> getJituanUserList() {
		return jituanUserList;
	}

	public void setJituanUserList(List<TUser> jituanUserList) {
		this.jituanUserList = jituanUserList;
	}

	public int getJituanUserId() {
		return jituanUserId;
	}

	public void setJituanUserId(int jituanUserId) {
		this.jituanUserId = jituanUserId;
	}

	public TUser getJituanUser() {
		return jituanUser;
	}

	public void setJitaunUser(TUser jitaunUser) {
		this.jituanUser = jitaunUser;
	}

	public Date getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(Date searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public TArea getArea() {
		return area;
	}

	public void setArea(TArea area) {
		this.area = area;
	}

	public TCarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(TCarInfo carInfo) {
		this.carInfo = carInfo;
	}

	public int getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(int carTypeId) {
		this.carTypeId = carTypeId;
	}

	public TAccount getAccount() {
		return account;
	}

	public void setAccount(TAccount account) {
		this.account = account;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public String getOrgSearchName() {
		return orgSearchName;
	}

	public void setOrgSearchName(String orgSearchName) {
		this.orgSearchName = orgSearchName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<TUser> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TUser> groupList) {
		this.groupList = groupList;
	}

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public List<TTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<TTerminal> terminals) {
		this.terminals = terminals;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public TOrgainzation getOrgainzation() {
		return orgainzation;
	}

	public void setOrgainzation(TOrgainzation orgainzation) {
		this.orgainzation = orgainzation;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<TUser> getGroupUserList() {
		return groupUserList;
	}

	public void setGroupUserList(List<TUser> groupUserList) {
		this.groupUserList = groupUserList;
	}

	public TUser getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(TUser groupUser) {
		this.groupUser = groupUser;
	}

	public TempPositionServiceImpl getTempPositionService() {
		return tempPositionService;
	}

	public void setTempPositionService(
			TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public int getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(int groupUserId) {
		this.groupUserId = groupUserId;
	}

	public int getSearchWeiyi() {
		return searchWeiyi;
	}

	public void setSearchWeiyi(int searchWeiyi) {
		this.searchWeiyi = searchWeiyi;
	}

	public String getSearchTerminalNum() {
		return searchTerminalNum;
	}

	public void setSearchTerminalNum(String searchTerminalNum) {
		this.searchTerminalNum = searchTerminalNum;
	}

	public Date getSearchFromDate() {
		return searchFromDate;
	}

	public void setSearchFromDate(Date searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	public Date getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(Date searchToDate) {
		this.searchToDate = searchToDate;
	}

	public int getOnoff() {
		return onoff;
	}

	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public int getSearchGroupId() {
		return searchGroupId;
	}

	public void setSearchGroupId(int searchGroupId) {
		this.searchGroupId = searchGroupId;
	}

	public List<TTerminal> getTerminalExcelList() {
		return terminalExcelList;
	}

	public void setTerminalExcelList(List<TTerminal> terminalExcelList) {
		this.terminalExcelList = terminalExcelList;
	}

	public List<TCarInfo> getCarInfoList() {
		return carInfoList;
	}

	public void setCarInfoList(List<TCarInfo> carInfoList) {
		this.carInfoList = carInfoList;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
	}

	public boolean isSearching() {
		return isSearching;
	}

	public void setSearching(boolean isSearching) {
		this.isSearching = isSearching;
	}

	public String getSearchString() {
		return searchString;
	}

	public int getJituanId() {
		return jituanId;
	}

	public void setJituanId(int jituanId) {
		this.jituanId = jituanId;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<TOrgainzation> getJituanGroupList() {
		return jituanGroupList;
	}

	public void setJituanGroupList(List<TOrgainzation> jituanGroupList) {
		this.jituanGroupList = jituanGroupList;
	}

	public List<TOrgainzation> getJituanList() {
		return jituanList;
	}

	public void setJituanList(List<TOrgainzation> jituanList) {
		this.jituanList = jituanList;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public static int getPagesize() {
		return PAGESIZE;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<TTempPosition> getTpositions() {
		return tpositions;
	}

	public void setTpositions(List<TTempPosition> tpositions) {
		this.tpositions = tpositions;
	}

	public int getPtotal() {
		return ptotal;
	}

	public void setPtotal(int ptotal) {
		this.ptotal = ptotal;
	}

	public int getOutline() {
		return outline;
	}

	public void setOutline(int outline) {
		this.outline = outline;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getRedActionName() {
		return redActionName;
	}

	public void setRedActionName(String redActionName) {
		this.redActionName = redActionName;
	}

	public String getREDIRECT() {
		return REDIRECT;
	}

}
