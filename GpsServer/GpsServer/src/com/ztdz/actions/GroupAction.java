package com.ztdz.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.ztdz.tools.Page;

/**
 * 该action服务于组用户请求的特殊响应
 * 
 * @@author wuxuehong
 * 
 *          2012-5-24
 */
public class GroupAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private int selectAreaId;
    private List<TArea> areas;
	private static final int PAGESIZE = 10;
	private Map<String, Object> session; // session 会话
	private HttpServletRequest request;// request

	private TUser user;
	private TOrgainzation org;

	private Integer tid; // 终端索引
	private String sim; // 终端编号
	private String option; // 搜索条件
	private String searchValue; // 搜索值

	private List<TTerminal> terminals;
	private List<TCarInfo> cars; // 车型信息

	// 页面跳转参数
	private String page;
	private String TARGET = "target";
	private String result;

	// 分页有关的数据
	private int total;// 总数
	private int pageIndex;// 当前页
	private int pages;// 页面总数

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

	// 两个与时间有关的属性，这两个常用于搜索中
	private Date searchStartDate;
	private Date searchEndDate;

	// 与搜索有关的属性 --- 这个是action内部的属性，不用get和set
	private boolean isSearching = false;// 这个属性表示是否当前的列表是处于搜索结果中，因为如果不设置的话那么搜索结果翻页无效
	private String searchString;// 要搜索的"名称"

	/**
	 * 由spring 完成服务注入
	 */
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private OrganizationServiceImpl organizationService;
	private TerminalServiceImpl terminalService;
	private CarInfoServiceImpl carInfoService;
	private AccountServiceImpl accountService;
	private TempPositionServiceImpl tempPositionService;
	private AreaServiceImpl areaService;

	// 数据报表管理
	private int searchWeiyi;// 唯一搜索的条件
	private String searchTerminalNum;// 终端编号 或者 手机号
	private Date searchFromDate;// 开始时间
	private Date searchToDate;// 结束时间
	private int onoff;// 在线状态
	private int carType;// 目标类型
	// private int searchGroupId;// 搜索的所属的分组机构
	private List<TTerminal> terminalExcelList;// 结果列表
	private List<TCarInfo> carInfoList;// 车类型列表
	private String cols;// 选中的要导出的列，用数字和逗号组成
	private InputStream excelStream; // excel输入流

	// private static final int PAGESIZE = 10;
	// private TTerminal terminal;// 进行编辑的终端
	// private boolean isSearching = false;// 这个属性表示是否当前的列表是处于搜索结果中，因为如果不设置的话那么搜索结果翻页无效
	// private String searchString;// 要搜索的"名称"

	/**
	 * 基本资料
	 * 
	 * @@return
	 */
	public String basicInfo() {
		user = (TUser) session.get("user");// 直接从会话中获取
		return SUCCESS;
	}

	/**
	 * 用户管理
	 * 
	 * @@return
	 */
	public String userManager() {
		return terminalManager();
	}

	/**
	 * 查询终端用户 账户管理
	 * 
	 * @return
	 */
	// <<<<<<< GroupAction.java
	// public String userSearch() {
	// user = (TUser) session.get("user");// 直接从会话中获取
	// System.out.println(sim + "\t" + user.getTOrgainzation().getOrgId());
	// if (sim != null) {
	// int orgid = user.getTOrgainzation().getOrgId();
	// total = terminalService.getCountMohuByOrgAndSim(sim, orgid);
	// // 得到总页数
	// if (total % Page.DEFAULT_PAGE_SIZE == 0) {
	// pages = total / Page.DEFAULT_PAGE_SIZE;
	// } else {
	// pages = total / Page.DEFAULT_PAGE_SIZE + 1;
	// }
	// // 得到当前的页索引
	// if (pageIndex < 1)
	// pageIndex = 1;
	// if (pageIndex > pages)// 暂时不判断
	// pageIndex = pages;
	// terminals = terminalService.findByOrg(orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
	//
	// terminals = terminalService.findMohuByOrgAndSim(sim, orgid);
	// } else {
	// userManager();
	// }
	// =======
	public String userSearch() {
		// user = (TUser) session.get("user");// 直接从会话中获取
		// System.out.println(sim+"\t"+user.getTOrgainzation().getOrgId());
		// if(sim != null){
		// int orgid = user.getTOrgainzation().getOrgId();
		// total = terminalService.getCountMohuByOrgAndSim(sim, orgid);
		// // 得到总页数
		// if (total % Page.DEFAULT_PAGE_SIZE == 0) {
		// pages = total / Page.DEFAULT_PAGE_SIZE;
		// } else {
		// pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		// }
		// // 得到当前的页索引
		// if (pageIndex < 1)
		// pageIndex = 1;
		// if (pageIndex > pages)// 暂时不判断
		// pageIndex = pages;
		// terminals = terminalService.findByOrg(orgid,(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		//
		// terminals = terminalService.findMohuByOrgAndSim(sim, orgid);
		// }else{
		// userManager();
		// }
		terminalSearch();
		// >>>>>>> 1.11
		page = "Group_userManager";
		return TARGET;
	}

	/**
	 * 查询终端用于 终端管理
	 * 
	 * @return
	 */
	public String terminalSearch() {

		user = (TUser) session.get("user");// 直接从会话中获取
		if (option != null) {
			// >>>>>>> 1.11
			int orgid = user.getTOrgainzation().getOrgId();
			if ("1".equals(option)) { // 机器编号
				total = terminalService.getCountMohuByOrgAndCarNumber(searchValue, orgid);
			} else if ("2".equals(option)) {// 联系人
				total = terminalService.getCountMohuByOrgAndUserName(searchValue, orgid);
			} else if ("3".equals(option)) { // 终端号码
				total = terminalService.getCountMohuByOrgAndSim(searchValue, orgid);
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
			// <<<<<<< GroupAction.java
			// terminals = terminalService.findByOrg(orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			//
			// terminals = terminalService.findMohuByOrgAndSim(sim, orgid);
			// } else {
			// =======
			// terminals = terminalService.findByOrg(orgid,(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			if ("1".equals(option)) { // 机器编号
				terminals = terminalService.findMohuByOrgAndCarNumber(searchValue, orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			} else if ("2".equals(option)) {// 联系人
				terminals = terminalService.findMohuByOrgAndUserName(searchValue, orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			} else if ("3".equals(option)) { // 终端号码
				terminals = terminalService.findMohuByOrgAndSim(searchValue, orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			}
		} else {
			userManager();
		}
		page = "Group_terminalManager";
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
		page = "Group_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了终端【" + terminal.getSim() + "】", new Date()));
		request.setAttribute("result", "修改成功！");
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
		page = "Group_userManager";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了终端【" + sim + "】", new Date()));
		userManager();
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
	 * 保存终端信息
	 * 
	 * @@return
	 */
	public String saveTerminal() {
		page = "Group_addTerminal";
		TTerminal temp = terminalService.findBySim(terminal.getSim());
		if (temp != null) {
			request.setAttribute("result", "该终端编号已经存在!");
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "该SIM卡号已经存在!");
			return TARGET;
		}
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminal.setRegistertime(new Date());
		TUser user = (TUser) session.get("user");
		terminal.setTOrgainzation(user.getTOrgainzation());
		terminal.setSignal(0);
		terminal.setElepress("0");
		terminalService.addTerminal(terminal);

		// terminal = terminalService.findBySim(terminal.getSim());
		TTempPosition position = new TTempPosition();
System.out.println("************************"+terminal.getSim());
		position.setSim(terminal.getSim());
		position.setTTerminal(terminal);
		position.setLatitude(-1);
		position.setLongitude(-1);
		position.setLatoffset(0);
		position.setLngoffset(0);
		position.setPTime(new Date());
		tempPositionService.addTempPosition(position);
		request.setAttribute("result", "新增终端用户成功!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "新增了终端【" + terminal.getSim() + "】", new Date()));
		return TARGET;
	}

	/**
	 * 终端管理
	 * 
	 * @@return
	 */
	public String terminalManager() {
		sim = null;
		option = null;
		searchValue = null;
		TUser user = (TUser) session.get("user");
		int orgid = user.getTOrgainzation().getOrgId();
		total = terminalService.getCountByOrg(orgid);
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
		terminals = terminalService.findByOrg(orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		return SUCCESS;
	}

	// -----------------------------------日志管理----------------------------------------//

	/**
	 * 日志管理 日志管理不遵循前面的isSearching规律，它不处理isSearching
	 * 
	 * @return
	 */
	public String logManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);// 到下一天的凌晨
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -3);// 前三天的凌晨
		searchStartDate = calendar.getTime();// 设定开始和结束时间
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
			page = "Group_logManager";
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
		System.out.println(searchStartDate);
		System.out.println(searchEndDate);
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
		page = "Group_logManager";
		return TARGET;
	}

	// ///////////////////////////// 终端用户的充值 /////////////////////////////////
	/**
	 * 分组用户下的终端用户的费用管理
	 */
	public String terminalExpenseManager() {
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		findTerminalExpenseList();
		return SUCCESS;
	}

	/**
	 * 费用管理中的终端用户查询
	 * 
	 * @return
	 */
	public String terminalExpenseSearch() {
		isSearching = true;
		pageIndex = 0;
		findTerminalExpenseList();
		return TARGET;
	}

	/**
	 * 用于分页中的终端用户费用管理 终端的模糊搜索还没有底层支持，终端应该是根据sim卡号搜索
	 * 
	 * @return
	 */
	public String findTerminalExpenseList() {
		user = (TUser) session.get("user");
		int orgId = user.getTOrgainzation().getOrgId();
		// 得到总记录数
		if (isSearching) {
			total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
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
		System.out.println("pageIndex=" + pageIndex);
		// 得到该页显示的记录
		if (isSearching) {
			terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "Group_terminalExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// 终端用户的充值 /////////////////////////////////
	/**
	 * 进入终端用户充值界面，这里并没有充值
	 * 
	 * @return
	 */
	public String terminalExpenseAdd() {
		System.out.println("terminal Id = " + terminalId);
		terminal = terminalService.findById(terminalId);
		account = null;// 应该不需要new，如果不充值的话就产生了多余的对象
		return SUCCESS;
	}

	/**
	 * 给终端用户充值
	 * 
	 * @return
	 */
	public String addTerminalExpense() {
		System.out.println(serviceStartDate.toLocaleString());
		System.out.println(serviceEndDate.toLocaleString());
		// 判断两个时间间隔是否是一整年！
		if ((serviceEndDate.getYear() - serviceStartDate.getYear()) != 1) {
			request.setAttribute("result", "时间间隔必须是一整年！");
			page = "Group_terminalExpenseAdd";
			return TARGET;// 这样返回页面，输入的数据就会没了！---应该不会没有了
		}
		user = (TUser) session.get("user");
		TOrgainzation pOrgainzation = user.getTOrgainzation();// pOrgainzation不为null，但是数据为空
		if (pOrgainzation.getBalance() < pOrgainzation.getFeestandard()) {// 分组机构的余额和服务年费比较，如果不足
			request.setAttribute("result", "分组机构的余额不足！");
			page = "Group_terminalExpenseAdd";
			return TARGET;
		}
		// 分组减，不要忘记update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - pOrgainzation.getFeestandard());
		// 充值成功，更新terminal
		terminal.setStartTime(serviceStartDate);// 设置服务时间
		terminal.setEndTime(serviceEndDate);
		terminalService.updateTerminal(terminal);
		// 插入一条账单
		// account.setExpense(pOrgainzation.getFeestandard());// 本次账单的费用是标准年费
		// account是null，但是如果是在进入之前new还是错的！因为没有id
		// account是null的原因是前台要提交的表单中并没有account中的内容（我觉得是，但还是错了）
		// 添加一个隐藏域：<input name="account.Expense" type="hidden" value="terminal.TOrgainzation.feestandard" />
		account.setTOrgainzation(pOrgainzation);// 给终端充值时账单中显示的还是分组用户
		account.setPaiddate(new Date());
		account.setPaider(pOrgainzation.getName());
		String remark = user.getName() + "为终端" + terminal.getSim() + "充值了，服务时间是" + terminal.getStartTime().toLocaleString() + "-"
				+ terminal.getEndTime().toLocaleString();
		account.setRemark(remark);// 添加备注
		organizationService.updateOrganization(pOrgainzation);// 提交语句
		accountService.addAccount(account);// TODO：这里报错！
		request.setAttribute("result", "充值成功！");
		page = "Group_terminalExpenseAdd";
		return TARGET;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	// ------------------------------ 数据报表管理 ---------------------------------------//

	/**
	 * 数据报表管理列表
	 */
	public String excelManager() {
		isSearching = false;
		searchString = "";
		cols = "";
		searchFromDate = null;
		searchEndDate = null;
		carType = 0;
		searchWeiyi = -1;// 这个很重要，进入的时候不搜索！
		onoff = 2;
		pageIndex = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
		carInfoList = carInfoService.findAll();// 得到所有的车类型
		findTerminalExcelList();
		return SUCCESS;
	}

	/**
	 * 搜索terminal
	 */
	public String terminalExcelSearch() {
		isSearching = true;
		findTerminalExcelList();
		page = "Group_excelManager";
		return TARGET;
	}

	/**
	 * 得到搜索结果的终端列表
	 * 
	 * @return
	 */
	public String findTerminalExcelList() {
		user = (TUser) session.get("user");
		terminalExcelList = new ArrayList<TTerminal>();
		if (!isSearching) {
			total = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
			pages = 0;
			pageIndex = 0;
			page = "Group_excelManager";
			return TARGET;
		}
		if (searchEndDate == null && searchFromDate == null && onoff == 2 && carType == 0) {
			total = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
			pages = 0;
			pageIndex = 0;
			page = "Group_excelManager";
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
		page = "Group_excelManager";
		return TARGET;
	}

	// 得到当前的条件
	private DetachedCriteria getCurrentCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
		user = (TUser) session.get("user");
		// 隐含的条件
		detachedCriteria.createAlias("TOrgainzation", "to").add(Restrictions.eq("to.orgId", user.getTOrgainzation().getOrgId()));
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
		if (carType != 0) {
			// 其他表的非主键
			detachedCriteria.createAlias("TCarInfo", "carInfo").add(Restrictions.eq("carInfo.carTypeId", carType));
		}
		return detachedCriteria;
	}

	// 根据选中的列导出数据
	public String excelExport() {
		String[] colValues = cols.split(",");
		if (colValues.length == 0) {
			page = "Group_excelManager";
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
					sheet.setColumnView(index, 25);// ？不知道 index列的宽度？
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
					} else if (terminal.getNetstatus() == 0) {
						netString = "离线";
					} else {
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

	// ////////////////////////////////////////////////////////////////////////////////////////////

	// ///////////////////////////////////////set and getters.///////////////////////////////////////////////
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
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

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public TOrgainzation getOrg() {
		return org;
	}

	public void setOrg(TOrgainzation org) {
		this.org = org;
	}

	public List<TTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<TTerminal> terminals) {
		this.terminals = terminals;
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

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public List<TCarInfo> getCars() {
		return cars;
	}

	public void setCars(List<TCarInfo> cars) {
		this.cars = cars;
	}

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
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

	public List<TLog> getLogList() {
		return logList;
	}

	public void setLogList(List<TLog> logList) {
		this.logList = logList;
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

	public void setSearchString(String searchString) {
		this.searchString = searchString;
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

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public List<TTerminal> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TTerminal> terminalList) {
		this.terminalList = terminalList;
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

	public AccountServiceImpl getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountServiceImpl accountService) {
		this.accountService = accountService;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
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

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public List<TArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TArea> areas) {
		this.areas = areas;
	}

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public int getSelectAreaId() {
		return selectAreaId;
	}

	public void setSelectAreaId(int selectAreaId) {
		this.selectAreaId = selectAreaId;
	}

}
