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
import java.util.Set;

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
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;
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
import com.ztdz.tools.PositionConvertUtil;

/**
 * 该action服务于 集团用户请求的特殊响应
 * 
 * @author hujiawei
 * 
 *         2012-5-24
 */
public class JiTuanAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private int selectAreaId;
    private List<TArea> areas;
	private static final int PAGESIZE = 10;
	private Map<String, Object> session; // session 会话
	private HttpServletRequest request;// request
	private final String TARGET = "target"; // 当返回的页面是用户自定义页面的时候,不返回SUCCESS,而是返回TARGET
	private String page; // 用户自定义返回页面
	private final String REDIRECT = "redirect";
	private String redActionName;// 重定向到的action的名字

	private TUser user; // 用户信息

	/* 与前台有关的属性字段 */
	// 基本资料
	private String oldPwd, newPwd; // 用户基本信息 密码修改

	// 分组用户管理界面
	private List<TOrgainzation> groupList;// 前台要显示的分组机构列表
	private int orgId;// 分组用户的机构id，这个是用于编辑，删除
	private int selectOrgId;// 上面分组机构的集团机构id，这个是用于编辑时选择上一级集团机构
	private TOrgainzation orgainzation;// 进行编辑的分组机构
	private TOrgainzation jituan;// 上面分组机构的集团机构
	private List<TArea> areaList;// 地区列表
	private int areaId;// 地区编号
	private TArea area;// 选中的地区

	// 分组用户的账号管理界面
	private List<TUser> groupUserList;// 前台要显示的分组机构中的账户列表
	private int groupUserId;// 分组用户的账户id，这个是用于编辑，删除
	private TUser groupUser;// 分组用户的账户，把它提出来不用user是怕两者搞混了

	// 日志管理界面
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式实例
	private List<TLog> logList;// 前台要显示的日志列表

	// 服务费管理界面
	private TAccount account;// 前台编辑的账单
	private List<TAccount> accountList;// 账单列表

	private int terminalId;// 进行处理的终端编号
	private TTerminal terminal;// 进行编辑的终端
	private List<TTerminal> terminalList;// 终端列表

	private Date serviceStartDate;// 终端的服务起始时间
	private Date serviceEndDate;// 终端的服务结束时间

	private int serviceYear;// 充值年数

	// 数据报表管理
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

	// 与终端搜索有关
	private int searchType;
	private String sim;// 根据sim模糊查询终端

	// 两个与时间有关的属性，这两个常用于搜索中
	private Date searchStartDate;
	private Date searchEndDate;

	// 与搜索有关的属性 --- 这个是action内部的属性，不用get和set
	private boolean isSearching = false;// 这个属性表示是否当前的列表是处于搜索结果中，因为如果不设置的话那么搜索结果翻页无效
	private String searchString;// 要搜索的"名称"

	// 分页有关的数据 ---- TODO：这几个数据之间还存在着一些干扰！
	private int total;// 总数
	private int pageIndex;// 当前页
	private int pages;// 页面总数

	
	//终端定位管理
	private List<TTempPosition> tpositions; //所有客户定位信息
	private int ptotal; //含定位信息终端数目
	private int outline; //离线终端数
	private int online;  //在线终端数
	/**
	 * 由spring 完成服务注入
	 */
	private AreaServiceImpl areaService;
	private OrganizationServiceImpl organizationService;
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private AccountServiceImpl accountService;
	private TerminalServiceImpl terminalService;
	private CarInfoServiceImpl carInfoService;
	private TempPositionServiceImpl tempPositionService;

	private HibernateTransactionManager txManager;// 事务管理
	
	
	//---所有终端定位
	public String allLocations(){
		List<TTerminal> terminals = terminalService.findByOrg(orgId);
		Iterator<TTerminal> it = terminals.iterator();
		tpositions  = new ArrayList<TTempPosition>();
		total = terminals.size(); //终端总数
		while(it.hasNext()){
			TTerminal p = it.next();
			if(p.getTTempPositions() != null && p.getTTempPositions().size() == 1){
				TTempPosition ttp = p.getTTempPositions().get(0);
				if(ttp.getLatitude() != -1){        //如果当前终端含有定位信息
					tpositions.add(PositionConvertUtil.convert(ttp));
					ptotal++;
					if(p.getNetstatus() == 1) online++;
					else outline++;
				}
			}
		}
		page = "Terminal_allLocations";
		return TARGET;
	}

	// ------------------------------------基本资料----------------------------------------//

	/**
	 * 查询集团用户基本资料
	 * 
	 * @return
	 */
	public String basicInfo() {
		user = (TUser) session.get("user");// 直接从会话中获取
		return SUCCESS;
	}

	/**
	 * 保存集团用户基本资料
	 * 
	 * @return
	 */
	public String saveBasicInfo() {
		user = (TUser) session.get("user");
		if (oldPwd == null || "".equals(oldPwd) || !user.getPwd().equals(oldPwd)) {
			request.setAttribute("result", "旧密码输入错误！");
			page = "JiTuan_basicInfo";
			return TARGET;
		}
		// TODO:如果错误，跳到错误页面
		if (newPwd == null || "".equals(newPwd)) {
			request.setAttribute("result", "新密码不能为空！");
			page = "JiTuan_basicInfo";
			return TARGET;
		}
		user.setPwd(newPwd);
		userService.updateUser(user);
		request.setAttribute("result", "修改成功！");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了基本资料", new Date()));
		page = "JiTuan_basicInfo";
		return TARGET;
	}

	// ------------------------------------分组用户管理----------------------------------------//

	/**
	 * 进入分组用户管理界面
	 * 
	 * @return
	 */
	public String groupManager() {
		isSearching = false;// 每次遇到xxxManager方法都要将isSearching置为false，然后将搜索的字符串置为空
		searchString = "";
		pageIndex = 0;
		findGroupList();
		return SUCCESS;
	}

	/**
	 * TODO:根据输入的名称搜索分组用户
	 * 
	 * @return
	 */
	public String groupSearch() {
		System.out.println("search group name: " + searchString);
		// 查数据库然后调用findGroupList方法得到列表
		isSearching = true;// 每次遇到xxxSearch方法都要将isSearching置为true
		pageIndex = 0;
		findGroupList();
		return TARGET;
	}

	/**
	 * 查找当前运维用户管理下的分组机构（分页）
	 * 
	 * @return
	 */
	public String findGroupList() {
		user = (TUser) session.get("user");
		// 得到总记录数
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				total = organizationService.getCountMohuByJituan(searchString, user.getTOrgainzation().getOrgId());
			}
		} else {
			total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
		}
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
		System.out.println("pageIndex=" + pageIndex);
		// 得到该页显示的记录
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				groupList = organizationService.findMohuByJituan(searchString, user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			}
		} else {
			groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_groupManager";
		return TARGET;
	}

	/**
	 * 添加新的分组机构之前的操作
	 * 
	 * @return
	 */
	public String beforeAddGroup() {
		user = (TUser) session.get("user");
		orgainzation = null;// new是为了防止之前的数据
		jituan = null;// 不同于运维之处
		// 不同于运维之处，这里没有集团list，但是需要有地区列表
		areaList = areaService.findAll();
		page = "JiTuan_addGroup";
		return TARGET;
	}

	/**
	 * 添加新的分组机构
	 * 
	 * @return
	 */
	public String addGroup() {
		user = (TUser) session.get("user");
		jituan = user.getTOrgainzation();// 不同于运维之处
		area = areaService.findById(areaId);// 获取选中的地区
		orgainzation.setTArea(area);
		orgainzation.setTOrgainzation(jituan);
		orgainzation.setFeestandard(jituan.getFeestandard());
		orgainzation.setBalance(0);// 设置余额为0
		orgainzation.setOrgLevel(3);// 分组机构
		// orgainzation.setTArea(user.getTOrgainzation().getTArea());// 不同于运维之处！设置区域为当前的登录运维的管理区域
		orgainzation.setRegistertime(new Date());// 注册时间为当前时间
		organizationService.addOrganization(orgainzation);
		request.setAttribute("result", "添加成功！");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "添加了新的分组机构【" + orgainzation.getName() + "】", new Date()));
		// redActionName = "JiTuan_groupManager";// 这里还是重定向到列表中，一般不会多次添加！
		// return REDIRECT;
		// redActionName = "JiTuan_addGroup";//
		// return TARGET;
		return SUCCESS;
	}

	/**
	 * 删除一个分组机构
	 * 
	 * @return
	 */
	public String deleteGroup() {
		System.out.println(orgId);
		user = (TUser) session.get("user");
		organizationService.delOrganization(orgId);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了分组机构【" + orgainzation.getName() + "】", new Date()));
		redActionName = "JiTuan_groupManager";// 重定向到列表中
		return REDIRECT;
	}

	/**
	 * 编辑一个分组机构
	 * 
	 * @return
	 */
	public String editGroupInfo() {
		user = (TUser) session.get("user");
		areaList = areaService.findAll();
		System.out.println(orgId);
		orgainzation = organizationService.findById(orgId);
		jituan = orgainzation.getTOrgainzation();// 得到分组的上一级集团
		return SUCCESS;
	}

	/**
	 * 保存一个分组机构的基本信息
	 * 
	 * @return
	 */
	public String saveGroupInfo() {
		System.out.println(selectOrgId);
		user = (TUser) session.get("user");
		area = areaService.findById(areaId);// 获取选中的地区
		orgainzation.setTArea(area);
		organizationService.updateOrganization(orgainzation);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了分组机构【" + orgainzation.getName() + "】", new Date()));
		request.setAttribute("result", "修改成功！");
		page = "JiTuan_editGroupInfo";//
		return TARGET;// 这里不要重定向，还是编辑页面，提示成功
	}

	// ------------------------------------分组用户账户管理----------------------------------//

	/**
	 * 得到分组机构中的账户列表
	 * 
	 * @return
	 */
	public String groupUserManager() {
		pageIndex = 0;
		findGroupUserList();
		return SUCCESS;
	}

	/**
	 * 搜索分组机构账户
	 * 
	 * @return
	 */
	public String groupUserSearch() {
		findGroupUserList();
		page = "JiTuan_groupUserManager";
		return TARGET;
	}

	/**
	 * 得到分组机构中的账户列表 这里是假的，账户这里的分页和模糊搜索都是假的，每次得到的都是全部数据
	 * 
	 * @return
	 */
	public String findGroupUserList() {
		orgainzation = organizationService.findById(orgId);
		// 得到总记录数
		total = orgainzation.getTUsers().size();
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
		// groupUserList = orgainzation.getTUsers();//set--->顺序会乱
		groupUserList = userService.findByOrgId(orgId);
		page = "JiTuan_groupUserManager";
		return TARGET;
	}

	/**
	 * 添加一个账户之前的操作
	 * 
	 * @return
	 */
	public String beforeAddGroupUser() {
		groupUser = new TUser();
		page = "JiTuan_addGroupUser";
		return TARGET;
	}

	/**
	 * 添加一个账户
	 * 
	 * @return
	 */
	public String addGroupUser() {
		user = (TUser) session.get("user");
		TUser tempTUser = userService.findByUserId(groupUser.getUserid());
		if (tempTUser != null) {
			request.setAttribute("result", "账户名已经存在了！");
			// page = "JiTuan_addGroupUser";
			// return TARGET;
			return SUCCESS;
		}
		groupUser.setTOrgainzation(orgainzation);// orgainzation中是有值的，必须滴！
		groupUser.setRegistertime(new Date());
		userService.addUser(groupUser);
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "为分组机构【" + orgainzation.getName() + "】添加了账户【" + groupUser.getName() + "】", new Date()));
		request.setAttribute("result", "添加成功！");
		// page = "JiTuan_addGroupUser";
		// return TARGET;
		return SUCCESS;
	}

	/**
	 * 删除一个分组机构下的账户
	 * 
	 * @return
	 */
	public String deleteGroupUser() {
		user = (TUser) session.get("user");
		String remark = "【" + user.getName() + "】" + "删除了分组机构【" + orgainzation.getName() + "】下的账户【" + userService.findById(groupUserId).getName() + "】";
		userService.delUserById(groupUserId);
		logService.addLog(new TLog(user, remark, new Date()));
		redActionName = "JiTuan_groupUserManager";// 测试过了，可以进行action的重定向！这样保证了后退时看到的列表信息是最新的
		return REDIRECT;
		// page = "JiTuan_addGroupUser";
		// return TARGET;
	}

	/**
	 * 编辑一个分组机构下的账户
	 * 
	 * @return
	 */
	public String editGroupUserInfo() {
		groupUser = userService.findById(groupUserId);
		return SUCCESS;
	}

	/**
	 * 保存一个分组机构的账户基本信息
	 * 
	 * @return
	 */
	public String saveGroupUserInfo() {
		user = (TUser) session.get("user");
		try {
			userService.updateUser(groupUser);
			request.setAttribute("result", "修改成功！");
			logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了分组机构【" + orgainzation.getName() + "】下的账户【" + groupUser.getName() + "】的信息",
					new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "修改失败！");// 貌似要判断userid是否有效
		}
		// groupUser = userService.findById(groupUserId);// 这里执行了很多条sql语句！user->org->users!
		// redActionName = "JiTuan_groupUserManager";// 测试过了，可以进行action的重定向！这样保证了后退时看到的列表信息是最新的
		// return REDIRECT;
		page = "JiTuan_editGroupUserInfo";
		return TARGET;
	}

	// -----------------------------------日志管理----------------------------------------//

	/**
	 * 日志管理 日志管理不遵循前面的isSearching规律，它不处理isSearching
	 * 
	 * @return
	 */
	public String logManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.add(Calendar.DATE, 1);// 到下一天的凌晨
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
			page = "JiTuan_logManager";
			return TARGET;
		}
		pageIndex = 0;
		findLogList();
		return TARGET;
	}

	/**
	 * 得到指定时间之内的日志列表，用于分页
	 * 
	 * @return
	 */
	public String findLogList() {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// 后一天的凌晨
		searchEndDate=calendar.getTime();
		
		user = (TUser) session.get("user");
		// 得到总记录数
		total = logService.getCountByUserIdByTimeBetween(user.getUserid(), searchStartDate, searchEndDate);
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
		logList = logService.findByUserIdByTimeBetween(user.getUserid(), searchStartDate, searchEndDate, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		
		calendar.add(Calendar.DATE, -1);//使页面显示符合人们习惯
		searchEndDate=calendar.getTime();
		
		page = "JiTuan_logManager";
		return TARGET;
	}

	// ------------------------------ 数据报表管理 ---------------------------------------//

	/**
	 * 数据报表管理列表
	 */
	public String excelManager() {
		isSearching = false;
		searchString = "";
		cols = "";
		searchWeiyi = -1;// 这个很重要，进入的时候不搜索！
		pageIndex = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
		onoff = 2;
		searchFromDate = null;
		searchEndDate = null;
		searchGroupId = 0;
		carType = 0;
		// 得到运维下所有的分组
		groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId());
		// 得到所有的车类型
		carInfoList = carInfoService.findAll();
		findTerminalExcelList();
		return SUCCESS;
	}

	/**
	 * 搜索terminal
	 */
	public String terminalExcelSearch() {
		isSearching = true;
		findTerminalExcelList();
		page = "JiTuan_excelManager";
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
			page = "JiTuan_excelManager";
			return TARGET;
		}
		if (searchEndDate == null && searchFromDate == null && onoff == 2 && searchGroupId == 0 && carType == 0) {
			total = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
			pages = 0;
			pageIndex = 0;
			page = "JiTuan_excelManager";
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
		terminalExcelList = terminalService.findAllByCriteria(detachedCriteria2, PAGESIZE * (pageIndex - 1), PAGESIZE);
		page = "JiTuan_excelManager";
		return TARGET;
	}

	// 得到当前的条件
	private DetachedCriteria getCurrentCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
		user = (TUser) session.get("user");
		// 隐含的条件应该没有问题了
		detachedCriteria.createAlias("TOrgainzation", "t").createAlias("t.TOrgainzation", "tt")
				.add(Restrictions.eq("tt.orgId", user.getTOrgainzation().getOrgId()));
		if (searchFromDate != null) {
			detachedCriteria.add(Expression.ge("registertime", searchFromDate));
		}
		Date dd;
		if (searchEndDate != null) {
			dd = searchEndDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DATE, 1);// 后一天的凌晨
			dd = calendar.getTime();
			detachedCriteria.add(Expression.le("registertime", dd));
		}
		if (onoff != 2) {
			detachedCriteria.add(Restrictions.eq("netstatus", onoff));
		}
		if (searchGroupId != 0) {// 其他表的主键
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", searchGroupId));
		}
		if (carType != 0) {// 其他表的非主键
			detachedCriteria.createAlias("TCarInfo", "carInfo").add(Restrictions.eq("carInfo.carTypeId", carType));
		}
		return detachedCriteria;
	}

	// 根据选中的列导出数据
	public String excelExport() {
		String[] colValues = cols.split(",");
		if (colValues.length == 0) {
			page = "JiTuan_excelManager";
			return TARGET;
		}
		// /////////////////////////////// 创建Excel表开始 /////////////////////////////////////////////
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

			String[] title = { "终端编号", "终端SIM卡号", "目标序列号", "目标类型", "目标型号", "入网时间", "服务开始时间", "服务结束时间", "锁机状态", "用户名", "联系电话", "债券责任人", "所属集团", "所属分组", "工作状态",
					"工作时间积累(分钟)", "电瓶电压", "信号强度", "网络状态", "离线时间", "定位模式", "基站编号", "小区单元号", "纬度方向", "纬度", "经度方向", "经度" };
			boolean[] t = new boolean[title.length];
			int i = 0;
			// 列是否导出
			for (; i < colValues.length - 1; i++) {
				if (Integer.parseInt(colValues[i]) == 1) {
					t[i] = true;
				} else {
					t[i] = false;
				}
			}
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
			for (int j = 0; j < title.length; j++) {
				if (t[j]) {
					label = new jxl.write.Label(index, 0, title[j]);// 第一行，index列
					sheet.addCell(label);
					sheet.setColumnView(index, 25);
					index++;
				}
			}
			DetachedCriteria detachedCriteria = getCurrentCriteria();
			terminalExcelList = terminalService.findAllByCriteria(detachedCriteria);
			for (int j = 0; j < terminalExcelList.size(); j++) {
				index = 0;
				terminal = terminalExcelList.get(j);
				
				if (t[0])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getSim()));
				if (t[1])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getPhone()));
				if (t[2])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getCarnumber()));
				if (t[3])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getTCarInfo().getTypeName()));

				if (t[4])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getModel()));
				if (t[5]) {
					if (terminal.getRegistertime() != null && dateFormat.format(terminal.getRegistertime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, dateFormat.format(terminal.getRegistertime())));
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
					sheet.addCell(new jxl.write.Label(index++, j + 1, lockString));
				}
				if (t[9])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getUsername()));
				if (t[10])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getCellphone()));
				if (t[11])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getPrincipal()));
				if (t[12])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getTOrgainzation().getTOrgainzation().getName()));
				if (t[13])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getTOrgainzation().getName()));

				if (t[14]) {
					String workString = "";
					if (terminal.getLock() == 0) {
						workString = "空闲";
					} else if (terminal.getLock() == 1) {
						workString = "工作";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1, workString));
				}
				if (t[15])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf(terminal.getWorktime())));
				if (t[16])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getElepress() == null ? "" : terminal.getElepress()));
				if (t[17])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf(terminal.getSignal()).equalsIgnoreCase("null") ? "" : String
							.valueOf(terminal.getSignal())));

				if (t[18]) {
					String netString = "";
					if (terminal.getNetstatus() == 1) {
						netString = "在线";
					}  else if (terminal.getNetstatus() == 0){
						netString = "离线";
					} else{
						netString = "未知";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1, netString));
				}
				if (t[19])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getRemark() == null ? "" : terminal.getRemark()));

				if (t[20])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getLocationModel()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[21]) {
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getStationId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[22])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getPlotId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[23])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getLatiDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[24])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf((terminal.getTTempPositions().get(0)).getLatitude())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}

				if (t[25])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getLongDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[26])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf((terminal.getTTempPositions().get(0)).getLongitude())));
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

	// ------------------------------- 费用充值 --------------------------------------------//
	/**
	 * 分组用户费用管理
	 */
	public String groupExpenseManager() {
		user = (TUser) session.get("user");
		isSearching = false;
		pageIndex = 0;
		searchString = "";
		findGroupExpenseList();
		return SUCCESS;
	}

	/**
	 * 费用管理中的分组用户查询
	 * 
	 * @return
	 */
	public String groupExpenseSearch() {
		isSearching = true;
		pageIndex = 0;
		findGroupExpenseList();
		page = "JiTuan_groupExpenseManager";
		return TARGET;
	}

	/**
	 * 用于分页中的分组用户费用管理
	 * 
	 * @return
	 */
	public String findGroupExpenseList() {
		// 得到总记录数
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				total = organizationService.getCountMohuByJituan(searchString, user.getTOrgainzation().getOrgId());
			}
		} else {
			total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
		}
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
		System.out.println("pageIndex=" + pageIndex);
		// 得到该页显示的记录
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				groupList = organizationService.findMohuByJituan(searchString, user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			}
		} else {
			groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_groupExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// 分组用户的充值 /////////////////////////////////
	/**
	 * 进入组用户充值界面，这里并没有充值
	 * 
	 * @return
	 */
	public String groupExpenseAdd() {
		orgainzation = organizationService.findById(orgId);
		account = null;// 应该不需要new，如果不充值的话就产生了多余的对象
		return SUCCESS;
	}

	/**
	 * 给组用户充值
	 * 
	 * @return
	 */
	public String addGroupExpense() {
		user = (TUser) session.get("user");
		TOrgainzation pOrgainzation = user.getTOrgainzation();// 不同于运维,这里得到集团
		if (pOrgainzation.getBalance() < account.getExpense()) {// 集团机构的余额和充值金额比较，如果不足
			request.setAttribute("result", "集团机构的余额不足！");
			page = "JiTuan_groupExpenseAdd";// 重新输入，所以是重定向
			return TARGET;
		}
		if (orgainzation.getBalance() + account.getExpense() > 100000) {// 集团机构的余额和充值金额比较，如果不足
			request.setAttribute("result", "账户余额足够，不需要充值！");
			page = "JiTuan_groupExpenseAdd";// 重新输入，所以是重定向
			return TARGET;
		}
		// 集团减，分组加，不要忘记update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - account.getExpense());
		orgainzation.setBalance(orgainzation.getBalance() + account.getExpense());
		organizationService.updateOrganization(pOrgainzation);// 把提交语句都放在后面
		organizationService.updateOrganization(orgainzation);

		account.setTOrgainzation(orgainzation);
		account.setPaider(pOrgainzation.getName());
		account.setPaiddate(new Date());
		String remark = "分组机构【" + orgainzation.getName() + "】充值了" + account.getExpense() + "元";
		account.setRemark(remark);// 添加备注
		accountService.addAccount(account);

		logService.addLog(new TLog(user, remark, new Date()));

		request.setAttribute("result", "充值成功！");
		page = "JiTuan_groupExpenseAdd";
		return TARGET;
	}

	// ///////////////////////////// 分组用户的充值明细管理 /////////////////////////////////
	/**
	 * 分组用户的充值明细，可以查询
	 * 
	 * @return
	 */
	public String groupExpenseDetail() {
		// 设置起始时间和结束时间
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.add(Calendar.DATE, 1);// 这种方式可以，得到的是下一天
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// 前三天的凌晨
		searchStartDate = calendar.getTime();
		findGroupExpenseDetailList();
		return SUCCESS;
	}

	/**
	 * 分组用户的充值查询
	 * 
	 * @return
	 */
	public String groupExpenseDetailSearch() {
		// 这里得到了搜索的起始时间和结束时间
		findGroupExpenseDetailList();
		return TARGET;
	}

	/**
	 * 得到账单列表<br/>
	 * 单独将这个方法列出来是为了分页的需要，并且这个方法可以重用
	 * 
	 * @return
	 */
	public String findGroupExpenseDetailList() {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// 后一天的凌晨
		searchEndDate=calendar.getTime();
		// 得到总记录数
		total = accountService.getCountByOrgIdByTime(orgId, searchStartDate, searchEndDate);
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
		// 得到该页显示的记录
		accountList = accountService.findByOrgIdByTime(orgId, searchStartDate, searchEndDate, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		calendar.add(Calendar.DATE, -1);//使页面显示符合人们习惯
		searchEndDate=calendar.getTime();
		
		page = "JiTuan_groupExpenseDetail";
		return TARGET;
	}

	// /////////////////------ 分组机构的终端管理------////////////////////////////
	/**
	 * 终端列表管理
	 */
	public String terminalManager() {
		orgainzation = organizationService.findById(orgId);// 找到分组
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		findTerminalList();
		return TARGET;
	}

	/**
	 * 终端查询
	 * 
	 * @return
	 */
	public String terminalSearch() {
		isSearching = true;
		pageIndex = 0;
		findTerminalList();
		return TARGET;
	}

	/**
	 * 添加终端之前的操作
	 * 
	 * @return
	 */
	public String beforeAddTerminal() {
		areas = areaService.findAll();// 得到地区列表
		terminal = new TTerminal();
		carInfoList = carInfoService.findAll();
		user = (TUser) session.get("user");
		// 根据运维的地区得到分组列表
		groupList = organizationService.findAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
		page = "JiTuan_addTerminal";
		return TARGET;
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
			page = "JiTuan_addTerminal";
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "该SIM卡号已经存在!");
			page = "JiTuan_addTerminal";
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
		page = "JiTuan_addTerminal";
		request.setAttribute("result", "新增终端用户成功!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "新增了终端【" + terminal.getSim() + "】", new Date()));
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
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了终端【" + sim + "】", new Date()));
		return terminalManager();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String findTerminalList() {
		System.out.println(orgId);// 选中的分组id
		// 得到总记录数
		if (isSearching) {
			// total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
			if (searchString.equalsIgnoreCase("")) {// 为空则搜索全部
				total = terminalService.getCountByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// 终端编号查询
					total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
				} else if (searchType == 2) {// 目标序列号
					total = terminalService.getCountMohuByOrgAndCarNumber(searchString, orgId);
				}
			}
		} else {
			total = terminalService.getCountByOrg(orgId);
		}
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
		// System.out.println("pageIndex=" + pageIndex);
		// 得到该页显示的记录
		if (isSearching) {
			// terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
			if (searchString.equalsIgnoreCase("")) {
				terminalList = terminalService.findByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// 终端编号查询
					terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				} else if (searchType == 2) {// 目标序列号
					terminalList = terminalService.findMohuByOrgAndCarNumber(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				}
			}
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_terminalManager";
		return TARGET;
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
		carInfoList = carInfoService.findAll();
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
		page = "JiTuan_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了终端【" + terminal.getSim() + "】", new Date()));
		request.setAttribute("result", "修改成功！");
		return TARGET;
	}

	// //////////////////////////////// 分组用户下的终端用户的费用管理 ///////////////////////////////
	/**
	 * 分组用户下的终端用户的费用管理
	 */
	public String terminalExpenseManager() {
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		searchType = 0;
		findTerminalExpenseList();
		return SUCCESS;
	}

	/**
	 * 费用管理中的终端用户查询
	 * 
	 * @return
	 */
	public String terminalExpenseSearch() {
		if (searchType == 0) {// 虽然点击了搜索，但是没有选择搜索类型
			page = "JiTuan_terminalExpenseManager";
			return TARGET;
		}
		isSearching = true;
		pageIndex = 0;
		findTerminalExpenseList();
		page = "JiTuan_terminalExpenseManager";
		return TARGET;
	}

	/**
	 * 用于分页中的终端用户费用管理 终端的模糊搜索还没有底层支持
	 * 
	 * @return
	 */
	public String findTerminalExpenseList() {
		// System.out.println(orgId);// 选中的分组id
		// 得到总记录数
		user = (TUser) session.get("user");
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {// 为空则搜索全部
				total = terminalService.getCountByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// 终端编号查询
					total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
				} else if (searchType == 2) {// 目标序列号
					total = terminalService.getCountMohuByOrgAndCarNumber(searchString, orgId);
				}
			}
		} else {
			total = terminalService.getCountByOrg(orgId);
		}
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
		// System.out.println("pageIndex=" + pageIndex);
		// 得到该页显示的记录
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				terminalList = terminalService.findByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// 终端编号查询
					terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				} else if (searchType == 2) {// 目标序列号
					terminalList = terminalService.findMohuByOrgAndCarNumber(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				}
			}
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_terminalExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// 终端用户的充值 /////////////////////////////////
	/**
	 * 进入终端用户充值界面，这里并没有充值
	 * 
	 * @return
	 */
	public String terminalExpenseAdd() {
		// System.out.println("terminal Id = " + terminalId);
		terminal = terminalService.findById(terminalId);
		account = null;// 应该不需要new，如果不充值的话就产生了多余的对象
		// account = new TAccount();
		return SUCCESS;
	}

	/**
	 * 给终端用户充值
	 * 
	 */
	public String addTerminalExpense() {
		if (serviceYear == 0) {
			request.setAttribute("result", "请选择充值年数！");
			page = "JiTuan_terminalExpenseAdd";
			return TARGET;
		}
		terminal = terminalService.findById(terminalId);
		// terminal.getTOrgainzation(),organizationService.findById(orgId)得到的org是null
		TOrgainzation pOrgainzation = organizationService.findById(orgId);// pOrgainzation不为null，但是数据为空
		if (pOrgainzation.getBalance() < pOrgainzation.getFeestandard() * serviceYear) {// 分组机构的余额和服务年费比较，如果不足
			request.setAttribute("result", "分组机构的余额不足！");
			page = "JiTuan_terminalExpenseAdd";
			return TARGET;
		}
		// 分组减，不要忘记update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - pOrgainzation.getFeestandard() * serviceYear);
		organizationService.updateOrganization(pOrgainzation);// 提交语句

		// 充值成功，更新terminal
		Date today = new Date();
		if (terminal.getEndTime().before(today)) {// 服务已经结束了
			terminal.setStartTime(today);
		} /*else {
			terminal.setStartTime(terminal.getEndTime());
		}*/
		// 根据输入年份得到结束时间
		Date toDate = terminal.getStartTime();
		Date newDate = new Date((toDate.getYear() + serviceYear), toDate.getMonth(), toDate.getDate());
		terminal.setEndTime(newDate);
		terminalService.updateTerminal(terminal);

		// 插入一条账单
		account.setTOrgainzation(pOrgainzation);// 给终端充值时账单中显示的还是分组用户
		account.setPaiddate(today);
		account.setPaider(pOrgainzation.getName());
		account.setExpense(pOrgainzation.getFeestandard() * serviceYear);
		account.setRemark("终端【" + terminal.getSim() + "】充值了" + serviceYear + "年");// 添加备注
		accountService.addAccount(account);// TODO：这里报错！

		// 插入一条账单和日志信息
		String remark = "【" + user.getName() + "】为终端【" + terminal.getSim() + "】充值了，服务时间是【" + dateFormat.format(terminal.getStartTime()) + "】-【"
				+ dateFormat.format(terminal.getEndTime()) + "】";
		logService.addLog(new TLog(user, remark, today));

		request.setAttribute("result", "充值成功！");
		page = "JiTuan_terminalExpenseAdd";
		return TARGET;
	}

	// 终端用户的充值查询和充值明细没有！

	// //////////////set and getters./////////////////
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public int getSelectOrgId() {
		return selectOrgId;
	}

	public void setSelectOrgId(int selectOrgId) {
		this.selectOrgId = selectOrgId;
	}

	public TUser getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(TUser groupUser) {
		this.groupUser = groupUser;
	}

	public int getGroupUserId() {
		return groupUserId;
	}

	public List<TLog> getLogList() {
		return logList;
	}

	public void setLogList(List<TLog> logList) {
		this.logList = logList;
	}

	public void setGroupUserId(int groupUserId) {
		this.groupUserId = groupUserId;
	}

	public String getRedActionName() {
		return redActionName;
	}

	public void setRedActionName(String redActionName) {
		this.redActionName = redActionName;
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

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<TOrgainzation> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TOrgainzation> groupList) {
		this.groupList = groupList;
	}

	public List<TUser> getGroupUserList() {
		return groupUserList;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setGroupUserList(List<TUser> groupUserList) {
		this.groupUserList = groupUserList;
	}

	public void setOrganizationService(OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public String getOldPwd() {
		return oldPwd;
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

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public TOrgainzation getJituan() {
		return jituan;
	}

	public void setJituan(TOrgainzation jituan) {
		this.jituan = jituan;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
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

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public TOrgainzation getOrgainzation() {
		return orgainzation;
	}

	public void setOrgainzation(TOrgainzation orgainzation) {
		this.orgainzation = orgainzation;
	}

	public TAccount getAccount() {
		return account;
	}

	public void setAccount(TAccount account) {
		this.account = account;
	}

	public List<TAccount> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<TAccount> accountList) {
		this.accountList = accountList;
	}

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
	}

	public List<TTerminal> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TTerminal> terminalList) {
		this.terminalList = terminalList;
	}

	public AccountServiceImpl getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountServiceImpl accountService) {
		this.accountService = accountService;
	}

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public HibernateTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(HibernateTransactionManager txManager) {
		this.txManager = txManager;
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

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public List<TArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<TArea> areaList) {
		this.areaList = areaList;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
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

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public int getServiceYear() {
		return serviceYear;
	}

	public void setServiceYear(int serviceYear) {
		this.serviceYear = serviceYear;
	}

	public TempPositionServiceImpl getTempPositionService() {
		return tempPositionService;
	}

	public void setTempPositionService(TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
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

	public List<TArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TArea> areas) {
		this.areas = areas;
	}

	public int getSelectAreaId() {
		return selectAreaId;
	}

	public void setSelectAreaId(int selectAreaId) {
		this.selectAreaId = selectAreaId;
	}

}
